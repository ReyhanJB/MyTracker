package edu.uci.seal.testapp;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayContactActivity extends Activity {
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_contact);
		
		Intent intent = getIntent();
		String contact = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
		TextView name = (TextView) findViewById(R.id.name_text);
		TextView phone = (TextView) findViewById(R.id.phone_text);
		TextView address = (TextView) findViewById(R.id.address_text);
		String nameInfo = "", phoneInfo = "", addressInfo = "";
		
		//WifiManager w = (WifiManager) getSystemService(WIFI_SERVICE);
		//w.disconnect();
		
		
		
		
		
		/*LocationManager m = (LocationManager) getSystemService(LOCATION_SERVICE);
		m.addTestProvider(LocationManager.GPS_PROVIDER, false, false,false, false, true, true, true, 0, 5);
		m.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
		
		Location l = new Location(LocationManager.GPS_PROVIDER);
		l.setLatitude(120);
		l.setLongitude(-12);
		l.setTime(System.currentTimeMillis());
		l.setElapsedRealtimeNanos(System.currentTimeMillis());
		l.setAccuracy(50);
		l.setAltitude(0);
		//m.setTestProviderLocation(LocationManager.GPS_PROVIDER, l);*/
		
		switch (contact) {
			case "Reyhan":
				System.out.println(contact);
				nameInfo = "Reyhaneh Jabbarvand";
				phoneInfo = "(571) 354-0364";
				addressInfo = "5209 Donald Bren Hall, UCIrvine \nIrvine, CA";
				break;
			case "Alireza":
				System.out.println(contact);
				nameInfo = "Alireza Sadeghi";
				phoneInfo = "(571) 354-0487";
				addressInfo = "5209 Donald Bren Hall, UCIrvine \nIrvine, CA";
				break;
			case "Mahmoud":
				System.out.println(contact);
				nameInfo = "Mahmoud Hammad";
				phoneInfo = "(571) 435-5537";
				addressInfo = "5209 Donald Bren Hall, UCIrvine \nIrvine, CA";
				break;
			case "Negar":
				System.out.println(contact);
				nameInfo = "Negar Ghorbani";
				phoneInfo = "(949) 244-9275";
				addressInfo = "5209 Donald Bren Hall, UCIrvine \nIrvine, CA";
				break;
		}
		
		name.setText(nameInfo);
		phone.setText(phoneInfo);
		address.setText(addressInfo);
	}
	
    protected void onDestroy(){
    	super.onDestroy();
    }

}