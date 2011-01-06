package org.rockholla.xml;

/**
 * Exception related to an XMLDocument
 * 
 * @author Patrick Force <patrickforce@gmail.com>
 *
 */
public class XmlDocumentException extends Exception 
{

	private static final long serialVersionUID = 1L;

	public XmlDocumentException() {}

	public XmlDocumentException(String message) 
	{
		super(message);
	}

	public XmlDocumentException(Throwable cause) 
	{
		super(cause);
	}

	public XmlDocumentException(String message, Throwable cause) 
	{
		super(message, cause);
	}

}
