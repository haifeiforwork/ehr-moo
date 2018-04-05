/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 프로 파일 파일 업로드 컨트롤용 Service Interface
 *
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: ProfileFileUploadService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface ProfileFileUploadService extends GenericService<User, String> {
	
	/**
	 * Profile Main Picture 를 저장하고 pictureId를 반환 한다.
	 * @param user 사진 정보를 저장할 대상 User 객체
	 * @param uploadList 업로드 파일 목록 리스트
	 * @return 저장된 File ID
	 */
	public String savePictureId(String fileId, String userId, String userName, String type, User user);
	
	/**
	 * Profile Profile Picture 를 저장하고 profilePictureId를 반환 한다.
	 * @param user 사진 정보를 저장할 대상 User 객체
	 * @param uploadList 업로드 파일 목록 리스트
	 * @return 저장된 File ID
	 */
	public String saveProfilePictureId(String fileId, String userId, String userName, String type, User user);

}
