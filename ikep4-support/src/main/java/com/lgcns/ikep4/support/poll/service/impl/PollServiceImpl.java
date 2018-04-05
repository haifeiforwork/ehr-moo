/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.poll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.poll.constants.PollConstants;
import com.lgcns.ikep4.support.poll.dao.PollDao;
import com.lgcns.ikep4.support.poll.model.Answer;
import com.lgcns.ikep4.support.poll.model.Poll;
import com.lgcns.ikep4.support.poll.model.Target;
import com.lgcns.ikep4.support.poll.search.PollReceiverSearchCondition;
import com.lgcns.ikep4.support.poll.service.PollService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * PollService 구현 클래스
 * 
 * @author 서혜숙
 * @version $Id: PollServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("pollService")
public class PollServiceImpl extends GenericServiceImpl<Poll, String> implements PollService {

	@Autowired
	private PollDao pollDao;

	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 투표입력
	 */
	public String create(Poll poll) {
		String pollId = idgenService.getNextId();		
		poll.setPollId(pollId);		

		pollDao.create(poll);
		
		if ( poll.getAnswerTitles() != null ) {
			for(String answerTitle : poll.getAnswerTitles() ) {
				poll.setAnswerId(idgenService.getNextId());
				poll.setAnswerTitle(answerTitle);
				pollDao.insertAnswer(poll);
			}
		}

		//사용자 권한
		poll.setTargetType("0");
		if(poll.getAddrUserList() != null) {
			for(int i = 0; i < poll.getAddrUserList().length; i++) {
				poll.setTargetId(poll.getAddrUserList()[i]);
				pollDao.insertTarget(poll);
			}
		}		
		//그룹 권한
		poll.setTargetType("1");
		if(poll.getAddrGroupTypeList() != null) {
			for(int i = 0; i < poll.getAddrGroupTypeList().length; i++) {
				poll.setTargetId(poll.getAddrGroupTypeList()[i]);
				pollDao.insertTarget(poll);
			}
		}		
		
		return pollId;
	}	
	
	/**
	 * 투표하기 정보 입력
	 */
	public String insertResult(Poll poll) {
		String pollId = poll.getPollId();
		pollDao.insertResult(poll);
		
		return pollId;
	}	

	/**
	 * 투표상세정보
	 */
	public Poll read(String id) {
		Poll poll = pollDao.get(id);

		int answerTotal = 0;
		int answerTotalSum = 0;
		int answerPercent = 0;
		int totalAnswerLen = 0;
		int idx = 0;
		int tmpPercent = 0;

		if ( poll != null ) {
			List<Answer> answerList = pollDao.selectAnswers(id);
			totalAnswerLen = answerList.size();
			for(Answer answer : answerList) {
				answerTotalSum = answer.getAnswerTotalSum();
				answerTotal = answer.getAnswerTotal();
				answerPercent = (int)Math.round((float)(answerTotal / (double)answerTotalSum * PollConstants.YN_PERCENT));

				if ( idx == (totalAnswerLen - 1)) {
					answerPercent = PollConstants.YN_PERCENT - tmpPercent;
					if ( tmpPercent > PollConstants.YN_PERCENT || answerTotal == 0 ) {
						answerPercent = 0;
					}
				}
				tmpPercent += answerPercent;
		
				answer.setAnswerPercent(answerPercent);
				idx++;
			}
			poll.setAnswerList(answerList);
			List<Target> targetList = pollDao.selectTargets(id);
			List<Target> targetUserList = new ArrayList<Target>();
			List<Target> targetGroupTypeList = new ArrayList<Target>();
			if ( targetList.size()>0 ) {
				for(Target target : targetList) {
					//사용자
					if ( "0".equals(target.getTargetType()) ) {
						targetUserList.add(target);
					//그룹	
					} else {
						targetGroupTypeList.add(target);
					}
				}
			}

			poll.setTargetUserList(targetUserList);
			poll.setTargetGroupTypeList(targetGroupTypeList);
			
			poll.setTargetList(targetList);		

		}
		return poll;
	}

