/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Follow;


/**
 * 
 * Follow 관련 처리 DAO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: FollowDao.java 16246 2011-08-18 04:48:28Z giljae $
 */

public interface FollowDao extends GenericDao<Follow, Follow> {
	
	/**
	 * 사용자의 모든 Follower 반환
	 * 
	 * @param userId를 넣은 Follow 객체
	 * @return Follow객체 List
	 */
	public List<Follow> selectFollower(Follow follow);

	/**
	 * 사용자의 모든 Following 반환
	 * 
	 * @param userId를 넣은 Follow 객체
	 * @return Follow객체 List
	 */
	public List<Follow> selectFollowing(Follow follow);

	/**
	 * 두 유저가 동시에 Following 하는 유저 목록
	 * 
	 * @param userId, ownerId를 넣은 Follow 객체
	 * @return Follow객체 List
	 */
	public List<Follow> selectBothFollowing(Map map);

	/**
	 * 두 유저를 동시에 Following 하는유저 목록
	 * 
	 * @param userId, ownerId를 넣은 Follow 객체
	 * @return Follow객체 List
	 */
	public List<Follow> selectBothFollower(Map map);

	/**
	 * 선택한 사용자의 following 수 반환
	 * 
	 * @param userId
	 * @return 선택한 사용자의 following 수
	 */
	public int selectMyFollowingCount(String id);

	/**
	 * 선택한 사용자의 follower 수 반환
	 * 
	 * @param userId
	 * @return 선택한 사용자의 follower 수
	 */
	public int selectMyFollowerCount(String id);

}
