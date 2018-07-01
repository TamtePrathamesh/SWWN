package com.handsintech.coder.e_astro.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.handsintech.coder.e_astro.Activites.MainActivity;
import com.handsintech.coder.e_astro.R;

//SPLASHSCREEN
public class SplashScreen extends AppCompatActivity {

   // requestWindowFeature(Window.FEATURE_NO_TITLE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash_screen);
//a1=getActionBar();
        //       a1.hide();

//         getSupportActionBar().hide();
        // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        // getActionBar().hide();
        Thread myThread =new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
    }

