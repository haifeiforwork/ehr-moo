/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.searchpreprocessor.dao.RankDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.Rank;
/**
 * 
 * 순위를 생성검색수정
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: RankDAOImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class RankDAOImpl extends GenericDaoSqlmap<Rank,String> implements RankDAO {

	private static final String NAMESPACE = "com.lgcns.ikep4.support.searchpreprocessor.rank.";
	
	/**
	 * 검색어 순위 읽기
	 */
	public Rank get(String id) {
		return (Rank) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 검색어 순위 존재여부
	 */
	public boolean exists(String id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 검색어 순위 생성
	 */
	public String create(Rank rank) {
		sqlInsert(NAMESPACE+"create", rank);
		
		return rank.getSearchKeywordId();
	}

	/**
	 * 검색어 순위 수정
	 */
	public void update(Rank rank) {
		sqlUpdate(NAMESPACE+"update", rank);
	}

	/**
	 * 검색어 순위 제거
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 순위리스트검색
	 */
	public List<Rank> getRankList(){
		return sqlSelectForList(NAMESPACE+"getRankList");
	}
	
	/**
	 * 순위리스트 모두 제거
	 */
	public void removeAll(){
		sqlDelete(NAMESPACE+"removeAll");
	}
}