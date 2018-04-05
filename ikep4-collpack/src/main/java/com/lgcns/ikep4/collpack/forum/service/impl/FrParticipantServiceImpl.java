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

import com.lgcns.ikep4.collpack.forum.dao.FrParticipantDao;
import com.lgcns.ikep4.collpack.forum.model.FrParticipant;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.service.FrParticipantService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrParticipantServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frParticipantService")
public class FrParticipantServiceImpl extends GenericServiceImpl<FrParticipant, String> implements FrParticipantService {


	@Autowired
	private FrParticipantDao frParticipantDao;
	

	public String create(FrParticipant frParticipant) {
		
		frParticipantDao.create(frParticipant);
		
		return null;
	}
	
	public FrParticipant get(String discussionId, String participationType, String registerId) {
		
		return frParticipantDao.get(discussionId, participationType, registerId);
		
	}
	

	public List<FrParticipant> list(FrSearch frSearch) {
		return frParticipantDao.list(frSearch);
	}
	
	
	public int getCountList(FrSearch frSearch) {
		return frParticipantDao.getCountList(frSearch);
	}


	public void remove(String discussionId, String participationType, String registerId) {
		
		frParticipantDao.remove(discussionId, participationType, registerId);
		
	}
	

}
