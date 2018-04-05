/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.alliance.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.collaboration.alliance.model.Alliance;
import com.lgcns.ikep4.collpack.collaboration.alliance.search.AllianceSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;



/**
 * Alliance Dao 인터페이스
 * 
 * @author 김종철
 * @version $Id: AllianceDao.java 16740 2011-10-03 09:07:01Z giljae $
 */
public interface AllianceDao extends GenericDao<Alliance, String> {

	/**
	 * 검색조건에 의한 동맹 목록
	 * 
	 * @param workspaceSearchCondition
	 * @return
	 */
	public List<Alliance> listBySearchCondition(AllianceSearchCondition searchCondition);
	
	/**
	 * 동맹에게 공유한 게시판 목록 (전체)
	 * @param workspaceId
	 * @return
	 */	
	public List<Alliance> giveAllianceBoardListByWorkspace(String workspaceId);
	
	/**
	 * 동맹으로부터 공유받은 게시판 목록 (전체)
	 * @param workspaceId
	 * @return
	 */
	public List<Alliance> receiveAllianceBoardListByWorkspace(String workspaceId);
	
	/**
	 * 해당 동맹에게 공유한 게시판 목록(WS 1건)
	 * @param workspaceId
	 * @return
	 */
	public List<Alliance> giveAllianceBoardByWorkspace(String workspaceId,String toWorkspaceId);
	
	/**
	 * 해당 동맹으로 부터 공유 받은 게시판 목록(WS 1건)
	 * @param workspaceId
	 * @return
	 */
	public List<Alliance> receiveAllianceBoardByWorkspace(String workspaceId,String toWorkspaceId);	

	
	/**
	 * 동맹 Coll 목록 
	 * @param workspaceId
	 * @return
	 */
	public List<Alliance> listAllianceByWorkspace(String workspaceId);

	/**
	 * 게시판을 공개한 동맹 Workspace 목록
	 * @param workspaceId
	 * @param boardId
	 * @return
	 */
	public List<Alliance> listAllianceByBoard(String workspaceId,String boardId);
	/**
	 * 검색조건에 의한 동맹 갯수
	 * 
	 * @param workspaceSearchCondition
	 * @return
	 */
	public Integer countBySearchCondition(AllianceSearchCondition searchCondition);

	/**
	 * Alliance 정보 조회
	 * 
	 * @param object
	 * @return
	 */
	public Alliance get(Alliance object);
	
	/**
	 * 조직(팀) Alliance 정보 조회
	 * @param object
	 * @return
	 */
	public Alliance getOrgAlliance(Alliance object);
	
	/**
	 * Workspace 상태 변경 ( 개설승인,폐쇄신청,폐쇄 )
	 * 
	 * @param workspace
	 */
	public void updateStatus(Alliance object);

	/**
	 * 동맹 영구 삭제
	 * 
	 * @param category
	 */
	public void physicalDelete(String allianceId);
}
