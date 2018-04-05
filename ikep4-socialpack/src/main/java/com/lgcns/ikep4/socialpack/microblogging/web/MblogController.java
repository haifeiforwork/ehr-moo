/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.socialpack.microblogging.base.Constant;
import com.lgcns.ikep4.socialpack.microblogging.model.Favorite;
import com.lgcns.ikep4.socialpack.microblogging.model.Follow;
import com.lgcns.ikep4.socialpack.microblogging.model.InvitingInfo;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;
import com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember;
import com.lgcns.ikep4.socialpack.microblogging.model.Mblog;
import com.lgcns.ikep4.socialpack.microblogging.model.QueryReturn;
import com.lgcns.ikep4.socialpack.microblogging.model.Setting;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.socialpack.microblogging.service.FavoriteService;
import com.lgcns.ikep4.socialpack.microblogging.service.FollowService;
import com.lgcns.ikep4.socialpack.microblogging.service.MbgroupMemberService;
import com.lgcns.ikep4.socialpack.microblogging.service.MbgroupService;
import com.lgcns.ikep4.socialpack.microblogging.service.MblogService;
import com.lgcns.ikep4.socialpack.microblogging.service.SearchService;
import com.lgcns.ikep4.socialpack.microblogging.service.SettingService;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory;
import com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService;
import com.lgcns.ikep4.support.externalSNS.service.FacebookService;
import com.lgcns.ikep4.support.externalSNS.service.TwitterService;
import com.lgcns.ikep4.support.poll.model.Poll;
import com.lgcns.ikep4.support.poll.service.PollService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * microblog 관리
 * 
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MblogController.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/microblogging")
public class MblogController extends BaseController {

	@Autowired
	private MblogService mblogService;

	@Autowired
	private FollowService followService;

	@Autowired
	private MbgroupService mbgroupService;

	@Autowired
	private MbgroupMemberService mbgroupMemberService;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private SearchService searchService;

	@Autowired
	private PollService pollService;

	@Autowired
	private TwitterService twitterService;

	@Autowired
	private FacebookService facebookService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SettingService settingService;

	@Autowired
	private ArAbuseHistoryService arAbuseHistoryService;
	
	/**
	 * Microblog 글을 등록한다.
	 *
	 * @param mblog Mblog 정보
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return 결과메세지
	 */
	@RequestMapping("/createMblog")
	public @ResponseBody
	String onSubmit(@ValidEx  Mblog mblog, BindingResult result, SessionStatus status) {
		if(result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); //BindingResult와 BaseController의 MessageSource를 parameter로 전달해야 합니다.
		}

		String actionResult = "ok";
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");


		/*
		 * idgen Service에서 id생성하여 넣기
		 */
		String id = idgenService.getNextId();
		mblog.setMblogId(id);

		mblog.setRegisterId(user.getUserId());
		mblog.setRegisterName(user.getUserName());

		if (mblog.getIsRetweetAllowed() == null) {
			mblog.setIsRetweetAllowed("1");
		}

		mblogService.create(mblog, user);

		String externalTarget = "";
		String externalAccount = "";
		
		// twitter 연동시
		if ("1".equals(mblog.getTwitter())) {
			//twitterUp(mblog.getContents());

			String accessToken = user.getTwitterAccount();
			String accessTokenSecret = user.getTwitterAuthCode();
			
			try {
				String twitterUserId = twitterService.twitterUp(accessToken, accessTokenSecret, mblog.getContents());
				
				externalTarget = "TWITTER";
				externalAccount = twitterUserId;

			} catch (Exception e) {
				//e.printStackTrace();
				
				// accessTokenValue DB Insert
				User twitterAuthUser = new User();
				twitterAuthUser.setTwitterAccount("");
				twitterAuthUser.setTwitterAuthCode("");
				twitterAuthUser.setUpdaterId(user.getUserId());
				twitterAuthUser.setUpdaterName(user.getUserName());
				twitterAuthUser.setUserId(user.getUserId());
				
				userService.updateTwitterInfo(twitterAuthUser);
	
				// Session(ikep.user) 재설정
				user.setTwitterAccount("");
				user.setTwitterAuthCode("");
				setRequestAttribute("ikep.user", user);
				
				actionResult = "TWITTER_ERROR";
				
			}
		}
		
		
		// facebook 연동시
		if ("1".equals(mblog.getFacebook())) {
			String sessionKey = user.getFacebookAuthCode();

			try {
				String uid = facebookService.createFacebook(sessionKey,  mblog.getContents());

				if(!"".equals(externalTarget)){
					externalTarget 	= externalTarget  + ","  + "FACEBOOK";
					externalAccount = externalAccount + "," + uid;
				}else{
					externalTarget 	= "FACEBOOK";
					externalAccount =  uid;
				}
				
			} catch (Exception e) {

				User facebookAuthUser = new User();
				facebookAuthUser.setFacebookAccount("");
				facebookAuthUser.setFacebookAuthCode("");
				facebookAuthUser.setUpdaterId(user.getUserId());
				facebookAuthUser.setUpdaterName(user.getUserName());
				facebookAuthUser.setUserId(user.getUserId());
				
				userService.updateFacebookInfo(facebookAuthUser);

				user.setFacebookAuthCode("");
				setRequestAttribute("ikep.user", user);
				
				actionResult = "FACEBOOK_ERROR";
				//e.printStackTrace();
			}

		}

