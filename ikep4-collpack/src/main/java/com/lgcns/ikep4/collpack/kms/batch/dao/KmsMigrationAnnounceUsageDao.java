package com.lgcns.ikep4.collpack.kms.batch.dao;

import java.util.HashMap;

import com.ibatis.sqlmap.client.SqlMapClient;

public interface KmsMigrationAnnounceUsageDao {
	
	public void setSqlMapClientInfo(SqlMapClient sqlMapClientInfo) ;
	
	public Object listAnnounceFromNewsDB();
	
	public Object listViewLogFromNewsDB();

	public Object getUserInfo(String string);

	public void createKmsBdLog(HashMap<String, String> viewLogFromNews);

}
