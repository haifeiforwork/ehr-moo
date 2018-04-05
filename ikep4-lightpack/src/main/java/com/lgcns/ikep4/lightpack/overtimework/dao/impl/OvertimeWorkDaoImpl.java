/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.overtimework.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.overtimework.dao.OvertimeWorkDao;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWork;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWorkSearchCondition;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Repository
public class OvertimeWorkDaoImpl extends GenericDaoSqlmap<OvertimeWork, String> implements OvertimeWorkDao {

	String NAMESPACE = "lightpack.overtimework.dao.overtimework.";

	public void overtimeworkInOutRegist(OvertimeWork overtimework){
		sqlInsert(NAMESPACE + "overtimeworkInOutRegist", overtimework);
	}
	
	public void overtimeworkInOutRegistUpdate(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkInOutRegistUpdate", overtimework);
	}
	
	public void overtimeworkInOutUpdate(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkInOutUpdate", overtimework);
	}
	
	public String getTeamLeader(String userId){
		return (String) sqlSelectForObject(NAMESPACE + "getTeamLeader", userId);
	}
	
	public boolean existsProductno(String productNo) {

		String count = (String) sqlSelectForObject(NAMESPACE + "checkProductNo", productNo);

		return !count.equals("0");
	}
	
	public String cardUserId(String cardId){
		return (String) sqlSelectForObject(NAMESPACE + "getCardUserId", cardId);
	}
	
	public boolean periodCheck(){
		String count = (String) sqlSelectForObject(NAMESPACE + "periodCheck");
		return !count.equals("0");
	}
	
	public boolean teamManagerCheck(Map<String, String> map){
		String count = (String) sqlSelectForObject(NAMESPACE + "teamManagerCheck",map);
		return !count.equals("0");
	}
	
	public boolean teamLeaderCheck(Map<String, String> map){
		String count = (String) sqlSelectForObject(NAMESPACE + "teamLeaderCheck",map);
		return !count.equals("0");
	}
	
	public Integer selectMyRequestCount(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectMyRequestCount", searchCondition);
	}
	
	public List<Map<String, Object>> selectMyRequestAll(OvertimeWorkSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectMyRequestAll", searchCondition);
	}
	
	public Integer selectTeamRequestCount(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamRequestCount", searchCondition);
	}
	
