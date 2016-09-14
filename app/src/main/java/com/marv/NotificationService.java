package com.marv;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

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

public class NotificationService extends Service{

    private Firebase firebase;

    @Override
    public void onCreate() {
        super.onCreate();
        final Context context=this;
        Firebase.setAndroidContext(this);
        firebase=new Firebase("https://marvelement.firebaseio.com/");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(((String)dataSnapshot.child("Notify").getValue()).equals("Yes")){
                    System.out.println("Push Notification Received!");
                    Intent intent = new Intent(context, MainActivity.class);
                    PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
                    Notification n  = new Notification.Builder(context)
                            .setContentTitle((String) dataSnapshot.child("NotifyTitle").getValue())
                            .setContentText((String) dataSnapshot.child("NotifyContent").getValue())
                            .setSmallIcon(R.drawable.polymer)
                            .setContentIntent(pIntent)
                            .setAutoCancel(true)
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .build();
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0, n);
                    Handler j=new Handler();
                    j.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            firebase.child("Notify").setValue("No");
                        }
                    },8500);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Notification Service Started Successfully");
        return super.onStartCommand(intent, flags, startId);
    }
}
