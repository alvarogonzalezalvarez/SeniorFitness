package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.listeners.FuerzaPiernasListener;


public class FuerzaPiernasActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(R.layout.activity_fuerza_piernas);
        listener = new FuerzaPiernasListener(repCount);
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
        listener = new FuerzaPiernasListener(repCount);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.navigation_fuerza_piernas:
                break;
            case R.id.navigation_fuerza_brazos:
                intent = new Intent(this, FuerzaBrazosActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}