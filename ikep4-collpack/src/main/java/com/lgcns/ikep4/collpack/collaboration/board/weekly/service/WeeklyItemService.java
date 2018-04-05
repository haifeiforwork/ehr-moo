package com.lgcns.ikep4.collpack.collaboration.board.weekly.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.model.WeeklyItem;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.search.WeeklyItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.support.user.member.model.User;


@Transactional
public interface WeeklyItemService {

	/**
	 * 주간 보고 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	SearchResult<WeeklyItem> listWeeklyItemBySearchCondition(WeeklyItemSearchCondition searchCondition);

	/**
	 * 주간보고 등록
	 * 
	 * @param weeklyItem
	 * @param user
	 * @return
	 */
	String createWeeklyItem(WeeklyItem weeklyItem, User user);

	/**
	 * 주간 보고 조회
	 * 
	 * @param userId
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
	WeeklyItem readWeeklyItem(String userId, String itemId, String workspaceId);

	/**
	 * 주간보고 삭제
	 * 
	 * @param weeklyItem
	 */
	void deleteWeeklyItem(WeeklyItem weeklyItem);

	/**
	 * 주간 보고 수정
	 * 
	 * @param weeklyItem
	 * @param user
	 */
	void updateWeeklyItem(WeeklyItem weeklyItem, User user);

	/**
	 * 주간보고 본문내용 조회
	 * 
	 * @param weeklyItem
	 * @return
	 */
	List<WeeklyItem> readWeeklyItemContents(WeeklyItem weeklyItem);

	/**
	 * 해당 WS에 하위 Workspace 목록 조회
	 * 
	 * @param searchCondition
	 * @return
	 */
	SearchResult<WeeklyItem> listLowRankWeeklyItemBySearchCondition(WeeklyItemSearchCondition searchCondition);

	/**
	 * Workspace 정보 조회
	 * 
	 * @param portalId
	 * @param workspaceId
	 * @return
	 */
	Workspace getWorkspaceInfo(String portalId, String workspaceId);

	/**
	 * 해당 기간에 주간보고 존재유무
	 * 
	 * @param map
	 * @return
	 */
	String checkWeeklyTerm(Map<String, String> map);

	/**
	 * Workspace 삭제 배치 실행 - 주간보고중 게시물에 첨부가 존재하는 게시물 목록
	 * 
	 * @param workspaceId
	 * @return
	 */
	public List<WeeklyItem> listDeleteWeeklyItemByWorkspace(String workspaceId);
}
