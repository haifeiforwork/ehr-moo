/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.searchpreprocessor.model.BatchLog;
import com.lgcns.ikep4.support.searchpreprocessor.search.SpSearchCondition;

/**
 * 
 * 배치실행로그
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: BatchLogDAO.java 16275 2011-08-18 07:08:37Z giljae $
 */
public interface BatchLogDAO extends GenericDao<BatchLog,String> {
	/**
	 * 배치로그 리스트
	 * @return
	 */
	List<BatchLog> listBySearchCondition(SpSearchCondition searchCondition);
	/**
	 * 배치로그 카운트
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(SpSearchCondition searchCondition); 
}