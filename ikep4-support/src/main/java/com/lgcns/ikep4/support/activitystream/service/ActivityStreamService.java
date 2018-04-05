package com.lgcns.ikep4.support.activitystream.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.model.ActivityStream;
import com.lgcns.ikep4.support.activitystream.model.ActivityStreamSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * ActivityStream 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityStreamService.java 10241 2011-05-09 10:40:32Z
 *          happyi1018 $
 */
@Transactional
public interface ActivityStreamService extends GenericService<ActivityStream, String> {

	/**
	 * ActivityStream 등록
	 * 
	 * @param activityStream
	 * @param user
	 * @return
	 */
	public String create(ActivityStream activityStream, User user, String actorId);

	public String create(String itemTypeCode, String activityCode, String objectId, String objectTitle, User user);

	public String create(String itemTypeCode, String activityCode, String objectId, String objectTitle, User user,
			String actorId);

	/**
	 * 게시물(방명록,댓글,블로그) 등록/수정/삭제, 프로파일수정 등의 ActivityStream 등록
	 * 
	 * @param itemTypeCode
	 * @param activityCode
	 * @param actorId
	 * @param targetId
	 * @param objectId
	 * @param objectTitle
	 * @param objectUrl
	 * @param user
	 * @return
	 */
	public String create(String itemTypeCode, String activityCode, String objectId, String objectTitle);

	/**
	 * 멤버가입/탈퇴 등의 ActivityStream 등록
	 * 
	 * @param itemTypeCode
	 * @param activityCode
	 * @param actorId
	 * @param targetId
	 * @param objectId
	 * @param objectTitle
	 * @param objectUrl
	 * @param user
	 * @return
	 */
	public String createForMember(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String actorId);

	/**
	 * SMS, MAIL, MESSAGE, Follow/UnFollow, Mention, Profile Invite/Visit 등등의
	 * ActivityStream 등록
	 * 
	 * @param itemTypeCode
	 * @param activityCode
	 * @param objectId
	 * @param objectTitle
	 * @param targetId
	 * @param targetCount
	 * @return
	 */
	public String createForMessage(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String targetId, int targetCount);

	/**
	 * Collavoration ActivityStream 등록(ANNOUNCE, WEEKLY, BBS)
	 * 
	 * @param itemTypeCode
	 * @param activityCode
	 * @param objectId
	 * @param objectTitle
	 * @param objectType
	 * @param placeId
	 * @param placeName
	 * @return
	 */
	public String createForCollavoration(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String objectType, String placeId, String placeName);

	/**
	 * 위와 동일하며, 사용자 세션이 없는 배치에서 사용
	 * 
	 * @param itemTypeCode
	 * @param activityCode
	 * @param objectId
	 * @param objectTitle
	 * @param targetId
	 * @param targetCount
	 * @param user
	 * @return
	 */
	public String createForMessage(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String targetId, int targetCount, User user);

	/**
	 * 위와 동일하며, 사용자 세션이 없는 배치에서 사용
	 * 
	 * @param itemTypeCode
	 * @param activityCode
	 * @param objectId
	 * @param objectTitle
	 * @param objectType
	 * @param placeId
	 * @param placeName
	 * @param user
	 * @return
	 */
	public String createForCollavoration(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String objectType, String placeId, String placeName, User user);

	/**
	 * 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<ActivityStream> getAll(ActivityStreamSearchCondition searchCondition, User user);

	/**
	 * 리스트 검색(Workspace)
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<ActivityStream> getAllWorkspace(ActivityStreamSearchCondition searchCondition, User user);

}
