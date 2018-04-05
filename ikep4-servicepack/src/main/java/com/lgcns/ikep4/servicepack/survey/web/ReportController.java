/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.servicepack.survey.model.Answer;
import com.lgcns.ikep4.servicepack.survey.model.Question;
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;
import com.lgcns.ikep4.servicepack.survey.model.Response;
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetail;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.service.QuestionGroupService;
import com.lgcns.ikep4.servicepack.survey.service.QuestionService;
import com.lgcns.ikep4.servicepack.survey.service.ResponseService;
import com.lgcns.ikep4.servicepack.survey.service.SendLogService;
import com.lgcns.ikep4.servicepack.survey.service.SurveyService;
import com.lgcns.ikep4.servicepack.survey.util.AuthUtils;
import com.lgcns.ikep4.servicepack.survey.util.CalUtils;
import com.lgcns.ikep4.servicepack.survey.util.ExcelUtil;
import com.lgcns.ikep4.servicepack.survey.util.MagicNumUtils;
import com.lgcns.ikep4.servicepack.survey.util.SurveyConstance;


/**
 * 현황을 분석하는 mvc
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: ReportController.java 16244 2011-08-18 04:11:42Z giljae $
 */

@Controller
@RequestMapping(value = "/servicepack/survey/report")
public class ReportController extends BaseSurveyController {

	// 문항
	@Autowired
	private ResponseService responseService;

	// 설문
	@Autowired
	private SurveyService surveyService;

	@Autowired
	SendLogService sendLogService;

	@Autowired
	private FileService fileService;
	
	// 문항그룹
	@Autowired
	private QuestionGroupService questionGroupService;

	// 문항
	@Autowired
	private QuestionService questionService;

	/**
	 * 참여목록 일별현황
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public ModelAndView result(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");
		boolean isSystemAdmin = aclService.isSystemAdmin(SurveyConstance.ITEM_TYPE_CODE, user.getUserId());

		// 설문내역
		Survey survey = surveyService.read(surveyId);

		// 총대상자카운트
		// Integer totalSendLog = sendLogService.getTotalSendLog(surveyId);
		Integer totalSendLog = 0;

		if (survey.getSurveyTarget().intValue() == 0) {
			totalSendLog = surveyService.getTotalCountByMember(user.getPortalId());
		} else {
			totalSendLog = surveyService.getTotalCountBySurveyId(survey.getSurveyId());
		}
		// 설문분석로그
		List<Response> dbList = responseService.getDayResultList(surveyId);

		List<Response> resultList = CalUtils.makeCalendar(survey.getStartDate(), survey.getEndDate());

		int mark = 0;
		for (Response response : resultList) {
			response.setResponseCnt(0);
			response.setTotalSendLog(totalSendLog);

			for (int i = mark; i < dbList.size(); i++) {
				
				Response dbInfo = (Response) dbList.get(i);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				if (df.format(response.getResponseDate()).equals(df.format(dbInfo.getResponseDate()))) {
					response.setResponseCnt(dbInfo.getResponseCnt());
					mark = i;
					break;
				}
			}

			log.debug(response);
		}

		/*
		 * for (Response response : dbList) { response.setDay(
		 * CalUtils.getDayOfWeek( response.getResponseDate() ) );
		 * log.debug(response); }
		 */

