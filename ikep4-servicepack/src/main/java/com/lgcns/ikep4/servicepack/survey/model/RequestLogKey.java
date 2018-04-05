/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.survey.model;
/**
 * 
 * 설문조사 메일 배치
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: RequestLogKey.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class RequestLogKey extends SurveyBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = 1918894022808625646L;

	/**
    SEND_SEQ[설문 메일발송요청 순차]
     */
    private Integer sendSeq;

    /**
    SURVEY_ID[설문 ID]
     */
    private String surveyId;
    
    

    public RequestLogKey() {
		super();
	}

	public RequestLogKey(Integer sendSeq, String surveyId) {
		super();
		this.sendSeq = sendSeq;
		this.surveyId = surveyId;
	}

	public Integer getSendSeq() {
        return sendSeq;
    }

    public void setSendSeq(Integer sendSeq) {
        this.sendSeq = sendSeq;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId == null ? null : surveyId.trim();
    }
}