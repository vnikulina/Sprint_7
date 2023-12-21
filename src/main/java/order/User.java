package order;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;

import static constants.UrlAddresses.MAIN_URL;

public class User {
    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(MAIN_URL)
                .build();
    }
}