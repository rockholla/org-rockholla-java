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

package org.rockholla.shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Utility for working with command line and shell tasks
 * 
<<<<<<< HEAD
 * 
=======
>>>>>>> 6898fe5c5ac2ca005987ea3ea2cc9a29d6604156
 * @author rockholla
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
