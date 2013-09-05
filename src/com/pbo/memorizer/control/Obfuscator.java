package com.pbo.memorizer.control;

public class Obfuscator {
	private String[] wipArrString; 
	
	public String obfuscateString(String message, int numWordsToRemove){
		String memorizingOutput = " ";
		int numOfWords = 0;
		
		// Calculate the number of words, replace that amount of random words with a *
		numOfWords = countWords(message);
		
		if(numOfWords > 0 && numWordsToRemove > 0 && numWordsToRemove < numOfWords){
			for(int i=0; i<numWordsToRemove;i++){
				int indexToRemove = Math.round((int)(numOfWords*Math.random()));
				
				wipArrString[indexToRemove] = "_____";
			}
			
		}
		
		int lengthOfMessage = wipArrString.length;
		memorizingOutput = "";
		
		for(int i = 0; i < lengthOfMessage; i++){
			if(i != lengthOfMessage-1){
				memorizingOutput += wipArrString[i] + " ";
			}
			else{
				memorizingOutput += wipArrString[i];
			}
			
		}
		
		return memorizingOutput;
	}
	public int countWords(String message){
			
		wipArrString = message.split(" ");
		
		int numOfWords = wipArrString.length;
		
		return numOfWords;
		
	}

}
