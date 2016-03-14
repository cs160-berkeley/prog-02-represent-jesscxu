package com.example.jessicaxu.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;

import java.util.ArrayList;

public class DisplayRepresentative extends Activity implements SensorEventListener {

    public ArrayList<String> repsNames;
    public ArrayList<Integer> repsParty;
    public String location;
    public String obama;
    public String romney;
    private float last_x = 0;
    private float last_y = 0;
    private float last_z = 0;
    private long lastUpdate;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    SampleGridPagerAdapter adapter;


    public void initializeInfo(String[] names, String[] parties, String location) {
        repsNames = new ArrayList<String>();
        repsParty = new ArrayList<Integer>();


//        repsNames.add("Sen. Sherrod Brown");
//        repsNames.add("Rep. Pat Tiberi");
//        repsNames.add("Rep. Steve Stivers");
//
//        repsParty.add(R.drawable.dem);
//        repsParty.add(R.drawable.rep);
//        repsParty.add(R.drawable.dem);

//        System.out.println("names:");
        for (String n : names) {
            repsNames.add(n);
//            System.out.println(n);
        }

        System.out.println("parties:");
        for (String p : parties) {
            if (p.equals("Democratic")) {
                repsParty.add(R.drawable.dem);
            }
            if (p.equals("Republican")) {
                repsParty.add(R.drawable.rep);
            }
            if (p.equals("Independent")) {
                repsParty.add(R.drawable.indep);
            }

        }

        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        System.out.println("joel hates me");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        repsNames = new ArrayList<String>();
        repsParty = new ArrayList<Integer>();

        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        adapter = new SampleGridPagerAdapter(this, getFragmentManager());
        pager.setAdapter(adapter);


//        initializeInfo();

        String[] names = new String[] {};
        String[] parties = new String[] {};
        location = "";
        obama = "";
        romney = "";

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String message = extras.getString("message");
            System.out.println(message);


            String[] info = message.split(":");
            names = info[0].split(",");
            parties = info[1].split(",");
            location = info[4];
            obama = "Obama: " + String.valueOf(info[2]) + "%";
            romney = "Romney: " + String.valueOf(info[3]) + "%";



        }

        initializeInfo(names, parties, location);




    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        System.out.println("POOP");
        // Shake-detecting code found on StackOverflow
        long curTime = System.currentTimeMillis();
        // only allow one update every 100ms.
        if ((curTime - lastUpdate) > 100) {
            float[] values = event.values;
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            float x = values[0];
            float y = values[1];
            float z = values[2];

            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > 800) {
                System.out.println("shaking detected");
                // Shaking detected
                Intent intent = new Intent(this, WatchToPhoneService.class);
                intent.putExtra("path", "/shake");
                intent.putExtra("message", "poop");

                startService(intent);

            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int value) {

    }
}
