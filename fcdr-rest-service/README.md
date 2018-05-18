# Sodium Monitoring Task Service
---
This project provides the sodium monitoring task REST services

## Components and Features
---
Tis project uses the following components and features

* Java 8
* Maven 3.5.2
* Tomcat 8.0.52
* PostgreSQL 9.6
* Git


The availability of the above tools is assumed throughout the instructions on this page

---

## How to build fcdr-rest-service-0.0.1-SNAPSHOT.war 
---
To deploy the war file, do the following:

1. cd ~/repositories
2. git clone https://github.com/hres/346R1.git
3. cd fcdr-rest-service
4. mvn clean intall
5. copy target/fcdr-rest-service-0.0.1-SNAPSHOT.war  to webapps directory of Tomcat 8.0 on HRE
