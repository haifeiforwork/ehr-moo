/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtPortletLogDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtPortletLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 
 * 사용량 통계 사용자 포틀릿 히스토리 daoimpl
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtPortletLogDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class UtPortletLogDaoImpl extends GenericDaoSqlmap<UtPortletLog,String> implements UtPortletLogDao {


	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.utPortletLog.";
		
		/**
		 * 읽기
		 */
		public UtPortletLog get(String id) {
			return (UtPortletLog) sqlSelectForObject(NAMESPACE+"get", id);
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
		public String create(UtPortletLog utPortletLog) {
			sqlInsert(NAMESPACE+"create", utPortletLog);
			
			return utPortletLog.getPortletHistoryId();
		}

		/**
		 * 수정
		 */
		public void update(UtPortletLog utPortletLog) {
			sqlUpdate(NAMESPACE+"update", utPortletLog);
		}

		/**
		 * 삭제
		 */
		public void remove(String id) {
			sqlDelete(NAMESPACE+"remove", id);
		}
		
		/**
		 * 통계 메뉴리스트
		 */
		public List<UtPortletLog> listBySearchCondition(UtSearchCondition searchCondition) { 
			return (List<UtPortletLog>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
		}

		/**
		 * 통계 메뉴카운트
		 */
		public Integer countBySearchCondition(UtSearchCondition searchCondition) {
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		} 
}