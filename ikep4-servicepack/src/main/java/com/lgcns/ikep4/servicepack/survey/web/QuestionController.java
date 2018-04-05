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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.servicepack.survey.model.Answer;
import com.lgcns.ikep4.servicepack.survey.model.Question;
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.service.QuestionGroupService;
import com.lgcns.ikep4.servicepack.survey.service.QuestionService;
import com.lgcns.ikep4.servicepack.survey.service.SurveyService;
import com.lgcns.ikep4.servicepack.survey.util.SurveyWebUtils;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 설문 문항그룹,문항 등록
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: QuestionController.java 16244 2011-08-18 04:11:42Z giljae $
 */

@Controller
@RequestMapping(value = "/servicepack/survey/question")
public class QuestionController extends BaseSurveyController {
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

	//파일서비스
	@Autowired
	private FileService fileService;

	/**
	 * 설문 문항등록 초기 페이지
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/createQuestion",method = RequestMethod.GET)
	public ModelAndView createQuestion(@RequestParam(value="surveyId",required=true) String surveyId) {
		User user = (User)getRequestAttribute("ikep.user");
		
		Survey survey = surveyService.read(surveyId); 
		List<QuestionGroup> questionGroupList = questionGroupService.getAllBySurveyId(surveyId);
		
		for (QuestionGroup questionGroup : questionGroupList) {
			questionGroup.setQuestionList( questionService.getAllByQuestionGroupId( questionGroup.getQuestionGroupId() ) );
		}
		
		ModelAndView mav = new ModelAndView("/servicepack/survey/question/createQuestion");
		mav.addObject("survey", survey);
		mav.addObject("questionGroupList", questionGroupList);
		mav.addObject("user",user);
		return mav;
	}
	
	/**
	 * 설문문항그룹 등록 ajax
	 * @param questionGroup
	 * @return
	 */
	@RequestMapping(value = "/createQuestionGroup")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QuestionGroup createQuestionGroup(@RequestBody QuestionGroup questionGroup) {
		QuestionGroup result = null;
		User user = (User)getRequestAttribute("ikep.user");
		
	    try {
	    	
	    	questionGroup.setRegistDate(new Date());
	    	questionGroup.setRegisterId(user.getUserId());
	    	questionGroup.setRegisterName(user.getUserName());
	    	questionGroup.setUpdateDate(new Date());
	    	questionGroup.setUpdaterId(user.getUserId());
	    	questionGroup.setUpdaterName(user.getUserName());
			
	    	boolean exists = surveyService.exists( questionGroup.getSurveyId() );
	    	
	    	if( !exists ) {throw new Exception();}
	    	
	    	questionGroup.setQuestionGroupId( idgenService.getNextId() );
	    	//System.out.println("################questionGroup.getContents():"+questionGroup.getContents());
	    	questionGroup.setContents(StringUtil.replaceHtmlString(questionGroup.getContents()));
	    	questionGroupService.create( questionGroup );
	    	
	    	result =  questionGroup;
	    	
	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }

	    return result;
	}

	/**
	 * 설문 문항그룹 수정 ajax
	 * @param questionGroup
	 * @return
	 */
	@RequestMapping(value = "/updateQuestionGroup")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QuestionGroup updateQuestionGroup(@RequestBody QuestionGroup questionGroup) {
		QuestionGroup result = null;
		User user = (User)getRequestAttribute("ikep.user");
		
	    try {
	    	
	    	questionGroup.setUpdateDate(new Date());
	    	questionGroup.setUpdaterId(user.getUserId());
	    	questionGroup.setUpdaterName(user.getUserName());
			
	    	boolean exists = surveyService.exists( questionGroup.getSurveyId() );
	    	
	    	if( !exists ) {throw new Exception();}
	    	
	    	//System.out.println("################questionGroup.getContents():"+questionGroup.getContents());
	    	questionGroup.setContents(StringUtil.replaceHtmlString(questionGroup.getContents()));
	    	questionGroupService.update( questionGroup );
	    	
	    	result =  questionGroup;
	    	
	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }

	    return result;
	}
	
