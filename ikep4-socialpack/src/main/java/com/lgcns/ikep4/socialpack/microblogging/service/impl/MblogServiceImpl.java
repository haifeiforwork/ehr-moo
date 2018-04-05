/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.microblogging.base.Constant;
import com.lgcns.ikep4.socialpack.microblogging.dao.AddonDao;
import com.lgcns.ikep4.socialpack.microblogging.dao.FavoriteDao;
import com.lgcns.ikep4.socialpack.microblogging.dao.Mblog2addonDao;
import com.lgcns.ikep4.socialpack.microblogging.dao.Mblog2tagDao;
import com.lgcns.ikep4.socialpack.microblogging.dao.MblogDao;
import com.lgcns.ikep4.socialpack.microblogging.dao.MbtagDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Addon;
import com.lgcns.ikep4.socialpack.microblogging.model.Mblog;
import com.lgcns.ikep4.socialpack.microblogging.model.Mblog2addon;
import com.lgcns.ikep4.socialpack.microblogging.model.Mblog2tag;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbtag;
import com.lgcns.ikep4.socialpack.microblogging.model.QueryReturn;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.socialpack.microblogging.service.MblogService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 
 * MblogService 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MblogServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service
public class MblogServiceImpl extends GenericServiceImpl<Mblog, String> implements MblogService {

	@Autowired
	private MblogDao mblogDao;

	@Autowired
	private AddonDao addonDao;

	@Autowired
	private MbtagDao mbtagDao;

	@Autowired
	private Mblog2tagDao mblog2tagDao;
	
	@Autowired
	private Mblog2addonDao mblog2addonDao;
	
	@Autowired
	private FavoriteDao favoriteDao;

	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private ActivityStreamService activityStreamService;
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#create(com.lgcns.ikep4.socialpack.microblogging.model.Mblog, com.lgcns.ikep4.support.user.member.model.User)
	 */
	public String create(Mblog mblog, User user) {

		/*
		 * 1. 답글들의 최초 원글 id 표시
		 */
		if (null == mblog.getThreadId() || "".equals(mblog.getThreadId().trim())) {
			mblog.setThreadId(mblog.getMblogId()); 
		}

		// 화면에 보여줄 때는 Contents 대신, 링크를 넣은 ContentsDisplay 필드를 보여주게 된다.
		// 아래 2,3 단계는 Contents에서 링크가 필요한 것을 찾아서 변경 저장하는 부분인다. 
		mblog.setContentsDisplay(mblog.getContents());
		/*
		 * 2. IKEP4_MB_MBLOG 테이블에 저장하기 전, contents에서 addon된 내용 링크 붙여서 contentsDisplay에 넣기
		 */
		makeContentsDisplayForAddon	(mblog);

		/*
		 * 3. IKEP4_MB_MBLOG 테이블에 저장하기 전, contents에서 된 내용 링크 붙여서 contentsDisplay에 넣기
		 */
		makeContentsDisplayForTag	(mblog);

		/*
		 * 4. IKEP4_MB_MBLOG 테이블에 저장
		 */
		
		if("".equals(mblog.getMbgroupId()))
		{
			mblog.setMbgroupId(null);
		}
		mblogDao.create				(mblog);

		// Activity Stream 등록 (MbgroupId가 없는 경우에만.)
		if(null == mblog.getMbgroupId() || "".equals(mblog.getMbgroupId()))
		{
			activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_MICROBLOG, IKepConstant.ACTIVITY_CODE_MICRO_POST, mblog.getMblogId(), mblog.getContents());
		}

		/*
		 * 5. IKEP4_MB_MBLOG 테이블에 저장 후, ADDON 정보와 트위터의 연결관계를 저장
		 */
		saveAddonRelation			(mblog, user);
		
		/*
		 * 6. IKEP4_MB_MBLOG 테이블에 저장 후, 해시태그/맨션 저장 후 트윗과의 연결관계 저장
		 */
		saveTagAndRelation			(mblog, user);

