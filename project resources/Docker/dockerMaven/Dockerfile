FROM java:8

RUN \
	apt-get update && apt-get install -y maven

RUN \
	wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - && \
	echo "deb http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google.list && \
	apt-get update && \
	apt-get install -y google-chrome-stable && \
	rm -rf /var/lib/apt/lists/*

CMD	echo "===> Maven version:" && \
	["mvn", "-v"]

COPY computer-database /computer-database