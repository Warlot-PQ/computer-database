#Computer Data Base project

## Source

https://github.com/loicortola/spec-cdb

## Configuration

Set environment variable CATALINA_HOME (in /etc/profile) to Tomcat 8 directory (requiered by cargo-maven2-plugin to automaticaly launch the Tomcat server).

## Librairy used

mysql-connector-java-5.1.39-bin.jar --- 

slf4j-api-1.7.21.jar --- Logger API

logback-classic-1.1.7.jar --- Logger implementation

logback-core-1.1.7.jar --- Logger implementation

jUnit4 --- Unit test

jsp --- Dynamically create XHTML

jstl --- Tag JSP

selenium.java --- UI integration testing

javax.servlet.api --- Servlet API

javax.servlet-jsp.api --- JSP API

commons-lang3 --- escape HTML code.

## Plugin used

maven-compiler-plugin --- Maven compilation

maven-war-plugin --- War file deployment

maven-checkstyle-plugin --- Code checkstyle

cargo-maven2-plugin --- start Tomcat server

selenium-maven-plugin --- start Selenium server

maven-surefire-plugin --- Run integration tests, exclude integration tests from unit tests

## Maven command

mvn checkstyle:checkstyle --- give the number of checkstyle errors.

mvn checkstyle:check --- give all checkstyle errors.

mvn clean test --- clean project and run unit test.

mvn clean integration-test --- clean project and run integration-test.