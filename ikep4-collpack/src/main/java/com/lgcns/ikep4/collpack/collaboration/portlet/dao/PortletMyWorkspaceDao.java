package com.lgcns.ikep4.collpack.collaboration.portlet.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.collaboration.portlet.model.PortletMyWorkspace;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface PortletMyWorkspaceDao extends GenericDao<PortletMyWorkspace, String> {
	
	/**
	 * 나의 Workspace 중 포틀릿에 저장 목록
	 * @param registerId
	 * @return
	 */
	public List<PortletMyWorkspace> list(String registerId);
	/**
	 * 나의 Workspace 목록
	 * @param registerId
	 * @return
	 */
	public List<PortletMyWorkspace> listAll(String registerId);
	/**
	 *  나의 Workspace 중 포틀릿에 저장된 Workspace의 최근 게시물 목록
	 * @param map
	 * @return
	 */
	public List<PortletMyWorkspace> listItem(Map<String,Object> map);
	
	/**
	 * 나의 Workspace 중 포틀릿에 저장된 목록 삭제
	 * @param registerId
	 */
	public void delete(String registerId);
	
}