package com.example.user.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import static com.example.user.healthapp.SharedPref.HEALTH_SHARED_PREF;
import static com.example.user.healthapp.SharedPref.sharedpreferences;

public class IconActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView b1,b2,b3,b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b1 = (ImageView)findViewById(R.id.i1);
        b2 = (ImageView)findViewById(R.id.i2);
        b3 = (ImageView)findViewById(R.id.i3);
        b4 = (ImageView)findViewById(R.id.i5);
        b4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(IconActivity.this, ViewReports.class);
                startActivity(i);

            }

        });
        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(IconActivity.this, MainActivity.class);
                startActivity(i);

            }

        }); b2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(IconActivity.this, Documents.class);
                startActivity(i);

            }

        });
        b3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(IconActivity.this, CustomFileExplorerDialogActivity.class);
                startActivity(i);

            }

        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
            Intent i = new Intent(IconActivity.this, IconActivity.class);
            startActivity(i);
        } else if (id == R.id.doc) {
            Intent i = new Intent(IconActivity.this, MainActivity.class);
            startActivity(i);

        } else if (id == R.id.user) {
            Intent i = new Intent(IconActivity.this, Documents.class);
            startActivity(i);

        }
        else if (id == R.id.plus) {
            Intent i = new Intent(IconActivity.this, CustomFileExplorerDialogActivity.class);
            startActivity(i);

        }
        else if (id == R.id.rep) {
            Intent i = new Intent(IconActivity.this, ViewReports.class);
            startActivity(i);

        }
        else if (id == R.id.query) {
            Intent i = new Intent(IconActivity.this, Reports.class);
            startActivity(i);

        }
        else if (id == R.id.logout) {
            Intent i = new Intent(IconActivity.this, Reports.class);
            startActivity(i);


            sharedpreferences = getSharedPreferences(HEALTH_SHARED_PREF, 0);
            sharedpreferences.edit().clear().apply();
            sharedpreferences=null;
            System.gc();
           Intent mainIntent = new Intent(IconActivity.this,Login.class);
            startActivity(mainIntent);
//            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
            finish();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
