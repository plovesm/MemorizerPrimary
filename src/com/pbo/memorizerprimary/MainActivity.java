package com.pbo.memorizerprimary;


import com.pbo.memorizer.model.MessageModel;
import com.pbo.memorizer.util.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	public final static String MESSAGE_MODEL = "com.pbo.memorizer.messageModel";
	
	private boolean debugMode = true;
	
	private TextView msgInputText;
	private Spinner aofSpinner;
	private EditText numOfWordsText;
	private Button btnMask;
	private Button btnEasy;
	private Button btnMedium;
	private Button btnHard;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//First we build (initialize) all the components
		buildComponents();
		
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void buildComponents(){
		
		//Initialize all of the components
		aofSpinner = (Spinner) findViewById(R.id.aof_spinner);
		msgInputText = (TextView) findViewById(R.id.output_view);
		msgInputText.setMovementMethod(new ScrollingMovementMethod());
		numOfWordsText = (EditText) findViewById(R.id.edit_numOfWordsToRemove);
		btnMask = (Button) findViewById(R.id.btn_mask);
		btnMask.setOnClickListener(this);
		btnEasy = (Button) findViewById(R.id.btn_easy);
		btnEasy.setOnClickListener(this);
		btnMedium = (Button) findViewById(R.id.btn_medium);
		btnMedium.setOnClickListener(this);
		btnHard = (Button) findViewById(R.id.btn_hard);
		btnHard.setOnClickListener(this);
		
		//Then we need to add the items to the dropdown
		addItemsToAOFSpinner();
	}
	
	
	private void addItemsToAOFSpinner(){
		
		//First, get a reference to the resources
		Resources res = getResources();
		
		//Capture the list of aof values
		final String[] aofValueList = res.getStringArray(R.array.aof_list);
		
		//Next, set up the drop down domain
		ArrayAdapter<CharSequence> aofNameList = ArrayAdapter.createFromResource(this, 
				R.array.aof_names, android.R.layout.simple_spinner_item);
		
		//And assign it to the drop down
		aofNameList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//Add the listener to the drop down and set the text 
		aofSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				
				//Take the selection and populate the text field
				msgInputText.setText(aofValueList[pos]);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				if(debugMode == true){
					Log.d("No Selection", "Nothing was selected in the spinner");
				}
			}
		});
		
		
		aofSpinner.setAdapter(aofNameList);
	}
	
	
	/** Called when the user clicks the button */
	public MessageModel prepToObfuscateText(){
		
		MessageModel messageObj = new MessageModel();
				
		//Capture the text that should be masked
		String memorizeTarget = msgInputText.getText().toString();
		messageObj.setMessage(memorizeTarget);
		
		if(StringUtils.isNullorEmpty(memorizeTarget)){
			Toast.makeText(this, 
					R.string.message_error_nullOrEmpty,
					Toast.LENGTH_SHORT).show();
			
			return null;
		}
		
		//Capture the number of words that should be masked
		Editable chkNumOfWordsText = numOfWordsText.getText();
		int numOfWordsToHide;
		
		//Check for null or empty and go from there
		if(!StringUtils.isNullorEmpty(chkNumOfWordsText.toString())){
			Log.d("RetrievingNumToHide", "Number to hide is not null or empty");
			
			numOfWordsToHide = Integer.valueOf(chkNumOfWordsText.toString());
			
			if(numOfWordsToHide >= messageObj.getTotalNumOfWords()){
				//Can't have a null or empty number of words to mask
				Toast.makeText(this, 
						R.string.hide_error_greaterThanWords,
						Toast.LENGTH_SHORT).show();
				
				return null;
			}
		}
		else{
			Log.d("NumToHideNull", "Number to hide is null or empty");
			
			//Can't have a null or empty number of words to mask
			Toast.makeText(this, 
					R.string.numOfWords_error_nullOrEmpty,
					Toast.LENGTH_SHORT).show();
			
			return null;
			
		}
		
		//Set the number of words to hide
		messageObj.setNumWordsToHide(numOfWordsToHide);
		
		//All is well
		return messageObj;
	}



	@Override
	public void onClick(View v) {
		
		
		if(v.getId() == R.id.btn_mask){
			
			//Call the obfuscate text method and move to next activity
			MessageModel msgModel = prepToObfuscateText();
			
			if(msgModel != null){
				//Create an Intent that launches the Memorize page
				Intent obfIntent = new Intent(this, DisplayMessageObfuscatedActivity.class);
				
				//Add extra messages to the intent
				obfIntent.putExtra(MESSAGE_MODEL, msgModel);
				
				//Now start the activity
				startActivity(obfIntent);
			}	
			
		}
		if(v.getId() == R.id.btn_easy){
			numOfWordsText.setText("4");
		}
		if(v.getId() == R.id.btn_medium){
			numOfWordsText.setText("6");
		}
		if(v.getId() == R.id.btn_hard){
			numOfWordsText.setText("8");
		}
	}
}
