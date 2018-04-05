package com.lgcns.ikep4.collpack.kms.perform.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.Collator;
import com.lgcns.ikep4.collpack.kms.admin.dao.AdminPermissionDao;
import com.lgcns.ikep4.collpack.kms.admin.dao.AdminWinnerManageDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminPermission;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.collpack.kms.perform.dao.PerformanceDao;
import com.lgcns.ikep4.collpack.kms.perform.model.Performance;
import com.lgcns.ikep4.collpack.kms.perform.search.PerformanceSearchCondition;
import com.lgcns.ikep4.collpack.kms.perform.service.PerformanceService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.excel.StringUtils;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

import edu.emory.mathcs.backport.java.util.Collections;

@Service("PerformanceService")
public class PerformanceServiceImpl extends GenericServiceImpl<Object, String> implements PerformanceService {
	
	
	@Autowired
	PerformanceDao performanceDao;
	
	@Autowired
	AdminWinnerManageDao adminWinnerManageDao;
	
	@Autowired
	AdminPermissionDao adminPermissionDao;
	
	public String create(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object read(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Object arg0) {
		// TODO Auto-generated method stub

	}

	
	public SearchResult<Performance> listPrivate(PerformanceSearchCondition searchCondition) {		
		
		
		Integer count = performanceDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<Performance> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<Performance>(searchCondition);
		}else{
			List<Performance> listPrivate = performanceDao.listPrivate(searchCondition);
			searchResult = new SearchResult<Performance>(listPrivate, searchCondition);
		}
		
		return searchResult;
	}
	
	public SearchResult<Performance> listPrivateStandby(PerformanceSearchCondition searchCondition) {		
		
		
		Integer count = performanceDao.countBySearchConditionStandby(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<Performance> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<Performance>(searchCondition);
		}else{
			List<Performance> listPrivate = performanceDao.listPrivateStandby(searchCondition);
			searchResult = new SearchResult<Performance>(listPrivate, searchCondition);
		}
		
		return searchResult;
	}
	
	
	
	

