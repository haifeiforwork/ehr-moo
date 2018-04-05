/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.forum.constants.ForumConstants;
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionDao;
import com.lgcns.ikep4.collpack.forum.dao.FrFeedbackDao;
import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.dao.FrItemScoreDao;
import com.lgcns.ikep4.collpack.forum.dao.FrLinereplyDao;
import com.lgcns.ikep4.collpack.forum.dao.FrParticipantDao;
import com.lgcns.ikep4.collpack.forum.dao.FrRecommendDao;
import com.lgcns.ikep4.collpack.forum.dao.FrReferenceDao;
import com.lgcns.ikep4.collpack.forum.dao.FrUserActivitiesDao;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrReference;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.collpack.forum.service.FrItemService;
import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrItemServiceImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Service("frItemService")
public class FrItemServiceImpl extends GenericServiceImpl<FrItem, String> implements FrItemService {


	@Autowired
	private FrItemDao frItemDao;
	
	@Autowired
	private FrLinereplyDao frLinereplyDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private FrUserActivitiesDao frUserActivitiesDao;
	
	@Autowired
	private FrFeedbackDao frFeedbackDao;
	
	@Autowired
	private FrReferenceDao frReferenceDao;
	
	@Autowired
	private FrRecommendDao frRecommendDao;
	
	@Autowired
	private FrParticipantDao frParticipantDao;
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	@Autowired
	private FrItemScoreDao frItemScoreDao;
	
	@Autowired
	private TagService tagService; 
	
	@Autowired
	private ActivityStreamService activityStreamService;
	
	@Autowired
	private FileService fileService;
	
