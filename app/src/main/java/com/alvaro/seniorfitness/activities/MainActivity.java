package com.alvaro.seniorfitness.activities;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;


public class MainActivity extends AppCompatActivity {

    SensorEventListener listener;
    SensorManager sensorService;
    TextView repCount;
    CountDownTimer countDown;
    ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

    protected void initActivity(int layoutResID) {
        setContentView(layoutResID);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        repCount = (TextView) findViewById(R.id.repCount);
        sensorService = (SensorManager) getApplicationContext()
                .getSystemService(getApplicationContext().SENSOR_SERVICE);

        final Button startButton = (Button) findViewById(R.id.start);
        final Button stopButton = (Button) findViewById(R.id.stop);
        final Button resetButton = (Button) findViewById(R.id.reset);
        TextView remainingTime = (TextView) findViewById(R.id.remainingTime);
        stopButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);
        remainingTime.setVisibility(View.GONE);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start(v);
                startButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stop(v);
                stopButton.setVisibility(View.GONE);
                startButton.setVisibility(View.GONE);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reset(v);
                stopButton.setVisibility(View.GONE);
                resetButton.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
            }
        });
    }

    public void start(final View view) {
        LinearLayout duracionText = (LinearLayout) findViewById(R.id.duracionText);
        duracionText.setVisibility(View.GONE);
        final TextView remainingTime = (TextView) findViewById(R.id.remainingTime);
        remainingTime.setVisibility(View.VISIBLE);
        EditText seconds = (EditText) findViewById(R.id.seconds);
        countDown = new CountDownTimer(Integer.valueOf(seconds.getText().toString())*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                remainingTime.setText(millisUntilFinished / 1000 + " segundos restantes");
            }

            public void onFinish() {
                remainingTime.setText("TIEMPO!");
                stop(view);
                tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,400);
                Button startButton = (Button) findViewById(R.id.start);
                Button stopButton = (Button) findViewById(R.id.stop);
                stopButton.setVisibility(View.GONE);
                startButton.setVisibility(View.GONE);
            }
        }.start();
    }

    public void stop(View view) {
        sensorService.unregisterListener(listener);
        countDown.cancel();
    }

    public void reset(View view) {
        stop(view);
        LinearLayout duracionText = (LinearLayout) findViewById(R.id.duracionText);
        duracionText.setVisibility(View.VISIBLE);
        TextView remainingTime = (TextView) findViewById(R.id.remainingTime);
        remainingTime.setVisibility(View.GONE);
        repCount.setText("0");
    }

    protected void onPause() {
        super.onPause();
        sensorService.unregisterListener(listener);
        if (countDown != null) {
            countDown.cancel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

}