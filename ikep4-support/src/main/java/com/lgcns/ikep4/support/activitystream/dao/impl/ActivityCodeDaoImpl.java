package com.lgcns.ikep4.support.activitystream.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.activitystream.dao.ActivityCodeDao;
import com.lgcns.ikep4.support.activitystream.model.ActivityCode;


/**
 * ActivityStream 저장 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityCodeDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class ActivityCodeDaoImpl extends GenericDaoSqlmap<ActivityCode, String> implements ActivityCodeDao {

	/**
	 * Activity code 쿼리 네이스페이스
	 */
	private static final String NAMESPACE = "support.activityCode.";

	/**
	 * Activity Code 등록
	 */
	public String create(ActivityCode activityCode) {
		return (String) sqlInsert(NAMESPACE + "insert", activityCode);
	}

	public ActivityCode get(String userId) {
		return null;
	}

	/**
	 * Activity Code 리스트 조회
	 */
	public List<ActivityCode> select(String userId) {
		return (List<ActivityCode>) sqlSelectForList(NAMESPACE + "select", userId);
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(ActivityCode object) {
		// TODO Auto-generated method stub

	}

	/**
	 * Activity Code 삭제
	 */
	public void remove(String userId) {
		sqlDelete(NAMESPACE + "delete", userId);
	}

	/**
	 * Activity Config 등록
	 */
	public String createConfig(ActivityCode activityCode) {
		return (String) sqlInsert(NAMESPACE + "insertConfig", activityCode);
	}

	/**
	 * Activity Config 조회
	 */
	public ActivityCode selectConfig(ActivityCode activityCode) {
		return (ActivityCode) sqlSelectForObject(NAMESPACE + "selectConfig", activityCode);
	}

	/**
	 * Activity Config 삭제
	 */
	public void removeConfig(ActivityCode activityCode) {
		sqlDelete(NAMESPACE + "deleteConfig", activityCode);
	}

	/**
	 * Activity Config 리스트 조회
	 */
	public List<ActivityCode> selectConfigList(ActivityCode activityCode) {
		return (List<ActivityCode>) sqlSelectForList(NAMESPACE + "selectConfigList", activityCode);
	}
}
