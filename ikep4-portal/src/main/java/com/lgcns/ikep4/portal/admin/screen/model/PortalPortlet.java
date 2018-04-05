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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;


/**
 * 포탈 포틀릿 VO
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalPortlet.java 19404 2012-06-21 02:02:00Z malboru80 $
 */
public class PortalPortlet extends BaseObject {
	public interface CreateFormGroup {
	}

	public interface UpdateFormGroup {
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -5661317295765060796L;

	@Valid
	private List<I18nMessage> i18nMessageList;

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
	 * 기준이되는 Locale 갯수
	 */
	private int localeSize;
	
	public int getLocaleSize() {
		return localeSize;
	}

	public void setLocaleSize(int localeSize) {
		this.localeSize = localeSize;
	}


	/**
	 * 포틀릿 ID
	 */
	@NotNull(groups = { UpdateFormGroup.class })
	private String portletId;

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
	private String updateDate;

	/**
	 * 포틀릿 이름
	 */
	private String portletName;

	/**
	 * 소유자 ID
	 */
	private String ownerId;

	/**
	 * 이동가능 여부(0:이동불가능, 1:이동가능)
	 */
	@NotNull
	private int moveable;

	/**
	 * 포틀릿 종류
	 */
	@NotEmpty
	private String portletType;

	/**
	 * 카테고리 ID
	 */
	@NotEmpty
	private String portletCategoryId;

	/**
	 * 시스템 코드
	 */
	@NotEmpty
	private String systemCode;

	/**
	 * 미리보기 이미지 파일 ID
	 */
	@NotEmpty
	private String previewImageId;

	/**
	 * 시스템 이름
	 */
	private String systemName;

	/**
	 * 카테고리 이름
	 */
	private String portletCategoryName;

	/**
	 * 검색 type
	 */
	private String searchType;

	/**
	 * 검색 text
	 */
	private String searchText;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * "포틀릿의 내부 컨텐츠 링크 방법(SIMPLE : 포틀릿 렌더링 URL을 페이지에 INCLUDE함,  JSON : JSON 형태로 REQUEST를 보냄,  IFRAME : IFRAME 형태로 렌터링, IFRAME_EXT: 외부아이프레임 )"
	 */
	@NotEmpty
	private String linkType;

	/**
	 * 포틀릿이 페이지에서 등록되는 열 위치 값
	 */
	private String defaultColIndex;

	/**
	 * 포틀릿 셋팅 ID
	 */
	private String portletConfigId;

	/**
	 * 열 인덱스
	 */
	private int colIndex;

	/**
	 * 행 인덱스
	 */
	private int rowIndex;

	/**
	 * 포틀릿 최소,일반,최대 화면 플래그 값(최소 : MIN, 일반 : NORMAL, 최대 : MAX)
	 */
	private String viewMode;

	/**
	 * 기본 뷰 URL
	 */
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 0, max = 1000)
	private String normalViewUrl;

	/**
	 * 설정모드 URL
	 */
	@Size(min = 0, max = 1000)
	private String configViewUrl;

	/**
	 * 포틀릿 최대화 모드일때 뷰 URL
	 */
	@Size(min = 0, max = 1000)
	private String maxViewUrl;

	/**
	 * 포틀릿 도움말 모드일때 뷰 URL
	 */
	@Size(min = 0, max = 1000)
	private String helpViewUrl;

	/**
	 * 포틀릿 더보기 모드일때 뷰 URL
	 */
	@Size(min = 0, max = 1000)
	private String moreViewUrl;

	/**
	 * 설정모드 지원 여부 (0:미지원, 1:지원)
	 */
	@NotNull
	private int configMode;

	/**
	 * 포틀릿 최대화 모드 지원 여부 (0:미지원, 1:지원)
	 */
	@NotNull
	private int maxMode;

	/**
	 * RELOAD 모드 지원 여부 (0:미지원, 1:지원)
	 */
	@NotNull
	private int reloadMode;

	/**
	 * 삭제 모드 지원 여부 (0:미지원, 1:지원)
	 */
	@NotNull
	private int removeMode;

	/**
	 * 더보기 모드 지원 여부 (0:미지원, 1:지원)
	 */
	@NotNull
	private int moreMode;

