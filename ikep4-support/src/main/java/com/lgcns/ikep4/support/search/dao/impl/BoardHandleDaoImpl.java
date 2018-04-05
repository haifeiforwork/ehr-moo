/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.search.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.search.dao.BoardHandleDao;
import com.lgcns.ikep4.support.search.model.BoardHandle;

/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: BoardHandleDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository
public class BoardHandleDaoImpl extends GenericDaoSqlmap<BoardHandle, String> implements BoardHandleDao{
	
	private static final String NAMESPACE = "support.search.dao.BoardHandle.";
	
	
	/**
	 * textContents update
	 * @param 	BoardHandle
	 * @return 	void
	 */
	public void updateTextContents(BoardHandle boardHandle){
		sqlInsert(NAMESPACE + "updateTextContents", boardHandle);
	}
	
	public void update(BoardHandle boardHandle){
		// TODO Auto-generated method stub
	}
	
	public String create(BoardHandle boardHandle){
		// TODO Auto-generated method stub
		return "";
	}
	
	public void delete(BoardHandle boardHandle){
		// TODO Auto-generated method stub
	}
	
	public BoardHandle get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
}