	public Integer overtimeworkInOutAllListCount(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "overtimeworkInOutAllListCount", searchCondition);
	}
	
	public Integer overtimeworkInOutMyListCount(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "overtimeworkInOutMyListCount", searchCondition);
	}
	
	public List<Map<String, Object>> overtimeworkInOutAllList(OvertimeWorkSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "overtimeworkInOutAllList", searchCondition);
	}
	
	public List<Map<String, Object>> overtimeworkInOutMyList(OvertimeWorkSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "overtimeworkInOutMyList", searchCondition);
	}
	
	public List<Map<String, Object>> selectTeamRequestAll(OvertimeWorkSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectTeamRequestAll", searchCondition);
	}
	
	public Integer selectTeamsRequestCount(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamsRequestCount", searchCondition);
	}
	
	public Integer selectTeamsRequestPrice(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamsRequestPrice", searchCondition);
	}
	
	public Integer selectTeamRequestPrice1(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamRequestPrice1", searchCondition);
	}
	
	public Integer selectRequestUserPrice(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestUserPrice", searchCondition);
	}
	
	public Integer selectRequestTeamPrice(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestTeamPrice", searchCondition);
	}
	
	public Integer selectRequestMyPrice1(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestMyPrice1", searchCondition);
	}
	
	public Integer selectRequestMyPrice2(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestMyPrice2", searchCondition);
	}
	
	public Integer selectTeamRequestPrice2(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamRequestPrice2", searchCondition);
	}
	
	public int getTotalPrice(OvertimeWork overtimework){
		return (Integer) sqlSelectForObject(NAMESPACE + "getTotalPrice", overtimework);
	}
	
	public List<Map<String, Object>> selectTeamsRequestAll(OvertimeWorkSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectTeamsRequestAll", searchCondition);
	}
	
	public List<OvertimeWork> teamsRequestDetailList(OvertimeWorkSearchCondition searchCondition){
		return sqlSelectForList(NAMESPACE + "teamsRequestDetailList", searchCondition);
	}
	
	public List<OvertimeWork> teamRequestDetailList(OvertimeWorkSearchCondition searchCondition){
		return sqlSelectForList(NAMESPACE + "teamRequestDetailList", searchCondition);
	}
	
	public List<OvertimeWork> getStatisticsList1(OvertimeWork overtimework){
		return sqlSelectForList(NAMESPACE + "getStatisticsList1", overtimework);
	}
	
	public void overtimeworkRequestDelete(String overtimeworkId){
		sqlDelete(NAMESPACE + "overtimeworkRequestDelete", overtimeworkId);
	}
	
	public List<OvertimeWork> getStatisticsList2(OvertimeWork overtimework){
		return sqlSelectForList(NAMESPACE + "getStatisticsList2", overtimework);
	}
	
	public List<OvertimeWork> getStatisticsList3(OvertimeWork overtimework){
		return sqlSelectForList(NAMESPACE + "getStatisticsList3", overtimework);
	}
	
	public Integer selectRequestCount(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestCount", searchCondition);
	}
	
	public Integer selectRequestAllCount(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestAllCount", searchCondition);
	}
	
	public Integer selectExceptOvertimeworkCount(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectExceptOvertimeworkCount", searchCondition);
	}
	
	public Integer selectPeriodListCount(String year){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectPeriodListCount", year);
	}
	
	public List<Map<String, Object>> selectRequest(OvertimeWorkSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectRequest", searchCondition);
	}
	
	public List<Map<String, Object>> selectRequestAll(OvertimeWorkSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectRequestAll", searchCondition);
	}
	
	public List<Map<String, Object>> selectExceptOvertimeworkList(OvertimeWorkSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectExceptOvertimeworkList", searchCondition);
	}
	
	public List<OvertimeWork> exceptOvertimeworkAllList(){
		return sqlSelectForList(NAMESPACE + "exceptOvertimeworkAllList");
	}
	
	public List<OvertimeWork> overtimeworkInOutExcelList(OvertimeWorkSearchCondition searchCondition){
		return sqlSelectForList(NAMESPACE + "overtimeworkInOutExcelList", searchCondition);
	}
	
	public List<OvertimeWork> overtimeworkUseTeamListAll(){
		return sqlSelectForList(NAMESPACE + "overtimeworkUseTeamListAll");
	}
	
	public List<OvertimeWork> overtimeworkUseTeamList(String userId){
		return sqlSelectForList(NAMESPACE + "overtimeworkUseTeamList", userId);
	}
	
	public OvertimeWork getOvertimeWorkUseRequestView(String overtimeworkId){
		return (OvertimeWork) sqlSelectForObject(NAMESPACE + "getUseRequestView", overtimeworkId);
	}
	
	public void overtimeworkApproveUse(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkApproveUse", overtimework);
	}
	
	public void updateOvertimeworkExcept(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "updateOvertimeworkExcept", overtimework);
	}
	
	public void createOvertimeworkExcept(OvertimeWork overtimework){
		sqlInsert(NAMESPACE + "createOvertimeworkExcept", overtimework);
	}
	
	public void deleteOvertimeworkExcept(OvertimeWork overtimework){
		sqlDelete(NAMESPACE + "deleteOvertimeworkExcept", overtimework);
	}
	
	public void overtimeworkUseRequestUpdate(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkUseRequestUpdate", overtimework);
	}
	
	public void overtimeworkTeamAuthSave(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkTeamAuthSave", overtimework);
	}
	
	public void overtimeworkUserCardSave(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkUserCardSave", overtimework);
	}
	
	public void overtimeworkCheckBoxUseRequest(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkCheckBoxUseRequest", overtimework);
	}
	
	public List<OvertimeWork> listByItemIdArray(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByItemIdArray", map);
	}
	
	public List<OvertimeWork> listByTeamItemIdArray(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByTeamItemIdArray", map);
	}
	
	public List<OvertimeWork> listByTeamItemIdArray1(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByTeamItemIdArray1", map);
	}
	
	public List<OvertimeWork> listByTeamItemIdArray2(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByTeamItemIdArray2", map);
	}
	
	public void overtimeworkManageCheckBoxUseRequest(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkManageCheckBoxUseRequest", overtimework);
	}
	
	public List<OvertimeWork> listCategory(OvertimeWork categoryBoardId){
		return sqlSelectForList(NAMESPACE + "listCategory", categoryBoardId);
	}
	
	public void createCategoryNm(OvertimeWork category) {
		this.sqlInsert(NAMESPACE+"createCategoryNm",category);
	}
	
	public void deleteCategoryNm(OvertimeWork category) {
		this.sqlUpdate(NAMESPACE+"deleteCategoryNm",category);
	}
	
	public void updateCategoryNm(OvertimeWork category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryNm",category);
	}
	
	public void updateCategoryAlign(OvertimeWork category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryAlign",category);
	}
	
	public void overtimeworkCheckBoxGroupRequest(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkCheckBoxGroupRequest", overtimework);
	}
	
	public void overtimeworkUseRequestDelete(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "overtimeworkUseRequestDelete", overtimework);
	}
	
	public void savePeriod(OvertimeWork overtimework){
		sqlUpdate(NAMESPACE + "savePeriod", overtimework);
	}
	
	public OvertimeWork getPeriod(){
		return (OvertimeWork) sqlSelectForObject(NAMESPACE + "getPeriod");
	}
	
	public OvertimeWork getOvertimeworkTeamAuthInfo(String teamId){
		return (OvertimeWork) sqlSelectForObject(NAMESPACE + "getOvertimeworkTeamAuthInfo",teamId);
	}
	
	public OvertimeWork overtimeworkInOutDetail(String overtimeworkId){
		return (OvertimeWork) sqlSelectForObject(NAMESPACE + "overtimeworkInOutDetail",overtimeworkId);
	}
	
	public OvertimeWork getOvertimeworkUserCardInfo(String userId){
		return (OvertimeWork) sqlSelectForObject(NAMESPACE + "getOvertimeworkUserCardInfo",userId);
	}
	
	public List<OvertimeWork> getPeriodList(String year){
		return sqlSelectForList(NAMESPACE + "getPeriodList", year);
	}
	
	public List<OvertimeWork> getOvertimeworkTeamAuthList(){
		return sqlSelectForList(NAMESPACE + "getOvertimeworkTeamAuthList");
	}
	
	public List<Map<String, Object>> getOvertimeworkUserCardList(OvertimeWorkSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "getOvertimeworkUserCardList", searchCondition);
	}
	
	public List<OvertimeWork> getOvertimeworkUserCardListExcel(OvertimeWorkSearchCondition searchCondition){
		return sqlSelectForList(NAMESPACE + "getOvertimeworkUserCardList",searchCondition);
	}
	
	public Integer selectOvertimeworkUserCardListCount(OvertimeWorkSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectOvertimeworkUserCardListCount", searchCondition);
	}
	
	public void insertDefaultPeriod(String year){
		sqlInsert(NAMESPACE+"insertDefaultPeriod",year);
	}
	
	public List<OvertimeWork> selectOvertimeWorkManager(String overtimeworkadm){
		return sqlSelectForList(NAMESPACE + "selectOvertimeWorkManager", overtimeworkadm);
	}

	public String create(OvertimeWork arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public OvertimeWork get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(OvertimeWork arg0) {
		// TODO Auto-generated method stub
		
	}
}