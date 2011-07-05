package org.rockholla.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;
import org.rockholla.file.FileUtility;
import org.rockholla.string.StringUtility;

public class MultiFileXPath 
{

	/** log4j logger */
	static final Logger logger = Logger.getLogger(MultiFileXPath.class);
	
	public static ArrayList<File> find(String directory, String xPath) throws IOException, JDOMException
	{
		
		ArrayList<File> results = new ArrayList<File>();
		ArrayList<File> files = FileUtility.getFolderFiles(directory);
		for(File file : files)
		{
			if(file.isDirectory()) continue;
			String fileContent = FileUtility.getFileString(file).trim();
			fileContent = XmlDocument.removeDoctypeDefinition(fileContent);
			if(!StringUtility.left(fileContent, 5).equals("<?xml"))
			{
				int xmlDecIndex = fileContent.indexOf("<?xml");
				if(xmlDecIndex >= 0) fileContent = fileContent.substring(xmlDecIndex);
				else continue;
			}
			XmlDocument xml = null;
			try
			{
				xml = new XmlDocument(fileContent);
			}
			catch(Exception exception)
			{
				System.out.println("Could not run XPath search on " + file.getAbsolutePath());
				if(FileUtility.getFileExtension(file).equals("xml"))
				{
					System.out.println(StringUtility.stackTraceToString(exception));
					System.out.println(fileContent);
					break;
				}
			}
			if(xml != null)
			{
				if(xml.search(xPath).size() > 0)
				{
					results.add(file);
				}
				xml = null;
			}
		}
		
		return results;
		
	}
	
	public static void main(String[] args) throws Exception
	{
		
		ArrayList<File> files = MultiFileXPath.find("/Users/pbf2105/Desktop/DMS_1920650192_20110501T170000", "//head[count(meta[@name = 'eiu_channel' and @content = 'Politics']) > 0 and count(meta[@name = 'eiu_category' and @content='Political structure'])]");
    	for(File file : files)
    	{
    		System.out.println(file.getAbsolutePath());
    	}
	}
	
}
