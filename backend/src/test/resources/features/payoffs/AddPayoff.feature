Feature: Add a Payoff

  Background:
    Given an owner named "John" with "john@doe.com" as mail address and "abc" as password
    And "John" own the store "John's Store"
    Given an owner named "Robert" with "robert@test.fr" as mail address and "abc" as password
    And "Robert" own the store "Robert's Store"

  Scenario: Add a Payoff
    When a payoff named "Test" with a cost of 13.25, a point cost of 10 with a vfp status is added to "John's Store"
    Then "John's Store" should have a payoff named "Test" with a cost of 13.25 and a point cost of 10 and a vfp status
    And add a payoff named "Test" to "John's Store" should raised an AlreadyExistingPayoffException