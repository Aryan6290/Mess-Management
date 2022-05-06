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
import com.example.mess_management_app.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UpdatePasswordAdminActivity extends AppCompatActivity {

    EditText rollNoEditText,oldPasswordEditText,newPasswordEditText;
    Button updateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_admin);

        rollNoEditText=findViewById(R.id.rollNoPasswordAdminEditText);
        newPasswordEditText=findViewById(R.id.newPasswordAdminEditText);
        oldPasswordEditText=findViewById(R.id.oldPasswordAdminEditText);
        updateButton=findViewById(R.id.updatePasswordAdminButton);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordChange();
            }
        });
    }

    private void passwordChange() {
        String rollNo=rollNoEditText.getText().toString().trim();
        String oldPassword=oldPasswordEditText.getText().toString().trim();
        String newPassword=newPasswordEditText.getText().toString().trim();

        if(rollNo.isEmpty()){
            rollNoEditText.setError("Roll No is required");
            rollNoEditText.requestFocus();
            return;
        }

        if(oldPassword.isEmpty()){
            oldPasswordEditText.setError("userId is required");
            oldPasswordEditText.requestFocus();
            return ;
        }

        if(newPassword.isEmpty()){
            newPasswordEditText.setError("Password is required");
            newPasswordEditText.requestFocus();
            return ;
        }

        String url = "https://kgec-mess-backend.herokuapp.com/api/user/change";

        ProgressDialog pDialog = new ProgressDialog(UpdatePasswordAdminActivity.this);
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        Map params = new HashMap();
        params.put("rollNo", rollNo);
        params.put("oldPassword",oldPassword);
        params.put("newPassword",newPassword);
        //Log.d("date",date);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(UpdatePasswordAdminActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ;
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
                Toast.makeText(UpdatePasswordAdminActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
            }
        });
        MySingleton.getInstance(UpdatePasswordAdminActivity.this).addToRequestQueue(jsonObjectRequest);


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