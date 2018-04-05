/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.microblogging.dao.AddonDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Addon;
import com.lgcns.ikep4.socialpack.microblogging.service.AddonService;


/**
 * 
 * AddonService 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: AddonServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service
public class AddonServiceImpl extends GenericServiceImpl<Addon, String> implements AddonService {

	@Autowired
	private AddonDao addonDao;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	public String create(Addon object) {
		return addonDao.create(object);
	}


	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public Addon read(String id) {
		Addon addon = addonDao.get(id);
		
		return addon;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	public void delete(String id) {
		addonDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.AddonService#getSeq()
	 */
	public int getSeq() {
		return addonDao.getSeq();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.AddonService#selectBySourceLink(java.lang.String)
	 */
	public Addon selectBySourceLink(String sourceLink) {
		return addonDao.selectBySourceLink(sourceLink);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.AddonService#selectByDisplayCode(java.lang.String)
	 */
	public Addon selectByDisplayCode(String displayCode) {
		return addonDao.selectByDisplayCode(displayCode);
	}

}
