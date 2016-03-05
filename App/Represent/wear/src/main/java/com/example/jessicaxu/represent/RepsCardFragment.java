package com.example.jessicaxu.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jessicaxu on 2/29/16.
 */
public class RepsCardFragment extends CardFragment {

    public RepsCardFragment() {
        super();
    }

    public static RepsCardFragment create(String name, int party) {
        RepsCardFragment cf = new RepsCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("party", party);
        cf.setArguments(bundle);
        return cf;
    }

    @Override
    public View onCreateContentView (final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rep_card_view, container, false);
        TextView name = (TextView) v.findViewById(R.id.name);
        final String name_string = getArguments().getString("name");
        name.setText(name_string);

        ImageView icon = (ImageView) v.findViewById(R.id.party);

        icon.setImageResource(getArguments().getInt("party"));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (inflater.getContext(), WatchToPhoneService.class);
                intent.putExtra("message", name_string);
                intent.putExtra("path", "/click_watch");
                inflater.getContext().startService(intent);
            }
        });

        return v;
    }
}
