/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersionPk;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualVersionDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ManualVersionDao extends GenericDao<ManualVersion, String> {
	/**
	 *개인 업무 매뉴얼 버젼 조회 개수
	 * @param manualSearchCondition
	 * @return
	 */
	public Integer countMyManualVersion(ManualSearchCondition manualSearchCondition);
	/**
	 *개인 업무 매뉴얼 버젼  조회
	 * @param manualSearchCondition
	 * @return
	 */
	public List<ManualVersion> listMyManualVersion(ManualSearchCondition manualSearchCondition);
	/**
	 *업무 매뉴얼 버젼  조회 개수
	 * @param manualSearchCondition
	 * @return
	 */
	public Integer countManualVersion(ManualSearchCondition manualSearchCondition);
	/**
	 *업무 매뉴얼 버젼  조회
	 * @param manualSearchCondition
	 * @return
	 */
	public List<ManualVersion> listManualVersion(ManualSearchCondition manualSearchCondition);
	/**
	 *최상위 매뉴얼 버젼 조회
	 * @param manualPk
	 * @return
	 */
	public ManualVersion getTopManualVersion(ManualPk manualPk);
	/**
	 *업무 매뉴얼 버젼 삭제
	 * @param manualVersionPk
	 */
	public void removeManualVersion(ManualVersionPk manualVersionPk);
	/**
	 *업무 매뉴얼 버젼 조회
	 * @param manualVersionPk
	 * @return
	 */
	public ManualVersion getManualVersion(ManualVersionPk manualVersionPk);
	/**
	 *업무 매뉴얼 버젼 수정
	 * @param manualVersion
	 */
	public void updateManualVersion(ManualVersion manualVersion);
	/**
	 *상신중인 매뉴얼 버젼 갯수
	 * @param manualPk
	 * @return
	 */
	public Integer countSubmittingManualVersion(ManualPk manualPk);
	/**
	 *매뉴얼 테이블에 존재하는 매뉴얼 버젼 정보 조회
	 * @param manualPk
	 * @return
	 */
	public ManualVersion getManualVersionBymanualId(ManualPk manualPk);
}
