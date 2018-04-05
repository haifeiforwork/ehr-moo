package com.lgcns.ikep4.portal.portlet.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.PortletRssDao;
import com.lgcns.ikep4.portal.portlet.model.PortletRss;

/**
 * 포틀릿 RSS DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortletRssDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("portletRssDao")
public class PortletRssDaoImpl extends GenericDaoSqlmap<PortletRss, String> implements PortletRssDao {

	/**
	 * 포틀릿 RSS 설정 등록
	 * @param portletRss RSS PORTLET 모델
	 */
	public void createPortletRss(PortletRss portletRss) {
		sqlInsert("portal.portlet.portletRss.createPortletRss", portletRss);
	}
	
	/**
	 * 포틀릿 RSS 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletRss 포틀릿 RSS 설정 정보
	 */
	public PortletRss getPortletRss(String portletConfigId) {
		return (PortletRss) sqlSelectForObject("portal.portlet.portletRss.getPortletRss", portletConfigId);
	}
	
	/**
	 * 포틀릿 RSS 설정 수정 
	 * @param portletRss RSS PORTLET 모델
	 */
	public void updatePortletRss(PortletRss portletRss) {
		sqlUpdate("portal.portlet.portletRss.updatePortletRss", portletRss);
	}
	
	@Deprecated
	public PortletRss get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortletRss object) {
		return null;
	}

	@Deprecated
	public void update(PortletRss object) {}

	@Deprecated
	public void remove(String id) {}
}