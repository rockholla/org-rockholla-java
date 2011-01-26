/**
 * 
 *	This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
<<<<<<< HEAD
 *  (at your option) any later version.
=======
 *   (at your option) any later version.
>>>>>>> 6898fe5c5ac2ca005987ea3ea2cc9a29d6604156
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

package org.rockholla.jdbc;

import java.util.HashMap;

/**
 * A JdbcResultRow is a representation of a database row
 * 
 * @author rockholla
 *
 */
public class JdbcResultRow extends HashMap<String, Object> 
{

	private static final long serialVersionUID = 1L;
	
	public JdbcResultRow() {}
	
	/**
	 * Gets a string value of a particular row key
	 * 
	 * @param key	the database column name value to retrieve
	 * @return		a string value
	 */
	public String getString(String key)
	{
		return String.valueOf(this.get(key));
	}
	
	/**
	 * Gets the string value of a particular row key
	 * 
	 * @param key	the int value of the row column index
	 * @return		a string value
	 */
	public String getString(int key)
	{
		return String.valueOf(this.get(String.valueOf(key)));
	}
	
	
}
