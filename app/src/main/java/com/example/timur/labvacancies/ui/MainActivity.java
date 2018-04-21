package com.example.timur.labvacancies.ui;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.timur.labvacancies.R;
import com.example.timur.labvacancies.data.MyPagerStateAdapter;
import com.example.timur.labvacancies.data.TabPagerItem;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Drawer drawer;
    private AccountHeader header;
    private ProfileDrawerItem pfd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDrawer();




    }


    private void createDrawer () {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PrimaryDrawerItem itemSecond = new PrimaryDrawerItem().withName("SecondFragment").withIdentifier(2);
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            drawer = new DrawerBuilder().
                    withActivity(this).
                    withSliderBackgroundColor(getColor(android.R.color.white)).
                    withToolbar(toolbar).
                    addDrawerItems(
                            //create new item
                            new PrimaryDrawerItem().withName("UserFragment").withIdentifier(1),
                            itemSecond
                    )
                    .build();
        }
    }
}
