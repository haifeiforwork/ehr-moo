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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemRecommendDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemRecommendService;

/**
 * 소셜블로그 블로깅 추천 정보 Service Implement Class
 *
 * @author 이형운
 * @version $Id: SocialBoardItemRecommendServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service("socialBoardItemRecommendService")
public class SocialBoardItemRecommendServiceImpl extends GenericServiceImpl<SocialBoardItemRecommend, String> implements
		SocialBoardItemRecommendService {

	/**
	 * 블로그 블로깅 추천 정보 컨트롤용 Dao
	 */
	@Autowired
	private SocialBoardItemRecommendDao socialBoardItemRecommendDao;
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemRecommendService#registerRecommend(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend)
	 */
	public void registerRecommend(SocialBoardItemRecommend socialBoardItemRecommend) {
		if( !(socialBoardItemRecommendDao.exists(socialBoardItemRecommend)) ){
			socialBoardItemRecommendDao.create(socialBoardItemRecommend);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemRecommendService#exists(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend)
	 */
	public boolean exists(SocialBoardItemRecommend socialBoardItemRecommend) {
		return socialBoardItemRecommendDao.exists(socialBoardItemRecommend);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemRecommendService#physicalDelete(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend)
	 */
	public void physicalDelete(SocialBoardItemRecommend socialBoardItemRecommend) {
		socialBoardItemRecommendDao.physicalDelete(socialBoardItemRecommend);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.socialblog.service.SocialBoardItemRecommendService#get(com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend)
	 */
	public SocialBoardItemRecommend get(SocialBoardItemRecommend socialBoardItemRecommend) {
		return socialBoardItemRecommendDao.get(socialBoardItemRecommend);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(SocialBoardItemRecommend socialBoardItemRecommend) {
		return socialBoardItemRecommendDao.create(socialBoardItemRecommend);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	@Deprecated
	public SocialBoardItemRecommend read(String id) {
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
	public void update(SocialBoardItemRecommend object) {
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	@Deprecated
	public void delete(String id) {
	}

}
