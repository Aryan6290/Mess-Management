package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.ProgressDialog;
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
    TextView dayType,nightType,nameText,dateToday;
    String selectedTime,selectedType,userId,selectedDate,responseShow;
    CardView dayCardView,nightCardView;
    long timeInMilliseconds = 0;


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

                 addMeaL(selectedDate);
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
                                nightCardView.setVisibility(View.VISIBLE);
                            }

                            Log.d("adada",i.get_id());
                            Log.d("adada",i.getTime());
                            Log.d("adada",i.getDate());
                            Log.d("adada",i.getType());

                            if(i.getTime().equals("day")){
                                dayType.setText(i.getType());
                               // dayType.setVisibility(View.VISIBLE);
                                dayCardView.setVisibility(View.VISIBLE);
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
        String[] type= {"veg","nonveg"};
        ArrayAdapter<String> timeAdapter=new ArrayAdapter<String>(ChooseMealBoarderActivity.this,R.layout.dropdown_item,time);
        ArrayAdapter<String> typeAdapter=new ArrayAdapter<String>(ChooseMealBoarderActivity.this,R.layout.dropdown_item,type);
        typeLayout.setAdapter(typeAdapter);
        timeLayout.setAdapter(timeAdapter);


        typeLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                selectedType=item;
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

