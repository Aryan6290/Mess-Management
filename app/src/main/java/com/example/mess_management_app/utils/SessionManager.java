package com.example.mess_management_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.example.mess_management_app.model.Data;
import com.example.mess_management_app.model.LoginResponse;

public class SessionManager {

    Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.context = context;
    }

    public void saveToken(String token){
        SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("token", token);
        editor.apply();
    }

   public void saveUser(String _id,String rollNo,String userName){
        SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
       SharedPreferences.Editor editor= pref.edit();
       editor.putString("_id", _id);
       editor.putString("rollNo", rollNo);
       editor.putString("userName", userName);
       editor.putBoolean("logged",true);
       editor.apply();
   }

   public void saveImage(String image){
       SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
       SharedPreferences.Editor editor= pref.edit();
       editor.putString("image",image);
       editor.apply();
   }

   public String getImage(){
       SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
       return pref.getString("image",null);
   }

   public void saveTypeSessionManager(String type){
       SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
       SharedPreferences.Editor editor= pref.edit();
       editor.putString("type",type);
       editor.apply();
   }

   public String getTypeSessionManager(){
       SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
       return pref.getString("type",null);
   }

   public boolean isLogged(){
       SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
       return pref.getBoolean("logged",false);
   }

   public String getToken(){
       SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
       return pref.getString("token",null);
   }

   public Data getUser(){
       SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
       return new Data(pref.getString("_id","1"),
               pref.getString("userName",null),
               pref.getString("rollNo",null));
   }

   public String getId(){
        return pref.getString("_id","1");
   }

   public void logOut(){
       SharedPreferences pref=context.getSharedPreferences("shared_pref",Context.MODE_PRIVATE);
       SharedPreferences.Editor editor= pref.edit();
       editor.clear();
       editor.apply();
   }

}
