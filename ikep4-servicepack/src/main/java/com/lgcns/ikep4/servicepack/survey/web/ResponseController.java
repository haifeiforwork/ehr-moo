/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;
import com.lgcns.ikep4.servicepack.survey.model.Response;
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetail;
import com.lgcns.ikep4.servicepack.survey.model.ResponseType;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.servicepack.survey.model.TargetKey;
import com.lgcns.ikep4.servicepack.survey.service.QuestionGroupService;
import com.lgcns.ikep4.servicepack.survey.service.QuestionService;
import com.lgcns.ikep4.servicepack.survey.service.ResponseService;
import com.lgcns.ikep4.servicepack.survey.service.SurveyService;
import com.lgcns.ikep4.servicepack.survey.util.MagicNumUtils;
import com.lgcns.ikep4.servicepack.survey.util.SurveyWebUtils;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 
 * 설문참여
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: ResponseController.java 16244 2011-08-18 04:11:42Z giljae $
 */

@Controller
@RequestMapping(value = "/servicepack/survey/response")
public class ResponseController extends BaseSurveyController {
	@Autowired
	private IdgenService idgenService;
	
	//설문
	@Autowired
	private SurveyService  surveyService;
	
	//문항그룹
	@Autowired
	private QuestionGroupService  questionGroupService;
	
	//문항
	@Autowired
	private QuestionService  questionService;
	
