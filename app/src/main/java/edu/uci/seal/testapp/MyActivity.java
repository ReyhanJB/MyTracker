package edu.uci.seal.testapp;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import edu.uci.seal.testapp.service.BatteryService;
import edu.uci.seal.testapp.service.BluetoothService;
import edu.uci.seal.testapp.service.LocationService;
import edu.uci.seal.testapp.service.NetworkService;


public class MyActivity extends Activity{

	public final static String EXTRA_MESSAGE = "edu.uci.seal.testapp.MESSAGE";
	LocationManager locationManager;
	LocationListener locationListener;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.d("Reyhan","onCreate");
        setContentView(R.layout.activity_my);

        test();
        showTextButton();
        startServiceButton();
        radioButtons();
        checkStatusButton();
        checkBoxes();
        contactSpinner();
        locationList();
        
    }
    
    private int test(){
    	int i = 0;
    	i=i+1;
		return i;
    }
    
    private void locationList(){
    	ListView locations = (ListView) findViewById(R.id.list_locations);
    	locations.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            	Intent intent = new Intent(getApplicationContext(), DisplayLocationActivity.class); 
		    	intent.putExtra(EXTRA_MESSAGE, parent.getItemAtPosition(position).toString()); //The first parameter is the key for the intent
		    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    	startActivity(intent);
            }
    	});
    }
    
    private void contactSpinner(){
    	Spinner spinner = (Spinner) findViewById(R.id.spinner_contact);
    	spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(!parent.getItemAtPosition(position).toString().equals("Contact")){
					Intent intent = new Intent(getApplicationContext(), DisplayContactActivity.class); 
			    	intent.putExtra(EXTRA_MESSAGE, parent.getItemAtPosition(position).toString()); //The first parameter is the key for the intent
			    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    	startActivity(intent);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
    		
    	});
    }
    
    private void checkBoxes(){
    	final CheckBox checkGPS = (CheckBox) findViewById(R.id.check_gps);
    	final CheckBox checkWiFi = (CheckBox) findViewById(R.id.check_net);
    	final CheckBox checkBattery = (CheckBox) findViewById(R.id.check_battery);
    	final CheckBox checkBlue = (CheckBox) findViewById(R.id.check_bluetooth);
    	
    	checkGPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

    		@Override
    		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    			Toast.makeText(MyActivity.this, "GPS checked", Toast.LENGTH_SHORT).show();
    		}
    	});    
    	
    	checkWiFi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

    		@Override
    		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    			Toast.makeText(MyActivity.this, "Network checked", Toast.LENGTH_SHORT).show();
    		}
    	});    
    	
    	checkBattery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

    		@Override
    		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    			Toast.makeText(MyActivity.this, "Battery checked", Toast.LENGTH_SHORT).show();
    		}
    	});    
    	
    	checkBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

    		@Override
    		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    			Toast.makeText(MyActivity.this, "Bluetooth checked", Toast.LENGTH_SHORT).show();
    		}
    	});    
    }
    
    private void checkStatusButton(){
    	final CheckBox checkGPS = (CheckBox) findViewById(R.id.check_gps);
    	final CheckBox checkWiFi = (CheckBox) findViewById(R.id.check_net);
    	final CheckBox checkBattery = (CheckBox) findViewById(R.id.check_battery);
    	final CheckBox checkBlue = (CheckBox) findViewById(R.id.check_bluetooth);
    	
    	Button checkStatusButton = (Button) findViewById(R.id.button_check);
    	checkStatusButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean gps = false,wifi = false,gprs = false,bluetooth = false;
		    	if(checkGPS.isChecked())
		    		gps = true;
		    	if(checkWiFi.isChecked())
		    		wifi = true;
		    	if(checkBattery.isChecked())
		    		gprs = true;
		    	if(checkBlue.isChecked())
		    		bluetooth = true;
		    	
				Intent intent = new Intent(getApplicationContext(), DisplayStatusActivity.class); 
		    	intent.putExtra(EXTRA_MESSAGE, gps+","+wifi+","+gprs+","+bluetooth); //The first parameter is the key for the intent
		    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    	startActivity(intent);
			}
    	});
    }
    
    private void showTextButton(){
    	 Button sendButton = (Button) findViewById(R.id.button_show_text);
    	 sendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
		    	EditText editText = (EditText) findViewById(R.id.edit_message);
		    	String message = editText.getText().toString();
		    	intent.putExtra(EXTRA_MESSAGE, message); //The first parameter is the key for the intent
		    	startActivity(intent);
			}
		});
    }
    
    private void startServiceButton(){
    	Button startServiceButton = (Button) findViewById(R.id.button_start_service);
    	final RadioGroup serviceSelectionGroup = (RadioGroup) findViewById(R.id.radio_service);
    	
   	 	startServiceButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int selectedId = serviceSelectionGroup.getCheckedRadioButtonId();
				RadioButton selectedService = (RadioButton) findViewById(selectedId);
				Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class); //Intent is created in the "context" of this class, and should be get in the class specified in second parameter
				switch (selectedId) {
					case R.id.radio_location:
						//Toast.makeText(MyActivity.this, selectedService.getText()+" service started", Toast.LENGTH_LONG).show();
						Intent locService = new Intent(getApplicationContext(), LocationService.class);
				        startService(locService);
			        	intent1.putExtra(EXTRA_MESSAGE, selectedService.getText()+" service started"); //The first parameter is the key for the intent
			        	intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	startActivity(intent1);
						break;
					case R.id.radio_network:
						//Toast.makeText(MyActivity.this, selectedService.getText()+" service started", Toast.LENGTH_LONG).show();
						Intent networkService = new Intent(getApplicationContext(), NetworkService.class);
				        startService(networkService);
			        	intent1.putExtra(EXTRA_MESSAGE, selectedService.getText()+" service started"); //The first parameter is the key for the intent
			        	intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	startActivity(intent1);
						break;
					case R.id.radio_battery:
						//Toast.makeText(MyActivity.this, selectedService.getText()+" service started", Toast.LENGTH_LONG).show();
						Intent batteryService = new Intent(getApplicationContext(), BatteryService.class);
				        startService(batteryService);
			        	intent1.putExtra(EXTRA_MESSAGE, selectedService.getText()+" service started"); //The first parameter is the key for the intent
			        	intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	startActivity(intent1);
						break;
					case R.id.radio_bluetooth:
						//Toast.makeText(MyActivity.this, selectedService.getText()+" service started", Toast.LENGTH_LONG).show();
						Intent blueService = new Intent(getApplicationContext(), BluetoothService.class);
				        startService(blueService);
			        	intent1.putExtra(EXTRA_MESSAGE, selectedService.getText()+" service started"); //The first parameter is the key for the intent
			        	intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	startActivity(intent1);
						break;
					default:
						break;
				}
			}
   	 		
   	 	});
    }
    
    private void radioButtons(){
    	RadioGroup group = (RadioGroup) findViewById(R.id.radio_service);
    	group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

    		@Override
    		public void onCheckedChanged(RadioGroup group, int selectedId) {
    			switch (selectedId) {
					case R.id.radio_location:
						Toast.makeText(MyActivity.this, ((RadioButton) findViewById(selectedId)).getText()+" checked", Toast.LENGTH_SHORT).show();
						break;
					case R.id.radio_network:
						Toast.makeText(MyActivity.this, ((RadioButton) findViewById(selectedId)).getText()+" checked", Toast.LENGTH_SHORT).show();
						break;
					case R.id.radio_bluetooth:
						Toast.makeText(MyActivity.this, ((RadioButton) findViewById(selectedId)).getText()+" checked", Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
				}
    		}
    	});
    }

	protected void onStart(){
		super.onStart();
		Log.d("Reyhan","onStart");
	}

	protected void onRestart(){
		super.onRestart();
		Log.d("Reyhan","onRestart");
	}

	protected void onResume(){
		super.onResume();
		Log.d("Reyhan","onResume");
	}

	protected void onStop(){
		super.onStop();
		Log.d("Reyhan","onStop");
	}


	protected void onPause(){
		super.onPause();
		Log.d("Reyhan","onPause");
	}

    protected void onDestroy(){
        super.onDestroy();
		Log.d("Reyhan","onDestroy");
        Intent locIntent = new Intent(getApplicationContext(), LocationService.class);
        Intent blueIntent = new Intent(getApplicationContext(), BluetoothService.class);
        stopService(locIntent);
        stopService(blueIntent);
    }

}
