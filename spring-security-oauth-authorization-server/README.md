# Spring Security Oauth - Authentication Server

## Introduction

### Endpoints

Requests for the tokens are handled by **Spring MVC controller endpoints** and access to protected resources is handled by standard **Spring Security filters**.

**/oauth/authorize - Authorization Endpoint**

- used to service requests for authentication
- where the Resource Owner logs in and grants authorization to the client (web app running in the browser)
- typical scenario: Resource Owner's user agent is redirected to the identity server
- Resource Owner's User Agent has direct access to the access token

**/oauth/token - TokenEndpoint**

- used to service requests for access tokens
- where the Client (Server Side API) calls to exchange the Authorization Code, ClientId and ClientSecret for an access token
- User Agent is provided only the Authorization Code, no direct access to the access token


# IMPORTANT NOTES

## Password flow

**How request should look like?**

POST /oauth/token

Authentication Basic btoa("<client_id>:<client_secret>")
Content-Type application/x-www-form-urlencoded

grant_type:password
username:john
password:123
client_id:passwordClient

**Important implementation details**

1. When SpringSecurity performs /oauth/token then it uses AuthorizationManager
2. WebSecurityConfiguration can be implemented in at least two different ways:
    - **extends GlobalAuthenticationConfigurerAdapter** and overrides **init(AuthenticationManagerBuilder)** 
    - **extends WebSecurityConfigurerAdapter**
        - this time we create security web configuration
        - this configuration is not used by default
        - we need to inject our AuthenticationManager to the **Oauth2AuthorizationServerConfiguration** using @Qualifier
        - and then configure endpoint to use it **authenticationManager(authenticationManager)**
3. 


## Authorization Code Flow

- https://stackoverflow.com/questions/33377971/oauth2-spring-security-authorization-code

## References

- https://stackoverflow.com/questions/36944986/how-do-oauth-authorize-and-oauth-token-interact-in-spring-oauth
- http://projects.spring.io/spring-security-oauth/docs/oauth2.html
- https://github.com/making/oauth2-sso-demo