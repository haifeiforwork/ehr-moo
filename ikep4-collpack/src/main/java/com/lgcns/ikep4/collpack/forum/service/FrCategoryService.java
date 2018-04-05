/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrCategoryService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface FrCategoryService extends GenericService<FrCategory, String>  {
	
	
	/**
	 * 카테고리 리스트
	 * @param portalId
	 * @return
	 */
	public FrCategory get(String categoryId);
	
	/**
	 * 카테고리 리스트
	 * @param portalId
	 * @return
	 */
	public List<FrCategory> list(String portalId);


	/**
	 * PK에 해당하는 테이블의 row를 삭제
	 * @param categoryId 삭제하고자하는 row의 PK 값
	 */
	public void delete(String categoryId, String portal);
	
	
	/**
	 * 카테고리 order update - 카테고리 id들을 순서대로 받음.
	 * @param categoryIdes
	 */
	public void updateCategoryOrder(String categoryIdes);
	
}
