package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.listeners.ResistenciaAerobicaListener;


public class ResistenciaAerobicaActivity extends ExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_resistencia_aerobica);
        initActivity(R.layout.activity_resistencia_aerobica);
        listener = new ResistenciaAerobicaListener(repCount, tone);
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
        listener = new ResistenciaAerobicaListener(repCount, tone);
    }

}