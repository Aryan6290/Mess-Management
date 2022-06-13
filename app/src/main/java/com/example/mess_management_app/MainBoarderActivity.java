package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mess_management_app.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;

public class MainBoarderActivity extends AppCompatActivity {
    ActionBarDrawerToggle toggle;
    NavigationView navView;
    SessionManager sessionManager;
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_boarder);

        sessionManager=new SessionManager(MainBoarderActivity.this);

        DrawerLayout drawerLayout=findViewById(R.id.drawLayoutBoarder);
        navView=findViewById(R.id.navViewBoarder);


        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();






        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.updateInfo){
                    Intent intent=new Intent(MainBoarderActivity.this,UpdateInfoBoarderActivity.class);
                    startActivity(intent);

                }

                if(item.getItemId()==R.id.updatePasswordBoarder){
                    Intent intent=new Intent(MainBoarderActivity.this,UpdatePasswordBoarderActivity.class);
                    startActivity(intent);
                }

                if(item.getItemId()==R.id.chooseMeal){
                    Intent intent=new Intent(MainBoarderActivity.this,ChooseMealBoarderActivity.class);
                    startActivity(intent);
                }


                if(item.getItemId()==R.id.logoutBoarder){
                    exitDialog();
                }

                if(item.getItemId()==R.id.onOffMeal){
                    Intent intent=new Intent(MainBoarderActivity.this,MealOnOffBoarderActivity.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void exitDialog(){
        new AlertDialog.Builder(MainBoarderActivity.this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(MainBoarderActivity.this,LoginActivity.class);
                        sessionManager.logOut();
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}