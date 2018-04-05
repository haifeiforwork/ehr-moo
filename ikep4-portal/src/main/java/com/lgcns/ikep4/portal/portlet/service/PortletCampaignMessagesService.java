package com.lgcns.ikep4.portal.portlet.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.portlet.model.PortletCampaignMessages;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포틀릿 ManagerMessage service
 *
 * @author 박순성
 * @version $Id: PortletCampaignMessagesService.java 11714 2011-05-18 05:49:19Z $
 */
@Transactional
public interface PortletCampaignMessagesService extends GenericService<PortletCampaignMessages, String> {
	
	/**
	 * ManagerMessage 등록
	 * @param Portlet Campaign Messages Model
	 * @param fileList 이미지 파일
	 * @param editorAttach 에디터 여부(1:에디터, 0:에디터 아님)
	 * @param user 유저 객체
	 */
	public void createPortletCampaignMessages(PortletCampaignMessages portletCampaignMessages, List<MultipartFile> fileList, String editorAttach, User user);
	
	public void createPortletManagementPolicy(PortletCampaignMessages portletCampaignMessages, List<MultipartFile> fileList, String editorAttach, User user);
	
	/**
	 * ManagerMessage 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @param user 유저 객체
	 * @return PortletCampaignMessages ManagerMessage 정보
	 */
	public PortletCampaignMessages readPortletCampaignMessages();
	
	public PortletCampaignMessages readPortletManagementPolicy();
}