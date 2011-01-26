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

package org.rockholla.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.Ostermiller.util.Base64;

/**
 * Utility for dealing with HTTP communication tasks
 * 
 * @author rockholla
 *
 */

public class HttpUtility 
{

	/**
	 * Performs an HTTP get
	 * 
	 * @param url		the URL to get
	 * @param username	the HTTP username, if HTTP auth is in place for the URL
	 * @param password	the HTTP password, if HTTP auth is in place for the URL
	 * @return			the string content from the URL
	 * @throws Exception
	 */
	public static String getUrlData(String url, String username, String password) throws Exception
	{
		URL urlObject = new URL(url);
        URLConnection urlConn = urlObject.openConnection();
        
        if(username != null && password != null)
        {
        	String encodedAuth = Base64.encode(username + ":" + password);
        	urlConn.setRequestProperty("Authorization", "Basic " + encodedAuth);
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String inputLine;
        String out = "";
        while ((inputLine = in.readLine()) != null)
        {
        	out += inputLine;
        }
        in.close();
        return out;

	}

	/**
	 * Performs an HTTP get
	 * 
	 * @param url	the URL to get
	 * @return		teh string content from the URL
	 * @throws Exception
	 */
	public static String getUrlData(String url) throws Exception
	{
		return getUrlData(url, null, null);
	}
	
}
