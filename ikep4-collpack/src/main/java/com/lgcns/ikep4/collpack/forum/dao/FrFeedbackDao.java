/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.forum.model.FrFeedback;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrFeedbackDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface FrFeedbackDao extends GenericDao<FrFeedback, String>  {
	
	/**
	 * 토론글 찬성,반대 정보 가져오기
	 * @param frFeedback
	 * @return
	 */
	public FrFeedback get(String itemId, String feedbackType, String registerId);
	
	/**
	 * 토론글ID에 대한 사용자 리스트
	 * @param itemId
	 * @param feedbackType
	 * @return
	 */
	public List<String> listUserId(String itemId);
	
	/**
	 * 주제ID에 대한 사용자 리스트
	 * @param discussionId
	 * @param feedbackType
	 * @return
	 */
	public List<String> listUserIdByDiscussionId(String discussionId);
	
	/**
	 * 사용자가 등록한 토론글 찬성,반대 개수 가져오기
	 * @param frFeedback
	 * @return
	 */
	public int getCountByUserIdAsType(String feedbackType, String registerId);
	
	/**
	 * 사용자가 넣은 피드백이 있는지 가져오기
	 * @param frFeedback
	 * @return
	 */
	public boolean exists(String itemId, String feedbackType, String registerId);
	
	/**
	 * 피드백 리스트 가져오기
	 * @param frFeedback
	 * @return
	 */
	public List<FrFeedback> list(String itemId);
	
	/**
	 * 사용자가 찬성, 반대한 피드백 삭제
	 * @param frFeedback
	 */
	public void remove(String itemId, String feedbackType, String registerId);
	
	/**
	 * itemID에 해당하는 피드백 모두 삭제
	 * @param itemId
	 */
	public void removeByItemId(String itemId);
	
	/**
	 * 주제ID로 피드백 삭제
	 * @param discussionId
	 */
	public void removeByDiscussionId(String discussionId);
	
	
}
