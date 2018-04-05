/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.alliance.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.collaboration.alliance.model.Alliance;
import com.lgcns.ikep4.collpack.collaboration.alliance.search.AllianceSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Alliance Service 인터페이스
 *
 * @author 김종철
 * @version $Id: AllianceService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface AllianceService extends GenericService<Alliance, String>{

	/**
	 *  동맹 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Alliance> listBySearchCondition(AllianceSearchCondition searchCondition);
	
	/**
	 * 동맹에게 공유한 게시판 목록
	 * @param workspaceId
	 * @return
	 */
	public List<Alliance> giveAllianceBoardListByWorkspace(String workspaceId);
	
	/**
	 * 동맹에게 공유받은 게시판 목록
	 * @param workspaceId
	 * @return
	 */
	public List<Alliance> receiveAllianceBoardListByWorkspace(String workspaceId);
	
	/**
	 * 해당 Workspace 의 동맹 목록
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
	 * 동맹정보 조회
	 * @param object
	 * @return
	 */
	public Alliance read(Alliance object);
	public String create(Alliance object,User user) ;
	/**
	 * 조직(팀) 동맹 정보 조회
	 * @param object
	 * @return
	 */
	public Alliance getOrgAlliance(Alliance object);
	/**
	 * 동맹 상태변경( 동맹요청,승인,보류,종료,취소 )
	 * @param workspace
	 */
	public void updateStatus(AllianceSearchCondition searchCondition,List<String> allianceIds,User user);
	/**
	 * 동맹 영구 삭제
	 * @param categoryId
	 */	
	public void physicalDelete(List<String> allianceIds,User user);
	public void physicalDelete(List<String> allianceIds);	
}
