/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.profile.model.Career;
import com.lgcns.ikep4.support.profile.service.CareerService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 개인 경력의 입력 조회용
 *
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: CareerController.java 16849 2011-10-20 07:47:08Z giljae $
 */
@Controller
@RequestMapping(value = "/support/career")
@SessionAttributes("career")
public class CareerController extends BaseController {
	
	@Autowired
	private CareerService careerService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private GroupService groupService;

	
	/**
	 * 경력 조회용 팝업
	 * @param targetUserId 프로파일 대상자 ID
	 * @return 경력 조회 팝업 페이지 리턴
	 */
	@RequestMapping(value = "/readCareerPopup.do")
	public ModelAndView readCareerPopup(@RequestParam("targetUserId") String targetUserId) {
		
		ModelAndView mav = new ModelAndView("/support/career/careerpopup");
		mav.addObject("targetUserId", targetUserId);
		
		return mav;
	}
	
	/**
	 * 경력 리스트 형태로 조회 하기 위한 메서드
	 * @param targetUserId 프로파일 대상자 ID
	 * @return 경력 목록형 조회 페이지
	 */
	@RequestMapping(value = "/getCareerList.do")
	public ModelAndView getCareerList(@RequestParam("targetUserId") String targetUserId) {
		
		ModelAndView mav = new ModelAndView("/support/career/careerlist");

		Career career = new Career();
		career.setRegisterId(targetUserId);
		List<Career> careerList = careerService.list(career);
		
		mav.addObject("careerList", careerList);
		mav.addObject("targetUserId", targetUserId);
		
		return mav;
	}
	
	
	/**
	 * 경력 카드 형태로 조회 하기 위한 메서드
	 * @param targetUserId 프로파일 대상자 ID
	 * @return 경력 카드형 조회 페이지
	 */
	@RequestMapping(value = "/getCareerCard.do")
	public ModelAndView getCareerCard(@RequestParam("targetUserId") String targetUserId) {

		User user = (User) getRequestAttribute("ikep.user");
		
		ModelAndView mav = new ModelAndView("/support/career/careercard");

		Career career = new Career();
		career.setRegisterId(targetUserId);
		List<Career> careerList = careerService.list(career);
	
		User profile = userService.read(targetUserId);
		mav.addObject("profile", profile);
		mav.addObject("careerList", careerList);
		mav.addObject("targetUserId", targetUserId);
		
		// 팀 조회
		Map<String, Object> groupmap = new HashMap<String, Object>();
		groupmap.put("userId", targetUserId);
		groupmap.put("localeCode", user.getLocaleCode());
		groupmap.put("isRoot", "true");
		List<Group> reportLineList = groupService.selectUserGroupWithHierarchy(groupmap);
		//mav.addObject("reportLine", reportLineList);
		String userTeamName = "";
		String userTeamEnglishName = "";
		for(int i = 0 ; i < reportLineList.size();i++ ){
			userTeamName += reportLineList.get(i).getGroupName() ;
			userTeamEnglishName += reportLineList.get(i).getGroupEnglishName() ;
			if( i != (reportLineList.size()-1)){
				userTeamName += " ";
				userTeamEnglishName += " ";
			}
		}
		mav.addObject("userTeamName", userTeamName);
		mav.addObject("userTeamEnglishName", userTeamEnglishName);
		
		return mav;
	}


	/**
	 * 경력 개별 건별 조회 용 페이지 
	 * @param careerId 경력 ID
	 * @param type CREATE 입력용, UPDATE 수정용
	 * @return
	 */
	@RequestMapping(value = "/getCareerView.do")
	public ModelAndView getCareerView(@RequestParam("careerId") String careerId
									, @RequestParam("type") String type) {
		
		ModelAndView mav = new ModelAndView("/support/career/careerview");

		Career career = careerService.read(careerId);
		
		mav.addObject("career", career);
		mav.addObject("type", type);
		mav.addObject("targetUserId", career.getRegisterId());
		
		return mav;
	}
	
	/**
	 * 입력 및 수정된 경력 정보를 저장 수정 하기 위한 입력창 요청 페이지
	 * @param careerId 경력 Id
	 * @return 입력 창 또는 수정 창 페이지 
	 */
	@RequestMapping(value = "/editCareer.do")
	public ModelAndView editCareer(@RequestParam("careerId") String paramCareerId) {
		
		String type = "CREATE";
		String viewName = "";
		String careerId = paramCareerId;

		Career career = new Career();
		
		if ( !(StringUtil.isEmpty(careerId)) ){
			career = careerService.read(careerId);
			type = "UPDATE";
			viewName = "/support/career/careeredit";
		}else{
			try {
				careerId = idgenService.getNextId();
				career.setCareerId(careerId);
				type = "CREATE";
				viewName = "";
			} catch (Exception e) {
				throw new IKEP4AjaxException("", e);
			}
			viewName = "/support/career/careercreate";
		}
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("career", career);
		mav.addObject("type", type);

		return mav;
	}
	

