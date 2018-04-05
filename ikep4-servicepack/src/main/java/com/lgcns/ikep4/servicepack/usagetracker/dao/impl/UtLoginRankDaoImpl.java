/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtLoginRankDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginRank;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 사용자현황통계
 *
 * @author ihko11
 * @version $Id: UtLoginRankDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class UtLoginRankDaoImpl extends GenericDaoSqlmap<UtLoginRank,String> implements UtLoginRankDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.model.utLoginRank.";
	
	/**
	 * 읽기
	 */
	public UtLoginRank get(String id) {
		return (UtLoginRank) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 존재여부
	 */
	public boolean exists(String id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 생성
	 */
	public String create(UtLoginRank utloginRank) {
		sqlInsert(NAMESPACE+"create", utloginRank);
		
		return utloginRank.getUserId();
	}

	/**
	 * 수정
	 */
	public void update(UtLoginRank UtLoginRank) {
		sqlUpdate(NAMESPACE+"update", UtLoginRank);
	}

	/**
	 * 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	
	public List<UtLoginRank> selectUserBatchReadItems(){
		return sqlSelectForList(NAMESPACE+"selectUserBatchReadItems");
	}
	
	/**
	 * 통계 메뉴리스트
	 */
	@SuppressWarnings("unchecked")
	public List<UtStatistics> listBySearchCondition(UtSearchCondition searchCondition) { 
		if( searchCondition.getSearchOption() == -1 ){
			return (List<UtStatistics>)getSqlMapClientTemplate().queryForList(NAMESPACE + "listBySearchCondition", searchCondition);
		}else{
			return (List<UtStatistics>)getSqlMapClientTemplate().queryForList(NAMESPACE + "listBySearchCondition"+searchCondition.getSearchOption(), searchCondition);
		}	
	}

	/**
	 * 통계 메뉴카운트
	 */
	public Integer countBySearchCondition(UtSearchCondition searchCondition) {
		if( searchCondition.getSearchOption() == -1 ){
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		}else{
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition"+searchCondition.getSearchOption(), searchCondition);
		}
	} 
}
