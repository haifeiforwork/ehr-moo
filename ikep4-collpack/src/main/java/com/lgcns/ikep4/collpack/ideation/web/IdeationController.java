/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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



import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.model.IdCategory;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;
import com.lgcns.ikep4.collpack.ideation.service.IdCategoryService;
import com.lgcns.ikep4.collpack.ideation.service.IdIdeaService;
import com.lgcns.ikep4.collpack.ideation.service.IdLinereplyService;
import com.lgcns.ikep4.collpack.ideation.service.IdRecommendService;
import com.lgcns.ikep4.collpack.ideation.service.IdUserActivitiesService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Qna controller
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: IdeationController.java 15406 2011-06-22 07:22:25Z loverfairy $$
 */
@Controller
@SessionAttributes("idea")
public class IdeationController extends IdeationBaseController {

	@Autowired
	private IdIdeaService idIdeaService;


	@Autowired
	private IdLinereplyService idLinereplyService;

	@Autowired
	private IdUserActivitiesService idUserActivitiesService;

	@Autowired
	private TagService tagService;
	
	@Autowired
	private IdRecommendService idRecommendService;
	
	@Autowired
	private PortalFavoriteService portalfavoriteService;
	
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private IdCategoryService idCategoryService;

	/**
	 * 답글 생성 화면 컨트롤 메서드
	 *
	 * @param itemId 원본 게시글 ID
	 * @return ModelAndView
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/createReplyItemView.do")
	public ModelAndView createReplyItemView(
			@RequestParam("itemId") String itemId
	) throws JsonGenerationException, JsonMappingException, IOException {


		User user = (User) getRequestAttribute("ikep.user");

		IdIdea idea = new IdIdea();

		idea = idIdeaService.get(itemId, user.getUserId());


		ObjectMapper mapper = new ObjectMapper();

		//파일 목록을 JSON으로 변환한다.
		String fileDataListJson = null;

		if(idea.getFileDataList()!= null ) {
			fileDataListJson = mapper.writeValueAsString(idea.getFileDataList());
		}
		
		// 태그 가져오기
		List<Tag> tagList = tagService.listTagByItemId(itemId, TagConstants.ITEM_TYPE_IDEATION);
		

		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");

		return this.bindResult(new ModelAndView()
		.addObject("idea", idea)
		.addObject("useActiveX", useActiveX)
		.addObject("fileDataListJson", fileDataListJson)
		.addObject("user", user)
		.addObject("tagList", tagList));
	}
	/**
	 * 등록폼
	 * @param itemId	idea ID
	 * @param isAdmin	
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping("/ideaForm.do")
	public ModelAndView getForm(@RequestParam(value = "itemId", required = false) String itemId 						
						, @RequestParam(value = "categoryId", required = false) String categoryId	
						, @ModelAttribute("isAdmin") boolean isAdmin
						,@RequestParam(value = "docIframe", required = false, defaultValue = "false") String docIframe)
							throws JsonGenerationException, JsonMappingException, IOException {
		
		String view = (docIframe.equals("true")) ? "collpack/ideation/ideaFormIframe" : "collpack/ideation/ideaForm";
		
		ModelAndView mav = new ModelAndView(view);
		
		IdIdea idea = new IdIdea();
		User user = (User) getRequestAttribute("ikep.user");
		
		if (!StringUtil.isEmpty(itemId)) {  //수정이면

			idea = idIdeaService.get(itemId, user.getUserId());

			// 권한체크
			accessCheck(isAdmin, idea.getRegisterId());
			
			//첨부파일 가져오기
			ObjectMapper mapper = new ObjectMapper();
			String fileDataListJson = mapper.writeValueAsString(idea.getFileDataList());
			mav.addObject("fileDataListJson", fileDataListJson);

			// 태그 가져오기
			List<Tag> tagList = tagService.listTagByItemId(itemId, TagConstants.ITEM_TYPE_IDEATION);
			mav.addObject("tagList", tagList);

		}
		
		if (!StringUtil.isEmpty(categoryId)) {  //
			idea.setCategoryId(categoryId);
		}		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		mav.addObject("idea", idea);
		mav.addObject("useActiveX", useActiveX);
		mav.addObject("user", user);
		Boolean isSystemAdmin = this.isAdmin();
		mav.addObject("isAdmin", isSystemAdmin);
		return this.bindResult(mav);
	}
	
	@RequestMapping(value = "/createIdea.do", method = RequestMethod.POST)
	public ModelAndView onSubmitItem(@ModelAttribute("idea") @ValidEx  IdIdea idea, BindingResult result, SessionStatus status, @ModelAttribute("isAdmin") boolean isAdmin
							,HttpServletRequest request, ModelMap model) {

		if (result.hasErrors()) {
			
			this.setErrorAttribute("idea", idea, result);
			return new ModelAndView("forward:ideaForm.do")
					.addObject("itemId", idea.getItemId());
			
		}

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		idea.setUpdaterId(user.getUserId());
		idea.setUpdaterName(user.getUserName());
		idea.setPortalId(portal.getPortalId());
		
		

		String itemId = "";
		if (StringUtil.isEmpty(idea.getItemId())) { // 등록일때

			idea.setRegisterId(user.getUserId());
			idea.setRegisterName(user.getUserName());

			String url = request.getRequestURL().toString();

			idea.setPathUrl(url.substring(0, url.lastIndexOf("/"))); // 태그

			itemId = idIdeaService.create(idea);

		} else { // 수정일때
			
			IdIdea item = idIdeaService.get(idea.getItemId(), user.getUserId());
			idea.setRegisterId(item.getRegisterId());
			idea.setRegisterName(item.getRegisterName());
			
			// 권한체크
			accessCheck(isAdmin, idea.getRegisterId());

			idIdeaService.update(idea);
			itemId = idea.getItemId();
		}

		
		model.clear();
		
		StringBuffer param = new StringBuffer();
		
		if (!StringUtil.isEmpty(idea.getDocIframe())) {
			String iframe = "&docIframe=" + idea.getDocIframe()+"&docPopup=true";
			param.append(iframe);
		}
		
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		
		return new ModelAndView("redirect:getView.do?itemId=" + itemId + param.toString());
	}
	/**
	 *  조회
	 * @param itemId	Idea ID
	 * @param docPopup	팝업설정
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getView.do", method = RequestMethod.GET)
	public ModelAndView getView(@RequestParam(value = "itemId", required = false) String itemId,
								@RequestParam(value = "docPopup", required = false, defaultValue = "false") String docPopup)  throws JsonGenerationException, JsonMappingException, IOException{

		String view = (docPopup.equals("true")) ? "collpack/ideation/viewNotTile" : "collpack/ideation/view";

		ModelAndView mav = new ModelAndView(view);
		User user = (User) getRequestAttribute("ikep.user");

		IdIdea idea = idIdeaService.get(itemId, user.getUserId());
		
		idea.setExaminationComment(StringUtil.replaceHtmlString(idea.getExaminationComment()));
	
		mav.addObject("idea", idea);

		// 태그 조회
		List<Tag> tagList = tagService.listTagByItemId(itemId, TagConstants.ITEM_TYPE_IDEATION);
		mav.addObject("tagList", tagList);
		
		
		// 댓글 조회
		IdSearch lineSearch = new IdSearch();
		lineSearch.setListType(IdeationConstants.LIST_TYPE_LINEREPLY);
		lineSearch.setItemId(itemId);

		listAction(lineSearch, mav);
		
		//사용자 즐겨찾기 조회
		boolean isFavorite = portalfavoriteService.exists(itemId, user.getUserId());
		mav.addObject("isFavorite", isFavorite);
		
		//첨부파일 가져오기
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = mapper.writeValueAsString(idea.getFileDataList());
		mav.addObject("fileDataListJson", fileDataListJson);

		return mav;
	}

	/**
	 * 게시글의 쓰레드를 가져오는 메소드 - 삭제
	 *
	 * @param searchCondition 댓글 검색조건
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listReplyItemThreadView")
	public ModelAndView listReplyItemThreadView(
		
			@RequestParam(value="itemId") String itemId,
			@RequestParam(value="itemGroupId") String itemGroupId
			
	) {
		User user =(User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isAdmin();


		return new ModelAndView()

		.addObject("user", user)
		.addObject("itemId", itemId)
		.addObject("isSystemAdmin", isSystemAdmin);
	}
	
	/**
	 * ajax이용한 아이디어글 리스트 가져오기
	 * 
	 * @param idSearch	서치 객체
	 * @return
	 */
	@RequestMapping("/ideaListMore.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView ideaListMore(IdSearch idSearch) {

		ModelAndView mav = new ModelAndView("collpack/ideation/ideaListMore");

		try {
			
			if(!StringUtil.isEmpty(idSearch.getIsMy())){		//내글이면
				User user = (User) getRequestAttribute("ikep.user");
				idSearch.setUserId(user.getUserId());
			}
			
			listAction(idSearch, mav);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}




	/**
	 * 공통 - 리스트
	 * 
	 * @param idSearch
	 * @param mav
	 * @return
	 */
	private void listAction(IdSearch idSearch, ModelAndView mav) {

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		idSearch.setPortalId(portal.getPortalId());

		// 페이징조건
		Properties prop = PropertyLoader.loadProperties("/configuration/ideation-conf.properties");
		int pagePer = Integer.parseInt(prop.getProperty("ideation.default.list.pagePer"));

		if(idSearch.getPagePer() == 0){
			idSearch.setPagePer(pagePer);
		}
		SearchCondition condition = new SearchCondition();
		condition.setPageIndex(idSearch.getPageIndex());
		condition.setPagePerRecord(idSearch.getPagePer());
		
		// 카운트
		int count = 0;
		if(idSearch.getListType() != null && idSearch.getListType().equals(IdeationConstants.LIST_TYPE_LINEREPLY)){
			 count = idLinereplyService.getCountList(idSearch);
		} else {
			 count = idIdeaService.getCountList(idSearch);
			 idSearch.setListType(IdeationConstants.LIST_TYPE_IDEA);
		}

		mav.addObject("totalCount", count);

		condition.terminateSearchCondition(count);
		mav.addObject("pageCondition", condition);

		// 게시물 리스트 가져오기
		idSearch.setEndRowIndex(condition.getEndRowIndex());
		idSearch.setStartRowIndex(condition.getStartRowIndex());

		if(idSearch.getListType() != null && idSearch.getListType().equals(IdeationConstants.LIST_TYPE_LINEREPLY)){
			List<IdLinereply> list = idLinereplyService.list(idSearch);
			mav.addObject("linereplyList", list);
		
		} else {
			
			List<IdIdea> list = idIdeaService.list(idSearch);
			
			List<IdIdea> newList = new ArrayList<IdIdea>();
			for(IdIdea idea : list){
				//토론 리스트
				List<Tag> tagList = tagService.listTagByItemId(idea.getItemId(), TagConstants.ITEM_TYPE_IDEATION);
				idea.setTagList(tagList);
				
				newList.add(idea);
			}
			
			mav.addObject("ideaList", newList);
		}
		mav.addObject("idCategory", idCategoryService.read(idSearch.getCategoryId()));
	}


	/**
	 * 나의 활동 리스트
	 * @param isUserAdopt	채택여부
	 * @param isSuggestion	제안여부
	 * @param myActivities
	 * @return
	 */
	@RequestMapping(value = "/myActivity.do", method = RequestMethod.GET)
	public ModelAndView myActivity(@RequestParam(value="isUserAdopt", required=false) String isUserAdopt
									,@RequestParam(value="isSuggestion", required=false) String isSuggestion
									,@ModelAttribute("myActivities") IdUserActivities myActivities) {

		ModelAndView mav = new ModelAndView("collpack/ideation/myActivity");

		User user = (User) getRequestAttribute("ikep.user");
		String userId = user.getUserId();
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		//활동정보 없을시 유저 정보 가져오기
		if(myActivities == null){
			IdUserActivities userInfo = idUserActivitiesService.getUserInfo(userId);
			mav.addObject("userInfo", userInfo);
		}

		// 전체 아이디어 랭킹
		Properties prop = PropertyLoader.loadProperties("/configuration/ideation-conf.properties");
		String pagePer = prop.getProperty("ideation.top.list.pagePer");

		IdSearch actSearch = new IdSearch();
		actSearch.setEndRowIndex(Integer.parseInt(pagePer));
		actSearch.setIsSuggestion(IdeationConstants.IS_SUGGESTION);
		actSearch.setPortalId(portal.getPortalId());

		List<IdUserActivities> activityList = idUserActivitiesService.list(actSearch);
		mav.addObject("activityList", activityList);

		// 내 아이디어글
		IdSearch itemSearch = new IdSearch();
		itemSearch.setUserId(userId);
		itemSearch.setIsMy(IdeationConstants.IS_MY);
		itemSearch.setListType(IdeationConstants.LIST_TYPE_IDEA);
		itemSearch.setIsUserAdopt(isUserAdopt);
		itemSearch.setIsSuggestion(isSuggestion);

		listAction(itemSearch, mav);
		
		//내가 즐겨찾기한 개수
		int favoriteCount = idIdeaService.getFavorite(userId, IKepConstant.ITEM_TYPE_CODE_IDEATION);
		mav.addObject("favoriteCount", favoriteCount);
		
		return mav;
	}



	/**
	 * 최신 아이디어 리스트
	 * @param idSearch	서치 객체
	 * @return
	 */
	@RequestMapping(value = "/lastList.do", method = RequestMethod.GET)
	public ModelAndView lastList(IdSearch idSearch) {
		
		String view = "collpack/ideation/lastList";
		
		if(idSearch.getDocIframe() != null && idSearch.getDocIframe().equals("true")){
			view = "collpack/ideation/lastListIframe";
		}

		ModelAndView mav = new ModelAndView(view);
		
		listAction(idSearch, mav);

		return mav;
	}
	
	
	/**
	 * 최신 아이디어 리스트
	 * @param idSearch	서치 객체
	 * @return
	 */
	@RequestMapping(value = "/lastListAjax.do", method = RequestMethod.GET)
	public @ResponseBody List<IdIdea> lastListAjax() {
		

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		ModelAndView mav = new ModelAndView("");
		IdSearch idSearch = new IdSearch();
		idSearch.setPortalId(portal.getPortalId());
		
		listAction(idSearch, mav);
		
		Map map = mav.getModel();
		
		List<IdIdea> list = (List<IdIdea>)map.get("ideaList");

		return list;
	}
	
	
	
	/**
	 *  사업화 현황
	 * @param idSearch	서치 객체
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/businessList.do", method = RequestMethod.GET)
	public ModelAndView businessList(IdSearch idSearch)  throws ParseException{

		ModelAndView mav = new ModelAndView("collpack/ideation/businessList");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		idSearch.setPortalId(portal.getPortalId());
		
		
		Date date = timeZoneSupportService.convertTimeZone();
		SimpleDateFormat sdfmt = new SimpleDateFormat("yyyy.MM.dd");
		
		//시작날짜 -기본 1달
		Date startDate = new Date();
		if(StringUtil.isEmpty(idSearch.getStartDateToString())){
			startDate = DateUtil.getRelativeDate(date, 0, -1);
		} else {
			Date sDate = sdfmt.parse(idSearch.getStartDateToString());
			startDate = timeZoneSupportService.convertServerTimeZone(sDate);
		}
		idSearch.setStartDate(startDate); 
		
		//끝날짜
		Date endDate = new Date();
		if((StringUtil.isEmpty(idSearch.getEndDateToString()))){
			endDate = date;
		} else {
			Date eDate = sdfmt.parse(idSearch.getEndDateToString());
			endDate = timeZoneSupportService.convertServerTimeZone(eDate);
		}
		idSearch.setEndDate(endDate);
		
		
		//전체 개수들
		IdIdea totalCountes = idIdeaService.getCountes(idSearch);
		mav.addObject("totalCountes", totalCountes);
		
		//사업화 개수들
		idSearch.setIsBusiness(IdeationConstants.IS_BUSINESS);
		IdIdea businessCountes = idIdeaService.getCountes(idSearch);
		mav.addObject("businessCountes", businessCountes);
		
		
		//제안 idea
		idSearch.setIsBusiness(null);
		listAction(idSearch, mav);
		

		return mav;
	}
	
	
	/**
	 * 활동 평가
	 * @param idSearch	서치 객체
	 * @return
	 */
	@RequestMapping(value = "/activityList.do", method = RequestMethod.GET)
	public ModelAndView activityList(IdSearch idSearch) {
		
		ModelAndView mav = new ModelAndView("collpack/ideation/activityList");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		// 전체 아이디어 랭킹
		Properties prop = PropertyLoader.loadProperties("/configuration/ideation-conf.properties");
		String pagePer = prop.getProperty("ideation.activity.list.pagePer");

		IdSearch actSearch = new IdSearch();
		actSearch.setEndRowIndex(Integer.parseInt(pagePer));
		actSearch.setIsSuggestion(IdeationConstants.IS_SUGGESTION);
		actSearch.setPortalId(portal.getPortalId());

		List<IdUserActivities> suggestList = idUserActivitiesService.list(actSearch);
		mav.addObject("suggestList", suggestList);
		
		//우수활동 사용자
		actSearch.setIsSuggestion(IdeationConstants.IS_CONTRIBUTION);
		List<IdUserActivities> contributionList = idUserActivitiesService.list(actSearch);
		mav.addObject("contributionList", contributionList);

		return mav;
	}
	
	/**
	 * keyIssue List
	 * @param idSearch	서치 객체
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/keyIssueList.do", method = RequestMethod.GET)
	public ModelAndView keyIssueList(IdSearch idSearch) throws ParseException {
		
		ModelAndView mav = new ModelAndView("collpack/ideation/keyIssueList");

		// 인기 아이디어글 top
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		idSearch.setStartRowIndex(Integer.parseInt(IdeationConstants.BASE_NO));
		idSearch.setEndRowIndex(100);
		idSearch.setPortalId(portal.getPortalId());
		
		Date date = timeZoneSupportService.convertTimeZone();
		SimpleDateFormat sdfmt = new SimpleDateFormat("yyyy.MM.dd");
		
		//시작날짜 -기본 1달
		Date startDate = new Date();
		if(idSearch.getStartDateToString()==null){
			startDate = DateUtil.getRelativeDate(date, 0, -1);
		} else {
			idSearch.getStartDateToString();
			Date sDate = sdfmt.parse(idSearch.getStartDateToString());
			startDate = timeZoneSupportService.convertServerTimeZone(sDate);
		}
		idSearch.setStartDate(startDate); 
		
		//끝날짜
		Date endDate = new Date();
		if(idSearch.getEndDateToString()==null){
			endDate = date;
		} else {
			idSearch.getStartDateToString();
			Date eDate = sdfmt.parse(idSearch.getEndDateToString());
			endDate = timeZoneSupportService.convertServerTimeZone(eDate);
		}
		idSearch.setEndDate(endDate);
		
		List<IdIdea> ideaList = idIdeaService.list(idSearch);
		
		if(!ideaList.isEmpty()){
			List<String> itemIdList = new ArrayList<String>();
			for(IdIdea item : ideaList){
				itemIdList.add(item.getItemId());
			}
			
			Tag tag = new Tag();
			tag.setPortalId(portal.getPortalId());
			tag.setTagItemType(TagConstants.ITEM_TYPE_IDEATION);
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

		ModelAndView mav = new ModelAndView("collpack/ideation/main");

		Properties prop = PropertyLoader.loadProperties("/configuration/ideation-conf.properties");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		//전체 개수들
		String limiteDate = prop.getProperty("ideation.main.limite.date");
		
		IdSearch countSearch = new IdSearch();
		countSearch.setLimitDate(limiteDate);
		countSearch.setPortalId(portal.getPortalId());
		IdIdea totalCountes = idIdeaService.getCountes(countSearch);
		mav.addObject("totalCountes", totalCountes);
		
		//사업화 개수들
		IdSearch busSearch = new IdSearch();
		busSearch.setIsBusiness(IdeationConstants.IS_BUSINESS);
		busSearch.setLimitDate(limiteDate);
		busSearch.setPortalId(portal.getPortalId());
		IdIdea businessCountes = idIdeaService.getCountes(busSearch);
		mav.addObject("businessCountes", businessCountes);
		
		//제안 idea
		String pagePer = prop.getProperty("ideation.main.list.pagePer");
		
		IdSearch idSearch= new IdSearch();
		idSearch.setStartRowIndex(Integer.parseInt(IdeationConstants.BASE_NO));
		idSearch.setEndRowIndex(Integer.parseInt(pagePer));
		idSearch.setPortalId(portal.getPortalId());
		List<IdIdea> list = idIdeaService.list(idSearch);
		
		List<IdIdea> newList = new ArrayList<IdIdea>();
		for(IdIdea idea : list){
			List<Tag> tagList = tagService.listTagByItemId(idea.getItemId(), TagConstants.ITEM_TYPE_IDEATION);
			idea.setTagList(tagList);
			newList.add(idea);
		}		
		mav.addObject("ideaList", newList);		
		return mav;
	}

	
	
	/**
	 * 아이디어글삭제
	 * @param idSearch	서치 객체
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/deleteIdea.do", method = RequestMethod.GET)
	public String deleteItem(IdSearch idSearch
							, ModelMap model
						    , @ModelAttribute("isAdmin") boolean isAdmin) {

		User user = (User) getRequestAttribute("ikep.user");
		
		IdIdea idIdea = idIdeaService.get(idSearch.getItemId(), user.getUserId());

		// 권한체크
		accessCheck(isAdmin, idIdea.getRegisterId());

		idIdeaService.remove(idSearch.getItemId());

		model.clear();


		StringBuffer param = new StringBuffer();
		
		if(idSearch.getIsBest() != null){
			String pam = "&isBest="+idSearch.getIsBest();
			param.append(pam);
			
		}
		
		if(idSearch.getIsAdopt() != null){
			String pam = "&isAdopt="+idSearch.getIsAdopt();
			param.append(pam);
			
		}
		
		if(idSearch.getIsBusiness() != null){
			String pam = "&isBusiness="+idSearch.getIsBusiness();
			param.append(pam);
			
		}
		
		if (!StringUtil.isEmpty(idSearch.getDocIframe())) {
			String iframe = "&docIframe=" + idSearch.getDocIframe()+"&docPopup=true";
			param.append(iframe);
		}
		
		return "redirect:lastList.do?1=1"+param.toString();
	}
	
	
	/**
	 * 아이디어글 여러개 한번에 삭제
	 * @param idSearch 서치 객체
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/deleteIdeaMul.do", method = RequestMethod.GET)
	public String deleteIdeaMul(IdSearch idSearch
							, ModelMap model
						    , @ModelAttribute("isAdmin") boolean isAdmin) {

		
		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		String[] itemIdes = idSearch.getItemId().split(",");
		
		for(String itemId : itemIdes){
			idIdeaService.remove(itemId);
		}

		model.clear();

		StringBuffer param = new StringBuffer();
		
		if(idSearch.getIsBest() != null){
			String pam = "&isBest="+idSearch.getIsBest();
			param.append(pam);
			
		}
		
		if(idSearch.getIsAdopt() != null){
			String pam = "&isAdopt="+idSearch.getIsAdopt();
			param.append(pam);
			
		}
		
		if(idSearch.getIsBusiness() != null){
			String pam = "&isBusiness="+idSearch.getIsBusiness();
			param.append(pam);
			
		}
		
		return "redirect:lastList.do?1=1"+param.toString();
	}

	/**
	 * 즐겨찾기 추가
	 * 
	 * @param itemId	Idea Id
	 * @return
	 */
	@RequestMapping("/addFavorite.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String addFavorite(@RequestParam("itemId") String itemId) {

		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			
			idIdeaService.updateFavoriteCount(itemId, IdeationConstants.NUM_INCRASE, user.getUserId());

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	
	/**
	 * 즐겨찾기 삭제
	 * 
	 * @param itemId 	idea ID
	 * @return
	 */
	@RequestMapping("/delFavorite.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String delFavorite(@RequestParam("itemId") String itemId) {

		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			
			idIdeaService.updateFavoriteCount(itemId, IdeationConstants.NUM_DECREASE, user.getUserId());

		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}


	/**
	 * 추천
	 * @param itemId		idea ID
	 * @return	
	 */
	@RequestMapping("/addRecommend.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String addRecommend(@RequestParam("itemId") String itemId) {
		
		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			String userId =  user.getUserId();
			
			boolean exists = idRecommendService.exists(itemId, userId);
			
			if(exists){		//존재하면
				return "exists";
			}
			idRecommendService.create(itemId, userId);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	
	
	/**
	 * 검토 의견 등록
	 * @param itemId				Idea ID
	 * @param examinationComment	검토 의견
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping("/createComment.do")
	public String createComment(@RequestParam("itemId") String itemId
								,@RequestParam("examinationComment") String examinationComment
								,@ModelAttribute("isAdmin") boolean isAdmin
								, ModelMap model
								,@RequestParam(value = "docIframe", required = false, defaultValue = "false") String docIframe) {
		
		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		idIdeaService.updateExamination(itemId, examinationComment);
		
		idIdeaService.updateBusinessItem(itemId, IdeationConstants.BUSINESS_TYPE_ABOPT);
		
		model.clear();
		
		StringBuffer param = new StringBuffer();
		
		if(docIframe.equals("true")){
			String pam = "&docIframe=true&docPopup=true";
			param.append(pam);
			
		}
		
		return "redirect:getView.do?itemId=" + itemId + param.toString();
	}

	
	/**
	 * 사업화 완료
	 * @param itemId	Idea ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping("/updateBusinessComplete.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateBusinessComplete(@RequestParam("itemId") String itemId,@ModelAttribute("isAdmin") boolean isAdmin) {
		
		try {
			
			// 권한 체크
			if (!isAdmin) {
				throw new IKEP4AjaxException("Access Error", null);
			}
			
			idIdeaService.updateBusinessItem(itemId, IdeationConstants.BUSINESS_TYPE_COMPLETE);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	
	
	/**
	 * 사업화 취소
	 * @param itemId	Idea ID
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping("/updateBusinessCancel.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateBusinessCancel(@RequestParam("itemId") String itemId,@ModelAttribute("isAdmin") boolean isAdmin) {
		
		try {
			
			// 권한 체크
			if (!isAdmin) {
				throw new IKEP4AjaxException("Access Error", null);
			}
			
			idIdeaService.updateBusinessItem(itemId, IdeationConstants.BUSINESS_TYPE_NONE);
			
			idIdeaService.updateExamination(itemId, "");
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	
	
	/**
	 * 메일 뷰 개수 업데이트
	 * @param itemId	Idea ID
	 * @return
	 */
	@RequestMapping("/updateMailCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateMailCount(@RequestParam("itemId") String itemId) {
		
		try {
			idIdeaService.updateMailCount(itemId);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	

	/**
	 * 블로그 뷰  개수 업데이트
	 * @param itemId	Idea ID
	 * @return
	 */
	@RequestMapping("/updateMblogCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateMblogCount(@RequestParam("itemId") String itemId) {
		
		try {
			idIdeaService.updateMblogCount(itemId);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	
	
	
}
