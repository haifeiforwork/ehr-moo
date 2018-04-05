package com.lgcns.ikep4.portal.portlet.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.portlet.model.PublicRss;

/**
 * 포탈 RSS Service
 *
 * @author 임종상
 * @version $Id: PortletRssService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface PublicRssService extends GenericService<PublicRss, String> {
	
	/**
	 * 포틀릿 RSS 등록
	 * @param portletRss RSS PORTLET 모델
	 */
	public void createPublicRss(PublicRss publicRss);
	
	/**
	 * 포틀릿 RSS 조회
	 * @param portletRss RSS PORTLET 모델
	 * @return PortletRss 포틀릿 RSS 정보
	 */
	public PublicRss readPublicRss(String portletConfigId);

	public List<PublicRss> getPublicRssList();

}