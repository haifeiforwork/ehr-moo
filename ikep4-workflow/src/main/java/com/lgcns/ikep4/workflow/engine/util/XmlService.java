package com.lgcns.ikep4.workflow.engine.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;


public class XmlService {
	
	Log log = LogFactory.getLog(XmlService.class);
	
	public Element rootNode					= null;
	public Element selectNode				= null;	
	public Document dom 					= null;	
	public String documentXML				= "";
	private boolean isInformation			= true;
	
	
//	private String			systemDomain			= "";
//	private String			systemPort				= "";
//	private String 			systemType				= "";
//	private String 			contextType				= "";
//	private String			charSet					= "";	
	
	
	public XmlService() {
		
	}   
	

	
	public XmlService(String serviceUrl) {
		try {
			loadingUrl(serviceUrl, "", "");
		}catch(Exception e) {
			log.info("# XmlService Loading Error : ("+serviceUrl+") ", e);
		}
	}
	
	public XmlService(String serviceUrl, String responseCharSet) {
		try {
			loadingUrl(serviceUrl, "", responseCharSet);
		}catch(Exception e) {
			log.info("# XmlService Loading Error : ("+serviceUrl+") ", e);
		}
	}	
	
	public XmlService(String serviceUrl, String requestCharSet, String responseCharSet) {
		try {
			loadingUrl(serviceUrl, requestCharSet, responseCharSet);
		}catch(Exception e) {
			log.info("# XmlService Loading Error : ("+serviceUrl+") ", e);
		}
	}		
	
	/**
	 * 
	 * @param serviceUrl
	 * @param requestCharSet
	 * @throws Exception
	 */
	public void loadingUrl(String serviceUrl, String requestCharSet) {
		try {
			loadingUrl(serviceUrl, requestCharSet, "");
		} catch(Exception e) {
			log.info("# XmlService.loadingUrl : ", e);
		}
		
	}
	
