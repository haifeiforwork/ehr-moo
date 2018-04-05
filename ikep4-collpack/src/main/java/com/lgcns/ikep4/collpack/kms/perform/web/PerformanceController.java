package com.lgcns.ikep4.collpack.kms.perform.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceCode;
import com.lgcns.ikep4.collpack.kms.admin.dao.AdminPermissionDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminTeamLeader;
import com.lgcns.ikep4.collpack.kms.admin.model.Board;
import com.lgcns.ikep4.collpack.kms.admin.search.KmsAdminSearchCondition;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminPermissionService;
import com.lgcns.ikep4.collpack.kms.perform.dao.PerformanceDao;
import com.lgcns.ikep4.collpack.kms.perform.model.Performance;
import com.lgcns.ikep4.collpack.kms.perform.search.PerformanceSearchCondition;
import com.lgcns.ikep4.collpack.kms.perform.service.PerformanceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.excel.ExcelModelConstants;
import com.lgcns.ikep4.util.excel.ExcelViewModel;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.messenger.AtMessengerCommunicator;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
/**
 * Performance Controller
 * 
 * @author 정애란(tseliot@lgcns.com)
 * @version 
 */

@Controller
@RequestMapping(value = "/collpack/kms/perform")
public class PerformanceController extends BaseController {
	
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private PerformanceService performanceService;
	
	@Autowired
	private PerformanceDao performanceDao;
	
	@Autowired
	private UserDao userDao;
	
	
	
	@Autowired
	private AdminPermissionService adminPermissionService;

	@Autowired
	private AdminPermissionDao adminPermissionDao;
	
	private Properties prop = null;		
	private String serverIp = null;
	private String serverPort = null;
	
	
	
	/**
	 * 세션에서 로그인 사용자 모델 객체를 조회한다.
	 * 
	 * @return 세션에 저장되어 있는 사용자 모델 객체
	 */
	private User readUser() {
		
		return (User) this.getRequestAttribute("ikep.user");
	}
	
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin() {
		User user = this.readUser();
		return this.aclService.isSystemAdmin(Board.KMS_MANAGER, user.getUserId());
	}
	
	
	/**
	 * 개인별 실적목록
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/listPrivate.do")	
	public ModelAndView listPrivate(PerformanceSearchCondition searchCondition, 
			@RequestParam(value="searchConditionString", required=false, defaultValue="") String searchConditionString,
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate) {
		
		ModelAndView modelAndView = null;
		try {
			
			if(!StringUtils.isEmpty(startDate)) {
				searchCondition.setStartDate(startDate);
				searchCondition.setEndDate(endDate);
			}
			
			if (searchCondition.getPagePerRecord() == null) {
				searchCondition.setPagePerRecord(10);
			}
			if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
				searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
				searchCondition.setSearchMonth(DateUtil.getToday("MM"));
			}
			
			if(StringUtils.isEmpty(searchConditionString)) {
				searchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition,
						"workPlaceName",
						"teamName",
						"teamCode",
						"registerName",
						"isPerson",
						"searchYear",
						"searchMonth",
						"pageIndex",
						"pagePerRecord",
						"startDate",
						"endDate"
											
				); 
				searchCondition.setSearchConditionString(searchConditionString);
				
			} else {
				ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition); 
			}
			/*
			if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
				searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
				searchCondition.setSearchMonth(DateUtil.getToday("MM"));
			}
			*/
			
			boolean isSystemAdmin = isSystemAdmin();
			User user = readUser();
			if(isSystemAdmin){
				modelAndView = new ModelAndView("collpack/kms/perform/listPrivate");			
			}else{
				searchCondition.setUserId(user.getUserId());
				searchCondition.setTeamName(StringUtil.nvl(searchCondition.getTeamName(), user.getTeamName()));
				
				//System.out.println("searchCondition:" + searchCondition.getSearchConditionString() +", sortColumn:" + searchCondition.getSortColumn()+", sortType:" + searchCondition.getSortType());
				
				if(searchCondition.getIsPerson().equals("Y")){
					searchCondition.setTeamCode(null);
				}
				modelAndView = new ModelAndView("collpack/kms/perform/listPrivatePerson");
			}		
			
			SearchResult<Performance> searchResult = performanceService.listPrivate(searchCondition);
			List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
			List<AdminTeamLeader> teamList = null;
			
