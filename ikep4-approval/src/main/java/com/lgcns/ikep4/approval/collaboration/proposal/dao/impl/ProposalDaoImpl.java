package com.lgcns.ikep4.approval.collaboration.proposal.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.collaboration.proposal.dao.ProposalDao;
import com.lgcns.ikep4.approval.collaboration.proposal.model.Proposal;
import com.lgcns.ikep4.approval.collaboration.proposal.model.ProposalPermission;
import com.lgcns.ikep4.approval.collaboration.proposal.search.ProposalSearchCondition;
import com.lgcns.ikep4.approval.collaboration.testreq.model.TestRequest;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 개발제안서 ProposalDao 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Repository
public class ProposalDaoImpl extends GenericDaoSqlmap<Proposal, String> implements ProposalDao{
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "com.lgcns.ikep4.approval.collaboration.proposal.dao.ProposalDao.";
	
	/**
	 * 개발제안서 목록 Count
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public int getProposaltCount( ProposalSearchCondition searchCondition) throws Exception {
		// TODO Auto-generated method stub
		return (Integer) sqlSelectForObject( NAMESPACE + "getProposalCount" , searchCondition);
	}
	
	/**
	 * 개발제안서 목록 조회
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<Proposal> getProposalList( ProposalSearchCondition searchCondition) throws Exception {
		
		return  sqlSelectForList( NAMESPACE + "getProposalList" , searchCondition);
	}
	
	/**
	 * 개발제안서 조회
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 */
	public Proposal getProposal( ProposalSearchCondition searchCondition) throws Exception {
		
		return (Proposal) sqlSelectForObject( NAMESPACE + "getProposal" , searchCondition);
	}
	
	/**
	 * 등록 메일 대상자 조회
	 * @param proposal
	 * @return
	 * @throws Exception
	 */
	public List<Proposal> getMailTargetUser( Proposal proposal) throws Exception{
		
		return  sqlSelectForList( NAMESPACE + "getMailTargetUser" , proposal);
	}
	
	public ProposalPermission getDeptOpinionPermission( ProposalSearchCondition searchCondition) throws Exception {
		
		return (ProposalPermission) sqlSelectForObject(  NAMESPACE + "getDeptOpinionPermission" , searchCondition);
	}
	
	/**
	 * 개발제안서 등록
	 * @param proposal
	 * @throws Exception
	 */
	public void insertProposal( Proposal proposal) throws Exception {
		
		sqlInsert( NAMESPACE + "insertProposal" , proposal);
	}
	
	/**
	 * 개발제안서 수정
	 * @param proposal
	 * @throws Exception
	 */
	public void updateProposal( Proposal proposal) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateProposal" , proposal);
	}
	
	/**
	 * 개발제안서 삭제
	 * @param proposal
	 * @throws Exception
	 */
	public void deleteProposal( Proposal proposal) throws Exception {
		
		sqlDelete( NAMESPACE + "deleteProposal" , proposal);
	}
	
	/**
	 * 부서의견 수정
	 * @param proposal
	 * @throws Exception
	 */
	public void updateOpinion( Proposal proposal) throws Exception {
		sqlUpdate( NAMESPACE + "updateOpinion" , proposal);
	}
	
	/**
	 * 개발제안서 파일 ID 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateFileId( Proposal proposal) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateFileId" , proposal);
	}

	public String create(Proposal arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Proposal get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(Proposal arg0) {
		// TODO Auto-generated method stub
		
	}
}