	/**
	 * 
	 * @param serviceUrl
	 * @throws Exception
	 */
	public void loadingUrl(String serviceUrl, String requestCharSet, String responseCharSet) {
		InputStreamReader reader	= null; 
		BufferedReader bufferReader	= null;
		StringBuffer bufferXML		= new StringBuffer();
		
		try {
			long startTime			= startPrint("Loading XmlService-Start", serviceUrl);
			
			DocumentBuilderFactory docBuilderFactory 	= DocumentBuilderFactory.newInstance();	// DocumentBuilderFactory 의 객체 생성
			docBuilderFactory.setCoalescing(true);
			DocumentBuilder docBuilder 	= docBuilderFactory.newDocumentBuilder();				// factory로부터 DocumentBuilder 를 얻어온다.
			String serviceDomain	= "";
			String pathParameter	= "";
			int servicePort			= 80;
	
			if(serviceUrl.startsWith("http://")) {
				serviceUrl			= serviceUrl.substring(7, serviceUrl.length());
			}
			int slashPoint			= serviceUrl.indexOf("/"); 
			serviceDomain			= serviceUrl.substring(0, slashPoint);
			int clonePoint			= serviceDomain.indexOf(':');
			if(0 < clonePoint) {
				servicePort			= Integer.parseInt(serviceDomain.substring((clonePoint+1), serviceDomain.length()));
				serviceDomain		= serviceDomain.substring(0, clonePoint); 
			}
			pathParameter			= serviceUrl.substring(slashPoint, serviceUrl.length());		
			java.net.URL uriFrameSet	= new java.net.URL("http", serviceDomain, servicePort, pathParameter);
			
			if(requestCharSet.equalsIgnoreCase("")) {
				requestCharSet		= "EUC-KR";
			}
			reader					= new InputStreamReader(uriFrameSet.openStream(), requestCharSet);
			bufferReader			= new BufferedReader(reader);

			int ch;
			while((ch = bufferReader.read()) != -1)  {
				bufferXML.append((char) ch);
			}
			bufferReader.close();
			documentXML				= bufferXML.toString();
			int startTagPosition	= documentXML.indexOf('<');
			documentXML				= bufferXML.substring(startTagPosition, bufferXML.length());

			ByteArrayInputStream byteStream = new ByteArrayInputStream(documentXML.getBytes());
			InputSource inputSource			= new InputSource(byteStream);
	
			if(!responseCharSet.equals("")) {
				inputSource.setEncoding("EUC-KR");
			} else {
				inputSource.setEncoding("ISO-8859-1");
			}
			//sourceStream				= new String((inputSource+"").getBytes(charSet), "ISO-8859-1");
			dom = docBuilder.parse(inputSource);									// XML 문서로부터 DOM 객체를 만들어서 문서(Document) 로 반환 받는다.
			endPrint("Loading XmlService-End", startTime);
		} catch(Exception e) {
			log.info("# XmlService.loadingUrl ", e);
		} finally {
			try {
				reader.close();
				bufferReader.close();
			} catch(Exception e) {
				log.info("# XmlService.loadingUrl : ", e);
			}
		}
	}	
	
	
	/**
	 * XML문서를 Document 객체로 loading 한다. 
	 * @param pSourceXml
	 */
	public void loadingXML(String pSourceXml) {
		try {
			DocumentBuilderFactory docBuilderFactory 	= DocumentBuilderFactory.newInstance();		// DocumentBuilderFactory 의 객체 생성
			docBuilderFactory.setCoalescing(true);
			DocumentBuilder docBuilder 		= docBuilderFactory.newDocumentBuilder();				// factory로부터 DocumentBuilder 를 얻어온다.
			
			ByteArrayInputStream byteStream = new ByteArrayInputStream(pSourceXml.getBytes());
			InputSource inputSource			= new InputSource(byteStream);
			inputSource.setEncoding("UTF-8");
			dom = docBuilder.parse(inputSource);
		} catch(Exception e) {
			log.info("# loadingSource ", e);
		}
	}	
	
	/**
	 * XML 파일을 Documebt 객체로 loading 한다. 
	 * @param pFilePath
	 */
	public void loadingFile(String pFilePath) {
		try {
			DocumentBuilderFactory docBuilderFactory 	= DocumentBuilderFactory.newInstance();		// DocumentBuilderFactory 의 객체 생성
			docBuilderFactory.setCoalescing(true);
			DocumentBuilder docBuilder 					= docBuilderFactory.newDocumentBuilder();	// factory로부터 DocumentBuilder 를 얻어온다.
			dom 										= docBuilder.parse(new File(pFilePath));
		} catch(Exception e) {
			log.info("# loadingXMLFile ", e);
		}		
	}
	
	
	/**
	 * Document 객체를 반환한다. 
	 * @return
	 */
	public Document getDocument() {
		return dom;
	}
	
	public void setDocument(Document pDom) {
		this.dom	= pDom;
	}

	/**
	 * Root를 기준으로 pXpath 경로의 NodeList를 반환한다.
	 * @param 	pXpath
	 * @return the nodeList
	 */
	public NodeList getNodeList(String pXpath) {
		NodeList nodeList		= null;
		try {
			nodeList			= getNodeList(dom, pXpath);
		} catch(Exception e) {
			log.info(" # XmlService.getNodeList(Error) ", e);
		}
		return nodeList;
	}
	
	/**
	 * pNode 기준으로 pXpath 경로의 NodeList를 반환한다. 
	 * @param pNode
	 * @param pXpath
	 * @return
	 */
	public NodeList getNodeList(Node pNode, String pXpath) {
		NodeList nodeList		= null;
		try {
			nodeList			= XPathAPI.selectNodeList(pNode, pXpath);
		} catch(Exception e) {
			log.info(" # XmlService.getNodeList(Error) ", e);
		}
		return nodeList;
	}
	
	
	/**
	 * 
	 * @param pNode
	 * @return
	 */
	public String getNodeText(Node pNode) {
		return this.getNodeText(pNode, "");
	}
	
