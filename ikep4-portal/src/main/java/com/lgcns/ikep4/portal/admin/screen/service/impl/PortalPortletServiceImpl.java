/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDefPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.xml.XmlUtil;



/**
 * 포탈 포틀릿 Service 구현 클래스
 * 
 * @author 한승환
 * @version $Id: PortalPortletServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("portalPortletService")
public class PortalPortletServiceImpl extends GenericServiceImpl<PortalPortlet, String> implements
		PortalPortletService {

	@Autowired
	private PortalPortletDao portalPortletDao;
	
	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;
	
	@Autowired
	private PortalDefPortletConfigDao portalDefPortletConfigDao;
	
	@Autowired
	private FileService fileService;

	static final int SUPPORT = 1;
	
	@Autowired
	private I18nMessageService i18nMessageService;
	
	@Autowired
	private PortalSecurityService portalSecurityService;
	
	static final String PORTLET_CLASS_NAME = "Portal-Portlet";
	
	public String createPortalPortlet(PortalPortlet portalPortlet) {
		
		
		// 필수 입력 정보를 채운다 .
		List<I18nMessage> i18nMessageList = null;
		
		if (!portalPortlet.getPortletType().equals("HTML")) {
			// 화면에서 정보를 받지 않고 직접 메세지를 셋팅
			List<I18nMessage> tmpMessageList = (i18nMessageService.makeInitLocaleList("portletName,portletDesc"));
			i18nMessageList = i18nMessageService.fillMandatoryFieldWithMessage(tmpMessageList,
					IKepConstant.ITEM_TYPE_CODE_PORTAL, portalPortlet.getPortletId(), portalPortlet.getPortletName());
		}else {
			//VO에서 받아온 메세지를 사용
			i18nMessageList = i18nMessageService.fillMandatoryField(portalPortlet.getI18nMessageList(),
					IKepConstant.ITEM_TYPE_CODE_PORTAL, portalPortlet.getPortletId());
		}
		// 다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		// Security Description  생성
		portalPortlet.getSecurity().setResourceName(portalPortlet.getPortletId());
		portalPortlet.getSecurity().setResourceDescription(portalPortlet.getPortletName());

		
		portalSecurityService.createSystemPermission(portalPortlet.getSecurity());

		return portalPortletDao.createPortalPortlet(portalPortlet);
	}
	

	public int updatePortalPortlet(PortalPortlet portalPortlet) {
		
		// 필수 입력 정보를 채운다 .
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalPortlet.getI18nMessageList(),
				 IKepConstant.ITEM_TYPE_CODE_PORTAL, portalPortlet.getPortletId());

		// 다국어정보를 저장한다.
		i18nMessageService.update(i18nMessageList);
		
		// Security Description  생성
		portalPortlet.getSecurity().setResourceName(portalPortlet.getPortletId());
		portalPortlet.getSecurity().setResourceDescription(portalPortlet.getPortletName());
		
		portalSecurityService.updateSystemPermissionAndResource(portalPortlet.getSecurity());
		
		return portalPortletDao.updatePortalPortlet(portalPortlet);
	}
	
	

	public SearchResult<PortalPortlet> listPortalPortletByCondition(AdminSearchCondition searchCondition) {
		
		Integer count = portalPortletDao.countPortalPortletByCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<PortalPortlet> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<PortalPortlet>(searchCondition);

		} else {

			User user = (User) this.getRequestAttribute("ikep.user");
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setItemTypeCode(IKepConstant.ITEM_TYPE_CODE_PORTAL);
			List<PortalPortlet> portletList = portalPortletDao.listPortalPortletByCondition(searchCondition);
			searchResult = new SearchResult<PortalPortlet>(portletList, searchCondition);
		}
		
		return searchResult;
	}

	public PortalPortlet readPortalPortlet(String portletId) {
		
		User user = (User) this.getRequestAttribute("ikep.user");
		
		PortalPortlet portalPortlet = portalPortletDao.readPortalPortlet(portletId,user.getLocaleCode());
		
		// .war file name setting
		if (!StringUtil.isEmpty(portalPortlet.getWarFileId())) {
			try
			{
				FileData fileData = fileService.read(portalPortlet.getWarFileId());
				portalPortlet.setWarFileName(fileData.getFileRealName());
			}
			catch(Exception e)
			{  
				portalPortlet.setWarFileName("File Not Exists"); 
			}
		}
		

		return portalPortlet;
	}
	
	public int deletePortalPortlet(String portletId) {
		
		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, portletId);
		portalPortletConfigDao.removePortletConfigAll(portletId);
		portalDefPortletConfigDao.removeDefPortletConfigAll(portletId);
		
		portalSecurityService.deleteSystemPermission(PORTLET_CLASS_NAME, portletId);
		
		return portalPortletDao.removePortalPortlet(portletId);
	}
	
	public List<PortalPortlet> convertXmlToPortalPortlet(PortalPortlet portalPortletParam, CommonsMultipartFile fileItem, String baseUrl){

		ArrayList<PortalPortlet> returnArList = new ArrayList<PortalPortlet>();
		String searchFileName = "WEB-INF/portlet.xml";

		String tempFileName = fileItem.getStorageDescription();
		String physicalFileName = tempFileName.substring(tempFileName.indexOf('[') + 1, tempFileName.indexOf(']'));

		InputStream inputStream = extractWarFile(physicalFileName, searchFileName);

		physicalFileName = fileItem.getFileItem().getName();

		// 아래 콘텍스트명으로 실제 웹어플리케이션 배포
		int beginIndex = physicalFileName.lastIndexOf('\\') == -1 ? physicalFileName.lastIndexOf('/')
				: physicalFileName.lastIndexOf('\\');
		String portletNameSpace = physicalFileName.substring(beginIndex + 1, physicalFileName.lastIndexOf('.'));

		XmlUtil xmlUtil = new XmlUtil();
		xmlUtil.initDocument();
		xmlUtil.loadDocument(inputStream);

		Document doc = xmlUtil.getDocument();

		Node portletAppNode = doc.getElementsByTagName("portlet-app").item(0);
		NodeList portletAppChildNodes = portletAppNode.getChildNodes();

		for (int i = 0; i < portletAppChildNodes.getLength(); i++) {
			Node portletAppChildNode = portletAppChildNodes.item(i);

			// Portlet Setting
			if (portletAppChildNode.getNodeName().equalsIgnoreCase("portlet")) {
				PortalPortlet portalPortlet = new PortalPortlet();

				// Previous Values Setting
				portalPortlet.setPortletType(portalPortletParam.getPortletType());
				portalPortlet.setPortletCategoryId(portalPortletParam.getPortletCategoryId());
				portalPortlet.setMoveable(portalPortletParam.getMoveable());
				portalPortlet.setPublicOption(portalPortletParam.getPublicOption());
				portalPortlet.setLinkType(portalPortletParam.getLinkType());
				portalPortlet.setSystemCode(portalPortletParam.getSystemCode());
				portalPortlet.setReloadMode(portalPortletParam.getReloadMode());
				portalPortlet.setRemoveMode(portalPortletParam.getRemoveMode());
				portalPortlet.setMultipleMode(portalPortletParam.getMultipleMode());
				portalPortlet.setHeaderMode(portalPortletParam.getHeaderMode());
				portalPortlet.setPreviewImageId(portalPortletParam.getPreviewImageId());
				portalPortlet.setStatus(portalPortletParam.getStatus());
				// Previous Values Setting
				
				//Security Value Setting
				portalPortlet.setSecurity(portalPortletParam.getSecurity());
				
				String urlBase = "";

				NodeList portletChildNodes = portletAppChildNode.getChildNodes();
				for (int j = 0; j < portletChildNodes.getLength(); j++) {
					Node portletChildNode = portletChildNodes.item(j);

					// Display Name Setting >> It's Optional
					if (portletChildNode.getNodeName().equalsIgnoreCase("display-name")) {
						portalPortlet.setPortletName(portletChildNode.getTextContent());
					}
					
					

					// Name Setting
					if (portletChildNode.getNodeName().equalsIgnoreCase("portlet-name")) {
						// Display Name Setting
						if (portalPortlet.getPortletName() == null){
							portalPortlet.setPortletName(portletChildNode.getTextContent());
						}

						// NormalViewUrl Setting
						//String norMalViewUrl = baseUrl + "/tck?portletName=" + portletNameSpace + "." + portletChildNode.getTextContent();
						///tck/__pmrssportlet0x2RssPortlet!|0_help
						urlBase = "/tck/__pm" + portletNameSpace + "0x2" + portletChildNode.getTextContent() + "!|0_" ;
						portalPortlet.setNormalViewUrl(urlBase+ "view");
					}
					// Portlet Support Setting
					if (portletChildNode.getNodeName().equalsIgnoreCase("supports")) {
						NodeList supportChildNodes = portletChildNode.getChildNodes();

						for (int k = 0; k < supportChildNodes.getLength(); k++) {
							// Portlet Mode Setting
							Node supportChildNode = supportChildNodes.item(k);
							if (supportChildNode.getNodeName().equalsIgnoreCase("portlet-mode")) {
								String portletMode = supportChildNode.getTextContent();

								/*
								if (portletMode.equalsIgnoreCase("VIEW")) {
									// default support
								} else if (portletMode.equalsIgnoreCase("EDIT")) {
									// not support
								} else if (portletMode.equalsIgnoreCase("POPUP")) {
									// not support
								} else
								*/	
								if (portletMode.equalsIgnoreCase("HELP")) {
									portalPortlet.setHelpMode(SUPPORT);
									portalPortlet.setHelpViewUrl(urlBase + "help");
								} else if (portletMode.equalsIgnoreCase("CONFIG") || portletMode.equalsIgnoreCase("EDIT")) {
									portalPortlet.setConfigMode(SUPPORT);
									portalPortlet.setConfigViewUrl(urlBase + "edit");
								} else if (portletMode.equalsIgnoreCase("REFRESH")) {
									portalPortlet.setReloadMode(SUPPORT);
								} else if (portletMode.equalsIgnoreCase("REMOVE")) {
									portalPortlet.setRemoveMode(SUPPORT);
								}

							}
							// Window State Setting
							else if (supportChildNode.getNodeName().equalsIgnoreCase("window-state")) {
								String windowState = supportChildNode.getTextContent();
								
								if (windowState.equalsIgnoreCase("MAXIMIZED")) {
									portalPortlet.setMaxMode(SUPPORT);
								} 
								/*
								else if (windowState.equalsIgnoreCase("NORMAL")) {
									// default support
								} else if (windowState.equalsIgnoreCase("MINIMIZED")) {
									// default support
								}
								*/
							}
						}
					}
				}

				returnArList.add(portalPortlet);
			}
		}

		xmlUtil.closeDocument();

		return returnArList;

	}
	
	/**
	 * Portlet war 파일을 extract한다
	 * 
	 * @param targetFileFullName
	 * @param searchFileName
	 * @return InputStream
	 */
	private InputStream extractWarFile(String targetFileFullName, String searchFileName) {
		InputStream inputStream = null;

		try {
			ZipFile zipFile = new ZipFile(targetFileFullName);

			if (zipFile != null) {
				ZipEntry zipEntry = zipFile.getEntry(searchFileName);

				if (zipEntry != null) {
					inputStream = zipFile.getInputStream(zipEntry);
				}
			}
		} catch (Exception e) {

		}

		return inputStream;
	}
	
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

}
