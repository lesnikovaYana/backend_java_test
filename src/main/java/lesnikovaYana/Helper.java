package ru.lesnikovaYana;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static ru.lesnikovaYana.Endpoints.DELETE_UN_AUTHED;
import static ru.lesnikovaYana.Endpoints.UPLOAD_FILE;

public class Helper {
    public static JsonPath getImageJson() {
        JsonPath path = given()
                .multiPart("image", new File(ResourcePath.FILE_JPG.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .then()
                .extract()
                .response()
                .jsonPath();
        return path;
    }

    public static ResponseSpecification error400() {
        ResponseSpecification spec400 = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectBody("success", equalTo(false))
                .expectStatusCode(400)
                .build();
        return spec400;
    }

    public static ResponseSpecification error417() {
        ResponseSpecification spec417 = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectBody("success", equalTo(false))
                .expectStatusCode(417)
                .build();
        return spec417;
    }

    public static ResponseSpecification error404() {
        ResponseSpecification spec417 = new ResponseSpecBuilder()
                .expectContentType(ContentType.HTML)
                .expectStatusCode(404)
                .build();
        return spec417;
    }
}
