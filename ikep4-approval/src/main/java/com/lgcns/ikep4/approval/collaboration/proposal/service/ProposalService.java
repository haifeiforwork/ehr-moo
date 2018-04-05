package com.lgcns.ikep4.approval.collaboration.proposal.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.collaboration.proposal.model.Proposal;
import com.lgcns.ikep4.approval.collaboration.proposal.search.ProposalSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 개발제안서 SERVICE
 * 
 * @author pjh
 * @version $Id$
 */
@Transactional
public interface ProposalService {
	
	/**
	 * 개발제안서 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SearchResult<Proposal> getProposalList( ProposalSearchCondition searchCondition, User user) throws Exception;
	
	/**
	 * 시험의뢰서 조회 및 기본셋팅
	 * @param newProductDevSearchCondition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getProposalObject( ProposalSearchCondition searchCondition, User user) throws Exception;
	
	/**
	 * 파일 저장후 갱신데이터 리턴
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String ajaxUdateFile( Proposal proposal, User user) throws Exception;
	
	/**
	 * 파일정보 조회
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Proposal getFileObject( Proposal proposal, User user) throws Exception;
	
	/**
	 * 부서 의견 권한 확인
	 * @param proposal
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Proposal getOpinionProposal( Proposal proposal, User user) throws Exception;
	
	/**
	 * 부서의견 수정
	 * @param proposal
	 * @param user
	 * @throws Exception
	 */
	public void UpdateOpinion( Proposal proposal, User user) throws Exception;
	
	/**
	 * 개발제안서 등록
	 * @param proposal
	 * @param user
	 * @throws Exception
	 */
	public void createProposal( Proposal proposal, User user) throws Exception;
	
	/**
	 * 개발제안서 수정
	 * @param proposal
	 * @param user
	 * @throws Exception
	 */
	public void updateProposal( Proposal proposal, User user) throws Exception;
	
	/**
	 * 개발제안서 삭제
	 * @param proposal
	 * @param user
	 * @throws Exception
	 */
	public void deleteProposal( Proposal proposal, User user) throws Exception;
	
	/**
	 * 권한 확인
	 * @param proposal
	 * @param user
	 * @param statCd
	 * @throws Exception
	 */
	public Proposal checkPermission (Proposal proposal, User user, int statCd) throws Exception;
}
