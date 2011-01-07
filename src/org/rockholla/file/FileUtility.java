/**
 * 
 *	This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
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
package org.rockholla.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;

import javax.activation.MimetypesFileTypeMap;


import org.apache.log4j.Logger;
import org.rockholla.array.ArrayUtility;

import com.Ostermiller.util.CSVParser;

/**
 * Helper class for the file system
 * 
 * @author rockholla
 *
 */
public class FileUtility 
{

	/** log4j logger */
	static final Logger logger = Logger.getLogger(FileUtility.class);
	
	/** General option enumerator */
	public static enum OPTION
	{
		RECURSIVE,
		EXCLUDE_DIRECTORIES,
		INCLUDE_ONLY_DIRECTORIES
	}
	/** Recursive option */
	public static final OPTION RECURSIVE = OPTION.RECURSIVE;
	/** Exclude directories option */
	public static final OPTION EXCLUDE_DIRECTORIES = OPTION.EXCLUDE_DIRECTORIES;
	/** Include only directories option */
	public static final OPTION INCLUDE_ONLY_DIRECTORIES = OPTION.INCLUDE_ONLY_DIRECTORIES;
	
	/** UTF-8 string representation */
	public static final String UTF_8 = "UTF-8";
	/** The default encoding for reading and writing files */
	private static final String DEFAULT_ENCODING = UTF_8;
	
	/**
	 * Gets the string contents of a file
	 * 
	 * @param filePath	the path to the file
	 * @return			the string contents
	 * @throws IOException
	 */
	public static String getFileString(String filePath) throws IOException
	{
		return getFileString(filePath, DEFAULT_ENCODING);
	}
	
