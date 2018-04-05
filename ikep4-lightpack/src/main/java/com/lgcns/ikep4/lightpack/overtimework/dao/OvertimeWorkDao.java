/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.overtimework.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWork;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWorkSearchCondition;

/**
 * 예약 합의 관련 Dao 구현체
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: OvertimeWorkDao.java 16276 2011-08-18 07:09:07Z yruyo $
 */
public interface OvertimeWorkDao extends GenericDao<OvertimeWork, String> {

	public void overtimeworkInOutRegist(OvertimeWork overtimework);
	
	public void overtimeworkInOutRegistUpdate(OvertimeWork overtimework);
	
	public void overtimeworkInOutUpdate(OvertimeWork overtimework);
	
	public String getTeamLeader(String userId);
	
	public Integer selectMyRequestCount(OvertimeWorkSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectMyRequestAll(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectTeamRequestCount(OvertimeWorkSearchCondition searchCondition);
	
	public Integer overtimeworkInOutAllListCount(OvertimeWorkSearchCondition searchCondition);
	
	public Integer overtimeworkInOutMyListCount(OvertimeWorkSearchCondition searchCondition);
	
	public List<Map<String, Object>> overtimeworkInOutAllList(OvertimeWorkSearchCondition searchCondition);
	
	public List<Map<String, Object>> overtimeworkInOutMyList(OvertimeWorkSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectTeamRequestAll(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectTeamsRequestCount(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectTeamsRequestPrice(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectTeamRequestPrice1(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectRequestUserPrice(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectRequestTeamPrice(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectRequestMyPrice1(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectRequestMyPrice2(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectTeamRequestPrice2(OvertimeWorkSearchCondition searchCondition);
	
	public int getTotalPrice(OvertimeWork overtimework);
	
	public List<Map<String, Object>> selectTeamsRequestAll(OvertimeWorkSearchCondition searchCondition);
	
	public List<OvertimeWork> teamsRequestDetailList(OvertimeWorkSearchCondition searchCondition);
	
	public List<OvertimeWork> teamRequestDetailList(OvertimeWorkSearchCondition searchCondition);
	
	public List<OvertimeWork> getStatisticsList1(OvertimeWork overtimework);
	
	public void overtimeworkRequestDelete(String overtimeworkId);
	
	public List<OvertimeWork> getStatisticsList2(OvertimeWork overtimework);
	
	public List<OvertimeWork> getStatisticsList3(OvertimeWork overtimework);
	
	public Integer selectRequestCount(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectRequestAllCount(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectExceptOvertimeworkCount(OvertimeWorkSearchCondition searchCondition);
	
	public Integer selectPeriodListCount(String year);
	
	public Integer selectOvertimeworkUserCardListCount(OvertimeWorkSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectRequest(OvertimeWorkSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectRequestAll(OvertimeWorkSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectExceptOvertimeworkList(OvertimeWorkSearchCondition searchCondition);
	
	public List<OvertimeWork> exceptOvertimeworkAllList();
	
	public List<OvertimeWork> overtimeworkInOutExcelList(OvertimeWorkSearchCondition searchCondition);
		
	public List<OvertimeWork> overtimeworkUseTeamListAll();
	
	public List<OvertimeWork> overtimeworkUseTeamList(String userId);
	
	public OvertimeWork getOvertimeWorkUseRequestView(String overtimeworkId);
	
	public void overtimeworkApproveUse(OvertimeWork overtimework);
	
	public void updateOvertimeworkExcept(OvertimeWork overtimework);
	
	public void createOvertimeworkExcept(OvertimeWork overtimework);
	
	public void deleteOvertimeworkExcept(OvertimeWork overtimework);
	
	public void overtimeworkUseRequestUpdate(OvertimeWork overtimework);
	
	public void overtimeworkTeamAuthSave(OvertimeWork overtimework);
	
	public void overtimeworkUserCardSave(OvertimeWork overtimework);
	
	public boolean existsProductno(String productNo);
	
	public String cardUserId(String cardId);
	
	public boolean periodCheck();
	
	public boolean teamManagerCheck(Map<String, String> map);
	
	public boolean teamLeaderCheck(Map<String, String> map);
	
	public void overtimeworkCheckBoxUseRequest(OvertimeWork overtimework);
	
	public List<OvertimeWork> listByItemIdArray(Map<String, List<String>> map);
	
	public List<OvertimeWork> listByTeamItemIdArray(Map<String, List<String>> map);
	
	public List<OvertimeWork> listByTeamItemIdArray1(Map<String, List<String>> map);
	
	public List<OvertimeWork> listByTeamItemIdArray2(Map<String, List<String>> map);
	
	public void overtimeworkManageCheckBoxUseRequest(OvertimeWork overtimework);
	
	public List<OvertimeWork> listCategory(OvertimeWork categoryBoardId);
	
	public void createCategoryNm(OvertimeWork category);
	
	public void updateCategoryNm(OvertimeWork category);
	
	public void deleteCategoryNm(OvertimeWork category);
	
	public void updateCategoryAlign(OvertimeWork category);
	
	public void overtimeworkCheckBoxGroupRequest(OvertimeWork overtimework);
	
	public void overtimeworkUseRequestDelete(OvertimeWork overtimework);
	
	public void savePeriod(OvertimeWork overtimework);
	
	public OvertimeWork getPeriod();
	
	public OvertimeWork getOvertimeworkTeamAuthInfo(String teamId);
	
	public OvertimeWork overtimeworkInOutDetail(String overtimeworkId);
	
	public OvertimeWork getOvertimeworkUserCardInfo(String userId);
	
	public List<OvertimeWork> getPeriodList(String year);
	
	public List<OvertimeWork> getOvertimeworkTeamAuthList();
	
	public List<Map<String, Object>> getOvertimeworkUserCardList(OvertimeWorkSearchCondition searchCondition);
	
	public List<OvertimeWork> getOvertimeworkUserCardListExcel(OvertimeWorkSearchCondition searchCondition);
	
	public void insertDefaultPeriod(String year);
	
	public List<OvertimeWork> selectOvertimeWorkManager(String overtimeworkadm);
}