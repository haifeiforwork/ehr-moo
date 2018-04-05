/* 
f * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.searchpreprocessor.util.PageCons;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.survey.model.Code;
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;
import com.lgcns.ikep4.servicepack.survey.model.RequestLog;
import com.lgcns.ikep4.servicepack.survey.model.SendLog;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyJobDetailBean;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.servicepack.survey.model.Target;
import com.lgcns.ikep4.servicepack.survey.model.TargetKey;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;
import com.lgcns.ikep4.servicepack.survey.service.JobService;
import com.lgcns.ikep4.servicepack.survey.service.QuestionGroupService;
import com.lgcns.ikep4.servicepack.survey.service.QuestionService;
import com.lgcns.ikep4.servicepack.survey.service.RequestLogService;
import com.lgcns.ikep4.servicepack.survey.service.ResponseService;
import com.lgcns.ikep4.servicepack.survey.service.SendLogService;
import com.lgcns.ikep4.servicepack.survey.service.SurveyService;
import com.lgcns.ikep4.servicepack.survey.util.AuthUtils;
import com.lgcns.ikep4.servicepack.survey.util.MagicNumUtils;
import com.lgcns.ikep4.servicepack.survey.util.SurveyConstance;
import com.lgcns.ikep4.servicepack.survey.validation.groups.Create;
import com.lgcns.ikep4.servicepack.survey.validation.groups.Reject;
import com.lgcns.ikep4.servicepack.survey.validation.groups.Update;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 설문
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SurveyController.java 16244 2011-08-18 04:11:42Z giljae $
 */

@Controller
@SessionAttributes("searchCondition")
@RequestMapping(value = "/servicepack/survey")
public class SurveyController extends BaseSurveyController {

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private SurveyService surveyService;

	@Autowired
	private FileService fileService;

	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private SendLogService sendLogService;

	// 문항그룹
	@Autowired
	private QuestionGroupService questionGroupService;

	// 문항
	@Autowired
	private QuestionService questionService;

	@Autowired
	private RequestLogService requestLogService;

	@Autowired
	private ResponseService responseService;

	@Autowired
	private JobService jobService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/index")
	public ModelAndView index() {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/servicepack/survey/index");
		mav.addObject("user", user);
		return mav;
	}

	/**
	 * 설문신청목록 (작성자일경우 작성자꺼만 관리자일경우에는 모두 가져옮)
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/surveyList")
	public ModelAndView surveyList(SurveySearchCondition searchCondition, BindingResult result, SessionStatus status,
			ModelMap model, HttpServletRequest request) {
		User user = (User) getRequestAttribute("ikep.user");

		SurveySearchCondition localSearchCondition = getSearchCondition(searchCondition, model, request);

		if (localSearchCondition.getSurveyStatus() != null && localSearchCondition.getSurveyStatus() < 0) {
			localSearchCondition.setSurveyStatus(null); // 전체목록
		}
		boolean isAdmin = isAdmin();

		// 관리자가 아니면 작성자가 작성한것에 대한 목록만 가져옴
		if (!isAdmin) {
			localSearchCondition.setRegisterId(user.getUserId());
		}
		
		localSearchCondition.setPortalId( user.getPortalId() );

		SearchResult<Survey> searchResult = surveyService.listBySearchCondition(localSearchCondition);

		ModelAndView mav = new ModelAndView("/servicepack/survey/surveyList");
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());

		getCodeList(mav);

		List<Code<Integer>> surveyStatusList = new ArrayList<Code<Integer>>();
		surveyStatusList.add(new Code<Integer>(-1, "ui.servicepack.survey.list.surveyStatus.all"));
		surveyStatusList.add(new Code<Integer>(0, "ui.servicepack.survey.list.surveyStatus.writing"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_1, "ui.servicepack.survey.list.surveyStatus.wait"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_2, "ui.servicepack.survey.list.surveyStatus.reject"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_3, "ui.servicepack.survey.list.surveyStatus.ansering"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_4, "ui.servicepack.survey.list.surveyStatus.end"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_5, "ui.servicepack.survey.list.surveyStatus.approve"));
		mav.addObject("surveyStatusList", surveyStatusList);

		return mav;
	}

	/**
	 * 리스트 검색조건 유지
	 * @param searchCondition
	 * @param model
	 * @param request
	 * @return
	 */
	private SurveySearchCondition getSearchCondition(SurveySearchCondition searchCondition, ModelMap model,
			HttpServletRequest request) {
		if (model.containsKey("searchCondition") && !StringUtils.hasText(searchCondition.getRedirect())) {
			String url = request.getRequestURI();
			SurveySearchCondition sessionSearchCondition = (SurveySearchCondition) model.get("searchCondition");
			if (url.indexOf(sessionSearchCondition.getRedirect() + ".do") >= 0) {
				return sessionSearchCondition;
			}
		}
		
		return searchCondition;
	}

