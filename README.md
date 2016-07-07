#Computer Data Base project

## Source

https://github.com/loicortola/spec-cdb

## Logback

Check parent pom logPath properties for logging file. The path must be correct to properly deploy the project.

## DB configuration

Use SQL file main/resources and test/resources in persistence module to setup.

## SSL configuration

Create a certificate by running the following command.

```javascript
/usr/bin/keytool -genkey -alias tomcat -keyalg RSA
```

When prompt is asking for a password, type enter to set the default password 'changeit'. .keystore file is generated in ${user.home}.

Configure the Tomcat server, TOMCAT_DIR/conf/server.xml

```xml
<Connector port="8443" 
		protocol="org.apache.coyote.http11.Http11NioProtocol"
		maxThreads="150" 
		scheme="https"
		secure="true"
		SSLEnabled="true"
		keystoreFile="${user.home}/.keystore"
		keystorePass="changeit"
		clientAuth="false"
		sslProtocol="TLS" />
```

## Maven and Tomcat config

To configure a Tomcat server and SSL in a maven multi-modules properly, create /home/.m2/setting.xml and /home/apache-tomcat-8.0.33/conf/tomcat-users.xml. Files are in "project resources/multi-modules". And configure the tomcat.directory properties in the parent pom to point your tomcat 8 directory.

To launch the project, first launch the Tomcat with ./catalina.sh and deploy the project into the server with mvn tomcat7:redeplay. Or run mvn tomcat7:run-war to do both with one command.

## QueryDSL

At compile-time, Qclasses are generated in target/generated-sources/queryDSL. Copy these files in com.excilys.entity package to make repository classes working.

## Todo before using project with docker features (not up to date)

Download Tomcat : at root project directory, enter mkdir apache-tomcat-8.0.33 && cd apache-tomcat-8.0.33 && wget http://wwwftp.ciril.fr/pub/apache/tomcat/tomcat-8/v8.0.35/bin/apache-tomcat-8.0.35.tar.gz && tar xvf apache-tomcat-8.0.35.tar.gz && mv apache-tomcat-8.0.35/* . && rm -R apache-tomcat-8.0.35

In Docker/dockerJenkins/config.xml, replace --user-- and --password-- with true values.

In Docker/setupDB.sh, src/main/resources/hikari.properties and src/test/resources/hikari.properties, set MySQL password.

## Docker

Config: Configure GitHub and Jenkins. Use pqwarlot/jenkins-cdb Docker Cloud image with dockerJenkins/config.xml test job (on docker branch) on Docker Cloud. Install rsync on the Jenkins container.

Use bash script docker.sh to localy launch continous integration process. Use localhost:9080/computer-database/Dashboard to reach Tomcat server on docker contrainer.

Work left : accept push only if all test working and push to master. Make Selenium use DB test only. Configure Jenkins to deploy Tomcat image containing war file. Place docker.sh on Jenkins container, not on the GitHub project.

### Help

docker build -t jenkins-cdb ./

docker run -d --name jenkins -v /var/run/docker.sock:/var/run/docker.sock -p 8085:8080 -p 
50000:50000 jenkins-cdb