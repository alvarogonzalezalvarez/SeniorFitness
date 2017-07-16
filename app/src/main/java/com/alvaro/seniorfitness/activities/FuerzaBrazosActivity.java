package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.listeners.FuerzaBrazosListener;


public class FuerzaBrazosActivity extends ExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_fuerza_brazos);
        initActivity(R.layout.activity_fuerza_brazos);
        listener = new FuerzaBrazosListener(repCount, tone);
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
        listener = new FuerzaBrazosListener(repCount, tone);
    }

}