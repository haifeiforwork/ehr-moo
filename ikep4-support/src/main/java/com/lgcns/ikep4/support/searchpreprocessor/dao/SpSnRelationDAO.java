/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.support.user.member.model.User;

public interface SpSnRelationDAO extends GenericDao<User,String> {
	/**
	 * 동료 리스트
	 * @param searchUtil
	 * @return
	 */
	public List<User> getSnRelationList(SearchUtil searchUtil);
	/**
	 * 동료 상세리스트
	 * @param searchUtil
	 * @return
	 */
	public List<User> getSnRelationDetailList(SearchUtil searchUtil);
	/**
	 * 동료 상세 카운트
	 * @param searchUtil
	 * @return
	 */
	public Integer countSnRelationDetailList(SearchUtil searchUtil);
}