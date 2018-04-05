package com.lgcns.ikep4.approval.work.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.work.dao.ApprSignDao;
import com.lgcns.ikep4.approval.work.model.ApprSign;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Sing 이미지 관리 서비스
 * 
 * @author 유승목
 * @version $Id$
 */
@Repository
public class ApprSignDaoImpl extends GenericDaoSqlmap<ApprSign, String> implements ApprSignDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.work.dao.ApprSign.";

	public ApprSign get(String signId) {
		return (ApprSign) sqlSelectForObject(NAMESPACE + "get", signId);
	}

	public boolean exists(String signId) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(ApprSign apprSign) {
		return (String) sqlInsert(NAMESPACE + "create", apprSign);
	}

	public void update(ApprSign apprSign) {
		// TODO Auto-generated method stub
	}

	public void remove(String signId) {
		sqlDelete(NAMESPACE + "delete", signId);
	}

	public List<ApprSign> select(ApprSign apprSign) {
		return (List<ApprSign>) sqlSelectForList(NAMESPACE + "select", apprSign);
	}

	public void setDefault(ApprSign apprSign) {
		sqlUpdate(NAMESPACE + "setDefault", apprSign);
	}

	public void initDefault(ApprSign apprSign) {
		sqlUpdate(NAMESPACE + "initDefault", apprSign);
	}

	public void changeApprPassword(User user) {
		sqlUpdate(NAMESPACE + "changeApprPassword", user);
	}

	public List<ApprSign> listBySearchCondition(ApprListSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}

	public Integer countBySearchCondition(ApprListSearchCondition searchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		return count;
	}
}
