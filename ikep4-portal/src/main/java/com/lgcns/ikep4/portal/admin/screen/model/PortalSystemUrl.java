/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;

/**
 * 시스템 Url 관리 모델
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemUrl.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
public class PortalSystemUrl extends BaseObject {
	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 627043983096774370L;

	/**
	 * 순번
	 */
	private String num;
	
	/**
	 * 시스템 URL ID
	 */
	private String systemUrlId;
	
	/**
	 * URL 이름
	 */
	@NotEmpty()
	@Size(max = 100)
	private String systemUrlName;
	
	/**
	 * URL 정보
	 */
	@NotEmpty()
	@Size(max = 500)
	private String url;
	
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
	private String registDate;
	
	/**
	 * 수정자 ID
	 */
	private String updaterId;
	
	/**
	 * 수정자 이름
	 */
	private String updaterName;
	
	/**
	 * 수정일
	 */
	private String updateDate;
	
	/**
	 * 다국어 메세지
	 */
	@Valid
	private List<I18nMessage> i18nMessageList;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * @return the systemUrlId
	 */
	public String getSystemUrlId() {
		return systemUrlId;
	}

	/**
	 * @param systemUrlId the systemUrlId to set
	 */
	public void setSystemUrlId(String systemUrlId) {
		this.systemUrlId = systemUrlId;
	}

	/**
	 * @return the systemUrlName
	 */
	public String getSystemUrlName() {
		return systemUrlName;
	}

	/**
	 * @param systemUrlName the systemUrlName to set
	 */
	public void setSystemUrlName(String systemUrlName) {
		this.systemUrlName = systemUrlName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the registDate
	 */
	public String getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
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

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the i18nMessageList
	 */
	public List<I18nMessage> getI18nMessageList() {
		return i18nMessageList;
	}

	/**
	 * @param i18nMessageList the i18nMessageList to set
	 */
	public void setI18nMessageList(List<I18nMessage> i18nMessageList) {
		this.i18nMessageList = i18nMessageList;
	}
}