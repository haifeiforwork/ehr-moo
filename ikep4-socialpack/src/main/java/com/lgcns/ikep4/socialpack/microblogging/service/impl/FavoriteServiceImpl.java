/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.microblogging.dao.FavoriteDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Favorite;
import com.lgcns.ikep4.socialpack.microblogging.service.FavoriteService;


/**
 * 
 * FavoriteService 구현클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: FavoriteServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service
public class FavoriteServiceImpl extends GenericServiceImpl<Favorite, Favorite> implements FavoriteService {

	@Autowired
	private FavoriteDao favoriteDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang
	 * .Object)
	 */
	public Favorite create(Favorite object) {
		return favoriteDao.create(object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.
	 * Serializable)
	 */
	public void delete(Favorite favorite) {
		favoriteDao.remove(favorite);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	public void update(Favorite object) {
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public Favorite read(Favorite favorite) {
		Favorite orgFavorite = favoriteDao.get(favorite);
		
		return orgFavorite;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.FavoriteService#countByUserId(java.lang.String)
	 */
	public int countByUserId(String id) {
		return favoriteDao.selectCountByUserId(id);
	}
}
