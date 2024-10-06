package com.lab2_emirandagarcia.lab2;
//Write notes too help you cause you forget a lot :'(
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gravitySensor, magneticSensor;
    private TextView gravityTextView, magneticTextView;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00"); //a function which simplifies the numbers for the sensor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Get the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Initialize sensors
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Initialize TextViews for displaying sensor data
        gravityTextView = findViewById(R.id.gravityTextView);
        magneticTextView = findViewById(R.id.magneticTextView);


        // Register listeners
        if (gravitySensor != null) {
            sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (magneticSensor != null) {
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Check sensor type
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            // Display gravity data
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            gravityTextView.setText("Gravity: \nX: " + decimalFormat.format(x) +"m/s\u00B2"+ "\nY: " + decimalFormat.format(y)+"m/s\u00B2"+ "\nZ: " + decimalFormat.format(z)+"m/s\u00B2");
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // Display magnetic field data
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            magneticTextView.setText("Magnetic Field: \nX: " + decimalFormat.format(x) +"m/s\u00B2"+ "\nY: " + decimalFormat.format(y) +"m/s\u00B2"+ "\nZ: " + decimalFormat.format(z)+"m/s\u00B2");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Modify this method to handle accuracy changes (add something here)
        if (sensor.getType() == Sensor.TYPE_GRAVITY) {
            gravityTextView.append("\nGravity Sensor accuracy changed: " + accuracy);
        } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticTextView.append("\nMagnetic Field Sensor accuracy changed: " + accuracy);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister sensor listeners to save battery
        sensorManager.unregisterListener(this, gravitySensor);
        sensorManager.unregisterListener(this, magneticSensor);
    }
    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }


}
