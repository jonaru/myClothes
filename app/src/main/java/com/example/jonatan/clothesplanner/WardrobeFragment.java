package com.example.jonatan.clothesplanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Jonatan on 2017-01-13.
 */

public class WardrobeFragment extends Fragment {
    public WardrobeFragment newInstance(int index) {
        WardrobeFragment fragment = new WardrobeFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);

        return fragment;
    }
}
