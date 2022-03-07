package com.example.mess_management_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginAdminActivity extends AppCompatActivity {
    TextView st;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        int id=0 ;

        login=findViewById(R.id.adminLoginButton);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            id = bundle.getInt("Admin");
        }
        st=findViewById(R.id.loginText);
       textSet(id);
       Log.d("main","val id "+ id);
    }
    public void textSet(int id){
        if(id==1){
            st.setText("Login As Admin");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(LoginAdminActivity.this,MainAdminActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            st.setText("Login As Boarder");

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(LoginAdminActivity.this,MainBoarderActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}