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

import com.lgcns.ikep4.collpack.qna.dao.QnaRecommendDao;
import com.lgcns.ikep4.collpack.qna.model.QnaRecommend;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaRecommendDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("qnaRecommendDao")
public class QnaRecommendDaoImpl extends GenericDaoSqlmap<QnaRecommend, String> implements QnaRecommendDao {
	
	public List<QnaRecommend> selectAll() {
		
		return sqlSelectForList("collpack.qna.dao.QnaRecommend.selectAll");
	}
	
	public List<QnaRecommend> selectByQnaId(String qnaId) {
		return sqlSelectForList("collpack.qna.dao.QnaRecommend.selectByQnaId", qnaId);
	}

	public String create(QnaRecommend qna) {
		return (String) sqlInsert("collpack.qna.dao.QnaRecommend.insert", qna);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public QnaRecommend get(String id) {
		return null;
	}
	
	public QnaRecommend getRecommend(String qnaId, String recommendUserId) {
		
		QnaRecommend qnaRecommend = new QnaRecommend();
		qnaRecommend.setQnaId(qnaId);
		qnaRecommend.setRegisterId(recommendUserId);
		
		return (QnaRecommend) sqlSelectForObject("collpack.qna.dao.QnaRecommend.select", qnaRecommend);
	}


	@Deprecated
	public void update(QnaRecommend obj) {
	}
	
	public void updateHit(String id) {
		sqlUpdate("collpack.qna.dao.QnaRecommend.updateHit", id);
	}
	
	
	public void updateIndentation(QnaRecommend obj) {
		sqlUpdate("collpack.qna.dao.QnaRecommend.updateIndentation", obj);
	}

	public void removeRecommend(String qnaId, String recommendUserId) {
		
		QnaRecommend qnaRecommend = new QnaRecommend();
		qnaRecommend.setQnaId(qnaId);
		qnaRecommend.setRegisterId(recommendUserId);
		
		sqlDelete("collpack.qna.dao.QnaRecommend.delete", qnaRecommend);
	}
	
	public void removeByQnaId(String qnaId) {
		sqlDelete("collpack.qna.dao.QnaRecommend.deleteQna", qnaId);
	}
	
	@Deprecated
	public void remove(String id) {
	}
	
	
}
