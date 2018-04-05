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
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyRecommendDao;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereplyRecommend;
import com.lgcns.ikep4.collpack.ideation.service.IdLinereplyRecommendService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: IdLinereplyRecommendServiceImpl.java 12460 2011-05-20 09:48:52Z loverfairy $
 */
@Service("idLinereplyRecommendService")
public class IdLinereplyRecommendServiceImpl extends GenericServiceImpl<IdLinereplyRecommend, String> implements IdLinereplyRecommendService {


	@Autowired
	private IdLinereplyRecommendDao idLinereplyRecommendDao;
	
	@Autowired
	private IdLinereplyDao idLinereplyDao;
	

	public boolean create(String linereplyId, String registerId) {
		
		boolean result = false;
		boolean exists = idLinereplyRecommendDao.exists(linereplyId, registerId);
		
		if(!exists){	//존재 하는지 검사
			
			idLinereplyRecommendDao.create(linereplyId, registerId);
			
			//토론의견 추천 업데이트
			idLinereplyDao.updateRecommendCount(linereplyId);
			
			result = true;
		}
		
		return result;
	}
	
	
	public boolean exists(String linereplyId, String registerId) {
		return idLinereplyRecommendDao.exists(linereplyId, registerId);
	}
	
	
	public List<IdLinereplyRecommend> list(String linereplyId) {
		return idLinereplyRecommendDao.list(linereplyId);
	}


	public void remove(String linereplyId, String registerId) {
		
		
		//추천삭제
		idLinereplyRecommendDao.remove(linereplyId, registerId);
		
	}
	

}
