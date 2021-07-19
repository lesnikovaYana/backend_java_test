package ru.lesnikovaYana.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.lesnikovaYana.dto.CategoryResponse;

public interface CategoryService {

    @GET("categories/{id}")
    Call<CategoryResponse> getCategory(@Path("id") int id);
}