		ModelAndView mav = new ModelAndView("/servicepack/survey/report/result");
		mav.addObject("survey", survey);
		mav.addObject("totalSendLog", totalSendLog);
		mav.addObject("resultList", resultList);
		mav.addObject("surveyAuth", new AuthUtils(survey, user, isSystemAdmin));
		mav.addObject("user", user);
		return mav;
	}

	/**
	 * 참여목록 상세조회
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/resultDetail", method = RequestMethod.GET)
	public ModelAndView resultDetail(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");

		//설문 관리자 체크 
	   List<User> admins = aclService.listSystemAdminAsUser( "Survey" );
       boolean isAdmin = false;
       
       for(int i=0; i<admins.size(); i++){
           
           if(user.getUserId().equals( admins.get( i ).getUserId())){
               isAdmin = true;              
           }           
       }
		
		// 설문내역
		Survey survey = surveyService.read(surveyId);
		// 설문 머릿말 이미지 
		String fileId = "";
		if(survey.getContentsType()==1){
			List<FileData> fileDataList = this.fileService.getItemFile(surveyId, "N");
			fileId = fileDataList.get(0).getFileId();
		}
		
		// 총대상자카운트
		// Integer totalSendLog = sendLogService.getTotalSendLog(surveyId);
		Integer totalSendLog = 0;

		if (survey.getSurveyTarget().intValue() == 0) { // 전사일경우
			totalSendLog = surveyService.getTotalCountByMember(user.getPortalId());
		} else {
			totalSendLog = surveyService.getTotalCountBySurveyId(survey.getSurveyId());
		}

		List<QuestionGroup> questionGroupList = questionGroupService.getAllBySurveyId(surveyId);

		for (QuestionGroup questionGroup : questionGroupList) {

			List<Question> questionList = questionService.getAllByQuestionGroupId(questionGroup.getQuestionGroupId());

			for (Question question : questionList) {
				List<Answer> answerList = question.getAnswer();

				for (Answer answer : answerList) {
					List<ResponseDetail> responseDetailList = responseService.getAllByAnswerId(answer.getAnswerId());

					if (question.getQuestionType().indexOf("A") >= 0 || question.getQuestionType().indexOf("B") >= 0) {
						// list의 totalcount
						answer.setResponseCnt(responseDetailList.size());
					} else if (question.getQuestionType().indexOf("C") >= 0) {
						// list의 totalcount
						resultDetailTypeC(question, answer, responseDetailList);

					} else if (question.getQuestionType().indexOf("D") >= 0) {

						getTypeD(question, answer, responseDetailList);
					}
				}
			}

			questionGroup.setQuestionList(questionList);
		}

		ModelAndView mav = new ModelAndView("/servicepack/survey/report/resultDetail");
		mav.addObject("survey", survey);
		mav.addObject("fileId", fileId);
		mav.addObject("totalSendLog", totalSendLog);
		mav.addObject("questionGroupList", questionGroupList);
		mav.addObject("user", user);
		mav.addObject( "isAdmin", isAdmin );
		return mav;
	}

	/**
	 * 설문 상세 결과 typec
	 * @param question
	 * @param answer
	 * @param responseDetailList
	 */
	private void resultDetailTypeC(Question question, Answer answer, List<ResponseDetail> responseDetailList) {
		answer.setResponseCnt(responseDetailList.size());
		// 순위형일경우에는 카운트만큼 배열을 나열한다.
		int[] response1 = new int[question.getAnswer().size()];

		// response1의 db값을 분석한다.
		for (ResponseDetail responseDetail : responseDetailList) {
			if (StringUtils.hasText(responseDetail.getResponse1())) {
				try {
					int value = Integer.parseInt(responseDetail.getResponse1());
					// 값은 1부터 시작 배열은0부터시작
					response1[value - 1] = response1[value - 1] + 1;

				} catch (Exception e) {
				}
			}
		}

		answer.setResponse1(response1);
	}

	/**
	 * 설문 결과 typed
	 * @param question
	 * @param answer
	 * @param responseDetailList
	 */
	private void getTypeD(Question question, Answer answer, List<ResponseDetail> responseDetailList) {
		// list의 totalcount
		answer.setResponseCnt(responseDetailList.size());
		// 순위형일경우에는 카운트만큼 배열을 나열한다.
		int[] response1 = null;
		int[] response2 = null;

		if (question.getQuestionType().equals("D0")) {
			response2 = new int[MagicNumUtils.ARRAY_SIZE_3];
		} else if (question.getQuestionType().equals("D1")) {
			response2 = new int[MagicNumUtils.ARRAY_SIZE_5];
		} else if (question.getQuestionType().equals("D2")) {
			response2 = new int[MagicNumUtils.ARRAY_SIZE_6];
		} else if (question.getQuestionType().equals("D3")) {
			response2 = new int[MagicNumUtils.ARRAY_SIZE_7];
		} else if (question.getQuestionType().equals("D4")) {
			response1 = new int[MagicNumUtils.ARRAY_SIZE_2];// 적용기회
			response2 = new int[MagicNumUtils.ARRAY_SIZE_5];
		} else if (question.getQuestionType().equals("D5")) {
			response1 = new int[MagicNumUtils.ARRAY_SIZE_2];// 적용기회
			response2 = new int[MagicNumUtils.ARRAY_SIZE_7];
		} else if (question.getQuestionType().equals("D6")) {
			response1 = new int[MagicNumUtils.ARRAY_SIZE_1]; // N/A
			response2 = new int[MagicNumUtils.ARRAY_SIZE_7];
		} else if (question.getQuestionType().equals("D7")) {
			response2 = new int[MagicNumUtils.ARRAY_SIZE_7];
		}

		makeTypeDdetail(question, responseDetailList, response1, response2);
		answer.setResponse1(response1);
		answer.setResponse2(response2);
	}

	/**
	 * 설문 상세 결과 typed
	 * @param question
	 * @param responseDetailList
	 * @param response1
	 * @param response2
	 */
	private void makeTypeDdetail(Question question, List<ResponseDetail> responseDetailList, int[] response1,
			int[] response2) {
		// response1의 db값을 분석한다.
		for (ResponseDetail responseDetail : responseDetailList) {
			if (StringUtils.hasText(responseDetail.getResponse1())) {

				resultDetailTypeAll(question, response1, response2, responseDetail);

			}
		}
	}

	/**
	 * 설문 상세 모든 타입별
	 * @param question
	 * @param response1
	 * @param response2
	 * @param responseDetail
	 */
	private void resultDetailTypeAll(Question question, int[] response1, int[] response2, ResponseDetail responseDetail) {
		if (question.getQuestionType().equals("D4") || question.getQuestionType().equals("D5")) {
			// 1,2각각 따로 저장되어있음
			if (responseDetail.getResponse1().equals("1")) {
				response1[1] = response1[1] + 1; // X선택
			} else {
				try {
					response1[0] = response1[0] + 1; // O에꺼선택

					int value = Integer.parseInt(responseDetail.getResponse2());
					// 값,배열 0부터시작
					response2[value] = response2[value] + 1;
				} catch (Exception e) {
				}
			}
		} else if (question.getQuestionType().equals("D6")) {
			// reponse1이 N/A면 N/A reponse1에 그외의 값이면
			// response2배열에 저장
			try {
				if (responseDetail.getResponse1().equals("N/A")){ // reponse1이
																	// N/A면
																	// N/A
																	// reponse1에
																	// 그외의
																	// 값이면
																	// response2배열에
																	// 저장
					response1[0] = response1[0] + 1;
				}else {
					int value = Integer.parseInt(responseDetail.getResponse1());
					// 값,배열 0부터시작
					response2[value] = response2[value] + 1;
				}
			} catch (Exception e) {
			}
		} else {
			try {
				int value = Integer.parseInt(responseDetail.getResponse1());
				// 값,배열 0부터시작
				response2[value] = response2[value] + 1;
			} catch (Exception e) {
			}
		}
	}

	/**
	 * excel download
	 * 
	 * @param surveyId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/resultDetailExcel", method = RequestMethod.POST)
	public void resultDetailExcel(@RequestParam(value = "surveyId", required = true) String surveyId,
			HttpServletRequest request, HttpServletResponse response) {
		// User user = (User)getRequestAttribute("ikep.user");
		// 설문내역
		// Survey survey = surveyService.read(surveyId);

		String fileName = "survey.xlsx";

		List<Question> questionTitleList = questionService.getReportAllBySurveyId(surveyId);

		LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
		LinkedHashMap<String, String> userMap = new LinkedHashMap<String, String>();
		Map<String, String> typeMap = new HashMap<String, String>();

		makeReportHeader(titleMap, typeMap, questionTitleList);
		makeUserMap(userMap, questionTitleList);

		List<Object> dataList = new ArrayList<Object>();

		Collection<Object> dataMap = makeReportData(surveyId, userMap).values();

		for (Object object : dataMap) {
			dataList.add(object);
			/*
			 * Map<String, String> temp = (LinkedHashMap<String, String>)object;
			 * Set<String> key = temp.keySet(); for (Iterator iterator =
			 * key.iterator(); iterator.hasNext();) { String string = (String)
			 * iterator.next(); //System.out.println(string +":" +
			 * temp.get(string) ); }
			 */
		}

		ExcelUtil.saveExcel(titleMap, typeMap, dataList, fileName, response);

	}

	/**
	 * execel header
	 */
	private void makeReportHeader(Map<String, String> titleMap, Map<String, String> typeMap,
			List<Question> questionTitleList) {
		// 기본 title
		titleMap.put("seq", "순번");
		typeMap.put("seq", "java.lang.String");

		titleMap.put("empNo", "사번");
		typeMap.put("empNo", "java.lang.String");

		titleMap.put("userName", "이름");
		typeMap.put("userName", "java.lang.String");

		titleMap.put("teamName", "소속");
		typeMap.put("teamName", "java.lang.String");

		titleMap.put("responseDate", "설문일");
		typeMap.put("responseDate", "java.lang.String");

		for (int i = 0; i < questionTitleList.size(); i++) {
			Question question = questionTitleList.get(i);
			titleMap.put(new String("Q" + question.getQuestionId()), (i + 1) + "." + question.getTitle());
			typeMap.put(new String("Q" + question.getQuestionId()), "java.lang.String");
		}
	}

	/**
	 * excel user map
	 * 
	 * @param surveyId
	 * @param titleMap
	 * @param questionTitleList
	 */
	private void makeUserMap(Map<String, String> userMap, List<Question> questionTitleList) {
		// 기본 title
		userMap.put("seq", "");
		userMap.put("empNo", "");
		userMap.put("userName", "");
		userMap.put("teamName", "");
		userMap.put("responseDate", "");

		for (int i = 0; i < questionTitleList.size(); i++) {
			Question question = questionTitleList.get(i);
			userMap.put(new String("Q" + question.getQuestionId()), "");
		}
	}

	/**
	 * 결과 데이터
	 * @param surveyId
	 * @param userMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> makeReportData(String surveyId, Map<String, String> userMap) {
		int seq = 1;

		// empNo
		Map<String, Object> reportMap = new LinkedHashMap<String, Object>();

		List<ResponseDetail> responseDetailList = responseService.getReportDetailAllBySurveyId(surveyId);

		for (ResponseDetail responseDetail : responseDetailList) {

			Map<String, String> userData = new LinkedHashMap<String, String>();

			// 존재하는지 체크해서 없으면 넣는다.
			if (!reportMap.containsKey(new String(responseDetail.getEmpNo()))) {
				BeanUtils.copyProperties(userMap, userData);
				userData.put("seq", Integer.toString(seq++));
				userData.put("empNo", responseDetail.getEmpNo());
				userData.put("userName", responseDetail.getUserName());
				userData.put("teamName", responseDetail.getTeamName());

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String responseDate = df.format(responseDetail.getResponseDate());
				userData.put("responseDate", responseDate);

				reportMap.put(new String(responseDetail.getEmpNo()), userData);
			} else{
				userData = (Map<String, String>) reportMap.get(new String(responseDetail.getEmpNo()));
			}
			
			String responseInfo = userData.get(new String("Q" + responseDetail.getQuestionId()));

			if (StringUtils.hasText(responseInfo)) {
				responseInfo = responseInfo + "," + makeResponseDetail(responseDetail);
			} else {
				responseInfo = makeResponseDetail(responseDetail);
			}
			userData.put(new String("Q" + responseDetail.getQuestionId()), responseInfo);

		}

		return reportMap;

	}

	/**
	 * responseDetail 만들기
	 * 
	 * @param responseDetail
	 * @return
	 */
	public String makeResponseDetail(ResponseDetail responseDetail) {

		StringBuilder result = new StringBuilder();

		if (responseDetail.getQuestionType().indexOf("A") >= 0 || responseDetail.getQuestionType().indexOf("B") >= 0
				|| responseDetail.getQuestionType().indexOf("C") >= 0) {

			makeResponseDetailType(responseDetail, result);
		} else if (responseDetail.getQuestionType().indexOf("D") >= 0) {
			makeResponseDetailTypeD(responseDetail, result);
		}

		return result.toString();
	}

	/**
	 * 결과 데이터 typed
	 * @param responseDetail
	 * @param result
	 */
	private void makeResponseDetailTypeD(ResponseDetail responseDetail, StringBuilder result) {
		if (responseDetail.getQuestionType().equals("D4") || responseDetail.getQuestionType().equals("D5")) {
			// 1,2각각 따로 저장되어있음
			if (responseDetail.getResponse1().equals("1")) {
				result.append("X");
			} else {
				result.append(Integer.valueOf(responseDetail.getResponse2())+1);
			}
		} else if (responseDetail.getQuestionType().equals("D6")) {
			// reponse1이 N/A면 N/A reponse1에 그외의 값이면 response2배열에 저장
			try {
				if (responseDetail.getResponse1().equals("N/A")) { // reponse1이
																	// N/A면
																	// N/A
																	// reponse1에
																	// 그외의
																	// 값이면
																	// response2배열에
																	// 저장
					result.append("N/A");
				} else {
					result.append(Integer.valueOf(responseDetail.getResponse1())+1);
				}
			} catch (Exception e) {
			}
		} else {
			result.append( Integer.valueOf(responseDetail.getResponse1()) +1);
		}
	}

	/**
	 * 결과 데이터 타입별
	 * @param responseDetail
	 * @param result
	 */
	private void makeResponseDetailType(ResponseDetail responseDetail, StringBuilder result) {
		// list의 값을 더한다 |로분리
		if (responseDetail.getQuestionType().indexOf("A2") >= 0
				|| responseDetail.getQuestionType().indexOf("A3") >= 0
				|| responseDetail.getQuestionType().indexOf("A6") >= 0
				|| responseDetail.getQuestionType().indexOf("A7") >= 0) {
			
			if(responseDetail.getResponse1() ==null || responseDetail.getResponse1().equals("")) {
				result.append(responseDetail.getAnswerSeq());
				// result.append(":");
				// result.append(responseDetail.getResponse1());
			} else {
				result.append(responseDetail.getAnswerSeq());
				result.append(":");
				result.append(responseDetail.getResponse1());				
			}
		} else if (responseDetail.getQuestionType().indexOf("B") >= 0) {
			result.append(responseDetail.getAnswerSeq());
			result.append(":");
			result.append(responseDetail.getResponse1());
		} 
		else if (responseDetail.getQuestionType().indexOf("C") >= 0) {
			result.append(responseDetail.getResponse1());
		}
		else {
			result.append(responseDetail.getAnswerSeq());
		}
	}

	/*
	 * @SuppressWarnings("unused") private DynaBean
	 * createExcelBean(LinkedHashMap<String, String> titleMap,List<Question>
	 * questionTitleList) throws Exception { // first create the properties
	 * DynaProperty properties[] = new DynaProperty[5+questionTitleList.size()];
	 * properties[0] = new DynaProperty("seq", String.class); properties[1] =
	 * new DynaProperty("empNo", String.class); properties[2] = new
	 * DynaProperty("userName", String.class); properties[3] = new
	 * DynaProperty("teamName", String.class); properties[4] = new
	 * DynaProperty("responseDate", String.class); for (int i = 0; i <
	 * questionTitleList.size(); i++) { Question question =
	 * questionTitleList.get(i); properties[5+i] = new
	 * DynaProperty("Q"+question.getQuestionId(), String.class); } // next using
	 * the properties define the class DynaClass surveyExcelClass = new
	 * BasicDynaClass("surveyExcelBean", null, properties); DynaBean
	 * surveyExcelBean = surveyExcelClass.newInstance(); return surveyExcelBean;
	 * }
	 */
}
