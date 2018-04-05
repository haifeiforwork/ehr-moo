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
import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.dao.FrLinereplyDao;
import com.lgcns.ikep4.collpack.forum.dao.FrParticipantDao;
import com.lgcns.ikep4.collpack.forum.dao.FrPolicyDao;
import com.lgcns.ikep4.collpack.forum.dao.FrRecommendDao;
import com.lgcns.ikep4.collpack.forum.dao.FrUserActivitiesDao;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrPolicy;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.collpack.forum.service.FrLinereplyService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: FrLinereplyServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("frLinereplyService")
public class FrLinereplyServiceImpl extends GenericServiceImpl<FrLinereply, String> implements FrLinereplyService {


	@Autowired
	private FrLinereplyDao frLinereplyDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private FrUserActivitiesDao frUserActivitiesDao;
	
	@Autowired
	private FrParticipantDao frParticipantDao;
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	@Autowired
	private FrItemDao frItemDao;
	
	@Autowired
	private FrRecommendDao frRecommendDao;
	
	@Autowired
	private FrPolicyDao frPolicyDao;	
	
	@Autowired
	private ActivityStreamService activityStreamService;
	
	
	public String create(FrLinereply frLinereply) {
		
		String id = idgenService.getNextId();
		
		frLinereply.setLinereplyId(id);
		
		if(StringUtil.isEmpty(frLinereply.getLinereplyGroupId())){
			frLinereply.setLinereplyGroupId(id);
		}
		
		frLinereply.setContents(StringUtil.replaceHtmlString(frLinereply.getContents()));
		
		//순서 증가시키기 -- 등록보다 순서 증가 먼저
		frLinereplyDao.updateStep(frLinereply.getLinereplyGroupId(), frLinereply.getStep());
			
		frLinereplyDao.create(frLinereply);
		
		//사용자 이력 등록
		FrUserActivities frUserActivities = new FrUserActivities();
		frUserActivities.setUserId(frLinereply.getRegisterId());
		frUserActivities.setLinereplyCount(ForumConstants.NUM_INCRASE);
		
		boolean activityExists = frUserActivitiesDao.exists(frLinereply.getRegisterId());
		
		if(activityExists){
			frUserActivitiesDao.updateLinereplyCount(frLinereply.getRegisterId());
		} else {
			frUserActivitiesDao.create(frUserActivities);
		}
		
		//참가자 등록
		boolean participantExists = frParticipantDao.exists(frLinereply.getDiscussionId(), ForumConstants.PARTICIPATION_LINEREPLAY_CREATE, frLinereply.getRegisterId());
		
		if(!participantExists){
			frParticipantDao.create(frLinereply.getDiscussionId(), ForumConstants.PARTICIPATION_LINEREPLAY_CREATE, frLinereply.getRegisterId());
			
			frDiscussionDao.updateParticipationCount(frLinereply.getDiscussionId());
		} 
		
		//토론주제에 토론의견 개수 변경
		frDiscussionDao.updateLinereplyCount(frLinereply.getDiscussionId());
		
		//토론글 토론의견 개수 변경
		frItemDao.updateLinereplyCount(frLinereply.getItemId());
		
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_FORUM, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST, frLinereply.getLinereplyId(), StringUtil.cutString(frLinereply.getContents(), ForumConstants.CONTENT_SUMMAR_COUNT, "..."));

		
		return id;
	}
	

	public FrLinereply get(String linereplyId) {
		return frLinereplyDao.get(linereplyId);
	}
	
	public int getCountByParentId(String linereplyParentId) {
		return frLinereplyDao.getCountByParentId(linereplyParentId);
	}



	public int getCountList(FrSearch frSearch) {
		return frLinereplyDao.getCountList(frSearch);
	}



	public int getCountListWithItemDiscussion(FrSearch frSearch) {
		return frLinereplyDao.getCountListWithItemDiscussion(frSearch);
	}



	public List<FrLinereply> list(FrSearch frSearch) {
		return frLinereplyDao.list(frSearch);
	}


	public List<FrLinereply> listWithItemDiscussion(FrSearch frSearch) {
		return frLinereplyDao.listWithItemDiscussion(frSearch);
	}
	
	


	public List<FrLinereply> listLastWithItemDiscussion(FrSearch frSearch) {
		return frLinereplyDao.listLastWithItemDiscussion(frSearch);
	}


	public int getCountListLastWithItemDiscussion(FrSearch frSearch) {
		return frLinereplyDao.getCountListLastWithItemDiscussion(frSearch);
	}


	public void update(FrLinereply frLinereply) {
		
		//내용 <br>처리
		frLinereply.setContents(StringUtil.replaceHtmlString(frLinereply.getContents()));
		
		frLinereplyDao.update(frLinereply);
	}
	
	
	
	/**
	 * 삭제 플래그 설정
	 */
	public void updateDeleteFlagTure(String linereplyId, String itemId) {
		
		//자식 글이 있는지 검사
		int childCount = frLinereplyDao.getCountByParentId(linereplyId);
		
		if(childCount > 0){	//자식글 있으면 삭제플래그 업데이트
			updateLinereplyDelete(linereplyId, ForumConstants.ITEM_DELETE_OK ,itemId);
		
		} else {		//자식글 없으면 리얼삭제
			remove(linereplyId);
		}
		
	}

	/**
	 * 삭제 플래그 삭제 - 되살리기
	 */
	public void updateDeleteFlagFalse(String linereplyId, String itemId) {
		
		updateLinereplyDelete(linereplyId, ForumConstants.ITEM_DELETE_NO, itemId);
		
	}
	
	/**
	 * 공통 함수 삭제 플래그 업데이트
	 * @param qnaLinereply
	 */
	private void updateLinereplyDelete(String linereplyId, int linereplyDelete, String itemId) {
		
		frLinereplyDao.updateLinereplyDelete(linereplyId, linereplyDelete);

		//질문에 한줄 댓글 수 변경
		frItemDao.updateLinereplyCount(itemId);
		
	}
	


	public void remove(String linereplyId) {
		
		
		//토론글  등록 이력 재등록
		List<String> userIdList = new ArrayList<String>();
		
		FrLinereply  frLinereply = frLinereplyDao.get(linereplyId);
		userIdList.add(frLinereply.getRegisterId());
		
		userIdList.addAll(frRecommendDao.listUserIdByLinereplyId(linereplyId));
		
		
		
		//토론의견추천삭제
		frRecommendDao.removeByLinereplyId(linereplyId);
		
		frLinereplyDao.remove(linereplyId);
		
		
		
		//토론주제에 토론의견 개수 변경
		frDiscussionDao.updateLinereplyCount(frLinereply.getDiscussionId());
		
		//토론글 토론의견 개수 변경
		frItemDao.updateLinereplyCount(frLinereply.getItemId());
		
		
		//참가자 이력 삭제
		FrSearch frSearch = new FrSearch();
		frSearch.setItemId(frLinereply.getItemId());
		frSearch.setUserId(frLinereply.getRegisterId());
		
		int itemCount = frLinereplyDao.getCountList(frSearch);
		
		if(itemCount == 1){	//내가 등록한 
			frParticipantDao.remove(frLinereply.getDiscussionId(), ForumConstants.PARTICIPATION_LINEREPLAY_CREATE, frLinereply.getRegisterId());
		}
		
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
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_FORUM, IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, frLinereply.getLinereplyId(), frLinereply.getContents());

		
	}


	public void updateBest() {
		//정책
		List<FrPolicy> policyList = frPolicyDao.list(ForumConstants.POLICY_BEST);

		frLinereplyDao.updateBestLinereplyInit();	//베스트 토론의견 초기화
		
		//우수 토론글 선정
		for(FrPolicy frPolicy : policyList){
			
			FrSearch frSearch = new FrSearch();
			frSearch.setPortalId(frPolicy.getPortalId());
			frSearch.setStartRowIndex(0);
			frSearch.setEndRowIndex(100000);
			
			List<FrLinereply> linereplyList = frLinereplyDao.list(frSearch);
			
			
			for(FrLinereply frLinereply : linereplyList){
				
				if(frLinereply.getRecommendCount() >= frPolicy.getRecommendWeight()){
					frLinereplyDao.updateBestLinereply(frLinereply.getLinereplyId());
				}
				
			}
			
		}
		
	}
	
	

}
