/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.search.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.search.model.BoardHandle;

/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: BoardHandleDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface BoardHandleDao extends GenericDao<BoardHandle, String> {
	
	/**
	 * textContents update
	 * @param 	BoardHandle
	 * @return 	void
	 */
	public void updateTextContents(BoardHandle boardHandle);
}