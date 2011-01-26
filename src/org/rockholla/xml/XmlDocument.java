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

package org.rockholla.xml;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jaxen.JaxenException;
import org.jdom.Attribute;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.Content;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.EntityRef;
import org.jdom.JDOMException;
import org.jdom.ProcessingInstruction;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * An XML Document representation, based on a JDOM document
 * 
 * @author rockholla
 * @see org.jdom.Document
 *
 */
public class XmlDocument extends Document 
{

	private static final long serialVersionUID = 1L;
	
	/** schema factory for validating the document */
	protected SchemaFactory _schemaFactory;
	/** the default encoding to use for reading and writing the document */
	protected String _encoding = "UTF-8";

	/**
	 * Constructor
	 */
	public XmlDocument() {}

	/**
	 * Constructor
	 * 
	 * @param xmlString	an XML string representing a valid XML document
	 * @throws XmlDocumentException
	 */
	public XmlDocument(String xmlString) throws XmlDocumentException 
	{
		
		try 
		{
			SAXBuilder saxBuilder = new SAXBuilder();
			Document newDocument = saxBuilder.build(new StringReader(xmlString));
			this.setRootElement((Element) newDocument.getRootElement().clone());
		} 
		catch(Exception exception) 
		{
			throw new XmlDocumentException(exception);
		}
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param rootElement	a JDOM element used to build the document
	 */
	public XmlDocument(Element rootElement) 
	{
		super(rootElement);
	}

	/**
	 * Constructor
	 * 
	 * @param content	A list of content used to build the document
	 */
	public XmlDocument(List<Content> content) 
	{
		super(content);
	}

	/**
	 * Constructor
	 * 
	 * @param rootElement	a JDOM element used to build the document	
	 * @param docType		a JDOM Document type
	 */
	public XmlDocument(Element rootElement, DocType docType) 
	{
		super(rootElement, docType);
	}

	/**
	 * Constructor
	 * 
	 * @param rootElement	a JDOM element used to build the document	
	 * @param docType		a JDOM Document type
	 * @param baseURI		a base URI for the XML document
	 */
	public XmlDocument(Element rootElement, DocType docType, String baseURI) 
	{
		super(rootElement, docType, baseURI);
	}
	
	/**
	 * Sets the encoding for the XML document
	 * 
	 * @param encoding	a string encoding value
	 */
	public void setEncoding(String encoding) 
	{
		this._encoding = encoding;
	}
	
	/**
	 * Gets the encoding for this document
	 * 
	 * @return	a string encoding value
	 */
	public String getEncoding() 
	{
		return this._encoding;
	}
	
	/**
	 * Returns the string representation for an XML-related content element
	 * 
	 * @param content	the content
	 * @param format	the format in which to return the string
	 * @return			the formatted content string
	 * @throws XmlDocumentException
	 */
	@SuppressWarnings("rawtypes")
	public static String toString(Content content, Format format) throws XmlDocumentException 
	{
	
		XMLOutputter xmlOutputter = new XMLOutputter();
		xmlOutputter.setFormat(format);
		if(content instanceof CDATA) 
		{
			return xmlOutputter.outputString((CDATA) content);
		} 
		else if(content instanceof Comment) 
		{
			return xmlOutputter.outputString((Comment) content);
		} 
		else if(content instanceof DocType) 
		{
			return xmlOutputter.outputString((DocType) content);
		} 
		else if(content instanceof Element) 
		{
			return xmlOutputter.outputString((Element) content);
		} 
		else if(content instanceof EntityRef) 
		{
			return xmlOutputter.outputString((EntityRef) content);
		} 
		else if(content instanceof List) 
		{
			return xmlOutputter.outputString((List) content);
		} 
		else if(content instanceof ProcessingInstruction) 
		{
			return xmlOutputter.outputString((ProcessingInstruction) content);
		} 
		else if(content instanceof Text) 
		{
			return xmlOutputter.outputString((Text) content);
		} 
		else 
		{
			throw new XmlDocumentException("Cannot convert content of type '" + content.getClass().getName() + "' to a string");
		}
		
	}
	
	/**
	 * Returns a neatly-formatted XML string for this document
	 * 
	 * @return	the XML string
	 * @throws XmlDocumentException
	 */
	public String getIndentedXmlString() throws XmlDocumentException 
	{
		return toString(this.getRootElement(), Format.getPrettyFormat().setEncoding(this._encoding));
	}
	
	/**
	 * Returns the XML string representation for this document
	 * 
	 * @return	the XML string
	 * @throws XmlDocumentException
	 */
	public String getXmlString() throws XmlDocumentException 
	{
		return toString(this.getRootElement(), Format.getRawFormat().setEncoding(this._encoding));
	}
	
	/**
	 * Returns the XML string representation for this document
	 * 
	 * @param encoding	the encoding to use for creating the string
	 * @return			the XML string
	 * @throws XmlDocumentException
	 */
	public String getXmlString(String encoding) throws XmlDocumentException
	{
		return toString(this.getRootElement(), Format.getRawFormat().setEncoding(encoding));
	}
	
	/**
	 * Replaces an attribute value in the document with a new value
	 * 
	 * @param xPath				the XPath to the attribute
	 * @param attributeValue	the new value
	 * @throws JDOMException
	 */
	@SuppressWarnings("unchecked")
	public void replaceAttributeValue(String xPath, String attributeValue) throws JDOMException 
	{
		
		List<Attribute> attributeResults = (List<Attribute>) this.search(xPath);
		for(Attribute attribute : attributeResults) 
		{
			attribute.setValue(attributeValue);
		}
		
	}
	
	/**
	 * Finds and returns node results from this XML document via an XPath search
	 * 
	 * @param xPath		the XPath search string
	 * @return 			results of the search in the form of a node list
	 * @throws JDOMException
	 * @throws JaxenException 
	 * @throws JDOMException 
	 */
	@SuppressWarnings("rawtypes")
	public List search(String xPath) throws JDOMException 
	{
		List xPathResults = XPath.selectNodes(this, xPath);
		return xPathResults;
	}
	
	/**
	 * Creates an independent XML (JDOM) element
	 * 
	 * @param xmlString
	 * @return
	 * @throws XmlDocumentException
	 */
	public static Element createElement(String xmlString) throws XmlDocumentException
	{
		return new XmlDocument(xmlString).getRootElement();
	}
	
	/**
	 * Determines the validity of this XML document based on the designated schema
	 * 
	 * @param schemaFileLocation	the local location of the schema file
	 * @return						true if it validates
	 * @throws XmlValidationException
	 */
	public boolean isValid(String schemaFileLocation) throws XmlValidationException 
	{
		
		if(this._schemaFactory == null) 
		{
			this._schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		}
		try 
		{
			File schemaFile = new File(schemaFileLocation);
			Schema schema = this._schemaFactory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(schemaFileLocation);
			validator.validate(source);
		} 
		catch(Exception exception) 
		{
			throw new XmlValidationException("Error validating document against '" + schemaFileLocation + "'", exception);
		}
		return true;
		
	}
	
	/**
	 * Transforms this XML document per the input XSL file
	 * 
	 * @param xslFileLocation	the local location of the XSL file
	 * @return					the resulting string of the transformation
	 * @throws XmlDocumentException
	 * @throws TransformerException
	 */
	public String transform(String xslFileLocation) throws XmlDocumentException, TransformerException 
	{
		return this.transform(xslFileLocation, null);
	}
	
	/**
	 * Transforms this XML document per the input XSL file
	 * 
	 * @param xslFileLocation	the local location of the XSL file
	 * @param parameters		the parameters to input to the XSL file
	 * @return					the resulting string of the transformation
	 * @throws XmlDocumentException
	 * @throws TransformerException
	 */
	public String transform(String xslFileLocation, HashMap<String,String> parameters) throws XmlDocumentException, TransformerException 
	{
		
		System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
		
		StringWriter stringWriter = new StringWriter();
		StringReader sourceXmlStringReader = new StringReader(this.getXmlString());
		
		StreamSource source = new StreamSource(sourceXmlStringReader);
		StreamResult result = new StreamResult(stringWriter);
		
		result.setWriter(stringWriter);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(xslFileLocation)));
		
		if(parameters != null) 
		{
			Iterator<String> iterator = parameters.keySet().iterator();
			while(iterator.hasNext()) 
			{
				String parameterName = iterator.next();
				transformer.setParameter(parameterName, parameters.get(parameterName));
			}
		}
		
		transformer.transform(source, result);
		return stringWriter.toString();
		
	}

}
