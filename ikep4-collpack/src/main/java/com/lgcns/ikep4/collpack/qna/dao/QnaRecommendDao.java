/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.qna.model.QnaRecommend;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaRecommendDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface QnaRecommendDao extends GenericDao<QnaRecommend, String>  {
	
	/**
	 * 모든 게시물 반환
	 * 
	 * @return
	 */
	List<QnaRecommend> selectAll();
	
	
	/**
	 * qnaId에 해당하는 recommed 반환
	 * 
	 * @return
	 */
	List<QnaRecommend> selectByQnaId(String qnaId);
	
	
	/**
	 * 한게시물만 가져오기
	 * @param map
	 * @return
	 */
	QnaRecommend getRecommend(String qnaId, String registerId);
	
	/**
	 * recommend 삭제
	 * @param map
	 * @return
	 */
	void removeRecommend(String qnaId, String registerId);
	
	/** 
	 * QnaID에 해당하는 모든 게시물 삭제
	 * @param id
	 */
	void removeByQnaId(String qnaId);
	
	
}
