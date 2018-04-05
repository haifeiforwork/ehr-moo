package com.lgcns.ikep4.collpack.kms.perform.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.kms.perform.dao.PerformanceDao;
import com.lgcns.ikep4.collpack.kms.perform.model.Performance;
import com.lgcns.ikep4.collpack.kms.perform.search.PerformanceSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.util.lang.StringUtil;

@Repository("PerformanceDao")
public class PerformanceDaoImpl extends GenericDaoSqlmap<Object, String> implements PerformanceDao {

	private static final String NAMESPACE = "collpack.kms.perform.dao.Performance.";
	
	public String create(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	public void update(Object arg0) {
		// TODO Auto-generated method stub

	}

	private boolean isTeam(PerformanceSearchCondition searchCondition) {
		String teamName = StringUtil.nvl(searchCondition.getTeamName(), "");
		int teamNameLength = teamName.length();
		boolean isIncludePart = false;	
		
		
		//팀단위로 조회시 하위 파트데이터도 포함한다.		
		if( teamName.indexOf("팀") > -1 && teamName.lastIndexOf("팀") == (teamNameLength-1)){
			isIncludePart = true;
		}
		
		System.out.println("##teamName :" + teamName + ", teamNameLen:" + teamNameLength +", isIncludePart:" + isIncludePart + ", indexOf(팀):" + teamName.indexOf("팀")+ ", lastIndexOf:" + teamName.lastIndexOf("팀") );
		return isIncludePart;
	}
	
	
	public List listPrivate(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		
		if(searchCondition.getIsPerson().equals("Y")){			
			
			if(isIncludePart)
				return this.sqlSelectForList(NAMESPACE + "listPrivateByIncludePart", searchCondition);
			else				
				return this.sqlSelectForList(NAMESPACE + "listPrivate", searchCondition);
			
		}else{
			if(isIncludePart)
				return this.sqlSelectForList(NAMESPACE + "listTeamByIncludePart", searchCondition);
			else
				return this.sqlSelectForList(NAMESPACE + "listTeam", searchCondition);
		}
	}
	
	public List listPrivateStandby(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		
		if(searchCondition.getIsPerson().equals("Y")){			
			
			if(isIncludePart)
				return this.sqlSelectForList(NAMESPACE + "listPrivateByIncludePartStandby", searchCondition);
			else				
				return this.sqlSelectForList(NAMESPACE + "listPrivateStandby", searchCondition);
			
		}else{
			if(isIncludePart)
				return this.sqlSelectForList(NAMESPACE + "listTeamByIncludePartStandby", searchCondition);
			else
				return this.sqlSelectForList(NAMESPACE + "listTeamStandby", searchCondition);
		}
	}

	public Integer countBySearchCondition(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		
		if(searchCondition.getIsPerson().equals("Y")){
			
			if(isIncludePart)
				return (Integer)this.sqlSelectForObject(NAMESPACE + "countByIncludePartSearchCondition", searchCondition);
			else
				return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
			
			
		}else{
			if(isIncludePart) 
				return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamByIncludePartBySearchCondition", searchCondition);
			else
				return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamBySearchCondition", searchCondition);
		}
	}
	
	public Integer countBySearchConditionStandby(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		
		if(searchCondition.getIsPerson().equals("Y")){
			
			if(isIncludePart)
				return (Integer)this.sqlSelectForObject(NAMESPACE + "countByIncludePartSearchConditionStandby", searchCondition);
			else
				return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchConditionStandby", searchCondition);
			
			
		}else{
			if(isIncludePart) 
				return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamByIncludePartBySearchConditionStandby", searchCondition);
			else
				return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamBySearchConditionStandby", searchCondition);
		}
	}

	public Integer countStatBySearchCondition(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		if(isIncludePart)
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countStatByIncludePartBySearchCondition", searchCondition);
		else
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countStatBySearchCondition", searchCondition);
	}
	
	public Integer countStatEctBySearchCondition(PerformanceSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countStatEctBySearchCondition", searchCondition);
	}

	public List listPrivateStat(PerformanceSearchCondition searchCondition) {		
		boolean isIncludePart = isTeam(searchCondition);
		if(isIncludePart)			
			return this.sqlSelectForList(NAMESPACE + "listPrivateStatByIncludePart", searchCondition);
		else
			return this.sqlSelectForList(NAMESPACE + "listPrivateStat", searchCondition);
	}
	
	public List listPrivateStatEct(PerformanceSearchCondition searchCondition) {		
		return this.sqlSelectForList(NAMESPACE + "listPrivateStatEct", searchCondition);
	}

	public List listPrivateStatPerson(PerformanceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listPrivateStatPerson", searchCondition);
	}
	
	public Performance listPerformance(PerformanceSearchCondition searchCondition) {
		return (Performance)this.sqlSelectForObject(NAMESPACE + "listPerformance", searchCondition);
	}
	
	public List listPerformanceGroup(PerformanceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listPerformanceGroup", searchCondition);
	}
	
	public List leftListPrivateStatPerson(PerformanceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "leftListPrivateStatPerson", searchCondition);
	}

