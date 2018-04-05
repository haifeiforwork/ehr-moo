package com.lgcns.ikep4.support.activitystream.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.activitystream.dao.ActivityStreamDao;
import com.lgcns.ikep4.support.activitystream.model.ActivityStream;
import com.lgcns.ikep4.support.activitystream.model.ActivityStreamSearchCondition;


/**
 * ActivityStream 저장 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityStreamDaoImpl.java 10241 2011-05-09 10:40:32Z
 *          happyi1018 $
 */
@Repository
public class ActivityStreamDaoImpl extends GenericDaoSqlmap<ActivityStream, String> implements ActivityStreamDao {

	/**
	 * Activity Stream 쿼리 네임 스페이스
	 */
	private static final String NAMESPACE = "support.activityStream.";
	
	/**
	 * 등록
	 */
	public String create(ActivityStream activityStream) {
		return (String) sqlInsert(NAMESPACE + "insert", activityStream);
	}
	
	/**
	 * 조회
	 */
	public ActivityStream get(String activityStreamId) {
		return (ActivityStream) sqlSelectForObject(NAMESPACE + "select", activityStreamId);
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(ActivityStream object) {
		// TODO Auto-generated method stub

	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 리스트 조회
	 */
	public List<ActivityStream> listBySearchCondition(ActivityStreamSearchCondition searchCondition) {
		return (List<ActivityStream>) sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}
	
	/**
	 * 리스트 갯수 조회
	 */
	public Integer countBySearchCondition(ActivityStreamSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}
	
	/**
	 * 리스트 조회 (Workspace 용)
	 */
	public List<ActivityStream> listBySearchConditionWorkspace(ActivityStreamSearchCondition searchCondition) {
		return (List<ActivityStream>) sqlSelectForList(NAMESPACE + "listBySearchConditionWorkspace", searchCondition);
	}
}
