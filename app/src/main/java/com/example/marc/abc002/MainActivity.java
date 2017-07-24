package com.example.marc.abc002;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {
    Intent service;
    public static final int MY_PERMISSIONS_RECEIVE_SMS = 91;
    public static final int MY_PERMISSIONS_SEND_SMS = 92;
    TextView textView11;
    Boolean permission = false;
    int ReceiveSmsPermisssionGranted = 99;
    public String TAG = "marclog_MainActivity";
    //--------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView11 = (TextView) findViewById(R.id.text11);
        Log.d(TAG, "onCreate() in activity");
        getPermissions();
        textView11.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "textView11.post is here_____");
                if (ReceiveSmsPermisssionGranted == 0) {
                    Log.d(TAG, "Permission not granted. kill process.");
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                if (ReceiveSmsPermisssionGranted == 1) {
                    service = new Intent(getBaseContext(), CapPhoto.class);
                    Log.d(TAG, "created service");
                    startService(service);
                    Log.d(TAG, "called startService");
                    finish();
                }
                textView11.postDelayed(this, 500);
            }
        });
        Log.d(TAG, "end of onCreate method");
    }
    //--------------------------------------------------------------------------------------------
    @Override
    protected void onStop() {
        super.onStop();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    //--------------------------------------------------------------------------------------------
    public void getPermissions() {
        int permissionCheck = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "RECEIVE_SMS permission was previously granted", Toast.LENGTH_LONG).show();
            Log.d(TAG, "RECEIVE_SMS permission was previously granted");
                ReceiveSmsPermisssionGranted = 1;
        } else {
            Toast.makeText(this, "need to ask the user for permission to RECEIVE_SMS", Toast.LENGTH_LONG).show();
            Log.d(TAG, "need to ask the user for permission to RECEIVE_SMS");
            if (shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)) {
                Toast.makeText(this, "RECEIVE_SMS permission was previously denied by the user, will ask again", Toast.LENGTH_LONG).show();
                Log.d(TAG, "RECEIVE_SMS permission was previously denied by the user, will ask again");
                requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_RECEIVE_SMS);
            } else {
                Toast.makeText(this, "RECEIVE_SMS request the permission now??", Toast.LENGTH_LONG).show();
                Log.d(TAG, "RECEIVE_SMS request the permission now??");
                requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_RECEIVE_SMS);
            }
        }
    }
    //--------------------------------------------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Toast.makeText(this, "permissions callback", Toast.LENGTH_LONG).show();
        Log.d(TAG, "permissions callback");
        switch (requestCode) {
            case MY_PERMISSIONS_RECEIVE_SMS: {
                Toast.makeText(this, "MY_PERMISSIONS_RECEIVE_SMS callback", Toast.LENGTH_LONG).show();
                Log.d(TAG, "MY_PERMISSIONS_RECEIVE_SMS callback");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "RECEIVE_SMS permission granted", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "RECEIVE_SMS permission granted");
                    ReceiveSmsPermisssionGranted = 1;
                } else {
                    Toast.makeText(this, "RECEIVE_SMS denied. Closing app.", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "RECEIVE_SMS denied. Closing app.");
                    ReceiveSmsPermisssionGranted = 0;
                }
            }
        }
    }
    //--------------------------------------------------------------------------------------------
}
