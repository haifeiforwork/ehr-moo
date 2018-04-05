/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.microblogging.base.Constant;
import com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupDao;
import com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupMemberDao;
import com.lgcns.ikep4.socialpack.microblogging.model.InvitingInfo;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;
import com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.socialpack.microblogging.service.MbgroupMemberService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * MbgroupMemberService 구현클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupMemberServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service
public class MbgroupMemberServiceImpl extends GenericServiceImpl<MbgroupMember, MbgroupMember> implements MbgroupMemberService {

	@Autowired
	private MbgroupMemberDao mbgroupMemberDao;

	@Autowired
	private MbgroupDao mbgroupDao;
	
	@Autowired
	private ActivityStreamService activityStreamService;
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	public MbgroupMember create(MbgroupMember object) {

		// Activity Stream 등록을 위한 조회
		Mbgroup mbgroup = mbgroupDao.get(object.getMbgroupId());
		
		// Activity Stream 등록
		activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_MICROBLOG, IKepConstant.ACTIVITY_CODE_INVITE, object.getMbgroupId(), mbgroup.getMbgroupName(), object.getMemberId(), 1);
		
		return mbgroupMemberDao.create(object);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	public void delete(MbgroupMember mbgroupMember) {
		// Activity Stream 등록을 위한 조회
		Mbgroup mbgroup = mbgroupDao.get(mbgroupMember.getMbgroupId());
		
		// Activity Stream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_MICROBLOG, IKepConstant.ACTIVITY_CODE_QUIT, mbgroup.getMbgroupId(), mbgroup.getMbgroupName());
		
		mbgroupMemberDao.remove(mbgroupMember);

		// 마지막 회원인 경우에는 그룹을 삭제상태로 바꾼다.
		mbgroupMember.setStatus(Constant.MBGROUP_MEMBER_STATUS_NORMAL);
		if("Y".equals(checkLastGroupMemberYN(mbgroupMember))){

			mbgroupDao.remove(mbgroupMember.getMbgroupId());
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update(java.lang.Object)
	 */
	public void update(MbgroupMember object) {

		// Activity Stream 등록을 위한 조회
		Mbgroup mbgroup = mbgroupDao.get(object.getMbgroupId());
		
		// Activity Stream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_MICROBLOG, IKepConstant.ACTIVITY_CODE_JOIN, mbgroup.getMbgroupId(), mbgroup.getMbgroupName());
		
		mbgroupMemberDao.update(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public MbgroupMember read(MbgroupMember object) {
		MbgroupMember mbgroupMember = mbgroupMemberDao.get(object);
		
		return mbgroupMember;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists(java.io.Serializable)
	 */
	public boolean exists(MbgroupMember mbgroupMember) {
		return mbgroupMemberDao.exists(mbgroupMember);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MbgroupMemberService#mbgroupMemberList(com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember)
	 */
	public List<MbgroupMember> mbgroupMemberList(MbgroupMember mbgroupMember) {
		return mbgroupMemberDao.selectMbgroupMemberList(mbgroupMember);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MbgroupMemberService#mbgroupMemberUserList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<User> mbgroupMemberUserList(MblogSearchCondition mblogSearchCondition) {
		return mbgroupMemberDao.selectMbgroupMemberUserList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MbgroupMemberService#invitingInfoList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List<InvitingInfo> invitingInfoList(MblogSearchCondition mblogSearchCondition) {
		return mbgroupMemberDao.selectInvitingInfoList(mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.MbgroupMemberService#checkLastGroupMemberYN(com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember)
	 */
	public String checkLastGroupMemberYN(MbgroupMember mbgroupMember) {
		String result = "N";
		List<MbgroupMember>  mbgroupMemberList = mbgroupMemberDao.selectMbgroupMemberList(mbgroupMember);
		
		if(1 == mbgroupMemberList.size() ){
			MbgroupMember returnMbgroupMember = mbgroupMemberList.get(0);
			if(mbgroupMember.getMemberId().equals(returnMbgroupMember.getMemberId())){
				result = "Y";
			}
		}
		return result;
	}
}
