# country
Learning project about Microservices, cloud, docker, Kubernetes and any crazy stuff. Let's play.

![Java](https://img.shields.io/badge/jdk-11-blue.svg)
[![Build Status](https://travis-ci.org/fjavierm/country.svg?branch=master)](https://travis-ci.org/fjavierm/country)
[![License](https://img.shields.io/badge/License-Apache%20V2.0-orange.svg)](http://www.apache.org/licenses/LICENSE-2.0)

## Technical information

The application has active the `maven-surefire-plugin` and `maven-failsafe-plugin` plugins.

### maven-surefire-plugin
- \*\*/Test*.java
- **/*Test.java
- **/*TestCase.java

### maven-failsafe-plugin
- \*\*/IT*.java
- **/*IT.java
- **/*ITCase.java

### Using the auto run for docker container dependencies

If we want to allow our development environments to run the docker container dependencies automatically, we need to add the active profile `development` to our application:

`-Dspring.profiles.active=development`
