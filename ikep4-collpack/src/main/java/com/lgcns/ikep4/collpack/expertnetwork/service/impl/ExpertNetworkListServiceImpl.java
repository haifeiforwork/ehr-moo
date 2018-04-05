/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkCategoryDao;
import com.lgcns.ikep4.collpack.expertnetwork.dao.ExpertNetworkListDao;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkListPK;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkBlockPageCondition;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * Expert Network ExpertListService implementation
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkListServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service
@Transactional
public class ExpertNetworkListServiceImpl extends GenericServiceImpl<ExpertNetworkList, ExpertNetworkListPK> implements ExpertNetworkListService {

	private ExpertNetworkListDao expertNetworkListDao;

	@Autowired
	public ExpertNetworkListServiceImpl(ExpertNetworkListDao dao) {
		super(dao);
		this.expertNetworkListDao = dao;
	}

	@Autowired
	private ExpertNetworkCategoryDao expertNetworkCategoryDao;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService#countByCategoryId(java.lang.String)
	 */
	public int countByCategoryId(String categoryId) {
		return expertNetworkListDao.countByCategoryId(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService#listByCategoryIdPage(com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkBlockPageCondition)
	 */
	public List<ExpertNetworkList> listByCategoryIdPage(ExpertNetworkBlockPageCondition pageCondition) {
		return expertNetworkListDao.listByCategoryIdPage(pageCondition);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService#deleteByCategoryId(java.lang.String)
	 */
	public void deleteByCategoryId(String categoryId) {
		expertNetworkListDao.removeByCategoryId(categoryId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService#listRandom(int, java.lang.String)
	 */
	public List<ExpertNetworkList> listRandom(int categoryCount, String portalId) {
		// 카테고리 목록
		List<ExpertNetworkCategory> categoryItemList = expertNetworkCategoryDao.listRandomCategoryByCount(categoryCount, portalId);

		List<ExpertNetworkList> expertItemList = new ArrayList<ExpertNetworkList>();

		for (ExpertNetworkCategory item : categoryItemList) {
			ExpertNetworkList expertItem = expertNetworkListDao.getByCategoryIdRandom(item.getCategoryId());
			if (null != expertItem) {
				expertItemList.add(expertItem);
			}
		}

		return expertItemList;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService#createOrUpdateExpertList(com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList)
	 */
	public void createOrUpdateExpertList(ExpertNetworkList expertNetworkList) {
		if (exists(expertNetworkList)) {
			update(expertNetworkList);
		} else {
			create(expertNetworkList);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService#batchGatherExpert()
	 */
	public void batchGatherExpert() {
		// 일반 전문가 삭제
		expertNetworkListDao.removeByAuthorized();
		// 재계산된 전문가 등록
		expertNetworkListDao.batchGatherExpert();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService#updateAuthorized(com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList)
	 */
	public void updateAuthorized(ExpertNetworkList expertNetworkList) {
		expertNetworkListDao.updateAuthorized(expertNetworkList);
	}

}
