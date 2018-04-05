/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.forum.constants.ForumConstants;
import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrParticipant;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.collpack.forum.service.FrCategoryService;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionService;
import com.lgcns.ikep4.collpack.forum.service.FrFeedbackService;
import com.lgcns.ikep4.collpack.forum.service.FrItemService;
import com.lgcns.ikep4.collpack.forum.service.FrLinereplyService;
import com.lgcns.ikep4.collpack.forum.service.FrParticipantService;
import com.lgcns.ikep4.collpack.forum.service.FrUserActivitiesService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;


/**
 * Qna controller
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: ForumController.java 16903 2011-10-25 01:37:27Z giljae $$
 */
@Controller
@SessionAttributes("frDiscussion")
public class ForumController extends ForumBaseController {

	@Autowired
	private FrItemService frItemService;

	@Autowired
	private FrDiscussionService frDiscussionService;

	@Autowired
	private FrLinereplyService frLinereplyService;

	@Autowired
	private FrUserActivitiesService frUserActivitiesService;

	@Autowired
	private FrParticipantService frParticipantService;

	@Autowired
	private TagService tagService;

	@Autowired
	private FrFeedbackService frFeedbackService;
	
	@Autowired
	private FrCategoryService frCategoryService;
	
	@Autowired
	private PortalFavoriteService portalfavoriteService;
	
	@Autowired
	private FileService fileService;

	/**
	 * 토론주제 등록 폼
	 * @param discussionId	주제 ID
	 * @param categoryId	카테고리 ID
	 * @param isAdmin		
	 * @param categoryList
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping("/discussionForm.do")
	public ModelAndView getForm(@RequestParam(value = "discussionId", required = false) String discussionId
								,@RequestParam(value = "categoryId", required = false) String categoryId
								,@ModelAttribute("isAdmin") boolean isAdmin
								,@ModelAttribute("categoryList") List<FrCategory> categoryList
								,@RequestParam(value = "docIframe", required = false, defaultValue = "false") String docIframe)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		String view = (docIframe.equals("true")) ? "collpack/forum/discussionFormIframe" : "collpack/forum/discussionForm";

		FrDiscussion frDiscussion = new FrDiscussion();
		List<Tag> tagList = new ArrayList<Tag>();

		if (StringUtil.isEmpty(discussionId)) { // 질문 등록이면

			frDiscussion.setCategoryId(frDiscussion.getCategoryId());

		} else { // 수정이면

			frDiscussion = frDiscussionService.get(discussionId);
			
			// 권한체크
			accessCheck(isAdmin, frDiscussion.getRegisterId());

			// 태그 가져오기
			tagList = tagService.listTagByItemId(discussionId, TagConstants.ITEM_TYPE_FORUM);

		}
		
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		return this.bindResult(new ModelAndView(view)
				.addObject("frDiscussion", frDiscussion)
				.addObject("tagList", tagList)
				.addObject("useActiveX", useActiveX));
	}

	/**
	 *  토론 주제 등록 
	 * @param frDiscussion	주제 객체
	 * @param result
	 * @param status
	 * @param model
	 * @param isAdmin
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createDiscussion.do", method = RequestMethod.POST)
	public ModelAndView onSubmit(@ValidEx FrDiscussion frDiscussion, BindingResult result, SessionStatus status,
			ModelMap model, @ModelAttribute("isAdmin") boolean isAdmin, HttpServletRequest request) {

		StringBuffer param = new StringBuffer();
		
		if (result.hasErrors()) {
			
			if (!StringUtil.isEmpty(frDiscussion.getDocIframe())) {
				String docIframe = "?docIframe=" +frDiscussion.getDocIframe();
				param.append(docIframe);
			}
			
			this.setErrorAttribute("frDiscussion", frDiscussion, result);
			return new ModelAndView("forward:discussionForm.do"+param.toString())
					.addObject("discussionId", frDiscussion.getDiscussionId())
					.addObject("categoryId", frDiscussion.getCategoryId());
		}

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		frDiscussion.setUpdaterId(user.getUserId());
		frDiscussion.setUpdaterName(user.getUserName());
		frDiscussion.setPortalId(portal.getPortalId());

		String discussionId = "";
		if (StringUtil.isEmpty(frDiscussion.getDiscussionId())) { // 등록일때

			frDiscussion.setRegisterId(user.getUserId());
			frDiscussion.setRegisterName(user.getUserName());

			String url = request.getRequestURL().toString();

			frDiscussion.setPathUrl(url.substring(0, url.lastIndexOf("/"))); // 태그등록 때문에

			discussionId = frDiscussionService.create(frDiscussion);

		} else { // 수정일때
			
			// 권한체크
			accessCheck(isAdmin, frDiscussion.getRegisterId());

			frDiscussionService.update(frDiscussion);
			discussionId = frDiscussion.getDiscussionId();
		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();

		
		

		if (!StringUtil.isEmpty(frDiscussion.getCategoryId())) {
			String cateId = "&categoryId=" +frDiscussion.getCategoryId();
			param.append(cateId);
		}
		
		if (!StringUtil.isEmpty(frDiscussion.getDocIframe())) {
			String docIframe = "&docIframe=" +frDiscussion.getDocIframe()+"&docPopup=true";
			param.append(docIframe);
		}

		model.clear(); // modelAttribute url에서 제거

		return new ModelAndView("redirect:getView.do?discussionId=" + discussionId + param.toString());
	}

	/**
	 * 토론글 등록
	 * @param frItem	토론글 객체
	 * @param result
	 * @param isAdmin
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createItem.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String onSubmitItem(@ValidEx FrItem frItem, BindingResult result, @ModelAttribute("isAdmin") boolean isAdmin
							,HttpServletRequest request) {

		if (result.hasErrors()) {
			
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String itemId = "";
		
		try {
			
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		frItem.setUpdaterId(user.getUserId());
		frItem.setUpdaterName(user.getUserName());
		frItem.setPortalId(portal.getPortalId());
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX) && frItem.getMsie() == 1) {
			
			//사용자 브라우저가 IE인 경우
				try {	
					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(frItem.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						frItem.setEditorFileLinkList(util.getFileLinkList());
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					frItem.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
		}
		
		
		if (StringUtil.isEmpty(frItem.getItemId())) { // 등록일때

			frItem.setRegisterId(user.getUserId());
			frItem.setRegisterName(user.getUserName());

			String url = request.getRequestURL().toString();

			frItem.setPathUrl(url.substring(0, url.lastIndexOf("/"))); // 태그

			itemId = frItemService.create(frItem);

		} else { // 수정일때

			FrItem item = frItemService.get(frItem.getItemId(), user.getUserId());
			frItem.setRegisterId(item.getRegisterId());
			frItem.setRegisterName(item.getRegisterName());
			
			itemId = frItem.getItemId();
			
			// 권한체크
			accessCheck(isAdmin, frItem.getRegisterId());

			frItemService.update(frItem);
		}
		
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return itemId;
	}

	/**
	 * 상세 화면
	 * @param discussionId	토론주제 ID
	 * @param itemId		토론글 ID
	 * @param docPopup		팝업으로 띄울지
	 * @return
	 */
	@RequestMapping(value = "/getView.do", method = RequestMethod.GET)
	public ModelAndView getView(@RequestParam(value = "discussionId", required = false) String discussionId
								,@RequestParam(value = "itemId", required = false) String itemId
								,@RequestParam(value = "docPopup", required = false, defaultValue = "false") String docPopup) {

		String view = (docPopup.equals("true")) ? "collpack/forum/viewNotTile" : "collpack/forum/view";

		ModelAndView mav = new ModelAndView(view);
		User user = (User) getRequestAttribute("ikep.user");

		FrItem frItem = new FrItem();
		FrDiscussion frDiscussion = new FrDiscussion();
		
		String disId = discussionId;
		String tmpItemId = itemId;

		// 토론ID가 있을 경우
		if (!StringUtil.isEmpty(tmpItemId)) {

			frItem = frItemService.get(tmpItemId, user.getUserId());

			if(frItem != null){
				disId = frItem.getDiscussionId();
			}
		} 

		mav.addObject("discussionId", disId);

		if(!StringUtil.isEmpty(disId)){
			// 토론주제 조회
			frDiscussion = frDiscussionService.get(disId);
			frDiscussion.setContents(StringUtil.replaceHtmlString(frDiscussion.getContents()));
	
			List<Tag> discussionTagList = tagService.listTagByItemId(disId, TagConstants.ITEM_TYPE_FORUM);
			mav.addObject("discussionTagList", discussionTagList);
	
			// 토론글 조회
			FrSearch itemSearch = new FrSearch();
			itemSearch.setListType(ForumConstants.LIST_TYPE_ITEM);
			itemSearch.setDiscussionId(disId);
	
			listForum(itemSearch, mav);
	
			// 참가자 리스트
			FrSearch partSearch = new FrSearch();
			partSearch.setDiscussionId(disId);
			listParticipant(partSearch, mav);
			
		}
		
		if(StringUtil.isEmpty(tmpItemId)){	//토론글 ID가 없으면 토론글 목록 처음 글 조회
			Map map = mav.getModel();
			List<FrItem> itemList = (List<FrItem>) map.get("itemList");
			if(itemList.size() > 0){
				//frItem = itemList.get(0);
				
				tmpItemId = itemList.get(0).getItemId();
				
				frItem = frItemService.get(tmpItemId, user.getUserId());
				
				//조회수 증가하면 업데이트
				/*
				if(item.getHitCount() != frItem.getHitCount()){
					frItem.setHitCount(item.getHitCount());
					frDiscussion.setHitCount(frDiscussion.getHitCount()+1);
					
				}
				*/
				
			}
			
		}
		
		mav.addObject("frDiscussion", frDiscussion);
		mav.addObject("frItem", frItem);
		mav.addObject("itemId", tmpItemId);
		
		if(!StringUtil.isEmpty(tmpItemId)){	//아이템이 있으면 토론글 관련 데이터 조회
			
			getViewItemAction(mav, tmpItemId);
		}
		
		// ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		mav.addObject("useActiveX", useActiveX);

		return mav;
	}
	

