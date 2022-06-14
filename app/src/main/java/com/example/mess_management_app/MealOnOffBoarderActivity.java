package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mess_management_app.utils.SessionManager;

public class MealOnOffBoarderActivity extends AppCompatActivity {

    SessionManager sessionManager;
    RadioGroup radioGroup;
    SharedPreferences sharedPreferences;
    AppCompatButton appCompatButton;

    RadioButton yesButton,noButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getApplicationContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_meal_on_off_boarder);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        yesButton= findViewById(R.id.yesButton);
        noButton= findViewById(R.id.NoButton);
        String s1 = sharedPreferences.getString("messValue", "");

        if(s1==null || s1.equals("")){
            noButton.setChecked(true);
        }else{
            yesButton.setChecked(true);
        }
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        sessionManager= new SessionManager(this);

        radioGroup= findViewById(R.id.radioGroupOnOff);
        appCompatButton= findViewById(R.id.saveOnOffButton);

        appCompatButton.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   onSaveClick();
                                               }
                                           }
        );

        TextView textView=findViewById(R.id.nameTextOnOff);
        textView.setText(sessionManager.getUser().getUserName());
    }
    void onSaveClick(){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton   genderradioButton = (RadioButton) findViewById(selectedId);
        if(genderradioButton.getText().toString().equals("Yes")){
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("messValue", genderradioButton.getText().toString());
            myEdit.apply();

        }else if(genderradioButton.getText().toString().equals("No")){
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.remove("messValue");
            myEdit.apply();
        }
        Log.i("clicked",String.valueOf(selectedId));

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();


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

}