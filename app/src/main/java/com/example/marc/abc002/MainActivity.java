package com.example.marc.abc002;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
public class MainActivity extends AppCompatActivity {
    Intent service;
    public String TAG = "marclog_MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() in activity");
        service = new Intent(getBaseContext(), CapPhoto.class);
        Log.d(TAG, "created service");
        startService(service);
        Log.d(TAG, "called startService");
        finish();
    }
}
