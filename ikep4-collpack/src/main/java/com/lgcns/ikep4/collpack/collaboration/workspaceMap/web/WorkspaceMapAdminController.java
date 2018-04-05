/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.WorkspaceMap;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.search.MapBlockPageCondition;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.service.WorkspaceMapAdminService;
import com.lgcns.ikep4.collpack.common.tree.TreeManager;
import com.lgcns.ikep4.collpack.common.tree.TreeNode;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
//import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * WorkspaceMap Controller
 *
 * @author 홍정관
 * @version $Id: WorkspaceMapAdminController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/collaboration/workspaceMap/admin")
@SessionAttributes("WorkspaceMap")
public class WorkspaceMapAdminController extends MapTreeController {

	@Autowired
	private WorkspaceMapAdminService workspaceMapAdminService;

	
	//@Autowired
	//private ACLService aclService;
	
	/**
	 * Map 관리 페이지 Tree
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping(value = "/workspaceMapAdminMain")
	public ModelAndView workspaceMapAdminMain(@RequestParam(value="workspaceId")String workspaceId,
			@RequestParam(value="mapId", required=false) String mapId){

		WorkspaceMap rootMap = new WorkspaceMap();
		rootMap = workspaceMapAdminService.readWorkspaceMapRoot(workspaceId);
		
		Map<String, String> treeMap = new HashMap<String, String>();
    	treeMap.put("workspaceId", workspaceId);
    	treeMap.put("mapParentId", rootMap.getMapId());
    	
    	List<WorkspaceMap> mapList =  workspaceMapAdminService.listByWorkspaceMapRootId(treeMap);
    	
    	List<TreeNode> treeNodeList = makeListMapTreeNodes(mapList);
		String mapTagJSON = TreeManager.getJSON(treeNodeList);
    	
    	
    	// view 연결
		ModelAndView mav = new ModelAndView();

		mav.addObject("mapTagJSON", mapTagJSON);
		mav.addObject("rootMap", rootMap);
		mav.addObject("workspaceId",workspaceId);
		mav.addObject("mapId",mapId);
		mav.addObject("workspaceMap",new WorkspaceMap());
		mav.setViewName("collpack/collaboration/workspaceMap/admin/workspaceMapAdminMain");
		
		return mav;
	}
	
	/**
	 * 
	 * MAP List 반환
	 * @param categoryParentId
	 * @return
	 */
	@RequestMapping(value = "/listChildMaps")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TreeNode> listChildMaps(@RequestParam("workspaceId") String workspaceId, @RequestParam("mapParentId") String mapParentId) {
		List<WorkspaceMap> workspaceMapList = null;
		List<TreeNode> treeNodeList = null;

    	try {
    		Map<String,String> map = new HashMap<String,String>();
    		map.put("workspaceId", workspaceId);
    		map.put("mapParentId", mapParentId);
    		workspaceMapList = workspaceMapAdminService.listByWorkspaceMapRootId(map);
    		treeNodeList = makeListMapTreeNodes(workspaceMapList);
        	return treeNodeList;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("categoryListAjax", ex);
    	}
	}
	
