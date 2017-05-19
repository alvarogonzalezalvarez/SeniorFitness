package com.alvaro.seniorfitness.activities.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.widget.TextView;

import com.alvaro.seniorfitness.exercises.FuerzaBrazosManager;


public class FuerzaBrazosListener implements SensorEventListener {
    TextView textView;

    float upper_threshold;
    float lower_threshold;

    FuerzaBrazosManager manager;
    int reps;
    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

    public FuerzaBrazosListener(final TextView textView, float upper_threshold, float lower_threshold) {
        this.textView = textView;
        this.upper_threshold = upper_threshold;
        this.lower_threshold = lower_threshold;

        reps = 0;
        manager = new FuerzaBrazosManager() {
            @Override
            protected void onRepIncrease() {
                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
                reps++;
                textView.setText(Integer.toString(reps));
            }
        };

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            manager.updateEvent(event);
        } else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
