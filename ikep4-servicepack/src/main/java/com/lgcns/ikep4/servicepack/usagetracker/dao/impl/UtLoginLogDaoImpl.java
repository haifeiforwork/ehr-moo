/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtLoginLogDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 
 * 사용량 통계 사용자 로그인 히스토리 daoimpl
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtLoginLogDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class UtLoginLogDaoImpl extends GenericDaoSqlmap<UtLoginLog,String> implements UtLoginLogDao {


	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.utLoginLog.";
		
		/**
		 * 읽기
		 */
		public UtLoginLog get(String id) {
			return (UtLoginLog) sqlSelectForObject(NAMESPACE+"get", id);
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
		public String create(UtLoginLog utConfig) {
			sqlInsert(NAMESPACE+"create", utConfig);
			
			return utConfig.getLoginHistoryId();
		}

		/**
		 * 수정
		 */
		public void update(UtLoginLog UtLoginLog) {
			sqlUpdate(NAMESPACE+"update", UtLoginLog);
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
		public List<UtLoginLog> listBySearchCondition(UtSearchCondition searchCondition) { 
			return (List<UtLoginLog>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
		}
		
		public List<UtLoginLog> smsUseListBySearchCondition(UtSearchCondition searchCondition) { 
			return (List<UtLoginLog>)this.sqlSelectForList(NAMESPACE + "smsUseListBySearchCondition", searchCondition);
		}

		/**
		 * 통계 메뉴카운트
		 */
		public Integer countBySearchCondition(UtSearchCondition searchCondition) {
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		} 
		
		public Integer smsUseCountBySearchCondition(UtSearchCondition searchCondition) {
			return (Integer)this.sqlSelectForObject(NAMESPACE + "smsUseCountBySearchCondition", searchCondition);
		} 
		
		/**
		 * 통계 메뉴리스트
		 */
		public List<UtLoginLog> excelLoginListBySearchCondition(UtSearchCondition searchCondition) { 
			return this.sqlSelectForList(NAMESPACE + "excelLoginListBySearchCondition", searchCondition);
		}
		
		public List<UtLoginLog> excelSmsListBySearchCondition(UtSearchCondition searchCondition) { 
			return this.sqlSelectForList(NAMESPACE + "excelSmsListBySearchCondition", searchCondition);
		}
		
}