	/**
	 * 
	 * @param pXpath
	 * @return
	 */
	public String getNodeText(String pXpath) {
		String nodeText			= "";
		Node node				= null;
		try {
			if(!pXpath.equals("")) {
				node			= XPathAPI.selectSingleNode(dom.getFirstChild(), pXpath);
				if(node != null) {
				    if (node instanceof CharacterData) {
				    	CharacterData cd = (CharacterData) node;	
				    	nodeText		= cd.getData();
				    } else {
				    	if(this.isValue(node.getFirstChild())) {
				    		nodeText		= node.getFirstChild().getNodeValue();
				    	}
				    }
				}
			}
		} catch(Exception e) {
			log.info(" # XmlService.getNodeText(Error) ", e);
		}
		return nodeText;
	}	
	
	/**
	 * 
	 * @param pNode
	 * @param pXpath
	 * @return
	 */
	public String getNodeText(Node pNode, String pXpath) {
		String nodeText			= "";
		Node node				= null;
		try {
			if(pXpath != null && !pXpath.equals("")) {
				node			= XPathAPI.selectSingleNode(pNode, pXpath);
			} else {
				node			= pNode;
			}
			if (node instanceof CharacterData) {
		    	CharacterData cd = (CharacterData) node;	
		    	nodeText		= cd.getData(); 				
			} else {
				nodeText		= node.getFirstChild().getNodeValue();
			}
		} catch(Exception e) {
			log.info(" # XmlService.getNodeText(Error) ", e);
		}
		return nodeText;
	}

	/**
	 * 
	 * @param	pXpath
	 * @return the node
	 */
	public Node getSingleNode(String pXpath) {
		Node node				= null;
		try {
			node				= XPathAPI.selectSingleNode(dom, pXpath);
		} catch(Exception e) {
			log.info(" # XmlService.getSingleNode(Error) ", e);
		}
		return node;
	}
	
	/**
	 * 
	 * @param	pXpath
	 * @return the node
	 */
	public Node getSingleNode(Node pNode, String pXpath) {
		Node node				= null;
		try {
			node				= XPathAPI.selectSingleNode(pNode, pXpath);
		} catch(Exception e) {
			log.info(" # XmlService.getSingleNode(Error) ", e);
		}
		return node;
	}	
	
	/**
	 * @param	pXpath
	 * @return the node
	 */
	public String getAttrValue(String pXpath) {
		Node node				= null;
		String attrValue		= "";
		try {
			if(isExistNode(dom, pXpath)) {
				node				= XPathAPI.selectSingleNode(dom, pXpath);
				if(node.getTextContent() != null && !node.getTextContent().equals("")) {
					attrValue		= node.getTextContent();
				} else {
					attrValue		= XPathAPI.eval(node.getParentNode(), node.getNodeName()).toString().trim();
				}				
			}
		} catch(Exception e) {
			log.debug("#XmlService.getAttrValue(String pXpath) Error ", e);
			
		}
		return attrValue;		
	}	
	
