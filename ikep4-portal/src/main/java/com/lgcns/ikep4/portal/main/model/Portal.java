/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.main.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * portal Model
 *
 * @author 임종상(malboru80@naver.com)
 * @version $Id: Portal.java 19234 2012-06-13 04:33:46Z malboru80 $
 */
public class Portal extends BaseObject{
	
	private static final long serialVersionUID = 7275409745446021286L;

	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 포탈 이름
	 */
	@NotEmpty
	@Size(max = 100)
	private String portalName;
	
	/**
	 * 설명
	 */
	@NotEmpty
	@Size(max = 250)
	private String description;
	
	/**
	 * 기본 포탈 여부(0:기본아님, 1:기본포탈)
	 */
	private int defaultOption;
	
	/**
	 * 로그인 배경 이미지 파일 ID
	 */
	private String loginImageId;
	
	/**
	 * 메인화면 로고 이미지 파일 ID
	 */
	private String logoImageId;
	
	/**
	 * 호스트 (IP 또는 도메인주소)
	 */
	@NotEmpty
	@Size(max = 500)
	private String portalHost;
	
	/**
	 * 루트 경로 정보
	 */
	@NotEmpty
	@Size(max = 2000)
	private String portalPath;
	
	/**
	 * 현재 서비스 여부(0:비활성화, 1:활성화)
	 */
	private int active;
	
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
	 * 수정자 이름
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
	
	/**
	 * 디폴트 로케일 코드
	 */
	private String defaultLocaleCode;
	
	/**
	 * 디폴트 로케일 이름
	 */
	private String defaultLocaleName;
	
	/**
	 * 관리자
	 */
	private String adminUsers;
	
	/**
	 * 추가 관리자
	 */
	private String addAdmins;
	
	/**
	 * 삭제 관리자
	 */
	private String deleteAdmins;
	
	/**
	 * 포탈 호스트 별칭
	 */
	private String portalHostAlias;
	
	/**
	 * 공유할 포탈 ID
	 */
	private String sharePortalId;
	
	/**
	 * 포틀릿 공유 플래그
	 */
	private String sharePortlet;
	
	/**
	 * 메뉴 공우 플래그
	 */
	private String shareMenu;
	
	/**
	 * 퀵메뉴 공유 플래그
	 */
	private String shareQuickMenu;
	
	/**
	 * 시스템 공유 플래그
	 */
	private String shareSystem;
	
	/**
	 * 롤 공유 플래그
	 */
	private String shareRole;
	
	/**
	 * 롤타입 공유 플래그
	 */
	private String shareRoleType;
	
	/**
	 * 호칭 공유 플래그
	 */
	private String shareJobTitle; 
	
	/**
	 * 직위 공유 플래그
	 */
	private String shareJobPosition;
	
	/**
	 * 직급 공유 플래그
	 */
	private String shareJobRank;
	
	/**
	 * 직군 공유 플래그
	 */
	private String shareJobClass;
	
	/**
	 * 직책 공유 플래그
	 */
	private String shareJobDuty;
	
	/**
	 * 캐시 사용 여부
	 */
	private String cacheYn;
	
	public String getCacheYn() {
		return cacheYn;
	}

	public void setCacheYn(String cacheYn) {
		this.cacheYn = cacheYn;
	}

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	public String getDefaultLocaleName() {
		return defaultLocaleName;
	}

	public void setDefaultLocaleName(String defaultLocaleName) {
		this.defaultLocaleName = defaultLocaleName;
	}

	public String getDefaultLocaleCode() {
		return defaultLocaleCode;
	}

