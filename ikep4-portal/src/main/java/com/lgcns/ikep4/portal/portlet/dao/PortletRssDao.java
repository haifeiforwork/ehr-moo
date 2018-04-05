package com.lgcns.ikep4.portal.portlet.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.PortletRss;

/**
 * 
 * RSS 포틀릿 Dao
 *
 * @author 임종상
 * @version $Id: PortletRssDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortletRssDao extends GenericDao<PortletRss, String> {
	
	/**
	 * 포틀릿 RSS 설정 등록
	 * @param portletRss RSS PORTLET 모델
	 */
	public void createPortletRss(PortletRss portletRss);
	
	/**
	 * 포틀릿 RSS 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletRss 포틀릿 RSS 설정 정보
	 */
	public PortletRss getPortletRss(String portletConfigId);
	
	/**
	 * 포틀릿 RSS 설정 수정 
	 * @param portletRss RSS PORTLET 모델
	 */
	public void updatePortletRss(PortletRss portletRss);
}