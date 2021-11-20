package ru.lesnikovaYana.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.lesnikovaYana.Helper;

import static io.restassured.RestAssured.given;
import static ru.lesnikovaYana.Endpoints.*;

public class ImgurDeleteTest extends BaseTest{

    @Test
    @DisplayName("Удаление существующего объекта (Authed): ImageHash")
    void deleteImageImageHashPositiveTest() {
        given()
                .when()
                .delete(DELETE_AUTHED, Helper.getImageJson().getString("data.id"));
    }

    @Test
    @DisplayName("Удаление существующего объекта (Un-Authed): DeleteHash")
    void deleteImageDeleteHashPositiveTest() {
        given()
                .when()
                .delete(DELETE_UN_AUTHED, Helper.getImageJson().getString("data.deletehash"));
    }

    @Test
    @DisplayName("Удаление уже удаленного объекта: ImageHash")
    void deletingAlreadyDeletedObjectAuthedTest() {
        String jsonPath = Helper.getImageJson().getString("data.id");

        given()
                .when()
                .delete(DELETE_AUTHED, jsonPath);

        given()
                .when()
                .delete(DELETE_AUTHED, jsonPath);
    }

    @Test
    @DisplayName("Удаление уже удаленного объекта: DeleteHash")
    void deletingAlreadyDeletedObjectUnAuthedTest() {
        String jsonPath =  Helper.getImageJson().getString("data.deletehash");

        given()
                .when()
                .delete(DELETE_UN_AUTHED,jsonPath);

        given()
                .when()
                .delete(DELETE_UN_AUTHED,jsonPath);
    }

    @ParameterizedTest
    @DisplayName("Удаление по невалидному ImageHash")
    @ValueSource(strings = {" ", "&$@()#*@!", "п"})
    void deleteInvalidImageHashTest(String s) {
        given()
                .expect()
                .spec(Helper.error400())
                .when()
                .delete(DELETE_AUTHED, s);
    }

    @ParameterizedTest
    @DisplayName("Удаление по невалидному DeleteHash")
    @ValueSource(strings = {" ", "&$@()#*@!", "п"})
    void deleteInvalidDeleteHashTest(String s) {
        given()
                .expect()
                .spec(Helper.error400())
                .when()
                .delete(DELETE_UN_AUTHED, s);
    }
}
