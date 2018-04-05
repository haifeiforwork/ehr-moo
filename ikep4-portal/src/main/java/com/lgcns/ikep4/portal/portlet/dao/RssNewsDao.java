package com.lgcns.ikep4.portal.portlet.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.PublicRss;

/**
 * 
 * RSS 포틀릿 Dao
 *
 * @author 임종상
 * @version $Id: PortletRssDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface RssNewsDao extends GenericDao<PublicRss, String> {
	
	public void remove(String portletConfigId);
	
	public void create(Map<String, Object> parameter);
}