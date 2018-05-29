package com.example.timur.labvacancies;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.timur.labvacancies.ui.VacanciesActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, VacanciesActivity.class);
                startActivity(intent);
                SplashScreen.this.finish();
            }
        };

        handler.postDelayed(runnable, 3000);
    }
}
