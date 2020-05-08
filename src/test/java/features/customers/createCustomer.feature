Feature: Razorpay creates customers with basic details

  @customerCreation
  Scenario: A customer should be created with all data
    Given I create customer data with all fields as expected
    When I used POST request to create customer
    Then I get a response code of 200 from create customer endpoint
    And I get a response body with created customer's information

  @customerCreation
  Scenario: A customer should not be created without name information
    Given I create customer data without name information
    When I used POST request to create customer without name information
    Then I get a response code of 400 for name information

  @customerCreation
  Scenario: A customer should not be created without contact information
    Given I create customer data without contact information
    When I used POST request to create customer without contact information
    Then I get a response code of 400 for contact information

  @customerCreation
  Scenario: A customer should not be created with invalid email address
    Given I create customer data with wrong email address
    When I used POST request to create customer with wrong email address
    Then I get a response code of 400 for wrong email address
    And I got wrong email message as "The email must be a valid email address."

  @customerCreation
  Scenario: A customer should not be created with invalid contact information
    Given I create customer data with wrong contact information
    When I used POST request to create customer with wrong contact information
    Then I get a response code of 400 for wrong contact information
    And I got wrong contact message as "Contact number contains invalid characters, only digits and + symbol are allowed"