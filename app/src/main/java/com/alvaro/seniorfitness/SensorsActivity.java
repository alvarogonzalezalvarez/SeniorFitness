package com.alvaro.seniorfitness;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener {

    //Common variables
    private SensorManager mSensorManager;
    public Vibrator v;
    private float vibrateThreshold = 0;

    //Accelerometer variables
    private Sensor mAccelerometer;

    private float deltaXMax = 0;
    private float deltaYMax = 0;
    private float deltaZMax = 0;
    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;
    private TextView currentX, currentY, currentZ, maxX, maxY, maxZ;
    private float lastX, lastY, lastZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //We will check if the accelerometer is available.
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            currentX = (TextView) findViewById(R.id.currentX);
            currentY = (TextView) findViewById(R.id.currentY);
            currentZ = (TextView) findViewById(R.id.currentZ);
            maxX = (TextView) findViewById(R.id.maxX);
            maxY = (TextView) findViewById(R.id.maxY);
            maxZ = (TextView) findViewById(R.id.maxZ);

            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            vibrateThreshold = mAccelerometer.getMaximumRange() / 2;
        }

        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //clean current values
            currentX.setText("0.0");
            currentY.setText("0.0");
            currentZ.setText("0.0");

            //display the current x,y,z accelerometer values
            currentX.setText(Float.toString(deltaX));
            currentY.setText(Float.toString(deltaY));
            currentZ.setText(Float.toString(deltaZ));

            //display the max x,y,z accelerometer values
            if (deltaX > deltaXMax) {
                deltaXMax = deltaX;
                maxX.setText(Float.toString(deltaXMax));
            }
            if (deltaY > deltaYMax) {
                deltaYMax = deltaY;
                maxY.setText(Float.toString(deltaYMax));
            }
            if (deltaZ > deltaZMax) {
                deltaZMax = deltaZ;
                maxZ.setText(Float.toString(deltaZMax));
            }

            //get the change of the x,y,z values of the accelerometer
            deltaX = Math.abs(lastX - event.values[0]);
            deltaY = Math.abs(lastY - event.values[1]);
            deltaZ = Math.abs(lastZ - event.values[2]);

            //if the change is below 1, we will consider it as noise. If is above the threshold,
            //the device vibrates
            if (deltaX < 1) {
                deltaX = 0;
            }
            if (deltaY < 1) {
                deltaY = 0;
            }
            if (deltaZ < 1) {
                deltaZ = 0;
            }
            if ((deltaX > vibrateThreshold) || (deltaY > vibrateThreshold)
                    || (deltaZ > vibrateThreshold)) {
                v.vibrate(50);
            }

            //set the last know values of x,y,z
            lastX = event.values[0];
            lastY = event.values[1];
            lastZ = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
