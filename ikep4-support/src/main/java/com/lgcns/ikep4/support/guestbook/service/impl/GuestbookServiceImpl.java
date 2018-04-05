/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.guestbook.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.guestbook.dao.GuestbookDao;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;
import com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition;
import com.lgcns.ikep4.support.guestbook.service.GuestbookService;


/**
 * 방명록 게시글 조회용 Service Impl
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: GuestbookServiceImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Service("guestbookService")
public class GuestbookServiceImpl extends GenericServiceImpl<Guestbook, String> implements GuestbookService {

	/**
	 * 방명록 글 조회 용 Dao
	 */
	@Autowired
	private GuestbookDao guestbookDao;
	
	/**
	 * Acitvity Stream 기록을 위한 Service
	 */
	@Autowired
	private ActivityStreamService activityStreamService;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.service.GuestbookService#listGuestbook(com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition)
	 */
	public SearchResult<Guestbook> listGuestbook(GuestbookSearchCondition guestbookSearch) {
		//return guestbookDao.selectAll(guestbookSearch);
		
		Integer count = this.guestbookDao.selectAllCount(guestbookSearch);
		guestbookSearch.terminateSearchCondition(count);  
		
		SearchResult<Guestbook> searchResult = null; 
		if(guestbookSearch.isEmptyRecord()) {
			searchResult = new SearchResult<Guestbook>(guestbookSearch);
			
		} else {
			
			List<Guestbook> guestbookList = this.guestbookDao.selectAll(guestbookSearch) ;
			searchResult = new SearchResult<Guestbook>(guestbookList, guestbookSearch); 
		}  
		
		return searchResult;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.service.GuestbookService#listGuestbookByGuestId(com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition)
	 */
	public SearchResult<Guestbook> listGuestbookByGuestId(GuestbookSearchCondition guestbookSearch) {
		
		Integer count = this.guestbookDao.selectAllCount(guestbookSearch);
		guestbookSearch.terminateSearchCondition(count);  
		
		SearchResult<Guestbook> searchResult = null; 
		if(guestbookSearch.isEmptyRecord()) {
			searchResult = new SearchResult<Guestbook>(guestbookSearch);
			
		} else {
			
			List<Guestbook> guestbookList = this.guestbookDao.selectAllMore(guestbookSearch) ;
			searchResult = new SearchResult<Guestbook>(guestbookList, guestbookSearch); 
		}  
		
		return searchResult;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.service.GuestbookService#listGuestbookByGroup(java.lang.String, java.lang.String)
	 */
	public List<Guestbook> listGuestbookByGroup(String groupId, String userLocale) {
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setGroupId(groupId);
		guestbookSearch.setSessUserLocale(userLocale);
		return guestbookDao.selectGuestbookByGroup(guestbookSearch);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.service.GuestbookService#listGuestbookByChild(java.lang.String, java.lang.String)
	 */
	public List<Guestbook> listGuestbookByChild(String parentId, String userLocale) {
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setParentId(parentId);
		guestbookSearch.setSessUserLocale(userLocale);
		return guestbookDao.selectGuestbookByChild(guestbookSearch);
	}


	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		return guestbookDao.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(Guestbook guestbook) {
		
		String guestbookId = guestbookDao.create(guestbook);
		// Activity Stream 추가 
		activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_ADDRESSBOOK, IKepConstant.ACTIVITY_CODE_GUESTBOOK_POST, guestbook.getRegisterId(), guestbook.getContents(), guestbook.getRegisterId(), 1);
		
		return guestbookId;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	public void update(Guestbook guestbook) {
		guestbookDao.update(guestbook);
		// Activity Stream 추가 
		activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_ADDRESSBOOK, IKepConstant.ACTIVITY_CODE_GUESTBOOK_DELETE, guestbook.getRegisterId(), guestbook.getContents(), guestbook.getRegisterId(), 1);
	}



	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.service.GuestbookService#listGuestbookByChild(java.lang.String, java.lang.String)
	 */
	public void updateStep(String groupId, int step, String targetUserId) {
		guestbookDao.updateStep(groupId, step, targetUserId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.service.GuestbookService#deleteGuestbook(java.lang.String)
	 */
	public void deleteGuestbook(String guestbookId) {
		guestbookDao.removeGuestbook(guestbookId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.service.GuestbookService#selectGuestbook(com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition)
	 */
	public Guestbook selectGuestbook(GuestbookSearchCondition guestbookSearch) {
		return guestbookDao.selectGuestbook(guestbookSearch);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.guestbook.service.GuestbookService#readPortletPropertyValue(java.util.Map)
	 */
	public String readPortletPropertyValue(Map<String, Object> map) {
		return guestbookDao.readPortletPropertyValue(map);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.Serializable)
	 */
	@Deprecated
	public Guestbook read(String id) {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {
		guestbookDao.remove(id);
	}

}