		return "ok";
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		return mblogDao.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public Mblog read(String id) {
		Mblog mblog = mblogDao.get(id);
		
		return mblog;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	public void delete(String id) {
		if (log.isDebugEnabled()) {
			log.debug("=========mblog delete=========id:"+id);
		}

		// 1.마이크로블로깅과 태그의 종속관계 테이블 데이터를 지운다.
		Mblog2tag mblog2tag = new Mblog2tag();
		mblog2tag.setMblogId(id);

		if (log.isDebugEnabled()) {
			log.debug("mblog2tag:"+mblog2tag);
		}
		
		mblog2tagDao.remove(mblog2tag);


		// 2.마이크로블로깅과 ADDON의 종속관계 테이블 데이터를 지운다.
		Mblog2addon mblog2addon = new Mblog2addon();
		mblog2addon.setMblogId(id);

		if (log.isDebugEnabled()) {
			log.debug("mblog2addon:"+mblog2addon);
		}
		
		mblog2addonDao.remove(mblog2addon);
		

		// 3.Favorite 설정 정보를 삭제한다.
		favoriteDao.deleteByMblogId(id);


		// Activity Stream 등록을 위한 조회.
		Mblog mblog = mblogDao.get(id);
		
		
		// 4.마이크로블로깅 컨텐츠(글) 정보를 삭제한다.
		mblogDao.remove(id);
		

		// Activity Stream 등록 (MbgroupId가 없는 경우에만.)
		if(null != mblog && (null == mblog.getMbgroupId() || "".equals(mblog.getMbgroupId())))
		{
			activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_MICROBLOG, IKepConstant.ACTIVITY_CODE_MICRO_DELETE, mblog.getMblogId(), mblog.getContents());
		}
		

		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#list()
	 */
	public List<Mblog> timelineList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectTimelineList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#timelineListUserOnly(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> timelineListUserOnly(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectTimelineListUserOnly(mblogSearchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#threadList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> threadList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectThreadList(mblogSearchCondition);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#timelineAllList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> timelineAllList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectTimelineAllList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#threadAllList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> threadAllList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectThreadAllList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#addonListByAddonType(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> addonListByAddonType(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectAddonListByAddonType(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#retwitByMeList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> retwitByMeList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectRetwitByMeList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#retwitByOtherList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> retwitByOtherList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectRetwitByOtherList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#myMentionList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> myMentionList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectMyMentionList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#favoriteList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> favoriteList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectFavoriteList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#tweetListByTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> tweetListByTag(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectTweetListByTag(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#userListByHashTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<User> userListByHashTag(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectUserListByHashTag(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#userListByMentionTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<User> userListByMentionTag(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectUserListByMentionTag(mblogSearchCondition);
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#tweetListByGroupTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> tweetListByGroupTag(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectTweetListByGroupTag(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#userListByGroupHashTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<User> userListByGroupHashTag(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectUserListByGroupHashTag(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#userListByGroupMentionTag(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<User> userListByGroupMentionTag(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectUserListByGroupMentionTag(mblogSearchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#parentNReplyListByMblogId(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> parentNReplyListByMblogId(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectParentNReplyListByMblogId(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#retweetUserListByMblogId(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<User> retweetUserListByMblogId(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectRetweetUserListByMblogId(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#retweetStatisticsByMblogId(java.lang.String)
	 */
	public List<QueryReturn> retweetStatisticsByMblogId(String id) {
		return mblogDao.selectRetweetStatisticsByMblogId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#mentionedUserListByMblogId(java.lang.String)
	 */
	public List<User> mentionedUserListByMblogId(MblogSearchCondition twitSearchCondition) {
		return mblogDao.selectMentionedUserListByMblogId(twitSearchCondition);
	}


	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#userInfoByUserId(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public User userInfoByUserId(MblogSearchCondition userSearchCondition) {
		return mblogDao.selectUserInfoByUserId(userSearchCondition);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#myTweetCount(java.lang.String)
	 */
	public int myTweetCount(String id) {
		return mblogDao.selectMyTweetCount(id);
	}


	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#groupTimelineList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> groupTimelineList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectGroupTimelineList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#groupThreadList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> groupThreadList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectGroupThreadList(mblogSearchCondition);
	}
		
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#groupAddonListByAddonType(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> groupAddonListByAddonType(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectGroupAddonListByAddonType(mblogSearchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#followingList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<User> followingList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectFollowingList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#followerList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<User> followerList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectFollowerList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#userIdForAutoComplete(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<String> userIdForAutoComplete(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectUserIdForAutoComplete(mblogSearchCondition);
	}


	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#groupTweetCount(java.lang.String)
	 */
	public int groupTweetCount(String id) {
		return mblogDao.selectGroupTweetCount(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#retwitFromOutsideList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> retwitFromOutsideList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectRetwitFromOutsideList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#retwitToOutsideList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> retwitToOutsideList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectRetwitToOutsideList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#recentFileListByAddonType(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> recentFileListByAddonType(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectRecentFileListByAddonType(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MblogService#workspaceTimelineList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<Mblog> workspaceTimelineList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectWorkspaceTimelineList(mblogSearchCondition);
	}

	public List<Mblog> teamTimelineList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectTeamTimelineList(mblogSearchCondition);
	}

