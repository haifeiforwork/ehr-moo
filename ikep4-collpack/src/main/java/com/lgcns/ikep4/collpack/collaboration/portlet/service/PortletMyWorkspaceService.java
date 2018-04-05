/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.portlet.service;

import java.util.List;

import com.lgcns.ikep4.collpack.collaboration.portlet.model.PortletMyWorkspace;
import com.lgcns.ikep4.framework.core.service.GenericService;



/**
 * 포틀릿 오늘의 명언 관리 서비스
 * 
 * @author happyi1018
 * @version $Id: PortletMyWorkspaceService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface PortletMyWorkspaceService extends GenericService<PortletMyWorkspace, String> {

	/**
	 * 나의 Workspace 중 포틀릿에 저장 목록
	 * @param registerId
	 * @return
	 */
	public List<PortletMyWorkspace> list(String registerId);
	/**
	 *  나의 Workspace 목록
	 * @param registerId
	 * @return
	 */
	public List<PortletMyWorkspace> listAll(String registerId);
	/**
	 * 나의 Workspace 중 포틀릿에 저장된 Workspace의 최근 게시물 목록
	 * @param myWorkspaceList
	 * @param registerId
	 * @param listSize
	 * @return
	 */
	public List<PortletMyWorkspace> listItem(List<PortletMyWorkspace> myWorkspaceList,String registerId,int listSize) ;
	
	/**
	 * 나의 Workspace 중 선택한 Workspace 포틀릿에 저장
	 */
	public String create(PortletMyWorkspace portletMyWorkspace);
	
	/**
	 *  나의 Workspace 중 포틀릿에 저장된 목록 삭제
	 */
	public void delete(String registerId);
	
}
