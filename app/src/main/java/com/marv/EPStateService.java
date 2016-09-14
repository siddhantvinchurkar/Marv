package com.marv;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

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

public class EPStateService extends Service {

    private static final String TAG = "EPStateService";
    private MusicIntentReceiver myReceiver;
    int flag=0;

    public EPStateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myReceiver = new MusicIntentReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
        SharedPreferences sp=getSharedPreferences("EPStateService",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("flag",0);
        editor.commit();
        flag=sp.getInt("flag",0);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d(TAG, "Headset is unplugged");
                        SharedPreferences sp=getSharedPreferences("EPStateService",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putInt("flag",0);
                        editor.commit();
                        break;
                    case 1:
                        Log.d(TAG, "Headset is plugged");
                        SharedPreferences sp2=getSharedPreferences("EPStateService",MODE_PRIVATE);
                        flag=sp2.getInt("flag",1);
                        if(flag==0) {
                            Intent intent1 = new Intent();
                            intent1.setClass(getApplicationContext(), EPDialog.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent1);
                            SharedPreferences.Editor editor2=sp2.edit();
                            editor2.putInt("flag",1);
                            editor2.commit();
                        }
                        break;
                    default:
                        Log.d(TAG, "I have no idea what the headset state is");
                }
            }
        }
    }

}