	public List<Mblog> followingTimelineList(MblogSearchCondition mblogSearchCondition) {
		return mblogDao.selectFollowingTimelineList(mblogSearchCondition);
	}

	/*
	 * *************************************************************************************
	 * private 함수 -Start
	 * *************************************************************************************
	 */
	
	/*
	 * contents 안에 displaycode가 있으면 링크가 되게 변형해서 리턴한다. 
	 */
	private void makeContentsDisplayForAddon(Mblog mblog)
	{
		String contents = mblog.getContentsDisplay();
		
		if (log.isDebugEnabled()) {
			log.debug("================= makeContentsDisplayForAddon ========================");
			log.debug("contents first: "+contents);
		}
		
		StringBuffer contentsDisplay = new StringBuffer();
		
		while(contents.length() > 0){
			/*
			 * 4가지 유형 중 한가지라도 있는지 확인한다. 가장 먼저 나타나는 것을 찾는다.
			 */
			int beginIndex		= -1;
			int beginIndexUrl 	= contents.indexOf(Constant.ADDON_LEFTBRACE_URL); 	//{url:
			if(0 <= beginIndexUrl && (0 > beginIndex || beginIndex > beginIndexUrl)){
				beginIndex = beginIndexUrl;
			}
			int beginIndexPoll 	= contents.indexOf(Constant.ADDON_LEFTBRACE_POLL); 	//{poll:
			if(0 <= beginIndexPoll && (0 > beginIndex || beginIndex > beginIndexPoll)){
				beginIndex = beginIndexPoll;
			}
			int beginIndexImg 	= contents.indexOf(Constant.ADDON_LEFTBRACE_IMG); 	//{img:
			if(0 <= beginIndexImg && (0 > beginIndex || beginIndex > beginIndexImg)){
				beginIndex = beginIndexImg;
			}
			int beginIndexFile = contents.indexOf(Constant.ADDON_LEFTBRACE_FILE); //{file:
			if(0 <= beginIndexFile && (0 > beginIndex || beginIndex > beginIndexFile)){
				beginIndex = beginIndexFile;
			}
	
			// 시작점이 있다면 그 위치값은 0과 같거나 크다.
			if (0 <= beginIndex)
			{
				int endIndex = contents.indexOf(Constant.ADDON_RIGHTBRACE, beginIndex); //}
				
				// 시작점 끝점 모두 있으면 시작점 앞을 버퍼에 담고, addon을 변형시켜서 또 담는다. contents는 계속 줄어든다.
				if(0 < endIndex) 
				{
					String before = contents.substring(0, beginIndex);
					
					contentsDisplay.append(before);

					//addon은 endIndex까지가 단어이므로 +1 한다.
					String displayCode = contents.substring(beginIndex, endIndex + 1);
						
					// displayCode로 addon 정보를 조회한다.
					Addon addon = addonDao.selectByDisplayCode(displayCode);
									
					contentsDisplay.append(getLinkByAddonCodeDisplayCode(addon, mblog));

					//addon은 endIndex까지가 단어이므로 +1 한다.
					contents = contents.substring(endIndex + 1);
					
					if (log.isDebugEnabled()) {
						log.debug("beginIndex:"+beginIndex);
						log.debug("endIndex:"+endIndex);
						log.debug("before:"+before+":");
						log.debug("displayCode:"+displayCode+":");
						log.debug("contentsDisplay:"+contentsDisplay+":");
						log.debug("contents:"+contents+":");
					}
				}
				else
				{
					contentsDisplay.append(contents);
					contents = "";
				}
			}
			else
			{
				contentsDisplay.append(contents);
				contents = "";
			}
		}
		mblog.setContentsDisplay(contentsDisplay.toString());
	}

	
	/*
	 * Addon 정보를 이용하여 type별로 link를 조합해서 리턴한다.
	 */
	private String getLinkByAddonCodeDisplayCode(Addon addon, Mblog mblog)
	{
		if (log.isDebugEnabled()) {
			log.debug("================= getLinkByAddonCodeDisplayCode ========================");
			log.debug("addon: "+addon);
			log.debug("mblog: "+mblog);
		}
		
		String sourceLink = "";
		if(null != addon)
		{
			// 타입별로 다른 링크를 만든다.
			if(Constant.ADDON_TYPE_URL.equals(addon.getAddonType()))
			{
				sourceLink = " <a name='addonLink' href='" + addon.getSourceLink() + "' target='_blank'>" + addon.getDisplayCode() + "</a> ";
			}
			else if(Constant.ADDON_TYPE_POLL.equals(addon.getAddonType()))
			{
				sourceLink = " <a name='addonLink' onclick='detailPollPop(" + addon.getSourceLink() + ");' >" + addon.getDisplayCode() + "</a> ";
			}
			else if(Constant.ADDON_TYPE_IMG.equals(addon.getAddonType()))
			{
				sourceLink =  " <a name='addonLink' onclick=\"openImage('twitImage_" + mblog.getMblogId() + addon.getSourceLink() + "');\" >" + addon.getDisplayCode() + "</a> "
							+ " <div id='twitImage_" + mblog.getMblogId() + addon.getSourceLink() + "'>" 
							+ " <a name='addonLink' onclick=\"iKEP.viewImageFile('" + addon.getSourceLink() + "');\" >"
							+ " <img src='/ikep4-webapp/support/fileupload/downloadFile.do?fileId=" + addon.getSourceLink() + "'  style='max-width:400px; max-height:auto;'  name='addonLink' > " 
							+ " </a>"
							+ " <a name='addonLink' onclick=\"closeImage('twitImage_" + mblog.getMblogId() + addon.getSourceLink() + "');\" >X</a> "
							+ " </div>";
			}
			else if(Constant.ADDON_TYPE_FILE.equals(addon.getAddonType()))
			{
				sourceLink = " <a name='addonLink' href='/ikep4-webapp/support/fileupload/downloadFile.do?fileId=" + addon.getSourceLink() + "' >" + addon.getDisplayCode() + "</a> ";
			}
			
		}
		if (log.isDebugEnabled()) {
			log.debug("sourceLink:"+sourceLink);
		}
		return sourceLink;
	}

