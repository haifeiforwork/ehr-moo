/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ManualDao extends GenericDao<Manual, String> {
	/**
	 *카테고리별 목록 조회 개수
	 * @param manualSearchCondition
	 * @return
	 */
	public Integer countCategoryManual(ManualSearchCondition manualSearchCondition);
	/**
	 *카테고리별 목록 조회
	 * @param manualSearchCondition
	 * @return
	 */
	public List<Manual> listCategoryManual(ManualSearchCondition manualSearchCondition);
	/**
	 *업무매뉴얼 메인 화면 조회 개수
	 * @param portalId
	 * @return
	 */
	public Integer countManual(String portalId);
	/**
	 *업무매뉴얼 메인 화면 조회
	 * @param manual
	 * @return
	 */
	public List<Manual> listManual(Manual manual);
	/**
	 *업무매뉴얼  조회
	 * @param manualPk
	 * @return
	 */
	public Manual getManual(ManualPk manualPk);
	/**
	 *조회수 증가
	 * @param manualPk
	 */
	public void updateHitCount(ManualPk manualPk);
	/**
	 *글 갯수 증가
	 * @param manualPk
	 */
	public void updateLinereplyCount(ManualPk manualPk);
	/**
	 *업무매뉴얼  삭제
	 * @param manualPk
	 */
	public void removeManual(ManualPk manualPk);
	/**
	 *업무매뉴얼 수정
	 * @param manual
	 */
	public void updateManual(Manual manual);
}
