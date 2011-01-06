package org.rockholla.jdbc;

import java.util.HashMap;

/**
 * A JdbcResultRow is a representation of a database row
 * 
 * @author Patrick Force <patrickforce@gmail.com>
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
