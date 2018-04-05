package com.lgcns.ikep4.collpack.kms.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.kms.main.dao.KmsPortletDao;
import com.lgcns.ikep4.collpack.kms.main.model.KmsPortlet;
import com.lgcns.ikep4.collpack.kms.main.service.KmsPortletService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.user.member.model.User;

@Service("kmsPortletService")
public class KmsPortletServiceImpl extends GenericServiceImpl<KmsPortlet, String> implements KmsPortletService {

	@Autowired
	public KmsPortletDao kmsPortletDao;
	
	public String create(KmsPortlet arg0) {
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

	

	
	/**
	 * 포틀릿 조회
	 */
	public KmsPortlet read(String portletId) {

		return kmsPortletDao.get(portletId);
	}
	
	public void update(KmsPortlet arg0) {
		// TODO Auto-generated method stub

	}

}
