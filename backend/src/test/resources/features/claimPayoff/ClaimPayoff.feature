Feature: Claim Payoff

  This feature supports the way that points are used to claim a payoff.

  Background:
    Given a customer named "John Doe" with "john@doe.com" as mail address and "123456789" as password
    Given a store owner named "Jane Doe" with "jane@doe.com" as mail address and "verySecurePassword" as password
    Given a store named "Jane's place", owned by "jane@doe.com", with opening hours from 8:0 to 20:0
    Given a payOff named "Cookie", which costs 5 euros, which can be exchanged for 3 points and is available at "Jane's place"

  Scenario: Gaining points by making a purchase
    Given "John Doe" has 10 points
    When "John Doe" use points to buy "Cookie" at "Jane's place"
    Then "John Doe" has 7 points


