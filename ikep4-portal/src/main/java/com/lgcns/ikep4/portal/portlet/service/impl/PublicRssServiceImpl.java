package com.lgcns.ikep4.portal.portlet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.PublicRssDao;
import com.lgcns.ikep4.portal.portlet.model.PublicRss;
import com.lgcns.ikep4.portal.portlet.service.PublicRssService;

/**
 * 
 * 포틀릿 RSS Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortletRssServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("publicRssService")
public class PublicRssServiceImpl extends GenericServiceImpl<PublicRss, String> implements PublicRssService {

	@Autowired
	private PublicRssDao publicRssDao;

	/**
	 * 포틀릿 RSS 등록
	 * @param portletRss RSS PORTLET 모델
	 */
	public void createPublicRss(PublicRss publicRss) {
		publicRssDao.deleteAll();
		
		String[] portletConfigIds = publicRss.getPortletConfigId().split(",");
		String[] rssUrls = publicRss.getRssUrl().split(",");
		
		for(int i = 0; i < publicRss.getTabCount() ; i++){
			publicRss.setPortletConfigId(portletConfigIds[i]);
			publicRss.setRssUrl(rssUrls[i]);
		
			//등록된 설정이 있으면 인서트
			publicRssDao.createPublicRss(publicRss);
		}
	}

	/**
	 * 포틀릿 RSS 조회
	 * @param portletRss RSS PORTLET 모델
	 * @return PortletRss 포틀릿 RSS 정보
	 */
	public PublicRss readPublicRss(String portletConfigId) {
		return publicRssDao.getPublicRss(portletConfigId);
	}
	
	/**
	 * 포틀릿 RSS 조회
	 * @param portletRss RSS PORTLET 모델
	 * @return PortletRss 포틀릿 RSS 정보
	 */
	public List<PublicRss> getPublicRssList() {  
		return this.publicRssDao.getPublicRssList();
	}
	
}