/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 소셜 블로그 댓글 관련 Service Implement Class
 *
 * @author 이형운
 * @version $Id: SocialBoardLinereplyServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service("socialBoardLinereplyService")
public class SocialBoardLinereplyServiceImpl extends GenericServiceImpl<SocialBoardLinereply, String> implements
		SocialBoardLinereplyService {

	/**
	 * 블로그 댓글 컨트롤용 Dao
	 */
	@Autowired
	private SocialBoardLinereplyDao socialBoardLinereplyDao;
	
	/**
	 * 블로그 포스팅 컨트롤용 Dao
	 */
	@Autowired
	private SocialBoardItemDao socialBoardItemDao;
	
	/**
	 * 키값 생성 관리 컨트롤용 Service
	 */
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * Activity Stream 기록을 위한 Service
	 */
	@Autowired
	private ActivityStreamService activityStreamService;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService#listBySearchCondition(com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition)
	 */
	public SearchResult<SocialBoardLinereply> listSocialBoardLinereplyBySearchCondition(
			SocialBoardLinereplySearchCondition socialBoardLinereplySearchCondition) {
		
		Integer count = socialBoardLinereplyDao.countBySearchCondition(socialBoardLinereplySearchCondition);
		socialBoardLinereplySearchCondition.terminateSearchCondition(count);  
		
		SearchResult<SocialBoardLinereply> result = null;
		
		if(socialBoardLinereplySearchCondition.isEmptyRecord()) {
			result = new SearchResult<SocialBoardLinereply>(socialBoardLinereplySearchCondition);
			
		} else { 
			List<SocialBoardLinereply> socialBoardLinereplyList = this.socialBoardLinereplyDao.listBySearchCondition(socialBoardLinereplySearchCondition);  
			result = new SearchResult<SocialBoardLinereply>(socialBoardLinereplyList, socialBoardLinereplySearchCondition); 
		}  
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#readSocialBoardLinereply(java.io.Serializable)
	 */
	public SocialBoardLinereply readSocialBoardLinereply(String linereplyId) {
		return socialBoardLinereplyDao.get(linereplyId);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService#createSocialBoardLinereply(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply)
	 */
	public String createSocialBoardLinereply(SocialBoardLinereply socialBoardLinereply) {
		final String generatedId = this.idgenService.getNextId(); 
		
		socialBoardLinereply.setLinereplyId(generatedId);
		socialBoardLinereply.setLinereplyGroupId(generatedId); 
		
		//게시글의 댓글 갯수를 업데이트한다.
		socialBoardItemDao.updateLinereplyCount(socialBoardLinereply.getItemId());
		
		final String insertedId = socialBoardLinereplyDao.create(socialBoardLinereply);
		
		// Activity Stream 추가 
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST, socialBoardLinereply.getItemId(), socialBoardLinereply.getContents());
		
		return insertedId;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService#createReplySocialBoardLinereply(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply)
	 */
	public String createReplySocialBoardLinereply(SocialBoardLinereply socialBoardLinereply) {
		
		socialBoardLinereplyDao.updateStep(socialBoardLinereply);
		
		final String generatedId = this.idgenService.getNextId(); 
		socialBoardLinereply.setLinereplyId(generatedId);
		 
		//게시글의 댓글 갯수를 업데이트한다.
		socialBoardItemDao.updateLinereplyCount(socialBoardLinereply.getItemId());
		
		final String insertedId = socialBoardLinereplyDao.create(socialBoardLinereply);
		
		// Activity Stream 추가 
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST, socialBoardLinereply.getItemId(), socialBoardLinereply.getContents());
		
		return insertedId;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService#updateSocialBoardLinereply(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply)
	 */
	public void updateSocialBoardLinereply(SocialBoardLinereply socialBoardLinereply) {
		socialBoardLinereplyDao.update(socialBoardLinereply);
		
		// Activity Stream 추가 
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, IKepConstant.ACTIVITY_CODE_LINE_REPLY_EDIT, socialBoardLinereply.getItemId(), socialBoardLinereply.getContents());
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService#adminDeleteSocialBoardLinereply(java.lang.String,java.lang.String)
	 */
	public void adminDeleteSocialBoardLinereply(String itemId, String linereplyId) {
		
		SocialBoardLinereply socialBoardLinereply = socialBoardLinereplyDao.get(linereplyId);
		socialBoardLinereplyDao.physicalDeleteThreadByLinereplyId(linereplyId); 
		
		//게시글의 댓글 갯수를 업데이트한다.
		socialBoardItemDao.updateLinereplyCount(itemId);
		
		// Activity Stream 추가 
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, socialBoardLinereply.getItemId(), socialBoardLinereply.getContents());
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService#userDeleteSocialBoardLinereply(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply)
	 */
	public void userDeleteSocialBoardLinereply(SocialBoardLinereply socialBoardLinereply) {
		
		// 삭제전 Activity Stream 저장용 정보 확인
		SocialBoardLinereply  selSocialBoardLinereply  = socialBoardLinereplyDao.get(socialBoardLinereply.getLinereplyId());
		
		Integer count = socialBoardLinereplyDao.countChildren(socialBoardLinereply.getLinereplyId());
		if(count == 0) {
			socialBoardLinereplyDao.physicalDeleteThreadByLinereplyId(socialBoardLinereply.getLinereplyId()); 
			
		} else {
			socialBoardLinereplyDao.logicalDelete(socialBoardLinereply); 	
			
		} 
		//게시글의 댓글 갯수를 업데이트한다.
		socialBoardItemDao.updateLinereplyCount(socialBoardLinereply.getItemId());
		
		// Activity Stream 추가 
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG, IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, selSocialBoardLinereply.getItemId(), selSocialBoardLinereply.getContents());
	}
	

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService#updateStep(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply)
	 */
	public void updateStep(SocialBoardLinereply socialBoardLinereply) {
		socialBoardLinereplyDao.updateStep(socialBoardLinereply);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardLinereplyService#listTop5Linereply(java.util.Map<String, Object>)
	 */
	public List<SocialBoardLinereply> listTopXLinereply(Map<String, Object> topXList) {
		return socialBoardLinereplyDao.listTopXLinereply(topXList);
	}

}
