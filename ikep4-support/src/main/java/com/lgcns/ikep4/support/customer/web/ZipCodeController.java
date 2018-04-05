package com.lgcns.ikep4.support.customer.web;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.util.xml.XmlUtil;

@Controller
@RequestMapping( value = "/support/customer/zipcode" )
public class ZipCodeController extends BaseController {
	
	private static final String ZIP_OPEN_API_URL = "http://biz.epost.go.kr/KpostPortal/openapied?regkey=861394877e6c97c4e1346297408364&target=post&query=";
	
	//private static final String ZIP_OPEN_API_NEW_URL = "http://openapi.epost.go.kr/postal/retrieveNewAdressService/retrieveNewAdressService/getNewAddressList?ServiceKey=";
	
	private static final String ZIP_OPEN_API_NEW_URL = "http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdService/retrieveNewAdressAreaCdService/getNewAddressListAreaCd?ServiceKey=";
	
	//private static final String NEW_SVC_KEY = "f9uo2PDHCRdARgbKYt6eva2hJ%2F%2Bua6tqNR2v0l7pvG0zkMVeArYPuxM8i5c2g92DEDpKWPkT3t6pznvkw5DfaA%3D%3D";
	
	private static final String NEW_SVC_KEY = "3E7mOjLnVzyaVVZ5xHVfBrL3y8jtQbEJBWHHtN8GGKS1TIJ%2Fpp1VUDxMwjQGqV%2BMtfc8AcsHxAf1vkRwhfpKqA%3D%3D";
	/**
	 * 우편번호 검색 메인화면
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/main.do")	
	public ModelAndView main() {
		ModelAndView modelAndView = new ModelAndView("support/customer/zipcode/main");
		return modelAndView;
	}
	
	@RequestMapping(value = "/search.do")	
	public ModelAndView search(@RequestParam(value="addrName1",required = false) String addrName1,
			@RequestParam(value="addrName2",required = false) String addrName2,
			@RequestParam(value="addrType",required = false) String addrType,
			@RequestParam(value="searchSe",required = false) String searchSe) throws Exception {
		ModelAndView modelAndView = new ModelAndView("support/customer/zipcode/main");
		LinkedHashMap<String, String> zipCodeList = new LinkedHashMap<String, String>();
		if(addrType.equals("new")){
			zipCodeList = getNewZipCodeList(addrName2,searchSe);
		}else{
			zipCodeList = getOldZipCodeList(addrName1);
		}
		
		modelAndView.addObject("zipCodeList", zipCodeList);		
		modelAndView.addObject("addrType", addrType);
		
		return modelAndView;
	}
	
	private LinkedHashMap<String, String> getOldZipCodeList(String addrName){
		LinkedHashMap<String, String> zipData = new LinkedHashMap<String, String>();
		
		try {
			
			URL xmlUrl = new URL(ZIP_OPEN_API_URL+URLEncoder.encode(addrName, "EUC-KR"));
			URLConnection urlConn = xmlUrl.openConnection();
			urlConn.setRequestProperty("accept-language", "ko");
			InputStream in = urlConn.getInputStream();

			XmlUtil xmlUtil = new XmlUtil();
			xmlUtil.initDocument();
			xmlUtil.loadDocument(in);		

			int nodeCnt = xmlUtil.getNodeCount("/post/itemlist/item");
			
			String addr = null;
			String zipCode = null;
			
			for (int i = 0; i < nodeCnt; i++) {
				
				addr 	= xmlUtil.getValue("/post/itemlist/item/address", i);
				zipCode = xmlUtil.getValue("/post/itemlist/item/postcd", i);
				zipCode = zipCode.substring(0, 3) + "-" + zipCode.substring(3);
				
				zipData.put(addr, zipCode);
			}

			xmlUtil.closeDocument();
			 
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return zipData;
	}	
	
	private LinkedHashMap<String, String> getNewZipCodeList(String addrName, String searchSe)throws Exception{
		LinkedHashMap<String, String> zipData = new LinkedHashMap<String, String>();
		java.util.Vector list = new java.util.Vector();		
		//String rvalUrl = "http://openapi.epost.go.kr/postal/retrieveNewAdressService/retrieveNewAdressService/getNewAddressList?searchSe="+searchSe+"&srchwrd="+URLEncoder.encode(addrName, "UTF-8")+"&serviceKey=f9uo2PDHCRdARgbKYt6eva2hJ%2F%2Bua6tqNR2v0l7pvG0zkMVeArYPuxM8i5c2g92DEDpKWPkT3t6pznvkw5DfaA%3D%3D&encoding=UTF-8";
		String rvalUrl = "http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdService/retrieveNewAdressAreaCdService/getNewAddressListAreaCd?searchSe="+searchSe+"&srchwrd="+URLEncoder.encode(addrName, "UTF-8")+"&serviceKey=3E7mOjLnVzyaVVZ5xHVfBrL3y8jtQbEJBWHHtN8GGKS1TIJ%2Fpp1VUDxMwjQGqV%2BMtfc8AcsHxAf1vkRwhfpKqA%3D%3D&encoding=UTF-8";
		String addr = "";
		String zipCode = "";
		try{	
		   SAXBuilder parser = new SAXBuilder();	 	  
		   parser.setIgnoringElementContentWhitespace(true);	 
		   Document doc = parser.build(rvalUrl);	 	 
		   Element root = doc.getRootElement();   	 
		   //List newAddressList = root.getChildren("newAddressList");   
		   List newAddressList = root.getChildren("newAddressListAreaCd");
		   List cmmMsgHeader = root.getChildren("cmmMsgHeader");
		   if(newAddressList.size()>0)
		   {
			   for(int i=0;i<newAddressList.size();i++) 
			   {
				   list.addElement(newAddressList.get(i));
			   }
			   for (int j=0 ; j <list.size();j++) {
					Element tmp = (Element)list.elementAt(j);
					addr 	= tmp.getChildText("lnmAdres");
					zipCode = tmp.getChildText("zipNo");
					zipData.put(addr, zipCode);
			   }		   
		   }

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return zipData;
	}	
	
	private LinkedHashMap<String, String> getZipCodeList(String addrName){
		LinkedHashMap<String, String> zipData = new LinkedHashMap<String, String>();
		
		try {
			
			URL xmlUrl = new URL(ZIP_OPEN_API_URL+URLEncoder.encode(addrName, "EUC-KR"));
			URLConnection urlConn = xmlUrl.openConnection();
			urlConn.setRequestProperty("accept-language", "ko");
			InputStream in = urlConn.getInputStream();

			XmlUtil xmlUtil = new XmlUtil();
			xmlUtil.initDocument();
			xmlUtil.loadDocument(in);		

			int nodeCnt = xmlUtil.getNodeCount("/post/itemlist/item");
			
			String addr = null;
			String zipCode = null;
			
			for (int i = 0; i < nodeCnt; i++) {
				
				addr 	= xmlUtil.getValue("/post/itemlist/item/address", i);
				zipCode = xmlUtil.getValue("/post/itemlist/item/postcd", i);
				zipCode = zipCode.substring(0, 3) + "-" + zipCode.substring(3);
				
				zipData.put(addr, zipCode);
			}

			xmlUtil.closeDocument();
			 
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return zipData;
	}	
	
	public static void main(String[] args){
		
		ZipCodeController zc = new ZipCodeController();
		LinkedHashMap<String, String> list = zc.getZipCodeList("능동");
		
		Iterator<String> it = list.keySet().iterator();
		
		while(it.hasNext()){
			String key = it.next();
			//System.out.println("map key:" + key + ", value:[" + list.get(key)+"]" );
		}		
	}
	
}
