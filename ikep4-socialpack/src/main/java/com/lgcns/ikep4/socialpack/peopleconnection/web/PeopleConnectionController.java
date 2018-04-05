/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.peopleconnection.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.socialpack.microblogging.model.Follow;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.socialpack.microblogging.service.FollowService;
import com.lgcns.ikep4.socialpack.microblogging.service.MbgroupService;
import com.lgcns.ikep4.socialpack.microblogging.service.MblogService;
import com.lgcns.ikep4.socialpack.peopleconnection.model.ExternalExpert;
import com.lgcns.ikep4.socialpack.peopleconnection.service.PeopleConnectionService;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.Relation;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationService;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityService;
import com.lgcns.ikep4.support.activitystream.model.ActivityStreamSearchCondition;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.profile.model.ProfileExpertFellow;
import com.lgcns.ikep4.support.profile.service.ProfileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;


/**
 * 
 * peopleconnection 관리
 *
 * @author 최성우
 * @version $Id: PeopleConnectionController.java 16246 2011-08-18 04:48:28Z giljae $
 */

@Controller
@RequestMapping(value = "/socialpack/peopleconnection")
public class PeopleConnectionController extends BaseController {

	@Autowired
	private MblogService mblogService;

	@Autowired
	private FollowService followService;

	@Autowired
	private MbgroupService mbgroupService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private PortalFavoriteService favoriteService;

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private PeopleConnectionService peopleConnectionService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private SocialityService socialityService;

