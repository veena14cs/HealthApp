package com.example.user.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
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
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText name,mobile,email,address,mname,lname;
    String names,phone,emails,addr,middle,last;
    Button btn;
    InputStream is =null;
    String result = null;
    String line = null;
    int code;
    int person_group_id=3;
    boolean isEmailVal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        name = (EditText) findViewById(R.id.editText);
        email = (EditText) findViewById(R.id.editText3);
        mobile = (EditText) findViewById(R.id.editText2);
        address = (EditText) findViewById(R.id.editText4);
        mname = (EditText) findViewById(R.id.editText8);
        lname = (EditText) findViewById(R.id.editText12);
        btn = (Button) findViewById(R.id.button6);


        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                names = name.getText().toString();
                middle = mname.getText().toString();
                last = lname.getText().toString();
                phone = mobile.getText().toString();
                emails = email.getText().toString();
                addr = address.getText().toString();



                isEmailVal = isEmailValid(emails);


                if (TextUtils.isEmpty(names)) {
                    Toast.makeText(getApplicationContext(), "Please enter First Name", Toast.LENGTH_LONG).show();
                    name.setError("Please enter First Name.");

                }
                else if (TextUtils.isEmpty(middle)) {
                    Toast.makeText(getApplicationContext(), "Please enter Middle Name", Toast.LENGTH_LONG).show();
                    mname.setError("Enter proper Middle Name.");

                }
                else if (TextUtils.isEmpty(last)) {
                    Toast.makeText(getApplicationContext(), "Please enter Last Name", Toast.LENGTH_LONG).show();
                    lname.setError("Enter proper Last Name.");

                }
                else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Please enter Mobile no", Toast.LENGTH_LONG).show();
                    mobile.setError("Enter proper Contact Number.");

                }
                else if (!isEmailVal) {
                    Toast.makeText(getApplicationContext(), "Please enter proper E-mail address.", Toast.LENGTH_LONG).show();
                    email.setError("Enter proper E-mail address.");
                    return;
                } else if (TextUtils.isEmpty(addr)) {
                    Toast.makeText(getApplicationContext(), "Please enter  Address", Toast.LENGTH_LONG).show();
                    address.setError("Please enter  Address.");

                }


                else {
                    saveUserDetails();

                }

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
            Intent i = new Intent(MainActivity.this, IconActivity.class);
            startActivity(i);
        } else if (id == R.id.doc) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);

        } else if (id == R.id.user) {
            Intent i = new Intent(MainActivity.this, Documents.class);
            startActivity(i);

        }
        else if (id == R.id.plus) {
            Intent i = new Intent(MainActivity.this, CustomFileExplorerDialogActivity.class);
            startActivity(i);

        }
        else if (id == R.id.rep) {
            Intent i = new Intent(MainActivity.this, ViewReports.class);
            startActivity(i);

        }
        else if (id == R.id.query) {
            Intent i = new Intent(MainActivity.this, Reports.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void saveUserDetails()
    {
        runOnUiThread(new Runnable() {


            @Override
            public void run() {


                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("firstname",names));
                nameValuePairs.add(new BasicNameValuePair("middlename",middle));
                nameValuePairs.add(new BasicNameValuePair("lastname",last));
                nameValuePairs.add(new BasicNameValuePair("mobileno",phone));
                nameValuePairs.add(new BasicNameValuePair("emailid",emails));
                nameValuePairs.add(new BasicNameValuePair("address",addr));




                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://acehealthcare.co.in/save_doctor_details.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    Log.i("nameValuePairs", String.valueOf(nameValuePairs));
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


    public static boolean isEmailValid(String email)
    {
        boolean isValid = false;

        String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
