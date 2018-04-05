/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.qna.model.QnaStatistics;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaStatisticsService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface QnaStatisticsService extends GenericService<QnaStatistics, String> {
	
	
	/**
	 * 답변 통계 등록 배치로 구현됨.
	 * @param qna
	 * @return
	 */
	public void create();

	
}
