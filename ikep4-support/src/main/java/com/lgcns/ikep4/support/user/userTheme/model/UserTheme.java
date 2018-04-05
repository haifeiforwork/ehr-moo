package com.lgcns.ikep4.support.user.userTheme.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 사용자 테마 model
 *
 * @author 임종상
 * @version $Id: UserTheme.java 16276 2011-08-18 07:09:07Z giljae $
 */
public class UserTheme extends BaseObject {

	private static final long serialVersionUID = -4161461751891775563L;
	
	/**
	 * 테마 ID
	 */
	private String themeId;
	
	/**
	 * 사용자 ID
	 */
	private String userId;
	
	/**
	 * 테마 이름
	 */
	private String themeName;
	
	/**
	 * 테마 설명
	 */
	private String description;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 기본 테마 여부 (0:기본 아님, 1:기본 테마)
	 */
	private int defaultOption;
	
	/**
	 * 테마 CSS 경로 정보
	 */
	private String cssPath;    
	    
	/**
	 * 테마 미리보기 이미지 파일 ID
	 */
	private String previewImageId;    
	
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
	private Date updateDate;
	
	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public int getDefaultOption() {
		return defaultOption;
	}

	public void setDefaultOption(int defaultOption) {
		this.defaultOption = defaultOption;
	}

	public String getCssPath() {
		return cssPath;
	}

	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}

	public String getPreviewImageId() {
		return previewImageId;
	}

	public void setPreviewImageId(String previewImageId) {
		this.previewImageId = previewImageId;
	}
}
