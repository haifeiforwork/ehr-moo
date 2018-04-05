/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDao;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategoryLocale;
import com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 범주 관리 서비스
 *  
 * @author 신용환(combinet@gmail.com)
 * @version $Id: PlannerCategoryServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service("plannerCategoryService")
public class PlannerCategoryServiceImpl extends GenericServiceImpl<PlannerCategory, String> implements
		PlannerCategoryService {

	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private ScheduleDao scheduleDao;
	
	@Autowired
	public PlannerCategoryServiceImpl(PlannerCategoryDao dao) {
		this.dao = dao;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.portal.planner.service.PlannerCategoryService#getList()
	 */
	public List<PlannerCategory> getList() {
		return ((PlannerCategoryDao) super.dao).getList();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String create(PlannerCategory category) {
		
		String id = idgenService.getNextId();
		if(log.isDebugEnabled()) {
			StringBuffer msg = new StringBuffer("ID created by idgenService: [")
			.append(id).append("]");
			log.debug(msg.toString());
		}
		
		category.setCategoryId(id);
		super.create(category);
		
		List<PlannerCategoryLocale> plocaleList = category.getPlannerCategoryLocaleList();
		for (Iterator iterator = plocaleList.iterator(); iterator.hasNext();) {
			PlannerCategoryLocale plannerCategoryLocale = (PlannerCategoryLocale) iterator.next();
			plannerCategoryLocale.setCategoryId(id);
			((PlannerCategoryDao) super.dao).createCategoryLocale(plannerCategoryLocale);
		}
		
		return id;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void update(PlannerCategory category) {
		((PlannerCategoryDao) super.dao).update(category);
		
		String categoryId = category.getCategoryId();
		((PlannerCategoryDao) super.dao).deletePlannerCategoryLocaleList(categoryId);
		List<PlannerCategoryLocale> plocaleList = category.getPlannerCategoryLocaleList();
		for (Iterator iterator = plocaleList.iterator(); iterator.hasNext();) {
			PlannerCategoryLocale plannerCategoryLocale = (PlannerCategoryLocale) iterator.next();
			plannerCategoryLocale.setCategoryId(categoryId);
			((PlannerCategoryDao) super.dao).createCategoryLocale(plannerCategoryLocale);
		}		
	}
	
	@Override
	public void delete(String cid) {
		((PlannerCategoryDao) super.dao).deletePlannerCategoryLocaleList(cid);
		((PlannerCategoryDao) super.dao).remove(cid);
	}
	
	public void delete(String[] cid) {
		((PlannerCategoryDao) super.dao).deletePlannerCategoryLocaleList(cid);
		((PlannerCategoryDao) super.dao).delete(cid);
	}
	
	public void deleteCategoryLocale(String[] cid) {
		((PlannerCategoryDao) super.dao).deletePlannerCategoryLocaleList(cid);
	}

	public void create(User user, String portalId, PlannerCategory category) {
		category.setPortalId(portalId);
		category.setRegisterId(user.getUserId());
		category.setRegisterName(user.getUserName());
		create(category);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService#isDuplicate(com.lgcns.ikep4.lightpack.planner.model.PlannerCategory)
	 */
	@SuppressWarnings("rawtypes")
	public boolean isDuplicate(PlannerCategory category) {
		List<PlannerCategoryLocale> plocaleList = category.getPlannerCategoryLocaleList();
		int count = 0;
		String categoryId = category.getCategoryId();

		for (Iterator iterator = plocaleList.iterator(); iterator.hasNext();) {
			PlannerCategoryLocale plannerCategoryLocale = (PlannerCategoryLocale) iterator.next();
			if(categoryId != null && !categoryId.equals(""))
				plannerCategoryLocale.setCategoryId(category.getCategoryId());
			count = ((PlannerCategoryDao) super.dao).isDuplicate(plannerCategoryLocale);
			if(count != 0) {
				break;
			}
		}	
		return count == 0 ? false: true;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService#deleteCategory(java.lang.String[])
	 */
	public void deleteCategory(String[] cid) {
		delete(cid);
		scheduleDao.updateToDefaultCategory(cid);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService#getLocaleList()
	 */
	public List<Map<String, String>> getLocaleList() {
		return (List<Map<String, String>>) ((PlannerCategoryDao) super.dao).getLocaleList();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService#readWithLocale(java.lang.String)
	 */
	public PlannerCategory readWithLocale(String categoryId) {
		return ((PlannerCategoryDao) super.dao).readWithLocale(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService#getList(java.lang.String)
	 */
	public List<PlannerCategory> getList(String string) {
		return ((PlannerCategoryDao) super.dao).getList(string);
	}

}
