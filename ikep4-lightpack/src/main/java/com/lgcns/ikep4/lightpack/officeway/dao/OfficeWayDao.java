/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officeway.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWay;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWaySearchCondition;

/**
 * 예약 합의 관련 Dao 구현체
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: OfficeWayDao.java 16276 2011-08-18 07:09:07Z yruyo $
 */
public interface OfficeWayDao extends GenericDao<OfficeWay, String> {

	public void officewayUseRequest(OfficeWay officeway);
	
	public String getTeamLeader(String userId);
	
	public Integer selectMyRequestCount(OfficeWaySearchCondition searchCondition);
	
	public List<Map<String, Object>> selectMyRequestAll(OfficeWaySearchCondition searchCondition);
	
	public Integer selectTeamRequestCount(OfficeWaySearchCondition searchCondition);
	
	public List<Map<String, Object>> selectTeamRequestAll(OfficeWaySearchCondition searchCondition);
	
	public Integer selectTeamsRequestCount(OfficeWaySearchCondition searchCondition);
	
	public Integer selectTeamsRequestPrice(OfficeWaySearchCondition searchCondition);
	
	public Integer selectTeamRequestPrice1(OfficeWaySearchCondition searchCondition);
	
	public Integer selectRequestUserPrice(OfficeWaySearchCondition searchCondition);
	
	public Integer selectRequestTeamPrice(OfficeWaySearchCondition searchCondition);
	
	public Integer selectRequestMyPrice1(OfficeWaySearchCondition searchCondition);
	
	public Integer selectRequestMyPrice2(OfficeWaySearchCondition searchCondition);
	
	public Integer selectTeamRequestPrice2(OfficeWaySearchCondition searchCondition);
	
	public int getTotalPrice(OfficeWay officeway);
	
	public List<Map<String, Object>> selectTeamsRequestAll(OfficeWaySearchCondition searchCondition);
	
	public List<OfficeWay> teamsRequestDetailList(OfficeWaySearchCondition searchCondition);
	
	public List<OfficeWay> teamRequestDetailList(OfficeWaySearchCondition searchCondition);
	
	public List<OfficeWay> getStatisticsList1(OfficeWay officeway);
	
	public void officewayRequestDelete(String officewayId);
	
	public List<OfficeWay> getStatisticsList2(OfficeWay officeway);
	
	public List<OfficeWay> getStatisticsList3(OfficeWay officeway);
	
	public Integer selectRequestCount(OfficeWaySearchCondition searchCondition);
	
	public Integer selectRequestAllCount(OfficeWaySearchCondition searchCondition);
	
	public Integer selectExceptOfficewayCount(OfficeWaySearchCondition searchCondition);
	
	public Integer selectPeriodListCount(String year);
	
	public List<Map<String, Object>> selectRequest(OfficeWaySearchCondition searchCondition);
	
	public List<Map<String, Object>> selectRequestAll(OfficeWaySearchCondition searchCondition);
	
	public List<Map<String, Object>> selectExceptOfficewayList(OfficeWaySearchCondition searchCondition);
	
	public List<OfficeWay> exceptOfficewayAllList();
	
	public List<OfficeWay> officewayUseTeamListAll();
	
	public List<OfficeWay> officewayUseTeamList(String userId);
	
	public OfficeWay getOfficeWayUseRequestView(String officewayId);
	
	public void officewayApproveUse(OfficeWay officeway);
	
	public void updateOfficewayExcept(OfficeWay officeway);
	
	public void createOfficewayExcept(OfficeWay officeway);
	
	public void deleteOfficewayExcept(OfficeWay officeway);
	
	public void officewayUseRequestUpdate(OfficeWay officeway);
	
	public void officewayUseRequestAdminUpdate(OfficeWay officeway);
	
	public void officewayTeamAuthSave(OfficeWay officeway);
	
	public boolean existsProductno(String productNo);
	
	public boolean periodCheck();
	
	public boolean teamManagerCheck(Map<String, String> map);
	
	public boolean teamLeaderCheck(Map<String, String> map);
	
	public void officewayCheckBoxUseRequest(OfficeWay officeway);
	
	public List<OfficeWay> listByItemIdArray(Map<String, List<String>> map);
	
	public List<OfficeWay> listByTeamItemIdArray(Map<String, List<String>> map);
	
	public List<OfficeWay> listByTeamItemIdArray1(Map<String, List<String>> map);
	
	public List<OfficeWay> listByTeamItemIdArray2(Map<String, List<String>> map);
	
	public void officewayManageCheckBoxUseRequest(OfficeWay officeway);
	
	public List<OfficeWay> listCategory(OfficeWay categoryBoardId);
	
	public void createCategoryNm(OfficeWay category);
	
	public void updateCategoryNm(OfficeWay category);
	
	public void deleteCategoryNm(OfficeWay category);
	
	public void updateCategoryAlign(OfficeWay category);
	
	public void officewayCheckBoxGroupRequest(OfficeWay officeway);
	
	public void officewayUseRequestDelete(OfficeWay officeway);
	
	public void savePeriod(OfficeWay officeway);
	
	public OfficeWay getPeriod();
	
	public OfficeWay periodInfo();
	
	public OfficeWay getOfficewayTeamAuthInfo(String teamId);
	
	public List<OfficeWay> getPeriodList(String year);
	
	public List<OfficeWay> getOfficewayTeamAuthList();
	
	public void insertDefaultPeriod(String year);
	
	public List<OfficeWay> selectOfficeWayManager(String officewayadm);
}