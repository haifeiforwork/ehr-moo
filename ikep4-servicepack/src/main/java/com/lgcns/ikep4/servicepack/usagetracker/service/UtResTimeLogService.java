package com.lgcns.ikep4.servicepack.usagetracker.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

@Transactional
public interface UtResTimeLogService extends GenericService<UtResTimeLog,String> {

	/**
	 * 응답시간 로그 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtResTimeLog> listBySearchCondition(UtSearchCondition searchCondition);
}