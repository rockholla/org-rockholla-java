package org.rockholla.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.Ostermiller.util.Base64;

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
