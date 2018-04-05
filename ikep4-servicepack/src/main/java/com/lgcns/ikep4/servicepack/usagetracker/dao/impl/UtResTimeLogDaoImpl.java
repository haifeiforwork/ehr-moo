package com.lgcns.ikep4.servicepack.usagetracker.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtResTimeLogDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

@Repository
public class UtResTimeLogDaoImpl extends GenericDaoSqlmap<UtResTimeLog,String>  implements UtResTimeLogDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.usagetracker.utResTimeLog.";

	/**
	 * 생성
	 */
	public String create(UtResTimeLog utResTimeLog) {
		sqlInsert(NAMESPACE+"create", utResTimeLog);
		
		return utResTimeLog.getResTimeAccessId();
	}
	
	/**
	 * 응답시간 URL 카운트
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchCondition(UtSearchCondition searchCondition) {
		if( searchCondition.getSearchOption() == 3 ){
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		}else{
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition"+searchCondition.getSearchOption(), searchCondition);
		}
		
	}
	
	/**
	 * 응답시간 URL 리스트
	 * @param searchCondition
	 * @return
	 */
	public List<UtResTimeLog> listBySearchCondition(UtSearchCondition searchCondition) {
		if( searchCondition.getSearchOption() == 3 ){
			return (List<UtResTimeLog>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
		}else{
			return (List<UtResTimeLog>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition"+searchCondition.getSearchOption(), searchCondition);
		}
		
	}

	@Deprecated
	public boolean exists(String arg0) {
		return false;
	}

	@Deprecated
	public UtResTimeLog get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	public void remove(String arg0) {
		// TODO Auto-generated method stub
	}

	@Deprecated
	public void update(UtResTimeLog arg0) {
		// TODO Auto-generated method stub
		
	}
}