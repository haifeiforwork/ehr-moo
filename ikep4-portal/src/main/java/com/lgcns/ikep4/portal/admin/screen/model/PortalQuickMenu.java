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
 * 포탈 퀵 메뉴 Model
 *
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalQuickMenu.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalQuickMenu extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -6461884533889971953L;

	/**
	 * 메뉴 ID
	 */
	private String quickMenuId;
	
	/**
	 * 메뉴 이름
	 */
	@NotEmpty
	@Size(max = 100)
	private String quickMenuName;
	
	/**
	 * 정렬_순서
	 */
	private String sortOrder;
	
	/**
	 * MORE URL 정보
	 */
	@Size(max = 500)
	private String moreUrl;
	
	/**
	 * 링크 타겟 정보(INNER:창내부, WINDOW:새창)
	 */
	private String target;
	
	/**
	 * 개수 조회여부 (0:조회하지 않음, 1:조회함)
	 */
	private int count;
	
	/**
	 * 개수를 조회할 경우에 사용할 개수 조회용 URL
	 */
	@Size(max = 500)
	private String countUrl;
	
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
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 아이콘 ID
	 */
	@Size(max = 27)
	@Pattern(regexp="^[A-Za-z0-9_]*$")
	private String iconId;
	
	/**
	 * INTERVAL 간격(0:사용안함, 1:1분, 2:2분...)
	 */
	private int intervalTime;
	
	/**
	 * MORE URL 타입
	 */
	private String moreUrlType;
	
	/**
	 * 사용자 ID
	 */
	private String userId;
	
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
	 * @return the quickMenuId
	 */
	public String getQuickMenuId() {
		return quickMenuId;
	}

	/**
	 * @param quickMenuId the quickMenuId to set
	 */
	public void setQuickMenuId(String quickMenuId) {
		this.quickMenuId = quickMenuId;
	}

	/**
	 * @return the quickMenuName
	 */
	public String getQuickMenuName() {
		return quickMenuName;
	}

	/**
	 * @param quickMenuName the quickMenuName to set
	 */
	public void setQuickMenuName(String quickMenuName) {
		this.quickMenuName = quickMenuName;
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
	 * @return the moreUrl
	 */
	public String getMoreUrl() {
		return moreUrl;
	}

	/**
	 * @param moreUrl the moreUrl to set
	 */
	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
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
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the countUrl
	 */
	public String getCountUrl() {
		return countUrl;
	}

	/**
	 * @param countUrl the countUrl to set
	 */
	public void setCountUrl(String countUrl) {
		this.countUrl = countUrl;
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
	 * @return the intervalTime
	 */
	public int getIntervalTime() {
		return intervalTime;
	}

	/**
	 * @param intervalTime the intervalTime to set
	 */
	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	/**
	 * @return the moreUrlType
	 */
	public String getMoreUrlType() {
		return moreUrlType;
	}

	/**
	 * @param moreUrlType the moreUrlType to set
	 */
	public void setMoreUrlType(String moreUrlType) {
		this.moreUrlType = moreUrlType;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	
}