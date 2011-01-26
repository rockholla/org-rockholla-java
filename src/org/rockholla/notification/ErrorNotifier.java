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

package org.rockholla.notification;

import java.io.File;

import org.rockholla.file.FileUtility;



/**
 * Generic error notification utility
 * 
 * @author rockholla
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
