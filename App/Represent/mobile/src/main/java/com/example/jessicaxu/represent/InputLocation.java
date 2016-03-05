package com.example.jessicaxu.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InputLocation extends AppCompatActivity {

    public String zipcode;
    EditText zipcode_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_location);

        zipcode_num = (EditText) findViewById(R.id.zipcode);


    }

    public void clickToActivityLocalReps (View view) {
        Intent get_zipcode = new Intent(this, LocalReps.class);

        String poop = "" + zipcode_num.getText().toString();
        get_zipcode.putExtra("zipcode", poop);
        startActivity(get_zipcode);
    }

    public void clickToActivityLocalRepsCurrLoc (View view) {
        Intent get_zipcode = new Intent(this, LocalReps.class);

        String poop = "0";
        get_zipcode.putExtra("zipcode", poop);
        startActivity(get_zipcode);
    }
}
