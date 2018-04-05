/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.service;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.*;
import com.lgcns.ikep4.framework.core.service.GenericService;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: DictionaryService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface DictionaryService extends GenericService<Dictionary, String> {
	/**
	 * 용어사전검색
	 * @param dictionarySearchCondition
	 * @return
	 */	
	public SearchResult<Dictionary> listDictionaryBySearchCondition(DictionarySearchCondition dictionarySearchCondition);
	
	/**
	 * 용어 상세조회
	 * @param dictionary
	 * @return
	 */		
	public Dictionary readDetail(Dictionary dictionary);
	
	/**
	 * 사전 삭제
	 * @param dictionaryId
	 * @return
	 */	
	public void deleteDictionary(String dictionaryId);
	
	/**
	 * 사전 목록(메인화면)
	 * @param 
	 * @return
	 */	
	public List<Dictionary> selectDictionarys();
	
	/**
	 * 사전 목록(메인화면)
	 * @param dictionarySearchCondition
	 * @return
	 */	
	public List<Dictionary> selectDictionarysByCondition(DictionarySearchCondition dictionarySearchCondition);
	
	/**
	 * 메인화면에서 디폴트로 셋팅할 사전
	 * @param dictionarySearchCondition
	 * @return
	 */		
	public String selectDictionaryId(DictionarySearchCondition dictionarySearchCondition);
	
	/**
	 * 사전 이력 목록
	 * @param wordGroupId
	 * @return
	 */		
	public List<Dictionary> selectWordHistoryList(String wordGroupId);
	
	/**
	 * 사전 등록
	 * @param dictionary
	 * @return
	 */		
	public String insertDictionary(Dictionary dictionary);
	
	/**
	 * 사전 정보 가져오기
	 * @param dictionaryId
	 * @return
	 */		
	public Dictionary selectDictionary(String dictionaryId);
	
	/**
	 * 사전명 변경
	 * @param dictionary
	 * @return
	 */	
	public void updateDictionaryName(Dictionary dictionary);
	
	/**
	 * 사전 순서 변경
	 * @param dictionaryIdes
	 * @return
	 */		
	public void updateDictionarySortOrder(String dictionaryIdes);
	
	/**
	 * 사전생성
	 * @param dictionary
	 * @param user
	 * @return
	 */		
	public String create(Dictionary dictionary,User user);
	
	
	/**
	 * 중복 단어 개수
	 * @param dictionarySearchCondition
	 * @return
	 */	
	boolean isDuplicateByWordName(DictionarySearchCondition dictionarySearchCondition); 
}
