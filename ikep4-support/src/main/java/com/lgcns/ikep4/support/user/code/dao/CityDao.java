package com.lgcns.ikep4.support.user.code.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.City;


/**
 * 도시 관리 DAO 정의 
 * @author 송희정
 *
 */
public interface CityDao extends GenericDao<City, String>  {

	/**
	 * 도시 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<City> selectAll(AdminSearchCondition searchCondition);

	
	/**
	 * 도시 조회 카운트
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	
	Integer selectCount(AdminSearchCondition searchCondition);


	
	
	
	
}
