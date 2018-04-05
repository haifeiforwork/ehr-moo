/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.qna.dao.QnaPolicyDao;
import com.lgcns.ikep4.collpack.qna.model.QnaPolicy;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaPolicyDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("qnaPolicyDao")
public class QnaPolicyDaoImpl extends GenericDaoSqlmap<QnaPolicy, String> implements QnaPolicyDao {
	
	
	
	public List<QnaPolicy> listPolicy(String portalId) {
		return sqlSelectForList("collpack.qna.dao.QnaPolicy.selectPolicy", portalId);
	}
	
	public int readCount(String portalId) {
		return (Integer)sqlSelectForObject("collpack.qna.dao.QnaPolicy.selectCount", portalId);
	}
	
	public List<String> listPotalId() {
		return (List)sqlSelectForListOfObject("collpack.qna.dao.QnaPolicy.listPotalId");
	}

	public String create(QnaPolicy qnaPolicy) {
		return (String) sqlInsert("collpack.qna.dao.QnaPolicy.insert", qnaPolicy);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public QnaPolicy get(String id) {
		return null;
	}
	
	public void update(QnaPolicy qnaPolicy) {
		
		sqlUpdate("collpack.qna.dao.QnaPolicy.update", qnaPolicy);
	}
	
	
	public void remove(String policyId) {
		
		sqlDelete("collpack.qna.dao.QnaPolicy.delete", policyId);
	}
	
	
}
