package utils;

import io.restassured.RestAssured;

public class Endpoints {

    private static PropertyReader propertyReader = new PropertyReader();

    public static void setCustomersEndpoint() {
        RestAssured.baseURI = propertyReader.readProperty("baseURI") + "/customers";
    }

    public static void setOrdersEndpoint() {
        RestAssured.baseURI = propertyReader.readProperty("baseURI") + "/orders";
    }
}
