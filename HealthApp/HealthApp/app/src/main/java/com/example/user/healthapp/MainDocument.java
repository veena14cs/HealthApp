package com.example.user.healthapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.user.healthapp.AdapterClasses.Doc_List_Adapter;
import com.example.user.healthapp.DataClasses.Document;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainDocument extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imageView,closeImage;
    Button share;
    InputStream is =null;
    String result = null;
    String line = null;
    int code;
    String doc_name,doc_id;
    ListView docsList;
    RelativeLayout container;
    Doc_List_Adapter adapter;
    ArrayList<Document> productList = new ArrayList<>();
    public static ArrayList<String> documentstoSend=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        imageView = (ImageView) findViewById(R.id.viewImage);
        closeImage = (ImageView) findViewById(R.id.closeImage);
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                docsList.setVisibility(View.VISIBLE);
            }
        });
        share = (Button) findViewById(R.id.share);
        docsList=(ListView)findViewById(R.id.docsList);
        documentstoSend.clear();
        container=(RelativeLayout)findViewById(R.id.container);
        view_details();

        initListners();
//        try {



//            URL url = new URL("http://acehealthcare.co.in/uploads/"+doc_name);
////            Log.i("Shruti",doc_name.toString());
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            Log.e("Bitmap","returned");
//            imageView.setImageBitmap(myBitmap);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("Exception",e.getMessage());
//
//        }


         share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bitmap icon = ((BitmapDrawable)imageView.getDrawable()).getBitmap();;
                Intent share = new Intent(Intent.ACTION_SEND);

                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
                startActivity(Intent.createChooser(share, "Share Image"));




//                Uri bmpUri = getLocalBitmapUri(imageView);
//                if (bmpUri != null) {
//                    // Construct a ShareIntent with link to image
//                    Intent shareIntent = new Intent();
//                    shareIntent.setAction(Intent.ACTION_SEND);
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//                    shareIntent.setType("image/*");
//                    // Launch sharing dialog for image
//                    startActivity(Intent.createChooser(shareIntent, "Share Image"));
//                } else {
//                    // ...sharing failed, handle error
//                }
            }
        });



        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void shareFiles() {

//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");

//        intent.setType("image/*"); //any kind of images can support.
        Log.e("docs arrat=",""+documentstoSend);
        ArrayList<String> filesToSend=new ArrayList<>();

        ArrayList<Uri> files = new ArrayList<Uri>();

        for(String path : documentstoSend /* List of the files you want to send */) {
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            files.add(uri);
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("*/*");
        //        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        // new code
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Files"));

    }

    private void initListners() {
        docsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("pos", "" + position);

                    String SelectedPic = productList.get(position).getUrl();
                    if (!SelectedPic.isEmpty()) {
                        Picasso.with(MainDocument.this).load(SelectedPic).placeholder(R.drawable.loadingimg)
                                .into(imageView);
                        container.setVisibility(View.VISIBLE);
                        docsList.setVisibility(View.GONE);
                    } else {
                        imageView.setImageResource(R.drawable.no_pic_placeholder_with_border);
                    }

                }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareFiles();
            }
        });
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
            Intent i = new Intent(MainDocument.this, IconActivity.class);
            startActivity(i);
        } else if (id == R.id.doc) {
            Intent i = new Intent(MainDocument.this, MainActivity.class);
            startActivity(i);

        } else if (id == R.id.user) {
            Intent i = new Intent(MainDocument.this, Documents.class);
            startActivity(i);

        }
        else if (id == R.id.plus) {
            Intent i = new Intent(MainDocument.this, CustomFileExplorerDialogActivity.class);
            startActivity(i);

        }
        else if (id == R.id.rep) {
            Intent i = new Intent(MainDocument.this, ViewReports.class);
            startActivity(i);

        }
        else if (id == R.id.query) {
            Intent i = new Intent(MainDocument.this, Reports.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".jpg");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    public void view_details()
    {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    HttpClient httpClient = new DefaultHttpClient();

                    //HttpPost httpPost = new HttpPost("http://darshanevents.in/gadgetexpo/chklist.php");
//                    HttpPost httpPost = new HttpPost("http://acehealthcare.co.in/view_details.php");
                    HttpPost httpPost = new HttpPost("http://acehealthcare.co.in/view_details.php");


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

                String docno="",docURL="";
                JSONArray jArray;
                try {

                    JSONObject json_data = new JSONObject(new String(result));
                    jArray = json_data.getJSONArray("result");

                    Log.e("result",""+jArray);
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);

                        docno = json_data.getString("doc_id");
                        docURL  = json_data.getString("doc_name");
                        Log.e("docno",""+docno);
                        Log.e("docname",""+docURL);

                        Document obj = new Document();

                        obj.setDoc_no(docno);
                        obj.setUrl(docURL);

                        productList.add(obj);

                    }
                } catch (Exception e1) {
                    //Toast.makeText(getBaseContext(), "No data Found", Toast.LENGTH_LONG)
                    Log.e("fail 3", e1.getMessage());
                }

                adapter = new Doc_List_Adapter(MainDocument.this, productList);
                docsList.setAdapter(adapter);

            }

        });



    }

}
