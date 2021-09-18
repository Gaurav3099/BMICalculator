package com.gaurav.bmicalculator;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class result extends AppCompatActivity {
    TextView tvBmi, tvUW, tvNW, tvOW, tvOB;;
    Button btnBack, btnShare, btnSave;
    DBHandler DBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvBmi = (TextView) findViewById(R.id.tvBmi);
        tvUW = (TextView) findViewById(R.id.tvUW);
        tvNW = (TextView) findViewById(R.id.tvNW);
        tvOW = (TextView) findViewById(R.id.tvOW);
        tvOB = (TextView) findViewById(R.id.tvOB);
        DBH = new DBHandler(this);


        btnBack = (Button) findViewById(R.id.btnBack);
        btnShare=(Button)findViewById(R.id.btnShare);
        btnSave=(Button)findViewById(R.id.btnSave);


        Intent r = getIntent();
        final String res = r.getStringExtra("res");
        tvBmi.setText(res);
        String weight=r.getStringExtra("w");
        String hm =r.getStringExtra("h");
        Double weight1=Double.parseDouble(weight);
        Double hm1=Double.parseDouble(hm);
        Double BMI= weight1/(hm1*hm1);
        final String BMI1=String.format("%.2f",BMI);
        String str = null;

        Date d=new Date();
        final String date= String.valueOf(d);



        String Underweight = "Below 18.5 is Underweight";
        String Normal = "Between 18.5 to 25 is Normal";
        String Overweight = "Between 25 to 30 is Overweight ";
        String Obese = "More Than 30 is Obese";


        tvUW.setText(Underweight);
        tvNW.setText(Normal);
        tvOW.setText(Overweight);
        tvOB.setText(Obese);


        if (BMI < 18.5) {
            tvBmi.setText("Your BMI is " + BMI1 + " and You Are Underweight");
            tvUW.setTextColor(Color.RED);
            str = "You Are Underweight";
        }
        if (BMI > 18.5 && BMI < 25) {
            tvBmi.setText("Your BMI is " + BMI1 + " and You Are Normal");
            tvNW.setTextColor(Color.RED);
            str = "You Are Normal";
        }
        if (BMI > 25 && BMI < 30) {
            tvBmi.setText("Your BMI is " + BMI1 + " and You Are Overweight");
            tvOW.setTextColor(Color.RED);
            str = "You Are Overweight";
        }
        if (BMI > 30) {
            tvBmi.setText("Your BMI is " + BMI1 + " and You Are Obese");
            tvOB.setTextColor(Color.RED);
            str = "You Are Obese";
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               DBH.addBMI(date,BMI1);

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(Intent.ACTION_SENDTO);
                s.setData(Uri.parse("mailto:"+ "gaurav.sarkar830@gmail.com"));
                s.putExtra(Intent.EXTRA_TEXT,"" + res);
                startActivity(s);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}
