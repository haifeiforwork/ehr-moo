package com.lgcns.ikep4.util.xml;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lgcns.ikep4.util.xml.XmlUtil;


public class XmlTest {

	Log log = LogFactory.getLog(getClass());

	@Test
	public void dummyTest() throws Exception {
		log.debug("delete this");
	}

	public void setUp() {}
	
	public void read() throws Exception {

		URL xmlUrl = new URL("http://media.daum.net/rss/today/primary/sports/rss2.xml");
		URLConnection urlConn = xmlUrl.openConnection();
		InputStream in = urlConn.getInputStream();

		XmlUtil xmlUtil = new XmlUtil();
		xmlUtil.initDocument();
		xmlUtil.loadDocument(in);

		log.debug("------------------------------------------");
		log.debug("/rss/channel/title:" + xmlUtil.getValue("/rss/channel/title"));
		log.debug("/rss/channel/link:" + xmlUtil.getValue("/rss/channel/link"));
		log.debug("/rss/channel/description:" + xmlUtil.getValue("/rss/channel/description"));
		log.debug("------------------------------------------");

		int nodeCnt = xmlUtil.getNodeCount("/rss/channel/item");
		for (int i = 0; i < nodeCnt; i++) {
			log.debug("------------------------------------------");
			log.debug("/rss/channel/item/title:" + xmlUtil.getValue("/rss/channel/item/title"));
			log.debug("/rss/channel/item/description:" + xmlUtil.getValue("/rss/channel/item/description"));
			log.debug("/rss/channel/item/link:" + xmlUtil.getValue("/rss/channel/item/link"));
			log.debug("/rss/channel/item/updatedate:" + xmlUtil.getValue("/rss/channel/item/updatedate"));
		}

		xmlUtil.closeDocument();
	}
	
	public void write() throws Exception {

		XmlUtil xmlUtil = new XmlUtil();

		xmlUtil.initDocument();
		xmlUtil.parseDocument("<school></school>");

		xmlUtil.appendElement("/school", "name", new String[] { "명동대학교" });
		xmlUtil.appendElement("/school", "address", new String[] { "서울시 명동역 1번출구 " });
		xmlUtil.appendElement("/school", "tel", new String[] { "02-222-2222" });

		xmlUtil.setValue("/school/tel", "02-333-3333");

		xmlUtil.writeDocument("d:/test.xml", "UTF-8");
		xmlUtil.closeDocument();

		xmlUtil.initDocument();
		xmlUtil.loadDocument("d:/test.xml");

		log.debug("------------------------------------------");
		log.debug("/school/name:" + xmlUtil.getValue("/school/name"));
		log.debug("/school/address:" + xmlUtil.getValue("/school/address"));
		log.debug("/school/tel:" + xmlUtil.getValue("/school/tel"));
		log.debug("------------------------------------------");

		xmlUtil.closeDocument();
	}

}
