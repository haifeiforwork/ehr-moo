package com.lgcns.ikep4.support.activitystream.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.dao.ActivityStreamDao;
import com.lgcns.ikep4.support.activitystream.model.ActivityStream;
import com.lgcns.ikep4.support.activitystream.model.ActivityStreamSearchCondition;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.favorite.util.FavoriteConstant;
import com.lgcns.ikep4.support.favorite.util.FavoriteUtil;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * ActivityStream 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityStreamServiceImpl.java 3386 2011-03-18 07:16:40Z
 *          handul32 $
 */
@Service
public class ActivityStreamServiceImpl extends GenericServiceImpl<ActivityStream, String> implements
		ActivityStreamService {

	/**
	 * Activity Stream Dao
	 */
	@Autowired
	private ActivityStreamDao activityStreamDao;

	/**
	 * 아이디생성 Service
	 */
	@Autowired
	private IdgenService idgenService;

	/**
	 * 등록
	 */
	public String create(ActivityStream activityStream, User user, String actorId) {

		activityStream.setActivityStreamId(idgenService.getNextId());

		activityStream.setActorId(actorId);
		activityStream.setRegisterId(user.getUserId());
		activityStream.setRegisterName(user.getUserName());
		activityStream.setUpdaterId(user.getUserId());
		activityStream.setUpdaterName(user.getUserName());

		return activityStreamDao.create(activityStream);
	}

	/**
	 * 등록
	 */
	public String create(String itemTypeCode, String activityCode, String objectId, String objectTitle) {

		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		return create(itemTypeCode, activityCode, objectId, objectTitle, user);

	}

	/**
	 * 등록
	 */
	public String createForMember(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String actorId) {

		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		return create(itemTypeCode, activityCode, objectId, objectTitle, user, actorId);

	}

	/**
	 * 등록
	 */
	public String createForCollavoration(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String objectType, String placeId, String placeName) {

		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		return createForCollavoration(itemTypeCode, activityCode, objectId, objectTitle, objectType, placeId,
				placeName, user);
	}

	/**
	 * 등록
	 */
	public String createForMessage(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String targetId, int targetCount) {

		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user",
				RequestAttributes.SCOPE_SESSION);

		return createForMessage(itemTypeCode, activityCode, objectId, objectTitle, targetId, targetCount, user);

	}

	/**
	 * 등록
	 */
	public String create(String itemTypeCode, String activityCode, String objectId, String objectTitle, User user) {

		ActivityStream activityStream = new ActivityStream();

		activityStream.setItemTypeCode(itemTypeCode);
		activityStream.setActivityCode(activityCode);
		activityStream.setObjectId(objectId);
		activityStream.setObjectTitle(objectTitle);
		activityStream.setObjectType("");
		activityStream.setObjectUrl("");
		activityStream.setTargetId("");
		activityStream.setTargetCount(0);
		activityStream.setPlaceId("");
		activityStream.setPlaceName("");

		return create(activityStream, user, user.getUserId());
	}

	/**
	 * 등록
	 */
	public String create(String itemTypeCode, String activityCode, String objectId, String objectTitle, User user,
			String actorId) {

		ActivityStream activityStream = new ActivityStream();

		activityStream.setItemTypeCode(itemTypeCode);
		activityStream.setActivityCode(activityCode);
		activityStream.setObjectId(objectId);
		activityStream.setObjectTitle(objectTitle);
		activityStream.setObjectType("");
		activityStream.setObjectUrl("");
		activityStream.setTargetId("");
		activityStream.setTargetCount(0);
		activityStream.setPlaceId("");
		activityStream.setPlaceName("");

		return create(activityStream, user, actorId);
	}

	/**
	 * 등록
	 */
	public String createForMessage(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String targetId, int targetCount, User user) {

		ActivityStream activityStream = new ActivityStream();

		activityStream.setItemTypeCode(itemTypeCode);
		activityStream.setActivityCode(activityCode);
		activityStream.setObjectId(objectId);
		activityStream.setObjectTitle(objectTitle);
		activityStream.setObjectType("");
		activityStream.setObjectUrl("");
		activityStream.setTargetId(targetId);
		activityStream.setTargetCount(targetCount);
		activityStream.setPlaceId("");
		activityStream.setPlaceName("");

		return create(activityStream, user, user.getUserId());
	}

	/**
	 * 등록
	 */
	public String createForCollavoration(String itemTypeCode, String activityCode, String objectId, String objectTitle,
			String objectType, String placeId, String placeName, User user) {

		ActivityStream activityStream = new ActivityStream();

		activityStream.setItemTypeCode(itemTypeCode);
		activityStream.setActivityCode(activityCode);
		activityStream.setObjectId(objectId);
		activityStream.setObjectTitle(objectTitle);
		activityStream.setObjectType(objectType);
		activityStream.setObjectUrl("");
		activityStream.setTargetId("");
		activityStream.setTargetCount(0);
		activityStream.setPlaceId(placeId);
		activityStream.setPlaceName(placeName);

		return create(activityStream, user, user.getUserId());
	}

	/**
	 * 조회
	 */
	public ActivityStream read(String id) {
		return (ActivityStream) activityStreamDao.get(id);
	}

	/**
	 * 리스트 조회
	 */
	public SearchResult<ActivityStream> getAll(ActivityStreamSearchCondition searchCondition, User user) {

		// Integer count =
		// activityStreamDao.countBySearchCondition(searchCondition);
		// searchCondition.terminateSearchCondition(count);

		SearchResult<ActivityStream> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + FavoriteConstant.LIST_PAGE_SIZE);

		List<ActivityStream> activityStreamList = activityStreamDao.listBySearchCondition(searchCondition);

		if (activityStreamList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + FavoriteConstant.LIST_PAGE_SIZE);
			searchCondition.setRecordCount(0);
		} else {

			activityStreamList = makeActivityDescription(activityStreamList, user);

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + FavoriteConstant.LIST_PAGE_SIZE);
			searchCondition.setRecordCount(activityStreamList.size());

			searchResult = new SearchResult<ActivityStream>(activityStreamList, searchCondition);
		}

		return searchResult;
	}

	/**
	 * Activity Stream 설명 문구 생성
	 * 
	 * @param activityStreamList
	 * @param user
	 * @return
	 */
	private List<ActivityStream> makeActivityDescription(List<ActivityStream> activityStreamList, User user) {

		String activityDescription;
		String replaceStr;

		List<ActivityStream> list = new ArrayList<ActivityStream>();

		for (ActivityStream activity : activityStreamList) {

			if (user.getLocaleCode().equals("ko")) {
				activityDescription = activity.getActivityDescription();
			} else {
				activity.setActorName(activity.getActorEnglishName());
				activity.setTargetName(activity.getTargetEnglishName());
				activityDescription = activity.getActivityEnglishDescription();
			}

			String detailUrl = FavoriteUtil.makeTargetUrl(activity.getItemTypeCode(), activity.getObjectType());

			replaceStr = "<span class='link_1'><a href='#a' onclick=\"iKEP.showUserContextMenu(this,'"
					+ activity.getActorId() + "', 'bottom')\">" + activity.getActorName() + "</a></span>";

			activityDescription = StringUtil.replace(activityDescription, "$Actor", replaceStr);

			replaceStr = "<span class='link_1'><a href='#a' onclick=\"iKEP.showUserContextMenu(this,'"
					+ activity.getTargetId() + "', 'bottom')\">" + activity.getTargetName() + "</a></span>";

			activityDescription = StringUtil.replace(activityDescription, "$Target", replaceStr);

			if (activity.getActivityCode().equals(IKepConstant.ACTIVITY_CODE_JOIN)
					|| activity.getActivityCode().equals(IKepConstant.ACTIVITY_CODE_QUIT)
					|| activity.getActivityCode().equals(IKepConstant.ACTIVITY_CODE_INVITE)
					|| activity.getActivityCode().equals(IKepConstant.ACTIVITY_CODE_COLL_JOIN)
					|| activity.getActivityCode().equals(IKepConstant.ACTIVITY_CODE_COLL_QUIT)
					|| activity.getActivityCode().equals(IKepConstant.ACTIVITY_CODE_COLL_INVITE)
					|| activity.getItemTypeCode().equals(IKepConstant.ITEM_TYPE_CODE_SMS)
					|| activity.getItemTypeCode().equals(IKepConstant.ITEM_TYPE_CODE_MESSAGE)
					|| activity.getItemTypeCode().equals(IKepConstant.ITEM_TYPE_CODE_MESSAGING)) {

				replaceStr = StringUtil.replaceQuot(activity.getObjectTitle());
			} else {
				replaceStr = "<span class='link_2'><a href='#a' onclick=\"showDatail2('" + detailUrl
						+ activity.getObjectId() + "','" + StringUtil.replaceQuot(activity.getObjectTitle()) + "')\">"
						+ StringUtil.replaceQuot(activity.getObjectTitle()) + "</a></span>";

			}

			activityDescription = StringUtil.replace(activityDescription, "$Object", replaceStr);

			replaceStr = activity.getPlaceName();

			activityDescription = StringUtil.replace(activityDescription, "$Place", replaceStr);

			activityDescription = StringUtil.replace(activityDescription, "$Count",
					String.valueOf(activity.getTargetCount()));

			activity.setActivityDescription(activityDescription);

			list.add(activity);
		}

		return list;

	}

	/**
	 * 리스트 조회(Workspace 용)
	 */
	public SearchResult<ActivityStream> getAllWorkspace(ActivityStreamSearchCondition searchCondition, User user) {

		// Integer count =
		// activityStreamDao.countBySearchCondition(searchCondition);
		// searchCondition.terminateSearchCondition(count);
		Integer count = (searchCondition.getPageIndex() + 1) * FavoriteConstant.LIST_PAGE_SIZE;
		searchCondition.terminateSearchCondition(count);

		SearchResult<ActivityStream> searchResult = null;

		// searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		// searchCondition.setEndRowIndex(searchCondition.getCurrentCount() +
		// 10);

		List<ActivityStream> activityStreamList = activityStreamDao.listBySearchConditionWorkspace(searchCondition);

		if (activityStreamList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + FavoriteConstant.LIST_PAGE_SIZE);
			searchCondition.setRecordCount(0);
		} else {

			activityStreamList = makeActivityDescription(activityStreamList, user);

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + FavoriteConstant.LIST_PAGE_SIZE);
			searchCondition.setRecordCount(activityStreamList.size());

			searchResult = new SearchResult<ActivityStream>(activityStreamList, searchCondition);
		}

		return searchResult;
	}

}
