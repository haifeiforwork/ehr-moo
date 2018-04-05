/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.poll.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.poll.model.Answer;
import com.lgcns.ikep4.support.poll.model.Poll;
import com.lgcns.ikep4.support.poll.model.Target;
import com.lgcns.ikep4.support.poll.search.PollReceiverSearchCondition;


/**
 * 
 * @author 서혜숙
 * @version $Id: PollDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface PollDao extends GenericDao<Poll, String> {
 
	/**
	 * 투표 보기사항
	 * 
	 * @param pollId
	 * @return
	 */
	public List<Answer> selectAnswers(String pollId);
	
	/**
	 * 투표 대상자
	 * 
	 * @param pollId
	 * @return
	 */
	public List<Target> selectTargets(String pollId);	
	
	/**
	 * Answer 정보 삭제
	 *
	 * @param pollId
	 * @return
	 */
	public void deleteAnswer(String pollId);	
	
	/**
	 * Target 정보 삭제
	 * 
	 * @param pollId
	 * @return
	 */
	public void deleteTarget(String pollId);	
	
	/**
	 * Result 정보 삭제
	 * 
	 * @param pollId
	 * @return
	 */
	public void deleteResult(String pollId);	
	
	/**
	 * 투표 정보 변경
	 * 
	 * @param poll
	 * @return
	 */
	public void updateStatus(Poll poll);	
	
	/**
	 * Answer 입력
	 * 
	 * @param poll
	 * @return
	 */
	public String insertAnswer(Poll poll);	
	
	/**
	 * Target 입력
	 * 
	 * @param poll
	 * @return
	 */
	public String insertTarget(Poll poll);
	
	/**
	 * Result 입력
	 * 
	 * @param poll
	 * @return
	 */
	public String insertResult(Poll poll);	
	
	/**
	 * 투표목록
	 * 
	 * @param pollReceiverSearchCondition
	 * @return
	 */	
	List<Poll> listPollReceiverBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition);
	
	/**
	 * 투표목록수
	 * 
	 * @param pollReceiverSearchCondition
	 * @return
	 */		
	Integer countPollReceiverBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition); 

	/**
	 * 투표목록(투표관리화면)
	 * 
	 * @param pollReceiverSearchCondition
	 * @return
	 */		
	List<Poll> listPollAdminBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition);
	
	/**
	 * 투표목록수(투표관리화면)
	 * 
	 * @param pollReceiverSearchCondition
	 * @return
	 */		
	Integer countPollAdminBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition); 
	
	/**
	 * 투표상세정보
	 * 
	 * @param poll
	 * @return
	 */		
	public Poll getByPoll(Poll poll);
	
	/**
	 * 투표중복여부
	 * 
	 * @param poll
	 * @return
	 */		
	public Integer checkAlreadyApply(Poll poll);	
}
