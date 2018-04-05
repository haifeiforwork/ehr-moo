/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.searchpreprocessor.dao.BatchLogDAO;
import com.lgcns.ikep4.support.searchpreprocessor.model.BatchLog;
import com.lgcns.ikep4.support.searchpreprocessor.search.SpSearchCondition;

/**
 * 
 * 배치로그
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: BatchLogDAOImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository
public class BatchLogDAOImpl extends GenericDaoSqlmap<BatchLog,String> implements BatchLogDAO {

	private static final String NAMESPACE = "com.lgcns.ikep4.support.searchpreprocessor.batchLog.";
	

	/**
	 * 배치로그리스트
	 */
	
	public List<BatchLog> listBySearchCondition(SpSearchCondition searchCondition) { 
		List<BatchLog> surveyItemList = (List<BatchLog>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
		
		return surveyItemList;
	}

	/**
	 * 배치로그 카운트
	 */
	public Integer countBySearchCondition(SpSearchCondition searchCondition) {
		
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		
		return count;
	} 


	public BatchLog get(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}


	/**
	 * 배치로그 생성
	 */
	public String create(BatchLog object) {
		sqlInsert(NAMESPACE+"create", object);
		return object.getId();
	}


	public void update(BatchLog object) {
		// TODO Auto-generated method stub
		
	}


	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
}