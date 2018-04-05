/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.SocialityTypeDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityType;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityTypePk;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityTypeService;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialityTypeServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service 
@Transactional
public class SocialityTypeServiceImpl extends GenericServiceImpl<SocialityType, SocialityTypePk> implements SocialityTypeService {

	@Autowired
	private SocialityTypeDao socialityTypeDao;
	
	public SocialityTypePk create(SocialityType socialityType) {
		return socialityTypeDao.create(socialityType);
	}

	public boolean exists(SocialityTypePk socialityTypePk) {
		return socialityTypeDao.exists(socialityTypePk);
	}

	public SocialityType read(SocialityTypePk socialityTypePk) {
		return socialityTypeDao.get(socialityTypePk);
	}

	public void delete(SocialityTypePk socialityTypePk) {
		socialityTypeDao.remove(socialityTypePk);
	}

	public void update(SocialityType socialityType) {
		socialityTypeDao.update(socialityType);
	}
	////////////////////////////////////
}
