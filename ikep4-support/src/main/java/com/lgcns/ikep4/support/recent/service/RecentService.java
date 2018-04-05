package com.lgcns.ikep4.support.recent.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.recent.model.Recent;
import com.lgcns.ikep4.support.recent.model.RecentSearchCondition;


/**
 * Recent 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: RecentService.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Transactional
public interface RecentService extends GenericService<Recent, String> {

	
	/**
	 * 도큐먼트 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Recent> getAllForDocument(RecentSearchCondition searchCondition);

	/**
	 * 피플 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Recent> getAllForPeople(RecentSearchCondition searchCondition);
	
	/**
	 * 도큐먼트 요약 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Recent> getSummaryForDocument(RecentSearchCondition searchCondition);
	
	/**
	 * 피플 요약 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Recent> getSummaryForPeople(RecentSearchCondition searchCondition);
	
	/**
	 * 마이 Collaboration 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectCollaboration(Map<String, String> map);
	
	/**
	 * 마이 Cafe 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectCafe(Map<String, String> map);
	
	/**
	 * 마이 MicroBlog 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<Recent> selectMicroblog(Map<String, String> map);
	
	/**
	 * 마이 Follower 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Recent> selectFollower(RecentSearchCondition searchCondition);
	
	/**
	 * 마이 Following 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Recent> selectFollowing(RecentSearchCondition searchCondition);
	
	/**
	 * 마이 Intimate 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Recent> selectIntimate(RecentSearchCondition searchCondition);
	
	/**
	 * 마이 Collaboration Member 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Recent> selectCollaborationMember(RecentSearchCondition searchCondition);

}
