package com.example.mess_management_app.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL="https://kgec-mess-backend.herokuapp.com/";
    private static RetrofitInstance mInstance;
    private Retrofit retrofit;

    HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
    private OkHttpClient login(){
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        return okHttpClient;
    }



   private RetrofitInstance(){
    retrofit=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(login())
            .build();
   }

   public static synchronized RetrofitInstance getInstance(){
       if (mInstance==null){
           mInstance=new RetrofitInstance();
       }
       return mInstance;
   }

   public Api getApi(){
        return retrofit.create(Api.class);
   }
}
