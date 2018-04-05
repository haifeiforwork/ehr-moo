package com.lgcns.ikep4.support.user.code.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.dao.CityDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.City;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.CityService;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 도시 관리 서비스 구현체 
 * @author 송희정
 *
 */
@Service("CityService")
@Transactional
public class CityServiceImpl extends GenericServiceImpl<City, String> implements CityService {

	
	@Autowired
	private CityDao cityDao;

	@Autowired
	private I18nMessageService i18nMessageService;
	
	@Autowired
	private IdgenService idgenService;

	public SearchResult<City> list(AdminSearchCondition searchCondition) {
		
		Integer count = cityDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<City> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<City>(searchCondition);
		}else{
			
			List<City> cityList = cityDao.selectAll(searchCondition);
			searchResult = new SearchResult<City>(cityList, searchCondition);
		}
		
		return searchResult;
	}

	
	
	public City read(String code) {

		return cityDao.get(code);
	}

	
	public boolean exists(String code) {

		return cityDao.exists(code);
	}
	
	public void update(City city) {
		
		i18nMessageService.update(city.getI18nMessageList());
		cityDao.update(city);
	}

	public String create(City city) {
		
		city.setCityId(idgenService.getNextId());
		
		cityDao.create(city);
		
		List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(city.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, city.getCityId());
		city.setI18nMessageList(i18nMessageList);

		i18nMessageService.create(i18nMessageList);
		

		return city.getCityId();
	}
	
	
	public void delete(String code) {

		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, code);
		cityDao.remove(code);
	}

}
