package com.example.mess_management_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mess_management_app.api.RetrofitInstance;
import com.example.mess_management_app.model.Data;
import com.example.mess_management_app.model.LoginResponse;
import com.example.mess_management_app.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class LoginAdminActivity extends AppCompatActivity {
    TextView st;
    Button loginButton;
    EditText userIdEditText,passwordEditText,rollNoEditText;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        int id=0 ;

        loginButton=findViewById(R.id.adminLoginButton);
        userIdEditText=findViewById(R.id.userIdAdminEditText);
        passwordEditText=findViewById(R.id.passwordAdminEditText);
        rollNoEditText=findViewById(R.id.rollNoAdminEditText);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            id = bundle.getInt("Admin");
        }
        st=findViewById(R.id.loginText);
       textSet(id);
       Log.d("main","val id "+ id);

       sessionManager=new SessionManager(getApplicationContext());


    }

    private void userSignUp(Integer id,String type) {

        String userId=userIdEditText.getText().toString().trim();
        String password=passwordEditText.getText().toString().trim();
        String rollNo=rollNoEditText.getText().toString().trim();


        if(userId.isEmpty()){
            userIdEditText.setError("userId is required");
            userIdEditText.requestFocus();
            return ;
        }

        if(password.isEmpty()){
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return ;
        }

        if(rollNo.isEmpty()){
            rollNoEditText.setError("Roll No is required");
            rollNoEditText.requestFocus();
            return;
        }


        String url = "https://kgec-mess-backend.herokuapp.com/api/user/login";

        ProgressDialog pDialog = new ProgressDialog(LoginAdminActivity.this);
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        Map params = new HashMap();
        params.put("userId", userId);
        params.put("rollNo", rollNo);
        params.put("password",password);
        params.put("type",type);
        Log.d("check",userId+rollNo+password+type);
        //Log.d("date",date);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject=response.getJSONObject("data");
                    sessionManager.saveToken(response.getString("token"));
                    sessionManager.saveUser(jsonObject.getString("_id"),jsonObject.getString("rollNo"),jsonObject.getString("userName"));
                    sessionManager.saveTypeSessionManager(type);
                    Toast.makeText(LoginAdminActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    if(id==1){
                        Intent intent = new Intent(LoginAdminActivity.this, MainAdminActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(LoginAdminActivity.this, MainBoarderActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body="";
                //get status code here
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                if(error.networkResponse.data!=null) {
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(LoginAdminActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
            }
        });
        MySingleton.getInstance(LoginAdminActivity.this).addToRequestQueue(jsonObjectRequest);

    }

    public void textSet(int id){
        if(id==1){
            st.setText("Login As Admin");
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userSignUp(id,"admin");
                }
            });


        }else{
            st.setText("Login As Boarder");
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userSignUp(id,"boarder");
                }
            });

        }
    }
}