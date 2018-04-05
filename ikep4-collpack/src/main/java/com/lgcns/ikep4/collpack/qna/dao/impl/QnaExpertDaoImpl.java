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

import com.lgcns.ikep4.collpack.qna.dao.QnaExpertDao;
import com.lgcns.ikep4.collpack.qna.model.QnaExpert;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaExpertDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("qnaExpertDao")
public class QnaExpertDaoImpl extends GenericDaoSqlmap<QnaExpert, String> implements QnaExpertDao {
	
	

	public String create(QnaExpert obj) {
		return (String) sqlInsert("collpack.qna.dao.QnaExpert.insert", obj);
	}
	
	public List<QnaExpert> list(String qnaId) {
		
		return sqlSelectForList("collpack.qna.dao.QnaExpert.selectAll", qnaId);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public QnaExpert get(String id) {
		return null;
	}
	

	public QnaExpert getExpert(String qnaId, String expertId) {
		
		QnaExpert qnaExpert = new QnaExpert();
		qnaExpert.setQnaId(qnaId);
		qnaExpert.setExpertId(expertId);
		
		return (QnaExpert) sqlSelectForObject("collpack.qna.dao.QnaExpert.select", qnaExpert);
	}

	public void update(QnaExpert obj) {
		sqlUpdate("collpack.qna.dao.QnaExpert.update", obj);
	}
	
	@Deprecated
	public void remove(String qnaId) {
	}

	public void removeExpert(String qnaId, String expertId) {
		
		QnaExpert qnaExpert = new QnaExpert();
		qnaExpert.setQnaId(qnaId);
		qnaExpert.setExpertId(expertId);
		
		sqlDelete("collpack.qna.dao.QnaExpert.delete", qnaExpert);
	}
	
	public void removeByQnaId(String qnaId) {
		sqlDelete("collpack.qna.dao.QnaExpert.deleteQna", qnaId);
	}

	
}
