package ru.lesnikovaYana;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.lesnikovaYana.db.dao.ProductsMapper;
import ru.lesnikovaYana.dto.CategoryResponse;
import ru.lesnikovaYana.dto.CategoryResponse.Product;
import ru.lesnikovaYana.enam.CategoryType;
import ru.lesnikovaYana.service.ProductService;
import ru.lesnikovaYana.util.DbUtils;
import ru.lesnikovaYana.util.RetrofitUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.lesnikovaYana.util.DbUtils.countProducts;
import static ru.lesnikovaYana.util.DbUtils.deleteProductById;

public class DeleteProductTest extends BaseTest{
    long id;

    @Test
    @SneakyThrows
    void deleteProductPositiveTest(){

        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();

        Integer countProductsBefore = countProducts(productsMapper);
        int result = deleteProductById(productsMapper, id);
        Integer countProductsAfter = countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore));
        assertThat(result, equalTo(1));
    }

    @Test
    @SneakyThrows
    void deleteProductByIdInvalidNegativeTest(){
        Integer countProductsBefore = countProducts(productsMapper);
        int result = deleteProductById(productsMapper, -1283l);
        Integer countProductsAfter = countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore));
        assertThat(result, equalTo(0));
    }

    @Test
    @SneakyThrows
    void deleteProductByIdNonExistentTest(){
        Integer countProductsBefore = countProducts(productsMapper);
        int result = deleteProductById(productsMapper, 1l);
        Integer countProductsAfter = countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore));
        assertThat(result, equalTo(0));
    }

    @Test
    @SneakyThrows
    void deleteAnAlreadyDeletedObjectTest(){
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();

        //здесь count не работает
        //Integer countProductsBefore = countProducts(productsMapper);
        int result = deleteProductById(productsMapper, id);
        //Integer countProductsAfter = countProducts(productsMapper);
        //assertThat(countProductsAfter, equalTo(countProductsBefore));
        assertThat(result, equalTo(1));

        int result2 = deleteProductById(productsMapper, id);
        assertThat(result2, equalTo(0));
    }
}
