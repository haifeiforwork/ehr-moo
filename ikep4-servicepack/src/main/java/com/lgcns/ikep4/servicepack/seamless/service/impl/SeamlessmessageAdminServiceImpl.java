package com.lgcns.ikep4.servicepack.seamless.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.servicepack.seamless.dao.SeamlessmessageAdminDao;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageAdmin;
import com.lgcns.ikep4.servicepack.seamless.service.SeamlessmessageAdminService;

@Service
public class SeamlessmessageAdminServiceImpl extends GenericServiceImpl<SeamlessmessageAdmin,String> implements SeamlessmessageAdminService{

	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private SeamlessmessageAdminDao seamlessmessageAdminDao;
	
	@Autowired
	public SeamlessmessageAdminServiceImpl(SeamlessmessageAdminDao dao) {
		super(dao);
		this.seamlessmessageAdminDao = dao;
	}
	
	@Override
	public String create(SeamlessmessageAdmin seamlessmessageAdmin) {
		String existCheck = "C";
		if (this.seamlessmessageAdminDao.exists(seamlessmessageAdmin.getPortalId()) ) {
			this.seamlessmessageAdminDao.update(seamlessmessageAdmin);
			existCheck = "U";
		} else {
			this.seamlessmessageAdminDao.create(seamlessmessageAdmin);
		}
		return existCheck;
	}
}
