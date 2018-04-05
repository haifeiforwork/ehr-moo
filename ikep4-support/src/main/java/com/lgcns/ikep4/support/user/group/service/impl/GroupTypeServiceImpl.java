/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.group.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.dao.GroupTypeDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.model.GroupType;
import com.lgcns.ikep4.support.user.group.service.GroupTypeService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * Group Type 서비스 구현체
 *
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: GroupTypeServiceImpl.java 18008 2012-04-11 10:42:30Z arthes $
 */
@Service("groupTypeService")
@Transactional
public class GroupTypeServiceImpl extends GenericServiceImpl<GroupType, String> implements GroupTypeService {

	@Autowired
	private GroupTypeDao groupTypeDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private IdgenService idGenService;
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	public String create(GroupType groupType) {
		

		groupTypeDao.create(groupType);
		
		// groupId 생성해서 세팅
		String groupId = idGenService.getNextId();

		Group group = new Group();
		group.setPortalId(groupType.getPortalId());
		group.setGroupId(groupId);
		group.setParentGroupId("");
		group.setGroupName(groupType.getGroupTypeName());
		group.setGroupTypeId(groupType.getGroupTypeId());
		group.setChildGroupCount("0");
		group.setGroupEnglishName(groupType.getGroupTypeEnglishName());
		group.setRegisterId(groupType.getRegisterId());
		group.setRegisterName(groupType.getRegisterName());
		group.setUpdaterId(groupType.getRegisterId());
		group.setUpdaterName(groupType.getRegisterName());

		groupDao.create(group);
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update(java.lang.Object)
	 */
	public void update(GroupType groupType) {

		groupTypeDao.update(groupType);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	public void delete(String id) {

		groupTypeDao.remove(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public GroupType read(String id) {
		
		return groupTypeDao.get(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {

		return groupTypeDao.exists(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.service.GroupTypeService#selectForList(java.lang.String)
	 */
	public List<GroupType> selectForList() {
		
		return groupTypeDao.selectForList();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.group.service.GroupTypeService#list(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<GroupType> list(AdminSearchCondition searchCondition) {

		Integer count = groupTypeDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<GroupType> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<GroupType>(searchCondition);

		} else {

			List<GroupType> groupTypeList = groupTypeDao.selectAll(searchCondition);
			searchResult = new SearchResult<GroupType>(groupTypeList, searchCondition);
		}
		
		return searchResult;
	}
}