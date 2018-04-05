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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;

/**
 * 포탈 메뉴 VO
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalMenu.java 19249 2012-06-13 06:42:29Z malboru80 $
 */
public class PortalMenu extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -722884521372616283L;

	/**
	 * 순서
	 */
	private String num;
	
	/**
	 * 메뉴 ID
	 */
	private String menuId;
	
	/**
	 * 메뉴 이름
	 */
	@NotEmpty
	@Size(max = 100)
	private String menuName;
	
	/**
	 * 메뉴 설명
	 */
	@NotEmpty
	@Size(max = 250)
	private String description;
	
	/**
	 * 부모 메뉴 ID
	 */
	private String parentMenuId;
	
	/**
	 * 시스템 코드
	 */
	private String systemCode;
	
	/**
	 * 정렬_순서 (실제 코드순서가 문자열로 되어있다고 간주)
	 */
	private String sortOrder;
	
	/**
	 * 타입구분(CATEGORY:폴더구분용, ITEM:실사용)
	 */
	private String menuType;
	
	/**
	 * URL 정보
	 */
	@Size(max = 500)
	private String url;
	
	/**
	 * 링크 타겟 정보(INNER:창내부, WINDOW:새창)
	 */
	private String target;
	
	/**
	 * 등록자  ID
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
	 * 등록자  ID
	 */
	private String updaterId;
	
	/**
	 * 등록자 이름
	 */
	private String updaterName;
	
	/**
	 * 등록일
	 */
	private String updateDate;
	
	/**
	 * 아이콘 ID (CSS Class ID)
	 */
	@Size(max = 27)
	@Pattern(regexp="^[A-Za-z0-9_]*$")
	private String iconId;
	
	/**
	 * URL 구분(URL:URL주소, JAVASCRIPT:JAVASCRIPT 함수)
	 */
	private String urlType;
	
	/**
	 * read 퍼미션
	 */
	private ACLResourcePermission aclResourcePermissionRead;
	
	/**
	 * 사용자 권한 배열
	 */
	private String[] addrUserList;
	
	/**
	 * 그룹 권한 배열
	 */
	private String[] addrGroupTypeList;
	
	/**
	 * 역할 권한 배열
	 */
	private String[] addrRoleList;
	
	/**
	 * 고용형태 권한 배열
	 */
	private String[] addrJobClassList;
	
	/**
	 * 직책 권한 배열
	 */
	private String[] addrJobDutyList;
	
	/**
	 * 메뉴 레벨
	 */
	private String depthLevel;
	
	/**
	 * 다국어 메세지
	 */
	@Valid
	private List<I18nMessage> i18nMessageList;
	
	/**
	 * 권한관리
	 */
	private PortalSecurity security;

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
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the parentMenuId
	 */
	public String getParentMenuId() {
		return parentMenuId;
	}

	/**
	 * @param parentMenuId the parentMenuId to set
	 */
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
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
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * @param menuType the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
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
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
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
	 * @return the iconId
	 */
	public String getIconId() {
		return iconId;
	}

	/**
	 * @param iconId the iconId to set
	 */
	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	/**
	 * @return the urlType
	 */
	public String getUrlType() {
		return urlType;
	}

	/**
	 * @param urlType the urlType to set
	 */
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	/**
	 * @return the aclResourcePermissionRead
	 */
	public ACLResourcePermission getAclResourcePermissionRead() {
		return aclResourcePermissionRead;
	}

	/**
	 * @param aclResourcePermissionRead the aclResourcePermissionRead to set
	 */
	public void setAclResourcePermissionRead(ACLResourcePermission aclResourcePermissionRead) {
		this.aclResourcePermissionRead = aclResourcePermissionRead;
	}

	/**
	 * @return the addrUserList
	 */
	public String[] getAddrUserList() {
		return addrUserList;
	}

	/**
	 * @param addrUserList the addrUserList to set
	 */
	public void setAddrUserList(String[] addrUserList) {
		this.addrUserList = new String[addrUserList.length]; 
		 
	    for(int i=0; i>addrUserList.length; i++) {
	 
	        this.addrUserList[i] = addrUserList[i];
	 
	    }
	}

	/**
	 * @return the addrGroupTypeList
	 */
	public String[] getAddrGroupTypeList() {
		return addrGroupTypeList;
	}

	/**
	 * @param addrGroupTypeList the addrGroupTypeList to set
	 */
	public void setAddrGroupTypeList(String[] addrGroupTypeList) {
		this.addrGroupTypeList = new String[addrGroupTypeList.length]; 
		 
	    for(int i=0; i>addrGroupTypeList.length; i++) {
	 
	        this.addrGroupTypeList[i] = addrGroupTypeList[i];
	 
	    }
	}

	/**
	 * @return the addrRoleList
	 */
	public String[] getAddrRoleList() {
		return addrRoleList;
	}

	/**
	 * @param addrRoleList the addrRoleList to set
	 */
	public void setAddrRoleList(String[] addrRoleList) {
		this.addrRoleList = new String[addrRoleList.length]; 
		 
	    for(int i=0; i>addrRoleList.length; i++) {
	 
	        this.addrRoleList[i] = addrRoleList[i];
	 
	    }
	}

	/**
	 * @return the addrJobClassList
	 */
	public String[] getAddrJobClassList() {
		return addrJobClassList;
	}

	/**
	 * @param addrJobClassList the addrJobClassList to set
	 */
	public void setAddrJobClassList(String[] addrJobClassList) {
		this.addrJobClassList = new String[addrJobClassList.length]; 
		 
	    for(int i=0; i>addrJobClassList.length; i++) {
	 
	        this.addrJobClassList[i] = addrJobClassList[i];
	 
	    }
	}

	/**
	 * @return the addrJobDutyList
	 */
	public String[] getAddrJobDutyList() {
		return addrJobDutyList;
	}

	/**
	 * @param addrJobDutyList the addrJobDutyList to set
	 */
	public void setAddrJobDutyList(String[] addrJobDutyList) {
		this.addrJobDutyList = new String[addrJobDutyList.length]; 
		 
	    for(int i=0; i>addrJobDutyList.length; i++) {
	 
	        this.addrJobDutyList[i] = addrJobDutyList[i];
	 
	    }
	}

	/**
	 * @return the depthLevel
	 */
	public String getDepthLevel() {
		return depthLevel;
	}

	/**
	 * @param depthLevel the depthLevel to set
	 */
	public void setDepthLevel(String depthLevel) {
		this.depthLevel = depthLevel;
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

	/**
	 * @return the security
	 */
	public PortalSecurity getSecurity() {
		return security;
	}

	/**
	 * @param security the security to set
	 */
	public void setSecurity(PortalSecurity security) {
		this.security = security;
	}
}