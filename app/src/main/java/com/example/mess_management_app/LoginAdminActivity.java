package com.example.mess_management_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LoginAdminActivity extends AppCompatActivity {
    TextView st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        int id=0 ;

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
        }else{
            st.setText("Login As Boarder");
        }
    }
}