/**
 * 
 */
package com.pbo.memorizer.primary;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pbo.memorizer.model.MessageModel;
import com.pbo.memorizer.util.MemorizerUtils;

/**
 * @author ott1982
 *
 */
public class TestDifficultyCalculations {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.pbo.memorizer.util.MemorizerUtils#calculateDifficulty(double, com.pbo.memorizer.model.MessageModel)}.
	 */
	@Test
	public void testCalculateDifficulty() {
		MessageModel msgModel = new MessageModel();;
		
		msgModel.setMessage("We believe in God, the Eternal Father, and in His Son, Jesus Christ, and in the Holy Ghost.");
		
		assertEquals(MemorizerUtils.calculateDifficulty(.25, msgModel), "5");
		assertEquals(MemorizerUtils.calculateDifficulty(.50, msgModel), "9");
		assertEquals(MemorizerUtils.calculateDifficulty(.75, msgModel), "14");
		
	}

}
