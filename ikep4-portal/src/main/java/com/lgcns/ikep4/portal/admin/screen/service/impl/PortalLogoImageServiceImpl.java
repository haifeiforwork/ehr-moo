package com.lgcns.ikep4.portal.admin.screen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDao;
import com.lgcns.ikep4.portal.admin.screen.service.PortalLogoImageService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 포탈 메인 로고 이미지 Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalLogoImageServiceImpl.java 17495 2012-03-13 08:45:02Z yruyo $
 */
@Service("portalLogoImageService")
public class PortalLogoImageServiceImpl extends GenericServiceImpl<Object, String> implements PortalLogoImageService {

	@Autowired
	private FileService fileService;
	
	@Autowired
	PortalDao portalDao;
	
	/**
	 * 로고 이미지 ID 수정
	 * @param fileId 이미지 파일 아이디
	 * @param user 유저 객체
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal updateLogoImage(String fileId, User user, String portalId) {
		
		Portal portal = portalDao.getPortal(portalId);
		
		if(portal != null && !StringUtil.isEmpty(portal.getLogoImageId())) {
			fileService.removeFile(portal.getLogoImageId());
		}
		
		//파일 첨부
		if(fileId != null && !"".equals(fileId)) {
			fileService.createFileLink(fileId, portalId, IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
		}
		
		Portal updatePortal = new Portal();
		updatePortal.setPortalId(portalId);
		updatePortal.setUpdaterId(user.getUserId());
		updatePortal.setUpdaterName(user.getUserName());
		updatePortal.setLogoImageId(fileId);
		
		//포탈 로고이미지 파일ID 업데이트
		portalDao.updatePortalLogoImageId(updatePortal);
		
		return updatePortal;
	}
	
	/**
	 * 로고 이미지 초기화
	 * @param user 유저 객체
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal resetLogoImage(User user, String portalId) {
		Portal portal = portalDao.getPortal(portalId);
		
		if(portal != null && !StringUtil.isEmpty(portal.getLogoImageId())) {
			fileService.removeFile(portal.getLogoImageId());
		}
		
		Portal updatePortal = new Portal();
		updatePortal.setPortalId(portalId);
		updatePortal.setUpdaterId(user.getUserId());
		updatePortal.setUpdaterName(user.getUserName());
		updatePortal.setLogoImageId("");
		
		//포탈 로고이미지 파일ID 업데이트
		portalDao.updatePortalLogoImageId(updatePortal);
		
		return updatePortal;
	}
}