	/**
	 * TODO Javadoc주석작성
	 * @param pNode
	 * @return
	 */
	public String getAttrValue(Node pNode) {
		return getAttrValue(pNode, "");
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param pNode
	 * @param pXpath
	 * @return
	 */
	public String getAttrValue(Node pNode, String pXpath) {
		String attrValue		= "";
		try {
			if(pXpath != null && !pXpath.equals("")) {
				if(isExistNode(pNode, pXpath)) {
					pNode			= getSingleNode(pNode, pXpath);
					if(pNode.getTextContent() != null && !pNode.getTextContent().equals("")) {
						attrValue		= pNode.getTextContent();
					} else {
						attrValue		= XPathAPI.eval(pNode.getParentNode(), pNode.getNodeName()).toString().trim();
					}				
				}
			} else {
				if(pNode != null) {
					if(pNode.getTextContent() != null) {
						attrValue		= pNode.getTextContent();
					} else {
						attrValue		= XPathAPI.eval(pNode.getParentNode(), pNode.getNodeName()).toString().trim();
					}
				}
			}
		} catch(Exception e) {
			log.info("#XmlService.getAttrValue(Node pNode, String pXpath) Error ", e);
		}
		return attrValue;		
	}		
	
	
	/**
	 * 
	 * @param pXpath
	 * @param pAttributeName
	 * @return
	 */
	public String getAttribute(String pXpath, String pAttributeName) {
		String attrValue			= "";
		try {
			Node pNode					= getSingleNode(pXpath);
			NamedNodeMap attributeMap	= pNode.getAttributes();
			if (attributeMap.getLength() != 0) {
				for (int i = 0 ; i < attributeMap.getLength() ; i++) {
					String attrTemp[]	= attributeMap.item(i).toString().split("=");
					if(attrTemp[0].equals(pAttributeName)) {
						String attrInfo	= attributeMap.item(i).toString();
						attrValue		= attrInfo.substring((pAttributeName.length()+1), attrInfo.length());
						if(attrValue.indexOf("\"") == 0 || attrValue.indexOf("\'") == 0) {
							attrValue	= attrValue.substring(1, attrValue.length()-1);
						}
						break;
					}
				}
			}	
		} catch(Exception e) {
			log.info("#XmlService.getAttribute(String pXpath, String pAttributeName) Error ", e);
		}
		return attrValue;
	}
	
	

	/**
	 * Node로부터 Attrubute 값을 반환한다. 
	 * @param pNode
	 * @param pAttributeName
	 * @return
	 */
	public String getAttribute(Node pNode, String pAttributeName) {
		String attrValue			= ""; 
		NamedNodeMap attributeMap	= pNode.getAttributes();
		if (attributeMap.getLength() != 0) {
			for (int i = 0 ; i < attributeMap.getLength() ; i++) {
				String attrTemp[]	= attributeMap.item(i).toString().split("=");
				if(attrTemp[0].equals(pAttributeName)) {
					String attrInfo	= attributeMap.item(i).toString();
					attrValue		= attrInfo.substring((pAttributeName.length()+1), attrInfo.length());
					if(attrValue.startsWith("\"") || attrValue.startsWith("\'")) {
						attrValue	= attrValue.substring(1, attrValue.length()-1);
					}
					break;
				}
			}
		}		
		return attrValue;
	}
	
	/**
	 * 
	 * @param pNode
	 * @param pXpath
	 * @param pAttributeName
	 * @return
	 */
	public String getAttribute(Node pNode, String pXpath, String pAttributeName) {
		String attrValue			= "";
		try {
			Node nodeTemp				= XPathAPI.selectSingleNode(pNode, pXpath);
			attrValue					= getAttribute(nodeTemp, pAttributeName);
		} catch(Exception e) {
			log.info("# XmlService.getAttribute(Node pNode, String pXpath, String pAttributeName)", e);
		}
		return attrValue;
	}	
	
	/**
	 * 
	 * @param pNode
	 * @param pXpath
	 * @return
	 */
	public boolean isExistNode(Node pNode, String pXpath) {
		boolean isExist				= false;
		try {
			NodeList nodeList		= XPathAPI.selectNodeList(pNode, pXpath);
			if(0 < nodeList.getLength()) {
				isExist 	= true;
			}
		} catch(Exception e) {
			isExist					= false;
			log.info("# XmlService.isExistNode", e);
		}
		
		return isExist;
	}
	
	/**
	 * pXpath에 해당하는 Node가 있는지를 체크하는 메소드
	 * TODO Javadoc주석작성
	 * @param pXpath
	 * @return
	 */
	public boolean isExistNode(String pXpath) {
		boolean isExist				= false;
		try {
			NodeList nodeList		= XPathAPI.selectNodeList(dom, pXpath);
			if(0 < nodeList.getLength()) {
				isExist 	= true;
			}
		} catch(Exception e) {
			isExist					= false;
			log.info("# XmlService.isExistNode", e);
		}
		return isExist;
	}	
	
	
	/**
	 * pNode에 속성값이 있는지를 체크하는 메소드
	 * @param pNode
	 * @param pAttributeName
	 * @return
	 */
	public boolean isAttribute(Node pNode, String pAttributeName) {
		boolean isExist				= false;
		try {
			NamedNodeMap attributeMap	= pNode.getAttributes();
			
			if (attributeMap.getLength() != 0) {
				for (int i = 0 ; i < attributeMap.getLength() ; i++) {
					String attrTemp[]	= attributeMap.item(i).toString().split("=");
					if(attrTemp[0].equals(pAttributeName)) {
						isExist		= true;
						break;
					}
				}
			}			
		} catch(Exception e) {
			isExist					= false;
			log.info("# XmlService.isAttribute", e);
		}
		return isExist;
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param pXpath
	 * @return
	 */
	public String nodeCount(String pXpath) {
		NodeList nodeList		= this.getNodeList(pXpath);
		return "" + nodeList.getLength();
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param pNode
	 * @param pXpath
	 * @return
	 */
	public String nodeCount(Node pNode, String pXpath) {
		NodeList nodeList		= this.getNodeList(pNode, pXpath);
		return "" + nodeList.getLength();
	}
	
	
	/**
	 * pNode에 대한 값이 있는지를 체크하는 메소드
	 * @param pNode
	 * @return
	 */
	public boolean isValue(Node pNode) {
		boolean isExist				= false;
		try {
			String nodeValue		= getAttrValue(pNode);
			if(!nodeValue.equals("")) {
				isExist				= true;				
			}
		} catch(Exception e) {
			isExist					= false;
			log.info("# XmlService.isValue", e);
		}
		return isExist;
	}
		
	
	
	/**
	 * pNode에 pNodeName으로 하위 node가 있는지를 체크하는 메소드
	 * @param pNode
	 * @param pNodeName
	 * @return
	 */
	public boolean isChildNode(Node pNode, String pNodeName) {
		boolean isExist				= false;
		NodeList nodeListTemp		= null;
		try {
			if(pNode.getChildNodes() != null) {
				nodeListTemp		= pNode.getChildNodes();
				if(nodeListTemp != null && 0 < nodeListTemp.getLength()) {
					for(int i=0;i<(nodeListTemp.getLength()-1);i++) {
						String nodeNameTemp	= nodeListTemp.item(i).getNodeName(); 
						nodeNameTemp		= nodeNameTemp.substring(nodeNameTemp.indexOf(":"), nodeNameTemp.length());
						if(nodeNameTemp != null && nodeNameTemp.equals("#text") && nodeNameTemp.equals(pNodeName)) {
							isExist			= true;
							break;
						}
					}
				}
			}
		} catch(Exception e) {
			log.info("# XmlService.isChildNode(Node pNode) ", e);			
		}
		return isExist;
	}
	
	/**
	 * pXpath에 해당하는 Node가 단일 Node인지 멀티 Node인지를 체크하는 메소드
	 * @param pNode
	 * @param pNodeName
	 * @return
	 */
	public boolean isMultiNode(Node pNode, String pXpath) {
		boolean isMulti				= false;
		int nodeCount				= 0;
		try {
			nodeCount				= XPathAPI.selectNodeList(pNode, pXpath).getLength();
		} catch(Exception e) {
			log.info("# XmlService.isMultiNode(Node pNode, String pNodeName) nodeCont : " + nodeCount + " : ", e);			
		} 
		if(1 < nodeCount) {
			isMulti	= true;
		}
		return isMulti;
	}
	
	
	
	public void appendElement(String xpath, String elementName, String elementValue) {
		try {
			NodeList nodes = XPathAPI.selectNodeList(dom, xpath);
			Element element, element2;
			Text node;

			for (int i = 0; i < nodes.getLength(); i++) {
				// loop안에서 선언한다. 모든 node에 append가 된다
				element = dom.createElement(elementName);
				node 	= dom.createCDATASection(elementValue);
				element.appendChild(node);

				element2 = (Element) nodes.item(i);
				element2.appendChild(element);
			}
		} catch (Exception e) {
			log.info("# XmlService.appendElement ", e);
		}
	}		
	
	
	/**
	 * TODO Javadoc주석작성
	 * @param xpath
	 * @param elementName
	 * @param elementValue
	 * @param attrName
	 * @param attrValue
	 */
	public void appendElement(String xpath, String elementName, String elementValue, String attrName, String attrValue) {
		try {
			NodeList nodes = XPathAPI.selectNodeList(dom, xpath);
			Element element, element2;
			Text node;

			for (int i = 0; i < nodes.getLength(); i++) {
				// loop안에서 선언한다. 모든 node에 append가 된다
				element = dom.createElement(elementName);
				node 	= dom.createCDATASection(elementValue);
				element.appendChild(node);
				element.setAttribute(attrName, attrValue);

				element2 = (Element) nodes.item(i);
				element2.appendChild(element);
			}
		} catch (Exception e) {
			log.info("# XmlService.appendElement ", e);
		}
	}		
	
	/**
	 * TODO Javadoc주석작성
	 * @param xpath
	 * @param elementName
	 * @param elementValue
	 */
	public void appendElement(String xpath, String elementName, String[] elementValue) {
		try {
			NodeList nodes = XPathAPI.selectNodeList(dom, xpath);
			Element element, element2;
			Text node;

			for (int i = 0; i < nodes.getLength(); i++) {
				// loop안에서 선언한다. 모든 node에 append가 된다
				element = dom.createElement(elementName);
				// nodes의 갯수와 elementValue의 갯수가 달라도 된다
				if (i < elementValue.length && elementValue[i] != null) {
					node = dom.createCDATASection(elementValue[i]);
					element.appendChild(node);
				}
				element2 = (Element) nodes.item(i);
				element2.appendChild(element);
			}
		} catch (Exception e) {
			log.info("# XmlService.appendElement ", e);
		}
	}	
	
	
	/**
	 * TODO Javadoc주석작성
	 * @param pXpath
	 * @param nodeValue
	 */
	public void setNodeValue(String pXpath, String nodeValue) {
		Node selectNode				= this.getSingleNode(pXpath);
		selectNode.setNodeValue(nodeValue);
		selectNode.setTextContent(nodeValue);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getContent() {
		return documentXML;
	}
	
	public void clear() {
		documentXML				= "";
		dom						= null;
	}
	
	public long startPrint(String header, String serviceUrl) {
		if(isInformation) {
			log.info(	"\n *****************************************************************************" +
								"\n * " + header + " : " + serviceUrl + 
								"\n *****************************************************************************"
			);
		}
		return System.currentTimeMillis();
	}	
	
	/**
	 * 프로세스 처리 마지막 로그이력 남기는 메소드
	 * @param startTime			: 시작시간 Long타입
	 * @param procCount			: 프로세스 처리 갯수
	 */
	public void endPrint(String header, long startTime) {
		long endTime = System.currentTimeMillis();
		long delay	= endTime - startTime;
		if(isInformation) {
			log.info(	"\n *****************************************************************************" +
								"\n * " + header + " : delay time = " + delay + " : avg = " +((delay)/1000) +
								"\n *****************************************************************************"
			);
		}
	}
	
	
	
	
}