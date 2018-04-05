package com.lgcns.ikep4.collpack.kms.batch.dao.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lgcns.ikep4.collpack.kms.batch.dao.KmsMigrationAnnounceUsageDao;

@Repository("KmsMigrationAnnounceUsageDao")
public class KmsMigrationAnnounceUsageDaoImpl implements KmsMigrationAnnounceUsageDao {
	
	private static final String NAMESPACE = "collpack.kms.batch.dao.KmsMigrationAnnounceUsageDao.";
	
	
	@Autowired
	SqlMapClient sqlMapClientInfo;
	
	
	public void setSqlMapClientInfo(SqlMapClient sqlMapClientInfo) {
		this.sqlMapClientInfo = sqlMapClientInfo;
	}
	
	public Object listAnnounceFromNewsDB(){
		
		try {
			
			return sqlMapClientInfo.queryForList(NAMESPACE + "listAnnounceFromNewsDB");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
		
	}

	public Object listViewLogFromNewsDB() {
		try {
			
			return sqlMapClientInfo.queryForList(NAMESPACE + "listViewLogFromNewsDB");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Object getUserInfo(String emp_no) {
		try {
			
			return sqlMapClientInfo.queryForObject(NAMESPACE + "getUserInfo", emp_no);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void createKmsBdLog(HashMap<String, String> viewLogFromNews) {
		try {
			
			sqlMapClientInfo.insert(NAMESPACE +"createKmsBdLog", viewLogFromNews);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