	/**
	 * 해당 경력 정보를 수정이나 신규 입력 데이타를 저장한다.
	 * @param career 경력 정보 객체
	 * @param result 경력정보 Validation 결과
	 * @param status SessionStatus 값
	 * @return 경력 정보 저장된 결과
	 */
	@RequestMapping(value = "/saveCareer.do")
	public @ResponseBody String saveCareer( @Valid Career career, BindingResult result, SessionStatus status) {
		

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource); 
		}
		
		// Career 타임존 예외
		//career.setWorkStartDate( timeZoneSupportService.convertTimeZone(career.getWorkStartDate()));
		//if( career.getWorkEndDate() != null ){
		//	career.setWorkEndDate( timeZoneSupportService.convertTimeZone(career.getWorkEndDate()));
		//}
		
		String careerId = career.getCareerId();
		if ( careerService.exists(careerId) ){
			careerService.update(career);
		}else{
			if ( !(StringUtil.isEmpty(careerId)) ){
				try {
					careerId = idgenService.getNextId();
					career.setCareerId(careerId);
				} catch (Exception e) {
					throw new IKEP4AjaxException("", e);
				}
			}
			careerService.create(career);
		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();

		return careerId;
	}

	/**
	 * 해당 경력 정보를 삭제 한다.
	 * @param careerId 경력 ID
	 * @param status SessionStatus 값
	 * @return 경력 삭제 결과
	 */
	@RequestMapping(value = "/deleteCareer.do")
	public @ResponseBody String deleteCareer(@RequestParam("careerId") String careerId, SessionStatus status) {
		
		careerService.delete(careerId);


		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();

		return "deleteOk";
		
	}
	
	/**
	 * 경력정보 다운로드 전 경력 정보 존재 유무 확인 메서드
	 * @param targetUserId 경력 대상자 ID
	 * @return 경력 정보 수
	 */
	@RequestMapping(value = "/chkDownloadExcelCareer.do")
	public @ResponseBody String downloadExcelCareer(@RequestParam("targetUserId") String targetUserId) {
		
		Integer downloadRowCnt = 0;

		Career career = new Career();
		career.setRegisterId(targetUserId);
		List<Career> careerList = careerService.list(career);
		
		if( careerList != null && careerList.size() > 0 ){
			downloadRowCnt = careerList.size();
		}

		return String.valueOf(downloadRowCnt);
		
	}
	
	/**
	 * 경력 정보 엑셀 데이타 다운로드
	 * @param targetUserId 경력 대상자 ID
	 * @param response 엑셀 파일 다운로드
	 */
	@RequestMapping(value = "/downloadExcelCareer.do")
	public void downloadExcelCareer(@RequestParam("targetUserId") String targetUserId
									, HttpServletResponse response) {
		
		User careerUser = userService.read(targetUserId);
		
		Career career = new Career();
		career.setRegisterId(targetUserId);
		List<Career> careerList = careerService.list(career);
		
		String fileName = careerUser.getUserId()+"_" + careerUser.getUserName() + "_" + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xlsx";

		if( careerList.size() > 0 ) {

			List<Object> excelDownloadList = new ArrayList<Object>();
			for (Career excelCareer : careerList) {
				excelCareer.setStrWorkStartDate(DateUtil.getFmtDateString(excelCareer.getWorkStartDate(),"yyyy.MM.dd"));
				excelCareer.setStrWorkEndDate(DateUtil.getFmtDateString(excelCareer.getWorkEndDate(),"yyyy.MM.dd"));
				excelDownloadList.add(excelCareer);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
			//titleMap.put("registerName", "registerName");
			titleMap.put("strWorkStartDate", "workStartDate");
			titleMap.put("strWorkEndDate", "workEndDate");
			titleMap.put("companyName", "companyName");
			titleMap.put("roleName", "roleName");
			titleMap.put("workChange", "workChange");
			
			// 파일 저장
			ExcelUtil.saveExcel(titleMap, excelDownloadList, fileName, response, careerUser.getUserId());
		}

	}	


}
