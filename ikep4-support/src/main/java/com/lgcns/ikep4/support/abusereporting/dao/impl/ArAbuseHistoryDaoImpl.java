package com.lgcns.ikep4.support.abusereporting.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.abusereporting.dao.ArAbuseHistoryDao;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;


/**
 * 
 * ArAbuseHistoryDao 구현 클래스
 *
 * @author 최성우
 * @version $Id: ArAbuseHistoryDaoImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Repository
public class ArAbuseHistoryDaoImpl extends GenericDaoSqlmap<ArAbuseHistory, ArAbuseHistory> implements ArAbuseHistoryDao {

	private static final String NAMESPACE = "support.abusereporting.dao.arAbuseHistory.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public ArAbuseHistory create(ArAbuseHistory arAbuseHistory) {
		return (ArAbuseHistory) sqlInsert(NAMESPACE + "insert", arAbuseHistory);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ArAbuseHistory get(ArAbuseHistory arAbuseHistory) {
		return (ArAbuseHistory) sqlSelectForObject(NAMESPACE + "select", arAbuseHistory);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(ArAbuseHistory arAbuseHistory) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ArAbuseHistory object) {

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(ArAbuseHistory arAbuseHistory) {

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.dao.ArAbuseHistoryDao#listBySearchconditionForReport(com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition)
	 */
	public List<ArAbuseHistory> listBySearchconditionForReport(ArAbuseSearchCondition arAbuseSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listBySearchconditionForReport", arAbuseSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.dao.ArAbuseHistoryDao#listBySearchconditionForStatistics(com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List listBySearchconditionForStatistics(ArAbuseSearchCondition arAbuseSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "listBySearchconditionForStatistics", arAbuseSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.abusereporting.dao.ArAbuseHistoryDao#countBySearchconditionForReport(com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition)
	 */
	public Integer countBySearchconditionForReport(ArAbuseSearchCondition arAbuseSearchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchconditionForReport", arAbuseSearchCondition);
	}


}
