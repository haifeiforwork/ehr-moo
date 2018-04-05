package com.lgcns.ikep4.util.xml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * Xml파일 읽기, 쓰기 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: XmlUtil.java 16247 2011-08-18 04:54:29Z giljae $
 */
@SuppressWarnings("unchecked")
public class XmlUtil {

	public static final int NONE = 0;

	public static final int DOM = 1;

	public static final int SAX = 2;

	private DocumentBuilderFactory dbFactory;

	private DocumentBuilder dBuilder;

	private Document document = null;

	private TransformerFactory tFactory;

	private Transformer transformer;
	
	public XmlUtil() {
	}

	/**
	 * XML Document 초기화 @
	 */
	public void initDocument() {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);
			dBuilder = dbFactory.newDocumentBuilder();

			tFactory = TransformerFactory.newInstance();
			transformer = tFactory.newTransformer();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * XML Document 읽기 InputStream 을 만들어서 읽기
	 * 
	 * @param in @
	 */
	public void loadDocument(InputStream in) {
		try {
			document = dBuilder.parse(in);
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * XML Document 읽기 xmlPath 파일을 통해서 읽기
	 * 
	 * @param xmlPath @
	 */
	public void loadDocument(String xmlPath) {
		try {
			document = dBuilder.parse(new File(xmlPath));
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * XML Document 읽기 xmlString 을 통해서 읽기
	 * 
	 * @param xmlString @
	 */
	public void parseDocument(String xmlString) {
		try {
			StringReader sr = new StringReader(xmlString);
			InputSource is = new InputSource(sr);

			document = dBuilder.parse(is);
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * XML Document 쓰기 OutputStream 을 통해서 쓰기
	 * 
	 * @param out
	 * @param encoding @
	 */
	public void writeDocument(OutputStream out, String encoding) {
		try {
			setEncoding(encoding);

			// javax.xml.transform.dom.
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(out);
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * XML Document 쓰기 PrintWriter 을 통해서 쓰기
	 * 
	 * @param out
	 * @param encoding @
	 */
	public void writeDocument(PrintWriter out, String encoding) {
		try {
			StringWriter sw = new StringWriter();

			setEncoding(encoding);

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(sw);
			transformer.transform(source, result);

			out.print(sw.toString());
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * XML Document 쓰기 xmlPath 파일을 만들어서 쓰기
	 * 
	 * @param xmlPath
	 * @param encoding @
	 */
	public void writeDocument(String xmlPath, String encoding) {
		try {
			setEncoding(encoding);

			// javax.xml.transform.dom.
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(xmlPath));
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * XML Document 얻기
	 * 
	 * @return
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * XML Document 닫기
	 */
	public void closeDocument() {
		this.document = null;
	}

	/**
	 * Attribute 추가하기 element와 attribute를 새로 만들고 싶으면 아래의 순서대로 부른다
	 * x.appendElement("personnel/person", "nation", "korea");
	 * x.appendAttribute("personnel/person/nation", "capital", "서울"); node마다 각각의
	 * 값을 주기 위해서 String[] elementValue를 넘긴다
	 * 
	 * @param xpath
	 * @param attrName
	 * @param attrValue @
	 */
	public void appendAttribute(String xpath, String attrName, String[] attrValue) {
		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			Element element;

			// append하기 때문에 attribute는 반드시 unique한 값을 가져야 하기 때문에 갯수가 맞지 않으면 에러를
			// 발생시킨다
			// if (nodes.getLength() != attrValue.length)
			// throw new Exception("node 갯수와 value 갯수가 맞지 않습니다.");

			for (int i = 0; i < nodes.getLength(); i++) {
				element = (Element) nodes.item(i);
				element.setAttribute(attrName, attrValue[i]);
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * Attribute 삭제하기 해당 attribute node를 삭제하고 싶으면 domDeleteElement를 사용한다
	 * 
	 * @param xpath
	 * @param attrName @
	 */
	public void deleteAttribute(String xpath, String attrName) {
		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			Element element;

			for (int i = 0; i < nodes.getLength(); i++) {
				element = (Element) nodes.item(i);
				element.removeAttribute(attrName);
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * Element 추가하기 elementValue가 없으면 text node를 append하지 않는다
	 * x.appendElement("personnel/person", "nation", String[] k = { "korea" });
	 * node마다 각각의 값을 주기 위해서 String[] elementValue를 넘긴다
	 * 
	 * @param xpath
	 * @param elementName
	 * @param elementValue @
	 */
	public void appendElement(String xpath, String elementName, String[] elementValue) {
		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			Element element, element2;
			Text node;

			for (int i = 0; i < nodes.getLength(); i++) {
				// loop안에서 선언한다. 모든 node에 append가 된다
				element = document.createElement(elementName);
				// nodes의 갯수와 elementValue의 갯수가 달라도 된다
				if (i < elementValue.length && elementValue[i] != null) {
					node = document.createCDATASection(elementValue[i]);
					element.appendChild(node);
				}
				element2 = (Element) nodes.item(i);
				element2.appendChild(element);
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * Element 삭제하기 x.deleteElement("personnel/person") 하위 node 전체가 삭제된다
	 * 
	 * @param xpath @
	 */
	public void deleteElement(String xpath) {
		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			Node node;

			for (int i = 0; i < nodes.getLength(); i++) {
				node = nodes.item(i);
				node.getParentNode().removeChild(node); // 부모 노드에서 자식 노드(자신)를 삭제
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * xml 문자열을 파싱해서 해당 노드에 child로 붙인다 x.appendParse("personnel/person",
	 * "<node2><node3></node3></node2>");
	 * x.appendAttribute("personnel/person/node2", "id", new String[] { "1",
	 * "2", "3", "4", "5", "6" }); x.appendParse("personnel/person",
	 * "<node2><node3 id=\"1\">가</node3></node2>");
	 * 
	 * @param xpath
	 * @param xmlString @
	 */
	public void appendParse(String xpath, String xmlString) {
		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			Document tempDoc = makeTempDoc(xmlString);
			Node node;

			for (int i = 0; i < nodes.getLength(); i++) {
				// loop안에 선언해야 한다.
				node = tempDoc.getDocumentElement();
				node = document.importNode(node, true);
				nodes.item(i).appendChild(node);
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * 기존의 노드에 값만 추가 시킨다 x.appendTextNode("personnel/person/node2/node3", "가");
	 * 
	 * @param xpath
	 * @param txtValue @
	 */
	public void appendTextNode(String xpath, String[] txtValue) {
		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			Text node;

			for (int i = 0; i < nodes.getLength(); i++) {
				// nodes의 갯수와 txtValue의 갯수가 달라도 된다
				if (i < txtValue.length && txtValue[i] != null) {
					node = document.createCDATASection(txtValue[i]);
					nodes.item(i).appendChild(node);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * 해당노드의 반복되는 갯수 xpath="personnel/person/email" attr, element 모두 xpath로 지정하면
	 * 갯수를 돌려준다
	 * 
	 * @param xpath
	 * @return @
	 */
	public int getNodeCount(String xpath) {
		NodeList nodes = null;
		try {
			nodes = XPathAPI.selectNodeList(document, xpath);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return nodes.getLength();

	}

	/**
	 * 해당 값의 index 값을 돌려준다. 중복값이 있으면 처음 발견되는 값의 index를 돌려준다
	 * xpath="personnel/person", attr="id"
	 * 
	 * @param xpath
	 * @param attrName
	 * @param attrValue
	 * @return @
	 */
	public int getIndexAttribute(String xpath, String attrName, String attrValue) {

		int idx = -1;

		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			NamedNodeMap map;
			Node node, node2;
			String value = null;

			for (int i = 0; i < nodes.getLength(); i++) {
				node = nodes.item(i);
				map = node.getAttributes();
				node2 = map.getNamedItem(attrName);
				if (node2 != null && attrValue != null) {
					value = node2.getNodeValue();
					if (attrValue.equals(value)) {
						idx = i;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return idx;
	}

	/**
	 * Attribute 의 값을 얻어온다
	 * 
	 * @param xpath
	 * @param attrName
	 * @return @
	 */
	public List<String> getAttribute(String xpath, String attrName) {
		return getDocumentAttribute(xpath, attrName, -1);
	}

	/**
	 * Attribute 의 값을 얻어온다 attr과 value의 갯수가 다를 수 있기 때문에 원하는 index의 attribute값이
	 * 아닐수도 있다 dom일 경우는 xml의 순서대로 읽어오기 때문에 index로의 처리가 가능하다
	 * 
	 * @param xpath
	 * @param attrName
	 * @param index
	 * @return @
	 */
	public String getAttribute(String xpath, String attrName, int index) {
		return (String) getDocumentAttribute(xpath, attrName, index).get(0);
	}

	/**
	 * Attribute 의 값을 지정한다 xpath="personnel/person", attr="id"
	 * 
	 * @param xpath
	 * @param attrName
	 * @param attrValue @
	 */
	public void setAttribute(String xpath, String attrName, String attrValue) {
		setDocumentAttribute(xpath, attrName, null, new String[] { attrValue }, -1, 0);
	}

	/**
	 * Attribute 의 값을 지정한다 attribute는 unique하기 때문에 origs, news가 가능하다. index로
	 * 변경하지 않아도 된다 origValue는 원래 값, newValue는 새로운 값. origValue 값이 null이거나 ""이면
	 * 전체를 newValue 값으로 setting한다 대소문자를 구분하고 싶지 않으면 외부에서 대소문자로 변환해서 넘겨준다
	 * 
	 * @param xpath
	 * @param attrName
	 * @param origValue
	 * @param newValue @
	 */
	public void setAttribute(String xpath, String attrName, String origValue, String newValue) {
		setDocumentAttribute(xpath, attrName, origValue, new String[] { newValue }, -1, 0);
	}

	/**
	 * Attribute 의 값을 지정한다 해당 index의 attribute를 수정한다
	 * 
	 * @param xpath
	 * @param attrName
	 * @param attrValue
	 * @param index @
	 */
	public void setAttribute(String xpath, String attrName, String attrValue, int index) {
		setDocumentAttribute(xpath, attrName, null, new String[] { attrValue }, index, 0);
	}

	/**
	 * Attribute 의 값을 지정한다 해당 index의 attribute를 수정한다
	 * 
	 * @param xpath
	 * @param attrName
	 * @param attrValue @
	 */
	public void setAttribute(String xpath, String attrName, String[] attrValue) {
		setDocumentAttribute(xpath, attrName, null, attrValue, -1, attrValue.length);
	}

	/**
	 * 노드 의 값을 얻어온다 xpath에 해당하는 값이 여러개이면 여러개 하나면 하나만 돌려준다
	 * xpath="personnel/person/email"
	 * 
	 * @param xpath
	 * @return @
	 */
	public List<String> getValue(String xpath) {
		return getDocumentValue(xpath, -1);
	}

	/**
	 * 노드 의 값을 얻어온다 attr과 value의 갯수가 다를 수 있기 때문에 원하는 index의 attribute값이 아닐수도 있다
	 * dom일 경우는 xml의 순서대로 읽어오기 때문에 index로의 처리가 가능하다
	 * 
	 * @param xpath
	 * @param index
	 * @return @
	 */
	public String getValue(String xpath, int index) {
		return (String) getDocumentValue(xpath, index).get(0);
	}

	/**
	 * 노드 의 값을 지정한다 domSetAttribute하고 다르다. attribute는 unique하기 때문에 origs, news가
	 * 가능하다 xpath="personnel/person/email"
	 * 
	 * @param xpath
	 * @param value @
	 */
	public void setValue(String xpath, String value) {
		setDocumentValue(xpath, new String[] { value }, -1, 0);
	}

	/**
	 * 노드 의 값을 지정한다 attr과 value의 갯수가 다를 수 있기 때문에 원하는 index의 attribute값이 아닐수도 있다
	 * dom일 경우는 xml의 순서대로 읽어오기 때문에 index로의 처리가 가능하다
	 * 
	 * @param xpath
	 * @param value
	 * @param index @
	 */
	public void setValue(String xpath, String value, int index) {
		setDocumentValue(xpath, new String[] { value }, index, 0);
	}

	/**
	 * 노드 의 값을 지정한다
	 * 
	 * @param xpath
	 * @param value @
	 */
	public void setValue(String xpath, String[] value) {
		setDocumentValue(xpath, value, -1, value.length);
	}

	/**
	 * Encoding 값을 지정한다 domWrite할때 사용한다 setEncoding("euc-kr");
	 * 
	 * @param encoding
	 */
	private void setEncoding(String encoding) {
		try {
			if (encoding == null) {
				return;
			}
			transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * 임시 Document를 생성한다
	 * 
	 * @param nodeString
	 * @return @
	 */
	private Document makeTempDoc(String nodeString) {
		Document doc = null;
		try {
			StringReader sr = new StringReader(nodeString);
			InputSource is = new InputSource(sr);
			doc = dBuilder.parse(is);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return doc;

	}

	/**
	 * Attribute 값을 얻어온다
	 * 
	 * @param xpath
	 * @param attrName
	 * @param index
	 * @return @
	 */
	private List getDocumentAttribute(String xpath, String attrName, int index) {

		ArrayList al = new ArrayList();

		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			NamedNodeMap map;
			Node node, node2;
			String value = null;

			for (int i = 0; i < nodes.getLength(); i++) {
				if (index >= 0 && i != index) {
					continue;
				}

				node = nodes.item(i);
				map = node.getAttributes(); // 같은 node를 찾는다
				node2 = map.getNamedItem(attrName);
				if (node2 != null) {
					value = node2.getNodeValue();
					al.add(value);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return al;
	}

	/**
	 * Attribute 값을 지정한다
	 * 
	 * @param xpath
	 * @param attrName
	 * @param attrValue
	 * @param setValue
	 * @param index
	 * @param length @
	 */
	private void setDocumentAttribute(String xpath, String attrName, String attrValue, String[] setValue, int index,
			int length) {

		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			NamedNodeMap map;
			Node node, node2;
			String value = null;
			int off;

			for (int i = 0; i < nodes.getLength(); i++) {
				if (length > 0 && i >= length) {
					break;
				}
				if (index >= 0 && i != index) {
					continue;
				}

				node = nodes.item(i);
				map = node.getAttributes();
				node2 = map.getNamedItem(attrName);
				off = (length > 0) ? i : 0;
				if (node2 != null) {
					if (index > -1 || attrValue == null) {
						node2.setNodeValue(setValue[off]);
					} else {
						value = node2.getNodeValue();
						if (attrValue.equals(value)) {
							node2.setNodeValue(setValue[off]);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * 노드의 값을 얻어온다
	 * 
	 * @param xpath
	 * @param index
	 * @return @
	 */
	private List<String> getDocumentValue(String xpath, int index) {

		ArrayList al = new ArrayList();

		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			NodeList children;
			Node node;
			Node childNode;

			// value를 리턴하기 위해서 string 변수를 선언
			String value = null;

			// 여러개의 element에서 하나의 태그가 빠지면 nodes.getLength()의 갯수가 아예 줄어든다
			// al.add(Integer.toString(nodes.getLength()));
			for (int i = 0; i < nodes.getLength(); i++) {
				if (index >= 0 && i != index) {
					continue;
				}
				node = nodes.item(i);
				if (node.hasChildNodes()) {
					// child node가 있는 node type은 element와 document뿐이다
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						// Element 인 경우에는 자식노드를 검색해서 자식 노드중 텍스트 노드, 그러니까
						// 태그 사이의 텍스트 값을 리턴하자 일단.
						children = node.getChildNodes();
						for (int j = 0; j < children.getLength(); j++) {
							childNode = children.item(j);
							if (childNode.getNodeType() == Node.TEXT_NODE
									|| childNode.getNodeType() == Node.CDATA_SECTION_NODE) {
								// 자식노드를 순환하다가 TextNode 발견하면 value로 세팅.
								// out.println(childNode.getNodeName());
								value = childNode.getNodeValue();
								al.add(value);
							}
						}
					}
					/*
					 * <personnel> <person id="Big.Boss">
					 * <name><family>Boss</family> <given>Big</given></name>
					 * <email>chief@foo.com</email> <link subordinates=
					 * "one.worker two.worker three.worker four.worker five.worker"
					 * /> </person> </personnel> -- person이나 name일 경우 값이 없다.
					 */
					// Document 인 경우에는 Node value라는 개념이 애매하니까..
					// 전체 String 을 리턴하거나 null값을리턴해야 하는데 일단 null을 리턴하기로 하자.
					// Do nothing
				} else {
					// 자식노드가 없는 Attribute나 Text노드, CDATASection등의 값을 질의한 경우.
					// getNodeValue를 이용.
					value = node.getNodeValue();
					al.add(value);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return al;
	}

	/**
	 * 노드의 값을 지정한다
	 * 
	 * @param xpath
	 * @param value
	 * @param index
	 * @param length @
	 */
	private void setDocumentValue(String xpath, String[] value, int index, int length) {

		try {
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			NodeList children;
			Node node;
			int off;

			for (int i = 0; i < nodes.getLength(); i++) {
				if (length > 0 && i >= length) {
					break;
				}
				if (index >= 0 && i != index) {
					continue;
				}

				off = (length > 0) ? i : 0;
				node = nodes.item(i);
				if (node.hasChildNodes()) {
					// 기존 Text노드를 삭제하고 다시 setting해야 한다
					// 삭제하지 않으면 기존값에 새로운값이 붙여서 처리된다
					children = node.getChildNodes();
					for (int j = 0; j < children.getLength(); j++) {
						if (children.item(j) instanceof Text) {
							node.removeChild(children.item(j));
						}
					}
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						node.appendChild((Text) document.createCDATASection(value[off]));
					} else {
						node.setNodeValue(value[off]);
					}
				} else {
					/*
					 * 자식노드가 없는 Attribute나 Text노드, CDATASection등의 값을 질의한 경우.
					 * person, link인 경우에 해당 <personnel> <person id="Big.Boss">
					 * <name><family>Boss</family> <given>Big</given></name>
					 * <email>chief@foo.com</email> <link subordinates=
					 * "one.worker two.worker three.worker four.worker five.worker"
					 * /> </person> </personnel>
					 */
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						node.appendChild((Text) document.createCDATASection(value[off]));
					} else {
						node.setNodeValue(value[off]);
					}
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}
