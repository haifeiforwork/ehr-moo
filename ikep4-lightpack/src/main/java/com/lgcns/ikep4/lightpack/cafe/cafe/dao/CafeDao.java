/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.search.CafeSearchCondition;


/**
 * Cafe DAO
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CafeDao.java 17088 2011-12-13 10:26:06Z giljae $
 */
public interface CafeDao extends GenericDao<Cafe, String> {

	/**
	 * Cafe 목록
	 * 
	 * @param cafeSearchCondition
	 * @return
	 */
	List<Cafe> listBySearchCondition(CafeSearchCondition searchCondition);

	/**
	 * Personal Cafe 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	List<Cafe> listBySearchConditionPersonal(CafeSearchCondition searchCondition);

	/**
	 * 동맹요청 목록
	 * 
	 * @param map
	 * @return
	 */
	List<Cafe> listCafeByType(Map<String, String> map);

	/**
	 * 나의 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listMyCafe(Map<String, String> map);

	/**
	 * 폐쇠된 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listCloseCafe(Map<String, String> map);

	/**
	 * 새로운 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listNewCafe(Map<String, String> map);

	/**
	 * 운영진 이상의 Cafe 목록 - 게시판 이관 대상 Cafe 조회
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listCafeManager(Map<String, String> map);

	/**
	 * 카테고리별 Cafe 목록
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> listCafeByCategory(Map<String, String> map);

	/**
	 * 하위 그룹 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchConditionGroupHierarchy(CafeSearchCondition searchCondition);

	/**
	 * 하위 그룹 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Cafe> listBySearchConditionGroupHierarchy(CafeSearchCondition searchCondition);

	/**
	 * cafe/부서 미개설리스트
	 * 
	 * @param searchCondition
	 * @return
	 */
	List<Cafe> listBySearchConditionGroup(CafeSearchCondition searchCondition);

	/**
	 * cafe/부서 조직원정보
	 * 
	 * @param groupId
	 * @return
	 */
	List<Cafe> listGroupMembers(String groupId);

	/**
	 * cafe/부서 폐쇄대기 리스트
	 * 
	 * @param searchCondition
	 * @return
	 */
	List<Cafe> listBySearchConditionCloseGroup(CafeSearchCondition searchCondition);

	/**
	 * 그룹 Cafe 갯수
	 * 
	 * @param map
	 * @return
	 */
	public List<Cafe> countCafeTypeOrg(Map<String, String> map);

	/**
	 * cafe/부서 미개설리스트 카운트
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionGroup(CafeSearchCondition searchCondition);

	/**
	 * cafe/부서 폐쇄대기 카운트
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionCloseGroup(CafeSearchCondition searchCondition);

	/**
	 * Cafe Count
	 * 
	 * @param cafeSearchCondition
	 * @return
	 */
	Integer countBySearchCondition(CafeSearchCondition searchCondition);

	/**
	 * 사용자가 가입한 WS/ 나의 WS 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchConditionPersonal(CafeSearchCondition searchCondition);

	/**
	 * Cafe 정보 조회
	 * 
	 * @param object
	 * @return
	 */
	public Cafe get(String cafeId);

	/**
	 * 디폴트 Cafe 조회
	 * 
	 * @param map
	 * @return
	 */
	public Cafe getDefaultCafe(Map<String, String> map);

	/**
	 * cafe/부서 조직도 정보
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Cafe> getOrgGroup(String groupId);

	/**
	 * Cafe 명 중복여부 체크
	 * 
	 * @param map
	 * @return
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
	 * 방문 기록 정보 등록
	 * 
	 * @param map
	 */
	public void createCafeVisit(Map<String, String> map);

	/**
	 * 1일간 방문 여부 조회
	 * 
	 * @param map
	 * @return
	 */
	public boolean existsCafeVisit(Map<String, String> map);

	/**
	 * cafe 존재유무
	 * 
	 * @param object
	 * @return
	 */
	public boolean exists(Cafe object);

	/**
	 * Cafe 상태 변경 ( 개설승인,폐쇄신청,폐쇄,개설반려 )
	 * 
	 * @param cafe
	 */
	public void updateStatus(Cafe object);

	/**
	 * 소개 정보 수정
	 * 
	 * @param object
	 */
	public void updateIntro(Cafe object);

	/**
	 * 소개 정보 수정
	 * 
	 * @param object
	 */
	public void updateLayout(Cafe object);

	/**
	 * Cafe 영구 삭제처리
	 * 
	 * @param cafeId
	 */
	public void physicalDelete(String cafeId);

	/**
	 * 삭제된 Cafe 목록
	 * 
	 * @return
	 */
	public List<Cafe> listAllCafeDelete();

	/**
	 * 해당 Cafe 의 방문 기록 삭제
	 * 
	 * @param cafeId
	 */
	// public void physicalDeleteCafe(String cafeId);

	/**
	 * 팀 Cafe 개설 배치
	 * 
	 * @param userId
	 */
	public void syncTeamCafe(String userId);

	/**
	 * 신규 사용자 팀 회원에 등록을 위한 배치
	 * 
	 * @param userId
	 */
	public void syncUserCafe(String userId);

	/**
	 * 해당 Cafe 삭제 배치
	 * 
	 * @param cafeId
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
	 * get Cafe Default LayoutId
	 * 
	 * @return
	 */
	public String getDefaultLayoutId();

	/**
	 * 카페 활동 정보
	 * 
	 * @param cafeId
	 * @return
	 */
	public List<Cafe> getCafeDateCount(String cafeId);

	/**
	 * 최근 이미지
	 * 
	 * @param cafeSearchCondition
	 * @return
	 */
	public List<Cafe> getCafeImageFile(CafeSearchCondition cafeSearchCondition);
	
	/**
	 * 카페 방문 목록 삭제
	 * @param cafeId
	 */
	public void physicalDeleteCafeVisit(String cafeId);

}
