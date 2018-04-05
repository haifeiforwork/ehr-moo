/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.dao.TimezoneDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.Timezone;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.TimezoneService;


/**
 * TIMEZONE 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: TimezoneServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("timezoneService")
@Transactional
public class TimezoneServiceImpl extends GenericServiceImpl<Timezone, String> implements TimezoneService {

	@Autowired
	private TimezoneDao timezoneDao;

	@Autowired
	private I18nMessageService i18nMessageService;
	
	public SearchResult<Timezone> selectAll(AdminSearchCondition searchCondition) {

		Integer count = timezoneDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<Timezone> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Timezone>(searchCondition);

		} else {

			List<Timezone> timezoneList = timezoneDao.selectAll(searchCondition);
			searchResult = new SearchResult<Timezone>(timezoneList, searchCondition);
		}
		
		return searchResult;
	}
	
	public String create(Timezone timezone) {
		
		i18nMessageService.create(timezone.getI18nMessageList());
		timezoneDao.create(timezone);
		
		return null;
	}
	
	public void update(Timezone timezone) {

		i18nMessageService.update(timezone.getI18nMessageList());
		timezoneDao.update(timezone);
	}
	
	public void delete(String id) {
		
		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, id);
		timezoneDao.remove(id);
	}
	
	public Timezone read(String id) {

		return timezoneDao.get(id);
	}
	
	public boolean exists(String id) {

		return timezoneDao.exists(id);
	}
	
	public List<Timezone> list(Map<String,String> param) {
		
		return timezoneDao.list(param);
	}
	
	public String getMaxSortOrder() {
		
		return timezoneDao.getMaxSortOrder();
	}
	
	public void goUp(Map<String,String> map) {
		
		Timezone upTimezone = timezoneDao.goUp(map);
		String upSortOrder = upTimezone.getSortOrder();

		upTimezone.setSortOrder((String) map.get("sortOrder"));
		
		timezoneDao.updateSortOrder(upTimezone);

		Timezone timezone = new Timezone();
		timezone.setTimezoneId((String)map.get("timezoneId"));
		timezone.setSortOrder(upSortOrder);

		timezoneDao.updateSortOrder(timezone);
	}
	
	public void goDown(Map<String,String> map) {
		
		Timezone downTimezone = timezoneDao.goDown(map);
		String downSortOrder = downTimezone.getSortOrder();
		
		downTimezone.setSortOrder((String) map.get("sortOrder"));
		
		timezoneDao.updateSortOrder(downTimezone);
		
		Timezone timezone = new Timezone();
		timezone.setTimezoneId((String) map.get("timezoneId"));
		timezone.setSortOrder(downSortOrder);
		
		timezoneDao.updateSortOrder(timezone);
	}

}
