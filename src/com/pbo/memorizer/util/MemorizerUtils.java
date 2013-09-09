package com.pbo.memorizer.util;

import android.util.Log;

import com.pbo.memorizer.model.MessageModel;

public class MemorizerUtils {
	
	public static String calculateDifficulty(double percentage, MessageModel msgModel){
		String numOfWords = "";
		
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
		
	public static MessageModel obfuscateMessage(MessageModel msgModel){
		
		if(!StringUtils.isNullorEmpty(msgModel.getMessage()) && 
				msgModel.getNumWordsToHide() > 0 && 
				msgModel.getNumWordsToHide() <= msgModel.getTotalNumOfWords()){
			
			String[] msgWords = msgModel.getWordsAsArray();
			int numWordsToRemove = msgModel.getNumWordsToHide();
			int totalNumOfWords = msgWords.length;
			
			for(int i = 0; i < numWordsToRemove; i++){
				
				//Figure out which random word to remove
				int indexToRemove = calcIndexToRemove(totalNumOfWords);
				
				if(msgWords[indexToRemove].equals(MemorizerConstants.HIDE_WORD_STRING)){
					
					//Check to make sure we aren't replacing an already replaced word
					while(msgWords[indexToRemove].equals(MemorizerConstants.HIDE_WORD_STRING)){
						
						//Recalculate a new index since the last has already been taken
						indexToRemove = calcIndexToRemove(totalNumOfWords);
					}
									
				}
				
				//Hide the word
				msgWords[indexToRemove] = MemorizerConstants.HIDE_WORD_STRING;
			}
			
			//Now put the obfuscated string back together again
			msgModel.setObfMessage(joinStringArray(msgWords, " "));
			
		}
		else{
			Log.d("Obfuscation error", "Error while trying to obfuscate text: " + msgModel.toString());
			return null;
		}
		
		return msgModel;
	}
	
	private static int calcIndexToRemove(int totalNumOfWords){
		
		//TODO Pretty sure we need some error catching here...
		int indexToRemove = (Double.valueOf((Math.round((totalNumOfWords * Math.random()))))).intValue();
		
		return indexToRemove;
	}
	
	private static String joinStringArray(String[] stringArr, String seperator){
		
		StringBuilder stringJoiner = new StringBuilder();
		
		//Iterate through and build the String with the seperator
		for(int i = 0; i < stringArr.length; i++) {
			stringJoiner.append(stringArr[i]);
			if(i != stringArr.length-1){
				stringJoiner.append(seperator);
			}
		}
		
		return stringJoiner.toString();
	}
}
