package com.example.manny.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    TextView stepCount;

    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCount = (TextView) findViewById(R.id.stepCount);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        // IF YOU PAUSE THEN STOP DETECTING STEPS
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        stepCount.setText(String.valueOf(sensorEvent.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
