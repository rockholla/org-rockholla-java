/*
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

package org.rockholla.jdbc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * A connector to a MySQL database
 * 
 * @author rockholla
 *
 */
public class MySqlConnector 
{
	
	/** log4j logger */
	static final Logger logger = Logger.getLogger(MySqlConnector.class);
	
	/** the host name */
	protected String _host;
	/** the port */
	protected int _port = 3306;
	/** the name of the database */
	protected String _database;
	/** the username */
	protected String _username;
	/** the password */
	protected String _password;
	
	/** Optional: used to defined production vs. dev, etc. */
	public String environment;
	
	/** the java.sql.Connection */
	protected Connection _connection;
	
	/** a list of SQL commands, queue */
	protected ArrayList<String> _batch = new ArrayList<String>();
	
	/**
	 * Constructor
	 * 
	 * @param host		the host name
	 * @param username	the username
	 * @param password	the password
	 */
	public MySqlConnector(String host, String username, String password)
	{
		this._host = host;
		this._username = username;
		this._password = password;
	}
	
	/**
	 * Constructor
	 * 
	 * @param host		the host name
	 * @param database	the database name
	 * @param username	the username
	 * @param password	the password
	 */
	public MySqlConnector(String host, String database, String username, String password) 
	{
		
		this._host = host;
		this._database = database;
		this._username = username;
		this._password = password;
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param host		the host name
	 * @param port		the port
	 * @param database	the database name
	 * @param username	the username
	 * @param password	the password
	 */
	public MySqlConnector(String host, int port, String database, String username, String password) 
	{
		
		this._host = host;
		this._database = database;
		this._username = username;
		this._password = password;
		if(port >= 0) this._port = port;
		
	}
	
	/**
	 * Gets the host name for this connection
	 * 
	 * @return	the host name
	 */
	public String getHost()
	{
		return this._host;
	}
	
	public String getUsername()
	{
		return this._username;
	}
	
	public String getPassword()
	{
		return this._password;
	}
	
	/**
	 * The main connection, will run automatically before starting a command or series of related commands
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedEncodingException 
	 */
	protected void _connect() throws SQLException, ClassNotFoundException, UnsupportedEncodingException 
	{

		if(this._connection == null || this._connection.isClosed()) 
		{
			Class.forName("com.mysql.jdbc.Driver");
			this._connection = DriverManager.getConnection(
					"jdbc:mysql://" + this._host + ":" + this._port + (this._database != null ? "/" + URLEncoder.encode(this._database, "UTF-8") : "") + "?user=" + URLEncoder.encode(this._username, "UTF-8") + "&password=" + URLEncoder.encode(this._password, "UTF-8")
			);
		}
		
	}
	
	/**
	 * Disconnects from the database, run after each command or series of commands
	 * 
	 * @throws MySqlConnectorException
	 */
	protected void _disconnnect() throws MySqlConnectorException 
	{
		
		try 
		{
			if(this._connection != null && !this._connection.isClosed()) 
			{
				this._connection.close();
			}
			this._connection = null;
		} 
		catch(Exception exception) 
		{
			throw new MySqlConnectorException(exception);
		}
		
	}
	
	/**
	 * Adds a SQL statement to the local batch, queue
	 * 
	 * @param sql	the SQL statement
	 */
	public void addToBatch(String sql) 
	{
		this._batch.add(sql);
	}
	
	/**
	 * Removes all statements from the local batch, queue
	 * 
	 */
	public void clearBatch() 
	{
		this._batch.clear();
	}
	
	/**
	 * Gets the number of pending SQL statements in the local batch, queue
	 * 
	 * @return	the number of pending SQL statements
	 */
	public int getBatchCount() 
	{
		return this._batch.size();
	}
	
	/**
	 * Executes the local batch, queue of SQL statements
	 * 
	 * @return Array total records updated per statement executed
	 * @throws MySqlConnectorException
	 */
	public int[] executeBatch() throws MySqlConnectorException 
	{
		
		int[] updateCounts = {};
		Statement statement = null;
		
		try 
		{
			this._connect();
			
			statement = this._connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			this._connection.setAutoCommit(false);
			for(String sql : this._batch) 
			{
				statement.addBatch(sql);
			}
			updateCounts = statement.executeBatch();
			this._connection.commit();
			
			statement.close();
			statement = null;
			this._disconnnect();
			logger.info("SUCCESSFULLY EXECUTED MYSQLCONNECTOR BATCH");
		} 
		catch(Exception exception) 
		{
			try 
			{
				this._connection.rollback();
				statement.close();
				statement = null;
				this._disconnnect();
				throw new MySqlConnectorException("Error in batch SQL: " + exception.getMessage() + "\n" + this.getBatch(), exception);
			} 
			catch(Exception exception1) 
			{
				throw new MySqlConnectorException("Error in batch SQL: " + exception1.getMessage() + "\n" + this.getBatch(), exception1);
			}
		}
		
		this._batch.clear();
		return updateCounts;
		
	}
	
	/**
	 * Executes an update of a single SQL statement
	 * 
	 * @param sql	the SQL statement including the update
	 * @return		the total records updated
	 * @throws MySqlConnectorException
	 */
	public int executeUpdate(String sql) throws MySqlConnectorException 
	{
		
		Statement statement = null;
		int newId = -1;
		
		try 
		{
			this._connect();
			
			statement = this._connection.createStatement();
			statement.executeUpdate(sql);
			ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
			while(resultSet.next()) 
			{
				newId = resultSet.getInt(1);
			}
			statement.close();
			statement = null;
			this._disconnnect();
			
			return newId;
			
		} 
		catch(Exception exception) 
		{
			try 
			{
				statement.close();
				statement = null;
				this._disconnnect();
				throw new MySqlConnectorException(exception);
			} 
			catch(Exception exception1) 
			{
				throw new MySqlConnectorException(exception);
			}
		}
		
	}
	
	/**
	 * Returns a result set
	 * 
	 * @param sql	the SQL to execute to get the result set
	 * @return		a JdbcResultSet containing JdbcResultRows
	 * @throws MySqlConnectorException
	 */
	public JdbcResultSet get(String sql) throws MySqlConnectorException 
	{
		
		JdbcResultSet jdbcResultSet = new JdbcResultSet();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try 
		{
			this._connect();
			
			statement = this._connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			ArrayList<String> columnNames = new ArrayList<String>();
			for(int i = 1; i <= columnCount; i++) 
			{
				columnNames.add(metaData.getColumnName(i));
			}
			
			while(resultSet.next()) 
			{
				JdbcResultRow jdbcResultRow = new JdbcResultRow();
				int i = 1;
				for(String columnName : columnNames) 
				{
					try
					{
						jdbcResultRow.put(columnName, resultSet.getObject(columnName));
					}
					catch(Exception exception)
					{
						jdbcResultRow.put(String.valueOf(i), resultSet.getObject(i));
					}
					jdbcResultSet.add(jdbcResultRow);
					i++;
				}
			}
			
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;
			this._disconnnect();
		} 
		catch(Exception exception) 
		{
			try 
			{
				resultSet.close();
				resultSet = null;
				statement.close();
				statement = null;
				this._disconnnect();
				throw new MySqlConnectorException(exception);
			} 
			catch(Exception exception1) 
			{
				throw new MySqlConnectorException(exception);
			}
		}
			
		return jdbcResultSet;
		
	}
	
	/**
	 * Gets the entire batch of SQL statements as a single SQL string
	 * 
	 * @return	a SQL string
	 */
	public String getBatch() 
	{
		
		String batch = "";
		for(String batchItem : this._batch) 
		{
			batch += batchItem + "\n";
		}
		return batch;
		
	}
	
}
