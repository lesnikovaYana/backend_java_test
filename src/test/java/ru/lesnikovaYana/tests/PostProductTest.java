package ru.lesnikovaYana;

import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Response;
import ru.lesnikovaYana.db.model.Products;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.lesnikovaYana.dto.CategoryResponse.*;
import static ru.lesnikovaYana.util.DbUtils.deleteProductById;

public class PostProductTest extends BaseTest{

    @Test
    @SneakyThrows
    @DisplayName("Заполнены все поля валидными данными")
    void createProductPositiveTest() {
        Response<Product> response = productService.createProduct(product)
                .execute();
        long id = response.body().getId();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(201));

        deleteProductById(productsMapper,id);
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Валидация полей - некорректные данные: categoryTitle")
    @NullSource
    @ValueSource(strings = {"FOOD", "Electr0nik", " ", "Мебель", "Poke mon", "~!@#$%^&*()", " test"})
    void createProductValidatingFieldsIncorrectCategoryTitleTest(String category) {
        Product incorrectProduct = new Product()
                .withTitle("Lamp")
                .withPrice(100)
                .withCategoryTitle(category);

        Response<Product> response = productService.createProduct(incorrectProduct)
                .execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Валидация полей - некорректные данные: title")
    @NullSource
    @ValueSource(strings = {"FOOD", "Electr0nik", " ", "Мебель", "Poke mon", "~!@#$%^&*()", " test"})
    void createProductValidatingFieldsIncorrectTitleTest(String title) {
        Product incorrectProduct = new Product()
                .withTitle(title)
                .withPrice(100)
                .withCategoryTitle("Furniture");

        Response<Product> response = productService.createProduct(incorrectProduct)
                .execute();
        long id =  response.body().getId();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(201));

        deleteProductById(productsMapper,id);
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Валидация полей - некорректные данные: price")
    @ValueSource(ints = {0, -1283, -7, -1000, -2147483647, 2147483647})
    void createProductValidatingFieldsIncorrectPriceTest(int price) {
        Product incorrectProduct = new Product()
                .withTitle("watermelon")
                .withPrice(price)
                .withCategoryTitle("Furniture");

        Response<Product> response = productService.createProduct(incorrectProduct)
                .execute();
        long id =  response.body().getId();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(201));

        deleteProductById(productsMapper,id);
    }

    @Test
    @SneakyThrows
    @DisplayName("Не заполнено ни одно поле")
    void createProductEmptyFieldsNegativeTest() {
        Product emptyProduct = new Product();
        Response<Product> response = productService.createProduct(emptyProduct)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

    @Test
    @SneakyThrows
    @DisplayName("Заполнены не все обязательные поля")
    void createProductNotAllFieldsNegativeTest() {
        Product incompleteProduct = new Product()
                .withTitle(faker.food().ingredient())
                .withPrice((int) (Math.random() * 10000));
        Response<Product> response = productService.createProduct(incompleteProduct)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }
}
