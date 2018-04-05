/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Mblog;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * mblogDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MblogDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class MblogDaoImpl extends GenericDaoSqlmap<Mblog, String> implements MblogDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.mblog.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectTimelineList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectTimelineList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectTimelineList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectTimelineListUserOnly(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectTimelineListUserOnly(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectTimelineListUserOnly", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectThreadList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectThreadList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectThreadList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectGroupTimelineList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectGroupTimelineList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectGroupTimelineList", mblogSearchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectGroupThreadList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectGroupThreadList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectGroupThreadList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Mblog mblog) {
		return (String) sqlInsert(NAMESPACE + "insert", mblog);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String id) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectCount", id);
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
	public Mblog get(String id) {
		return (Mblog) sqlSelectForObject(NAMESPACE + "select", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE + "delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Mblog mblog) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectTimelineAllList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectTimelineAllList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectTimelineAllList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectThreadAllList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectThreadAllList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectThreadAllList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectAddonListByAddonType(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectAddonListByAddonType(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectAddonListByAddonType", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectGroupAddonListByAddonType(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectGroupAddonListByAddonType(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectGroupAddonListByAddonType", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectRetwitByMeList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectRetwitByMeList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectRetwitByMeList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectRetwitByOtherList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectRetwitByOtherList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectRetwitByOtherList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectMyMentionList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectMyMentionList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectMyMentionList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectRetwitFromOutsideList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectRetwitFromOutsideList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectRetwitFromOutsideList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectRetwitToOutsideList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectRetwitToOutsideList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectRetwitToOutsideList", mblogSearchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectFavoriteList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectFavoriteList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectFavoriteList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectTweetListByTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectTweetListByTag(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectTweetListByTag", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectUserListByHashTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectUserListByHashTag(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectUserListByHashTag", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectUserListByMentionTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectUserListByMentionTag(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectUserListByMentionTag", mblogSearchCondition);
	}


	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectTweetListByGroupTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectTweetListByGroupTag(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectTweetListByGroupTag", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectUserListByGroupHashTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectUserListByGroupHashTag(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectUserListByGroupHashTag", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectUserListByGroupMentionTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectUserListByGroupMentionTag(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectUserListByGroupMentionTag", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectParentNReplyListByMblogId(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectParentNReplyListByMblogId(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectParentNReplyListByMblogId", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectRetweetUserListByMblogId(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectRetweetUserListByMblogId(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectRetweetUserListByMblogId", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectRetweetStatisticsByMblogId(java.lang.String)
	 */
	public List selectRetweetStatisticsByMblogId(String id) {
		return sqlSelectForListOfObject(NAMESPACE + "selectRetweetStatisticsByMblogId", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectMentionedUserListByMblogId(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectMentionedUserListByMblogId(MblogSearchCondition twitSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectMentionedUserListByMblogId", twitSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectUserInfoByUserId(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public User selectUserInfoByUserId(MblogSearchCondition userSearchCondition) {
		return (User) sqlSelectForObject(NAMESPACE + "selectUserInfoByUserId", userSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectMyTweetCount(java.lang.String)
	 */
	public int selectMyTweetCount(String id) {
		return (Integer) sqlSelectForObject(NAMESPACE + "selectMyTweetCount", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectFollowingList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectFollowingList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectFollowingList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectFollowerList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectFollowerList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectFollowerList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectGroupTweetCount(java.lang.String)
	 */
	public int selectGroupTweetCount(String id) {
		return (Integer) sqlSelectForObject(NAMESPACE + "selectGroupTweetCount", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectUserIdForAutoComplete(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectUserIdForAutoComplete(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectUserIdForAutoComplete", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectRecentFileListByAddonType(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectRecentFileListByAddonType(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectRecentFileListByAddonType", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao#selectWorkspaceTimelineList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> selectWorkspaceTimelineList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectWorkspaceTimelineList", mblogSearchCondition);
	}

	public List<Mblog> selectTeamTimelineList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectTeamTimelineList", mblogSearchCondition);
	}

	public List<Mblog> selectFollowingTimelineList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForList(NAMESPACE + "selectFollowingTimelineList", mblogSearchCondition);
	}

}
