/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.overtimework.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWork;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWorkSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface OvertimeWorkService extends GenericService<OvertimeWork, String> {

	
	public SearchResult<Map<String, Object>> myRequestList(OvertimeWorkSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> teamRequestList(OvertimeWorkSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> overtimeworkInOutAllList(OvertimeWorkSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> overtimeworkInOutMyList(OvertimeWorkSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> teamsRequestList(OvertimeWorkSearchCondition searchCondition);
	
	public int teamsRequestPrice(OvertimeWorkSearchCondition searchCondition);
	
	public int teamRequestPrice1(OvertimeWorkSearchCondition searchCondition);
	
	public int selectRequestUserPrice(OvertimeWorkSearchCondition searchCondition);
	
	public int selectRequestTeamPrice(OvertimeWorkSearchCondition searchCondition);
	
	public int selectRequestMyPrice1(OvertimeWorkSearchCondition searchCondition);
	
	public int selectRequestMyPrice2(OvertimeWorkSearchCondition searchCondition);
	
	public int teamRequestPrice2(OvertimeWorkSearchCondition searchCondition);
	
	public int getTotalPrice(OvertimeWork overtimework);
	
	public SearchResult<Map<String, Object>> requestList(OvertimeWorkSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> requestAllList(OvertimeWorkSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> exceptOvertimeworkList(OvertimeWorkSearchCondition searchCondition);
	
	public List<OvertimeWork> exceptOvertimeworkAllList();
	
	public List<OvertimeWork> overtimeworkInOutExcelList(OvertimeWorkSearchCondition searchCondition);
	
	public List<OvertimeWork> overtimeworkUseTeamListAll();
	
	public List<OvertimeWork> overtimeworkUseTeamList(String userId);
	
	public List<OvertimeWork> teamsRequestDetailList(OvertimeWorkSearchCondition searchCondition);
	
	public List<OvertimeWork> teamRequestDetailList(OvertimeWorkSearchCondition searchCondition);
	
	public void overtimeworkRequestDelete(OvertimeWork overtimework);
	
	public List<OvertimeWork> getStatisticsList1(OvertimeWork overtimework);
	
	public List<OvertimeWork> getStatisticsList2(OvertimeWork overtimework);
	
	public List<OvertimeWork> getStatisticsList3(OvertimeWork overtimework);
	
	public String overtimeworkInOutRegist(OvertimeWork overtimework, User user);
	
	public String overtimeworkInOutUpdate(OvertimeWork overtimework, User user);
	
	public boolean existsProductno(String productNo);
	
	public String cardUserId(String cardId);
	
	public boolean periodCheck();
	
	public boolean teamManagerCheck(Map<String, String> map);
	
	public boolean teamLeaderCheck(Map<String, String> map);
	
	public void updateOvertimeworkExcept(OvertimeWork overtimework);
	
	public void createOvertimeworkExcept(OvertimeWork overtimework);
	
	public void deleteOvertimeworkExcept(OvertimeWork overtimework);
	
	public List<OvertimeWork> listCategory(OvertimeWork categoryBoardId);
	
	public void insertCategoryNm(List<OvertimeWork> receiveCategoryNmList) ;
	
	public void overtimeworkCheckBoxUseRequest(OvertimeWork overtimework, User user);
	
	public void overtimeworkManageCheckBoxUseRequest(OvertimeWork overtimework, User user);
	
	public void overtimeworkCheckBoxGroupRequest(OvertimeWork overtimework, User user);
	
	public void overtimeworkUseRequestUpdate(OvertimeWork overtimework, User user);
	
	public void savePeriod(OvertimeWork overtimework);
	
	public OvertimeWork getPeriod();
	
	public OvertimeWork overtimeworkInOutDetail(String overtimeworkId);
	
	public OvertimeWork getOvertimeworkTeamAuthInfo(String teamId);
	
	public OvertimeWork getOvertimeworkUserCardInfo(String userId);
	
	public List<OvertimeWork> getPeriodList(String year);
	
	public List<OvertimeWork> getOvertimeworkTeamAuthList();
	
	public SearchResult<Map<String, Object>> getOvertimeworkUserCardList(OvertimeWorkSearchCondition searchCondition);
	
	public List<OvertimeWork> getOvertimeworkUserCardListExcel(OvertimeWorkSearchCondition searchCondition);
	
	public OvertimeWork getOvertimeWorkUseRequestView(String overtimeworkId);
	
	public void overtimeworkApproveUse(OvertimeWork overtimework);
	
	public void overtimeworkUseRequestUpdate(OvertimeWork overtimework);
	
	public void overtimeworkTeamAuthSave(OvertimeWork overtimework);
	
	public void overtimeworkUserCardSave(OvertimeWork overtimework);
	
	public void overtimeworkUseRequestDelete(OvertimeWork overtimework);
	
	public void sendOvertimeWorkUseRequestMail(String flg, String message, OvertimeWork overtimework, User sender);
}