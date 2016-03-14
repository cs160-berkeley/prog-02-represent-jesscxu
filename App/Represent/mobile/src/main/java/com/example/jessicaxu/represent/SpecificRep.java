package com.example.jessicaxu.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SpecificRep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_rep);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();

        String name = args.getString("name");

        RepsData person = null;
        List<RepsData> persons = LocalReps.persons;
        for (RepsData pooper : persons) {
            if (pooper.name.equals(name)) {
                person = pooper;
            }
        }


        String party = person.party;
        String term = person.term;
        String[] bills = person.bills;
        String[] committees = person.committees;
        String title = person.title;

        TextView term_text = (TextView) findViewById(R.id.specific_term);
        term_text.setText(term);

        TextView party_text = (TextView) findViewById(R.id.party);
        party = party + " Party";
        party_text.setText(party);

        if (party.equals("Democratic Party")) {
            party_text.setTextColor(getResources().getColor(R.color.dem));
        }
        if (party.equals("Republican Party")) {
            party_text.setTextColor(getResources().getColor(R.color.rep));
        }
        if (party.equals("Indep Party")) {
            party_text.setTextColor(getResources().getColor(R.color.indep));
        }


        TextView title_text = (TextView) findViewById(R.id.title);
        title_text.setText(title);


        TextView name_text = (TextView) findViewById(R.id.specific_name);
        name_text.setText(name);

        ImageView photo_image = (ImageView) findViewById(R.id.specific_person_photo);
        photo_image.setImageBitmap(person.image);

        LinearLayout committees_layout = (LinearLayout) findViewById(R.id.committees);
        LinearLayout bills_layout = (LinearLayout) findViewById(R.id.bills);

        for (String committee : committees) {
            if (!committee.equals("null")) {
                TextView tv = new TextView(this);
                tv.setText(committee);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tv.setPadding(0,0,0,55);
                committees_layout.addView(tv);
            }
        }
        for (String bill : bills) {
            if (!bill.equals("null")) {
                TextView tv = new TextView(this);
                tv.setText(bill);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tv.setPadding(0, 0, 0, 55);
                bills_layout.addView(tv);
            }
        }
    }
}
