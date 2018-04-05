/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.portlet.service;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying;


/**
 * 포틀릿 오늘의 명언 관리 서비스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortletWiseSayingService.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortletWiseSayingService extends GenericService<PortletWiseSaying, String> {

	/**
	 * 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지 별 오늘의 명언 리스트
	 * @param searchCondition SearchCondition
	 * @return SearchResult<PortletWiseSaying> 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지 별 오늘의 명언 리스트
	 */
	public SearchResult<PortletWiseSaying> listBySearchCondition(SearchCondition searchCondition);
	
	/**
	 * 오늘의 명언 정보
	 * @param id 오늘의 명언 아이디
	 * @return PortletWiseSaying 오늘의 명언 정보
	 */
	public PortletWiseSaying get(String id);
	
	/**
	 * 오늘의 명언 무작위 선택
	 * @return PortletWiseSaying 오늘의 명언 무작위 선택
	 */
	public PortletWiseSaying getRandomWiseSaying();
	
	/**
	 * 오늘의 명언 등록 확인
	 * @param obj 오늘의 명언 정보
	 * @return boolean 오늘의 명언 등록 확인(등록:true, 미등록:false)
	 */
	public boolean existsWiseSaying(PortletWiseSaying obj);
	
	/**
	 * 오늘의 명언 등록
	 * @param obj 오늘의 명언 정보
	 * @return 등록된 오늘의 명언 아이디
	 * @throws Exception
	 */
	public String createWiseSaying(PortletWiseSaying obj);
	
	/**
	 * 오늘의 명언 수정
	 * @param obj 오늘의 명언 정보
	 * @throws Exception
	 */
	public void updateWiseSaying(PortletWiseSaying obj);

	/**
	 * 오늘의 명언 멀티 삭제
	 * @param ids 오늘의 명언 아이디
	 */
	public void multiDeleteWiseSaying(String[] ids);
	
}
