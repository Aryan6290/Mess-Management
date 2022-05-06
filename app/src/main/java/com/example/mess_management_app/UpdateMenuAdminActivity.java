package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.mess_management_app.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UpdateMenuAdminActivity extends AppCompatActivity {

    EditText mondayVegText, mondayNonVegText, tuesdayVegText, tuesdayNonVegText, wednesdayVegText, wednesdayNonVegText, thursdayVegText, thursdayNonVegText, fridayVegText, fridayNonVegText, saturdayVegText, saturdayNonVegText, sundayVegText, sundayNonVegText;
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
        mondayVegText =findViewById(R.id.mondayVegEditText);
        mondayNonVegText =findViewById(R.id.mondayNonVegEditText);
        tuesdayVegText =findViewById(R.id.tuesdayVegEditText);
        tuesdayNonVegText =findViewById(R.id.tuesdayNonVegEditText);
        wednesdayVegText =findViewById(R.id.wednesdayVegEditText);
        wednesdayNonVegText =findViewById(R.id.wednesdayNonVegEditText);
        thursdayVegText =findViewById(R.id.thursdayVegEditText);
        thursdayNonVegText =findViewById(R.id.thursdayNonVegEditText);
        fridayVegText =findViewById(R.id.fridayVegEditText);
        fridayNonVegText =findViewById(R.id.fridayNonVegEditText);
        saturdayVegText =findViewById(R.id.saturdayVegEditText);
        saturdayNonVegText =findViewById(R.id.saturdayNonVegEditText);
        sundayVegText =findViewById(R.id.sundayVegEditText);
        sundayNonVegText =findViewById(R.id.sundayNonVegEditText);

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
                try {
                    Log.d("check",response.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Toast.makeText(UpdateMenuAdminActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=response.getJSONObject("data");

                    JSONObject monJson=jsonObject.getJSONObject("monday");
                    JSONObject monDayJson=monJson.getJSONObject("day");
                    String monDayVeg=monDayJson.getString("veg");
                    String monDayNonVeg=monDayJson.getString("nonveg");
                    Log.d("check mon",monDayVeg);
                    Log.d("check mon",monDayNonVeg);

                    JSONObject tueJson=jsonObject.getJSONObject("tuesday");
                    JSONObject tueDayJson=tueJson.getJSONObject("day");
                    String tueDayVeg=tueDayJson.getString("veg");
                    String tueDayNonVeg=tueDayJson.getString("nonveg");
                    Log.d("check tue",tueDayVeg);
                    Log.d("check tue",tueDayNonVeg);


                    JSONObject wedJson=jsonObject.getJSONObject("wednesday");
                    JSONObject wedDayJson=wedJson.getJSONObject("day");
                    String wedDayVeg=wedDayJson.getString("veg");
                    String wedDayNonVeg=wedDayJson.getString("nonveg");
                    Log.d("check wed",wedDayVeg);
                    Log.d("check wed",wedDayNonVeg);


                    JSONObject thusJson=jsonObject.getJSONObject("thursday");
                    JSONObject thusDayJson=thusJson.getJSONObject("day");
                    String thusDayVeg=thusDayJson.getString("veg");
                    String thusDayNonVeg=thusDayJson.getString("nonveg");
                    Log.d("check thus",thusDayVeg);
                    Log.d("check thus",thusDayNonVeg);


                    JSONObject friJson=jsonObject.getJSONObject("friday");
                    JSONObject friDayJson=friJson.getJSONObject("day");
                    String friDayVeg=friDayJson.getString("veg");
                    String friDayNonVeg=friDayJson.getString("nonveg");
                    Log.d("check fri",friDayVeg);
                    Log.d("check fri",friDayNonVeg);


                    JSONObject satJson=jsonObject.getJSONObject("saturday");
                    JSONObject satDayJson=satJson.getJSONObject("day");
                    String satDayVeg=satDayJson.getString("veg");
                    String satDayNonVeg=satDayJson.getString("nonveg");
                    Log.d("check sat",satDayVeg);
                    Log.d("check sat",satDayNonVeg);


                    JSONObject sunJson=jsonObject.getJSONObject("sunday");
                    JSONObject sunDayJson=sunJson.getJSONObject("day");
                    String sunDayVeg=sunDayJson.getString("veg");
                    String sunDayNonVeg=sunDayJson.getString("nonveg");
                    Log.d("check sun",sunDayVeg);
                    Log.d("check sun",sunDayNonVeg);


                    mondayVegText.setText(monDayVeg);
                    mondayNonVegText.setText(monDayNonVeg);

                    tuesdayVegText.setText(tueDayVeg);
                    tuesdayNonVegText.setText(tueDayNonVeg);

                    wednesdayVegText.setText(wedDayVeg);
                    wednesdayNonVegText.setText(wedDayNonVeg);

                    thursdayVegText.setText(thusDayVeg);
                    thursdayNonVegText.setText(thusDayNonVeg);

                    fridayVegText.setText(friDayVeg);
                    fridayNonVegText.setText(friDayNonVeg);

                    saturdayVegText.setText(satDayVeg);
                    saturdayNonVegText.setText(satDayNonVeg);

                    sundayVegText.setText(sunDayVeg);
                    sundayNonVegText.setText(sunDayNonVeg);

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

                String mondayDayVegMeal="",mondayDayNonVegMeal="";
                String mondayNightVegMeal="",mondayNightNonVegMeal="";
                if(mondayText.equals("Day")){
                    mondayDayVegMeal= mondayVegText.getText().toString().trim();
                    mondayDayNonVegMeal= mondayNonVegText.getText().toString().trim();
                }else{
                    mondayNightVegMeal= mondayVegText.getText().toString().trim();
                    mondayNightNonVegMeal= mondayNonVegText.getText().toString().trim();
                }

                //Log.d("meal hai eafsd",mondayDayMeal+mondayNightMeal);
                int tuesdayId=tuesdayGroup.getCheckedRadioButtonId();
                tuesdayButton=findViewById(tuesdayId);
                String tuesdayText= (String) tuesdayButton.getText();

                String tuesdayDayVegMeal="",tuesdayDayNonVegMeal="";
                String tuesdayNightVegMeal="",tuesdayNightNonVegMeal="";
                if(tuesdayText.equals("Day")){
                    tuesdayDayVegMeal= tuesdayVegText.getText().toString().trim();
                    tuesdayDayNonVegMeal= tuesdayNonVegText.getText().toString().trim();
                }else{
                    tuesdayNightVegMeal= tuesdayVegText.getText().toString().trim();
                    tuesdayNightNonVegMeal= tuesdayNonVegText.getText().toString().trim();
                }

                int wednesdayId=wednesdayGroup.getCheckedRadioButtonId();
                wednesdayButton=findViewById(wednesdayId);
                String wednesdayText= (String) wednesdayButton.getText();

                String wednesdayDayVegMeal="",wednesdayDayNonVegMeal="";
                String wednesdayNightVegMeal="",wednesdayNightNonVegMeal="";
                if(wednesdayText.equals("Day")){
                    wednesdayDayVegMeal= wednesdayVegText.getText().toString().trim();
                    wednesdayDayNonVegMeal= wednesdayNonVegText.getText().toString().trim();
                }else{
                    wednesdayNightVegMeal= wednesdayVegText.getText().toString().trim();
                    wednesdayNightNonVegMeal= wednesdayNonVegText.getText().toString().trim();
                }

                int thursdayId=thursdayGroup.getCheckedRadioButtonId();
                thursdayButton=findViewById(thursdayId);
                String thursdayText= (String) thursdayButton.getText();

                String thursdayDayVegMeal="",thursdayDayNonVegMeal="";
                String thursdayNightVegMeal="",thursdayNightNonVegMeal="";
                if(thursdayText.equals("Day")){
                    thursdayDayVegMeal= thursdayVegText.getText().toString().trim();
                    thursdayDayNonVegMeal= thursdayNonVegText.getText().toString().trim();
                }else{
                    thursdayNightVegMeal= thursdayVegText.getText().toString().trim();
                    thursdayNightNonVegMeal= thursdayNonVegText.getText().toString().trim();
                }

                int fridayId=fridayGroup.getCheckedRadioButtonId();
                fridayButton=findViewById(fridayId);
                String fridayText= (String) fridayButton.getText();

                String fridayDayVegMeal="",fridayDayNonVegMeal="";
                String fridayNightVegMeal="",fridayNightNonVegMeal="";
                if(fridayText.equals("Day")){
                    fridayDayVegMeal= fridayVegText.getText().toString().trim();
                    fridayDayNonVegMeal= fridayNonVegText.getText().toString().trim();
                }else{
                    fridayNightVegMeal= fridayVegText.getText().toString().trim();
                    fridayNightNonVegMeal= fridayNonVegText.getText().toString().trim();
                }

                int saturdayId=saturdayGroup.getCheckedRadioButtonId();
                saturdayButton=findViewById(saturdayId);
                String saturdayText= (String) saturdayButton.getText();

                String saturdayDayVegMeal="",saturdayDayNonVegMeal="";
                String saturdayNightVegMeal="",saturdayNightNonVegMeal="";
                if(saturdayText.equals("Day")){
                    saturdayDayVegMeal= saturdayVegText.getText().toString().trim();
                    saturdayDayNonVegMeal= saturdayNonVegText.getText().toString().trim();
                }else{
                    saturdayNightVegMeal= saturdayVegText.getText().toString().trim();
                    saturdayNightNonVegMeal= saturdayNonVegText.getText().toString().trim();
                }

                int sundayId=sundayGroup.getCheckedRadioButtonId();
                sundayButton=findViewById(sundayId);
                String sundayText= (String) sundayButton.getText();

                String sundayDayVegMeal="",sundayDayNonVegMeal="";
                String sundayNightVegMeal="",sundayNightNonVegMeal="";
                if(sundayText.equals("Day")){
                    sundayDayVegMeal= sundayVegText.getText().toString().trim();
                    sundayDayNonVegMeal= sundayNonVegText.getText().toString().trim();
                }else{
                    sundayNightVegMeal= sundayVegText.getText().toString().trim();
                    sundayNightNonVegMeal= sundayNonVegText.getText().toString().trim();
                }


                JSONObject mondayDay= new JSONObject();
                try {
                    mondayDay.put("veg",mondayDayVegMeal);
                    mondayDay.put("nonveg",mondayDayNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject mondayNight= new JSONObject();
                try {
                    mondayDay.put("veg",mondayNightVegMeal);
                    mondayDay.put("nonveg",mondayNightNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject mondayJson= new JSONObject();
                try {
                    mondayJson.put("day",mondayDay);
                    mondayJson.put("night",mondayNight);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Log.d("json check monday",mondayJson.getString("day"));
                    Log.d("json check monday",mondayJson.getString("night"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONObject tuesdayDay= new JSONObject();
                try {
                    tuesdayDay.put("veg",tuesdayDayVegMeal);
                    tuesdayDay.put("nonveg",tuesdayDayNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject tuesdayNight= new JSONObject();
                try {
                    tuesdayNight.put("veg",tuesdayNightVegMeal);
                    tuesdayNight.put("nonveg",tuesdayNightNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject tuesdayJson= new JSONObject();
                try {
                    tuesdayJson.put("day",tuesdayDay);
                    tuesdayJson.put("night",tuesdayNight);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONObject wednesdayDay= new JSONObject();
                try {
                    wednesdayDay.put("veg",wednesdayDayVegMeal);
                    wednesdayDay.put("nonveg",wednesdayDayNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject wednesdayNight= new JSONObject();
                try {
                    wednesdayNight.put("veg",wednesdayNightVegMeal);
                    wednesdayNight.put("nonveg",wednesdayNightNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONObject wednesdayJson= new JSONObject();
                try {
                    wednesdayJson.put("day",wednesdayDay);
                    wednesdayJson.put("night",wednesdayNight);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONObject thursdayDay= new JSONObject();
                try {
                    thursdayDay.put("veg",thursdayDayVegMeal);
                    thursdayDay.put("nonveg",thursdayDayNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject thursdayNight= new JSONObject();
                try {
                    thursdayNight.put("veg",thursdayNightVegMeal);
                    thursdayNight.put("nonveg",thursdayNightNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject thursdayJson= new JSONObject();
                try {
                    thursdayJson.put("day",thursdayDay);
                    thursdayJson.put("night",thursdayNight);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONObject fridayDay= new JSONObject();
                try {
                    fridayDay.put("veg",fridayDayVegMeal);
                    fridayDay.put("nonveg",fridayDayNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject fridayNight= new JSONObject();
                try {
                    fridayNight.put("veg",fridayNightVegMeal);
                    fridayNight.put("nonveg",fridayNightNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject fridayJson= new JSONObject();
                try {
                    fridayJson.put("day",fridayDay);
                    fridayJson.put("night",fridayNight);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject saturdayDay= new JSONObject();
                try {
                    saturdayDay.put("veg",saturdayDayVegMeal);
                    saturdayDay.put("nonveg",saturdayDayNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject saturdayNight= new JSONObject();
                try {
                    saturdayNight.put("veg",saturdayNightVegMeal);
                    saturdayNight.put("nonveg",saturdayNightNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject saturdayJson= new JSONObject();
                try {
                    saturdayJson.put("day",saturdayDay);
                    saturdayJson.put("night",saturdayNight);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject sundayDay= new JSONObject();
                try {
                    sundayDay.put("veg",sundayDayVegMeal);
                    sundayDay.put("nonveg",sundayDayNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject sundayNight= new JSONObject();
                try {
                    sundayNight.put("veg",sundayNightVegMeal);
                    sundayNight.put("nonveg",sundayNightNonVegMeal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject sundayJson= new JSONObject();
                try {
                    sundayJson.put("day",sundayDay);
                    sundayJson.put("night",sundayNight);
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
