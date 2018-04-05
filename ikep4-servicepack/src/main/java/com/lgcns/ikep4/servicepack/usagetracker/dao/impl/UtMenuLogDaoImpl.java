/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtMenuLogDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenuLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 
 * 사용량 통계 사용자 메뉴 히스토리 daoimpl
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtMenuLogDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class UtMenuLogDaoImpl extends GenericDaoSqlmap<UtMenuLog,String> implements UtMenuLogDao {


	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.utMenuLog.";
		
		/**
		 * 읽기
		 */
		public UtMenuLog get(String id) {
			return (UtMenuLog) sqlSelectForObject(NAMESPACE+"get", id);
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
		public String create(UtMenuLog utMenuLog) {
			sqlInsert(NAMESPACE+"create", utMenuLog);
			
			return utMenuLog.getMenuAccessId();
		}

		/**
		 * 수정
		 */
		public void update(UtMenuLog UtMenuLog) {
			sqlUpdate(NAMESPACE+"update", UtMenuLog);
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
		public List<UtMenuLog> listBySearchCondition(UtSearchCondition searchCondition) { 
			return (List<UtMenuLog>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
		}
		
		public List<UtMenuLog> mobileListBySearchCondition(UtSearchCondition searchCondition) { 
			return (List<UtMenuLog>)this.sqlSelectForList(NAMESPACE + "mobileListBySearchCondition", searchCondition);
		}

		/**
		 * 통계 메뉴카운트
		 */
		public Integer countBySearchCondition(UtSearchCondition searchCondition) {
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		} 
		
		public Integer mobileCountBySearchCondition(UtSearchCondition searchCondition) {
			return (Integer)this.sqlSelectForObject(NAMESPACE + "mobileCountBySearchCondition", searchCondition);
		} 
		
		
		/**
		 * 통계 메뉴리스트
		 */
		public List<UtMenuLog> excelMenuListBySearchCondition(UtSearchCondition searchCondition) { 
			return this.sqlSelectForList(NAMESPACE + "excelMenuListBySearchCondition", searchCondition);
		}
		
		public List<UtMenuLog> excelMobileMenuListBySearchCondition(UtSearchCondition searchCondition) { 
			return this.sqlSelectForList(NAMESPACE + "excelMobileMenuListBySearchCondition", searchCondition);
		}
}