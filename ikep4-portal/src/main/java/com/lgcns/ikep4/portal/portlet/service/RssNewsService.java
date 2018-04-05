package com.lgcns.ikep4.portal.portlet.service;

import java.util.List;

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
public interface RssNewsService extends GenericService<PortletRss, String> {
	
    /**
     * 포틀릿 폴더 삭제
     * @param portletConfigId
     */
	public void deleteSelectedFolder(String portletConfigId);
	
	/**
	 * 포틀릿 폴더 등록
	 * @param categoryIdList
	 * @param registerId
	 * @param portletConfigId
	 */
	public void saveSelectedFolder(List<String> categoryIdList, String registerId,String portletConfigId);
}