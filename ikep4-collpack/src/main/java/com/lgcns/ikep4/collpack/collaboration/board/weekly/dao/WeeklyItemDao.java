package com.lgcns.ikep4.collpack.collaboration.board.weekly.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.collaboration.board.weekly.model.WeeklyItem;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.search.WeeklyItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface WeeklyItemDao extends GenericDao<WeeklyItem, String> {
	/**
	 * 주간 보고 목록 카운트
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(WeeklyItemSearchCondition searchCondition);

	/**
	 * 주간 보고 목록 조회
	 * @param searchCondition
	 * @return
	 */
	List<WeeklyItem> listBySearchCondition(WeeklyItemSearchCondition searchCondition);

	/**
	 * 주간 보고 정보 조회
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	WeeklyItem getWeeklyItem(String itemId, String workspaceId);

	/**
	 * 조회 Reference itemId 중복 확인
	 * @param userId
	 * @param itemId
	 * @return
	 */
	int getWeeklyReference(String userId, String itemId);

	/**
	 * 주간 보고 조회 카운트 변경
	 * @param itemId
	 */
	void updateWeeklyHitCount(String itemId);

	/**
	 * 조회 Reference 등록
	 * @param userId
	 * @param itemId
	 */
	void createWeeklyItemReference(String userId, String itemId);

	/** 
	 * 주간 보고 삭제
	 * @param itemId
	 */
	void removeWeeklyItem(String itemId);

	/**
	 * 주간 보고 Reference 삭제
	 * @param itemId
	 */
	void removeWeeklyReference(String itemId);
	
	/** 
	 * 주간 보고 본문 정보 조회
	 * @param weeklyItem
	 * @return
	 */
	List<WeeklyItem> getWeeklyItemContents(WeeklyItem weeklyItem);

	/**
	 * 주간 보고 Summary 정보 조회
	 * @param workspaceId
	 * @param weeklyTerm
	 * @return
	 */
	List<WeeklyItem> getSummaryWeeklyItems(String workspaceId, String weeklyTerm);

	/**
	 * 팀 하위 Workspace 개설 정보 조회
	 * @param searchCondition
	 * @return
	 */
	Integer countLowRankItemBySearchCondition(WeeklyItemSearchCondition searchCondition);

	/**
	 *  팀 하위 Workspace 개설 정보 목록
	 * @param searchCondition
	 * @return
	 */
	List<WeeklyItem> listLowRankBySearchCondition(WeeklyItemSearchCondition searchCondition);
	/**
	 * 주간 보고 주 체크
	 * @param map
	 * @return
	 */
	String checkWeeklyTerm(Map<String, String> map); 
	/**
	 * Workspace 삭제 배치 실행 - 주간보고 첨부가 존재하는 게시물 목록
	 * @param workspaceId
	 * @return
	 */
	public List<WeeklyItem> listDeleteWeeklyItemByWorkspace(String workspaceId);
}
