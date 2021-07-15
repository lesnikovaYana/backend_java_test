package ru.lesnikovaYana.tests;

import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ImgurDeleteTest extends BaseTest{
    protected String imageHash;
    protected String deleteHash;

    @Test
    void deleteImageImageHashPositiveTest() {
       getImageHash();

        given()
                .header("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/image/{imageHash}", imageHash)
                .prettyPeek()
                .then()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Test
    void deleteImageDeleteHashPositiveTest() {
       getDeleteHash();

        given()
                .header("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/image/{imageDeleteHash}", deleteHash)
                .prettyPeek()
                .then()
                .body("success", equalTo(true))
                .statusCode(200);
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

    private void getDeleteHash() {
        deleteHash = given()
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
                .getString("data.deletehash");
    }
}
