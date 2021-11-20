package ru.lesnikovaYana.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.lesnikovaYana.Helper;
import ru.lesnikovaYana.ResourcePath;

import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.lesnikovaYana.Endpoints.UPLOAD_FILE;
import static ru.lesnikovaYana.ResourcePath.*;

public class ImgurNegativeUploadTest extends BaseTest{

    @ParameterizedTest
    @DisplayName("Загрузка файла некоректного размера")
    @EnumSource(value = ResourcePath.class, names = {"FILE_1BITE","FILE_10MB"})
    void uploadSmallFileSizeTest(ResourcePath path) {
        String title = path.getTitle();
        given()
                .multiPart("image", new File(title))
                .expect()
                .spec(Helper.error400())
                .when()
                .post(UPLOAD_FILE);
    }

    @Test
    @DisplayName("Загрузка изображения с расширением tiff")
    void uploadTiffExtensionFileTest() {
        given()
                .multiPart("image", new File(FILE_TIFF.getTitle()))
                .expect()
                .spec(Helper.error417())
                .when()
                .post(UPLOAD_FILE);
    }

    @Test
    @DisplayName("Загрузка изображения с расширением ogg")
    void uploadOggExtensionFileTest() {
        given()
                .multiPart("video", new File(FILE_OGG.getTitle()))
                .expect()
                .spec(Helper.error400())
                .when()
                .post(UPLOAD_FILE);
    }

    @ParameterizedTest
    @DisplayName("Загрузка невалидных файлов")
    @EnumSource(value = ResourcePath.class, names = {"FILE_CHANGED_EXE","FILE_DOCX"})
    void uploadExtensionChangedFileTest(ResourcePath path) {
        String title = path.getTitle();
        given()
                .multiPart("image", new File(title))
                .expect()
                .spec(Helper.error417())
                .when()
                .post(UPLOAD_FILE);
    }
}

