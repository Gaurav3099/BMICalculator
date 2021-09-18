package com.gaurav.bmicalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Name extends AppCompatActivity {
    TextView tvHistory;
    DBHandler DBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        tvHistory= (TextView) findViewById(R.id.tvHistory);
        DBH=new DBHandler(this);

        tvHistory.setText(DBH.viewBMI());


    }
}
