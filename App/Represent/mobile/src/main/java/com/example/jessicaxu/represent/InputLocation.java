package com.example.jessicaxu.represent;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InputLocation extends AppCompatActivity implements AsyncResponse, GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener {

    public String zipcode;
    private String apiUrl;
    private String state;
    private String county;
    EditText zipcode_num;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    public static Map<String, String> states;

    static {
        states = new HashMap<>();
        states.put("Alabama","AL");
        states.put("Alaska","AK");
        states.put("Alberta","AB");
        states.put("American Samoa","AS");
        states.put("Arizona","AZ");
        states.put("Arkansas","AR");
        states.put("Armed Forces (AE)","AE");
        states.put("Armed Forces Americas","AA");
        states.put("Armed Forces Pacific","AP");
        states.put("British Columbia","BC");
        states.put("California","CA");
        states.put("Colorado","CO");
        states.put("Connecticut","CT");
        states.put("Delaware","DE");
        states.put("District Of Columbia","DC");
        states.put("Florida","FL");
        states.put("Georgia","GA");
        states.put("Guam","GU");
        states.put("Hawaii","HI");
        states.put("Idaho","ID");
        states.put("Illinois","IL");
        states.put("Indiana","IN");
        states.put("Iowa","IA");
        states.put("Kansas","KS");
        states.put("Kentucky","KY");
        states.put("Louisiana","LA");
        states.put("Maine","ME");
        states.put("Manitoba","MB");
        states.put("Maryland","MD");
        states.put("Massachusetts","MA");
        states.put("Michigan","MI");
        states.put("Minnesota","MN");
        states.put("Mississippi","MS");
        states.put("Missouri","MO");
        states.put("Montana","MT");
        states.put("Nebraska","NE");
        states.put("Nevada","NV");
        states.put("New Brunswick","NB");
        states.put("New Hampshire","NH");
        states.put("New Jersey","NJ");
        states.put("New Mexico","NM");
        states.put("New York","NY");
        states.put("Newfoundland","NF");
        states.put("North Carolina","NC");
        states.put("North Dakota","ND");
        states.put("Northwest Territories","NT");
        states.put("Nova Scotia","NS");
        states.put("Nunavut","NU");
        states.put("Ohio","OH");
        states.put("Oklahoma","OK");
        states.put("Ontario","ON");
        states.put("Oregon","OR");
        states.put("Pennsylvania","PA");
        states.put("Prince Edward Island","PE");
        states.put("Puerto Rico","PR");
        states.put("Quebec","PQ");
        states.put("Rhode Island","RI");
        states.put("Saskatchewan","SK");
        states.put("South Carolina","SC");
        states.put("South Dakota","SD");
        states.put("Tennessee","TN");
        states.put("Texas","TX");
        states.put("Utah","UT");
        states.put("Vermont","VT");
        states.put("Virgin Islands","VI");
        states.put("Virginia","VA");
        states.put("Washington","WA");
        states.put("West Virginia","WV");
        states.put("Wisconsin","WI");
        states.put("Wyoming","WY");
        states.put("Yukon Territory","YT");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_location);

        zipcode_num = (EditText) findViewById(R.id.zipcode);


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    @Override
    public void onConnectionSuspended(int poop) {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult poop) {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    public void clickToActivityLocalReps (View view) {
        String zip = zipcode_num.getText().toString();
        String url = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=";
        url = url + zip;
        url = url + "&apikey=75ab04f41f554b42bfe55e89e6cb169e";
        apiUrl = url;

        try{
            Geocoder geo = new Geocoder(this);
            Address address = geo.getFromLocationName(zip, 1).get(0);
            double lat = address.getLatitude();
            double lon = address.getLongitude();
            getCountyFromLatLon(lat, lon);
        } catch(IOException e) {

        }


//        Intent get_zipcode = new Intent(this, LocalReps.class);
//
//        String poop = "" + zipcode_num.getText().toString();
//        get_zipcode.putExtra("zipcode", poop);
//        startActivity(get_zipcode);
    }

    public void clickToActivityLocalRepsCurrLoc (View view) {
        if (mLastLocation != null) {
            double lat = mLastLocation.getLatitude();
            double lon = mLastLocation.getLongitude();
            apiUrl = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + String.valueOf(lat) + "&longitude=" + String.valueOf(lon) + "&apikey=75ab04f41f554b42bfe55e89e6cb169e";

            getCountyFromLatLon(lat, lon);


        } else {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, "Could not find location", duration);
            toast.show();
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
                    state = states.get(address.getAdminArea());
                    new RetrieveFeedTask(this).execute(apiUrl);
                    break;
                }
            }
        } catch (IOException e) {

        }

    }
}
