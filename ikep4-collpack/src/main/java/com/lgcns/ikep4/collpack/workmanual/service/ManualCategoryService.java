/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service;

import java.util.List;

import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.base.tree.TreeNode;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualCategoryService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ManualCategoryService extends GenericService<ManualCategory, String> {
	/**
	 *Portal 포함하여 조회
	 * @param categoryId
	 * @param portalId
	 * @return
	 */
	public ManualCategory getManualCategory(String categoryId, String portalId);
	/**
	 *Portal로 조회
	 * @param portalId
	 * @return
	 */
	public List<ManualCategory> listManualCategoryByPortalId(String portalId);
	/**
	 *신규 생성
	 * @param manualCategory
	 * @param readUsers
	 * @param readGroups
	 * @param writeUsers
	 * @param approvalUsers
	 * @return
	 */
	public String createManualCategory(ManualCategory manualCategory, String readUsers, String readGroups, String writeUsers, String approvalUsers);
	/**
	 *수정
	 * @param manualCategory
	 * @param readUsers
	 * @param readGroups
	 * @param writeUsers
	 * @param approvalUsers
	 */
	public void updateManualCategory(ManualCategory manualCategory, String readUsers, String readGroups, String writeUsers, String approvalUsers);
	/**
	 *삭제
	 * @param categoryId
	 * @param portalId
	 */
	public void deleteManualCategory(String categoryId, String portalId);
	/**
	 *트리에서 사용하는 노드 JSON - 최상위 카테고리 조회
	 * @param user
	 * @return
	 */
	public String listTopCategory(User user);
	/**
	 *하위 카테고리 노드 조회
	 * @param categoryParentId
	 * @return
	 */
	public List<TreeNode> listChildCategory(String categoryParentId);
	/**
	 *트리 이동
	 * @param sourceId
	 * @param targetParentId
	 * @param targetSortOrder
	 */
	public void moveCategory(String sourceId, String targetParentId, String targetSortOrder);
}
