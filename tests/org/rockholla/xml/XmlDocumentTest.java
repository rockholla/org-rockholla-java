package org.rockholla.xml;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.rockholla.TestHelper;

public class XmlDocumentTest 
{

	@Rule public TestName name = new TestName();
	final Logger logger = Logger.getLogger(XmlDocumentTest.class);
	
	public static final String TEST_XML = 
		"<Root>" +
			"<FirstChild>" +
				"<One attrOne=\"attrOneValue\" attrTwo=\"attrTwoValue\">Here's some text content</One>" +
			"</FirstChild>" +
			"<SecondChild>" +
				"<Two attrThree=\"attrThreeValue\" attrFour=\"attrFourValue\">Here's some text content</Two>" +
				"<Three attrFive=\"attrFiveValue\" attrSix=\"attrSixValue\">" +
					"<Parent>" +
						"<Child>one child</Child>" +
						"<Child>another child</Child>" +
						"<Child><![CDATA[this is content within =  CDATA]]></Child>" +
					"</Parent>" +
				"</Three>" +
			"</SecondChild>" +
		"</Root>";
	
	@Test
	public void testXmlDocument() {
		fail("Not yet implemented");
	}

	@Test
	public void testXmlDocumentString() {
		fail("Not yet implemented");
	}

	@Test
	public void testXmlDocumentElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testXmlDocumentListOfContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testXmlDocumentElementDocType() {
		fail("Not yet implemented");
	}

	@Test
	public void testXmlDocumentElementDocTypeString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetEncoding() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEncoding() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringContentFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIndentedXmlString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetXmlString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetXmlStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testReplaceAttributeValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testReplace() throws Exception 
	{
		
		logger.info(TestHelper.getRunningMethodNotification(name.getMethodName()));
		
		XmlDocument xml = new XmlDocument(TEST_XML);
		Element newElement = XmlDocument.createElement("<NewThree newAttrOne=\"newAttrOne\"><NewChild attr=\"value\">This is the new content</NewChild></NewThree>");
		xml.replace("//Three", newElement);
		
		logger.info("Resulting XML:\n" + xml.getIndentedXmlString());
		
		assertTrue(xml.search("//NewThree").size() > 0);
		assertTrue(xml.search("//NewChild[@attr = 'value']").size() > 0);
		
	}

	@Test
	public void testAddChildrenElementListOfElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddChildrenStringListOfElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddChild() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDocumentPathElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDocumentPathContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashMapToElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashMapItemToElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValid() {
		fail("Not yet implemented");
	}

	@Test
	public void testTransformString() {
		fail("Not yet implemented");
	}

	@Test
	public void testTransformStringHashMapOfStringString() {
		fail("Not yet implemented");
	}

}
