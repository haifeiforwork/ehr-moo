package com.lgcns.ikep4.portal.admin.screen.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포탈 메인화면 로고 이미지 Service
 *
 * @author 임종상
 * @version $Id: PortalLogoImageService.java 17495 2012-03-13 08:45:02Z yruyo $
 */
@Transactional
public interface PortalLogoImageService extends GenericService<Object, String> {
	
	/**
	 * 로고 이미지 ID 수정
	 * @param fileId 이미지 파일 아이디
	 * @param user 유저 객체
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal updateLogoImage(String fileId, User user, String portalId);
	
	/**
	 * 로고 이미지 초기화
	 * @param user 유저 객체
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal resetLogoImage(User user, String portalId);
}