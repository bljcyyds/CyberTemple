package com.example.cybertemple;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private int recLen = 5;
    private TextView skip;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //Full screen
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_start);
        initView();
        timer.schedule(task, 1000, 1000);

        //No skip
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
    private void initView() {
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(this);
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    skip.setText("Skip " + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        skip.setVisibility(View.GONE);
                    }
                }
            });
        }
    };

    //Click skip
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }

}



