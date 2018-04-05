/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.model;

import java.util.List;

import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 소셜블로그 기번 정보를 저장하기 위한 테이블
 *
 * @author 이형운
 * @version $Id: SocialBlog.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialBlog extends BaseObject {

	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -2987289386490702849L;

	/**
	 * 소셜블로그 ID
	 */
	private String blogId;
	
	/**
	 * 소셜블로그 소유자 ID
	 */
	private String ownerId;
	
	/**
	 * 소셜블로그 소개 메시지
	 */
	@Size(min=0, max=150)
	private String introduction;
	
	/**
	 * 소셜블로그 제공된 배경 이미지 URL
	 */
	private String imageUrl;
	
	/**
	 * 소셜블로그 개별 이미지 사용여부
	 * (0: 제공 이미지, 1: 개별 이미지)
	 */
	private Integer isPrivateImage;
	
	/**
	 * 소셜블로그 개별 이미지를 배경이미지로 사용 시 등록한 이미지 파일 아이디
	 */
	private String imageFileId;
	
	/**
	 * 레이아웃 ID
	 */
	private String layoutId;
	
	/**
	 * 소셜 블로그 소유자 정보
	 */
	private User owner;
	
	/**
	 * 소셜 블로그의 
	 */
	private List<SocialBlogPortletLayout> socialBlogPortletLayoutList;

	/**
	 * @return the blogId
	 */
	public String getBlogId() {
		return blogId;
	}

	/**
	 * @param blogId the blogId to set
	 */
	public void setBlogId(String blogId) {
		this.blogId = blogId;
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
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the isPrivateImage
	 */
	public Integer getIsPrivateImage() {
		return isPrivateImage;
	}

	/**
	 * @param isPrivateImage the isPrivateImage to set
	 */
	public void setIsPrivateImage(Integer isPrivateImage) {
		this.isPrivateImage = isPrivateImage;
	}

	/**
	 * @return the imageFileId
	 */
	public String getImageFileId() {
		return imageFileId;
	}

	/**
	 * @param imageFileId the imageFileId to set
	 */
	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	/**
	 * @return the layoutId
	 */
	public String getLayoutId() {
		return layoutId;
	}

	/**
	 * @param layoutId the layoutId to set
	 */
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the socialBlogPortletLayoutList
	 */
	public List<SocialBlogPortletLayout> getSocialBlogPortletLayoutList() {
		return socialBlogPortletLayoutList;
	}

	/**
	 * @param socialBlogPortletLayoutList the socialBlogPortletLayoutList to set
	 */
	public void setSocialBlogPortletLayoutList(List<SocialBlogPortletLayout> socialBlogPortletLayoutList) {
		this.socialBlogPortletLayoutList = socialBlogPortletLayoutList;
	}
	

}
