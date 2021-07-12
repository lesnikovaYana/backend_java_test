package ru.lesnikovaYana.tests;

import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ImgurGetTest extends BaseTest{
    protected String imageHash;
    protected String imageDeleteHash;

    @Test
    void getImagePositiveTest() {
        getImageHash();

        imageDeleteHash = given()
                .header("Authorization", token)
                .when()
                .get("https://api.imgur.com/3/image/{imageHash}", imageHash)
                .prettyPeek()
                .then()
                .body("success", equalTo(true))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void getImageEmptyNegativeTest() {
        given()
                .header("Authorization", token)
                .when()
                .get("https://api.imgur.com/3/image/null")
                .prettyPeek()
                .then()
                .body("success", equalTo(false))
                .statusCode(400);
    }

    private void getImageHash() {
         imageHash = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_JPG_500kb.jpg"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    private void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", username, imageDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
