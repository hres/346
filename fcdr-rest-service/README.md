# Sodium Monitoring Task Service

This project provides the sodium monitoring task REST services

---

## Components and Features

This project uses the following components and features

* Java 8
* Maven 3.5.2
* Tomcat 8.0.52
* PostgreSQL 9.6
* Git


The availability of the above tools is assumed throughout the instructions on this page

---

## How to build fcdr-rest-service-1.war file

To deploy the war file, do the following:

1. `cd ~/repositories`
2. `git clone https://github.com/hres/346R1.git`
3. `cd fcdr-rest-service`
4. ` mvn clean intall`
5. `copy target/fcdr-rest-service-1.war`  to `webapps` directory of Tomcat 8.0 on HRE

---
## Install PostgreSQL 9.6 and create a database for the sodium monitoring

1. On the command-line run `sudo apt-get install postgresql-9.6` to install PostgreSQL 
2. On the command-line from the project src/scripts directory, run <br/><br/>
	`psql postgres postgres` to login as postgres user <br/><br/>
	`DROP DATABASE sodium_db_dev;` (if its already there, use `\l` to check) <br/><br/>
	`CREATE DATABASE sodium_db_dev;` <br/><br/>
	`\c sodium_db_dev` <br/><br/>
	`\i sodium_refactoring.sql` <br/><br/>
	`\i lkp_data.sql` <br/><br/>
	
---
## Database credentials 	

create a file on a root accessible folder <br/>
`/etc/sodium-monitoring/config.properties ` <br/>
insigle config.properties set the following variables: <br><br>
`url=url of the database`<br>
`user= user of the database`<br>
`password= database's password`<br>
`schema= database's schema`<br>
