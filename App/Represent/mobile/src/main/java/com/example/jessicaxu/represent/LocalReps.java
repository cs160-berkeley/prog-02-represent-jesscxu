package com.example.jessicaxu.represent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class LocalReps extends AppCompatActivity {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "RLMITHQTaAw5rZNTS4hO4C00h";
    private static final String TWITTER_SECRET = "GjNFBM5RVQ1uwDzJGbVAUDWxIwYcuGlxTzgF5eoXdamUV2AQ5t";

    private String county;
    private String state;
    ArrayList<String> names;
    ArrayList<String> websites;
    ArrayList<String> emails;
    ArrayList<String> parties;
    ArrayList<String> terms;
    ArrayList<String> twitter_ids;
    ArrayList<String> bioguides;
    ArrayList<String> titles;
    private ProgressBar progress_bar;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_reps);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        linearLayout = (LinearLayout) findViewById(R.id.local_reps);

        progress_bar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        Intent getInfo = getIntent();

        Bundle extras = getInfo.getExtras();



        initializeData(extras);

    }

    public void buildCards() {
        if (checkImagesLoaded()) {


            RecyclerView rv = (RecyclerView)findViewById(R.id.rv);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            rv.setLayoutManager(llm);

            RVAdapter adapter = new RVAdapter(persons);
            rv.setAdapter(adapter);

            progress_bar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    public boolean checkImagesLoaded() {
        for (RepsData person : persons) {
            if (person.image == null) {
                return false;
            }
            if (!person.tweet_loaded) {
                return false;
            }
        }


        return true;
    }

    public static List<RepsData> persons;

    public void twitter_info() {
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> result) {
                TwitterApiClient client = new TwitterApiClient(result.data);
                for (final RepsData person : persons) {

                    client.getStatusesService().userTimeline(null, person.tweet, 1, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                        @Override
                        public void success(Result<List<Tweet>> result) {
                            person.tweet_loaded = true;
                            if (result.data.size() > 0){
                                Tweet tweet = result.data.get(0);
                                person.tweetView = new TweetView(LocalReps.this, tweet);
                                buildCards();
                            }

                        }

                        @Override
                        public void failure(TwitterException e) {

                        }
                    });
                }
            }

            @Override
            public void failure(TwitterException e) {

            }
        });

    }

    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.
    private void initializeData(Bundle extras){
        persons = new ArrayList<>();
//
//        String names_m = "";
//        String parties_m = "";
//        String location;
//
//
//        for (int i = 0; i < names.size(); i++) {
//            if (i == names.size() - 1) {
//                names_m = names_m + names.get(i);
//            }
//            names_m = names_m + names.get(i) + ",";
//            if (i == names.size() - 1) {
//                parties_m = parties_m + parties.get(i);
//            }
//            parties_m = parties_m + parties.get(i) + ",";
//        }

        names = extras.getStringArrayList("names");
        websites = extras.getStringArrayList("websites");
        emails = extras.getStringArrayList("emails");
        parties = extras.getStringArrayList("parties");
        terms = extras.getStringArrayList("terms");
        twitter_ids = extras.getStringArrayList("twitter_ids");
        bioguides = extras.getStringArrayList("bioguides");
        titles = extras.getStringArrayList("titles");
        county = extras.getString("county");
        state = extras.getString("state");


        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));

            String party = "";
            if (parties.get(i).equals("D")) {
                party = "Democratic";
            } else if (parties.get(i).equals("R")) {
                party = "Republican";
            } else {
                party = "Independent";
            }

            String title = "";
            if (titles.get(i).equals("Rep")) {
                title = "Representative";
            } else if (titles.get(i).equals("Sen"))
            {
                title = "Senator";
            }

            String url = "http://congress.api.sunlightfoundation.com/committees?member_ids=";
            url = url + bioguides.get(i);
            url = url + "&apikey=75ab04f41f554b42bfe55e89e6cb169e";

            final RepsData person = new RepsData(names.get(i), websites.get(i), emails.get(i), party, twitter_ids.get(i), bioguides.get(i), title, terms.get(i));
            persons.add(person);

            ImageLoader imageLoader = ImageLoader.getInstance();
            String imageURL = "https://theunitedstates.io/images/congress/450x550/" + bioguides.get(i) + ".jpg";

            imageLoader.loadImage(imageURL, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageURL, View view, Bitmap loadedImage){
                    person.image = loadedImage;
                    buildCards();
                }
            });

            RetrieveFeedTask asyncTask = new RetrieveFeedTask(new AsyncResponse(){
                @Override
                public void processFinish(String output){

                    try {
                        JSONObject json = (JSONObject) new JSONTokener(output).nextValue();
                        JSONArray results = json.getJSONArray("results");

                        ArrayList<String> committees = new ArrayList<String>();

                        for (int i = 0 ; i < results.length(); i++) {
                            JSONObject dict_results = results.getJSONObject(i);
                            committees.add(dict_results.getString("name"));
                        }

                        person.committees = committees.toArray(new String[0]);

                    } catch (JSONException e) {
                        // Appropriate error handling code
                    }

                }
            });

            System.out.println(url);

            asyncTask.execute(url);

            url = "http://congress.api.sunlightfoundation.com/bills?sponsor_id=";
            url = url + bioguides.get(i);
            url = url + "&apikey=75ab04f41f554b42bfe55e89e6cb169e";

            asyncTask = new RetrieveFeedTask(new AsyncResponse(){
                @Override
                public void processFinish(String output){

                    try {
                        JSONObject json = (JSONObject) new JSONTokener(output).nextValue();
                        JSONArray results = json.getJSONArray("results");

                        ArrayList<String> bills = new ArrayList<String>();

                        for (int i = 0 ; i < results.length(); i++) {
                            JSONObject dict_results = results.getJSONObject(i);
                            bills.add(dict_results.getString("short_title"));
                        }

                        person.bills = bills.toArray(new String[0]);

                    } catch (JSONException e) {
                        // Appropriate error handling code
                    }

                }
            });
            System.out.println(url);
            asyncTask.execute(url);



        }

        twitter_info();

        messageToWatch();


    }


    public void messageToWatch() {
        Log.d("Watch", county + ", " + state);


        String url = "https://d1b10bmlvqabco.cloudfront.net/attach/ijddlu9pcyk1sk/hq1pd634o47ic/ilndte8ipnrj/newelectioncounty2012.json";

        RetrieveFeedTask asyncTask = new RetrieveFeedTask(new AsyncResponse(){
            @Override
            public void processFinish(String output){

                try {
                    JSONObject json = (JSONObject) new JSONTokener(output).nextValue();

                    JSONObject vote_results = json.getJSONObject(county + ", " + state);
                    double obama = vote_results.getDouble("obama");
                    double romney = vote_results.getDouble("romney");

                    Log.d("votes", String.valueOf(obama));
                    Log.d("votes", String.valueOf(romney));

                    String message = "";

                    for (RepsData person : persons) {
                        message = message + person.name + ",";
                    }
                    message = message + ":";

                    for (RepsData person : persons) {
                        message = message + person.party + ",";
                    }
                    message = message + ":";

                    message = message + obama + ":" + romney;

                    message += ":" + county + ", " + state;

                    Log.d("message", message);

                    Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                    sendIntent.putExtra("path", "data");
                    sendIntent.putExtra("message", message);

                    startService(sendIntent);

                } catch (JSONException e) {
                    // Appropriate error handling code
                }

            }
        });

        asyncTask.execute(url);
//        if (zipcode.equals("0")) {
//
//            persons.add(new RepsData("Sen. Bill Nelson", "http://nelson.house.gov", "senate@nelson.senate.gov","Democratic",
//                    "New documents show Takata employees routinely manipulated safety testing data. Here’s the report: http://1.usa.gov/21dxL2Q.", R.drawable.nelson));
//            persons.add(new RepsData("Sen. Marco Rubio", "http://rubio.senate.gov", "senate@rubio.rep.gov","Republican",
//                    "Start your weekend early & attend our rally in Wichita on Friday afternoon! Don't wait. RSVP here: http://rub.io/JVmLCR  #KSforMarco", R.drawable.rubio));
//            persons.add(new RepsData("Rep. Daniel Webster", "http://webster.house.gov", "rep@webster.rep.gov", "Republican",
//                    "Voted to protect Medicaid from fraud & abuse by ensuring providers banned by @CMSGov can’t continue to defraud the system simply by moving", R.drawable.webster));
//
//            message = "Sen. Bill Nelson,Sen. Marco Rubio,Rep. Daniel Webster:d,r,r:Orlando County — FL";
//




    }

    public void detailedView(View v) {
        Intent intent = new Intent(this, SpecificRep.class);
        Bundle args = new Bundle();

        RepsData person = persons.get((int) v.getTag());

        args.putString("name", person.name);



        intent.putExtras(args);
        startActivity(intent);
    }

}
