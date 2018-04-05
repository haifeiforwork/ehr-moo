/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.profile.service.ProfileFileUploadService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * TODO Javadoc주석작성
 *
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: ProfileFileUploadServiceImpl.java 16334 2011-08-22 01:04:48Z giljae $
 */
@Service("profileFileUploadService")
public class ProfileFileUploadServiceImpl extends GenericServiceImpl<User, String> implements ProfileFileUploadService {

	/**
	 * 파일 컨트롤 Service
	 */
	@Autowired
	FileService fileService;
	
	/**
	 * 프로파일 user 정보 컨트롤 용 Service
	 */
	@Autowired
	UserService userService;

	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.Serializable)
	 */
	@Deprecated
	public User read(String id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	@Deprecated
	public String create(FileData object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	@Deprecated
	public void update(FileData object) {

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileFileUploadService#savePictureId
	 * (java.io.Serializable,java.io.Serializable,java.io.Serializable,com.lgcns.ikep4.support.user.member.model.User)
	 */
	public String savePictureId(String fileId, String userId, String userName, String type, User sessionuser ) {
		
		// 이전 업로드 데이타 삭제
		User beforeUser = userService.read(userId);
		if( !(StringUtil.isEmpty(beforeUser.getPictureId())) ){
			fileService.removeFile(beforeUser.getPictureId());
		}
		
		List<String> fileIdList = new ArrayList<String>();
		fileIdList.add( fileId );
		fileService.createFileLink(fileIdList, userId, type, sessionuser); 
		
		User user = new User();
		user.setPictureId(fileId);
		user.setUserId(userId);
		user.setUpdaterId(userId);
		user.setUpdaterName(userName);
		
		userService.updatePictureId(user);
		return user.getPictureId();
		
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.profile.service.ProfileFileUploadService#saveProfilePictureId(com.lgcns.ikep4.support.fileupload.model.FileData)
	 */
	public String saveProfilePictureId(String fileId, String userId, String userName, String type, User sessionuser) {
		
		// 이전 업로드 데이타 삭제
		User beforeUser = userService.read(userId);
		if( !(StringUtil.isEmpty(beforeUser.getProfilePictureId())) ){
			fileService.removeFile(beforeUser.getProfilePictureId());
		}
		
		List<String> fileIdList = new ArrayList<String>();
		fileIdList.add( fileId );
		fileService.createFileLink(fileIdList, userId, type, sessionuser);
		
		User user = new User();
		user.setProfilePictureId(fileId);
		user.setUserId(userId);
		user.setUpdaterId(userId);
		user.setUpdaterName(userName);
		
		userService.updateProfilePictureId(user);
		return user.getProfilePictureId();
	}

}
