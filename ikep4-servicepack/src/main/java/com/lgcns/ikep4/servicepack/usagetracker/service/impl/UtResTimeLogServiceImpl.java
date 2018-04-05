package com.lgcns.ikep4.servicepack.usagetracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtResTimeLogDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtResTimeLogService;

@Service
@Transactional
public class UtResTimeLogServiceImpl extends GenericServiceImpl<UtResTimeLog, String> implements UtResTimeLogService {

	@Autowired
	private UtResTimeLogDao utResTimeLogDao;
	
	/**
	 * 응답시간 Log 추가
	 */
	@Override
	public String create(UtResTimeLog utResTimeLog) {
		return utResTimeLogDao.create(utResTimeLog);
	}

	/**
	 * 응답시간 로그 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtResTimeLog> listBySearchCondition(UtSearchCondition searchCondition) {
		SearchResult<UtResTimeLog> searchResult = null;
		
		Integer count = this.utResTimeLogDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UtResTimeLog>(searchCondition);
		} else {
			List<UtResTimeLog> resTimeLogList = utResTimeLogDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<UtResTimeLog>(resTimeLogList, searchCondition);
		}
		
		return searchResult;
	}
}