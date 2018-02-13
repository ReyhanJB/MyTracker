package edu.uci.seal.testapp.service;

import edu.uci.seal.testapp.DisplayMessageActivity;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

public class NetworkService extends Service{

	public final static String EXTRA_MESSAGE = "edu.uci.seal.testapp.MESSAGE";
	
	private final BroadcastReceiver connectivityChange = new BroadcastReceiver() {

		@SuppressLint("InlinedApi")
		@Override
		public void onReceive(Context context, Intent intent) {
			Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
			int type = intent.getIntExtra(ConnectivityManager.EXTRA_NETWORK_TYPE, -1);
			boolean disconnection = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
			
			if(type == ConnectivityManager.TYPE_WIFI){
				if(!disconnection)
					intent1.putExtra(EXTRA_MESSAGE, "Connected to WiFi"); //The first parameter is the key for the intent
				if(disconnection)
					intent1.putExtra(EXTRA_MESSAGE, "Disconnected from WiFi"); //The first parameter is the key for the intent
			}
			if(type == ConnectivityManager.TYPE_MOBILE){
				if(!disconnection)
					intent1.putExtra(EXTRA_MESSAGE, "Connected to Cellular"); //The first parameter is the key for the intent
				if(disconnection)
					intent1.putExtra(EXTRA_MESSAGE, "Disconnected from Cellular"); //The first parameter is the key for the intent
			}
			
			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent1);
		}
		
	};
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		IntentFilter filters = new IntentFilter();
		filters.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		super.registerReceiver(connectivityChange, filters);
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onDestroy(){
		super.onDestroy();
		unregisterReceiver(connectivityChange);
	}

}
