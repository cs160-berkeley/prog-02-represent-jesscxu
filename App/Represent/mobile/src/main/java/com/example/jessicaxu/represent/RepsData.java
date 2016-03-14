package com.example.jessicaxu.represent;

import android.graphics.Bitmap;

import com.twitter.sdk.android.tweetui.TweetView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jessicaxu on 2/27/16.
 */
public class RepsData {
    String name;
    String website;
    String email;
    String party;
    String tweet;
    String bio;
    String term;
    String title;
    String[] committees;
    String[] bills;
    Bitmap image;
    TweetView tweetView;
    boolean tweet_loaded;


    RepsData(String name, String website, String email, String party, String tweet, String bio, String title, String term) {
        this.name = name;
        this.website = website;
        this.email = email;
        this.party = party;
        this.title = title;
        this.bio = bio;
        this.tweet = tweet;
        this.term = term;
        this.committees = new String[] {"committee 1", "committee 2", "committee 3"};
        this.bills = new String[] {"bill 1", "bill 2", "bill 3"};
        this.image = null;
        this.tweetView = null;
        this.tweet_loaded = false;
    }


}
