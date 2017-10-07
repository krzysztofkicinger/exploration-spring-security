Feature: User tries to access /admin page

  Scenario: Authenticated user with admin privileges wants to access /admin page
    Given user has username 'admin' and role 'ADMIN'
    When user wants to access '/admin' page
    And user authenticates with its credentials
    Then admin page should be displayed

  Scenario: Non-authenticated user wants to access /admin page
    When user wants to access '/admin' page
    Then the request should be redirected to '/login'

  Scenario: Authenticated user who does not have ADMIN privileges wants to access /admin page
    Given user has username 'user' and role 'USER'
    When user wants to access '/admin' page
    And user authenticates with its credentials
    Then request should be rejected