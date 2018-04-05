/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.memo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.memo.dao.MemoDao;
import com.lgcns.ikep4.support.memo.model.Memo;
import com.lgcns.ikep4.support.memo.search.MemoSearchCondition;

/**
 * 나누미 DAO
 *
 * @author 배성훤(sunghwonbae@gmail.com)
 * @version $Id: nanumiDaoImpl.java 6218 2011-04-14 02:03:19Z combinet $
 */
@Repository(value = "memoDao")
public class MemoDaoImpl extends GenericDaoSqlmap<Memo, String> implements MemoDao {

	static final String NAMESPACE = "support.memo.dao.Memo.";

	public Integer countBySearchCondition(MemoSearchCondition memoSearchCondition) {
		
		Integer count =0;
		
		count = (Integer)sqlSelectForObject(NAMESPACE + "countMemoList", memoSearchCondition);

		return count;
	}
	
	public List<Memo> getMemoList(MemoSearchCondition memoSearchCondition) {
		
		List<Memo> memoList = sqlSelectForList(NAMESPACE + "getMemoList", memoSearchCondition);

		return memoList;
		
	}

	
	public Memo get(String id) {
		return (Memo) sqlSelectForObject(NAMESPACE +"get", id);
	}


	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}


	public String create(Memo memo) {
		// TODO Auto-generated method stub
		return (String)this.sqlInsert(NAMESPACE +"create", memo);
	}


	public void update(Memo object) {
		 	this.sqlUpdate(NAMESPACE +"update", object);
	}


	public void remove(String id) {
		sqlDelete(NAMESPACE + "remove", id);
		
	}





}
