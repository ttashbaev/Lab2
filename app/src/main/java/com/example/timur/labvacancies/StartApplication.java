package com.example.timur.labvacancies;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.timur.labvacancies.data.AuService;
import com.example.timur.labvacancies.data.NetworkBuilder;
import com.example.timur.labvacancies.data.SQLiteHelper;

/**
 * Created by Timur on 26.04.2018.
 */

public class StartApplication extends Application {
    private AuService service;

    private SQLiteHelper mHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        service = NetworkBuilder.initService();

        mHelper = new SQLiteHelper(this);
    }

    public static StartApplication get(Context context) {
        return (StartApplication) context.getApplicationContext();
    }

    public SQLiteHelper getHelper() {
        return mHelper;
    }

    public AuService getService(){
        return  service;
    }
}
