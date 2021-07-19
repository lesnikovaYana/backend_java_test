package ru.lesnikovaYana;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.lesnikovaYana.dto.CategoryResponse;
import ru.lesnikovaYana.enam.CategoryType;
import ru.lesnikovaYana.service.ProductService;
import ru.lesnikovaYana.util.RetrofitUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.lesnikovaYana.dto.CategoryResponse.*;

public class PostProductTest {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    //без ID
    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    @SneakyThrows
    void createProductPositiveTest() {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(201));

        tearDown();
    }

    @Test
    @SneakyThrows
    void createProductValidatingFieldsIncorrectDataTest() {
        Product incorrectProduct = new Product()
                .withTitle("♣☺” , “”‘~!@#$%^&*()?>,./<][ /*<!–”, “$")
                .withPrice(-100)
                .withCategoryTitle("Electronic"); //код 500 если использовать несуществующую категорию
        Response<Product> response = productService.createProduct(incorrectProduct)
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(201));
        tearDown();
    }

    @Test
    @SneakyThrows
    void createProductEmptyFieldsNegativeTest() {
        Product emptyProduct = new Product();
        Response<Product> response = productService.createProduct(emptyProduct)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

    @Test
    @SneakyThrows
    void createProductNotAllFieldsNegativeTest() {
        Product incompleteProduct = new Product()
                .withTitle(faker.food().ingredient())
                .withPrice((int) (Math.random() * 10000));
        Response<Product> response = productService.createProduct(incompleteProduct)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

    @SneakyThrows
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}
