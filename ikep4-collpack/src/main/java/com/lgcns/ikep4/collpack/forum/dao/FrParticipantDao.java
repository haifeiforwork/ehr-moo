/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.forum.model.FrParticipant;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrParticipantDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface FrParticipantDao extends GenericDao<FrParticipant, String>  {
	
	
	/**
	 * 참여자 등록
	 * @param discussionId
	 * @param participationType
	 * @param registerId
	 */
	public void create(String discussionId,  String participationType, String registerId);
	
	/**
	 * 참여자 존재 하는지
	 * @param discussionId
	 * @param participationType
	 * @param registerId
	 * @return
	 */
	public boolean exists(String discussionId,  String participationType, String registerId);
	
	/**
	 * 참여자 정보 가져오기
	 * @param frParticipant
	 * @return
	 */
	public FrParticipant get(String discussionId, String participationType, String registerId);
	
	/**
	 * 참여자 목록
	 * @param frSearch
	 * @return
	 */
	public List<FrParticipant> list(FrSearch frSearch);
	
	/**
	 * 참여자 목록 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountList(FrSearch frSearch);
	
	/**
	 * 참여자 삭제
	 * @param discussionId
	 * @param participationType
	 * @param registerId
	 */
	public void remove(String discussionId, String participationType, String registerId);
	
	/**
	 * 해당 토론에 해당하는 참여자 모두 삭제
	 * @param discussionId
	 */
	public void removeByDiscussion(String discussionId);
	
}
