/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.collpack.ideation.model.IdReference;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdReferenceDao.java 12098 2011-05-19 09:46:17Z loverfairy $
 */
public interface IdReferenceDao extends GenericDao<IdReference, String>  {
	
	/**
	 * 등록
	 * @param itemId
	 * @param registerId
	 */
	public void create(String itemId,  String registerId);
	
	
	/**
	 * 사용자 아이디어 조회 했는지
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public boolean exists(String itemId, String registerId);
	
	/**
	 * 아이디어 조회 리스트
	 * @param itemId
	 * @return
	 */
	public List<IdReference> list(String itemId);
	
	
	/**
	 * 아이디어에 해당하는 사용자ID 가져오기
	 * @param itemId
	 * @return
	 */
	public List<String> listUserId(String itemId);
	
	
	/**
	 * 사용자 아이디어 조회 삭제
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public void remove(String itemId, String registerId);
	
	/**
	 * 아이디어에 해당하는 모든 조회목록삭제
	 * @param itemId
	 */
	public void removebyItemId(String itemId);
	
	
	
}
