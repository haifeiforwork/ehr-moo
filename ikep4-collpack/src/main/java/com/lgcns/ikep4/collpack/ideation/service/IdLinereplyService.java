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
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyService.java 7008 2011-04-21 02:07:44Z loverfairy $
 */
@Transactional
public interface IdLinereplyService extends GenericService<IdLinereply, String>  {
	
	/**
	 * 의견 상세 조회
	 * @param linereplyId
	 * @return
	 */
	public IdLinereply get(String linereplyId);
	
	/**
	 * 자식글 있는지 조회
	 * @param linereplyParentId
	 * @return
	 */
	public int getCountByParentId(String linereplyParentId);
	
	/**
	 * 토론의견 목록 조회
	 * @param idSearch
	 * @return
	 */
	public List<IdLinereply> list(IdSearch idSearch);
	
	/**
	 * 토론 의견 목록 수
	 * @param idSearch
	 * @return
	 */
	public int getCountList(IdSearch idSearch);
	
	
	/**
	 * 삭제
	 * @param linereplyId
	 */
	public void remove(String linereplyId);
	
	/**
	 * 삭제 플래그 설정
	 */
	public void updateDeleteFlagTure(String linereplyId, String itemId);
	
	/**
	 * 삭제 플래그 삭제 - 되살리기
	 */
	public void updateDeleteFlagFalse(String linereplyId, String itemId);
	
	
}
