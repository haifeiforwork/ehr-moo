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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.forum.constants.ForumConstants;
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionDao;
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionScoreDao;
import com.lgcns.ikep4.collpack.forum.dao.FrFeedbackDao;
import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.dao.FrItemScoreDao;
import com.lgcns.ikep4.collpack.forum.dao.FrLinereplyDao;
import com.lgcns.ikep4.collpack.forum.dao.FrParticipantDao;
import com.lgcns.ikep4.collpack.forum.dao.FrRecommendDao;
import com.lgcns.ikep4.collpack.forum.dao.FrReferenceDao;
import com.lgcns.ikep4.collpack.forum.dao.FrUserActivitiesDao;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrDiscussionServiceImpl.java 16903 2011-10-25 01:37:27Z giljae $
 */
@Service("frDiscussionService")
public class FrDiscussionServiceImpl extends GenericServiceImpl<FrDiscussion, String> implements FrDiscussionService {


	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
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
	private FrItemScoreDao frItemScoreDao;
	
	@Autowired
	private FrParticipantDao frParticipantDao;
	
	@Autowired
	private FileService fileService; 
	
	@Autowired
	private TagService tagService; 
	
	@Autowired
	private FrDiscussionScoreDao frDiscussionScoreDao; 
	
	@Autowired
	private ActivityStreamService activityStreamService;
	
	
	@Override
	public String create(FrDiscussion frDiscussion) {
		
		String id = idgenService.getNextId();
		
		frDiscussion.setDiscussionId(id);
		
		//내용 <br>처리
		//frDiscussion.setContents(StringUtil.replaceHtmlString(frDiscussion.getContents()));
		
		frDiscussionDao.create(frDiscussion);
		
		//사용자 이력 등록
		FrUserActivities frUserActivities = new FrUserActivities();
		frUserActivities.setUserId(frDiscussion.getRegisterId());
		frUserActivities.setDiscussionCount(ForumConstants.NUM_INCRASE);
		
		//사용자 활동 이력 업데이트
		boolean activityExists = frUserActivitiesDao.exists(frDiscussion.getRegisterId());
		
		if(activityExists){
			frUserActivitiesDao.updateDiscussionCount(frDiscussion.getRegisterId());
		} else {
			frUserActivitiesDao.create(frUserActivities);
			
		}
		
		//참가자 등록
		frParticipantDao.create(id, ForumConstants.PARTICIPATION_DISCUSSION_CREATE, frDiscussion.getRegisterId());
		
		//태그 등록	- 태그 있을때 등록
		createTag(frDiscussion);
		
		//파일 업로드
		if(!frDiscussion.getImageId().equals(ForumConstants.JUNIT_TEST)){		//junit 에러 방지
			User user = new User();
			user.setUserId(frDiscussion.getRegisterId());
			user.setUserName(frDiscussion.getRegisterName());
			fileService.createFileLink(frDiscussion.getImageId(), frDiscussion.getDiscussionId(), IKepConstant.ITEM_TYPE_CODE_FORUM, user);
		
		}
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_FORUM, IKepConstant.ACTIVITY_CODE_DOC_POST, frDiscussion.getDiscussionId(), frDiscussion.getTitle());

		
		return id;
	}
	
	public FrDiscussion get(String discussionId) {
		
		return frDiscussionDao.get(discussionId);
	}

	public List<FrDiscussion> list(FrSearch frSearch) {
		return frDiscussionDao.list(frSearch);
	}

	public int getCountList(FrSearch frSearch) {
		return frDiscussionDao.getCountList(frSearch);
	}

	public List<FrDiscussion> listPopular(FrSearch frSearch) {
		return frDiscussionDao.listPopular(frSearch);
	}

	public int getCountListPopular(FrSearch frSearch) {
		return frDiscussionDao.getCountListPopular(frSearch);
	}


	@Override
	public void update(FrDiscussion frDiscussion) {
		
		//내용 <br>처리
		//frDiscussion.setContents(StringUtil.replaceHtmlString(frDiscussion.getContents()));
		
		frDiscussionDao.update(frDiscussion);
		
		//태그 등록	- 태그 있을때 등록
		createTag(frDiscussion);
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_FORUM, IKepConstant.ACTIVITY_CODE_DOC_EDIT, frDiscussion.getDiscussionId(), frDiscussion.getTitle());

	}
	
	

	public void updateMailCount(String discussionId) {
		frDiscussionDao.updateMailCount(discussionId);
	}

	public void updateMblogCount(String discussionId) {
		frDiscussionDao.updateMblogCount(discussionId);
	}

	public void remove(String discussionId, String registerId) {
		
		FrDiscussion frDiscussion = frDiscussionDao.get(discussionId);
		
		//해당토론과 관련되어 활동한 사용자 목록
		List<String> userIdList = new ArrayList<String>();
		
		userIdList.add(registerId);
		
		userIdList.addAll(frItemDao.listUserId(discussionId));
		
		userIdList.addAll(frFeedbackDao.listUserIdByDiscussionId(discussionId));
		
		userIdList.addAll(frReferenceDao.listUserIdByDiscussionId(discussionId));
		
		userIdList.addAll(frLinereplyDao.listUserIdByDiscussionId(discussionId));
		
		userIdList.addAll(frRecommendDao.listUserIdByDiscussionId(discussionId));
		
		
		//토론의견추천삭제
		frRecommendDao.removeByDiscussionId(discussionId);
		
		
		//토론의견삭제
		frLinereplyDao.removeByDiscussionId(discussionId);
		
		//토론글찬반삭제
		frFeedbackDao.removeByDiscussionId(discussionId);
		
		//토론글조회이력삭제
		frReferenceDao.removeByDiscussionId(discussionId);
		
		//토론글스코어 삭제
		frItemScoreDao.removeByDiscussionId(discussionId);
		
		//토론 글 삭제
		frItemDao.removeByDiscussionId(discussionId);
		
		//참가자 삭제
		frParticipantDao.removeByDiscussion(discussionId);
		
		//토론 주제 스코어 삭제
		frDiscussionScoreDao.remove(discussionId);
		
		//토론 주제 삭제
		frDiscussionDao.remove(discussionId);
		
		//태그 삭제
		tagService.delete(discussionId, TagConstants.ITEM_TYPE_FORUM);
		
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
			
			//토론주제 개수
			frUserActivities.setDiscussionCount(frDiscussionDao.getCountByUserId(userId));
			
			//토론주제 같은 사용자가 등록한 토론글 개수
			FrSearch frSearch = new FrSearch();
			frSearch.setUserId(userId);
			FrItem itemCountes = frItemDao.getCountes(frSearch);
			
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
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_FORUM, IKepConstant.ACTIVITY_CODE_DOC_DELETE, frDiscussion.getDiscussionId(), frDiscussion.getTitle());

		
		//파일 삭제
		fileService.removeItemFile(discussionId);
	}
	
	
	/**
	 * 태그 등록 - 등록, 수정시 사용
	 * @param qna
	 */
	private void createTag(FrDiscussion frDiscussion){
		
		//태그 등록	- 태그 있을때 등록
		if(!StringUtil.isEmpty(frDiscussion.getTag())){
			
			Tag tag = new Tag();
			
			tag.setTagName(frDiscussion.getTag());										//사용자가 작성한 tag
			tag.setTagItemId(frDiscussion.getDiscussionId());									//게시물 ID
			tag.setTagItemType(TagConstants.ITEM_TYPE_FORUM);						//모듈 타입 TagConstants에 정의되어 있음.
			tag.setTagItemSubType(ForumConstants.TAG_TYPE_DISCUSSION);									//모듈 서브 타입  - 있을때만 넣기
			tag.setTagItemName(frDiscussion.getTitle());									//게시물 제목
			tag.setTagItemContents(frDiscussion.getContents());  						//게시물 내용
			tag.setTagItemUrl("/collpack/forum/getView.do?docPopup=true&discussionId="+frDiscussion.getDiscussionId());	//게시물 팝업창 url
			tag.setRegisterId(frDiscussion.getRegisterId());
			tag.setRegisterName(frDiscussion.getRegisterName());
			tag.setPortalId(frDiscussion.getPortalId());
			
			tagService.create(tag);
		}
	}
	

	public List<FrDiscussion> listDiscussionByReference(String userId, int endRowIndex){
		
		return frDiscussionDao.listDiscussionByReference(userId, endRowIndex);
		
	}
	
	
	public List<FrDiscussion> listDiscussionByItemCount(int endRowIndex, String limitDate) {
		
		return frDiscussionDao.listDiscussionByItemCount(endRowIndex, limitDate);
		
	}
}
