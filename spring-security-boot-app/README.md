# Testing Spring (Boot) Application secured with Spring Security

Test support is provided by two modules:

* spring-boot-test
* spring-boot-test-autoconfigure

For most developers the easiest is to use **spring-boot-starter-test**(includes jUnit, AssertJ, Hamcrest, Mockito and others).

Some others:
   
* SpringTest - utilities and integration test support for Spring Boot applications
* JSONassert - asn assertion library for JSON
* JsonPath - Xpath for JSON

# Testing Spring Application (Introduction)

## Unit Testing

POJO's that make up application can be tested without Spring or any other container. For those testing scenarios mock objects should be created to test the code in the isolation. All dependencies that are required by application components can be mocked or stubbed.

### Spring's Mock Objects

* **MockEnvironment**, **MockPropertySource** - mock implementations of Environment and PropertySource, useful for developing out-of-container tests for code that depends on environment-specific properties

### Unit Testing support Classes

**ReflectionTestUtils** - reflection-based utility methods, useful when scenarios needs to:

* change the value of a constant
* set a non-public field
* invoke non-public setter method
* incoke a non-public configuration/lifecycle callback method when testing

**AopTestUtils** - collection of AOP-related utility methods

**ModelAndViewAssert** - used in combination with jUnit and TestNG for dealing with ModelAndView objecs

## Integration Testing

## Goals of Spring's Integration Testing

1. Manage Spring IoC container caching between test executions
2. Provide DI of test fixture instances
3. Provider transaction management appropriate to integration testing
    * By default each transactional test is rolled back
    * If test should modify the database it should be annotated with ``@Commit``
4. Supply Spring-specific base classes that assist developers in writing integration tests

## Support classes for Integration Testing

**ApplicationContext** - performing explicit bean lookups or testing the state of the context as a whole

**JdbcTemplate** - executing SQL statements to query database

**JdbcTestUtils** - collection of JDBC related utility functions intended to simplify standard database testing scenarios
    
* countRowsInTable(..)
* countRowsInTableWhere(..)
* deleteFromTables(..)
* deleteFromTablesWhere(..)
* dropTables(..)

## Annotations

### Spring Testing Annotations

1. **@BootstrapWith**
2. **@ContextConfiguration** - class-level, determines how to load and configure ``Application Context``

# Testing Spring Boot Application
 
**@SpringBootTest** -  can 

## References

* https://docs.spring.io/spring/docs/4.3.11.RELEASE/spring-framework-reference/html/testing.html
* https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html