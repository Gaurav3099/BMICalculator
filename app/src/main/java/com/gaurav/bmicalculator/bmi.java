package com.gaurav.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class bmi extends AppCompatActivity
        implements com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks{
    Spinner spnFeet, spnInch;
    Button btnBmi, btnView;
    GoogleApiClient gac;
    Location loc;
    EditText etWeight;
    SharedPreferences sp1;
    TextView tvHello, tvLocation, tvTemp;
    DBHandler DBH;



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private com.google.android.gms.common.api.GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        spnFeet = (Spinner) findViewById(R.id.spnFeet);
        spnInch = (Spinner) findViewById(R.id.spnInch);
        btnBmi = (Button) findViewById(R.id.btnBmi);
        btnView = (Button) findViewById(R.id.btnView);
        etWeight = (EditText) findViewById(R.id.etWeight);
        tvHello = (TextView) findViewById(R.id.tvHello);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        DBH = new DBHandler(this);



        Task1 t = new Task1();
        t.execute("http://api.openweathermap.org/data/2.5/weather?units=metric" + "&q=" + tvLocation + "&appid=c6e315d09197cec231495138183954bd");


        Intent i = getIntent();
        String name = i.getStringExtra("name");
        tvHello.setText("Hello " + name);


        final String size[] = {"3", "4", "5", "6", "7"};
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, size);
        spnFeet.setAdapter(a);
        final String size1[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ArrayAdapter<String> in = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, size1);
        spnInch.setAdapter(in);

        btnBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int p = spnFeet.getSelectedItemPosition();
                double number = Double.parseDouble(spnFeet.getSelectedItem().toString());
                double feet = number * 0.305;
                int m = spnInch.getSelectedItemPosition();
                double num = Double.parseDouble(spnInch.getSelectedItem().toString());
                double inch = num * 0.0254;
                double sum = feet + inch;
                double weight = Double.parseDouble(etWeight.getText().toString());
                double bmi = weight / (sum * sum);


                String res = "Your BMI is : " + bmi;
                String ht = Double.toString(sum);
                String wt = Double.toString(weight);

                Intent r = new Intent(bmi.this, result.class);

                r.putExtra("w", wt);
                r.putExtra("h", ht);

                r.putExtra("res", res);
                startActivity(r);


            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(bmi.this, Name.class);
                startActivity(k);

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new com.google.android.gms.common.api.GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        if (gac != null)
            client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        if (gac != null)
            client.disconnect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("bmi Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    class Task1 extends AsyncTask<String, Void, Double> {


        @Override
        protected Double doInBackground(String... strings) {
            String json = "", line = "";
            double temperature = 0.0;


            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                while ((line = br.readLine()) != null) {
                    json = json + line + "\n";

                }
                if (json != null) {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject info = jsonObject.getJSONObject("main");
                    temperature = info.getDouble("temp");
                }


            } catch (Exception e) {

            }

            return temperature;


        }


        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvTemp.setText("temp=" + aDouble);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Geocoder g = new Geocoder(bmi.this, Locale.ENGLISH);

        if (loc != null) {
            try {
                List<Address> la = g.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                Address addr = la.get(0);

                String msg = addr.getSubLocality();

                tvLocation.setText(msg);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Come in Open area", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

        Toast.makeText(this, "Connection Suspend", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.web) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://letsweb.in/gaurav"));
            startActivity(i);
        }
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "App made by Gaurav", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private class GoogleApiClient {
    }
}




