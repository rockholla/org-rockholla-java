package org.rockholla.array;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

public class ArrayUtilityTest 
{

	@Test
	public void testFind() 
	{
		
		System.out.println("-------------- Running ArrayUtilityTest.testFind ... ---------------");
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
		System.out.println("Result of trying to find 'four': " + result);
		assertTrue(result == 3);
		
		// Testing search for different object, but with same construction in certain cases, as is the case with a 'Color' (same RGB value)
		result = ArrayUtility.find(five, list);
		System.out.println("Result of trying to find 'five': " + result);
		assertTrue(result == 1);
		
		// Testing search for a totally different kind of thing
		result = ArrayUtility.find("a string", list);
		System.out.println("Result of trying to find a string: " + result);
		assertTrue(result == -1);
		
		// Now let's make sure the overloaded one works, to find within an array
		System.out.println("Now testing the overloaded find method with a search w/in a native array...");
		
		Color[] array = {one, two, three, four};
		// Testing finding the exact object
		result = ArrayUtility.find(four, array);
		System.out.println("Result of trying to find 'four': " + result);
		assertTrue(result == 3);
		
		// Testing search for different object, but with same construction in certain cases, as is the case with a 'Color' (same RGB value)
		result = ArrayUtility.find(five, array);
		System.out.println("Result of trying to find 'five': " + result);
		assertTrue(result == 1);
		
		// Testing search for a totally different kind of thing
		result = ArrayUtility.find("a string", array);
		System.out.println("Result of trying to find a string: " + result);
		assertTrue(result == -1);
		
	}

}
