package edu.uci.seal.testapp.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import edu.uci.seal.testapp.DisplayMessageActivity;

public class BluetoothService extends Service{

	public final static String EXTRA_MESSAGE = "edu.uci.seal.testapp.MESSAGE";
	
	final BroadcastReceiver bluetoothStateChange = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			Log.d("reyhan", "state receiver onrecieve");
			final String action = intent.getAction();
			Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
			
			if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
				switch(state) {
					case BluetoothAdapter.STATE_OFF:
						intent1.putExtra(EXTRA_MESSAGE, "Bluetooth is off"); //The first parameter is the key for the intent
						break;
                	case BluetoothAdapter.STATE_TURNING_OFF:
						intent1.putExtra(EXTRA_MESSAGE, "Bluetooth turning is off"); //The first parameter is the key for the intent
                		break;
                	case BluetoothAdapter.STATE_ON:
						intent1.putExtra(EXTRA_MESSAGE, "Bluetooth is on"); //The first parameter is the key for the intent
                		break;
                	case BluetoothAdapter.STATE_TURNING_ON:
						intent1.putExtra(EXTRA_MESSAGE, "Bluetooth is turning on"); //The first parameter is the key for the intent
                		break;
				}
				intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent1);
			}
		}
	};
	
	final BroadcastReceiver bluetoothDiscoveryStatus = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			final String action = intent.getAction();
			Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
			
			if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
				int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);
	            switch(mode){
	            	case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
	                	intent1.putExtra(EXTRA_MESSAGE, "Bluetooth scan mode is connectable and discoverable"); //The first parameter is the key for the intent
						break;
	            	case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
	                	intent1.putExtra(EXTRA_MESSAGE, "Bluetooth scan mode is connectable"); //The first parameter is the key for the intent
						break;
	            	case BluetoothAdapter.SCAN_MODE_NONE:
	                	intent1.putExtra(EXTRA_MESSAGE, "Bluetooth scan mode is non-connectable"); //The first parameter is the key for the intent
						break;
	            }
	            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent1);
			}
		}
	};
	
	private final BroadcastReceiver bluetoothConnecttionNotification = new BroadcastReceiver() {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        
	    	final String action = intent.getAction();
	    	Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
	        if(action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)){
	        	intent1.putExtra(EXTRA_MESSAGE, "Bluetooth device connected"); //The first parameter is the key for the intent
	        }
	        if(action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)){
	        	intent1.putExtra(EXTRA_MESSAGE, "Bluetooth device disconnected"); //The first parameter is the key for the intent

	        }
	        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent1);
	    }
	};
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Register the required BroadCastReceivers
		IntentFilter filterState = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		//IntentFilter filterScan = new IntentFilter();
		IntentFilter filterConnect = new IntentFilter();
		
		//filterScan.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		//filterScan.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		//filterScan.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
	    filterConnect.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
	    filterConnect.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
	    
	    registerReceiver(bluetoothStateChange, filterState);
	    //registerReceiver(bluetoothDiscoveryStatus, filterScan);
	    registerReceiver(bluetoothConnecttionNotification, filterConnect);
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    unregisterReceiver(bluetoothStateChange);
	    //unregisterReceiver(bluetoothDiscoveryStatus);
	    unregisterReceiver(bluetoothConnecttionNotification);
	}

}
