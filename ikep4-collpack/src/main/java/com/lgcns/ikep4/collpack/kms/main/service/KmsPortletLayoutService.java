package com.lgcns.ikep4.collpack.kms.main.service;

import java.util.List;

import com.lgcns.ikep4.collpack.kms.main.model.KmsPortletLayout;
import com.lgcns.ikep4.framework.core.service.GenericService;

public interface KmsPortletLayoutService extends GenericService<KmsPortletLayout, String> {

	List<KmsPortletLayout> listLayout();
	
	List<KmsPortletLayout> listLayoutNew();

}
