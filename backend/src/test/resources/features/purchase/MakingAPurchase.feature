Feature: Gaining Points

  This feature supports the way that points are gained by the customer

  Background:
    Given a customer named "John Doe" with "john@doe.com" as mail address and "123456789" as password


  Scenario: Gaining points
    Given "John Doe" has 0 points
    When "John Doe" makes a purchase of 100 euros
    Then "John Doe" should have 100 points