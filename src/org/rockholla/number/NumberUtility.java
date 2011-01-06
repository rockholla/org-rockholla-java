package org.rockholla.number;

/**
 * Helper class for all numeric types
 * 
 * @author Patrick Force <patrickforce@gmail.com>
 *
 */
public class NumberUtility 
{

	/** standard format enum */
	public static enum FORMAT 
	{
		TWO_CHARACTER_INTEGER
	}
	
	/**
	 * Formats an integer in a designated FORMAT
	 * 
	 * @param number	the integer to format
	 * @param format	the FORMAT
	 * @return			a string representation of the number
	 */
	public static String format(int number, FORMAT format) 
	{
		
		String result = String.valueOf(number);
		
		switch(format) 
		{
			case TWO_CHARACTER_INTEGER: 
			{
				if(result.length() == 1) 
				{
					result = "0" + result;
				} 
				else if(result.length() == 0) 
				{
					result = "00";
				}
				break;
			}
		}
		
		return result;
		
	}
	
	/**
	 * Determines if a particular string value could be a valid integer
	 * 
	 * @param value	the value to check	
	 * @return		true if the input value could be an integer value
	 */
	public static boolean isInteger(String value) 
	{
		
		try 
		{
			Integer.parseInt(value);
			return true;
		} 
		catch(Exception exception) 
		{
			return false;
		}
		
	}
	
	/**
	 * Determines if a particular string value could be a valid double
	 * 
	 * @param value	the value to check
	 * @return		true if the input value could be a double value
	 */
	public static boolean isDouble(String value)
	{
		
		try
		{
			if(value.indexOf(".") < 0)
			{
				return false;
			}
			Double.parseDouble(value);
			return true;
		}
		catch(Exception exception)
		{
			return false;
		}
		
	}
	
}
