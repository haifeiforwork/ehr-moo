/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.qna.dao.QnaStatisticsDao;
import com.lgcns.ikep4.collpack.qna.model.QnaStatistics;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaStatisticsDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("qnaStatisticsDao")
public class QnaStatisticsDaoImpl extends GenericDaoSqlmap<QnaStatistics, String> implements QnaStatisticsDao {
	
	
	public String create(QnaStatistics qnaStatistics) {
		return (String) sqlInsert("collpack.qna.dao.QnaStatistics.insert", qnaStatistics);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public QnaStatistics get(String portalId) {
		return (QnaStatistics)sqlSelectForObject("collpack.qna.dao.QnaStatistics.select", portalId);
	}
	
	
	public void update(QnaStatistics qnaStatistics) {
		sqlUpdate("collpack.qna.dao.QnaStatistics.update", qnaStatistics);
	}
	
	
	public void remove(String portalId) {
		sqlDelete("collpack.qna.dao.QnaStatistics.delete", portalId);
	}
	
	
}
