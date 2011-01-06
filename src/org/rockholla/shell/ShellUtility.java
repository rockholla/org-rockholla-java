package org.rockholla.shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author Patrick Force <patrickforce@gmail.com>
 *
 */
public class ShellUtility 
{

	/** log4j logger */
	static final Logger logger = Logger.getLogger(ShellUtility.class);
	
	/**
	 * Runs a command with arguments or a path to a shell script
	 * 
	 * @param args			the command and arguments or the shell script path
	 * @return				a list of line results for each "stdout", and "stderr", the output of the script
	 * @throws Exception
	 */
	public static ArrayList<String> run(String...args) throws Exception
	{			
		ArrayList<String> results = new ArrayList<String>();
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();
		
		BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		
		while((line = stdout.readLine()) != null)
		{
			results.add(line);
		}
 
		return results;
	}
	
	/**
	 * Runs a shell command in a new thread
	 * 
	 * @param args			the command and arguments or the shell script path
	 * @throws Exception	a list of line results for each "stdout", and "stderr", the output of the script
	 */
	public static void runInNewThread(String...args) throws Exception
	{
		class ShellThread implements Runnable
		{
			public String[] args;
			
			public void run()
			{
				try
				{
					ShellUtility.run(args);
				}
				catch(Exception exception)
				{
					logger.error(exception.getMessage(), exception);
				}
			}
		}
		ShellThread shellThread = new ShellThread();
		shellThread.args = args;
		Thread newThread = new Thread(shellThread);
		newThread.start();
	}
	
	public static void main(String[] args) throws Exception
	{
		ArrayList<String> results = run("sh", "-c", "/usr/local/mysql/bin/mysqldump -h localhost -u roots -p32watdod drupal | gzip > /Users/pbf2105/test.sql.gz");
		for(String line : results)
		{
			System.out.println(line);
		}
	}
	
}
