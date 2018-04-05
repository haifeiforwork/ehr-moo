package com.lgcns.ikep4.support.recent.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.recent.dao.RecentDao;
import com.lgcns.ikep4.support.recent.model.Recent;
import com.lgcns.ikep4.support.recent.model.RecentSearchCondition;


/**
 * Recent 저장 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: RecentDaoImpl.java 17752 2012-03-29 04:48:03Z yu_hs $
 */
@Repository
public class RecentDaoImpl extends GenericDaoSqlmap<Recent, String> implements RecentDao {
	
	/**
	 * Recent 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.recent.";

	public String create(Recent recent) {
		// TODO Auto-generated method stub
		return "";
	}

	public Recent get(String recentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(Recent object) {
		// TODO Auto-generated method stub

	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 도큐먼드 리스트 조회
	 */
	public List<Recent> listBySearchConditionForDocument(RecentSearchCondition searchCondition) {
		return (List<Recent>) sqlSelectForList(NAMESPACE + "listBySearchConditionForDocument", searchCondition);
	}
	
	/**
	 * 피플 리스트 조회
	 */
	public List<Recent> listBySearchConditionForPeople(RecentSearchCondition searchCondition) {
		return (List<Recent>) sqlSelectForList(NAMESPACE + "listBySearchConditionForPeople", searchCondition);
	}
	
	/**
	 * 마이 워크스페이스 검색
	 */
	public List<Recent> selectCollaboration(Map<String, String> map) {
		return (List<Recent>) sqlSelectForList(NAMESPACE + "selectCollaboration", map);
	}
	
	/**
	 * 마이 카페 검색
	 */
	public List<Recent> selectCafe(Map<String, String> map) {
		return (List<Recent>) sqlSelectForList(NAMESPACE + "selectCafe", map);
	}
	
	/**
	 * 마이 블로그 검색
	 */
	public List<Recent> selectMicroblog(Map<String, String> map) {
		return (List<Recent>) sqlSelectForList(NAMESPACE + "selectMicroblog", map);
	}
	
	/**
	 * 마이 Followr 검색
	 */
	public List<Recent> selectFollower(RecentSearchCondition searchCondition) {
		return (List<Recent>) sqlSelectForList(NAMESPACE + "selectFollower", searchCondition);
	}
	
	/**
	 * 마이 Following 검색
	 */
	public List<Recent> selectFollowing(RecentSearchCondition searchCondition) {
		return (List<Recent>) sqlSelectForList(NAMESPACE + "selectFollowing", searchCondition);
	}
	
	/**
	 * 마이 친밀도 검색
	 */
	public List<Recent> selectIntimate(RecentSearchCondition searchCondition) {
		return (List<Recent>) sqlSelectForList(NAMESPACE + "selectIntimate", searchCondition);
	}
	
	/**
	 * 마이 워크스페이스 멤버 검색
	 */
	public List<Recent> selectCollaborationMember(RecentSearchCondition searchCondition) {
		return (List<Recent>) sqlSelectForList(NAMESPACE + "selectCollaborationMember", searchCondition);
	}

}
