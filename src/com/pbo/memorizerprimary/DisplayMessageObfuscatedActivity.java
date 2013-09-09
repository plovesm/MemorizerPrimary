package com.pbo.memorizerprimary;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.pbo.memorizer.control.Obfuscator;
import com.pbo.memorizer.model.MessageModel;

public class DisplayMessageObfuscatedActivity extends Activity implements OnClickListener{

	private TextView obfMessageView;
	private Button btnRetry;
	private Button btnAnswer;
	
	private MessageModel originalMsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message_obfuscated);
		
		buildComponents();
	}
	
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void buildComponents(){
		//Instantiate the UI elements
		obfMessageView = (TextView) findViewById(R.id.view_obfuscated_msg);
		//obfMessageView.setMovementMethod(new ScrollingMovementMethod());
		btnRetry = (Button) findViewById(R.id.btn_retry);
		btnAnswer = (Button) findViewById(R.id.btn_answer);
		
		//Set Listeners
		btnRetry.setOnClickListener(this);
		btnAnswer.setOnClickListener(this);
		
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		initializeComponents();
	}
	
	private void initializeComponents(){
		
		//Retrieve the origin Intent
		Intent intent = getIntent();
		originalMsg = (MessageModel) intent.getSerializableExtra(MainActivity.MESSAGE_MODEL);
		
		//Instantiate the Obfuscator
		Obfuscator ob = new Obfuscator();
		
		//Determine how many words are in the message
		int numOfWords = ob.countWords(originalMsg.getMessage());
		int numToMask = originalMsg.getNumWordsToHide();
		
		//Check to make sure the number of words is legit and then obfuscate it.
		if(numToMask < numOfWords && numToMask != 0){
			originalMsg.setObfMessage(ob.obfuscateString(originalMsg.getMessage(), numToMask));
		}
				
		//Create a text view to output the obfuscated message
		obfMessageView.setText(originalMsg.getObfMessage());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_display_message_obfuscated,
				menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void toggleAnswer(){
		//Toggle answer between obfuscated and full
		if(obfMessageView.getText().equals(originalMsg.getObfMessage())){
			
			//Set text to original
			obfMessageView.setText(originalMsg.getMessage());
			btnAnswer.setText(R.string.button_hide_answer);
			
		}
		else{
			
			//Set text to obfuscated
			obfMessageView.setText(originalMsg.getObfMessage());
			btnAnswer.setText(R.string.button_show_answer);
		}
	}
	
	
	@Override
	public void onClick(View v) {

		if(v.getId() == R.id.btn_answer){
			
			//Toggle answer between obfuscated and full
			toggleAnswer();
						
			
		}
		else if (v.getId() == R.id.btn_retry){
			
			//Create an Intent that launches the Memorize page
			Intent mainIntent = new Intent(this, MainActivity.class);
			
			//Now start the activity
			startActivity(mainIntent);
			
			btnRetry.setBackgroundColor(getTitleColor());
			
		}
		else if (v.getId() == R.id.view_obfuscated_msg){
			
			toggleAnswer();
		}
		
		
		
	}

}
