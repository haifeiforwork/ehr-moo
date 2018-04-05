/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.todo.dao.MyOrderDao;
import com.lgcns.ikep4.lightpack.todo.model.MyOrder;
import com.lgcns.ikep4.lightpack.todo.model.TodoConstants;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;
import com.lgcns.ikep4.lightpack.todo.service.MyOrderService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;

/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: MyOrderServiceImpl.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Service 
@Transactional
public class MyOrderServiceImpl extends GenericServiceImpl<MyOrder, String> implements MyOrderService {
	@Autowired
	private MyOrderDao myOrderDao;

    @Autowired
    private TimeZoneSupportService timeZoneSupportService;
    
	public String create(MyOrder myOrder) {
		return null;
	}

	public boolean exists(String id) {
		return false;
	}

	public MyOrder read(String id) {
		return null;
	}

	public void delete(String id) {
	}

	public void update(MyOrder myOrder) {
	}

	//나의  지시 카운트
	public Integer countMyOrder(String directorId) {
		return myOrderDao.countMyOrder(directorId);
	}
	
	//나의 지시
	public List<MyOrder> listMyOrder(TodoSearchCondition todoSearchCondition) {
		todoSearchCondition.setSystemCode(TodoConstants.TODO_SYSTEM_CODE);
		todoSearchCondition.setSubworkCode(TodoConstants.TODO_SUBWORK_CODE);
		return myOrderDao.listMyOrder(todoSearchCondition);
	}

	//나의 지시 검색  카운트
	public Integer countMyOrderSearch(TodoSearchCondition todoSearchCondition) {
		return myOrderDao.countMyOrderSearch(todoSearchCondition);
	}

	//나의 지시 검색
	public SearchResult<MyOrder> listMyOrderSearch(TodoSearchCondition todoSearchCondition) {
		//timeZone 적용 
		if(todoSearchCondition.getFromDay() != null) {
	        todoSearchCondition.setFromDay(timeZoneSupportService.convertServerTimeZone(todoSearchCondition.getFromDay()));
		}
		if(todoSearchCondition.getToDay() != null) {
	        todoSearchCondition.setToDay(timeZoneSupportService.convertServerTimeZone(todoSearchCondition.getToDay()));
		}
		
		todoSearchCondition.setSystemCode(TodoConstants.TODO_SYSTEM_CODE);
		todoSearchCondition.setSubworkCode(TodoConstants.TODO_SUBWORK_CODE);
		
		Integer count = myOrderDao.countMyOrderSearch(todoSearchCondition);
		todoSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<MyOrder> searchResult = null; 
		if(todoSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<MyOrder>(todoSearchCondition);
		} else {
			List<MyOrder> myOrderList = myOrderDao.listMyOrderSearch(todoSearchCondition); 
			searchResult = new SearchResult<MyOrder>(myOrderList, todoSearchCondition);  			
		}  

		//timeZone 해지
		if(todoSearchCondition.getFromDay() != null) {
	        todoSearchCondition.setFromDay(timeZoneSupportService.convertTimeZone(todoSearchCondition.getFromDay()));
		}
		if(todoSearchCondition.getToDay() != null) {
	        todoSearchCondition.setToDay(timeZoneSupportService.convertTimeZone(todoSearchCondition.getToDay()));
		}	
		
		return searchResult;
	}
}