package com.lgcns.ikep4.collpack.kms.batch.dao;

import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

public interface KmsMigrationBoardItemDao {
	
	public void setSqlMapClientInfo(SqlMapClient sqlMapClientInfo) ;
	
	public Object listInformationView();
	
	public Object getInformation(Map<String, String> boardIdMap);
	
	public Object listInformationMemoView(Map<String, String> boardIdMap);
	
	public Object listInformationPracinfo();

}
