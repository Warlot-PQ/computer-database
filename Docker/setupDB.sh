#!/bin/bash

mysqlUser=$1
mysqlPassword=$2

mysql --user="root" --password="PASSWORD" < "./DB/1-SCHEMA.sql";
mysql --user="root" --password="PASSWORD" < "./DB/2-PRIVILEGES-DOCKER.sql";
mysql --user="root" --password="PASSWORD" < "./DB/3-ENTRIES.sql";
mysql --user="root" --password="PASSWORD" < "./DB-test/1-SCHEMA-TEST.sql";
mysql --user="root" --password="PASSWORD" < "./DB-test/2-PRIVILEGES-DOCKER-TEST.sql";
mysql --user="root" --password="PASSWORD" < "./DB-test/3-ENTRIES-TEST.sql";