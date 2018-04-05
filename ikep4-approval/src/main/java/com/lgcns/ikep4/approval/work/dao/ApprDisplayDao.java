/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.work.model.ApprDisplay;
import com.lgcns.ikep4.approval.work.search.ApprDisplaySearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * 공람지정
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface ApprDisplayDao extends GenericDao<ApprDisplay, String> {

	void createApprDisplay(ApprDisplay apprDisplay);
	
	void createApprDisplaySub(ApprDisplay apprDisplay);
	
	void creatDisplayUserIdForGroup(Map map);
	
	List<ApprDisplay> getUserIdByGroup(Map map);
	
	List<ApprDisplay> getUserIdBySubGroupCntList(Map map);
	
	List<ApprDisplay> getUserIdByParentGroup(Map map);

	List<ApprDisplay> listBySearchCondition(ApprDisplaySearchCondition searchCondition);
	
	Integer countBySearchCondition(ApprDisplaySearchCondition searchCondition);
	
	void deleteApprDisplaySub(Map map);
	
	void deleteApprDisplay(Map map);
	
	Integer getApprDisplaySubCount(Map map);
	
	List<ApprDisplay> listByDisplaySearchCondition(ApprDisplaySearchCondition searchCondition);
	
	Integer countByDisplaySearchCondition(ApprDisplaySearchCondition searchCondition);
	
	public String getApprDisplayId(Map map);
	
	public void updateApprDisplaySub(Map map);
	
	Integer countByDisplayUserStatus(String apprId);
	
	boolean existDisplayDoc (Map map);
	
	boolean existDisplayUser (Map map);
}
