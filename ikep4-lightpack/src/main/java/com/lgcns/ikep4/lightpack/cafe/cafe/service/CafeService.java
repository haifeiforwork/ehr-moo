/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.search.CafeSearchCondition;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Cafe Service
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CafeService.java 19561 2012-06-29 04:17:04Z malboru80 $
 */
@Transactional
public interface CafeService extends GenericService<Cafe, String> {

	/**
	 * Cafe 목록
	 * 
	 * @param CafeSearchCondition
	 * @return
	 */
	public SearchResult<Cafe> listBySearchCondition(CafeSearchCondition searchCondition);

	/**
	 * 타입별 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listCafeByType(Map<String, String> map);

	/**
	 * 카테고리별 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listCafeByCategory(Map<String, String> map);

	/**
	 * 나의 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listMyCafe(Map<String, String> map, boolean withBoardItem);
	
	/**
	 * 나의 Cafe 목록(portlet)
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listMyCafePortlet(Map<String, String> map);
	
	public List<Cafe> listMyCafeBoardItemPortlet(List<Cafe> list);

	/**
	 * 폐쇄된 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listCloseCafe(Map<String, String> map);

	/**
	 * 신규 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listNewCafe(Map<String, String> map);

	/**
	 * 나의 운영진 이상의 Cafe 목록 - 게시판 이관 대상 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listCafeManager(Map<String, String> map);

	/**
	 * Cafe 그룹 조직원 정보
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Cafe> listGroupMembers(String groupId);

	/**
	 * 그룹 타입의 Cafe 갯수
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> countCafeTypeOrg(Map<String, String> map);

	/**
	 * Cafe 생성
	 * 
	 * @param portalId
	 * @param user
	 * @param object
	 * @return
	 */
	public String createCafe(String portalId, User user, Cafe object);

	/**
	 * Cafe 방문 기록 등록
	 * 
	 * @param map
	 */
	public void createCafeVisit(Map<String, String> map);

	/**
	 * Cafe 조회
	 * 
	 * @param object
	 * @return
	 */
	public Cafe read(String cafeId);

	/**
	 * 디폴트 Cafe 조회
	 * 
	 * @param map
	 * @return
	 */
	public Cafe getDefaultCafe(Map<String, String> map);

	/**
	 * Cafe OrgTreeNode
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Cafe> getOrgGroup(String groupId);

	/**
	 * Cafe 중복여부 체크
	 * 
	 * @param CafeId
	 */
	public boolean checkCafeName(Map<String, String> map);

	/**
	 * Cafe Team ID 중복여부 체크
	 * 
	 * @param teamId
	 * @return
	 */
	public String checkCafeTeam(String teamId);

	/**
	 * Cafe 존재유무
	 * 
	 * @param object
	 * @return
	 */
	public boolean exists(Cafe object);

	/**
	 * Cafe 정보 수정
	 * 
	 * @param Cafe
	 * @param user
	 */
	public void updateCafeInfo(Cafe cafe, User user);

	/**
	 * Cafe 소개정보 수정
	 * 
	 * @param Cafe
	 * @param user
	 */
	public void updateCafeIntro(Cafe cafe, User user);

	/**
	 * Cafe 상태정보 수정
	 * 
	 * @param CafeIds
	 * @param CafeStatus
	 * @param user
	 */
	public void updateCafeStatus(List<String> cafeIds, String cafeStatus, User user);

	/**
	 * Cafe 상태정보 수정
	 * 
	 * @param portal
	 * @param user
	 * @param CafeIds
	 * @param CafeStatus
	 */
	public void updateCafeStatus(Portal portal, User user, List<String> CafeIds, String CafeStatus);

	/**
	 * Cafe 상태변경( 개설승인,폐쇄신청,폐쇄 )
	 * 
	 * @param Cafe
	 */
	public void updateStatus(Cafe object);

	/**
	 * Cafe 물리적 삭제
	 * 
	 * @param CafeId
	 */
	public void physicalDelete(String CafeId);

	/**
	 * Cafe 물리적 삭제
	 * 
	 * @param CafeIds
	 */
	public void physicalDelete(List<String> cafeIds);

	/**
	 * Cafe 삭제된 목록 - 배치 프로그램
	 * 
	 * @return
	 */
	public List<Cafe> listAllCafeDelete();

	/**
	 * 방문 기록 정보 삭제 - 미사용 ???
	 * 
	 * @param CafeId
	 */
	// public void physicalDeleteCafeVisit(String CafeId);

	/**
	 * 미개설된 팀 Cafe 생성 배치작업
	 * 
	 * @param userId
	 */
	public void syncTeamCafe(String userId);

	/**
	 * 신규 사용자의 팀 Cafe 자동 등록 - 배치 작업
	 * 
	 * @param userId
	 */
	public void syncUserCafe(String userId);

	/**
	 * 해당 Cafe 삭제 - 배치 작업
	 * 
	 * @param CafeId
	 */
	public void deleteCafeBatch(String cafeId);

	/**
	 * Cafe 메인 팀 Hierarchy 조회
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Cafe> listGroupHierarchy(String groupId);

	/**
	 * 카페 활동정보
	 * 
	 * @param cafeId
	 * @return
	 */
	public List<Cafe> getCafeDateCount(String cafeId);

	/**
	 * 카페 갤러리 이미지 조회
	 * 
	 * @param cafeId
	 * @return
	 */
	public SearchResult<Cafe> getCafeImageFile(CafeSearchCondition cafeSearchCondition);

}
