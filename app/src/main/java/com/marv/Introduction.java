package com.marv;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class Introduction extends AppIntro implements ISlideBackgroundColorHolder, Introduction_Slide2.OnFragmentInteractionListener,Introduction_Slide3.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(false);
        showSkipButton(false);
        setFadeAnimation();
        Fragment fragment1 = new Introduction_Slide1();
        Fragment fragment2 = new Introduction_Slide2();
        Fragment fragment3 = new Introduction_Slide3();
        addSlide(fragment1);
        addSlide(fragment2);
        addSlide(fragment3);
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#000000");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(Introduction.this,MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onFragmentInteraction(String id) {
        String local="";
        if(id.startsWith("submit="))local="submit";
        else local=id;
        switch (local){
            case "grantpermissions": Toast.makeText(getApplicationContext(), "This is Awesome!", Toast.LENGTH_SHORT).show();
                break;
            case "submit": Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
