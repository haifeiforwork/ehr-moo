package com.lgcns.ikep4.portal.portlet.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.PortletCampaignMessagesDao;
import com.lgcns.ikep4.portal.portlet.model.PortletCampaignMessages;

/**
 * 
 * CampaignMessages 포틀릿 DAO 구현 클래스
 *
 * @author 박순성
 * @version $Id$
 */
@Repository("portletCampaignMessagesDao")
public class PortletCampaignMessagesDaoImpl extends GenericDaoSqlmap<PortletCampaignMessages, String> implements PortletCampaignMessagesDao {

	/**
	 * ManagerMessage 포틀릿 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletCampaignMessages 포틀릿 설정 정보 
	 */
	public PortletCampaignMessages getPortletCampaignMessages() {
		return (PortletCampaignMessages) sqlSelectForObject("portal.portlet.portletCampaignMessages.getPortletCampaignMessages");
	}
	
	public PortletCampaignMessages getPortletManagementPolicy() {
		return (PortletCampaignMessages) sqlSelectForObject("portal.portlet.portletCampaignMessages.getPortletManagementPolicy");
	}
	/**
	 * ManagerMessage 포틀릿 설정 등록
	 * @param portletCampaignMessages Portlet ManagerMessage Model
	 */
	public void createPortletCampaignMessages(PortletCampaignMessages portletCampaignMessages) {
		sqlInsert("portal.portlet.portletCampaignMessages.createPortletCampaignMessages", portletCampaignMessages);
	}
	
	public void createPortletManagementPolicy(PortletCampaignMessages portletManagementPolicy) {
		sqlInsert("portal.portlet.portletCampaignMessages.createPortletManagementPolicy", portletManagementPolicy);
	}
	
	/**
	 * ManagerMessage 포틀릿 설정 수정
	 * @param portletCampaignMessages Portlet ManagerMessage Model
	 */
	public void updatePortletCampaignMessages(PortletCampaignMessages portletCampaignMessages) {
		sqlUpdate("portal.portlet.portletCampaignMessages.updatePortletCampaignMessages", portletCampaignMessages);
	}
	
	public void updatePortletManagementPolicy(PortletCampaignMessages portletManagementPolicy) {
		sqlUpdate("portal.portlet.portletCampaignMessages.updatePortletManagementPolicy", portletManagementPolicy);
	}

	public String create(PortletCampaignMessages arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public PortletCampaignMessages get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(PortletCampaignMessages arg0) {		// TODO Auto-generated method stub
		
	}		
}
