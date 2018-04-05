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

import com.lgcns.ikep4.collpack.qna.dao.QnaCategoryDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaDao;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.service.QnaCategoryService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: QnaCategoryServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("qnaCategoryService")
public class QnaCategoryServiceImpl extends GenericServiceImpl<QnaCategory, String> implements QnaCategoryService {


	@Autowired
	private QnaCategoryDao qnaCategoryDao;
	
	@Autowired
	private QnaDao qnaDao;
	
	@Autowired
	private IdgenService idgenService;
	

	public String create(QnaCategory qnaCategory) {
		
		String id = idgenService.getNextId();
		
		qnaCategory.setCategoryId(id);
			
		qnaCategoryDao.create(qnaCategory);
			
		
		return id;
	}
	

	public List<QnaCategory> list(String portalId) {
		return qnaCategoryDao.listAll(portalId);
	}


	public QnaCategory read(String id) {
		
		return qnaCategoryDao.get(id);
	}
	
	public void update(QnaCategory qnaCategory) {
		qnaCategoryDao.update(qnaCategory);
	}
	
	
	
	public void applyOrderCategory(String categoryId, String nextCategoryId, String orderType) {
		
		int categoryOrder = 0;
		int nextCategoryOrder = 0;
		if(orderType.equals("up")){
			categoryOrder = -1;
			nextCategoryOrder = 1;
		} else {
			categoryOrder = 1;
			nextCategoryOrder = -1;
		}
		
		qnaCategoryDao.updateOrder(categoryId, categoryOrder);
		qnaCategoryDao.updateOrder(nextCategoryId, nextCategoryOrder);
		
	}
	
	
	public void updateCategoryOrder(String categoryIdes) {
		
		String[] categoryIdList = categoryIdes.split(",");
		
		for(int i=0, count = categoryIdList.length; i < count; i++){
			String categoryId = categoryIdList[i];
			
			qnaCategoryDao.updateCategoryOrder(categoryId, i);
		}
		
	}


	public void delete(String categoryId, String portalId) {
		
		//기존 qna 카테고리 값을 null로 수정한다.
		qnaDao.updateCategoryId(categoryId, null);	
		
		qnaCategoryDao.remove(categoryId);
		
	}
	

}
