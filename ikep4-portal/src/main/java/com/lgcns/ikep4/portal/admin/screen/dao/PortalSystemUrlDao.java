package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.admin.screen.search.PortalSystemUrlSearchCondition;

public interface PortalSystemUrlDao extends GenericDao<PortalSystemUrl, String> {
	
	/**
	 * 페이지 별 시스템 URL 리스트
	 * @param systemUrlSearchCondition PortalSystemUrlSearchCondition
	 * @return SearchResult<PortalSystemUrl> 전체 시스템 URL 리스트
	 */
	List<PortalSystemUrl> listBySearchCondition(PortalSystemUrlSearchCondition systemUrlSearchCondition);
	
	/**
	 * 페이지 별 시스템 URL 개수
	 * @param systemUrlSearchCondition PortalSystemUrlSearchCondition
	 * @return SearchResult<PortalSystemUrl> 전체 시스템 URL 개수
	 */
	Integer countBySearchCondition(PortalSystemUrlSearchCondition systemUrlSearchCondition);
	
	/**
	 * 시스템 URL 정보
	 * @param param Map(fieldName:다국어로 관리되는 field 이름, localeCode:사용자별 locale 코드, systemUrlId:시스템 URL 코드)
	 * @return PortalSystemUrl 시스템 URL 정보
	 */
	public PortalSystemUrl read(Map<String, String> param);
	
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