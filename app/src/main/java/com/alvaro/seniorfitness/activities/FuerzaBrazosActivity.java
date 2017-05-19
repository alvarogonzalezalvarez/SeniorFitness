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
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.SensorsActivity;
import com.alvaro.seniorfitness.activities.listeners.FuerzaBrazosListener;

public class FuerzaBrazosActivity extends MainActivity {

    FuerzaBrazosListener listener;
    SensorManager sensorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuerza_brazos);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final TextView repCount = (TextView) findViewById(R.id.repCount);

        sensorService = (SensorManager) getApplicationContext()
                            .getSystemService(getApplicationContext().SENSOR_SERVICE);

        listener = new FuerzaBrazosListener(repCount, 8.0f, 0.0f);
    }

    public void start(View view) {
        sensorService.registerListener(listener,
                sensorService.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stop(View view) {
        sensorService.unregisterListener(listener);
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
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
