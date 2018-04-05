/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.forum.dao.FrReferenceDao;
import com.lgcns.ikep4.collpack.forum.model.FrReference;
import com.lgcns.ikep4.collpack.forum.service.FrReferenceService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrReferenceServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frReferenceService")
public class FrReferenceServiceImpl extends GenericServiceImpl<FrReference, String> implements FrReferenceService {


	@Autowired
	private FrReferenceDao frReferenceDao;
	

	public String create(FrReference frReference) {
		
		frReferenceDao.create(frReference);
		
		return null;
	}
	
	
	public List<FrReference> list(String itemId) {
		return frReferenceDao.list(itemId);
	}


	public void remove(String itemId, String registerId) {
		
		frReferenceDao.remove(itemId, registerId);
		
	}
	

}
