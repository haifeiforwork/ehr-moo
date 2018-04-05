/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.microblogging.dao.FollowDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Follow;
import com.lgcns.ikep4.socialpack.microblogging.service.FollowService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;


/**
 * 
 * FollowService 구현클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: FollowServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service
public class FollowServiceImpl extends GenericServiceImpl<Follow, Follow> implements FollowService {

	@Autowired
	private FollowDao followDao;

	@Autowired
	private UserService userService;
	@Autowired
	private ActivityStreamService activityStreamService;
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists(java.io.Serializable)
	 */
	public boolean exists(Follow object) {
		return followDao.exists(object);
	}
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang
	 * .Object)
	 */
	public Follow create(Follow object) {

		// Activity Stream 등록을 위한 조회
		User user = userService.read(object.getUserId());
		
		// Activity Stream 등록
		activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_MICROBLOG, IKepConstant.ACTIVITY_CODE_FOLLOW, object.getUserId(), user.getUserName(), object.getFollowingUserId(), 1);
		
		return followDao.create(object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.
	 * Serializable)
	 */
	public void delete(Follow object) {
		// Activity Stream 등록을 위한 조회
		User user = userService.read(object.getUserId());
		
		// Activity Stream 등록
		activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_MICROBLOG, IKepConstant.ACTIVITY_CODE_UNFOLLOW, object.getUserId(), user.getUserName(), object.getFollowingUserId(), 1);
		
		followDao.remove(object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	public void update(Follow object) {
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.FollowService#getFollower(com.lgcns.ikep4.socialpack.microblogging.model.Follow)
	 */
	public List<Follow> getFollower(Follow follow) {
		return followDao.selectFollower(follow);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.FollowService#getFollowing(com.lgcns.ikep4.socialpack.microblogging.model.Follow)
	 */
	public List<Follow> getFollowing(Follow follow) {
		return followDao.selectFollowing(follow);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.FollowService#getBothFollowing(java.util.Map)
	 */
	public List<Follow> getBothFollowing(Map map) {
		return followDao.selectBothFollowing(map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.FollowService#getBothFollowed(java.util.Map)
	 */
	public List<Follow> getBothFollower(Map map) {
		return followDao.selectBothFollower(map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.FollowService#myFollowingCount(java.lang.String)
	 */
	public int myFollowingCount(String id) {
		return followDao.selectMyFollowingCount(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.FollowService#myFollowerCount(java.lang.String)
	 */
	public int myFollowerCount(String id) {
		return followDao.selectMyFollowerCount(id);
	}

}
