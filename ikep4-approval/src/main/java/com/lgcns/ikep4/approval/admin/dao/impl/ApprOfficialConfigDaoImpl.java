package com.lgcns.ikep4.approval.admin.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.admin.dao.ApprOfficialConfigDao;
import com.lgcns.ikep4.approval.admin.model.ApprOfficialConfig;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 공문 설정 DAO
 * 
 * @author 유승목
 * @version $Id$
 */
@Repository
public class ApprOfficialConfigDaoImpl extends GenericDaoSqlmap<ApprOfficialConfig, String> implements
		ApprOfficialConfigDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.admin.dao.ApprOfficialConfig.";

	public ApprOfficialConfig get(String configId) {
		return (ApprOfficialConfig) sqlSelectForObject(NAMESPACE + "get", configId);
	}

	public boolean exists(String signId) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(ApprOfficialConfig apprOfficialConfig) {
		return (String) sqlInsert(NAMESPACE + "create", apprOfficialConfig);
	}

	public void update(ApprOfficialConfig apprOfficialConfig) {
		sqlUpdate(NAMESPACE + "update", apprOfficialConfig);
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void deleteImgFile(Map map) {
		sqlUpdate(NAMESPACE + "deleteImgFile", map);
	}

}
