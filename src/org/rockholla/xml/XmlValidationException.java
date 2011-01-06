package org.rockholla.xml;

/**
 * Exception related to an XmlDocument.isValid attempt
 * 
 * @author Patrick Force <patrickforce@gmail.com>
 *
 */
public class XmlValidationException extends Exception 
{

	private static final long serialVersionUID = 1L;

	public XmlValidationException() {}

	public XmlValidationException(String message) 
	{
		super(message);
	}

	public XmlValidationException(Throwable cause) 
	{
		super(cause);
	}

	public XmlValidationException(String message, Throwable cause) 
	{
		super(message, cause);
	}

}
