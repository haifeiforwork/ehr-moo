package com.lgcns.ikep4.servicepack.survey.model;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 설문 문항 테이블
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: Question.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class Question extends SurveyBaseObject{
	
    /**
	 *
	 */
	private static final long serialVersionUID = -3339996281748079783L;

	
	
	 /**
     * 설문 문항 질문 ID
     */
    private String questionId;

    /**
     * 그룹아이디
     */
    private String questionGroupId;
	/**
	 * 질문순번
	 */
	private Integer questionSeq;
	

    /**
     *설문문항 질문제목
     */
    private String title;

    
    
    /**
     * 설문문항 질문형식
		( A0 : 단일선택 기본형,
		A1 : 단일선택 이미지,
		A2 : 단일선택 TextBox 추가,
		A3 : 단일선택 TextArea 추가,
		A4 : 다중선택 기본형
		A5 : 다중선택 이미지,
		A6 : 다중선택 TextBox 추가,
		A7 : 다중선택 TextArea 추가,
		B0: 단일 TextBox,
		B0: TextBox Form,
		B0: 다중TextBox,
		B0: TextArea,
		C0 : 순위
		D0 : 척도 3점,
		D0 : 척도 5점,
		D0 : 척도 6점,
		D0 : 척도 7점,
		D0 : 척도 OX + 5점,
		D0 : 척도 OX + 7점,
		D0 : 척도 N/A + 7점,
		D0 : 수준 )
     */
    private String questionType;

    /**
     *설문 문항 필수 답변 여부
	( 0 : 필수아님, 1 : 필수)
     */
    private Integer requiredAnswer;

    /**
     * 컬럼일경우에 로갯수
     */
    private Integer columnCount;
    
    //수직,수평,컬럼
    private Integer displayType;

    /**
     * 척도 1
     */
    private String scale1;

    /**
     * 척도 2
     */
    private String scale2;

    /**
     * 척도 3
     */
    private String scale3;

    /**
     * 요구 역량 점수
     */
    private Long needAbility;
    
    /**
     * 답변갯수
     */
    private int answerRowCnt;


	private List<Answer> answer = new ArrayList<Answer>();
    
    
	

	public Question() {
		super();
	}

	public List<Answer> getAnswer() {
		return answer;
	}

	public void setAnswer(List<Answer> answer) {
		this.answer = answer;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionGroupId() {
		return questionGroupId;
	}

	public void setQuestionGroupId(String questionGroupId) {
		this.questionGroupId = questionGroupId;
	}

	public Integer getQuestionSeq() {
		return questionSeq;
	}

	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Integer getRequiredAnswer() {
		return requiredAnswer;
	}

	public void setRequiredAnswer(Integer requiredAnswer) {
		this.requiredAnswer = requiredAnswer;
	}

	public String getScale1() {
		return scale1;
	}

	public void setScale1(String scale1) {
		this.scale1 = scale1;
	}

	public String getScale2() {
		return scale2;
	}

	public void setScale2(String scale2) {
		this.scale2 = scale2;
	}

	public String getScale3() {
		return scale3;
	}

	public void setScale3(String scale3) {
		this.scale3 = scale3;
	}

	public Long getNeedAbility() {
		return needAbility;
	}

	public void setNeedAbility(Long needAbility) {
		this.needAbility = needAbility;
	}

	public Integer getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}

	public Integer getDisplayType() {
		return displayType;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	/**
	 * @return the answerRowCnt
	 */
	public int getAnswerRowCnt() {
		return answerRowCnt;
	}

	/**
	 * @param answerRowCnt the answerRowCnt to set
	 */
	public void setAnswerRowCnt(int answerRowCnt) {
		this.answerRowCnt = answerRowCnt;
	}


	
	
}