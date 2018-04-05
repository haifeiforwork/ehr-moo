package com.lgcns.ikep4.approval.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.admin.dao.ApprStatDao;
import com.lgcns.ikep4.approval.admin.model.ApprStat;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 통계 DAO
 * 
 * @author 유승목
 * @version $Id$
 */
@Repository
public class ApprStatDaoImpl extends GenericDaoSqlmap<ApprStat, String> implements ApprStatDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.admin.dao.ApprStat.";

	public String create(ApprStat arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprStat get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(ApprStat arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 리드타임 통계 생성
	 */
	public void generateTimeStat(ApprStat apprStat) {
		sqlInsert(NAMESPACE + "generateTimeStat", apprStat);
	}

	/**
	 * 사용자별 통계 생성
	 */
	public void generateUserStat(ApprStat apprStat) {
		sqlInsert(NAMESPACE + "generateUserStat", apprStat);
	}

	/**
	 * 양식별 통계 생성
	 */
	public void generateFormStat(ApprStat apprStat) {
		sqlInsert(NAMESPACE + "generateFormStat", apprStat);
	}

	/**
	 * 리드타임 통계 리스트
	 * 
	 * @return
	 */
	public List<ApprStat> getTimeStatList(ApprListSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "getTimeStatList", searchCondition);
	}

	/**
	 * 리드타임 통계 카운트
	 * 
	 * @return
	 */
	public Integer getTimeStatCount(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "getTimeStatCount", searchCondition);
		return count;
	}

	/**
	 * 사용자별 통계 리스트
	 * 
	 * @return
	 */
	public List<ApprStat> getUserStatList(ApprListSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "getUserStatList", searchCondition);
	}

	/**
	 * 사용자별 통계 카운트
	 * 
	 * @return
	 */
	public Integer getUserStatCount(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "getUserStatCount", searchCondition);
		return count;
	}

	/**
	 * 양식별 통계 리스트
	 * 
	 * @return
	 */
	public List<ApprStat> getFormStatList(ApprListSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "getFormStatList", searchCondition);
	}
}
