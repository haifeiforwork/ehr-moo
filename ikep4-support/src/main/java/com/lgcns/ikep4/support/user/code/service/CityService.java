package com.lgcns.ikep4.support.user.code.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.City;



/**
 * 도시관리 서비스
 * @author 송희정
 *
 */
public interface CityService extends GenericService<City, String> {

	/*
	 * 도시 조회
	 * @param searchCondition 검색조건
	 */
	public SearchResult<City> list(AdminSearchCondition searchCondition);
	
	
}