	public static final int PORTAL_FAVORITE_PAGE_PER_RECORD = 100000;
	public static final int FETCH_SIZE_5 = 5;
	/**
	 * 사용자의 peopleConnectionMain 페이지를 조회한다.
	 *
	 * @param targetUserId 조회할 대상 user의 id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/peopleConnectionMain.do")
	public ModelAndView peopleConnection(@RequestParam(value="targetUserId", required=false) String paramTargetUserId) {

		// 세션 객체 가지고 오기
		User loginUser = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("loginUser.getUserId():" + loginUser.getUserId());
		}

		// 1. 사용자 기본정보 조회
		User profile = null;
		// 넘어온 targetUserId가 없으면 로그인 사용자 홈피이다.
		String targetUserId = paramTargetUserId;
		if(null == targetUserId || "".equals(targetUserId)){

			profile = loginUser;
			
			targetUserId = profile.getUserId();
		}
		else
		{
			MblogSearchCondition userSearchCondition = new MblogSearchCondition();
			userSearchCondition.setMemberId(targetUserId);
			userSearchCondition.setLoginId(loginUser.getUserId());
			
			profile = mblogService.userInfoByUserId(userSearchCondition);
		}

		// 2. fellow list 조회
		List<ProfileExpertFellow> fellowExpertList = profileService.listProfileFellowExpert(targetUserId);
		
		// 3. following list 조회
		Follow follow = new Follow();
		follow.setUserId(targetUserId);
		List<Follow> followingList = followService.getFollowing(follow);
		
		// 4. follower list 조회
		follow = new Follow();
		follow.setFollowingUserId(targetUserId);
		List<Follow> followerList = followService.getFollower(follow);

		// 5. 속한 마이크로블로그 그룹
		List<Mbgroup> mbGroupList = mbgroupService.myGroupList(targetUserId);

		// 6. favorite list 조회
		PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();
		
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(loginUser.getLocaleCode());
		searchCondition.setRegisterId(loginUser.getUserId());
		
		searchCondition.setPagePerRecord(PORTAL_FAVORITE_PAGE_PER_RECORD);
		
		SearchResult<PortalFavorite> portalFavorite = favoriteService.getAllForPeople(searchCondition);

		// 7. ActivityStream 관련 기본 정보 조회. (속한 조직내 그룹, 조회조건)
		ActivityStreamSearchCondition asSearchCondition = new ActivityStreamSearchCondition();
		asSearchCondition.setUserLocaleCode	(loginUser.getLocaleCode());
		asSearchCondition.setRegisterId		(loginUser.getUserId());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("localeCode"	, loginUser.getLocaleCode());
		map.put("userId"		, loginUser.getUserId());
		List<Group> groupList = groupService.selectUserGroup(map);
		
		// 8. 추천 동료 list
		Tag searchFellowTag = new Tag();
		searchFellowTag.setTagItemId	(loginUser.getUserId());					//userId
		searchFellowTag.setTagItemType	(TagConstants.ITEM_TYPE_PROFILE_PRO);		//처음 userId에 등록된 태그를 가져올 모듈타입 
		searchFellowTag.setEndRowIndex	(FETCH_SIZE_5);                          				//가져올 개수
		searchFellowTag.setAsTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO); 		//조회할 모듈 ID
			
		// userId List
		List<String> fellowIdList = tagService.listItemIdAsRandom(searchFellowTag);
		
		// user객체 List
		List<User> recommendFellowList = new ArrayList<User>();
		
		// 추천동료의 사용자정보 조회.
		Iterator<String> fellowIt = fellowIdList.iterator();
		while(fellowIt.hasNext()){
			String userId = (String) fellowIt.next();
			User user = userService.read(userId);
			recommendFellowList.add(user);
		}

		// 9. 외부전문가 추천
		Tag searchExternalExpertTag = new Tag();
		searchExternalExpertTag.setTagItemId	(loginUser.getUserId());				//userId
		searchExternalExpertTag.setTagItemType	(TagConstants.ITEM_TYPE_PROFILE_PRO);	//처음 userId에 등록된 태그를 가져올 모듈타입 
		searchExternalExpertTag.setEndRowIndex	(FETCH_SIZE_5);                                    //가져올 개수
		searchExternalExpertTag.setAsTagItemType(TagConstants.ITEM_TYPE_WHO); 			//조회할 모듈 ID
			
		// who 게시판 아이디 List
		List<String> externalExpertProfileIdList = tagService.listItemIdAsRandom(searchExternalExpertTag);
		
		// ExternalExpert객체 List
		List<ExternalExpert> recommendExternalExpertList = peopleConnectionService.listByProfileIdList(externalExpertProfileIdList);


		// 10. social graph 용 조회
		Map<String, String> netMap = new HashMap<String, String>();
		netMap.put("userID", targetUserId);
		netMap.put("type", "D");
		List<Relation> directList = relationService.listGroup(netMap);
		
		netMap.put("type", "C");
		List<Relation> communicationList = relationService.listGroup(netMap);

		netMap.put("type", "F");
		List<Relation> fellowshipList = relationService.listGroup(netMap);

		netMap.put("type", "E");
		List<Relation> expertiseList = relationService.listGroup(netMap);
		
		// 11. 나의 랭킹
		int myRanking = socialityService.getMyRanking(targetUserId);

		ModelAndView mav = new ModelAndView("/socialpack/peopleconnection/peopleConnectionMain");
		mav.addObject("targetUserId"				, targetUserId);
		mav.addObject("profile"						, profile);
		mav.addObject("fellowExpertList"			, fellowExpertList);
		mav.addObject("fellowExpertListSize"		, fellowExpertList.size());
		mav.addObject("followingList"				, followingList);
		mav.addObject("followingCount"				, followingList.size());
		mav.addObject("followerList"				, followerList);
		mav.addObject("followerCount"				, followerList.size());
		mav.addObject("mbGroupList"					, mbGroupList);
		mav.addObject("mbGroupListSize"				, mbGroupList.size());
		mav.addObject("portalFavorite"				, portalFavorite.getEntity());
		mav.addObject("portalFavoriteSize"			, portalFavorite.getRecordCount());

		mav.addObject("groupList"					, groupList);
		mav.addObject("searchCondition"				, asSearchCondition);
		
		mav.addObject("recommendFellowList"			, recommendFellowList);
		mav.addObject("recommendExternalExpertList"	, recommendExternalExpertList);
		
		mav.addObject("directList"					, directList);
		mav.addObject("communicationList"			, communicationList);
		mav.addObject("fellowshipList"				, fellowshipList);
		mav.addObject("expertiseList"				, expertiseList);
		mav.addObject("myRanking"					, myRanking);
		
		return mav;
	}


}
