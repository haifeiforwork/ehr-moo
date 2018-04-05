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

import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaLinereply;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaLinereplyService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface QnaLinereplyService extends GenericService<QnaLinereply, String> {
	
	
	/**
	 * 게시물 개수 가져오기
	 * @param qnaSearch
	 * @return
	 */
	public int getCount(Qna qnaSearch);

	
	public List<QnaLinereply> list(Qna qnaSearch);
	
	/**
	 * 한줄 댓글 임시 삭제
	 * @param linereplyId
	 */
	public void deleteLinereplyFlag(String linereplyId);
	
	/**
	 * 한줄 댓글 임시 삭제 취소
	 * @param linereplyId
	 */
	public void aliveLinereplyDeleteFlay(String linereplyId);
	
}
