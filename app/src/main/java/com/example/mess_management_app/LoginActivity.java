package com.example.mess_management_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button adminLogin,boarderLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        adminLogin=findViewById(R.id.adminButton);
        boarderLogin=findViewById(R.id.boarderButton);

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,LoginAdminActivity.class);
                intent.putExtra("Admin",1);
                startActivity(intent);
            }
        });

        boarderLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,LoginAdminActivity.class);
                intent.putExtra("Admin",2);
                startActivity(intent);
            }
        });
    }
}