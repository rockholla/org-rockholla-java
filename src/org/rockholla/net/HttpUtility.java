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
	
//	public static void main(String[] args) throws Exception
//	{
//		System.out.println(getUrlData("http://seasnail.cc.columbia.edu:8181/solr/blackrock/admin/dataimport.jsp?handler=/forest-data-import", "blackrock", "75tromb15"));
//	}
	
}
