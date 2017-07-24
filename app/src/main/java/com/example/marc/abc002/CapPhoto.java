package com.example.marc.abc002;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
@SuppressWarnings("deprecation")
public class CapPhoto extends Service {
    public String TAG = "marclog_CapPhoto";
    private Handler mHandler = new Handler();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "start of onCreate method in service");
        class PrimeThread extends Thread {
            PrimeThread() {
            }
            public void run() {
                while (true) {
                    Log.d(TAG, "start sleeping");
                    try {
                        Thread.sleep(10000);
                    } catch (Exception e) {
                    }
                    Log.d(TAG, "done sleeping");
                    Intent intent2 = new Intent();
                    intent2.setAction(Intent.ACTION_MAIN);
                    intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.setComponent(new ComponentName("com.example.marc.abc001", "com.example.marc.abc001.MainActivity"));
                    Log.d(TAG, "Calling startActivity now");
                    try {
                        startActivity(intent2);
                    } catch (Exception e) {
                        Log.d(TAG, "Error on startActivity");
                        e.printStackTrace();
                    }
                }
            }
        }
        PrimeThread p = new PrimeThread();
        p.start();
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "start of onStart method in service");
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getDateTime(), Toast.LENGTH_LONG).show();
                    Log.i("toast", "timer here !!!");
                }
            });
        }
        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
            return sdf.format(new Date());
        }
    }
}