package com.lgcns.ikep4.portal.portlet.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.PortletManagerMessageDao;
import com.lgcns.ikep4.portal.portlet.model.PortletManagerMessage;

/**
 * 
 * ManagerMessage 포틀릿 DAO 구현 클래스
 *
 * @author 박순성
 * @version $Id$
 */
@Repository("portletManagerMessageDao")
public class PortletManagerMessageDaoImpl extends GenericDaoSqlmap<PortletManagerMessage, String> implements PortletManagerMessageDao {

	/**
	 * ManagerMessage 포틀릿 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletManagerMessage 포틀릿 설정 정보 
	 */
	public PortletManagerMessage getPortletManagerMessage() {
		return (PortletManagerMessage) sqlSelectForObject("portal.portlet.portletManagerMessage.getPortletManagerMessage");
	}
	
	/**
	 * ManagerMessage 포틀릿 설정 등록
	 * @param portletManagerMessage Portlet ManagerMessage Model
	 */
	public void createPortletManagerMessage(PortletManagerMessage portletManagerMessage) {
		sqlInsert("portal.portlet.portletManagerMessage.createPortletManagerMessage", portletManagerMessage);
	}
	
	/**
	 * ManagerMessage 포틀릿 설정 수정
	 * @param portletManagerMessage Portlet ManagerMessage Model
	 */
	public void updatePortletManagerMessage(PortletManagerMessage portletManagerMessage) {
		sqlUpdate("portal.portlet.portletManagerMessage.updatePortletManagerMessage", portletManagerMessage);
	}
	
	@Deprecated
	public PortletManagerMessage get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortletManagerMessage object) {
		return null;
	}

	@Deprecated
	public void update(PortletManagerMessage object) {}

	@Deprecated
	public void remove(String id) {}

}
