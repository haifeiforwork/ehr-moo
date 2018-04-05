/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.workmanual.dao.ManualDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ReferenceDao;
import com.lgcns.ikep4.collpack.workmanual.model.Manual;
import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.model.Reference;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.ManualService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service 
@Transactional
public class ManualServiceImpl extends GenericServiceImpl<Manual, String> implements ManualService {
	@Autowired
	private ManualDao manualDao;
	@Autowired
	private ReferenceDao referenceDao;
	
	@Autowired
	private TagService tagService;

	@Deprecated
	public String create(Manual manual) {
		return manualDao.create(manual);
	}

	@Deprecated
	public boolean exists(String manualId) {
		return manualDao.exists(manualId);
	}

	@Deprecated
	public Manual read(String manualId) {
		return manualDao.get(manualId);
	}

	@Deprecated
	public void delete(String manualId) {
		manualDao.remove(manualId);
	}

	@Deprecated
	public void update(Manual manual) {
		manualDao.update(manual);
	}
	////////////////////////////////////

	//카테고리별 목록 조회
	public SearchResult<Manual> listCategoryManual(ManualSearchCondition manualSearchCondition) {
		Integer count = manualDao.countCategoryManual(manualSearchCondition);
		manualSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<Manual> searchResult = null; 
		if(manualSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Manual>(manualSearchCondition);
		} else {
			List<Manual> categoryManualList = manualDao.listCategoryManual(manualSearchCondition); 
			for(int i=0; i<categoryManualList.size(); i++) {
				List<Tag> tagList = tagService.listTagByItemId(categoryManualList.get(i).getManualId(), TagConstants.ITEM_TYPE_WORK_MANUAL);
				categoryManualList.get(i).setTagList(tagList);
			}
			searchResult = new SearchResult<Manual>(categoryManualList, manualSearchCondition);  			
		}  
		
		return searchResult;
	}
	//업무매뉴얼 메인 화면 조회 개수
	public Integer countManual(String portalId) {
		return (Integer) manualDao.countManual(portalId);
	}
	//업무매뉴얼 메인 화면 조회
	public List<Manual> listManual(String portalId, Integer endRowNum) {
		Manual manual = new Manual();
		manual.setPortalId(portalId);
		manual.setIndexRowNum(endRowNum);
		List<Manual> manualList = manualDao.listManual(manual);
		for(int i=0; i<manualList.size(); i++) {
			List<Tag> tagList = tagService.listTagByItemId(manualList.get(i).getManualId(), TagConstants.ITEM_TYPE_WORK_MANUAL);
			manualList.get(i).setTagList(tagList);
		}
		
		return manualList;
	}	
	//업무매뉴얼  조회
	public Manual readManual(String manualId, String portalId, String userId) {
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualId);
		manualPk.setPortalId(portalId);
		
		Reference reference = new Reference();
		reference.setManualId(manualId);
		reference.setRegisterId(userId);
		if(referenceDao.countShowToday(reference) == 0) {
			if(referenceDao.exists(reference)) {
				referenceDao.update(reference);
			} else {
				referenceDao.create(reference);
			}
			manualDao.updateHitCount(manualPk);
		}

		Manual manual = manualDao.getManual(manualPk);
		List<Tag> tagList = tagService.listTagByItemId(manual.getManualId(), TagConstants.ITEM_TYPE_WORK_MANUAL);
		manual.setTagList(tagList);

		return manual;
	}
	//인기 태그 조회
	public List<Tag> listPopularTag(String portalId) {
		Tag tag = new Tag();
		tag.setTagItemType(TagConstants.ITEM_TYPE_WORK_MANUAL);		//itemType TagConstants에서 모듈에 맞게 사용
		//tag.setTagItemSubType(QnaConstants.ITEM_SUB_TYPE_QNA);      //Item Sub Type 있을시만 넣어줌
		//tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);	//정렬순 TagConstants에 정의되어 있음. - 넣지않으면 인기순으로 가져옴.
		tag.setPortalId(portalId);			//portalID
		tag.setEndRowIndex(5);					//태그 가져올 개수            
		return tagService.listTagByItemType(tag);
	}
}
