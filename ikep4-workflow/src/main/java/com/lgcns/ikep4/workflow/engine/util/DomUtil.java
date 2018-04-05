package com.lgcns.ikep4.workflow.engine.util;

import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomUtil {
	
	Log log = LogFactory.getLog(DomUtil.class);
	
	public DomUtil() {
		
		
	}
	

	/**
	 * DOM 문서에서 Element를 생성하는 메소드 
	 * @param document
	 * @param parentElement
	 * @param tagName
	 */
	public void createElement(Document document, Element parentElement, String tagName, String tagText, HashMap hshAttr) {
		Element createElement			= document.createElement(tagName);
		if(!tagText.equals("")) {
			createElement.appendChild(document.createTextNode(tagText));
		}
		if(hshAttr != null) {
			if(0 < hshAttr.size()) {
				Iterator iterator			= hshAttr.keySet().iterator();
				
				while(iterator.hasNext()) {
					String attrName			= iterator.next().toString();
					String attrValue		= hshAttr.get(attrName).toString();
					createElement.setAttribute(attrName, attrValue);
				}
			}
		}
		if(parentElement == null) {
			document.appendChild(createElement);	
		} else {
			parentElement.appendChild(createElement);
		}
	}
	
	/**
	 * DOM 문서에서 Element를 생성하고 결과 Element를 반환하는 메소드 
	 * @param document
	 * @param parentElement
	 * @param tagName
	 * @param tagText
	 * @return
	 */
	public Element createElementReturn(Document document, Element parentElement, String tagName, String tagText, HashMap hshAttr) {
		Element createElement			= document.createElement(tagName);
		if(!tagText.equals("")) {
			createElement.appendChild(document.createTextNode(tagText));
		}
		if(hshAttr != null) {
			if(0 < hshAttr.size()) {
				Iterator iterator			= hshAttr.keySet().iterator();
				
				while(iterator.hasNext()) {
					String attrName			= iterator.next().toString();
					String attrValue		= hshAttr.get(attrName).toString();
					createElement.setAttribute(attrName, attrValue);
				}
			}
		}		
		if(parentElement == null) {
			document.appendChild(createElement);	
		} else {
			parentElement.appendChild(createElement);
		}		
		return createElement;
	}	
	
	/**
	 * DOM문서를 문자열값으로 반환하는 메소드 
	 * @param pDoc
	 * @return
	 */
	public String generationDoc(Document pDoc) {
		String generationStr							= "";
		try {
			if(pDoc != null) {
				DOMSource source						= new DOMSource(pDoc);
				StringWriter writer							= new StringWriter();
		        StreamResult streamResult				= new StreamResult(writer);
		        TransformerFactory transformerFactory 	= TransformerFactory.newInstance(); 
		        Transformer transformer 				= transformerFactory.newTransformer(); 
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		        transformer.setOutputProperty(OutputKeys.METHOD, "xml"); 
		        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");   
		        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
		        transformer.transform(source, streamResult);
		        generationStr							= writer.toString();;
			}
		} catch(Exception e) {
			log.info("# Error : " + e);
		}
		return generationStr;
	}	
	
	
}