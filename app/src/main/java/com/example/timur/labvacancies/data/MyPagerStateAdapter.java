package com.example.timur.labvacancies.data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Timur on 16.04.2018.
 */

public class MyPagerStateAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TabPagerItem> tabs;

    public MyPagerStateAdapter(FragmentManager fm, ArrayList<TabPagerItem> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }
}
