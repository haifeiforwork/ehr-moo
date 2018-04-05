/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtPortletLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 
 * 사용량 통계 포틀릿 히스토리 dao
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtPortletLogDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface UtPortletLogDao extends GenericDao<UtPortletLog,String> {
	/**
	 * 통계 메뉴구성 리스트
	 * @param searchCondition
	 * @return
	 */
	List<UtPortletLog> listBySearchCondition(UtSearchCondition searchCondition);
	/**
	 * 통계 메뉴 카운트
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(UtSearchCondition searchCondition); 
}