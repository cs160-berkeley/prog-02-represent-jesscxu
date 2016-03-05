package com.example.jessicaxu.represent;

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
    int photoId;
    String term;
    String[] committees;
    String[] bills;


    RepsData(String name, String website, String email, String party, String tweet, int photoId) {
        this.name = name;
        this.website = website;
        this.email = email;
        this.party = party;
        this.photoId = photoId;
        this.tweet = tweet;
        this.term = "2008 - 2016";
        this.committees = new String[] {"committee 1", "committee 2", "committee 3"};
        this.bills = new String[] {"bill 1", "bill 2", "bill 3"};
    }


}
