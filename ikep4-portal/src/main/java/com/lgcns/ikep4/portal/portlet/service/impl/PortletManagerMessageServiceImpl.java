package com.lgcns.ikep4.portal.portlet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.PortletManagerMessageDao;
import com.lgcns.ikep4.portal.portlet.model.PortletManagerMessage;
import com.lgcns.ikep4.portal.portlet.service.PortletManagerMessageService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 포틀릿 ManagerMessage service 구현 클래스
 *
 * @author 박순성
 * @version $Id: PortletManagerMessageServiceImpl.java 11714 2011-05-18 05:49:19Z limjongsang $
 */
@Service("portletManagerMessageService")
public class PortletManagerMessageServiceImpl extends GenericServiceImpl<PortletManagerMessage, String> implements PortletManagerMessageService {

	@Autowired
	private PortletManagerMessageDao portletManagerMessageDao;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * ManagerMessage 등록
	 * @param portletManagerMessage Portlet ManagerMessage Model
	 * @param fileList 이미지 파일
	 * @param editorAttach 에디터 여부(1:에디터, 0:에디터 아님)
	 * @param user 유저 객체
	 */
	public void createPortletManagerMessage(PortletManagerMessage portletManagerMessage, List<MultipartFile> fileList, String editorAttach, User user) {
		
		//기존 설정 정보 조회
		PortletManagerMessage oldPortletManagerMessage = portletManagerMessageDao.getPortletManagerMessage();
		
		if(oldPortletManagerMessage == null) {
			String dreamTogetherId = idgenService.getNextId();
			portletManagerMessage.setPortletConfigId(dreamTogetherId);
		} else {
			portletManagerMessage.setPortletConfigId(oldPortletManagerMessage.getPortletConfigId());
		}
		
		if(oldPortletManagerMessage != null && !StringUtil.isEmpty(oldPortletManagerMessage.getImageFileId())) {
			fileService.removeFile(oldPortletManagerMessage.getImageFileId());
		}
		
		if(oldPortletManagerMessage != null && !StringUtil.isEmpty(oldPortletManagerMessage.getTitleFileId())) {
			fileService.removeFile(oldPortletManagerMessage.getTitleFileId());
		}
		
		String fileId1 = "";
		String fileId2 = "";
		
		//파일 첨부
		if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttach, user);
			
			fileId1 = uploadList.get(0).getFileId();
			fileService.createFileLink(fileId1, portletManagerMessage.getPortletConfigId(), IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
			if(!fileList.get(1).isEmpty()){
				fileId2 = uploadList.get(1).getFileId();
				fileService.createFileLink(fileId2, portletManagerMessage.getPortletConfigId(), IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
			}
		}
		
		portletManagerMessage.setImageFileId(fileId1);
		portletManagerMessage.setTitleFileId(fileId2);
		portletManagerMessage.setRegisterId(user.getUserId());
		portletManagerMessage.setRegisterName(user.getUserName());
		portletManagerMessage.setUpdaterId(user.getUserId());
		portletManagerMessage.setUpdaterName(user.getUserName());
		
		if(oldPortletManagerMessage == null) {
			//없으면 인서트
			portletManagerMessageDao.createPortletManagerMessage(portletManagerMessage);
		} else {
			//있으면 업데이트
			portletManagerMessageDao.updatePortletManagerMessage(portletManagerMessage);
		}
	}

	/**
	 * ManagerMessage 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @param user 유저 객체
	 * @return PortletManagerMessage ManagerMessage 정보
	 */
	public PortletManagerMessage readPortletManagerMessage() {
		return portletManagerMessageDao.getPortletManagerMessage();
	}
}