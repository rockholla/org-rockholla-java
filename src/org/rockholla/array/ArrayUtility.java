package org.rockholla.array;

import java.util.List;

/**
 * Helper class for Arrays and Lists
 * 
 * @author Patrick Force <patrickforce@gmail.com>
 *
 */
public class ArrayUtility 
{

	/**
	 * Locates a value within a list
	 * 
	 * @param value	the object value to locate
	 * @param list	the list within which to find the value
	 * @return		the index at which the value was found
	 */
	@SuppressWarnings("rawtypes")
	public static int find(Object value, List list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).equals(value))
			{
				return i;
			}
		}
		return -1;
	}
	
}
