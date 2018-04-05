/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service;

import com.lgcns.ikep4.collpack.workmanual.model.Approval;
import com.lgcns.ikep4.collpack.workmanual.model.ManualVersion;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualVersionService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ManualVersionService extends GenericService<ManualVersion, String> {
	/**
	 *개인 업무 매뉴얼 버젼  조회
	 * @param manualSearchCondition
	 * @return
	 */
	public SearchResult<ManualVersion> listMyManualVersion(ManualSearchCondition manualSearchCondition);
	/**
	 *업무 매뉴얼 버젼  조회
	 * @param manualSearchCondition
	 * @return
	 */
	public SearchResult<ManualVersion> listManualVersion(ManualSearchCondition manualSearchCondition);
	/**
	 *버젼 되돌리기
	 * @param manualSearchCondition
	 * @param user
	 */
	public void createRedoManualVersion(ManualSearchCondition manualSearchCondition, User user);
	/**
	 *업무 매뉴얼 버젼 조회
	 * @param versionId
	 * @param portalId
	 * @return
	 */
	public ManualVersion readManualVersion(String versionId, String portalId);
	/**
	 *상신중인 매뉴얼 버젼 갯수
	 * @param manualId
	 * @param portalId
	 * @return
	 */
	public Integer countSubmittingManualVersion(String manualId, String portalId);
	/**
	 *업무 매뉴얼 버젼 삭제
	 * @param versionId
	 * @param portalId
	 * @param userId
	 * @param userName
	 */
	public void deleteManualVersion(String versionId, String portalId, String userId, String userName);
	/**
	 *업무 매뉴얼 버젼 수정
	 * @param manualVersion
	 * @param user
	 */
	public void updateManualVersion(ManualVersion manualVersion, User user);
	/**
	 *상신
	 * @param approval
	 * @param user
	 * @return
	 */
	public String createApproval(Approval approval, User user);
	/**
	 *상신 취소
	 * @param versionId
	 * @param user
	 * @param manualType
	 */
	public void cancelApproval(String versionId, User user, String manualType);
	/**
	 *매뉴얼 테이블에 존재하는 매뉴얼 버젼 정보 조회
	 * @param manualId
	 * @param portalId
	 * @return
	 */
	public ManualVersion getManualVersionBymanualId(String manualId, String portalId);
	/**
	 *매뉴얼 버젼 신규 등록
	 * @param manualVersion
	 * @param categoryId
	 * @param tags
	 * @param user
	 */
	public void createManualVersion(ManualVersion manualVersion, String categoryId, String tags, User user);
}
