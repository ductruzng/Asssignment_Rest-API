package trungndph39729.fpoly.assignment.Service;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import trungndph39729.fpoly.assignment.Domain.CategoryDomain;
import trungndph39729.fpoly.assignment.Domain.ItemsDomain;
import trungndph39729.fpoly.assignment.Domain.Order;
import trungndph39729.fpoly.assignment.Domain.Response;
import trungndph39729.fpoly.assignment.Domain.User;

public interface ApiServices {
    public static String BASE_URL = "http://10.0.2.2:3000/api/";


    @POST("register-send-mail")
    Call<Response<User>> register(@Body User user);

    @POST("login")
    Call<Response<User>> login(@Body User user);

    @GET("products")
    Call<Response<ArrayList<ItemsDomain>>> getProducts (@Header("Authorization") String token);

    @GET("categories")
    Call<Response<ArrayList<CategoryDomain>>> getCategories ();

    @GET("profile/{id}")
    Call<Response<User>> getUser (@Path("id") String id);


    @Multipart
    @PUT("update-profile-by-id/{id}")
    Call<Response<User>> updateProfile(
            @Path("id") String id,
            @Part("phoneNo") RequestBody phoneNo,
                                  @Part("email") RequestBody email,
                                  @Part("name") RequestBody name,
                                  @Part MultipartBody.Part avatar);

    @GET("search-product")
    Call<Response<ArrayList<ItemsDomain>>> searchProduct(@Query("key") String key);

    @POST("orders")
    Call<Response<Order>> addOrder(@Body Order order);


    @GET("orders/{userId}")
    Call<Response<ArrayList<Order>>> getOrderById (@Path("userId") String userId);

}

