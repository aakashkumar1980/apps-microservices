To load the profiles in spring boot application, you can follow these steps:

## Local laptop setup
### using IntelliJ IDEA:
Select "Edit Configurations" in the Run menu, then add a new configuration for your Spring Boot application. In the "VM options" field, you can specify the active profile using `-Dspring.profiles.active=dev` or any other profile you want to use.

### using command line:
use the following command to run your Spring Boot application with a specific profile:
```bash
$ java -jar build/libs/springboot-restapi-0.0.1.jar --spring.profiles.active=dev
```
<br/>

## Server setup (Openshift/EKS)
In the server setup, you can specify the active profile in the deployment configuration. For example, if you are using OpenShift or EKS, you can set the environment variable `SPRING_PROFILES_ACTIVE` to the desired profile.