	public List<Performance> allListPrivate(PerformanceSearchCondition searchCondition) {
		
		Integer count = performanceDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		searchCondition.setEndRowIndex(count);
		
		List<Performance> listPrivate = performanceDao.listPrivate(searchCondition);
		return listPrivate;	
		
	}

	
	public SearchResult<Performance> listPrivateStat(PerformanceSearchCondition searchCondition) {
		
		try{
		int nowYear = Integer.parseInt(DateUtil.getToday("yyyy"));
		int nowMonth = Integer.parseInt(DateUtil.getToday("MM"));
		int searchYear = Integer.parseInt(StringUtil.nvl(searchCondition.getSearchYear(), String.valueOf(nowYear)));
		int searchMonth = Integer.parseInt(StringUtil.nvl(searchCondition.getSearchMonth(), String.valueOf(nowMonth)));
		if(searchYear > nowYear || (searchYear + searchMonth) > (nowYear + nowMonth) ) {
			searchCondition.terminateSearchCondition(0);
			return new SearchResult<Performance>(searchCondition);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		Integer count = 0;
		if(searchCondition.getWorkPlaceName() == null){
			count = performanceDao.countStatBySearchCondition(searchCondition);
		}else{
			if(searchCondition.getWorkPlaceName().equals("ectGrp")){
				count = performanceDao.countStatEctBySearchCondition(searchCondition);
			}else{
				count = performanceDao.countStatBySearchCondition(searchCondition);
			}
		}
		
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<Performance> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<Performance>(searchCondition);
		}else{
			List<Performance> listPrivateStat = new ArrayList<Performance>();
			if(searchCondition.getWorkPlaceName() == null){
				listPrivateStat = performanceDao.listPrivateStat(searchCondition);
			}else{
				if(searchCondition.getWorkPlaceName().equals("ectGrp")){
					listPrivateStat = performanceDao.listPrivateStatEct(searchCondition);
				}else{
					listPrivateStat = performanceDao.listPrivateStat(searchCondition);
				}
			}
			
			searchResult = new SearchResult<Performance>(listPrivateStat, searchCondition);
		}
		
		return searchResult;
	}
	
	public List<Performance> allListPrivateStat(PerformanceSearchCondition searchCondition) {
		
		int nowYear = Integer.parseInt(DateUtil.getToday("yyyy"));
		int nowMonth = Integer.parseInt(DateUtil.getToday("MM"));
		int searchYear = Integer.parseInt(StringUtil.nvl(searchCondition.getSearchYear(), String.valueOf(nowYear)));
		int searchMonth = Integer.parseInt(StringUtil.nvl(searchCondition.getSearchMonth(), String.valueOf(nowMonth)));
		if(searchYear > nowYear || (searchYear + searchMonth) > (nowYear + nowMonth) ) {			
			return new ArrayList<Performance>();
		}
		
		
		Integer count = performanceDao.countStatBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		searchCondition.setEndRowIndex(count);
		
		List<Performance> listPrivate = performanceDao.listPrivateStat(searchCondition);
		return listPrivate;
	}
	
	public Performance getPrivatePerformance(PerformanceSearchCondition searchCondition){
		
		Performance resultPerformance = new Performance();
		resultPerformance.setUserId(searchCondition.getUserId());
		
		try{
			SearchResult<Performance> searchResult = leftListPrivateStatPerson(searchCondition);
			List<Performance> list = searchResult.getEntity();
			
			double regCnt = 0;
			double regSum = 0;
			double markSum = 0;
			double scoreSum = 0;
			//double conversionMark = 0;
			
			for(Performance performance : list){
				/*if(performance.getRegCnt()!=null){
					if(regCnt > 0){
						regCnt += Double.parseDouble(performance.getRegCnt());
					}else{
						regCnt = Double.parseDouble(performance.getRegCnt());
					}
				}
				if(performance.getRegSum()!=null){
					if(regSum > 0){
						regSum += Double.parseDouble(performance.getRegSum());
					}else{
						regSum = Double.parseDouble(performance.getRegSum());
					}
				}*/
				
				if(performance.getRegCnt()!=null){
					regCnt = Double.parseDouble(performance.getRegCnt());
				}
				if(performance.getRegSum()!=null){
					if(Integer.parseInt(performance.getRegSum()) > 0){
						regSum = Double.parseDouble(performance.getRegSum());
					}else{
						regSum += Double.parseDouble(performance.getRegSum());
					}
				}
				
				if(performance.getScoreSum()!=null){
					if(Integer.parseInt(performance.getScoreSum()) > 0){
						scoreSum = Double.parseDouble(performance.getScoreSum());
					}else{
						scoreSum += Double.parseDouble(performance.getScoreSum());
					}
				}
				
				if(performance.getMarkSum()!=null){
					if(Integer.parseInt(performance.getMarkSum()) > 0){
						markSum = Double.parseDouble(performance.getMarkSum());
					}else{
						markSum += Double.parseDouble(performance.getMarkSum());
					}
				}
				
				
				//conversionMark += Double.parseDouble(performance.getConversionMark());
				
			}		
			int tmpRegCnt = 0;
			int tmpRegSum = 0;
			tmpRegCnt = performanceDao.countQnaReplyCnt(searchCondition);
			tmpRegSum = performanceDao.countQnaRecommendCnt(searchCondition);
			if(tmpRegCnt > regCnt){
				resultPerformance.setRegCnt(String.valueOf(regCnt+tmpRegCnt));
			}else{
				resultPerformance.setRegCnt(String.valueOf(regCnt));
			}
			
			if(tmpRegSum > regSum){
				resultPerformance.setRegSum(String.valueOf(regSum+tmpRegSum));
			}else{
				resultPerformance.setRegSum(String.valueOf(regSum));
			}
			
			
			resultPerformance.setMarkSum(String.valueOf(markSum));
			resultPerformance.setScoreSum(String.valueOf(scoreSum));
			resultPerformance.setConversionMark("0");
					
		}catch (Exception e) {
			e.printStackTrace();
			resultPerformance.setRegCnt("0");
			resultPerformance.setRegSum("0");
			resultPerformance.setMarkSum("0");
			resultPerformance.setScoreSum("0");
			resultPerformance.setConversionMark("0");
		}
		return resultPerformance;
	}

	public SearchResult<Performance> listPrivateStatPerson(PerformanceSearchCondition searchCondition) {
		
		SearchResult<Performance> searchResult = null;
		List<Performance> listPrivateStat = null;
		
		try {			
			if(searchCondition.getFromLeft() == null)
				searchCondition.setSearchMonth(null);
			listPrivateStat = performanceDao.listPrivateStatPerson(searchCondition);
		} catch (Exception e) {
			listPrivateStat = null;
		}
		
		
		String searchYear = searchCondition.getSearchYear();
		
		String obligationCnt = null;
		String registerName = null;
		String workPlaceName = null;
		String teamName = null;
		String userName = null;
		
		 
		if(listPrivateStat == null || listPrivateStat.size() == 0){
			KmsAdminSearchCondition searchCondition1 = new KmsAdminSearchCondition();
			searchResult = new SearchResult<Performance>(searchCondition);	
			String thisYear = DateUtil.getToday("yyyy").toString();
			String thisMonth = DateUtil.getToday("MM").toString();
			searchCondition1.setSyear(thisYear);
			searchCondition1.setSmonth(thisMonth);
			searchCondition1.setUserId(searchCondition.getUserId());
			AdminPermission adminPermission = adminPermissionDao.getUserPermInfo(searchCondition1);
			
			obligationCnt = adminPermission.getObligationCnt();
			registerName = adminPermission.getUserName();
			workPlaceName = adminPermission.getWorkPlaceName();
			teamName = adminPermission.getTeamName();
			userName = adminPermission.getUserName();
			
			//개인별 연간실적 통계에서 타인 조회시
			if(!StringUtils.isNull(searchCondition.getRegisterName()) && !(searchCondition.getUserName().equals(searchCondition.getRegisterName()))){								
				return searchResult;
			}
			
			
		}else{
			
			
			obligationCnt = ((Performance)listPrivateStat.get(0)).getObligationSum();
			registerName = ((Performance)listPrivateStat.get(0)).getRegisterName();
			workPlaceName = ((Performance)listPrivateStat.get(0)).getWorkPlaceName();
			teamName = ((Performance)listPrivateStat.get(0)).getTeamName();
			userName = ((Performance)listPrivateStat.get(0)).getUserName();
			
		}
		
		
		teamName = StringUtil.nvl(teamName, searchCondition.getTeamName());
			
		searchCondition.terminateSearchCondition(12);
		searchCondition.setPageIndex(1);
		searchCondition.setPageCount(1);
			
				
		List<HashMap<String, Object>> listLog = performanceDao.listLog(searchCondition);
		List<HashMap<String, String>> listLineReply = performanceDao.listLineReply(searchCondition);
		Map<String, BigDecimal> assessMap = (Map<String, BigDecimal>)adminWinnerManageDao.getAssessStandard();
		List<Performance> completeListPrivateStat = new ArrayList<Performance>();
		HashMap<String, String> fillData = new HashMap<String, String>();
		
		fillData.put("obligationCnt", obligationCnt);
		fillData.put("registerName", registerName);
		fillData.put("workPlaceName", workPlaceName);
		fillData.put("teamName", teamName);
		fillData.put("userName", userName);
		
		//좌측메뉴의 월간활동건수
		if(StringUtil.nvl(searchCondition.getFromLeft(), "N").equals("Y") && !StringUtil.isEmpty(searchCondition.getSearchMonth())){
			
			if(listPrivateStat !=null && listPrivateStat.size() > 0){				
				completeListPrivateStat = listPrivateStat;
			}
			
		}else{
			
			makePerformanceForAYearPerson(listPrivateStat, searchYear, fillData, false, listLog,
					assessMap, completeListPrivateStat, listLineReply, searchCondition);				
		}
		
		
		searchResult = new SearchResult<Performance>(completeListPrivateStat, searchCondition);
		
		return searchResult;
	}
	
	public SearchResult<Performance> listPerformance(PerformanceSearchCondition searchCondition) {
		
		SearchResult<Performance> searchResult = null;
		List<Performance> listPrivateStat = new ArrayList<Performance>();
		List<Performance> listGroup = null;
		
		listGroup = performanceDao.listPerformanceGroup(searchCondition);
		for(Performance performanceGroup : listGroup){
			Performance tempPerformance = new Performance();
			searchCondition.setTeamCode(performanceGroup.getGroupId());
			searchCondition.setTeamName(performanceGroup.getTeamName());
			searchCondition.setCategory(performanceGroup.getCategory());
			tempPerformance = performanceDao.listPerformance(searchCondition);
			listPrivateStat.add(tempPerformance); 
		}
		
		
		searchResult = new SearchResult<Performance>(listPrivateStat, searchCondition);
		
		return searchResult;
	}
	
	public SearchResult<Performance> leftListPrivateStatPerson(PerformanceSearchCondition searchCondition) {
		
		SearchResult<Performance> searchResult = null;
		List<Performance> listPrivateStat = null;
		
		try {			
			if(searchCondition.getFromLeft() == null)
				searchCondition.setSearchMonth(null);
			listPrivateStat = performanceDao.leftListPrivateStatPerson(searchCondition);
		} catch (Exception e) {
			listPrivateStat = null;
		}
		
		
		String searchYear = searchCondition.getSearchYear();
		
		String obligationCnt = null;
		String registerName = null;
		String workPlaceName = null;
		String teamName = null;
		String userName = null;
		
		 
		if(listPrivateStat == null || listPrivateStat.size() == 0){
			KmsAdminSearchCondition searchCondition1 = new KmsAdminSearchCondition();
			searchResult = new SearchResult<Performance>(searchCondition);	
			String thisYear = DateUtil.getToday("yyyy").toString();
			String thisMonth = DateUtil.getToday("MM").toString();
			searchCondition1.setSyear(thisYear);
			searchCondition1.setSmonth(thisMonth);
			searchCondition1.setUserId(searchCondition.getUserId());
			AdminPermission adminPermission = adminPermissionDao.getUserPermInfo(searchCondition1);
			
			obligationCnt = adminPermission.getObligationCnt();
			registerName = adminPermission.getUserName();
			workPlaceName = adminPermission.getWorkPlaceName();
			teamName = adminPermission.getTeamName();
			userName = adminPermission.getUserName();
			
			//개인별 연간실적 통계에서 타인 조회시
			if(!StringUtils.isNull(searchCondition.getRegisterName()) && !(searchCondition.getUserName().equals(searchCondition.getRegisterName()))){								
				return searchResult;
			}
			
			
		}else{
			
			
			obligationCnt = ((Performance)listPrivateStat.get(0)).getObligationSum();
			registerName = ((Performance)listPrivateStat.get(0)).getRegisterName();
			workPlaceName = ((Performance)listPrivateStat.get(0)).getWorkPlaceName();
			teamName = ((Performance)listPrivateStat.get(0)).getTeamName();
			userName = ((Performance)listPrivateStat.get(0)).getUserName();
			
		}
		
		
		teamName = StringUtil.nvl(teamName, searchCondition.getTeamName());
			
		searchCondition.terminateSearchCondition(12);
		searchCondition.setPageIndex(1);
		searchCondition.setPageCount(1);
			
				
		List<HashMap<String, Object>> listLog = performanceDao.listLog(searchCondition);
		List<HashMap<String, String>> listLineReply = performanceDao.listLineReply(searchCondition);
		Map<String, BigDecimal> assessMap = (Map<String, BigDecimal>)adminWinnerManageDao.getAssessStandard();
		List<Performance> completeListPrivateStat = new ArrayList<Performance>();
		HashMap<String, String> fillData = new HashMap<String, String>();
		
		fillData.put("obligationCnt", obligationCnt);
		fillData.put("registerName", registerName);
		fillData.put("workPlaceName", workPlaceName);
		fillData.put("teamName", teamName);
		fillData.put("userName", userName);
		
		//좌측메뉴의 월간활동건수
		if(StringUtil.nvl(searchCondition.getFromLeft(), "N").equals("Y") && !StringUtil.isEmpty(searchCondition.getSearchMonth())){
			
			if(listPrivateStat !=null && listPrivateStat.size() > 0){				
				completeListPrivateStat = listPrivateStat;
			}
			
		}else{
			
			makePerformanceForAYear(listPrivateStat, searchYear, fillData, false, listLog,
					assessMap, completeListPrivateStat, listLineReply, searchCondition);				
		}
		
		
		searchResult = new SearchResult<Performance>(completeListPrivateStat, searchCondition);
		
		return searchResult;
	}

	private Performance setConversionMark(Map<String, BigDecimal> assessMap, Performance completePerformance) {
		double conversionMark = 0;
		double tot_mark = getAssessRate(assessMap, "markRate", completePerformance.getMarkSum());			
		double tot_reg =  getAssessRate(assessMap, "regCntRate", completePerformance.getRegSum());
		double tot_hit =  getAssessRate(assessMap, "viewCntRate", completePerformance.getHitSum());
		double tot_recommend =  getAssessRate(assessMap, "recommendCntRate", completePerformance.getRecommendSum());
		double tot_linereply =  getAssessRate(assessMap, "lineReplyCntRate", completePerformance.getLineReplySum());
		double tot_usage =  getAssessRate(assessMap, "usageCntRate", completePerformance.getUsageSum());
		conversionMark = Math.round(tot_mark + tot_reg + tot_hit + tot_recommend + tot_linereply + tot_usage);
		completePerformance.setConversionMark(String.valueOf(conversionMark));

		return completePerformance;
	}

	private double getAssessRate(Map<String, BigDecimal> assessMap, String assessMapKey, String data) {
		BigDecimal assessRate = assessMap.get(assessMapKey);
		double assessRateDbl = new Double(assessRate.toString()).doubleValue()/100;			
		double tot_data = Double.parseDouble(makeNotNull(data)) * assessRateDbl;
		return tot_data;
	}
	
	private String makeNotNull(String data){
		return StringUtil.nvl(data, "0");
	}

	private Performance setUsageSum(List<HashMap<String, Object>> listLog, Performance completePerformance, String registDate) {
		String keyRegistDate = null;
		
		for(HashMap<String, Object> logMap : listLog){		
			
			keyRegistDate = (String)logMap.get("REG_DATE");
			if(registDate.equals(keyRegistDate)){
				BigDecimal assessRate = (BigDecimal)logMap.get("USAGE_SUM");
				completePerformance.setUsageSum(assessRate.toString());			
			}
		}
		return completePerformance;
	}

	private Performance setCompletePerformance(Performance completePerformance, String registDate) {
		completePerformance.setRegistDate(registDate);		
		completePerformance.setRegSum(StringUtil.nvl(completePerformance.getRegSum(),"0"));
		completePerformance.setScoreSum(StringUtil.nvl(completePerformance.getScoreSum(),"0"));
		completePerformance.setObligationSum(StringUtil.nvl(completePerformance.getObligationSum(),"0"));
		completePerformance.setObligationRegRate(StringUtil.nvl(completePerformance.getObligationRegRate(),"0%"));
		completePerformance.setMarkSum(StringUtil.nvl(completePerformance.getMarkSum(),"0"));
		completePerformance.setHitSum(StringUtil.nvl(completePerformance.getHitSum(),"0"));
		completePerformance.setRecommendSum(StringUtil.nvl(completePerformance.getRecommendSum(),"0"));
		completePerformance.setLineReplySum(StringUtil.nvl(completePerformance.getLineReplySum(),"0"));
				
		return completePerformance;
	}

	private String getMakeRegistDate(String searchYear, int i) {

		String searchMonth = "0" + i;
		searchMonth = searchMonth.substring(searchMonth.length()-2, searchMonth.length());
		
		if(i == 12){
			return Integer.parseInt(searchYear)-1 + searchMonth;
		}
		return searchYear + searchMonth;
		
	}

	public SearchResult<Performance> listTeamStat(PerformanceSearchCondition searchCondition) {
		try{
			int nowYear = Integer.parseInt(DateUtil.getToday("yyyy"));
			int nowMonth = Integer.parseInt(DateUtil.getToday("MM"));
			int searchYear = Integer.parseInt(StringUtil.nvl(searchCondition.getSearchYear(), String.valueOf(nowYear)));
			int searchMonth = Integer.parseInt(StringUtil.nvl(searchCondition.getSearchMonth(), String.valueOf(nowMonth)));
			if(searchYear > nowYear || (searchYear + searchMonth) > (nowYear + nowMonth) ) {
				searchCondition.terminateSearchCondition(0);
				return new SearchResult<Performance>(searchCondition);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			Integer count = performanceDao.countTeamStatBySearchCondition(searchCondition);
			searchCondition.terminateSearchCondition(count);
			
			SearchResult<Performance> searchResult = null;
			
			if(searchCondition.isEmptyRecord()){
				searchResult = new SearchResult<Performance>(searchCondition);
			}else{
				List<Performance> listTeamStat = performanceDao.listTeamStat(searchCondition);
				searchResult = new SearchResult<Performance>(listTeamStat, searchCondition);
			}
			
			return searchResult;
	}

	public List<Performance> allListTeamStat(PerformanceSearchCondition searchCondition) {
		int nowYear = Integer.parseInt(DateUtil.getToday("yyyy"));
		int nowMonth = Integer.parseInt(DateUtil.getToday("MM"));
		int searchYear = Integer.parseInt(StringUtil.nvl(searchCondition.getSearchYear(), String.valueOf(nowYear)));
		int searchMonth = Integer.parseInt(StringUtil.nvl(searchCondition.getSearchMonth(), String.valueOf(nowMonth)));
		if(searchYear > nowYear || (searchYear + searchMonth) > (nowYear + nowMonth) ) {			
			return new ArrayList<Performance>();
		}
		
		
		Integer count = performanceDao.countTeamStatBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		searchCondition.setEndRowIndex(count);
		List<Performance> listTeamStat = performanceDao.listTeamStat(searchCondition);
		return listTeamStat;
	}

	public SearchResult<Performance> listTeamStatPerson(PerformanceSearchCondition searchCondition) {
		SearchResult<Performance> searchResult = null;
		List<Performance> listTeamStatPerson = null;
		
		try {			
			searchCondition.setSearchMonth(null);
			listTeamStatPerson = performanceDao.listTeamStatPerson(searchCondition);
		} catch (Exception e) {
			listTeamStatPerson = null;
		}
		
		String obligationCnt = null;
		String teamName = null;
		String memberCnt = null;
		
		try {
			
			if(listTeamStatPerson == null || listTeamStatPerson.size() == 0){
				searchResult = new SearchResult<Performance>(searchCondition);
				HashMap<String, String> teamInfo = (HashMap<String, String>)performanceDao.getTeamMemCntAndObligationCnt(searchCondition);
				obligationCnt = teamInfo.get("OBLIGATION_SUM");
				memberCnt = teamInfo.get("MEMBER_CNT");
				teamName = searchCondition.getTeamName();
			}else{
				obligationCnt = ((Performance)listTeamStatPerson.get(0)).getObligationSum();
				teamName = ((Performance)listTeamStatPerson.get(0)).getTeamName();
				memberCnt = ((Performance)listTeamStatPerson.get(0)).getMemberCnt();
			}
			
			teamName = StringUtil.nvl(teamName, searchCondition.getTeamName());
				
			searchCondition.terminateSearchCondition(12);
			searchCondition.setPageIndex(1);
			searchCondition.setPageCount(1);
			
			String searchYear = searchCondition.getSearchYear();
			
			List<HashMap<String, Object>> listTeamLog = performanceDao.listTeamLog(searchCondition);
			List<HashMap<String, String>> listTeamLineReply = performanceDao.listTeamLineReply(searchCondition);
			Map<String, BigDecimal> assessMap = (Map<String, BigDecimal>)adminWinnerManageDao.getAssessStandard();
			List<Performance> completeListTeamStat = new ArrayList<Performance>();
			
			HashMap<String, String> fillData = new HashMap<String, String>();
			fillData.put("obligationCnt", obligationCnt);
			fillData.put("teamName", teamName);
			fillData.put("memberCnt", memberCnt);
			
			makePerformanceForAYearPerson(listTeamStatPerson, searchYear, fillData, true, listTeamLog,
					assessMap, completeListTeamStat, listTeamLineReply, searchCondition);
			
			searchResult = new SearchResult<Performance>(completeListTeamStat, searchCondition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return searchResult;
	}

	private void makePerformanceForAYear(List<Performance> listStat, String searchYear,
			HashMap<String, String> fillData, boolean isTeamStat,
			List<HashMap<String, Object>> listLog,
			Map<String, BigDecimal> assessMap, List<Performance> completeListStat, List<HashMap<String, String>> listLineReply,
			PerformanceSearchCondition searchCondition2) {
		try {
			Performance completePerformance;
			String registDate;
			int userCnt = 0;
			int oblCnt = 0; 
			int tempRegCnt = 0;
			int tempRegSum = 0;
			PerformanceSearchCondition searchCondition1 = new PerformanceSearchCondition();
			DecimalFormat format = new DecimalFormat("#.#");
			for(int i=1; i<=12; i++){
				completePerformance = new Performance();
				
				registDate = getMakeRegistDate(searchYear, i);
				if( listStat != null ){
					for(Performance dbPerformance : listStat){
						
						if(registDate.equals(dbPerformance.getRegistDate())){
							completePerformance = dbPerformance;
						}	
						//searchCondition1.setSearchYear(registDate);
						//if(dbPerformance.getGroupId() == null){
						//	searchCondition1.setUserId(dbPerformance.getUserId());
						//}else{
						//	searchCondition1.setTeamCode(dbPerformance.getGroupId());
						//}
						//userCnt = performanceDao.countUserCnt(searchCondition1);
						//oblCnt = performanceDao.countOblCnt(searchCondition1);
					}
				}
				/*searchCondition1.setSearchYear(registDate);
				searchCondition1.setTeamCode(searchCondition2.getTeamCode());
				searchCondition1.setUserId(searchCondition2.getUserId());
				
				if(!isTeamStat){
					searchCondition1.setTeamCode("");
				}
				userCnt = performanceDao.countUserCnt(searchCondition1);
				oblCnt = performanceDao.countOblCnt(searchCondition1);
				int tmpRegCnt = performanceDao.countQnaReplyCnt(searchCondition1);
				if(completePerformance.getRegCnt()==null){
					tempRegCnt = tmpRegCnt;
					completePerformance.setRegCnt(Integer.toString(tempRegCnt));
				}else{
					tempRegCnt = Integer.parseInt(completePerformance.getRegCnt())+tmpRegCnt;
					completePerformance.setRegCnt(Integer.toString(tempRegCnt));
				}
				
				
				int tmpRegSum = performanceDao.countQnaRecommendCnt(searchCondition1);
				if(completePerformance.getRegSum()==null){
					tempRegSum = tmpRegSum;
					completePerformance.setRegSum(Integer.toString(tempRegSum));
				}else{
					tempRegSum = Integer.parseInt(completePerformance.getRegSum())+tmpRegSum;
					completePerformance.setRegSum(Integer.toString(tempRegSum));
				}
				
				
				completePerformance = setCompletePerformance(completePerformance, registDate);				
				
				if(isTeamStat){				
					completePerformance.setMemberCnt(Integer.toString(userCnt));
				}else{
					completePerformance.setRegisterName(fillData.get("registerName"));
					completePerformance.setWorkPlaceName(fillData.get("workPlaceName"));				
					completePerformance.setUserName(fillData.get("userName"));
				}
				
				if(Double.parseDouble(StringUtil.nvl(completePerformance.getRegSum(), "0")) == 0 || (StringUtil.nvl(completePerformance.getRegSum(), "0").equals("0"))){
					completePerformance.setObligationRegRate("0%");
				}else{
					completePerformance.setObligationRegRate(format.format(Double.parseDouble(StringUtil.nvl(completePerformance.getRegSum(), "0"))/Double.parseDouble(StringUtil.nvl(Integer.toString(oblCnt), "0"))*100)+"%");
				}
				completePerformance.setTeamName(fillData.get("teamName"));
				completePerformance.setObligationSum(StringUtil.nvl(Integer.toString(oblCnt), "0"));
				completePerformance.setRowNum(String.valueOf(i));
				completePerformance = setUsageSum(listLog, completePerformance, registDate);
				completePerformance.setUsageSum(StringUtil.nvl(completePerformance.getUsageSum(), "0"));
				completePerformance = setLineReplySum(listLineReply, completePerformance, registDate);
				completePerformance.setLineReplySum(StringUtil.nvl(completePerformance.getLineReplySum(), "0"));
				completePerformance = setConversionMark(assessMap, completePerformance);
				completeListStat.add(completePerformance);	*/	
				
				searchCondition1.setSearchYear(registDate);
				searchCondition1.setTeamCode(searchCondition2.getTeamCode());
				searchCondition1.setUserId(searchCondition2.getUserId());
				if(!isTeamStat){
					searchCondition1.setTeamCode("");
				}
				userCnt = performanceDao.countUserCnt(searchCondition1);
				oblCnt = performanceDao.countOblCnt(searchCondition1);
				
				completePerformance = setCompletePerformance(completePerformance, registDate);				
				
				if(isTeamStat){				
					completePerformance.setMemberCnt(Integer.toString(userCnt));
				}else{
					completePerformance.setRegisterName(fillData.get("registerName"));
					completePerformance.setWorkPlaceName(fillData.get("workPlaceName"));				
					completePerformance.setUserName(fillData.get("userName"));
				}
				
				if(Double.parseDouble(StringUtil.nvl(completePerformance.getRegSum(), "0")) == 0 || (StringUtil.nvl(completePerformance.getRegSum(), "0").equals("0"))){
					completePerformance.setObligationRegRate("0%");
				}else{
					completePerformance.setObligationRegRate(format.format(Double.parseDouble(StringUtil.nvl(completePerformance.getRegSum(), "0"))/Double.parseDouble(StringUtil.nvl(Integer.toString(oblCnt), "0"))*100)+"%");
				}
				
				completePerformance.setTeamName(fillData.get("teamName"));
				completePerformance.setObligationSum(StringUtil.nvl(Integer.toString(oblCnt), "0"));
				completePerformance.setRowNum(String.valueOf(i));
				completePerformance = setUsageSum(listLog, completePerformance, registDate);
				completePerformance.setUsageSum(StringUtil.nvl(completePerformance.getUsageSum(), "0"));
				completePerformance = setLineReplySum(listLineReply, completePerformance, registDate);
				completePerformance.setLineReplySum(StringUtil.nvl(completePerformance.getLineReplySum(), "0"));
				completePerformance = setConversionMark(assessMap, completePerformance);
				completeListStat.add(completePerformance);	
				
			}	
			
			Collections.sort(completeListStat, comparator);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void makePerformanceForAYearPerson(List<Performance> listStat, String searchYear,
			HashMap<String, String> fillData, boolean isTeamStat,
			List<HashMap<String, Object>> listLog,
			Map<String, BigDecimal> assessMap, List<Performance> completeListStat, List<HashMap<String, String>> listLineReply,
			PerformanceSearchCondition searchCondition2) {
		try {
			Performance completePerformance;
			String registDate;
			int userCnt = 0;
			int oblCnt = 0; 
			int tempRegCnt = 0;
			int tempRegSum = 0;
			PerformanceSearchCondition searchCondition1 = new PerformanceSearchCondition();
			DecimalFormat format = new DecimalFormat("#.#");
			for(int i=1; i<=12; i++){
				completePerformance = new Performance();
				
				registDate = getMakeRegistDate(searchYear, i);
				if( listStat != null ){
					for(Performance dbPerformance : listStat){
						
						if(registDate.equals(dbPerformance.getRegistDate())){
							completePerformance = dbPerformance;
						}	
						//searchCondition1.setSearchYear(registDate);
						//if(dbPerformance.getGroupId() == null){
						//	searchCondition1.setUserId(dbPerformance.getUserId());
						//}else{
						//	searchCondition1.setTeamCode(dbPerformance.getGroupId());
						//}
						//userCnt = performanceDao.countUserCnt(searchCondition1);
						//oblCnt = performanceDao.countOblCnt(searchCondition1);
					}
				}
				searchCondition1.setSearchYear(registDate);
				searchCondition1.setTeamCode(searchCondition2.getTeamCode());
				searchCondition1.setUserId(searchCondition2.getUserId());
				
				if(!isTeamStat){
					searchCondition1.setTeamCode("");
				}
				userCnt = performanceDao.countUserCnt(searchCondition1);
				oblCnt = performanceDao.countOblCnt(searchCondition1);
				int tmpRegCnt = performanceDao.countQnaReplyCnt(searchCondition1);
				if(completePerformance.getRegCnt()==null){
					tempRegCnt = tmpRegCnt;
					completePerformance.setRegCnt(Integer.toString(tempRegCnt));
				}else{
					tempRegCnt = Integer.parseInt(completePerformance.getRegCnt())+tmpRegCnt;
					completePerformance.setRegCnt(Integer.toString(tempRegCnt));
				}
				
				
				int tmpRegSum = performanceDao.countQnaRecommendCnt(searchCondition1);
				if(completePerformance.getRegSum()==null){
					tempRegSum = tmpRegSum;
					completePerformance.setRegSum(Integer.toString(tempRegSum));
				}else{
					tempRegSum = Integer.parseInt(completePerformance.getRegSum())+tmpRegSum;
					completePerformance.setRegSum(Integer.toString(tempRegSum));
				}
				
				
				completePerformance = setCompletePerformance(completePerformance, registDate);				
				
				if(isTeamStat){				
					completePerformance.setMemberCnt(Integer.toString(userCnt));
				}else{
					completePerformance.setRegisterName(fillData.get("registerName"));
					completePerformance.setWorkPlaceName(fillData.get("workPlaceName"));				
					completePerformance.setUserName(fillData.get("userName"));
				}
				
				if(Double.parseDouble(StringUtil.nvl(completePerformance.getRegSum(), "0")) == 0 || (StringUtil.nvl(completePerformance.getRegSum(), "0").equals("0"))){
					completePerformance.setObligationRegRate("0%");
				}else{
					completePerformance.setObligationRegRate(format.format(Double.parseDouble(StringUtil.nvl(completePerformance.getRegSum(), "0"))/Double.parseDouble(StringUtil.nvl(Integer.toString(oblCnt), "0"))*100)+"%");
				}
				completePerformance.setTeamName(fillData.get("teamName"));
				completePerformance.setObligationSum(StringUtil.nvl(Integer.toString(oblCnt), "0"));
				completePerformance.setRowNum(String.valueOf(i));
				completePerformance = setUsageSum(listLog, completePerformance, registDate);
				completePerformance.setUsageSum(StringUtil.nvl(completePerformance.getUsageSum(), "0"));
				completePerformance = setLineReplySum(listLineReply, completePerformance, registDate);
				completePerformance.setLineReplySum(StringUtil.nvl(completePerformance.getLineReplySum(), "0"));
				completePerformance = setConversionMark(assessMap, completePerformance);
				completeListStat.add(completePerformance);		
				
				
			}	
			
			Collections.sort(completeListStat, comparator);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	private final static Comparator<Performance> comparator = new Comparator<Performance>(){
		private final Collator collator = Collator.getInstance();
		
		public int compare(Performance obj1, Performance obj2){
			return collator.compare(obj1.getRegistDate(), obj2.getRegistDate());
		}
	};

	private Performance setLineReplySum(List<HashMap<String, String>> listLineReply, Performance completePerformance,
			String registDate) {
		String keyRegistDate = null;
		
		for(HashMap<String, String> logMap : listLineReply){		
			
			keyRegistDate = (String)logMap.get("REG_DATE");
			if(registDate.equals(keyRegistDate)){
				completePerformance.setLineReplySum(logMap.get("LINEREPLY_SUM"));			
			}
		}
		return completePerformance;
	}

	public SearchResult<Performance> listExpert(PerformanceSearchCondition searchCondition) {
		
		Integer count = performanceDao.countExpertBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<Performance> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<Performance>(searchCondition);
		}else{
			List<Performance> listExpert = performanceDao.listExpert(searchCondition);
			searchResult = new SearchResult<Performance>(listExpert, searchCondition);
		}
		
		return searchResult;
	}

	public List<Performance> allListExpert(PerformanceSearchCondition searchCondition) {
		Integer count = performanceDao.countExpertBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		searchCondition.setEndRowIndex(count);
		
		List<Performance> listPrivate = performanceDao.listExpert(searchCondition);
		return listPrivate;	
	}

}
