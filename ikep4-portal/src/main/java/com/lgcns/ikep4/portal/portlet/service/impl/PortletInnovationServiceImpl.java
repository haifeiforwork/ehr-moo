package com.lgcns.ikep4.portal.portlet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.PortletInnovationDao;
import com.lgcns.ikep4.portal.portlet.model.PortletInnovation;
import com.lgcns.ikep4.portal.portlet.service.PortletInnovationService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 포틀릿 Innovation service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortletInnovationServiceImpl.java 17849 2012-04-03 07:56:14Z unshj $
 */
@Service("portletInnovationService")
public class PortletInnovationServiceImpl extends GenericServiceImpl<PortletInnovation, String> implements PortletInnovationService {

	@Autowired
	private PortletInnovationDao portletInnovationDao;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * Innovation 등록
	 * @param portletInnovation Portlet Innovation Model
	 * @param fileList 이미지 파일
	 * @param editorAttach 에디터 여부(1:에디터, 0:에디터 아님)
	 * @param user 유저 객체
	 */
	public void createPortletInnovation(PortletInnovation portletInnovation, String editorAttach, User user) {
		
		//기존 설정 정보 조회
		PortletInnovation oldPortletInnovation = portletInnovationDao.getPortletInnovation(portletInnovation.getPortletConfigId());
		
		if(oldPortletInnovation != null && !StringUtil.isEmpty(oldPortletInnovation.getImageFileId())) {
			fileService.removeFile(oldPortletInnovation.getImageFileId());
		}
		
		fileService.createFileLink(portletInnovation.getImageFileId(), portletInnovation.getPortletConfigId(), IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
	
		portletInnovation.setRegisterId(user.getUserId());
		portletInnovation.setRegisterName(user.getUserName());
		portletInnovation.setUpdaterId(user.getUserId());
		portletInnovation.setUpdaterName(user.getUserName());
		
		if(oldPortletInnovation == null) {
			//없으면 인서트
			portletInnovationDao.createPortletInnovation(portletInnovation);
		} else {
			//있으면 업데이트
			portletInnovationDao.updatePortletInnovation(portletInnovation);
		}
	}

	/**
	 * Innovation 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @param user 유저 객체
	 * @return PortletInnovation Innovation 정보
	 */
	public PortletInnovation readPortletInnovation(String portletConfigId) {
		
		PortletInnovation portletInnovation = portletInnovationDao.getPortletInnovation(portletConfigId);
		
		if(portletInnovation != null && !StringUtil.isEmpty(portletInnovation.getImageFileId())) {
			FileData fileData = new FileData();
			fileData = fileService.read(portletInnovation.getImageFileId());
			
			portletInnovation.setFileData(fileData);
		}
		
		return portletInnovation;
	}
}