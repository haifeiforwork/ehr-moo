/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.dictionary.constants.DictionaryConstants;
import com.lgcns.ikep4.collpack.dictionary.dao.DictionaryDao;
import com.lgcns.ikep4.collpack.dictionary.model.Dictionary;
import com.lgcns.ikep4.collpack.dictionary.search.DictionarySearchCondition;
import com.lgcns.ikep4.collpack.dictionary.service.DictionaryService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: DictionaryServiceImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Service("KmsDictionaryService")
public class DictionaryServiceImpl extends GenericServiceImpl<Dictionary, String> implements DictionaryService {
	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Autowired
	private IdgenService idgenService;

	@Autowired
	private ActivityStreamService activityStreamService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 용어 등록
	 */	
	public String create(Dictionary dictionary,User user) {
		String activityCode = IKepConstant.ACTIVITY_CODE_DOC_POST;
		String tagName = "";
		String wordId = dictionary.getWordId();
		
		//신규
		if (dictionary.getWordId() == null || "".equals(dictionary.getWordId())) {
			dictionary.setWordGroupId(idgenService.getNextId());
			dictionary.setWordId(idgenService.getNextId());	
			activityCode = IKepConstant.ACTIVITY_CODE_DOC_EDIT;
		} else { //수정이력적용
			dictionary.setWordId(idgenService.getNextId());	
			//이력적용일 경우 태그적용
			if ( "apply".equals(dictionary.getMode()) ) {
				List<Tag> tagList = tagService.listTagByItemId(wordId,  TagConstants.ITEM_TYPE_CONPORATE_VOCA);
				Tag tag = new Tag();
				for (int i=0; i<tagList.size();i++) {
					tag = tagList.get(i);
					tagName = tagName + tag.getTagName();
					if ( i < (tagList.size()-1) ) {
						tagName += ",";
					}
				}
				dictionary.setTag(tagName);
				dictionary.setUpdateReason(dictionary.getVersion()+"버전으로 적용되었습니다."); //이력적용일 경우 수정사유 적용
			}			
		}

		
		String version = dictionaryDao.selectVersion(dictionary);
		dictionary.setVersion(Double.parseDouble(version));		
				
		dictionaryDao.create(dictionary);
		
		//태그 등록	- 태그 있을때 등록
		if(!StringUtil.isEmpty(dictionary.getTag())){
			Tag tag = new Tag();
			
			tag.setTagName(dictionary.getTag());								//사용자가 작성한 tag
			tag.setTagItemId(dictionary.getWordId());							//게시물 ID
			tag.setTagItemType(TagConstants.ITEM_TYPE_CONPORATE_VOCA);			//모듈 타입 TagConstants에 정의되어 있음.
			tag.setTagItemSubType(dictionary.getWordGroupId());					//모듈 서브 타입  - 있을때만 넣기
			tag.setTagItemName(dictionary.getWordName());						//게시물 제목
			tag.setTagItemContents(dictionary.getContents());  					//게시물 내용
			tag.setTagItemUrl("/collpack/dictionary/getDictionary.do?wordId="+dictionary.getWordId());	//게시물 팝업창 url
			tag.setRegisterId(dictionary.getRegisterId());
			tag.setRegisterName(dictionary.getRegisterName());
			tag.setPortalId(dictionary.getPortalId());
			
			tagService.create(tag);
		}	
		
	    this.fileService.saveFileLinkForEditor(dictionary.getEditorFileLinkList(), dictionary.getWordId(), Dictionary.ITEM_TYPE, user);
		// Activity Stream 추가 
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_VOCABULARY, activityCode, dictionary.getRegisterId(), dictionary.getWordName());	
		