	//문항
	@Autowired
	private ResponseService  responseService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 설문참여 가능한지 체크
	 * @param surveyId
	 * @param mav
	 * @return
	 */
	private boolean validCheck(String surveyId, ModelAndView mav) {
		boolean result = true;
		
		User user = (User)getRequestAttribute("ikep.user");
		Survey survey = surveyService.read(surveyId); 
		
		mav.addObject("survey", survey);
		mav.addObject("user",user);
		
		Date currentDt = new Date();
		
		Response searchKey = new Response();
		searchKey.setSurveyId( survey.getSurveyId() );
		searchKey.setResponseUserId(user.getUserId());
		
		//참석여부
		boolean already = responseService.already(searchKey);
		if( already )
		{
			mav.setViewName("redirect:/servicepack/survey/surveyEndList.do");
			mav.addObject("erroMessage","message.servicepack.survey.response.end.exists");
			result=false;
		}
		else
		{	
			
			if( survey.getSurveyTarget().intValue() !=  0 ) //전사일경우는 체크안함
			{
				//응답자 목록에 잇는지 없는지 체크
				TargetKey logKey = new TargetKey(surveyId, user.getUserId(),null );
				boolean exists = surveyService.existByUserId(logKey);
				
				if( !exists )
				{
					mav.setViewName("redirect:/servicepack/survey/surveyIngList.do");
					mav.addObject("erroMessage","message.servicepack.survey.response.not.exists");
					result =false;
				}
			}
			if( survey.getSurveyStatus().intValue() != SurveyStatus.ANSERING.getCode() || currentDt.after( survey.getEndDate() ) )
			{
				mav.setViewName("redirect:/servicepack/survey/surveyIngList.do");
				mav.addObject("erroMessage","message.servicepack.survey.response.not.ansering");
				result = false;
			}
		}
		
		return result;
	}
	
	
	/**
	 * 설문참여(메일,웹페이지 참여 첫패이지)
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/response",method = RequestMethod.GET)
	public ModelAndView response(@RequestParam(value="surveyId",required=true) String surveyId) {
		ModelAndView mav = new ModelAndView("/servicepack/survey/response/response");

		boolean result =validCheck(surveyId,mav);
		
		Survey survey = surveyService.read(surveyId);
		
		// 설문 머릿말 이미지 
		String fileId = "";
		if(survey.getContentsType()==1){
			List<FileData> fileDataList = this.fileService.getItemFile(surveyId, "N");
			fileId = fileDataList.get(0).getFileId();
		}		
		
		if( result )
		{
			List<QuestionGroup> questionGroupList = questionGroupService.getAllBySurveyId(surveyId);
			
			for (QuestionGroup questionGroup : questionGroupList) {
				questionGroup.setQuestionList( questionService.getAllByQuestionGroupId( questionGroup.getQuestionGroupId() ) );
			}
			
			mav.addObject("questionGroupList", questionGroupList);
		}
		
		mav.addObject("fileId", fileId);
		
		return mav;
	}
	
	/**
	 * 설문참여
	 * @param surveyId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/responseSubmit",method = RequestMethod.POST)
	public ModelAndView responseSubmit(@RequestParam(value="surveyId",required=true) String surveyId,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/servicepack/survey/response/response");
		User user = (User)getRequestAttribute("ikep.user");
		
		
		try
		{
			boolean result = validCheck(surveyId,mav);
			
			if( result )
			{
				String responseId = idgenService.getNextId();
				
				List<ResponseDetail> responseDetailList = getResponseDetailInfo(request, responseId);
				
				//응답정보
				Response response = new Response();
				response.setResponseId(responseId);
				response.setResponseUserId( user.getUserId() );
				response.setResponse(0);
				response.setResponseDate( new Date() );
				response.setResponseUserMail( user.getMail() );
				response.setSurveyId( surveyId );
				//response.setSurveyRejectReason1(Integer);
				//response.setSurveyRejectReason2(String);
				response.setResponseDetail(responseDetailList);
				
				responseService.create( response );
				
				mav.setViewName("redirect:/servicepack/survey/surveyEndList.do");
			}
		}
		catch(Exception e){
			mav.setViewName("redirect:/servicepack/survey/response/response.do?surveyId="+surveyId);
		}
		
		return mav;
	}
	
	/**
	 * 설문거부
	 * @param surveyId
	 * @param surveyRejectReason1
	 * @param surveyRejectReason2
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rejectSubmit",method = RequestMethod.POST)
	public ModelAndView rejectSubmit(@RequestParam(value="surveyId",required=true) String surveyId,
			@RequestParam(value="surveyRejectReason1",required=false) int surveyRejectReason1,
			@RequestParam(value="surveyRejectReason2",required=false) String surveyRejectReason2,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/servicepack/survey/response/response");
		User user = (User)getRequestAttribute("ikep.user");
		
		try
		{
			boolean result = validCheck(surveyId,mav);
			
			if( result )
			{
				String responseId = idgenService.getNextId();
				
				List<ResponseDetail> responseDetailList = new ArrayList<ResponseDetail>();
				
				//응답정보
				Response response = new Response();
				response.setResponseId(responseId);
				response.setResponseUserId( user.getUserId() );
				response.setResponse(1); //거부
				response.setResponseDate( new Date() );
				response.setResponseUserMail( user.getMail() );
				response.setSurveyId( surveyId );
				response.setSurveyRejectReason1(surveyRejectReason1);
				response.setSurveyRejectReason2(surveyRejectReason2);
				response.setResponseDetail(responseDetailList);
				
				responseService.create( response );
				
				mav.setViewName("redirect:/servicepack/survey/surveyEndList.do");
			}	
		}
		catch(Exception e){
			mav.setViewName("redirect:/servicepack/survey/response/response.do?surveyId="+surveyId);
		}
		
		return mav;
	}
	

	/**
	 * 설문 참여시 파라미터별 로직분석
	 * responseType [1:문항(주관식), 2:문항(객관식), 3:예시(주관식), 4:예시(객관식)]
	 *  설문 응답 1
		[ 척도OX+5점/7점의 적용기회 여부(0, 1)
		척도NA+7점의 N/A(0, 1)
		척도수준별의 요구역량(1~5)
		그 외 선택답변 ]
		
		설문 응답 2
		(척도OX+5점/7점, 척도NA+7점의 답변)
	 * @param request
	 * @param responseId
	 * @return
	 */
	private List<ResponseDetail> getResponseDetailInfo(HttpServletRequest request, String responseId) {
		String responseType="0";
		String prefix = "q";
		Map<String,Object> requestParam = SurveyWebUtils.getParametersStartingWith(request, prefix);
		Collection<Object>  collection = requestParam.values();
		
		//설문상세 응답정보
		List<ResponseDetail> responseDetailList = new ArrayList<ResponseDetail>();
		
		for (Iterator<Object> iterator = collection.iterator(); iterator.hasNext();) {
			String[] value = iterator.next().toString().split("\\|");

			String questionType =  value[0];
			String questionId = value[MagicNumUtils.ARRAY_SIZE_1];
			String answerId =  value[MagicNumUtils.ARRAY_SIZE_2];
			
			ResponseDetail responseDetail = new ResponseDetail();
			responseDetail.setAnswerId(answerId);
			responseDetail.setQuestionId(questionId);
			responseDetail.setResponseId(responseId);
			responseDetail.setQuestionType(questionType);
			
			//잔여 상세정보 가져오기
			//답변번호가 extra데이터가 된다.
			String extra = ServletRequestUtils.getStringParameter(request, responseDetail.getAnswerId(),"");
			
			if ( responseDetail.getQuestionType().indexOf("A") >= 0 ) {
				responseType = ResponseType.ACHOOSE.getCode();
				responseDetail.setResponse1(extra);
			}
		    else if ( responseDetail.getQuestionType().indexOf("B")>=0 ){
		    	
		    	if( extra.equals("") ){ continue;}//첫번째 값이 없으면 SKIP
		    	
		    	responseType = ResponseType.AWRITE.getCode();
		    	responseDetail.setResponse1(extra);
		    }
		    else if ( responseDetail.getQuestionType().indexOf("C")>=0 ){
		    	if( extra.equals("") ){ continue;}//첫번째 값이 없으면 SKIP
		    	responseType = ResponseType.QCHOOSE.getCode();
		    	responseDetail.setResponse1(extra);
		    }
		    else if ( responseDetail.getQuestionType().indexOf("D")>=0 ){
		    	String response1 =  value[MagicNumUtils.ARRAY_SIZE_3];
		    	responseType = ResponseType.QCHOOSE.getCode();
		    	responseDetail.setResponse1(response1);
		    	
		    	if( responseDetail.getQuestionType().equals("D4")  || responseDetail.getQuestionType().equals("D5") )
		    	{
		    			responseDetail.setResponse2(extra);
		    	}
		    }
			
			responseDetail.setResponseType(responseType);
			
			responseDetailList.add(responseDetail); 
			//System.out.println("_----------------------------------------"+value);
		}
		
		return responseDetailList;
	}
}