	@Override
	public String create(FrItem frItem) {
		
		String id = idgenService.getNextId();
		
		frItem.setItemId(id);
		
		
		frItemDao.create(frItem);
		
		//사용자 이력 등록
		FrUserActivities frUserActivities = new FrUserActivities();
		frUserActivities.setUserId(frItem.getRegisterId());
		frUserActivities.setItemCount(ForumConstants.NUM_INCRASE);
		
		boolean activityExists = frUserActivitiesDao.exists(frItem.getRegisterId());
		
		if(activityExists){
			frUserActivitiesDao.updateItemCount(frItem.getRegisterId());
		} else {
			frUserActivitiesDao.create(frUserActivities);
		}
		
		//참가자 등록
		boolean participantExists = frParticipantDao.exists(frItem.getDiscussionId(), ForumConstants.PARTICIPATION_ITEM_CREATE, frItem.getRegisterId());
		
		if(!participantExists){
			frParticipantDao.create(frItem.getDiscussionId(), ForumConstants.PARTICIPATION_ITEM_CREATE, frItem.getRegisterId());
		
			frDiscussionDao.updateParticipationCount(frItem.getDiscussionId());
		
		} 
		
		//토론주제에 조회수 변경
		frDiscussionDao.updateHitCount(frItem.getDiscussionId());
		
		//토론주제에 토론글 개수 변경
		frDiscussionDao.updateItemCount(frItem.getDiscussionId());
		
		//파일업로드 - edit
		if(frItem.getEditorFileLinkList() != null) {
			
			User user = new User();
			user.setUserId(frItem.getRegisterId());
			user.setUserName(frItem.getRegisterName());
			
			fileService.saveFileLinkForEditor(frItem.getEditorFileLinkList(), frItem.getItemId(), IKepConstant.ITEM_TYPE_CODE_IDEATION, user);
	
		}
		
		//태그 등록
		createTag(frItem);
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_FORUM, IKepConstant.ACTIVITY_CODE_DOC_POST, frItem.getItemId(), frItem.getTitle());

		
		return id;
	}
	
	public FrItem get(String itemId, String registerId) {
		
		
		FrItem frItem = frItemDao.get(itemId);
		
		//조회수 업데이트
		if(frItem != null){
			boolean exists = frReferenceDao.exists(itemId, registerId);
			
			if(!exists){
				FrReference frReference = new FrReference();
				frReference.setItemId(itemId);
				frReference.setRegisterId(registerId);
				
				frReferenceDao.create(frReference);
				
				frItemDao.updateHitCount(itemId);
				frItem.setHitCount(frItem.getHitCount()+1);
				
				frDiscussionDao.updateHitCount(frItem.getDiscussionId());
			} 
		}
		return frItem;
	}
	
	
	public List<FrItem> list(FrSearch frSearch) {
		return frItemDao.list(frSearch);
	}

	public int getCountList(FrSearch frSearch) {
		return frItemDao.getCountList(frSearch);
	}
	
	public List<FrItem> listWithDiscussion(FrSearch frSearch) {
		return frItemDao.listWithDiscussion(frSearch);
	}
	
	public int getCountListWithDiscussion(FrSearch frSearch) {
		return frItemDao.getCountListWithDiscussion(frSearch);
	}


	public List<FrItem> listPopular(FrSearch frSearch) {
		return frItemDao.listPopular(frSearch);
	}

	public int getCountListPopular(FrSearch frSearch) {
		return frItemDao.getCountListPopular(frSearch);
	}
	
	public List<FrItem> listItemRandomCategory(FrSearch frSearch) {
		return frItemDao.listItemRandomCategory(frSearch);
	}

	

	public List<FrItem> listLastWithDiscussion(FrSearch frSearch) {
		return frItemDao.listLastWithDiscussion(frSearch);
	}

	public int getCountListLastWithDiscussion(FrSearch frSearch) {
		return frItemDao.getCountListLastWithDiscussion(frSearch);
	}

	@Override
	public void update(FrItem frItem) {
		
		frItemDao.update(frItem);
		
		//태그 등록
		createTag(frItem);
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_FORUM, IKepConstant.ACTIVITY_CODE_DOC_EDIT, frItem.getItemId(), frItem.getTitle());

	}
	
	

	public void updateFavoriteCount(String itemId, int favoriteCount) {
		frItemDao.updateFavoriteCount(itemId, favoriteCount);
		
		FrItem frItem = frItemDao.get(itemId);
		
		//사용자 이력 등록
		FrUserActivities frUserActivities = new FrUserActivities();
		frUserActivities.setUserId(frItem.getRegisterId());
		
		if(favoriteCount == IdeationConstants.NUM_INCRASE){	//즐겨찾기 추가면
			frUserActivities.setFavoriteCount(ForumConstants.NUM_INCRASE);	
		} else {	//즐겨찾기 삭제면
			frUserActivities.setFavoriteCount(ForumConstants.NUM_DECREASE);
		}
		
		boolean activityExists = frUserActivitiesDao.exists(frItem.getRegisterId());
		
		if(activityExists){
			frUserActivitiesDao.updateFavoriteCount(frItem.getRegisterId());
		} else {
			frUserActivitiesDao.create(frUserActivities);
		}
		
	}

	public void remove(String itemId) {
		
		//토론글  등록 이력 재등록
		List<String> userIdList = new ArrayList<String>();
		
		FrItem frItem = frItemDao.get(itemId);
		userIdList.add(frItem.getRegisterId());
		
		userIdList.addAll(frFeedbackDao.listUserId(itemId));
		
		userIdList.addAll(frReferenceDao.listUserId(itemId));
		
		userIdList.addAll(frLinereplyDao.listUserId(itemId));
		
		userIdList.addAll(frRecommendDao.listUserIdByItemId(itemId));
		
		
		//토론의견추천삭제
		frRecommendDao.removeByItemId(itemId);
		
		//토론의견삭제
		frLinereplyDao.removeByItemId(itemId);
		
		//토론글찬반삭제
		frFeedbackDao.removeByItemId(itemId);
		
		//토론글조회이력삭제
		frReferenceDao.removebyItemId(itemId);
		
		//토론글스코어 삭제
		frItemScoreDao.remove(itemId);
		
		//토론 글 삭제
		frItemDao.remove(itemId);
		
		//토론주제에 토론글 개수 변경
		frDiscussionDao.updateHitCount(frItem.getDiscussionId());
		
		
		//참가자 이력 삭제
		FrSearch frSearch = new FrSearch();
		frSearch.setDiscussionId(frItem.getDiscussionId());
		frSearch.setUserId(frItem.getRegisterId());
		
		int itemCount = frItemDao.getCountList(frSearch);
		
		if(itemCount == 0){
			frParticipantDao.remove(frItem.getDiscussionId(), ForumConstants.PARTICIPATION_ITEM_CREATE, frItem.getRegisterId());
		
			frDiscussionDao.updateParticipationCount(frItem.getDiscussionId());
		}
		
		//태그 삭제
		tagService.delete(itemId, TagConstants.ITEM_TYPE_FORUM);
		
		//사용자 활동 점수 계산
		List<String> newUserIdList = new ArrayList<String>();
		for(String userId : userIdList){
			if(!newUserIdList.contains(userId)){
				newUserIdList.add(userId);
			}
		}
		
		for(String userId : newUserIdList){
			
			FrUserActivities frUserActivities = new FrUserActivities();
			frUserActivities.setUserId(userId);
			
			//토론주제 같은 사용자가 등록한 토론글 개수
			FrSearch activitySearch = new FrSearch();
			activitySearch.setUserId(userId);
			FrItem itemCountes = frItemDao.getCountes(activitySearch);
			
			frUserActivities.setItemCount(itemCountes.getCount());
			frUserActivities.setAgreementCount(itemCountes.getAgreementCount());
			frUserActivities.setOppositionCount(itemCountes.getOppositionCount());
			frUserActivities.setBestItemCount(itemCountes.getBestCount());
			frUserActivities.setFavoriteCount(itemCountes.getFavoriteCount());
			
			//토론주제 같은 사용자가 등록한 토론의견 개수
			FrSearch lineSearch = new FrSearch();
			lineSearch.setUserId(userId);
			FrLinereply linereplyCountes = frLinereplyDao.getCountes(lineSearch);
			frUserActivities.setLinereplyCount(linereplyCountes.getCount());
			frUserActivities.setBestLinereplyCount(linereplyCountes.getBestCount());
			frUserActivities.setRecommendCount(linereplyCountes.getRecommendCount());
			
			frUserActivitiesDao.update(frUserActivities);
			
		}
		
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_FORUM, IKepConstant.ACTIVITY_CODE_DOC_DELETE, frItem.getItemId(), frItem.getTitle());

	}
	
	/**
	 * 태그 등록 - 등록, 수정시 사용
	 * @param qna
	 */
	private void createTag(FrItem frItem){
		
		//태그 등록	- 태그 있을때 등록
		if(!StringUtil.isEmpty(frItem.getTag())){
			
			Tag tag = new Tag();
			
			tag.setTagName(frItem.getTag());										//사용자가 작성한 tag
			tag.setTagItemId(frItem.getItemId());									//게시물 ID
			tag.setTagItemType(TagConstants.ITEM_TYPE_FORUM);						//모듈 타입 TagConstants에 정의되어 있음.
			tag.setTagItemSubType(ForumConstants.TAG_TYPE_ITEM);									//모듈 서브 타입  - 있을때만 넣기
			tag.setTagItemName(frItem.getTitle());									//게시물 제목
			tag.setTagItemContents(frItem.getContents());  						//게시물 내용
			tag.setTagItemUrl("/collpack/forum/getView.do?docPopup=true&discussionId="+frItem.getDiscussionId()+"&itemId="+frItem.getItemId());	//게시물 팝업창 url
			tag.setRegisterId(frItem.getRegisterId());
			tag.setRegisterName(frItem.getRegisterName());
			tag.setPortalId(frItem.getPortalId());
			
			tagService.create(tag);
		}
	}
	


}
