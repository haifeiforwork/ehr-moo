/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.forum.model.FrReference;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrReferenceDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface FrReferenceDao extends GenericDao<FrReference, String>  {
	
	/**
	 * 토론글 조회 리스트
	 * @param itemId
	 * @return
	 */
	public List<FrReference> list(String itemId);
	
	/**
	 * 토론글에 해당하는 사용자ID 가져오기
	 * @param itemId
	 * @return
	 */
	public List<String> listUserId(String itemId);
	
	/**
	 * 토론주제애 해당하는 사용자 ID 리스트
	 * @param discussionId
	 * @return
	 */
	public List<String> listUserIdByDiscussionId(String discussionId);
	
	/**
	 * 사용자 토론글 조회 했는지
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public boolean exists(String itemId, String registerId);
	
	/**
	 * 사용자 토론글 조회 삭제
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public void remove(String itemId, String registerId);
	
	/**
	 * 토론글에 해당하는 모든 조회목록삭제
	 * @param itemId
	 */
	public void removebyItemId(String itemId);
	
	
	/**
	 * 토론주제id에 해당하는 것 모두 삭제
	 * @param discussionId
	 */
	public void removeByDiscussionId(String discussionId);
	
	
	
}
