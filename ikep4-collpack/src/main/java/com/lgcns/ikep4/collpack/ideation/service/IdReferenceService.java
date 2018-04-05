/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.collpack.ideation.model.IdReference;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdReferenceService.java 7008 2011-04-21 02:07:44Z loverfairy $
 */
@Transactional
public interface IdReferenceService extends GenericService<IdReference, String>  {
	
	/**
	 * 등록
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public String create(String itemId, String registerId);
	
	/**
	 * 토론글 조회 리스트
	 * @param itemId
	 * @return
	 */
	public List<IdReference> list(String itemId);
	
	
	/**
	 * 사용자 토론글 조회 삭제
	 * @param itemId
	 * @param registerId
	 * @return
	 */
	public void remove(String itemId, String registerId);
	
	
	
}
