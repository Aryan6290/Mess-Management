package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mess_management_app.adapter.MealAdapter;
import com.example.mess_management_app.model.Meals;
import com.example.mess_management_app.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainAdminActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    NavigationView navView;
    RecyclerView recyclerView;
    MealAdapter mealAdapter;
    TextView totalCountShow,totalNonVegCountShow,totalVegCountShow;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        DrawerLayout drawerLayout=findViewById(R.id.drawLayout);
        navView=findViewById(R.id.navView);
        recyclerView=findViewById(R.id.adminRecycleView);
        totalCountShow=findViewById(R.id.TotalCountShow);
        totalNonVegCountShow=findViewById(R.id.TotalNonVegCountShow);
        totalVegCountShow=findViewById(R.id.TotalVegCountShow);

        sessionManager=new SessionManager(MainAdminActivity.this);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        String type="day";

        showAllDetails(type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.addBoarder){
                    Intent intent=new Intent(MainAdminActivity.this,AddBoarderAdminActivity.class);
                    startActivity(intent);
                }

                if(item.getItemId()==R.id.updatePassword){
                    Intent intent=new Intent(MainAdminActivity.this,UpdatePasswordAdminActivity.class);
                    startActivity(intent);
                }

                if(item.getItemId()==R.id.updateMenu){
                    Intent intent=new Intent(MainAdminActivity.this,UpdateMenuAdminActivity.class);
                    startActivity(intent);
                }

                if(item.getItemId()==R.id.mealCountsDetails){
                    Intent intent=new Intent(MainAdminActivity.this,MealCountDetailAdminActivity.class);
                    startActivity(intent);
                }

                if(item.getItemId()==R.id.logoutAdmin){
                    Intent intent=new Intent(MainAdminActivity.this,LoginActivity.class);
                    sessionManager.logOut();
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }



    private void showAllDetails(String time) {

        String url = "https://kgec-mess-backend.herokuapp.com/api/meal/get/all";

        ProgressDialog pDialog = new ProgressDialog(MainAdminActivity.this);
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        SimpleDateFormat sdfe = new SimpleDateFormat("dd/MM/yy");
        String datef = sdfe.format(new Date());

        Map params = new HashMap();
        params.put("date", datef);
        params.put("time",time);

        Log.d("check",datef+time);
        //Log.d("date",date);
        final int[] count = {0};
        final int[] veg = { 0 };
        final int[] nonVeg = {0};

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("check","response");

                try {
                    JSONArray Jarray = response.getJSONArray("meals");
                    ArrayList<Meals>temp = new ArrayList();
                    for (int i=0;i<Jarray.length();i++){
                        //Adding each element of JSON array into ArrayList
                        JSONObject object = Jarray.getJSONObject(i);
                        Meals editDoctorModel = new Meals();
                        editDoctorModel.set_id(object.getString("_id"));
                        editDoctorModel.setUserId(object.getString("userId"));
                        editDoctorModel.setType(object.getString("type"));
                        editDoctorModel.setDate(object.getString("date"));
                        editDoctorModel.setTime(object.getString("time"));

                        count[0]++;
                        if(object.getString("type")=="veg"){
                            veg[0]++;
                        }else{
                            nonVeg[0]++;
                        }
                        temp.add(editDoctorModel);
                    }

                   totalCountShow.setText(String.valueOf(count[0]));
                    totalNonVegCountShow.setText(String.valueOf(nonVeg[0]));
                    totalVegCountShow.setText(String.valueOf(veg[0]));

                    Log.d("check", String.valueOf(temp.size()+nonVeg[0]+veg[0]));
                    mealAdapter=new MealAdapter(MainAdminActivity.this,temp);
                    recyclerView.setAdapter(mealAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainAdminActivity.this));


                    Log.d("check", String.valueOf(count[0]));
                    Log.d("check", String.valueOf(veg[0]));
                    Log.d("check", String.valueOf(nonVeg[0]));

                    //String day = null,night=null;


                    pDialog.dismiss();

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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainAdminActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
            }
        });
        MySingleton.getInstance(MainAdminActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_admin_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.dayTextAdmin:
                showAllDetails("day");
                return true;

            case R.id.nighTextAdmin:
                showAllDetails("night");
                return true;

            case R.id.adminDatePicker:
                return true;

        }
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}