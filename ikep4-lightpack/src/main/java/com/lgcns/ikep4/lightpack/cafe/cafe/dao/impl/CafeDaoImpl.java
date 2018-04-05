/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.cafe.cafe.dao.CafeDao;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.search.CafeSearchCondition;

/**
 * Cafe Dao 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: CafeDaoImpl.java 17089 2011-12-13 10:28:04Z giljae $
 */
@Repository("cafeDao")
public class CafeDaoImpl extends GenericDaoSqlmap<Cafe, String> implements
		CafeDao {

	private static final String NAMESPACE = "lightpack.cafe.cafe.dao.Cafe.";

	/**
	 * 검색조건에 의한 cafe 목록
	 */
	public List<Cafe> listBySearchCondition(CafeSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition",
				searchCondition);
	}

	/**
	 * 검색조건에 의한 cafe 갯수
	 */
	public Integer countBySearchCondition(CafeSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE
				+ "countBySearchCondition", searchCondition);
	}

	public List<Cafe> listBySearchConditionPersonal(
			CafeSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE
				+ "listBySearchConditionPersonal", searchCondition);
	}

	public Integer countBySearchConditionPersonal(
			CafeSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE
				+ "countBySearchConditionPersonal", searchCondition);
	}

	/**
	 * 동맹요청 목록
	 */
	public List<Cafe> listCafeByType(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listCafeByType", map);
	}

	public List<Cafe> listCafeByCategory(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listCafeByCategory", map);
	}

	public List<Cafe> listMyCafe(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listMyCafe", map);
	}

	public List<Cafe> listCloseCafe(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listCloseCafe", map);
	}

	public List<Cafe> listNewCafe(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listNewCafe", map);
	}

	public List<Cafe> listCafeManager(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "listCafeManager", map);
	}

	public Integer countBySearchConditionGroupHierarchy(
			CafeSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE
				+ "countBySearchConditionGroupHierarchy", searchCondition);
	}

	public List<Cafe> listBySearchConditionGroupHierarchy(
			CafeSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE
				+ "listBySearchConditionGroupHierarchy", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#listBySearchConditionGroup
	 * (com.lgcns.ikep4.lightpack.cafe.search.CafeSearchCondition) cafe/부서 미개설부서
	 * 리스트
	 */
	public List<Cafe> listBySearchConditionGroup(
			CafeSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchConditionGroup",
				searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#listBySearchConditionCloseGroup
	 * (com.lgcns.ikep4.lightpack.cafe.search.CafeSearchCondition) cafe/부서 폐쇄대기
	 * 리스트
	 */
	public List<Cafe> listBySearchConditionCloseGroup(
			CafeSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE
				+ "listBySearchConditionCloseGroup", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#getGroupMemberIds(java.lang
	 * .String) cafe/부서 조직원정보
	 */
	public List<Cafe> listGroupMembers(String groupId) {

		return sqlSelectForList(NAMESPACE + "listGroupMembers", groupId);
	}

	public List<Cafe> countCafeTypeOrg(Map<String, String> map) {
		return this.sqlSelectForList(NAMESPACE + "countCafeByTeam", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#countBySearchConditionGroup
	 * (com.lgcns.ikep4.lightpack.cafe.search.CafeSearchCondition) cafe/부서 미개설부서
	 * 리스트 카운트
	 */
	public Integer countBySearchConditionGroup(
			CafeSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE
				+ "countBySearchConditionGroup", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#countBySearchConditionCloseGroup
	 * (com.lgcns.ikep4.lightpack.cafe.search.CafeSearchCondition) cafe/부서 폐쇄대기
	 * 카운트
	 */
	public Integer countBySearchConditionCloseGroup(
			CafeSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE
				+ "countBySearchConditionCloseGroup", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 * cafe 생성
	 */
	public String create(Cafe object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getCafeId();
	}

	public void createCafeVisit(Map<String, String> map) {
		sqlInsert(NAMESPACE + "createCafeVisit", map);
	}

	public boolean existsCafeVisit(Map<String, String> map) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE
				+ "existsCafeVisit", map);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#get(com.lgcns.ikep4
	 * .lightpack.cafe.model.Cafe) cafe 정보 조회
	 */
	public Cafe get(String cafeId) {
		return (Cafe) sqlSelectForObject(NAMESPACE + "getCafe", cafeId);

	}

	public Cafe getDefaultCafe(Map<String, String> map) {
		return (Cafe) sqlSelectForObject(NAMESPACE + "getDefaultCafe", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#getOrgGroup(java.lang.String)
	 * cafe/부서 조직도 정보
	 */
	public List<Cafe> getOrgGroup(String groupId) {
		return sqlSelectForList(NAMESPACE + "selectOrgGroup", groupId);
	}

	/**
	 * 중복된 Cafe 이름 검색
	 */
	public boolean checkCafeName(Map<String, String> map) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE
				+ "checkCafeName", map);
		return count < 1;
	}

	/**
	 * 중복된 Cafe Team Id
	 * 
	 * @param teamId
	 * @return
	 */
	public String checkCafeTeam(String teamId) {
		return (String) sqlSelectForObject(NAMESPACE + "checkCafeTeam", teamId);

	}

	@Deprecated
	public boolean exists(String cafeId) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#exists(com.lgcns.ikep4
	 * .lightpack.cafe.model.Cafe) cafe 존재유무
	 */
	public boolean exists(Cafe object) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "exists",
				object);
		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 * cafe 정보 수정
	 */
	public void update(Cafe object) {
		this.sqlUpdate(NAMESPACE + "update", object);
	}

	public void updateIntro(Cafe object) {
		this.sqlUpdate(NAMESPACE + "updateIntro", object);
	}

	public void updateLayout(Cafe object) {
		this.sqlUpdate(NAMESPACE + "updateLayout", object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#updateStatus(com.lgcns
	 * .ikep4.lightpack.cafe.model.Cafe) cafe 상태 변경(개설,폐쇄신청,폐쇄,개설반려)
	 */
	public void updateStatus(Cafe object) {
		this.sqlUpdate(NAMESPACE + "updateStatus", object);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lgcns.ikep4.lightpack.cafe.dao.CafeDao#physicalDelete(java
	 * .lang.String) cafe 물리적 삭제
	 */
	public void physicalDelete(String cafeId) {
		sqlDelete(NAMESPACE + "physicalDelete", cafeId);

	}

	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	public List<Cafe> listAllCafeDelete() {
		return this.sqlSelectForList(NAMESPACE + "listAllCafeDelete");
	}

	public void physicalDeleteCafeVisit(String cafeId) {
		sqlDelete(NAMESPACE + "physicalDeleteCafeVisit", cafeId);
	}

	public void syncTeamCafe(String userId) {
		sqlSelectForObject(NAMESPACE + "syncTeamCafe", userId);
	}

	public void syncUserCafe(String userId) {
		sqlSelectForObject(NAMESPACE + "syncUserCafe", userId);
	}

	public void deleteCafeBatch(String cafeId) {
		sqlSelectForObject(NAMESPACE + "deleteCafeBatch", cafeId);
	}

	public List<Cafe> listGroupHierarchy(String groupId) {
		return this.sqlSelectForList(NAMESPACE + "listGroupHierarchy", groupId);
	}

	public String getDefaultLayoutId() {
		return (String) this.sqlSelectForObject(NAMESPACE
				+ "getDefaultLayoutId");
	}

	public List<Cafe> getCafeDateCount(String cafeId) {
		return this.sqlSelectForList(NAMESPACE + "getCafeDateCount", cafeId);
	}

	public List<Cafe> getCafeImageFile(CafeSearchCondition cafeSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "getCafeImageFile",
				cafeSearchCondition);
	}
}
