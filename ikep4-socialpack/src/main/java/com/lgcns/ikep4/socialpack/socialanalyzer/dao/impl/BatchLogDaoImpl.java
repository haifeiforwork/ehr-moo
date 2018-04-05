/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.BatchLogDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.BatchLog;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.BatchLogPk;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: BatchLogDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class BatchLogDaoImpl extends GenericDaoSqlmap<BatchLog, BatchLogPk> implements BatchLogDao {
	private static final String NAMESPACE = "socialpack.socialanalyzer.dao.batchLog.";

	@Deprecated
	public BatchLogPk create(BatchLog batchLog) {
		return null;
	}

	@Deprecated
	public boolean exists(BatchLogPk batchLogPk) {
		return false;
	}

	@Deprecated
	public BatchLog get(BatchLogPk batchLogPk) {
		return null;
	}

	@Deprecated
	public void remove(BatchLogPk batchLogPk) {
	}

	@Deprecated
	public void update(BatchLog batchLog) {
	}
	////////////////////////////////////

	//배치 로그 조회
	public List<BatchLog> listBatchLog(String searchYM) {
		return sqlSelectForList(NAMESPACE + "listBatchLog", searchYM);
	}
}
