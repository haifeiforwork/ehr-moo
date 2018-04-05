package com.lgcns.ikep4.support.user.userTheme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.user.userTheme.dao.UserThemeDao;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;

/**
 * 
 * 사용자 테마 service 구현 클래스
 *
 * @author 임종상
 * @version $Id: UserThemeServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("userThemeService")
public class UserThemeServiceImpl extends GenericServiceImpl<UserTheme, String> implements UserThemeService {

	@Autowired
	private UserThemeDao userThemeDao;
	
	//사용자 테마 조회
	public UserTheme readUserTheme(String userId) {
		return userThemeDao.getUserTheme(userId);
	}

	//사용자 테마 등록
	public void createUserTheme(UserTheme userTheme) {
		userThemeDao.createUserTheme(userTheme);
	}

	//사용자 테마 수정
	public void updateUserTheme(UserTheme userTheme) {
		userThemeDao.updateUserTheme(userTheme);
	}
	
	//테마 정보 조회
	public UserTheme readTheme(String themeId, String portalId) {
		return userThemeDao.getTheme(themeId, portalId);
	}
	
	//기본 테마 정보 조회
	public UserTheme readDefaultTheme(String portalId) {
		return userThemeDao.getDefaultTheme(portalId);
	}
}
