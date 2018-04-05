/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officesupplies.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSupplies;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSuppliesSearchCondition;

/**
 * 예약 합의 관련 Dao 구현체
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: OfficeSuppliesDao.java 16276 2011-08-18 07:09:07Z yruyo $
 */
public interface OfficeSuppliesDao extends GenericDao<OfficeSupplies, String> {

	public void officesuppliesUseRequest(OfficeSupplies officesupplies);
	
	public String getTeamLeader(String userId);
	
	public Integer selectMyRequestCount(OfficeSuppliesSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectMyRequestAll(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectTeamRequestCount(OfficeSuppliesSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectTeamRequestAll(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectTeamsRequestCount(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectTeamsRequestPrice(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectTeamRequestPrice1(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectRequestUserPrice(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectRequestTeamPrice(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectRequestMyPrice1(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectRequestMyPrice2(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectTeamRequestPrice2(OfficeSuppliesSearchCondition searchCondition);
	
	public int getTotalPrice(OfficeSupplies officesupplies);
	
	public List<Map<String, Object>> selectTeamsRequestAll(OfficeSuppliesSearchCondition searchCondition);
	
	public List<OfficeSupplies> teamsRequestDetailList(OfficeSuppliesSearchCondition searchCondition);
	
	public List<OfficeSupplies> teamRequestDetailList(OfficeSuppliesSearchCondition searchCondition);
	
	public List<OfficeSupplies> getStatisticsList1(OfficeSupplies officesupplies);
	
	public void officesuppliesRequestDelete(String officesuppliesId);
	
	public List<OfficeSupplies> getStatisticsList2(OfficeSupplies officesupplies);
	
	public List<OfficeSupplies> getStatisticsList3(OfficeSupplies officesupplies);
	
	public Integer selectRequestCount(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectRequestAllCount(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectExceptOfficesuppliesCount(OfficeSuppliesSearchCondition searchCondition);
	
	public Integer selectPeriodListCount(String year);
	
	public List<Map<String, Object>> selectRequest(OfficeSuppliesSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectRequestAll(OfficeSuppliesSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectExceptOfficesuppliesList(OfficeSuppliesSearchCondition searchCondition);
	
	public List<OfficeSupplies> exceptOfficesuppliesAllList();
	
	public List<OfficeSupplies> officesuppliesUseTeamListAll();
	
	public List<OfficeSupplies> officesuppliesUseTeamList(String userId);
	
	public OfficeSupplies getOfficeSuppliesUseRequestView(String officesuppliesId);
	
	public void officesuppliesApproveUse(OfficeSupplies officesupplies);
	
	public void updateOfficesuppliesExcept(OfficeSupplies officesupplies);
	
	public void createOfficesuppliesExcept(OfficeSupplies officesupplies);
	
	public void deleteOfficesuppliesExcept(OfficeSupplies officesupplies);
	
	public void officesuppliesUseRequestUpdate(OfficeSupplies officesupplies);
	
	public void officesuppliesTeamAuthSave(OfficeSupplies officesupplies);
	
	public boolean existsProductno(String productNo);
	
	public boolean periodCheck();
	
	public boolean teamManagerCheck(Map<String, String> map);
	
	public boolean teamLeaderCheck(Map<String, String> map);
	
	public void officesuppliesCheckBoxUseRequest(OfficeSupplies officesupplies);
	
	public List<OfficeSupplies> listByItemIdArray(Map<String, List<String>> map);
	
	public List<OfficeSupplies> listByTeamItemIdArray(Map<String, List<String>> map);
	
	public List<OfficeSupplies> listByTeamItemIdArray1(Map<String, List<String>> map);
	
	public List<OfficeSupplies> listByTeamItemIdArray2(Map<String, List<String>> map);
	
	public void officesuppliesManageCheckBoxUseRequest(OfficeSupplies officesupplies);
	
	public List<OfficeSupplies> listCategory(OfficeSupplies categoryBoardId);
	
	public void createCategoryNm(OfficeSupplies category);
	
	public void updateCategoryNm(OfficeSupplies category);
	
	public void deleteCategoryNm(OfficeSupplies category);
	
	public void updateCategoryAlign(OfficeSupplies category);
	
	public void officesuppliesCheckBoxGroupRequest(OfficeSupplies officesupplies);
	
	public void officesuppliesUseRequestDelete(OfficeSupplies officesupplies);
	
	public void savePeriod(OfficeSupplies officesupplies);
	
	public OfficeSupplies getPeriod();
	
	public OfficeSupplies getOfficesuppliesTeamAuthInfo(String teamId);
	
	public List<OfficeSupplies> getPeriodList(String year);
	
	public List<OfficeSupplies> getOfficesuppliesTeamAuthList();
	
	public void insertDefaultPeriod(String year);
	
	public List<OfficeSupplies> selectOfficeSuppliesManager(String officesuppliesadm);
}