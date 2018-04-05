package com.lgcns.ikep4.collpack.kms.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.kms.main.dao.KmsPortletLayoutDao;
import com.lgcns.ikep4.collpack.kms.main.model.KmsPortletLayout;
import com.lgcns.ikep4.collpack.kms.main.service.KmsPortletLayoutService;
import com.lgcns.ikep4.collpack.kms.main.service.KmsPortletService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

@Service("kmsPortletLayoutService")
public class KmsPortletLayoutServiceImpl extends GenericServiceImpl<KmsPortletLayout, String> implements
		KmsPortletLayoutService {
	
	@Autowired
	public KmsPortletLayoutDao kmsPortletLayoutDao;
	
	@Autowired
	public KmsPortletService kmsPortletService;
	
	
	
	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;

	public String create(KmsPortletLayout arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public KmsPortletLayout read(String portletLayoutId) {
		KmsPortletLayout kmsPortletLayout= kmsPortletLayoutDao.get(portletLayoutId);
		kmsPortletLayout.setKmsPortlet(kmsPortletService.read(kmsPortletLayout.getPortletId()));
		return kmsPortletLayout;
	}

	public void update(KmsPortletLayout arg0) {
		// TODO Auto-generated method stub

	}

	public List<KmsPortletLayout> listLayout() {
		List<KmsPortletLayout> kmsPortletLayoutList = kmsPortletLayoutDao.listLayout();
		
		for( KmsPortletLayout kmsPortletLayout : kmsPortletLayoutList){
			kmsPortletLayout.setKmsPortlet(kmsPortletService.read(kmsPortletLayout.getPortletId()));
		}

		return kmsPortletLayoutList;
	}
	
	public List<KmsPortletLayout> listLayoutNew() {
		List<KmsPortletLayout> kmsPortletLayoutList = kmsPortletLayoutDao.listLayoutNew();
		
		for( KmsPortletLayout kmsPortletLayout : kmsPortletLayoutList){
			kmsPortletLayout.setKmsPortlet(kmsPortletService.read(kmsPortletLayout.getPortletId()));
		}

		return kmsPortletLayoutList;
	}

}
