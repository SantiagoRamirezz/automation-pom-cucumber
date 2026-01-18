Feature: Employee Management
  As an HR user
  I want to be able to add and verify employees
  So that I can manage employee records efficiently

  @EMP001 @web
  Scenario: Add and verify a new employee
    Given the user is logged in
    When the user navigates to the PIM module
    And the user adds a new employee with login details
    Then the new employee should appear in the employee list
