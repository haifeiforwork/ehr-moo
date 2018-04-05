/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.guestbook.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;
import com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition;


/**
 * 방명록 게시글 조회용 Service Interface
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: GuestbookService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface GuestbookService extends GenericService<Guestbook, String> {

	/**
	 * 모든 방명록 게시글  반환
	 * @param guestbookSearch 방명록 정보 조회용 객체
	 * @return 방명록 글 목록 리스트
	 */
	public SearchResult<Guestbook> listGuestbook(GuestbookSearchCondition guestbookSearch);
	
	/**
	 * GuestbookId 별로 그 해당 ID 하위의 모든 방명록 글을 조회 하는 메서드
	 * @param guestbookSearch 방명록 정보 조회용 객체
	 * @return 방명록 글 목록 리스트
	 */
	public SearchResult<Guestbook> listGuestbookByGuestId(GuestbookSearchCondition guestbookSearch);
	
	/**
	 * 방명록 Group Id 별로 그 해당 ID 하위의 모든 방명록 글을 조회 하는 메서드
	 * @param groupId 방명록 Group Id
	 * @param userLocale 조회하는 사용자의 Locale 정보
	 * @return 방명록 글 목록 리스트
	 */
	public List<Guestbook> listGuestbookByGroup(String groupId, String userLocale) ;

	/**
	 * 방명록 Parent Id 별로 그 해당 ID 하위의 모든 방명록 글을 조회 하는 메서드
	 * @param parentId 방명록 Parent Id
	 * @param userLocale 조회하는 사용자의 Locale 정보
	 * @return 방명록 글 목록 리스트
	 */
	public List<Guestbook> listGuestbookByChild(String parentId, String userLocale);

	/**
	 * 들여쓰기  카운트 증가 시킨다.
	 * @param groupId 방명록 그룹 ID
	 * @param step 증가 시킬 기준 Step
	 * @param targetUserId 방명록 대상자 ID
	 */
	public void updateStep(String groupId, int step, String targetUserId);

	/** 
	 * Guestbook ID에 해당하는 모든 게시물 삭제 
	 * @param guestbookId 방명록  Id
	 */
	public void deleteGuestbook(String guestbookId);
	
	/**
	 * 현재 Guestbook ID 기준으로 Guestbook 정보를 가지고 온다.
	 * @param guestbookSearch 방명록 정보 조회용 객체
	 * @return 방명록 글 목록 (하위 포함)
	 */
	public Guestbook selectGuestbook(GuestbookSearchCondition guestbookSearch);

	/**
	 * 방명록에 설정된 Portlet Property 정보를 읽어 온다.
	 * @param map portletConfigId : 방명록 포틀릿 Config ID , propertyName : 방명록 포틀릿 Property 명
	 * @return 방명록  Portlet Property 결과 값
	 */
	public String readPortletPropertyValue(Map<String, Object> map);
}
