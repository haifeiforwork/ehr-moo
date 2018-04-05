/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.todo.model.MyOrder;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: MyOrderDao.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface MyOrderDao extends GenericDao<MyOrder, String> {
	/**
	 * 나의  지시 카운트
	 * 
	 * @param directorId 
	 * @return 
	 */
	Integer countMyOrder(String directorId);
	/**
	 * 나의 지시
	 * @param todoSearchCondition 
	 * @return
	 */
	List<MyOrder> listMyOrder(TodoSearchCondition todoSearchCondition);
	/**
	 * 나의 지시 검색 카운트
	 * @param todoSearchCondition 
	 * @return
	 */
	Integer countMyOrderSearch(TodoSearchCondition todoSearchCondition);
	/**
	 * 나의 지시 검색
	 * @param todoSearchCondition
	 * @return
	 */
	List<MyOrder> listMyOrderSearch(TodoSearchCondition todoSearchCondition);
}
