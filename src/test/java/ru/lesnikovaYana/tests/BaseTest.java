package ru.lesnikovaYana;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.lesnikovaYana.db.dao.ProductsMapper;
import ru.lesnikovaYana.dto.CategoryResponse;
import ru.lesnikovaYana.dto.CategoryResponse.Product;
import ru.lesnikovaYana.enam.CategoryType;
import ru.lesnikovaYana.service.CategoryService;
import ru.lesnikovaYana.service.ProductService;
import ru.lesnikovaYana.util.DbUtils;
import ru.lesnikovaYana.util.RetrofitUtils;

public abstract class BaseTest {
    static CategoryService categoryService;
    static ProductService productService;
    static ProductsMapper productsMapper;
    Product product;
    Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit()
                .create(CategoryService.class);
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
        productsMapper = DbUtils.getProductsMapper();
    }

    @BeforeEach
    @SneakyThrows
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 10000));
    }
}
