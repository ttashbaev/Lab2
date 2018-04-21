package com.example.timur.labvacancies.data;

import android.support.v4.app.Fragment;

/**
 * Created by Timur on 16.04.2018.
 */

public class TabPagerItem {
    private final Fragment fragment;
    private final CharSequence title;

    public TabPagerItem (Fragment fragment, CharSequence title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public CharSequence getTitle() {
        return title;
    }
}
