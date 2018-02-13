package edu.uci.seal.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DisplayLocationActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_location);
		
		Intent intent = getIntent();
		String location = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);//The parameter is the key of the intent, so it should be defined uniquely
		ImageView map = new ImageView(this);
		
		switch (location) {
			case "Irvine":
				map.setImageResource(R.drawable.irvine);
				break;
			case "Fairfax":
				map.setImageResource(R.drawable.fairfax);
				break;
			case "Tehran":
				map.setImageResource(R.drawable.tehran);
				break;
			case "Los Angles":
				map.setImageResource(R.drawable.ic_launcher);
				break;
		}
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.location_content);
		layout.addView(map);
	}
	
    protected void onDestroy(){
    	super.onDestroy();
    }
}