package com.example.jessicaxu.represent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService {

//   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
private static final String TOAST = "/send_toast";

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
            args.putInt("photo_id", person.photoId);
            args.putString("party", person.party);
            args.putStringArray("committees", person.committees);
            args.putStringArray("bills", person.bills);

            intent.putExtras(args);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (messageEvent.getPath().equalsIgnoreCase("/shake")) {
            System.out.println("shaking!");

            Intent intent = new Intent(this, LocalReps.class);
            Bundle args = new Bundle();

            args.putString("zipcode", "123456");
            intent.putExtras(args);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
