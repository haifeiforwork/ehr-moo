package com.lgcns.ikep4.collpack.kms.main.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.kms.main.model.KmsPortletLayout;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface KmsPortletLayoutDao extends GenericDao<KmsPortletLayout, String> {

	List<KmsPortletLayout> listLayout();

	List<KmsPortletLayout> listLayoutNew();
}
