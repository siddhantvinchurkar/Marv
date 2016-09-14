package com.marv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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

public class SuggestionActivity extends AppCompatActivity {

    TextView wnew;
    EditText name,suggestion;
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wnew=(TextView)findViewById(R.id.wnew);
        name=(EditText)findViewById(R.id.name);
        suggestion=(EditText)findViewById(R.id.suggestion);
        Firebase.setAndroidContext(this);
        firebase=new Firebase("https://marvelement.firebaseio.com/");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data=(String)dataSnapshot.child("New").getValue();
                wnew.setText(data);
                UniversalClass.whatsNew=data;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuggestionActivity.this,SettingsActivity.class));
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().isEmpty()&&suggestion.getText().toString().isEmpty()){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setTitle("Empty Fields");
                    ab.setMessage("You have not entered your name and suggestion yet.");
                    ab.setCancelable(false);
                    ab.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do nothing
                        }
                    });
                    ab.create();
                    ab.show();
                }
                else if(name.getText().toString().isEmpty()){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setTitle("Empty Field");
                    ab.setMessage("You have not entered your name yet.");
                    ab.setCancelable(false);
                    ab.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do nothing
                        }
                    });
                    ab.create();
                    ab.show();
                }
                else if(suggestion.getText().toString().isEmpty()){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setTitle("Empty Field");
                    ab.setMessage("You have not entered your suggestion yet.");
                    ab.setCancelable(false);
                    ab.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do nothing
                        }
                    });
                    ab.create();
                    ab.show();
                }
                else if(name.getText().toString().equals("New")){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setMessage("That's not a real name...");
                    ab.create();
                    ab.show();
                }
                else if(name.getText().toString().equals("Notify")){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setMessage("That's not a real name...");
                    ab.create();
                    ab.show();
                }
                else if(name.getText().toString().equals("NotifyTitle")){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setMessage("That's not a real name...");
                    ab.create();
                    ab.show();
                }
                else if(name.getText().toString().equals("NotifyContent")){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setMessage("That's not a real name...");
                    ab.create();
                    ab.show();
                }
                else if(name.getText().toString().equals("GreetContent")){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setMessage("That's not a real name...");
                    ab.create();
                    ab.show();
                }
                else if(name.getText().toString().equals("Greet")){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setMessage("That's not a real name...");
                    ab.create();
                    ab.show();
                }
                else if(name.getText().toString().equals("GreetSpeak")){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setMessage("That's not a real name...");
                    ab.create();
                    ab.show();
                }
                else if(name.getText().toString().equals("PictureURL")){
                    AlertDialog.Builder ab=new AlertDialog.Builder(SuggestionActivity.this);
                    ab.setMessage("That's not a real name...");
                    ab.create();
                    ab.show();
                }
                else{
                    if(isNetworkAvailable()) {
                        firebase.child("Suggestions").child(name.getText().toString()).setValue(suggestion.getText().toString());
                        name.setText("");
                        suggestion.setText("");
                        Snackbar.make(view, "Suggestion successfully sent", Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        Snackbar.make(view, "No internet connection", Snackbar.LENGTH_LONG)
                                .setAction("WIFI SETTINGS", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    }
                                })
                                .show();
                    }
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
