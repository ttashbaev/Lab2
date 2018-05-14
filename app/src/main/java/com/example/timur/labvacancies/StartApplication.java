package com.example.timur.labvacancies;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.timur.labvacancies.data.AuService;
import com.example.timur.labvacancies.data.NetworkBuilder;

/**
 * Created by Timur on 26.04.2018.
 */

public class StartApplication extends Application {
    private AuService service;

    @Override
    public void onCreate() {
        super.onCreate();
        service = NetworkBuilder.initService();
    }

    public static StartApplication get(Context context) {
        return (StartApplication) context.getApplicationContext();
    }

    public AuService getService(){
        return  service;
    }
}
