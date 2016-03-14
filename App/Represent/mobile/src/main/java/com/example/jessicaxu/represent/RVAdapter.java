package com.example.jessicaxu.represent;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jessicaxu on 2/27/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    List<RepsData> persons;

    RVAdapter(List<RepsData> persons){
        this.persons = persons;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_reps, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(persons.get(i).name);
        personViewHolder.personWebsite.setText(persons.get(i).website);
        personViewHolder.personEmail.setText(persons.get(i).email);

        if (persons.get(i).tweetView != null) {
            ViewGroup parent = (ViewGroup) persons.get(i).tweetView.getParent();
            if (parent != null) {
                parent.removeView(persons.get(i).tweetView);
            }

            personViewHolder.personTweet.addView(persons.get(i).tweetView);

        }

        personViewHolder.personPhoto.setImageBitmap(persons.get(i).image);
        personViewHolder.moreInfo.setTag(i);

        if (persons.get(i).party.equals("Democratic")) {
            personViewHolder.border.setBackgroundResource(R.drawable.dem_border);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personWebsite;
        TextView personEmail;
        LinearLayout personTweet;
        ImageView personPhoto;
        RelativeLayout border;
        Button moreInfo;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personWebsite = (TextView)itemView.findViewById(R.id.person_website);
            personEmail = (TextView)itemView.findViewById(R.id.person_email);
            personTweet = (LinearLayout)itemView.findViewById(R.id.person_tweet);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            border = (RelativeLayout)itemView.findViewById(R.id.border);
            moreInfo = (Button) itemView.findViewById(R.id.button);
        }
    }

}