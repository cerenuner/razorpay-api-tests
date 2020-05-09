package steps.customers;

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

import java.util.Random;

public class CreateCustomer {
    private RequestSpecification request;
    private ValidatableResponse response;
    private JSONObject customerInfo;

    @Before
    public void beforeTests() {
        Endpoints.setCustomersEndpoint();
        request = Authenticator.basicLogin(RestAssured.with());
    }

    //First scenario
    @Given("I create customer data with all fields as expected")
    public void createCustomerDataForProperRequest() {
        int customerPrefix = new Random().nextInt(1000000);
        customerInfo = JsonObjectCreator.getCustomerJsonObject("customer" + customerPrefix, customerPrefix + "@test.com", "+49" + customerPrefix, "0", "test note");
    }

    @When("I used POST request to create customer")
    public void postCustomerInfoForProperRequest() {
        response = request.body(customerInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 200 from create customer endpoint")
    public void inspectStatusCodeForProperRequest() {
        response.statusCode(HttpStatus.SC_OK);
    }

    @Then("I get a response body with created customer's information")
    public void inspectCustomerResponseForProperRequest() {
        response.body("entity", IsEqual.equalTo("customer"));
        response.body("name", IsEqual.equalTo(customerInfo.get("name")));
        response.body("email", IsEqual.equalTo(customerInfo.get("email")));
        response.body("contact", IsEqual.equalTo(customerInfo.get("contact")));
        response.body("notes", IsEqual.equalTo(customerInfo.get("notes")));
        response.body("gstin", IsEqual.equalTo(null));
        response.body("$", Matchers.hasKey("id"));
        response.body("$", Matchers.hasKey("created_at"));
    }

    //Second scenario
    @Given("I create customer data without name information")
    public void createCustomerDataForMissingName() {
        int customerPrefix = new Random().nextInt(1000000);
        customerInfo = JsonObjectCreator.getCustomerJsonObject("customer" + customerPrefix, customerPrefix + "@test.com", "+49" + customerPrefix, "0", "test note");
        customerInfo.remove("name");
    }

    @When("I used POST request to create customer without name information")
    public void postCustomerInfoForMissingName() {
        response = request.body(customerInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 400 for name information")
    public void inspectStatusCodeForMissingName() {
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    //Third scenario
    @Given("I create customer data without contact information")
    public void createCustomerDataForMissingContact() {
        int customerPrefix = new Random().nextInt(1000000);
        customerInfo = JsonObjectCreator.getCustomerJsonObject("customer" + customerPrefix, customerPrefix + "@test.com", "+49" + customerPrefix, "0", "test note");
        customerInfo.remove("contact");
    }

    @When("I used POST request to create customer without contact information")
    public void postCustomerInfoForMissingContact() {
        response = request.body(customerInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 400 for contact information")
    public void inspectStatusCodeForMissingContact() {
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    //Fourth scenario
    @Given("I create customer data with wrong email address")
    public void createCustomerDataForWrongEmail() {
        int customerPrefix = new Random().nextInt(1000000);
        customerInfo = JsonObjectCreator.getCustomerJsonObject("customer" + customerPrefix, "wrongEmail", "+49" + customerPrefix, "0", "test note");
    }

    @When("I used POST request to create customer with wrong email address")
    public void postCustomerInfoWithWrongEmail() {
        response = request.body(customerInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 400 for wrong email address")
    public void inspectStatusCodeForWrongEmail() {
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Then("I got wrong email message as {string}")
    public void inspectErrorMessageForWrongEmail(String errorMessage) {
        response.body("error.description", IsEqual.equalTo(errorMessage));
    }

    //Fifth scenario
    @Given("I create customer data with wrong contact information")
    public void createCustomerDataForWrongContact() {
        int customerPrefix = new Random().nextInt(1000000);
        customerInfo = JsonObjectCreator.getCustomerJsonObject("customer" + customerPrefix, customerPrefix + "@test.com", "onlyString", "0", "test note");
    }

    @When("I used POST request to create customer with wrong contact information")
    public void postCustomerInfoWithWrongContact() {
        response = request.body(customerInfo.toJSONString()).post().then();
    }

    @Then("I get a response code of 400 for wrong contact information")
    public void inspectStatusCodeForWrongContact() {
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Then("I got wrong contact message as {string}")
    public void inspectErrorMessageForWrongContact(String errorMessage) {
        response.body("error.description", IsEqual.equalTo(errorMessage));
    }

}
