/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.proposal.dao;

import java.util.List;

import com.lgcns.ikep4.approval.collaboration.proposal.model.Proposal;
import com.lgcns.ikep4.approval.collaboration.proposal.model.ProposalPermission;
import com.lgcns.ikep4.approval.collaboration.proposal.search.ProposalSearchCondition;
import com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequest;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 개발제안서 DAO
 * 
 * @author pjh
 * @version $Id$
 */
public interface ProposalDao extends GenericDao<Proposal, String> {
	
	/**
	 * 개발제안서 목록 Count
	 * @param proposalSearchCondition
	 * @return
	 * @throws Exception
	 */
	public int getProposaltCount( ProposalSearchCondition searchCondition) throws Exception;
	
	/**
	 * 개발제안서 목록 조회
	 * @param testRequestSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<Proposal> getProposalList( ProposalSearchCondition searchCondition) throws Exception;
	
	/**
	 * 개발제안서 조회
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 */
	public Proposal getProposal( ProposalSearchCondition searchCondition) throws Exception;
	
	public ProposalPermission getDeptOpinionPermission( ProposalSearchCondition searchCondition) throws Exception;
	
	/**
	 * 등록 메일 대상자 조회
	 * @param proposal
	 * @return
	 * @throws Exception
	 */
	public List<Proposal> getMailTargetUser( Proposal proposal) throws Exception;
	
	/**
	 * 개발제안서 등록
	 * @param proposal
	 * @throws Exception
	 */
	public void insertProposal( Proposal proposal) throws Exception;
	
	/**
	 * 개발제안서 수정
	 * @param proposal
	 * @throws Exception
	 */
	public void updateProposal( Proposal proposal) throws Exception;
	
	/**
	 * 부서의견 수정
	 * @param proposal
	 * @throws Exception
	 */
	public void updateOpinion( Proposal proposal) throws Exception;
	
	/**
	 * 개발제안서 삭제
	 * @param proposal
	 * @throws Exception
	 */
	public void deleteProposal( Proposal proposal) throws Exception;
	
	/**
	 * 개발제안서 파일 ID 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateFileId(  Proposal proposal) throws Exception;
}

