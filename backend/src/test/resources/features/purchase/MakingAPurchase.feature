Feature: Gaining Points

  This feature supports the way that points are gained by the customer

  Background:
    Given a customer named "John Doe" with "john@doe.com" as mail address and "123456789" as password
    Given a store owner named "Jane Doe" with "jane@doe.com" as mail address and "verySecurePassword" as password
    Given a store named "Jane's place", owned by "jane@doe.com"

  Scenario: Gaining points by making a purchase
    Given "John Doe" has 0 points
    When "John Doe" makes a purchase of 100 euros at the store "Jane's place"
    Then "John Doe" should have 100 points
    And a purchase is registered for "John Doe" at "Jane's place" with a value of 100.0 euros

  Scenario: Gaining points by making a purchase with the balance of the fidelity card
    Given "John Doe" has 0 points
    And "John Doe" has a fidelity card with a balance of 102.24 euros
    When "John Doe" makes a purchase of 100.5 euros with the fidelity card at the store "Jane's place"
    Then "John Doe" should have 100 points
    And a purchase is registered for "John Doe" at "Jane's place" with a value of 100.5 euros

  Scenario: Trying to buy something with more points than the customer has
    Given "John Doe" has 0 points
    And "John Doe" has a fidelity card with a balance of 80.24 euros
    When "John Doe" makes a purchase of 81 euros with the fidelity card at the store "Jane's place"
    Then "InsufficientBalanceException" is raised

  Scenario: Update VFP
    Given "John Doe" has 0 points
    And "John Doe" already bought 4 times at the store "Jane's place" in the last week
    When "John Doe" makes a purchase of 20 euros at the store "Jane's place"
    Then "John Doe" vfp status should be updated

  Scenario: No Update VFP
    Given "John Doe" has 0 points
    And "John Doe" already bought 3 times at the store "Jane's place" in the last week
    When "John Doe" makes a purchase of 20 euros at the store "Jane's place"
    Then "John Doe" vfp status should not be updated