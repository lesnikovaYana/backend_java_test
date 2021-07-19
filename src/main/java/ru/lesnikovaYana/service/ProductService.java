package ru.lesnikovaYana.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.lesnikovaYana.dto.CategoryResponse.Product;

import java.util.ArrayList;

public interface ProductService {
    @POST("products")
    Call<Product> createProduct(@Body Product product);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @PUT("products")
    Call<Product> putProduct(@Body Product product);

    @GET("products")
    Call<ArrayList<Product>> getProducts();

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id);
}
