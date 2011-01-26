/**
 * 
 *	This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *	 
 */

package org.rockholla.array;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class for Arrays and Lists
 * 
 * @author rockholla
 *
 */
public class ArrayUtility 
{

	/**
	 * Locates a value within a list
	 * 
	 * @param value	the object value to locate
	 * @param list	the list within which to find the value
	 * @return		the index at which the value was found, -1 if not found
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
	
	/**
	 * Locates a value within an array
	 * 
	 * @param value	the object value to locate
	 * @param array	the array within which to find the value
	 * @return		the index at which the value was found, -1 if not found
	 */
	public static int find(Object value, Object[] array)
	{
		return find(value, Arrays.asList(array));
	}
	
}
