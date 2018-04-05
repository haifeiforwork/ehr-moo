/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.dictionary.constants.DictionaryConstants;
import com.lgcns.ikep4.collpack.dictionary.dao.DictionaryDao;
import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.*;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: DictionaryDaoImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Repository("KmsDictionaryDao")
public class DictionaryDaoImpl extends GenericDaoSqlmap<Dictionary, String> implements DictionaryDao {

	
	/**
	 * 용어 목록
	 */	
	public List<Dictionary> listDictionaryBySearchCondition(DictionarySearchCondition dictionarySearchCondition) {
		return sqlSelectForList("dictionary.selectAll", dictionarySearchCondition);
	}
	
	/**
	 * 용어 목록수
	 */		
	public Integer countDictionaryBySearchCondition(DictionarySearchCondition dictionarySearchCondition) {
		return (Integer)sqlSelectForObject("dictionary.countDictionaryBySearchCondition", dictionarySearchCondition);
	}
	
	/**
	 * 사전 목록
	 */		
	public List<Dictionary> selectDictionaryGroup(DictionarySearchCondition dictionarySearchCondition) {
		return sqlSelectForList("dictionary.selectDictionaryGroup", dictionarySearchCondition);
	}
	
	/**
	 * 사전 조회 정보
	 */		
	public List<Dictionary> listMyDictionaryBySearchCondition(DictionarySearchCondition dictionarySearchCondition) {
		return sqlSelectForList("dictionary.selectHit", dictionarySearchCondition);
	}
		
	public List<Dictionary> selectTop(Dictionary dictionary) {
		return sqlSelectForList("dictionary.selectTop", dictionary);
	}
	
	public List<Dictionary> selectNew(Dictionary dictionary) {
		return sqlSelectForList("dictionary.selectNew", dictionary);
	}	

	/**
	 * 용어 등록
	 */		
	public String create(Dictionary dictionary) {	
		return (String) sqlInsert("dictionary.insert", dictionary);
	}

	/**
	 * 용어 조회정보 등록
	 */	
	public String insertHit(Dictionary dictionary) {
		return (String) sqlInsert("dictionary.insertHit", dictionary);
	}

	/**
	 * 사전 등록
	 */		
	public String insertDictionary(Dictionary dictionary) {
		return (String) sqlInsert("dictionary.insertDictionary", dictionary);
	}
	
	public boolean exists(String id) {
		return false;
	}
	
	public Dictionary get(String wordId) {
		return (Dictionary) sqlSelectForObject("dictionary.select", wordId);
	}

	/**
	 * 사전 정보 가져오기
	 */		
	public Dictionary selectDictionary(String dictionaryId) {
		return (Dictionary) sqlSelectForObject("dictionary.selectDictionary", dictionaryId);
	}

	/**
	 * 사전 목록(메인화면)
	 */		
	public List<Dictionary> selectDictionarys() {
		return (List) sqlSelectForListOfObject("dictionary.selectDictionarys");
	}
	
	/**
	 * 사전 목록(메인화면)
	 */		
	public List<Dictionary> selectDictionarysByCondition(DictionarySearchCondition dictionarySearchCondition) {
		return (List) sqlSelectForListOfObject("dictionary.selectDictionarys",dictionarySearchCondition);
	}	
		
	/**
	 * 용어 이력 목록
	 */		
	public List<Dictionary> selectWordHistoryList(String wordGroupId) {
		return (List) sqlSelectForListOfObject("dictionary.selectWordHistoryList", wordGroupId);
	}	
	
	/**
	 * 용어 조회 여부
	 */		
	public Integer checkAlreadyRead(Dictionary dictionary) {
		return (Integer) sqlSelectForObject("dictionary.selectAlreadyRead", dictionary);
	}
	
	/**
	 * 용어 버전정보(입력된 데이터 중 최신정보)
	 */		
	public String selectOriginVersion(Dictionary dictionary) {
		String version = (String) sqlSelectForObject("dictionary.selectVersion", dictionary);
		version = Double.toString(Double.parseDouble(version));
		return version;
	}
	
	/**
	 * 용어 버전정보(새롭게 입력할 정보)
	 */		
	public String selectVersion(Dictionary dictionary) {
		String version = (String) sqlSelectForObject("dictionary.selectVersion", dictionary);
		Double dVersion = 1.0;
		if ( "0.0".equals(version) ) {
			version = "1.0";
		} else {
			dVersion = Double.parseDouble(version);
			dVersion = dVersion + DictionaryConstants.DICTIONARY_VERSION_INCREASE;
			version = Double.toString(dVersion);
		}
		return version;
	}

	/**
	 * 최근 등록자 ID
	 */
	public String selectRecentInputRegisterId(String wordGroupId) {
		return (String) sqlSelectForObject("dictionary.selectRecentInputRegisterId", wordGroupId);
	}
	
	/**
	 * 화면에서 디폴트로 셋팅할 사전
	 */	
	public String selectDictionaryId(DictionarySearchCondition dictionarySearchCondition) {
		return (String) sqlSelectForObject("dictionary.selectDictionaryId",dictionarySearchCondition);
	}
	
	
	/**
	 * 중복 단어 검사
	 */		
	public Integer countDictionaryByWordName(DictionarySearchCondition dictionarySearchCondition) {
		return (Integer)sqlSelectForObject("dictionary.countDictionaryByWordName", dictionarySearchCondition);
	}
	
	/**
	 * 용어 삭제
	 */		
	public void remove(String wordId) {
		sqlDelete("dictionary.delete", wordId);
	}
	
	/**
	 * 사전 삭제
	 */	
	public void deleteDictionary(String dictionaryId) {
		sqlDelete("dictionary.deleteDictionary", dictionaryId);
	}	
	
	/**
	 * 사전에 속한 모든 용어의 조회정보 삭제
	 */		
	public void deleteReferenceByDictionaryId(String dictionaryId) {
		sqlDelete("dictionary.deleteReferenceByDictionaryId", dictionaryId);
	}
	
	/**
	 * 용어의 조회정보 삭제
	 */		
	public void deleteReferenceByWordId(String wordId) {
		sqlDelete("dictionary.deleteReferenceByWordId", wordId);
	}	
	
	/**
	 * 사전에 속한 모든 용어 삭제
	 */		
	public void deleteWordByDictionaryId(String dictionaryId) {
		sqlDelete("dictionary.deleteWordByDictionaryId", dictionaryId);
	}	

	/**
	 * 용어 갱신
	 */	
	public void update(Dictionary dictionary) {
		sqlUpdate("dictionary.update", dictionary);
	}

	/**
	 * 사전명 갱신
	 */		
	public void updateDictionaryName(Dictionary dictionary) {
		sqlUpdate("dictionary.updateDictionaryName", dictionary);
	}

	/**
	 * 사전 순서 갱신
	 */		
	public void updateDictionarySortOrder(Dictionary dictionary) {
		sqlUpdate("dictionary.updateDictionarySortOrder", dictionary);
	}
	
	/**
	 * 용어 조회정보 갱신
	 */	
	public void updateHit(String profileId) {
		sqlUpdate("dictionary.updateHit", profileId);
	}
	
	/**
	 * 용어 조회일자 갱신
	 */		
	public void updateHitDate(Dictionary dictionary) {
		sqlUpdate("dictionary.updateHitDate", dictionary);
	}		
	
}
