# GRADLE

## What is Gradle?

Gradle is a tool that helps you build, test, and package your code. Think of it like a recipe book: it tells your computer how to turn your source code into a finished product (like a jar file).

## Major Sections of Gradle Files

- **plugins**: These are add-ons that give Gradle extra powers. For example, the `org.springframework.boot` plugin helps Gradle build Spring Boot applications.  
  Other useful plugins include:
  - `org.sonarqube`: Integrates SonarQube for code quality analysis.
  - `jacoco`: Adds code coverage reporting.
  - `io.spring.dependency-management`: Manages dependency versions.
  - `application`: Helps run Java applications from the command line.
  ```kotlin
  plugins {
      id("org.springframework.boot") version "3.2.0"
      id("org.sonarqube") version "4.0.0.2929"
      id("jacoco")
      id("java")
  }
  ```

- **java toolchain**: Tells Gradle which version of Java to use.
- **group, version, description**: Basic info about your project.
- **repositories**: Places where Gradle looks for libraries (like Maven Central).
- **dependencies**: Lists the libraries your project needs (like Spring Boot, Apache Commons).<br/><br/>
- **tasks**: Special instructions for building, testing, or packaging your code.  
  Tasks can be built-in (like `build`, `test`, `clean`) or custom.  
  You can also configure tasks for plugins, such as running SonarQube analysis or generating code coverage reports.
  ```kotlin
  // this sets up the SonarQube task to run after tests complete
  tasks.named("sonarqube") {
      dependsOn("test") // Ensure tests run before analysis
  }
  
  // this configures jacoco to generate code coverage reports
  jacocoTestReport {
      reports {
          xml.required.set(true)
          html.required.set(true)
      }
  }
  ```

### What are Plugins?

Plugins are like apps for Gradle. They add features. For example:
- The **Spring Boot plugin** helps package your app so it can run easily.
- The **Java plugin** helps compile Java code.
- The **Jacoco plugin** generates code coverage reports to see how much of your code is tested.
- The **SonarQube plugin** checks your code for quality issues.
  > NOTE: SonarQube is a tool that analyzes your code for bugs, vulnerabilities, and code smells. It helps you maintain high code quality.
  It is a third party server that is integrated in the CI/CD pipeline to ensure code quality before deployment.
  The server connection is configured in the Jenkins portal by the DevOps team, and developers can run the analysis locally using the SonarQube Gradle plugin. 
  Below is the link about the SonarQube server and its usage:
  ![](https://www.tatvasoft.com/blog/wp-content/uploads/2020/10/How-to-Integrate-SonarQube-with-Jenkins-1.jpg)

---
<br/>

## Build Lifecycle (How Gradle/Maven Build Projects)

1. **Clean**: Removes old build files.
2. **Compile**: Turns your source code into bytecode <i>(i.e *.java -> *.class)</i>.
3. **Test**: Runs your unit tests.
4. **Package**: Bundles everything into a jar file <i>(jar is a kind of compressed zip file)</i>.
5. **Run/Deploy**: Starts your application.

Gradle and Maven follow these steps automatically when you run commands like `gradle build` or `mvn package`.

```bash
# Clean and build the project
./gradlew clean build
```

### How is a Spring Boot Jar Assembled?

- Gradle uses the **bootJar** task to create a special jar file.
- The jar includes your code, libraries, and a manifest file.
- The **main class** (entry point) is set in the Gradle file (`com.example.tutorial.Application`).
- When you run the jar, Java looks for this main class and starts your app from there.

> **Example: Main Class Declaration**
```kotlin
springBoot {
    mainClass.set("com.example.tutorial.Application")
}
```
*// This tells Spring Boot which class to run first.*

---

## How Does Tomcat Start the Application?

- **Tomcat** is a web server that runs Java web apps.
- Spring Boot includes an embedded Tomcat server inside the jar.
- When you run the jar (`java -jar yourapp.jar`), Tomcat starts automatically.
- Tomcat looks for the main class, which starts Spring Boot, and sets up your REST API endpoints.

![Tomcat Architecture](https://upload.wikimedia.org/wikipedia/commons/4/4e/Apache_Tomcat_logo.svg)

> **Example: Spring Boot Main Class**
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // Starts Tomcat and your app
    }
}
```
*// This is the entry point for your Spring Boot app.*

---

## What is Tomcat Server?

Tomcat is like a waiter in a restaurant. It listens for requests (like "give me data!") and passes them to your app. It then returns the response to whoever asked.

---

## How to Deploy a Spring Boot Jar

1. Build your jar file using Gradle (`gradle bootJar`).
2. Copy the jar to your server.
3. Run it with: `java -jar yourapp.jar`
4. Tomcat starts, and your API is live!

> **Example: Running the Jar**
```bash
java -jar build/libs/yourapp.jar
```
*// This starts your Spring Boot application.*

---

## Real-World Example

Imagine you built a REST API for a library. You write code, Gradle packages it, and you run the jar. Tomcat starts, and people can ask your API for book info!

> **Example: Simple REST Controller**
```java
@RestController
public class BookController {
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id) {
        // ...fetch book from database...
        return new Book(id, "Spring in Action");
    }
}
```
*// This endpoint returns book info for a given ID.*

---

