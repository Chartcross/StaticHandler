package com.chartcross.statichandler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MainActivity";
    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                String message = "message sent from activity #" + Integer.toString(msg.arg1, 16);
                Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, message);
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button messageButton = findViewById(R.id.send_message_button);
        messageButton.setOnClickListener(this);
        Log.d(TAG, "onCreate activity #" + Integer.toString(this.hashCode(), 16));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_message_button) {
            Message msg = new Message();
            msg.arg1 = MainActivity.this.hashCode();
            mHandler.sendMessageDelayed(msg, 20000);
        }
    }
}
