package com.lgcns.ikep4.collpack.collaboration.portlet.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.collaboration.portlet.dao.PortletMyWorkspaceDao;
import com.lgcns.ikep4.collpack.collaboration.portlet.model.PortletMyWorkspace;
import com.lgcns.ikep4.collpack.collaboration.portlet.service.PortletMyWorkspaceService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * 
 * 포틀릿 오늘의 명언 Service 구현 클래스
 *
 * @author 박철종
 * @version $Id: PortletMyWorkspaceServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("PortletMyWorkspaceService")
public class PortletMyWorkspaceServiceImpl extends GenericServiceImpl<PortletMyWorkspace, String> implements PortletMyWorkspaceService {

	@Autowired
	private PortletMyWorkspaceDao portletMyWorkspaceDao;
	
	/**
	 * 내가 신청한 WS 목록 -- 포틀릿에 등록 목록
	 */
	public List<PortletMyWorkspace> list(String registerId) {
		return portletMyWorkspaceDao.list(registerId);
	}
	/**
	 * 내가 신청한 WS 목록
	 */
	public List<PortletMyWorkspace> listAll(String registerId) {
		return portletMyWorkspaceDao.listAll(registerId);
	}
		
	/**
	 * 최근 게시물 목록
	 */
	public List<PortletMyWorkspace> listItem(List<PortletMyWorkspace> myWorkspaceList,String registerId,int listSize) {
		
		List<PortletMyWorkspace> wsItemList = new ArrayList<PortletMyWorkspace>();
		int k=0;
		for(int i=0; i<myWorkspaceList.size();i++) {
			
			PortletMyWorkspace myWorkspace = myWorkspaceList.get(i);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("workspaceId", myWorkspace.getWorkspaceId());
			map.put("registerId", registerId);
			map.put("listSize", listSize);
			
			List<PortletMyWorkspace> itemList = portletMyWorkspaceDao.listItem(map);
			
			for(int j=0;j<itemList.size();j++) {
				PortletMyWorkspace tmpMyWorkspace = itemList.get(j);
				wsItemList.add(k,tmpMyWorkspace);
				k++;
			}
			//wsItemList.add(i, (PortletMyWorkspace) itemList);
			
			//wsItemList.addAll(itemList);

		}
		return wsItemList;
	}
	/**
	 * 포틀릿 등록 - WS 목록
	 */
	public String create(PortletMyWorkspace portletMyWorkspace) {
		
		for(String workspaceId:portletMyWorkspace.getWorkspaceIds())
		{
			portletMyWorkspace.setWorkspaceId(workspaceId);	
			portletMyWorkspaceDao.create(portletMyWorkspace);
		}
		return portletMyWorkspace.getRegisterId();
	}

	/**
	 * 포틀릿 삭제 -WS 목록
	 */
	public void delete(String registerId) {
		portletMyWorkspaceDao.delete(registerId);
	}
	


}