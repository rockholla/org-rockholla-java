package org.rockholla.notification;

import java.io.File;

import org.rockholla.file.FileUtility;



/**
 * Generic error notification utility
 * 
 * @author Patrick Force <patrickforce@gmail.com>
 *
 */
public class ErrorNotifier 
{

	/**
	 * Notifies to a file of the using apps choosing
	 * 
	 * @param notification	the string notification
	 * @param file			the file to which the notification will be appended
	 */
	public static void notify(String notification, File file)
	{
		try
		{
			FileUtility.appendLineToFile(file.getAbsolutePath(), notification);
		}
		catch(Exception e) {}
	}
	
}
