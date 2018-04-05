package com.lgcns.ikep4.portal.portlet.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.PortletManagerMessage;

/**
 * 
 * ManagerMessage 포틀릿 DAO
 *
 * @author 박순성
 * @version $Id: PortletManagerMessageDao.java 11714 2011-05-18 05:49:19Z limjongsang $
 */
public interface PortletManagerMessageDao extends GenericDao<PortletManagerMessage, String> {
	
	/**
	 * ManagerMessage 포틀릿 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletManagerMessage 포틀릿 설정 정보 
	 */
	public PortletManagerMessage getPortletManagerMessage();
	
	/**
	 * ManagerMessage 포틀릿 설정 등록
	 * @param portletManagerMessage Portlet ManagerMessage Model
	 */
	public void createPortletManagerMessage(PortletManagerMessage portletManagerMessage);
	
	/**
	 * ManagerMessage 포틀릿 설정 수정
	 * @param portletManagerMessage Portlet ManagerMessage Model
	 */
	public void updatePortletManagerMessage(PortletManagerMessage portletManagerMessage);
}