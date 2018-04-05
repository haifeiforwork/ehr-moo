/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.dao;

import java.util.Map;

import com.lgcns.ikep4.collpack.kms.board.model.BoardMigration;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author happyi1018
 * @version $Id: BoardMigrationDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface BoardMigrationDao extends GenericDao<BoardMigration, String> {
	
	public String getBoardId(Map<String, String> boardIdMap);
	
	public void createItemMapping(Map<String, String> boardIdMap);
	
	public String getItemId(Map<String, String> boardIdMap);
	
	public Object listItem();
	
	public void deleteItem();
	
	public Integer migrateBoardItemChkCnt(Map<String, String> hashMap);
}
