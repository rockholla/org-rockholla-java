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

package org.rockholla.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.Ostermiller.util.Base64;

/**
 * Utility for dealing with HTTP communication tasks
 * 
 * @author rockholla
 *
 */

public class HttpUtility 
{

	static final Logger logger = Logger.getLogger(HttpUtility.class);
	
	/**
	 * Gets data from a URL
	 * 
	 * @param url		the URL to get
	 * @param username	the username for HTTP authentication (if required)
	 * @param password	the password for HTTP authentication (if required)
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
	 * @return		the string content from the URL
	 * @throws Exception
	 */
	public static String getUrlData(String url) throws Exception
	{
		return getUrlData(url, null, null);
	}
	
	/**
	 * Posts data to a URL
	 * 
	 * @param url			the URL to where data will be posted
	 * @param contentType	the content type of the data to post
	 * @param content		the actual content to post
	 * @return				an org.rockholla.net.HttpResponse
	 * @throws Exception
	 */
	public static HttpResponse postToUrl(String url, String contentType, String content) throws Exception
	{
		return postToUrl(url, contentType, content, null, null);
	}
	
	/**
	 * Posts data to a URL
	 * 
	 * @param url			the URL to where data will be posted
	 * @param contentType	the content type of the data to post
	 * @param content		the actual content to post
	 * @param username		the username for HTTP authentication (if required)
	 * @param password		the password for HTTP authentication (if required)
	 * @return				an org.rockholla.net.HttpResponse
	 * @throws Exception
	 */
	public static HttpResponse postToUrl(String url, String contentType, String content, String username, String password) throws Exception
	{
		return sendToUrl(url, contentType, content, "POST", username, password);
	}
	
	/**
	 * Puts data to a URL
	 * 
	 * @param url			the URL to where data will be put
	 * @param contentType	the content type of the data to put
	 * @param content		the actual content to put
	 * @return				an org.rockholla.net.HttpResponse
	 * @throws Exception
	 */
	public static HttpResponse putToUrl(String url, String contentType, String content) throws Exception
	{
		return putToUrl(url, contentType, content, null, null);
	}
	
	/**
	 * Puts data to a URL
	 * 
	 * @param url			the URL to where data will be put
	 * @param contentType	the content type of the data to put
	 * @param content		the actual content to put
	 * @param username		the username for HTTP authentication (if required)
	 * @param password		the password for HTTP authentication (if required)
	 * @return				an org.rockholla.net.HttpResponse
	 * @throws Exception
	 */
	public static HttpResponse putToUrl(String url, String contentType, String content, String username, String password) throws Exception
	{
		return sendToUrl(url, contentType, content, "PUT", username, password);
	}
	
	/**
	 * Sends data to a URL
	 * 
	 * @param url			the URL to where data will be sent
	 * @param contentType	the content type of the data to send
	 * @param content		the actual content to be sent
	 * @param method		the HTTP method used in sending
	 * @param username		the username for HTTP authentication (if required)
	 * @param password		the password for HTTP authentication (if required)
	 * @return				an org.rockholla.net.HttpResponse
	 * @throws Exception
	 */
	public static HttpResponse sendToUrl(String url, String contentType, String content, String method, String username, String password) throws Exception
	{
		
		logger.debug("Performing " + method + ": " + url);
		URL urlObject = new URL(url);
		HttpURLConnection urlConn = (HttpURLConnection) urlObject.openConnection();
		urlConn.setRequestMethod(method);
		urlConn.setAllowUserInteraction(false); // no user interact [like pop up]
		urlConn.setDoOutput(true); // want to send
		if(content != null)
		{
			urlConn.setRequestProperty("Content-type", contentType);
			urlConn.setRequestProperty("Content-length", Integer.toString(content.length()));
		}
		if(username != null && password != null)
        {
        	String encodedAuth = Base64.encode(username + ":" + password);
        	urlConn.setRequestProperty("Authorization", "Basic " + encodedAuth);
        }
		if(content != null)
		{
			OutputStream ost = urlConn.getOutputStream();
	        PrintWriter pw = new PrintWriter(ost);
	        pw.print(content); // here we "send" our body!
	        pw.flush();
	        pw.close();
		}
        
        HttpResponse httpResponse = new HttpResponse();
        int i = 1;// this will print all header parameter
        String hKey;
        while((hKey = urlConn.getHeaderFieldKey(i)) != null)
        {	
           String hVal = urlConn.getHeaderField(i);
           httpResponse.headers.put(hKey, hVal);
           i++;
        }
        //and InputStream from here will be body
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String inputLine;
        String out = "";
        while ((inputLine = in.readLine()) != null)
        {
        	out += inputLine;
        }
        in.close();
        
        httpResponse.response = out;
        
        return httpResponse;
        
	}
	
	/**
	 * Opens a URL in the user's default browser
	 * 
	 * @param url	the URL to open
	 * @return		An error if encountered, otherwise null
	 */
	public static String openInBrowser(String url)
	{
		
		if(!java.awt.Desktop.isDesktopSupported()) 
		{
            return "Desktop is not supported (fatal)";
        }

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        if(!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) 
        {
            return "Desktop doesn't support the browse action (fatal)";
        }

        try {

            java.net.URI uri = new java.net.URI(url);
            desktop.browse(uri);
        }
        catch (Exception e) 
        {
            return e.getMessage();
        }

		return null;
		
	}
	
}
