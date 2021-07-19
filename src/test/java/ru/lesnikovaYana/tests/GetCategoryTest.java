package ru.lesnikovaYana;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.lesnikovaYana.dto.CategoryResponse;
import ru.lesnikovaYana.service.CategoryService;
import ru.lesnikovaYana.util.RetrofitUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetCategoryTest {
    static CategoryService categoryService;
    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {
        Response<CategoryResponse> response = categoryService.getCategory(2).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assertThat(response.body().getTitle(), equalTo("Electronic"));
    }

    @SneakyThrows
    @Test
    void getCategoryByIdNonExistentNegativeTest() {
        Response<CategoryResponse> response = categoryService.getCategory(10).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }

    @SneakyThrows
    @Test
    void getCategoryByIdInvalidNegativeTest() {
        Response<CategoryResponse> response = categoryService.getCategory(-5).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }
}
