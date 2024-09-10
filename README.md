# Book Store

### Author: Aliaksandr Kolosovsky

## Project Overview

This project is a Book Store application.

## Technologies Used

- **Database**: PostgreSQL
- **Web Server**: Apache Tomcat

## Configuration

### Database

| Key                    | Value                                            |
| ---------------------- | ------------------------------------------------ |
| DataBase.url           | `jdbc:postgresql://localhost:5432/bookstore`   |
| DataBase.login         | `postgres`                                      |
| DataBase.password      | `root`                                          |

## Maven Configuration

The project uses Maven for build and dependency management. Below are the key dependencies and versions:

```xml
<properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <postgresql.version>42.7.3</postgresql.version>
    <log4j.version>2.23.1</log4j.version>
    <slf4j-api.version>2.0.13</slf4j-api.version>
    <jakarta.servlet-api.version>6.0.0</jakarta.servlet-api.version>
    <maven-war-plugin.version>3.4.0</maven-war-plugin.version>
    <jakarta.servlet.jsp-api.version>4.0.0</jakarta.servlet.jsp-api.version>
    <jakarta.servlet.jsp.jstl.version>3.0.1</jakarta.servlet.jsp.jstl.version>
    <lombok.version>1.18.34</lombok.version>
</properties>
