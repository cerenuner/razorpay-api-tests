package utils;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Authenticator {

    public static RequestSpecification basicLogin(RequestSpecification request) {
        PropertyReader propertyReader = new PropertyReader();
        String apiKeyId = propertyReader.readProperty("apiKeyId");
        String apiKey = propertyReader.readProperty("apiKey");
        request.given()
                .auth()
                .basic(apiKeyId, apiKey)
                .contentType(ContentType.JSON);
        return request;
    }
}