	/**
	 * Gets the string contents of a file
	 * 
	 * @param filePath	the path to the file
	 * @param encoding	the encoding to use to read the file
	 * @return			the string contents
	 * @throws IOException
	 */
	public static String getFileString(String filePath, String encoding) throws IOException 
	{
		
		String lineSep = System.getProperty("line.separator");
		InputStream inStream = new FileInputStream(filePath);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, encoding));
		StringBuffer stringBuffer = new StringBuffer();
		String line = "";

        while((line = bufferedReader.readLine()) != null) {
        	stringBuffer.append(line);
        	stringBuffer.append(lineSep);
        }
		
		return stringBuffer.toString();
		
	}
	
	/**
	 * Gets the lines of a file
	 * 
	 * @param filePath	the path to the file
	 * @return			a List of Strings (lines)
	 * @throws IOException
	 */
	public static ArrayList<String> getFileLines(String filePath) throws IOException
	{
		return getFileLines(filePath, DEFAULT_ENCODING);
	}
	
	/**
	 * Gets the lines of a file
	 * 
	 * @param filePath	the path to the file
	 * @param encoding	the encoding to use to read the file
	 * @return			a List of Strings (lines)
	 * @throws IOException
	 */
	public static ArrayList<String> getFileLines(String filePath, String encoding) throws IOException
	{
		
		InputStream inStream = new FileInputStream(filePath);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, encoding));
		String line = "";
		ArrayList<String> lines = new ArrayList<String>();

        while((line = bufferedReader.readLine()) != null) {
        	lines.add(line);
        }
		
		return lines;
		
	}
	
	/**
	 * Write a file
	 * 
	 * @param filePath	the path where the file is to be written
	 * @param content	the string content to write to the file
	 * @throws IOException
	 */
	public static void write(String filePath, String content) throws IOException 
	{
		write(filePath, content, DEFAULT_ENCODING);
	}
	
	/**
	 * Write a file
	 * 
	 * @param filePath	the path where the file is to be written
	 * @param content	the string content to write to the file
	 * @param encoding	the encoding to use to write the file
	 * @throws IOException
	 */
	public static void write(String filePath, String content, String encoding) throws IOException 
	{
		
		OutputStream outputStream = new FileOutputStream(filePath);
		Writer writer = new OutputStreamWriter(outputStream, encoding);

		writer.write(content);
		writer.close();
		
	}
	
	/**
	 * Write a file
	 * 
	 * @param filePath	the path where the file is to be written
	 * @param file		the java.io.File containing the contents write
	 * @throws IOException
	 */
	public static void write(String filePath, File file) throws IOException 
	{
		
		FileReader in = new FileReader(file);
	    FileWriter out = new FileWriter(new File(filePath));
	    int c;

	    while ((c = in.read()) != -1)
	    {
	    	out.write(c);
	    }

	    in.close();
	    out.close();
		
	}
	
	/**
	 * Gets a file stream as a string
	 * 
	 * @param inputStream	the input stream	
	 * @return				the string representation of the stream
	 * @throws IOException
	 */
	public static String getStreamAsString(InputStream inputStream) throws IOException 
	{
		return getStreamAsString(inputStream, DEFAULT_ENCODING);
	}
	
	/**
	 * Gets a file stream as a string
	 * 
	 * @param inputStream	the input stream	
	 * @param encoding		the encoding to use to read the stream
	 * @return				the string representation of the stream
	 * @throws IOException
	 */
	public static String getStreamAsString(InputStream inputStream, String encoding) throws IOException 
	{
		
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        if(inputStream != null) 
        {
            StringBuilder sb = new StringBuilder();
            String line;

            try 
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
                while((line = reader.readLine()) != null) 
                {
                    sb.append(line).append("\n");
                }
            } 
            finally 
            {
            	inputStream.close();
            }
            return sb.toString();
        } 
        else 
        {        
            return "";
        }
        
    }
	
	/**
	 * Get the folders/files within a particular folder
	 * 
	 * @param folderPath	the path to the folder
	 * @param args			an OPTION
	 * 							RECURSIVE = recurse into child directories for details
	 * 							INCLUDE_DIRECTORIES = include details of files AND directories in the result
	 * 							INCLUDE_ONLY_DIRECTORIES = include ONLY diretories in the result
	 * @return	a List of Files (files and/or folders)
	 */
	public static ArrayList<File> getFolderFiles(String folderPath, OPTION ...args) 
	{
		
		File folder = new File(folderPath);
	    File[] files = folder.listFiles();
	    ArrayList<File> folderFiles = new ArrayList<File>();
	    
	    for(int i = 0; i < files.length; i++) 
	    {
	    	
	    	if(files[i].isFile() && !files[i].isHidden() && !includesOption(OPTION.INCLUDE_ONLY_DIRECTORIES, args)) 
	    	{
	    		
	    		folderFiles.add(files[i]);
	    		
	    	} 
	    	else if(files[i].isDirectory() && !files[i].isHidden()) 
	    	{
	    		
	    		if(!includesOption(OPTION.EXCLUDE_DIRECTORIES, args) || includesOption(OPTION.INCLUDE_ONLY_DIRECTORIES, args)) 
	    		{
	    			folderFiles.add(files[i]);
	    		}
	    		
	    		if(includesOption(OPTION.RECURSIVE, args)) 
	    		{
	    			ArrayList<File> recursiveFolderFiles = FileUtility.getFolderFiles(files[i].getPath(), args);
		    		for(File recursiveFolderFile: recursiveFolderFiles) 
		    		{
		    			folderFiles.add(recursiveFolderFile);
		    		}
	    		}
	    		
	    	}
	    }
	    
	    return folderFiles;
		
	}
	
	/**
	 * Creates a path and all directories along the way
	 * 
	 * @param folderPath	the path to create
	 */
	public static void createFolderPath(String folderPath) 
	{
		
		File folderPathFiles = new File(folderPath);
		if(!folderPathFiles.isDirectory()) 
		{
			folderPathFiles.mkdirs();
		}
		
	}
	
	public static void deleteFolder(String folderPath)
	{
		deleteFolder(folderPath, false);
	}
	
	/**
	 * Deletes a folder's contents
	 * 
	 * @param folderPath	the path to the folder to delete
	 * @param keepRootFolder	if true, only the contents will be deleted, false will delete the folder itself as well
	 */
	public static void deleteFolder(String folderPath, Boolean keepRootFolder) 
	{
		File folderPathFiles = new File(folderPath);
		deleteFolder(folderPathFiles, keepRootFolder);
	}
	
	/**
	 * Deletes a folder's contents
	 * 
	 * @param folderPath	the java.io.File folder to delete
	 * @param keepRootFolder	if true, only the contents will be deleted, false will delete the folder itself as well
	 */
	public static void deleteFolder(File folder, Boolean keepRootFolder) 
	{
		
		if(!folder.isDirectory()) 
		{
			return;
		}
		
		File[] files = folder.listFiles();
		
		for(int i = 0; i < files.length; i++) 
		{
			if(files[i].isDirectory()) 
			{
				deleteFolder(files[i], false);
			} 
			else 
			{
				files[i].delete();
			}
		}
		
		if(!keepRootFolder) 
		{
			folder.delete();
		}
	    
	}
	
	/**
	 * Removes a folder's contents
	 * 
	 * @param folder	the java.io.File folder where the contents reside
	 */
	public static void emptyFolder(File folder) 
	{
		deleteFolder(folder, true);
	}
	
	/**
	 * Adds string content to an existing file
	 * 
	 * @param filePath	the path to the file
	 * @param data		the string data to append to the file
	 * @throws Exception
	 */
	public static void appendLineToFile(String filePath, String data) throws Exception 
	{
		
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true)); 
		out.write(data + "\n"); 
		out.close(); 
		
	}
	
	/**
	 * Copies a folder and its contents to a new location.  If the target location does not exist, it will be created.
	 * 
	 * @param sourceLocation		the source folder and files
	 * @param targetLocation		where the folder and files will be copied
	 * @throws IOException
	 */
	public static void copyFolder(File sourceLocation, File targetLocation) throws IOException
	{
		copyFolder(sourceLocation, targetLocation, new ArrayList<String>());
	}
	
	/**
	 * Copies a folder and its contents to a new location.  If the target location does not exist, it will be created.
	 * 
	 * @param sourceLocation		the source folder and files
	 * @param targetLocation		where the folder and files will be copied
	 * @param excludeDirectories	a List of directory names to ignore during the copy, ex: [.svn, .save] will ignore all .svn and .save folders
	 * @throws IOException
	 */
	public static void copyFolder(File sourceLocation , File targetLocation, ArrayList<String> excludeDirectories) throws IOException 
	{
	 
	    if(sourceLocation.isDirectory()) 
	    {
	    	if(ArrayUtility.find(sourceLocation.getName(), excludeDirectories) >= 0) 
	    	{
	    		return;
	    	}
	        if(!targetLocation.exists()) 
	        {
	            targetLocation.mkdir();
	        }
	 
	        String[] children = sourceLocation.list();
	        for(int i=0; i<children.length; i++) 
	        {
	            copyFolder(new File(sourceLocation, children[i]),
	                    new File(targetLocation, children[i]), 
	                    excludeDirectories);
	        }
	    } 
	    else 
	    {
	        InputStream in = new FileInputStream(sourceLocation);
	        OutputStream out = new FileOutputStream(targetLocation);
	 
	        // Copy the bits from instream to outstream
	        byte[] buf = new byte[1024];
	        int len;
	        while((len = in.read(buf)) > 0) 
	        {
	            out.write(buf, 0, len);
	        }
	        in.close();
	        out.close();
	    }
	}
	
	/**
	 * Parses and returns a CSV file in a multi-dimensional string array
	 * 
	 * @param filePath	the path to the CSV file
	 * @return			a String[][] representation of the CSV file
	 * @throws IOException
	 */
	public static String[][] getCsvFile(String filePath) throws IOException 
	{
		
		String fileData = getFileString(filePath);
		String[][] lines = CSVParser.parse(new StringReader(fileData));
		
		return lines;
	}
	
	/**
	 * Gets the file extension of a file
	 * 
	 * @param file	the java.io.File
	 * @return		a file extension
	 */
	public static String getFileExtension(File file) 
	{
		
		String[] fileNameParts = file.getName().split("\\.");
		if(fileNameParts.length > 0) 
		{
			return fileNameParts[fileNameParts.length - 1];
		} 
		else 
		{
			return null;
		}
		
	}
	
	/**
	 * Gets the base file name (w/o extension) of a file
	 * 
	 * @param file	the java.io.File
	 * @return		the base file name
	 */
	public static String getBaseFileName(File file)
	{
		if(file.isDirectory()) return file.getName();
		return file.getName().substring(0, file.getName().lastIndexOf("."));
	}
	
	/**
	 * Gets the byte array representation of a file
	 * 
	 * @param file	the java.io.File
	 * @return		the byte array
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException 
	{
        
		InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        if(length > Integer.MAX_VALUE) 
        {
            // File is too large
        	throw new IOException("File is too large: " + file.getName());
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while(offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) 
        {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if(offset < bytes.length) 
        {
            throw new IOException("Could not completely read file: " + file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
        
    }
	
	/**
	 * Gets a file's MIME type
	 * 
	 * @param file	the java.io.File
	 * @return		the MIME type
	 */
	public static String getFileMimeType(File file) 
	{
		return new MimetypesFileTypeMap().getContentType(file);
	}
	
	/**
	 * Helper method to parse OPTION args to see if a particular OPTION exists
	 * 
	 * @param option	the OPTION to attempt to locate
	 * @param args		the list of OPTIONS to search
	 * @return			true if the OPTION was found
	 */
	public static Boolean includesOption(OPTION option, OPTION ...args)
	{
		for(OPTION arg : args)
		{
			if(option.equals(arg))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets a list of all folder names in a particular path
	 * 
	 * @param path	the path to analyze
	 * @return		a List of folder names
	 */
	public static ArrayList<String> getFoldersInPath(String path)
	{
		
		ArrayList<String> folders = new ArrayList<String>();
		Boolean isDirectory = new File(path).isDirectory();
		if(path.indexOf("/") >= 0)
		{
			path = path.replaceAll("\\\\", "");
		}
		path = path.replaceAll("\\\\", "/");
		String[] pathParts = path.split("\\/");
		int atPart = 0;
		for(String pathPart : pathParts)
		{
			if(pathPart.trim().length() > 0 && (isDirectory || (!isDirectory && (atPart + 1) < pathParts.length)))
			{
				folders.add(pathPart);
			}
			
			atPart++;
		}
		return folders;
		
	}
	
	/**
	 * Determines whether or not a folder name exists in a designated path
	 * 
	 * @param folderName	the folder name to attempt to locate
	 * @param path			the path in which to locate the folder name
	 * @return				true if the folder name was located in the path
	 */
	public static Boolean folderExistsInPath(String folderName, String path)
	{
		
		if(ArrayUtility.find(folderName, getFoldersInPath(path)) >= 0)
		{
			return true;
		}
		return false;
		
	}
	
	public static void main(String[] args) 
	{
		
		//System.out.println(folderExistsInPath("testing", "C:\\\\Users\\pbf2105\\testing\\Lets see this one\\andonemore"));
		//System.out.println(folderExistsInPath("Lets see this one", "/Users/pbf2105/testing/Lets\\ see\\ this\\ one/andonemore"));
		
		/*for(File file : getFolderFiles("/Users/pbf2105/Eclipse/edu.columbia.blackrock.dataimporter/to-import/environmental-monitoring", OPTION.INCLUDE_ONLY_DIRECTORIES))
		{
			System.out.println(file.getPath());
		}*/
		
		String folder = "to-import/environmental-monitoring";
		System.out.println(folder.substring(0, folder.indexOf("/")));
		
	}
	
}
