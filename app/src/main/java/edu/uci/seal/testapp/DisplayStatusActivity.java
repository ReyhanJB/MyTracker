package edu.uci.seal.testapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class DisplayStatusActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_status);
		
		Intent intent = getIntent();
		String[] tokens = intent.getStringExtra(MyActivity.EXTRA_MESSAGE).split(",");
		String gpsCheck = tokens[0];
		String wifiCheck = tokens[1];
		String batteryCheck = tokens[2];
		String bluetoothCheck = tokens[3];
		if(gpsCheck.equals("true")){
			TextView gps = (TextView) findViewById(R.id.GPS);
			String text = "";
			text += gpsStat();
			gps.setText(text);
			gps.setTextSize(10);
		}
		if(wifiCheck.equals("true")){
			TextView wifi = (TextView) findViewById(R.id.WiFi);
			String text = "";
			text += wifiStat();
			wifi.setText(text);
			wifi.setTextSize(10);
		}
		if(batteryCheck.equals("true")){
			TextView gprs = (TextView) findViewById(R.id.GPRS);
			String text = "";
			text += batteryStat();
			gprs.setText(text);
			gprs.setTextSize(10);
		}
		if(bluetoothCheck.equals("true")){
			TextView bluetooth = (TextView) findViewById(R.id.Bluetooth);
			String text = "";
			text += bluetoothStat();
			bluetooth.setText(text);
			bluetooth.setTextSize(10);
		}
		
	}
	
	private String gpsStat(){
		String out = "-------------------------------------\n";
		LocationManager locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
		List<String> providers = locMan.getAllProviders();
		out += "List of location providers:\n";
		for(String provider:providers)
			out += provider+"\n";
		if(providers.size() != 0){
			Location loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(loc != null){
				out += "Latitude: "+loc.getLatitude()+"\n";
				out += "Altitude: "+loc.getAltitude()+"\n";
				out += "Longitude: "+loc.getLongitude()+"\n";
				out += "Accuracy: "+loc.getAccuracy()+"\n";
				out += "Speed: "+loc.getSpeed();
			}
		}
		return out+"\n-------------------------------------";
	}
	
	private String wifiStat(){
		String out = "-------------------------------------\n";
		out += "WiFi:\n";
		ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo winfo = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(winfo.isAvailable()){
			out += "Available\n";
			if(winfo.isConnected()){
				WifiManager wifiManager = (WifiManager) getSystemService (WIFI_SERVICE);
				WifiInfo Winfo = wifiManager.getConnectionInfo ();
				out += "Connected\n";
				out += "SSID: "+Winfo.getSSID()+"\n";
				out += "IP Address: "+Winfo.getIpAddress()+"\n";
				out += "Network ID: "+Winfo.getNetworkId()+"\n";
				out += "Link Speed: "+Winfo.getLinkSpeed()+"\n";
				out += "RSSI: "+Winfo.getRssi();
			}
			else
				out += "Not connected\n";
			
		}
		else
			out += "Not available\n";
		out += "\n\n\nRadio:\n";
		NetworkInfo info = conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(info.isAvailable()){
			out += "Available\n";
			if(info.isConnected()){
				out += "Connected\n";
				out += "SubType Name: "+info.getSubtypeName()+"\n";
				out += "Roaming: "+info.isRoaming();
			}
			else
				out += "Not connected\n";
		}
		else
			out += "Not available";
		return out+"\n-------------------------------------";
	}
	
	private String batteryStat(){
		String out = "-------------------------------------\n";
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
		if(isCharging){
			out += "Charging\n";
			int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
			boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
			boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
			if(usbCharge)
				out += "Charging using USB\n";
			if(acCharge)
				out += "Charging using AC\n";
			int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			out += "Battery percentage: "+level+"%";

		}
		else
			out += "Not charging\n";
		
		return out+"\n-------------------------------------";
	}
	
	@SuppressLint("NewApi")
	private String bluetoothStat(){
		String out = "-------------------------------------\n";
		BluetoothManager blue = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
		BluetoothAdapter adapter = blue.getAdapter();
		if(adapter != null){
			if(adapter.getState() == BluetoothAdapter.STATE_ON || adapter.getState() == BluetoothAdapter.STATE_TURNING_ON){
				out += "Bluetooth on\n";
                out += "Name: "+adapter.getName()+"\n";
				out += "Address: "+adapter.getAddress()+"\n";
				out += "Scan Mode: "+adapter.getScanMode()+"\n";
				out += "List of connected devices: \n";
				Set<BluetoothDevice> devices = adapter.getBondedDevices();
				for(BluetoothDevice device:devices)
					out += device.getName()+","+device.getAddress()+"\n";
			}
			else
				out += "Bluetooth off\n";
		}
		else
			out += "No bluetooth adapter found";
		
		return out+"\n-------------------------------------";
	}
	
    protected void onDestroy(){
    	super.onDestroy();
    }

}