package com.example.jessicaxu.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SpecificRep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_rep);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();

        String name = args.getString("name");
        String party = args.getString("party");
        String term = args.getString("term");
        int photo = args.getInt("photo_id");
        String[] bills = args.getStringArray("bills");
        String[] committees = args.getStringArray("committees");

        TextView term_text = (TextView) findViewById(R.id.specific_term);
        term_text.setText(term);

        TextView party_text = (TextView) findViewById(R.id.party);
        party_text.setText(party);

        TextView name_text = (TextView) findViewById(R.id.specific_name);
        name_text.setText(name);

        ImageView photo_image = (ImageView) findViewById(R.id.specific_person_photo);
        photo_image.setImageResource(photo);

        ArrayAdapter<String> comm_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, committees);
        ListView comm_listView = (ListView) findViewById(R.id.committees);
        comm_listView.setAdapter(comm_adapter);

        ArrayAdapter<String> bills_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bills);
        ListView bills_listView = (ListView) findViewById(R.id.bills);
        bills_listView.setAdapter(bills_adapter);

    }
}
