/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.searchpreprocessor.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.searchpreprocessor.model.History;
import com.lgcns.ikep4.support.searchpreprocessor.model.Report;
import com.lgcns.ikep4.support.searchpreprocessor.model.Result;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;

/**
 * 검색 히스토리
 * 
 * @author ihko11
 * @version $Id: HistoryService.java 16639 2011-09-24 07:46:44Z giljae $
 */
@Transactional
public interface HistoryService extends GenericService<History, String> {
	/**
	 * 검색어 히스토리 리스트
	 * 
	 * @param searchUtil
	 * @return
	 */
	public Result getList(SearchUtil searchUtil);

	/**
	 * 검색어 연관리스트
	 * 
	 * @param searchKeyword
	 * @return
	 */
	public List<History> getRelatedList(SearchUtil searchUtil);

	/**
	 * 검색어 일별 리스트
	 * 
	 * @param searchUtil
	 * @return
	 */
	public List<Report> reportDayHistory(SearchUtil searchUtil);

	/**
	 * 검색어 월별 리스트
	 * 
	 * @param searchUtil
	 * @return
	 */
	public List<Report> reportMonthHistory(SearchUtil searchUtil);

	/**
	 * 검색어 제거
	 * 
	 * @param date
	 */
	public void removeAll(Date date);

	/**
	 * 사용자 검색어 제거
	 * 
	 * @param date
	 */
	public void removeAllByRegister(String registerId);

	/**
	 * 검색어 히스토리 메인리스트
	 * 
	 * @param searchUtil
	 * @return
	 */
	public List<History> getMainList(SearchUtil searchUtil);
}
