/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.base.Constant;
import com.lgcns.ikep4.support.addressbook.dao.AddrgroupDao;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.model.Person2group;
import com.lgcns.ikep4.support.addressbook.model.PersonList;
import com.lgcns.ikep4.support.addressbook.search.AddrgroupSearchCondition;
import com.lgcns.ikep4.support.addressbook.service.AddrgroupService;
import com.lgcns.ikep4.support.addressbook.service.Person2groupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 주소록 그룹 사용자 Service Impl
 * 
 * @author 이형운
 * @version $Id: AddrgroupServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("addrgroupService")
public class AddrgroupServiceImpl extends GenericServiceImpl<Addrgroup, String> implements AddrgroupService {

	/**
	 * 주소록 그룹 컨트롤용 Dao
	 */
	@Autowired
	private AddrgroupDao addrgroupDao;
	
	/**
	 * 키값 생성을 위한 컨트롤 Service
	 */
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 주소록 Person 과 주소록 그룹 매칭 정보 컨트롤용 Service
	 */
	@Autowired
	private Person2groupService person2groupService;

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.AddrgroupService#read(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public Addrgroup read(Addrgroup addrgroup) {
		return addrgroupDao.get(addrgroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.AddrgroupService#exists(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public boolean exists(Addrgroup addrgroup) {
		return addrgroupDao.exists(addrgroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public String create(Addrgroup addrgroup) {
		return addrgroupDao.create(addrgroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public void update(Addrgroup addrgroup) {
		addrgroupDao.update(addrgroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.AddrgroupService#delete(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public void delete(Addrgroup addrgroup) {
		addrgroupDao.remove(addrgroup);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.AddrgroupService#list(com.lgcns.ikep4.support.addressbook.model.AddrgroupSearchCondition)
	 */
	public SearchResult<Addrgroup> list(AddrgroupSearchCondition addrgroupSearch) {
		
		Integer count = addrgroupDao.selectAllTotalCount(addrgroupSearch);
		addrgroupSearch.terminateSearchCondition(count);
		
		SearchResult<Addrgroup> searchResult = null;
		
		if (addrgroupSearch.isEmptyRecord()) {
			searchResult = new SearchResult<Addrgroup>(addrgroupSearch);

		} else {

			List<Addrgroup> addrgroupList = addrgroupDao.selectAll(addrgroupSearch);
			searchResult = new SearchResult<Addrgroup>(addrgroupList, addrgroupSearch);
		}
		
		return searchResult;

	}
	
	public List<Person> personList(String[] personNames){
		List<Person> personList = new ArrayList<Person>();
		personList = (List<Person>) addrgroupDao.personList(personNames);
		return (List<Person>) personList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.AddrgroupService#listWithoutCount(com.lgcns.ikep4.support.addressbook.model.AddrgroupSearchCondition)
	 */
	public SearchResult<Addrgroup> listWithoutCount(AddrgroupSearchCondition addrgroupSearch) {
		
		SearchResult<Addrgroup> searchResult = null;
		
		addrgroupSearch.setStartRowIndex(0);
		addrgroupSearch.setEndRowIndex(Constant.DEFAULT_ADDRGROUP_PERSON_FETCH_SIZE);
		
		List<Addrgroup> addrgroupList = addrgroupDao.selectAll(addrgroupSearch);
		searchResult = new SearchResult<Addrgroup>(addrgroupList, addrgroupSearch);
		
		return searchResult;

	}
	
	public SearchResult<Addrgroup> listPubCount(AddrgroupSearchCondition addrgroupSearch) {
		
		SearchResult<Addrgroup> searchResult = null;
		
		addrgroupSearch.setStartRowIndex(0);
		addrgroupSearch.setEndRowIndex(Constant.DEFAULT_ADDRGROUP_PERSON_FETCH_SIZE);
		
		List<Addrgroup> addrgroupList = addrgroupDao.selectPubAll(addrgroupSearch);
		searchResult = new SearchResult<Addrgroup>(addrgroupList, addrgroupSearch);
		
		return searchResult;

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.AddrgroupService#listCount(com.lgcns.ikep4.support.addressbook.model.AddrgroupSearchCondition)
	 */
	public Integer listCount(AddrgroupSearchCondition addrgroupSearch) {
		return addrgroupDao.selectAllTotalCount(addrgroupSearch);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.AddrgroupService#saveAddrgroupList(com.lgcns.ikep4.support.addressbook.model.Addrgroup,com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void saveAddrgroupList(Addrgroup addrgroup, User user) {
		// 주소록 저장
		if ( !(StringUtil.isEmpty(addrgroup.getAddrgroupId())) ){
			
			addrgroup.setPortalId(user.getPortalId());
			this.update(addrgroup);
			
			// 주소록 사용자 저장
			Person2group delP2G = new Person2group();
			delP2G.setAddrgroupId(addrgroup.getAddrgroupId());
			person2groupService.delete(delP2G);
				
			List<String> addrgroupIds = addrgroup.getAddrpersonList();
			if( addrgroupIds != null ){
				for( int i = 0 ; i < addrgroupIds.size() ; i++ ){
					
					List<String> personlist = StringUtil.getTokens(addrgroupIds.get(i), "|");
					
					if( personlist != null){
					Person2group person2group = new Person2group();
					person2group.setAddrgroupId(addrgroup.getAddrgroupId());
					person2group.setPersonId(personlist.get(0));				
					person2group.setUserType(personlist.get(1));
					person2group.setRegisterId(user.getUserId());
					person2group.setRegisterName(user.getUserName());
					person2group.setUpdaterId(user.getUserId());
					person2group.setUpdaterName(user.getUserName());
					person2groupService.create(person2group);
					}
		
				}
			}
			
		}else{
			
			String newAddrgroupId = idgenService.getNextId();
			
			addrgroup.setRegisterId(user.getUserId());
			addrgroup.setRegisterName(user.getUserName());
			addrgroup.setPortalId(user.getPortalId());
			addrgroup.setAddrgroupId(newAddrgroupId);
			this.create(addrgroup);
			
			List<String> addrgroupIds = addrgroup.getAddrpersonList();
			if( addrgroupIds != null ){
				for( int i = 0 ; i < addrgroupIds.size() ; i++ ){
					
					List<String> personlist = StringUtil.getTokens(addrgroupIds.get(i), "|");
					
					Person2group person2group = new Person2group();
					person2group.setAddrgroupId(newAddrgroupId);
					person2group.setPersonId(personlist.get(0));				
					person2group.setUserType(personlist.get(1));
					person2group.setRegisterId(user.getUserId());
					person2group.setRegisterName(user.getUserName());
					person2group.setUpdaterId(user.getUserId());
					person2group.setUpdaterName(user.getUserName());
					person2groupService.create(person2group);
		
				}
			}
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.addressbook.service.AddrgroupService#deleteAddrgroupList (java.lang.String)
	 */
	public void deleteAddrgroupList(String addrgroupId) {
		
		Person2group person2group = new Person2group();
		person2group.setAddrgroupId(addrgroupId);				
		person2groupService.delete(person2group);
		
		Addrgroup addrgroup = new Addrgroup();
		addrgroup.setAddrgroupId(addrgroupId);
		this.delete(addrgroup);
		
	}

}
