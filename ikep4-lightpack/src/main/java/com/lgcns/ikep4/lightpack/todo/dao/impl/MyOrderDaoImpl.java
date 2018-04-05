/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.todo.dao.MyOrderDao;
import com.lgcns.ikep4.lightpack.todo.model.MyOrder;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: MyOrderDaoImpl.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Repository
public class MyOrderDaoImpl extends GenericDaoSqlmap<MyOrder, String> implements MyOrderDao {
	private static final String NAMESPACE = "lightpack.todo.dao.myOrder.";
	
	public String create(MyOrder myOrder) {
		return null;
	}

	public boolean exists(String id) {
		return false;
	}

	public MyOrder get(String id) {
		return null;
	}

	public void remove(String id) {
	}

	public void update(MyOrder myOrder) {
	}
	
	//나의  지시 카운트
	public Integer countMyOrder(String directorId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countMyOrder", directorId);
	}
	
	//나의 지시
	public List<MyOrder> listMyOrder(TodoSearchCondition todoSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listMyOrder", todoSearchCondition);
	}
	
	//나의 지시 검색 카운트
	public Integer countMyOrderSearch(TodoSearchCondition todoSearchCondition) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countMyOrderSearch", todoSearchCondition);
	}
	
	//나의 지시 검색
	public List<MyOrder> listMyOrderSearch(TodoSearchCondition todoSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listMyOrderSearch", todoSearchCondition);
	}
}

