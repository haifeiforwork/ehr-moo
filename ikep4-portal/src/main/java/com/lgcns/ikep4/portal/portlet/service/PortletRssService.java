package com.lgcns.ikep4.portal.portlet.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.portlet.model.PortletRss;

/**
 * 포탈 RSS Service
 *
 * @author 임종상
 * @version $Id: PortletRssService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PortletRssService extends GenericService<PortletRss, String> {
	
	/**
	 * 포틀릿 RSS 등록
	 * @param portletRss RSS PORTLET 모델
	 */
	public void createPortletRss(PortletRss portletRss);
	
	/**
	 * 포틀릿 RSS 조회
	 * @param portletRss RSS PORTLET 모델
	 * @return PortletRss 포틀릿 RSS 정보
	 */
	public PortletRss readPortletRss(String portletConfigId);
}