package com.pbo.memorizer.model;

import java.io.Serializable;

import com.pbo.memorizer.util.MemorizerConstants;
import com.pbo.memorizer.util.StringUtils;

public class MessageModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String subject;
	private String message;
	private String obfMessage = "Message not found"; //Default
	private int numWordsToHide;
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String[] getWordsAsArray(){
		
		String[] msgWords = null;
		
		if(!StringUtils.isNullorEmpty(this.message)){
			
			//Split the message on spaces.
			msgWords = this.message.split(" ");
		}
		
		return msgWords;
	}
	
	public String getObfMessage() {
		return obfMessage;
	}

	public void setObfMessage(String obfMessage) {
		this.obfMessage = obfMessage;
	}
	
	public String[] getObfMessageWordsAsArray(){
		
		String[] msgWords = null;
		
		if(!StringUtils.isNullorEmpty(this.obfMessage)){
			
			//Split the message on spaces.
			msgWords = this.obfMessage.split(" ");
		}
		
		return msgWords;
	}
	public int getTotalNumOfWords() {
		
		if(!StringUtils.isNullorEmpty(message)){
			return (getWordsAsArray().length);
		}
		
		return 0;
	}

	public int getNumWordsToHide() {
		if(this.numWordsToHide <= 0){
			this.calcNumWordsToHide(MemorizerConstants.EASY_FACTOR);
		}
		
		return numWordsToHide;
	}

	public void setNumWordsToHide(int numWordsToHide) {
		this.numWordsToHide = numWordsToHide;
	}
	
	public void calcNumWordsToHide(double percentageToHide){
		
		if(this.getMessage() != null){
			if(percentageToHide >= 1){
				this.setNumWordsToHide(this.getTotalNumOfWords()); 
			}
			else{
				double value = Math.round(this.getTotalNumOfWords() * percentageToHide);
				
				Double dblVal = Double.valueOf(value);
				
				//Set the num of words
				this.setNumWordsToHide(dblVal.intValue());
								
			}
		}
	}
	
	public MessageModel(){
		
	}
	
	public MessageModel(String msg) {
		
		setMessage(msg);
		
	}
	
	public MessageModel(String msg, int numWordsToHide) {
		
		setMessage(msg);
		setNumWordsToHide(numWordsToHide);
	}
	
	public MessageModel(String name, String msg) {
		
		setSubject(name);
		setMessage(msg);
		
	}
}
