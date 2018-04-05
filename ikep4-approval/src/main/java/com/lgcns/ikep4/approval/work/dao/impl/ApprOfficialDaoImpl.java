package com.lgcns.ikep4.approval.work.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.work.dao.ApprOfficialDao;
import com.lgcns.ikep4.approval.work.model.ApprOfficial;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 공문 시행 DAO
 * 
 * @author 유승목
 * @version $Id$
 */
@Repository
public class ApprOfficialDaoImpl extends GenericDaoSqlmap<ApprOfficial, String> implements ApprOfficialDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.work.dao.ApprOfficial.";

	public ApprOfficial get(String officialId) {
		return (ApprOfficial) sqlSelectForObject(NAMESPACE + "get", officialId);
	}

	public boolean exists(String signId) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(ApprOfficial apprOfficial) {
		return (String) sqlInsert(NAMESPACE + "create", apprOfficial);
	}

	public void update(ApprOfficial apprOfficial) {
		sqlUpdate(NAMESPACE + "update", apprOfficial);
	}

	public void remove(String officialId) {
		sqlUpdate(NAMESPACE + "delete", officialId);
	}

	public List<ApprOfficial> listBySearchCondition(ApprListSearchCondition searchCondition) {
		return sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	public Integer countBySearchCondition(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		return count;
	}

}