	public void setDefaultLocaleCode(String defaultLocaleCode) {
		this.defaultLocaleCode = defaultLocaleCode;
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
	 * @return the portalName
	 */
	public String getPortalName() {
		return portalName;
	}

	/**
	 * @param portalName the portalName to set
	 */
	public void setPortalName(String portalName) {
		this.portalName = portalName;
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
	 * @return the defaultOption
	 */
	public int getDefaultOption() {
		return defaultOption;
	}

	/**
	 * @param defaultOption the defaultOption to set
	 */
	public void setDefaultOption(int defaultOption) {
		this.defaultOption = defaultOption;
	}

	/**
	 * @return the loginImageId
	 */
	public String getLoginImageId() {
		return loginImageId;
	}

	/**
	 * @param loginImageId the loginImageId to set
	 */
	public void setLoginImageId(String loginImageId) {
		this.loginImageId = loginImageId;
	}

	/**
	 * @return the logoImageId
	 */
	public String getLogoImageId() {
		return logoImageId;
	}

	/**
	 * @param logoImageId the logoImageId to set
	 */
	public void setLogoImageId(String logoImageId) {
		this.logoImageId = logoImageId;
	}

	/**
	 * @return the portalHost
	 */
	public String getPortalHost() {
		return portalHost;
	}

	/**
	 * @param portalHost the portalHost to set
	 */
	public void setPortalHost(String portalHost) {
		this.portalHost = portalHost;
	}

	/**
	 * @return the portalPath
	 */
	public String getPortalPath() {
		return portalPath;
	}

	/**
	 * @param portalPath the portalPath to set
	 */
	public void setPortalPath(String portalPath) {
		this.portalPath = portalPath;
	}

	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(int active) {
		this.active = active;
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

	/**
	 * @return the adminUsers
	 */
	public String getAdminUsers() {
		return adminUsers;
	}

	/**
	 * @param adminUsers the adminUsers to set
	 */
	public void setAdminUsers(String adminUsers) {
		this.adminUsers = adminUsers;
	}

	/**
	 * @return the addAdmins
	 */
	public String getAddAdmins() {
		return addAdmins;
	}

	/**
	 * @param addAdmins the addAdmins to set
	 */
	public void setAddAdmins(String addAdmins) {
		this.addAdmins = addAdmins;
	}

	/**
	 * @return the deleteAdmins
	 */
	public String getDeleteAdmins() {
		return deleteAdmins;
	}

	/**
	 * @param deleteAdmins the deleteAdmins to set
	 */
	public void setDeleteAdmins(String deleteAdmins) {
		this.deleteAdmins = deleteAdmins;
	}

	public String getPortalHostAlias() {
		return portalHostAlias;
	}

	public void setPortalHostAlias(String portalHostAlias) {
		this.portalHostAlias = portalHostAlias;
	}

	public String getSharePortalId() {
		return sharePortalId;
	}

	public void setSharePortalId(String sharePortalId) {
		this.sharePortalId = sharePortalId;
	}

	public String getSharePortlet() {
		return sharePortlet;
	}

	public void setSharePortlet(String sharePortlet) {
		this.sharePortlet = sharePortlet;
	}

	public String getShareMenu() {
		return shareMenu;
	}

	public void setShareMenu(String shareMenu) {
		this.shareMenu = shareMenu;
	}

	public String getShareQuickMenu() {
		return shareQuickMenu;
	}

	public void setShareQuickMenu(String shareQuickMenu) {
		this.shareQuickMenu = shareQuickMenu;
	}

	public String getShareSystem() {
		return shareSystem;
	}

	public void setShareSystem(String shareSystem) {
		this.shareSystem = shareSystem;
	}

	public String getShareRole() {
		return shareRole;
	}

	public void setShareRole(String shareRole) {
		this.shareRole = shareRole;
	}

	public String getShareRoleType() {
		return shareRoleType;
	}

	public void setShareRoleType(String shareRoleType) {
		this.shareRoleType = shareRoleType;
	}

	public String getShareJobTitle() {
		return shareJobTitle;
	}

	public void setShareJobTitle(String shareJobTitle) {
		this.shareJobTitle = shareJobTitle;
	}

	public String getShareJobPosition() {
		return shareJobPosition;
	}

	public void setShareJobPosition(String shareJobPosition) {
		this.shareJobPosition = shareJobPosition;
	}

	public String getShareJobRank() {
		return shareJobRank;
	}

	public void setShareJobRank(String shareJobRank) {
		this.shareJobRank = shareJobRank;
	}

	public String getShareJobClass() {
		return shareJobClass;
	}

	public void setShareJobClass(String shareJobClass) {
		this.shareJobClass = shareJobClass;
	}

	public String getShareJobDuty() {
		return shareJobDuty;
	}

	public void setShareJobDuty(String shareJobDuty) {
		this.shareJobDuty = shareJobDuty;
	}
}
