/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.searchpreprocessor.dao.DictionaryDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;
/**
 * 
 * 검색어사전
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: DictionaryDAOImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class DictionaryDAOImpl extends GenericDaoSqlmap<Dictionary,String> implements DictionaryDAO {

	private static final String NAMESPACE = "com.lgcns.ikep4.support.searchpreprocessor.dictionary.";
	
	/**
	 * 데이터 읽기
	 */
	public Dictionary get(String id) {
		return (Dictionary) sqlSelectForObject(NAMESPACE+"get", id);
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
	public String create(Dictionary dictionary) {
		sqlInsert(NAMESPACE+"create", dictionary);
		
		return dictionary.getSearchKeywordId();
	}

	/**
	 * 수정
	 */
	public void update(Dictionary dictionary) {
		sqlUpdate(NAMESPACE+"update", dictionary);
	}

	/**
	 * 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 리스트
	 */
	public List<Dictionary> getList(Dictionary dictionary){
		return sqlSelectForList(NAMESPACE+"getList", dictionary);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> getPortalIdList(){
		return getSqlMapClientTemplate().queryForList(NAMESPACE+"getPortalIdList");
	}
	
	/**
	 * 순위리스트
	 */
	public List<Dictionary> getRankList(Dictionary dictionary){
		return sqlSelectForList(NAMESPACE+"getRankList", dictionary);
	}
	
	/**
	 * 검색으로 사전검색
	 */
	public Dictionary getBySearchKeyword(Dictionary dictionary){
		return (Dictionary) sqlSelectForObject(NAMESPACE+"getBySearchKeyword", dictionary);
	}
	
}