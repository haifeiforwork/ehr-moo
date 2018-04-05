package com.lgcns.ikep4.collpack.kms.main.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortletLayout;
import com.lgcns.ikep4.collpack.kms.main.dao.KmsPortletLayoutDao;
import com.lgcns.ikep4.collpack.kms.main.model.KmsPortletLayout;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository("kmsPortletLayoutDao")
public class KmsPortletLayoutDaoImpl extends GenericDaoSqlmap<KmsPortletLayout, String> implements KmsPortletLayoutDao {

	private static final String NAMESPACE = "collpack.kms.main.dao.KmsPortletLayout.";
	
	public String create(KmsPortletLayout arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public KmsPortletLayout get(String portletLayoutId) {
		return (KmsPortletLayout) sqlSelectForObject(NAMESPACE + "get", portletLayoutId);
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(KmsPortletLayout arg0) {
		// TODO Auto-generated method stub

	}

	public List<KmsPortletLayout> listLayout() {
		return sqlSelectForList(NAMESPACE + "listLayout");
	}
	
	public List<KmsPortletLayout> listLayoutNew() {
		return sqlSelectForList(NAMESPACE + "listLayoutNew");
	}

}
