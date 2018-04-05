/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.model;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;


/**
 * 포탈 페이지 model
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalPage.java 19326 2012-06-19 06:55:36Z yu_hs $
 */
public class PortalPage extends BaseObject {

	private static final long serialVersionUID = -8824092015577247290L;

	/**
	 * 페이지 ID
	 */
	private String pageId;

	/**
	 * 페이지 이름
	 */
	private String pageName;
	
	/**
	 * 레이아웃 이름
	 */
	private String layoutName;

	/**
	 * 공용 여부(0:개인용, 1:공용)
	 */
	private int common;

	/**
	 * 시스템 코드
	 */
	private String systemCode;
	
	/**
	 * 시스템 이름
	 */
	private String systemName;

	/**
	 * 소유자 ID
	 */
	private String ownerId;
	
	/**
	 * 소유자 이름
	 */
	private String ownerName;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정일 ID
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;
	
	private int commonPortletVerticalWidth;

	private PortalSecurity security;

	/**
	 * rowNum
	 */
	private int num;
	
	private List<String> data;
	
	private List<String> layoutData;
	
	@Valid
	private List<I18nMessage> i18nMessageList;
	
	public List<I18nMessage> getI18nMessageList() {
		return i18nMessageList;
	}

	public void setI18nMessageList(List<I18nMessage> i18nMessageList) {
		this.i18nMessageList = i18nMessageList;
	}
	
	public void setData(List<String> data) {
		this.data = data;
	}
	
	public List<String> getData() {
		return data;
	}

	public void setLayoutData(List<String> layoutData) {
		this.layoutData = layoutData;
	}
	
	public List<String> getLayoutData() {
		return layoutData;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the pageId
	 */
	public String getPageId() {
		return pageId;
	}
	
	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	/**
	 * @return the pageName
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * @param pageName the pageName to set
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * @return the common
	 */
	public int getCommon() {
		return common;
	}

	/**
	 * @param common the common to set
	 */
	public void setCommon(int common) {
		this.common = common;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}
	
	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getLayoutName() {
		return layoutName;
	}

	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}
	
	public PortalSecurity getSecurity() {
		return security;
	}

	public void setSecurity(PortalSecurity security) {
		this.security = security;
	}

	public int getCommonPortletVerticalWidth() {
		return commonPortletVerticalWidth;
	}

	public void setCommonPortletVerticalWidth(int commonPortletVerticalWidth) {
		this.commonPortletVerticalWidth = commonPortletVerticalWidth;
	}
}
