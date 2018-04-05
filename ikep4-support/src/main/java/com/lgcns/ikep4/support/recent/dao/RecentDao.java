/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.recent.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.recent.model.Recent;
import com.lgcns.ikep4.support.recent.model.RecentSearchCondition;


/**
 * Recent DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: RecentDao.java 17322 2012-02-13 09:50:12Z yu_hs $
 */
public interface RecentDao extends GenericDao<Recent, String> {

	/**
	 * 도큐먼트 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> listBySearchConditionForDocument(RecentSearchCondition searchCondition);

	/**
	 * 피플 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> listBySearchConditionForPeople(RecentSearchCondition searchCondition);

	/**
	 * 마이 Collaboration 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectCollaboration(Map<String, String> map);
	
	/**
	 * 마이 Cafe 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectCafe(Map<String, String> map);
	
	/**
	 * 마이 MicroBlog 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectMicroblog(Map<String, String> map);
	
	/**
	 * 마이 Follower 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectFollower(RecentSearchCondition searchCondition);
	
	/**
	 * 마이 Following 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectFollowing(RecentSearchCondition searchCondition);
	
	/**
	 * 마이 Intimate 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectIntimate(RecentSearchCondition searchCondition);
	
	/**
	 * 마이 Collaboration Member 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectCollaborationMember(RecentSearchCondition searchCondition);
	
	

}
