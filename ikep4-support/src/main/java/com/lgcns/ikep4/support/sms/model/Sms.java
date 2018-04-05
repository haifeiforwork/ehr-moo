/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.sms.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서혜숙
 * @version $Id: Sms.java 13649 2011-05-27 11:51:51Z shs0420 $
 */
public class Sms extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4384194596792625462L;

	/**
	 * smsId
	 */
	@NotEmpty
	private String smsId;
	
	/**
	 * 서비스 코드 SMS : SMS01 LMS : LMS01
	 */
	@NotEmpty
	private String serviceCode;	
	
	/**
	 * SMS 메시지 유형 SMS = A, LMS = M
	 */
	@NotEmpty
	private String messageType;
	
	/**
	 * SMS 발신 일자 - 예약 발송에서 사용됨 14자로 제한
	 */
	@Size(max=14)
	private String smsSendDate;
	
	
	/**
	 * 발신자전화번호
	 */
	@NotEmpty
	private String senderPhoneno;

	/**
	 * 수신자아이디
	 */
	@NotEmpty
	private String receiverId;
	
	/**
	 * receiverId 들 
	 */
	private String[] receiverIds;	

	/**
	 * receiverPhoneno 들 
	 */
	private String[] receiverPhonenos;	

	/**
	 * 수신자전화번호
	 */
	@NotEmpty
	private String receiverPhoneno;

	/**
	 * 발신 메시지 내용
	 */
	@Size(min=0, max=4000)
	private String contents;

	/**
	 * 발신 결과 CODE
	 */
	private String resultCode;		

	/**
	 * 등록자 발신자ID
	 */
	@NotEmpty
	private String registerId;		
	
	/**
	 * 등록자 이름
	 */
	@NotEmpty
	private String registerName;	

	/**
	 * 등록일시
	 */
	@NotEmpty
	private Date registDate;	
	
	/**
	 * 수신자 이름
	 */
	private String receiverName;	
		
	/**
	 * 수신자 팀
	 */
	private String receiverTeamName;	

	/**
	 * 게시판 가져올 끝 수
	 */
	private String endNo;
	
	/**
	 * 게시판 가져올 처음 수
	 */
	private String baseNo;	

	/**
	 * 수신자 리스트
	 */
	private List<String> receiverList;

	/**
	 * 현재 페이지 번호
	 */
	private int curPage;
	
	/**
	 * 수신자 영어이름
	 */	
	private String receiverEnglishName;
	
	/**
	 * 수신자 팀영어명
	 */		
	private String receiverTeamEnglishName;
	
	/**
	 * SMS 플래그(0:시스템, 1:SMS) 
	 */		
	private int smsFlag;
	
	/**
	 * SMS 발신  메시지 제목
	 */
	private String subject;

	
	public int getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(int smsFlag) {
		this.smsFlag = smsFlag;
	}

	public String getReceiverEnglishName() {
		return receiverEnglishName;
	}

	public void setReceiverEnglishName(String receiverEnglishName) {
		this.receiverEnglishName = receiverEnglishName;
	}

	public String getReceiverTeamEnglishName() {
		return receiverTeamEnglishName;
	}

	public void setReceiverTeamEnglishName(String receiverTeamEnglishName) {
		this.receiverTeamEnglishName = receiverTeamEnglishName;
	}

	/**
	 * @return the smsId
	 */
	public String getSmsId() {
		return smsId;
	}

	/**
	 * @param id the smsId to set
	 */
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	/**
	 * @return the senderPhoneno
	 */
	public String getSenderPhoneno() {
		return senderPhoneno;
	}

	/**
	 * @param senderPhoneno the senderPhoneno to set
	 */
	public void setSenderPhoneno(String senderPhoneno) {
		this.senderPhoneno = senderPhoneno;
	}
	
	

	
	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	
	public String[] getReceiverPhonenos() {
		return receiverPhonenos;
	}

	public void setReceiverPhonenos(String[] receiverPhonenos) {
		if ( receiverPhonenos != null ) {
			this.receiverPhonenos = new String[receiverPhonenos.length];
			System.arraycopy(receiverPhonenos, 0, this.receiverPhonenos, 0, receiverPhonenos.length);
		}
	}

	public String[] getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(String[] receiverIds) {
		if ( receiverIds != null ) {
			this.receiverIds = new String[receiverIds.length];
			System.arraycopy(receiverIds, 0, this.receiverIds, 0, receiverIds.length);
		}
	}
	
	/**
	 * @return the receiverPhoneno
	 */
	public String getReceiverPhoneno() {
		return receiverPhoneno;
	}

	/**
	 * @param receiverPhoneno the receiverPhoneno to set
	 */
	public void setReceiverPhoneno(String receiverPhoneno) {
		this.receiverPhoneno = receiverPhoneno;
	}
	
	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	/**
	 * @return the resultCode
	 */		
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */		
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}	
	
	/**
	 * @return the registerId
	 */		
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */		
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}	
	
	/**
	 * @return the registerName
	 */		
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */		
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registDate
	 */		
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */		
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverTeamName() {
		return receiverTeamName;
	}

	public void setReceiverTeamName(String receiverTeamName) {
		this.receiverTeamName = receiverTeamName;
	}	
	
	public String getEndNo() {
		return endNo;
	}

	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	public String getBaseNo() {
		return baseNo;
	}

	public void setBaseNo(String baseNo) {
		this.baseNo = baseNo;
	}	
	
	public List<String> getReceiverList() {
		return receiverList;
	}

	public void setReceiverList(List<String> receiverList) {
		this.receiverList = receiverList;
	}	
	
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}


	public void setSmsSendDate(String smsSendDate) {
		this.smsSendDate = smsSendDate;
	}

	public String getSmsSendDate() {
		return smsSendDate;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return messageType;
	}	
}
