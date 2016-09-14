package com.marv;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;

public class EPDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epdialog);
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setTitle("Marv");
        ab.setMessage("You just plugged your earphones in. How about some music?");
        ab.setIcon(R.mipmap.ic_launcher);
        ab.setCancelable(false);
        ab.create();
        ab.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long eventtime = SystemClock.uptimeMillis() - 1;
                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
                        am.dispatchMediaKeyEvent(downEvent);

                        KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
                        am.dispatchMediaKeyEvent(upEvent);
                    }
                }, 500);
                finish();
            }
        });
        ab.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        ab.show();
    }
}
