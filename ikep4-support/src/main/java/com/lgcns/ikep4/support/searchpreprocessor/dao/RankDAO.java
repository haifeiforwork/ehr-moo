/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.searchpreprocessor.model.Rank;

public interface RankDAO extends GenericDao<Rank,String> {
	/**
	 * 검색어 순위리스트
	 * @return
	 */
	public List<Rank> getRankList();
	/**
	 * 검색어 순위삭제
	 *
	 */
	public void removeAll();
}