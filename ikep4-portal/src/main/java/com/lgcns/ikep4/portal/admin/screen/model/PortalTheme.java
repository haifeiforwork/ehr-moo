/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

package com.lgcns.ikep4.portal.admin.screen.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 포탈 테마 모델
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalTheme.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalTheme extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7211795490809288348L;
	
	/**
	 * 순번
	 */
	private String num;

	/**
	 * 테마 ID
	 */
	private String themeId;
	
	/**
	 * 테마 이름
	 */
	@NotEmpty
	@Size(max = 100)
	private String themeName;
	
	/**
	 * 설명
	 */
	@Size(max = 250)
	private String description;
	
	/**
	 * 기본 테마 여부 (0:기본 아님, 1:기본 테마)
	 */
	private int defaultOption;
	
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
	 * 테마 미리보기 이미지 파일 ID
	 */
	@NotEmpty
	private String previewImageId;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 테마 CSS 경로 정보
	 */
	@NotEmpty
	@Size(max = 2000)
	private String cssPath;
	
	/**
	 * 검색 type
	 */
	private String searchType;
	
	/**
	 * 검색 text
	 */
	private String searchText;
	
	/**
	 * 첨부파일
	 */
	private FileData fileData;
	
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
	 * @return the themeId
	 */
	public String getThemeId() {
		return themeId;
	}

	/**
	 * @param themeId the themeId to set
	 */
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	/**
	 * @return the themeName
	 */
	public String getThemeName() {
		return themeName;
	}

	/**
	 * @param themeName the themeName to set
	 */
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return defaultOption
	 */
	public int getDefaultOption() {
		return defaultOption;
	}

	/**
	 * @param defaultOption
	 */
	public void setDefaultOption(int defaultOption) {
		this.defaultOption = defaultOption;
	}

	/**
	 * @return registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId
	 */
	
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	/**
	 * @param updaterId
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * @param updaterName
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
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
	 * @return previewImageId
	 */
	public String getPreviewImageId() {
		return previewImageId;
	}

	/**
	 * @param previewImageId
	 */
	public void setPreviewImageId(String previewImageId) {
		this.previewImageId = previewImageId;
	}

	/**
	 * @return portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return cssPath
	 */
	public String getCssPath() {
		return cssPath;
	}

	/**
	 * @param cssPath
	 */
	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * @return the fileData
	 */
	public FileData getFileData() {
		return fileData;
	}

	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(FileData fileData) {
		this.fileData = fileData;
	}
}
