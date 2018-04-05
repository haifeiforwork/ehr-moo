/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaLinereply;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaLinereplyDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface QnaLinereplyDao extends GenericDao<QnaLinereply, String>  {
	/**
	 * 모든 게시물 반환
	 * 
	 * @return
	 */
	List<QnaLinereply> selectAll(Qna qnaSearch);
	
	/**
	 * 전체 개수
	 * @param qnaSearch
	 * @return
	 */
	int getCount(Qna qnaSearch);
	
	
	/**
	 * step 늘리기
	 * 
	 * @return
	 */
	void updateStep(String linereplyGroupId, int step);
	
	/**
	 * 삭제 플래그 수정
	 * 
	 * @return
	 */
	void updateLinereplyDelete(String linereplyId, int linereplyDelete);
	
	
	/**
	 * 자식글 있는지 검사
	 * 
	 * @return
	 */
	int selectCountByParentId(String linereplyParentId);
	
	
	/** 
	 * QnaID에 해당하는 모든 게시물 삭제 
	 * @param id
	 */
	void removeByQnaId(String linereplyId);
}
