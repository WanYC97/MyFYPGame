package com.myfyp.game;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.myfyp.game.helper.StepCounterInterface;

public class AndroidLauncher extends AndroidApplication implements SensorEventListener, StepCounterInterface {
	public static final int MY_PERMISSIONS_REQUEST_ACTIVITY = 99;
	private SensorManager mSensorManager;
	private Sensor sensor;
	private Sensor sensor_Detector;
	private static float CURRENT_STEP;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		checkActivityPermission();

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
		sensor_Detector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
		mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(this, sensor_Detector, SensorManager.SENSOR_DELAY_FASTEST);
		/*
		try{
			if(sensor != null){
				mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
				System.out.println("Sensor.TYPE_STEP_COUNTER");

			}
			else if(sensor_Detector != null)
				mSensorManager.registerListener(this, sensor_Detector, SensorManager.SENSOR_DELAY_FASTEST);
				System.out.println("Sensor.TYPE_STEP_DETECTOR");
		}

		catch(Exception exception){
			System.out.println("No count sensor!");
		}	*/
		begin();
	}

	public boolean checkActivityPermission() {
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED){
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACTIVITY_RECOGNITION)){
				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
			}
			else{
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, MY_PERMISSIONS_REQUEST_ACTIVITY);
				}
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_ACTIVITY: {
				for (int index = permissions.length - 1; index >= 0; --index) {
					if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
						// exit the app if one permission is not granted
						Toast.makeText(this, "Required permission '" + permissions[index]
								+ "' not granted, exiting", Toast.LENGTH_LONG).show();
						finish();
						return;
					}
				}
				// all permissions were granted
				begin();
				break;
			}
		}
	}

	public void begin(){
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyFypGame(new AndroidLauncher()), config);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType()  == Sensor.TYPE_STEP_COUNTER) {
			CURRENT_STEP = event.values[0];
		} else if (event.sensor.getType()  == Sensor.TYPE_STEP_DETECTOR) {
			if (event.values[0] == 1.0) {
				CURRENT_STEP++;
				System.out.println("DETECTOR " + CURRENT_STEP);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public float getCURRENT_STEP() {
		return CURRENT_STEP;
	}

	@Override
	public void setCURRENT_STEP(float CURRENT_STEP) {

	}
}
