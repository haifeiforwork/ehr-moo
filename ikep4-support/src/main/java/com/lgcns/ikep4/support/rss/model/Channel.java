package com.lgcns.ikep4.support.rss.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Channel
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: Channel.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Channel extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 5093994378219444702L;

	/**
	 * 채널 ID
	 */
	private String channelId;

	/**
	 * 등록자 ID
	 */
	private String ownerId;

	/**
	 * 채널 URL
	 */
	@NotEmpty
	@Size(min = 0, max = 1000)
	private String channelUrl;

	/**
	 * 채널 제목
	 */
	@NotEmpty
	@Size(min = 0, max = 1000)
	private String channelTitle;

	/**
	 * 정렬순서
	 */
	private String sortOrder;

	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정자
	 */
	private String updaterId;

	/**
	 * 수정자명
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * 채널 아이템 리스트
	 */
	private List<ChannelItem> channelItemList;

	/**
	 * 채널 타입
	 */
	private String channelType;

	/**
	 * 채널 설명
	 */
	private String channelDescription;

	/**
	 * 채널 중복 체크 여부
	 */
	private String channelCheck;
	
	
	/**
	 * RSS 카테고리 ID
	 */
	private String categoryId;
	
	
	/**
	 * 카테고리 이름
	 */
	private String categoryName;

	
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getOwnerId() {
		return ownerId;
	}
	

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}	

	public String getChannelUrl() {
		return channelUrl;
	}

	public void setChannelUrl(String channelUrl) {
		this.channelUrl = channelUrl;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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

	public List<ChannelItem> getChannelItemList() {
		return channelItemList;
	}

	public void setChannelItemList(List<ChannelItem> list) {
		channelItemList = list;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelDescription() {
		return channelDescription;
	}

	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}

	public String getChannelCheck() {
		return channelCheck;
	}

	public void setChannelCheck(String channelCheck) {
		this.channelCheck = channelCheck;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}
}
