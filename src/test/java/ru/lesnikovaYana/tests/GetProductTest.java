package ru.lesnikovaYana;

import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.lesnikovaYana.dto.CategoryResponse;
import ru.lesnikovaYana.service.ProductService;
import ru.lesnikovaYana.util.RetrofitUtils;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.lesnikovaYana.dto.CategoryResponse.*;

public class GetProductTest extends BaseTest{

    // переделать
    @Test
    @SneakyThrows
    void getProductByIdPositiveTest() {
        Response<Product> response = productService.getProduct(9090)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
    }

    @Test
    @SneakyThrows
    void getProductByIdNonExistentNegativeTest() {
        Response<Product> response = productService.getProduct(1)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }

    @Test
    @SneakyThrows
    void getProductByIdInvalidNegativeTest() {
        Response<Product> response = productService.getProduct(-100)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }
}
