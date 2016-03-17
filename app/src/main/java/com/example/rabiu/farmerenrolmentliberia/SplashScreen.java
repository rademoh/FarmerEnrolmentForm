package com.example.rabiu.farmerenrolmentliberia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Rabiu on 3/22/15.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread startTimer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    Intent I = new Intent(SplashScreen.this, Third.class);
                    startActivity(I);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        };
        startTimer.start();

  }
}
