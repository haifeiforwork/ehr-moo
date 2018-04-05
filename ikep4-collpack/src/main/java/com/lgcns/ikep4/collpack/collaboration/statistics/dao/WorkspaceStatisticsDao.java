/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.statistics.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.collaboration.statistics.model.WorkspaceStatistics;
import com.lgcns.ikep4.collpack.collaboration.statistics.search.StatisticsSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 홍정관
 * @version $Id: WorkspaceStatisticsDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface WorkspaceStatisticsDao extends GenericDao<WorkspaceStatistics, String> {

	/**
	 * Workspace 통계
	 * @param searchCondition
	 * @return
	 */
	List<WorkspaceStatistics> listStatisticsByCollaboration(StatisticsSearchCondition searchCondition);

	/**
	 * Workspace 맴버 통계
	 * @param searchCondition
	 * @return
	 */
	List<WorkspaceStatistics> listStatisticsByMember(StatisticsSearchCondition searchCondition);
	
	/**
	 * 통계정보 Batch Job 수집
	 *
	 */
	public void syncWorkspaceStatistics();
	
	/**
	 * 통계 삭제 (미사용 확인중)
	 */
	//public void physicalDeleteStatistics(String workspaceId);
	
}
