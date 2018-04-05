package com.lgcns.ikep4.collpack.kms.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.admin.dao.AdminBoardManageDao;
import com.lgcns.ikep4.collpack.kms.admin.model.Board;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository("AdminBoardManageDao")
public class AdminBoardManageDaoImpl extends GenericDaoSqlmap<Object, String> implements AdminBoardManageDao {

	private static final String NAMESPACE = "collpack.kms.admin.dao.AdminBoardManage.";
	
	/**
	 * 게시판 생성
	 */
	public String create(Object object) {
		this.sqlInsert(NAMESPACE + "create", object);

		return ((Board)object).getBoardId();
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(Object object) {
		this.sqlInsert(NAMESPACE + "update", object);

	}

	public List listByBoardRootId(Map<String, String> paramMap) {
		return this.sqlSelectForList(NAMESPACE + "listByBoardRootId", paramMap);
	}



	public List listChildrenBoard(Map<String, String> paramMap) {
		return this.sqlSelectForList(NAMESPACE + "listChildrenBoard", paramMap);
	}
	

	public Board get(Map<String, String> paramMap) {
		return (Board)this.sqlSelectForObject(NAMESPACE + "get", paramMap);
	}

	public List getParents(Map<String, String> paramMap) {
		return this.sqlSelectForList(NAMESPACE + "getParents", paramMap);
	}

	/**
	 * 게시판 순서 수정
	 */
	public void updateSortOderIncrease(Board board) {
		this.sqlUpdate(NAMESPACE + "updateSortOderIncrease", board);

	}

	/**
	 * 게시판 순서 수정
	 */
	public void updateSortOderDecrease(Board board) {
		this.sqlUpdate(NAMESPACE + "updateSortOderDecrease", board);

	}
	
	/**
	 * 게시판 이동
	 */
	public void updateMove(Board board) {
		this.sqlUpdate(NAMESPACE + "updateMove", board);
	}

	public void logicalDelete(Map<String, String> paramMap) {
		this.sqlUpdate(NAMESPACE + "logicalDelete", paramMap); 
		
	}	

}
