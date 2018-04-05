package com.lgcns.ikep4.support.rss.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * ChannelItem
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ChannelItem.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class ChannelItem extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 2841380092704396039L;

	/**
	 * 채널 아이템 Id
	 */
	private String itemId;

	/**
	 * 채널 ID
	 */
	private String channelId;

	/**
	 * 채널 아이템 URL
	 */
	private String itemUrl;

	/**
	 * 채널 아이템 제목
	 */
	private String itemTitle;

	/**
	 * 채널 아이템 내용
	 */
	private String itemDescription;

	/**
	 * 등록자명
	 */
	private String itemRegister;

	/**
	 * 등록자명(영문)
	 */
	private String itemRegisterEnglish;

	/**
	 * 채널 아이템 발행일
	 */
	private Date itemPublishDate;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		
		if(itemDescription.length() < 4000)
		{
			this.itemDescription = itemDescription;
		}
		else
		{
			this.itemDescription = itemDescription.substring(0, 3999);
		}
	}

	public String getItemRegister() {
		return itemRegister;
	}

	public void setItemRegister(String itemRegister) {
		this.itemRegister = itemRegister;
	}

	public Date getItemPublishDate() {
		return itemPublishDate;
	}

	public void setItemPublishDate(Date itemPublishDate) {
		this.itemPublishDate = itemPublishDate;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getItemRegisterEnglish() {
		return itemRegisterEnglish;
	}

	public void setItemRegisterEnglish(String itemRegisterEnglish) {
		this.itemRegisterEnglish = itemRegisterEnglish;
	}

}
