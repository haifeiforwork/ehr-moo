/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.poll.service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.poll.model.Answer;
import com.lgcns.ikep4.support.poll.model.Poll;
import com.lgcns.ikep4.support.poll.search.PollReceiverSearchCondition;

/**
 *
 * @author 서혜숙
 * @version $Id: PollService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface PollService extends GenericService<Poll, String> {
	public List<Answer> listAnswers(String pollId);
	
	public SearchResult<Poll> listPollReceiverBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition);

	public SearchResult<Poll> listPollAdminBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition);
	
	/**
	 * 투표 입력
	 * @param poll
	 * @return
	 */
	public String insertResult(Poll poll);
	
	/**
	 * 투표 진행상태 변경
	 * @param poll
	 * @return
	 */
	public void updateStatus(Poll poll);		
	
	/**
	 * 투표 상세정보
	 * @param poll
	 * @return
	 */	
	public Poll readByPoll(Poll poll);
	
	/**
	 * 투표중복여부
	 * @param poll
	 * @return
	 */	
	public Integer checkAlreadyApply(Poll poll);	
}
