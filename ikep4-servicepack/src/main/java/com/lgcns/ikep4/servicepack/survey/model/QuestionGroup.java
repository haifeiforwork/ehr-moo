package com.lgcns.ikep4.servicepack.survey.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 설문 문항 그룹
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: QuestionGroup.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class QuestionGroup extends SurveyBaseObject{
    /**
	 *
	 */
	private static final long serialVersionUID = -6317286900680817482L;

	/**
     *그룹아이디
     */
    private String questionGroupId;

    /**
     * 설문아이디
     */
    private String surveyId;

    /**
     * 그룹순번
     */
    private Integer questionGroupSeq;

    /**
     * 그룹제목
     */
    private String title;
    
    /**
     * 그룹상세
     */
    private String contents;
    
    private List<Question> questionList=new ArrayList<Question>();
    

	public QuestionGroup() {
		super();
	}
	
	

	public List<Question> getQuestionList() {
		return questionList;
	}



	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}



	public String getQuestionGroupId() {
		return questionGroupId;
	}

	public void setQuestionGroupId(String questionGroupId) {
		this.questionGroupId = questionGroupId;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getQuestionGroupSeq() {
		return questionGroupSeq;
	}

	public void setQuestionGroupSeq(Integer questionGroupSeq) {
		this.questionGroupSeq = questionGroupSeq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}