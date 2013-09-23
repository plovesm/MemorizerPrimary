package com.pbo.memorizer.primary;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbo.memorizer.adapters.MessageListAdapter;
import com.pbo.memorizer.model.MessageModel;
import com.pbo.memorizer.util.StringUtils;

public class MainActivity extends Activity implements OnClickListener {
	
	public final static String MESSAGE_MODEL = "com.pbo.memorizer.messageModel";
	
	private boolean debugMode = true;
	
	private List<MessageModel> aofTotalList = new ArrayList<MessageModel>();
	private MessageModel currentAof;
	
	private ListView aofSelectionList;
	private TextView msgInputText;
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
		aofSelectionList = (ListView) findViewById(R.id.aof_select_view);
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
		//final String[] aofValueList = res.getStringArray(R.array.aof_list);
		final String[] aofFullList = res.getStringArray(R.array.aof_FullList);
		
		Log.d("Checking List ...", "Number of Messages: " + aofFullList.length);
		
		
		for(String aof : aofFullList){

			if(aof.contains("~")){

				//First split it into name value pairs
				String[] aofSplit = aof.split("~");
				
				//Then create a new object and add it to list
				if(aofSplit.length == 2){
					
					aofTotalList.add(new MessageModel(aofSplit[0], aofSplit[1]));
				
				}
				
				
			}
			
		}
		
		MessageListAdapter msgAdapter = new MessageListAdapter(this, R.layout.aof_selection_list, aofTotalList);
		
		aofSelectionList.setAdapter(msgAdapter);
		
		// TODO
		aofSelectionList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
            	
            	// When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                		"Selected: " + ((MessageModel)aofSelectionList.getSelectedItem()).getSubject(), 
                		Toast.LENGTH_SHORT).show();
            }
		});
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
			
			if(numOfWordsToHide == 0){
				//Can't have a zero number of words to mask
				Toast.makeText(this, 
						R.string.numOfWords_error_nullOrEmpty,
						Toast.LENGTH_SHORT).show();
				
				return null;
			}
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
		else if(v.getId() == R.id.btn_easy){
			numOfWordsText.setText(calculateDifficulty(.25));
			//btnEasy.setBackgroundColor(R.color.secondaryblue);
		}
		else if(v.getId() == R.id.btn_medium){
			numOfWordsText.setText(calculateDifficulty(.5));
		}
		else if(v.getId() == R.id.btn_hard){
			numOfWordsText.setText(calculateDifficulty(.75));
		}
		else if(v.getId() == R.id.aof_select_view){
			
			//Can't have a null or empty number of words to mask
			Toast.makeText(this, 
					"Selected: " + ((MessageModel)aofSelectionList.getSelectedItem()).getSubject(),
					Toast.LENGTH_SHORT).show();
		
		}
			
		
	}
	
	private String calculateDifficulty(double percentage){
		String numOfWords = "";
		MessageModel msgModel = new MessageModel();
		msgModel.setMessage(msgInputText.getText().toString());
		
		if(msgModel != null && msgModel.getMessage() != null){
			if(percentage >= 1){
				return String.valueOf(msgModel.getTotalNumOfWords()); 
			}
			else{
				double value = Math.round(msgModel.getTotalNumOfWords() * percentage);
				
				Double dblVal = Double.valueOf(value);
				
				//Set the num of words
				numOfWords = Integer.toString(dblVal.intValue());
								
			}
		}
		return numOfWords;
	}
}
