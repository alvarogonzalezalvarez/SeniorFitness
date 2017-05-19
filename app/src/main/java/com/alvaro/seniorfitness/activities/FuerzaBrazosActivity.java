package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.SensorsActivity;
import com.alvaro.seniorfitness.activities.listeners.FuerzaBrazosListener;

public class FuerzaBrazosActivity extends MainActivity {

    FuerzaBrazosListener listener;
    SensorManager sensorService;
    TextView repCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuerza_brazos);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        repCount = (TextView) findViewById(R.id.repCount);
        sensorService = (SensorManager) getApplicationContext()
                            .getSystemService(getApplicationContext().SENSOR_SERVICE);
        listener = new FuerzaBrazosListener(repCount, 8.0f, 0.0f);

        final Button startButton = (Button) findViewById(R.id.start);
        final Button stopButton = (Button) findViewById(R.id.stop);
        final Button resetButton = (Button) findViewById(R.id.reset);
        stopButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);

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
                startButton.setVisibility(View.VISIBLE);
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

    public void start(View view) {
        sensorService.registerListener(listener,
                sensorService.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stop(View view) {
        sensorService.unregisterListener(listener);
    }

    public void reset(View view) {
        stop(view);
        repCount.setText("0");
        listener = new FuerzaBrazosListener(repCount, 8.0f, 0.0f);
    }

    protected void onPause() {
        super.onPause();
        sensorService.unregisterListener(listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.navigation_sensors:
                intent = new Intent(this, SensorsActivity.class);
                this.startActivity(intent);
                break;
            case R.id.navigation_fuerza_brazos:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
