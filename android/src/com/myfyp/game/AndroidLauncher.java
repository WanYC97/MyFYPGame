package com.myfyp.game;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	public static final int MY_PERMISSIONS_REQUEST_ACTIVITY = 99;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		checkActivityPermission();
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
		initialize(new MyFypGame(new StepCounter()), config);
	}

}
