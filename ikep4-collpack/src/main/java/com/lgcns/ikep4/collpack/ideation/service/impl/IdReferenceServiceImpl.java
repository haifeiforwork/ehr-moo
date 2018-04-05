/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.collpack.ideation.dao.IdReferenceDao;
import com.lgcns.ikep4.collpack.ideation.model.IdReference;
import com.lgcns.ikep4.collpack.ideation.service.IdReferenceService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: IdReferenceServiceImpl.java 12460 2011-05-20 09:48:52Z loverfairy $
 */
@Service("idReferenceService")
public class IdReferenceServiceImpl extends GenericServiceImpl<IdReference, String> implements IdReferenceService {


	@Autowired
	private IdReferenceDao idReferenceDao;
	

	public String create(String itemId, String registerId) {
		
		idReferenceDao.create(itemId, registerId);
		
		return null;
	}
	
	
	public List<IdReference> list(String itemId) {
		return idReferenceDao.list(itemId);
	}


	public void remove(String itemId, String registerId) {
		
		idReferenceDao.remove(itemId, registerId);
		
	}
	

}
