package ru.lesnikovaYana.tests;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import ru.lesnikovaYana.ResourcePath;

import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.lesnikovaYana.Endpoints.*;
import static ru.lesnikovaYana.tests.ImgurDeleteTest.*;
import static ru.lesnikovaYana.tests.ImgurNegativeUploadTest.*;

public class ImgurGetTest extends BaseTest{
    private String imageDeleteHash;

    @Test
    void getImagePositiveTest() {

        imageDeleteHash = given()
                .when()
                .get(GET_IMAGE, HelperJson.getImageJson().getString("data.id"))
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
        tearDown();
    }

    @Test
    void getImageInvalidImageHashNegativeTest() {
        given()
                .expect()
                .spec(HelperSpec.Error400())
                .when()
                .get(GET_IMAGE, "&$@45RJ!k")
                .prettyPeek();
    }

    @Test
    void getImageEmptyNegativeTest() {
        String jsonPath = HelperJson.getImageJson().getString("data.id");

        imageDeleteHash = given()
                .when()
                .get(GET_IMAGE, jsonPath)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
        tearDown();

        given()
                .expect()
                .spec(HelperSpec.Error404())
                .when()
                .get(GET_IMAGE, jsonPath)
                .prettyPeek();
    }

    private void tearDown() {
        given()
                .when()
                .delete(DELETE_UN_AUTHED, imageDeleteHash);
    }
}