	/**
	 * 설문 문항그룹 삭제 ajax
	 * @param questionGroup
	 * @return
	 */
	@RequestMapping(value = "/removeQuestionGroup")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody QuestionGroup removeQuestionGroup(@RequestBody QuestionGroup questionGroup) {
		QuestionGroup result = null;
		User user = (User)getRequestAttribute("ikep.user");
		
	    try {
	    	
	    	
	    	questionGroup.setUpdateDate(new Date());
	    	questionGroup.setUpdaterId(user.getUserId());
	    	questionGroup.setUpdaterName(user.getUserName());
			
	    	boolean exists = surveyService.exists( questionGroup.getSurveyId() );
	    	
	    	if( !exists ) {throw new Exception();}
	    	
	    	questionGroupService.delete( questionGroup.getQuestionGroupId() );
	    	
	    	result =  questionGroup;
	    	
	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }

	    return result;
	}
	
	
	/**
	 * 설문 문항 등록
	 * @param question
	 * @return
	 */
	@RequestMapping(value = "/createQuestion")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Question createQuestion(@RequestBody Question question) {
		Question result = null;
		User user = (User)getRequestAttribute("ikep.user");
		
	    try {
	    	question.setRegistDate(new Date());
	    	question.setRegisterId(user.getUserId());
	    	question.setRegisterName(user.getUserName());
	    	question.setUpdateDate(new Date());
	    	question.setUpdaterId(user.getUserId());
	    	question.setUpdaterName(user.getUserName());
	    	
			
	    	boolean exists = questionGroupService.exists( question.getQuestionGroupId() );
	    	
	    	if( !exists ) {throw new Exception();}
	    	
	    	String questionId = idgenService.getNextId();
	    	
	    	question.setQuestionId(questionId);
	    	
	    	
	    	
	    	for (Answer answer : question.getAnswer() ) {
				answer.setRegistDate(new Date());
				answer.setRegisterId(user.getUserId());
				answer.setRegisterName(user.getUserName());
				answer.setUpdateDate(new Date());
				answer.setUpdaterId(user.getUserId());
				answer.setUpdaterName(user.getUserName());
				answer.setQuestionId( questionId );
				answer.setAnswerId( idgenService.getNextId() );
			}
	    	
	    	
	    	
	    	questionService.create( question );
	    	
	    	result =  question;
	    	
	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }

	    return result;
	}
	
	/**
	 * 설문문항 수정
	 * @param question
	 * @return
	 */
	@RequestMapping(value = "/updateQuestion")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Question updateQuestion(@RequestBody Question question) {
		Question result = null;
		User user = (User)getRequestAttribute("ikep.user");
		
	    try {
	    	question.setRegistDate(new Date());
	    	question.setRegisterId(user.getUserId());
	    	question.setRegisterName(user.getUserName());
	    	question.setUpdateDate(new Date());
	    	question.setUpdaterId(user.getUserId());
	    	question.setUpdaterName(user.getUserName());
	    	
	    	//System.out.println(question);
			
	    	boolean exists = questionGroupService.exists( question.getQuestionGroupId() );
	    	
	    	if( !exists ) {throw new Exception();}
	    	
	    	for (Answer answer : question.getAnswer() ) {
				answer.setRegistDate(new Date());
				answer.setRegisterId(user.getUserId());
				answer.setRegisterName(user.getUserName());
				answer.setUpdateDate(new Date());
				answer.setUpdaterId(user.getUserId());
				answer.setUpdaterName(user.getUserName());
				answer.setQuestionId( question.getQuestionId() );
				//answer.setAnswerId( idgenService.getNextId() );
				if(answer.getAnswerId()==null){
					answer.setAnswerId( idgenService.getNextId() );
				}else{
					answer.setAnswerId( answer.getAnswerId());
				}
				
				//파일내용 추가
				if( answer.getImg() != null && !answer.getImg().equals("") && ( question.getQuestionType().equals("A1") || question.getQuestionType().equals("A5")) )
				{
					List<FileData> fileList = fileService.getItemFile( answer.getAnswerId() , "");
					boolean equalFileName =false;
					
					for (FileData fileData : fileList) {
						if( fileData.getFileId().equals( answer.getImg() ) )
						{
							equalFileName =true;
							break;
						}
					}
					
					if( !equalFileName )
					{
						fileService.removeItemFile(answer.getAnswerId());
						fileService.createFileLink(answer.getImg(), answer.getAnswerId(), IKepConstant.ITEM_TYPE_CODE_SURVEY,user);
					}	
				}
			}	    	
	    	questionService.update( question );
	    	
	    	result =  question;
	    	
	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }

	    return result;
	}