	/**
	 * 투표상세정보 가져오기
	 */
	public Poll readByPoll(Poll paramPoll) {
		Poll poll = new Poll();
		poll = pollDao.getByPoll(paramPoll);
		String id = "";
		if ( poll != null ) {
			id = poll.getPollId();
		}
		int answerTotal = 0;
		int answerTotalSum = 0;
		int answerPercent = 0;
		int totalAnswerLen = 0;
		int idx = 0;
		int tmpPercent = 0;

		if ( poll != null ) {
			List<Answer> answerList = pollDao.selectAnswers(id);
			totalAnswerLen = answerList.size();
			for(Answer answer : answerList) {
				answerTotalSum = answer.getAnswerTotalSum();
				answerTotal = answer.getAnswerTotal();
				answerPercent = (int)Math.round((float)(answerTotal / (double)answerTotalSum * PollConstants.YN_PERCENT));

				if ( idx == (totalAnswerLen - 1)) {
					answerPercent = PollConstants.YN_PERCENT - tmpPercent;
					if ( tmpPercent > PollConstants.YN_PERCENT || answerTotal == 0 ) {
						answerPercent = 0;
					}
				}
				tmpPercent += answerPercent;
		
				answer.setAnswerPercent(answerPercent);
				idx++;
			}
			poll.setAnswerList(answerList);
			List<Target> targetList = pollDao.selectTargets(id);
			List<Target> targetUserList = new ArrayList<Target>();
			List<Target> targetGroupTypeList = new ArrayList<Target>();
			if ( targetList.size()>0 ) {
				for(Target target : targetList) {
					//사용자
					if ( "0".equals(target.getTargetType()) ) {
						targetUserList.add(target);
					//그룹	
					} else {
						targetGroupTypeList.add(target);
					}
				}
			}

			poll.setTargetUserList(targetUserList);
			poll.setTargetGroupTypeList(targetGroupTypeList);
			
			poll.setTargetList(targetList);		
		}
		return poll;
	}

	/**
	 * 투표삭제
	 */
	public void delete(String id) {
		pollDao.deleteTarget(id);
		pollDao.deleteResult(id);
		pollDao.deleteAnswer(id);
		pollDao.remove(id);
	}	
	
	/**
	 * 투표정보 수정
	 */
	public void update(Poll poll) {
		pollDao.update(poll);
		String pollId = poll.getPollId();
		
		pollDao.deleteAnswer(pollId);
		pollDao.deleteTarget(pollId);
		pollDao.deleteResult(pollId);
		
		if ( poll.getAnswerTitles() != null ) {
			for(String answerTitle : poll.getAnswerTitles() ) {
				poll.setAnswerId(idgenService.getNextId());
				poll.setAnswerTitle(answerTitle);
				pollDao.insertAnswer(poll);
			}
		}
		//사용자 권한
		poll.setTargetType("0");
		if(poll.getAddrUserList() != null) {
			for(int i = 0; i < poll.getAddrUserList().length; i++) {
				poll.setTargetId(poll.getAddrUserList()[i]);
				pollDao.insertTarget(poll);
			}
		}		
		//그룹 권한
		poll.setTargetType("1");
		if(poll.getAddrGroupTypeList() != null) {
			for(int i = 0; i < poll.getAddrGroupTypeList().length; i++) {
				poll.setTargetId(poll.getAddrGroupTypeList()[i]);
				pollDao.insertTarget(poll);
			}
		}		
	}
	
	/**
	 * 투표진행상태 갱신
	 */
	public void updateStatus(Poll poll) {
		pollDao.updateStatus(poll);
	}

	/**
	 * 투표목록(투표참여,My Online Poll)
	 */		
	public SearchResult<Poll> listPollReceiverBySearchCondition(PollReceiverSearchCondition searchCondition) {
		Integer count = this.pollDao.countPollReceiverBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<Poll> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Poll>(searchCondition);
			
		} else {
			
			List<Poll> pollItemList = this.pollDao.listPollReceiverBySearchCondition(searchCondition);  
			List<Answer> answerList = null;
			for(Poll poll : pollItemList) {
				answerList = pollDao.selectAnswers(poll.getPollId());
				poll.setAnswerList(answerList);
			}
			searchResult = new SearchResult<Poll>(pollItemList, searchCondition); 
		}  
		
		return searchResult;
	}	

	/**
	 * 투표목록(투표관리 화면)
	 */	
	public SearchResult<Poll> listPollAdminBySearchCondition(PollReceiverSearchCondition searchCondition) {
		Integer count = this.pollDao.countPollAdminBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<Poll> searchResult = null;
		
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Poll>(searchCondition);			
		} else {			
			List<Poll> pollItemList = this.pollDao.listPollAdminBySearchCondition(searchCondition);  
			searchResult = new SearchResult<Poll>(pollItemList, searchCondition); 
		}  
		
		return searchResult;
	}	
	
	/**
	 * 보기항목 목록
	 */
	public List<Answer> listAnswers(String pollId) {
		return pollDao.selectAnswers(pollId);
	}	
	
	/**
	 * 투표중복여부
	 */
	public Integer checkAlreadyApply(Poll poll) {
		return pollDao.checkAlreadyApply(poll);
	}	

}
