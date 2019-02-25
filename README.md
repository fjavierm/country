# country
Learning project about Microservices, cloud, docker, Kubernetes and any crazy stuff. Let's play.

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
