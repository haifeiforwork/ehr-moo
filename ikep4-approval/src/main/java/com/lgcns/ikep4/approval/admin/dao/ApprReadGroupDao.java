/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.admin.model.ApprReadGroup;
import com.lgcns.ikep4.approval.admin.search.ApprReadGroupSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 부서 결재함 열람권한 Dao 정의
 *
 * @author 
 * @version $Id$
 */
public interface ApprReadGroupDao extends GenericDao<ApprReadGroup, String> {



	/**
	 * 부서 결재함 열람권한 Count
	 * 
	 * @param ApprReadGroupSearchCondition
	 * @return
	 */
	Integer countBySearchCondition(ApprReadGroupSearchCondition searchCondition);
	/**
	 * 부서 결재함 열람권한 목록
	 * 
	 * @param ApprReadGroupSearchCondition
	 * @return
	 */
	List<ApprReadGroup> listBySearchCondition(ApprReadGroupSearchCondition searchCondition);
	
	
	/**
	 * 부서 결재함 열람권한  생성
	 * @param 	map
	 * @return 	nothing
	 */
	public void createApprReadGroup(ApprReadGroup apprReadGroup);
	
	/**
	 * 부서 목록
	 * @param 	map
	 * @return 	nothing
	 */
	public List<ApprReadGroup> getGroupList(Map map);
	
	/**
	 * 부서 결재함 열람권한 정보
	 * @param 	map
	 * @return 	nothing
	 */
	public ApprReadGroup getBasicInfo(Map map);
	
	/**
	 * 부서 결재함 열람권한  생성
	 * @param 	map
	 * @return 	nothing
	 */
	public void deleteApprReadGroup(Map map);
	
	/**
	 * 부서 결재함 열람권한  목록에서 삭제
	 * @param 	map
	 * @return 	nothing
	 */
	public void deleteApprReadGroupAjax(Map map);
	
}