	/**
	 * 설문 신청리스트 (설문신청 리스트는 작성자와 관리자가 뷰가능)
	 * 
	 * @param searchCondition
	 * @param result
	 * @param erroMessage
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/surveyIngList")
	public ModelAndView surveyIngList(SurveySearchCondition searchCondition, BindingResult result, String erroMessage,
			SessionStatus status, ModelMap model, HttpServletRequest request) {
		User user = (User) getRequestAttribute("ikep.user");

		SurveySearchCondition localSearchCondition = getSearchCondition(searchCondition, model, request);

		localSearchCondition.setSurveyStatus(SurveyStatus.ANSERING.getCode()); // 진행중인것만
																			// 조회
		localSearchCondition.setResponseUserId(user.getUserId()); // 참여자 아이디가 없는경우
		localSearchCondition.setPortalId( user.getPortalId() );

		SearchResult<Survey> searchResult = surveyService.ingListBySearchCondition(localSearchCondition);

		ModelAndView mav = new ModelAndView("/servicepack/survey/surveyIngList");
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("erroMessage", erroMessage);

		getCodeList(mav);

		return mav;
	}

	/**
	 * 설문 종료리스트 (내가 참여한 설문 목록)
	 * 
	 * @param searchCondition
	 * @param result
	 * @param erroMessage
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/surveyEndList")
	public ModelAndView surveyEndList(SurveySearchCondition searchCondition, BindingResult result, String erroMessage,
			SessionStatus status, ModelMap model, HttpServletRequest request) {
		User user = (User) getRequestAttribute("ikep.user");
		boolean isSystemAdmin = aclService.isSystemAdmin(SurveyConstance.ITEM_TYPE_CODE, user.getUserId());

		SurveySearchCondition localSearchCondition = getSearchCondition(searchCondition, model, request);

		if (localSearchCondition.getSurveyStatus() != null && localSearchCondition.getSurveyStatus() < 0) {
			localSearchCondition.setSurveyStatus(null); // 전체목록
		}

		localSearchCondition.setResponseUserId(user.getUserId()); // 참여자아이디가 있는경우
		localSearchCondition.setAdmin(isSystemAdmin);

		localSearchCondition.setSortColumn("s.survey_id");
		localSearchCondition.setSortType("desc");
		
		localSearchCondition.setPortalId( user.getPortalId() );

		SearchResult<Survey> searchResult = surveyService.endListBySearchCondition(localSearchCondition);

		ModelAndView mav = new ModelAndView("/servicepack/survey/surveyEndList");
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("erroMessage", erroMessage);

		getCodeList(mav);

		List<Code<Integer>> surveyStatusList = new ArrayList<Code<Integer>>();
		surveyStatusList.add(new Code<Integer>(-1, "ui.servicepack.survey.list.surveyStatus.all"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_3, "ui.servicepack.survey.list.surveyStatus.ansering"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_4, "ui.servicepack.survey.list.surveyStatus.end"));
		mav.addObject("surveyStatusList", surveyStatusList);

		return mav;
	}

	/**
	 * 설문 승인 리스트(관리자가 승인하는 리스트)
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/approve/approveList")
	public ModelAndView approveList(SurveySearchCondition searchCondition, BindingResult result, SessionStatus status,
			ModelMap model, HttpServletRequest request) {
		User user = (User) getRequestAttribute("ikep.user");

		SurveySearchCondition localSearchCondition = getSearchCondition(searchCondition, model, request);

		if (localSearchCondition.getSurveyStatus() != null && localSearchCondition.getSurveyStatus() < 0) {
			localSearchCondition.setSurveyStatus(null); // 전체목록
		}

		boolean isAdmin = isAdmin();

		// 관리자가 아니면 작성자가 작성한것에 대한 목록만 가져옴
		if (!isAdmin) {
			localSearchCondition.setApproverId(user.getUserId());
		}
		
		localSearchCondition.setPortalId( user.getPortalId() );

		SearchResult<Survey> searchResult = surveyService.approveListBySearchCondition(localSearchCondition);

		ModelAndView mav = new ModelAndView("/servicepack/survey/approve/approveList");
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());

		getCodeList(mav);

		List<Code<Integer>> surveyStatusList = new ArrayList<Code<Integer>>();
		surveyStatusList.add(new Code<Integer>(-1, "ui.servicepack.survey.list.surveyStatus.all"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_1, "ui.servicepack.survey.list.surveyStatus.wait"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_2, "ui.servicepack.survey.list.surveyStatus.reject"));
		surveyStatusList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_5, "ui.servicepack.survey.list.surveyStatus.approve"));
		mav.addObject("surveyStatusList", surveyStatusList);

		return mav;
	}

	/**
	 * 설문 상세 뷰
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/readSurvey", method = RequestMethod.GET)
	public ModelAndView readSurvey(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");
		boolean isSystemAdmin = aclService.isSystemAdmin(SurveyConstance.ITEM_TYPE_CODE, user.getUserId());

		Survey survey = surveyService.read(surveyId);
		
		// 설문 머릿말 이미지 
		String fileId = "";
		if(survey.getContentsType()==1){
			List<FileData> fileDataList = this.fileService.getItemFile(surveyId, "N");
			if(fileDataList!=null && fileDataList.size()>0) {
				fileId = fileDataList.get(0).getFileId();
			}
		}
		
		List<QuestionGroup> questionGroupList = questionGroupService.getAllBySurveyId(surveyId);

		for (QuestionGroup questionGroup : questionGroupList) {
			questionGroup.setQuestionList(questionService.getAllByQuestionGroupId(questionGroup.getQuestionGroupId()));
		}

		List<RequestLog> requestLogList = requestLogService.selectListBySurveyId(surveyId);

		Integer responseCnt = responseService.responseAllCount(surveyId);

		ModelAndView mav = new ModelAndView("/servicepack/survey/readSurvey");
		mav.addObject("survey", survey);
		mav.addObject("fileId", fileId);
		mav.addObject("questionGroupList", questionGroupList);
		mav.addObject("user", user);
		mav.addObject("surveyAuth", new AuthUtils(survey, user, isSystemAdmin));
		mav.addObject("requestLogList", requestLogList);
		mav.addObject("responseCnt", responseCnt);
		getCodeList(mav);

		return mav;
	}

	/**
	 * 설문 복사
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/copySurvey", method = RequestMethod.POST)
	public String copySurvey(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");

		Survey survey = surveyService.read(surveyId);
		if(user.getLocaleCode().toUpperCase().equals("KO")) {
			survey.setTitle("Copy-" + survey.getTitle());
		} else {
			survey.setTitle("Copy-" + survey.getTitle());
		}
		survey.setPortalId(user.getPortalId());
		survey.setRegistDate(new Date());
		survey.setRegisterId(user.getUserId());
		survey.setRegisterName(user.getUserName());
		survey.setUpdateDate(new Date());
		survey.setUpdaterId(user.getUserId());
		survey.setUpdaterName(user.getUserName());
		survey.setSurveyStatus(SurveyStatus.WRITING.getCode());

		surveyService.copy(survey);

		return "redirect:/servicepack/survey/surveyList.do";
	}

	/**
	 * 설문 문항 작성시 설문 상세보기 팝업
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/previewSurvey", method = RequestMethod.GET)
	public ModelAndView previewSurvey(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");

		Survey survey = surveyService.read(surveyId);
		
		// 설문 머릿말 이미지 
		String fileId = "";
		if(survey.getContentsType()==1){
			List<FileData> fileDataList = this.fileService.getItemFile(surveyId, "N");
			if(fileDataList!=null && fileDataList.size()>0) {
				fileId = fileDataList.get(0).getFileId();
			}
		}
		
		List<QuestionGroup> questionGroupList = questionGroupService.getAllBySurveyId(surveyId);

		for (QuestionGroup questionGroup : questionGroupList) {
			questionGroup.setQuestionList(questionService.getAllByQuestionGroupId(questionGroup.getQuestionGroupId()));
		}

		ModelAndView mav = new ModelAndView("/servicepack/survey/previewSurvey");
		mav.addObject("survey", survey);
		mav.addObject("fileId", fileId);
		mav.addObject("questionGroupList", questionGroupList);
		mav.addObject("user", user);
		getCodeList(mav);

		return mav;
	}

	/**
	 * 설문 승인 상세뷰
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/approve/readApprove", method = RequestMethod.GET)
	public ModelAndView readApprove(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");

		Survey survey = surveyService.read(surveyId);
		// 설문 머릿말 이미지 
		String fileId = "";
		if(survey.getContentsType()==1){
			List<FileData> fileDataList = this.fileService.getItemFile(surveyId, "N");
			if(fileDataList!=null && fileDataList.size()>0) {
				fileId = fileDataList.get(0).getFileId();
			}
		}
		
		List<QuestionGroup> questionGroupList = questionGroupService.getAllBySurveyId(surveyId);

		for (QuestionGroup questionGroup : questionGroupList) {
			questionGroup.setQuestionList(questionService.getAllByQuestionGroupId(questionGroup.getQuestionGroupId()));
		}

		ModelAndView mav = new ModelAndView("/servicepack/survey/approve/readApprove");
		mav.addObject("survey", survey);
		mav.addObject("fileId", fileId);
		mav.addObject("questionGroupList", questionGroupList);
		mav.addObject("user", user);
		getCodeList(mav);

		return mav;
	}

	/**
	 * 설문 삭제
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/deleteSurvey", method = RequestMethod.POST)
	public String deleteSurvey(@RequestParam(value = "surveyId", required = true) String surveyId) {
		surveyService.delete(surveyId);
		return "redirect:/servicepack/survey/surveyList.do";
	}

	/**
	 * 설문 작성 초기 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createSurvey", method = RequestMethod.GET)
	public ModelAndView createSurvey() {

		ModelAndView mav = new ModelAndView("/servicepack/survey/createSurvey");

		try {
			User user = (User) getRequestAttribute("ikep.user");
			Survey survey = new Survey(0, 0, 0, 0, 0, 0);

			mav.addObject("survey", survey);
			mav.addObject("user", user);
			getCodeList(mav);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav;
	}

	/**
	 * 설문 작성
	 * 
	 * @param survey
	 * @param result
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createSurveySubmit", method = RequestMethod.POST)
	public ModelAndView createSurveySubmit(Survey survey, BindingResult result, HttpServletRequest request,
			SessionStatus status) {
		ModelAndView mav = new ModelAndView("/servicepack/survey/createSurvey");

		User user = (User) getRequestAttribute("ikep.user");
		mav.addObject("user", user);

		try {
			// surveyId 생성
			String surveyId = idgenService.getNextId();
			survey.setSurveyId(surveyId);
			survey.setPortalId(user.getPortalId());
			survey.setRegistDate(new Date());
			survey.setRegisterId(user.getUserId());
			survey.setRegisterName(user.getUserName());
			survey.setUpdateDate(new Date());
			survey.setUpdaterId(user.getUserId());
			survey.setUpdaterName(user.getUserName());
			survey.setSurveyStatus(SurveyStatus.WRITING.getCode());
			if(survey.getContentsType()==1) {
				survey.setContents(" ");
			}
			getExtraData(survey, request, user, surveyId);

			// target valid
			if (survey.getSurveyTarget() != SurveyConstance.SURVEY_DEFAULT_TARGET_TYPE
					&& survey.getTargetList().size() <= 0 && survey.getTargetGroupList().size() <= 0) {
				result.rejectValue("surveyTarget", "Digits.survey.surveyTarget");
			}
			
			//System.out.println("^^^^^^^^^^^^^^^^^^");
			//System.out.print(survey.getTargetList().size());
			
			if (!isValid(result, survey, Create.class)) {
				getCodeList(mav);
				mav.addObject("survey", survey);
				return mav;
			}

			/**
			 * 설문 시작일자 종료일자를 서버타임으로 바꿔준다.
			 */
			survey.setStartDate(timeZoneSupportService.convertServerTimeZone(survey.getStartDate()));
			survey.setEndDate(timeZoneSupportService.convertServerTimeZoneEndDate(survey.getEndDate(), "survey"));
	