	/*
	 * contents 안에 Tag가 있으면 링크가 되게 변형해서 리턴한다.
	 */
	private void makeContentsDisplayForTag(Mblog mblog)
	{
		String contents = mblog.getContentsDisplay();
		
		if (log.isDebugEnabled()) {
			log.debug("================= makeContentsDisplayForTag ========================");
			log.debug("contents first: "+contents);
		}
		
		StringBuffer contentsDisplay = new StringBuffer();

		while(contents.length() > 0)
		{
			/*
			 * 2가지 유형 중 한가지라도 있는지 확인한다. 가장 먼저 나타나는 것을 찾는다.
			 */
			int beginIndex		= -1;
			int beginIndexMention 	= contents.indexOf(Constant.TAG_MENTION); 	//@
			if(0 <= beginIndexMention && (0 > beginIndex || beginIndex > beginIndexMention)){
				beginIndex = beginIndexMention;
			}
			int beginIndexHash 	= contents.indexOf(Constant.TAG_HASH); 	//#
			if(0 <= beginIndexHash && (0 > beginIndex || beginIndex > beginIndexHash)){
				beginIndex = beginIndexHash;
			}

			if (log.isDebugEnabled()) {
				log.debug("beginIndex:"+beginIndex);
			}
			
			// mention이나 해쉬태그가 있으면, 단어의 끝을 찾는다.
			if (0 <= beginIndex)
			{
				int endIndex = -1; 
				
				for (int x = beginIndex; x < contents.length(); x++) 
				{
					if (Character.isWhitespace(contents.charAt(x))) {

				    	  endIndex = x;
				    	  break;
					}
					endIndex = contents.length();
				}
			
				if (log.isDebugEnabled()) {
					log.debug("endIndex:"+endIndex);
				}
								
				// 시작점 끝점 모두 있으면 시작점 앞을 버퍼에 담고, Tag을 변형시켜서 또 담는다. contents는 계속 줄어든다.
				String before = contents.substring(0, beginIndex);
				
				contentsDisplay.append(before);
				
				String strTag = contents.substring(beginIndex, endIndex);

				if (log.isDebugEnabled()) {
					log.debug("before:"+before+":");
					log.debug("strTag:"+strTag+":");
				}
				
				contentsDisplay.append(getLinkByTag(strTag));

				contents = contents.substring(endIndex);

				if (log.isDebugEnabled()) {
					log.debug("contentsDisplay:"+contentsDisplay+":");
					log.debug("contents:"+contents+":");
				}
			}
			else
			{
				contentsDisplay.append(contents);
				contents = "";
			}
		}
		mblog.setContentsDisplay(contentsDisplay.toString());
	}

