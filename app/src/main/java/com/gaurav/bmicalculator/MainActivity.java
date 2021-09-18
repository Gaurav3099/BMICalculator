package com.gaurav.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {
    EditText etName, etAge, etPhone;
    RadioButton rbMale, rbFemale;
    Button btnRegister;
    SharedPreferences sp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etPhone = (EditText) findViewById(R.id.etPhone);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        btnRegister = (Button) findViewById(R.id.btnRegister);

 btnRegister.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {


         sp1 = getSharedPreferences("n", MODE_PRIVATE);


         if (etName.length() == 0){
             etName.setError("Name is empty");
             etName.requestFocus();
             return;
         }
         if (etAge.length() == 0){
             etAge.setError("Age is empty");
             etAge.requestFocus();
             return;
         }
         if (etPhone.length() == 0){
             etPhone.setError("Phone number is empty");
             etPhone.requestFocus();
             return;
         }
         String name =  etName.getText().toString();
         String age = etAge.getText().toString();
         String phone = etPhone.getText().toString();


         SharedPreferences.Editor editor = sp1.edit();
         editor.putString("n", name);
         editor.putString("a",age);
         editor.putString("p",phone);
         editor.commit();
         Intent i= new Intent(MainActivity.this,bmi.class);
         i.putExtra("name",name);
         startActivity(i);
         finish();
     }



 });


            }

    }

