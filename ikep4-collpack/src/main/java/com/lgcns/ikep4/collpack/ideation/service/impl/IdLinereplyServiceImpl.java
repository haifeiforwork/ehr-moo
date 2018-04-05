/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.dao.IdIdeaDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyRecommendDao;
import com.lgcns.ikep4.collpack.ideation.dao.IdUserActivitiesDao;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;
import com.lgcns.ikep4.collpack.ideation.service.IdLinereplyService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: IdLinereplyServiceImpl.java 13106 2011-05-25 07:50:40Z loverfairy $
 */
@Service("idLinereplyService")
public class IdLinereplyServiceImpl extends GenericServiceImpl<IdLinereply, String> implements IdLinereplyService {


	@Autowired
	private IdLinereplyDao idLinereplyDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private IdUserActivitiesDao idUserActivitiesDao;
	
	
	@Autowired
	private IdIdeaDao idIdeaDao;
	
	@Autowired
	private IdLinereplyRecommendDao idLinereplyRecommendDao;
	
	
	@Autowired
	private ActivityStreamService activityStreamService;
	
	
	public String create(IdLinereply idLinereply) {
		
		String id = idgenService.getNextId();
		
		idLinereply.setLinereplyId(id);
		
		if(StringUtil.isEmpty(idLinereply.getLinereplyGroupId())){
			idLinereply.setLinereplyGroupId(id);
		}
		
		idLinereply.setContents(StringUtil.replaceHtmlString(idLinereply.getContents()));
		
		//순서 증가시키기 -- 등록보다 순서 증가 먼저
		idLinereplyDao.updateStep(idLinereply.getLinereplyGroupId(), idLinereply.getStep());
			
		idLinereplyDao.create(idLinereply);
		
		//사용자 이력 등록
		IdUserActivities idUserActivities = new IdUserActivities();
		idUserActivities.setUserId(idLinereply.getRegisterId());
		idUserActivities.setLinereplyCount(IdeationConstants.NUM_INCRASE);
		
		boolean activityExists = idUserActivitiesDao.exists(idLinereply.getRegisterId());
		
		//사용자 이력에 있으면 댓글수 업데이트 없으면 생성
		if(activityExists){
			idUserActivitiesDao.updateLinereplyCount(idLinereply.getRegisterId());
		} else {
			idUserActivitiesDao.create(idUserActivities);
		}
		
		
		//아이디어 토론의견 개수 변경
		idIdeaDao.updateLinereplyCount(idLinereply.getItemId());
		
		//채택 카운터 늘리기
		if(idLinereply.getAdoptLinereply() == Integer.parseInt(IdeationConstants.IS_ADOPT)){
			
			idIdeaDao.updateAdoptCount(idLinereply.getItemId());
			
			//아이디어 채택 시키기
			idIdeaDao.updateAdoptItem(idLinereply.getItemId(), Integer.parseInt(IdeationConstants.IS_ADOPT));
			
			//채택된 사용자 정보  올리기
			IdIdea idea = idIdeaDao.get(idLinereply.getItemId());
			idUserActivitiesDao.updateAdoptiItemCount(idea.getRegisterId());
			
			//채택한 아이디어 개수 수정
			idUserActivitiesDao.updateAdoptCount(idLinereply.getRegisterId());
		}
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_IDEATION, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST, idLinereply.getLinereplyId(), StringUtil.cutString(idLinereply.getContents(), IdeationConstants.CONTENT_SUMMAR_COUNT, "..."));

		
		return id;
	}
	

	public IdLinereply get(String linereplyId) {
		return idLinereplyDao.get(linereplyId);
	}
	
	public int getCountByParentId(String linereplyParentId) {
		return idLinereplyDao.getCountByParentId(linereplyParentId);
	}


	public int getCountList(IdSearch idSearch) {
		return idLinereplyDao.getCountList(idSearch);
	}


	public List<IdLinereply> list(IdSearch idSearch) {
		return idLinereplyDao.list(idSearch);
	}


	public void update(IdLinereply idLinereply) {
		
		//내용 <br>처리
		idLinereply.setContents(StringUtil.replaceHtmlString(idLinereply.getContents()));
		
		
		idLinereplyDao.update(idLinereply);
	}
	
	
	
	/**
	 * 삭제 플래그 설정
	 */
	public void updateDeleteFlagTure(String linereplyId, String itemId) {
		
		//자식 글이 있는지 검사
		int childCount = idLinereplyDao.getCountByParentId(linereplyId);
		
		if(childCount > 0){	//자식글 있으면 삭제플래그 업데이트
			updateLinereplyDelete(linereplyId, IdeationConstants.ITEM_DELETE_OK ,itemId);
		
		} else {		//자식글 없으면 리얼삭제
			remove(linereplyId);
		}
		
	}

	/**
	 * 삭제 플래그 삭제 - 되살리기
	 */
	public void updateDeleteFlagFalse(String linereplyId, String itemId) {
		
		updateLinereplyDelete(linereplyId, IdeationConstants.ITEM_DELETE_NO, itemId);
		
	}
	
	/**
	 * 공통 함수 삭제 플래그 업데이트
	 * @param qnaLinereply
	 */
	private void updateLinereplyDelete(String linereplyId, int linereplyDelete, String itemId) {
		
		idLinereplyDao.updateLinereplyDelete(linereplyId, linereplyDelete);

		//질문에 한줄 댓글 수 변경
		idIdeaDao.updateLinereplyCount(itemId);
		
	}
	


	public void remove(String linereplyId) {
		
		
		//아이디어  등록 이력 재등록
		List<String> userIdList = new ArrayList<String>();
		
		IdLinereply  idLinereply = idLinereplyDao.get(linereplyId);
		
		userIdList.add(idLinereply.getRegisterId());
		
		userIdList.addAll(idLinereplyRecommendDao.listUserIdByLinereplyId(linereplyId));
		
		
		//토론의견추천삭제
		idLinereplyRecommendDao.removeByLinereplyId(linereplyId);
		
		idLinereplyDao.remove(linereplyId);
		
		
		//아이디어 토론의견 개수 변경
		idIdeaDao.updateLinereplyCount(idLinereply.getItemId());
		
		//채택 개수 변경
		idIdeaDao.updateAdoptCount(idLinereply.getItemId());
		
		
		//사용자 활동 점수 계산
		List<String> newUserIdList = new ArrayList<String>();
		for(String userId : userIdList){
			if(!newUserIdList.contains(userId)){
				newUserIdList.add(userId);
			}
		}
		
		for(String userId : newUserIdList){
			
			IdUserActivities idUserActivities = new IdUserActivities();
			idUserActivities.setUserId(userId);
			//토론주제 같은 사용자가 등록한 토론의견 개수
			IdSearch lineSearch = new IdSearch();
			lineSearch.setUserId(userId);
			IdLinereply linereplyCountes = idLinereplyDao.getCountes(lineSearch);
			idUserActivities.setLinereplyCount(linereplyCountes.getCount());
			idUserActivities.setRecommendCount(linereplyCountes.getRecommendCount());
			idUserActivities.setAdoptCount(linereplyCountes.getAdoptLinereply());
			
			idUserActivitiesDao.update(idUserActivities);
			
		}
		
		//아이디어 조회
		IdIdea idIdea = idIdeaDao.get(idLinereply.getItemId());
		
		//채택 된 개수 삭제
		if(idIdea.getAdoptCount() == 0 && idLinereply.getAdoptLinereply() == 1){
			
			idIdeaDao.updateAdoptItem(idLinereply.getItemId(), Integer.parseInt(IdeationConstants.IS_NOT_ADOPT));
		}
		
		//채택된 개수 수정
		idUserActivitiesDao.updateAdoptiItemCount(idIdea.getRegisterId());
		
		//내가 채택한 개수 수정
		idUserActivitiesDao.updateAdoptCount(idLinereply.getItemId());
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_IDEATION, IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, idLinereply.getLinereplyId(), idLinereply.getContents());

		
	}



}
