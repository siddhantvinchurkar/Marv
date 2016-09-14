package com.marv;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class SettingsActivity extends PreferenceActivity {

    private AppCompatDelegate mDelegate;
    Button cancel,grant;
    EditText dpass;
    TextView bypass;
    Vibrator vibe;
    long[] pattern = {0, 50, 50, 50, 50, 50};
    public int g=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setTitle("Settings");
        toolbar.setTitleTextColor(getResources().getColor(R.color.White));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });
        getListView().setBackground(getResources().getDrawable(R.drawable.centered_settings));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = SettingsActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        }
        addPreferencesFromResource(R.xml.preferences);
        vibe=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        if(UniversalClass.cheatSettings){
            LayoutInflater inflater=LayoutInflater.from(SettingsActivity.this);
            final View cheat=inflater.inflate(R.layout.password_dialog, null);
            AlertDialog.Builder ab=new AlertDialog.Builder(SettingsActivity.this);
            ab.setView(cheat);
            ab.setCancelable(false);
            ab.create();
            final AlertDialog show=ab.show();
            cancel=(Button)show.findViewById(R.id.cancel);
            grant=(Button)show.findViewById(R.id.grant);
            dpass=(EditText)show.findViewById(R.id.dpass);
            bypass=(TextView)show.findViewById(R.id.bypass);
            cancel.setText("CANCEL");
            grant.setVisibility(View.VISIBLE);
            dpass.requestFocus();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SettingsActivity.this, SplashActivity.class));
                    finish();
                }
            });
            grant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dpass.getText().toString().equals("1994")&&g<2){
                        show.dismiss();
                        Toast.makeText(getApplicationContext(), "Access Granted!", Toast.LENGTH_SHORT).show();
                        if(!UniversalClass.internet){Toast.makeText(getApplicationContext(), "It looks like you're not connected to the internet. Marv's offline mode is now enabled", Toast.LENGTH_LONG).show();}
                        vibe.vibrate(pattern,-1);
                    }
                    else{
                        if(g>=2){
                            bypass.setText("You have exceeded the number of password attempts allowed. Please try again later.");
                            bypass.setTextColor(getResources().getColor(R.color.Red));
                            vibe.vibrate(pattern, -1);
                            cancel.setText("OKAY");
                            InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Service.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(dpass.getWindowToken(), 0);
                            dpass.setVisibility(View.GONE);
                            grant.setVisibility(View.GONE);
                        }else {
                            g++;
                            bypass.setText("You have entered the wrong password. Please try again.");
                            bypass.setTextColor(getResources().getColor(R.color.Red));
                            vibe.vibrate(pattern, -1);
                        }
                    }
                }
            });
            dpass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId==EditorInfo.IME_ACTION_DONE) {
                        if(dpass.getText().toString().equals("1994")&&g<2){
                            show.dismiss();
                            Toast.makeText(getApplicationContext(),"Access Granted!",Toast.LENGTH_SHORT).show();
                            if(!UniversalClass.internet){Toast.makeText(getApplicationContext(), "It looks like you're not connected to the internet. Marv's offline mode is now enabled", Toast.LENGTH_LONG).show();}
                            vibe.vibrate(pattern,-1);
                        }
                        else{
                            if(g>=2){
                                bypass.setText("You have exceeded the number of password attempts allowed. Please try again later.");
                                bypass.setTextColor(getResources().getColor(R.color.Red));
                                vibe.vibrate(pattern, -1);
                                cancel.setText("OKAY");
                                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Service.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(dpass.getWindowToken(), 0);
                                dpass.setVisibility(View.GONE);
                                grant.setVisibility(View.GONE);
                            }else {
                                g++;
                                bypass.setText("You have entered the wrong password. Please try again.");
                                bypass.setTextColor(getResources().getColor(R.color.Red));
                                vibe.vibrate(pattern, -1);
                            }
                        }
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            });
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    private void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
    }

    @Override
    protected void onPause() {
        UniversalClass.cheatSettings=false;
        //closing transition animations
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
        super.onPause();
    }

    @Override
    protected void onResume() {
        //opening transition animations
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        super.onResume();
    }

}
