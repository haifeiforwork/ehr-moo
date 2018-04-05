package com.lgcns.ikep4.support.user.userTheme.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;

/**
 * 
 * 사용자 테마 Dao
 *
 * @author 임종상
 * @version $Id: UserThemeDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface UserThemeDao extends GenericDao<UserTheme, String> {
	
	//유저 테마 조회
	public UserTheme getUserTheme(String userId);
	
	//유저 테마 수정
	public void updateUserTheme(UserTheme userTheme);
	
	//유저 테마 등록
	public void createUserTheme(UserTheme userTheme);
	
	//테마 정보 조회
	public UserTheme getTheme(String themeId, String portalId);
	
	//기본 테마 정보 조회
	public UserTheme getDefaultTheme(String portalId);
}
