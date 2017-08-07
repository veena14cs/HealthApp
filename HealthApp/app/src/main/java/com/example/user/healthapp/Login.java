package com.example.user.healthapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import static com.example.user.healthapp.SharedPref.HEALTH_SHARED_PREF;
import static com.example.user.healthapp.SharedPref.sharedpreferences;
/**
 * Created by user on 21/07/2017.
 */

public class Login extends AppCompatActivity
 {

             EditText usr,pass;
             Button save;
             String username,password;
             public static int person_id=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usr = (EditText) findViewById(R.id.editText5);
        pass = (EditText) findViewById(R.id.editText6);
        save = (Button) findViewById(R.id.button2);


        save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                username = usr.getText().toString();
                password = pass.getText().toString();



                if (TextUtils.isEmpty(username)) {
                    //Toast.makeText(getApplicationContext(), "Please enter Username", Toast.LENGTH_LONG).show();
                    usr.setError("Please enter Username");

                } else if (TextUtils.isEmpty(password)) {
                    //Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_LONG).show();
                    pass.setError("Please enter Password");

                }
                else
                {

                    if(validateInput()==true)
                    {

                        saveLogindata();
                        Intent i = new Intent(Login.this, IconActivity.class);
                        startActivity(i);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

             private boolean validateInput() {


                 return checkforLenth(username);
             }


             private boolean isValidMobile(String phone) {
                 boolean check=false;
                 if(!Pattern.matches("[a-zA-Z]+", phone)) {

                     check= checkforLenth(phone);

                 }
                 return check;
             }

             private boolean checkforLenth(String phone) {
                Boolean check=false;
                 Log.e("",""+phone.length());
                 if(phone.length()==10 || phone.length()==12 ) {
                     check = true;

                 }
                return check;
             }

             private void saveLogindata() {
             sharedpreferences = getSharedPreferences(HEALTH_SHARED_PREF, 0);
             SharedPreferences.Editor sEdit = sharedpreferences.edit();
             sEdit.putString("isLoggedIn", "yes");
             sEdit.commit();
             }

}
