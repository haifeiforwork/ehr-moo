/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemReferenceDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemReferenceService;

/**
 * 소셜블로그 블로깅 조회 직접 접속 정보 Service Implement Class.
 * (현재는 페이지 직접 접속 링크를 통한 접속일때만 카운터 함)
 *
 * @author 이형운
 * @version $Id: SocialBoardItemReferenceServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service("socialBoardItemReferenceService")
public class SocialBoardItemReferenceServiceImpl extends GenericServiceImpl<SocialBoardItemReference, String> implements
		SocialBoardItemReferenceService {

	/**
	 * 블로깅 조회 직접 접속 정보 컨트롤용 Dao
	 */
	@Autowired
	private SocialBoardItemReferenceDao socialBoardItemReferenceDao;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemReferenceService#get(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference)
	 */
	public void registerReference(SocialBoardItemReference socialBoardItemReference) {
		if( !(socialBoardItemReferenceDao.exists(socialBoardItemReference)) ){
			socialBoardItemReferenceDao.create(socialBoardItemReference);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemReferenceService#get(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference)
	 */
	public SocialBoardItemReference get(SocialBoardItemReference socialBoardItemReference) {
		return socialBoardItemReferenceDao.get(socialBoardItemReference);
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(SocialBoardItemReference socialBoardItemReference) {
		return socialBoardItemReferenceDao.create(socialBoardItemReference);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemReferenceService#exists(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference)
	 */
	public boolean exists(SocialBoardItemReference socialBoardItemReference) {
		return socialBoardItemReferenceDao.exists(socialBoardItemReference);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemReferenceService#physicalDelete(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemReference)
	 */
	public void physicalDelete(SocialBoardItemReference socialBoardItemReference) {
		socialBoardItemReferenceDao.physicalDelete(socialBoardItemReference);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	@Deprecated
	public SocialBoardItemReference read(String id) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	@Deprecated
	public void update(SocialBoardItemReference object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {
	}

}
