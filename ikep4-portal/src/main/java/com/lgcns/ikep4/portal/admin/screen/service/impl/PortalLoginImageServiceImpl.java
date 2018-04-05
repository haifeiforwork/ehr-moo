package com.lgcns.ikep4.portal.admin.screen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDao;
import com.lgcns.ikep4.portal.admin.screen.service.PortalLoginImageService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 로그인 이미지 Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalLoginImageServiceImpl.java 17495 2012-03-13 08:45:02Z yruyo $
 */
@Service("portalLoginImageService")
public class PortalLoginImageServiceImpl extends GenericServiceImpl<Object, String> implements PortalLoginImageService {

	@Autowired
	private FileService fileService;
	
	@Autowired
	PortalDao portalDao;
	
	/**
	 * 로그인 이미지 수정
	 * @param fileList 이미지 파일
	 * @param editorAttach 에디터 여부(1:에디터, 0:에디터아님)
	 * @param user 유저 객체
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal updateLoginImage(String fileId, User user, String portalId) {
		
		Portal portal = portalDao.getPortal(portalId);
		
		if(portal != null && !StringUtil.isEmpty(portal.getLoginImageId())) {
			fileService.removeFile(portal.getLoginImageId());
		}
		
		//파일 첨부
		if(fileId != null && !"".equals(fileId)) {
			fileService.createFileLink(fileId, portalId, IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
		}
		
		Portal updatePortal = new Portal();
		updatePortal.setPortalId(portalId);
		updatePortal.setUpdaterId(user.getUserId());
		updatePortal.setUpdaterName(user.getUserName());
		updatePortal.setLoginImageId(fileId);
		
		//포탈 로그인이미지 파일ID 업데이트
		portalDao.updatePortalLoginImageId(updatePortal);
		
		return updatePortal;
	}
	
	/**
	 * 로그인 초기화
	 * @param user 유저 객체
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal resetLoginImage(User user, String portalId) {
		Portal portal = portalDao.getPortal(portalId);
		
		if(portal != null && !StringUtil.isEmpty(portal.getLoginImageId())) {
			fileService.removeFile(portal.getLoginImageId());
		}
		
		Portal updatePortal = new Portal();
		updatePortal.setPortalId(portalId);
		updatePortal.setUpdaterId(user.getUserId());
		updatePortal.setUpdaterName(user.getUserName());
		updatePortal.setLoginImageId("");
		
		//포탈 로그인이미지 파일ID 업데이트
		portalDao.updatePortalLoginImageId(updatePortal);
		
		return updatePortal;
	}
}