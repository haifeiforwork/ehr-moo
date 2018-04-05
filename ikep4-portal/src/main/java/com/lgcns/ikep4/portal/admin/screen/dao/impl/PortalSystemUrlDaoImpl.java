package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.admin.screen.search.PortalSystemUrlSearchCondition;

@Repository
public class PortalSystemUrlDaoImpl extends GenericDaoSqlmap<PortalSystemUrl, String> implements PortalSystemUrlDao {

	/**
	 * sql namespace
	 */
	private static final String NAMESPACE = "portal.admin.screen.portalSystemUrl.";
	
	/**
	 * 페이지 별 시스템 URL 리스트
	 * @param systemUrlSearchCondition PortalSystemUrlSearchCondition
	 * @return SearchResult<PortalSystemUrl> 전체 시스템 URL 리스트
	 */
	public List<PortalSystemUrl> listBySearchCondition(PortalSystemUrlSearchCondition systemUrlSearchCondition) {
		
		List<PortalSystemUrl> systemUrlList = (List<PortalSystemUrl>) this.sqlSelectForList(NAMESPACE + "listBySearchCondition", systemUrlSearchCondition);

		return systemUrlList;
	}
	
	/**
	 * 페이지 별 시스템 URL 개수
	 * @param systemUrlSearchCondition PortalSystemUrlSearchCondition
	 * @return SearchResult<PortalSystemUrl> 전체 시스템 URL 개수
	 */
	public Integer countBySearchCondition(PortalSystemUrlSearchCondition systemUrlSearchCondition) {
		
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", systemUrlSearchCondition);

		return count;
	}
	
	/**
	 * 시스템 URL 정보
	 * @param param Map(fieldName:다국어로 관리되는 field 이름, localeCode:사용자별 locale 코드, systemUrlId:시스템 URL 코드)
	 * @return PortalSystemUrl 시스템 URL 정보
	 */
	public PortalSystemUrl read(Map<String, String> param) {
		
		PortalSystemUrl portalSystemUrl = (PortalSystemUrl) this.sqlSelectForObject(NAMESPACE + "read", param);
		
		return portalSystemUrl;
	}

	@Deprecated
	public boolean exists(String id) {
		
		throw new UnsupportedOperationException();
	}

	/**
	 * 시스템 URL 생성
	 * @param object 시스템 URL 정보
	 * @return 생성된 시스템 URL 아이디
	 */
	public String create(PortalSystemUrl object) {
		
		this.sqlInsert(NAMESPACE + "create", object);
		
		return object.getSystemUrlId();
	}
	
	/**
	 * 시스템 URL 수정
	 * @param object 시스템 URL 정보
	 */
	public void update(PortalSystemUrl object) {
		
		this.sqlInsert(NAMESPACE + "update", object); 
	}
	
	/**
	 * 시스템 URL 삭제
	 * @param id 시스템 URL 아이디
	 */
	public void delete(String id) {
		
		this.sqlDelete(NAMESPACE + "delete", id);
	}
	
	/**
	 * URL 별 시스템 URL 아이디
	 * @param url URL
	 * @return String 시스템 URL 아이디
	 */
	public String readSystemUrlId(String url) {
		return (String) this.sqlSelectForObject(NAMESPACE + "readSystemUrlId", url);
	}

	@Deprecated
	public void remove(String id) {
		
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public PortalSystemUrl get(String id) {
		
		throw new UnsupportedOperationException();
	}	
}