	/*
	 * tagType별로 link를 조합해서 리턴한다.
	 */
	private String getLinkByTag(String strTag)
	{
		if (log.isDebugEnabled()) {
			log.debug("================= getLinkByTag ========================");
			log.debug("mbtagType: "+strTag);
		}
		
		String sourceLink = "";

		String mbtagType = strTag.substring(0,1);
		String mbtagName = strTag.substring(1);
		
		if(null != mbtagType)
		{
			// Tag 타입별로 링크가 처리한다.
			if(Constant.TAG_MENTION.equals(mbtagType))
			{
				sourceLink = mbtagType + "<a href='#a' name ='mention' id= '" + mbtagName + "' >" + mbtagName + "</a> ";
			}
			else if(Constant.TAG_HASH.equals(mbtagType))
			{
				sourceLink = " <a href='#a' name ='hashTag' id= '" + strTag + "' >" + strTag + "</a> ";
			}
			
		}
		if (log.isDebugEnabled()) {
			log.debug("sourceLink:"+sourceLink);
		}
		return sourceLink;
	}

	/*
	 * contents 안에 addon정보와 트위터의 연결관계를 저장한다.
	 */
	private void saveAddonRelation(Mblog mblog, User user)
	{
		String contents = mblog.getContents();
		
		if (log.isDebugEnabled()) {
			log.debug("================= saveAddonRelation ========================");
			log.debug("contents first: "+contents);
		}

		while(contents.length() > 0)
		{
			String addonType = "";
			/*
			 * 4가지 유형 중 한가지라도 있는지 확인한다. 가장 먼저 나타나는 것을 찾는다.
			 */

			int beginIndex		= -1;
			int beginIndexUrl 	= contents.indexOf(Constant.ADDON_LEFTBRACE_URL); 	//{url:
			if(0 <= beginIndexUrl && (0 > beginIndex || beginIndex > beginIndexUrl)){
				beginIndex = beginIndexUrl;
				addonType = Constant.ADDON_TYPE_URL;
			}
			int beginIndexPoll 	= contents.indexOf(Constant.ADDON_LEFTBRACE_POLL); 	//{poll:
			if(0 <= beginIndexPoll && (0 > beginIndex || beginIndex > beginIndexPoll)){
				beginIndex = beginIndexPoll;
				addonType = Constant.ADDON_TYPE_POLL;
			}
			int beginIndexImg 	= contents.indexOf(Constant.ADDON_LEFTBRACE_IMG); 	//{img:
			if(0 <= beginIndexImg && (0 > beginIndex || beginIndex > beginIndexImg)){
				beginIndex = beginIndexImg;
				addonType = Constant.ADDON_TYPE_IMG;
			}
			int beginIndexFile = contents.indexOf(Constant.ADDON_LEFTBRACE_FILE); //{file:
			if(0 <= beginIndexFile && (0 > beginIndex || beginIndex > beginIndexFile)){
				beginIndex = beginIndexFile;
				addonType = Constant.ADDON_TYPE_FILE;
			}
				
			// addon된 값이 있으면, 단어의 끝을 찾는다.
			if (0 <= beginIndex)
			{
				int endIndex = contents.indexOf(Constant.ADDON_RIGHTBRACE, beginIndex); //}
				
				// 시작점 끝점 모두 있으면 테이블 작업 후, contents를 끝점까지 잘라낸다. 
				if(0 < endIndex) 
				{
					// displayCode ->  뒷쪽 } 는 빼고 자른다.
					String displayCode = contents.substring(beginIndex, endIndex);
					
					String addonCode = "";
					
					if(Constant.ADDON_TYPE_URL.equals(addonType)){
						addonCode = displayCode.substring(Constant.ADDON_LEFTBRACE_URL.length());
					}
					else if(Constant.ADDON_TYPE_POLL.equals(addonType)){
						addonCode = displayCode.substring(Constant.ADDON_LEFTBRACE_POLL.length());
					}
					else if(Constant.ADDON_TYPE_IMG.equals(addonType)){
						addonCode = displayCode.substring(Constant.ADDON_LEFTBRACE_IMG.length());
					}
					else if(Constant.ADDON_TYPE_FILE.equals(addonType)){
						addonCode = displayCode.substring(Constant.ADDON_LEFTBRACE_FILE.length());
					}
					if (log.isDebugEnabled()) {
						log.debug("addonCode:"+addonCode+":");
					}
					
					/*
					 * IKEP4_MB_MBLOG2ADDON 테이블에 저장한다.
					 */
	
					Mblog2addon mblog2addon = new Mblog2addon();
					mblog2addon.setMblogId(mblog.getMblogId());
					mblog2addon.setAddonCode(addonCode);
					
					mblog2addonDao.create(mblog2addon);
					
					// 업로드파일인 경우에는 IKEP4_DM_FILE_LINK 테이블에 마이크로블로깅과 파일과의 관계를 입력한다.
					if(Constant.ADDON_TYPE_IMG.equals(addonType) || Constant.ADDON_TYPE_FILE.equals(addonType))
					{
						//addon 테이블에서 원래 파일id를 조회한다.
						Addon addon = addonDao.get(addonCode);
						
						List<String> fileIdList = new ArrayList<String>();
						fileIdList.add(addon.getSourceLink());
	
						//IKEP4_DM_FILE_LINK 테이블 에서 입력한다.
						fileService.createFileLink(fileIdList, mblog.getMblogId(), IKepConstant.ITEM_TYPE_CODE_MICROBLOG, user);
					}

					//addon은 endIndex까지가 단어이므로 +1 한다.
					contents = contents.substring(endIndex + 1);
					
					if (log.isDebugEnabled()) {
						log.debug("beginIndex:"+beginIndex);
						log.debug("endIndex:"+endIndex);
						log.debug("displayCode:"+displayCode+":");
						log.debug("contents:"+contents);
					}
				}
				else
				{
					contents = "";
				}
			}
			else
			{
				contents = "";
			}
		}
	}

