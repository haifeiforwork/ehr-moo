/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.socialpack.microblogging.model.Follow;
import com.lgcns.ikep4.socialpack.microblogging.service.FollowService;
import com.lgcns.ikep4.socialpack.socialblog.base.Constant;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogLayoutService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogPortletLayoutService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialCategoryService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 소셜 블로그 주요 프로세스 관련처리를 위한 Controller
 * 
 * @author 이형운
 * @version $Id: SocialBlogMainController.java 19512 2012-06-26 09:36:14Z malboru80 $
 */
@Controller
@RequestMapping(value = "/socialpack/socialblog")
@SessionAttributes("socialBoardItem")
public class SocialBlogMainController extends BaseController {

	/**
	 * 사용자 정보 컨트롤용 Service
	 */
	@Autowired
	UserService userService;

	/**
	 * 블로그 카테고리 컨트롤용 Service
	 */
	@Autowired
	private SocialCategoryService socialCategoryService;

	/**
	 * 블로그 기본 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogService socialBlogService;
	
	/**
	 * 블로그 블로깅 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBoardItemService socialBoardItemService;
	
	/**
	 * 블로그 댓글 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBoardLinereplyService socialBoardLinereplyService;
	
	/**
	 * 블로그 포틀릿 레이아웃 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogPortletLayoutService socialBlogPortletLayoutService;
	
	/**
	 * 블로그 레이아웃 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogLayoutService socialBlogLayoutService;
	
	/**
	 * 블로그 방문자 정보 컨트롤용 Service
	 */
	@Autowired
	private SocialBlogVisitService socialBlogVisitService;
	
	/**
	 * Follow 정보 컨트롤용 Service
	 */
	@Autowired
	private FollowService followService;

	/**
	 * 블로그 관리자 권한 체크 컨트롤용 Service
	 */
	@Autowired
	private ACLService aclService;

	/**
	 * 블로그 테그 정보 관리 컨트롤용 Service
	 */
	@Autowired
	private TagService tagService;

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
     * 세션의 로그인 User 정보
     * @return 세션에서 받아온 User 정보 객체
     */
    private User readSessionUser() {  
    	return (User)getRequestAttribute("ikep.user"); 
    }

	/**
	 * 소셜 블로그 메인 페이지 호출
	 * @param blogOwnerId 블로그 소유자 Id
	 * @param itemId 블로그 Item ID
	 * @return 블로그 Main Home View
	 */
	@RequestMapping(value = "/socialBlogHome.do")
	public ModelAndView socialBlogHome(@RequestParam(value = "blogOwnerId", required = false) String paramBlogOwnerId
									, @RequestParam(value = "itemId", required = false ) String itemId ) {

		User sessionUser = readSessionUser();

		ModelAndView mav = new ModelAndView("/socialpack/socialblog/socialBlogHome");
		String blogOwnerId = paramBlogOwnerId;
		
		if( StringUtil.isEmpty(blogOwnerId)){
			blogOwnerId = sessionUser.getUserId();
		}

		//해당 블로그에 정보를 받아 온다.
		SocialBlog socialBlog = getBlogInfo(blogOwnerId);
		mav.addObject("socialBlog", socialBlog);
		mav.addObject("isSystemAdmin", isSystemAdmin(sessionUser));
		mav.addObject("isBlogAdmin", isBlogAdmin(blogOwnerId));
		if( !(blogOwnerId.equals(sessionUser.getUserId())) ){
			// Social Blog 방문자 기록 추가 
			SocialBlogVisit socialBlogVisit = new SocialBlogVisit();
			socialBlogVisit.setBlogId(socialBlog.getBlogId());
			socialBlogVisit.setVisitorId(sessionUser.getUserId());
			socialBlogVisit.setVisitFlag("TODAY");
			socialBlogVisit.setBlogOwnerId(blogOwnerId);
			socialBlogVisitService.recordSocialBlogVisitByDay(socialBlogVisit);
		}
		
		// 내가 저장하고 있는 Portlet Layout
		List<SocialBlogPortletLayout> socialBlogPortletLayoutList = socialBlogPortletLayoutService.listSocialBlogPortletLayoutByBlogId(socialBlog);
		mav.addObject("socialBlogPortletLayoutList", socialBlogPortletLayoutList);
		
		// 내가 사용 하는 Layout
		SocialBlogLayout mySocialBlogLayout = socialBlogLayoutService.socialBlogLayoutByOwnerId(socialBlog);
		mav.addObject("mySocialBlogLayout", mySocialBlogLayout);

		return mav;
	}

