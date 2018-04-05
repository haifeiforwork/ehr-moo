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

import com.lgcns.ikep4.collpack.qna.model.QnaPolicy;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaPolicyService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface QnaPolicyService extends GenericService<QnaPolicy, String> {
	

	/**
	 * 답변 정책
	 * 
	 * @return
	 */
	public List<QnaPolicy> listPolicy(String portalId);
	
	/**
	 * policy 개수
	 * @param portalId 
	 * @return
	 */
	public int readCount(String portalId);

	/**
	 * 점수 저장 하고 best qna 선정
	 *
	 */
	public void applyBestQnaAndSaveScore();
	
	
	
}
