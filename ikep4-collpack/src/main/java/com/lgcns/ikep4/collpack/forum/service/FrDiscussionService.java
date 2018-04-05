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

import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrDiscussionService.java 16903 2011-10-25 01:37:27Z giljae $
 */
@Transactional
public interface FrDiscussionService extends GenericService<FrDiscussion, String>  {
	
	
	/**
	 * 모든 게시물 반환
	 * 
	 * @return
	 */
	public FrDiscussion get(String discussionId);
	
	/**
	 * 모든 게시물 반환
	 * 
	 * @return
	 */
	public List<FrDiscussion> list(FrSearch frSearch);
	
	/**
	 * 모든 게시물  개수 반환
	 * @param frSearch
	 * @return
	 */
	public int getCountList(FrSearch frSearch);
	
	/**
	 * 인기 의견주제
	 * @param frSearch
	 * @return
	 */
	public List<FrDiscussion> listPopular(FrSearch frSearch);
	
	/**
	 * 인기 의견주제 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountListPopular(FrSearch frSearch);
	
	/**
	 * 메일수 업데이트
	 * @param qnaId
	 */
	public void updateMailCount(String discussionId);
	
	
	/**
	 * 블로그수 업데이트
	 * @param qnaId
	 */
	public void updateMblogCount(String discussionId);
	
	/**
	 * 삭제
	 * @param discussionId
	 */
	public void remove(String discussionId, String registerId);
	
	/**
	 * 해당 사용자가 조회한 최신 토론주제 리스트
	 * @param frSearch
	 * @return
	 */
	public List<FrDiscussion> listDiscussionByReference(String userId, int endRowIndex);
	
	/**
	 * 기간내에 토론글이 가장많이 등록된 순으로 리스트 가져옴.
	 * @param userId
	 * @param limitDate
	 * @return
	 */
	public List<FrDiscussion> listDiscussionByItemCount(int endRowIndex, String limitDate);
	
	
}
