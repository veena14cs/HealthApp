package com.example.user.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Documents extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    InputStream is =null;
    String result = null;
    String line = null;
    int code;
    Button btn;
    RadioButton r1,r2;
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    RadioGroup mOption;
    String fname,mname,lname,mobile,email,addr,blood_gp,height,occupation,income,dob,gender,group_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn = (Button) findViewById(R.id.button7);
        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        e3 = (EditText) findViewById(R.id.editText3);
        e4 = (EditText) findViewById(R.id.editText4);
        e5 = (EditText) findViewById(R.id.editText6);
        e6 = (EditText) findViewById(R.id.editText7);
        e7 = (EditText) findViewById(R.id.editText9);
        e8 = (EditText) findViewById(R.id.editText11);
        e9 = (EditText) findViewById(R.id.editText10);
         mOption = (RadioGroup) findViewById(R.id.radioSex);
        r1 = (RadioButton) findViewById(R.id.radioMale);
        r2 = (RadioButton) findViewById(R.id.radioFemale);

        e1.setEnabled(false);
        e5.setEnabled(false);
        mOption.setEnabled(false);
        r1.setEnabled(false);
        r2.setEnabled(false);


        patient_details();


        btn.setOnClickListener(new View.OnClickListener() {

                                   public void onClick(View v) {
                                       // TODO Auto-generated method stub
                                       edit_patient();


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
            Intent i = new Intent(Documents.this, IconActivity.class);
            startActivity(i);
        } else if (id == R.id.doc) {
            Intent i = new Intent(Documents.this, MainActivity.class);
            startActivity(i);

        } else if (id == R.id.user) {
            Intent i = new Intent(Documents.this, Documents.class);
            startActivity(i);

        }
        else if (id == R.id.plus) {
            Intent i = new Intent(Documents.this, CustomFileExplorerDialogActivity.class);
            startActivity(i);

        }
        else if (id == R.id.rep) {
            Intent i = new Intent(Documents.this, ViewReports.class);
            startActivity(i);

        }
        else if (id == R.id.query) {
            Intent i = new Intent(Documents.this, Reports.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void patient_details()
    {




        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    HttpClient httpClient = new DefaultHttpClient();

                    //HttpPost httpPost = new HttpPost("http://darshanevents.in/gadgetexpo/chklist.php");
                    HttpPost httpPost = new HttpPost("http://acehealthcare.co.in/patient_details.php");


                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Log.e("pass 1", "connection success");

                } catch (Exception e) {
                    Log.e("fail 1", e.toString());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    is.close();
                    result = sb.toString();
                } catch (Exception e) {
                    Log.e("fail 2", e.toString());
                }


                JSONArray jArray;
                try {

                    JSONObject json_data = new JSONObject(new String(result));
                    jArray = json_data.getJSONArray("result");

                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);

                        fname = json_data.getString("firstname");
                        mname = json_data.getString("middlename");
                        lname = json_data.getString("lastname");
                        mobile = json_data.getString("mobileno");
                        email = json_data.getString("emailid");
                        addr = json_data.getString("address");
                        dob = json_data.getString("dob");
                        gender = json_data.getString("gender");
                        blood_gp = json_data.getString("blood_group");
                        height = json_data.getString("pheight");
                        occupation = json_data.getString("occupation");
                        income = json_data.getString("annual_income");
                        group_id = json_data.getString("person_id");


                        Log.i("group_id",group_id);

                        if(gender.equals("1"))
                        {

                            mOption.check(R.id.radioMale);
                        }
                        if(gender.equals("2"))
                        {

                            mOption.check(R.id.radioFemale);
                        }


                        e1.setText(fname+" "+mname+" "+lname);
                        e2.setText(mobile);
                        e3.setText(email);
                        e4.setText(addr);
                        e5.setText(dob);
                        e6.setText(blood_gp);
                        e7.setText(height);
                        e8.setText(occupation);
                        e9.setText(income);






                    }




                    //LocationAdapter adapter = new LocationAdapter(Loc_List.this, array);
                    //listView.setAdapter(adapter);


                } catch (Exception e1) {
                    //Toast.makeText(getBaseContext(), "No data Found", Toast.LENGTH_LONG)
                    Log.e("fail 3", e1.getMessage());
                }


            }

        });



    }


    public void edit_patient()
    {
        runOnUiThread(new Runnable() {


            @Override
            public void run() {


                String mobile = e2.getText().toString();
                String email = e3.getText().toString();
                String addr = e4.getText().toString();

                String blood_gp = e6.getText().toString();
                String height = e7.getText().toString();
                String occupation = e8.getText().toString();
                String income = e9.getText().toString();






                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("person_id",String.valueOf(Login.person_id)));
                nameValuePairs.add(new BasicNameValuePair("mobileno",mobile));
                nameValuePairs.add(new BasicNameValuePair("emailid",email));
                nameValuePairs.add(new BasicNameValuePair("address",addr));
                nameValuePairs.add(new BasicNameValuePair("blood_group",blood_gp));
                nameValuePairs.add(new BasicNameValuePair("pheight",height));
                nameValuePairs.add(new BasicNameValuePair("occupation",occupation));
                nameValuePairs.add(new BasicNameValuePair("annual_income",income));










                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://acehealthcare.co.in/edit_patient.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    Log.i("nameValuePairs", String.valueOf(nameValuePairs));
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Log.e("pass 1", "connection success");

                } catch (Exception e) {
                    Log.e("fail 1", e.toString());
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    is.close();
                    result = sb.toString();
                    Log.e("pass 2", "connection success");
                } catch (Exception e) {
                    Log.e("fail 2", e.toString());
                }
                try {
                    JSONObject json_data = new JSONObject(result);
                    code = (json_data.getInt("code"));

                    if (code == 1) {
                        Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Sorry,Try again", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("fall 3", e.toString());
                    Log.i("tagconvertstr", "[" + result + "]");
                }

            }

        });
    }




}
