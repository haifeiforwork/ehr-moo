package com.lgcns.ikep4.support.rss.model;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.framework.web.SearchResult;

public class ChannelCategory extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 5480143662202116985L;

	/**
	 *
	 */

	/**
	 * RSS 카테고리 ID
	 */
	private String categoryId;
	
	/**
	 * 카테고리 이름
	 */
	@NotEmpty
	private String categoryName;
	
	/**
	 * 카테고리 메뉴 표시 순서
	 */
	private int categoryOrder;
	
	/**
	 * 카테고리 등록자 ID
	 */
	private String ownerId;
		
	/**
	 * 등록자 ID
	 */
	private String registerName;
	
	/**
	 */
	private Date registDate;
	
	/**
	 * 등록자 이름
	 */
	private String registerId;
	
	/**
	 */
	private String updaterId;
	
	/**
	 */
	private String updaterName;
	
	/**
	 */
	private Date updateDate;		
		
	private List<Channel> channel = null;
	
	private List<ChannelItem> channelItem = null;
	
	private String portlet; 
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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
	public int getCategoryOrder() {
		return categoryOrder;
	}
	public void setCategoryOrder(int categoryOrder) {
		this.categoryOrder = categoryOrder;
	}
	
	public List<Channel> getChannel() {
		return channel;
	}
	public void setChannel(List<Channel> channel) {
		this.channel = channel;
	}
	public void setChannelItem(List<ChannelItem> channelItem) {
		this.channelItem = channelItem;
	}
	public List<ChannelItem> getChannelItem() {
		return channelItem;
	}
	public void setPortlet(String portlet) {
		this.portlet = portlet;
	}
	public String getPortlet() {
		return portlet;
	}	
}