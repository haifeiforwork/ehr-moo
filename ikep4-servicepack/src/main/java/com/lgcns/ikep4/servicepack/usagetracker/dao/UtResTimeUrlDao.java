package com.lgcns.ikep4.servicepack.usagetracker.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeUrl;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

public interface UtResTimeUrlDao extends GenericDao<UtResTimeUrl,String> {

	/**
	 * 응답시간 URL 카운트
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(UtSearchCondition searchCondition);
	
	/**
	 * 응답시간 URL 리스트
	 * @param searchCondition
	 * @return
	 */
	List<UtResTimeUrl> listBySearchCondition(UtSearchCondition searchCondition);
	
	/**
	 * 응답시간 URL 목록
	 * @param portalId
	 * @return
	 */
	List<UtResTimeUrl> listResTimeUrl(String portalId);
	
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