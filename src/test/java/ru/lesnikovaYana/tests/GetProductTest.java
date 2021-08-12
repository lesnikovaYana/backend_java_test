package ru.lesnikovaYana;

import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Response;
import ru.lesnikovaYana.dto.CategoryResponse;
import ru.lesnikovaYana.service.ProductService;
import ru.lesnikovaYana.util.RetrofitUtils;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.lesnikovaYana.dto.CategoryResponse.*;
import static ru.lesnikovaYana.util.DbUtils.deleteProductById;

public class GetProductTest extends BaseTest{

    @Test
    @SneakyThrows
    @DisplayName("Запрос данных по валидному ID")
    void getProductByIdPositiveTest() {
        Response<Product> productResponse = productService.createProduct(product)
                .execute();
        int id = productResponse.body().getId();

        Response<Product> response = productService.getProduct(id)
                .execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));

        deleteProductById(productsMapper,id);
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Запрос данных по несуществующему ID, но в валидном формате")
    @ValueSource(ints = {1, 9090, 507679})
    void getProductByIdNonExistentNegativeTest(int id) {
        Response<Product> response = productService.getProduct(id)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Запрос данных по невалидному ID")
    @ValueSource(ints = {-5, -45555, 0, -100, 2147483647})
    void getProductByIdInvalidNegativeTest(int id) {
        Response<Product> response = productService.getProduct(id)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }
}
