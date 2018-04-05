/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginRank;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 사용자 사용량 rank dao
 *
 * @author ihko11
 * @version $Id: UtLoginRankService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface UtLoginRankService extends GenericService<UtLoginRank, String> {
	/**
	 * 사용량 사용자 사용량 배치 통계 생성 item read
	 * @return
	 */
	public List<UtLoginRank> selectUserBatchReadItems();
	
	/**
	 * 사용량 통계 사용자 분석테이블에 입력
	 * @param batchLogList
	 */
	public void saveOrUpdate(List<UtLoginRank> batchLogList);
	
	/**
	 * 통계 메뉴구성 리스트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtStatistics> listBySearchCondition(UtSearchCondition searchCondition);
	
	public List<UtStatistics> excelListBySearchCondition(UtSearchCondition searchCondition);
}
