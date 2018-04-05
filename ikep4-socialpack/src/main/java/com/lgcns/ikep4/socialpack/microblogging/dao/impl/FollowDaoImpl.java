/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.microblogging.dao.FollowDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Follow;


/**
 * 
 * FollowDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: FollowDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class FollowDaoImpl extends GenericDaoSqlmap<Follow, Follow> implements FollowDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.follow.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.FollowDao#selectFollower(com.lgcns.ikep4.socialpack.microblogging.model.Follow)
	 */
	public List<Follow> selectFollower(Follow follow) {
		if (log.isDebugEnabled()) {
			log.debug("follow:"+follow);
		}
		
		return sqlSelectForList(NAMESPACE + "selectFollower" ,follow);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.FollowDao#selectFollowing(com.lgcns.ikep4.socialpack.microblogging.model.Follow)
	 */
	public List<Follow> selectFollowing(Follow follow) {
		return sqlSelectForList(NAMESPACE + "selectFollowing" ,follow);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public Follow create(Follow follow) {
		return (Follow) sqlInsert(NAMESPACE + "insert", follow);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(Follow follow) {
		sqlDelete(NAMESPACE + "delete", follow);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(Follow follow) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectCount", follow);
		if (count > 0){
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Follow get(Follow follow) {
		return null ;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Follow follow) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.FollowDao#selectBothFollowing(java.util.Map)
	 */
	public List<Follow> selectBothFollowing(Map map) {
		return sqlSelectForList(NAMESPACE + "selectBothFollowing" ,map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.FollowDao#selectBothFollowed(java.util.Map)
	 */
	public List<Follow> selectBothFollower(Map map) {
		return sqlSelectForList(NAMESPACE + "selectBothFollower" ,map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.FollowDao#selectMyFollowingCount(java.lang.String)
	 */
	public int selectMyFollowingCount(String id) {
		return (Integer) sqlSelectForObject(NAMESPACE + "selectMyFollowingCount", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.FollowDao#selectMyFollowerCount(java.lang.String)
	 */
	public int selectMyFollowerCount(String id) {
		return (Integer) sqlSelectForObject(NAMESPACE + "selectMyFollowerCount", id);
	}

}
