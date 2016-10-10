package com.marv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean signUp = false;
    private boolean authenticate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
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
                if(UniversalClass.username.isEmpty()||!UniversalClass.username.contains("@")||UniversalClass.password.isEmpty()){
                    AlertDialog.Builder ab=new AlertDialog.Builder(Introduction.this);
                    ab.setMessage("It is not necessary to complete this step. But if you wish to do so, please use a valid email ID and password.");
                    ab.setCancelable(true);
                    ab.create();
                    ab.show();
                    authenticate = false;
                }
                if(authenticate) {
                    SharedPreferences sp = getSharedPreferences("com.marv_preferences", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("Name", UniversalClass.username);
                    edit.commit();
                    if (UniversalClass.username.isEmpty()) {
                        UniversalClass.username = "friend";
                    }
                    mAuth.createUserWithEmailAndPassword(UniversalClass.email, UniversalClass.password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                                        signUp = true;
                                    }
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        signUp = false;
                                    }

                                    // ...
                                }
                            });
                    if (!signUp) {
                        mAuth.signInWithEmailAndPassword(UniversalClass.email, UniversalClass.password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Welcome back!", Toast.LENGTH_SHORT).show();
                                            signUp = false;
                                        }
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            signUp = true;
                                            Log.w("TAG", "signInWithEmail:failed", task.getException());
                                            Toast.makeText(getApplicationContext(), "Sign in Failed!", Toast.LENGTH_SHORT).show();
                                        }

                                        // ...
                                    }
                                });
                    }
                    authenticate = true;
                }
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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
