package com.lgcns.ikep4.collpack.kms.batch.dao;

import com.ibatis.sqlmap.client.SqlMapClient;

public interface KmsBatchDao  {
	
	public Object get(String arg0) ;
	
	public void setSqlMapClientInfo(SqlMapClient sqlMapClientInfo) ;

}
