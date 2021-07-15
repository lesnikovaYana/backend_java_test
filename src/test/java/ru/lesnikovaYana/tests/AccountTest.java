package ru.lesnikovaYana.tests;

import org.junit.jupiter.api.Test;
import ru.lesnikovaYana.Endpoints;

import static io.restassured.RestAssured.given;
import static ru.lesnikovaYana.Endpoints.GET_ACCOUNT;

public class AccountTest extends BaseTest{

    @Test
    void getAccountInfoTest() {
        given()
                .when()
                .get(GET_ACCOUNT, username)
                .prettyPeek();
    }
}