	/*
	 * contents 안에 해시태그/맨션 저장 후 트위터의 연결관계 저장한다.
	 */
	private void saveTagAndRelation(Mblog mblog, User user)
	{
		String contents =  mblog.getContents();
		if (log.isDebugEnabled()) {
			log.debug("================= saveTagAndRelation ========================");
			log.debug("contents first: "+contents);
		}

		while(contents.length() > 0)
		{
			String mbtagType = "";
			/*
			 * 2가지 유형 중 한가지라도 있는지 확인한다. 가장 먼저 나타나는 것을 찾는다.
			 */
			int beginIndex		= -1;
			int beginIndexMention 	= contents.indexOf(Constant.TAG_MENTION); 	//@
			if(0 <= beginIndexMention && (0 > beginIndex || beginIndex > beginIndexMention)){
				beginIndex = beginIndexMention;
				mbtagType = Constant.TAG_TYPE_MENTION;
			}
			int beginIndexHash 	= contents.indexOf(Constant.TAG_HASH); 	//#
			if(0 <= beginIndexHash && (0 > beginIndex || beginIndex > beginIndexHash)){
				beginIndex = beginIndexHash;
				mbtagType = Constant.TAG_TYPE_HASH;
			}
			
			// mention이나 해쉬태그가 있으면, 단어의 끝을 찾는다.
			if (Constant.TAG_TYPE_MENTION.equals(mbtagType) || Constant.TAG_TYPE_HASH.equals(mbtagType))
			{
				int endIndex = -1; 
				
				for (int x = beginIndex; x < contents.length(); x++) 
				{
					if (Character.isWhitespace(contents.charAt(x))) {

				    	  endIndex = x;
				    	  break;
					}
					endIndex = contents.length();
				}
			
				String strTag = contents.substring(beginIndex, endIndex);
				
				String mbtagName = strTag.substring(1);
				
				/*
				 * 기존에 태그가 등록되어있는지 검색해서  없으면 IKEP4_MB_MBTAG 테이블에 저장한다.
				 */
				Mbtag searchMbtag = new Mbtag();
				searchMbtag.setMbtagFullName(strTag);
				searchMbtag.setPortalId		(user.getPortalId());
				
				String mbtagId = mbtagDao.selectTagIdByTagFullName(searchMbtag);
				
				if(null == mbtagId || "".equals(mbtagId)){
					 // idgen Service에서 id생성하여 넣기
					mbtagId = idgenService.getNextId();
					
					Mbtag mbtag = new Mbtag();
					mbtag.setMbtagId		(mbtagId);
					mbtag.setMbtagType		(mbtagType);
					mbtag.setMbtagName		(mbtagName);
					mbtag.setMbtagFullName	(strTag);
					mbtag.setPortalId		(user.getPortalId());
					
					mbtagDao.create(mbtag);
				}
				
				/*
				 * 같은 내용이 등록되어 있지 않으면, IKEP4_MB_MBLOG2TAG 테이블에 저장한다.
				 */
				Mblog2tag mblog2tag = new Mblog2tag();
				mblog2tag.setMblogId	(mblog.getMblogId());
				mblog2tag.setMbtagId	(mbtagId);
				mblog2tag.setPortalId	(user.getPortalId());

				Mblog2tag orgMblog2tag = mblog2tagDao.get(mblog2tag);

				if(null == orgMblog2tag)
				{
					mblog2tagDao.create(mblog2tag);

					// Activity Stream 등록 (MbgroupId가 없는 경우에만.)
					if(null == mblog.getMbgroupId() || "".equals(mblog.getMbgroupId()))
					{
						activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_MICROBLOG, IKepConstant.ACTIVITY_CODE_MEMTION, mblog.getMblogId(), mblog.getContents(), mbtagName, 1);
					}
				}
				
				contents = contents.substring(endIndex);
				
				if (log.isDebugEnabled()) {
					log.debug("beginIndex:"+beginIndex);
					log.debug("endIndex:"+endIndex+":");
					log.debug("strTag:"+strTag+":");
					log.debug("contents:"+contents);
				}
			}
			else
			{
				contents = "";
			}
		}
	}

	/*
	 * *************************************************************************************
	 * private 함수 -End
	 * *************************************************************************************
	 */
}
