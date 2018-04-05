/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officesupplies.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSupplies;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSuppliesSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface OfficeSuppliesService extends GenericService<OfficeSupplies, String> {

	
	public SearchResult<Map<String, Object>> myRequestList(OfficeSuppliesSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> teamRequestList(OfficeSuppliesSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> teamsRequestList(OfficeSuppliesSearchCondition searchCondition);
	
	public int teamsRequestPrice(OfficeSuppliesSearchCondition searchCondition);
	
	public int teamRequestPrice1(OfficeSuppliesSearchCondition searchCondition);
	
	public int selectRequestUserPrice(OfficeSuppliesSearchCondition searchCondition);
	
	public int selectRequestTeamPrice(OfficeSuppliesSearchCondition searchCondition);
	
	public int selectRequestMyPrice1(OfficeSuppliesSearchCondition searchCondition);
	
	public int selectRequestMyPrice2(OfficeSuppliesSearchCondition searchCondition);
	
	public int teamRequestPrice2(OfficeSuppliesSearchCondition searchCondition);
	
	public int getTotalPrice(OfficeSupplies officesupplies);
	
	public SearchResult<Map<String, Object>> requestList(OfficeSuppliesSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> requestAllList(OfficeSuppliesSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> exceptOfficesuppliesList(OfficeSuppliesSearchCondition searchCondition);
	
	public List<OfficeSupplies> exceptOfficesuppliesAllList();
	
	public List<OfficeSupplies> officesuppliesUseTeamListAll();
	
	public List<OfficeSupplies> officesuppliesUseTeamList(String userId);
	
	public List<OfficeSupplies> teamsRequestDetailList(OfficeSuppliesSearchCondition searchCondition);
	
	public List<OfficeSupplies> teamRequestDetailList(OfficeSuppliesSearchCondition searchCondition);
	
	public void officesuppliesRequestDelete(OfficeSupplies officesupplies);
	
	public List<OfficeSupplies> getStatisticsList1(OfficeSupplies officesupplies);
	
	public List<OfficeSupplies> getStatisticsList2(OfficeSupplies officesupplies);
	
	public List<OfficeSupplies> getStatisticsList3(OfficeSupplies officesupplies);
	
	public void officesuppliesUseRequest(OfficeSupplies officesupplies, User user);
	
	public boolean existsProductno(String productNo);
	
	public boolean periodCheck();
	
	public boolean teamManagerCheck(Map<String, String> map);
	
	public boolean teamLeaderCheck(Map<String, String> map);
	
	public void updateOfficesuppliesExcept(OfficeSupplies officesupplies);
	
	public void createOfficesuppliesExcept(OfficeSupplies officesupplies);
	
	public void deleteOfficesuppliesExcept(OfficeSupplies officesupplies);
	
	public List<OfficeSupplies> listCategory(OfficeSupplies categoryBoardId);
	
	public void insertCategoryNm(List<OfficeSupplies> receiveCategoryNmList) ;
	
	public void officesuppliesCheckBoxUseRequest(OfficeSupplies officesupplies, User user);
	
	public void officesuppliesManageCheckBoxUseRequest(OfficeSupplies officesupplies, User user);
	
	public void officesuppliesCheckBoxGroupRequest(OfficeSupplies officesupplies, User user);
	
	public void officesuppliesUseRequestUpdate(OfficeSupplies officesupplies, User user);
	
	public void savePeriod(OfficeSupplies officesupplies);
	
	public OfficeSupplies getPeriod();
	
	public OfficeSupplies getOfficesuppliesTeamAuthInfo(String teamId);
	
	public List<OfficeSupplies> getPeriodList(String year);
	
	public List<OfficeSupplies> getOfficesuppliesTeamAuthList();
	
	public OfficeSupplies getOfficeSuppliesUseRequestView(String officesuppliesId);
	
	public void officesuppliesApproveUse(OfficeSupplies officesupplies);
	
	public void officesuppliesUseRequestUpdate(OfficeSupplies officesupplies);
	
	public void officesuppliesTeamAuthSave(OfficeSupplies officesupplies);
	
	public void officesuppliesUseRequestDelete(OfficeSupplies officesupplies);
	
	public void sendOfficeSuppliesUseRequestMail(String flg, String message, OfficeSupplies officesupplies, User sender);
}