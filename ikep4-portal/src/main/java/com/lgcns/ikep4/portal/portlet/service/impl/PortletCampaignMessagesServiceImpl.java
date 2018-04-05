package com.lgcns.ikep4.portal.portlet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.PortletCampaignMessagesDao;
import com.lgcns.ikep4.portal.portlet.model.PortletCampaignMessages;
import com.lgcns.ikep4.portal.portlet.service.PortletCampaignMessagesService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * Portlet Campaign Messages service 구현 클래스
 *
 * @author 최재영
 * @version $Id: PortletCampaignMessagesServiceImpl.java 11714 2011-05-18 05:49:19Z limjongsang $
 */
@Service("portletCampaignMessagesService")
public class PortletCampaignMessagesServiceImpl extends GenericServiceImpl<PortletCampaignMessages, String> implements PortletCampaignMessagesService {

	@Autowired
	private PortletCampaignMessagesDao portletCampaignMessagesDao;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * CampaignMessages 등록
	 * @param portlet Campaign Messages Model
	 * @param fileList 이미지 파일
	 * @param editorAttach 에디터 여부(1:에디터, 0:에디터 아님)
	 * @param user 유저 객체
	 */
	public void createPortletCampaignMessages(PortletCampaignMessages portletCampaignMessages, List<MultipartFile> fileList, String editorAttach, User user) {
		
		//기존 설정 정보 조회
		PortletCampaignMessages oldPortletCampaignMessages = portletCampaignMessagesDao.getPortletCampaignMessages();
		
		if(oldPortletCampaignMessages == null) {
			String dreamTogetherId = idgenService.getNextId();
			portletCampaignMessages.setPortletConfigId(dreamTogetherId);
		} else {
			portletCampaignMessages.setPortletConfigId(oldPortletCampaignMessages.getPortletConfigId());
		}
		
		if(oldPortletCampaignMessages != null && !StringUtil.isEmpty(oldPortletCampaignMessages.getImageFileId())) {
			fileService.removeFile(oldPortletCampaignMessages.getImageFileId());
		}
		
		String fileId = "";
		
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttach, user);
			
			fileId = uploadList.get(0).getFileId();
			fileService.createFileLink(fileId, portletCampaignMessages.getPortletConfigId(), IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
		}
		
		portletCampaignMessages.setImageFileId(fileId);
		portletCampaignMessages.setRegisterId(user.getUserId());
		portletCampaignMessages.setRegisterName(user.getUserName());
		portletCampaignMessages.setUpdaterId(user.getUserId());
		portletCampaignMessages.setUpdaterName(user.getUserName());
		
		if(oldPortletCampaignMessages == null) {
			//없으면 인서트
			portletCampaignMessagesDao.createPortletCampaignMessages(portletCampaignMessages);
		} else {
			//있으면 업데이트
			portletCampaignMessagesDao.updatePortletCampaignMessages(portletCampaignMessages);
		}
	}
	
	
	public void createPortletManagementPolicy(PortletCampaignMessages portletManagementPolicy, List<MultipartFile> fileList, String editorAttach, User user) {
		
		//기존 설정 정보 조회
		PortletCampaignMessages oldPortletManagementPolicy = portletCampaignMessagesDao.getPortletManagementPolicy();
		
		if(oldPortletManagementPolicy == null) {
			String dreamTogetherId = idgenService.getNextId();
			portletManagementPolicy.setPortletConfigId(dreamTogetherId);
		} else {
			portletManagementPolicy.setPortletConfigId(oldPortletManagementPolicy.getPortletConfigId());
		}
		
		if(oldPortletManagementPolicy != null && !StringUtil.isEmpty(oldPortletManagementPolicy.getImageFileId())) {
			fileService.removeFile(oldPortletManagementPolicy.getImageFileId());
		}
		
		String fileId = "";
		
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttach, user);
			
			fileId = uploadList.get(0).getFileId();
			fileService.createFileLink(fileId, portletManagementPolicy.getPortletConfigId(), IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
		}
		
		portletManagementPolicy.setImageFileId(fileId);
		portletManagementPolicy.setRegisterId(user.getUserId());
		portletManagementPolicy.setRegisterName(user.getUserName());
		portletManagementPolicy.setUpdaterId(user.getUserId());
		portletManagementPolicy.setUpdaterName(user.getUserName());
		
		if(oldPortletManagementPolicy == null) {
			//없으면 인서트
			portletCampaignMessagesDao.createPortletManagementPolicy(portletManagementPolicy);
		} else {
			//있으면 업데이트
			portletCampaignMessagesDao.updatePortletManagementPolicy(portletManagementPolicy);
		}
	}

	/**
	 * ManagerMessage 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @param user 유저 객체
	 * @return PortletCampaignMessages ManagerMessage 정보
	 */
	public PortletCampaignMessages readPortletCampaignMessages() {
		return portletCampaignMessagesDao.getPortletCampaignMessages();
	}
	
	public PortletCampaignMessages readPortletManagementPolicy() {
		return portletCampaignMessagesDao.getPortletManagementPolicy();
	}
}