	/**
	 * Map 생성
	 * @param workspaceMap
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createMap", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String onSubmit(@Valid WorkspaceMap workspaceMap, BindingResult result, SessionStatus status) {
		
		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			// BindingResult와 BaseController의 MessageSource를 parameter로 전달해야
			// 합니다.
			throw new IKEP4AjaxValidationException(result, messageSource);
		}
		//String mapId = workspaceMap.getMapId();
		WorkspaceMap resultMap = new WorkspaceMap();
		try {
			User user = (User) getRequestAttribute("ikep.user");
			
			workspaceMap.setRegisterId(user.getUserId());
			workspaceMap.setRegisterName(user.getUserName());
			workspaceMap.setUpdaterId(user.getUserId());
			workspaceMap.setUpdaterName(user.getUserName());

			try {
				resultMap = workspaceMapAdminService.createMap(workspaceMap);
				String appendJSON = TreeManager.getJSON(makeTreeNode(resultMap));
				status.setComplete();
				return appendJSON;
			} catch(Exception ex) {
	    		throw new IKEP4AjaxException("workspaceMapListAjax", ex);
	    	}
	    	
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		
	}
	

	/**
	 * 
	 * 맵 수정
	 * @param category
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/updateMap")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateCategory(@Valid WorkspaceMap workspaceMap, BindingResult result, SessionStatus status) {
		
		if (result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			// BindingResult와 BaseController의 MessageSource를 parameter로 전달해야
			// 합니다.
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// Session 정보
		User user = getSessionUser();
		workspaceMap.setUpdaterId(user.getUserId());
		workspaceMap.setUpdaterName(user.getUserName());

		try {
			workspaceMapAdminService.updateWithTags(workspaceMap);
			String appendJSON = TreeManager.getJSON(makeTreeNode(workspaceMap));
			status.setComplete();
			return appendJSON;
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("workspaceMapAjax", ex);
    	}
	}

	/**
	 *
	 * 맵 삭제
	 * @param category
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/deleteMap")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void deleteCategory(@RequestParam("workspaceId") String workspaceId,@RequestParam("mapId") String mapId, SessionStatus status) {


		try {
			workspaceMapAdminService.deleteWithTagsHierarchy(workspaceId,mapId);
			status.setComplete();
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("workspaceMapAjax", ex);
    	}
	}
	
	/**
	 * 
	 * 맵 이동
	 * @param sourceId
	 * @param sourceParentId
	 * @param sourceSortOrder
	 * @param targetParentId
	 * @param targetSortOrder
	 * @param status
	 */
	@RequestMapping(value = "/moveWorkspaceMap")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void moveWorkspaceMap(
			@RequestParam("sourceId") String sourceId,
			@RequestParam("sourceParentId") String sourceParentId,
			@RequestParam("sourceSortOrder") int sourceSortOrder,
			@RequestParam("targetParentId") String targetParentId,
			@RequestParam("targetSortOrder") int targetSortOrder,
			SessionStatus status) {


		try {
			if (sourceParentId.equals(targetParentId)) {
				workspaceMapAdminService.moveInnerMap(sourceId, sourceParentId, sourceSortOrder, targetSortOrder);
			} else {
				workspaceMapAdminService.moveOuterMap(sourceId, sourceParentId, sourceSortOrder, targetParentId, targetSortOrder);
			}
			status.setComplete();
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("workspaceMapAjax", ex);
    	}
	}
	

	/**
	 * Workspace 맵 목록 조회
	 * @param workspaceId
	 * @param mapId
	 * @param mapName
	 * @param tag
	 * @param pageCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/workspaceMapListView")
	public ModelAndView workspaceMapListView(
			@RequestParam(value="workspaceId") String workspaceId,
			@RequestParam(value="mapId") String mapId,
			@RequestParam(value="mapName") String mapName,
			@RequestParam(value="tag", required = false) String tag,
			MapBlockPageCondition pageCondition,
			BindingResult result,
			SessionStatus status) {
		

		if(StringUtil.isEmpty(workspaceId)){
			workspaceId = pageCondition.getWorkspaceId();
		}
		
		if(StringUtil.isEmpty(mapId)){
			mapId = pageCondition.getMapId();
		}
		
		if(StringUtil.isEmpty(mapName)){
			mapName = pageCondition.getMapName();
		}
		
		//수집된 정보의 태그정보 수집 or 선택한 태그정보 수집
		Map<String,String> param = new HashMap<String,String>();
		param.put("workspaceId", workspaceId);
		param.put("mapId", mapId);
		
		List<WorkspaceMap> tagList  = workspaceMapAdminService.readTagList(param);
		
		/**if(StringUtil.isEmpty(tag))
		{
			for(int i=0;i<tagList.size();i++)
			{
				tag = tagList.get(i).getTag();
				break;
			}
		}**/
		if(StringUtil.isEmpty(tag) && tagList.size()>0 ) {
			tag = tagList.get(0).getTag();
		}
		pageCondition.setWorkspaceId(workspaceId);
		pageCondition.setMapId(mapId);
		pageCondition.setTag(tag);
		
		//리스팅
		List<WorkspaceMap> tagItemList  = workspaceMapAdminService.listItemByTag(pageCondition);
			
		// view 연결
		ModelAndView mav = new ModelAndView("collpack/collaboration/workspaceMap/admin/workspaceMapListView");
		
		mav.addObject("mapId", mapId);
		mav.addObject("mapName", mapName);
		mav.addObject("workspaceId", workspaceId);
		mav.addObject("pageCondition", pageCondition);
		mav.addObject("tagItemList", tagItemList);
		mav.addObject("tagList", tagList);
		mav.addObject("tag", tag);
		
		status.setComplete();
		
		return mav;
	}	
}