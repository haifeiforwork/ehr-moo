/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition;

/**
 * 게시글 DAO
 * 
 * 게시글 IKEP4_BD_ITEM 테이블 CRUD와  목록조회등 DB와의 연동을 담당하는 클래스이다.
 *
 * @author ${user}
 * @version $$Id: AwardItemDao.java 16240 2011-08-18 04:08:15Z giljae $$
 */
public interface AwardItemDao extends GenericDao<AwardItem, String> {

	/**
	 * 게시글의 아이디 배열을 키로 해서 게시글을 조회한다.
	 *
	 * @param awardItemSearchCondition 게시글 검색조건
	 * @return 아이디 배열에 해당되는 게시글 목록
	 */
	List<AwardItem> listByItemIdArray(List<String> idList);

	/**
	 * 게시글 본인 포함 하위 쓰레드를 조회한다.
	 *
	 * @param id 게시글 아이디
	 * @return 본인 포함 하위 쓰레드 게시글
	 */
	List<AwardItem> listLowerItemThread(String id);

	/**
	 * 게시글 검색조건을 이용해서 게시글 목록을 조회한다.
	 *
	 * @param awardItemSearchCondition 게시글 검색조건
	 * @return 게시글 목록
	 */
	List<AwardItem> listBySearchCondition(AwardItemSearchCondition awardItemSearchCondition);
	List<AwardItem> listBySearchCondition2(AwardItemSearchCondition awardItemSearchCondition);
	List<AwardItem> listBySearchCondition3(AwardItemSearchCondition awardItemSearchCondition);
	
	List<AwardItem> deleteListBySearchCondition(AwardItemSearchCondition awardItemSearchCondition);


	/**
	 * 게시글의 상위, 하위 전체 쓰레드를 조회한다.
	 *
	 * @param id 게시글 ID
	 * @return 쓰레드 게시글 목록
	 */
	List<AwardItem> listReplayItemThreadByItemId(String id);

	/**
	 * 게시글 검색조건을 충족하는 게시글의 총 갯수를 구한다.
	 *
	 * @param awardItemSearchCondition 게시글 검색조건
	 * @return 게시글 갯수
	 */
	Integer countBySearchCondition(AwardItemSearchCondition awardItemSearchCondition);
	Integer countBySearchCondition2(AwardItemSearchCondition awardItemSearchCondition);
	Integer countBySearchCondition3(AwardItemSearchCondition awardItemSearchCondition);
	
	Integer deleteCountBySearchCondition(AwardItemSearchCondition awardItemSearchCondition);
	/**
	 * 게시글의 하위 답변 게시글의 갯수를 조회한다.
	 *
	 * @param id 조회 대상 게시글 ID
	 * @return 자식게시글(답변) 갯수
	 */
	Integer countChildren(String id);

	/**
	 * 게시글을 논리적으로 삭제한다.
	 *
	 * @param awardItem 삭제 대상 게시글
	 */
	void logicalDelete(AwardItem awardItem);

	/**
	 * 게시글을 삭제한다.
	 *
	 * @param id 삭제  대상 게시글 ID
	 */
	void physicalDelete(String id);
	
	void saveAwardItemMaterial(AwardItem awardItem);
	
	void deleteAwardItemMaterial(String itemId);
	
	List<String> materialList(String itemId);

	/**
	 * 게시글 쓰레드를 삭제한다.
	 *
	 * @param id 삭제 대상 게시글 ID
	 */
	void physicalDeleteThread(String id);

	/**
	 * 게시글의 추천 수를 증가시킨다.
	 * 
	 * @param id 업데이트 대상 게시글 ID
	 */
	void updateRecommendCount(String id);

	/**
	 * 게시글의 조회 수를 증가시킨다.
	 *
	 * @param id 업데이트 대상 게시글 ID
	 */
	void updateHitCount(String id);

	/**
	 * 게시글의 댓글 수를 증가 시킨다.
	 *
	 * @param id 업데이트 대상 게시글 ID
	 */
	void updateLinereplyCount(String itemId);

	/**
	 * 게시글 답글 수를 조회한다
	 *
	 * @param id 조회 대상 게시글 ID
	 */
	void updateReplyCount(String id);

	/**
	 * 스탭을 업데이트 한다.
	 *
	 * @param awardItem 업데이트 대상 게시글
	 */
	void updateStep(AwardItem awardItem);



