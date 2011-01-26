package org.rockholla.array;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.rockholla.TestHelper;

public class ArrayUtilityTest 
{
	
	@Rule public TestName name = new TestName();
	final Logger logger = Logger.getLogger(ArrayUtilityTest.class);

	@Test
	public void testFind() 
	{
		
		logger.info(TestHelper.getRunningMethodNotification(name.getMethodName()));
		ArrayList<Object> list = new ArrayList<Object>();
		
		Color one = new Color(16777215);
		Color two = new Color(6619135);
		Color three = new Color(6618936);
		Color four = new Color(6584376);
		Color five = new Color(6619135);
		
		list.add(one);
		list.add(two);
		list.add(three);
		list.add(four);
		
		int result;
		
		// Testing finding the exact object
		result = ArrayUtility.find(four, list);
		logger.info("Result of trying to find 'four': " + result);
		assertTrue(result == 3);
		
		// Testing search for different object, but with same construction in certain cases, as is the case with a 'Color' (same RGB value)
		result = ArrayUtility.find(five, list);
		logger.info("Result of trying to find 'five': " + result);
		assertTrue(result == 1);
		
		// Testing search for a totally different kind of thing
		result = ArrayUtility.find("a string", list);
		logger.info("Result of trying to find a string: " + result);
		assertTrue(result == -1);
		
		// Now let's make sure the overloaded one works, to find within an array
		logger.info("Now testing the overloaded find method with a search w/in a native array...");
		
		Color[] array = {one, two, three, four};
		// Testing finding the exact object
		result = ArrayUtility.find(four, array);
		logger.info("Result of trying to find 'four': " + result);
		assertTrue(result == 3);
		
		// Testing search for different object, but with same construction in certain cases, as is the case with a 'Color' (same RGB value)
		result = ArrayUtility.find(five, array);
		logger.info("Result of trying to find 'five': " + result);
		assertTrue(result == 1);
		
		// Testing search for a totally different kind of thing
		result = ArrayUtility.find("a string", array);
		logger.info("Result of trying to find a string: " + result);
		assertTrue(result == -1);
		
	}

}
