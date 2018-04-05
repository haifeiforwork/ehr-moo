/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.searchpreprocessor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.searchpreprocessor.dao.DictionaryDAO;
import com.lgcns.ikep4.support.searchpreprocessor.dao.HistoryDAO;
import com.lgcns.ikep4.support.searchpreprocessor.dao.RelationDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;
import com.lgcns.ikep4.support.searchpreprocessor.model.History;
import com.lgcns.ikep4.support.searchpreprocessor.model.Relation;
import com.lgcns.ikep4.support.searchpreprocessor.model.RelationKey;
import com.lgcns.ikep4.support.searchpreprocessor.model.Report;
import com.lgcns.ikep4.support.searchpreprocessor.model.Result;
import com.lgcns.ikep4.support.searchpreprocessor.service.HistoryService;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 검색어 히스토리로그
 * 
 * @author ihko11
 * @version $Id: HistoryServiceImpl.java 16639 2011-09-24 07:46:44Z giljae $
 */
@Service
@Transactional
public class HistoryServiceImpl extends GenericServiceImpl<History, String> implements HistoryService {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	DictionaryDAO dictionaryDao;

	@Autowired
	RelationDAO relationDao;

	HistoryDAO historyDao;

	@Autowired
	private IdgenService idgenService;

	private final int frequencyCount = 1;

	@Autowired
	public HistoryServiceImpl(HistoryDAO dao) {
		super(dao);
		this.historyDao = dao;
	}

	/**
	 * 검색어 히스트로 리스트
	 */
	public Result getList(SearchUtil searchUtil) {
		Result result = new Result();

		Integer totalCount = historyDao.getCount(searchUtil);
		result.setEndIndex(searchUtil.getStartIndex() + searchUtil.getEndIndex());
		result.setTotalCount(totalCount);

		if (totalCount.intValue() > 0) {
			List<History> historyList = historyDao.getList(searchUtil);
			result.setHistoryList(historyList);
		}

		return result;
	}

	/**
	 * 검색어히스토리 메인리스트
	 */
	public List<History> getMainList(SearchUtil searchUtil) {
		return historyDao.getList(searchUtil);
	}

	/**
	 * 검색어 히스토리 삭제
	 */
	public void removeAll(Date date) {
		historyDao.removeAll(date);
	}

	/**
	 * 검색어 연관리스트
	 */
	public List<History> getRelatedList(SearchUtil searchUtil) {
		List<History> result = new ArrayList<History>();
		result = historyDao.getRelatedList(searchUtil);

		return result;
	}

	/**
	 * 검색어 정보입력
	 */
	@Override
	public String create(History history) {

		Dictionary dic = new Dictionary();
		dic.setSearchKeyword(history.getSearchKeyword());
		dic.setPortalId(history.getPortalId());
		
		Dictionary dbInfo = dictionaryDao.getBySearchKeyword(dic);
		String searchKeywordId = null;

		if (dbInfo != null) {
			// count ++
			searchKeywordId = dbInfo.getSearchKeywordId();
			dictionaryDao.update(dbInfo);
		} else {
			searchKeywordId = idgenService.getNextId();

			Dictionary dictionary = new Dictionary();
			dictionary.setPortalId(history.getPortalId());
			dictionary.setSearchKeywordId(searchKeywordId);
			dictionary.setSearchKeyword(history.getSearchKeyword());
			dictionary.setFrequencyCount(frequencyCount);
			dictionary.setRegistDate(new Date());
			dictionary.setRegisterId(history.getRegisterId());
			dictionary.setRegisterName(history.getRegisterName());
			dictionary.setUpdateDate(new Date());
			dictionary.setUpdaterId(history.getRegisterId());
			dictionary.setUpdaterName(history.getRegisterName());

			dictionaryDao.create(dictionary);
		}

		String searchHistoryId = idgenService.getNextId();
		history.setSearchHistoryId(searchHistoryId);
		history.setSearchKeywordId(searchKeywordId);
		history.setRegistDate(new Date());
		history.setUpdateDate(new Date());

		historyDao.create(history);

		if (StringUtils.hasText(history.getPrefixSearchKeywordId())
				&& !history.getPrefixSearchKeywordId().equals(searchKeywordId)) {
			// 있는지 없는지 체크
			RelationKey relationKey = new RelationKey(history.getPrefixSearchKeywordId(), searchKeywordId);

			if (relationDao.exists(relationKey)) {
				Relation relation = new Relation();
				// 이전키워드 정보
				relation.setSearchKeywordId(history.getPrefixSearchKeywordId());
				// 현재 키워드 정보
				relation.setRelationKeywordId(searchKeywordId);
				relationDao.update(relation);
			} else {
				Dictionary prefixDictionary = dictionaryDao.get(history.getPrefixSearchKeywordId());
				String seq = idgenService.getNextId();

				Relation relation = new Relation();
				relation.setId(seq);
				// 이전키워드 정보
				relation.setSearchKeywordId(prefixDictionary.getSearchKeywordId());
				relation.setSearchKeyword(prefixDictionary.getSearchKeyword());

				// 현재 키워드 정보
				relation.setRelationKeywordId(searchKeywordId);
				relation.setRelationKeyword(history.getSearchKeyword());

				relation.setFrequencyCount(frequencyCount);
				relation.setRegistDate(new Date());
				relation.setRegisterId(history.getRegisterId());
				relation.setRegisterName(history.getRegisterName());
				relation.setUpdateDate(new Date());
				relation.setUpdaterId(history.getRegisterId());
				relation.setUpdaterName(history.getRegisterName());
				relation.setPortalId(history.getPortalId());

				relationDao.create(relation);
			}
		}

		return history.getSearchHistoryId();
	}

	/**
	 * 검색어 일일리포트
	 */
	public List<Report> reportDayHistory(SearchUtil searchUtil) {
		return historyDao.reportDayHistory(searchUtil);
	}

	/**
	 * 검색어 월별리포트
	 */
	public List<Report> reportMonthHistory(SearchUtil searchUtil) {
		return historyDao.reportMonthHistory(searchUtil);
	}

	public void removeAllByRegister(String registerId) {
		historyDao.removeAllByRegister(registerId);
	}

}
