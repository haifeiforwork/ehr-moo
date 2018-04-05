package com.lgcns.ikep4.workflow.engine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class HtmlService {
	
	Log log = LogFactory.getLog(HtmlService.class);
	
	public Element rootNode					= null;
	public Element selectNode				= null;	
	public Document dom 					= null;	
	
	public HtmlService() {
		
	}
	
	
	public String getContent(String serviceUrl) throws Exception {
		return getContent(serviceUrl, "");
	}
	
	/**
	 * 
	 * @param serviceUrl
	 * @throws Exception
	 */
	public String getContent(String serviceUrl, String charSet) {

		
		String serviceDomain	= "";
		String pathParameter	= "";
		int servicePort			= 80;
		
		InputStreamReader reader	= null; 
		BufferedReader bufferReader	= null;
		String documentXML			= "";
		StringBuffer bufferXML		= new StringBuffer();
		
		try {
			if(serviceUrl.startsWith("http://")) {
				serviceUrl			= serviceUrl.substring(7, serviceUrl.length());
			}
			int slashPoint			= serviceUrl.indexOf('/'); 
			serviceDomain			= serviceUrl.substring(0, slashPoint);
			int clonePoint			= serviceDomain.indexOf(':');
			if(0 < clonePoint) {
				servicePort			= Integer.parseInt(serviceDomain.substring((clonePoint+1), serviceDomain.length()));
				serviceDomain		= serviceDomain.substring(0, clonePoint); 
			}
			pathParameter			= serviceUrl.substring(slashPoint, serviceUrl.length());		
			
			java.net.URL uriFrameSet	= new java.net.URL("http", serviceDomain, servicePort, pathParameter);
		
			if(charSet.equalsIgnoreCase("")) {
				charSet		= "EUC-KR";
			}
			reader					= new InputStreamReader(uriFrameSet.openStream(), charSet);			
			bufferReader			= new BufferedReader(reader);
			int ch;
			while((ch = bufferReader.read()) != -1)  {
				bufferXML.append((char) ch);
			}
			bufferReader.close();
			
			documentXML				= bufferXML.toString();
			int startTagPosition	= documentXML.indexOf('<');
			documentXML				= bufferXML.substring(startTagPosition, bufferXML.length());
	
		} catch(IOException e) {
			log.info("# HtmlService.getContent(ioexception) : ", e);
		} catch(Exception e) {
			log.info("# HtmlService.getContent(exception) : ", e);
		} finally {
			try {
				reader.close();
				bufferReader.close();
			} catch(Exception e) {
				log.info("# HtmlService.getContent(exception) : ", e);
			}
		}
		
		return documentXML;
	}		
}