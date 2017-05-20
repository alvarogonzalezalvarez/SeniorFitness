package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.listeners.FuerzaBrazosListener;


public class FuerzaBrazosActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(R.layout.activity_fuerza_brazos);
        listener = new FuerzaBrazosListener(repCount);
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
        listener = new FuerzaBrazosListener(repCount);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.navigation_fuerza_brazos:
                break;
            case R.id.navigation_fuerza_piernas:
                intent = new Intent(this, FuerzaPiernasActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}