	/**
	 * 소셜 블로그 팝업용 글 조회 페이지 호출 직접 게시글만 호출 될때 
	 * @param itemId
	 * @return 블로그 해당 게시글 조회 팝업 View
	 */
	@RequestMapping(value = "/socialBlogPopup.do")
	public ModelAndView socialBlogPopup(@RequestParam(value = "itemId") String itemId ) {
		//@RequestParam(value = "blogOwnerId") String blogOwnerId

		ModelAndView mav = new ModelAndView("/socialpack/socialblog/socialBlogPopup");
		mav.addObject("itemId",itemId);

		return mav;
	}

	/**
	 * 소셜 블로그의 블로그 User의 Profile 정보를 조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 블로그 프로파일 정보 View
	 */
	@RequestMapping(value = "/viewSocialBlogProfile.do")
	public ModelAndView viewSocialBlogProfile(@RequestParam("blogOwnerId") String blogOwnerId) {

		ModelAndView mav = new ModelAndView("/socialpack/socialblog/viewSocialBlogProfile");

		User user = readSessionUser();
		Boolean isSystemAdmin = isSystemAdmin(user);
		Boolean isBlogAdmin = isBlogAdmin(blogOwnerId);
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("isBlogAdmin", isBlogAdmin);
		
		// 해당 블로그에 정보를 받아 온다.
		mav.addObject("socialBlog", getBlogInfo(blogOwnerId));

		User profile = userService.read(blogOwnerId);
		mav.addObject("profile", profile);

		mav.addObject("blogOwnerId", blogOwnerId);
		
		mav.addObject("microblogHomeUrl", "/socialpack/microblogging/privateHome.do");
		mav.addObject("profileHomeUrl", "/portal/main/listUserMain.do?rightFrameUrl=/support/profile/getProfile.do");

		return mav;
	}

