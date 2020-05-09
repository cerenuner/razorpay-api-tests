package steps.orders;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import utils.Authenticator;
import utils.Endpoints;
import utils.JsonObjectCreator;
import utils.enums.Currency;

import java.util.Random;

public class CreateOrder {

    private RequestSpecification request;
    private ValidatableResponse response;
    private JSONObject orderInfo;

    @Before
    public void beforeTests() {
        Endpoints.setOrdersEndpoint();
        request = Authenticator.basicLogin(RestAssured.with());
    }

    //First scenario
    @Given("I create order data with all fields as expected")
    public void createOrderDataForProperRequest() {
        int orderPrefix = new Random().nextInt(1000000);
        orderInfo = JsonObjectCreator.getOrderJsonObject(orderPrefix, Currency.IndianRupee, String.valueOf(orderPrefix), true, "noteAutomation");
    }

    @When("I used POST request to create order")
    public void postOrderInfoForProperRequest() {
        response = request.body(orderInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 200 from create order endpoint")
    public void inspectStatusCodeForProperRequest() {
        response.statusCode(HttpStatus.SC_OK);
    }

    @Then("I get a response body with created order's information")
    public void inspectCustomerResponseForProperRequest() {
        response.body("entity", IsEqual.equalTo("order"));
        response.body("amount", IsEqual.equalTo(orderInfo.get("amount")));
        response.body("currency", IsEqual.equalTo(orderInfo.get("currency")));
        response.body("receipt", IsEqual.equalTo(orderInfo.get("receipt")));
        response.body("notes", IsEqual.equalTo(orderInfo.get("notes")));
        response.body("amount_paid", IsEqual.equalTo(0));
        response.body("offer_id", IsEqual.equalTo(null));
        response.body("status", IsEqual.equalTo("created"));
        response.body("attempts", IsEqual.equalTo(0));
        response.body("$", Matchers.hasKey("id"));
        response.body("$", Matchers.hasKey("created_at"));
    }

    //Second scenario
    @Given("I create order data without amount information")
    public void createOrderDataForMissingAmount() {
        int orderPrefix = new Random().nextInt(1000000);
        orderInfo = JsonObjectCreator.getOrderJsonObject(orderPrefix, Currency.CzechKoruna, String.valueOf(orderPrefix), true, "noteAutomation");
        orderInfo.remove("amount");
    }

    @When("I used POST request to create order without amount information")
    public void postOrderInfoForMissingAmount() {
        response = request.body(orderInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 400 for amount information")
    public void inspectStatusCodeForMissingAmount() {
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Then("I got wrong amount message as {string}")
    public void inspectErrorMessageForWrongAmount(String errorMessage) {
        response.body("error.description", IsEqual.equalTo(errorMessage));
    }

    //Third scenario
    @Given("I create order data without currency information")
    public void createOrderDataForMissingCurrency() {
        int orderPrefix = new Random().nextInt(1000000);
        orderInfo = JsonObjectCreator.getOrderJsonObject(orderPrefix, Currency.UnitedStatesDollar, String.valueOf(orderPrefix), true, "noteAutomation");
        orderInfo.remove("currency");
    }

    @When("I used POST request to create order without currency information")
    public void postOrderInfoForMissingCurrency() {
        response = request.body(orderInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 400 for currency information")
    public void inspectStatusCodeForMissingCurrency() {
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Then("I got wrong currency message as {string}")
    public void inspectErrorMessageForWrongCurrency(String errorMessage) {
        response.body("error.description", IsEqual.equalTo(errorMessage));
    }

    //Fourth scenario
    @Given("I create order data with invalid currency")
    public void createOrderDataForInvalidCurrency() {
        int orderPrefix = new Random().nextInt(1000000);
        orderInfo = JsonObjectCreator.getOrderJsonObject(orderPrefix, "xxx", String.valueOf(orderPrefix), true, "noteAutomation");
    }

    @When("I used POST request to create order with invalid currency")
    public void postOrderInfoForInvalidCurrency() {
        response = request.body(orderInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 400 for invalid information")
    public void inspectStatusCodeForInvalidCurrency() {
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Then("I got wrong invalid currency message as {string}")
    public void inspectErrorMessageForInvalidCurrency(String errorMessage) {
        response.body("error.description", IsEqual.equalTo(errorMessage));
    }

    //Fifth scenario
    @Given("I create order data with zero amount")
    public void createOrderDataForZeroAmount() {
        int orderPrefix = new Random().nextInt(1000000);
        orderInfo = JsonObjectCreator.getOrderJsonObject(0, Currency.IndianRupee, String.valueOf(orderPrefix), true, "noteAutomation");
    }

    @When("I used POST request to create order with zero amount")
    public void postOrderInfoForZeroAmount() {
        response = request.body(orderInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 400 for zero amount")
    public void inspectStatusCodeForZeroAmount() {
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Then("I got wrong zero amount message as {string}")
    public void inspectErrorMessageForZeroAmount(String errorMessage) {
        response.body("error.description", IsEqual.equalTo(errorMessage));
    }

    //Fifth scenario
    @Given("I create order data with amount of 1")
    public void createOrderDataForAmountOfOne() {
        int orderPrefix = new Random().nextInt(1000000);
        orderInfo = JsonObjectCreator.getOrderJsonObject(1, Currency.UnitedStatesDollar, String.valueOf(orderPrefix), true, "noteAutomation");
    }

    @When("I used POST request to create order with amount of 1")
    public void postOrderInfoForAmountOfOne() {
        response = request.body(orderInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 200 for order with amount of 1")
    public void inspectStatusCodeForAmountOfOne() {
        response.statusCode(HttpStatus.SC_OK);
    }

}
