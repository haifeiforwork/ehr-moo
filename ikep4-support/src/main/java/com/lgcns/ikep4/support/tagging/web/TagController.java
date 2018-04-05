/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.tagging.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: TagController.java 17659 2012-03-22 05:03:57Z yu_hs $$
 */
@Controller
@RequestMapping(value = "/support/tagging")
public class TagController extends BaseController {

	@Autowired
	private TagService tagService;
	
	/**
	 * ajax 방식으로 태그 저장
	 * @param tag 태그 객체
	 * @return
	 */
	@RequestMapping("/createTagAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Tag> onSubmitAjax(Tag tag) {
		
		List<Tag> tagList = new ArrayList<Tag>();
		try{
			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");
			
			tag.setRegisterId(user.getUserId());
			tag.setRegisterName(user.getUserName());
			tag.setPortalId(portalId);
			
			tagService.create(tag);
			
			tagList = tagService.listTagByItemId(tag.getTagItemId(), tag.getTagItemType());
			
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
		} 
		
		return tagList;
	}
	
	
	
	/**
	 * 나의 태그
	 * @param teamList 
	 * @return
	 */
	@RequestMapping(value = "/mainMyTag.do")
	public ModelAndView listMyTag(@ModelAttribute("teamList") Map<String, Object> teamList) {
		
		ModelAndView mav = new ModelAndView("/support/tagging/tagMyMain");
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		Properties prop = PropertyLoader.loadProperties("/configuration/tagging-conf.properties");
		
		Tag tag = new Tag();
		tag.setRegisterId(user.getUserId());
		tag.setPortalId(portalId);
		tag.setEndRowIndex(Integer.parseInt(prop.getProperty("tag.mypage.main.pagePer")));
		tag.setStartRowIndex(0);
		
		int tagCount = 0;
		Map<String, Object> tagItem = null;
		
		List<String[]> listItem = new ArrayList<String[]>();
		if(CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_FULL)) {
			listItem.add(new String[]{TagConstants.ITEM_TYPE_PROFILE_PRO, getMessage("ui.support.tagging.listProfileProTag", null, user.getLocaleCode())});	//profile - 전문가 태그
			listItem.add(new String[]{TagConstants.ITEM_TYPE_PROFILE_ATTENTION, getMessage("ui.support.tagging.listProfileConTag", null, user.getLocaleCode())});	//profile - 관심 태그
			listItem.add(new String[]{TagConstants.ITEM_TYPE_WORKSPACE, getMessage("ui.support.tagging.listTeamCollTag", null, user.getLocaleCode())});	//Team Coll 태그
			listItem.add(new String[]{TagConstants.ITEM_TYPE_QNA, getMessage("ui.support.tagging.listQnaTag", null, user.getLocaleCode())});	//Q&A 태그
			listItem.add(new String[]{TagConstants.ITEM_TYPE_FORUM, getMessage("ui.support.tagging.listForumtag", null, user.getLocaleCode())});	//Forum 태그
			listItem.add(new String[]{TagConstants.ITEM_TYPE_IDEATION, getMessage("ui.support.tagging.listIdationTag", null, user.getLocaleCode())});	//Ideation 태그
		} else {
			listItem.add(new String[]{TagConstants.ITEM_TYPE_BBS, getMessage("ui.support.tagging.listBbsTag", null, user.getLocaleCode())});	//BBS 태그
			listItem.add(new String[]{TagConstants.ITEM_TYPE_CAFE, getMessage("ui.support.tagging.listCafeTag", null, user.getLocaleCode())});	//Cafe 태그
		}
		
		for(Iterator<String[]> it = listItem.iterator(); it.hasNext();){
			String[] item = it.next();
			
			tag.setTagItemType(item[0].equals(TagConstants.ITEM_TYPE_WORKSPACE) ? (String)teamList.get("teamIdes")+","+item[0] : item[0]);
			tagCount = tagService.getCount(tag);
			
			tagItem = new HashMap<String, Object>();
			tagItem.put("code", item[0]);
			tagItem.put("title", item[1]);
			tagItem.put("count", tagCount);
			tagItem.put("tags", tagCount > 0 ? tagService.listTagByItemType(tag) : null);
			
			items.add(tagItem);
		}
		