	/**
	 * 소셜 블로그의 블로그 User의 Profile 정보를 조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 블로그 프로파일 편집 창
	 */
	@RequestMapping(value = "/editSocialBlogProfile.do")
	public ModelAndView editSocialBlogProfile(@RequestParam("blogOwnerId") String blogOwnerId,
			@RequestParam("editflag") String editflag) {

		User user = readSessionUser();
		//권한 체크
		if( (editflag != null && editflag.equals("edit")) && !( isBlogAdmin(blogOwnerId) || isSystemAdmin(user) )){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/editSocialBlogProfile");

		// 해당 블로그에 정보를 받아 온다.
		mav.addObject("socialBlog", getBlogInfo(blogOwnerId));
		
		mav.addObject("editflag", editflag);
		mav.addObject("blogOwnerId", blogOwnerId);

		return mav;
	}

	/**
	 * 소셜 블로그의 소개 정보를 수정 저장하는 메서드
	 * @param socialBlog 블로그 기본 정보 객체
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/saveSocialBlogProfile.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void saveSocialBlogProfile( @Valid SocialBlog socialBlog 
													, BindingResult result
													, SessionStatus status) {
		User user = readSessionUser();
		
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}else{
			socialBlog.setOwnerId(user.getUserId());
			socialBlogService.updateIntroduction(socialBlog);
		}

		status.setComplete();

	}

	/**
	 * 소셜 블로그의 블로그 User의 Profile 정보를 조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 블로그 카테고리 View
	 */
	@RequestMapping(value = "/viewSocialBlogCategory.do")
	public ModelAndView viewSocialBlogCategory(@RequestParam("blogOwnerId") String blogOwnerId) {

		User user = readSessionUser();
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/viewSocialBlogCategory");

		SocialBlog socialBlog = getBlogInfo(blogOwnerId);

		List<SocialCategory> socialCategoryServiceList = socialCategoryService.listByBlogId(socialBlog.getBlogId());

		mav.addObject("socialCategoryServiceList", socialCategoryServiceList);
		
		SocialBoardItemSearchCondition socialBoardItemSearchCondition = new SocialBoardItemSearchCondition();
		socialBoardItemSearchCondition.setRegisterId(blogOwnerId);
		if( !(user.getUserId().equals(blogOwnerId))){
			socialBoardItemSearchCondition.setReadPermission("0");
		}
		Integer totalPostingCount = socialBoardItemService.countSocialBoardItemBySearchCondition(socialBoardItemSearchCondition);
		mav.addObject("totalPostingCount", totalPostingCount);

		return mav;
	}

	/**
	 * 소셜 블로그의 블로그 Activity 정보를 조회 Follower, Following, 포스팅 수 조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 블로그 Activity 페이지 View
	 */
	@RequestMapping(value = "/viewSocialBlogAcitvity.do")
	public ModelAndView viewSocialBlogAcitvity(@RequestParam("blogOwnerId") String blogOwnerId) {

		User user = readSessionUser();
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/viewSocialBlogAcitvity");
		
		// Follow & Following Count
		Follow follow = new Follow();
		follow.setUserId(blogOwnerId);
		follow.setFollowingUserId(blogOwnerId);
		List<Follow> followList = followService.getFollower(follow);
		List<Follow> followingList = followService.getFollowing(follow);
		
		mav.addObject("followerCount", followList.size());
		mav.addObject("followingCount", followingList.size());
		
		SocialBoardItemSearchCondition socialBoardItemSearchCondition = new SocialBoardItemSearchCondition();
		socialBoardItemSearchCondition.setRegisterId(blogOwnerId);
		if( !(user.getUserId().equals(blogOwnerId))){
			socialBoardItemSearchCondition.setReadPermission("0");
		}
		Integer totalPostingCount = socialBoardItemService.countSocialBoardItemBySearchCondition(socialBoardItemSearchCondition);
		mav.addObject("totalPostingCount", totalPostingCount);

		return mav;
	}

	/**
	 * 소셜 블로그의 블로그 포스팅 달력를 조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 포스팅 달력 View
	 */
	@RequestMapping(value = "/viewSocialBlogCalendar.do")
	public ModelAndView viewSocialBlogCalendar(@RequestParam("blogOwnerId") String blogOwnerId ) {

		User user = readSessionUser();
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/viewSocialBlogCalendar");

		if (user.getLocaleCode().equals(portal.getDefaultLocaleCode())) {
			mav.addObject("userName", user.getUserName());
		} else {
			mav.addObject("userName", user.getUserEnglishName());
		}
		mav.addObject("blogOwnerId", blogOwnerId);
		
		return mav;
	}
	
	/**
	 * 소셜 블로그의 블로그 포스팅 이력 일자 조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @param searchDate 검색 기준 월단뒤 (yyyy.MM)
	 * @return 블로그 포스팅 이력 날짜 리스트
	 */
	@RequestMapping(value = "/getSocialBlogPostingDate.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<String> getSocialBlogPostingDate(@RequestParam("blogOwnerId") String blogOwnerId
											, @RequestParam(value="searchDate", required=false ) String paramSearchDate) {

		String searchDate = paramSearchDate;
		if(StringUtil.isEmpty(searchDate)){
			searchDate = DateUtil.getToday("yyyy.MM");
		}
		
		SocialBoardItemSearchCondition socialBoardItemSearchCondition = new SocialBoardItemSearchCondition();
		socialBoardItemSearchCondition.setRegisterId(blogOwnerId);
		socialBoardItemSearchCondition.setSearchDate(searchDate);
		List<String> postingDateMap = socialBoardItemService.selectPostingDate(socialBoardItemSearchCondition);

		return postingDateMap;
		
	}

	/**
	 * 소셜 블로그의 블로그 Following 리스트  조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return Following 리스트 View
	 */
	@RequestMapping(value = "/viewSocialBlogFollowing.do")
	public ModelAndView viewSocialBlogFollowing(@RequestParam("blogOwnerId") String blogOwnerId) {

		ModelAndView mav = new ModelAndView("/socialpack/socialblog/viewSocialBlogFollowing");
		
		mav.addObject("blogOwnerId", blogOwnerId);
		
		return mav;
	}

	/**
	 * 소셜 블로그의 블로그 최근 댓글 목록 조회 
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 최근 댓글 목록 View
	 */
	@RequestMapping(value = "/viewSocialBlogRecentComment.do")
	public ModelAndView viewSocialBlogRecentComment(@RequestParam("blogOwnerId") String blogOwnerId) {

		ModelAndView mav = new ModelAndView("/socialpack/socialblog/viewSocialBlogRecentComment");
		Integer iRowCount = Constant.SOCIAL_BLOG_RECENT_COMMENT_COUNT;
		
		Map<String, Object> recentCmtMap = new HashMap<String, Object>();
		recentCmtMap.put("blogOwnerId", blogOwnerId);
		recentCmtMap.put("rowTopXCount", iRowCount);
		
		List<SocialBoardLinereply> top5LinereplyList = socialBoardLinereplyService.listTopXLinereply(recentCmtMap);
		mav.addObject("top5LinereplyList", top5LinereplyList);

		return mav;
	}

	/**
	 * 소셜 블로그의 블로그 Tag Cloud 조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return Tag Cloud View
	 */
	@RequestMapping(value = "/viewSocialBlogTagCloud.do")
	public ModelAndView viewSocialBlogTagCloud(@RequestParam("blogOwnerId") String blogOwnerId) {

		// 단순하게 템플릿 레이아웃을 불러 주는 부분.
		ModelAndView mav = new ModelAndView("/socialpack/socialblog/viewSocialBlogTagCloud");
		mav.addObject("blogOwnerId", blogOwnerId);
		
		return mav;
	}
	
	/**
	 * 소셜 블로그의 블로그 Tag Cloud 데이타 XML 조회
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return Tag Cloud 데이타 XML View
	 */
	@RequestMapping(value = "/getSocialBlogTagXml.do")
	public ModelAndView getSocialBlogTagXml(@RequestParam("blogOwnerId") String blogOwnerId) {

		User user = readSessionUser();

		Tag tag = new Tag();
		tag.setTagItemType(TagConstants.ITEM_TYPE_SOCIAL_BLOG); // itemType TagConstants에서 모듈에 맞게 사용
		tag.setTagItemSubType(blogOwnerId); // Item Sub Type 블로그의 경우 개별 블로그 단위여야 하기 때문에 userID를 기준으로 보여 준다.
		tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY); // 정렬순 TagConstants에
															// 정의되어 있음. - 넣지않으면
															// 인기순으로 가져옴.
		tag.setPortalId(user.getPortalId()); // portalID
		tag.setEndRowIndex(Constant.SOCIAL_BLOG_TAG_CLOUD_COUNT); // 태그 가져올 개수

		List<Tag> tagList = tagService.listTagByItemType(tag);
		
		return new ModelAndView("support/tagging/tagXml") 
		.addObject("tagList", tagList)            //태그 리스트
		.addObject("itemType", TagConstants.ITEM_TYPE_SOCIAL_BLOG)          //item type
		.addObject("subItemType",blogOwnerId);   //sub item type

	}

	/**
	 * 해당 사용자의 Blog 정보를 조회 하기 위한 메서드
	 * @param blogOwnerId 블로그 소유자 ID
	 * @return 블로그 기본 정보 객체
	 */
	public SocialBlog getBlogInfo(String blogOwnerId) {

		User sessionUser = readSessionUser();
		
		// 해당 블로그에 정보를 받아 온다.
		SocialBlog searchSocialBlog = new SocialBlog();
		searchSocialBlog.setOwnerId(blogOwnerId);
		
		return socialBlogService.select(searchSocialBlog,sessionUser.getLocaleCode());
	}
}
