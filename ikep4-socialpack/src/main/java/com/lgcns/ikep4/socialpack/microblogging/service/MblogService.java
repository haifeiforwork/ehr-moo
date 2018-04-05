/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.microblogging.model.Mblog;
import com.lgcns.ikep4.socialpack.microblogging.model.QueryReturn;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * GenericService microblog 서비스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MblogService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface MblogService extends GenericService<Mblog, String> {

	/**
	 * 검색조건을 이용해서 트윗글 목록을 조회한다. (본인 글(+following 사용자의 글) + 본인이 맨션된 글) 
	 *
	 * @param loginId, ownerId, registerOnly, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	timelineList					(MblogSearchCondition mblogSearchCondition);

	/**
	 * Timeline List 반환 (해당 user 것만)
	 * 
	 * @param loginId, ownerId, registerOnly, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	timelineListUserOnly			(MblogSearchCondition mblogSearchCondition);

	/**
	 * Thread List 반환 (같은 thread id에 해당하는 글)
	 * 
	 * @param loginId, ownerId, registerOnly, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	threadList						(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * 공개대상 Timeline List 반환
	 * 
	 * @param loginId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	timelineAllList					(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * 공개대상 Thread List 반환
	 * 
	 * @param loginId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	threadAllList					(MblogSearchCondition mblogSearchCondition);

	/**
	 * Addon (이미지, 파일, 투표, link) 관련 트윗글 List 반환
	 * 
	 * @param loginId, ownerId, addonType, registerOnly, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	addonListByAddonType			(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * 자신이 리트윗한 트윗글 List 반환
	 * 
	 * @param loginId, ownerId, registerOnly, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	retwitByMeList					(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * 타인에 의해 리트윗된 자신의 트윗글 List 반환
	 * 
	 * @param loginId, ownerId, registerOnly, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	retwitByOtherList				(MblogSearchCondition mblogSearchCondition);

	/**
	 * 나를(owner + 트윗터 홈 주인이 속한 부서(그룹) ID) 멘션한 트윗글 List 반환 
	 * 
	 * @param loginId, ownerId, ownerGroupId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	myMentionList					(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * favorite 지정한 트윗글 List 반환
	 * 
	 * @param loginId, ownerId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	favoriteList					(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * Tag 기반 검색 결과: 트윗글 List 반환: 해당 해쉬태그를 가진 글 + 해당 맨션이 있는 글 목록
	 * 
	 * @param loginId, mbtagType, searchWord, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	tweetListByTag					(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * 해쉬 Tag 기반 검색 결과: 해당 해쉬태그를 가진 글 작성자 User List 반환
	 * 
	 * @param loginId, mbtagType, searchWord, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return User List
	 */
	public List<User> 	userListByHashTag				(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * 맨션 Tag 기반 검색 결과: 아이디가 유사한 사용자 List 반환
	 * 
	 * @param loginId, searchWord, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return User List
	 */
	public List<User> 	userListByMentionTag			(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * 선택한 트윗의 부모글, 본글, reply List 반환
	 * 
	 * @param loginId, mblogId 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	parentNReplyListByMblogId		(MblogSearchCondition mblogSearchCondition);

	/**
	 * 선택한 트윗을 retweet한 사용자 List 반환
	 * 
	 * @param loginId, mblogId 를 담은 MblogSearchCondition 객체
	 * @return User List
	 */
	public List<User> 	retweetUserListByMblogId		(MblogSearchCondition mblogSearchCondition);

	/**
	 * 선택한 트윗을 retweet한 트윗의 열흘간 일자별로 누적된 retweet수 List 반환
	 * 
	 * @param mblogId 를 담은 MblogSearchCondition 객체
	 * @return 통계결과 리스트
	 */
	public List<QueryReturn> retweetStatisticsByMblogId	(String id);

	/**
	 * 선택한 트윗에 맨션된 사용자 List 반환
	 * 
	 * @param mblogId 를 담은 MblogSearchCondition 객체
	 * @return User List
	 */
	public List<User> 	mentionedUserListByMblogId		(MblogSearchCondition twitSearchCondition);
	
	/**
	 * 선택한 사용자의 사용자 정보 반환
	 * 
	 * @param loginId, memberId 를 담은 MblogSearchCondition 객체
	 * @return User List
	 */
	public User 		userInfoByUserId				(MblogSearchCondition userSearchCondition);
	
	/**
	 * 선택한 사용자가 작성한 트윗수 반환
	 * 
	 * @param user의 id
	 * @return 선택한 사용자가 작성한 트윗수
	 */
	public int 			myTweetCount					(String id);

	/**
	 * 홈피주인이 Following하고 있는(+로그인한 사용자도 동시에 Following하고 있는) 사용자 List 반환
	 * 
	 * @param loginId, ownerId, bothFollow, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return User List
	 */
	public List<User> 	followingList					(MblogSearchCondition mblogSearchCondition);

	/**
	 * 홈피주인의 Follower ( +동시에 로그인한 사용자의 Follower) 사용자 List 반환
	 * 
	 * @param loginId, ownerId, bothFollow, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return User List
	 */
	public List<User> 	followerList					(MblogSearchCondition mblogSearchCondition);

	/**
	 * 선택한 Group의 트윗수 반환
	 * 
	 * @param mbgroup의 id
	 * @return 선택한 Group의 트윗수
	 */
	public int 			groupTweetCount					(String id);

	/**
	 * 최근 한달간 맨션한 사용자(우선)와 following한 사용자 를 합해서 총 10명 List 반환 (AuthoComplete용)
	 * 
	 * @param loginId, memberId 를 담은 MblogSearchCondition 객체
	 * @return user의 id List
	 */
	public List<String> userIdForAutoComplete			(MblogSearchCondition mblogSearchCondition);

	/**
	 * 최근 등록한 이미지/파일 관련 트윗글 (파일명, ADDON_CODE) List 반환
	 * 
	 * @param loginId, addonType 를 담은 MblogSearchCondition 객체
	 * @return (파일명, ADDON_CODE) List 
	 */
	public List<Mblog> 	recentFileListByAddonType		(MblogSearchCondition mblogSearchCondition);
	
	// 그룹용 서비스
	/**
	 * 그룹의 Timeline List 반환
	 * 
	 * @param loginId, mbgroupId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	groupTimelineList				(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * 그룹의 Thread List 반환
	 * 
	 * @param loginId, mbgroupId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	groupThreadList					(MblogSearchCondition mblogSearchCondition);

	/**
	 * 그룹의 Addon (이미지, 파일, 투표, link) 관련 트윗글 List 반환
	 * 
	 * @param loginId, mbgroupId, addonType, registerOnly, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	groupAddonListByAddonType		(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * 그룹 외부에서 그룹으로 리트윗된 트윗글 List 반환
	 * 
	 * @param loginId, mbgroupId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	retwitFromOutsideList			(MblogSearchCondition mblogSearchCondition);

	/**
	 * 그룹 외부로 리트윗된 트윗글 List 반환
	 * 
	 * @param loginId, mbgroupId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	retwitToOutsideList				(MblogSearchCondition mblogSearchCondition);

	/**
	 * Group의 Tag 기반 검색 결과: 트윗글 List 반환: 해당 해쉬태그를 가진 글 목록, 해당 맨션이 있는 글 목록
	 * 
	 * @param loginId, mbtagType, mbgroupId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	tweetListByGroupTag				(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * Group의 해쉬 Tag 기반 검색 결과: 해당 해쉬태그를 가진 글 작성자 List 반환
	 * 
	 * @param loginId, searchWord, mbgroupId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<User> 	userListByGroupHashTag			(MblogSearchCondition mblogSearchCondition);
	
	/**
	 * Group의 맨션 Tag 기반 검색 결과: 아이디가 유사한 사용자 List 반환
	 * 
	 * @param loginId, searchWord, mbgroupId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return User List
	 */
	public List<User> 	userListByGroupMentionTag		(MblogSearchCondition mblogSearchCondition);
	

	/**
	 * Workspace의 Timeline List 반환
	 * 
	 * @param loginId, workspaceId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	workspaceTimelineList			(MblogSearchCondition mblogSearchCondition);

	/**
	 * team(조직도)에 속한 user들의 Timeline List 반환
	 * 
	 * @param loginId, ownerGroupId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	teamTimelineList				(MblogSearchCondition mblogSearchCondition);


	/**
	 * following하는 user들만의 Timeline List 반환
	 * 
	 * @param loginId, ownerId, standardType, standardMblogId, fetchSize 를 담은 MblogSearchCondition 객체
	 * @return 마이크로블로그 List
	 */
	public List<Mblog> 	followingTimelineList			(MblogSearchCondition mblogSearchCondition);

	/**
	 * Microblog 쓰기
	 * 
	 * @param Mblog
	 * @param 로그인 사용자 User 객체
	 * @return 마이크로블로그 List
	 */
	public String 		create							(Mblog mblog, User user);
}