	/**
	 * 도움말 모드 지원 여부 (0:미지원, 1:지원)
	 */
	@NotNull
	private int helpMode;

	/**
	 * 목록 번호
	 */
	private int num;

	/**
	 * .WAR 파일 리파지토리 아이디
	 */
	private String warFileId;

	/**
	 * 포틀릿 공용여부 (0:개인용, 1:공용), 공용일 경우 개인설정 화면에서나타나지 않음
	 */
	@NotNull
	private int publicOption;

	/**
	 * .WAR 파일 명
	 */
	private String warFileName;

	/**
	 * 포틀릿 헤더 조회여부(0:헤더없음,1:헤더 있음)
	 */
	@NotNull
	private int headerMode;

	/**
	 * 포틀릿 사용 여부 (0:미사용, 1:사용)
	 */
	@NotNull
	private int status;

	/**
	 * 포틀릿 복수 생성 여부(0 : 복수생성, 1 : 하나만 생성)
	 */
	private int multipleMode;
	

	/**
	 * 권한 설정정보
	 */
	private PortalSecurity security;
	
	
	/**
	 * 외부아이프레임(IFRAME_EXT 일경우 height)
	 */
	private int iframeHeight;
	
	/**
	 * 포탈 생성시 공유 여부
	 */
	private String shareYn;
	
	/**
	 * 포틀릿 컨텐츠 캐시 사용여부
	 */
	private String cacheYn;
	
	/**
	 * 포틀릿 컨텐츠 캐시 주기(초단위) 
	 */
	private String cacheLiveSecond;
	
	/**
	 * 포틀릿 컨텐츠 캐시 max 갯수
	 */
	private String cacheMaxCount;
	
	/**
	 * 설정 파일의  캐시 STR
	 */
	private String cacheNameStr;
	
	/**
	 * 설정 파일의 캐시모드 STR
	 */
	private String cacheModeStr;
	
	/**
	 * 설정 파일의 캐시 ELEMENT STR
	 */
	private String elementKeyStr;
	
	public String getCacheNameStr() {
		return cacheNameStr;
	}

	public void setCacheNameStr(String cacheNameStr) {
		this.cacheNameStr = cacheNameStr;
	}

	public String getCacheModeStr() {
		return cacheModeStr;
	}

	public void setCacheModeStr(String cacheModeStr) {
		this.cacheModeStr = cacheModeStr;
	}

	public String getElementKeyStr() {
		return elementKeyStr;
	}

	public void setElementKeyStr(String elementKeyStr) {
		this.elementKeyStr = elementKeyStr;
	}

	public String getCacheYn() {
		return cacheYn;
	}

	public void setCacheYn(String cacheYn) {
		this.cacheYn = cacheYn;
	}

	public String getCacheLiveSecond() {
		return cacheLiveSecond;
	}

	public void setCacheLiveSecond(String cacheLiveSecond) {
		this.cacheLiveSecond = cacheLiveSecond;
	}

	public String getCacheMaxCount() {
		return cacheMaxCount;
	}

	public void setCacheMaxCount(String cacheMaxCount) {
		this.cacheMaxCount = cacheMaxCount;
	}

	public PortalSecurity getSecurity() {
		return security;
	}

	public void setSecurity(PortalSecurity security) {
		this.security = security;
	}

	public String getShareYn() {
		return shareYn;
	}

	public void setShareYn(String shareYn) {
		this.shareYn = shareYn;
	}

	public int getMultipleMode() {
		return multipleMode;
	}

	public void setMultipleMode(int multipleMode) {
		this.multipleMode = multipleMode;
	}

	public String getConfigViewUrl() {
		return configViewUrl;
	}

	public void setConfigViewUrl(String configViewUrl) {
		this.configViewUrl = configViewUrl;
	}

	public String getMaxViewUrl() {
		return maxViewUrl;
	}

	public void setMaxViewUrl(String maxViewUrl) {
		this.maxViewUrl = maxViewUrl;
	}

	public int getConfigMode() {
		return configMode;
	}

	public void setConfigMode(int configMode) {
		this.configMode = configMode;
	}

	public int getMaxMode() {
		return maxMode;
	}

