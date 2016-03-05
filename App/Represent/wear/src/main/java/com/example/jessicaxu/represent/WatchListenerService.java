package com.example.jessicaxu.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class WatchListenerService extends WearableListenerService {


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        if( messageEvent.getPath().equalsIgnoreCase( "/data" ) ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, DisplayRepresentative.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("message", value);
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Fred");
            System.out.println(value);
            startActivity(intent);
//        } else if (messageEvent.getPath().equalsIgnoreCase( LEXY_FEED )) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, DisplayRepresentative.class );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("CAT_NAME", "Lexy");
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Lexy");
//            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}