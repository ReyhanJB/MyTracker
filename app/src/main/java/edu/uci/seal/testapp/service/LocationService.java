package edu.uci.seal.testapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import edu.uci.seal.testapp.DisplayMessageActivity;

public class LocationService extends Service {

	public final static String EXTRA_MESSAGE = "edu.uci.seal.testapp.MESSAGE";
	LocationManager locationManager;
	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
			String message = "Location of the device has been changed";
			intent1.putExtra(EXTRA_MESSAGE, message); //The first parameter is the key for the intent
			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent1);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
			String message = "Status of provider is changed";
			intent1.putExtra(EXTRA_MESSAGE, message); //The first parameter is the key for the intent
			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent1);
		}

		public void onProviderEnabled(String provider) {
			Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
			String message = "The provider is now enabled";
			intent1.putExtra(EXTRA_MESSAGE, message); //The first parameter is the key for the intent
			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent1);
		}

		public void onProviderDisabled(String provider) {
			Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
			String message = "The provider is now disabled";
			intent1.putExtra(EXTRA_MESSAGE, message); //The first parameter is the key for the intent
			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent1);
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

        //register(5*1000,0);
		return Service.START_NOT_STICKY;
	}

    @Override
    public IBinder onBind(Intent intent) {
        print();
        return null;
    }

    private void register(int i, int j){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, i, j, locationListener);
    }

    private void unregister(){
        locationManager.removeUpdates(locationListener);
    }

    public void print(){
        Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class);
        intent1.putExtra(EXTRA_MESSAGE, "********showing the test message.");
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }

    public void onDestroy(){
        //unregister();
        Log.d("Test", "service stopped");
    }
}
