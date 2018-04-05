package com.lgcns.ikep4.portal.portlet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.PortletRssDao;
import com.lgcns.ikep4.portal.portlet.model.PortletRss;
import com.lgcns.ikep4.portal.portlet.service.PortletRssService;

/**
 * 
 * 포틀릿 RSS Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortletRssServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("portletRssService")
public class PortletRssServiceImpl extends GenericServiceImpl<PortletRss, String> implements PortletRssService {

	@Autowired
	private PortletRssDao portletrssDao;

	/**
	 * 포틀릿 RSS 등록
	 * @param portletRss RSS PORTLET 모델
	 */
	public void createPortletRss(PortletRss portletRss) {
		PortletRss temp = portletrssDao.getPortletRss(portletRss.getPortletConfigId());
		
		if(temp == null) {
			//등록된 설정이 있으면 인서트
			portletrssDao.createPortletRss(portletRss);
		} else {
			//등록된 설정이 없으면 업데이트
			portletrssDao.updatePortletRss(portletRss);
		}
	}

	/**
	 * 포틀릿 RSS 조회
	 * @param portletRss RSS PORTLET 모델
	 * @return PortletRss 포틀릿 RSS 정보
	 */
	public PortletRss readPortletRss(String portletConfigId) {
		return portletrssDao.getPortletRss(portletConfigId);
	}
}