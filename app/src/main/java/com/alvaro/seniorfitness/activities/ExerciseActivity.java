package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;


public class ExerciseActivity extends AppCompatActivity {

    SensorEventListener listener;
    SensorManager sensorService;
    TextView repCount;
    CountDownTimer countDown;
    ToneGenerator tone;
    Chronometer chronometer;
    static final Integer idSelected = null;

    protected void initActivity(int layoutResID) {
        setContentView(layoutResID);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        repCount = (TextView) findViewById(R.id.repCount);
        sensorService = (SensorManager) getApplicationContext()
                .getSystemService(getApplicationContext().SENSOR_SERVICE);

        final Button startButton = (Button) findViewById(R.id.start);
        final Button stopButton = (Button) findViewById(R.id.stop);
        stopButton.setVisibility(View.GONE);
        final Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setVisibility(View.GONE);
        TextView remainingTime = (TextView) findViewById(R.id.remainingTime);
        if (remainingTime != null) {
            remainingTime.setVisibility(View.GONE);
        }

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

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        tone = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    }

    public void start(final View view) {
        if (chronometer != null) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        } else {
            LinearLayout duracionText = (LinearLayout) findViewById(R.id.duracionText);
            duracionText.setVisibility(View.GONE);
            final TextView remainingTime = (TextView) findViewById(R.id.remainingTime);
            remainingTime.setVisibility(View.VISIBLE);
            EditText seconds = (EditText) findViewById(R.id.seconds);
            if (seconds.getText() == null || "".equals(seconds.getText().toString())) {
                seconds.setText("0");
            }
            countDown = new CountDownTimer(Integer.valueOf(seconds.getText().toString())*1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    remainingTime.setText(millisUntilFinished / 1000 + "");
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
    }

    public void stop(View view) {
        sensorService.unregisterListener(listener);
        if (chronometer != null) {
            chronometer.stop();
        } else {
            if (countDown != null) {
                countDown.cancel();
            }
        }
    }

    public void reset(View view) {
        if (chronometer != null) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.stop();
        } else {
            stop(view);
            LinearLayout duracionText = (LinearLayout) findViewById(R.id.duracionText);
            duracionText.setVisibility(View.VISIBLE);
            TextView remainingTime = (TextView) findViewById(R.id.remainingTime);
            remainingTime.setVisibility(View.GONE);
            if (repCount != null) {
                repCount.setText("0");
            }
        }
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

    public boolean goToExerciseActivity(MenuItem item, int currentExerciseActivity) {
        Intent intent;

        if (item.getItemId() != currentExerciseActivity) {
            switch (item.getItemId()) {
                case R.id.navigation_resistencia_aerobica:
                    goToActivity(ResistenciaAerobicaActivity.class);
                    break;
                case R.id.navigation_fuerza_piernas:
                    goToActivity(FuerzaPiernasActivity.class);
                    break;
                case R.id.navigation_fuerza_brazos:
                    goToActivity(FuerzaBrazosActivity.class);
                    break;
                case R.id.navigation_agilidad:
                    goToActivity(AgilidadActivity.class);
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        return true;
    }

    public void goToActivity(Class<?> cls) {
        tone.release();
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivityIfNeeded(intent,0);
    }

}