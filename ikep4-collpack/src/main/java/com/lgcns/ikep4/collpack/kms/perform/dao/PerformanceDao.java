package com.lgcns.ikep4.collpack.kms.perform.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.lgcns.ikep4.collpack.kms.perform.model.Performance;
import com.lgcns.ikep4.collpack.kms.perform.search.PerformanceSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface PerformanceDao extends GenericDao<Object, String> {

	List listPrivate(PerformanceSearchCondition searchCondition);
	
	List listPrivateStandby(PerformanceSearchCondition searchCondition);

	Integer countBySearchCondition(PerformanceSearchCondition searchCondition);
	
	Integer countBySearchConditionStandby(PerformanceSearchCondition searchCondition);

	Integer countStatBySearchCondition(PerformanceSearchCondition searchCondition);
	
	Integer countStatEctBySearchCondition(PerformanceSearchCondition searchCondition);

	List<Performance> listPrivateStat(PerformanceSearchCondition searchCondition);
	
	List<Performance> listPrivateStatEct(PerformanceSearchCondition searchCondition);

	List<Performance> listPrivateStatPerson(PerformanceSearchCondition searchCondition);	
	
	Performance listPerformance(PerformanceSearchCondition searchCondition);
	
	List<Performance> listPerformanceGroup(PerformanceSearchCondition searchCondition);
	
	List<Performance> leftListPrivateStatPerson(PerformanceSearchCondition searchCondition);

	List<HashMap<String, Object>> listLog(PerformanceSearchCondition searchCondition);
	
	List<HashMap<String, String>> listLineReply(PerformanceSearchCondition searchCondition);

	Integer countStatPersonBySearchCondition(PerformanceSearchCondition searchCondition);

	Integer countTeamStatBySearchCondition(PerformanceSearchCondition searchCondition);
	
	Integer countUserCnt(PerformanceSearchCondition searchCondition);
	
	Integer countQnaRecommendCnt(PerformanceSearchCondition searchCondition);
	
	Integer countQnaReplyCnt(PerformanceSearchCondition searchCondition);
	
	Integer countOblCnt(PerformanceSearchCondition searchCondition);

	List<Performance> listTeamStat(PerformanceSearchCondition searchCondition);

	List<Performance> listTeamStatPerson(PerformanceSearchCondition searchCondition);

	List<HashMap<String, Object>> listTeamLog(PerformanceSearchCondition searchCondition);
	
	List<HashMap<String, String>> listTeamLineReply(PerformanceSearchCondition searchCondition);

	List<Performance> listExpert(PerformanceSearchCondition searchCondition);

	Integer countExpertBySearchCondition(PerformanceSearchCondition searchCondition);

	Integer countByIncludePartSearchCondition(PerformanceSearchCondition searchCondition);

	List<Performance> listPrivateByIncludePart(PerformanceSearchCondition searchCondition);

	Object getTeamMemCntAndObligationCnt(PerformanceSearchCondition searchCondition);

}
