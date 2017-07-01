package com.alvaro.seniorfitness.activities;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.listeners.AgilidadListener;


public class AgilidadActivity extends ExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(R.layout.activity_agilidad);
        listener = new AgilidadListener(repCount, tone);
    }

    @Override
    public void start(final View view) {
        sensorService.registerListener(listener,
                sensorService.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_FASTEST);
        super.start(view);
    }

    @Override
    public void reset(View view) {
        super.reset(view);
        listener = new AgilidadListener(repCount, tone);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return goToExerciseActivity(item, R.id.navigation_agilidad);
    }

}