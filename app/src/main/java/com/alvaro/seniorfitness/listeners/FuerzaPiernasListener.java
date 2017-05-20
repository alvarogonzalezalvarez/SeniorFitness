package com.alvaro.seniorfitness.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.TextView;


public class FuerzaPiernasListener extends MainListener {
    boolean state = false;

    public FuerzaPiernasListener(final TextView textView) {
        super(textView);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            float value = event.values[1];

            // the value will be the effect of gravity on the x-axis of the device
            // the value of 9.8 means that the device is perpendicular upwards relative to the x-axis
            // the value of 0 means that the device is parallel relative to the x-axis
            if (!state) {
                if (value < -8.5f) {
                    completeRep();
                    state = !state;
                }
            } else {
                if (value > -4.0f) {
                    state = !state;
                }
            }
        }
    }
}