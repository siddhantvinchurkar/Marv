package com.marv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

/* © Copyright 2016 Siddhant Vinchurkar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

public class Introduction extends AppIntro implements Introduction_Slide2.OnFragmentInteractionListener,Introduction_Slide3.OnFragmentInteractionListener, Introduction_Slide4.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(false);
        showSkipButton(false);
        setColorDoneText(getResources().getColor(R.color.Black));
        setColorTransitionsEnabled(true);
        setImmersive(true);
        Fragment fragment1 = new Introduction_Slide1();
        Fragment fragment2 = new Introduction_Slide2();
        Fragment fragment3 = new Introduction_Slide3();
        Fragment fragment4 = new Introduction_Slide4();
        Fragment fragment5 = new Introduction_Slide5();
        Fragment fragment6 = new Introduction_Slide6();
        addSlide(fragment1);
        addSlide(fragment2);
        addSlide(fragment3);
        addSlide(fragment4);
        if(UniversalClass.isRooted)addSlide(fragment6);
        addSlide(fragment5);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button
        LayoutInflater inflater= LayoutInflater.from(Introduction.this);
                final View setupcomplete=inflater.inflate(R.layout.setup_complete_dialog, null);
                AlertDialog.Builder ab=new AlertDialog.Builder(Introduction.this);
                ab.setView(setupcomplete);
                ab.setCancelable(true);
                ab.create();
                final AlertDialog show=ab.show();
                TextView introcomplete=(TextView) setupcomplete.findViewById(R.id.introcomplete);
                introcomplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sp = getSharedPreferences("com.marv_preferences",MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("first_launch", false);
                        edit.commit();
                        startActivity(new Intent(Introduction.this,MainActivity.class));
                        finish();
                    }
                });
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onFragmentInteraction(String id) {
        switch (id){
            case "grantpermissions": Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                break;
            case "submit": onResume();
                Toast.makeText(getApplicationContext(), "This is Awesome!", Toast.LENGTH_SHORT).show();
                break;
            case "introcando": startActivity(new Intent(Introduction.this,Help.class)); UniversalClass.introBackStack=true;
                break;
        }
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

}
