package com.lgcns.ikep4.portal.portlet.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.portlet.dao.RssNewsDao;
import com.lgcns.ikep4.portal.portlet.model.PublicRss;

/**
 * 포틀릿 RSS DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortletRssDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("rssNewsDao")
public class RssNewsDaoImpl extends GenericDaoSqlmap<PublicRss, String> implements RssNewsDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "portal.portlet.dao.rssNews.";
	
	public void remove(String portletConfigId) {
		this.sqlInsert(NAMESPACE + "delete", portletConfigId);
	}
	
	public void create(Map<String, Object> parameter) {
		this.sqlInsert(NAMESPACE + "create",parameter);
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public PublicRss get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}	

	public void update(PublicRss arg0) {
		// TODO Auto-generated method stub
		
	}

	public String create(PublicRss arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}