		mav.addObject("items", items);
		return mav;
	}
	
	
	
	/**
	 * 모든 태그 리스트
	 * @param tag 태그 객체
	 * @return
	 */
	@RequestMapping(value = "/listAllTag.do", method = RequestMethod.GET)
	public ModelAndView list(Tag tag){
		
		tag.setListType(TagConstants.LIST_TYPE_ALL);
		
		User user = (User) getRequestAttribute("ikep.user");
		
		if(tag.getIsMy() == TagConstants.IS_MY){
			tag.setRegisterId(user.getUserId());
		}
		
		ModelAndView mav = listAction(tag, "/support/tagging/tagList");
		mav.addObject("tagItemType", tag.getTagItemType());
		return mav;
	}
	
	
	/**
	 * collaboration 리스트
	 * @param tag	태그 객체
	 * @return
	 */
	@RequestMapping(value = "/listCollTag.do", method = RequestMethod.GET)
	public ModelAndView collList(Tag tag){
		
		User user = (User) getRequestAttribute("ikep.user");
		
		tag.setListType(TagConstants.LIST_TYPE_COLL);
		tag.setRegisterId(user.getUserId());
		
		ModelAndView mav = listAction(tag, "/support/tagging/tagList");
		mav.addObject("tagItemType", tag.getTagItemType());
		return mav;
	}
	
	/**
	 * ajax로 태그 검색
	 * @param tag	태그 객체
	 * @return
	 */
	@RequestMapping("/tagSearchAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView searchAjaxList(Tag tag){
		
		tag.setListType(TagConstants.LIST_TYPE_SEARCH);
		
		ModelAndView mav = listAction(tag, "/support/tagging/tagListSearchAjax");
		
		return mav;
	}
	
	/**
	 * 태그 검색
	 * @param tag	태그 객체
	 * @return
	 */
	@RequestMapping(value = "/tagSearch.do", method = RequestMethod.GET)
	public ModelAndView searchList(Tag tag){
		
		tag.setListType(TagConstants.LIST_TYPE_SEARCH);
		
		ModelAndView mav = listAction(tag, "/support/tagging/tagListSearch");
		return mav;
	}
	
	
	/**
	 * ajax로 추천태그 리스트
	 * @param tag
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/tagRecommend.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map recommendList(Tag tag){
		
		Map map = new HashMap();
		try{
			tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);
			
			String[] userIdes = tag.getRegisterId().split(",");
			
			List<String> userList = new ArrayList<String>();
			for(String userId: userIdes){
				userList.add(userId);
			}
			tag.setUserIdList(userList);
			tag.setRegisterId(null);
			
			ModelAndView mav = listAction(tag, "/support/tagging/tagListSearchAjax");
			
			Map modelMap = mav.getModel();
			
			map.put("count", modelMap.get("count")); 
			map.put("tagList", modelMap.get("tagList")); 
			
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		return map;
	}
	
	
	/**
	 * ajax로 인기태그 리스트
	 * @param tag 태그 객체
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/tagPopular.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map popularList(Tag tag){
		
		Map map = new HashMap();
		try{
			tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);
			
			ModelAndView mav = listAction(tag, "/support/tagging/tagListSearchAjax");
			
			Map modelMap = mav.getModel();
			
			map.put("count", modelMap.get("count")); 
			map.put("tagList", modelMap.get("tagList")); 
			
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		return map;
	}
	
	
	
	/**
	 * 태그 리스트 - 공통
	 * @param tag
	 * @param returnUrl
	 * @return
	 */
	public ModelAndView listAction(Tag tag, String returnUrl) {

		ModelAndView mav = new ModelAndView(returnUrl);
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		tag.setPortalId(portalId);
		
		//페이징조건
		Properties prop = PropertyLoader.loadProperties("/configuration/tagging-conf.properties");
		
		if(tag.getPagePer() == 0){
			tag.setPagePer(Integer.parseInt(prop.getProperty("tag.manager.list.pagePer")));	
		}
		
		
		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setPageIndex(tag.getPageIndex());
		searchCondition.setPagePerRecord(tag.getPagePer());
		
		
		//카운트
		int count = 0;
		if(tag.getListType() != null && tag.getListType().equals(TagConstants.LIST_TYPE_SEARCH)){
			count = tagService.getCountSearch(tag);
		} else {
			count = tagService.getCount(tag);
		}
		
		mav.addObject("count", count);
		
		searchCondition.terminateSearchCondition(count); 
		
		mav.addObject("searchCondition", searchCondition);
		
		//게시물 리스트 가져오기
		tag.setEndRowIndex(searchCondition.getEndRowIndex());
		tag.setStartRowIndex(searchCondition.getStartRowIndex());
		
		List<Tag> tagList = new ArrayList<Tag>();
		
		if(tag.getListType() != null && tag.getListType().equals(TagConstants.LIST_TYPE_SEARCH)){
			tagList = tagService.listTagSearch(tag);
		} else {
			tagList = tagService.list(tag);
		}
		
		
		for(Tag tagObject : tagList){
			
			List<Tag> itemTagList = tagService.listTagByItemId(tagObject.getTagItemId(), tagObject.getTagItemType());
			
			tagObject.setTagList(itemTagList);
		}
		
		mav.addObject("tagList", tagList);
		
		mav.addObject("listType", tag.getListType());
		
		return mav;
	}
	
	/**
	 * 태그 삭제
	 * @param tagItemId		게시물 ID
	 * @param tagItemType	게시물 타입
	 * @return
	 */
	@RequestMapping(value = "/deleteTag.do", method = RequestMethod.GET)
	public String remove(@RequestParam("tagItemId") String tagItemId
						,@RequestParam("tagItemType") String tagItemType) {

		tagService.delete(tagItemId, tagItemType);

		return "redirect:listTag.do";
	}
	
	
	/**
	 * collaboration team id 리스트
	 * @return
	 */
	@ModelAttribute("teamList")
	public Map<String, Object> teamList(){
		Map<String, Object> map = new HashMap<String, Object>();
		if(CommonConstant.PACKAGE_VERSION.equals(CommonConstant.IKEP_VERSION_FULL)) { 
			String portalId = (String) getRequestAttribute("ikep.portalId");
			List<Tag> list = tagService.listTeamType(portalId);
			
			String teamIdes = "";
			for(int i = 0; i <list.size(); i++){
				Tag team = list.get(i);
				if(i != 0){teamIdes += ",";}
				teamIdes += team.getTeamTypeId();
			}
			
			map.put("list", list);
			map.put("teamIdes", teamIdes+","+TagConstants.ITEM_TYPE_WORKSPACE);
		}
		
		return map;
	}
	
	

}
