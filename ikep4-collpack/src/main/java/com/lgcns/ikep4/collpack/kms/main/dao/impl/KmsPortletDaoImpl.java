package com.lgcns.ikep4.collpack.kms.main.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortlet;
import com.lgcns.ikep4.collpack.kms.main.dao.KmsPortletDao;
import com.lgcns.ikep4.collpack.kms.main.model.KmsPortlet;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository("kmsPortletDao")
public class KmsPortletDaoImpl extends GenericDaoSqlmap<KmsPortlet, String> implements KmsPortletDao {
	
	private static final String NAMESPACE = "collpack.kms.main.dao.KmsPortlet.";

	public String create(KmsPortlet arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public KmsPortlet get(String portletId) {
		return (KmsPortlet) sqlSelectForObject(NAMESPACE + "get", portletId);
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(KmsPortlet arg0) {
		// TODO Auto-generated method stub
		
	}

}
