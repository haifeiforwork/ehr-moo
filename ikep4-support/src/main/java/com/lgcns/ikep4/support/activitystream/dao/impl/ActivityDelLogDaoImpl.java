package com.lgcns.ikep4.support.activitystream.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.activitystream.dao.ActivityDelLogDao;
import com.lgcns.ikep4.support.activitystream.model.ActivityDelLog;


/**
 * ActivityDelLog 저장 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityDelLogDaoImpl.java 10972 2011-05-13 08:19:46Z handul32
 *          $
 */
@Repository
public class ActivityDelLogDaoImpl extends GenericDaoSqlmap<ActivityDelLog, String> implements ActivityDelLogDao {
	
	/**
	 * Activity delete log 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.activityDelLog.";

	public ActivityDelLog get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 등록
	 */
	public String create(ActivityDelLog object) {
		return (String) sqlInsert(NAMESPACE + "insert", object);
	}

	public void update(ActivityDelLog object) {
		// TODO Auto-generated method stub

	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 리스트 조회
	 */
	public List<ActivityDelLog> select(ActivityDelLog activityDelLog) {
		return (List<ActivityDelLog>) sqlSelectForList(NAMESPACE + "select", activityDelLog);
	}

	/**
	 * 삭제 배치 처리
	 */
	public int deleteBatch(ActivityDelLog activityDelLog) {
		return sqlDelete(NAMESPACE + "deleteBatch", activityDelLog);
	}

}