	/**
	 * 전시 기간을 벗어난 삭제 목록을 파라미터 갯수만큼 가져온다.
	 * 
	 * @param getCount 가져와야 하는 삭제 목록
	 * 
	 * @return 삭제대상 게시글  목록
	 */
	List<AwardItem> listBatchDeletePassedAwardItem(Integer getCount);

	/**
	 * 게시물 상태가 2인 삭제 목록을 파라미터 갯수만큼 가져온다.
	 * 
	 * @param getCount 가져와야 하는 삭제 목록
	 * 
	 * @return 삭제대상 게시글  목록
	 */
	List<AwardItem> listBatchDeleteStatusAwardItem(Integer getCount);

	/**
	 * 특정 게시판에 최신에 등록된 게시글을 가져온다.
	 * 
	 * @param parameter  awardId 게시판 ID, count 글갯수
	 * @return
	 */
	List<AwardItem> listRecentAwardItem(AwardItemSearchCondition searchCondition);
	List<AwardItem> listRecentAwardItem2(AwardItemSearchCondition searchCondition);

	/**
	 * 최신에 등록된 게시물을 가져온다.
	 * 
	 * @param parameterMap
	 */
	List<AwardItem> listRecentAllAwardItem(Map<String, Object> parameter);

	/**
	 * 게시글 삭제 상태를 변경한다.
	 * 
	 * @param parameter itemId 게시글 ID, status 게시글 상태
	 */
	void updateItemDeleteField(Map<String, Object> parameter);


	/**
	 * 삭제 대상 게시 글을 가져온다
	 * 
	 * @param awardId 게시판ID
	 * @param getCount 가져와야 하는 갯수
	 * @return
	 */
	List<AwardItem> listByAwardItemOfDeletedAward(Map<String, Object> parameter);
	
	/**
	 * 메일수 수정
	 * @param itemId
	 */
	public void updateMailCount(String itemId);
	
	/**
	 * 블로그수 수정
	 * @param itemId
	 */
	public void updateMblogCount(String itemId);
	
	/**
	 * 게시물 이동
	 * @param map
	 */
	void updateAwardItemMove(Map<String, String> map); 

	List<AwardItem> listRecentAwardItemPortlet(Map<String, Object> parameter);
	
	/**
	 * 임시 저장 게시물 리스트 갯수
	 * @param awardItemSearchCondition
	 * @return
	 */
	public Integer countTempSaveBySearchCondition(AwardItemSearchCondition awardItemSearchCondition);
	/**
	 * 임시 저장 게시물 리스트
	 * @param awardItemSearchCondition
	 * @return
	 */
	public List<AwardItem> listTempSaveBySearchCondition(AwardItemSearchCondition awardItemSearchCondition);
	
	
	@SuppressWarnings("rawtypes")
	void updateMailSent(Set alarmIds);
	
	@SuppressWarnings("rawtypes")
	void updateMailSending(Set itemIds);
	
	int getMailWaitYn(String itemId);
	
	int getAwardItemCount(AwardItem awardItem);
	
	int getHappyAwardItemCount(AwardItem awardItem);
	
	public void updateMailWaitYn(String itemId);
	int getMailWaitTime();
	
	int getMailWaitCount();
	
	/**
	 * 이전 게시글의 정보를 조회한다.
	 *
	 * @param bordId 조회 대상 게시판 ID
	 * @param itemId 조회 대상 게시글 ID
	 * @param readFlag "prev"
	 * @return 이전 게시글의 정보
	 */
	AwardItem getPrevItem(String awardId, String itemId, String readFlag);
	
	/**
	 * 다음 게시글의 정보를 조회한다.
	 *
	 * @param bordId 조회 대상 게시판 ID
	 * @param itemId 조회 대상 게시글 ID
	 * @param readFlag "next"
	 * @return 다음 게시글의 정보
	 */
	AwardItem getNextItem(String awardId, String itemId, String readFlag);
	
	public Integer countAwardMenuList(AwardItemSearchCondition awardItemSearchCondition);
	
	public List<AwardItem> getAwardMenuList(AwardItemSearchCondition searchCondition);
	
	public List<AwardItem> getPresentAwardMenuList(String userId);
	
	public void deleteAwardMenuList(String userId);
	
	public void insertAwardMenuList(Map<String, Object> param);
	
	public List<AwardItem> listCode(String grpCd);
	
}
