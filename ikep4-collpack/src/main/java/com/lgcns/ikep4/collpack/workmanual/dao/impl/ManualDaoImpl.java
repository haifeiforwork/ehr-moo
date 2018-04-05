/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.workmanual.dao.ManualDao;
import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ManualDaoImpl extends GenericDaoSqlmap<Manual, String> implements ManualDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.manual."; 
	
	public String create(Manual manual) {
		return (String) sqlInsert(NAMESPACE + "create", manual);
	}

	public boolean exists(String manualId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", manualId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public Manual get(String manualId) {
		return (Manual) sqlSelectForObject(NAMESPACE + "get", manualId);
	}

	public void remove(String manualId) {
		sqlDelete(NAMESPACE + "remove", manualId);
	}

	public void update(Manual manual) {
		sqlUpdate(NAMESPACE + "update", manual);
	}
	////////////////////////////////////

	//카테고리별 목록 조회 개수
	public Integer countCategoryManual(ManualSearchCondition manualSearchCondition) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countCategoryManual", manualSearchCondition);
	}
	//카테고리별 목록 조회
	public List<Manual> listCategoryManual(ManualSearchCondition manualSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listCategoryManual", manualSearchCondition);
	}
	//업무매뉴얼 메인 화면 조회 개수
	public Integer countManual(String portalId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countManual", portalId);
	}
	//업무매뉴얼 메인 화면 조회
	public List<Manual> listManual(Manual manual) {
		return sqlSelectForList(NAMESPACE + "listManual", manual);
	}
	//업무매뉴얼  조회
	public Manual getManual(ManualPk manualPk) {
		return (Manual) sqlSelectForObject(NAMESPACE + "getManual", manualPk);
	}
	//조회수 증가
	public void updateHitCount(ManualPk manualPk) {
		sqlUpdate(NAMESPACE + "updateHitCount", manualPk);
	}
	//글 갯수 증가
	public void updateLinereplyCount(ManualPk manualPk) {
		sqlUpdate(NAMESPACE + "updateLinereplyCount", manualPk);
	}
	//업무매뉴얼  삭제
	public void removeManual(ManualPk manualPk) {
		sqlDelete(NAMESPACE + "removeManual", manualPk);
	}
	//업무매뉴얼 수정
	public void updateManual(Manual manual) {
		sqlUpdate(NAMESPACE + "updateManual", manual);
	}
}
