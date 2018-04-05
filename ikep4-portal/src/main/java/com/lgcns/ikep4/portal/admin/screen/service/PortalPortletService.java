/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;


/**
 * 포탈 포틀릿 Service
 * 
 * @author 한승환
 * @version $Id: PortalPortletService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PortalPortletService extends GenericService<PortalPortlet, String> {
	
	/**
	 * 포틀릿 조회
	 * 
	 * @param portletId  포틀릿 아이디
	 * @return 포틀릿 상세정보
	 */
	public PortalPortlet readPortalPortlet(String portletId);
	
	/**
	 * 포틀릿 삭제
	 * 
	 * @param portletId  포틀릿 아이디
	 * @return 
	 */
	public int deletePortalPortlet(String portletId);
	
	/**
	 * 포틀릿 등록
	 * 
	 * @param portalPortlet  포틀릿 정보
	 * @return 포틀릿 등록 결과  성공:1, 실패:2
	 */
	public String createPortalPortlet(PortalPortlet portalPortlet);
	
	/**
	 * 포틀릿 수정
	 * 
	 * @param portalPortlet  포틀릿 정보
	 * @return 성공 1 , 실패 0 을 return 함
	 */
	public int updatePortalPortlet(PortalPortlet portalPortlet);
	
	/**
	 * 포틀릿 목록
	 * 
	 * @param searchCondition 검색조건
	 * @return 포틀릿 검색 결과 목록
	 */
	public SearchResult<PortalPortlet> listPortalPortletByCondition(AdminSearchCondition searchCondition);
	
	/**
	 * .war 파일의 /WEB-INF/portlet.xml 파일을 파싱하여 포틀릿 목록 정보를 생성함
	 * 
	 * @param portalPortletParam 파라미터 정보
	 * @param fileItem 멀티파트 파일
	 * @param baseUrl http://~/context/ 까지 서버의 URL정보
	 * @return 포틀릿 목록 정보
	 */
	public List<PortalPortlet> convertXmlToPortalPortlet(PortalPortlet portalPortletParam, CommonsMultipartFile fileItem, String baseUrl);
}
