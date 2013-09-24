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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbo.memorizer.adapters.MessageListAdapter;
import com.pbo.memorizer.model.MessageModel;
import com.pbo.memorizer.util.MemorizerConstants;
import com.pbo.memorizer.util.StringUtils;

public class MainActivity extends Activity implements OnClickListener {
	
	public final static String MESSAGE_MODEL = "com.pbo.memorizer.messageModel";
	
	private boolean debugMode = true;
	
	private List<MessageModel> aofTotalList = new ArrayList<MessageModel>();
	private MessageModel currentAof;
	
	private ListView aofSelectionList;
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
		addItemsToAOFList();
	}
	
	
	private void addItemsToAOFList(){
		
		//First, get a reference to the resources
		Resources res = getResources();
		
		//Capture the list of aof values
		//final String[] aofValueList = res.getStringArray(R.array.aof_list);
		final String[] aofFullList = res.getStringArray(R.array.aof_FullList);
		
		Log.d("Checking List ...", "Number of Messages: " + aofFullList.length);
		
		
		for(String aof : aofFullList){
			//Split the string into a name value pair
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
		
		// Set the onclick listener for the ListView
		aofSelectionList.setOnItemClickListener(new OnItemClickListener() {
            
			public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
            	
				ListAdapter a = aofSelectionList.getAdapter();
				
				currentAof = (MessageModel)a.getItem(position);
				view.setBackgroundColor(getResources().getColor(R.color.secondaryblue));
				
            	// When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                		"Selected: " + ((MessageModel)a.getItem(position)).getSubject(), //getCheckedItemPosition(), // getCheckedItemPosition(), 
                		Toast.LENGTH_SHORT).show();
            }
            
		});
		
		
	}
	
	
	/** Called when the user clicks the button */
	public boolean validateMeassagePrep(){
				
		//Capture the text that should be masked
		
		if(currentAof != null && StringUtils.isNullorEmpty(currentAof.getMessage())){
			Toast.makeText(this, 
					R.string.message_error_nullOrEmpty,
					Toast.LENGTH_SHORT).show();
			
			return false;
		}
		
		return true;
	}



	@Override
	public void onClick(View v) {
		
		
		if(v.getId() == R.id.btn_mask){
			
			//Validate the message is ready
			validateMeassagePrep();
			
			if(currentAof != null){
				//Create an Intent that launches the Memorize page
				Intent obfIntent = new Intent(this, DisplayMessageObfuscatedActivity.class);
				
				//Add extra messages to the intent
				obfIntent.putExtra(MESSAGE_MODEL, currentAof);
				
				//Now start the activity
				startActivity(obfIntent);
			}	
			
		}
		else if(v.getId() == R.id.btn_easy){
			currentAof.calcNumWordsToHide(MemorizerConstants.EASY_FACTOR);
		}
		else if(v.getId() == R.id.btn_medium){
			currentAof.calcNumWordsToHide(MemorizerConstants.MEDIUM_FACTOR);
		}
		else if(v.getId() == R.id.btn_hard){
			currentAof.calcNumWordsToHide(MemorizerConstants.HARD_FACTOR);
		}
		
	}
		
}
