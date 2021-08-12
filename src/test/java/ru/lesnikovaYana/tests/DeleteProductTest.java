package ru.lesnikovaYana;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Response;
import ru.lesnikovaYana.dto.CategoryResponse.Product;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.lesnikovaYana.util.DbUtils.deleteProductById;

public class DeleteProductTest extends BaseTest{

    @Test
    @SneakyThrows
    @DisplayName("Удаление существующего объекта")
    void deleteProductPositiveTest(){
        Response<Product> response = productService.createProduct(product)
                .execute();
        long id =  response.body().getId();

        boolean result = deleteProductById(productsMapper, id);
        assertThat(result, equalTo(true));
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Удаление по невалидному ID")
    @ValueSource(longs = {0, -1283, -10000, -4556, -2147483647})
    void deleteProductByIdInvalidNegativeTest(long id){
        boolean result = deleteProductById(productsMapper, id);
        assertThat(result, equalTo(false));
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Удаление по несуществующему ID")
    @ValueSource(longs = {1, 750599904340651L, 70, 116})
    void deleteProductByIdNonExistentTest(long id){
        boolean result = deleteProductById(productsMapper, id);
        assertThat(result, equalTo(false));
    }

    @Test
    @SneakyThrows
    @DisplayName("Удаление уже удаленного объекта")
    void deleteAnAlreadyDeletedObjectTest(){
        Response<Product> response = productService.createProduct(product)
                .execute();
        long id =  response.body().getId();

        boolean result = deleteProductById(productsMapper, id);
        assertThat(result, equalTo(true));

        boolean result2 = deleteProductById(productsMapper, id);
        assertThat(result2, equalTo(false));
    }
}