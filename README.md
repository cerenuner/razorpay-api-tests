# Razorpay API Tests

[Razorpay](https://razorpay.com/docs/api/) API tests scenarios include Customer and Order creation endpoint tests.

## Why Customer and Order Creation Endpoints?

Razorpay accept payments from customers and automate payouts to vendors & employees. In Razorpay API, `Customer`, `Order`, `Payment`, `Settlement`, `Refund`, `Invoice`, `Subscription`, `Payment Link`, `Smart Collect`, `Route` and `Items` process can be executed. However, `Payment` is the most important functionality and all payment functions requires `Customer` and `Order` information. Thats why, to be completely sure, `Customer` and `Order` functionalities should be tested at first. In these functionalities, creation endpoints are choosen for TASK-2.

### Test Scenario

Project has 11 scenarios that can be located within feature package:

Scenario                                                        | Explanation
-------------                                                   | -------------
A customer should be created with all data                      | A fully and correctly prepared customer information is sending to `/customers` endpoint with POST request and response code and response data is verified with sended values.
A customer should not be created without name information       | A customer information without `name` value is sending to `/customers` endpoint with POST request and response code and response error message is checked.
A customer should not be created without contact information    | A customer information without `contact` value is sending to `/customers` endpoint with POST request and response code and response error message is checked.
A customer should not be created with invalid email address   | A customer invalid `email adress` value is sending to `/customers` endpoint with POST request and response code and response error message is checked.
A customer should not be created with invalid contact information     | A customer invalid `contact` value is sending to `/customers` endpoint with POST request and response code and response error message is checked.
An order should be created with all data                     | A fully and correctly prepared order information is sending to `/orders` endpoint with POST request and response code and response data is verified with sended values.
An order should not be created without amount information       |An order information without `amount` value is sending to `/orders` endpoint with POST request and response code and response error message is checked.
An order should not be created without currency information       |An order information without `currency` value is sending to `/orders` endpoint with POST request and response code and response error message is checked.
An order should not be created with invalid currency | An order invalid `currency` value is sending to `/orders` endpoint with POST request and response code and response error message is checked.
An order should not be created with zero amount | An order value with zero amount is sending to `/orders` endpoint with POST request and response code and response error message is checked.
An order should not be created with amount of 1 | An order value with amount of 1 is sending to `/orders` endpoint with POST request and response code and response error message is checked.

> :warning: **For failing scenarios**: `A customer should not be created without name information`, `A customer should not be created without contact information`, `An order should not be created with zero amount` and `An order should not be created with negative amount` scenarios are failing because API works differently from its [API doc](https://razorpay.com/docs/api/).  

> :warning: **For 11. scenario**: In [API doc](https://razorpay.com/docs/api/) order creation amount should be bigger than 1. Thats why scenario should be included boundry value test which are <0 , ==1 and >1.

### Test Structure

Razorpay API test structure has Features, Steps and Util packages.

    .├── src
      ├── test
        ├── java
          ├── features          # .feature files
          ├── steps             # test step files
          ├── utils             # utility fuctions and enums
        ├── resources           # configuration values
    ├── target                  # test report file location
    ├── pom.xml                 # maven dependencies
    └── README.md

#### features and steps packages

In features package, Cucumber's .feature files are located.

    .
    ├── ...
        ├── features
            ├── customers                       # customer .feature files
                ├── createCustomer.feature      # customer creation steps
            ├── orders                          # order .feature files
                ├── createOrder.feature         # order creation steps
    └── ...

#### utils package and resource file

In utils package, utility classes are located. Utility classes are used to login, setting RestAssured's base uri, creating Json body for orders and customer operations.

In resources package, configuration values such as base URL and API keys are located.

    .
    ├── ...
        ├── utils
            ├── enums                       
                ├── Currency                # currency enum values
            ├── Authhenticator              # basic auth authenticator class
            ├── Endpoints                   # rest assured base url setter class
            ├── JsonObjectCreator           # json body creator class
            ├── PropertyReader              # property value reader class
    ├── resources
        ├── config.properties               # configuration values
    └── ...

### Used libraries and plugins

[net.minidev](https://mvnrepository.com/artifact/net.minidev/json-smart/2.3) is used for Json creation and edition process instead of creating MAP and HASHMAP object. This library ease Json operations such as adding, removing Json values and creating Json body etc.

[de.monochromata.cucumber](https://mvnrepository.com/artifact/de.monochromata.cucumber/reporting-plugin/4.0.48) is used for reporting test results. In Cucumber test report, visual experience is not that good and this plugin creates more pretty HTML test reports. 

### Running the tests

For this test project `maven` is used for dependencies and test execution.

For running all scenarios use
```bash
mvn test
```

For running customer creation scenarios use
```bash
mvn test -Dcucumber.options="--tags @customerCreation"
```

For running order creation scenarios use
```bash
mvn test -Dcucumber.options="--tags @orderCreation"
```

### Test reporting

[de.monochromata.cucumber](https://mvnrepository.com/artifact/de.monochromata.cucumber/reporting-plugin/4.0.48) is used for reporting test results. After test execution, in `target/test-results/cucumber-html-reports/` directory, `Features`, `Tags`, `Steps` and `Failures` based tests resutls are shown with html files.
