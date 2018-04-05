package com.lgcns.ikep4.support.user.code.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.dao.CityDao;
import com.lgcns.ikep4.support.user.code.dao.NationDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.City;
import com.lgcns.ikep4.support.user.code.model.Nation;

/**
 * 도시 관련 Dao 구현체
 * @author 송희정
 *
 */
@Repository("cityDao")
public class CityDaoImpl extends GenericDaoSqlmap<City, String> implements CityDao {


	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.NationDao#selectAll(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<City> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.City.selectAll", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.NationDao#selectCount(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.code.dao.City.selectCount", searchCondition);
	}
	
	
	public String create(City city) {

		return (String) sqlInsert("support.user.code.dao.City.insert", city);
	}

	public boolean exists(String code) {

		String count = (String) sqlSelectForObject("support.user.code.dao.City.checkCode", code);

		return !count.equals("0");
	}

	public City get(String code) {
		// TODO Auto-generated method stub
		return (City)sqlSelectForObject("support.user.code.dao.City.select", code);
	}

	public void remove(String code) {

		sqlDelete("support.user.code.dao.City.delete", code);
	}


	public void update(City city) {

		sqlUpdate("support.user.code.dao.City.update", city);
	}

}
