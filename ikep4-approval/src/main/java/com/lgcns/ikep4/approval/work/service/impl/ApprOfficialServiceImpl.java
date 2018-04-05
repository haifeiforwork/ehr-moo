package com.lgcns.ikep4.approval.work.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.approval.work.dao.ApprOfficialDao;
import com.lgcns.ikep4.approval.work.model.ApprOfficial;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprOfficialService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 공문 시행 서비스
 * 
 * @author handul
 * @version $Id$
 */
@Service
public class ApprOfficialServiceImpl extends GenericServiceImpl<ApprOfficial, String> implements ApprOfficialService {

	@Autowired
	private ApprOfficialDao apprOfficialDao;

	@Autowired
	private IdgenService idgenService;

	public String create(ApprOfficial apprOfficial) {

		apprOfficial.setOfficialId(idgenService.getNextId());
		apprOfficialDao.create(apprOfficial);

		return apprOfficial.getOfficialId();
	}

	public boolean exists(String signId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(ApprOfficial apprOfficial) {

		apprOfficialDao.update(apprOfficial);

	}

	public void delete(String officialId) {
		apprOfficialDao.remove(officialId);
	}

	public ApprOfficial read(String officialId) {
		return (ApprOfficial) apprOfficialDao.get(officialId);
	}

	public SearchResult<ApprOfficial> list(ApprListSearchCondition searchCondition) {

		Integer count = apprOfficialDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<ApprOfficial> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprOfficial>(searchCondition);
		} else {
			List<ApprOfficial> apprList = (List<ApprOfficial>) apprOfficialDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<ApprOfficial>(apprList, searchCondition);
		}

		return searchResult;
	}

	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

}