	/**
	 * ajax이용한 참여자 리스트 가져오기
	 * @param frSearch	search 객체
	 * @return
	 */
	@RequestMapping(value = "/participantListMore.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView participantListMore(FrSearch frSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/participaniListMore");

		try {

			frSearch.setListType(ForumConstants.LIST_TYPE_DISCUSSION);
			listParticipant(frSearch, mav);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;

	}

	/**
	 * 공통 - 참가자 리스트
	 * 
	 * @param frSearch
	 * @param mav
	 */
	private void listParticipant(FrSearch frSearch, ModelAndView mav) {

		// 페이징조건
		Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
		int pagePer = Integer.parseInt(prop.getProperty("forum.view.participant.list.pagePer"));

		SearchCondition condition = new SearchCondition();
		condition.setPageIndex(frSearch.getPageIndex());
		condition.setPagePerRecord(pagePer);

		// 카운트
		int count = frParticipantService.getCountList(frSearch);

		mav.addObject("participantCount", count);
		
		mav.addObject("participantDiscussionId", frSearch.getDiscussionId());

		condition.terminateSearchCondition(count);
		mav.addObject("participantCondition", condition);

		// 게시물 리스트 가져오기
		frSearch.setEndRowIndex(condition.getEndRowIndex());
		frSearch.setStartRowIndex(condition.getStartRowIndex());

		List<FrParticipant> list = frParticipantService.list(frSearch);

		mav.addObject("participantList", list);

	}

