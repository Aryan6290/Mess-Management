package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mess_management_app.adapter.MealAdapter;
import com.example.mess_management_app.model.Meals;
import com.example.mess_management_app.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateMenuAdminActivity extends AppCompatActivity {

    EditText mondayDayText,mondayNightText,tuesdayDayText,tuesdayNightText,wednesdayDayText,wednesdayNightText,thursdayDayText,thursdayNightText,fridayDayText,fridayNightText,saturdayDayText,saturdayNightText,sundayDayText,sundayNightText;
    RadioGroup mondayGroup,tuesdayGroup,wednesdayGroup,thursdayGroup,fridayGroup,saturdayGroup,sundayGroup;
    RadioButton mondayButton,tuesdayButton,wednesdayButton,thursdayButton,fridayButton,saturdayButton,sundayButton;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu_admin);
        saveButton=findViewById(R.id.menuSaveButton);
        mondayGroup=findViewById(R.id.mondayRadioGroup);
        tuesdayGroup=findViewById(R.id.tuesdayRadioGroup);
        wednesdayGroup=findViewById(R.id.wednesdayRadioGroup);
        thursdayGroup=findViewById(R.id.thursdayRadioGroup);
        fridayGroup=findViewById(R.id.fridayRadioGroup);
        saturdayGroup=findViewById(R.id.saturdayRadioGroup);
        sundayGroup=findViewById(R.id.sundayRadioGroup);
        mondayDayText=findViewById(R.id.mondayDayEditText);
        mondayNightText=findViewById(R.id.mondayNightEditText);
        tuesdayDayText=findViewById(R.id.tuesdayDayEditText);
        tuesdayNightText=findViewById(R.id.tuesdayNightEditText);
        wednesdayDayText=findViewById(R.id.wednesdayDayEditText);
        wednesdayNightText=findViewById(R.id.wednesdayNightEditText);
        thursdayDayText=findViewById(R.id.thursdayDayEditText);
        thursdayNightText=findViewById(R.id.thursdayNightEditText);
        fridayDayText=findViewById(R.id.fridayDayEditText);
        fridayNightText=findViewById(R.id.fridayNightEditText);
        saturdayDayText=findViewById(R.id.saturdayDayEditText);
        saturdayNightText=findViewById(R.id.saturdayNightEditText);
        sundayDayText=findViewById(R.id.sundayDayEditText);
        sundayNightText=findViewById(R.id.sundayNightEditText);

        getMealSchedule();

        addMealSchedule();
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    private void getMealSchedule(){
        String url = "https://kgec-mess-backend.herokuapp.com/api/meal/schedule/get";

        ProgressDialog pDialog = new ProgressDialog(UpdateMenuAdminActivity.this);
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("check","response");

                try {
                    Toast.makeText(UpdateMenuAdminActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=response.getJSONObject("data");
                    JSONObject monJson=jsonObject.getJSONObject("monday");
                    String monDay=monJson.getString("day");
                    String monNight=monJson.getString("night");
                    Log.d("check mon",monDay);
                    Log.d("check mon",monNight);

                    JSONObject tueJson=jsonObject.getJSONObject("tuesday");
                    String tueDay=tueJson.getString("day");
                    String tueNight=tueJson.getString("night");
                    Log.d("check tue",tueDay);
                    Log.d("check tue",tueNight);


                    JSONObject wedJson=jsonObject.getJSONObject("wednesday");
                    String wedDay=wedJson.getString("day");
                    String wedNight=wedJson.getString("night");
                    Log.d("check tue",wedDay);
                    Log.d("check tue",wedNight);


                    JSONObject thusJson=jsonObject.getJSONObject("thursday");
                    String thusDay=thusJson.getString("day");
                    String thusNight=thusJson.getString("night");


                    JSONObject friJson=jsonObject.getJSONObject("friday");
                    String friDay=friJson.getString("day");
                    String friNight=friJson.getString("night");


                    JSONObject satJson=jsonObject.getJSONObject("saturday");
                    String satDay=satJson.getString("day");
                    String satNight=satJson.getString("night");


                    JSONObject sunJson=jsonObject.getJSONObject("sunday");
                    String sunDay=sunJson.getString("day");
                    String sunNight=sunJson.getString("night");


                    mondayDayText.setText(monDay);
                    mondayNightText.setText(monNight);

                    tuesdayDayText.setText(tueDay);
                    tuesdayNightText.setText(tueNight);

                    wednesdayDayText.setText(wedDay);
                    wednesdayNightText.setText(wedNight);

                    thursdayDayText.setText(thusDay);
                    thursdayNightText.setText(thusNight);

                    fridayDayText.setText(friDay);
                    fridayNightText.setText(friNight);

                    saturdayDayText.setText(satDay);
                    saturdayNightText.setText(satNight);

                    sundayDayText.setText(sunDay);
                    sundayNightText.setText(sunNight);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("check","error");
                Log.d("check", String.valueOf(error.networkResponse.statusCode));
                String body="";
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    Log.d("check",body);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(UpdateMenuAdminActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
            }
        });
        MySingleton.getInstance(UpdateMenuAdminActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    private void addMealSchedule(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mondayId=mondayGroup.getCheckedRadioButtonId();
                mondayButton=findViewById(mondayId);
                String mondayText= (String) mondayButton.getText();

                String mondayDayMeal="",mondayNightMeal="";
                if(mondayText.equals("Day")){
                    mondayDayMeal=mondayDayText.getText().toString().trim();
                }else{
                    mondayNightMeal=mondayNightText.getText().toString().trim();
                }

                Log.d("meal hai eafsd",mondayDayMeal+mondayNightMeal);
                int tuesdayId=tuesdayGroup.getCheckedRadioButtonId();
                tuesdayButton=findViewById(tuesdayId);
                String tuesdayText= (String) tuesdayButton.getText();

                String tuesdayDayMeal="",tuesdayNightMeal="";
                if(tuesdayText.equals("Day")){
                    tuesdayDayMeal=tuesdayDayText.getText().toString().trim();
                }else{
                    tuesdayNightMeal=tuesdayNightText.getText().toString().trim();
                }

                int wednesdayId=wednesdayGroup.getCheckedRadioButtonId();
                wednesdayButton=findViewById(wednesdayId);
                String wednesdayText= (String) wednesdayButton.getText();

                String wednesdayDayMeal="",wednesdayNightMeal="";
                if(wednesdayText.equals("Day")){
                    wednesdayDayMeal=wednesdayDayText.getText().toString().trim();
                }else{
                    wednesdayNightMeal=wednesdayNightText.getText().toString().trim();
                }

                int thursdayId=thursdayGroup.getCheckedRadioButtonId();
                thursdayButton=findViewById(thursdayId);
                String thursdayText= (String) thursdayButton.getText();

                String thursdayDayMeal="",thursdayNightMeal="";
                if(thursdayText.equals("Day")){
                    thursdayDayMeal=thursdayDayText.getText().toString().trim();
                }else{
                    thursdayNightMeal=thursdayNightText.getText().toString().trim();
                }

                int fridayId=fridayGroup.getCheckedRadioButtonId();
                fridayButton=findViewById(fridayId);
                String fridayText= (String) fridayButton.getText();

                String fridayDayMeal="",fridayNightMeal="";
                if(fridayText.equals("Day")){
                    fridayDayMeal=fridayDayText.getText().toString().trim();
                }else{
                    fridayNightMeal=fridayNightText.getText().toString().trim();
                }

                int saturdayId=saturdayGroup.getCheckedRadioButtonId();
                saturdayButton=findViewById(saturdayId);
                String saturdayText= (String) saturdayButton.getText();

                String saturdayDayMeal="",saturdayNightMeal="";
                if(saturdayText.equals("Day")){
                    saturdayDayMeal=saturdayDayText.getText().toString().trim();
                }else{
                    saturdayNightMeal=saturdayNightText.getText().toString().trim();
                }

                int sundayId=sundayGroup.getCheckedRadioButtonId();
                sundayButton=findViewById(sundayId);
                String sundayText= (String) sundayButton.getText();

                String sundayDayMeal="",sundayNightMeal="";
                if(sundayText.equals("Day")){
                    sundayDayMeal=sundayDayText.getText().toString().trim();
                }else{
                    sundayNightMeal=sundayNightText.getText().toString().trim();
                }


                JSONObject mondayJson= new JSONObject();
                try {
                    mondayJson.put("day",mondayDayMeal);
                    mondayJson.put("night",mondayNightMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Log.d("json check monday",mondayJson.getString("day"));
                    Log.d("json check monday",mondayJson.getString("night"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject tuesdayJson= new JSONObject();
                try {
                    tuesdayJson.put("day",tuesdayDayMeal);
                    tuesdayJson.put("night",tuesdayNightMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject wednesdayJson= new JSONObject();
                try {
                    wednesdayJson.put("day",wednesdayDayMeal);
                    wednesdayJson.put("night",wednesdayNightMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject thursdayJson= new JSONObject();
                try {
                    thursdayJson.put("day",thursdayDayMeal);
                    thursdayJson.put("night",thursdayNightMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject fridayJson= new JSONObject();
                try {
                    fridayJson.put("day",fridayDayMeal);
                    fridayJson.put("night",fridayNightMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject saturdayJson= new JSONObject();
                try {
                    saturdayJson.put("day",saturdayDayMeal);
                    saturdayJson.put("night",saturdayNightMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject sundayJson= new JSONObject();
                try {
                    sundayJson.put("day",sundayDayMeal);
                    sundayJson.put("night",sundayNightMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Log.d("monday check",mondayJson.getString("day")+mondayJson.getString("night"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject meal= new JSONObject();
                try {
                    meal.put("monday",mondayJson);
                    meal.put("tuesday",tuesdayJson);
                    meal.put("wednesday",wednesdayJson);
                    meal.put("thursday",thursdayJson);
                    meal.put("friday",fridayJson);
                    meal.put("saturday",saturdayJson);
                    meal.put("sunday",sundayJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject object=meal.getJSONObject("monday");
                    Log.d("main json",object.getString("day")+object.getString("night"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }




                String url = "https://kgec-mess-backend.herokuapp.com/api/meal/schedule/add";

                ProgressDialog pDialog = new ProgressDialog(UpdateMenuAdminActivity.this);
                pDialog.setMessage("Loading...PLease wait");
                pDialog.show();

                SessionManager sessionManager=new SessionManager(UpdateMenuAdminActivity.this);
                String userId=sessionManager.getUser().get_id();

                Map params = new HashMap();
                params.put("userId", userId);
                params.put("data",meal);


                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                        Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("check","response");

                        try {
                            Toast.makeText(UpdateMenuAdminActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("check","error");
                        Log.d("check", String.valueOf(error.networkResponse.statusCode));
                        String body="";
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            Log.d("check",body);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(UpdateMenuAdminActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                        Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
                    }
                });
                MySingleton.getInstance(UpdateMenuAdminActivity.this).addToRequestQueue(jsonObjectRequest);
            }
        });
    }
}
