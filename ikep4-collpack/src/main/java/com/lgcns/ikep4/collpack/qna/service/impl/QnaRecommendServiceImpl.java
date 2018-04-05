/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.qna.dao.QnaDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaRecommendDao;
import com.lgcns.ikep4.collpack.qna.model.QnaRecommend;
import com.lgcns.ikep4.collpack.qna.service.QnaRecommendService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: QnaRecommendServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("qnaRecommendService")
public class QnaRecommendServiceImpl extends GenericServiceImpl<QnaRecommend, String> implements QnaRecommendService {


	@Autowired
	private QnaRecommendDao qnaRecommendDao;
	
	@Autowired
	private QnaDao qnaDao;
	

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang
	 * .Object)
	 */
	public String create(QnaRecommend qnaRecommend) {
		
		//존재 하는지 검사---
		QnaRecommend obj = qnaRecommendDao.getRecommend(qnaRecommend.getQnaId(), qnaRecommend.getRegisterId());
		
		String result = "";
		if(obj == null){
			
			qnaRecommendDao.create(qnaRecommend);
			
			//추천수 변경
			qnaDao.updateRecommendCount(qnaRecommend.getQnaId());
			
			//부모 추천수 변경
			qnaDao.updateRecommendCountSum(qnaRecommend.getQnaId());
			
			result = qnaRecommend.getQnaId();
		} else {
			
			result = null;
		}
		
		return result;
	}
	
	

	public List<QnaRecommend> list(String qnaId) {
		return qnaRecommendDao.selectByQnaId(qnaId);
	}


	public QnaRecommend read(String qnaId, String recommendUserId) {
		
		return qnaRecommendDao.getRecommend(qnaId, recommendUserId);
	}
	

	
	public void delete(String qnaId, String recommendUserId) {
		
		qnaRecommendDao.removeRecommend(qnaId, recommendUserId);
		
		//추천수 변경
		qnaDao.updateRecommendCount(qnaId);
	}

	

}
