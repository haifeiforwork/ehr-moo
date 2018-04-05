/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officeway.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.officeway.dao.OfficeWayDao;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWay;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWaySearchCondition;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Repository
public class OfficeWayDaoImpl extends GenericDaoSqlmap<OfficeWay, String> implements OfficeWayDao {

	String NAMESPACE = "lightpack.officeway.dao.officeway.";

	public void officewayUseRequest(OfficeWay officeway){
		sqlInsert(NAMESPACE + "officewayUseRequest", officeway);
	}
	
	public String getTeamLeader(String userId){
		return (String) sqlSelectForObject(NAMESPACE + "getTeamLeader", userId);
	}
	
	public boolean existsProductno(String productNo) {

		String count = (String) sqlSelectForObject(NAMESPACE + "checkProductNo", productNo);

		return !count.equals("0");
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
	
	public Integer selectMyRequestCount(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectMyRequestCount", searchCondition);
	}
	
	public List<Map<String, Object>> selectMyRequestAll(OfficeWaySearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectMyRequestAll", searchCondition);
	}
	
	public Integer selectTeamRequestCount(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamRequestCount", searchCondition);
	}
	
	public List<Map<String, Object>> selectTeamRequestAll(OfficeWaySearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectTeamRequestAll", searchCondition);
	}
	
	public Integer selectTeamsRequestCount(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamsRequestCount", searchCondition);
	}
	
	public Integer selectTeamsRequestPrice(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamsRequestPrice", searchCondition);
	}
	
	public Integer selectTeamRequestPrice1(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamRequestPrice1", searchCondition);
	}
	
	public Integer selectRequestUserPrice(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestUserPrice", searchCondition);
	}
	
	public Integer selectRequestTeamPrice(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestTeamPrice", searchCondition);
	}
	
	public Integer selectRequestMyPrice1(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestMyPrice1", searchCondition);
	}
	
	public Integer selectRequestMyPrice2(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestMyPrice2", searchCondition);
	}
	
	public Integer selectTeamRequestPrice2(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamRequestPrice2", searchCondition);
	}
	
	public int getTotalPrice(OfficeWay officeway){
		return (Integer) sqlSelectForObject(NAMESPACE + "getTotalPrice", officeway);
	}
	
	public List<Map<String, Object>> selectTeamsRequestAll(OfficeWaySearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectTeamsRequestAll", searchCondition);
	}
	
	public List<OfficeWay> teamsRequestDetailList(OfficeWaySearchCondition searchCondition){
		return sqlSelectForList(NAMESPACE + "teamsRequestDetailList", searchCondition);
	}
	
	public List<OfficeWay> teamRequestDetailList(OfficeWaySearchCondition searchCondition){
		return sqlSelectForList(NAMESPACE + "teamRequestDetailList", searchCondition);
	}
	
	public List<OfficeWay> getStatisticsList1(OfficeWay officeway){
		return sqlSelectForList(NAMESPACE + "getStatisticsList1", officeway);
	}
	
	public void officewayRequestDelete(String officewayId){
		sqlDelete(NAMESPACE + "officewayRequestDelete", officewayId);
	}
	
	public List<OfficeWay> getStatisticsList2(OfficeWay officeway){
		return sqlSelectForList(NAMESPACE + "getStatisticsList2", officeway);
	}
	
	public List<OfficeWay> getStatisticsList3(OfficeWay officeway){
		return sqlSelectForList(NAMESPACE + "getStatisticsList3", officeway);
	}
	
	public Integer selectRequestCount(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestCount", searchCondition);
	}
	
	public Integer selectRequestAllCount(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestAllCount", searchCondition);
	}
	
	public Integer selectExceptOfficewayCount(OfficeWaySearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectExceptOfficewayCount", searchCondition);
	}
	
	public Integer selectPeriodListCount(String year){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectPeriodListCount", year);
	}
	
	public List<Map<String, Object>> selectRequest(OfficeWaySearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectRequest", searchCondition);
	}
	
	public List<Map<String, Object>> selectRequestAll(OfficeWaySearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectRequestAll", searchCondition);
	}
	
	public List<Map<String, Object>> selectExceptOfficewayList(OfficeWaySearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectExceptOfficewayList", searchCondition);
	}
	
	public List<OfficeWay> exceptOfficewayAllList(){
		return sqlSelectForList(NAMESPACE + "exceptOfficewayAllList");
	}
	
	public List<OfficeWay> officewayUseTeamListAll(){
		return sqlSelectForList(NAMESPACE + "officewayUseTeamListAll");
	}
	
	public List<OfficeWay> officewayUseTeamList(String userId){
		return sqlSelectForList(NAMESPACE + "officewayUseTeamList", userId);
	}
	
	public OfficeWay getOfficeWayUseRequestView(String officewayId){
		return (OfficeWay) sqlSelectForObject(NAMESPACE + "getUseRequestView", officewayId);
	}
	
	public void officewayApproveUse(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "officewayApproveUse", officeway);
	}
	
