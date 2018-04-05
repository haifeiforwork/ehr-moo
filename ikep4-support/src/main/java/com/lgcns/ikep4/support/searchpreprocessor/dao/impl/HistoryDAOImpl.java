/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.searchpreprocessor.dao.HistoryDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.History;
import com.lgcns.ikep4.support.searchpreprocessor.model.Report;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;

/**
 * 
 * 검색 히스토리
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: HistoryDAOImpl.java 16639 2011-09-24 07:46:44Z giljae $
 */
@Repository
public class HistoryDAOImpl extends GenericDaoSqlmap<History,String> implements HistoryDAO {

private static final String NAMESPACE = "com.lgcns.ikep4.support.searchpreprocessor.history.";
	
	/**
	 * 검색어 히스토리 읽기
	 */
	public History get(String id) {
		return (History) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 검색어 히스토리 존재여부
	 */
	public boolean exists(String id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 검색어 히스토리 생성
	 */
	public String create(History history) {
		sqlInsert(NAMESPACE+"create", history);
		
		return history.getSearchHistoryId();
	}

	/**
	 * 검색어 히스토리 수정
	 */
	public void update(History history) {
		sqlUpdate(NAMESPACE+"update", history);
	}

	/**
	 * 검색어 히스토리 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 관련일자에 대해 모두 remove
	 */
	public void removeAll(Date date){
		sqlDelete(NAMESPACE+"removeAll", date);
	}
	
	/**
	 * 히스토리 검색리스트
	 */
	public List<History> getList(SearchUtil searchUtil){
		return sqlSelectForList(NAMESPACE+searchUtil.getCategory()+"SearchList", searchUtil,searchUtil.getStartIndex(),searchUtil.getEndIndex());
	}
	
	/**
	 * 히스토리 검색리스트 카운트
	 */
	public Integer getCount(SearchUtil searchUtil) {
		return (Integer)sqlSelectForObject(NAMESPACE+searchUtil.getCategory()+"SearchCount", searchUtil);
	}
	
	/**
	 * 연관검색어 리스트
	 */
	public List<History> getRelatedList(SearchUtil searchUtil){
		return sqlSelectForList(NAMESPACE+"relatedSearchList", searchUtil);
	}
	
	/**
	 * 일일분석 리포트
	 */
	@SuppressWarnings("unchecked")
	public List<Report> reportDayHistory(SearchUtil searchUtil){
		return getSqlMapClientTemplate().queryForList(NAMESPACE+"reportDayHistory", searchUtil);
	}
	/**
	 * 월별분석 리포트
	 */
	@SuppressWarnings("unchecked")
	public List<Report> reportMonthHistory(SearchUtil searchUtil){
		return getSqlMapClientTemplate().queryForList(NAMESPACE+"reportMonthHistory", searchUtil);
	}

	public void removeAllByRegister(String registerId) {
		sqlDelete(NAMESPACE+"removeAllByRegister", registerId);
	}
}