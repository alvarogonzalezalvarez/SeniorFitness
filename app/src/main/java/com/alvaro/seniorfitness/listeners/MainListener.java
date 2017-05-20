package com.alvaro.seniorfitness.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.widget.TextView;


public class MainListener implements SensorEventListener {
    TextView textView;
    int reps;
    ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);;

    public MainListener(final TextView textView) {
        this.textView = textView;
        reps = 0;
    }

    public void completeRep() {
        tone.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        reps++;
        textView.setText(Integer.toString(reps));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}