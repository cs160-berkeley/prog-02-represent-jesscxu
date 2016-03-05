package com.example.jessicaxu.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LocalReps extends AppCompatActivity {

    public String zipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_reps);

        Intent get_zipcode = getIntent();

        Bundle extras = get_zipcode.getExtras();
        if (extras != null) {
            zipcode = extras.getString("zipcode").toString();
            System.out.println (zipcode);
        } else {
            zipcode = "0";
        }

        initializeData();

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);



    }

    public static List<RepsData> persons;

    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.
    private void initializeData(){
        persons = new ArrayList<>();
        String message;

        if (zipcode.equals("0")) {

            persons.add(new RepsData("Sen. Bill Nelson", "http://nelson.house.gov", "senate@nelson.senate.gov","Democratic",
                    "New documents show Takata employees routinely manipulated safety testing data. Here’s the report: http://1.usa.gov/21dxL2Q.", R.drawable.nelson));
            persons.add(new RepsData("Sen. Marco Rubio", "http://rubio.senate.gov", "senate@rubio.rep.gov","Republican",
                    "Start your weekend early & attend our rally in Wichita on Friday afternoon! Don't wait. RSVP here: http://rub.io/JVmLCR  #KSforMarco", R.drawable.rubio));
            persons.add(new RepsData("Rep. Daniel Webster", "http://webster.house.gov", "rep@webster.rep.gov", "Republican",
                    "Voted to protect Medicaid from fraud & abuse by ensuring providers banned by @CMSGov can’t continue to defraud the system simply by moving", R.drawable.webster));

            message = "Sen. Bill Nelson,Sen. Marco Rubio,Rep. Daniel Webster:d,r,r:Orlando County — FL";

        } else {

            persons.add(new RepsData("Sen. Sherrod Brown", "http://brown.senate.gov", "senate@brown.senate.gov", "Democratic",
                    "Women & minorities are underrepresented in corporate boardrooms. The SEC should act on new board diversity rules.", R.drawable.brown2));
            persons.add(new RepsData("Rep. Pat Tiberi", "http://tiberi.house.gov", "rep@tiberi.rep.gov", "Republican",
                    "At all levels of government, we have no higher responsibility than to protect the dignity of life. #prolife", R.drawable.tiberi2));
            persons.add(new RepsData("Rep. Steve Stivers", "http://stivers.house.gov", "rep@stivers.rep.gov", "Republican",
                    "Big welcome to the #ColumbusPartnership - Grateful for their work to boost economic vitality in #cbus.", R.drawable.stivers2));

            message = "Sen. Sherrod Brown,Rep. Pat Tiberi,Rep. Steve Stivers:d,r,r:Franklin County — OH";

        }



        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("path", "data");
        sendIntent.putExtra("message", message);

        startService(sendIntent);

    }

    public void detailedView(View v) {
        Intent intent = new Intent(this, SpecificRep.class);
        Bundle args = new Bundle();

        RepsData person = persons.get((int) v.getTag());

        args.putString("name", person.name);
        args.putString("term", person.term);
        args.putInt("photo_id", person.photoId);
        args.putString("party", person.party);
        args.putStringArray("committees", person.committees);
        args.putStringArray("bills", person.bills);



        intent.putExtras(args);
        startActivity(intent);
    }

}
