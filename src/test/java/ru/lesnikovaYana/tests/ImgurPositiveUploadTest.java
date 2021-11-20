package ru.lesnikovaYana.tests;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.lesnikovaYana.ResourcePath;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static ru.lesnikovaYana.Endpoints.DELETE_UN_AUTHED;
import static ru.lesnikovaYana.Endpoints.UPLOAD_FILE;
import static ru.lesnikovaYana.ResourcePath.*;

public class ImgurPositiveUploadTest extends BaseTest{
    private  String imageDeleteHash;

    @ParameterizedTest
    @DisplayName("Загрузка файла корректного размера")
    @EnumSource(value = ResourcePath.class, names = {"FILE_JPG","FILE_1X1PX","FILE_HD"})
    void uploadCorrectFileSizeTest(ResourcePath path) {
        String title = path.getTitle();
        imageDeleteHash = given()
                .multiPart("image", new File(title))
                .when()
                .post(UPLOAD_FILE)
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @ParameterizedTest
    @DisplayName("Загрузка файла корректного расширения")
    @EnumSource(value = ResourcePath.class, names = {"FILE_GIF","FILE_PNG","FILE_BMP","FILE_MP4",
            "FILE_AVI","FILE_MOV","FILE_WEBM"})
    void uploadExtensionFileTest(ResourcePath path) {
        String title = path.getTitle();
        imageDeleteHash = given()
                .multiPart("image", new File(title))
                .when()
                .post(UPLOAD_FILE)
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    @DisplayName("Загрузка изображения через url")
    void uploadImageUrlTest() {
        imageDeleteHash = given()
                .formParam("type", "url")
                .multiPart("image", TEST_URL.getTitle())
                .when()
                .post(UPLOAD_FILE)
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    @DisplayName("Загрузка изображения в кодировке base64")
    void uploadImageBase64Test() {
        String encodedFile;
        byte[] byteArray = getFileContent();
        encodedFile = Base64.getEncoder().encodeToString(byteArray);

        imageDeleteHash = given()
                .formParam("type", "base64")
                .multiPart("image", encodedFile)
                .when()
                .post(UPLOAD_FILE)
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
