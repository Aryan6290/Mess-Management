package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mess_management_app.model.DataX;
import com.example.mess_management_app.model.DataXY;
import com.example.mess_management_app.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateInfoBoarderActivity extends AppCompatActivity {


    TextView nameText,rollNoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info_boarder);
        nameText=findViewById(R.id.nameShowInfo);
        rollNoText=findViewById(R.id.rollNoShowInfo);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        showProfileDetail();
    }

    private void showProfileDetail() {

        String url = "https://kgec-mess-backend.herokuapp.com/api/user/get-details";
        SessionManager sessionManager=new SessionManager(UpdateInfoBoarderActivity.this);
        String userId=sessionManager.getUser().get_id();


        ProgressDialog pDialog = new ProgressDialog(UpdateInfoBoarderActivity.this);
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        Map params = new HashMap();
        params.put("userId", userId);
        Log.d("check",userId);
        //Log.d("date",date);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("Sgsgsgsg",response.toString());

                try {
                    JSONObject jsonObject=response.getJSONObject("data");
                    nameText.setText(jsonObject.getString("userName"));
                    rollNoText.setText(jsonObject.getString("rollNo"));

                    Log.d("user info",jsonObject.getString("userName"));
                    Log.d("user info",jsonObject.getString("rollNo"));


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
                Toast.makeText(UpdateInfoBoarderActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
            }
        });
        MySingleton.getInstance(UpdateInfoBoarderActivity.this).addToRequestQueue(jsonObjectRequest);
        pDialog.dismiss();

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