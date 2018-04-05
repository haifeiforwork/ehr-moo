package com.lgcns.ikep4.portal.portlet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.portlet.dao.RssNewsDao;
import com.lgcns.ikep4.portal.portlet.model.PortletRss;
import com.lgcns.ikep4.portal.portlet.service.RssNewsService;

/**
 * 
 * 포틀릿 RSS Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortletRssServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("rssNewsService")
public class RssNewsServiceImpl extends GenericServiceImpl<PortletRss, String> implements RssNewsService {

	@Autowired
	private RssNewsDao rssNewsDao;

	/**
     * 포틀릿 폴더 삭제
     * @param portletConfigId
     */
	public void deleteSelectedFolder(String portletConfigId)
	{
		this.rssNewsDao.remove(portletConfigId);
	}
	
	/**
	 * 포틀릿 폴더 등록
	 * @param categoryIdList
	 * @param registerId
	 * @param portletConfigId
	 */
	public void saveSelectedFolder(List<String> categoryIdList, String registerId,String portletConfigId)
	{
        Map<String, Object> map = new HashMap<String, Object>();

		map.put("registerId", registerId);
		map.put("portletConfigId", portletConfigId);
		map.put("updaterId", registerId);    
		for(String boardId : categoryIdList) {
			map.put("categoryId", boardId); 
			this.rssNewsDao.create(map);
		} 
	}
}