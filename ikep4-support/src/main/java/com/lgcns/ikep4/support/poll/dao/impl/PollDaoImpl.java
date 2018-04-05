/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.poll.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.support.poll.dao.PollDao;
import com.lgcns.ikep4.support.poll.model.Answer;
import com.lgcns.ikep4.support.poll.model.Poll;
import com.lgcns.ikep4.support.poll.model.Target;
import com.lgcns.ikep4.support.poll.search.PollReceiverSearchCondition;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 
 * @author 서혜숙
 * @version $Id: PollDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository("pollDao")
public class PollDaoImpl extends GenericDaoSqlmap<Poll, String> implements PollDao {
	/**
	 * 투표 보기사항
	 */	
	public List<Answer> selectAnswers(String pollId) {
		return (List) sqlSelectForListOfObject("poll.selectAnswers", pollId);
	}

	/**
	 * 투표 대상자
	 */		
	public List<Target> selectTargets(String pollId) {
		return (List) sqlSelectForListOfObject("poll.selectTargets", pollId);
	}
	
	/**
	 * 투표목록
	 */		
	public List<Poll> listPollReceiverBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition) {
		return sqlSelectForList("poll.selectAll", pollReceiverSearchCondition);
	}	

	/**
	 * 투표목록수
	 */		
	public Integer countPollReceiverBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition) {
		return (Integer)sqlSelectForObject("poll.countPollReceiverBySearchCondition", pollReceiverSearchCondition);
	}

	/**
	 * 투표목록(투표관리화면)
	 */		
	public List<Poll> listPollAdminBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition) {
		return sqlSelectForList("poll.selectAdminList", pollReceiverSearchCondition);
	}	

	/**
	 * 투표목록수(투표관리화면)
	 */		
	public Integer countPollAdminBySearchCondition(PollReceiverSearchCondition pollReceiverSearchCondition) {
		return (Integer)sqlSelectForObject("poll.countPollAdminBySearchCondition", pollReceiverSearchCondition);
	}
	
	/**
	 * 투표저장
	 */		
	public String create(Poll poll) {
		sqlInsert("poll.insert", poll);
		return poll.getPollId();
	}
	
	/**
	 * 보기항목저장
	 */		
	public String insertAnswer(Poll poll) {
		return (String) sqlInsert("poll.insertAnswer", poll);
	}
	
	/**
	 * 대상자저장
	 */		
	public String insertTarget(Poll poll) {
		return (String) sqlInsert("poll.insertTarget", poll);
	}	
	
	/**
	 * 투표정보저장
	 */			
	public String insertResult(Poll poll) {
		return (String) sqlInsert("poll.insertResult", poll);
	}	

	/**
	 * 투표건수확인
	 */		
	public boolean exists(String id) {
		Integer count = (Integer) sqlSelectForObject("poll.selectCount", id);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 투표가져오기
	 */		
	public Poll get(String id) {
		return (Poll) sqlSelectForObject("poll.select", id);
	}

	/**
	 * 투표삭제
	 */		
	public void remove(String id) {
		sqlDelete("poll.delete", id);
	}

	/**
	 * 보기항목삭제
	 */		
	public void deleteAnswer(String id) {
		sqlDelete("poll.deleteAnswer", id);
	}

	/**
	 * 대상자삭제
	 */		
	public void deleteTarget(String id) {
		sqlDelete("poll.deleteTarget", id);
	}
	
	/**
	 * 투표정보삭제
	 */		
	public void deleteResult(String id) {
		sqlDelete("poll.deleteResult", id);
	}
	
	/**
	 * 투표갱신
	 */	
	public void update(Poll poll) {
		sqlUpdate("poll.update", poll);
	}
	
	/**
	 * 투표진행상태 갱신
	 */		
	public void updateStatus(Poll poll) {
		sqlUpdate("poll.updateStatus", poll);
	}
	
	/**
	 * 투표관련 정보 가져오기
	 */		
	public Poll getByPoll(Poll poll) {
		return (Poll) sqlSelectForObject("poll.select", poll);
	}	
	
	/**
	 * 투표 중복여부 가져오기
	 */		
	public Integer checkAlreadyApply(Poll poll) {
		return (Integer) sqlSelectForObject("poll.selectAlreadyApply", poll);
	}	
}
