/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.microblogging.model.Follow;

/**
 * 
 * Follow 관련 서비스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: FollowService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface FollowService extends GenericService<Follow, Follow> {

	/**
	 * 사용자의 모든 Follower 반환
	 * 
	 * @param userId를 넣은 Follow 객체
	 * @return Follow객체 List
	 */
	public List<Follow> getFollower			(Follow follow);

	/**
	 * 사용자의 모든 Following 반환
	 * 
	 * @param userId를 넣은 Follow 객체
	 * @return Follow객체 List
	 */
	public List<Follow> getFollowing		(Follow follow);

	/**
	 * 두 유저가 동시에 Following 하는 유저 목록
	 * 
	 * @param userId, ownerId를 넣은 Follow 객체
	 * @return Follow객체 List
	 */
	public List<Follow> getBothFollowing	(Map map);

	/**
	 * 두 유저를 동시에 Following 하는유저 목록
	 * 
	 * @param userId, ownerId를 넣은 Follow 객체
	 * @return Follow객체 List
	 */
	public List<Follow> getBothFollower		(Map map);

	/**
	 * 선택한 사용자의 following 수 반환
	 * 
	 * @param userId
	 * @return 선택한 사용자의 following 수
	 */
	public int			myFollowingCount	(String id);

	/**
	 * 선택한 사용자의 follower 수 반환
	 * 
	 * @param userId
	 * @return 선택한 사용자의 follower 수
	 */
	public int			myFollowerCount		(String id);
}