	public List listLog(PerformanceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listLog", searchCondition);
	}

	public Integer countStatPersonBySearchCondition(PerformanceSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countStatPersonBySearchCondition", searchCondition);
	}

	public Integer countTeamStatBySearchCondition(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		if(isIncludePart)	
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamStatByIncludePartBySearchCondition", searchCondition);
		else
			return (Integer)this.sqlSelectForObject(NAMESPACE + "countTeamStatBySearchCondition", searchCondition);
	}
	
	public Integer countUserCnt(PerformanceSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countUserCnt", searchCondition);
	}
	
	public Integer countQnaRecommendCnt(PerformanceSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countQnaRecommendCnt", searchCondition);
	}
	
	public Integer countQnaReplyCnt(PerformanceSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countQnaReplyCnt", searchCondition);
	}
	
	public Integer countOblCnt(PerformanceSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countOblCnt", searchCondition);
	}

	public List listTeamStat(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		if(isIncludePart)	
			return this.sqlSelectForList(NAMESPACE + "listTeamStatByIncludePart", searchCondition);
		else
			return this.sqlSelectForList(NAMESPACE + "listTeamStat", searchCondition);
	}

	public List listTeamStatPerson(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		
		if(isIncludePart)			
			return this.sqlSelectForList(NAMESPACE + "listTeamStatPersonByIncludePart", searchCondition);
		else
			return this.sqlSelectForList(NAMESPACE + "listTeamStatPerson", searchCondition);
		
	}

	public List listTeamLog(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		
		if(isIncludePart)		
			return this.sqlSelectForList(NAMESPACE + "listTeamLogByIncludePart", searchCondition);
		else
			return this.sqlSelectForList(NAMESPACE + "listTeamLog", searchCondition);
	}

	public List listExpert(PerformanceSearchCondition searchCondition) {
		try {
			
			return this.sqlSelectForList(NAMESPACE + "listExpert", searchCondition);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer countExpertBySearchCondition(PerformanceSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countExpertBySearchCondition", searchCondition);
	}

	public Integer countByIncludePartSearchCondition(PerformanceSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countByIncludePartSearchCondition", searchCondition);		
	}

	public List listPrivateByIncludePart(PerformanceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listPrivateByIncludePart", searchCondition);
	}

	public Object getTeamMemCntAndObligationCnt(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		
		if(isIncludePart)	
			return this.sqlSelectForObject(NAMESPACE + "getTeamMemCntAndObligationCntByIncludePart", searchCondition);
		else
			return this.sqlSelectForObject(NAMESPACE + "getTeamMemCntAndObligationCnt", searchCondition);
	}

	public List listLineReply(PerformanceSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "listLineReply", searchCondition);
	}

	public List listTeamLineReply(PerformanceSearchCondition searchCondition) {
		boolean isIncludePart = isTeam(searchCondition);
		
		if(isIncludePart)		
			return this.sqlSelectForList(NAMESPACE + "listTeamLineReplyByIncludePart", searchCondition);
		else
			return this.sqlSelectForList(NAMESPACE + "listTeamLineReply", searchCondition);
	}

	
}
