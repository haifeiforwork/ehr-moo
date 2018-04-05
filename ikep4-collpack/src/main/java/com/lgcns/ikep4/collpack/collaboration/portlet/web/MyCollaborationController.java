package com.lgcns.ikep4.collpack.collaboration.portlet.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.portlet.model.PortletMyWorkspace;
import com.lgcns.ikep4.collpack.collaboration.portlet.service.PortletMyWorkspaceService;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

@Controller
@RequestMapping(value = "/collpack/collaboration/portlet/myCollaboration")
public class MyCollaborationController extends BaseController{
	
	@Autowired
	private PortletMyWorkspaceService portletMyWorkspaceService;
	
	@Autowired
	private PortalPortletUserConfigService portalPortletUserConfigService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 포틀릿 메인
	 * @param portletConfigId
	 * @return
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("collpack/collaboration/portlet/myCollaboration/normalView");
		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if (listSize < 1) {
			listSize = 3;	
		}
		
		try{
			/*
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", user.getPortalId());
			map.put("memberId", user.getUserId());
			map.put("listSize",  Integer.toString(listSize));
	
			List<Workspace> workspaceList = workspaceService.listMyCollaboration(map);
			*/
			
			// 목록 캐시에서 조회
			List<PortletMyWorkspace> myWorkspaceList = (List<PortletMyWorkspace>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
			
			if(myWorkspaceList == null) {
				myWorkspaceList = portletMyWorkspaceService.list(user.getUserId());
				
				// 캐시에 저장
				cacheService.addCacheElementPortletContent(portletId, portletConfigId, myWorkspaceList);
			}
			
			List<PortletMyWorkspace> wsItemList = portletMyWorkspaceService.listItem(myWorkspaceList,user.getUserId(),listSize);
			
			mav.addObject("listSize", listSize);
			mav.addObject("wsItemList", wsItemList);
			mav.addObject("myWorkspaceList", myWorkspaceList);
			mav.addObject("portletConfigId", portletConfigId);

		}catch(Exception exception){
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\n 포탈 포틀릿 페이지 오류...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
		
		return mav;
	}
	/**
	 * 포틀릿 환경설정
	 * @param portletConfigId
	 * @return
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("collpack/collaboration/portlet/myCollaboration/configView");

		try {
			User user = (User) getRequestAttribute("ikep.user");
			
			int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
			if (listSize < 1) {
				listSize = 5;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", user.getPortalId());
			map.put("memberId", user.getUserId());
			map.put("listSize",  Integer.toString(listSize));
			
			List<PortletMyWorkspace> myWorkspaceList = portletMyWorkspaceService.listAll(user.getUserId());
			//List<Workspace> workspaceList = workspaceService.listMyCollaboration(map);

			mav.addObject("listSize", listSize);
			//mav.addObject("workspaceList", workspaceList);
			mav.addObject("myWorkspaceList", myWorkspaceList);
			mav.addObject("portletConfigId", portletConfigId);
			mav.addObject("portletId", portletId);

		}catch(Exception exception){
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\n 포틀릿 환경설정 오류...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}

		return mav;
	}

	/**
	 * 포틀릿 환경설정 저장
	 * @param portletMyWorkspace
	 * @param result
	 * @param status
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveMyCollaboration.do", method = RequestMethod.POST)
	public @ResponseBody boolean saveMyCollaboration(
			PortletMyWorkspace portletMyWorkspace,
			BindingResult result, SessionStatus status, HttpServletRequest request) {
		
		try {
		
			User user = (User) getRequestAttribute("ikep.user");
			
			ModelBeanUtil.bindRegisterInfo(portletMyWorkspace, user.getUserId(), user.getUserName());
			
			
			//portalPortletUserConfigService.setPortletUserConfig(portletMyWorkspace.getPortletConfigId(),"List",);
			
			portletMyWorkspaceService.delete(user.getUserId());
			if(portletMyWorkspace.getWorkspaceIds()!=null && portletMyWorkspace.getWorkspaceIds().size()>0) {
				portletMyWorkspaceService.create(portletMyWorkspace);
			}
			
			status.setComplete();

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 포틀릿 환경설정 저장 실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());			
			/**if(e instanceof IKEP4ApplicationException) {
				throw (IKEP4ApplicationException)e;
			} else {
				throw new IKEP4ApplicationException("", e);
			}**/
		}

		
		return true;
	}
	/**
	 * 포틀릿 메인 최대화
	 * @param portletConfigId
	 * @return
	 */
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("collpack/collaboration/portlet/myCollaboration/maxView");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
			if (listSize < 1) {
				listSize = 5;
			}
			
			/*
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", user.getPortalId());
			map.put("memberId", user.getUserId());
			map.put("listSize",  Integer.toString(listSize));
	
			List<Workspace> workspaceList = workspaceService.listMyCollaboration(map);
			*/
			
			// 목록 캐시에서 조회
			List<PortletMyWorkspace> myWorkspaceList = (List<PortletMyWorkspace>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
			
			if(myWorkspaceList == null) {
				myWorkspaceList = portletMyWorkspaceService.list(user.getUserId());
				
				// 캐시에 저장
				cacheService.addCacheElementPortletContent(portletId, portletConfigId, myWorkspaceList);
			}
			
			List<PortletMyWorkspace> wsItemList = portletMyWorkspaceService.listItem(myWorkspaceList,user.getUserId(),listSize);
			
			mav.addObject("listSize", listSize);
			mav.addObject("wsItemList", wsItemList);
			mav.addObject("myWorkspaceList", myWorkspaceList);
			mav.addObject("portletConfigId", portletConfigId);

		}catch(Exception exception){
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\n 포틀릿 메인 최대화 오류...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}

		return mav;
	}	
}