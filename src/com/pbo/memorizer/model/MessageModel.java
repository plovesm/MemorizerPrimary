package com.pbo.memorizer.model;

import java.io.Serializable;

import com.pbo.memorizer.util.StringUtils;

public class MessageModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private String obfMessage = "Message not found"; //Default
	private int numWordsToHide;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getObfMessage() {
		return obfMessage;
	}

	public void setObfMessage(String obfMessage) {
		this.obfMessage = obfMessage;
	}

	public int getTotalNumOfWords() {
		if(!StringUtils.isNullorEmpty(message)){
			return ((message.split(" ")).length);
		}
		
		return 0;
	}

	public int getNumWordsToHide() {
		return numWordsToHide;
	}

	public void setNumWordsToHide(int numWordsToHide) {
		this.numWordsToHide = numWordsToHide;
	}
	
	public MessageModel() {
				
	}
	
	public MessageModel(String msg) {
		
		setMessage(msg);
		
	}
	
	public MessageModel(String msg, int numWordsToHide) {
		
		setMessage(msg);
		setNumWordsToHide(numWordsToHide);
	}
	
}
