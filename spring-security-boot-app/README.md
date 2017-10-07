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
2. **@ContextConfiguration** - class-level, determines how to load and configure ``Application Context`` for integration test (context XML ``locations`` or @Configuration ``classes``)
3. **@WebAppConfiguration** - class-level, declares that context should be a ``WebApplicationContext``, must be used on conjunction with ``@ContextConfiguration``
4. **@ContextHierarchy** - class-level, list of one or more ``@ConfigurationContext``\`s 
5. **@ActiveProfiles** - class-level, which profiles should be active when loading an ApplicationContext, supports inheriting (any profile from the @ActiveProfile from the super class will be will be included)
6. **@TestPropertySources** - class-level, configures locations of property files and inlined properties to be added to the set ot ``PropertySources`` in the ``Envirinment`` for ApplicationContext, have higher precedence than environment variables or Java system properties
7. **@DirtiesContext** - includes that ApplicationContext can be dirtied during the execution of the test, should be closed and rebuilt
    * class-level and method-level
    * classMode - ApplicationContext will be marked dirty before or after the entire class' tests
        * BEFORE_CLASS
        * AFTER_CLASS (default)
        * BEFORE_EACH_TEST_METHOD
        * AFTER_EACH_TEST_METHOD
    * methodMode - single test specific mode
        * BEFORE_METHOD
        * AFTER_METHOD
    * hierarchicalMode - ...
8. **@TestExecutionListeners** - class-level, registers ``TestExecutionListener`` implementations
9. **@Commit(=@Rollback(false))/@Rollback** - transaction for the transaction test should be committed/rolled back
10. **@BeforeTransaction/@AfterTransaction** - annotated void method should be executed before or after transation is started fo test methods
11. **@Sql** - class/method level to configure SQL scripts to be executed against a given database during integration tests
* config - **@SqlConfig** - metadata how to pars and execute SQL scripts
12. **@SqlGroup** - container for several @SQL annotations

### Standard Spring Annotations that can be used for testing

* **@Autowired**
* **@Qualifier**
* **@Resource**
* **@ManagedBean**
* **@Inject**
* **@Named**
* **@PersistenceContext**
* **@PersistenceUnit**
* **@Required**
* **@Transaction**

### Spring jUnit 4 testing annotations

* **@IfProfileValue** - class/method level, annotated test is enabled for specific testing environment, if the configured ``ProfileValueSource`` returns a matching **value[s]** for the provided **name**, then the test is enabled
* **@ProfileValueSourceConfiguration**
* **@Timed** - test must finish execution in specific time period, if not than fails
* **@Repeat** - annotated test method must be executed repeatedly

## Spring TestContext Framework

// TODO: Some topics to be done in the future (not required for usage)

### Context Management



# Testing Spring Boot Application
 
**@SpringBootTest** - alternative to standard ``@ContextConfiguration``, works by creating the ``ApplicationContext`` used in the tests via ``SpringApplication`` (required for Boot)
* **webEnvironment** - refines how tests will run
    * MOCK - loads WebApplicationContext and provides mock servlet environment, can be used in conjunction with ```@AutoConfigureMockMvc``` for ``MockMvc``-based testing
    * RANDOM_PORT - loads ``EmbeddedWebApplicationContext`` and provides a real servlet environment listening or random port
    * DEFINED_PORT - loads ``EmbeddedWebApplicationContext`` and provides a real servlet environment listening or defined port (application.properties)
    * NONE - loads ApplicationContext without any Servlet environment
    


## References

* https://docs.spring.io/spring/docs/4.3.11.RELEASE/spring-framework-reference/html/testing.html
* https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html