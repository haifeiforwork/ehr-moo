/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.DictionarySearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: DictionaryDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface DictionaryDao extends GenericDao<Dictionary, String>  {
	
	/**
	 * verion정보(0.1증가)
	 * @param dictionary
	 * @return
	 */
	public String selectVersion(Dictionary dictionary);
	
	/**
	 * verion정보
	 * @param dictionary
	 * @return
	 */	
	public String selectOriginVersion(Dictionary dictionary);	
	
	/**
	 * 최근 등록자 ID
	 * @param wordGroupId
	 * @return
	 */
	public String selectRecentInputRegisterId(String wordGroupId);	
	
	/**
	 * 첫 로딩시 선택되어야 할 사전
	 * 
	 * @return
	 */	
	public String selectDictionaryId(DictionarySearchCondition dictionarySearchCondition);
	
	/**
	 * dictionary정보
	 * @param dictionaryId 
	 * @return
	 */
	public Dictionary selectDictionary(String id);
	
	/**
	 * dictionary정보
	 * @param dictionaryId 
	 * @return
	 */	
	public List<Dictionary> selectDictionarys();
	
	/**
	 * dictionary정보
	 * @param dictionarySearchCondition 
	 * @return
	 */	
	public List<Dictionary> selectDictionarysByCondition(DictionarySearchCondition dictionarySearchCondition);	
	/**
	 * 용어별 수정이력목록
	 * @param wordGroupId 
	 * @return
	 */		
	public List<Dictionary> selectWordHistoryList(String wordGroupId);
	
	/**
	 * hit 카운트 올리기
	 * @param wordId
	 * @return
	 */
	public void updateHit(String id);
	
	/**
	 * hit 날짜변경
	 * @param wordId
	 * @return
	 */
	public void updateHitDate(Dictionary dictionary);	

	/**
	 * 사전 이름 변경
	 * @param dictionary
	 * @return
	 */
	public void updateDictionaryName(Dictionary dictionary);	
	
	/**
	 * 사전 정렬순서 변경
	 * @param dictionary
	 * @return
	 */
	public void updateDictionarySortOrder(Dictionary dictionary);		
	

	/**
	 * 조회여부
	 * @param dictionary
	 * @return
	 */
	public Integer checkAlreadyRead(Dictionary dictionary);
	
	/**
	 * hit 정보 입력
	 * @param dictionary
	 * @return
	 */
	public String insertHit(Dictionary dictionary);
	
	/**
	 * Dictionary 정보 입력
	 * @param dictionary
	 * @return
	 */
	public String insertDictionary(Dictionary dictionary);	
	
	/**
	 * 사전 삭제
	 * @param dictionaryId
	 * @return
	 */
	public void deleteDictionary(String dictionaryId);
	
	/**
	 * 사전별 조회이력 삭제
	 * @param dictionaryId
	 * @return
	 */	
	public void deleteReferenceByDictionaryId(String dictionaryId);
	
	/**
	 * 용어별 조회이력 삭제
	 * @param wordId
	 * @return
	 */		
	public void deleteReferenceByWordId(String wordId);
	
	/**
	 * 사전별 용어 삭제
	 * @param dictionaryId
	 * @return
	 */		
	public void deleteWordByDictionaryId(String dictionaryId);

	/**
	 * 용어사전검색
	 * @param dictionarySearchCondition
	 * @return
	 */
	public List<Dictionary> listDictionaryBySearchCondition(DictionarySearchCondition dictionarySearchCondition);
	
	/**
	 * 용어사전검색결과수
	 * @param dictionarySearchCondition
	 * @return
	 */	
	Integer countDictionaryBySearchCondition(DictionarySearchCondition dictionarySearchCondition); 

	/**
	 * 내가등록한용어,내가조회한용어 목록에서 보여주는 사전리스트
	 * @param dictionarySearchCondition
	 * @return
	 */		
	public List<Dictionary> selectDictionaryGroup(DictionarySearchCondition dictionarySearchCondition);
	
	
	/**
	 * 중복 단어 개수
	 * @param dictionarySearchCondition
	 * @return
	 */	
	Integer countDictionaryByWordName(DictionarySearchCondition dictionarySearchCondition); 
	
}
