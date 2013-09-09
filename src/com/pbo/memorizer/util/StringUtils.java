package com.pbo.memorizer.util;

public class StringUtils {
	
	public static boolean isNullorEmpty(String test){
		
		if(test.equals(null) || test.equals("")){
			return true;
		}
		
		return false;
	}
	
}
