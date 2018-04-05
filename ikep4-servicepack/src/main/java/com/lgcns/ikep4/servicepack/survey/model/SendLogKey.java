package com.lgcns.ikep4.servicepack.survey.model;

/**
 * 
 * 설문발송내역정보
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SendLogKey.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class SendLogKey extends SurveyBaseObject{
    /**
	 *
	 */

	/**
	 *
	 */
	private static final long serialVersionUID = 6455816657105207654L;

	/**
     * 설문 ID
     */
    private String surveyId;

    /**
     *수신자 ID
     */
    private String receiverId;
    
    

	public SendLogKey(String surveyId, String receiverId) {
		super();
		this.surveyId = surveyId;
		this.receiverId = receiverId;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

    
}