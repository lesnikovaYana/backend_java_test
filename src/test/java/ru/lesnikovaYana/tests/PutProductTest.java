package ru.lesnikovaYana;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.lesnikovaYana.db.model.Products;
import ru.lesnikovaYana.dto.CategoryResponse.Product;
import ru.lesnikovaYana.util.DbUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.lesnikovaYana.util.DbUtils.deleteProductById;
import static ru.lesnikovaYana.util.DbUtils.updateProduct;

public class PutProductTest extends BaseTest{
    long id;
    Products products;

    @Test
    @SneakyThrows
    void putProductPositiveTest() {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id = response.body().getId();

        products = new Products();
        products.setPrice(100);
        products.setCategory_id(2l);
        products.setTitle("Test");
        products.setId(id);

        Integer countProductsBefore = DbUtils.countProducts(productsMapper);
        int result = updateProduct(productsMapper, products);
        assertThat(result, equalTo(1));
        Integer countProductsAfter = DbUtils.countProducts(productsMapper);
        assertThat(countProductsAfter, equalTo(countProductsBefore));

        deleteProductById(productsMapper, id);
    }

    @Test
    @SneakyThrows
    void putProductValidatingFieldsIncorrectDataTest() {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id = response.body().getId();

        products = new Products();
        products.setId(id);
        products.setTitle("♣☺” , “”‘~!@#$%^&*()?>,./<][ /*<!–”, “$");
        products.setPrice(-100);
        products.setCategory_id(3l);

        Integer countProductsBefore = DbUtils.countProducts(productsMapper);
        int result = updateProduct(productsMapper, products);
        assertThat(result, equalTo(1));
        Integer countProductsAfter = DbUtils.countProducts(productsMapper);
        assertThat(countProductsAfter, equalTo(countProductsBefore));

        deleteProductById(productsMapper, id);
    }

    @Test
    @SneakyThrows
    void putProductByIdNonExistentNegativeTest() {

        products = new Products();
        products.setId(1l);
        products.setTitle("Elephant");
        products.setPrice(100);
        products.setCategory_id(3l);

        Integer countProductsBefore = DbUtils.countProducts(productsMapper);
        int result = updateProduct(productsMapper, products);
        assertThat(result, equalTo(0));
        Integer countProductsAfter = DbUtils.countProducts(productsMapper);
        assertThat(countProductsAfter, equalTo(countProductsBefore));
    }

    @Test
    @SneakyThrows
    void putProductByIdInvalidNegativeTest() {

        products = new Products();
        product.setId(-12598);
        product.setTitle("Elephant");
        product.setPrice(100);
        product.setCategoryTitle("Food");

        Integer countProductsBefore = DbUtils.countProducts(productsMapper);
        int result = updateProduct(productsMapper, products);
        assertThat(result, equalTo(0));
        Integer countProductsAfter = DbUtils.countProducts(productsMapper);
        assertThat(countProductsAfter, equalTo(countProductsBefore));
    }
}
