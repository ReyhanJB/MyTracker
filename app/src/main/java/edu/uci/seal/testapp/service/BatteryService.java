package edu.uci.seal.testapp.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;

import edu.uci.seal.testapp.DisplayMessageActivity;

public class BatteryService extends Service{

	public final static String EXTRA_MESSAGE = "edu.uci.seal.testapp.MESSAGE";
	
	private final BroadcastReceiver batteryStatus = new BroadcastReceiver() {

		@SuppressLint("InlinedApi")
		@Override
		public void onReceive(Context context, Intent intent) {
			Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
			int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
			int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
			//int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
			if(status == BatteryManager.BATTERY_STATUS_CHARGING){
				if(chargePlug == BatteryManager.BATTERY_PLUGGED_USB)
					intent1.putExtra(EXTRA_MESSAGE, "Battery is charging thorugh usb");
				if(chargePlug == BatteryManager.BATTERY_PLUGGED_AC)
					intent1.putExtra(EXTRA_MESSAGE, "Battery is charging thorugh AC");
				if(chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS)
					intent1.putExtra(EXTRA_MESSAGE, "Battery is charging wireless");
			}
			if(status == BatteryManager.BATTERY_STATUS_DISCHARGING || status == BatteryManager.BATTERY_STATUS_NOT_CHARGING)
				intent1.putExtra(EXTRA_MESSAGE, "Battery is discharging");
			
			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent1);
		}
	};
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		//IntentFilter connectionFilters = new IntentFilter();
		IntentFilter batteryFilters = new IntentFilter();
		//connectionFilters.addAction(Intent.ACTION_POWER_CONNECTED);
		//connectionFilters.addAction(Intent.ACTION_POWER_DISCONNECTED);
		batteryFilters.addAction(Intent.ACTION_BATTERY_LOW);
		batteryFilters.addAction(Intent.ACTION_BATTERY_OKAY);//Battery is not low anymore
		batteryFilters.addAction(Intent.ACTION_BATTERY_CHANGED);
		super.registerReceiver(batteryStatus, batteryFilters);
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onDestroy(){
		super.onDestroy();
		unregisterReceiver(batteryStatus);
	}

}
