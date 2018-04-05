package com.lgcns.ikep4.support.personal.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.personal.model.Personal;
import com.lgcns.ikep4.support.personal.model.PersonalSearchCondition;


/**
 * Personal 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PersonalService.java 18402 2012-04-29 12:24:41Z icerainbow $
 */
@Transactional
public interface PersonalService extends GenericService<Personal, String> {

	/**
	 * 도큐먼트 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Personal> getAllForDocument(PersonalSearchCondition searchCondition);

	/**
	 * 파일 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Personal> getAllForFile(PersonalSearchCondition searchCondition);

	/**
	 * 코멘트 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Personal> getAllForComment(PersonalSearchCondition searchCondition);
	
	/**
	 * 나의 갤러리 파일 검색
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<Personal> getMyGallery(PersonalSearchCondition searchCondition,String targetUserId);

}
