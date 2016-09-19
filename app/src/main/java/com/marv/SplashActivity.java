package com.marv;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;

/* © Copyright 2016 Siddhant Vinchurkar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.   */
public class SplashActivity extends Activity{

    Handler handler;
    WifiManager wifiManager;
    Typewriter load;
    public int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        wifiManager=(WifiManager)getSystemService(WIFI_SERVICE);
        handler=new Handler();
        load=(Typewriter)findViewById(R.id.load);
        load.setCharacterDelay(20);
        load.animateText("\nLoading...");
        SharedPreferences abcd=getSharedPreferences("com.marv_preferences",MODE_PRIVATE);
        boolean notify=abcd.getBoolean("Receive Push Notifications",true);
        boolean earphones=abcd.getBoolean("Ask to Play Music",false);
        if(notify) {
            startService(new Intent(SplashActivity.this, NotificationService.class));
        }else{
            stopService(new Intent(SplashActivity.this,NotificationService.class));
        }
        if(earphones){
            startService(new Intent(SplashActivity.this,EPStateService.class));
        }
        else{
            stopService(new Intent(SplashActivity.this,EPStateService.class));
        }

        class Time implements Runnable{

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for (int i=0;i<=1;i++){
                    try{
                        Thread.sleep(3000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            if(isNetworkAvailable()) {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                if(!UniversalClass.cheatSettings) {
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else{
                                load.setCharacterDelay(20);
                                load.animateText("\nUnable to access the internet");
                                AlertDialog.Builder ab=new AlertDialog.Builder(SplashActivity.this);
                                ab.setTitle("Unable to access the internet");
                                ab.setIcon(R.drawable.ic_no_internet);
                                ab.setMessage("Marv lives in cyberspace. He needs to access the internet to function properly.");
                                ab.setCancelable(false);
                                ab.setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(getIntent());
                                    }
                                });
                                ab.setNegativeButton("WIFI SETTINGS", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    }
                                });
                                ab.setNeutralButton("EXIT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        System.exit(0);
                                    }
                                });
                                ab.create();
                                ab.show();
                            }
                        }
                    });
                }

            }}

        new Thread(new Time()).start();

    }

    @Override
    protected void onResume() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            if(a==2) {
                UniversalClass.cheatSettings=true;
                if(!isNetworkAvailable()){
                    UniversalClass.internet=false;
                }
                else{UniversalClass.overrideSpeechInput=true;}
                a=0;
                startActivity(new Intent(SplashActivity.this, SettingsActivity.class));
            }else{
                a++;
            }
        }
        return true;
    }
}
