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
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;


/**
 * 시스템 map VO
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystem.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
public class PortalSystem extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 5578830535539871316L;
	
	/**
	 * 순서
	 */
	private String num;

	/**
	 * 트리 레벨
	 */
	private int depthLevel;
	
	/**
	 * 시스템 코드
	 */
	@NotEmpty
	@Size(max = 13)
	private String systemCode;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 시스템 이름
	 */
	@NotEmpty
	@Size(max = 100)
	private String systemName;
	
	/**
	 * 시스템 설명
	 */
	@Size(max = 250)
	private String description;
	
	/**
	 * 부모 시스템 코드
	 */
	private String parentSystemCode;
	
	/**
	 * 시스템 종류 (CATEGORY:구분용 시스템, ITEM:실제 URL을 가지고 있음)
	 */
	private String systemType;
	
	/**
	 * 메인화면 출력여부(0:미표시, 1:표시)
	 */
	private int mainView;
	
	/**
	 * URL 정보
	 */
	@Size(max = 500)
	private String url;
	
	/**
	 * 정렬_순서 (실제 코드순서가 문자열로 되어있다고 간주)
	 */
	private String sortOrder;
	
	/**
	 * 링크 타겟 정보(INNER:창내부, WINDOW:새창)
	 */
	private String target;
	
	/**
	 * 소유자 ID
	 */
	private String ownerId;
	
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
	 * URL 타입
	 */
	private String urlType;
	
	/**
	 * 수정전 시스템 코드
	 */
	private String oldSystemCode;
	
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
	 * 다국어 메세지
	 */
	@Valid
	private List<I18nMessage> i18nMessageList;
	
	/**
	 * 권한관리
	 */
	private PortalSecurity security;
	
	/**
	 * 클래스명
	 */
	private String className;
	
	/**
	 * 수정 이전 클래스명
	 */
	private String oldClassName;
	
	private String adminUserAddFlag;
	                   
	public String getAdminUserAddFlag() {
		return adminUserAddFlag;
	}

	public void setAdminUserAddFlag(String adminUserAddFlag) {
		this.adminUserAddFlag = adminUserAddFlag;
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
	 * @return the depthLevel
	 */
	public int getDepthLevel() {
		return depthLevel;
	}

	/**
	 * @param depthLevel the depthLevel to set
	 */
	public void setDepthLevel(int depthLevel) {
		this.depthLevel = depthLevel;
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
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
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
	 * @return the parentSystemCode
	 */
	public String getParentSystemCode() {
		return parentSystemCode;
	}

	/**
	 * @param parentSystemCode the parentSystemCode to set
	 */
	public void setParentSystemCode(String parentSystemCode) {
		this.parentSystemCode = parentSystemCode;
	}

	/**
	 * @return the systemType
	 */
	public String getSystemType() {
		return systemType;
	}

	/**
	 * @param systemType the systemType to set
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	/**
	 * @return the mainView
	 */
	public int getMainView() {
		return mainView;
	}

	/**
	 * @param mainView the mainView to set
	 */
	public void setMainView(int mainView) {
		this.mainView = mainView;
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
	 * @return the oldSystemCode
	 */
	public String getOldSystemCode() {
		return oldSystemCode;
	}

	/**
	 * @param oldSystemCode the oldSystemCode to set
	 */
	public void setOldSystemCode(String oldSystemCode) {
		this.oldSystemCode = oldSystemCode;
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

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the oldClassName
	 */
	public String getOldClassName() {
		return oldClassName;
	}

	/**
	 * @param oldClassName the oldClassName to set
	 */
	public void setOldClassName(String oldClassName) {
		this.oldClassName = oldClassName;
	}
}