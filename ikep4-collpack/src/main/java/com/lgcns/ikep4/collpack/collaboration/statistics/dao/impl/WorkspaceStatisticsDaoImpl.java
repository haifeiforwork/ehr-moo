/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.statistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.statistics.dao.WorkspaceStatisticsDao;
import com.lgcns.ikep4.collpack.collaboration.statistics.model.WorkspaceStatistics;
import com.lgcns.ikep4.collpack.collaboration.statistics.search.StatisticsSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 
 * WorkspaceMapDaoImpl
 *
 * @author 홍정관
 * @version $Id: WorkspaceStatisticsDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("workspaceStatisticsDao")
public class WorkspaceStatisticsDaoImpl extends GenericDaoSqlmap<WorkspaceStatistics, String> implements WorkspaceStatisticsDao {

	private static final String NAMESPACE = "collpack.collaboration.statistics.dao.WorkspaceStatistics.";

	public WorkspaceStatistics get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(WorkspaceStatistics object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(WorkspaceStatistics object) {
		// TODO Auto-generated method stub
		
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * WS 통계정보
	 */
	public List<WorkspaceStatistics> listStatisticsByCollaboration(StatisticsSearchCondition searchCondition) {
		return (List<WorkspaceStatistics>) this.sqlSelectForList(NAMESPACE+"listStatisticsByCollaboration", searchCondition);
	}
	/**
	 * 멤버 통계정보
	 */
	public List<WorkspaceStatistics> listStatisticsByMember(StatisticsSearchCondition searchCondition) {
		return (List<WorkspaceStatistics>) this.sqlSelectForList(NAMESPACE+"listStatisticsByMember", searchCondition);
	}
	/**
	 * 통계 배치 
	 */
	public void syncWorkspaceStatistics() {
		sqlSelectForObject(NAMESPACE + "syncWorkspaceStatistics");
	}		
	
	
	////////
	//public void physicalDeleteStatistics(String workspaceId) {
	//	sqlDelete(NAMESPACE + "physicalDeleteStatistics", workspaceId);
	//}	

}
