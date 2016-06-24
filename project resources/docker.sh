#!/bin/bash

WORKSPACE="../"

if [ $# -ne 4 ]; then
	echo "Please use four arguments, user and password to connect to the DB plus user and password to connect to Docker Hub."
	exit -1
fi

echo "===> Declaring variables";
DOCKER_IMAGE_MAVEN="java-maven"
DOCKER_IMAGE_DB="mysql:5.5"
DOCKER_IMAGE_TOMCAT="tomcat-prod"

DOCKER_CONTAINER_BUILDER="maven"
DOCKER_CONTAINER_DB="mysql"
DOCKER_CONTAINER_TOMCAT="tomcat-prod-cdb"
DOCKER_NETWORK="customNetwork"

NETWORK_IP="172.25.0.0/16"
DOCKER_IP_DB="unknown for know"

DOCKERFILE_MAVEN_PATH="Docker/dockerMaven"
DOCKERFILE_TOMCAT_PATH="Docker/dockerTomcat"
SETUP_DB_FILE="setupDB.sh"

# Remove previous artefacts, || true is used no to stop Jenkins on remove error
echo "===> Remove all previous install artefacts:";
docker network disconnect ${DOCKER_NETWORK} ${DOCKER_CONTAINER_BUILDER} || true
docker network disconnect ${DOCKER_NETWORK} ${DOCKER_CONTAINER_DB} || true
docker network disconnect ${DOCKER_NETWORK} ${DOCKER_CONTAINER_TOMCAT} || true
docker network rm ${DOCKER_NETWORK} || true
docker rm -f ${DOCKER_CONTAINER_TOMCAT} || true
docker rm -f ${DOCKER_CONTAINER_BUILDER} || true
docker rm -f ${DOCKER_CONTAINER_DB} || true

echo "===> Replace .properties user and password values:"
sed -i -e "s/USER/${1}/g" ${WORKSPACE}/cdb-persistence/src/main/resources/hikari.properties 
sed -i -e "s/PASSWORD/${2}/g" ${WORKSPACE}/cdb-persistence/src/main/resources/hikari.properties 
sed -i -e "s/PASSWORD/${2}/g" Docker/${SETUP_DB_FILE}

# Create network
echo "===> Creating network:";
docker network create -d bridge --subnet ${NETWORK_IP} ${DOCKER_NETWORK}

echo "===> Launching test container and connect to ${DOCKER_NETWORK}:"
# mysql:5.5 only. 5.7 core changed generating error on Aggretation without Group By.
echo "===> Launching MySQL container:"
docker run --net=${DOCKER_NETWORK} --name ${DOCKER_CONTAINER_DB} -e MYSQL_ROOT_PASSWORD=qwerty1234 -d ${DOCKER_IMAGE_DB}

# Retrieve IP DB container
echo "===> Retrieve IP DB container:"
DOCKER_IP_DB=`docker inspect --format {{.NetworkSettings.Networks.${DOCKER_NETWORK}.IPAddress}} ${DOCKER_CONTAINER_DB}`
echo "===> DB container ${DOCKER_CONTAINER_DB} is at $DOCKER_IP_DB"

# Set up DB docker
echo "===> Copying set up files to ${DOCKER_CONTAINER_DB} container:"
docker cp ${WORKSPACE}/cdb-persistence/src/main/resources/DB/ ${DOCKER_CONTAINER_DB}:/DB
docker cp ${WORKSPACE}/cdb-persistence/src/main/resources/DB/ ${DOCKER_CONTAINER_DB}:/DB-test
docker cp Docker/${SETUP_DB_FILE} ${DOCKER_CONTAINER_DB}:/setupDB.sh

# Let mysql container start and initialize itself
echo "===> Pause to let ${DOCKER_CONTAINER_DB} container start and initialize itself:"
sleep 10 

echo "===> Configurating ${DOCKER_CONTAINER_DB} DB:"
docker exec -i ${DOCKER_CONTAINER_DB} /setupDB.sh

# Set up Maven docker pre-build
echo "===> Transfering computer-database projet with (Tomcat-8 server) to ${DOCKERFILE_MAVEN_PATH}:"
rsync -a --exclude=Docker --exclude=gatling-test ${WORKSPACE} ${WORKSPACE}/${DOCKERFILE_MAVEN_PATH}/computer-database

echo "==> Build Maven image:"
docker build -t ${DOCKER_IMAGE_MAVEN} ${WORKSPACE}/${DOCKERFILE_MAVEN_PATH}

# Start Java-Maven and MySQL docker
#-d run in background -i keep stdi open -t open a pseudo-tty
docker run --net=${DOCKER_NETWORK} -dit --name ${DOCKER_CONTAINER_BUILDER} ${DOCKER_IMAGE_MAVEN} bash

echo "===> Launching maven resources, testResources and install:"
docker exec -i ${DOCKER_CONTAINER_BUILDER} mvn -f /computer-database/pom.xml resources:resources -DdbIP="${DOCKER_IP_DB}"
docker exec -i ${DOCKER_CONTAINER_BUILDER} mvn -f /computer-database/pom.xml resources:testResources -DdbIP="${DOCKER_IP_DB}"
docker exec -i ${DOCKER_CONTAINER_BUILDER} mvn -f /computer-database/pom.xml -DdbIP="${DOCKER_IP_DB}" install

# Copy Tomcat war
echo "===> Transfering war file from ${DOCKER_CONTAINER_BUILDER} to ${DOCKER_CONTAINER_TOMCAT} build directory"

docker cp ${DOCKER_CONTAINER_BUILDER}:/computer-database/target/computer-database-0.3.war ${WORKSPACE}/${DOCKERFILE_TOMCAT_PATH}

echo "===> Build Tomcat image with the new war file"
docker build -t ${DOCKER_IMAGE_TOMCAT} ${WORKSPACE}/${DOCKERFILE_TOMCAT_PATH}

echo "===> Launching Tomcat container, accessible on host port 9080:"
echo "===> CAUTION: ${DOCKER_CONTAINER_DB} is the prod DB for now, use a prod DB later and a prod network!"
docker run --net=${DOCKER_NETWORK} -d -p 9080:8080 --name ${DOCKER_CONTAINER_TOMCAT} ${DOCKER_IMAGE_TOMCAT}

# Push Tomcat container to docker hub
echo "===> Tagging ${DOCKER_IMAGE_TOMCAT} container:"
docker commit ${DOCKER_CONTAINER_TOMCAT} pqwarlot/${DOCKER_CONTAINER_TOMCAT}

echo "===> Authentifying to Docker Hub:"
docker login --username="${3}" --password="${4}"

echo "===> Pushing ${DOCKER_CONTAINER_TOMCAT} container to Docker Hub:"
docker push pqwarlot/${DOCKER_CONTAINER_TOMCAT}

rm -Rf ${WORKSPACE}/${DOCKERFILE_MAVEN_PATH}/computer-database
rm -f ${WORKSPACE}/${DOCKERFILE_TOMCAT_PATH}/*.war