# Spring Security - Architecture and Implementation

## Technical Overview

### Core Components

**SecurityContextHolder**

- stores details of the present security context
- contains details od the Principal
- uses ThreadLocal to store the details (this can be changed by using **SecurityContextHolder.MODE_GLOBAL** strategy)

**Obtaining information about current user**

Spring Security uses ``Authentication`` object to represent principal currently iterating with the application

```java
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

if (principal instanceof UserDetails) {
String username = ((UserDetails)principal).getUsername();
} else {
String username = principal.toString();
}
```

**UserDetails**

- core interface
- represents a principal, but in an extensible and application-specific way
- adapter between user database and SecurityContext
- you can cast it to application user entity

**UserDetailsService***

```java
UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
```

- for successful authentication ``UserDetails`` is used to build the ``Authentication`` object stored in SecurityContextHolder
- provided UserDetailsService implementations: ``InMemoryDaoImpl``, ``JdbcDaoImpl``
- service **does not** authenticates user, this is done by ``AuthenticationManager``

**GrantedAuthority**

- ``Authentication`` provides ``getAuthorities(): List<GrantedAuthority>`` method
- authority that is granted to the Principal
- GrantedAuthority objects are usually loaded by the ``UserDetailsService``
- application-wide permissions

**Summary**

- SecurityContextHolder, to provide access to the SecurityContext.
- SecurityContext, to hold the Authentication and possibly request-specific security information.
- Authentication, to represent the principal in a Spring Security-specific manner.
- GrantedAuthority, to reflect the application-wide permissions granted to a principal.
- UserDetails, to provide the necessary information to build an Authentication object from your application’s DAOs or other source of security data.
- UserDetailsService, to create a UserDetails when passed in a String-based username (or certificate ID or the like).

### Authentication

**Username and Password scenario***

1. Username and password are obtained and combined into an instance of

**Authentication in a Web Application**

1. Request for a protected resources goes to the server
2. If the user is not authenticated then the server sends back a response indicating that user must authenticate:
    - HTTP response code (or)
    - redirect to a particular we page
3. Depending on authentication mechanism:
    - browser will redirect to the specific web page (or)
    - browser will somehow retrieve the identity (cookie, BASIC authentication, X.509)
4. Browser sends back a response to the server:
    - HTTP POST containing form contents (or)
    - HTTP Header containing authentication details
5. Server decides whether credentials are correct
6. Original request will be retired.
    - if user has sufficient authorities then request will be successful
    - otherwise HTTP status code 403 will be sent

**Main participants (order preserved)**

**ExceptionTranslationFilter**

- responsible for detecting any exceptions thrown by Spring Security
- returns 403 error code (principal is authenticated but lacks sufficient access)
- (or) launches an ``AuthenticationEntryPoint`` if principal has not been authenticated

**AuthenticationEntryPoint**

- reponsible for 2 step

**Authentication Mechanism**

- special name for the function of collecting authentication details from a user agent
- form-based login or Basic authentication
- once it is finished ``Authentication`` "request" object is presented to the ``AuthenticationManager``
- if valid then ``Authentication`` object is put to the ``SecurityContextHolder``

**Storing SecurityContext between requests**

- ``SecurityContextPersistenceFilter`` - responsible for storing the SecurityContext between requests
- sessionId
- stores HttpSession in SecurityContextHolder

### Access-Control (Authorization) in Spring Security

Main interface ``AccessDecisionManager``, has a ``decide(Authentication)``

**Secure Objects and AbstractSecurityInterceptor**

- **Secure Object** - any object that can have security (web request, method invocation etc.)
- each SO has is own ``AbstractSecurityInterceptor``

## Core Services

**AuthenticationManager**

**ProviderManager**

- default implementation of AuthenticationManager
- delegates authentication to list of AuthenticationProvider(s)
- if all providers returns null then **ProviderNotFoundException** is throws
- clears sensitive credentials information (important when working with caches - there is possibility to disable it using **eraseCredentialsAfterAuthorization**)


**AuthenticationProvider**

- performs authentication
- returns a fully qualified ``Authentication`` object or throws an exception

**PasswordEncoder**

- provides encoding and decoding of passwords