	public void setMaxMode(int maxMode) {
		this.maxMode = maxMode;
	}

	public int getReloadMode() {
		return reloadMode;
	}

	public void setReloadMode(int reloadMode) {
		this.reloadMode = reloadMode;
	}

	public int getRemoveMode() {
		return removeMode;
	}

	public void setRemoveMode(int removeMode) {
		this.removeMode = removeMode;
	}

	public int getMoreMode() {
		return moreMode;
	}

	public void setMoreMode(int moreMode) {
		this.moreMode = moreMode;
	}

	public String getNormalViewUrl() {
		return normalViewUrl;
	}

	public void setNormalViewUrl(String normalViewUrl) {
		this.normalViewUrl = normalViewUrl;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getHelpViewUrl() {
		return helpViewUrl;
	}

	public void setHelpViewUrl(String helpViewUrl) {
		this.helpViewUrl = helpViewUrl;
	}

	public int getHelpMode() {
		return helpMode;
	}

	public void setHelpMode(int helpMode) {
		this.helpMode = helpMode;
	}

	public String getDefaultColIndex() {
		return defaultColIndex;
	}

	public void setDefaultColIndex(String defaultColIndex) {
		this.defaultColIndex = defaultColIndex;
	}

	public String getPortletConfigId() {
		return portletConfigId;
	}

	public void setPortletConfigId(String portletConfigId) {
		this.portletConfigId = portletConfigId;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
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
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
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
	 * @return the portletCategoryName
	 */
	public String getPortletCategoryName() {
		return portletCategoryName;
	}

	/**
	 * @param portletCategoryName the portletCategoryName to set
	 */
	public void setPortletCategoryName(String portletCategoryName) {
		this.portletCategoryName = portletCategoryName;
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
	 * @return the moveable
	 */
	public int getMoveable() {
		return moveable;
	}

	/**
	 * @param moveable the moveable to set
	 */
	public void setMoveable(int moveable) {
		this.moveable = moveable;
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
	 * @return the portletId
	 */
	public String getPortletId() {
		return portletId;
	}

	/**
	 * @param portletId the portletId to set
	 */
	public void setPortletId(String portletId) {
		this.portletId = portletId;
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
	 * @return the portletName
	 */
	public String getPortletName() {
		return portletName;
	}

	/**
	 * @param portletName the portletName to set
	 */
	public void setPortletName(String portletName) {
		this.portletName = portletName;
	}

	/**
	 * @return the portletType
	 */
	public String getPortletType() {
		return portletType;
	}

	/**
	 * @param portletType the portletType to set
	 */
	public void setPortletType(String portletType) {
		this.portletType = portletType;
	}

	/**
	 * @return the portletCategoryId
	 */
	public String getPortletCategoryId() {
		return portletCategoryId;
	}

	/**
	 * @param portletCategoryId the portletCategoryId to set
	 */
	public void setPortletCategoryId(String portletCategoryId) {
		this.portletCategoryId = portletCategoryId;
	}

	/**
	 * @return the previewImageId
	 */
	public String getPreviewImageId() {
		return previewImageId;
	}

	/**
	 * @param previewImageId the previewImageId to set
	 */
	public void setPreviewImageId(String previewImageId) {
		this.previewImageId = previewImageId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getMoreViewUrl() {
		return moreViewUrl;
	}

	public void setMoreViewUrl(String moreViewUrl) {
		this.moreViewUrl = moreViewUrl;
	}

	public String getWarFileId() {
		return warFileId;
	}

	public void setWarFileId(String warFileId) {
		this.warFileId = warFileId;
	}

	public int getPublicOption() {
		return publicOption;
	}

	public void setPublicOption(int publicOption) {
		this.publicOption = publicOption;
	}

	public String getWarFileName() {
		return warFileName;
	}

	public void setWarFileName(String warFileName) {
		this.warFileName = warFileName;
	}

	public int getHeaderMode() {
		return headerMode;
	}

	public void setHeaderMode(int headerMode) {
		this.headerMode = headerMode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getIframeHeight() {
		return iframeHeight;
	}

	public void setIframeHeight(int iframeHeight) {
		this.iframeHeight = iframeHeight;
	}
}
