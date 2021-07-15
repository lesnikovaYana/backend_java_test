package ru.lesnikovaYana.tests;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static ru.lesnikovaYana.Endpoints.UPLOAD_FILE;
import static ru.lesnikovaYana.ResourcePath.*;

public class ImgurNegativeUploadTest extends BaseTest{

    @Test
    void uploadSmallFileSizeTest() {

        given()
                .multiPart("image", new File(FILE_1BITE.getTitle()))
                .expect()
                .spec(HelperSpec.Error400())
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek();
    }

    @Test
    void uploadBigFileSizeTest() {
        given()
                .multiPart("image", new File(FILE_10MB.getTitle()))
                .expect()
                .spec(HelperSpec.Error400())
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek();
    }

    @Test
    void uploadTiffExtensionFileTest() {
        given()
                .multiPart("image", new File(FILE_TIFF.getTitle()))
                .expect()
                .spec(HelperSpec.Error417())
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek();
    }

    @Test
    void uploadOggExtensionFileTest() {
        given()
                .multiPart("video", new File(FILE_OGG.getTitle()))
                .expect()
                .spec(HelperSpec.Error400())
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek();
    }

    @Test
    void uploadExtensionChangedFileTest() {
        given()
                .multiPart("image", new File(FILE_CHANGED_EXE.getTitle()))
                .expect()
                .spec(HelperSpec.Error417())
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek();
    }

    @Test
    void uploadNotImageOrVideoFileTest() {
        given()
                .multiPart("image", new File(FILE_DOCX.getTitle()))
                .expect()
                .spec(HelperSpec.Error417())
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek();
    }

    @Test
    void uploadEmptyFileTest() {
        given()
                .multiPart("image", FILE_EMPTY)
                .expect()
                .spec(HelperSpec.Error400())
                .when()
                .post(UPLOAD_FILE)
                .prettyPeek();
    }

    public static class HelperSpec {
        public static ResponseSpecification Error400() {
            ResponseSpecification spec400 = new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectBody("success", equalTo(false))
                    .expectStatusCode(400)
                    .build();
            return spec400;
        }

        public static ResponseSpecification Error417() {
            ResponseSpecification spec417 = new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectBody("success", equalTo(false))
                    .expectStatusCode(417)
                    .build();
            return spec417;
        }

        public static ResponseSpecification Error404() {
            ResponseSpecification spec417 = new ResponseSpecBuilder()
                    .expectContentType(ContentType.HTML)
                    .expectStatusCode(404)
                    .build();
            return spec417;
        }
    }
}

