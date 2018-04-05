package com.lgcns.ikep4.approval.work.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.work.model.ApprOfficial;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;


/**
 * 공문 시행 서비스
 * 
 * @author 유승목
 * @version $Id$
 */
@Transactional
public interface ApprOfficialService extends GenericService<ApprOfficial, String> {

	/**
	 * 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<ApprOfficial> list(ApprListSearchCondition searchCondition);

}
