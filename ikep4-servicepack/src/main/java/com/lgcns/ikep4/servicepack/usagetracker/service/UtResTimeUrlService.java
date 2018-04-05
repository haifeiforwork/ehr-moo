package com.lgcns.ikep4.servicepack.usagetracker.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeUrl;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

@Transactional
public interface UtResTimeUrlService extends GenericService<UtResTimeUrl,String> {
	
	/**
	 * 응답시간 URL 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtResTimeUrl> listBySearchCondition(UtSearchCondition searchCondition) ;
	
	/**
	 * 응답시간 URL 목록
	 * @param portalId
	 * @return
	 */
	public List<UtResTimeUrl> listResTimeUrl(String portalId);
	
	/**
	 * 선택된것 삭제
	 * @param resTimeUrlId
	 */
	public void removeCheckedAll(String[] resTimeUrlId);
	
	/**
	 * URL존재여부
	 * @param resTimeUrl
	 */
	public boolean existsURL(String resTimeUrl);
	
	/**
	 * 등록된 URL, 사용여부 체크 조회
	 * @param utResTimeUrl
	 * @return
	 */
	public UtResTimeUrl getResTimeUrl(UtResTimeUrl utResTimeUrl);
}