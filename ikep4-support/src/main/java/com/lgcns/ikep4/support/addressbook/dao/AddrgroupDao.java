/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.search.AddrgroupSearchCondition;


/**
 * 주소록 그룹 사용자 Dao Interface
 * 
 * @author 이형운
 * @version $Id: AddrgroupDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface AddrgroupDao extends GenericDao<Addrgroup, String> {

	/**
	 * 모든 주소록 그룹  조회
	 * 
	 * @param addrgroupSearch 주소록 그룹 조회용 객체
	 * @return 주소록 그룹 리스트 
	 */
	public List<Addrgroup> selectAll(AddrgroupSearchCondition addrgroupSearch);
	
	public List<Person> personList(String[] personNames);
	
	public List<Addrgroup> selectPubAll(AddrgroupSearchCondition addrgroupSearch);
	
	/**
	 * 모든 주소록 그룹  수 조회
	 * 
	 * @param addrgroupSearch 주소록 그룹 조회용 객체
	 * @return 주소록 그룹 수
	 */
	public Integer selectAllTotalCount(AddrgroupSearchCondition addrgroupSearch);
	
	/**
	 * 해당 주소록 그룹 조회
	 * @param addrgroup 주소록 그룹 객체
	 * @return 주소록 그룹 객체
	 */
	public Addrgroup get(Addrgroup addrgroup);

	/**
	 * 해당 주소록 정보의  존재 유무 
	 * @param addrgroup 주소록 그룹 객체
	 * @return 주소록 존재 유무
	 */
	public boolean exists(Addrgroup addrgroup);

	/**
	 * 해당 주소록 삭제
	 * @param addrgroup 주소록 그룹 객체
	 */
	public void remove(Addrgroup addrgroup);
}
