/**
 * 
 */
package com.pbo.memorizer.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pbo.memorizer.model.MessageModel;
import com.pbo.memorizer.primary.R;

/**
 * @author ott1982
 *
 */
public class MessageListAdapter extends ArrayAdapter<MessageModel> {
	
	private List<MessageModel> msgList;
	private Context context;
	int layoutResourceId;
	
	public MessageListAdapter(Context context,
			int textViewResourceId, List<MessageModel> msgList) {
		super(context, textViewResourceId, msgList);
		
		this.context = context;
		this.msgList = msgList;
		this.layoutResourceId = textViewResourceId;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		MessageHolder msgHolder = null;
		
		if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
            		Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            
            
        }
		else{
			msgHolder = (MessageHolder)row.getTag();
		}
		
		msgHolder = new MessageHolder();
        msgHolder.msgTitle = (TextView)row.findViewById(R.id.aof_listing_title);
        msgHolder.msgBody = (TextView)row.findViewById(R.id.aof_listing_body);
		
		MessageModel msg = msgList.get(position);
		
		if(msg != null){
		Log.d("Message not null: ", msg.toString() + " Title: " + msg.getSubject() + " Body: " + msg.getMessage());
		
		msgHolder.msgTitle.setText(msg.getSubject());
		msgHolder.msgBody.setText(msg.getMessage());
		}
		else{
			Log.d("Message is null: ", msgList.size() + " long"); 
		}
		
		return row;
	}
	
	static class MessageHolder{
		
		TextView msgTitle;
		TextView msgBody;
		
		
	}

}
