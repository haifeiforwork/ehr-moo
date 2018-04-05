package com.lgcns.ikep4.portal.portlet.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * RSS PORTLET 모델
 *
 * @author 임종상
 * @version $Id: PortletRss.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PublicRss extends BaseObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 6333766514794226251L;
	
	/** RSS 리스트 */
	private List<PublicRss> rssList;
	
	/**
	 * 포틀릿 ConfigId
	 */
	private String portletConfigId;

	/**
	 * RSS URL
	 */
	private String rssUrl;
	
	/**
	 * RSS TITLE
	 */
	private String rssTitle;
	
	/**
	 * tab 개수
	 */
	private int tabCount;
	
	/**
	 * 리스트 개수
	 */
	private int listCount;
	
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
	private Date updateDate;
	

	public List<PublicRss> getRssList() {
		return this.rssList;
	}

	public void setRssList(List<PublicRss> rssList) {
		this.rssList = rssList;
	}
	public String getPortletConfigId() {
		return portletConfigId;
	}

	public void setPortletConfigId(String portletConfigId) {
		this.portletConfigId = portletConfigId;
	}

	public String getRssUrl() {
		return rssUrl;
	}

	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}

	public String getRssTitle() {
		return rssTitle;
	}
	
	public void setRssTitle(String rssTitle) {
		this.rssTitle = rssTitle;
	}
	
	public int getTabCount() {
		return tabCount;
	}

	public void setTabCount(int tabCount) {
		this.tabCount = tabCount;
	}
	
	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
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
}
