package com.lgcns.ikep4.collpack.kms.batch.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lgcns.ikep4.collpack.kms.batch.dao.KmsBatchDao;

@Repository("KmsBatchDao")
public class KmsBatchDaoImpl implements KmsBatchDao {
	
	private static final String NAMESPACE = "collpack.kms.batch.dao.KmsBatchDao.";
	
	
	@Autowired
	SqlMapClient sqlMapClientInfo;
	
	
	public void setSqlMapClientInfo(SqlMapClient sqlMapClientInfo) {
		this.sqlMapClientInfo = sqlMapClientInfo;
	}
		
	
	

	public Object get(String arg0) {
		try {
			
			return sqlMapClientInfo.queryForObject(NAMESPACE + "listTest");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	

}
