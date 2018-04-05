package com.lgcns.ikep4.portal.portlet.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.PortletInnovation;

/**
 * 
 * Innovation 포틀릿 DAO
 *
 * @author 임종상
 * @version $Id: PortletInnovationDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortletInnovationDao extends GenericDao<PortletInnovation, String> {
	
	/**
	 * Innovation 포틀릿 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletInnovation 포틀릿 설정 정보 
	 */
	public PortletInnovation getPortletInnovation(String portletConfigId);
	
	/**
	 * Innovation 포틀릿 설정 등록
	 * @param portletInnovation Portlet Innovation Model
	 */
	public void createPortletInnovation(PortletInnovation portletInnovation);
	
	/**
	 * Innovation 포틀릿 설정 수정
	 * @param portletInnovation Portlet Innovation Model
	 */
	public void updatePortletInnovation(PortletInnovation portletInnovation);
}