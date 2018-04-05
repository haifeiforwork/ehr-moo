/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrLinereplyDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface FrLinereplyDao extends GenericDao<FrLinereply, String>  {
	
	/**
	 * 자식글 있는지 조회
	 * @param linereplyParentId
	 * @return
	 */
	public int getCountByParentId(String linereplyParentId);
	
	/**
	 * 사용자가 쓴 의견수, 베스트 의견수, 타인이 등록한 추천수
	 * @param registerId
	 * @return
	 */
	public FrLinereply getCountes(FrSearch frSearch);
	
	/**
	 * 토론글에 해당하는 의견 등록자 ID
	 * @param itemId
	 * @return
	 */
	public List<String> listUserId(String itemId);
	
	/**
	 * 토론주제에 해당하는 의견 등록자 ID
	 * @param discussionId
	 * @return
	 */
	public List<String> listUserIdByDiscussionId(String discussionId);
	
	/**
	 * 토론의견 목록 조회
	 * @param frSearch
	 * @return
	 */
	public List<FrLinereply> list(FrSearch frSearch);
	
	/**
	 * 토론 의견 목록 수
	 * @param frSearch
	 * @return
	 */
	public int getCountList(FrSearch frSearch);
	
	/**
	 * 토론주제, 토론글, 토론의견 목록 조회
	 * @param frSearch
	 * @return
	 */
	public List<FrLinereply> listWithItemDiscussion(FrSearch frSearch);
	
	/**
	 * 토론주제, 토론글, 토론의견 목록 수
	 * @param frSearch
	 * @return
	 */
	public int getCountListWithItemDiscussion(FrSearch frSearch);
	
	/**
	 * 최신 토록주제, 토론글, 토론의견 목록
	 * @param frSearch
	 * @return
	 */
	public List<FrLinereply> listLastWithItemDiscussion(FrSearch frSearch);
	
	/**
	 * 최신 토록주제, 토론글, 토론의견 목록 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountListLastWithItemDiscussion(FrSearch frSearch);
	
	/**
	 * step 수정
	 * @param linereplyGroupId
	 * @param step
	 */
	public void updateStep(String linereplyGroupId, int step);
	
	/**
	 * 토론의견 delete 필드 수정
	 * @param linereplyId
	 * @param linereplyDelete
	 */
	public void updateLinereplyDelete(String linereplyId, int linereplyDelete);
	
	/**
	 * 베스트 의견 수정
	 * @param linereplyId
	 */
	public void updateBestLinereply(String linereplyId);
	
	/**
	 * 베스트 의견 초기화
	 *
	 */
	public void updateBestLinereplyInit();
	
	/**
	 * 추천수 수정
	 * @param linereplyId
	 */
	public void updateRecommendCount(String linereplyId);
	
	/**
	 * itemid별 목록 모두 삭제
	 * @param itemId
	 */
	public void removeByItemId(String itemId);
	
	/**
	 * 토론 주제에 해당하는 토론의견 삭제
	 * @param discussionId
	 */
	public void removeByDiscussionId(String discussionId);
	
}
