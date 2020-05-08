Feature: Razorpay creates Orders that can be linked to payments.

  @orderCreation
  Scenario: An order should be created with all data
    Given I create order data with all fields as expected
    When I used POST request to create order
    Then I get a response code of 200 from create order endpoint
    And I get a response body with created order's information

  @orderCreation
  Scenario: An order should not be created without amount information
    Given I create order data without amount information
    When I used POST request to create order without amount information
    Then I get a response code of 400 for amount information
    And I got wrong amount message as "The amount field is required."

  @orderCreation
  Scenario: An order should not be created without currency information
    Given I create order data without currency information
    When I used POST request to create order without currency information
    Then I get a response code of 400 for currency information
    And I got wrong currency message as "The currency field is required."

  @orderCreation
  Scenario: An order should not be created with invalid currency
    Given I create order data with invalid currency
    When I used POST request to create order with invalid currency
    Then I get a response code of 400 for invalid information
    And I got wrong currency message as "Currency is not supported"

  @orderCreation
  Scenario: An order should not be created with zero amount
    Given I create order data with zero amount
    When I used POST request to create order with zero amount
    Then I get a response code of 400 for zero amount
    And I got wrong zero amount message as "The amount must be at least INR 1.00"

  @orderCreation
  Scenario: An order should not be created with amount of 1
    Given I create order data with amount of 1
    When I used POST request to create order with amount of 1
    Then I get a response code of 200 for order with amount of 1