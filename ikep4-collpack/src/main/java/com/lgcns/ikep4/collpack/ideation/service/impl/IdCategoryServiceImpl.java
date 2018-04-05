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
import com.lgcns.ikep4.collpack.ideation.dao.IdCategoryDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdIdeaDao;
import com.lgcns.ikep4.collpack.ideation.model.IdCategory;
import com.lgcns.ikep4.collpack.ideation.service.IdCategoryService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: IdCategoryServiceImpl.java 15601 2011-06-27 01:53:04Z loverfairy $
 */
@Service("idCategoryService")
public class IdCategoryServiceImpl extends GenericServiceImpl<IdCategory, String> implements IdCategoryService {


	@Autowired
	private IdCategoryDao idCategoryDao;
	
	@Autowired
	private IdIdeaDao idIdeaDao;
	
	@Autowired
	private IdgenService idgenService;
	

	public String create(IdCategory idCategory) {
		
		String id = idgenService.getNextId();
		
		idCategory.setCategoryId(id);
			
		idCategoryDao.create(idCategory);
			
		
		return id;
	}
	

	public List<IdCategory> list(String portalId) {
		return idCategoryDao.listAll(portalId);
	}


	public IdCategory read(String id) {
		
		return idCategoryDao.get(id);
	}
	
	public void update(IdCategory idCategory) {
		idCategoryDao.update(idCategory);
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
		
		idCategoryDao.updateOrder(categoryId, categoryOrder);
		idCategoryDao.updateOrder(nextCategoryId, nextCategoryOrder);
		
	}
	
	
	public void updateCategoryOrder(String categoryIdes) {
		
		String[] categoryIdList = categoryIdes.split(",");
		
		for(int i=0, count = categoryIdList.length; i < count; i++){
			String categoryId = categoryIdList[i];
			
			idCategoryDao.updateCategoryOrder(categoryId, i);
		}
		
	}


	public void delete(String categoryId, String portalId) {
		
		//기존 id 카테고리 값을 null로 수정한다.
		idIdeaDao.updateCategoryId(categoryId, null);	
		
		idCategoryDao.remove(categoryId);
		
	}
	

}
