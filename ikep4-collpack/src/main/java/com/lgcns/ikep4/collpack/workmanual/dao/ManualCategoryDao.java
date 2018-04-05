/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualCategoryDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ManualCategoryDao extends GenericDao<ManualCategory, String> {
	/**
	 *Portal 포함하여 조회
	 * @param manualCategory
	 * @return
	 */
	public ManualCategory getManualCategory(ManualCategory manualCategory);
	/**
	 *Portal로 조회
	 * @param portalId
	 * @return
	 */
	public List<ManualCategory> listManualCategoryByPortalId(String portalId);
	/**
	 *하위 매뉴얼수 
	 * @param categoryParentId
	 * @return
	 */
	public Integer countChildren(String categoryParentId);
	/**
	 *매뉴얼 결재자 정보 삭제
	 * @param manualCategory
	 */
	public void removeApprovalLineByCategoryId(ManualCategory manualCategory);
	/**
	 *매뉴얼 결재정보 삭제
	 * @param manualCategory
	 */
	public void removeApprovalByCategoryId(ManualCategory manualCategory);
	/**
	 *댓글 정보 삭제
	 * @param manualCategory
	 */
	public void removeLinereplyByCategoryId(ManualCategory manualCategory);
	/**
	 *조회 정보 삭제
	 * @param manualCategory
	 */
	public void removeReferenceByCategoryId(ManualCategory manualCategory);
	/**
	 *버젼 정보 삭제
	 * @param manualCategory
	 */
	public void removeManualVersionByCategoryId(ManualCategory manualCategory);
	/**
	 *매뉴얼 정보 삭제
	 * @param manualCategory
	 */
	public void removeManualByCategoryId(ManualCategory manualCategory);
	/**
	 *조회 사용자 정보 삭제
	 * @param manualCategory
	 */
	public void removeReadUserByCategoryId(ManualCategory manualCategory);
	/**
	 *조회 조직 삭제
	 * @param manualCategory
	 */
	public void removeReadGroupByCategoryId(ManualCategory manualCategory);
	/**
	 *문서 결재자 삭제
	 * @param manualCategory
	 */
	public void removeApprovalUserByCategoryId(ManualCategory manualCategory);
	/**
	 *문서 담당자 삭제
	 * @param manualCategory
	 */
	public void removeWriteUserByCategoryId(ManualCategory manualCategory);
	/**
	 *카테고리 삭제
	 * @param manualCategory
	 */
	public void removeManualCategoryByCategoryId(ManualCategory manualCategory);
	/**
	 *루트 카테고리 조회
	 * @param portalId
	 * @return
	 */
	public ManualCategory getRootCategory(String portalId);
	/**
	 *하위 카테고리 조회
	 * @param categoryParentId
	 * @return
	 */
	public List<ManualCategory> listChildCategory(String categoryParentId);
	/**
	 *카테고리 이동 - 원본 순번 바꾸기
	 * @param map
	 */
	public void updateMoveCategoryInSource(Map<String, String> map);
	/**
	 *카테고리 이동 - 타겟 순번 바꾸기
	 * @param map
	 */
	public void updateMoveCategoryInTarget(Map<String, String> map);
	/**
	 *카테고리 이동 - 자신 변경
	 * @param map
	 */
	public void updateMoveCategoryInMine(Map<String, String> map);
}
