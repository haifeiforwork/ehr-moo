package com.lgcns.ikep4.collpack.collaboration.board.weekly.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.board.weekly.dao.WeeklyItemDao;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.model.WeeklyItem;
import com.lgcns.ikep4.collpack.collaboration.board.weekly.search.WeeklyItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository
public class WeeklyItemDaoImpl extends GenericDaoSqlmap<WeeklyItem, String> implements WeeklyItemDao {
	private static final String NAMESPACE = "collpack.collaboration.board.weekly.dao.weeklyItem.";

	public WeeklyItem get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * 주간보고 등록
	 */
	public String create(WeeklyItem weeklyItem) {
		this.sqlInsert(NAMESPACE + "create", weeklyItem);
		return weeklyItem.getItemId();
	}
	/**
	 * 주간보고 수정
	 */
	public void update(WeeklyItem object) {
		this.sqlInsert(NAMESPACE + "update", object); 
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	/*
	 * 주간보고 게시판 목록 View 카운트
	 * @see com.lgcns.ikep4.collpack.collaboration.board.weekly.dao.WeeklyItemDao#countBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board.weekly.search.WeeklyItemSearchCondition)
	 */
	public Integer countBySearchCondition(WeeklyItemSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);

	}
	/*
	 * 하위Coll. 주간보고 게시판 List View 카운트
	 * @see com.lgcns.ikep4.collpack.collaboration.board.weekly.dao.WeeklyItemDao#countLowRankItemBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board.weekly.search.WeeklyItemSearchCondition)
	 */
	public Integer countLowRankItemBySearchCondition(WeeklyItemSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countLowRankBySearchCondition", searchCondition);

	}
	
	/*
	 * 주간보고 게시판 목록 View 리스트
	 * @see com.lgcns.ikep4.collpack.collaboration.board.weekly.dao.WeeklyItemDao#listBySearchCondition(com.lgcns.ikep4.collpack.collaboration.board.weekly.search.WeeklyItemSearchCondition)
	 */
	public List<WeeklyItem> listBySearchCondition(WeeklyItemSearchCondition searchCondition) {
		return (List<WeeklyItem>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);

	}
	/**
	 * 주간보고 조회
	 */
	public WeeklyItem getWeeklyItem(String itemId, String workspaceId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("itemId", itemId);
		param.put("workspaceId", workspaceId);
		return (WeeklyItem)this.sqlSelectForObject(NAMESPACE + "getWeeklyItem", param);
		
	}
	/**
	 * 주간보고 조회 이력 정보
	 */
	public int getWeeklyReference(String userId, String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countWeeklyReference", param);

	}
	/**
	 * 주간정보 조회 정보 수정
	 */
	public void updateWeeklyHitCount(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateWeeklyHitCount", itemId);  
	}
	/**
	 * 주간보고 조회 이력 정보 등록
	 */
	public void createWeeklyItemReference(String userId, String itemId) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("userId", userId);
		param.put("itemId", itemId);
		this.sqlInsert(NAMESPACE + "createWeeklyReference", param);
	}

	/**
	 * 주간보고 삭제
	 */
	public void removeWeeklyItem(String itemId) {
		this.sqlDelete(NAMESPACE + "removeWeeklyItem", itemId);
	}
	/**
	 * 주간보고 조회 이력 삭제
	 */
	public void removeWeeklyReference(String itemId) {
		this.sqlDelete(NAMESPACE + "removeWeeklyReference", itemId);
	}
	/**
	 * 주간보고 본문내용 조회
	 */
	public List<WeeklyItem> getWeeklyItemContents(WeeklyItem weeklyItem) {
		return (List<WeeklyItem>)this.sqlSelectForList(NAMESPACE + "getWeeklyItemContents", weeklyItem);

	}
	/**
	 * 주간보고 취합 정보 조회
	 */
	public List<WeeklyItem> getSummaryWeeklyItems(String workspaceId, String weeklyTerm) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("workspaceId", workspaceId);
		param.put("weeklyTerm", weeklyTerm);
		
		return (List<WeeklyItem>)this.sqlSelectForList(NAMESPACE + "getSummaryWeeklyItems", param);

	}
	/**
	 * 주간 보고 하위 취합 정보 목록
	 */
	public List<WeeklyItem> listLowRankBySearchCondition(WeeklyItemSearchCondition searchCondition) {
		return (List<WeeklyItem>)this.sqlSelectForList(NAMESPACE + "listLowRankBySearchCondition", searchCondition);
	}
	/**
	 * 주간보고 존재유무 확인
	 */
	public String checkWeeklyTerm(Map<String, String> map) {
		return (String) this.sqlSelectForObject(NAMESPACE + "checkWeeklyTerm", map);

		
	}
	/**
	 * 주간보고 삭제
	 */
	public List<WeeklyItem> listDeleteWeeklyItemByWorkspace(String workspaceId) {
		return (List<WeeklyItem>)this.sqlSelectForList(NAMESPACE + "listDeleteWeeklyItemByWorkspace", workspaceId);
	}	
}
