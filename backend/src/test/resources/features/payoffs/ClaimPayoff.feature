Feature: Claim Payoff

  This feature supports the way that points are used to claim a payoff.

  Background:
    Given a customer named "John Doe" with "john@doe.com" as mail address and "123456789" as password
    Given a store owner named "Jane Doe" with "jane@doe.com" as mail address and "verySecurePassword" as password
    Given a store named "Jane's place", owned by "jane@doe.com"
    Given "John Doe" makes a purchase of 10 euros at the store "Jane's place"
    Given a store owner named "Robert" with "a@doe.com" as mail address and "pwd" as password
    Given a store named "Robert's place", owned by "a@doe.com"
    Given a payOff named "Cookie", which costs 5 euros, which can be exchanged for 3 points and is available at "Jane's place"
    Given a payOff named "Flowers", which costs 8 euros, which can be exchanged for 6 points and is available at "Robert's place"

  Scenario: Claim a payoff
    Given "John Doe" has 10 points
    When "John Doe" use points to buy "Cookie" at "Jane's place"
    Then "John Doe" has 7 points
    And a payoff is registered for "John Doe" with "Cookie" as payoff and "Jane's place" as store

  Scenario: Trying to buy a non existing payoff
    Given "John Doe" has 10 points
    When "John Doe" use points to buy "not" at "Jane's place"
    Then "PayoffNotFoundException" is raised


  Scenario: Buying a payoff with not enough points
    Given "John Doe" has 2 points
    When "John Doe" use points to buy "Cookie" at "Jane's place"
    Then "InsufficientBalanceException" is raised

  Scenario: Buying a payoff in a store where it is not available
    Given "John Doe" has 10 points
    When "John Doe" use points to buy "Flowers" at "Robert's place"
    Then "NoPreviousPurchaseException" is raised

