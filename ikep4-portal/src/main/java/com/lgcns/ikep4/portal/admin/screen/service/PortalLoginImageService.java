package com.lgcns.ikep4.portal.admin.screen.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 로그인 이미지 Service
 *
 * @author 임종상
 * @version $Id: PortalLoginImageService.java 17495 2012-03-13 08:45:02Z yruyo $
 */
@Transactional
public interface PortalLoginImageService extends GenericService<Object, String> {
	
	/**
	 * 로그인 이미지 수정
	 * @param fileList 이미지 파일
	 * @param editorAttach 에디터 여부(1:에디터, 0:에디터아님)
	 * @param user 유저 객체
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal updateLoginImage(String fileId, User user, String portalId);
	
	/**
	 * 로그인 초기화
	 * @param user 유저 객체
	 * @param portalId 포탈 ID
	 * @return Portal 포탈 정보
	 */
	public Portal resetLoginImage(User user, String portalId);
}