			surveyService.create(survey);

			status.setComplete();

			mav.setViewName("redirect:/servicepack/survey/question/createQuestion.do?surveyId=" + survey.getSurveyId());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		mav.addObject("survey", survey);
		getCodeList(mav);
		return mav;
	}

	/**
	 * 설문 수정 초기
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/updateSurvey", method = RequestMethod.GET)
	public ModelAndView updateSurvey(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");

		Survey survey = surveyService.read(surveyId);
		// 설문 머릿말 이미지 
		String fileId = "";
		if(survey.getContentsType()==1){
			List<FileData> fileDataList = this.fileService.getItemFile(surveyId, "N");
			if(fileDataList!=null && fileDataList.size()>0) {
				fileId = fileDataList.get(0).getFileId();
			}
		}
		ModelAndView mav = new ModelAndView("/servicepack/survey/updateSurvey");
		mav.addObject("survey", survey);
		mav.addObject("fileId", fileId);
		mav.addObject("user", user);
		getCodeList(mav);

		return mav;
	}

	/**
	 * 설문 수정
	 * 
	 * @param survey
	 * @param result
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateSurveySubmit", method = RequestMethod.POST)
	public ModelAndView updateSurveySubmit(Survey survey, BindingResult result, HttpServletRequest request,
			SessionStatus status) {
		ModelAndView mav = new ModelAndView("/servicepack/survey/updateSurvey");

		User user = (User) getRequestAttribute("ikep.user");
		mav.addObject("user", user);

		try {

			// surveyId 생성
			String surveyId = survey.getSurveyId();
			survey.setUpdateDate(new Date());
			survey.setUpdaterId(user.getUserId());
			survey.setUpdaterName(user.getUserName());
			if(survey.getContentsType()==1) {
				survey.setContents(" ");
			}
			getExtraData(survey, request, user, surveyId);

			// target valid
			if (survey.getSurveyTarget() != SurveyConstance.SURVEY_DEFAULT_TARGET_TYPE
					&& survey.getTargetList().size() <= 0 && survey.getTargetGroupList().size() <= 0) {
				result.rejectValue("surveyTarget", "Digits.survey.surveyTarget");
			}

			if (!isValid(result, survey, Update.class)) {
				getCodeList(mav);
				mav.addObject("survey", survey);
				return mav;
			}

			Survey dbInfo = surveyService.read(surveyId);

			survey.setSurveyStatus(dbInfo.getSurveyStatus());
			survey.setPortalId(dbInfo.getPortalId());

			/**
			 * 설문 시작일자 종료일자를 서버타임으로 바꿔준다.
			 */
			survey.setStartDate(timeZoneSupportService.convertServerTimeZone(survey.getStartDate()));
			survey.setEndDate(timeZoneSupportService.convertServerTimeZoneEndDate(survey.getEndDate(), "survey"));
			
			surveyService.update(survey);

			status.setComplete();

			mav.setViewName("redirect:/servicepack/survey/readSurvey.do?surveyId=" + survey.getSurveyId());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		mav.addObject("survey", survey);
		getCodeList(mav);
		return mav;
	}

	/**
	 * 설문 작성시 설문에 대한 request param정의
	 * 
	 * @param survey
	 * @param request
	 * @param user
	 * @param surveyId
	 * @throws Exception
	 */
	private void getExtraData(Survey survey, HttpServletRequest request, User user, String surveyId) {
		try {
			// file upload
			// 타입이 이미지일경우
			if (survey.getContentsType() != SurveyConstance.SURVEY_DEFAULT_CONTENT_TYPE) {
				MultipartRequest multipartRequest = (MultipartRequest) request;
				List<MultipartFile> fileList = multipartRequest.getFiles("file");
				
				List<FileData> uploadList = fileService.uploadFile(fileList, "", "", user);
 
				for (FileData fileData : uploadList) {
					// 머릿말이 이미지인경우 머릿말을 파일ID로 넣는것을 설문명을 입력?
					// --> 머릿말을 공백으로 임시 처리
					// survey.setContents(fileData.getFileId());
					survey.setContents(" ");
					survey.setFileData(fileData);
					fileService.removeItemFile(survey.getSurveyId());
					fileService.createFileLink(fileData.getFileId(), survey.getSurveyId(),
							IKepConstant.ITEM_TYPE_CODE_SURVEY, user);
					break;
				}

			}

			// 대상자
			if (survey.getSurveyTarget() != SurveyConstance.SURVEY_DEFAULT_TARGET_TYPE) {
				String[] targetList = request.getParameterValues("tmpTargetList");
				List<Target> sTargetList = new ArrayList<Target>();
				List<Target> sTargetGroupList = new ArrayList<Target>();

				// 일반 사용자 id
				if (targetList != null) {
					for (int i = 0; i < targetList.length; i++) {
						
						if( StringUtils.hasText(targetList[i]) )
						{	
							TargetKey key = new TargetKey(surveyId, targetList[i],
									SurveyConstance.SURVEY_DEFAULT_TARGET_USER);
	
							Target target = new Target();
							target.setTargetKey(key);
							target.setRegistDate(new Date());
							target.setRegisterId(user.getUserId());
							target.setRegisterName(user.getUserName());
							target.setUpdateDate(new Date());
							target.setUpdaterId(user.getUserId());
							target.setUpdaterName(user.getUserName());
	
							sTargetList.add(target);
						}	
					}
				}

				survey.setTargetList(sTargetList);

				String[] targetGroupList = request.getParameterValues("tmpTargetGroupList");

				// 그룹 id
				if (targetGroupList != null) {
					for (int i = 0; i < targetGroupList.length; i++) {
						if( StringUtils.hasText(targetGroupList[i]) )
						{	
							TargetKey key = new TargetKey(surveyId, targetGroupList[i],
									SurveyConstance.SURVEY_DEFAULT_TARGET_GROUP);
							Target target = new Target();
							target.setTargetKey(key);
							target.setRegistDate(new Date());
							target.setRegisterId(user.getUserId());
							target.setRegisterName(user.getUserName());
							target.setUpdateDate(new Date());
							target.setUpdaterId(user.getUserId());
							target.setUpdaterName(user.getUserName());
	
							sTargetGroupList.add(target);
	
							log.debug("targetGroupList:" + target);
						}	
					}
				}
				survey.setTargetGroupList(sTargetGroupList);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 승인 페이지
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/approve/approve", method = RequestMethod.GET)
	public ModelAndView approve(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");
		Survey survey = surveyService.read(surveyId);

		List<QuestionGroup> questionGroupList = questionGroupService.getAllBySurveyId(surveyId);

		for (QuestionGroup questionGroup : questionGroupList) {
			questionGroup.setQuestionList(questionService.getAllByQuestionGroupId(questionGroup.getQuestionGroupId()));
		}

		ModelAndView mav = new ModelAndView("/servicepack/survey/approve/approve");
		mav.addObject("survey", survey);
		mav.addObject("questionGroupList", questionGroupList);
		mav.addObject("user", user);

		return mav;
	}
	

	/**
	 * 승인완료
	 * 
	 * @param survey
	 * @param result
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/approve/approveSubmit", method = RequestMethod.POST)
	public String approveSubmit(Survey survey, BindingResult result, HttpServletRequest request, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");
		//System.out.println(request.getParameter("surveyTitle"));
		//survey.setTitle(request.getParameter("surveyTitle") + " 설문에 대한 승인 결과");
		
		survey.setTitle(request.getParameter("surveyTitle"));
		//System.out.println("^^^^^^^^" + survey.getTargetList().size() + "--------");
		// 수정됨 svn에서
		////Survey sry = surveyService.read(survey.getSurveyId());
		
		//System.out.println("^^^^^^^^" + sry.getTargetList().size() + "--------");
		// 수정됨 svn에서
		////survey.setTargetList(sry.getTargetList()); 
		
		//for(int i = 0; i < survey.getTargetList().size(); i++){
		//	System.out.println(survey.getTargetList().get(i).getTargetName());
		//}
		/*
		System.out.println("^^^^^^^^" + sry.getTargetGroupList().size() + "--------");		
		for(int i = 0; i < sry.getTargetGroupList().size(); i++){
			System.out.println("111111111111111");
			System.out.println(sry.getTargetGroupList().get(i).getTargetKey().getTargetId());
			System.out.println("222222222222222");
		}		 
		 */
		// 수정됨 svn에서
		////survey.setTargetGroupList(sry.getTargetGroupList());
		
		//System.out.println("^^^^^^^^" + survey.getTargetGroupList().size() + "--------");		
		//for(int i = 0; i < survey.getTargetGroupList().size(); i++){
		//	System.out.println("111111111111111");
		//	System.out.println(survey.getTargetGroupList().get(i).getTargetKey().getTargetId());
		//	System.out.println("222222222222222");
		//}
		
		
		try {
			// survey.setSurveyStatus( SurveyStatus.WAIT.getCode() ); //승인대기중
			survey.setUpdateDate(new Date());
			survey.setUpdaterId(user.getUserId());
			survey.setUpdaterName(user.getUserName());
			surveyService.approve(survey);

			//System.out.println("======getSendOption=======" + survey.getSendOption());
			// 메일 전송
			if (survey.getSurveyStatus().equals(SurveyStatus.ANSERING.getCode()) && survey.getMailSendYn() == 1) {
			//if (survey.getSurveyStatus().equals(SurveyStatus.ANSERING.getClass()) && survey.getMailSendYn() == 1 && survey.getSendOption() != 0){
				String sendMailOption = "OPEN";
				job(survey, sendMailOption, request);
			}
			//System.out.println("=== Survey Status ANSERING ===" + SurveyStatus.ANSERING.getCode());
			//System.out.println("=== Survey Status ======" + survey.getSurveyStatus());
			//System.out.println("=== MailSendYn =====" + survey.getMailSendYn());
			
			//System.out.println("sendMail 호출전" + survey.getTitle() + "sendMail 호출전");
			//System.out.println(survey.getSurveyId());
			//System.out.println(survey.getUpdateDate().toString());
			sendMail(survey, request, user);
			
			status.setComplete();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		if (survey.getSurveyStatus().equals(SurveyStatus.END.getCode())
				|| survey.getSurveyStatus().equals(SurveyStatus.WAIT.getCode())
				|| survey.getSurveyStatus().equals(SurveyStatus.ANSERING.getCode())){
			return "redirect:/servicepack/survey/surveyList.do";
		}else{
			return "redirect:/servicepack/survey/approve/approveList.do";
		}	
	}



	/**
	 * 승인 반려
	 * 
	 * @param survey
	 * @return
	 */
	@RequestMapping(value = "/approve/rejectSubmit")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Survey rejectSubmit(@ValidEx(groups = { Reject.class }) Survey survey, HttpServletRequest request) {
		User user = (User) getRequestAttribute("ikep.user");
			
		survey.setTitle(request.getParameter("surveyTitle"));
		
		try {
			// survey.setSurveyStatus( SurveyStatus.WAIT.getCode() ); //승인대기중
			survey.setUpdateDate(new Date());
			survey.setUpdaterId(user.getUserId());
			survey.setUpdaterName(user.getUserName());

			surveyService.reject(survey);
			
			sendMail(survey, request, user);
			
		} catch (Exception e) {
			throw new IKEP4AjaxException("code", e);
		}

		return survey;

	}

	/**
	 * 메일 발송 리스트 생성
	 * @param survey
	 * @param request
	 * @param user
	 */
	private void sendMail(Survey survey, HttpServletRequest request, User user) {
		try
		{
			
			//System.out.println("sendMail 호출후"+survey.getTitle()+"sendMail 호출후");
			//관리자에게 상신
			if (survey.getSurveyStatus().equals(SurveyStatus.WAIT.getCode()) ) {	
				
				String templetName =""; //"approveSurveyMail.vm";
				if(user.getLocaleCode().toUpperCase().equals("KO")) {
					templetName="approveSurveyMail.vm";
				} else {
					templetName="approveSurveyEnglishMail.vm";
				}
				//survey 관리자 권한을 가지고 있는 사람들 모두에게 보냄
				List<User> userList = aclService.listSystemAdminAsAll(SurveyConstance.ITEM_TYPE_CODE);
				int limitSendLog=0;
				for (User user2 : userList) {
					
					if( limitSendLog >= MagicNumUtils.ARRAY_SIZE_10)
					{
						break;
					}
						
					
					SendLog sendLog = new SendLog();
					sendLog.setReceiverMail( user2.getMail() );
					sendLog.setReceiverName( user2.getUserName() );
					
					sendTamplet(sendLog,survey,user,serverURL(request),templetName);
					log.debug("상신메일---------------");
					
					limitSendLog++;
				}
			}else if (survey.getSurveyStatus().equals(SurveyStatus.APPROVE.getCode()) ) {	
				String templetName ="";//"confirmSurveyMail.vm";

				if(user.getLocaleCode().toUpperCase().equals("KO")) {
					templetName="confirmSurveyMail.vm";
				} else {
					templetName="confirmSurveyEnglishMail.vm";
				}				
				SendLog sendL = getSendLog(survey);
				sendTamplet(sendL,survey,user,serverURL(request),templetName);
				log.debug("승인 완료메일---------------");				
				
				/*
				templetName = "SurveyMail.vm";				
				
				for(int i = 0; i < survey.getTargetList().size(); i++){
					//System.out.println(survey.getTargetList().get(i).getTargetName());
					//StringTokenizer st = new StringTokenizer(survey.getTargetList().get(i).getTargetName(), "/");
					//String[] str = survey.getTargetList().get(i).getTargetName().split("/");
					//System.out.println("===================");
					//System.out.println(str[0] + str[1] + str[2]);
					//SendLog sendLog = new SendLog();
					//sendLog.setReceiverMail(str[0]);
					//sendLog.setReceiverName(str[1]);
					//System.out.println("=====대상자ID=======" + survey.getTargetList().get(i).getTargetKey().getTargetId());
					//SendLog sendLog = getSendLog(survey);
					
					List ls = surveyService.getListTarget(survey.getTargetList().get(i).getTargetKey().getTargetId());
					java.util.Iterator iter = ls.iterator();
					
					while(iter.hasNext()){
						HashMap data = (HashMap) iter.next();
						
						SendLog sendLog = new SendLog();
						sendLog.setReceiverMail(data.get("MAIL").toString());
						sendLog.setReceiverName(data.get("USER_NAME").toString());						

						sendTamplet(sendLog,survey,user,serverURL(request),templetName);
						log.debug("승인 완료메일1---------------");
					}
				}
				
				for(int j = 0; j < survey.getTargetGroupList().size(); j++){
					//survey.getTargetGroupList().get(j).getTargetKey().getTargetId()
					//surveyService.xxxxx(survey.getTargetGroupList().get(j).getTargetKey().getTargetId())
					//System.out.println("*****************" + survey.getTargetGroupList().get(j).getTargetKey().getTargetId() + "============");
					List ls = surveyService.getListTargetGroup(survey.getTargetGroupList().get(j).getTargetKey().getTargetId());
					//System.out.println("======" + "GROUP TARGETS" + ls.size());
					java.util.Iterator iter = ls.iterator();
					
					while(iter.hasNext()){
						HashMap data = (HashMap) iter.next();
						
						SendLog sendLog = new SendLog();
						sendLog.setReceiverMail(data.get("MAIL").toString());
						sendLog.setReceiverName(data.get("USER_NAME").toString());
						
						//SendLog sendLog = getSendLog(survey);
						sendTamplet(sendLog,survey,user,serverURL(request),templetName);
						log.debug("승인 완료메일2---------------");
					}			
				}
				*/
							
			}else if (survey.getSurveyStatus().equals(SurveyStatus.REJECT.getCode()) ) {	
				String templetName ="";//"rejectSurveyMail.vm";
				if(user.getLocaleCode().toUpperCase().equals("KO")) {
					templetName="rejectSurveyMail.vm";
				} else {
					templetName="rejectSurveyEnglishMail.vm";
				}				
				SendLog sendLog = getSendLog(survey);
				sendTamplet(sendLog,survey,user,serverURL(request),templetName);
				log.debug("반송메일---------------");
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage() );
			
		}
	}

	/**
	 * 관리자권한을 가진 사람이 설문 작성자에게 보냄
	 * @param survey
	 * @return
	 */
	private SendLog getSendLog(Survey survey) {
		Survey dbInfo = surveyService.read(survey.getSurveyId());
		User receiveUser = userService.read( dbInfo.getRegisterId() );
		SendLog sendLog = new SendLog();
		sendLog.setReceiverMail( receiveUser.getMail() );
		sendLog.setReceiverName( receiveUser.getUserName() );
		return sendLog;
	}

	/**
	 * 승인반려 초기페이지
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/approve/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");
		//Survey survey = new Survey();
		//survey.setSurveyId(surveyId);

		Survey survey = surveyService.read(surveyId);
		//System.out.println(survey.getSurveyId());
		//System.out.println(survey.getTitle());
		
		ModelAndView mav = new ModelAndView("/servicepack/survey/approve/reject");
		mav.addObject("survey", survey);
		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public ModelAndView open(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");
		Survey survey = new Survey();
		survey.setSurveyId(surveyId);

		ModelAndView mav = new ModelAndView("/servicepack/survey/open");
		mav.addObject("survey", survey);
		mav.addObject("user", user);

		return mav;
	}

	/**
	 * 메일보내기
	 * 
	 * @param survey
	 * @param result
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public String sendMail(Survey survey, BindingResult result, HttpServletRequest request, SessionStatus status) {
		try {
			if (survey.getMailSendYn() == 1) {
				String sendMailOption = "OPEN";
				job(survey, sendMailOption, request);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return "redirect:/servicepack/survey/readSurvey.do?surveyId=" + survey.getSurveyId();
	}

	/**
	 * 미참여자 메일보내기
	 * 
	 * @param survey
	 * @param result
	 * @param request
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/sendNotMail", method = RequestMethod.POST)
	public String sendNotMail(Survey survey, BindingResult result, HttpServletRequest request, SessionStatus status) {
		try {
			if (survey.getMailSendYn() == 1) {
				String sendMailOption = "NOT";
				job(survey, sendMailOption, request);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return "redirect:/servicepack/survey/report/result.do?surveyId=" + survey.getSurveyId();
	}

	/**
	 * 설문 작성 기본 코드
	 * 
	 * @param mav
	 */
	public void getCodeList(ModelAndView mav) {
		@SuppressWarnings("unchecked")
		List<Code<Integer>> pageNumList = Arrays.asList(new Code<Integer>(PageCons.PAGE_PER_ROW_10, "10"), new Code<Integer>(PageCons.PAGE_PER_ROW_15, "15"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_20, "20"), new Code<Integer>(PageCons.PAGE_PER_ROW_30, "30"), new Code<Integer>(PageCons.PAGE_PER_ROW_40, "40"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_50, "50"));

		mav.addObject("codeList", pageNumList);

		List<Code<Integer>> contentsTypeList = new ArrayList<Code<Integer>>();
		contentsTypeList.add(new Code<Integer>(0, "ui.servicepack.survey.create.contentsType.text"));
		contentsTypeList.add(new Code<Integer>(1, "ui.servicepack.survey.create.contentsType.image"));
		mav.addObject("contentsTypeList", contentsTypeList);

		List<Code<Integer>> anonymousList = new ArrayList<Code<Integer>>();
		anonymousList.add(new Code<Integer>(0, "ui.servicepack.survey.create.anonymous.yes"));
		anonymousList.add(new Code<Integer>(1, "ui.servicepack.survey.create.anonymous.no"));
		mav.addObject("anonymousList", anonymousList);

		List<Code<Integer>> rejectButtonList = new ArrayList<Code<Integer>>();
		rejectButtonList.add(new Code<Integer>(0, "ui.servicepack.survey.create.rejectButton.display"));
		rejectButtonList.add(new Code<Integer>(1, "ui.servicepack.survey.create.rejectButton.none"));
		mav.addObject("rejectButtonList", rejectButtonList);

		List<Code<Integer>> resultOpenList = new ArrayList<Code<Integer>>();
		resultOpenList.add(new Code<Integer>(0, "ui.servicepack.survey.create.resultOpen.public"));
		resultOpenList.add(new Code<Integer>(1, "ui.servicepack.survey.create.resultOpen.private"));
		mav.addObject("resultOpenList", resultOpenList);

		List<Code<Integer>> surveyTargetList = new ArrayList<Code<Integer>>();
		surveyTargetList.add(new Code<Integer>(0, "ui.servicepack.survey.create.surveyTarget.all"));
		surveyTargetList.add(new Code<Integer>(1, "ui.servicepack.survey.create.surveyTarget.one"));
		mav.addObject("surveyTargetList", surveyTargetList);

		User user = (User) getRequestAttribute("ikep.user");
		mav.addObject("user", user);

	}
	
	
	/**
	 * 실질적인 메일 보내는 로직
	 * @param sendLogList
	 * @param survey
	 * @param request
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sendTamplet(SendLog sendLog,Survey survey,User user,String url,String templetName){
		try
		{
			HashMap<String, String> map = new HashMap<String, String>();
			List<HashMap> list = new ArrayList<HashMap>();
			map.put("email", sendLog.getReceiverMail() );
			map.put("name", sendLog.getReceiverName() );
			list.add(map);
			
			//System.out.println("++++++++++++++sendTamplet+++++++++++");
			//System.out.println(sendLog.getReceiverMail());
			//System.out.println(sendLog.getReceiverName());
			
			Mail mail = new Mail();
			mail.setToEmailList(list);
			//mail.setTitle( survey.getTitle() );
					
			if( templetName.equals("approveSurveyMail.vm") || templetName.equals("approveSurveyEnglishMail.vm")){
				mail.setTitle(messageSource.getMessage("message.servicepack.survey.approveSurvey", null, new Locale(user.getLocaleCode())) + " " + survey.getTitle());
			}else if( templetName.equals("confirmSurveyMail.vm") ||  templetName.equals("confirmSurveyEnglishMail.vm") ){
				mail.setTitle(messageSource.getMessage("message.servicepack.survey.confirmSurvey", null, new Locale(user.getLocaleCode())) + " " + survey.getTitle());				
			}else if( templetName.equals("rejectSurveyMail.vm") || templetName.equals("rejectSurveyEnglishMail.vm") ){
				mail.setTitle(messageSource.getMessage("message.servicepack.survey.rejectSurvey", null, new Locale(user.getLocaleCode())) + " " + survey.getTitle());
			}
			//System.out.println(mail.getTitle());
			
			mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
			mail.setMailTemplatePath(templetName);

			//System.out.println("((((((((((((((((((((((((((((");
			//System.out.println(survey.getTitle());
			//System.out.println("))))))))))))))))))))))))))))))");
			
			Map dataMap = new HashMap();
			dataMap.put("sendLog",sendLog );
			dataMap.put("survey", survey);
			dataMap.put("url", url );
			dataMap.put("user", user );
			
			String reStr = mailSendService.sendMail(mail, dataMap,user);
			
			log.debug("-----------------------mail----------------" + reStr);
		}
		catch(Exception e){
			log.debug(e.getMessage());
		}
	}
	

	/**
	 * mail batch
	 * 
	 * @param surveyId
	 * @param request
	 */
	public void job(Survey survey, String sendMailOption, HttpServletRequest request) {
		User user = (User) getRequestAttribute("ikep.user");
		//System.out.println("**********jobjobjobjob**********");
		try {
			String surveyId = survey.getSurveyId();
			
			String sendComment = survey.getSendComment();

			Survey dbInfo = surveyService.read(surveyId);

			Integer totalSendLog = 0;

			if (dbInfo.getSurveyTarget().intValue() == 0) {
				totalSendLog = surveyService.getTotalCountByMember(user.getPortalId());
			} else {
				totalSendLog = surveyService.getTotalCountBySurveyId(surveyId);
			}

			if (sendMailOption.equals("NOT")) {
				totalSendLog = totalSendLog - responseService.responseAllCount(surveyId);
			}

			int amount = survey.getSendOption();

			int sendSeq = requestLogService.maxSeq(surveyId) + 1;
			String url = serverURL(request);
			Date startTime = DateUtils.addHours(new Date(), amount);

			RequestLog requestLog = new RequestLog();
			// SEND_OPTION IS '설문 메일발송 옵션( 0 : 즉시, 1 : 1시간후, 6 : 6시간후, 12 :
			// 12시간후)';
			requestLog.setSendOption(amount);
			requestLog.setSendRequestDate(new Date());
			requestLog.setSendStartDate(startTime);
			requestLog.setSendSuccessCount(0);
			requestLog.setSendTargetCount(totalSendLog);
			requestLog.setSendSeq(sendSeq);
			requestLog.setSurveyId(surveyId);
			requestLog.setRegistDate(new Date());
			requestLog.setRegisterId(user.getUserId());
			requestLog.setRegisterName(user.getUserName());
			requestLog.setSendComment(sendComment);
			requestLogService.create(requestLog);

			Map<String, Object> jobData = new HashMap<String, Object>();
			jobData.put("surveyId", surveyId);
			jobData.put("sendSeq", sendSeq);
			jobData.put("user", user);
			jobData.put("url", url);
			jobData.put("sendMailOption", sendMailOption);
			jobData.put("surveyService", surveyService);
			jobData.put("mailSendService", mailSendService);
			jobData.put("sendLogService", sendLogService);
			jobData.put("requestLogService", requestLogService);

			SurveyJobDetailBean job = new SurveyJobDetailBean();
			job.setTriggerName("trigger_" + surveyId + System.currentTimeMillis());
			job.setJobName("job_" + surveyId + System.currentTimeMillis());
			job.setJobClassName("com.lgcns.ikep4.servicepack.survey.web.SurveyScheduled");
			job.setDescription("survey scheduler");
			job.setJobDataAsMap(jobData);
			job.setStartTime(startTime);
			job.setRepeatInterval(MagicNumUtils.SCHEDUL_INTERVAL);
			// 한번만 수행하도록 함
			job.setPriority(-1);
			job.setRepeatCount(0);

			jobService.saveJob(job);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
	
	/**
	 * 오픈한 설문의 설문 종료일 변경 화면 출력 (popup창)
	 *
	 */
	@RequestMapping(value = "/updateEndDateView.do")
	public ModelAndView updateEndDateView(@RequestParam("endDate") String endDate,
	        @RequestParam("surveyId") String surveyId){
	    
	    ModelAndView mav = new ModelAndView("servicepack/survey/updateEndDateView");
	    mav.addObject( "endDate", endDate )
	       .addObject( "surveyId", surveyId );
	    
	    return mav;  
	}
	
	/**
	 * 오픈한 설문의 설문 종료일 변경 프로세스  
	 */
	@RequestMapping(value = "/modifyEndDate.do")
	public @ResponseBody Map<String, Object> modifyEndDate(HttpServletRequest request) {
	    
	    Map<String, Object> result=new HashMap<String, Object>();
	    
	    try {      
    	    HashMap<String, String> data = new HashMap<String, String>();
    	    data.put( "endDate", request.getParameter( "newEndDate" ) );
    	    data.put( "surveyId", request.getParameter( "surveyId" ) );
    	    
    	    surveyService.updateEndDate(data);
    	    
            result.put("success", "success");
        } catch (Exception ex) {
            result.put("success", "fail");
    
        }

        return result;
	    
	}

}
