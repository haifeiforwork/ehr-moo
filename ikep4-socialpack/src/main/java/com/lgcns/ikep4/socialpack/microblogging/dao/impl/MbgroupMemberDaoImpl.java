/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupMemberDao;
import com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;


/**
 * 
 * MbgroupMemberDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupMemberDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class MbgroupMemberDaoImpl extends GenericDaoSqlmap<MbgroupMember, MbgroupMember> implements MbgroupMemberDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.mbgroupMember.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public MbgroupMember create(MbgroupMember mbgroupMember) {
		return (MbgroupMember) sqlInsert(NAMESPACE + "insert", mbgroupMember);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(MbgroupMember mbgroupMember) {
		sqlDelete(NAMESPACE + "delete", mbgroupMember);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(MbgroupMember mbgroupMember) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectCount", mbgroupMember);
		if (count > 0){
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public MbgroupMember get(MbgroupMember mbgroupMember) {
		return (MbgroupMember) sqlSelectForObject(NAMESPACE + "select", mbgroupMember);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(MbgroupMember mbgroupMember) {
		this.sqlUpdate(NAMESPACE + "update", mbgroupMember); 
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupMemberDao#selectMbgroupMemberList(com.lgcns.ikep4.socialpack.microblogging.model.MbgroupMember)
	 */
	public List<MbgroupMember> selectMbgroupMemberList(MbgroupMember mbgroupMember) {
		return sqlSelectForList(NAMESPACE + "selectMbgroupMemberList", mbgroupMember);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupMemberDao#selectMbgroupMemberUserList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectMbgroupMemberUserList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectMbgroupMemberUserList", mblogSearchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupMemberDao#selectInvitingInfoList(com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition)
	 */
	public List selectInvitingInfoList(MblogSearchCondition mblogSearchCondition) {
		return sqlSelectForListOfObject(NAMESPACE + "selectInvitingInfoList", mblogSearchCondition);
	}

}
