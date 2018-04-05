/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.socialpack.microblogging.base.Constant;


/**
 * 
 * Setting VO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Setting.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Setting extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 1964439730285504294L;

	/**
	 * USER_ID
	 */
	private String userId;

	/**
	 * 최대 FEED 갯수
	 */
	private int maxFeedCount = Constant.MAX_FEED_COUNT_DEFAULT;

	/**
	 * 최대 FAVORITE 갯수
	 */
	private int maxFavoriteCount = Constant.MAX_FAVORITE_COUNT_DEFAULT;

	/**
	 * 한번에 FEED하는 게시물 갯수
	 */
	private int feedsAtATime = Constant.FEEDS_AT_TIME_DEFAULT;

	/**
	 * REGIST_DATE
	 */
	private Date registDate;

	/**
	 * UPDATER_ID
	 */
	private String updaterId;

	/**
	 * UPDATE_DATE
	 */
	private Date updateDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getMaxFeedCount() {
		return maxFeedCount;
	}

	public void setMaxFeedCount(int maxFeedCount) {
		this.maxFeedCount = maxFeedCount;
	}

	public int getMaxFavoriteCount() {
		return maxFavoriteCount;
	}

	public void setMaxFavoriteCount(int maxFavoriteCount) {
		this.maxFavoriteCount = maxFavoriteCount;
	}

	public int getFeedsAtATime() {
		return feedsAtATime;
	}

	public void setFeedsAtATime(int feedsAtATime) {
		this.feedsAtATime = feedsAtATime;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
