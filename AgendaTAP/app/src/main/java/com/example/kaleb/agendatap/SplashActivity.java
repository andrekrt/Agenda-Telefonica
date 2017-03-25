package com.example.kaleb.agendatap;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(this, 3000);
    }

    @Override
    public void run() {
        Intent vaiParaLogin = new Intent(this, LoginActivity.class);
        startActivity(vaiParaLogin);
        finish();

    }
}
