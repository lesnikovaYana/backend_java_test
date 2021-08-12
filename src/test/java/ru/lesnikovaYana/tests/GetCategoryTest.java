package ru.lesnikovaYana;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Response;
import ru.lesnikovaYana.dto.CategoryResponse;
import ru.lesnikovaYana.service.CategoryService;
import ru.lesnikovaYana.util.RetrofitUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetCategoryTest extends BaseTest{

    @SneakyThrows
    @Test
    @DisplayName("Запрос данных по валидному ID")
    void getCategoryByIdPositiveTest() {
        Response<CategoryResponse> response = categoryService.getCategory(2).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assertThat(response.body().getTitle(), equalTo("Electronic"));
    }

    @SneakyThrows
    @ParameterizedTest
    @DisplayName("Запрос данных по несуществующему ID, но в валидном формате")
    @ValueSource(ints = {10, 45, 14, 7889})
    void getCategoryByIdNonExistentNegativeTest(int id) {
        Response<CategoryResponse> response = categoryService.getCategory(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }

    @SneakyThrows
    @ParameterizedTest
    @DisplayName("Запрос данных по невалидному ID")
    @ValueSource(ints = {-5, -45555, 0, -100, 2147483647})
    void getCategoryByIdInvalidNegativeTest(int id) {
        Response<CategoryResponse> response = categoryService.getCategory(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(404));
    }
}