	/**
	 * ajax이용한 토론주제 리스트 가져오기
	 * 
	 * @param frSearch
	 * @return
	 */
	@RequestMapping(value = "/discussionListMore.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView discussionListMore(FrSearch frSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/discussionListMore");

		try {
			frSearch.setListType(ForumConstants.LIST_TYPE_DISCUSSION);
			
			if(!StringUtil.isEmpty(frSearch.getIsPopular())){		//인기글이면
				frSearch.setListType(ForumConstants.LIST_TYPE_DISCUSSION_POPULAR);
			}
			
			if(!StringUtil.isEmpty(frSearch.getIsBest())){		//우수글이면
				frSearch.setIsBest(ForumConstants.BEST_TYPE);
			}
			
			if(!StringUtil.isEmpty(frSearch.getIsMy())){		//내글이면
				User user = (User) getRequestAttribute("ikep.user");
				frSearch.setUserId(user.getUserId());
			}

			
			listForum(frSearch, mav);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;

	}




	/**
	 * ajax이용한 토론글 리스트 가져오기
	 * 
	 * @param frSearch
	 * @return
	 */
	@RequestMapping("/itemListMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView itemListMore(FrSearch frSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/itemListMore");

		try {
			
			frSearch.setListType(ForumConstants.LIST_TYPE_ITEM);
			
			listForum(frSearch, mav);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

	
	/**
	 * ajax이용한 토론글, 토론주제 리스트 가져오기
	 * 
	 * @param frSearch
	 * @return
	 */
	@RequestMapping(value = "/itemDisListMore.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView itemDisListMore(FrSearch frSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/itemDisListMore");

		try {

			frSearch.setListType(ForumConstants.LIST_TYPE_ITEM_DISCUSSION);
			
			if(!StringUtil.isEmpty(frSearch.getIsPopular())){		//인기글이면
				
				if(frSearch.getLimitDate() == null){
					frSearch.setLimitDate("7");
				}
				
				frSearch.setIsPopular(ForumConstants.POPULAR_TYPE);
			}
			
			if(!StringUtil.isEmpty(frSearch.getIsBest())){		//우수글이면
				frSearch.setIsBest(ForumConstants.BEST_TYPE);
			}
			
			if(!StringUtil.isEmpty(frSearch.getIsMy())){		//내글이면
				User user = (User) getRequestAttribute("ikep.user");
				frSearch.setUserId(user.getUserId());
			}

			listForum(frSearch, mav);
			
			//search인지 
			if(frSearch.getSearchColumn() != null){
				mav.addObject("listType", "Search");
			}
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}
	
	
	
	/**
	 * ajax이용한 토론글, 토론주제 리스트 가져오기
	 * 
	 * @param frSearch
	 * @return
	 */
	@RequestMapping(value = "/itemSearch.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView itemSearch(FrSearch frSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/itemListSearchMore");

		try {

			frSearch.setListType(ForumConstants.LIST_TYPE_ITEM_DISCUSSION);

			listForum(frSearch, mav);
			
			mav.addObject("listType", "Search");
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}
	
	
	
	/**
	 * ajax이용한 최근 토론글 리스트 가져오기
	 * 
	 * @param frSearch
	 * @return
	 */
	@RequestMapping("/itemLastListMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView itemLastListMore(FrSearch frSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/lastListMore");

		try {

			lastListAction(mav, frSearch);
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

	/**
	 * ajax이용한 토론의견 리스트 가져오기
	 * 
	 * @param frSearch
	 * @return
	 */
	@RequestMapping("/linereplyListMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView linereplyListMore(FrSearch frSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/linereplyDisListMore");

		try {
			frSearch.setListType(ForumConstants.LIST_TYPE_LINEREPLY_ITEM);
			
			if(!StringUtil.isEmpty(frSearch.getIsPopular())){		//인기글이면
				if(frSearch.getLimitDate() == null){
					frSearch.setLimitDate("7");
				}
				
				frSearch.setIsPopular(ForumConstants.POPULAR_TYPE);
			}
			
			if(!StringUtil.isEmpty(frSearch.getIsBest())){		//우수글이면
				frSearch.setIsBest(ForumConstants.BEST_TYPE);
			}
			
			if(!StringUtil.isEmpty(frSearch.getIsMy())){		//내글이면
				User user = (User) getRequestAttribute("ikep.user");
				frSearch.setUserId(user.getUserId());
			}
			
			listForum(frSearch, mav);
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

	

	/**
	 * 공통 - 리스트
	 * 
	 * @param frSearch
	 * @param mav
	 * @return
	 */
	private void listForum(FrSearch frSearch, ModelAndView mav) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		frSearch.setPortalId(portal.getPortalId());

		// 페이징조건
		Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
		int pagePer = Integer.parseInt(prop.getProperty("forum.default.list.pagePer"));

		SearchCondition condition = new SearchCondition();
		condition.setPageIndex(frSearch.getPageIndex());
		condition.setPagePerRecord(pagePer);

		// 카운트
		int count = 0;

		String viewText = "";

		if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_DISCUSSION)) {
			count = frDiscussionService.getCountList(frSearch);
			viewText = "discussion";

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_ITEM)) {
			count = frItemService.getCountList(frSearch);
			viewText = "item";

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_ITEM_DISCUSSION)) {
			count = frItemService.getCountListWithDiscussion(frSearch);
			viewText = "item";

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_ITEM_LAST)) {
			count = frItemService.getCountListLastWithDiscussion(frSearch);
			viewText = "item";

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_LINEREPLY_ITEM)) {
			count = frLinereplyService.getCountListWithItemDiscussion(frSearch);
			viewText = "linereply";
		
		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_LINEREPLY)) {
			count = frLinereplyService.getCountList(frSearch);
			viewText = "linereply";

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_DISCUSSION_POPULAR)) {
			count = frDiscussionService.getCountListPopular(frSearch);
			viewText = "discussion";

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_ITEM_POPULAR)) {
			count = frItemService.getCountListPopular(frSearch);
			viewText = "item";
		}

		
		condition.terminateSearchCondition(count);
		
		if(viewText.equals("linereply")){
			mav.addObject("linereplyTotalCount", count);
			mav.addObject("linereplyCondition", condition);
		} else {
			mav.addObject("totalCount", count);
			mav.addObject("pageCondition", condition);
		}
		
		// 게시물 리스트 가져오기
		frSearch.setEndRowIndex(condition.getEndRowIndex());
		frSearch.setStartRowIndex(condition.getStartRowIndex());

		List list = getList(frSearch);

		mav.addObject(viewText + "List", list);

	}

	/**
	 * 리스트 가져오기
	 * @param frSearch
	 * @return
	 */
	private List getList(FrSearch frSearch) {
		
		List list = new ArrayList();
		
		if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_DISCUSSION)) {
			list = frDiscussionService.list(frSearch);

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_ITEM)) {
			list = frItemService.list(frSearch);

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_ITEM_DISCUSSION)) {
			list = (List<FrItem>)frItemService.listWithDiscussion(frSearch);
			
			//비교하여 리스트 display 함
			/*
			if(!list.isEmpty() && !(frSearch.getPageIndex() == 1)){
				FrItem item = (FrItem)list.get(0);
				mav.addObject("comDiscussionId",item.getDiscussionId());
			}
			*/
			
		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_ITEM_LAST)) {
			list = frItemService.listLastWithDiscussion(frSearch);

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_LINEREPLY_ITEM)) {
			list = frLinereplyService.listWithItemDiscussion(frSearch);
			
			//비교하여 리스트 display 함
			/*
			if(!list.isEmpty() && !(frSearch.getPageIndex() == 1)){
				FrLinereply linereply = (FrLinereply)list.get(0);
				mav.addObject("comDiscussionId",linereply.getFrItem().getFrDiscussion().getDiscussionId());
				mav.addObject("comItemId",linereply.getItemId());
			}
			*/
			
		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_LINEREPLY)) {
			list =frLinereplyService.list(frSearch);
			
		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_DISCUSSION_POPULAR)) {
			list = frDiscussionService.listPopular(frSearch);
			
			List<FrDiscussion> newDisList = new ArrayList<FrDiscussion>();
			
			if (list.size() > 0) {

				for(int i = 0; i < list.size(); i++){
					FrDiscussion discussion = (FrDiscussion)list.get(i);
					
					//태그 리스트
					List<Tag> tagList = tagService.listTagByItemId(discussion.getDiscussionId(), TagConstants.ITEM_TYPE_FORUM);
					discussion.setTagList(tagList);
					
					newDisList.add(discussion);
					
				}
			}
			
			list = newDisList;

		} else if (frSearch.getListType().equals(ForumConstants.LIST_TYPE_ITEM_POPULAR)) {
			list = frItemService.listPopular(frSearch);
		}
		return list;
	}

	/**
	 *  ajax이용한 토론글 가져오기
	 * @param itemId	토론글 ID
	 * @return
	 */
	@RequestMapping(value = "/getItem.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody FrItem getItem(@RequestParam("itemId") String itemId) {

		FrItem frItem = new FrItem();

		try {

			User user = (User) getRequestAttribute("ikep.user");
			String userId = user.getUserId();

			frItem = frItemService.get(itemId, userId);
			
			// 태그 조회
			List<Tag> itemTagList = tagService.listTagByItemId(itemId, TagConstants.ITEM_TYPE_FORUM);
			frItem.setTagList(itemTagList);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return frItem;

	}

	/**
	 * 나의 활동 리스트
	 * @param tabType	리스트 타입
	 * @param myActivities
	 * @return
	 */
	@RequestMapping(value = "/myActivity.do", method = RequestMethod.GET)
	public ModelAndView myActivity(@RequestParam(value="tabType", required=false, defaultValue="item") String tabType
								,@ModelAttribute("myActivities") FrUserActivities myActivities) {

		ModelAndView mav = new ModelAndView("collpack/forum/myActivity");

		User user = (User) getRequestAttribute("ikep.user");
		String userId = user.getUserId();
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		//활동정보 없을시 유저 정보 가져오기
		if(myActivities == null){
			FrUserActivities userInfo = frUserActivitiesService.getUserInfo(userId);
			mav.addObject("userInfo", userInfo);
		}


		// 전체 토론 랭킹
		Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
		String pagePer = prop.getProperty("forum.activity.best.list.pagePer");

		FrSearch actSearch = new FrSearch();
		actSearch.setIsBest(ForumConstants.BEST_TYPE);
		actSearch.setEndRowIndex(Integer.parseInt(pagePer));
		actSearch.setPortalId(portal.getPortalId());

		List<FrUserActivities> activityList = frUserActivitiesService.list(actSearch);
		mav.addObject("activityList", activityList);

		// 내 토론글
		FrSearch disSearch = new FrSearch();
		disSearch.setUserId(userId);
		
		if(tabType.equals(ForumConstants.TAP_TYPE_LINEREPLY)){		//내가 등록한 토론의견
			disSearch.setListType(ForumConstants.LIST_TYPE_LINEREPLY_ITEM);
			
		} else if(tabType.equals(ForumConstants.TAP_TYPE_DISCUSSION)){		//내가 등록한 토론주제
			disSearch.setListType(ForumConstants.LIST_TYPE_DISCUSSION);
			
		} else if(tabType.equals(ForumConstants.TAP_TYPE_ITEM_BEST)){		//내가 등록한 best 토론글
			disSearch.setListType(ForumConstants.LIST_TYPE_ITEM_DISCUSSION);
			disSearch.setIsBest(ForumConstants.BEST_TYPE);
			
		} else if(tabType.equals(ForumConstants.TAP_TYPE_LINEREPLY_BEST)){	//내가 등록한 best 토론 의견
			disSearch.setListType(ForumConstants.LIST_TYPE_LINEREPLY_ITEM);
			disSearch.setIsBest(ForumConstants.BEST_TYPE);
			
		} else {
			disSearch.setListType(ForumConstants.LIST_TYPE_ITEM_DISCUSSION);	//내가 등록한 토론글
		}
		

		listForum(disSearch, mav);

		return mav;
	}
	
	
	/**
	 * ajax로 나의 랭킹
	 * @param categoryId	카테고리 ID
	 * @return
	 */
	@RequestMapping(value = "/myRankAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody FrUserActivities myRankAjax() {

		User user = (User) getRequestAttribute("ikep.user");
		String userId = user.getUserId();
		
		//활동정보 없을시 유저 정보 가져오기
		FrUserActivities frUserActivities = frUserActivitiesService.get(userId);
		return frUserActivities;
	}
	
	
	/**
	 * 랭킹 리스트
	 * @param categoryId	카테고리 ID
	 * @return
	 */
	@RequestMapping(value = "/rankList.do")
	public  ModelAndView rankList(FrSearch actSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/activityList");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		// 인기 토론주제 top
		Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
		String pagePer = prop.getProperty("forum.default.list.pagePer");
		
		actSearch.setIsBest(ForumConstants.BEST_TYPE);
		actSearch.setPortalId(portal.getPortalId());
		actSearch.setEndRowIndex(Integer.parseInt(pagePer));

		List<FrUserActivities> activityList = frUserActivitiesService.list(actSearch);
		
		mav.addObject("activityList", activityList);
		
		return mav;
	}
	
	/**
	 * ajax로 랭킹 리스트
	 * @param categoryId	카테고리 ID
	 * @return
	 */
	@RequestMapping(value = "/rankListAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<FrUserActivities> rankListAjax(FrSearch actSearch) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		actSearch.setIsBest(ForumConstants.BEST_TYPE);
		actSearch.setPortalId(portal.getPortalId());

		List<FrUserActivities> activityList = frUserActivitiesService.list(actSearch);
		
		return activityList;
	}

	/**
	 * 인기 토론
	 * @param frSearch		서치 객체
	 * @return
	 */
	@RequestMapping(value = "/popularList.do", method = RequestMethod.GET)
	public ModelAndView popularList(FrSearch frSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/popularList");

		// 인기 토론주제 top
		Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
		String pagePer = prop.getProperty("forum.top.list.pagePer");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		FrSearch topSearch = new FrSearch();
		topSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
		topSearch.setEndRowIndex(Integer.parseInt(pagePer));
		topSearch.setPortalId(portal.getPortalId());

		List<FrDiscussion> discussionTopList = frDiscussionService.listPopular(topSearch);
		mav.addObject("topLeftList", discussionTopList);

		// 인기 토론글 top
		List<FrItem> itemTopList = frItemService.listPopular(topSearch);
		mav.addObject("topRightList", itemTopList);

		// 인기 토론주제 리스트
		
		if(frSearch.getTabType() != null && frSearch.getTabType().equals(ForumConstants.TAG_TYPE_ITEM)){
			frSearch.setListType(ForumConstants.LIST_TYPE_ITEM_DISCUSSION);
			frSearch.setIsPopular(ForumConstants.POPULAR_TYPE);
		} else {
			frSearch.setListType(ForumConstants.LIST_TYPE_DISCUSSION_POPULAR);
		}
		
		if(StringUtil.isEmpty(frSearch.getLimitDate())){
			frSearch.setLimitDate("7");
		}

		listForum(frSearch, mav);

		return mav;
	}
	
	
	/**
	 * ajax로 인기 토론 리스트
	 * @param categoryId	카테고리 ID
	 * @return
	 */
	@RequestMapping(value = "/popularListAjax.do")
	public @ResponseBody List<FrDiscussion> popularListAjax(FrSearch topSearch) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		topSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
		topSearch.setPortalId(portal.getPortalId());

		List<FrDiscussion> discussionTopList = frDiscussionService.listPopular(topSearch);

		return discussionTopList;
	}
	

	/**
	 * 우수 토론 리스트
	 * @param frSearch
	 * @return
	 */
	@RequestMapping(value = "/bestList.do", method = RequestMethod.GET)
	public ModelAndView bestList(FrSearch frSearch) {

		ModelAndView mav = new ModelAndView("collpack/forum/bestList");

		// 토론의견 top
		Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
		String pagePer = prop.getProperty("forum.top.list.pagePer");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		FrSearch topSearch = new FrSearch();
		topSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
		topSearch.setEndRowIndex(Integer.parseInt(pagePer));
		topSearch.setIsBest(ForumConstants.BEST_TYPE);
		topSearch.setPortalId(portal.getPortalId());
		topSearch.setOrderType(ForumConstants.ORDER_BEST);

		// 토론글 top
		List<FrItem> itemTopList = frItemService.list(topSearch);
		mav.addObject("itemTopList", itemTopList);

		// 토론의견
		List<FrLinereply> lineTopList = frLinereplyService.list(topSearch);
		mav.addObject("lineTopList", lineTopList);

		// 토론 리스트
		if(frSearch.getTabType() != null && frSearch.getTabType().equals(ForumConstants.TAP_TYPE_LINEREPLY)){
			frSearch.setListType(ForumConstants.LIST_TYPE_LINEREPLY_ITEM);
		} else {
			frSearch.setListType(ForumConstants.LIST_TYPE_ITEM_DISCUSSION);
		}
		
		if(StringUtil.isEmpty(frSearch.getLimitDate())){
			frSearch.setLimitDate("7");
		}
		
		frSearch.setIsBest(ForumConstants.BEST_TYPE);

		listForum(frSearch, mav);

		return mav;
	}
	
	
	/**
	 * ajax로 우수 토론 리스트
	 * @param categoryId	카테고리 ID
	 * @return
	 */
	@RequestMapping(value = "/bestListAjax.do")
	public @ResponseBody List<FrItem> bestListAjax(FrSearch topSearch) {


		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		topSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
		topSearch.setIsBest(ForumConstants.BEST_TYPE);
		topSearch.setPortalId(portal.getPortalId());
		topSearch.setOrderType(ForumConstants.ORDER_BEST);

		// 인기 토론글 top
		List<FrItem> itemTopList = frItemService.list(topSearch);

		return itemTopList;
	}

	/**
	 * 논쟁중인 토론 리스트
	 * @param categoryId	카테고리 ID
	 * @param categoryList	
	 * @return
	 */
	@RequestMapping(value = "/lastList.do", method = RequestMethod.GET)
	public ModelAndView lastList(@RequestParam(value = "categoryId", required = false) String categoryId
								,@ModelAttribute("categoryList") List<FrCategory> categoryList
								,@RequestParam(value = "docIframe", required = false, defaultValue = "false") String docIframe) {

		String view = (docIframe.equals("true")) ? "collpack/forum/lastListIframe" : "collpack/forum/lastList";
		
		ModelAndView mav = new ModelAndView(view);

		// 인기 토론글 top
		Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
		String pagePer = prop.getProperty("forum.top.list.pagePer");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		FrSearch topSearch = new FrSearch();
		topSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
		topSearch.setEndRowIndex(Integer.parseInt(pagePer));
		topSearch.setIsBest(ForumConstants.BEST_TYPE);
		topSearch.setPortalId(portal.getPortalId());
		topSearch.setOrderType(ForumConstants.ORDER_BEST);
		topSearch.setCategoryId(categoryId);
		
		List<FrItem> popularList = frItemService.listPopular(topSearch);
		mav.addObject("topLeftList", popularList);

		// 우수 토론글 top
		List<FrItem> bestList = frItemService.list(topSearch);
		mav.addObject("topRightList", bestList);
		
		String categoryName = "";
		if(!StringUtil.isEmpty(categoryId)){
			for(FrCategory category : categoryList){
				if(category.getCategoryId().equals(categoryId)){
					categoryName = category.getCategoryName();
				}
			}
			mav.addObject("categoryName", categoryName);
			
		}

		// 최근 토론주제 리스트
		FrSearch disSearch = new FrSearch();
		disSearch.setPageIndex(1);
		disSearch.setCategoryId(categoryId);
		
		lastListAction(mav, disSearch);

		return mav;
	}
	
	
	
	/**
	 * ajax로 논쟁중인 토론 리스트
	 * @param categoryId	카테고리 ID
	 * @return
	 */
	@RequestMapping(value = "/lastListAjax.do", method = RequestMethod.GET)
	public @ResponseBody List<FrDiscussion> lastListAjax() {

		ModelAndView mav = new ModelAndView("");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		// 최근 토론주제 리스트
		FrSearch disSearch = new FrSearch();
		disSearch.setPageIndex(1);
		disSearch.setPortalId(portal.getPortalId());
		
		lastListAction(mav, disSearch);
		
		Map map = mav.getModel();
		
		List<FrDiscussion> list = (List<FrDiscussion>)map.get("discussionList");

		return list;
	}
	
	
	
	/**
	 * keyIssue List
	 * 
	 * @return
	 */
	@RequestMapping(value = "/keyIssueList.do", method = RequestMethod.GET)
	public ModelAndView keyIssueList() {

		ModelAndView mav = new ModelAndView("collpack/forum/keyIssueList");

		// 인기 토론글 top
		Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
		String pagePer = prop.getProperty("forum.default.list.pagePer");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		FrSearch topSearch = new FrSearch();
		topSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
		topSearch.setEndRowIndex(Integer.parseInt(pagePer));
		topSearch.setIsBest(ForumConstants.BEST_TYPE);
		topSearch.setPortalId(portal.getPortalId());
		topSearch.setOrderType(ForumConstants.ORDER_BEST);

		List<FrItem> popularList = frItemService.listPopular(topSearch);
		
		if(!popularList.isEmpty()){
			List<String> itemIdList = new ArrayList<String>();
			for(FrItem item : popularList){
				itemIdList.add(item.getItemId());
			}
			
			Tag tag = new Tag();
			tag.setPortalId(portal.getPortalId());
			tag.setTagItemType(TagConstants.ITEM_TYPE_FORUM);
			tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);
			tag.setTagItemIdList(itemIdList);
			
			
			int tagCount = tagService.getCount(tag);
			
			tag.setEndRowIndex(tagCount);
			
			List<Tag> tagList = tagService.list(tag);
			mav.addObject("tagList",tagList);
			
			if(!tagList.isEmpty()){
				String tagId = tagList.get(0).getTagId();
				mav.addObject("tagId", tagId);
			}
		}


		return mav;
	}

	/**
	 * main
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main.do")
	public ModelAndView viewMain() {

		ModelAndView mav = new ModelAndView("collpack/forum/main");

		// 인기 토론
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		FrSearch topSearch = new FrSearch();
		topSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
		topSearch.setEndRowIndex(1);
		topSearch.setPortalId(portal.getPortalId());

		List<FrDiscussion> poplurDiscussionList = frDiscussionService.listPopular(topSearch);
		mav.addObject("poplurDiscussionList", poplurDiscussionList);

		if (!poplurDiscussionList.isEmpty()) {

			topSearch.setDiscussionId(poplurDiscussionList.get(0).getDiscussionId());
			topSearch.setOrderType(ForumConstants.ORDER_LINEREPLY);
			List<FrItem> poplurItemList = frItemService.list(topSearch);
			mav.addObject("poplurItemList", poplurItemList);

			if (poplurItemList != null && poplurItemList.size() > 0) {

				FrSearch lineSearch = new FrSearch();
				lineSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
				lineSearch.setEndRowIndex(3);
				lineSearch.setItemId(poplurItemList.get(0).getItemId());
				lineSearch.setOrderType(ForumConstants.ORDER_BEST);
				List<FrLinereply> poplurLinereplyList = frLinereplyService.list(lineSearch);
				mav.addObject("poplurLinereplyList", poplurLinereplyList);
			}
		}

		// 카테고리별 우수 토론
		List<FrCategory> categorylist = frCategoryService.list(portal.getPortalId());
		
		if(!categorylist.isEmpty()){
		
			int ranVal1 = (int)(Math.random()*categorylist.size());
			
			FrSearch randomSearch = new FrSearch();
			randomSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
			randomSearch.setEndRowIndex(2);
			randomSearch.setPortalId(portal.getPortalId());
			randomSearch.setCategoryId(categorylist.get(ranVal1).getCategoryId());
			List<FrItem> randomList1 = frItemService.listItemRandomCategory(randomSearch);
			mav.addObject("randomList1", randomList1);
			mav.addObject("randomList1CategoryName", categorylist.get(ranVal1).getCategoryName());
			
			if(categorylist.size() > 1){
				int ranVal2 = (int)(Math.random()*categorylist.size());
				
				while(ranVal1 == ranVal2){
					ranVal2 = (int)(Math.random()*categorylist.size());
				}
				
				randomSearch.setCategoryId(categorylist.get(ranVal2).getCategoryId());
				List<FrItem> randomList2 = frItemService.listItemRandomCategory(randomSearch);
				mav.addObject("randomList2", randomList2);
				mav.addObject("randomList2CategoryName", categorylist.get(ranVal2).getCategoryName());
			}
		}

		//논쟁중인 토론
		FrSearch disSearch = new FrSearch();
		disSearch.setPageIndex(1);
		
		lastListAction(mav, disSearch);

		return mav;
	}

	/**
	 * 공통 - 최신 토론리스트
	 * @param mav
	 * @param disSearch
	 */
	private void lastListAction(ModelAndView mav, FrSearch disSearch){
		
		if(disSearch.getCategoryId() == null){
			disSearch.setLimitDate("30");
		}
		
		disSearch.setListType(ForumConstants.LIST_TYPE_DISCUSSION);
		disSearch.setPageIndex(disSearch.getPageIndex());
		//disSearch.setIsItem(ForumConstants.IS_ITEM);	//토론글이 있을 경우만
	
		//최신 리스트
		listForum(disSearch, mav);

		Map map = mav.getModel();
		List<FrDiscussion> discussionList = (List<FrDiscussion>) map.get("discussionList");
		int count = (Integer) map.get("totalCount");

		List<FrDiscussion> newDisList = new ArrayList<FrDiscussion>();

		Properties prop = PropertyLoader.loadProperties("/configuration/forum-conf.properties");
		String participantPer = prop.getProperty("forum.view.participant.list.pagePer");
		String limitIndex = prop.getProperty("forum.item.limit.list.pagePer");

		if (count > 0) {

			for (FrDiscussion discussion : discussionList) {
				
				//토론글 리스트
				FrSearch itemSearch = new FrSearch();
				itemSearch.setStartRowIndex(Integer.parseInt(ForumConstants.BASE_NO));
				itemSearch.setEndRowIndex(Integer.parseInt(limitIndex));
				itemSearch.setDiscussionId(discussion.getDiscussionId());
				List<FrItem> itemList = frItemService.list(itemSearch);
				
				discussion.setItemList(itemList);

				//참가자 리스트
				itemSearch.setEndRowIndex(Integer.parseInt(participantPer));
				
				List<FrParticipant> participantList = frParticipantService.list(itemSearch);
				discussion.setParticipantList(participantList);

				//토론 리스트
				List<Tag> tagList = tagService.listTagByItemId(discussion.getDiscussionId(), TagConstants.ITEM_TYPE_FORUM);
				discussion.setTagList(tagList);
				
				newDisList.add(discussion);

			}
		}

		mav.addObject("discussionList", newDisList);
	}
	
	/**
	 * 토론주제삭제
	 * @param frSearch 서치 객체
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/deleteDiscussion.do", method = RequestMethod.GET)
	public String deleteDiscussion(FrSearch frSearch
									, ModelMap model
								    , @ModelAttribute("isAdmin") boolean isAdmin) {

		FrDiscussion discussion = frDiscussionService.get(frSearch.getDiscussionId());

		// 권한체크
		accessCheck(isAdmin, discussion.getRegisterId());

		frDiscussionService.remove(frSearch.getDiscussionId(), discussion.getRegisterId());

		model.clear();

		if (StringUtil.isEmpty(frSearch.getListType())) {
			frSearch.setListType("last");
		}

		StringBuffer param = new StringBuffer();
		
		if (!StringUtil.isEmpty(frSearch.getCategoryId())) {
			String cateId = "?categoryId=" + frSearch.getCategoryId();
			param.append(cateId);
		}
		
		if (!StringUtil.isEmpty(frSearch.getDocIframe())) {
			String iframe = "?docIframe=" + frSearch.getDocIframe()+"&docPopup=true";
			param.append(iframe);
		}

		return "redirect:" + frSearch.getListType() + "List.do" + param.toString();
	}
	
	/**
	 * 토론글삭제
	 * @param frSearch
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/deleteItem.do", method = RequestMethod.GET)
	public String deleteItem(FrSearch frSearch
							, ModelMap model
						    , @ModelAttribute("isAdmin") boolean isAdmin) {

		User user = (User) getRequestAttribute("ikep.user");
		
		FrItem frItem = frItemService.get(frSearch.getItemId(), user.getUserId());

		// 권한체크
		accessCheck(isAdmin, frItem.getRegisterId());

		frItemService.remove(frSearch.getItemId());

		model.clear();

		StringBuffer param = new StringBuffer();
		
		if (!StringUtil.isEmpty(frSearch.getCategoryId())) {
			String cateId = "&categoryId=" + frSearch.getCategoryId();
			param.append(cateId);
		}
		
		if (!StringUtil.isEmpty(frSearch.getDocIframe())) {
			String iframe = "&docIframe=" + frSearch.getDocIframe()+"&docPopup=true";
			param.append(iframe);
		}

		return "redirect:getView.do?discussionId=" + frItem.getDiscussionId()+param.toString();
	}
	
	
	

	/**
	 * 즐겨찾기
	 * 
	 * @param itemId	토론글 ID
	 * @return
	 */
	@RequestMapping("/addFavorite.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String addFavorite(@RequestParam("itemId") String itemId) {

		try {
			frItemService.updateFavoriteCount(itemId, ForumConstants.NUM_INCRASE);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}
	
	
	/**
	 * 즐겨찾기삭제
	 * 
	 * @param itemId	토론글 ID
	 * @return
	 */
	@RequestMapping("/delFavorite.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String delFavorite(@RequestParam("itemId") String itemId) {

		try {
			frItemService.updateFavoriteCount(itemId, ForumConstants.NUM_DECREASE);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}


	/**
	 * 찬성하기
	 * @param itemId		토론글 ID
	 * @param discussionId	토론 주제 ID
	 * @return
	 */
	@RequestMapping("/addAgreement.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String addAgreement(@RequestParam("itemId") String itemId, @RequestParam("discussionId") String discussionId) {

		try {

			User user = (User) getRequestAttribute("ikep.user");
			String userId = user.getUserId();

			boolean feedback = frFeedbackService.exists(itemId, ForumConstants.FEEDBACK_AGREEMENT, userId);

			if (feedback) {
				return "exists";
			}

			frFeedbackService.createAgreement(itemId, userId, discussionId);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	/**
	 * 반대하기
	 * @param itemId	토론글 ID
	 * @param discussionId	토론주제 ID
	 * @return
	 */
	@RequestMapping("/addOpposition.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String addOpposition(@RequestParam("itemId") String itemId, @RequestParam("discussionId") String discussionId) {

		try {

			User user = (User) getRequestAttribute("ikep.user");
			String userId = user.getUserId();

			boolean feedback = frFeedbackService.exists(itemId, ForumConstants.FEEDBACK_OPPOSITION, userId);

			if (feedback) { // 존재하면
				return "exists";
			}

			frFeedbackService.createOpposition(itemId, userId, discussionId);

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}
	
	
	/**
	 * 메일 뷰 개수 업데이트
	 * @param discussionId	토론 주제 ID
	 * @return
	 */
	@RequestMapping("/updateMailCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateMailCount(@RequestParam("discussionId") String discussionId) {
		
		try {
			frDiscussionService.updateMailCount(discussionId);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	

	/**
	 * 블로그 뷰  개수 업데이트
	 * @param discussionId	토론 주제 ID
	 * @return
	 */
	@RequestMapping("/updateMblogCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateMblogCount(@RequestParam("discussionId") String discussionId) {
		
		try {
			frDiscussionService.updateMblogCount(discussionId);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	
	
	
	/**
	 * 토론 글관련 데이터 조회
	 * @param mav
	 * @param itemId
	 */
	private void getViewItemAction(ModelAndView mav, String itemId){
		
		// 토론 의견 조회
		FrSearch lineSearch = new FrSearch();
		lineSearch.setListType(ForumConstants.LIST_TYPE_LINEREPLY);
		lineSearch.setItemId(itemId);

		listForum(lineSearch, mav);

		// 태그 조회
		List<Tag> itemTagList = tagService.listTagByItemId(itemId, TagConstants.ITEM_TYPE_FORUM);
		mav.addObject("itemTagList", itemTagList);
		
		//사용자 즐겨찾기 조회
		User user = (User) getRequestAttribute("ikep.user");
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());
		mav.addObject("isFavorite", isFavorite);
	}
	
	
	@RequestMapping("/getViewItem.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView getViewItem(@RequestParam("itemId") String itemId) {

		ModelAndView mav = new ModelAndView("collpack/forum/itemViewMore");
		
		try {
			User user = (User) getRequestAttribute("ikep.user");
			
			FrItem frItem = frItemService.get(itemId, user.getUserId());
			mav.addObject("frItem", frItem);
			
			getViewItemAction(mav, itemId);
			 
		} catch(Exception ex) {
			throw new IKEP4AjaxException("", ex);
			
		} 
		
		return mav;
	}

	
	/**
	 * ajax로 내가 조회한 토론글 리스트
	 * @param categoryId	카테고리 ID
	 * @return
	 */
	@RequestMapping(value = "/listDiscussionByReference.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<FrDiscussion> listDiscussionByReference(int endRowIndex) {

		List<FrDiscussion> list = new ArrayList<FrDiscussion>();
		
		try{
			User user = (User) getRequestAttribute("ikep.user");
			String userId = user.getUserId();
			
	
			list = frDiscussionService.listDiscussionByReference(userId, endRowIndex);
		
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
	
		return list;
	}
	
	
	/**
	 * ajax로 내가 조회한 토론글 리스트
	 * @param categoryId	카테고리 ID
	 * @return
	 */
	@RequestMapping(value = "/listDiscussionByItemCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<FrDiscussion> listDiscussionByItemCount(int endRowIndex, String limitDate) {

		List<FrDiscussion> list = new ArrayList<FrDiscussion>();
		
		try{
	
			list = frDiscussionService.listDiscussionByItemCount(endRowIndex, limitDate);
		
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
	
		return list;
	}
}
