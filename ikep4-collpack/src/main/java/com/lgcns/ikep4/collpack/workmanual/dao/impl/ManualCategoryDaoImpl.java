/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.workmanual.dao.ManualCategoryDao;
import com.lgcns.ikep4.collpack.workmanual.model.ManualCategory;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualCategoryDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ManualCategoryDaoImpl extends GenericDaoSqlmap<ManualCategory, String> implements ManualCategoryDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.manualCategory."; 
	
	public String create(ManualCategory manualCategory) {
		sqlInsert(NAMESPACE + "create", manualCategory);
		return manualCategory.getCategoryId();
	}

	public boolean exists(String categoryId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", categoryId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public ManualCategory get(String categoryId) {
		return (ManualCategory) sqlSelectForObject(NAMESPACE + "get", categoryId);
	}

	public void remove(String categoryId) {
		sqlDelete(NAMESPACE + "remove", categoryId);
	}

	public void update(ManualCategory manualCategory) {
		sqlUpdate(NAMESPACE + "update", manualCategory);
	}
	////////////////////////////////////

	//Portal 포함하여 조회
	public ManualCategory getManualCategory(ManualCategory manualCategory) {
		return (ManualCategory) sqlSelectForObject(NAMESPACE + "getManualCategory", manualCategory);
	}
	//Portal로 조회
	public List<ManualCategory> listManualCategoryByPortalId(String portalId) {
		return sqlSelectForList(NAMESPACE + "listManualCategoryByPortalId", portalId);
	}
	//하위 매뉴얼수 
	public Integer countChildren(String categoryParentId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countChildren", categoryParentId);
	}
	//매뉴얼 결재자 정보 삭제
	public void removeApprovalLineByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeApprovalLineByCategoryId", manualCategory);
	 }
	//매뉴얼 결재정보 삭제
	public void removeApprovalByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeApprovalByCategoryId", manualCategory);
	 }
	//댓글 정보 삭제
	public void removeLinereplyByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeLinereplyByCategoryId", manualCategory);
	 }
	//조회 정보 삭제
	public void removeReferenceByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeReferenceByCategoryId", manualCategory);
	 }
	//버젼 정보 삭제
	public void removeManualVersionByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeManualVersionByCategoryId", manualCategory);
	 }
	//매뉴얼 정보 삭제
	public void removeManualByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeManualByCategoryId", manualCategory);
	 }
	//조회 사용자 정보 삭제
	public void removeReadUserByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeReadUserByCategoryId", manualCategory);
	 }
	//조회 조직 삭제
	public void removeReadGroupByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeReadGroupByCategoryId", manualCategory);
	 }
	//문서 결재자 삭제
	public void removeApprovalUserByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeApprovalUserByCategoryId", manualCategory);
	 }
	//문서 담당자 삭제
	public void removeWriteUserByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeWriteUserByCategoryId", manualCategory);
	 }
	//카테고리 삭제
	public void removeManualCategoryByCategoryId(ManualCategory manualCategory) {
		sqlDelete(NAMESPACE + "removeManualCategoryByCategoryId", manualCategory);
	 }
	
	
	
	
	//루트 카테고리 조회
	public ManualCategory getRootCategory(String portalId) {
		return (ManualCategory) sqlSelectForObject(NAMESPACE + "getRootCategory", portalId);
	}
	//하위 카테고리 조회
	public List<ManualCategory> listChildCategory(String categoryParentId) {
		return sqlSelectForList(NAMESPACE + "listChildCategory", categoryParentId);
	}
	//카테고리 이동 - 원본 순번 바꾸기
	public void updateMoveCategoryInSource(Map<String, String> map) {
		sqlUpdate(NAMESPACE + "updateMoveCategoryInSource", map);
	}
	//카테고리 이동 - 타겟 순번 바꾸기
	public void updateMoveCategoryInTarget(Map<String, String> map) {
		sqlUpdate(NAMESPACE + "updateMoveCategoryInTarget", map);
	}
	//카테고리 이동 - 자신 변경
	public void updateMoveCategoryInMine(Map<String, String> map) {
		sqlUpdate(NAMESPACE + "updateMoveCategoryInMine", map);
	}
}
