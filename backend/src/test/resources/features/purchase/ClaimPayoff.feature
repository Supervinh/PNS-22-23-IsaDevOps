Feature: Claim a Payoff

  Background:
    Given a customer named "John Doe" with "john@doe.com" as mail address and "123456789" as password
    Given a store owner named "Jane Doe" with "jane@doe.com" as mail address and "verySecurePassword" as password
    Given a store named "Jane's place", owned by "jane@doe.com", with opening hours from 8:0 to 20:0

  Scenario: Gaining points by making a purchase
    Given "John Doe" has 0 points
    When "John Doe" makes a purchase of 100 euros at the store "Jane's place"
    Then "John Doe" should have 100 points


  Scenario: Gaining points by making a purchase with the balance of the fidelity card
    Given "John Doe" has 0 points
    And "John Doe" has a fidelity card with a balance of 100 euros
    When "John Doe" makes a purchase of 100 euros with the fidelity card at the store "Jane's place"
    Then "John Doe" should have 100 points
