/**
 * 
 */
package com.pbo.memorizer.primary;

import org.junit.Test;

import com.pbo.memorizer.model.MessageModel;
import com.pbo.memorizer.util.MemorizerUtils;

import junit.framework.TestCase;

/**
 * @author ott1982
 *
 */
public class TestMemorizerUtils extends TestCase {

	MessageModel msgModel;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println("Setting up...");
		msgModel = new MessageModel();
						
	}
	
	@Test
	public void testDifficultyEasy(){
		
		msgModel.setMessage("We believe in God, the Eternal Father, and in His Son, Jesus Christ, and in the Holy Ghost.");
		
		assertEquals(MemorizerUtils.calculateDifficulty(.25, msgModel), "5");
		
	}
	
	@Test
	public void testDifficultyMedium(){
		
		msgModel.setMessage("We believe in God, the Eternal Father, and in His Son, Jesus Christ, and in the Holy Ghost.");
		
		assertEquals(MemorizerUtils.calculateDifficulty(.50, msgModel), "9");
		
	}
	
	@Test
	public void testDifficultyHard(){
		
		msgModel.setMessage("We believe in God, the Eternal Father, and in His Son, Jesus Christ, and in the Holy Ghost.");
		
		assertEquals(MemorizerUtils.calculateDifficulty(.75, msgModel), "14");
		
	}
	
}