		// 금지어 체크모듈 호출
		// 외부 트위터 또는 페이스북에 등록되는 포스팅만 abuse reporting에 history 남김
		if ("1".equals(mblog.getTwitter()) || "1".equals(mblog.getFacebook())) {
			ArAbuseHistory arAbuseHistory = new ArAbuseHistory();
			
			arAbuseHistory.setModuleCode("MB");
			arAbuseHistory.setItemId(id);
			arAbuseHistory.setExternalTarget(externalTarget);
			arAbuseHistory.setExternalAccount(externalAccount);
			arAbuseHistory.setContents(mblog.getContents());
			arAbuseHistory.setPortalId(user.getPortalId());
			
			arAbuseHistoryService.checkAndSaveProhibitWord(arAbuseHistory, user);
		}
		
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return actionResult;
	}

	/**
	 * Microblog 글을 단건조회한다.
	 *
	 * @param mblogId Microblog의 id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getMblog.do")
	public ModelAndView getView(@RequestParam("mblogId") String mblogId) {
		Mblog mblog = new Mblog();
		
		if (mblogId != null) {
			mblog = mblogService.read(mblogId);
			
			// GroupId 가 있으면 사용자가 그룹에 속했는지 확인, 그룹멤버가 아니면 알림페이지 리턴.
			if(null != mblog.getMbgroupId() && 0 < mblog.getMbgroupId().length()){
				ModelAndView modelAndView = checkGroupMember(mblog.getMbgroupId());
				if(null != modelAndView){
					return modelAndView;
				}
			}			
		}
		
		ModelAndView mav = new ModelAndView("/socialpack/microblogging/viewMicroblog");
		mav.addObject("mblog", mblog);
		
		return mav;
	}

	/**
	 * 사용자의 Microblog 홈피를 조회한다. 인자가 없을 때는 로그인사용자의 홈피가 기본으로 조회된다.
	 *
	 * @param ownerId Microblog 홈피의 주인id
	 * @param tabNum Microblog 홈피로딩시 처음보여줄 tab의 번호
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/privateHome.do")
	public ModelAndView privateHome(@RequestParam(value="ownerId", required=false) String paramOwnerId, @RequestParam(value="tabNum", required=false) String tabNum) {

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("loginUser.getUserId():" + loginUser.getUserId());
			log.debug("loginUser.getPortalId():" + loginUser.getPortalId());
		}

		// 1. 사용자 기본정보 조회
		User ownerUserInfo = null;
		// 넘어온 ownerId가 없으면 로그인 사용자 홈피이다.
		String ownerId = paramOwnerId;
		
		if(null == ownerId || "".equals(ownerId)){

			ownerUserInfo = loginUser;
			
			ownerId = ownerUserInfo.getUserId();
		}
		else
		{
			MblogSearchCondition userSearchCondition = new MblogSearchCondition();
			userSearchCondition.setMemberId(ownerId);
			userSearchCondition.setLoginId(loginUser.getUserId());
			
			ownerUserInfo = mblogService.userInfoByUserId(userSearchCondition);
		}
		
		// 2. 트윗한 글 수
		int ownerTweetCount = mblogService.myTweetCount(ownerId);

		// 3. following
		Follow follow = new Follow();
		follow.setUserId(ownerId);
		List<Follow> ownerFollowingList = followService.getFollowing(follow);

		// 4. follower
		follow = new Follow();
		follow.setFollowingUserId(ownerId);
		List<Follow> ownerFollowerList = followService.getFollower(follow);

		// 5. 속한 그룹
		List<Mbgroup> ownerGroupList = mbgroupService.myGroupList(ownerId);

		List<InvitingInfo> invitingInfoList = null;
		List<String> trendList = null;

		List<Follow> bothFollowingList = null;
		int bothFollowingListCount = 0;
		List<Follow> bothFollowerList = null;
		int bothFollowerListCount = 0;
		List<Mbgroup> bothGroupList = null;
		int bothGroupListCount = 0;

		// 개인블로그 자신이 들어갔을 때
		if (ownerId.equals(loginUser.getUserId())) {
			// 6. 초대받은 그룹
			MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
			mblogSearchCondition.setMemberId(ownerId);
			mblogSearchCondition.setSearchType(Constant.SEARCH_TYPE_MEMBER);

			invitingInfoList = mbgroupMemberService.invitingInfoList(mblogSearchCondition);

			// 7. trends 최신 등록/검색된 태그
			MblogSearchCondition mblogSearchConditionForTag = new MblogSearchCondition();
			mblogSearchConditionForTag.setPortalId(loginUser.getPortalId());

			trendList = searchService.trendList(mblogSearchConditionForTag);
		} else // 타인의 블로그에 들어갔을 때
		{
			Map map = new HashMap();
			map.put("ownerId", ownerId);
			map.put("userId", loginUser.getUserId());

			// 6. both Following
			bothFollowingList = followService.getBothFollowing(map);
			bothFollowingListCount = bothFollowingList.size();
			// 7. both follower
			bothFollowerList = followService.getBothFollower(map);
			bothFollowerListCount = bothFollowerList.size();

			// 8. both join group
			bothGroupList = mbgroupService.bothGroup(map);
			bothGroupListCount = bothGroupList.size();
		}

		// 9. Max Feed Count 조회
		int maxFeedCount = Constant.MAX_FEED_COUNT_DEFAULT;
		Setting setting = settingService.read(loginUser.getUserId());
		
		if(null != setting ){
			maxFeedCount = setting.getMaxFeedCount();
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/privateHome");
		mav.addObject("ownerId"					, ownerId);
		mav.addObject("ownerUserInfo"			, ownerUserInfo);
		mav.addObject("ownerTweetCount"			, ownerTweetCount);
		mav.addObject("ownerFollowingList"		, ownerFollowingList);
		mav.addObject("ownerFollowingCount"		, ownerFollowingList.size());
		mav.addObject("ownerFollowerList"		, ownerFollowerList);
		mav.addObject("ownerFollowerCount"		, ownerFollowerList.size());
		mav.addObject("ownerGroupList"			, ownerGroupList);
		mav.addObject("ownerGroupSize"			, ownerGroupList.size());

		mav.addObject("invitingInfoList"		, invitingInfoList);
		mav.addObject("trendList"				, trendList);

		mav.addObject("bothFollowingList"		, bothFollowingList);
		mav.addObject("bothFollowingListCount"	, bothFollowingListCount);
		mav.addObject("bothFollowerList"		, bothFollowerList);
		mav.addObject("bothFollowerListCount"	, bothFollowerListCount);
		mav.addObject("bothGroupList"			, bothGroupList);
		mav.addObject("bothGroupListCount"		, bothGroupListCount);

		mav.addObject("maxFeedCount"			, maxFeedCount);
		mav.addObject("tabNum"					, tabNum);
		
		return mav;
	}

	/**
	 * workspace에 속하는 user들만의 Timeline을 보여줄 페이지를 조회한다. 
	 *
	 * @param workspaceId workspace의 id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/workspaceTimelineHome.do")
	public ModelAndView workspaceTimelineHome(String workspaceId) {

		if (log.isDebugEnabled()) {
			log.debug("workspaceId:" + workspaceId);
		}
		
		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("loginUser.getUserId():" + loginUser.getUserId());
		}

		// 1. Max Feed Count 조회
		int maxFeedCount = Constant.MAX_FEED_COUNT_DEFAULT;
		Setting setting = settingService.read(loginUser.getUserId());
		
		if(null != setting ){
			maxFeedCount = setting.getMaxFeedCount();
		}

		if (log.isDebugEnabled()) {
			log.debug("maxFeedCount:" + maxFeedCount);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/workspaceTimelineHome");

		mav.addObject("workspaceId"			, workspaceId);
		mav.addObject("maxFeedCount"		, maxFeedCount);
		
		return mav;
	}

	/**
	 * team에 속하는 user들만의 Timeline을 보여줄 페이지를 조회한다. 
	 *
	 * @param groupId team의 id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/teamTimelineHome.do")
	public ModelAndView teamTimelineHome(String groupId) {

		if (log.isDebugEnabled()) {
			log.debug("teamTimelineHome groupId:" + groupId);
		}
		
		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("loginUser.getUserId():" + loginUser.getUserId());
		}

		// 1. Max Feed Count 조회
		int maxFeedCount = Constant.MAX_FEED_COUNT_DEFAULT;
		Setting setting = settingService.read(loginUser.getUserId());
		
		if(null != setting ){
			maxFeedCount = setting.getMaxFeedCount();
		}

		if (log.isDebugEnabled()) {
			log.debug("maxFeedCount:" + maxFeedCount);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/teamTimelineHome");

		mav.addObject("groupId"				, groupId);
		mav.addObject("maxFeedCount"		, maxFeedCount);
		
		return mav;
	}

	/**
	 * following하는 user들만의 Timeline을 보여줄 페이지를 조회한다. 
	 *
	 * @param groupId team의 id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/followingTimelineHome.do")
	public ModelAndView followingTimelineHome(String userId) {

		if (log.isDebugEnabled()) {
			log.debug("followingTimelineHome userId:" + userId);
		}
		
		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("loginUser.getUserId():" + loginUser.getUserId());
		}

		// 1. Max Feed Count 조회
		int maxFeedCount = Constant.MAX_FEED_COUNT_DEFAULT;
		Setting setting = settingService.read(loginUser.getUserId());
		
		if(null != setting ){
			maxFeedCount = setting.getMaxFeedCount();
		}

		if (log.isDebugEnabled()) {
			log.debug("maxFeedCount:" + maxFeedCount);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/followingTimelineHome");

		mav.addObject("userId"				, userId);
		mav.addObject("maxFeedCount"		, maxFeedCount);
		
		return mav;
	}

	/**
	 * Microblog의 특정 Group의 홈페이지를 조회한다. 
	 *
	 * @param mbgroupId Microblog Group의 id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/groupHome.do")
	public ModelAndView groupHome(String mbgroupId) {

		if (log.isDebugEnabled()) {
			log.debug("groupHome mbgroupId:" + mbgroupId);
		}
		
		// 1. 그룹 기본정보 조회. 
		Mbgroup nowMbgroup = mbgroupService.read(mbgroupId);
		
		// 원래 없는 그룹이면 요청하신 페이지가 없습니다 페이지로.
		if(null == nowMbgroup )
		{
			return new ModelAndView("/socialpack/microblogging/noPageInfo");
			
		} // 삭제된 그룹이면 삭제 내용을 보여주는 페이지로.
		else if(1 == nowMbgroup.getIsDelete() )
		{
			ModelAndView mav = new ModelAndView("/socialpack/microblogging/deletedGroupInfo");

			// 삭제한 updater_id 로 user 정보 조회해야 한다.
			User updaterInfo = userService.read(nowMbgroup.getUpdaterId());

			if (log.isDebugEnabled()) {
				log.debug("groupHome nowMbgroup.getUpdaterId():" + nowMbgroup.getUpdaterId());
				log.debug("groupHome updaterInfo:" + updaterInfo);
			}
			mav.addObject("nowMbgroup"	, nowMbgroup);
			mav.addObject("updaterInfo"	, updaterInfo);
			
			return mav;
		}
		else
		{
			// 세션 객체 가지고 오기
			User loginUser = (User) getRequestAttribute("ikep.user");
		
			// 2. 그룹 회원 여부
			MbgroupMember mbgroupMember = new MbgroupMember();
			mbgroupMember.setMbgroupId(mbgroupId);
			mbgroupMember.setMemberId(loginUser.getUserId());
	
			boolean isGroupMember = mbgroupMemberService.exists(mbgroupMember);
	
			// 3. 그룹에서 트윗한 글 수
			int groupTweetCount = mblogService.groupTweetCount(mbgroupId);
	
			// 4. 멤버 리스트
			mbgroupMember = new MbgroupMember();
			mbgroupMember.setMbgroupId(mbgroupId);
			mbgroupMember.setStatus("1");
	
			List<MbgroupMember> nowMemberList = mbgroupMemberService.mbgroupMemberList(mbgroupMember);
	
			// 5. 그룹에 초대상태인 사람 리스트
			List<InvitingInfo> invitingInfoList = null;
			MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
			mblogSearchCondition.setMbgroupId(mbgroupId);
			mblogSearchCondition.setSearchType(Constant.SEARCH_TYPE_GROUP);
	
			invitingInfoList = mbgroupMemberService.invitingInfoList(mblogSearchCondition);
	
			// 6. trends 최신 그룹에 등록/검색된 태그
			mblogSearchCondition.setPortalId(loginUser.getPortalId());
			List<String> trendList = searchService.trendList(mblogSearchCondition);

			// 7. Max Feed Count 조회
			int maxFeedCount = Constant.MAX_FEED_COUNT_DEFAULT;
			Setting setting = settingService.read(loginUser.getUserId());
			
			if(null != setting ){
				maxFeedCount = setting.getMaxFeedCount();
			}
			
			ModelAndView mav = new ModelAndView("/socialpack/microblogging/groupHome");
	
			mav.addObject("mbgroupId"				, mbgroupId);
			mav.addObject("isGroupMember"			, isGroupMember);
			mav.addObject("nowMbgroup"				, nowMbgroup);
			mav.addObject("groupTweetCount"			, groupTweetCount);
			mav.addObject("nowMemberList"			, nowMemberList);
			mav.addObject("nowMemberListSize"		, nowMemberList.size());
			mav.addObject("invitingInfoList"		, invitingInfoList);
			mav.addObject("invitingInfoListSize"	, invitingInfoList.size());
			mav.addObject("trendList"				, trendList);

			mav.addObject("maxFeedCount"			, maxFeedCount);

			return mav;
		}
	}

	/**
	 * Microblog 등록용 팝업를 리턴한다. 
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getTweettingPage.do")
	public ModelAndView getTwittingPage() {

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/tweettingpage");

		return mav;
	}

	/**
	 * 검색조건을 이용해서 트윗글 목록을 조회한다. (본인 글(+following 사용자의 글) + 본인이 맨션된 글) 
	 *
	 * @param ownerId, registerOnly, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/timelineList.do")
	public ModelAndView timelineList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(loginUser.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("loginUser.getUserId():" + loginUser.getUserId());
			log.debug("timelineList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.timelineList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("timelineList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * Workspace의 Timeline List 반환
	 * 
	 * @param workspaceId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/workspaceTimelineList.do")
	public ModelAndView workspaceTimelineList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(loginUser.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("workspaceTimelineList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.workspaceTimelineList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("workspaceTimelineList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * team(조직도)에 속한 user들의 Timeline List 반환
	 * 
	 * @param ownerGroupId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/teamTimelineList.do")
	public ModelAndView teamTimelineList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(loginUser.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("teamTimelineList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.teamTimelineList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("teamTimelineList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * following하는 user들만의 Timeline List 반환
	 * 
	 * @param ownerId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/followingTimelineList.do")
	public ModelAndView followingTimelineList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(loginUser.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("followingTimelineList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.followingTimelineList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("followingTimelineList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * Thread List 반환 (같은 thread id에 해당하는 글)
	 * 
	 * @param ownerId, registerOnly, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/threadList.do")
	public ModelAndView threadList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(loginUser.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("threadList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.threadList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("threadList list:" + list);
		}

		mav.addObject("threadType", "Y");
		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 그룹의 Timeline List 반환
	 * 
	 * @param mbgroupId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/groupTimelineList.do")
	public ModelAndView groupTimelineList(MblogSearchCondition mblogSearchCondition) {

		// 그룹에 속했는지 확인, 그룹멤버가 아니면 알림페이지 리턴.
		ModelAndView modelAndView = checkGroupMember(mblogSearchCondition.getMbgroupId());
		if(null != modelAndView){
			return modelAndView;
		}
		
		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(loginUser.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("groupTimelineList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.groupTimelineList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("groupTimelineList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 그룹의 Thread List 반환
	 * 
	 * @param mbgroupId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/groupThreadList.do")
	public ModelAndView groupThreadList(MblogSearchCondition mblogSearchCondition) {

		// 그룹에 속했는지 확인, 그룹멤버가 아니면 알림페이지 리턴.
		ModelAndView modelAndView = checkGroupMember(mblogSearchCondition.getMbgroupId());
		if(null != modelAndView){
			return modelAndView;
		}
		
		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(loginUser.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("groupThreadList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.groupThreadList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("groupThreadList list:" + list);
		}

		mav.addObject("threadType", "Y");
		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 공개대상 Timeline List 반환
	 * 
	 * @param standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/timelineAllList.do")
	public ModelAndView timelineAllList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(loginUser.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("timelineAllList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.timelineAllList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("timelineAllList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 공개대상 Thread List 반환
	 * 
	 * @param standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/threadAllList.do")
	public ModelAndView threadAllList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(loginUser.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("threadAllList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.threadAllList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("threadAllList list:" + list);
		}

		mav.addObject("threadType", "Y");
		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * Addon (이미지, 파일, 투표, link) 관련 트윗글 List 반환
	 * 
	 * @param ownerId, addonType, registerOnly, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addonListByAddonType.do")
	public ModelAndView addonListByAddonType(MblogSearchCondition mblogSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("addonListByAddonType mblogSearchCondition:" + mblogSearchCondition);
		}
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());

		// AddonType별 리턴될 url
		String returnUrl = "/socialpack/microblogging/timelineListForImage";

		if (Constant.ADDON_TYPE_URL.equals(mblogSearchCondition.getAddonType())) {
			returnUrl = "/socialpack/microblogging/timelineListForUrl";
		} else if (Constant.ADDON_TYPE_POLL.equals(mblogSearchCondition.getAddonType())) {
			returnUrl = "/socialpack/microblogging/timelineListForVote";
		} else if (Constant.ADDON_TYPE_IMG.equals(mblogSearchCondition.getAddonType())) {
			returnUrl = "/socialpack/microblogging/timelineListForImage";
		} else if (Constant.ADDON_TYPE_FILE.equals(mblogSearchCondition.getAddonType())) {
			returnUrl = "/socialpack/microblogging/timelineListForFile";
		}

		ModelAndView mav = new ModelAndView(returnUrl);
		List<Mblog> list = mblogService.addonListByAddonType(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("addonListByAddonType list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 그룹의 Addon (이미지, 파일, 투표, link) 관련 트윗글 List 반환
	 * 
	 * @param mbgroupId, addonType, registerOnly, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/groupAddonListByAddonType.do")
	public ModelAndView groupAddonListByAddonType(MblogSearchCondition mblogSearchCondition) {

		// 그룹에 속했는지 확인, 그룹멤버가 아니면 알림페이지 리턴.
		ModelAndView modelAndView = checkGroupMember(mblogSearchCondition.getMbgroupId());
		if(null != modelAndView){
			return modelAndView;
		}
		
		if (log.isDebugEnabled()) {
			log.debug("groupAddonListByAddonType mblogSearchCondition:" + mblogSearchCondition);
		}
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());

		// AddonType별 리턴될 url
		String returnUrl = "/socialpack/microblogging/timelineListForImage";

		if (Constant.ADDON_TYPE_URL.equals(mblogSearchCondition.getAddonType())) {
			returnUrl = "/socialpack/microblogging/timelineListForUrl";
		} else if (Constant.ADDON_TYPE_POLL.equals(mblogSearchCondition.getAddonType())) {
			returnUrl = "/socialpack/microblogging/timelineListForVote";
		} else if (Constant.ADDON_TYPE_IMG.equals(mblogSearchCondition.getAddonType())) {
			returnUrl = "/socialpack/microblogging/timelineListForImage";
		} else if (Constant.ADDON_TYPE_FILE.equals(mblogSearchCondition.getAddonType())) {
			returnUrl = "/socialpack/microblogging/timelineListForFile";
		}

		ModelAndView mav = new ModelAndView(returnUrl);
		List<Mblog> list = mblogService.groupAddonListByAddonType(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("groupAddonListByAddonType list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 자신이 리트윗한 트윗글 List 반환
	 * 
	 * @param ownerId, registerOnly, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/retwitByMeList.do")
	public ModelAndView retwitByMeList(MblogSearchCondition mblogSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("retwitByMeList mblogSearchCondition:" + mblogSearchCondition);
		}
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.retwitByMeList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("retwitByMeList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 타인에 의해 리트윗된 자신의 트윗글 List 반환
	 * 
	 * @param ownerId, registerOnly, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/retwitByOtherList.do")
	public ModelAndView retwitByOtherList(MblogSearchCondition mblogSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("retwitByOtherList mblogSearchCondition:" + mblogSearchCondition);
		}
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setLoginId(user.getUserId());

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.retwitByOtherList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("retwitByOtherList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 나를(owner + 트윗터 홈 주인이 속한 부서(그룹)) 멘션한 트윗글 List 반환 
	 * 
	 * @param ownerId, ownerGroupId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/myMentionList.do")
	public ModelAndView myMentionList(MblogSearchCondition mblogSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("myMentionList mblogSearchCondition:" + mblogSearchCondition);
		}
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setOwnerGroupId(user.getGroupId());
		mblogSearchCondition.setLoginId(user.getUserId());

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.myMentionList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("myMentionList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 그룹 외부에서 그룹으로 리트윗된 트윗글 List 반환
	 * 
	 * @param mbgroupId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/retwitFromOutsideList.do")
	public ModelAndView retwitFromOutsideList(MblogSearchCondition mblogSearchCondition) {

		// 그룹에 속했는지 확인, 그룹멤버가 아니면 알림페이지 리턴.
		ModelAndView modelAndView = checkGroupMember(mblogSearchCondition.getMbgroupId());
		if(null != modelAndView){
			return modelAndView;
		}
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setLoginId(user.getUserId());

		if (log.isDebugEnabled()) {
			log.debug("retwitFromOutsideList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.retwitFromOutsideList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("retwitFromOutsideList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * 그룹 외부로 리트윗된 트윗글 List 반환
	 * 
	 * @param mbgroupId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/retwitToOutsideList.do")
	public ModelAndView retwitToOutsideList(MblogSearchCondition mblogSearchCondition) {

		// 그룹에 속했는지 확인, 그룹멤버가 아니면 알림페이지 리턴.
		ModelAndView modelAndView = checkGroupMember(mblogSearchCondition.getMbgroupId());
		if(null != modelAndView){
			return modelAndView;
		}
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setLoginId(user.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("retwitToOutsideList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.retwitToOutsideList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("retwitToOutsideList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());

		return mav;
	}

	/**
	 * favorite 지정한 트윗글 List 반환
	 * 
	 * @param ownerId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/favoriteList.do")
	public ModelAndView favoriteList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setLoginId(user.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("favoriteList mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.favoriteList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("favoriteList list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());
		//mav.addObject("addDelFavoriteTag", "Y");

		return mav;
	}

	/**
	 * 해당 해쉬태그를 가진 글 + 해당 맨션이 있는 글 목록을 조회하여 리턴한다.
	 * 
	 * @param mbtagType, searchWord, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchTweets.do")
	public ModelAndView searchTweets(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		if (log.isDebugEnabled()) {
			log.debug("searchTweets =====================");
		}

		// searchString 에서 mbtagType와 searchWord 를 분리해낸다.
		setSearchCondition(mblogSearchCondition, "T");

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.tweetListByTag(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("searchTweets list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());
		mav.addObject("searchString", mblogSearchCondition.getSearchString());

		return mav;
	}

	/**
	 * Tag 타입별 사용자 검색 결과 목록 리턴.
	 * 해쉬 Tag 기반 검색 결과: 해당 해쉬태그를 가진 글 작성자 User List 반환
	 * 맨션 Tag 기반 검색 결과: 아이디가 유사한 사용자 List 반환
	 * 
	 * @param mbtagType, searchWord, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchPeople.do")
	public ModelAndView searchPeople(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		if (log.isDebugEnabled()) {
			log.debug("searchPeople =====================");
			log.debug("user.getUserId():" + user.getUserId());
			log.debug("mblogSearchCondition:" + mblogSearchCondition);
		}
		
		// searchString 에서 mbtagType와 searchWord 를 분리해낸다.
		setSearchCondition(mblogSearchCondition, "P");

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/userList");
		List<User> list = null;

		if (Constant.TAG_TYPE_MENTION.equals(mblogSearchCondition.getMbtagType())) {
			list = mblogService.userListByMentionTag(mblogSearchCondition);
		} else if (Constant.TAG_TYPE_HASH.equals(mblogSearchCondition.getMbtagType())) {
			list = mblogService.userListByHashTag(mblogSearchCondition);
		}

		if (log.isDebugEnabled()) {
			log.debug("searchPeople list:" + list);
		}

		mav.addObject("userList", list);
		mav.addObject("size", list.size());
		mav.addObject("searchString", mblogSearchCondition.getSearchString());
		mav.addObject("addFollowTag", "Y");

		if (( null == mblogSearchCondition.getStandardUserId() || "".equals(mblogSearchCondition.getStandardUserId()) ) && 0 < list.size()) {
			mav.addObject("addFollowScript", "Y");
		}

		return mav;
	}

	/**
	 * Group의 Tag 기반 검색 결과: 트윗글 List 반환: 해당 해쉬태그를 가진 글 목록, 해당 맨션이 있는 글 목록
	 * 
	 * @param mbtagType, mbgroupId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchGroupTweets.do")
	public ModelAndView searchGroupTweets(MblogSearchCondition mblogSearchCondition) {

		// 그룹에 속했는지 확인, 그룹멤버가 아니면 알림페이지 리턴.
		ModelAndView modelAndView = checkGroupMember(mblogSearchCondition.getMbgroupId());
		if(null != modelAndView){
			return modelAndView;
		}
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		if (log.isDebugEnabled()) {
			log.debug("searchGroupTweets =====================");
		}

		// searchString 에서 mbtagType와 searchWord 를 분리해낸다.
		setSearchCondition(mblogSearchCondition,"T");

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/timelineList");
		List<Mblog> list = mblogService.tweetListByGroupTag(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("searchGroupTweets list:" + list);
		}

		mav.addObject("mblogList", list);
		mav.addObject("size", list.size());
		mav.addObject("searchString", mblogSearchCondition.getSearchString());

		return mav;
	}

	/**
	 * Group에서 Tag 타입별 사용자 검색 결과 목록 리턴.
	 * 
	 * 맨션 Tag 기반 검색 결과: 아이디가 유사한 사용자 List 반환
	 * 해쉬 Tag 기반 검색 결과: 해당 해쉬태그를 가진 글 작성자 List 반환
	 * 
	 * @param searchWord, mbgroupId, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchGroupPeople.do")
	public ModelAndView searchGroupPeople(MblogSearchCondition mblogSearchCondition) {

		// 그룹에 속했는지 확인, 그룹멤버가 아니면 알림페이지 리턴.
		ModelAndView modelAndView = checkGroupMember(mblogSearchCondition.getMbgroupId());
		if(null != modelAndView){
			return modelAndView;
		}
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setPortalId(user.getPortalId());
		
		if (log.isDebugEnabled()) {
			log.debug("searchGroupPeople =====================");
			log.debug("user.getUserId():" + user.getUserId());
			log.debug("mblogSearchCondition:"+mblogSearchCondition);
		}

		// searchString 에서 mbtagType와 searchWord 를 분리해낸다.
		setSearchCondition(mblogSearchCondition, "P");

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/userList");
		List<User> list = null;

		if (Constant.TAG_TYPE_MENTION.equals(mblogSearchCondition.getMbtagType())) {
			list = mblogService.userListByGroupMentionTag(mblogSearchCondition);
		} else if (Constant.TAG_TYPE_HASH.equals(mblogSearchCondition.getMbtagType())) {
			list = mblogService.userListByGroupHashTag(mblogSearchCondition);
		}

		if (log.isDebugEnabled()) {
			log.debug("searchGroupPeople list:" + list);
		}

		mav.addObject("userList", list);
		mav.addObject("size", list.size());
		mav.addObject("searchString", mblogSearchCondition.getSearchString());
		mav.addObject("addFollowTag", "Y");

		if (( null == mblogSearchCondition.getStandardUserId() || "".equals(mblogSearchCondition.getStandardUserId()) ) && 0 < list.size()) {
			mav.addObject("addFollowScript", "Y");
		}

		return mav;
	}

	/**
	 * Microblog를 삭제한다.
	 * 
	 * @param id Microblog의 id
	 * @return 결과
	 */
	@RequestMapping(value = "/removeMblog.do")
	public @ResponseBody
	String remove(String id) {

		mblogService.delete(id);

		return "ok";
	}

	/**
	 * 답글용 팝업 페이지를 리턴한다.
	 * 
	 * @param mblogId Microblog의 id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getMblogReplyForm.do")
	public ModelAndView getMblogReplyForm(String mblogId) {
		ModelAndView mav = new ModelAndView("/socialpack/microblogging/replyPopup");
		if (mblogId != null) {
			Mblog mblog = mblogService.read(mblogId);
			mav.addObject("originMblog", mblog);
		}
		return mav;
	}

	/**
	 * 리트윗용 팝업 페이지를 리턴한다.
	 * 
	 * @param mblogId Microblog의 id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getMblogRetwitForm.do")
	public ModelAndView getMblogRetwitForm(String mblogId) {
		ModelAndView mav = new ModelAndView("/socialpack/microblogging/retwitPopup");
		if (mblogId != null) {
			Mblog mblog = mblogService.read(mblogId);
			mav.addObject("originMblog", mblog);
		}
		return mav;
	}

	/**
	 * 최근 한달간 맨션한 사용자(우선)와 following한 사용자 를 합해서 총 10명 List 반환 (자동완성 기능용)
	 * 
	 * @param memberId 를 담은 MblogSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchForAutoComplete.do")
	public ModelAndView searchForAutoComplete(MblogSearchCondition mblogSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("searchForAutoComplete =====================");
			log.debug("mblogSearchCondition:"+mblogSearchCondition);
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setLoginId(user.getUserId());
		
		ModelAndView mav = new ModelAndView("/socialpack/microblogging/userIdList");
		
		List<String> list = mblogService.userIdForAutoComplete(mblogSearchCondition);

		if (log.isDebugEnabled()) {
			log.debug("searchForAutoComplete list:" + list);
		}

		mav.addObject("userIdList", list);
		mav.addObject("size", list.size());

		return mav;
	}
	
	/**
	 * 게시글 관련정보 상세조회
	 * 
	 * @param mblogId를 담은 Mblog 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getDetailsForTweet.do")
	public ModelAndView getDetailsForTweet(Mblog mblog) {

		if (log.isDebugEnabled()) {
			log.debug("getDetailsForTweet mblog:" + mblog.toString());
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		// 1. 원글 + 본글 + reply 리스트로 조회
		MblogSearchCondition searchCondition = new MblogSearchCondition();
		searchCondition.setMblogId(mblog.getMblogId());
		searchCondition.setLoginId(user.getUserId());
		
		List<Mblog> list = mblogService.parentNReplyListByMblogId(searchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForTweet list:" + list);
		}

		// 2. 리트윗한 사람 목록 조회 + 수
		List<User> retweetUserList = mblogService.retweetUserListByMblogId(searchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForTweet retweetUserList:" + retweetUserList);
		}

		// 3. 리트윗 일자별 현황
		String retweetStatistics = this.getJSONArrayByDataList(mblogService.retweetStatisticsByMblogId(mblog.getMblogId()));

		if (log.isDebugEnabled()) {
			log.debug("getDetailsForTweet retweetStatistics jsonArray:" + retweetStatistics);
		}
		
		// 4. 트윗에 맨션된 사용자 정보 리스트

		MblogSearchCondition userSearchCondition = new MblogSearchCondition();
		userSearchCondition.setMblogId(mblog.getMblogId());
		userSearchCondition.setLoginId(user.getUserId());
		
		List<User> mentionedUserList = mblogService.mentionedUserListByMblogId(userSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForTweet mentionedUserList:" + mentionedUserList);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/detailsForTweet");
		mav.addObject("selectedMblogId", mblog.getMblogId());
		mav.addObject("mblogList", list);
		mav.addObject("retweetUserList", retweetUserList);
		mav.addObject("retweetUserListSize", retweetUserList.size());
		mav.addObject("retweetStatistics", retweetStatistics);
		mav.addObject("mentionedUserList", mentionedUserList);

		return mav;
	}

	/**
	 * 사용자 관련정보 상세조회
	 * 
	 * @param userId 사용자의 id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getDetailsForUser.do")
	public ModelAndView getDetailsForUser(String userId) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForUser userId:" + userId);
			log.debug("user.getUserId():" + user.getUserId());
		}

		// 1. 사용자 기본정보 조회
		MblogSearchCondition userSearchCondition = new MblogSearchCondition();
		userSearchCondition.setMemberId(userId);
		userSearchCondition.setLoginId(user.getUserId());
		
		User userInfo = mblogService.userInfoByUserId(userSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForUser userInfo:" + userInfo);
		}

		// 2. 트윗한 글 수
		int myTweetCount = mblogService.myTweetCount(userId);
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForUser myTweetCount:" + myTweetCount);
		}

		// 3. following 수
		int myFollowingCount = followService.myFollowingCount(userId);
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForUser myFollowingCount:" + myFollowingCount);
		}

		// 4. follower 수
		int myFollowerCount = followService.myFollowerCount(userId);
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForUser myFollowerCount:" + myFollowerCount);
		}

		// 5. 속한 그룹
		List<Mbgroup> myGroupList = mbgroupService.myGroupList(userId);
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForUser myGroupList:" + myGroupList);
		}

		// 5. 최신 트윗글 - 대상자의 최신 트윗3개
		MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
		mblogSearchCondition.setOwnerId(userId);
		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setRegisterOnly("Y");
		mblogSearchCondition.setFetchSize(Constant.THE_NEWEST_TWEET_SIZE);

		List<Mblog> mblogList = mblogService.timelineListUserOnly(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getDetailsForUser mblogList:" + mblogList);
		}
		
		ModelAndView mav = new ModelAndView("/socialpack/microblogging/detailsForUser");
		mav.addObject("userInfo", userInfo);
		mav.addObject("myTweetCount", myTweetCount);
		mav.addObject("myFollowingCount", myFollowingCount);
		mav.addObject("myFollowerCount", myFollowerCount);
		mav.addObject("myGroupList", myGroupList);
		mav.addObject("myGroupListSize", myGroupList.size());
		mav.addObject("mblogList", mblogList);
		mav.addObject("mblogList", mblogList);

		return mav;
	}

	/*
	 * *********************************************************************************
	 * Follow 관련
	 * *****************************************************************
	 * ****************
	 */

	/**
	 * 홈피주인이 Following하고 있는 사용자 List와  홈피주인의 Follower 사용자 List 반환
	 * 
	 * @param ownerId를 담은 MblogSearchCondition 객체
	 * @return  Following List와 Follower List를 담은 Map객체
	 */
	@RequestMapping(value = "/getFollowingFollowerList.do")
	public @ResponseBody
	Map<String, Object> getFollowingFollowerList(MblogSearchCondition mblogSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("getFollowingFollowerList =====================");
		}

		// following, follower 둘 다 담긴 List
		Map<String, Object> item = new HashMap<String, Object>();

		try {
			// 세션 객체 가지고 오기
			User user = (User) getRequestAttribute("ikep.user");

			if (log.isDebugEnabled()) {
				log.debug("user.getUserId():" + user.getUserId());
			}

			mblogSearchCondition.setLoginId(user.getUserId());

			List<User> followingList = mblogService.followingList(mblogSearchCondition);
			if (log.isDebugEnabled()) {
				log.debug("getFollowingFollowerList followingList:" + followingList);
			}

			List<User> followerList = mblogService.followerList(mblogSearchCondition);
			if (log.isDebugEnabled()) {
				log.debug("getFollowingFollowerList followerList:" + followerList);
			}

			item.put("followingList", followingList);
			item.put("followerList", followerList);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return item;

	}

	/**
	 * 홈피주인이 Following하고 있는(+로그인한 사용자도 동시에 Following하고 있는) 사용자 List 반환
	 * 
	 * @param ownerId, bothFollow, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "/getFollowingList.do")
	public ModelAndView getFollowingList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(user.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("getFollowingList =====================");
			log.debug("user.getUserId():" + user.getUserId());
			log.debug("mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/userList");
		List<User> list = mblogService.followingList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getFollowingList list:" + list);
		}

		mav.addObject("userList", list);

		if (( null == mblogSearchCondition.getStandardUserId() || "".equals(mblogSearchCondition.getStandardUserId()) ) && 0 < list.size()) {
			mav.addObject("addFollowScript", "Y");
		}
		
		return mav;
	}

	/**
	 * 홈피주인의 Follower ( +동시에 로그인한 사용자의 Follower) 사용자 List 반환
	 * 
	 * @param ownerId, bothFollow, standardType, standardMblogId 를 담은 MblogSearchCondition 객체
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "/getFollowerList.do")
	public ModelAndView getFollowerList(MblogSearchCondition mblogSearchCondition) {
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		mblogSearchCondition.setLoginId(user.getUserId());
		
		if (log.isDebugEnabled()) {
			log.debug("getFollowerList =====================");
			log.debug("user.getUserId():" + user.getUserId());
			log.debug("mblogSearchCondition:" + mblogSearchCondition);
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/userList");
		List<User> list = mblogService.followerList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getFollowerList list:" + list);
		}

		mav.addObject("userList", list);

		if (( null == mblogSearchCondition.getStandardUserId() || "".equals(mblogSearchCondition.getStandardUserId()) ) && 0 < list.size()) {
			mav.addObject("addFollowScript", "Y");
		}
		
		return mav;
	}

	/**
	 * 최근 등록한 이미지/파일 관련 트윗글 (파일명, ADDON_CODE) List 반환
	 * 
	 * @param addonType 를 담은 MblogSearchCondition 객체
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "/getRecentFileListByAddonType.do")
	public ModelAndView getRecentFileListByAddonType(MblogSearchCondition mblogSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("getRecentFileListByAddonType =====================");
			log.debug("mblogSearchCondition:" + mblogSearchCondition);
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		mblogSearchCondition.setLoginId(user.getUserId());

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/fileList");
		List<Mblog> list = mblogService.recentFileListByAddonType(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getRecentFileListByAddonType list:" + list);
		}

		mav.addObject("fileList", list);

		return mav;
	}

	/**
	 * 맨션된 id가 userId(U)인지 groupId(G)인지 둘다 아닌지(N) 를 확인하여 리턴한다.
	 * 
	 * @param objectId  를 담은 MblogSearchCondition 객체
	 * @return  id 종류구분
	 */
	@RequestMapping(value = "/getObjectIdType.do")
	public @ResponseBody String getObjectIdType(String objectId) {
		
		if (log.isDebugEnabled()) {
			log.debug("getFollowerList =====================");
			log.debug("objectId:" + objectId);
		}
		
		String returnType = "";
		
		if(	userService.exists(objectId)){
			returnType = "U";
		}else if(mbgroupService.exists(objectId)){
			returnType = "G";
		}else{
			returnType = "N";
		}

		return returnType;
	}

	
	
	/*
	 * *********************************************************************************
	 * Favorite 관련
	 * ***************************************************************
	 * ******************
	 */

	/**
	 * 해당 마이크로블로그를 관심글 등록한다.
	 * 
	 * @param mblogId Microblog의 id
	 * @return  결과
	 */
	@RequestMapping("/createFavorite.do")
	public @ResponseBody
	String createFavorite(String mblogId) {

		if (log.isDebugEnabled()) {
			log.debug("mblogId:" + mblogId);
		}

		String result = "";
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		// favorite으로 등록되어있는 것과 개인설정에서 Max favorite count 설정된 값 조회해서 비교.
		int savedCount = favoriteService.countByUserId(user.getUserId());

		int maxFavoriteCount = Constant.MAX_FEED_COUNT_DEFAULT;
		
		Setting setting = settingService.read(user.getUserId());
		
		if(null != setting ){
			maxFavoriteCount = setting.getMaxFavoriteCount();
		}

		if (log.isDebugEnabled()) {
			log.debug("savedCount:" + savedCount);
			log.debug("maxFavoriteCount:" + maxFavoriteCount);
		}

		if(savedCount >= maxFavoriteCount)
		{
			result = messageSource.getMessage("ui.socialpack.microblogging.favorite.message.tomuch", null, new Locale(user.getLocaleCode()));
		}
		else
		{
			Favorite favorite = new Favorite();
			favorite.setUserId(user.getUserId());
			favorite.setMblogId(mblogId);
	
			Favorite orgFavorite = favoriteService.read(favorite);
	
			if (log.isDebugEnabled()) {
				log.debug("orgFavorite:" + orgFavorite);
			}
	
			// 기존에 없을 때만 등록한다.
			if (null == orgFavorite) {
				favoriteService.create(favorite);
			}

			result = messageSource.getMessage("ui.socialpack.microblogging.message.success", null, new Locale(user.getLocaleCode()));
		}

		return result;
	}

	/**
	 * 해당 마이크로블로그를 관심글에서 삭제한다.
	 * 
	 * @param mblogId Microblog의 id
	 * @return  결과
	 */
	@RequestMapping("/removeFavorite.do")
	public @ResponseBody
	String removeFavorite(String mblogId) {

		if (log.isDebugEnabled()) {
			log.debug("mblogId:" + mblogId);
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():" + user.getUserId());
		}

		Favorite favorite = new Favorite();
		favorite.setUserId(user.getUserId());
		favorite.setMblogId(mblogId);

		favoriteService.delete(favorite);

		return "ok";
	}

	/**
	 * 투표팝업창을 리턴한다.
	 * 
	 * @return  ModelAndView
	 */
	@RequestMapping("/pollForm.do")
	public ModelAndView pollForm() {

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/pollForm");

		Poll poll = new Poll();
		poll.setStartDate(DateUtil.toDate(DateUtil.getToday("yyyyMMdd")));
		poll.setEndDate(DateUtil.toDate(DateUtil.getNextDate(DateUtil.getToday("yyyyMMdd"), Constant.DAYS_IN_YEAR, "yyyyMMdd")));
		mav.addObject("poll", poll);

		return mav;
	}

	/**
	 * 투표건을 저장한다.
	 * 
	 * @param poll question 등이 담긴 Poll 객체
	 * @return  ModelAndView
	 */
	@RequestMapping("/pollSave.do")
	public ModelAndView pollSave(Poll poll) {

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/pollSave");

		User user = (User) getRequestAttribute("ikep.user");

		poll.setStatus("1");
		poll.setAnswerCount(poll.getAnswerTitles().length);
		poll.setPermissionType("0");
		poll.setRegisterId(user.getUserId());
		poll.setRegisterName(user.getUserName());
		poll.setUpdaterId(user.getUserId());
		poll.setUpdaterName(user.getUserName());
		poll.setPortalId(user.getPortalId());
		
		poll.setRequestChannel("0");// 안쓰는 필드가 되었고  기본값을 넣기로 했음.

		String pollId = pollService.create(poll);

		mav.addObject("pollId", pollId);

		return mav;
	}

	/**
	 * 외부연동 관련 계정정보를 리턴한다.
	 * 
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "/getSnsAuthInfo.do")
	public ModelAndView getSnsAuthInfo() {
		return new ModelAndView("/socialpack/microblogging/snsAuthInfo");
	}

	/*
	 * *************************************************************************************
	 * private 함수 -Start
	 * ********************************************************
	 * *****************************
	 */

	/**
	 * 리트윗 일자별 현황을 JSONArray String 변환작업
	 * 
	 * @param list 리트윗 일자별 현황이 담긴 리스트
	 * @return 리트윗 일자별 현황 
	 */
	private String getJSONArrayByDataList(List<QueryReturn> list) {

		String retweetStatistics =  null;
		
		if(null != list && 0 < list.size()){

			JSONArray jsonArray = new JSONArray();

			for (QueryReturn queryReturn : list) {
				List map = new ArrayList();
				map.add(queryReturn.getDays());
				map.add(queryReturn.getCnt());
				jsonArray.add(map);
			}

			retweetStatistics = jsonArray.toString();
		}
		
		return retweetStatistics;
	}

	/**
	 * MblogSearchCondition의 searchString 에서 mbtagType와 searchWord 를 분리해낸다.
	 * 
	 * @param mblogSearchCondition searchString이 있는  MblogSearchCondition 객체
	 */
	private void setSearchCondition(MblogSearchCondition mblogSearchCondition, String searchTabType) {
		if (log.isDebugEnabled()) {
			log.debug("================= setSearchCondition ========================");
			log.debug("setSearchCondition mblogSearchCondition: " + mblogSearchCondition);
		}

		String tagString = "";
		String searchWord = "";
		if (null != mblogSearchCondition.getSearchString()
				&& 1 < mblogSearchCondition.getSearchString().trim().length()) {
			tagString = mblogSearchCondition.getSearchString().trim().substring(0, 1);
			searchWord = mblogSearchCondition.getSearchString().trim().substring(1);
		}

		if (log.isDebugEnabled()) {
			log.debug("setSearchCondition tagString:" + tagString);
			log.debug("setSearchCondition searchWord:" + searchWord);
		}

		String mbtagType = "";
		if (Constant.TAG_MENTION.equals(tagString)) {
			mbtagType = Constant.TAG_TYPE_MENTION;
		} else if (Constant.TAG_HASH.equals(tagString)) {
			mbtagType = Constant.TAG_TYPE_HASH;
		} else {// tag가 안붙은 경우에는 해쉬태그와 같은 형태로 보고 검색을 한다.
			if("P".equals(searchTabType)){
				mbtagType = Constant.TAG_TYPE_MENTION;
			}else{
				mbtagType = Constant.TAG_TYPE_HASH;
			}
			searchWord = mblogSearchCondition.getSearchString();
		}

		mblogSearchCondition.setMbtagType(mbtagType);
		mblogSearchCondition.setSearchWord(searchWord);
	}
	
	/**
	 * 로그인 사용자가 그룹 멤버가 아니면 그룹에 속해있어야 볼 수 있다는 안내 페이지로 리턴한다.
	 * 
	 * @param mbgroupId 마이크로블로그 그룹의 Id
	 * @return  ModelAndView
	 */
	private ModelAndView checkGroupMember(String mbgroupId){

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		// 그룹 회원 여부
		MbgroupMember mbgroupMember = new MbgroupMember();
		mbgroupMember.setMbgroupId(mbgroupId);
		mbgroupMember.setMemberId(loginUser.getUserId());

		if(!mbgroupMemberService.exists(mbgroupMember)){

			ModelAndView mav = new ModelAndView("/socialpack/microblogging/messagePage");
			
				try {
					mav.addObject("reStr", messageSource.getMessage("ui.socialpack.microblogging.message.onlyMember", null,
							new Locale(loginUser.getLocaleCode())));
				
				} catch (Exception e) {
			}
			return mav;
		}
		return null;
	}

	/*
	 * *************************************************************************************
	 * private 함수 -End
	 * **********************************************************
	 * ***************************
	 */
}
