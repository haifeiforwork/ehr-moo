package com.lgcns.ikep4.support.user.userTheme.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;

/**
 * 
 * 사용자 테마 service
 *
 * @author 임종상
 * @version $Id: UserThemeService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface UserThemeService extends GenericService<UserTheme, String> {
	
	//사용자 테마 조회
	public UserTheme readUserTheme(String userId);

	//사용자 테마 등록
	public void createUserTheme(UserTheme userTheme);
	
	//사용자 테마 수정
	public void updateUserTheme(UserTheme userTheme);
	
	//테마 정보 조회
	public UserTheme readTheme(String themeId, String portalId);
		
	//기본 테마 정보 조회
	public UserTheme readDefaultTheme(String portalId);
}
