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
import com.lgcns.ikep4.collpack.qna.model.QnaExpert;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaExpertService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface QnaExpertService extends GenericService<QnaExpert, String> {
	
	/**
	 * qna로 expert 입력
	 * @param qna
	 */
	public void createByQna(Qna qna, User user); 
		
	
	/**
	 * 게시물 리스트 가져오기
	 * @param qnaId
	 * @return
	 */
	public List<QnaExpert> list(String qnaId);
	
	/**
	 * 한게시물만 가져오기
	 * @param map
	 * @return
	 */
	public QnaExpert read(String qnaId, String expertId);
	
	/**
	 * 게시물 삭제하기
	 * @param map
	 * @return
	 */
	public void delete(String qnaId, String expertId);
	
	/**
	 * Qnaid에 해당하는 게시물 삭제
	 * @param qnaId
	 * @param expertId
	 */
	public void removeQna(String qnaId);
}
