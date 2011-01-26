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

package org.rockholla.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for strings
 * 
 * @author rockholla
 *
 */
public class StringUtility 
{

	/**
	 * Removes duplicate whitespace from a string, i.e. two or more consecutive spaces
	 * 
	 * @param inputString	the string to process
	 * @return				the processed string
	 */
	public static CharSequence removeDuplicateWhitespace(CharSequence inputString) 
	{ 
		String patternString = "\\s+"; 
		String replaceString = " "; 
		Pattern pattern = Pattern.compile(patternString); 
		Matcher matcher = pattern.matcher(inputString); 
		return matcher.replaceAll(replaceString); 
	}
	
	/**
	 * Replaces all characters in a string that are non-alphanumeric with a designated string
	 * 
	 * @param string	a string that may include both non-alphanumeric and alphanumeric characters (excluding whitespace)
	 * @param string	a character/string used in replacement
	 * @return	the string with only alphanumeric characters (and the replaced character that may be a non-alphanumeric)
	 */
	public static String replaceNonAlphanumerics(String string, String replaceWith) 
	{	
		return string.replaceAll("[^\\sa-zA-Z0-9]", replaceWith);	
	}
	
	/**
	 * Replaces all accented/diacritic characters with their corresponding English alphabet character
	 * 
	 * @param s	the string containing accents/diacritics
	 * @return	the string with characters replaced
	 */
	public static String replaceDiacriticsAndAccents(String s) 
	{
		
	    s = s.replaceAll("[èéêë]","e");
	    s = s.replaceAll("[ûù]","u");
	    s = s.replaceAll("[ïî]","i");
	    s = s.replaceAll("[àâ]","a");
	    s = s.replaceAll("Ô","o");

	    s = s.replaceAll("[ÈÉÊË]","E");
	    s = s.replaceAll("[ÛÙ]","U");
	    s = s.replaceAll("[ÏÎ]","I");
	    s = s.replaceAll("[ÀÂ]","A");
	    s = s.replaceAll("Ô","O");
	    
	    return s;
		
	}
	
	/**
	 * Repeats a string sequence a designated amount of times
	 * 
	 * @param string	the string sequence to repeat
	 * @param times		the number of times to repeat the sequence
	 * @return			the resulting string
	 */
	public static String repeat(String string, int times)
	{
		
		String result = "";
		for(int i = 0; i < times; i++)
		{
			result += string;
		}
		return result;
		
	}
	
	/**
	 * Returns a portion of a string starting from the beginning for a designated amount of characters
	 * 
	 * @param string	the string from which to pull
	 * @param length	the number of characters from the beginning to pull
	 * @return			the resulting string
	 */
	public static String left(String string, int length)
	{
		return string.substring(0, length);
	}
	
	/**
	 * Returns a portion of a string starting from the end back a designated amount of characters
	 * 
	 * @param string	the string from which to pull
	 * @param length	the number of characters from then end to pull
	 * @return			the resulting string
	 */
	public static String right(String string, int length)
	{
		return string.substring(string.length() - length, string.length());
	}
	
	/**
	 * Count the number of occurrences of a character in a given string
	 * 
	 * @param haystack	the string to search
	 * @param needle	the character to search for
	 * @return			the number of occurrences
	 */
	public static int count(String haystack, char needle)
	{
	    int count = 0;
	    for (int i=0; i < haystack.length(); i++)
	    {
	        if (haystack.charAt(i) == needle)
	        {
	             count++;
	        }
	    }
	    return count;
	}

	
}
