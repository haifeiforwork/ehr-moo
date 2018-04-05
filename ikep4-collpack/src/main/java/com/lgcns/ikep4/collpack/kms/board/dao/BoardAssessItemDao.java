/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.kms.board.model.BoardAssessItem;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


public interface BoardAssessItemDao extends GenericDao<BoardAssessItem, String> {

	List<BoardAssessItem> listAssessItem(String boardId);
	
	void createAssessItem(BoardAssessItem boardAssessItem);
	
	void updateAssessItem(BoardAssessItem boardAssessItem);
	
	void deleteAssessItem(String id);
	
	int countIsAssessor(String userId);
	
	int countIsKeyInfoAssessor(String userId);
}
