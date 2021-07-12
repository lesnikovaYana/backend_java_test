package ru.lesnikovaYana.tests;

import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ImgurNegativeUploadTest extends BaseTest{

    @Test
    void uploadSmallFileSizeTest() {
        given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_1bite.jpeg"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.error", equalTo("We don't support that file type!"))
                .statusCode(400);
    }

    @Test
    void uploadBigFileSizeTest() {
        given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_10mb.jpeg"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.error", equalTo("We don't support that file type!"))
                .statusCode(400);
    }

    @Test
    void uploadTiffExtensionFileTest() {
        given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_TIFF_1MB.tiff"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.error", equalTo("Internal expectation failed"))
                .statusCode(417);
    }

    @Test
    void uploadOggExtensionFileTest() {
        given()
                .headers("Authorization", token)
                .multiPart("video", new File("src/test/resources/file_OGG_480_1_7mg.ogg"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.error", equalTo("We don't support that file type!"))
                .statusCode(400);
    }

    @Test
    void uploadExtensionChangedFileTest() {
        given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_changed_exe.jpg"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.error", equalTo("Internal expectation failed"))
                .statusCode(417);
    }

    @Test
    void uploadNotImageOrVideoFileTest() {
        given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/file_docx.docx"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.error", equalTo("Internal expectation failed"))
                .statusCode(417);
    }

    @Test
    void uploadEmptyFileTest() {
        given()
                .headers("Authorization", token)
                .multiPart("image", "/path/to/file")
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .body("data.error", equalTo("Bad Request"))
                .statusCode(400);
    }
}
