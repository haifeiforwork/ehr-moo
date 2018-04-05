/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkTaggingDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkTagging;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkTaggingPK;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkTaggingService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.base.constant.CommonConstant;

/**
 * Expert Network ExpertTaggingService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkTaggingServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class ExpertNetworkTaggingServiceImpl extends GenericServiceImpl<ExpertNetworkTagging, ExpertNetworkTaggingPK> implements ExpertNetworkTaggingService {

	private ExpertNetworkTaggingDao expertNetworkTaggingDao;

	@Autowired
	public ExpertNetworkTaggingServiceImpl(ExpertNetworkTaggingDao dao) {
		super(dao);
		this.expertNetworkTaggingDao = dao;
	}

	/**
	 * categoryId 당 최고 등록가능한  Tag 개수
	 */
	private static final int MAX_TAG_COUNT = 10;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkTaggingService#listByCategoryId(java.lang.String)
	 */
	public List<ExpertNetworkTagging> listByCategoryId(String categoryId) {
		return expertNetworkTaggingDao.listByCategoryId(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkTaggingService#createTags(java.lang.String, java.lang.String)
	 */
	public int createTags(String categoryId, String tags) {
		// Tag 정보가 없을때
		if (null == tags) { return 0; }

		ExpertNetworkTagging tagging = new ExpertNetworkTagging();
		tagging.setCategoryId(categoryId);

		String[] aryTags = tags.split(CommonConstant.COMMA_SEPARATER);
		int sortOrder = 0;

		for (String tag : aryTags) {
			// 빈공간(값이 없으면) skip
			if ("".equals(tag.trim())) { continue; }

			sortOrder += 1;
			tagging.setSortOrder(sortOrder);
			tagging.setTag(tag);
			create(tagging);
			
			// 최고 10건까지만 등록가능
			if (MAX_TAG_COUNT <= sortOrder) { break; }
		}

		return sortOrder;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkTaggingService#getTagsByCategoryId(java.lang.String)
	 */
	public String getTagsByCategoryId(String categoryId) {
		List<ExpertNetworkTagging> taggingList = expertNetworkTaggingDao.listByCategoryId(categoryId);

		// Tag들을 (,)로 분리된 String 으로 변환
		StringBuffer sb;
		if (1 > taggingList.size()) {
			return "";
		} else {
			sb = new StringBuffer();
			sb.append(taggingList.get(0).getTag());

			for (int i = 1; i < taggingList.size(); i++) {
				sb.append(CommonConstant.COMMA_SEPARATER);
				sb.append(taggingList.get(i).getTag());
			}
		}

		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkTaggingService#deleteByCategoryId(java.lang.String)
	 */
	public int deleteByCategoryId(String categoryId) {
		return expertNetworkTaggingDao.removeByCategoryId(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkTaggingService#deleteByCategoryIdHierarchy(java.lang.String)
	 */
	public int deleteByCategoryIdHierarchy(String categoryId) {
		return expertNetworkTaggingDao.removeByCategoryIdHierarchy(categoryId);
	}

}
