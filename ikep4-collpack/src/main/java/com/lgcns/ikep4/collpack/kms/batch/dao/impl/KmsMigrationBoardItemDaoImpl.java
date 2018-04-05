package com.lgcns.ikep4.collpack.kms.batch.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lgcns.ikep4.collpack.kms.batch.dao.KmsMigrationBoardItemDao;

@Repository("KmsMigrationBoardItemDao")
public class KmsMigrationBoardItemDaoImpl implements KmsMigrationBoardItemDao {
	
	private static final String NAMESPACE = "collpack.kms.batch.dao.KmsMigrationBoardItemDao.";
	
	
	@Autowired
	SqlMapClient sqlMapClientInfo;
	
	
	public void setSqlMapClientInfo(SqlMapClient sqlMapClientInfo) {
		this.sqlMapClientInfo = sqlMapClientInfo;
	}
	
	public Object listInformationView(){
		
		try {
			return sqlMapClientInfo.queryForList(NAMESPACE + "listInformationView");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public Object getInformation(Map<String, String> boardIdMap) {
		try {
			return sqlMapClientInfo.queryForObject(NAMESPACE + "getInformation", boardIdMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public Object listInformationMemoView(Map<String, String> boardIdMap) {
		try {
			return sqlMapClientInfo.queryForList(NAMESPACE + "listInformationMemoView", boardIdMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public Object listInformationPracinfo() {
		try {
			return sqlMapClientInfo.queryForList(NAMESPACE + "listInformationPracinfo");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}


}
