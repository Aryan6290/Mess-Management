package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mess_management_app.api.RetrofitInstance;
import com.example.mess_management_app.model.AddUser;
import com.example.mess_management_app.model.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class AddBoarderAdminActivity extends AppCompatActivity {

    EditText nameEditText,rollNoEditText,passwordEditText;
    ProgressDialog dialog;
    Button addUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_boarder_admin);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Adding user...");
        dialog.setCancelable(false);


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        nameEditText=findViewById(R.id.addBoarderNameEditText);
        rollNoEditText=findViewById(R.id.addBoarderRollNoEditText);
        passwordEditText=findViewById(R.id.addBoarderPasswordEditText);
        addUserButton=findViewById(R.id.addBoarderButton);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });


    }

    private void addUser() {
        String userId=nameEditText.getText().toString().trim();
        String password=passwordEditText.getText().toString().trim();
        String rollNo=rollNoEditText.getText().toString().trim();
        String type="boarder";


        if(userId.isEmpty()){
            nameEditText.setError("userId is required");
            nameEditText.requestFocus();
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
        String url = "https://kgec-mess-backend.herokuapp.com/api/user/add";

        ProgressDialog pDialog = new ProgressDialog(AddBoarderAdminActivity.this);
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        Map params = new HashMap();
        params.put("userName", userId);
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

                    Toast.makeText(AddBoarderAdminActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body="";
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(AddBoarderAdminActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
            }
        });
        MySingleton.getInstance(AddBoarderAdminActivity.this).addToRequestQueue(jsonObjectRequest);


    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}