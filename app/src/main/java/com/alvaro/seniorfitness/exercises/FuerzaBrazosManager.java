package com.alvaro.seniorfitness.exercises;

import android.hardware.SensorEvent;

/**
 * Created by Alvaro on 19/5/17.
 */
public abstract class FuerzaBrazosManager {

    protected abstract void onRepIncrease();


    boolean state = false; // Two states, 0 and 1. 0 = Raising towards self, 1 = lowering

    public void updateEvent(SensorEvent event){
        float value = currentPos(event);

        // the value will be the effect of gravity on the x-axis of the device
        // the value of 9.8 means that the device is perpendicular upwards relative to the x-axis
        // the value of 0 means that the device is parallel relative to the x-axis
        if (!state) {
            if (value > 7.5f) {
                onRepIncrease();
                state = !state;
            }
        } else {
            if (value < 0.0f) {
                state = !state;
            }
        }

    }

    private float currentPos(SensorEvent event) {
        // takes the gravity value on the x-axis and returns it.
        // the effect of gravity on the device will help us determine the current orientation of it
        float x = event.values[1];

        return x;
    }
}
