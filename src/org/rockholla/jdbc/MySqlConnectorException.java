package org.rockholla.jdbc;

/**
 * Exception related to the MySqlConnector
 * 
 * @author Patrick Force <patrickforce@gmail.com>
 *
 */
public class MySqlConnectorException extends Exception 
{

	private static final long serialVersionUID = 1L;

	public MySqlConnectorException() {}

	public MySqlConnectorException(String message) 
	{
		super(message);
	}

	public MySqlConnectorException(Throwable cause) 
	{
		super(cause);
	}

	public MySqlConnectorException(String message, Throwable cause) 
	{
		super(message, cause);
	}

}
