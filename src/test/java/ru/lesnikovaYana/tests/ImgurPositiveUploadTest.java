package ru.lesnikovaYana.tests;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.urlEncodingEnabled;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class ImgurPositiveUploadTest extends BaseTest{
    private final String URL_TEST = "https://images.kinorium.com/movie/shot/37317/w1500_127038.jpg";
    protected String imageDeleteHash;

    @Test
    void uploadCorrectFileSizeTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_JPG_500kb.jpg"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadGifExtensionFileTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_gif.gif"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadPngExtensionFileTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_png.png"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadBmpExtensionFileTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_bmp.bmp"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadMp4ExtensionFileTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("video", new File("src/test/resources/file_mp4.mp4"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadAviExtensionFileTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("video", new File("src/test/resources/file_AVI_480_750kB.avi"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadMovExtensionFileTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("video", new File("src/test/resources/file_MOV_480_700kB.mov"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadWebmExtensionFileTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("video", new File("src/test/resources/file_WEBM_480_900KB.webm"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadImageHDFileTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_HD.jpg"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadImage1x1pxFileTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_1x1px.jpg"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadImageUrlTest() {
        imageDeleteHash = given()
                .headers("Authorization", token)
                .formParam("type", "url")
                .multiPart("image", URL_TEST)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
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
                .headers("Authorization", token)
                .formParam("type", "base64")
                .multiPart("image", encodedFile)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.id", is(notNullValue()))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @AfterEach
    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", username, imageDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    private byte[] getFileContent() {
        byte[] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File("src/test/resources/file_JPG_500kb.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }
}
