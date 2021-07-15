package ru.lesnikovaYana.tests;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import ru.lesnikovaYana.ResourcePath;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static ru.lesnikovaYana.Endpoints.*;
import static ru.lesnikovaYana.tests.ImgurNegativeUploadTest.*;

public class ImgurDeleteTest extends BaseTest{

    @Test
    void deleteImageImageHashPositiveTest() {
        given()
                .when()
                .delete(DELETE_AUTHED, HelperJson.getImageJson().getString("data.id"))
                .prettyPeek();
    }

    @Test
    void deleteImageDeleteHashPositiveTest() {
        given()
                .when()
                .delete(DELETE_UN_AUTHED, HelperJson.getImageJson().getString("data.deletehash"))
                .prettyPeek();
    }

    @Test
    void deletingAlreadyDeletedObjectAuthedTest() {
        String jsonPath = HelperJson.getImageJson().getString("data.id");

        given()
                .when()
                .delete(DELETE_AUTHED, jsonPath)
                .prettyPeek();

        given()
                .when()
                .delete(DELETE_AUTHED, jsonPath)
                .prettyPeek();
    }

    @Test
    void deletingAlreadyDeletedObjectUnAuthedTest() {
        String jsonPath =  HelperJson.getImageJson().getString("data.deletehash");

        given()
                .when()
                .delete(DELETE_UN_AUTHED,jsonPath)
                .prettyPeek();

        given()
                .when()
                .delete(DELETE_UN_AUTHED,jsonPath)
                .prettyPeek();
    }

    @Test
    void deleteInvalidImageHashTest() {
        given()
                .expect()
                .spec(HelperSpec.Error400())
                .when()
                .delete(DELETE_AUTHED, "&$@45RJ!k")
                .prettyPeek();
    }

    @Test
    void deleteInvalidDeleteHashTest() {
        given()
                .expect()
                .spec(HelperSpec.Error400())
                .when()
                .delete(DELETE_UN_AUTHED, "&$@45RJ!k")
                .prettyPeek();
    }

    public static class HelperJson {
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
    }

}
