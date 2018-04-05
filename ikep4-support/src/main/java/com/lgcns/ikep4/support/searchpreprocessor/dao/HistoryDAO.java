/**
 * LG CNS IKEP4 
 */
package com.lgcns.ikep4.support.searchpreprocessor.dao;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.searchpreprocessor.model.History;
import com.lgcns.ikep4.support.searchpreprocessor.model.Report;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;

/**
 * 
 * 검색어 히스트로
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: HistoryDAO.java 16639 2011-09-24 07:46:44Z giljae $
 */
public interface HistoryDAO extends GenericDao<History, String> {
	/**
	 * 검색어 히스토리 리스트
	 * 
	 * @param searchUtil
	 * @return
	 */
	public List<History> getList(SearchUtil searchUtil);

	/**
	 * 검색어 히스토리 카운트
	 * 
	 * @param searchUtil
	 * @return
	 */
	public Integer getCount(SearchUtil searchUtil);

	/**
	 * 검색어 연관리스트
	 * 
	 * @param searchKeyword
	 * @return
	 */
	public List<History> getRelatedList(SearchUtil searchUtil);

	/**
	 * 검색어 일별 히스토리 리스트
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
	 * 검색어 삭제
	 * 
	 * @param date
	 */
	public void removeAll(Date date);

	/**
	 * 사용자 검색어 삭제
	 * 
	 * @param date
	 */
	public void removeAllByRegister(String registerId);
}