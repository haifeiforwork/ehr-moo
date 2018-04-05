package com.lgcns.ikep4.collpack.collaboration.portlet.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.portlet.dao.PortletMyWorkspaceDao;
import com.lgcns.ikep4.collpack.collaboration.portlet.model.PortletMyWorkspace;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository
public class PortletMyWorkspaceDaoImpl extends GenericDaoSqlmap<PortletMyWorkspace, String> implements PortletMyWorkspaceDao {

	private static final String NAMESPACE = "collpack.collaboration.portlet.dao.portletMyWorkspace.";
	/**
	 * 나의 Workspace 중 포틀릿에 저장 목록
	 */
	public List<PortletMyWorkspace> list(String registerId) {
		
		return (List<PortletMyWorkspace>) this.sqlSelectForList(NAMESPACE + "list", registerId);

	}
	/**
	 * 내가 신청한 WS 전체 목록
	 */
	public List<PortletMyWorkspace> listAll(String registerId) {
		
		return (List<PortletMyWorkspace>) this.sqlSelectForList(NAMESPACE + "listAll", registerId);

	}
	/**
	 * 게시물 최근 목록 
	 */
	public List<PortletMyWorkspace> listItem(Map<String,Object> map) {
		
		return (List<PortletMyWorkspace>) this.sqlSelectForList(NAMESPACE + "listItem", map);

	}	
	@Deprecated
	public boolean exists(String id) {
		
		throw new UnsupportedOperationException();
		
	}

	/**
	 * 내가 신청한 WS중 포틀릿에 등록
	 */
	public String create(PortletMyWorkspace object) {
		return (String)this.sqlInsert(NAMESPACE + "create", object);
	}

	
	/**
	 * 내가 신청한 WS 포틀릿 목록 삭제
	 */
	public void delete(String registerId) {
		
		this.sqlDelete(NAMESPACE + "delete", registerId);
		
	}


	@Deprecated
	public PortletMyWorkspace get(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Deprecated
	public void update(PortletMyWorkspace object) {
		// TODO Auto-generated method stub
		
	}


	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}


	
}