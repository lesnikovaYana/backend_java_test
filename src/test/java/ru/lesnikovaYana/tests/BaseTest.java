package ru.lesnikovaYana.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;

public abstract class BaseTest {
    static Properties properties = new Properties();
    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;
    static String token;
    static String username;

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());

        getProperties();
        token = properties.getProperty("token");
        username = properties.getProperty("username");

        RestAssured.baseURI = "https://api.imgur.com/3";

        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectBody("success", is(true))
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();

        RestAssured.requestSpecification = requestSpec;
        //RestAssured.responseSpecification = responseSpec;
    }


    private static void getProperties(){
        try (InputStream output = new FileInputStream("src/test/resources/application.properties")) {
            properties.load(output);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
