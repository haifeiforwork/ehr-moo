/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.forum.model.FrFeedback;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrFeedbackService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface FrFeedbackService extends GenericService<FrFeedback, String>  {
	
	
	/**
	 * 토론글 찬성
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public boolean createAgreement(String itemId , String registerId, String discussionId);
	
	/**
	 * 토론글 반대
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public boolean createOpposition(String itemId , String registerId, String discussionId);
	
	
	/**
	 * 토론글 찬성 삭제
	 * @param frFeedback
	 */
	public void removeAgreement(String itemId, String registerId, String discussionId);
	
	/**
	 * 토론글 반대 삭제
	 * @param frFeedback
	 */
	public void removeOpposition(String itemId, String registerId, String discussionId);
	
	/**
	 * 존재여부
	 * @param itemId
	 * @param feedbackType
	 * @param registerId
	 */
	public boolean exists(String itemId, String feedbackType, String registerId);
	
	
}
