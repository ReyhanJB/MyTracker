package edu.uci.seal.testapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);//The parameter is the key of the intent, so it should be defined uniquely
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setTextColor(Color.GRAY);
		textView.setText(message);
		//The text box in this view will be created dynamically, not statically specified in the layout XML file
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
		layout.addView(textView);
		new Thread(new Runnable()
	    {
	        @Override
	        public void run()
	        {
	            try
	            {
	                Thread.sleep(2000);
	                finish();
	            }
	            catch (InterruptedException e)
	            {
	                e.printStackTrace();
	            }
	        }
	    }).start();
		
	}
	
    protected void onDestroy(){
    	super.onDestroy();
    }

}