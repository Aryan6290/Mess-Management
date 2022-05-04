package com.example.mess_management_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mess_management_app.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager=new SessionManager(SplashActivity.this);

        getSupportActionBar().hide();
        ImageView logo=findViewById(R.id.logo);
        TextView madeByText=findViewById(R.id.madeByText);
        logo.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.middle_animation));
        madeByText.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom_animation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.isLogged()){
                    if(sessionManager.getTypeSessionManager().equals("admin")){
                        Intent loginIntent = new Intent(SplashActivity.this, MainAdminActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }else{
                        Intent loginIntent = new Intent(SplashActivity.this, MainBoarderActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                }else {
                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        },3000);
    }
}