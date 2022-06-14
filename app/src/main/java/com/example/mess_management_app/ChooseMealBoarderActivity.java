package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mess_management_app.adapter.MealAdapter;
import com.example.mess_management_app.api.RetrofitInstance;
import com.example.mess_management_app.model.DataX;
import com.example.mess_management_app.model.GetMealResponse;
import com.example.mess_management_app.utils.SessionManager;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

public class ChooseMealBoarderActivity extends AppCompatActivity {


    private TextView mDateShow;
    private MaterialDatePicker<Long> materialDatePicker;
    private SessionManager sessionManager;
    ArrayList<DataX> arrayList;
    TextView dayType,nightType,nameText,dateToday,dayText,nightText;
    String selectedTime,selectedType,userId,selectedDate,responseShow;
    CardView dayCardView,nightCardView;
    long timeInMilliseconds = 0;
    String monDayVeg,monDayNonVeg,tueDayVeg,tueDayNonVeg,wedDayVeg,wedDayNonVeg,thusDayVeg,thusDayNonVeg,friDayVeg,friDayNonVeg,satDayVeg,satDayNonVeg,sunDayVeg,sunDayNonVeg;
    String currentDay,vegDay,nonVegDay,nightVeg,daySelected,nightSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_meal_boarder);
        dayType=findViewById(R.id.dayText);
        nightType=findViewById(R.id.nightText);
        dayCardView=findViewById(R.id.dayCardView);
        nightCardView=findViewById(R.id.nightCardView);
        nameText=findViewById(R.id.nameText);
        dateToday=findViewById(R.id.dateToday);
        FloatingActionButton fButton=findViewById(R.id.floatingActionButton);
        dayText=findViewById(R.id.mealDayTextMeal);
        nightText=findViewById(R.id.mealNightTextMeal);

        getScheduleMeal();
        sessionManager=new SessionManager(ChooseMealBoarderActivity.this);
        Log.d("admin meal id",sessionManager.getUser().get_id());
        Log.d("admin meal username",sessionManager.getUser().getUserName());
        nameText.setText(sessionManager.getUser().getUserName());
        arrayList=new ArrayList();

        SimpleDateFormat sdfe = new SimpleDateFormat("dd/MM/yy");
        String datef = sdfe.format(new Date());
        showMealDetail(datef);


        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.d("check",selectedDate);
                if(selectedDate==null){
                    selectedDate=datef;
                }

                SharedPreferences    sharedPreferences=getApplicationContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
                String s1 = sharedPreferences.getString("messValue", "");
                if(s1==null || s1.equals("")) {
                    addMeaL(selectedDate);
                }else{
                    Toast.makeText(ChooseMealBoarderActivity.this, "Please turn your mess on first!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        //mDateShow=findViewById(R.id.dateDayShow);


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int date=calendar.get(Calendar.DATE);
        // previous MARCH
        Log.d("date start", String.valueOf(date));
        calendar.set(Calendar.DAY_OF_MONTH, date);
        long start = calendar.getTimeInMillis();
        Log.d("march", String.valueOf(start));

        calendar.add(Calendar.DATE,7);

        Date end=  calendar.getTime();
        Log.d("date", String.valueOf(end));


        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            Date mDate = sdf.parse(String.valueOf(end));
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli : " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("time in ms",String.valueOf(timeInMilliseconds));


        //calendar constraints
        CalendarConstraints.Builder calendarConstraintBuilder = new CalendarConstraints.Builder();
        calendarConstraintBuilder.setValidator(DateValidatorPointForward.now());


        //material date picker
        MaterialDatePicker.Builder<Long> builder=MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");
        builder.setCalendarConstraints(calendarConstraintBuilder.build());
        materialDatePicker= builder.build();
    }

    void getScheduleMeal(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        // full name form of the day
        currentDay=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        Log.d("ffff",currentDay);

        String url = "https://kgec-mess-backend.herokuapp.com/api/meal/schedule/get";

        ProgressDialog pDialog = new ProgressDialog(ChooseMealBoarderActivity.this);
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
                    Toast.makeText(ChooseMealBoarderActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=response.getJSONObject("data");

                    JSONObject monJson=jsonObject.getJSONObject("monday");
                    JSONObject monDayJson=monJson.getJSONObject("day");
                    monDayVeg=monDayJson.getString("veg");
                    monDayNonVeg=monDayJson.getString("nonveg");
                    Log.d("check mon",monDayVeg);
                    Log.d("check mon",monDayNonVeg);

                    JSONObject tueJson=jsonObject.getJSONObject("tuesday");
                    JSONObject tueDayJson=tueJson.getJSONObject("day");
                    tueDayVeg=tueDayJson.getString("veg");
                    tueDayNonVeg=tueDayJson.getString("nonveg");
                    Log.d("check tue",tueDayVeg);
                    Log.d("check tue",tueDayNonVeg);


                    JSONObject wedJson=jsonObject.getJSONObject("wednesday");
                    JSONObject wedDayJson=wedJson.getJSONObject("day");
                    wedDayVeg=wedDayJson.getString("veg");
                    wedDayNonVeg=wedDayJson.getString("nonveg");
                    Log.d("check wed",wedDayVeg);
                    Log.d("check wed",wedDayNonVeg);


                    JSONObject thusJson=jsonObject.getJSONObject("thursday");
                    JSONObject thusDayJson=thusJson.getJSONObject("day");
                    thusDayVeg=thusDayJson.getString("veg");
                    thusDayNonVeg=thusDayJson.getString("nonveg");
                    Log.d("check thus",thusDayVeg);
                    Log.d("check thus",thusDayNonVeg);


                    JSONObject friJson=jsonObject.getJSONObject("friday");
                    JSONObject friDayJson=friJson.getJSONObject("day");
                    friDayVeg=friDayJson.getString("veg");
                    friDayNonVeg=friDayJson.getString("nonveg");
                    Log.d("check fri",friDayVeg);
                    Log.d("check fri",friDayNonVeg);


                    JSONObject satJson=jsonObject.getJSONObject("saturday");
                    JSONObject satDayJson=satJson.getJSONObject("day");
                    satDayVeg=satDayJson.getString("veg");
                    satDayNonVeg=satDayJson.getString("nonveg");
                    Log.d("check sat",satDayVeg);
                    Log.d("check sat",satDayNonVeg);


                    JSONObject sunJson=jsonObject.getJSONObject("sunday");
                    JSONObject sunDayJson=sunJson.getJSONObject("day");
                    sunDayVeg=sunDayJson.getString("veg");
                    sunDayNonVeg=sunDayJson.getString("nonveg");
                    Log.d("check sun",sunDayVeg);
                    Log.d("check sun",sunDayNonVeg);


                    if(currentDay.equals("Monday")){
                        vegDay=monDayVeg;
                        nonVegDay=monDayNonVeg;
                    }
                    else if(currentDay.equals("Tuesday")){
                        vegDay=tueDayVeg;
                        nonVegDay=tueDayNonVeg;
                    }else if(currentDay.equals("Wednesday")){
                        vegDay=wedDayVeg;
                        nonVegDay=wedDayNonVeg;
                    }else if(currentDay.equals("Thursday")){
                        vegDay=thusDayVeg;
                        nonVegDay=thusDayNonVeg;
                    }else if (currentDay.equals("Friday")){
                        vegDay=friDayVeg;
                        nonVegDay=friDayNonVeg;
                    }
                    else if(currentDay.equals("Saturday")){
                        vegDay=satDayVeg;
                        nonVegDay=satDayNonVeg;
                    }else{
                        vegDay=sunDayVeg;
                        nonVegDay=sunDayNonVeg;
                    }
                    // Log.d("ffff",monDayVeg+monDayNonVeg);
                    Log.d("ffff",vegDay+nonVegDay);


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
                Toast.makeText(ChooseMealBoarderActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
            }
        });
        MySingleton.getInstance(ChooseMealBoarderActivity.this).addToRequestQueue(jsonObjectRequest);

        Log.d("ffff outside",vegDay+nonVegDay);


    }


    private void showMealDetail(String date)  {
        userId=sessionManager.getUser().get_id();
        dayCardView.setVisibility(View.GONE);
        nightCardView.setVisibility(View.GONE);

        String url = "https://kgec-mess-backend.herokuapp.com/api/meal/get";

        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        Map params = new HashMap();
        params.put("userId", userId);
        params.put("date", date);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(params), response -> {
            Log.d("response",response.toString());
            try {
                JSONArray Jarray = response.getJSONArray("meals");
                ArrayList<DataX>temp = new ArrayList();
                for (int i=0;i<Jarray.length();i++){
                    //Adding each element of JSON array into ArrayList
                    JSONObject object = Jarray.getJSONObject(i);
                    DataX editDoctorModel = new DataX();
                    editDoctorModel.set_id(object.getString("_id"));
                    editDoctorModel.setUserId(object.getString("userId"));
                    editDoctorModel.setType(object.getString("type"));
                    editDoctorModel.setDate(object.getString("date"));
                    editDoctorModel.setTime(object.getString("time"));
                    temp.add(editDoctorModel);
                }

                //String day = null,night=null;
                Log.d("aaaafdfffd",temp.toString());
                for(DataX i:temp){

                    if(i.getTime().equals("night")){
                        nightType.setText(i.getType());
                        //  nightType.setVisibility(View.VISIBLE);
                        if(i.getType().equals("veg")) {
                            nightText.setText(vegDay);
                        }else{
                            nightText.setText(nonVegDay);
                        }
                        nightCardView.setVisibility(View.VISIBLE);

                       // Log.d("ffff night",nightSelected);
                    }

                    Log.d("adada",i.get_id());
                    Log.d("adada",i.getTime());
                    Log.d("adada",i.getDate());
                    Log.d("adada",i.getType());
                    Log.d("adada veg",vegDay);

                    if(i.getTime().equals("day")){
                        dayType.setText(i.getType());
                        if(i.getType().equals("veg")) {
                            dayText.setText(vegDay);
                        }else{
                            dayText.setText(nonVegDay);
                        }
                        // dayType.setVisibility(View.VISIBLE);
                        dayCardView.setVisibility(View.VISIBLE);
                     //   Log.d("ffff day",daySelected);
                    }
                }
                pDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }
        );

        MySingleton.getInstance(ChooseMealBoarderActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.datePicker:
                showCalendar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.choose_date,menu);
        return super.onCreateOptionsMenu(menu);
    }

    void showCalendar(){
        materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Long selection) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                selectedDate = sdf.format(selection);
                if(selectedDate!=null){
                    dateToday.setText(selectedDate);
                }
                showMealDetail(selectedDate);
            }
        });
    }

    void addMeaL(String date){
        LayoutInflater layoutInflater=LayoutInflater.from(ChooseMealBoarderActivity.this);
        View view=layoutInflater.inflate(R.layout.dialog,null);


        AlertDialog alertDialog=new AlertDialog.Builder(ChooseMealBoarderActivity.this)
                .setView(view)
                .create();
        //alertDialog.show();

        AutoCompleteTextView typeLayout,timeLayout;
        Button saveButton;

        saveButton=view.findViewById(R.id.savedButton);
        typeLayout=view.findViewById(R.id.autoCompleteTextViewType);
        timeLayout=view.findViewById(R.id.autoCompleteTextViewTime);
        String[] time={"day","night"};
        String[] type= {"veg"+" ("+vegDay+")","nonveg"+" ("+nonVegDay+")"};
        ArrayAdapter<String> timeAdapter=new ArrayAdapter<String>(ChooseMealBoarderActivity.this,R.layout.dropdown_item,time);
        ArrayAdapter<String> typeAdapter=new ArrayAdapter<String>(ChooseMealBoarderActivity.this,R.layout.dropdown_item,type);
        typeLayout.setAdapter(typeAdapter);
        timeLayout.setAdapter(timeAdapter);


        typeLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                if(item.substring(0,3).equals("veg")){
                    selectedType="veg";
                }else {
                    selectedType = "nonveg";
                }
            }
        });

        timeLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                selectedTime=item;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("gggg",vegDay+nonVegDay);
                if(selectedTime.equals("day") && selectedType.equals("veg")){
                    daySelected=vegDay;
                }else if(selectedTime.equals("day") && selectedType.equals("nonveg")){
                    daySelected=nonVegDay;
                }else if(selectedTime.equals("night") && selectedType.equals("veg")){
                    nightSelected=vegDay;
                }else if(selectedTime.equals("night") && selectedType.equals("nonveg")){
                    nightSelected=nonVegDay;
                }

                Log.d("chosee","saved button"+daySelected);
                Log.d("chosee","saved button"+ nightSelected);

                Log.d("chosee","saved button");
                String url = "https://kgec-mess-backend.herokuapp.com/api/meal/add";

                ProgressDialog pDialog = new ProgressDialog(ChooseMealBoarderActivity.this);
                pDialog.setMessage("Loading...PLease wait");
                pDialog.show();


                Map params = new HashMap();
                params.put("userId", userId);
                params.put("type", selectedType);
                params.put("time",selectedTime);
                params.put("date",date);
                Log.d("check",userId+selectedType+selectedTime+date);
                //Log.d("date",date);

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                        Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //responseShow=response.getString("message");
                            Toast.makeText(ChooseMealBoarderActivity.this,response.getString("message"),Toast.LENGTH_LONG).show();
                            Log.d("meal add response",response.getString("message"));
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
                        Toast.makeText(ChooseMealBoarderActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                        Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
                    }
                });
                MySingleton.getInstance(ChooseMealBoarderActivity.this).addToRequestQueue(jsonObjectRequest);
                pDialog.dismiss();

                //Toast.makeText(ChooseMealBoarderActivity.this,"Hiii adsess",Toast.LENGTH_LONG).show();

                //Toast.makeText(ChooseMealBoarderActivity.this,responseShow,Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showMealDetail(selectedDate);
                    }
                }, 500);

            }

        });
        alertDialog.show();
    }
}