		return dictionary.getWordId();
	}

	/**
	 * 사전 등록
	 */		
	public String insertDictionary(Dictionary dictionary) {
		String dictionaryId = idgenService.getNextId();	
		dictionary.setDictionaryId(dictionaryId);
		
		dictionaryDao.insertDictionary(dictionary);
		
		return dictionaryId;
	}

	/**
	 * 용어 정보 가져오기
	 */	
	public Dictionary read(String wordId) {
		Dictionary dictionary = dictionaryDao.get(wordId);
				
		return dictionary;
	}

	/**
	 * 사전 정보 가져오기
	 */	
	public Dictionary selectDictionary(String dictionaryId) {
		Dictionary dictionary = dictionaryDao.selectDictionary(dictionaryId);
		
		return dictionary;
	}	
	
	/**
	 * 사전 목록 가져오기(메인화면)
	 */		
	public List<Dictionary> selectDictionarys() {
		List<Dictionary> dictionary = dictionaryDao.selectDictionarys();
		
		return dictionary;
	}
	
	/**
	 * 사전 목록 가져오기(메인화면)
	 */	
	public List<Dictionary> selectDictionarysByCondition(DictionarySearchCondition dictionarySearchCondition) {
		List<Dictionary> dictionary = dictionaryDao.selectDictionarysByCondition(dictionarySearchCondition);
		
		return dictionary;
	}	
	
	/**
	 * 용어 이력 목록 가져오기
	 */	
	public List<Dictionary> selectWordHistoryList(String wordGroupId) {
		List<Dictionary> dictionary = dictionaryDao.selectWordHistoryList(wordGroupId);
		
		return dictionary;
	}
	
	/**
	 * 메인화면에서 디폴트로 셋팅할 사전 
	 */	
	public String selectDictionaryId(DictionarySearchCondition dictionarySearchCondition) {	
		return dictionaryDao.selectDictionaryId(dictionarySearchCondition);
	}	

	/**
	 * 용어 상세정보
	 */		
	public Dictionary readDetail(Dictionary dictionaryParam) {
		int checkCnt = dictionaryDao.checkAlreadyRead(dictionaryParam);
		String wordId = dictionaryParam.getWordId();
		String registerId = dictionaryParam.getRegisterId();
		String referenceId;
		
		Dictionary dictionary = new Dictionary();
		dictionary = dictionaryDao.get(wordId);
		String recentVersion = dictionaryDao.selectOriginVersion(dictionary);
		String curVersion = Double.toString(dictionary.getVersion());


		//최신 버전만 조회수 관련 데이터 갱신
		if ( recentVersion.equals(curVersion) ) {
			//처음 조회한 경우		
			if (checkCnt == 0) {
				dictionaryDao.updateHit(wordId);
				referenceId = idgenService.getNextId();
				dictionary.setReferenceId(referenceId);
				dictionary.setRegisterId(registerId);
				dictionaryDao.insertHit(dictionary);						
				dictionary.setHitCount(dictionary.getHitCount()+1);
			} else {
				//로긴한 userID
				dictionary.setViewId(registerId);			
				dictionaryDao.updateHitDate(dictionary);
			}
		}

		//최근 입력자 ID
		String recentInputRegisterId = dictionaryDao.selectRecentInputRegisterId(dictionary.getWordGroupId());
		dictionary.setRecentInputRegisterId(recentInputRegisterId);
		
		//최근 버전 정보
		dictionary.setRecentVersion(recentVersion);
		return dictionary;
	}	

	/**
	 * 용어 삭제
	 */		
	public void delete(String wordId) {
		// Activity Stream 추가 
		Dictionary dictionary = dictionaryDao.get(wordId);
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_VOCABULARY, IKepConstant.ACTIVITY_CODE_DOC_DELETE, dictionary.getRegisterId(), dictionary.getWordName());
				
		dictionaryDao.deleteReferenceByWordId(wordId);
		dictionaryDao.remove(wordId);
		tagService.delete(wordId, TagConstants.ITEM_TYPE_CONPORATE_VOCA);
		fileService.removeItemFile(wordId);
	}

	/**
	 * 사전 삭제
	 */		
	public void deleteDictionary(String dictionaryId) {
		// Activity Stream 추가 
		Dictionary dictionary = dictionaryDao.selectDictionary(dictionaryId);
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_VOCABULARY, IKepConstant.ACTIVITY_CODE_DOC_DELETE, dictionary.getRegisterId(), dictionary.getDictionaryName());
		
		dictionaryDao.deleteReferenceByDictionaryId(dictionaryId);
		dictionaryDao.deleteWordByDictionaryId(dictionaryId);
		dictionaryDao.deleteDictionary(dictionaryId);
	}	

	/**
	 * 용어 갱신
	 */		
	public void update(Dictionary dictionary) {
		dictionaryDao.update(dictionary);
	}	
	
	/**
	 * 사전명 갱신
	 */		
	public void updateDictionaryName(Dictionary dictionary) {
		dictionaryDao.updateDictionaryName(dictionary);
	}	
	
	/**
	 * 사전정렬순서 갱신
	 */		
	public void updateDictionarySortOrder(String dictionaryIdes) {
		Dictionary dictionary = new Dictionary();
		String[] dictionaryIdList = dictionaryIdes.split(",");
		
		for(int i=0, count = dictionaryIdList.length; i < count; i++){
			String dictionaryId = dictionaryIdList[i];
			dictionary.setDictionaryId(dictionaryId);
			dictionary.setSortOrder(Integer.toString(i+1));
			dictionaryDao.updateDictionarySortOrder(dictionary);
		}
		
	}	
	
	/**
	 * 용어 목록
	 */	
	public SearchResult<Dictionary> listDictionaryBySearchCondition(DictionarySearchCondition searchCondition) {
		//가~나 버튼을 클릭한 경우가 아닌 경우 검색조건 변경
		if ( "-1".equals(searchCondition.getDictionarySortIndex()) || "".equals(searchCondition.getDictionarySortIndex()) || searchCondition.getDictionarySortIndex() == null ) {
			searchCondition.setStartSortChar("");
			searchCondition.setEndSortChar("");
		//인덱스버튼을 클릭한 경우
		} else {
			searchCondition.setSearchColumn("");
			searchCondition.setSearchWord("");
		}
		Integer count = this.dictionaryDao.countDictionaryBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<Dictionary> searchResult = new SearchResult<Dictionary>(searchCondition);
		List<Tag> tagList = null; 
		
		if(!searchCondition.isEmptyRecord()) {
			List<Dictionary> dictionaryItemList = new ArrayList<Dictionary>();
			if ( "tag".equals(searchCondition.getSearchColumn())) {
			     Tag tag = new Tag();
			     tag.setPortalId(searchCondition.getPortalId());              //포탈 ID
			     tag.setTagItemType(TagConstants.ITEM_TYPE_CONPORATE_VOCA);      //모듈 타입 TagConstants에 정의되어 있음.-team coll은 type ID를 넣으셔야 합니다. - 없으면 않넣으면 됨.
			     tag.setTagName(searchCondition.getSearchWord());
			     tag.setGroupType("tagItemSubType");
				 tag.setPageIndex(1);
				 tag.setPagePer(DictionaryConstants.DICTIONARY_TAG_PAGE_PER_RECORD);				     
			     
			     //Map<String, Object> itemIdMap = tagService.listItemAsCount(tag, searchCondition.getPageIndex(), searchCondition.getPagePerRecord());
			     Map<String, Object> itemIdMap = tagService.listItemId(tag);
			     
			     count = (Integer)itemIdMap.get("count");
				 searchCondition.terminateSearchCondition(count);  
				
			     List<String> tmpTagList = (List<String>)itemIdMap.get("list");
				 for (int i=0; i<tmpTagList.size();i++) {
					 dictionaryItemList.add(read(tmpTagList.get(i)));
				 }			    
			} else {
				dictionaryItemList = getDictionaryItemList(searchCondition);
				
			}
			
			if ( (!"myInputList".equals(searchCondition.getMode()) && !"myViewList".equals(searchCondition.getMode())) || "Y".equals(searchCondition.getIsMore())) {
				for(Dictionary dictionary : dictionaryItemList) {
					tagList = tagService.listTagByItemId(dictionary.getWordId(),  TagConstants.ITEM_TYPE_CONPORATE_VOCA);
					dictionary.setTagList(tagList); 
				}		
			}
			
			searchResult = new SearchResult<Dictionary>(dictionaryItemList, searchCondition); 		
		}  
		
		return searchResult;
	}
	
	/**
	 * 용어 목록수
	 */		
	public List<Dictionary> getDictionaryItemList(DictionarySearchCondition searchCondition) {
		List<Dictionary> dictionaryItemListResult = new ArrayList<Dictionary>();
		
		if ( !"myInputList".equals(searchCondition.getMode()) && !"myViewList".equals(searchCondition.getMode()) ) {
			dictionaryItemListResult = this.dictionaryDao.listDictionaryBySearchCondition(searchCondition);
		} else { //myInputList,myViewList
			if ( !"Y".equals(searchCondition.getIsMore()) ) { //get 5
				dictionaryItemListResult = this.dictionaryDao.listDictionaryBySearchCondition(searchCondition);
			} else { //isMore
				List<Dictionary> dictionaryGroup = dictionaryDao.selectDictionaryGroup(searchCondition); 
				for (int i=0; i<dictionaryGroup.size();i++) {
					searchCondition.setDictionaryId(dictionaryGroup.get(i).getDictionaryId());
					List<Dictionary> dictionaryGroupList = dictionaryDao.listDictionaryBySearchCondition(searchCondition);
					for (int j=0; j<dictionaryGroupList.size();j++) {
						dictionaryItemListResult.add(dictionaryGroupList.get(j));
					}
				}
			}
			
		}		
		
		return dictionaryItemListResult;
	}

	public boolean isDuplicateByWordName(DictionarySearchCondition dictionarySearchCondition) {
		
		int count = dictionaryDao.countDictionaryByWordName(dictionarySearchCondition);
		
		boolean result = false;
		
		if(count > 0){
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}
	
	
	
	
}
