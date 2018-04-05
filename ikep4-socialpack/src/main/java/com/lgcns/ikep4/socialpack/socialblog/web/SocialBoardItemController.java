/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.web;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.socialpack.socialblog.base.Constant;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardCode;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogLayoutService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletLayoutService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemRecommendService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemReferenceService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


/**
 * 소셜 블로그 포스팅 정보 관련 Controller
 * 
 * @author 이형운
 * @version $Id: SocialBoardItemController.java 16298 2011-08-19 08:02:31Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/socialblog")
//@SessionAttributes("socialBoardItem")
public class SocialBoardItemController extends BaseController {

	/**
	 * 사용자 정보 컨트롤용 Service
	 */
	@Autowired
	UserService userService;

	/**
	 * 블로그 기본 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogService socialBlogService;

	/**
	 * 블로그 블로깅 글 컨트롤용 Service
	 */
	@Autowired
	private SocialBoardItemService socialBoardItemService;

	/**
	 * 블로그 블로깅 글 직접 접속 관리 컨트롤용 Service
	 */
	@Autowired
	private SocialBoardItemReferenceService socialBoardItemReferenceService; 
	
	/**
	 * 블로그 블로깅  글 추천 관리 컨트롤용 Service
	 */
	@Autowired
	private SocialBoardItemRecommendService  socialBoardItemRecommendService;
	
	/**
	 * 블로그 포틀릿 레이아웃 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogPortletLayoutService socialBlogPortletLayoutService;
	
	/**
	 * 블로그 레이아웃 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogLayoutService socialBlogLayoutService;
	
	/**
	 * 블로그 카테고리 관리 컨트롤용 Service
	 */
	@Autowired
	private SocialCategoryService socialCategoryService;

	/**
	 * 블로그 관리자 권한 체크 컨트롤용 Service
	 */
	@Autowired
	private ACLService aclService;

	/**
	 * 소셜 블로그 포스팅에 대한 시스템 관리자 접근 권한 확인
	 * 
	 * @param user 사용자 정보 객체
	 * @return 시스템 관리자 접근 권한 확인
	 */
	public boolean isSystemAdmin(User user) {
		boolean isSystemAdmin = aclService.isSystemAdmin(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, user.getUserId());
		//isSystemAdmin = true;
		return isSystemAdmin;
	}
	
	
    /**
     * 소셜 블로그 포스팅에 대한 관리자 접근 권한 확인
     * @param blogOwnerId 블로그 소유자 ID
     * @return 블로그 관리 권한 Flag
     */
    public boolean isBlogAdmin(String blogOwnerId) { 
    	
    	boolean isBlogAdmin = false;
    	
    	User sessionUser = readSessionUser();
    	if( blogOwnerId.equals(sessionUser.getUserId())){
    		isBlogAdmin = true;
    	}
    	
        return isBlogAdmin;
    }

    /**
     * 세션의 로그인 User 정보
     * @return 세션에서 받아온 User 정보 객체
     */
	private User readSessionUser() {
		return (User) getRequestAttribute("ikep.user");
	}

	/**
	 * 소셜 블로그 포스팅 목록 별 조회
	 * @param paramSocialBoardItemSearchCondition 블로그 게시글 조회용 객체 
	 * @param blogOwnerId 블로그 소유자 Id
	 * @param searchType 검색 조건
	 * @param searchKeyword 검색 키워드
	 * @return 블로그 포스팅 조회 결과 리스트 View
	 * @throws JsonGenerationException 
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listSocialBoardItemView.do")
	public ModelAndView listSocialBoardItemView(SocialBoardItemSearchCondition paramSocialBoardItemSearchCondition
											, @RequestParam("blogOwnerId") String blogOwnerId
											, @RequestParam(value = "searchType", required = false) String searchType
											, @RequestParam(value = "searchKeyword", required = false) String searchKeyword ) 
	throws JsonGenerationException, JsonMappingException, IOException {

		User user = readSessionUser();
		Boolean isSystemAdmin = isSystemAdmin(user);

		SocialBoardItemSearchCondition socialBoardItemSearchCondition = paramSocialBoardItemSearchCondition;
		if (socialBoardItemSearchCondition == null) {
			socialBoardItemSearchCondition = new SocialBoardItemSearchCondition();
		}
		// socialBoardItemSearchCondition.setPageCount(Integer.parseInt(pageNo));
		
		socialBoardItemSearchCondition.setRegisterId(blogOwnerId);
		if( !(user.getUserId().equals(blogOwnerId))){
			socialBoardItemSearchCondition.setReadPermission("0");
		}
		if( !(StringUtil.isEmpty(searchType)) ){
			if(searchType.equals("searchAllType")){
				socialBoardItemSearchCondition.setSearchColumn("ALL");
				socialBoardItemSearchCondition.setSearchWord(searchKeyword);
			}else if(searchType.equals("searchByDate")){
				socialBoardItemSearchCondition.setSearchDate(searchKeyword);			
			}
		}

		String viewName = "/socialpack/socialblog/listSocialBoardItemView";
		SearchResult<SocialBoardItem> searchResult = socialBoardItemService.listSocialBoardItemBySearchCondition(socialBoardItemSearchCondition);

		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("socialBlog", getBlogInfo(blogOwnerId))
				.addObject("socialBoardCode", new SocialBoardCode())
				.addObject("user", user)
				.addObject("isSystemAdmin", isSystemAdmin)
				.addObject("searchCondition", socialBoardItemSearchCondition)
				.addObject("searchResult", searchResult);

		return modelAndView;
	}

	/**
	 * 직접 조회시 ItemID로 직접 포스팅에 접속하는 경우
	 * 
	 * @param itemId 블로그 게시글 ID
	 * @return 게시글 직접 접속시 보여 주는 개별 글 View
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/readSocialBoardItemView.do")
	public ModelAndView readBoardItemView(@RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		User user = readSessionUser();
		Boolean isSystemAdmin = isSystemAdmin(user);
		String viewName = "/socialpack/socialblog/readSocialBoardItemView";

		SocialBoardItemSearchCondition socialBoardItemSearchCondition = new SocialBoardItemSearchCondition();
		socialBoardItemSearchCondition.setItemId(itemId);
		SearchResult<SocialBoardItem> searchResult = socialBoardItemService.listSocialBoardItemBySearchCondition(socialBoardItemSearchCondition);
			
		if( !(searchResult.isEmptyRecord())){

			// 직접 접속시 읽은 기록 남기기
			SocialBoardItemReference socialBoardItemReference = new SocialBoardItemReference();
			socialBoardItemReference.setItemId(itemId);
			socialBoardItemReference.setRegisterId(user.getUserId());
			if( !(socialBoardItemReferenceService.exists(socialBoardItemReference))){
				socialBoardItemReferenceService.registerReference(socialBoardItemReference);
			}
			if( !(user.getUserId().equals(searchResult.getEntity().get(0).getRegisterId())) ){	
				socialBoardItemSearchCondition.setReadPermission("0");
				searchResult = socialBoardItemService.listSocialBoardItemBySearchCondition(socialBoardItemSearchCondition);
			}

		} else {
			throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.deletedItem");
		}
		
		ModelAndView modelAndView = new ModelAndView(viewName)
				.addObject("user", user)
				.addObject("isSystemAdmin", isSystemAdmin)
				.addObject("searchCondition", searchResult.getSearchCondition())
				.addObject("searchResult", searchResult);

		return modelAndView;
		
	}

	/**
	 * 블로그 게시글 별 해당 Tag 정보를 조회
	 * @param searchCondition 블로그 게시글 조회용 객체
	 * @return 해당 Tag 정보 View
	 */
	@RequestMapping(value = "/listSocialItemTagView.do")
	public ModelAndView listSocialItemTagView(SocialBoardLinereplySearchCondition searchCondition) {

		User user = readSessionUser();
		Boolean isSystemAdmin = isSystemAdmin(user);
		
		return new ModelAndView().addObject("user", user).addObject("isSystemAdmin", isSystemAdmin);

	}

	/**
	 * 블로그 포스팅 신규 작성 Form 페이지를 반환한다.
	 * @param blogOwnerId 블로그 소유자 Id
	 * @return 포스팅 신규 작성 Form view
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/createSocialBoardItemView.do")
	public ModelAndView createSocialBoardItemView( @RequestParam("blogOwnerId") String blogOwnerId ) throws JsonGenerationException, JsonMappingException, IOException {  

		User user = readSessionUser();
		String restrictionType = Constant.SOCICAL_BLOG_RESTRICTION_TYPE;
		long fileSize = Constant.SOCIAL_BLOG_FILE_SIZE;
		
		//권한 체크
		if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(user) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		// 해당 블로그에 정보를 받아 온다.
		SocialBlog socialBlog = getBlogInfo(blogOwnerId);
		List<SocialCategory> socialCategoryList = socialCategoryService.listByBlogId(socialBlog.getBlogId());
		
		SocialBoardItem socialBoardItem = new SocialBoardItem();
		ObjectMapper mapper = new ObjectMapper();
		socialBoardItem.setFileDataListJson((String)mapper.writeValueAsString(socialBoardItem.getFileDataList()));
		
		// 내가 저장하고 있는 Portlet Layout
		List<SocialBlogPortletLayout> socialBlogPortletLayoutList = socialBlogPortletLayoutService.listSocialBlogPortletLayoutByBlogId(socialBlog);
		// 내가 사용 하는 Layout
		SocialBlogLayout mySocialBlogLayout = socialBlogLayoutService.socialBlogLayoutByOwnerId(socialBlog);

		Boolean isSystemAdmin = isSystemAdmin(user); 
		String viewName = "/socialpack/socialblog/createSocialBoardItemView";
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		return new ModelAndView(viewName)
			.addObject("socialBlogPortletLayoutList", socialBlogPortletLayoutList)
			.addObject("mySocialBlogLayout", mySocialBlogLayout)
			.addObject("socialBlog", socialBlog)
			.addObject("socialBoardItem", socialBoardItem)  
			.addObject("socialCategoryList", socialCategoryList)
			.addObject("user", user) 
			.addObject("restrictionType", restrictionType) 
			.addObject("fileSize", fileSize)
			.addObject("editType", "CREATE")
			.addObject("useActiveX", useActiveX)
			.addObject("isSystemAdmin", isSystemAdmin);
		
	}
	
	
	/**
	 * 블로그 포스팅 저장하는 메서드
	 * @param socialBoardItem 블로그 포스팅 글 정보 객체
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 * @return 저장 후 페이지 페이지 조회 View
	 */
	@RequestMapping(value = "/createSocialBoardItem.do")
	public ModelAndView createSocialBoardItem( @Valid SocialBoardItem socialBoardItem, BindingResult result, SessionStatus status, SocialBoardItemSearchCondition searchCondition) {
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		User user = readSessionUser();

		ModelBeanUtil.bindRegisterInfo(socialBoardItem, user.getUserId(), user.getUserName());
		socialBoardItemService.createSocialBoardItem(socialBoardItem, user);

		//SocialBoardItemSearchCondition searchCondition = (SocialBoardItemSearchCondition) this.getRequestAttribute("searchCondition");

		status.setComplete();
		//this.setRequestAttribute("searchCondition", searchCondition);

		//return new ModelAndView("redirect:/socialpack/socialblog/listSocialBoardItemView.do")
		return new ModelAndView("redirect:/socialpack/socialblog/socialBlogHome.do")
			.addObject("blogOwnerId", user.getUserId())
			.addObject("searchCondition", searchCondition);
		
	}

	/**
	 * 블로그 포스팅 특정 게시글 수정시 수정 Form 을 반환한다.
	 * @param blogOwnerId
	 * @param itemId
	 * @return 게시글 수정시 수정 Form View
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateSocialBoardItemView.do")
	public ModelAndView updateBoardItemView( @RequestParam("blogOwnerId") String blogOwnerId
											, @RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {

		User user = readSessionUser();
		Boolean isSystemAdmin = isSystemAdmin(user);
		String restrictionType = Constant.SOCICAL_BLOG_RESTRICTION_TYPE;
		long fileSize = Constant.SOCIAL_BLOG_FILE_SIZE;
		
		//권한 체크
		if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(user) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		SocialBoardItem socialBoardItem = socialBoardItemService.readSocialBoardItem(itemId,user);

		// 해당 블로그에 정보를 받아 온다.
		SocialBlog socialBlog = getBlogInfo(blogOwnerId);
		List<SocialCategory> socialCategoryList = socialCategoryService.listByBlogId(socialBlog.getBlogId());
		
		// 내가 저장하고 있는 Portlet Layout
		List<SocialBlogPortletLayout> socialBlogPortletLayoutList = socialBlogPortletLayoutService.listSocialBlogPortletLayoutByBlogId(socialBlog);
		// 내가 사용 하는 Layout
		SocialBlogLayout mySocialBlogLayout = socialBlogLayoutService.socialBlogLayoutByOwnerId(socialBlog);

		String viewName = "/socialpack/socialblog/updateSocialBoardItemView";
		
		//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		
		return new ModelAndView(viewName)
			.addObject("socialBlogPortletLayoutList", socialBlogPortletLayoutList)
			.addObject("mySocialBlogLayout", mySocialBlogLayout)
			.addObject("socialBlog", socialBlog)
			.addObject("socialBoardItem", socialBoardItem)
			.addObject("socialCategoryList", socialCategoryList)
			.addObject("user", user)
			.addObject("restrictionType", restrictionType) 
			.addObject("fileSize", fileSize)
			.addObject("editType", "EDIT")
			.addObject("useActiveX", useActiveX)
			.addObject("isSystemAdmin", isSystemAdmin);
	}
	
	/**
	 * 블로그 포스팅 글 수정 
	 * @param socialBoardItem 블로그 기본 정보 객체
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 * @return 저장 후 페이지 페이지 조회 View
	 */
	@RequestMapping(value = "/updateSocialBoardItem.do") 
	public ModelAndView updateBoardItem( @Valid SocialBoardItem socialBoardItem , BindingResult result , SessionStatus status) {
		
		if (result.hasErrors()) { 
			throw new IKEP4AjaxValidationException(result, messageSource);
		} 
		
		User user = readSessionUser();
		ModelBeanUtil.bindRegisterInfo(socialBoardItem, user.getUserId(), user.getUserName());
		
		socialBoardItemService.updateSocialBoardItem(socialBoardItem, user);
		SocialBoardItemSearchCondition searchCondition = (SocialBoardItemSearchCondition) this.getRequestAttribute("searchCondition");
		
		status.setComplete(); 
		
		//return new ModelAndView("redirect:/socialpack/socialblog/listSocialBoardItemView.do")
		return new ModelAndView("redirect:/socialpack/socialblog/socialBlogHome.do")
			.addObject("blogOwnerId", user.getUserId())
			.addObject("searchCondition", searchCondition);
	}
	
	
	/**
	 * 블로그 본인이 직적 작성한 블로그 포스팅 삭제 
	 * @param blogOwnerId 블로그 소유자 Id
	 * @param itemId 블로그 게시글 ID
	 * @return 삭제 후 페이지 페이지 조회 View
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/userDeleteSocialBoardItem.do") 
	public ModelAndView userDeleteSocialBoardItem(@RequestParam("blogOwnerId") String blogOwnerId
												, @RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException {
		
		User user = readSessionUser();
		
		//권한 체크
		if(!( isBlogAdmin(blogOwnerId) || isSystemAdmin(user) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		SocialBoardItem socialBoardItem = socialBoardItemService.readSocialBoardItem(itemId,user); 
		
		socialBoardItemService.userDeleteSocialBoardItem(socialBoardItem);
		ModelBeanUtil.bindRegisterInfo(socialBoardItem, user.getUserId(), user.getUserName()); 
		SocialBoardItemSearchCondition searchCondition = (SocialBoardItemSearchCondition) this.getRequestAttribute("searchCondition");
		
		return new ModelAndView("redirect:/socialpack/socialblog/listSocialBoardItemView.do")
			.addObject("blogOwnerId", blogOwnerId)
			.addObject("searchCondition", searchCondition);
	}


	/**
	 *  관리자 권한으로 삭제 버튼 호출시  블로그 포스팅 삭제 
	 * @param blogOwnerId 블로그 소유자 Id
	 * @param itemId 블로그 게시글 ID
	 * @return 삭제 후 페이지 페이지 조회 View
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/adminDeleteSocialBoardItem.do") 
	public ModelAndView adminDeleteBoardItem( @RequestParam("blogOwnerId") String blogOwnerId
											, @RequestParam("itemId") String itemId) throws JsonGenerationException, JsonMappingException, IOException { 
		
		User user = readSessionUser();
		//권한 체크
		if(!( isSystemAdmin(user) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		SocialBoardItem socialBoardItem = socialBoardItemService.readSocialBoardItem(itemId,user);
		socialBoardItemService.adminDeleteSocialBoardItem(socialBoardItem);
	
		SocialBoardItemSearchCondition searchCondition = (SocialBoardItemSearchCondition) this.getRequestAttribute("searchCondition");
		
		return new ModelAndView("redirect:/socialpack/socialblog/listSocialBoardItemView.do")
			.addObject("blogOwnerId", blogOwnerId)
			.addObject("searchCondition", searchCondition);
		
	}

	/**
	 * 블로그 포스팅 글 추천  
	 * @param itemId 블로그 게시글 ID
	 */
	@RequestMapping(value = "/updateRecommendCount.do") 
	public @ResponseBody String updateRecommendCount(@RequestParam("blogOwnerId") String blogOwnerId
												, @RequestParam("itemId") String itemId) { 
		
		String resultRecmd = "SUCCESSRECMD";
		User user = readSessionUser();
		
		try {
			
			// 자신의 Blog는 추천 안함
			if( isBlogAdmin(blogOwnerId) ){
				resultRecmd = "MYSELFRECMD";
			}else{
		
				SocialBoardItemRecommend socialBoardItemRecommend = new SocialBoardItemRecommend();
				socialBoardItemRecommend.setRegisterId(user.getUserId());
				socialBoardItemRecommend.setItemId(itemId);
				
				if( !(socialBoardItemRecommendService.exists(socialBoardItemRecommend)) ){
					
					// 추천 데이타 입력
					socialBoardItemRecommendService.create(socialBoardItemRecommend);
					
					// 추천 데이타 Update Count
					socialBoardItemService.updateRecommendCount(itemId);
				}else{
					resultRecmd = "ALREADYRECMD";
				}
			}
			
			return resultRecmd ;
			
		} catch(Exception exception) { 
			throw new IKEP4AjaxException("code", exception);
		}
		
		
	}


	/**
	 * 해당 사용자의 Blog 정보를 조회 하기 위한 메서드
	 * 
	 * @param blogOwnerId 블로그 소유자 Id
	 * @return
	 */
	public SocialBlog getBlogInfo(String blogOwnerId) {

		User sessionUser = readSessionUser();
		
		// 해당 블로그에 정보를 받아 온다.
		SocialBlog searchSocialBlog = new SocialBlog();
		searchSocialBlog.setOwnerId(blogOwnerId);

		return socialBlogService.select(searchSocialBlog,sessionUser.getLocaleCode());
	}
	
	
	/**
	 *  메일 뷰 개수 업데이트
	 * @param itemId		
	 * @return
	 */
	@RequestMapping("/updateMailCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateMailCount(String itemId) {
		
		try {
			socialBoardItemService.updateMailCount(itemId);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
	

	/**
	 * 블로그 뷰  개수 업데이트
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/updateMblogCount.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateMblogCount(String itemId) {
		
		try {
			socialBoardItemService.updateMBlogCount(itemId);
			
		} catch(Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		} 
		
		return "success";
	}
}
