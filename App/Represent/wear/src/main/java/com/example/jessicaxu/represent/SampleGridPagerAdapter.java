package com.example.jessicaxu.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridPagerAdapter;
import android.widget.ImageView;


import java.util.List;

/**
 * Created by jessicaxu on 2/29/16.
 */
public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    private List mRows;



    public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    static final int[] BG_IMAGES = new int[] {
    };

    // A simple container for static data in each page
    private static class Page {
        // static resources
        int titleRes;
        int textRes;
        int iconRes;
    }

    // Create a static set of pages in a 2D array
    private final Page[][] PAGES = {  };

    // Override methods in FragmentGridPagerAdapter
    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {

        Fragment presView = new Fragment();

        if (col == (getColumnCount(0) - 1)) {

            String loc = ((DisplayRepresentative) mContext).location;
            String obama = ((DisplayRepresentative) mContext).obama;
            String romney = ((DisplayRepresentative) mContext).romney;

//            System.out.println(loc);

            presView = PresidentialView.create(obama, romney, loc);





            return presView;
        }

        String title = ((DisplayRepresentative) mContext).repsNames.get(col);

        int party = ((DisplayRepresentative) mContext).repsParty.get(col);



        CardFragment fragment = RepsCardFragment.create(title, party);



        return fragment;
    }



    // Obtain the background image for the specific page
    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        String location = ((DisplayRepresentative) mContext).location;
        if(column != getColumnCount(row) - 1) {
            return GridPagerAdapter.BACKGROUND_NONE;
        } else {
            // Default to background image for row
            return GridPagerAdapter.BACKGROUND_NONE;
        }
    }

    // Obtain the number of pages (vertical)
    @Override
    public int getRowCount() {
        int size = ((DisplayRepresentative) mContext).repsNames.size();
        if (size > 0) {
            return 1;
        }
        return 0;
    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        int size = ((DisplayRepresentative) mContext).repsNames.size();
        if (size > 0) {
            return size + 1;
        }
        return 0;
    }
}
