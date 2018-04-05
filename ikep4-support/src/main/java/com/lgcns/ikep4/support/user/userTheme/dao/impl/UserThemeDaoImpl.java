package com.lgcns.ikep4.support.user.userTheme.dao.impl;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.userTheme.dao.UserThemeDao;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;

/**
 * 
 * 사용자 테마 Dao 구현 클래스
 *
 * @author 임종상
 * @version $Id: UserThemeDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository("userThemeDao")
public class UserThemeDaoImpl extends GenericDaoSqlmap<UserTheme, String> implements UserThemeDao {
	
	//사용자 테마 조회
	public UserTheme getUserTheme(String userId) {
		return (UserTheme) sqlSelectForObject("support.user.userTheme.userTheme.getUserTheme", userId);
	}

	//사용자 테마 수정
	public void updateUserTheme(UserTheme userTheme) {
		sqlUpdate("support.user.userTheme.userTheme.updateUserTheme", userTheme);
	}

	//사용자 테마 등록
	public void createUserTheme(UserTheme userTheme) {
		sqlInsert("support.user.userTheme.userTheme.createUserTheme", userTheme);
	}
	
	//테마 조회
	public UserTheme getTheme(String themeId, String portalId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("themeId", themeId);
		map.put("portalId", portalId);
		
		return (UserTheme) sqlSelectForObject("support.user.userTheme.userTheme.getTheme", map);
	}

	//기본 테마 조회
	public UserTheme getDefaultTheme(String portalId) {
		return (UserTheme) sqlSelectForObject("support.user.userTheme.userTheme.getDefaultTheme", portalId);
	}

	@Deprecated
	public UserTheme get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(UserTheme object) {
		return null;
	}

	@Deprecated
	public void update(UserTheme object) {}

	@Deprecated
	public void remove(String id) {}
}