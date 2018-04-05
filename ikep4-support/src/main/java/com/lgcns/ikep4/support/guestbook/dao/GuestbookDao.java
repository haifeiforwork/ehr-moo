/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.guestbook.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;
import com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition;


/**
 * 방명록 게시글 조회용 Dao Interface
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: GuestbookDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface GuestbookDao extends GenericDao<Guestbook, String> {

	/**
	 * 모든 방명록 게시글  반환
	 * @param guestbookSearch 방명록 정보 조회용 객체
	 * @return 방명록 글 목록 리스트
	 */
	public List<Guestbook> selectAll(GuestbookSearchCondition guestbookSearch);
	
	/**
	 * 모든 방명록의 게시글 수를 반환한다.
	 * @param guestbookSearch 방명록 정보 조회용 객체
	 * @return 방명록 글 수
	 */
	public Integer selectAllCount(GuestbookSearchCondition guestbookSearch);
	
	/**
	 * More 버튼으로 추가 건씩 추가  방명록 게시글  반환 
	 * @param guestbookSearch 방명록 정보 조회용 객체
	 * @return 방명록 글 목록 리스트
	 */
	public List<Guestbook> selectAllMore(GuestbookSearchCondition guestbookSearch);
	
	/**
	 * 해당 그룹에 속한 방명록 글 리스트 반환
	 * @param groupId 방명록 그룹 ID
	 * @return 방명록 글 목록 리스트
	 */
	public List<Guestbook> selectGuestbookByGroup(GuestbookSearchCondition guestbookSearch);

	/**
	 * 해당 그룹의 같은 parent ID를 가지는 글 목록을 가져 온다.
	 * @param parentId 방명록 부모글 ID
	 * @return 방명록 글 목록 리스트
	 */
	public List<Guestbook> selectGuestbookByChild(GuestbookSearchCondition guestbookSearch);
	
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
	public void removeGuestbook(String guestbookId);
	
	
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
