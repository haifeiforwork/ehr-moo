package com.lgcns.ikep4.portal.portlet.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.PortletCampaignMessages;

/**
 * 
 * CampaignMessages 포틀릿 DAO
 *
 * @author 최재영
 * @version $Id: PortletCampaignMessagesDao.java 11714 2011-05-18 05:49:19Z limjongsang $
 */
public interface PortletCampaignMessagesDao extends GenericDao<PortletCampaignMessages, String> {
	
	/**
	 * ManagerMessage 포틀릿 설정 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortletCampaignMessages 포틀릿 설정 정보 
	 */
	public PortletCampaignMessages getPortletCampaignMessages();
	
	public PortletCampaignMessages getPortletManagementPolicy();
	
	/**
	 * ManagerMessage 포틀릿 설정 등록
	 * @param portletCampaignMessages Portlet ManagerMessage Model
	 */
	public void createPortletCampaignMessages(PortletCampaignMessages portletCampaignMessages);
	
	public void createPortletManagementPolicy(PortletCampaignMessages portletManagementPolicy);
	
	/**
	 * ManagerMessage 포틀릿 설정 수정
	 * @param portletCampaignMessages Portlet ManagerMessage Model
	 */
	public void updatePortletCampaignMessages(PortletCampaignMessages portletCampaignMessages);
	
	public void updatePortletManagementPolicy(PortletCampaignMessages portletManagementPolicy);
}