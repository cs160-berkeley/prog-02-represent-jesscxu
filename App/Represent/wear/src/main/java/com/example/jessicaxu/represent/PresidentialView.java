package com.example.jessicaxu.represent;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jessicaxu on 3/2/16.
 */
public class PresidentialView extends Fragment {

    public PresidentialView() {
        super();
    }

    public static PresidentialView create(String obama_vote, String romney_vote, String location) {
        PresidentialView cf = new PresidentialView();
        Bundle bundle = new Bundle();
        bundle.putString("obama_vote", obama_vote);
        bundle.putString("romney_vote", romney_vote);
        bundle.putString("location", location);
        System.out.println(location);
        cf.setArguments(bundle);
        return cf;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pres_view, container, false);

        TextView obamaVote = (TextView) v.findViewById(R.id.obama);
        obamaVote.setText(getArguments().getString("obama_vote"));

        TextView romneyVote = (TextView) v.findViewById(R.id.romney);
        romneyVote.setText(getArguments().getString("romney_vote"));

        TextView location = (TextView) v.findViewById(R.id.location);
        location.setText(getArguments().getString("location"));

        return v;
    }

}
