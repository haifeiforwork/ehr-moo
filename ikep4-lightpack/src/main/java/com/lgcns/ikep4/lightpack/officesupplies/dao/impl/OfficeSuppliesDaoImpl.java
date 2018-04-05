/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officesupplies.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.officesupplies.dao.OfficeSuppliesDao;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSupplies;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSuppliesSearchCondition;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Repository
public class OfficeSuppliesDaoImpl extends GenericDaoSqlmap<OfficeSupplies, String> implements OfficeSuppliesDao {

	String NAMESPACE = "lightpack.officesupplies.dao.officesupplies.";

	public void officesuppliesUseRequest(OfficeSupplies officesupplies){
		sqlInsert(NAMESPACE + "officesuppliesUseRequest", officesupplies);
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
	
	public Integer selectMyRequestCount(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectMyRequestCount", searchCondition);
	}
	
	public List<Map<String, Object>> selectMyRequestAll(OfficeSuppliesSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectMyRequestAll", searchCondition);
	}
	
	public Integer selectTeamRequestCount(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamRequestCount", searchCondition);
	}
	
	public List<Map<String, Object>> selectTeamRequestAll(OfficeSuppliesSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectTeamRequestAll", searchCondition);
	}
	
	public Integer selectTeamsRequestCount(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamsRequestCount", searchCondition);
	}
	
	public Integer selectTeamsRequestPrice(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamsRequestPrice", searchCondition);
	}
	
	public Integer selectTeamRequestPrice1(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamRequestPrice1", searchCondition);
	}
	
	public Integer selectRequestUserPrice(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestUserPrice", searchCondition);
	}
	
	public Integer selectRequestTeamPrice(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestTeamPrice", searchCondition);
	}
	
	public Integer selectRequestMyPrice1(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestMyPrice1", searchCondition);
	}
	
	public Integer selectRequestMyPrice2(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestMyPrice2", searchCondition);
	}
	
	public Integer selectTeamRequestPrice2(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectTeamRequestPrice2", searchCondition);
	}
	
	public int getTotalPrice(OfficeSupplies officesupplies){
		return (Integer) sqlSelectForObject(NAMESPACE + "getTotalPrice", officesupplies);
	}
	
	public List<Map<String, Object>> selectTeamsRequestAll(OfficeSuppliesSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectTeamsRequestAll", searchCondition);
	}
	
	public List<OfficeSupplies> teamsRequestDetailList(OfficeSuppliesSearchCondition searchCondition){
		return sqlSelectForList(NAMESPACE + "teamsRequestDetailList", searchCondition);
	}
	
	public List<OfficeSupplies> teamRequestDetailList(OfficeSuppliesSearchCondition searchCondition){
		return sqlSelectForList(NAMESPACE + "teamRequestDetailList", searchCondition);
	}
	
	public List<OfficeSupplies> getStatisticsList1(OfficeSupplies officesupplies){
		return sqlSelectForList(NAMESPACE + "getStatisticsList1", officesupplies);
	}
	
	public void officesuppliesRequestDelete(String officesuppliesId){
		sqlDelete(NAMESPACE + "officesuppliesRequestDelete", officesuppliesId);
	}
	
	public List<OfficeSupplies> getStatisticsList2(OfficeSupplies officesupplies){
		return sqlSelectForList(NAMESPACE + "getStatisticsList2", officesupplies);
	}
	
	public List<OfficeSupplies> getStatisticsList3(OfficeSupplies officesupplies){
		return sqlSelectForList(NAMESPACE + "getStatisticsList3", officesupplies);
	}
	
	public Integer selectRequestCount(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestCount", searchCondition);
	}
	
	public Integer selectRequestAllCount(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestAllCount", searchCondition);
	}
	
	public Integer selectExceptOfficesuppliesCount(OfficeSuppliesSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectExceptOfficesuppliesCount", searchCondition);
	}
	
	public Integer selectPeriodListCount(String year){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectPeriodListCount", year);
	}
	
	public List<Map<String, Object>> selectRequest(OfficeSuppliesSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectRequest", searchCondition);
	}
	
	public List<Map<String, Object>> selectRequestAll(OfficeSuppliesSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectRequestAll", searchCondition);
	}
	
	public List<Map<String, Object>> selectExceptOfficesuppliesList(OfficeSuppliesSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectExceptOfficesuppliesList", searchCondition);
	}
	
	public List<OfficeSupplies> exceptOfficesuppliesAllList(){
		return sqlSelectForList(NAMESPACE + "exceptOfficesuppliesAllList");
	}
	
	public List<OfficeSupplies> officesuppliesUseTeamListAll(){
		return sqlSelectForList(NAMESPACE + "officesuppliesUseTeamListAll");
	}
	
	public List<OfficeSupplies> officesuppliesUseTeamList(String userId){
		return sqlSelectForList(NAMESPACE + "officesuppliesUseTeamList", userId);
	}
	
	public OfficeSupplies getOfficeSuppliesUseRequestView(String officesuppliesId){
		return (OfficeSupplies) sqlSelectForObject(NAMESPACE + "getUseRequestView", officesuppliesId);
	}
	
	public void officesuppliesApproveUse(OfficeSupplies officesupplies){
		sqlUpdate(NAMESPACE + "officesuppliesApproveUse", officesupplies);
	}
	
	public void updateOfficesuppliesExcept(OfficeSupplies officesupplies){
		sqlUpdate(NAMESPACE + "updateOfficesuppliesExcept", officesupplies);
	}
	
	public void createOfficesuppliesExcept(OfficeSupplies officesupplies){
		sqlInsert(NAMESPACE + "createOfficesuppliesExcept", officesupplies);
	}
	
	public void deleteOfficesuppliesExcept(OfficeSupplies officesupplies){
		sqlDelete(NAMESPACE + "deleteOfficesuppliesExcept", officesupplies);
	}
	
	public void officesuppliesUseRequestUpdate(OfficeSupplies officesupplies){
		sqlUpdate(NAMESPACE + "officesuppliesUseRequestUpdate", officesupplies);
	}
	
	public void officesuppliesTeamAuthSave(OfficeSupplies officesupplies){
		sqlUpdate(NAMESPACE + "officesuppliesTeamAuthSave", officesupplies);
	}
	
	public void officesuppliesCheckBoxUseRequest(OfficeSupplies officesupplies){
		sqlUpdate(NAMESPACE + "officesuppliesCheckBoxUseRequest", officesupplies);
	}
	
	public List<OfficeSupplies> listByItemIdArray(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByItemIdArray", map);
	}
	
	public List<OfficeSupplies> listByTeamItemIdArray(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByTeamItemIdArray", map);
	}
	
	public List<OfficeSupplies> listByTeamItemIdArray1(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByTeamItemIdArray1", map);
	}
	
	public List<OfficeSupplies> listByTeamItemIdArray2(Map<String, List<String>> map) {
		return this.sqlSelectForList(NAMESPACE + "listByTeamItemIdArray2", map);
	}
	
	public void officesuppliesManageCheckBoxUseRequest(OfficeSupplies officesupplies){
		sqlUpdate(NAMESPACE + "officesuppliesManageCheckBoxUseRequest", officesupplies);
	}
	
	public List<OfficeSupplies> listCategory(OfficeSupplies categoryBoardId){
		return sqlSelectForList(NAMESPACE + "listCategory", categoryBoardId);
	}
	
	public void createCategoryNm(OfficeSupplies category) {
		this.sqlInsert(NAMESPACE+"createCategoryNm",category);
	}
	
	public void deleteCategoryNm(OfficeSupplies category) {
		this.sqlUpdate(NAMESPACE+"deleteCategoryNm",category);
	}
	
	public void updateCategoryNm(OfficeSupplies category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryNm",category);
	}
	
	public void updateCategoryAlign(OfficeSupplies category) {
		this.sqlUpdate(NAMESPACE+"updateCategoryAlign",category);
	}
	
	public void officesuppliesCheckBoxGroupRequest(OfficeSupplies officesupplies){
		sqlUpdate(NAMESPACE + "officesuppliesCheckBoxGroupRequest", officesupplies);
	}
	
	public void officesuppliesUseRequestDelete(OfficeSupplies officesupplies){
		sqlUpdate(NAMESPACE + "officesuppliesUseRequestDelete", officesupplies);
	}
	
	public void savePeriod(OfficeSupplies officesupplies){
		sqlUpdate(NAMESPACE + "savePeriod", officesupplies);
	}
	
	public OfficeSupplies getPeriod(){
		return (OfficeSupplies) sqlSelectForObject(NAMESPACE + "getPeriod");
	}
	
	public OfficeSupplies getOfficesuppliesTeamAuthInfo(String teamId){
		return (OfficeSupplies) sqlSelectForObject(NAMESPACE + "getOfficesuppliesTeamAuthInfo",teamId);
	}
	
	public List<OfficeSupplies> getPeriodList(String year){
		return sqlSelectForList(NAMESPACE + "getPeriodList", year);
	}
	
	public List<OfficeSupplies> getOfficesuppliesTeamAuthList(){
		return sqlSelectForList(NAMESPACE + "getOfficesuppliesTeamAuthList");
	}
	
	public void insertDefaultPeriod(String year){
		sqlInsert(NAMESPACE+"insertDefaultPeriod",year);
	}
	
	public List<OfficeSupplies> selectOfficeSuppliesManager(String officesuppliesadm){
		return sqlSelectForList(NAMESPACE + "selectOfficeSuppliesManager", officesuppliesadm);
	}

	public String create(OfficeSupplies arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public OfficeSupplies get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(OfficeSupplies arg0) {
		// TODO Auto-generated method stub
		
	}
}