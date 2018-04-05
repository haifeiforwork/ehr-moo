package com.lgcns.ikep4.servicepack.usagetracker.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;


public interface UtResTimeLogDao extends GenericDao<UtResTimeLog,String> {

	/**
	 * 응답시간 로그 카운트
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchCondition(UtSearchCondition searchCondition);
	
	/**
	 * 응답시간 URL 리스트
	 * @param searchCondition
	 * @return
	 */
	public List<UtResTimeLog> listBySearchCondition(UtSearchCondition searchCondition);
}