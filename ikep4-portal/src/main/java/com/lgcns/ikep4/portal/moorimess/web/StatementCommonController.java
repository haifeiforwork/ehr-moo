package com.lgcns.ikep4.portal.moorimess.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.PortalCode;
import com.lgcns.ikep4.portal.moorimess.search.StatementSearchCondition;
import com.lgcns.ikep4.portal.moorimess.service.StatementService;
import com.lgcns.ikep4.support.quartz.model.QuartzLog;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.jco.EpStatementDataRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.model.StatementDetail;


@Controller
@RequestMapping(value="/portal/moorimess/statementCommon")
public class StatementCommonController extends BaseController {

	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private EpStatementDataRcvRFC epStatementRcv;
	
	@Autowired
	private StatementService service;
	
	public boolean isSystemAdmin(User user) {
		
		return this.aclService.isSystemAdmin("MoorimEss", user.getUserId());
	}
	
	
	@RequestMapping(value = "/statementMenuView.do")
	public ModelAndView statementMenuView(HttpServletRequest request,@RequestParam(value="sflag", required=false) String sflag) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/statementCommon/statementMenuView");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("sflag", sflag);
		return mav;
	}
	
	@RequestMapping(value = "/statementList.do")
	public ModelAndView statementList(StatementSearchCondition statementSearchCondition,@RequestParam(value="searchConditionString", required=false) String searchConditionString, HttpServletRequest request) {

		if(statementSearchCondition == null) {
			statementSearchCondition = new StatementSearchCondition();
		}
		ModelAndView mav = new ModelAndView( "/portal/moorimess/statementCommon/statementList");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		StatementDetail statement = new StatementDetail();
		statement.setIp100("X");
		statement.setIs100("X");
		statement.setIm100("X");
		statement.setIuname(user.getSapId());
		statement.setIreqt("X");
		statement.setIreqn("A");
		
		if(statementSearchCondition.getIfname() == null) {
			statementSearchCondition.setIfname("");
		}
		if(statementSearchCondition.getIstate() == null) {
			statementSearchCondition.setIstate("");
		}
		
		statement.setIfname(statementSearchCondition.getIfname());
		statement.setIstate(statementSearchCondition.getIstate());
		
		statementSearchCondition.setStartRowIndex(statementSearchCondition.getPagePerRecord()*statementSearchCondition.getPageIndex()-statementSearchCondition.getPagePerRecord()+1);
		statementSearchCondition.setEndRowIndex(statementSearchCondition.getPagePerRecord()*statementSearchCondition.getPageIndex());
		statement.setPageIndex(statementSearchCondition.getPageIndex());
		statement.setEndRowIndex(statementSearchCondition.getEndRowIndex());//X~Y : Y값
		statement.setStartRowIndex(statementSearchCondition.getStartRowIndex());//X~Y : X값
		statement.setPagePerRecord(statementSearchCondition.getPagePerRecord());
		statement.setSessionUser(user.getSapId());
		//List<StatementDetail> statementList =  epStatementRcv.EpStatementDataRcvRFC(statement, null);
		Map<String, Object> sitem = epStatementRcv.EpStatementPageDataRcvRFC(statement, null);
		List<StatementDetail> statementList = (List<StatementDetail>) sitem.get("statementDetailList");
		StatementDetail statementResult = (StatementDetail) sitem.get("statementPage");
		statementSearchCondition.setPageIndex(statementResult.getPageIndex());
		statementSearchCondition.setPageCount(statementResult.getPageCount());//페이지수
		statementSearchCondition.setRecordCount(statementResult.getRecordCount());//전체갯수
		statementSearchCondition.setEmptyRecord(statementResult.isEmptyRecord());//있는지 없는지 없으면 true
		PortalCode portalCode = new PortalCode();
		// 올해 년도 구하기
		String thisYear = DateUtil.getToday("yyyy");
		
		//분기 구하기
		int thisMonth = Integer.parseInt(DateUtil.getToday("MM").toString());
		int quarter = 0;
		int month1 = 0;
		int month2 = 0;
		int month3 = 0;
		int nowQuarter = 0;
		if(statementSearchCondition.getQuarter()==null){			
			if(thisMonth%3==0){
				quarter = thisMonth/3;
			}else{
				quarter = (thisMonth/3)+1;
			}
			nowQuarter = quarter;
		}
		
		
		//날짜 형식 세팅
		String sMonth = "";
		String eMonth = "";
		
		//최초 로딩인 경우
		if("yes".equals(statementSearchCondition.getIsInit())){
			statementSearchCondition.setPeriod("1");
			switch (nowQuarter) {
			case 1:
				statementSearchCondition.setIfspmon(thisYear + ".01.01");
				statementSearchCondition.setItspmon(thisYear + ".03.31");
				month1 = 1;
				month2 = 2;
				month3 = 3;
				break; //1분기
			case 2:
				statementSearchCondition.setIfspmon(thisYear + ".04.01");
				statementSearchCondition.setItspmon(thisYear + ".06.30");
				month1 = 4;
				month2 = 5;
				month3 = 6;
				break; //2분기
			case 3:
				statementSearchCondition.setIfspmon(thisYear + ".07.01");
				statementSearchCondition.setItspmon(thisYear + ".09.30");
				month1 = 7;
				month2 = 8;
				month3 = 9;
				break; //3분기
			case 4:
				statementSearchCondition.setIfspmon(thisYear + ".10.01");
				statementSearchCondition.setItspmon(thisYear + ".12.31");
				month1 = 10;
				month2 = 11;
				month3 = 12;
				break; //4분기							
			}
		}else{
			switch (nowQuarter) {
			case 1:
				month1 = 1;
				month2 = 2;
				month3 = 3;
				break; //1분기
			case 2:
				month1 = 4;
				month2 = 5;
				month3 = 6;
				break; //2분기
			case 3:
				month1 = 7;
				month2 = 8;
				month3 = 9;
				break; //3분기
			case 4:
				month1 = 10;
				month2 = 11;
				month3 = 12;
				break; //4분기							
			}
		}
		
		//YYYYMM 형식 6자리로 세팅
		if(!("".equals(statementSearchCondition.getIfspmon()))&&statementSearchCondition.getIfspmon()!=null){
			sMonth = statementSearchCondition.getIfspmon().replaceAll("\\.","").substring(0,6);
		}		
		if(!("".equals(statementSearchCondition.getItspmon()))&&statementSearchCondition.getItspmon()!=null){
			eMonth = statementSearchCondition.getItspmon().replaceAll("\\.","").substring(0,6);
		}
		
		//서치컨디션 유지 
		if(StringUtils.isEmpty(searchConditionString)) {
			searchConditionString = ModelBeanUtil.makeSearchConditionString(statementSearchCondition,
					"ifspmon",
					"itspmon",
					"isInit",
					"quarter",
					"period",
					"kostl"
			);
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, statementSearchCondition);
		}
		StatementDetail budget = new StatementDetail();
		
		budget.setIm100("X");	
		budget.setIs100("X");
		budget.setIp100("X");
		budget.setIfspmon(sMonth);								//검색시작월
		budget.setItspmon(eMonth);								//검색종료월
		budget.setIuname(user.getSapId());						//사용자 마스터 레코드의 사용자 이름
		budget.setIneonetacc("X");								//네오넷 계정 아이디
		budget.setIkostl(statementSearchCondition.getKostl());
		Map<String, Object> item = epStatementRcv.EpStatementBudgetSimpleListDataRcvRFC(budget, null);
		List<StatementDetail> budgetList1 = (List<StatementDetail>) item.get("statementDetailList1");
		List<StatementDetail> budgetList2 = (List<StatementDetail>) item.get("statementDetailList2");
		List<StatementDetail> budgetList3 = (List<StatementDetail>) item.get("statementDetailList3");
		List<StatementDetail> costCenterList = (List<StatementDetail>) item.get("costCenterList");
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("statementList", statementList);
		mav.addObject("budgetList1", budgetList1);
		mav.addObject("budgetList2", budgetList2);
		mav.addObject("budgetList3", budgetList3);
		mav.addObject("costCenterList", costCenterList);
		mav.addObject("thisYear",thisYear);
		mav.addObject("quarter", quarter);
		mav.addObject("month1", month1);
		mav.addObject("month2", month2);
		mav.addObject("month3", month3);
		mav.addObject("nowQuarter", nowQuarter);
		mav.addObject("statementResult", statementResult);
		mav.addObject("portalCode", portalCode);
		mav.addObject("statementSearchCondition", statementSearchCondition);
		return mav;
	}
	
	@RequestMapping(value = "/statementAllList.do")
	public ModelAndView statementAllList(StatementSearchCondition statementSearchCondition,
			@RequestParam(value="searchConditionString", required=false) String searchConditionString, 
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/statementCommon/statementAllList");
		
		//TodoSearchCondition 셋팅
		if(statementSearchCondition == null) {
			statementSearchCondition = new StatementSearchCondition();
		}
		if(statementSearchCondition.getIracct() == null) {
			statementSearchCondition.setIracct("");
		}
		if(statementSearchCondition.getIp100() == null) {
			statementSearchCondition.setIp100("");
		}
		if(statementSearchCondition.getIs100() == null) {
			statementSearchCondition.setIs100("");
		}
		if(statementSearchCondition.getIm100() == null) {
			statementSearchCondition.setIm100("");
		}
		if(statementSearchCondition.getCardno() == null) {
			statementSearchCondition.setCardno("");
		}
		if(statementSearchCondition.getIfname() == null) {
			statementSearchCondition.setIfname("");
		}
		if(statementSearchCondition.getIstate() == null) {
			statementSearchCondition.setIstate("");
		}
		
		// 올해 년도 구하기
		String thisYear = DateUtil.getToday("yyyy").toString();
		
		//분기 구하기
		String thisMonth = DateUtil.getToday("MM").toString();
		
		
		int lastDay = DateUtil.getLastDate(Integer.parseInt(thisYear), Integer.parseInt(thisMonth));
		
		//최초 로딩인 경우
		if("yes".equals(statementSearchCondition.getIsInit())){
			//최초 로딩시 검색조건 디폴트 선택
			statementSearchCondition.setIp100("X");
			statementSearchCondition.setIs100("X");
			statementSearchCondition.setIm100("X");
			statementSearchCondition.setIreqs("X");
			statementSearchCondition.setIreqd("X");
			statementSearchCondition.setIreqr("X");
			statementSearchCondition.setIreqn("S");
			statementSearchCondition.setIconffdate(thisYear+"."+thisMonth+".01");
			statementSearchCondition.setIconftdate(thisYear+"."+thisMonth+"."+Integer.toString(lastDay));
			statementSearchCondition.setIapprfdate("");
			statementSearchCondition.setIapprtdate("");
		}
		
		//서치컨디션 유지 
		if(StringUtils.isEmpty(searchConditionString)) {
			searchConditionString = ModelBeanUtil.makeSearchConditionString(statementSearchCondition,
					"ip100",
					"is100",
					"im100",
					"iconffdate",
					"iconftdate",
					"iapprfdate",
					"iapprtdate",
					"iracct",
					"isInit",
					"cardno",
					"ireqs",
					"ireqd",
					"ireqr",
					"ireqn",
					"zzkostl"
			);
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, statementSearchCondition);
		}
		String iconffdate = "";
		String iconftdate = "";
		String iapprfdate = "";
		String iapprtdate = "";
		if(!("".equals(statementSearchCondition.getIconffdate()))&&statementSearchCondition.getIconffdate()!=null){
			iconffdate = statementSearchCondition.getIconffdate().replaceAll("\\.","");
		}		
		if(!("".equals(statementSearchCondition.getIconftdate()))&&statementSearchCondition.getIconftdate()!=null){
			iconftdate = statementSearchCondition.getIconftdate().replaceAll("\\.","");
		}
		if(!("".equals(statementSearchCondition.getIapprfdate()))&&statementSearchCondition.getIapprfdate()!=null){
			iapprfdate = statementSearchCondition.getIapprfdate().replaceAll("\\.","");
		}		
		if(!("".equals(statementSearchCondition.getIapprtdate()))&&statementSearchCondition.getIapprtdate()!=null){
			iapprtdate = statementSearchCondition.getIapprtdate().replaceAll("\\.","");
		}
		
		
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		StatementDetail statement = new StatementDetail();
		statement.setIm100(statementSearchCondition.getIm100());	
		statement.setIs100(statementSearchCondition.getIs100());
		statement.setIp100(statementSearchCondition.getIp100());
		statement.setIconffdate(iconffdate);								
		statement.setIconftdate(iconftdate);	
		statement.setIapprfdate(iapprfdate);								
		statement.setIapprtdate(iapprtdate);
		statement.setIracct(statementSearchCondition.getIracct());	
		statement.setCardno(statementSearchCondition.getCardno());	
		statement.setIuname(user.getSapId());
		statement.setIreqs(statementSearchCondition.getIreqs());
		if(iapprfdate != "" || iapprtdate != ""){
			statement.setIreqr("");
		}else{
			statement.setIreqr(statementSearchCondition.getIreqr());
		}
		statement.setIreqd(statementSearchCondition.getIreqd());
		statement.setIreqn(statementSearchCondition.getIreqn());
		statement.setIfname(statementSearchCondition.getIfname());
		statement.setIstate(statementSearchCondition.getIstate());
		statement.setZzkostl(statementSearchCondition.getZzkostl());
		//Map<String, Object> item = epStatementRcv.EpStatementAllDataRcvRFC(statement, null);
		
		statementSearchCondition.setStartRowIndex(statementSearchCondition.getPagePerRecord()*statementSearchCondition.getPageIndex()-statementSearchCondition.getPagePerRecord()+1);
		statementSearchCondition.setEndRowIndex(statementSearchCondition.getPagePerRecord()*statementSearchCondition.getPageIndex());
		statement.setPageIndex(statementSearchCondition.getPageIndex());
		statement.setEndRowIndex(statementSearchCondition.getEndRowIndex());//X~Y : Y값
		statement.setStartRowIndex(statementSearchCondition.getStartRowIndex());//X~Y : X값
		statement.setPagePerRecord(statementSearchCondition.getPagePerRecord());
		Map<String, Object> item = epStatementRcv.EpStatementAllPageDataRcvRFC(statement, null);
		List<StatementDetail> statementList = (List<StatementDetail>) item.get("statementDetailList");
		List<StatementDetail> costCenterList = (List<StatementDetail>) item.get("costCenterList");
		StatementDetail statementResult = (StatementDetail) item.get("statementPage");
		if(costCenterList.size() < 1){
			int tmpMonth = Integer.parseInt(thisMonth)-2;
			int lastYear = 0;
			String tmpMonth1 = "";
			if(tmpMonth < 10){
				if(tmpMonth < 1){
					tmpMonth = 12+tmpMonth;
					tmpMonth1 = Integer.toString(tmpMonth);
					lastYear = Integer.parseInt(thisYear)-1;
				}else{
					tmpMonth1 = "0"+Integer.toString(tmpMonth);
					lastYear = Integer.parseInt(thisYear);
				}
			}else{
				tmpMonth1 = Integer.toString(tmpMonth);
				lastYear = Integer.parseInt(thisYear);
			}
			statement.setIconffdate(Integer.toString(lastYear)+tmpMonth1+"01");								
			statement.setIconftdate(thisYear+thisMonth+Integer.toString(lastDay));	
			statement.setIapprfdate("");								
			statement.setIapprtdate("");
			item = epStatementRcv.EpStatementAllDataRcvRFC(statement, null);
			costCenterList = (List<StatementDetail>) item.get("costCenterList");
		}
		statementSearchCondition.setPageIndex(statementResult.getPageIndex());
		statementSearchCondition.setPageCount(statementResult.getPageCount());//페이지수
		statementSearchCondition.setRecordCount(statementResult.getRecordCount());//전체갯수
		statementSearchCondition.setEmptyRecord(statementResult.isEmptyRecord());//있는지 없는지 없으면 true
		PortalCode portalCode = new PortalCode();
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("statementList", statementList);
		mav.addObject("costCenterList", costCenterList);
		mav.addObject("statementResult", statementResult);
		mav.addObject("portalCode", portalCode);
		mav.addObject("statementSearchCondition", statementSearchCondition);
		
		return mav;
	}
	
	@RequestMapping(value = "/budgetList.do")
	public ModelAndView budgetList(StatementSearchCondition statementSearchCondition,@RequestParam(value="searchConditionString", required=false) String searchConditionString, HttpServletRequest request) {
		
		//TodoSearchCondition 셋팅
		if(statementSearchCondition == null) {
			statementSearchCondition = new StatementSearchCondition();
		}
		if(statementSearchCondition.getIracct() == null) {
			statementSearchCondition.setIracct("");
		}
		if(statementSearchCondition.getIp100() == null) {
			statementSearchCondition.setIp100("");
		}
		if(statementSearchCondition.getIs100() == null) {
			statementSearchCondition.setIs100("");
		}
		if(statementSearchCondition.getIm100() == null) {
			statementSearchCondition.setIm100("");
		}
		
		// 올해 년도 구하기
		String thisYear = DateUtil.getToday("yyyy");
		
		//분기 구하기
		int thisMonth = Integer.parseInt(DateUtil.getToday("MM").toString());
		int quarter = 0;
		if(statementSearchCondition.getQuarter()==null){			
			if(thisMonth%3==0){
				quarter = thisMonth/3;
			}else{
				quarter = (thisMonth/3)+1;
			}
		}
		//날짜 형식 세팅
		String sMonth = "";
		String eMonth = "";
		
		//최초 로딩인 경우
		if("yes".equals(statementSearchCondition.getIsInit())){
			//최초 로딩시 검색조건 디폴트 선택
			statementSearchCondition.setIp100("X");
			statementSearchCondition.setIs100("X");
			statementSearchCondition.setIm100("X");
			
			switch (quarter) {
			case 1:
				statementSearchCondition.setIfspmon(thisYear + ".01.01");
				statementSearchCondition.setItspmon(thisYear + ".03.31");
				break; //1분기
			case 2:
				statementSearchCondition.setIfspmon(thisYear + ".04.01");
				statementSearchCondition.setItspmon(thisYear + ".06.30");
				break; //2분기
			case 3:
				statementSearchCondition.setIfspmon(thisYear + ".07.01");
				statementSearchCondition.setItspmon(thisYear + ".09.30");
				break; //3분기
			case 4:
				statementSearchCondition.setIfspmon(thisYear + ".10.01");
				statementSearchCondition.setItspmon(thisYear + ".12.31");
				break; //4분기							
			}
		}
		
		//서치컨디션 유지 
		if(StringUtils.isEmpty(searchConditionString)) {
			searchConditionString = ModelBeanUtil.makeSearchConditionString(statementSearchCondition,
					"ip100",
					"is100",
					"im100",
					"ifspmon",
					"itspmon",
					"iracct",
					"iuname",
					"ineonetacc",
					"quarter",
					"isInit"
			);
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, statementSearchCondition);
		}
		ModelAndView mav = new ModelAndView( "/portal/moorimess/statementCommon/budgetList");
				
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		//YYYYMM 형식 6자리로 세팅
		if(!("".equals(statementSearchCondition.getIfspmon()))&&statementSearchCondition.getIfspmon()!=null){
			sMonth = statementSearchCondition.getIfspmon().replaceAll("\\.","").substring(0,6);
		}		
		if(!("".equals(statementSearchCondition.getItspmon()))&&statementSearchCondition.getItspmon()!=null){
			eMonth = statementSearchCondition.getItspmon().replaceAll("\\.","").substring(0,6);
		}
		
		//statement 파라메터 세팅
		StatementDetail statement = new StatementDetail();
		
		statement.setIm100(statementSearchCondition.getIm100());	
		statement.setIs100(statementSearchCondition.getIs100());
		statement.setIp100(statementSearchCondition.getIp100());
		statement.setIfspmon(sMonth);								//검색시작월
		statement.setItspmon(eMonth);								//검색종료월
		//계정 과목 검색시 6자리인 경우 앞에 00을 붙여서 RFC를 호출한다.
		if(statementSearchCondition.getIracct().length()==8){		//계정과목	
			statement.setIracct("00"+statementSearchCondition.getIracct()); 
		}else{
			statement.setIracct(statementSearchCondition.getIracct());	
		}
		statement.setIuname(user.getSapId());						//사용자 마스터 레코드의 사용자 이름
		statement.setIneonetacc("");								//네오넷 계정 아이디

		List<StatementDetail> statementList = epStatementRcv.EpStatementBudgetListDataRcvRFC(statement, null);
		List<StatementDetail> kstarList = epStatementRcv.EpStatementKstarListDataRcvRFC(statement, null);
		
		
		mav.addObject("thisYear",thisYear);
		mav.addObject("quarter",quarter);
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("statementList", statementList);
		mav.addObject("kstarList", kstarList);
		//mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/statementAllView.do")
	public ModelAndView statementAllView(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/statementCommon/statementAllView");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		//mav.addObject("Bigmenu", "payDiligenceMng");
		//mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/budgetView.do")
	public ModelAndView budgetView(HttpServletRequest request,
								   @RequestParam(value = "ibukrs", required = true) String ibukrs, 
								   @RequestParam(value = "ispmon", required = true) String ispmon, 
								   @RequestParam(value = "iracct", required = true) String iracct, 
								   @RequestParam(value = "irzzkostl", required = true) String irzzkostl, 
								   @RequestParam(value = "ircntr", required = true) String ircntr) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/statementCommon/budgetView");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		
		//statement 파라메터 세팅
		StatementDetail statement = new StatementDetail();
		
		statement.setIbukrs(ibukrs);
		statement.setIspmon(ispmon);
		statement.setIracct(iracct);
		statement.setIrzzkostl(irzzkostl);
		statement.setIrcntr(ircntr);
				
		List<StatementDetail> statementList = epStatementRcv.EpStatementBudgetViewDataRcvRFC(statement, null);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("statementList", statementList);
		//mav.addObject("serverLinkUrl", getLinkPath(request));
		return mav;
	}
	
	@RequestMapping(value = "/statementView.do")
	public ModelAndView statementView(@RequestParam(value = "belnr", required = true) String belnr, 
			@RequestParam(value = "bukrs", required = true) String bukrs, 
			@RequestParam(value = "gjahr", required = true) String gjahr, 
			@RequestParam(value = "belnr2", required = true) String belnr2, 
			@RequestParam(value = "cardno", required = true) String cardno, 
			@RequestParam(value = "reqnamt", required = true) String reqnamt, 
			@RequestParam(value = "dmbtr", required = true) String dmbtr, 
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/moorimess/statementCommon/statementView");
		
		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		StatementDetail statement = new StatementDetail();
		statement.setBelnr(belnr);
		statement.setBukrs(bukrs);
		statement.setGjahr(gjahr);
		List<StatementDetail> statementList1 =  epStatementRcv.EpStatementDetailDataRcvRFC(statement, null);
		List<StatementDetail> statementList2 = null;
		if(belnr2 != ""){
			statement.setBelnr(belnr2);
			statement.setBukrs(bukrs);
			statement.setGjahr(gjahr);
			statementList2 =  epStatementRcv.EpStatementDetailDataRcvRFC(statement, null);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("statementList1", statementList1);
		mav.addObject("statementList2", statementList2);
		mav.addObject("cardno", cardno);
		mav.addObject("reqnamt", reqnamt);
		mav.addObject("dmbtr", dmbtr);
		return mav;
	}
	
	@RequestMapping(value = "/statementConfirm.do")
	public @ResponseBody
	void statementConfirm(
			@RequestParam(value = "confm", required = true) String confm, 
			@RequestParam("stdId") String[] stdIds, 
			@RequestParam("ztexts") String[] ztexts, 
			@RequestParam("revs") String[] revs, 
			HttpServletRequest request) {

		User user = (User) getRequestAttribute("ikep.user");
		StatementDetail statement = new StatementDetail();
		statement.setIp100("X");
		statement.setIs100("X");
		statement.setIm100("X");
		statement.setIuname(user.getSapId());
		statement.setIreqt("X");
		statement.setIreqn("A");
		statement.setIuname2("");
		statement.setIreqgubun("1");
		statement.setZapp("B");
		statement.setIfname("REQNAM_T");
		statement.setIstate("A");
		try{
		epStatementRcv.EpStatementConfirmDataRcvRFC(statement, stdIds, confm, ztexts, revs, null);
		}catch(Exception exception){
			
		}
	}
	
	@RequestMapping("/mandateView.do")
	public ModelAndView mandateView() {
		
		ModelAndView mav = new ModelAndView("/portal/moorimess/statementCommon/mandate");
		
		User user = (User) getRequestAttribute("ikep.user");

		//statement 파라메터 세팅
		StatementDetail statement = new StatementDetail();
		
		statement.setIuname(user.getSapId());
		statement.setIuname2("");
		statement.setIreqgubun("1");
		statement.setZapp("A");
		
		List<StatementDetail> statementList = epStatementRcv.EpStatementMandateDataRcvRFC(statement, null);
		
		//화면으로 return 해줄 model
		StatementDetail statementDetail = new StatementDetail();
		//위임자가 있을 경우에만 세팅
		if(statementList.size()!=0){
			//코스트명 
			String kostl = "";
			// SAP에서 조회한 위임자 SAPID로 위임자의 이름,부서를 디비에서 조회
			if(statementList.get(0).getUname()!=null&&(!"".equals(statementList.get(0).getUname()))){
				User mUser = service.selectMandatorInfo(statementList.get(0));
				mUser.setSapId(statementList.get(0).getUname());

				statementDetail.setRfcUserName(mUser.getUserName());
				statementDetail.setMandatorDept(mUser.getTeamName());
				
				//화면에 코스트명 디스플레이를 위한 세팅
				for(int i=0;i<statementList.size();i++){
					String ibukrs ="";
					if("M100".equals(statementList.get(i).getBukrs())){
						ibukrs = "SP";
					}else if("S100".equals(statementList.get(i).getBukrs())){
						ibukrs = "MP";
					}else if("P100".equals(statementList.get(i).getBukrs())){
						ibukrs = "P&P";
					}
					
					if(i==0){
						kostl += ibukrs+"-"+statementList.get(i).getKostlt();
					}else{
						kostl += "/"+ibukrs+"-"+statementList.get(i).getKostlt();
					}
				}
				
				statementDetail.setUname(statementList.get(0).getUname());
				statementDetail.setUname2date(statementList.get(0).getUname2date());
				statementDetail.setKostlt(kostl);
				//전표 결제 권한이 있는 경우 
				statementDetail.setIsRight("true");
			}else{
				//위임자가 없는 경우
				statementDetail = null;
			}
		}else{
			//전표 결제 권한이 없는경우
			statementDetail.setIsRight("false");
		}
		mav.addObject("statementDetail",statementDetail);
		return mav;
	}
	
	@RequestMapping("/mandateEditForm.do")
	public ModelAndView mandateEditForm() {
		ModelAndView mav =  new ModelAndView("/portal/moorimess/statementCommon/mandateEditForm");
		
		User user = (User) getRequestAttribute("ikep.user");

		//statement 파라메터 세팅
		StatementDetail statement = new StatementDetail();
		
		statement.setIuname(user.getSapId());
		statement.setIuname2("");
		statement.setIreqgubun("1");
		statement.setZapp("A");
		
		List<StatementDetail> statementList = epStatementRcv.EpStatementMandateDataRcvRFC(statement, null);
		
		//코스트명 
		String kostl = "";
		//화면으로 return 해줄 model
		StatementDetail statementDetail = new StatementDetail();
		
		// SAP에서 조회한 위임자 SAPID로 위임자의 이름,부서를 디비에서 조회
		if(statementList.get(0).getUname()!=null&&(!"".equals(statementList.get(0).getUname()))){
			
			User mUser = service.selectMandatorInfo(statementList.get(0));
			mUser.setSapId(statementList.get(0).getUname());

			statementDetail.setRfcUserName(mUser.getUserName());
			statementDetail.setMandatorDept(mUser.getTeamName());
			
			//화면에 코스트명 디스플레이를 위한 세팅
			for(int i=0;i<statementList.size();i++){
				String ibukrs ="";
				if("M100".equals(statementList.get(i).getBukrs())){
					ibukrs = "SP";
				}else if("S100".equals(statementList.get(i).getBukrs())){
					ibukrs = "MP";
				}else if("P100".equals(statementList.get(i).getBukrs())){
					ibukrs = "P&P";
				}
				
				if(statementList.get(0).getKostlt()!=null&&(!"".equals(statementList.get(0).getKostlt()))){
					if(i==0){
						kostl += ibukrs+"-"+statementList.get(i).getKostlt();
					}else{
						kostl += "/"+ibukrs+"-"+statementList.get(i).getKostlt();
					}
				}
			}
			
			statementDetail.setUname(statementList.get(0).getUname());
			statementDetail.setUname2date(statementList.get(0).getUname2date());
			statementDetail.setKostlt(kostl);
		}

		mav.addObject("statementList",statementList);
		mav.addObject("statementDetail",statementDetail);
		return mav;
	}
	
	/** 위임자 등록/해제 */
	@RequestMapping("/mandateCreate")
	public ModelAndView mandateCreate(StatementDetail statement) {
		User user = (User) getRequestAttribute("ikep.user");
		String trusteeId = statement.getRfcUserId();
		String portalId = user.getPortalId();
		String gubun = statement.getIreqgubun();
		
		//위임자 RFC FUNCTION 파라메터 세팅
		statement.setIuname(user.getSapId());
		statement.setIuname2(statement.getUname());
		try {
			service.createMandator(user, portalId, trusteeId, gubun);
			epStatementRcv.EpStatementMandateEditDataRcvRFC(statement, null);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return new ModelAndView("redirect:/portal/moorimess/statementCommon/mandateView.do");
	}
	
	/**
	 * 권한자 임원/팀장인지 여부 
	 * @return
	 */
	@ModelAttribute("isMaster")
	public boolean isMaster(){
        
		User user = (User) getRequestAttribute("ikep.user");
        
        return service.isMaster(user);
        
	}
}
