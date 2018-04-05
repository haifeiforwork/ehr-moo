/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.admin.screen.search.PortalSystemUrlSearchCondition;


/**
 * 시스템 Url 관리 서비스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemUrlService.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface PortalSystemUrlService extends GenericService<PortalSystemUrl, String> {

	/**
	 * 페이지 별 시스템 URL 리스트
	 * @param searchCondition PortalSystemUrlSearchCondition
	 * @return SearchResult<PortalSystemUrl> 전체 시스템 URL 리스트
	 */
	public SearchResult<PortalSystemUrl> listBySearchCondition(PortalSystemUrlSearchCondition searchCondition);
	
	/**
	 * 시스템 URL 정보
	 * @param param Map(fieldName:다국어로 관리되는 field 이름, localeCode:사용자별 locale 코드, systemUrlId:시스템 URL 코드)
	 * @return PortalSystemUrl 시스템 URL 정보
	 */
	public PortalSystemUrl read(Map<String, String> param);
	
	/**
	 * 시스템 URL 생성
	 * @param PortalSystemUrl 시스템 URL 정보
	 * @return 생성된 시스템 URL 아이디
	 */
	public String create(PortalSystemUrl portalSystemUrl);
	
	/**
	 * 시스템 URL 수정
	 * @param PortalSystemUrl 시스템 URL 정보
	 */
	public void update(PortalSystemUrl portalSystemUrl);
	
	/**
	 * 시스템 URL 삭제
	 * @param systemUrlId 시스템 URL 아이디
	 */
	public void delete(String systemUrlId);
	
	/**
	 * URL 별 시스템 URL 아이디
	 * @param url URL
	 * @return String 시스템 URL 아이디
	 */
	public String readSystemUrlId(String url);
}
