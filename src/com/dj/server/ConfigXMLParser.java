package com.dj.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * We are storing config in xml file and this class helps us get the relevant properties.
 */
public class ConfigXMLParser {

	private static final String DEFAULT_CONF = "/conf/config.xml";
	private HashMap<String, String> configMap = new HashMap<>(); 

	public Map<String, String> getConfigData(String serverHome) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(serverHome+DEFAULT_CONF));
			document.getDocumentElement().normalize();
			 
			Element root = document.getDocumentElement();
			System.out.println(root.getNodeName());
			 
			NodeList nList = document.getElementsByTagName("server");
			 
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
			 Node node = nList.item(temp);
			 System.out.println("");  
			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {
			    //Print each employee's detail
			    Element eElement = (Element) node;
			    configMap.put("port",eElement.getElementsByTagName("port").item(0).getTextContent());
			    configMap.put("maxthread",eElement.getElementsByTagName("maxthread").item(0).getTextContent());
			    configMap.put("webroot",eElement.getElementsByTagName("webroot").item(0).getTextContent());
			 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configMap;
	}
}
