/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service;

import java.util.List;

import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.tagging.model.Tag;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ManualService extends GenericService<Manual, String> {
	/**
	 *카테고리별 목록 조회
	 * @param manualSearchCondition
	 * @return
	 */
	public SearchResult<Manual> listCategoryManual(ManualSearchCondition manualSearchCondition);
	/**
	 *업무매뉴얼 메인 화면 조회 개수
	 * @param portalId
	 * @return
	 */
	public Integer countManual(String portalId);
	/**
	 *업무매뉴얼 메인 화면 조회
	 * @param portalId
	 * @param endRowNum
	 * @return
	 */
	public List<Manual> listManual(String portalId, Integer endRowNum);
	/**
	 *업무매뉴얼  조회
	 * @param manualId
	 * @param portalId
	 * @param userId
	 * @return
	 */
	public Manual readManual(String manualId, String portalId, String userId);
	/**
	 *인기 태그 조회
	 * @param portalId
	 * @return
	 */
	public List<Tag> listPopularTag(String portalId);
}
