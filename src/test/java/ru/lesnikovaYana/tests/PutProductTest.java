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
import ru.lesnikovaYana.dto.CategoryResponse.Product;
import ru.lesnikovaYana.enam.CategoryType;
import ru.lesnikovaYana.service.ProductService;
import ru.lesnikovaYana.util.RetrofitUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PutProductTest {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    @SneakyThrows
    void putProductPositiveTest() {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();

        product.setId(id);
        product.setTitle("Elephant");
        product.setPrice(100);
        product.setCategoryTitle("Food");

        Response<Product> putResponse = productService.putProduct(product)
                .execute();
        assertThat(putResponse.isSuccessful(), CoreMatchers.is(true));
        assertThat(putResponse.code(), equalTo(200));

        tearDown();
    }

    @Test
    @SneakyThrows
    void putProductValidatingFieldsIncorrectDataTest() {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();

        product.setId(id);
        product.setTitle("♣☺” , “”‘~!@#$%^&*()?>,./<][ /*<!–”, “$");
        product.setPrice(-100);
        product.setCategoryTitle("Food"); //код 500 если использовать несуществующую категорию

        Response<Product> putResponse = productService.putProduct(product)
                .execute();
        assertThat(putResponse.isSuccessful(), CoreMatchers.is(true));
        assertThat(putResponse.code(), equalTo(200));

        tearDown();
    }

    @Test
    @SneakyThrows
    void putProductByIdNonExistentNegativeTest() {

        product.setId(1);
        product.setTitle("Elephant");
        product.setPrice(100);
        product.setCategoryTitle("Food");

        Response<Product> putResponse = productService.putProduct(product)
                .execute();
        assertThat(putResponse.isSuccessful(), CoreMatchers.is(false));
        assertThat(putResponse.code(), equalTo(400));
    }

    @Test
    @SneakyThrows
    void putProductByIdInvalidNegativeTest() {

        product.setId(-12598);
        product.setTitle("Elephant");
        product.setPrice(100);
        product.setCategoryTitle("Food");

        Response<Product> putResponse = productService.putProduct(product)
                .execute();
        assertThat(putResponse.isSuccessful(), CoreMatchers.is(false));
        assertThat(putResponse.code(), equalTo(400));
    }

    @SneakyThrows
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}
