FROM jenkins:latest

USER root
WORKDIR /tmp

RUN	wget https://get.docker.com/builds/Linux/x86_64/docker-1.9.1.tgz
RUN	tar -xf docker-1.9.1.tgz
RUN	mv ./usr/local/bin/docker /usr/bin/
RUN	chmod 770 /usr/bin/docker

RUN	groupadd docker
RUN	usermod -aG docker jenkins

RUN apt-get update
RUN apt-get install rsync