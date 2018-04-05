/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.fileupload.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.fileupload.model.Thumbnail;


/**
 * 섬네일 Dao
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ThumbnailDao.java 16273 2011-08-18 07:07:47Z giljae $
 */
public interface ThumbnailDao extends GenericDao<Thumbnail, String> {

	/**
	 * ItemId에 연결된 썸네일 리스트 조회
	 * 
	 * @param itemId
	 * @return
	 */
	public List<Thumbnail> getItemThumbnail(String itemId);

	/**
	 * 프로파일 이미지 검색
	 * 
	 * @param userId
	 * @return
	 */
	public Thumbnail selectProfileImage(String userId);

}
