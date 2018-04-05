package com.lgcns.ikep4.support.user.member.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.UserDetail;


@Transactional
public interface UserProfileImageService extends GenericService<UserDetail, String>{
	
	/**
	 * 사용자 정보를 받아 SAP 이미지 패스에서 이미지를 다운 받아 저장한다 
	 * @param userList
	 * @return
	 */
	public void userProfileImagesSync(List<UserDetail> userList);
	
    public List<User> downloadUserProfileImages(List<UserDetail> userList);

}
