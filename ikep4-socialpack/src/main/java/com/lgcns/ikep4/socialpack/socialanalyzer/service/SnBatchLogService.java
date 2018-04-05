/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.BatchLog;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.BatchLogPk;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SnBatchLogService.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SnBatchLogService extends GenericService<BatchLog, BatchLogPk> {
	/**
	 * 배치 로그 조회
	 * @param searchYM
	 * @return
	 */
	public List<BatchLog> listBatchLog(String searchYM);
}
