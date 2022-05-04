package com.example.mess_management_app.api;

import com.example.mess_management_app.model.AddUser;
import com.example.mess_management_app.model.GetMealResponse;
import com.example.mess_management_app.model.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("api/user/login")
    Call<LoginResponse>loginUser(
            @Field("rollNo") String rollNo,
            @Field("password") String password,
            @Field("userName") String userName,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("api/user/add")
    Call<AddUser>addUser(
            @Field("rollNo") String rollNo,
            @Field("userName") String userName,
            @Field("password") String password,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("api/meal/get")
    Call<GetMealResponse>getMeal(
            @Field("userId") String userId,
            @Field("date") String date
    );

}
