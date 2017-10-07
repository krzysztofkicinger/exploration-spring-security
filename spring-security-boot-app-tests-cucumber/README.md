# Testing Spring Boot application secured with Spring Security using Cucumber and Kotlin

## Maven Configuration

Add following properties:

```xml
<kotlin.version>1.1.51</kotlin.version>
<cucumber.version>1.2.5</cucumber.version>
```

Add following dependencies:

```xml
<dependencies>
        <!-- CUCUMBER BDD TESTS -->
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-core</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-java8</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-spring</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>
        <!-- KOTLIN DEPENDENCIES -->
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib-jre8</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-test-junit</artifactId>
            <version>${kotlin.version}</version>
            <scope>test</scope>
        </dependency>
        <!-- SPRING SECURITY TESTS -->
        <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <version>${spring-security.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

Add following build configuration:

```xml
<build>
        <testSourceDirectory>src/test/kotlin</testSourceDirectory>
        <testResources>
            <testResource>
                <directory>src/test/resources</directory>
            </testResource>
        </testResources>
        <plugins>
            <plugin>
                <groupId>org.jetbrains.kotlin</groupId>
                <artifactId>kotlin-maven-plugin</artifactId>
                <version>${kotlin.version}</version>
                <executions>
                    <execution>
                        <id>compile</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>test-compile</id>
                        <phase>test-compile</phase>
                        <goals>
                            <goal>test-compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

## Main Cucumber runner configuration

```kotlin
@RunWith(Cucumber::class)
@CucumberOptions(
        features = arrayOf("src/test/resources/features"),
        glue = arrayOf("com.kicinger.spring.security.boot.app.steps"),
        format = arrayOf("pretty")
)
class BehavioralTests {

}
```

Description:
* **features** - directories where *.feature files with scenarios are placed
* **glue** - packages where classes that defines steps are placed
* **format** - pretty logging format

## Scenario

Create the scenario file **src/test/resources/feature/admin.feature**

```gherkin
Feature: User tries to access /admin page

  Scenario: Authenticated user with admin privileges wants to access /admin page
    Given user has username 'admin' and role 'ADMIN'
    When user wants to access '/admin' page
    And user authenticates with its credentials
    Then admin page should be displayed
```

## Steps Definitions

### Create class representing Cucumber's Steps

```kotlin
@ContextConfiguration(classes = arrayOf(SpringSecurityBootApplication::class))
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class AdminControllerSteps(
    var requestBuilder: MockHttpServletRequestBuilder? = null,
    var user: UserRequestPostProcessor? = null
) {

    @Autowired
    var mockMvc: MockMvc? = null

}
```

Since Cucumber runner works as a wrapper to JUnit all classes that implements steps definitions must declare 
no argument constructor. With regard to this requirement we need to use nullable type references with default
values set to null. Arguments passed to constructor:
* **requestBuilder** - builder for the request that will be performed during scenario
* **user** - user's representation that provides its credentials and roles

**MockMvc** is a conducive helper class for performing test requests and asserting the response.
It's implementation is available within TestContext but cannot be autowired in the constructor.

### Create Steps definition

```kotlin
@Given("^user has username '(.*)' and role '(.*)'$")
fun givenUser(username: String, role: String) {
    user = user(username).roles(role)
}

@When("^user wants to access '(.*)' page$")
fun whenUserWantsToAccess(page: String) {
    requestBuilder = get(page)
}

@And("^user authenticates with its credentials$")
fun andUserAuthenticatesWithItsCredentials() {
    requestBuilder!!.with(user!!)
}

@Then("^admin page should be displayed$")
fun thenAdminPageShouldBeDisplayed() {
    mockMvc!!.perform(requestBuilder!!)
            .andExpect(status().is2xxSuccessful)
}
```

### Define request that should be performed

Method are statically imported from **MockMvcRequestBuilders**:

```kotlin
requestBuilder = get(page)
```

### Create user that performs the request

Method are statically imported from **SecurityMockMvcRequestPostProcessors**:

```kotlin
user = user(username).roles(role)
```
### Authenticate request with user

```kotlin
requestBuilder!!.with(user!!)
```