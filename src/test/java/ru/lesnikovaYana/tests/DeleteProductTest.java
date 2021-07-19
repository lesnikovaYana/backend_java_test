package ru.lesnikovaYana;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
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

public class DeleteProductTest {
    static ProductService productService;
    CategoryResponse.Product product;
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    @SneakyThrows
    void setUp() {
        product = new CategoryResponse.Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    @SneakyThrows
    void deleteProductPositiveTest(){
        Response<CategoryResponse.Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();

        Response<ResponseBody> bodyResponse = productService.deleteProduct(id).execute();
        assertThat(bodyResponse.isSuccessful(), CoreMatchers.is(true));
        assertThat(bodyResponse.code(), equalTo(200));
    }

    @Test
    @SneakyThrows
    void deleteProductByIdInvalidNegativeTest(){
        Response<ResponseBody> response = productService.deleteProduct(-1283).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

    @Test
    @SneakyThrows
    void deleteProductByIdNonExistentTest(){
        Response<ResponseBody> response = productService.deleteProduct(1).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

    @Test
    @SneakyThrows
    void deleteAnAlreadyDeletedObjectTest(){
        Response<CategoryResponse.Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();

        Response<ResponseBody> bodyResponse = productService.deleteProduct(id).execute();
        assertThat(bodyResponse.isSuccessful(), CoreMatchers.is(true));
        assertThat(bodyResponse.code(), equalTo(200));

        Response<ResponseBody> bodyResponse2 = productService.deleteProduct(id).execute();
        assertThat(bodyResponse2.isSuccessful(), CoreMatchers.is(false));
        assertThat(bodyResponse2.code(), equalTo(500));
    }
}