	public void updateOfficewayExcept(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "updateOfficewayExcept", officeway);
	}
	
	public void createOfficewayExcept(OfficeWay officeway){
		sqlInsert(NAMESPACE + "createOfficewayExcept", officeway);
	}
	
	public void deleteOfficewayExcept(OfficeWay officeway){
		sqlDelete(NAMESPACE + "deleteOfficewayExcept", officeway);
	}
	
	public void officewayUseRequestUpdate(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "officewayUseRequestUpdate", officeway);
	}
	
	public void officewayUseRequestAdminUpdate(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "officewayUseRequestAdminUpdate", officeway);
	}
	
	public void officewayTeamAuthSave(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "officewayTeamAuthSave", officeway);
	}
	
	public void officewayCheckBoxUseRequest(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "officewayCheckBoxUseRequest", officeway);
	}
	
	public List<OfficeWay> listByItemIdArray(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByItemIdArray", map);
	}
	
	public List<OfficeWay> listByTeamItemIdArray(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByTeamItemIdArray", map);
	}
	
	public List<OfficeWay> listByTeamItemIdArray1(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByTeamItemIdArray1", map);
	}
	
	public List<OfficeWay> listByTeamItemIdArray2(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByTeamItemIdArray2", map);
	}
	
	public void officewayManageCheckBoxUseRequest(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "officewayManageCheckBoxUseRequest", officeway);
	}
	
	public List<OfficeWay> listCategory(OfficeWay categoryBoardId){
		return sqlSelectForList(NAMESPACE + "listCategory", categoryBoardId);
	}
	
	public void createCategoryNm(OfficeWay category) {
		this.sqlInsert(NAMESPACE+"createCategoryNm",category);
	}
	
	public void deleteCategoryNm(OfficeWay category) {
		this.sqlUpdate(NAMESPACE+"deleteCategoryNm",category);
	}
	
	public void updateCategoryNm(OfficeWay category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryNm",category);
	}
	
	public void updateCategoryAlign(OfficeWay category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryAlign",category);
	}
	
	public void officewayCheckBoxGroupRequest(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "officewayCheckBoxGroupRequest", officeway);
	}
	
	public void officewayUseRequestDelete(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "officewayUseRequestDelete", officeway);
	}
	
	public void savePeriod(OfficeWay officeway){
		sqlUpdate(NAMESPACE + "savePeriod", officeway);
	}
	
	public OfficeWay getPeriod(){
		return (OfficeWay) sqlSelectForObject(NAMESPACE + "getPeriod");
	}
	
	public OfficeWay periodInfo(){
		return (OfficeWay) sqlSelectForObject(NAMESPACE + "periodInfo");
	}
	
	public OfficeWay getOfficewayTeamAuthInfo(String teamId){
		return (OfficeWay) sqlSelectForObject(NAMESPACE + "getOfficewayTeamAuthInfo",teamId);
	}
	
	public List<OfficeWay> getPeriodList(String year){
		return sqlSelectForList(NAMESPACE + "getPeriodList", year);
	}
	
	public List<OfficeWay> getOfficewayTeamAuthList(){
		return sqlSelectForList(NAMESPACE + "getOfficewayTeamAuthList");
	}
	
	public void insertDefaultPeriod(String year){
		sqlInsert(NAMESPACE+"insertDefaultPeriod",year);
	}
	
	public List<OfficeWay> selectOfficeWayManager(String officewayadm){
		return sqlSelectForList(NAMESPACE + "selectOfficeWayManager", officewayadm);
	}

	public String create(OfficeWay arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public OfficeWay get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(OfficeWay arg0) {
		// TODO Auto-generated method stub
		
	}
}