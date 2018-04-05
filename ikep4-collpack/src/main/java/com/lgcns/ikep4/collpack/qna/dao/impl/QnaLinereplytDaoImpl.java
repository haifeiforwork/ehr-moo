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

import com.lgcns.ikep4.collpack.qna.dao.QnaLinereplyDao;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaLinereply;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaLinereplytDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("qnaLinereplyDao")
public class QnaLinereplytDaoImpl extends GenericDaoSqlmap<QnaLinereply, String> implements QnaLinereplyDao {
	
	public List<QnaLinereply> selectAll(Qna qnaSearch) {
		
		return sqlSelectForList("collpack.qna.dao.QnaLinereply.selectAll", qnaSearch);
	}
	
	public int getCount(Qna qnaSearch) {
		return (Integer)sqlSelectForObject("collpack.qna.dao.QnaLinereply.selectCount", qnaSearch);
	}

	public int selectCountByParentId(String linereplyParentId) {
		return (Integer)sqlSelectForObject("collpack.qna.dao.QnaLinereply.selectCountByParentId", linereplyParentId);
	}

	public String create(QnaLinereply qna) {
		return (String) sqlInsert("collpack.qna.dao.QnaLinereply.insert", qna);
	}

	public boolean exists(String id) {
		return false;
	}

	public QnaLinereply get(String id) {
		return (QnaLinereply) sqlSelectForObject("collpack.qna.dao.QnaLinereply.select", id);
	}
	

	public void update(QnaLinereply qna) {
		sqlUpdate("collpack.qna.dao.QnaLinereply.update", qna);
	}
	

	public void updateStep(String linereplyGroupId, int step) {
		
		QnaLinereply qnaLinereply = new QnaLinereply();
		qnaLinereply.setLinereplyGroupId(linereplyGroupId);
		qnaLinereply.setStep(step);
		
		sqlUpdate("collpack.qna.dao.QnaLinereply.updateStep", qnaLinereply);
	}

	public void updateLinereplyDelete(String linereplyId, int linereplyDelete) {
		
		QnaLinereply qnaLinereply = new QnaLinereply();
		qnaLinereply.setLinereplyId(linereplyId);
		qnaLinereply.setLinereplyDelete(linereplyDelete);
		
		sqlUpdate("collpack.qna.dao.QnaLinereply.updateLinereplyDelete", qnaLinereply);
		
	}

	public void remove(String qnaId) {
		sqlDelete("collpack.qna.dao.QnaLinereply.delete", qnaId);
	}

	public void removeByQnaId(String id) {
		sqlDelete("collpack.qna.dao.QnaLinereply.deleteQna", id);
		
	}
	
	
}
