/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.alliance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.alliance.dao.AllianceDao;
import com.lgcns.ikep4.collpack.collaboration.alliance.model.Alliance;
import com.lgcns.ikep4.collpack.collaboration.alliance.search.AllianceSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;



/**
 * Alliance Dao 구현
 * 
 * @author 김종철
 * @version $Id: AllianceDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("allianceDao")
public class AllianceDaoImpl extends GenericDaoSqlmap<Alliance, String> implements AllianceDao {

	private static final String NAMESPACE = "collpack.collaboration.alliance.dao.Alliance.";

	/**
	 * 동맹 목록
	 */
	public List<Alliance> listBySearchCondition(AllianceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
	}
	/**
	 * 동맹에게 공유한 게시판 목록 전체
	 */
	public List<Alliance> giveAllianceBoardListByWorkspace(String workspaceId) {
		return this.sqlSelectForList(NAMESPACE + "giveAllianceBoardListByWorkspace", workspaceId);
	}
	/**
	 * 동맹에게 공유받은 게시판 목록 전체
	 */
	public List<Alliance> receiveAllianceBoardListByWorkspace(String workspaceId) {
		return this.sqlSelectForList(NAMESPACE + "receiveAllianceBoardListByWorkspace", workspaceId);
	}
	/**
	 * 해당 동맹에게 공유한 게시판 목록(WS 1건)
	 */
	public List<Alliance> giveAllianceBoardByWorkspace(String workspaceId,String toWorkspaceId) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		map.put("toWorkspaceId", toWorkspaceId);
		return this.sqlSelectForList(NAMESPACE + "giveAllianceBoardByWorkspace", map);
	}
	/**
	 * 해당 동맹으로 부터 공유 받은 게시판 목록(WS 1건)
	 */	
	public List<Alliance> receiveAllianceBoardByWorkspace(String workspaceId,String toWorkspaceId) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		map.put("toWorkspaceId", toWorkspaceId);		
		return this.sqlSelectForList(NAMESPACE + "receiveAllianceBoardByWorkspace", map);
	}
	/**
	 * 동맹 목록
	 */
	public List<Alliance> listAllianceByWorkspace(String workspaceId) {
		return this.sqlSelectForList(NAMESPACE + "listAllianceByWorkspace", workspaceId);
	}
	/**
	 * 게시판을 공개한 동맹 Workspace 목록(게시판 조회/수정)
	 */
	public List<Alliance> listAllianceByBoard(String workspaceId,String boardId) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		map.put("boardId", boardId);
		return this.sqlSelectForList(NAMESPACE + "listAllianceByBoard", map);
	}	
	/**
	 * 동맹 갯수
	 */
	public Integer countBySearchCondition(AllianceSearchCondition searchCondition) {
		return (Integer) this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}

	/**
	 * 동맹 신청
	 */
	public String create(Alliance object) {
		sqlInsert(NAMESPACE + "create", object);
		return object.getAllianceId();
	}

	/**
	 * 동맹 조회
	 */
	public Alliance get(Alliance object) {
		return (Alliance) sqlSelectForObject(NAMESPACE + "get", object);
	}
	/**
	 * 동맹 조회(조직)
	 */
	public Alliance getOrgAlliance(Alliance object) {
		return (Alliance) sqlSelectForObject(NAMESPACE + "getOrgAlliance", object);
	}
	/**
	 * 동맹 조회
	 */
	public Alliance get(String allianceId) {
		return (Alliance) sqlSelectForObject(NAMESPACE + "getAlliance", allianceId);
	}

	@Deprecated
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * 동맹 상태 변경
	 */
	public void updateStatus(Alliance object) {
		this.sqlUpdate(NAMESPACE + "updateStatus", object);
	}

	/**
	 * 동맹 정보 수정
	 */
	public void update(Alliance object) {
		this.sqlUpdate(NAMESPACE + "update", object);
	}

	@Deprecated
	public void remove(String id) {
		// TODO Auto-generated method stub
	}
	/**
	 * 동맹 영구 삭제
	 */
	public void physicalDelete(String allianceId) {
		this.sqlUpdate(NAMESPACE + "physicalDelete", allianceId);
	}

}
