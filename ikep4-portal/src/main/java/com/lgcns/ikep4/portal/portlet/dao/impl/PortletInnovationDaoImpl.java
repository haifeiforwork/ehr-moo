package com.lgcns.ikep4.portal.portlet.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.PortletInnovationDao;
import com.lgcns.ikep4.portal.portlet.model.PortletInnovation;

/**
 * 
 * Innovation 포틀릿 DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortletInnovationDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("portletInnovationDao")
public class PortletInnovationDaoImpl extends GenericDaoSqlmap<PortletInnovation, String> implements PortletInnovationDao {

	/**
	 * Innovation 포틀릿 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletInnovation 포틀릿 설정 정보 
	 */
	public PortletInnovation getPortletInnovation(String portletConfigId) {
		return (PortletInnovation) sqlSelectForObject("portal.portlet.portletInnovation.getPortletInnovation", portletConfigId);
	}
	
	/**
	 * Innovation 포틀릿 설정 등록
	 * @param portletInnovation Portlet Innovation Model
	 */
	public void createPortletInnovation(PortletInnovation portletInnovation) {
		sqlInsert("portal.portlet.portletInnovation.createPortletInnovation", portletInnovation);
	}
	
	/**
	 * Innovation 포틀릿 설정 수정
	 * @param portletInnovation Portlet Innovation Model
	 */
	public void updatePortletInnovation(PortletInnovation portletInnovation) {
		sqlUpdate("portal.portlet.portletInnovation.updatePortletInnovation", portletInnovation);
	}
	
	@Deprecated
	public PortletInnovation get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortletInnovation object) {
		return null;
	}

	@Deprecated
	public void update(PortletInnovation object) {}

	@Deprecated
	public void remove(String id) {}

}
