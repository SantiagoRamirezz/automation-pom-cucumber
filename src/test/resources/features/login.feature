Feature: Login

  Scenario: Successful login
    Given the user opens the login page
    When the user logs in with username "Admin" and password "admin123"
    Then the user should see the dashboard page