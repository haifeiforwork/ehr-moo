package com.lgcns.ikep4.servicepack.survey.model;



public class Answer extends SurveyBaseObject{
    /**
	 *
	 */
	private static final long serialVersionUID = -8929534880176684192L;

	/**
     * 설문문항답변 ID
     */
    private String answerId;

    /**
     * 설문문항질문 ID
     */
    private String questionId;

    /**
     * 설문문항답변 제목
     */
    private String title;
    
    /**
     * 설문문항답변 정렬순서
     */
    private Integer answerSeq;
    
	/**
	 * 질문순번
	 */
	private Integer questionSeq;
	
    /**
     * 그룹순번
     */
    private Integer questionGroupSeq;
    
    
    private String img;
    
    private String joinQuestionIds;
    
    
    
    public String getJoinQuestionIds() {
		return joinQuestionIds;
	}

	public void setJoinQuestionIds(String joinQuestionIds) {
		this.joinQuestionIds = joinQuestionIds;
	}

	public Integer getQuestionSeq() {
		return questionSeq;
	}

	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

	public Integer getQuestionGroupSeq() {
		return questionGroupSeq;
	}

	public void setQuestionGroupSeq(Integer questionGroupSeq) {
		this.questionGroupSeq = questionGroupSeq;
	}

	//설문 총분석
    private Integer responseCnt;
    private String responseText;
    private int[] response1 = new int[0];
    private int[] response2 = new int[0];
    

	public int[] getResponse1() {
		return response1;
	}

	public int[] getResponse2() {
		return response2;
	}

	public void setResponse1(int[] response1) {
		if(response1 != null){
			this.response1 = new int[response1.length];
			System.arraycopy(response1, 0, this.response1, 0, response1.length);
		}
		//this.response1 = response1;
	}

	public void setResponse2(int[] response2) {
		//this.response2 = response2;
		if(response2 != null){
			this.response2 = new int[response2.length];
			System.arraycopy(response2, 0, this.response2, 0, response2.length);
		}
	}

	public Integer getResponseCnt() {
		return responseCnt;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseCnt(Integer responseCnt) {
		this.responseCnt = responseCnt;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Answer() {
		super();
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getAnswerSeq() {
		return answerSeq;
	}

	public void setAnswerSeq(Integer answerSeq) {
		this.answerSeq = answerSeq;
	}

   
}