	/**
	 * 설문 문항 삭제
	 * @param question
	 * @return
	 */
	@RequestMapping(value = "/removeQuestion")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Question removeQuestion(@RequestBody Question question) {
		Question result = null;
		
	    try 
	    {
	    	boolean exists = questionGroupService.exists( question.getQuestionGroupId() );
	    	if( !exists ) {throw new Exception();}
	    	
	    	questionService.delete( question.getQuestionId() );
	    	
	    	result =  question;
	    	
	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }

	    return result;
	}
	
	@RequestMapping(value = "/joinQuestionMng", method = RequestMethod.GET)
	public ModelAndView joinQuestionMng(@RequestParam(value = "surveyId", required = true) String surveyId) {
		User user = (User) getRequestAttribute("ikep.user");


		
		List<Question> questionList = questionService.getReportAllBySurveyId(surveyId);
		
		List<Question> joinQuestionList = questionService.getJoinSelectTypeAnswer(surveyId);



		ModelAndView mav = new ModelAndView("/servicepack/survey/question/joinQuestionMng");
		mav.addObject("joinQuestionList", joinQuestionList);
		mav.addObject("questionList", questionList);
		mav.addObject("user", user);


		return mav;
	}
	@RequestMapping(value = "/joinQuestion")
	public @ResponseBody
	Map<String, Object> joinQuestion(HttpServletRequest request) {

		Map<String, Object> result=new HashMap<String, Object>();

		try {		
			String surveyId = request.getParameter("surveyId");
			Map<String,Object> requestParam = SurveyWebUtils.getParametersStartingWith(request, "");
			Collection<Object>  collection = requestParam.values();
			Map<String, String> params=new HashMap<String, String>();
			for (Iterator<Object> iterator = collection.iterator(); iterator.hasNext();) {
				String[] value = iterator.next().toString().split("_");
				if(value.length==2){
					//System.out.println("################조인할answerId:"+value[0]+"###조인될questionId:"+value[1]);
					if(StringUtil.isEmpty(params.get(value[0]))){
						params.put(value[0], value[1]);
					}else{
						String prevValue = params.get(value[0]);
						params.remove(value[0]);
						params.put(value[0], prevValue+"|"+value[1]);
					}
				}

			}
			questionService.removeJoinQuestion(surveyId); 
			for ( Map.Entry< String , String > keyValue : params.entrySet( ) )
			{
				//System.out.println("################조인할answerId:"+keyValue.getKey());
				//System.out.println("################조인될questionId:"+keyValue.getValue());
				Question joinQuestion = new Question();
				List<Answer> answerList=new ArrayList<Answer>();
				Answer joinAnswer = new Answer();
				joinAnswer.setAnswerId(keyValue.getKey());
				joinAnswer.setJoinQuestionIds( keyValue.getValue() );
				answerList.add(joinAnswer);
				joinQuestion.setAnswer(answerList);
				questionService.updateJoinAnswer(joinQuestion);
			}
			
			result.put("success", "success");
		} catch (Exception ex) {
			result.put("success", "fail");
	
		}

		return result;
	}
	
	@RequestMapping("/updateAnswerTitle")
	public @ResponseBody
	Map<String, Object> updateAnswerTitle(@RequestBody Map<String, String> params) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			questionService.updateAnswerTitle(params);
			result.put("success","success");
		} catch (Exception ex) {
			result.put("success",  "fail");
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
}
