package com.example.mess_management_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mess_management_app.model.DataX;
import com.example.mess_management_app.model.DataXY;
import com.example.mess_management_app.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateInfoBoarderActivity extends AppCompatActivity {

    TextView nameText,rollNoText,baseText;
    ImageView updateButton,profilePicture,profilePic;
    Uri imageUri;
    String userId;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info_boarder);
        nameText=findViewById(R.id.nameShowInfo);
        rollNoText=findViewById(R.id.rollNoShowInfo);
        updateButton=findViewById(R.id.updateImage);
        profilePicture=findViewById(R.id.profile_image);

         sessionManager=new SessionManager(UpdateInfoBoarderActivity.this);
         userId=sessionManager.getUser().get_id();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType( "image/*");
                startActivityForResult(intent,40);
            }
        });

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        showProfileDetail();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK  && requestCode==40){
            assert data != null;
            imageUri=data.getData();
            Bitmap bitmap= null;
            String image;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                // initialize byte stream
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                // compress Bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                // Initialize byte array
                byte[] bytes=stream.toByteArray();
                // get base64 encoded string
                image= Base64.encodeToString(bytes,Base64.DEFAULT);
                // set encoded text on textview
                //Log.d("bas64 Image",image);
                sessionManager.saveImage(image);
                Log.d("session manager",sessionManager.getImage());
                //baseText.setText(image);
                byte[] bytesf=Base64.decode(image,Base64.DEFAULT);
                // Initialize bitmap
                Bitmap bitmap2= BitmapFactory.decodeByteArray(bytesf,0,bytes.length);
                // set bitmap on imageView
                profilePicture.setImageBitmap(bitmap2);

              // addProfileImage(image);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void addProfileImage(String imageBase64){

        String url = "https://kgec-mess-backend.herokuapp.com/api/user/add-details";

        ProgressDialog pDialog = new ProgressDialog(UpdateInfoBoarderActivity.this);
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        Log.d("check before call",imageBase64);
        Map params = new HashMap();
        params.put("userId", userId);
        params.put("image", imageBase64);
        Log.d("check userId",userId);
        Log.d("check image",imageBase64);
        //Log.d("date",date);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("Sgsgsgsg",response.toString());

                try {
                    Toast.makeText(UpdateInfoBoarderActivity.this,response.getString("message"),Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UpdateInfoBoarderActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
            }
        });

        MySingleton.getInstance(UpdateInfoBoarderActivity.this).addToRequestQueue(jsonObjectRequest);

    }



    private void showProfileDetail() {

        String url = "https://kgec-mess-backend.herokuapp.com/api/user/get-details";

        ProgressDialog pDialog = new ProgressDialog(UpdateInfoBoarderActivity.this);
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        String image=sessionManager.getImage();
        if(image != null) {
            byte[] decodedByte = Base64.decode(image, 0);
            // Initialize bitmap
            Bitmap bitmap2= BitmapFactory.decodeByteArray(decodedByte,0,decodedByte.length);
            // set bitmap on imageView
            profilePicture.setImageBitmap(bitmap2);
        }

        Map params = new HashMap();
        params.put("userId", userId);
        Log.d("check",userId);
        //Log.d("date",date);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("Sgsgsgsg",response.toString());

                try {
                    JSONObject jsonObject=response.getJSONObject("data");
                    nameText.setText(jsonObject.getString("userName"));
                    rollNoText.setText(jsonObject.getString("rollNo"));

                    String imageString=jsonObject.getString("image");
                    Log.d("on respone base64 image",imageString);

//                    if(!imageString.isEmpty()) {
//                        byte[] bytes=Base64.decode(imageString,Base64.DEFAULT);
//                        // Initialize bitmap
//                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                        // set bitmap on imageView
//                        profilePicture.setImageBitmap(bitmap);
//                    }

                    Log.d("user info",jsonObject.getString("userName"));
                    Log.d("user info",jsonObject.getString("rollNo"));


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
                Toast.makeText(UpdateInfoBoarderActivity.this,body.substring(12,body.length()-2),Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                Log.d("satus code", String.valueOf(error.networkResponse.statusCode));
            }
        });

        MySingleton.getInstance(UpdateInfoBoarderActivity.this).addToRequestQueue(jsonObjectRequest);

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