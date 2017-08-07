package com.example.user.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import static com.example.user.healthapp.SharedPref.HEALTH_SHARED_PREF;
import static com.example.user.healthapp.SharedPref.sharedpreferences;
public class Splash extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                sharedpreferences = getSharedPreferences(HEALTH_SHARED_PREF, 0);
                String apiResult = sharedpreferences.getString("isLoggedIn", "no");
                if (apiResult.equals("yes"))

                {
                    Intent mainIntent = new Intent(Splash.this,IconActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
                else
                {
                     /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(Splash.this,Login.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
