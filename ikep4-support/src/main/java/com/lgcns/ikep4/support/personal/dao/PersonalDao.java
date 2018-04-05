/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.personal.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.personal.model.Personal;
import com.lgcns.ikep4.support.personal.model.PersonalSearchCondition;


/**
 * Personal DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PersonalDao.java 18402 2012-04-29 12:24:41Z icerainbow $
 */
public interface PersonalDao extends GenericDao<Personal, String> {

	/**
	 * 도큐먼트 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Personal> listBySearchConditionForDocument(PersonalSearchCondition searchCondition);

	/**
	 * 도큐먼트 리스트 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionForDocument(PersonalSearchCondition searchCondition);

	/**
	 * 파일 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Personal> listBySearchConditionForFile(PersonalSearchCondition searchCondition);

	/**
	 * 코멘트 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Personal> listBySearchConditionForComment(PersonalSearchCondition searchCondition);

	/**
	 * 나의 갤러리 파일 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Personal> listBySearchConditionForMyGallery(PersonalSearchCondition searchCondition);

	/**
	 * 나의 갤러리 파일 리스트 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionForMyGallery(PersonalSearchCondition searchCondition);

}
