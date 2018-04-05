package com.lgcns.ikep4.servicepack.survey.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * 
 * 설문발송내역정보
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SendLog.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class SendLog extends SurveyBaseObject{
    /**
	 *
	 */
	private static final long serialVersionUID = 2500861836858405598L;

	/**
     * 설문 ID
     */
    private String surveyId;

    /**
     * 발신대상 사용자 순차 번호
     */
    private Integer sendSeq;

    /**
     * 발신 횟수 (설문미응답 대상자에게 재발송시)
     */
    private Integer sendCount;

    /**
     *수신자 ID
     */
    private String receiverId;

    /**
     * 수신자 이름
     */
    private String receiverName;

    /**
     *수신자 이메일
     */
    private String receiverMail;

    /**
     * 발신일시
     */
    @DateTimeFormat(pattern="yyyy.MM.dd")
    private Date sendDate;

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getSendSeq() {
		return sendSeq;
	}

	public void setSendSeq(Integer sendSeq) {
		this.sendSeq = sendSeq;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMail() {
		return receiverMail;
	}

	public void setReceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
}