/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.forum.model.FrParticipant;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrParticipantService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface FrParticipantService extends GenericService<FrParticipant, String>  {
	
	
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
	 * 참여자 목록 삭제
	 * @param discussionId
	 * @param participationType
	 * @param registerId
	 */
	public void remove(String discussionId, String participationType, String registerId);
	
	
}
