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

import com.lgcns.ikep4.collpack.workmanual.dao.ManualVersionDao;
import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersionPk;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualVersionDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class ManualVersionDaoImpl extends GenericDaoSqlmap<ManualVersion, String> implements ManualVersionDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.manualVersion."; 
	
	public String create(ManualVersion manualVersion) {
		return (String) sqlInsert(NAMESPACE + "create", manualVersion);
	}

	public boolean exists(String versionId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", versionId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public ManualVersion get(String versionId) {
		return (ManualVersion) sqlSelectForObject(NAMESPACE + "get", versionId);
	}

	public void remove(String versionId) {
		sqlDelete(NAMESPACE + "remove", versionId);
	}

	public void update(ManualVersion manualVersion) {
		sqlUpdate(NAMESPACE + "update", manualVersion);
	}
	////////////////////////////////////

	//개인 업무 매뉴얼 버젼  조회 개수
	public Integer countMyManualVersion(ManualSearchCondition manualSearchCondition) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countMyManualVersion", manualSearchCondition);
	}
	//개인 업무 매뉴얼 버젼  조회
	public List<ManualVersion> listMyManualVersion(ManualSearchCondition manualSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listMyManualVersion", manualSearchCondition);
	}
	//업무 매뉴얼 버젼  조회 개수
	public Integer countManualVersion(ManualSearchCondition manualSearchCondition) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countManualVersion", manualSearchCondition);
	}
	//업무 매뉴얼 버젼  조회
	public List<ManualVersion> listManualVersion(ManualSearchCondition manualSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listManualVersion", manualSearchCondition);
	}
	//최상위 매뉴얼 버젼 조회
	public ManualVersion getTopManualVersion(ManualPk manualPk) {
		return (ManualVersion) sqlSelectForObject(NAMESPACE + "getTopManualVersion", manualPk);
	}
	//업무 매뉴얼 버젼 삭제
	public void removeManualVersion(ManualVersionPk manualVersionPk) {
		sqlDelete(NAMESPACE + "removeManualVersion", manualVersionPk);
	}
	//업무 매뉴얼 버젼 조회
	public ManualVersion getManualVersion(ManualVersionPk manualVersionPk) {
		return (ManualVersion) sqlSelectForObject(NAMESPACE + "getManualVersion", manualVersionPk);
	}
	//업무 매뉴얼 버젼 수정
	public void updateManualVersion(ManualVersion manualVersion) {
		sqlUpdate(NAMESPACE + "updateManualVersion", manualVersion);
	}
	//상신중인 매뉴얼 버젼 갯수
	public Integer countSubmittingManualVersion(ManualPk manualPk) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countSubmittingManualVersion", manualPk);
	}
	//매뉴얼 테이블에 존재하는 매뉴얼 버젼 정보 조회
	public ManualVersion getManualVersionBymanualId(ManualPk manualPk) {
		return (ManualVersion) sqlSelectForObject(NAMESPACE + "getManualVersionBymanualId", manualPk);
	}
}
