/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.survey.model;

import java.util.Date;

/**
 * 
 * 설문 메일 로그
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: RequestLog.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class RequestLog extends RequestLogKey {
    /**
	 *
	 */
	private static final long serialVersionUID = -4345849598272535310L;

	/**
    SEND_OPTION[설문 메일발송 옵션( 0 : 즉시, 1 : 1시간후, 6 : 6시간후, 12 : 12시간후)]
     */
    private Integer sendOption;

    /**
    SEND_REQUEST_DATE[설문 메일발송 요청 시간(발신옵션 선택에 따른 요청 시간)]
     */
    private Date sendRequestDate;

    /**
    SEND_START_DATE[설문 메일발송 배치 프로그램 시작일시]
     */
    private Date sendStartDate;

    /**
    SEND_END_DATE[설문 메일발송 배치 프로그램 종료일시]
     */
    private Date sendEndDate;

    /**
    SEND_SUCCESS_COUNT[설문 메일발송 성공 건수]
     */
    private Integer sendSuccessCount;

    /**
    SEND_TARGET_COUNT[설문 메일발송 총 대상 건수]
     */
    private Integer sendTargetCount;
    
    private String sendComment;


    public Date getSendRequestDate() {
        return sendRequestDate;
    }

    public void setSendRequestDate(Date sendRequestDate) {
        this.sendRequestDate = sendRequestDate;
    }

    public Date getSendStartDate() {
        return sendStartDate;
    }

    public void setSendStartDate(Date sendStartDate) {
        this.sendStartDate = sendStartDate;
    }

    public Date getSendEndDate() {
        return sendEndDate;
    }

    public void setSendEndDate(Date sendEndDate) {
        this.sendEndDate = sendEndDate;
    }

	public Integer getSendSuccessCount() {
		return sendSuccessCount;
	}

	public Integer getSendTargetCount() {
		return sendTargetCount;
	}

	public void setSendSuccessCount(Integer sendSuccessCount) {
		this.sendSuccessCount = sendSuccessCount;
	}

	public void setSendTargetCount(Integer sendTargetCount) {
		this.sendTargetCount = sendTargetCount;
	}

	public Integer getSendOption() {
		return sendOption;
	}

	public void setSendOption(Integer sendOption) {
		this.sendOption = sendOption;
	}

	public String getSendComment() {
		return sendComment;
	}

	public void setSendComment(String sendComment) {
		this.sendComment = sendComment;
	}

}