package com.lgcns.ikep4.collpack.kms.perform.service;

import java.util.List;

import com.lgcns.ikep4.collpack.kms.perform.model.Performance;
import com.lgcns.ikep4.collpack.kms.perform.search.PerformanceSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;

public interface PerformanceService extends GenericService<Object, String> {

	SearchResult<Performance> listPrivate(PerformanceSearchCondition searchCondition);
	
	SearchResult<Performance> listPrivateStandby(PerformanceSearchCondition searchCondition);

	List<Performance> allListPrivate(PerformanceSearchCondition searchCondition);

	SearchResult<Performance> listPrivateStat(PerformanceSearchCondition searchCondition);
	
	List<Performance> allListPrivateStat(PerformanceSearchCondition searchCondition);

	SearchResult<Performance> listPrivateStatPerson(PerformanceSearchCondition searchCondition);
	
	SearchResult<Performance> listPerformance(PerformanceSearchCondition searchCondition);

	SearchResult<Performance> listTeamStat(PerformanceSearchCondition searchCondition);

	List<Performance> allListTeamStat(PerformanceSearchCondition searchCondition);

	SearchResult<Performance> listTeamStatPerson(PerformanceSearchCondition searchCondition);

	SearchResult<Performance> listExpert(PerformanceSearchCondition searchCondition);
	
	List<Performance> allListExpert(PerformanceSearchCondition searchCondition);
	
	Performance getPrivatePerformance(PerformanceSearchCondition searchCondition);

}
