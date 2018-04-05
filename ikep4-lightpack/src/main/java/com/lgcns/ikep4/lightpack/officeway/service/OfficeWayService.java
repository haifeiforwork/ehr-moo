/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officeway.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWay;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWaySearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface OfficeWayService extends GenericService<OfficeWay, String> {

	
	public SearchResult<Map<String, Object>> myRequestList(OfficeWaySearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> teamRequestList(OfficeWaySearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> teamsRequestList(OfficeWaySearchCondition searchCondition);
	
	public int teamsRequestPrice(OfficeWaySearchCondition searchCondition);
	
	public int teamRequestPrice1(OfficeWaySearchCondition searchCondition);
	
	public int selectRequestUserPrice(OfficeWaySearchCondition searchCondition);
	
	public int selectRequestTeamPrice(OfficeWaySearchCondition searchCondition);
	
	public int selectRequestMyPrice1(OfficeWaySearchCondition searchCondition);
	
	public int selectRequestMyPrice2(OfficeWaySearchCondition searchCondition);
	
	public int teamRequestPrice2(OfficeWaySearchCondition searchCondition);
	
	public int getTotalPrice(OfficeWay officeway);
	
	public SearchResult<Map<String, Object>> requestList(OfficeWaySearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> requestAllList(OfficeWaySearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> exceptOfficewayList(OfficeWaySearchCondition searchCondition);
	
	public List<OfficeWay> exceptOfficewayAllList();
	
	public List<OfficeWay> officewayUseTeamListAll();
	
	public List<OfficeWay> officewayUseTeamList(String userId);
	
	public List<OfficeWay> teamsRequestDetailList(OfficeWaySearchCondition searchCondition);
	
	public List<OfficeWay> teamRequestDetailList(OfficeWaySearchCondition searchCondition);
	
	public void officewayRequestDelete(OfficeWay officeway);
	
	public List<OfficeWay> getStatisticsList1(OfficeWay officeway);
	
	public List<OfficeWay> getStatisticsList2(OfficeWay officeway);
	
	public List<OfficeWay> getStatisticsList3(OfficeWay officeway);
	
	public void officewayUseRequest(OfficeWay officeway, User user);
	
	public boolean existsProductno(String productNo);
	
	public boolean periodCheck();
	
	public boolean teamManagerCheck(Map<String, String> map);
	
	public boolean teamLeaderCheck(Map<String, String> map);
	
	public void updateOfficewayExcept(OfficeWay officeway);
	
	public void createOfficewayExcept(OfficeWay officeway);
	
	public void deleteOfficewayExcept(OfficeWay officeway);
	
	public List<OfficeWay> listCategory(OfficeWay categoryBoardId);
	
	public void insertCategoryNm(List<OfficeWay> receiveCategoryNmList) ;
	
	public void officewayCheckBoxUseRequest(OfficeWay officeway, User user);
	
	public void officewayManageCheckBoxUseRequest(OfficeWay officeway, User user);
	
	public void officewayCheckBoxGroupRequest(OfficeWay officeway, User user);
	
	public void officewayUseRequestUpdate(OfficeWay officeway, User user);
	
	public void officewayUseRequestAdminUpdate(OfficeWay officeway, User user);
	
	public void savePeriod(OfficeWay officeway);
	
	public OfficeWay getPeriod();
	
	public OfficeWay periodInfo();
	
	public OfficeWay getOfficewayTeamAuthInfo(String teamId);
	
	public List<OfficeWay> getPeriodList(String year);
	
	public List<OfficeWay> getOfficewayTeamAuthList();
	
	public OfficeWay getOfficeWayUseRequestView(String officewayId);
	
	public void officewayApproveUse(OfficeWay officeway);
	
	public void officewayUseRequestUpdate(OfficeWay officeway);
	
	public void officewayTeamAuthSave(OfficeWay officeway);
	
	public void officewayUseRequestDelete(OfficeWay officeway);
	
	public void sendOfficeWayUseRequestMail(String flg, String message, OfficeWay officeway, User sender);
}