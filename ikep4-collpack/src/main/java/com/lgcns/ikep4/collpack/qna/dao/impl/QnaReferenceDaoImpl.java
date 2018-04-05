/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.qna.dao.QnaReferenceDao;
import com.lgcns.ikep4.collpack.qna.model.QnaReference;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaReferenceDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("qnaReferenceDao")
public class QnaReferenceDaoImpl extends GenericDaoSqlmap<QnaReference, String> implements QnaReferenceDao {
	
	
	public String create(QnaReference qnaReference) {
		return (String) sqlInsert("collpack.qna.dao.QnaReference.create", qnaReference);
	}
	
	public int getCountByRegisterId(String qnaId, String registerId) {
		
		QnaReference qnaReference = new QnaReference();
		qnaReference.setQnaId(qnaId);
		qnaReference.setRegisterId(registerId);
		
		return (Integer)sqlSelectForObject("collpack.qna.dao.QnaReference.getCountByRegisterId", qnaReference);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public QnaReference get(String id) {
		return null;
	}
	

	@Deprecated
	public void update(QnaReference obj) {
	}
	
	
	public void removeQna(String qnaId) {
		sqlDelete("collpack.qna.dao.QnaReference.removeQna", qnaId);
	}
	
	@Deprecated
	public void remove(String id) {
	}
	
	
}
