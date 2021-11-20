package ru.lesnikovaYana.tests;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.lesnikovaYana.Endpoints;
import ru.lesnikovaYana.ResourcePath;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.urlEncodingEnabled;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static ru.lesnikovaYana.Endpoints.DELETE_UN_AUTHED;
import static ru.lesnikovaYana.Endpoints.UPLOAD_FILE;
import static ru.lesnikovaYana.ResourcePath.*;

public class ImgurPositiveUploadTest extends BaseTest{
    protected String imageDeleteHash;

    @Test
    void uploadCorrectFileSizeTest() {
        imageDeleteHash = given()
                .multiPart("image", new File(FILE_JPG.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadGifExtensionFileTest() {
        imageDeleteHash = given()
                .multiPart("image", new File(FILE_GIF.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadPngExtensionFileTest() {
        imageDeleteHash = given()
                .multiPart("image", new File(FILE_PNG.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadBmpExtensionFileTest() {
        imageDeleteHash = given()
                .multiPart("image", new File(FILE_BMP.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadMp4ExtensionFileTest() {
        imageDeleteHash = given()
                .multiPart("video", new File(FILE_MP4.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadAviExtensionFileTest() {
        imageDeleteHash = given()
                .multiPart("video", new File(FILE_AVI.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadMovExtensionFileTest() {
        imageDeleteHash = given()
                .multiPart("video", new File(FILE_MOV.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadWebmExtensionFileTest() {
        imageDeleteHash = given()
                .multiPart("video", new File(FILE_WEBM.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadImageHDFileTest() {
        imageDeleteHash = given()
                .multiPart("image", new File(FILE_HD.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadImage1x1pxFileTest() {
        imageDeleteHash = given()
                .multiPart("image", new File(FILE_1X1PX.getTitle()))
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadImageUrlTest() {
        imageDeleteHash = given()
                .formParam("type", "url")
                .multiPart("image", TEST_URL.getTitle())
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadImageBase64Test() {
        String encodedFile;
        byte[] byteArray = getFileContent();
        encodedFile = Base64.getEncoder().encodeToString(byteArray);

        imageDeleteHash = given()
                .formParam("type", "base64")
                .multiPart("image", encodedFile)
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @AfterEach
    void tearDown() {
        given()
                .when()
                .delete(DELETE_UN_AUTHED, imageDeleteHash);
    }

    private byte[] getFileContent() {
        byte[] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(FILE_JPG.getTitle()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }
}
