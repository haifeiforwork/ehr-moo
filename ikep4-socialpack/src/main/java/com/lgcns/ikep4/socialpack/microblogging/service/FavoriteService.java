/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.socialpack.microblogging.model.Favorite;

/**
 * 
 * 관심글 관련 서비스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: FavoriteService.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Transactional
public interface FavoriteService extends GenericService<Favorite, Favorite> {

	/**
	 * userId에 해당하는 favorite 수
	 * 
	 * @param userId
	 * @return  favorite 수
	 */
	public int			countByUserId	(String id);
}
