package com.rahul.hackathon.spm;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rahul.hackathon.spm.database.UserInfoStorage;
import com.rahul.hackathon.spm.model.User;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    private int DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                UserInfoStorage userInfoStorage = new
                        UserInfoStorage(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
                boolean isRegistered = userInfoStorage.isUserRegistered();
                if (!isRegistered) {
                    Intent registerIntent = new Intent(SplashScreenActivity.this, RegistrationActivity.class);
                    startActivity(registerIntent);
                    finish();
                } else {
                    Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }

            }
        }, DELAY);

    }
}
