package com.alvaro.seniorfitness.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
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
import android.widget.Toast;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.database.SeniorFitnessContract;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExerciseActivity extends AppCompatActivity {

    SensorEventListener listener;
    SensorManager sensorService;
    TextView repCount;
    CountDownTimer countDown;
    ToneGenerator tone;
    Chronometer chronometer;
    long elapsedTime = 0;
    private Activity these;
    String userId;
    String sessionId;
    String testId;
    private SeniorFitnessDBHelper dbHelper;

    protected void initActivity(int layoutResID) {
        setContentView(layoutResID);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        dbHelper = new SeniorFitnessDBHelper(this);
        userId = getIntent().getStringExtra("userId");
        sessionId = getIntent().getStringExtra("sessionId");
        testId = getIntent().getStringExtra("testId");
        repCount = (TextView) findViewById(R.id.repCount);
        sensorService = (SensorManager) getApplicationContext()
                .getSystemService(getApplicationContext().SENSOR_SERVICE);
        these = this;

        final Button startButton = (Button) findViewById(R.id.start);
        final Button stopButton = (Button) findViewById(R.id.stop);
        stopButton.setVisibility(View.GONE);
        final Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setVisibility(View.GONE);
        final Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setVisibility(View.GONE);
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateString = sdf.format(new Date());
                if (chronometer != null) {
                    new insertResult().execute(userId, testId, sessionId,
                            Long.valueOf(elapsedTime/1000).toString(), dateString);
                } else {
                    new insertResult().execute(userId, testId, sessionId, repCount.getText().toString(),
                            dateString);
                }
                Intent intent = NavUtils.getParentActivityIntent(these);
                intent.putExtra("userId", userId);
                NavUtils.navigateUpTo(these, intent);
            }
        });

        tone = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    }

    public void start(final View view) {
        if (chronometer != null) {
            elapsedTime = 0;
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
                    Button saveButton = (Button) findViewById(R.id.save);
                    saveButton.setVisibility(View.VISIBLE);
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
            elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
            Button saveButton = (Button) findViewById(R.id.save);
            saveButton.setVisibility(View.VISIBLE);
        } else {
            if (countDown != null) {
                countDown.cancel();
            }
        }
    }

    public void reset(View view) {
        if (chronometer != null) {
            elapsedTime = 0;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra("userId", userId);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class insertResult extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... what) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // Mapa de valores, cuyas claves serán los nombres de las columnas
            ContentValues values = new ContentValues();
            values.put(SeniorFitnessContract.Result.COLUMN_NAME_USERID, what[0]);
            values.put(SeniorFitnessContract.Result.COLUMN_NAME_TESTID, what[1]);
            values.put(SeniorFitnessContract.Result.COLUMN_NAME_SESSIONID, what[2]);
            values.put(SeniorFitnessContract.Result.COLUMN_NAME_RESULT, what[3]);
            values.put(SeniorFitnessContract.Result.COLUMN_NAME_DATE, what[4]);

            long newRowId = db.insert(SeniorFitnessContract.Result.TABLE_NAME, null, values);
            return newRowId;
        }

        @Override
        protected void onPostExecute(Long result) {
            Toast.makeText(getApplicationContext(), "Resultado guardado",
                    Toast.LENGTH_SHORT).show();
        }
    }

}