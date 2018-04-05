package com.lgcns.ikep4.portal.portlet.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.PublicRssDao;
import com.lgcns.ikep4.portal.portlet.model.PublicRss;

/**
 * 포틀릿 RSS DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortletRssDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("publicRssDao")
public class PublicRssDaoImpl extends GenericDaoSqlmap<PublicRss, String> implements PublicRssDao {

	/**
	 * 포틀릿 RSS 설정 등록
	 * @param portletRss RSS PORTLET 모델
	 */
	public void createPublicRss(PublicRss publicRss) {
		sqlInsert("portal.portlet.publicRss.createPublicRss", publicRss);
	}
	
	/**
	 * 포틀릿 RSS 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletRss 포틀릿 RSS 설정 정보
	 */
	public PublicRss getPublicRss(String portletConfigId) {
		return (PublicRss) sqlSelectForObject("portal.portlet.publicRss.getPublicRss", portletConfigId);
	}
	
	/**
	 * 포틀릿 RSS 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletRss 포틀릿 RSS 설정 정보
	 */
	public List<PublicRss> getPublicRssList() {
		return sqlSelectForList("portal.portlet.publicRss.getPublicRssList");
	}

	/**
	 * 포틀릿 RSS 설정 수정 
	 * @param portletRss RSS PORTLET 모델
	 */
	public void updatePublicRss(PublicRss publicRss) {
		sqlUpdate("portal.portlet.publicRss.updatePublicRss", publicRss);
	}
	
	public void deleteAll() {
		sqlDelete("portal.portlet.publicRss.deleteAllRssChannel");
	}
	
	
	@Deprecated
	public PublicRss get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PublicRss object) {
		return null;
	}

	@Deprecated
	public void update(PublicRss object) {}

	@Deprecated
	public void remove(String id) {}
	
}