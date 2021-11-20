package ru.lesnikovaYana.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.lesnikovaYana.Helper;

import static io.restassured.RestAssured.given;
import static ru.lesnikovaYana.Endpoints.*;

public class ImgurGetTest extends BaseTest{
    private String title = Helper.getImageJson().getString("data.id");

    @Test
    @DisplayName("Получить изображение")
    void getImagePositiveTest() {
        given()
                .when()
                .get(GET_IMAGE, title)
                .then()
                .extract()
                .jsonPath()
                .getString("data.deletehash");
        tearDown();
    }

    @ParameterizedTest
    @DisplayName("Получить изображение по невалидному ImageHash")
    @ValueSource(strings = {" ", "&$@()#*@!", "п"})
    void getImageInvalidImageHashNegativeTest(String s) {
        given()
                .expect()
                .spec(Helper.error400())
                .when()
                .get(GET_IMAGE, s);
    }

    private void tearDown() {
        given()
                .when()
                .delete(DELETE_AUTHED, title);
    }

}
