/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.qna.model.QnaRecommend;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaRecommendService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface QnaRecommendService extends GenericService<QnaRecommend, String> {
	

	/**
	 * qnaId에 해당하는 추천 리스트
	 * @param qnaId
	 * @return
	 */
	public List<QnaRecommend> list(String qnaId);
	
	/**
	 * 추천 한개만 가져오기
	 * @param qnaId
	 * @param recommendUserId
	 * @return
	 */
	public QnaRecommend read(String qnaId, String recommendUserId);
	
	/**
	 * 해당 추천 삭제
	 * @param qnaId
	 * @param recommendUserId
	 */
	public void delete(String qnaId, String recommendUserId);
	
}