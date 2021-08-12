package ru.lesnikovaYana;

import com.sun.net.httpserver.Authenticator;
import lombok.SneakyThrows;
import org.apache.ibatis.exceptions.PersistenceException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.postgresql.util.PSQLException;
import retrofit2.Response;
import ru.lesnikovaYana.db.model.Products;
import ru.lesnikovaYana.dto.CategoryResponse.Product;
import ru.lesnikovaYana.util.DbUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.lesnikovaYana.util.DbUtils.deleteProductById;
import static ru.lesnikovaYana.util.DbUtils.updateProduct;

public class PutProductTest extends BaseTest{
    private long id;
    private Products products;

    @Test
    @SneakyThrows
    @DisplayName("Обновление с корректными данными")
    void putProductPositiveTest() {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id = response.body().getId();

        products = new Products();
        products.setPrice(110);
        products.setCategory_id(1L);
        products.setTitle("peach");
        products.setId(id);

        boolean result = updateProduct(productsMapper, products);
        assertThat(result, equalTo(true));

        deleteProductById(productsMapper, id);
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Валидация полей - некорректные данные: categoryTitle")
    @ValueSource(longs = {0, -1, -2147483647, 750599904340651L, 7, 5})
    void putProductValidatingFieldsIncorrectCategoryTitleTest(long title) {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id = response.body().getId();

        products = new Products();
        products.setId(id);
        products.setTitle("koumiss");
        products.setPrice(100);
        products.setCategory_id(title);

        assertThrows(Exception.class, () -> {
            updateProduct(productsMapper, products);
        });

    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Валидация полей - некорректные данные: price")
    @ValueSource(ints = {0, -1, -2147483647, 2147483647, -7, -500})
    void putProductValidatingFieldsIncorrectPriceTest(int price) {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id = response.body().getId();

        products = new Products();
        products.setId(id);
        products.setTitle("rice");
        products.setPrice(price);
        products.setCategory_id(1L);

        boolean result = updateProduct(productsMapper, products);
        assertThat(result, equalTo(true));

        deleteProductById(productsMapper, id);
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Валидация полей - некорректные данные: title")
    @NullSource
    @ValueSource(strings = {"FOOD", "Electr0nik", " ", "Мебель", "Poke mon", "~!@#$%^&*()", " test"})
    void putProductValidatingFieldsIncorrectTitleTest(String title) {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id = response.body().getId();

        products = new Products();
        products.setId(id);
        products.setTitle(title);
        products.setPrice(756);
        products.setCategory_id(1L);

        boolean result = updateProduct(productsMapper, products);
        assertThat(result, equalTo(true));

        deleteProductById(productsMapper, id);
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Обновление по несуществующему ID")
    @ValueSource(longs = {1, 750599904340651L, 70, 116})
    void putProductByIdNonExistentNegativeTest(long id) {

        products = new Products();
        products.setId(id);
        products.setTitle("Elephant");
        products.setPrice(100);
        products.setCategory_id(3l);


        boolean result = updateProduct(productsMapper, products);
        assertThat(result, equalTo(false));
    }

    @ParameterizedTest
    @SneakyThrows
    @DisplayName("Обновление по невалидному ID")
    @ValueSource(longs = {0, -1283, -10000, -4556, -2147483647})
    void putProductByIdInvalidNegativeTest(long id) {

        products = new Products();
        products.setId(id);
        products.setTitle("Elephant");
        products.setPrice(100);
        products.setCategory_id(3l);

        boolean result = updateProduct(productsMapper, products);
        assertThat(result, equalTo(false));
    }
}
