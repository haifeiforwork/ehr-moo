/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.statistics.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.collaboration.statistics.model.WorkspaceStatistics;
import com.lgcns.ikep4.collpack.collaboration.statistics.search.StatisticsSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
/**
 * 
 * WorkspaceMapService
 *
 * @author 홍정관
 * @version $Id: WorkspaceStatisticsService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface WorkspaceStatisticsService extends GenericService<WorkspaceStatistics, String> {

	/**
	 * Workspace 통계
	 * @param searchCondition
	 * @return
	 */
	SearchResult<WorkspaceStatistics> listStatisticsByCollaboration(StatisticsSearchCondition searchCondition);

	/**
	 * Workspace 맴버 통계
	 * @param searchCondition
	 * @return
	 */
	SearchResult<WorkspaceStatistics> listStatisticsByMember(StatisticsSearchCondition searchCondition);

	/** 
	 * Workspace 통계 정보 엑셀 다운로드
	 * @param searchCondition
	 * @return
	 */
	List<WorkspaceStatistics> excelListForCollaboration(StatisticsSearchCondition searchCondition);
	
	/**
	 * 통계 정보 일 배치 수집
	 *
	 */
	public void syncWorkspaceStatistics();
	
	//public void physicalDeleteStatistics(String workspaceId);

	/**
	 * 회원 통계 정보 엑셀다운로드
	 */
	List<WorkspaceStatistics> excelListForMember(StatisticsSearchCondition searchCondition);
}
