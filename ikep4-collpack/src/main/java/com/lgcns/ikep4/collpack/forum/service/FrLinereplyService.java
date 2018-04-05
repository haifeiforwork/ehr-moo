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

import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrLinereplyService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface FrLinereplyService extends GenericService<FrLinereply, String>  {
	
	/**
	 * 의견 상세 조회
	 * @param linereplyId
	 * @return
	 */
	public FrLinereply get(String linereplyId);
	
	/**
	 * 자식글 있는지 조회
	 * @param linereplyParentId
	 * @return
	 */
	public int getCountByParentId(String linereplyParentId);
	
	/**
	 * 토론의견 목록 조회
	 * @param frSearch
	 * @return
	 */
	public List<FrLinereply> list(FrSearch frSearch);
	
	/**
	 * 토론 의견 목록 수
	 * @param frSearch
	 * @return
	 */
	public int getCountList(FrSearch frSearch);
	
	/**
	 * 토론주제, 토론글, 토론의견 목록 조회
	 * @param frSearch
	 * @return
	 */
	public List<FrLinereply> listWithItemDiscussion(FrSearch frSearch);
	
	/**
	 * 토론주제, 토론글, 토론의견 목록 수
	 * @param frSearch
	 * @return
	 */
	public int getCountListWithItemDiscussion(FrSearch frSearch);
	
	/**
	 * 최신 토록주제, 토론글, 토론의견 목록
	 * @param frSearch
	 * @return
	 */
	public List<FrLinereply> listLastWithItemDiscussion(FrSearch frSearch);
	
	/**
	 * 최신 토록주제, 토론글, 토론의견 목록 개수
	 * @param frSearch
	 * @return
	 */
	public int getCountListLastWithItemDiscussion(FrSearch frSearch);
	
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
	
	/**
	 * best Item 선택
	 *
	 */
	public void updateBest();
	
}
