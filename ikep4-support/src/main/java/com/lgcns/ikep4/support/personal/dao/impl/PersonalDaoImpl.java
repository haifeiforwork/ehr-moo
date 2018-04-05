package com.lgcns.ikep4.support.personal.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.personal.dao.PersonalDao;
import com.lgcns.ikep4.support.personal.model.Personal;
import com.lgcns.ikep4.support.personal.model.PersonalSearchCondition;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * Personal 저장 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PersonalDaoImpl.java 18402 2012-04-29 12:24:41Z icerainbow $
 */
@Repository
public class PersonalDaoImpl extends GenericDaoSqlmap<Personal, String> implements PersonalDao {
	
	/**
	 * Personal 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.personal.";
	
	public String create(Personal object) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(Personal object) {
		// TODO Auto-generated method stub

	}

	public void remove(String personalId) {
		// TODO Auto-generated method stub
	}

	public Personal get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 도큐먼트 리스트 조회
	 */
	public List<Personal> listBySearchConditionForDocument(PersonalSearchCondition searchCondition) {
		return (List<Personal>) sqlSelectForList(NAMESPACE + "listBySearchConditionForDocument", searchCondition);
	}
	
	/**
	 * 도큐먼트 리스트 갯수
	 */
	public Integer countBySearchConditionForDocument(PersonalSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionForDocument", searchCondition);
	}
	
	/**
	 * 파일 리스트 조회
	 */
	public List<Personal> listBySearchConditionForFile(PersonalSearchCondition searchCondition) {
		return (List<Personal>) sqlSelectForList(NAMESPACE + "listBySearchConditionForFile", searchCondition);
	}
	
	/**
	 * 커멘트 리스트 조회
	 */
	public List<Personal> listBySearchConditionForComment(PersonalSearchCondition searchCondition) {
		return (List<Personal>) sqlSelectForList(NAMESPACE + "listBySearchConditionForComment", searchCondition);
	}
	
	/**
	 * 나의 갤러리 파일 리스트 조회
	 */
	public List<Personal> listBySearchConditionForMyGallery(PersonalSearchCondition searchCondition) {
		return (List<Personal>) sqlSelectForList(NAMESPACE + "listBySearchConditionForMyGallery", searchCondition);
	}
	
	/**
	 * 나의 갤러리 파일 리스트 갯수
	 */
	public Integer countBySearchConditionForMyGallery(PersonalSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchConditionForMyGallery", searchCondition);
	}
	
}
