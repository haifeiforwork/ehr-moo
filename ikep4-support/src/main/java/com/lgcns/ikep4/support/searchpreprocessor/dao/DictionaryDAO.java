/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;
/**
 * 
 * 검색어 사전
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: DictionaryDAO.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface DictionaryDAO extends GenericDao<Dictionary,String> {
	/**
	 * 검색어 사전 리스트
	 * @param dictionary
	 * @return
	 */
	public List<Dictionary> getList(Dictionary dictionary);
	/**
	 * 검색어 랭킹 리스트
	 * @param dictionary
	 * @return
	 */
	public List<Dictionary> getRankList(Dictionary dictionary);
	
	/**
	 * portal id list
	 * @return
	 */
	public List<String> getPortalIdList();
	/**
	 * 검색어 찾기
	 * @param searchKeyword
	 * @return
	 */
	public Dictionary getBySearchKeyword(Dictionary dictionary);
}