			if(searchCondition.getIsPerson().equals("N")){
				KmsAdminSearchCondition permissionSearchCondition = new KmsAdminSearchCondition();
				permissionSearchCondition.setUserId(user.getUserId());			
				permissionSearchCondition.setStartRowIndex(0);
				permissionSearchCondition.setEndRowIndex(1000);
				teamList = adminPermissionDao.getTeamByLeaderPermList(permissionSearchCondition);
			}else{
				if(searchCondition.getWorkPlaceName() !=null ){
					teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
				}			
			}
			
			modelAndView.addObject("announceCode", new AnnounceCode()).addObject("searchResult", searchResult)
			.addObject("searchCondition", searchResult.getSearchCondition())
			.addObject("workPlaceList", workPlaceList)
			.addObject("isSystemAdmin", new Boolean(isSystemAdmin))
			.addObject("nowYear", DateUtil.getToday("yyyy"))
			.addObject("nowMonth", DateUtil.getToday("MM"))
			.addObject("user", user)
			.addObject("teamList", teamList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		return modelAndView;
	}
	
	@RequestMapping(value = "/listPrivateStandby.do")	
	public ModelAndView listPrivateStandby(PerformanceSearchCondition searchCondition, 
			@RequestParam(value="searchConditionString", required=false, defaultValue="") String searchConditionString) {
		
		ModelAndView modelAndView = null;
		try {
			
			
			if (searchCondition.getPagePerRecord() == null) {
				searchCondition.setPagePerRecord(10);
			}
			if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
				searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
				searchCondition.setSearchMonth(DateUtil.getToday("MM"));
			}
			
			if(StringUtils.isEmpty(searchConditionString)) {
				searchConditionString = ModelBeanUtil.makeSearchConditionString(searchCondition,
						"workPlaceName",
						"teamName",
						"teamCode",
						"registerName",
						"isPerson",
						"searchYear",
						"searchMonth",
						"pageIndex",
						"pagePerRecord",
						"startDate",
						"endDate"
											
				); 
				searchCondition.setSearchConditionString(searchConditionString);
				
			} else {
				ModelBeanUtil.makeSearchCondition(searchConditionString, searchCondition); 
			}
			/*
			if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
				searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
				searchCondition.setSearchMonth(DateUtil.getToday("MM"));
			}
			*/
			
			boolean isSystemAdmin = isSystemAdmin();
			User user = readUser();
			if(isSystemAdmin){
				modelAndView = new ModelAndView("collpack/kms/perform/listPrivateStandby");			
			}else{
				searchCondition.setUserId(user.getUserId());
				searchCondition.setTeamName(StringUtil.nvl(searchCondition.getTeamName(), user.getTeamName()));
				
				//System.out.println("searchCondition:" + searchCondition.getSearchConditionString() +", sortColumn:" + searchCondition.getSortColumn()+", sortType:" + searchCondition.getSortType());
				
				if(searchCondition.getIsPerson().equals("Y")){
					searchCondition.setTeamCode(null);
				}
				modelAndView = new ModelAndView("collpack/kms/perform/listPrivatePersonStandby");
			}		
			
			SearchResult<Performance> searchResult = performanceService.listPrivateStandby(searchCondition);
			List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
			List<AdminTeamLeader> teamList = null;
			
			if(searchCondition.getIsPerson().equals("N")){
				KmsAdminSearchCondition permissionSearchCondition = new KmsAdminSearchCondition();
				permissionSearchCondition.setUserId(user.getUserId());			
				permissionSearchCondition.setStartRowIndex(0);
				permissionSearchCondition.setEndRowIndex(1000);
				teamList = adminPermissionDao.getTeamByLeaderPermList(permissionSearchCondition);
			}else{
				if(searchCondition.getWorkPlaceName() !=null ){
					teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
				}			
			}
			
			modelAndView.addObject("announceCode", new AnnounceCode()).addObject("searchResult", searchResult)
			.addObject("searchCondition", searchResult.getSearchCondition())
			.addObject("workPlaceList", workPlaceList)
			.addObject("isSystemAdmin", new Boolean(isSystemAdmin))
			.addObject("nowYear", DateUtil.getToday("yyyy"))
			.addObject("nowMonth", DateUtil.getToday("MM"))
			.addObject("user", user)
			.addObject("teamList", teamList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		return modelAndView;
	}
	
	
	/**
	 * 개인별실적 목록 엑셀다운로드
	 * 
	 */
	@RequestMapping(value = "/downloadExcelPrivate.do")
	public ModelAndView downloadExcelPrivate( PerformanceSearchCondition searchCondition, HttpServletResponse response) {
		ExcelViewModel excel = new ExcelViewModel();  
		try {
			if (searchCondition.getPagePerRecord() == null) {
				searchCondition.setPagePerRecord(10);
			}
			searchCondition.setPageIndex(1);
			
			if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
				searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
				searchCondition.setSearchMonth(DateUtil.getToday("MM"));
			}
			
			boolean isSystemAdmin = isSystemAdmin();
			User user = readUser();
			if(!isSystemAdmin){			
				searchCondition.setUserId(user.getUserId());	
				
				if(searchCondition.getIsPerson().equals("Y")){
					searchCondition.setTeamCode(null);
				}
			}
			
			List<Performance> listPrivate = performanceService.allListPrivate(searchCondition);
			
			
			String fileName = "Performance_"
			 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xls";

			
			if( listPrivate.size() > 0 ) {
				
				String messagePrefix = "message.collpack.kms.perform.private.list.";

				excel.setFileName(fileName);   
			    excel.setSheetName("Sheet");   
			    
			    excel.setTitle(getMessage("message.collpack.kms.perform.private.excel." , "fileName")
						 + DateUtil.getTodayDateTime("yyyyMMdd"));  
			    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
			    
			    excel.addColumn(getMessage(messagePrefix, "itemId"), "rowNum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
				excel.addColumn(getMessage(messagePrefix, "itemSeq"), "itemId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "file"), "attachFileCount", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "gubun"), "isKnowHowValue", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "title"), "title", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "registerId"), "registerId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "registerName"), "registerName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "registDate"), "registDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "hitCount"), "hitCount", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "lineReplyCount"), "lineReplyCount", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "recommendCount"), "recommendCount", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "infoGrade"), "infoGrade", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "targetSource"), "targetSource", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "mark"), "mark", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "empNo"), "empNo", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "teamName"), "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn(getMessage(messagePrefix, "status"), "status", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				
				excel.setDataList(listPrivate); 
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
		
	}	
	
	private String getMessage(String prefix, String key){
		String msg =  messageSource.getMessage(prefix+ key, null, Locale.getDefault());		
		return msg;
	}
	
	/**
	 * 개인별 실적통계목록
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/listPrivateStat.do")	
	public ModelAndView listPrivateStat(PerformanceSearchCondition searchCondition) {
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
			searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
			searchCondition.setSearchMonth(DateUtil.getToday("MM"));
		}
		
		boolean isSystemAdmin = isSystemAdmin();
		User user = readUser();
		if(!isSystemAdmin){			
			return execPrivateStatByPerson(searchCondition, user);
		}else{			
			return execPrivateStatByAdmin(searchCondition, isSystemAdmin, user);
		}
	}

	private ModelAndView execPrivateStatByPerson(PerformanceSearchCondition searchCondition, User user) {

		ModelAndView modelAndView = new ModelAndView("collpack/kms/perform/listPrivateStatPerson");
		
		//searchCondition.setUserId(StringUtil.nvl(searchCondition.getUserId(),user.getUserId()));
		//searchCondition.setTeamName(StringUtil.nvl(searchCondition.getTeamName(),user.getTeamName()));
		searchCondition.setUserName(StringUtil.nvl(searchCondition.getRegisterName(),user.getUserName()));
		User searchUser = new User();
		UserSearchCondition userSearchCondition = new UserSearchCondition();
		userSearchCondition.setGroupId(user.getGroupId());
		userSearchCondition.setUserId(searchCondition.getUserName());
		searchUser = userDao.searchUser(userSearchCondition);
		
		SearchResult<Performance> searchResult = null;
		if(searchUser != null){
			searchCondition.setUserId(StringUtil.nvl(searchUser.getUserId(),user.getUserId()));
			searchCondition.setTeamName(StringUtil.nvl(searchUser.getTeamName(),user.getTeamName()));
			searchResult = performanceService.listPrivateStatPerson(searchCondition);
		}else{
			searchCondition.setUserId(user.getUserId());
			searchCondition.setUserName(user.getUserName());
			searchCondition.setTeamName(user.getTeamName());
			String tmpRuserName = searchCondition.getRegisterName();
			searchCondition.setRegisterName("notfound");
			searchResult = performanceService.listPrivateStatPerson(searchCondition);
			searchCondition.setRegisterName(tmpRuserName);
			
		}
		
		String[] arrRegSum = null;
		String[] arrDate = null;
		
		List<User> listTeamUser = userDao.listTeamUser(user.getGroupId());
		List<AdminTeamLeader> teamList = teamListByLeaderPerm(user);
		
		HashMap<String, String> totalSumMap = new HashMap<String, String>();
		
		if(!searchResult.isEmptyRecord()){
			ArrayList<String[]> resultChartList = getChartData(searchResult, totalSumMap);
			arrRegSum = resultChartList.get(0);
			arrDate = resultChartList.get(1);	
		}
			
		
		modelAndView.addObject("announceCode", new AnnounceCode()).addObject("searchResult", searchResult)
		.addObject("totalSumMap", totalSumMap)
		.addObject("searchCondition", searchResult.getSearchCondition())		
		.addObject("nowYear", DateUtil.getToday("yyyy"))
		.addObject("nowMonth", DateUtil.getToday("MM"))
		.addObject("user", user)
		.addObject("arrRegSum", arrRegSum)
		.addObject("arrDate", arrDate)
		.addObject("listTeamUser", listTeamUser)
		.addObject("teamList", teamList);
		
		return modelAndView;
	}

	
	/**
	 * 팀선택시 소속팀원목록
	 * 
	 * @param teamCodes 삭제대상 부서코드
	 */
	@RequestMapping(value = "/listMembers.do")
	public @ResponseBody String listMembers(@RequestParam(value="teamCode", required=true) String teamCode) {

		try {
			
			List<User> listTeamUser = userDao.listTeamUser(teamCode);
			
			String prefix = "<option value=\"";
			String suffix = "</option>";
			StringBuffer strBuf = new StringBuffer();
						
			for(User memberInfo : listTeamUser){
				
				strBuf.append(prefix).append(memberInfo.getUserId()).append("\">")
					.append(memberInfo.getUserName()).append(suffix).append("\r\n");
			}
			return strBuf.toString();

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}
	}
	
	
	
	private ArrayList<String[]> getChartData(SearchResult<Performance> searchResult, HashMap<String, String> totalSumMap) {
		List<Performance> listPrivateStatPerson = searchResult.getEntity();
		int size = listPrivateStatPerson.size();
		ArrayList<String> regList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<String[]> resultList = new ArrayList();
		
		makeTotSumData(listPrivateStatPerson, regList, dateList, totalSumMap);
		
		resultList.add(0, regList.toArray(new String[size]));
		resultList.add(1, dateList.toArray(new String[size]));
		
		return resultList;
	}

	private void makeTotSumData(List<Performance> listPrivateStatPerson, ArrayList<String> regList,
			ArrayList<String> dateList, HashMap<String, String> totalSumMap) {
		
		int totObligationSum = 0;
		int totRegSum = 0;
		int totMarkSum = 0;
		int totHitCountSum = 0;
		int totUsageSum = 0;
		int totRecommendSum = 0;
		int totLineReplySum = 0;
		double totConversionMarkSum = 0.0;
		
		for(Performance performance : listPrivateStatPerson){
			regList.add(performance.getRegSum());
			dateList.add(performance.getRegistDate());
			
			try {
				totObligationSum += Integer.parseInt(performance.getObligationSum());
				totRegSum += Integer.parseInt(performance.getRegSum());
				totMarkSum += Integer.parseInt(performance.getMarkSum());
				totHitCountSum += Integer.parseInt(performance.getHitSum());
				totUsageSum += Integer.parseInt(performance.getUsageSum());
				totRecommendSum += Integer.parseInt(performance.getRecommendSum());
				totLineReplySum += Integer.parseInt(performance.getLineReplySum());
				totConversionMarkSum += Double.parseDouble(performance.getConversionMark());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}						
		}
		
		totalSumMap.put("totObligationSum", String.valueOf(totObligationSum));
		totalSumMap.put("totRegSum", String.valueOf(totRegSum));
		totalSumMap.put("totRegSumRate", String.valueOf(Math.round(((double)totRegSum/(double)totObligationSum)*100)) + "%");
		totalSumMap.put("totMarkSum", String.valueOf(totMarkSum));
		totalSumMap.put("totHitCountSum", String.valueOf(totHitCountSum));
		totalSumMap.put("totUsageSum", String.valueOf(totUsageSum));
		totalSumMap.put("totRecommendSum", String.valueOf(totRecommendSum));
		totalSumMap.put("totLineReplySum", String.valueOf(totLineReplySum));
		totalSumMap.put("totConversionMarkSum", String.valueOf(totConversionMarkSum));
		
		
	}

	private ModelAndView execPrivateStatByAdmin(PerformanceSearchCondition searchCondition,
			boolean isSystemAdmin, User user) {
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/perform/listPrivateStat");		
		setMonthCnt(searchCondition);
		
		SearchResult<Performance> searchResult = performanceService.listPrivateStat(searchCondition);
		List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
		List<AdminTeamLeader> teamList = null;
		
		if(searchCondition.getWorkPlaceName() !=null ){
			teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
		}
		
		modelAndView.addObject("announceCode", new AnnounceCode()).addObject("searchResult", searchResult)
					.addObject("searchCondition", searchResult.getSearchCondition())
					.addObject("workPlaceList", workPlaceList)
					.addObject("isSystemAdmin", new Boolean(isSystemAdmin))
					.addObject("nowYear", DateUtil.getToday("yyyy"))
					.addObject("nowMonth", DateUtil.getToday("MM"))
					.addObject("user", user)
					.addObject("teamList", teamList);
		return modelAndView;
	}
	
	
	private void setMonthCnt(PerformanceSearchCondition searchCondition) {
		//int days = DateUtil.getTwoDatesDifference(searchCondition.getStartDate().replace("-", ""), searchCondition.getEndDate().replace("-", ""));
		String tmpsmonth = searchCondition.getStartDate().replace("-", "").substring(4, 6);
		String tmpemonth = searchCondition.getEndDate().replace("-", "").substring(4, 6);
		String tmpsyear = searchCondition.getStartDate().replace("-", "").substring(0, 4);
		String tmpeyear = searchCondition.getEndDate().replace("-", "").substring(0, 4);
		int yearCnt = Integer.parseInt(tmpeyear)-Integer.parseInt(tmpsyear);
		yearCnt = yearCnt*12;
		int monthCnt = Integer.parseInt(tmpemonth)-Integer.parseInt(tmpsmonth);
		monthCnt = yearCnt+monthCnt;
		monthCnt = monthCnt+1;
		/*Double doubleDays = new Double(String.valueOf(days));
		Double monthDays = new Double(30);
		int monthCnt = new Double(Math.ceil(doubleDays / monthDays)).intValue();*/
		searchCondition.setMonthCnt(String.valueOf(monthCnt));		
	}

	/**
	 * 개인별실적 목록 엑셀다운로드(admin으로 조회한경우)
	 * 
	 */
	@RequestMapping(value = "/downloadExcelPrivateStat.do")
	public ModelAndView downloadExcelPrivateStat( PerformanceSearchCondition searchCondition, HttpServletResponse response) {
						
		ExcelViewModel excel = new ExcelViewModel();  
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPageIndex(1);
		
		setMonthCnt(searchCondition);
		List<Performance> listPrivateStat = performanceService.allListPrivateStat(searchCondition);
		
		String fileName = "PerformancePrivateStat_"
						 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xls";

		
		if( listPrivateStat.size() > 0 ) {
			
			String messagePrefix = "message.collpack.kms.perform.private.list.";
			
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    excel.setTitle(getMessage("message.collpack.kms.perform.private.stat.excel.", "fileName")
					 + DateUtil.getTodayDateTime("yyyyMMdd"));  
		    
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    	
		    excel.addColumn(getMessage(messagePrefix, "itemId"), "rowNum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "registerName"), "userName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "workPlaceName"), "workPlaceName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "teamName"), "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "obligationCnt"), "obligationSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "regCnt"), "regSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "regRate"), "obligationRegRate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "markSum"), "markSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    
		    excel.addColumn(getMessage(messagePrefix, "hitSum"), "hitSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "usageSum"), "usageSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "recommendSum"), "recommendSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "lineReplySum"), "lineReplySum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "conversionMark"), "conversionMark", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "jobTitleName"), "jobTitleName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "userStatus"), "userStatus", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    		    
		    excel.setDataList(listPrivateStat);			
		}
		
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	
	/**
	 * 개인별실적 목록 엑셀다운로드
	 * 
	 * 
	 */
	@RequestMapping(value = "/downloadExcelPrivateStatPerson.do")
	public ModelAndView downloadExcelPrivateStatPerson( PerformanceSearchCondition searchCondition, HttpServletResponse response) {
					
		ExcelViewModel excel = new ExcelViewModel();  
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(20);
		}
		User user = readUser();
		searchCondition.setPageIndex(1);
		searchCondition.setUserId(user.getUserId());
		
		SearchResult<Performance> searchResult = performanceService.listPrivateStatPerson(searchCondition);
		List<Performance> listPrivateStatPerson = searchResult.getEntity();
		
		String fileName = "PerformancePrivateStatPerson_"
						 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xls";

		
		if( listPrivateStatPerson.size() > 0 ) {
			
			String messagePrefix = "message.collpack.kms.perform.private.list.";
			
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    excel.setTitle(getMessage("message.collpack.kms.perform.private.stat.excel.", "fileName")
					 + DateUtil.getTodayDateTime("yyyyMMdd"));  
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    	
		    //excel.addColumn(getMessage(messagePrefix, "itemId"), "rowNum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "registerName"), "userName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "workPlaceName"), "workPlaceName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "teamName"), "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "searchMonth"), "registDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "obligationCnt"), "obligationSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "regCnt"), "regSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "regRate"), "obligationRegRate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "markSum"), "markSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "hitSum"), "hitSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "usageSum"), "usageSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "recommendSum"), "recommendSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "lineReplySum"), "lineReplySum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "conversionMark"), "conversionMark", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    
		    excel.setDataList(listPrivateStatPerson); 			
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	
	
	/**
	 * 부서별 실적통계목록
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/listTeamStat.do")	
	public ModelAndView listTeamStat(PerformanceSearchCondition searchCondition) {
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
			searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
			searchCondition.setSearchMonth(DateUtil.getToday("MM"));
		}
		
		boolean isSystemAdmin = isSystemAdmin();
		User user = readUser();
		if(!isSystemAdmin){			
			return execTeamStatByPerson(searchCondition, user);
		}else{			
			return execTeamStatByAdmin(searchCondition, isSystemAdmin, user);
		}
	}

	private ModelAndView execTeamStatByAdmin(PerformanceSearchCondition searchCondition, boolean isSystemAdmin,
			User user) {
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/perform/listTeamStat");
		setMonthCnt(searchCondition);
		SearchResult<Performance> searchResult = performanceService.listTeamStat(searchCondition);
		List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
		List<AdminTeamLeader> teamList = null;
		
		if(searchCondition.getWorkPlaceName() !=null ){
			teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
		}
		
		modelAndView.addObject("announceCode", new AnnounceCode()).addObject("searchResult", searchResult)
					.addObject("searchCondition", searchResult.getSearchCondition())
					.addObject("workPlaceList", workPlaceList)
					.addObject("isSystemAdmin", new Boolean(isSystemAdmin))
					.addObject("nowYear", DateUtil.getToday("yyyy"))
					.addObject("nowMonth", DateUtil.getToday("MM"))
					.addObject("user", user)
					.addObject("teamList", teamList);
		return modelAndView;
	}

	private ModelAndView execTeamStatByPerson(PerformanceSearchCondition searchCondition, User user) {
		ModelAndView modelAndView = new ModelAndView("collpack/kms/perform/listTeamStatPerson");
		searchCondition.setUserId(user.getUserId());	
		searchCondition.setTeamCode(StringUtil.nvl(searchCondition.getTeamCode(), user.getGroupId()));
		searchCondition.setTeamName(StringUtil.nvl(searchCondition.getTeamName(), user.getTeamName()));
		SearchResult<Performance> searchResult = performanceService.listTeamStatPerson(searchCondition);
		String[] arrRegSum = null;
		String[] arrDate = null;
		
		HashMap<String, String> totalSumMap = new HashMap<String, String>();
		
		if(!searchResult.isEmptyRecord()){
			ArrayList<String[]> resultChartList = getChartData(searchResult, totalSumMap);
			arrRegSum = resultChartList.get(0);
			arrDate = resultChartList.get(1);	
		}
			
		
		List<AdminTeamLeader> teamList = teamListByLeaderPerm(user);
		
		
		modelAndView.addObject("announceCode", new AnnounceCode()).addObject("searchResult", searchResult)
		.addObject("totalSumMap", totalSumMap)
		.addObject("searchCondition", searchResult.getSearchCondition())		
		.addObject("nowYear", DateUtil.getToday("yyyy"))
		.addObject("nowMonth", DateUtil.getToday("MM"))
		.addObject("user", user)
		.addObject("arrRegSum", arrRegSum)
		.addObject("arrDate", arrDate)
		.addObject("teamList", teamList);
		
		return modelAndView;
	}

	private List<AdminTeamLeader> teamListByLeaderPerm(User user) {
		KmsAdminSearchCondition permissionSearchCondition = new KmsAdminSearchCondition();
		permissionSearchCondition.setUserId(user.getUserId());			
		permissionSearchCondition.setStartRowIndex(0);
		permissionSearchCondition.setEndRowIndex(1000);
		List<AdminTeamLeader> teamList = adminPermissionDao.getTeamByLeaderPermList(permissionSearchCondition);
		return teamList;
	}
	

	/**
	 * 부서별실적 목록 엑셀다운로드(admin으로 조회한경우)
	 * 
	 */
	@RequestMapping(value = "/downloadExcelTeamStat.do")
	public ModelAndView downloadExcelTeamStat( PerformanceSearchCondition searchCondition, HttpServletResponse response) {
		
		ExcelViewModel excel = new ExcelViewModel();  
						
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPageIndex(1);
		setMonthCnt(searchCondition);
		
		List<Performance> listTeamStat = performanceService.allListTeamStat(searchCondition);
		
		
		String fileName = "PerformanceTeamStat_"
						 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xls";

		
		if( listTeamStat.size() > 0 ) {
			
			String messagePrefix = "message.collpack.kms.perform.private.list.";

			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    excel.setTitle(getMessage("message.collpack.kms.perform.team.stat.excel.", "fileName")
					 + DateUtil.getTodayDateTime("yyyyMMdd"));  
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    	
		    excel.addColumn(getMessage(messagePrefix, "itemId"), "rowNum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "teamName"), "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "memberCnt"), "memberCnt", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "obligationCnt"), "obligationSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "regCnt"), "regSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "regRate"), "obligationRegRate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "markSum"), "markSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "hitSum"), "hitSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "usageSum"), "usageSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "recommendSum"), "recommendSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "lineReplySum"), "lineReplySum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "conversionMark"), "conversionMark", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    
		    excel.setDataList(listTeamStat); 
			
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	/**
	 * 부서별실적 목록 엑셀다운로드
	 * 
	 */
	@RequestMapping(value = "/downloadExcelTeamStatPerson.do")
	public ModelAndView downloadExcelTeamStatPerson( PerformanceSearchCondition searchCondition, HttpServletResponse response) {
		ExcelViewModel excel = new ExcelViewModel();  	
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(20);
		}
		User user = readUser();
		searchCondition.setPageIndex(1);
		searchCondition.setUserId(user.getUserId());
		
		SearchResult<Performance> searchResult = performanceService.listTeamStatPerson(searchCondition);
		List<Performance> listTeamStatPerson = searchResult.getEntity();
		
		String fileName = "PerformanceTeamStatPerson_"
						 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xls";

		
		if( listTeamStatPerson.size() > 0 ) {
			String messagePrefix = "message.collpack.kms.perform.private.list.";
			
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    excel.setTitle(getMessage("message.collpack.kms.perform.team.stat.excel.", "fileName")
					 + DateUtil.getTodayDateTime("yyyyMMdd"));  
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    	
		    //excel.addColumn(getMessage(messagePrefix, "itemId"), "rowNum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "searchMonth"), "registDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "teamName"), "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "memberCnt"), "memberCnt", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "obligationCnt"), "obligationSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "regCnt"), "regSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "regRate"), "obligationRegRate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "markSum"), "markSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "hitSum"), "hitSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "usageSum"), "usageSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "recommendSum"), "recommendSum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "lineReplySum"), "lineReplySum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_NUMBER,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "conversionMark"), "conversionMark", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);

		    excel.setDataList(listTeamStatPerson);

		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	
	/**
	 * 전문가이력 목록(어드민 메뉴)
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/listExpert.do")	
	public ModelAndView listExpert(PerformanceSearchCondition searchCondition) {
		
		if(!isSystemAdmin()){
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView modelAndView = new ModelAndView("collpack/kms/perform/listExpert");
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		
		
		if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
			searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
			searchCondition.setSearchMonth(DateUtil.getToday("MM"));
		}
				
		SearchResult<Performance> searchResult = performanceService.listExpert(searchCondition);
		
		modelAndView.addObject("announceCode", new AnnounceCode()).addObject("searchResult", searchResult)
					.addObject("searchCondition", searchResult.getSearchCondition())
					.addObject("nowYear", DateUtil.getToday("yyyy"))
					.addObject("nowMonth", DateUtil.getToday("MM"));
		return modelAndView;
	}	
	
	
	/**
	 * 전문가평가이력 목록 엑셀다운로드
	 * 
	 */
	@RequestMapping(value = "/downloadExcelListExpert.do")
	public ModelAndView downloadExcelListExpert( PerformanceSearchCondition searchCondition, HttpServletResponse response) {
		
		ExcelViewModel excel = new ExcelViewModel();  
						
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(20);
		}
		
		if(searchCondition.getSearchYear() == null && searchCondition.getSearchMonth() == null){
			searchCondition.setSearchYear(DateUtil.getToday("yyyy"));
			searchCondition.setSearchMonth(DateUtil.getToday("MM"));
		}
				
		List<Performance> listExpert = performanceService.allListExpert(searchCondition);		
		
		String fileName = "ExpertList_"
						 + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xls";

		
		if( listExpert.size() > 0 ) {
			
			String messagePrefix = "message.collpack.kms.perform.private.list.";

			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    excel.setTitle(getMessage("message.collpack.kms.perform.private.expert.header.", "title") + "_"
					 + DateUtil.getTodayDateTime("yyyyMMdd"));  
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    	
		    excel.addColumn(getMessage(messagePrefix, "itemId"), "rowNum", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn(getMessage(messagePrefix, "gubun"), "isKnowHowValue", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "title"), "title", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "expertName"), "expertName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "teamName"), "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "searchMonth"), "registDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "expertReqDate"), "expertReqDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    
		    excel.addColumn(getMessage(messagePrefix, "assessDate"), "assessDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "hitCount"), "hitCount", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "lineReplyCount"), "lineReplyCount", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "recommendCount"), "expertReqDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "expertReqDate"), "recommendCount", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn(getMessage(messagePrefix, "mark"), "mark", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    
		    excel.setDataList(listExpert);   
			
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	/**
	 * 의무등록건수 미달자 메신저 알림 비동기 컨트롤 메서드 (only admin)
	 * 
	 * @param 
	 */
	@RequestMapping(value = "/notiPrivateStat.do")
	public @ResponseBody String notiPrivateStat(PerformanceSearchCondition searchCondition, @RequestParam(value="adminUserName", required=true ) String adminUserName ) {

		try {
			
			if (searchCondition.getPagePerRecord() == null) {
				searchCondition.setPagePerRecord(10);
			}
			searchCondition.setPageIndex(1);
			setMonthCnt(searchCondition);
			List<Performance> listPrivateStat = performanceService.allListPrivateStat(searchCondition);
			
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			for(Performance performance : listPrivateStat){
				
				boolean isUnderPerformance = isUnderPerformance(performance);
				if(isUnderPerformance){
					makeSendMessage(performance, adminUserName, atmc);
				}				
			}
			atmc.send();			

		} catch (Exception exception) {
			throw new IKEP4AjaxException("code", exception);

		}
		return null;
	}

	private void makeSendMessage(Performance performance, String adminUserName, AtMessengerCommunicator atmc) {		
		
		String prefix = "message.collpack.kms.perform.messenger.";
		String title = messageSource.getMessage( prefix + "title", new String[]{adminUserName}, Locale.getDefault());
		String contents = messageSource.getMessage( prefix + "contents", new String[]{performance.getUserName(), performance.getObligationSum(), performance.getRegSum()}, Locale.getDefault());
		
		//test
		//atmc.addMessage("cnskcm", adminUserName, contents.toString(), "", title);
		atmc.addMessage(performance.getUserId(), adminUserName, contents.toString(), "", title,"smspop");
		
		
	}

	private boolean isUnderPerformance(Performance performance) {
		
		int obligationSum = Integer.parseInt(StringUtil.nvl(performance.getObligationSum(), "0"));
		int regSum = Integer.parseInt(StringUtil.nvl(performance.getRegSum(), "0"));
		return regSum < obligationSum ? true : false;
	}

}
