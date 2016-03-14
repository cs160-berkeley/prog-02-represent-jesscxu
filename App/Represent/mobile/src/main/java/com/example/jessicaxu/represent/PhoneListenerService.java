package com.example.jessicaxu.represent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService implements AsyncResponse {

//   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
private static final String TOAST = "/send_toast";

    String state;
    String county;
    String apiUrl;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase("/click_watch") ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            RepsData person = null;
            List<RepsData> persons = LocalReps.persons;
            for (RepsData pooper : persons) {
                if (pooper.name.equals(value)) {
                    person = pooper;
                }
            }

            Intent intent = new Intent(this, SpecificRep.class);
            Bundle args = new Bundle();

            args.putString("name", person.name);
            args.putString("term", person.term);
            args.putInt("photo_id", R.drawable.brown);
            args.putString("party", person.party);
            args.putStringArray("committees", person.committees);
            args.putStringArray("bills", person.bills);

            intent.putExtras(args);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (messageEvent.getPath().equalsIgnoreCase("/shake")) {
            System.out.println("shaking!");



            Random rand = new Random();
            double lat = rand.nextInt(44 - 31 + 1) + 31;
            double lon = (rand.nextInt(118 - 81 + 1) + 81) * -1;


            apiUrl = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + String.valueOf(lat) + "&longitude=" + String.valueOf(lon) + "&apikey=75ab04f41f554b42bfe55e89e6cb169e";

            getCountyFromLatLon(lat, lon);

        } else {
            super.onMessageReceived(messageEvent );
        }

    }



    @Override
    public void processFinish(String response) {
        try {
            JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray results = json.getJSONArray("results");

            if (results.length() == 0) {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, "No representatives could be found for selected ZIPCODE", duration);
                toast.show();
                return;
            }

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> websites  = new ArrayList<String>();
            ArrayList<String> emails  = new ArrayList<String>();
            ArrayList<String> parties  = new ArrayList<String>();
            ArrayList<String> terms = new ArrayList<String>();
            ArrayList<String> twitter_ids = new ArrayList<String>();
            ArrayList<String> bioguides = new ArrayList<String>();
            ArrayList<String> titles = new ArrayList<String>();



            for (int i = 0 ; i < results.length(); i++) {
                JSONObject dict_results = results.getJSONObject(i);
                String fullName = dict_results.getString("first_name") + " " + dict_results.getString("last_name");

                fullName = dict_results.getString("title") + ". " + fullName;

                names.add(fullName);

                websites.add(dict_results.getString("website"));
                emails.add(dict_results.getString("oc_email"));
                parties.add(dict_results.getString("party"));
                titles.add(dict_results.getString("title"));


                String term = dict_results.getString("term_start").substring(0, 4) + " - " + dict_results.getString("term_end").substring(0, 4);
                terms.add(term);

                twitter_ids.add(dict_results.getString("twitter_id"));

                bioguides.add(dict_results.getString("bioguide_id"));

            }

            Intent intent = new Intent(this, LocalReps.class);

            Bundle args = new Bundle();
            args.putStringArrayList("names", names);
            args.putStringArrayList("websites", websites);
            args.putStringArrayList("emails", emails);
            args.putStringArrayList("parties", parties);
            args.putStringArrayList("terms", terms);
            args.putStringArrayList("twitter_ids", twitter_ids);
            args.putStringArrayList("bioguides", bioguides);
            args.putStringArrayList("titles", titles);
            args.putString("county", county);
            args.putString("state", state);

            intent.putExtras(args);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);


        } catch (JSONException e) {
            // Appropriate error handling code
        }


        System.out.println(response);
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
//        progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);
//        responseView.setText(response);
    }

    public void getCountyFromLatLon (double lat, double lon) {
        Geocoder geo = new Geocoder(this);
        try {
            List<Address> addresses = geo.getFromLocation(lat, lon, 5);
            for (Address address : addresses) {
                if (address.getSubAdminArea() != null) {
                    county = address.getSubAdminArea();
                    state = InputLocation.states.get(address.getAdminArea());

                    Log.d("loc", county);
                    Log.d("loc", state);
                    new RetrieveFeedTask(this).execute(apiUrl);
                    break;
                }
            }
        } catch (IOException e) {

        }

    }
}
