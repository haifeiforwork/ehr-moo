/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.npd.model;

/**
 * 신제품 개발 Permission VO
 * 
 * @author pjh
 * @version $Id$
 */
public class NpdPermission {
	
	// 의뢰자 수정 가능여부
	private boolean reqViewActive = false;
	
	// TCS 수정 가능여부
	private boolean tcsViewActive = false;
	
	// 2차검토부서접수
	private boolean sndRcvModiEnale = false;
	
	// 2차검토부서결과
	private boolean sndRsltModiEnale = false;
	
	// 2차검토 수정 가능여부
	private boolean sndRcvViewActive = false;
	
	// 2차검토 수정 가능여부
	private boolean sndRsltViewActive = false;
	
	// 승인버튼 활성화여부
	private boolean approveBtnActive = false;
	
	// 부결버튼 활성화여부
	private boolean rejectBtnActive = false;
	
	// 부결사유등록 활성화여부
	private boolean rejectResonBtnActive = false;
	
	// 부결여부
	private boolean isRejected = false;
	
	// 저장버튼
	private boolean saveBtnActive = false;
	
	// 접수버튼
	private boolean receiptBtnActive = false;
	
	// 초기화
	private boolean initRejctBtnActive = false; 
	
	// 결과파일버튼
	private boolean rsltFileBtnActive = false;
	
	// 결과파일 보기활성화 여부
	private boolean rsltFileViewActive = false;
	
	private boolean deleteBtnActive = false;
	
	// 접수자가 있는상태에서 접수하기 버튼
	private boolean reReceiptBntActive = false;
	
	// ECM 사용자여부
	private boolean ecmRoll = false;
	
	// 상신자
	private boolean drafter = false;
	
	// 요구일정 상세일자
	private boolean reqScheduleCdBtnActive = false;
	
	public NpdPermission() {
	}
	
	public NpdPermission( boolean init) {
		
		this.reqViewActive = init;
		this.saveBtnActive = init;
		this.drafter = init;
	}
	
	/**
	 * 의뢰자 접수 단계 권환
	 * @param active
	 */
	public void setReqModActive() {
		this.reqViewActive = true;
		this.approveBtnActive = true;
		this.saveBtnActive = true;
		this.deleteBtnActive = true;
	}
	
	/**
	 * 의뢰자 상신후 결재가 이뤄지지 않았을때 권한
	 */
	public void setReqModActive2() {
		this.reqViewActive = true;
		this.saveBtnActive = true;
		this.deleteBtnActive = true;
	}
	
	/**
	 * 검토,승인단계 버튼 활성화
	 */
	public void setBtnApprRejectActive(){
		this.approveBtnActive = true;
		this.rejectBtnActive = true;
	}
	
	/**
	 * 부결시 승인,부결,접수 버튼울 비활성화/활성화
	 * @param isReject
	 */
	public void setBtnActive( boolean isReject) {
		
		this.approveBtnActive = isReject;
		this.rejectBtnActive = isReject;
		this.isRejected = isReject;
	}
	
	/**
	 * TCS 활성화
	 */
	public void setTcsModActive() {
		this.saveBtnActive = true;
		this.reqViewActive = true;
		this.tcsViewActive = true;
		this.approveBtnActive = true;
		this.rejectBtnActive = true;
	}
	
	/**
	 * TCS 활성화 -상신후
	 */
	public void setTcsModActive2() {
		this.saveBtnActive = true;
		this.reqViewActive = true;
		this.tcsViewActive = true;
	}
	
	/**
	 * 2차검토부서접수 활성화
	 */
	public void setSndRcvModActive() {
		this.saveBtnActive = true;
		this.sndRcvViewActive = true;
		this.sndRcvModiEnale= true;
		this.approveBtnActive = true;
		this.rsltFileBtnActive = true;
		this.rejectBtnActive = true;
	}
	
	
	/**
	 * 2차검토부서접수 활성화
	 */
	public void setSndRcvModActive2() {
		this.saveBtnActive = true;
		this.sndRcvViewActive = true;
		this.sndRcvModiEnale= true;
		this.rsltFileBtnActive = true;
	}
	
	/**
	 * 2차검토부서결과 활성화
	 */
	public void setSndRsltModActive(){
		this.saveBtnActive = true;
		this.sndRsltViewActive = true;
		this.sndRsltModiEnale= true;
		this.approveBtnActive = true;
		this.rejectBtnActive = true;
	}
	
	/**
	 * 2차검토부서결과 활성화 - 상신후
	 */
	public void setSndRsltModActive2(){
		this.saveBtnActive = true;
		this.sndRsltViewActive = true;
		this.sndRsltModiEnale= true;
	}
	
	/**
	 * 반려이면 반려버튼 비활성화, 반려사유보기버튼 활성화
	 */
	public void setRejectBtnActive(){
		
		this.rejectBtnActive = false;
		this.rejectResonBtnActive = true;
	}
	
	/**
	 * @return the reqViewActive
	 */
	public boolean isReqViewActive() {
		return reqViewActive;
	}

	/**
	 * @param reqViewActive the reqViewActive to set
	 */
	public void setReqViewActive(boolean reqViewActive) {
		this.reqViewActive = reqViewActive;
	}

	/**
	 * @return the tcsViewActive
	 */
	public boolean isTcsViewActive() {
		return tcsViewActive;
	}

	/**
	 * @param tcsViewActive the tcsViewActive to set
	 */
	public void setTcsViewActive(boolean tcsViewActive) {
		this.tcsViewActive = tcsViewActive;
	}
	
	/**
	 * @return the sndRcvModiEnale
	 */
	public boolean isSndRcvModiEnale() {
		return sndRcvModiEnale;
	}

	/**
	 * @param sndRcvModiEnale the sndRcvModiEnale to set
	 */
	public void setSndRcvModiEnale(boolean sndRcvModiEnale) {
		this.sndRcvModiEnale = sndRcvModiEnale;
	}

	/**
	 * @return the sndRsltModiEnale
	 */
	public boolean isSndRsltModiEnale() {
		return sndRsltModiEnale;
	}

	/**
	 * @param sndRsltModiEnale the sndRsltModiEnale to set
	 */
	public void setSndRsltModiEnale(boolean sndRsltModiEnale) {
		this.sndRsltModiEnale = sndRsltModiEnale;
	}

	/**
	 * @return the sndRcvViewActive
	 */
	public boolean isSndRcvViewActive() {
		return sndRcvViewActive;
	}

	/**
	 * @param sndRcvViewActive the sndRcvViewActive to set
	 */
	public void setSndRcvViewActive(boolean sndRcvViewActive) {
		this.sndRcvViewActive = sndRcvViewActive;
	}

	/**
	 * @return the sndRsltViewActive
	 */
	public boolean isSndRsltViewActive() {
		return sndRsltViewActive;
	}

	/**
	 * @param sndRsltViewActive the sndRsltViewActive to set
	 */
	public void setSndRsltViewActive(boolean sndRsltViewActive) {
		this.sndRsltViewActive = sndRsltViewActive;
	}

	/**
	 * @return the approveBtnActive
	 */
	public boolean isApproveBtnActive() {
		return approveBtnActive;
	}

	/**
	 * @param approveBtnActive the approveBtnActive to set
	 */
	public void setApproveBtnActive(boolean approveBtnActive) {
		this.approveBtnActive = approveBtnActive;
	}

	/**
	 * @return the rejectBtnActive
	 */
	public boolean isRejectBtnActive() {
		return rejectBtnActive;
	}

	/**
	 * @param rejectBtnActive the rejectBtnActive to set
	 */
	public void setRejectBtnActive(boolean rejectBtnActive) {
		this.rejectBtnActive = rejectBtnActive;
	}
	
	/**
	 * @return the rejectResonBtnActive
	 */
	public boolean isRejectResonBtnActive() {
		return rejectResonBtnActive;
	}

	/**
	 * @param rejectResonBtnActive the rejectResonBtnActive to set
	 */
	public void setRejectResonBtnActive(boolean rejectResonBtnActive) {
		this.rejectResonBtnActive = rejectResonBtnActive;
	}

	/**
	 * @return the isRejected
	 */
	public boolean isRejected() {
		return isRejected;
	}

	/**
	 * @param isRejected the isRejected to set
	 */
	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}

	/**
	 * @return the saveBtnActive
	 */
	public boolean isSaveBtnActive() {
		return saveBtnActive;
	}

	/**
	 * @param saveBtnActive the saveBtnActive to set
	 */
	public void setSaveBtnActive(boolean saveBtnActive) {
		this.saveBtnActive = saveBtnActive;
	}

	/**
	 * @return the receiptBtnActive
	 */
	public boolean isReceiptBtnActive() {
		return receiptBtnActive;
	}

	/**
	 * @param receiptBtnActive the receiptBtnActive to set
	 */
	public void setReceiptBtnActive(boolean receiptBtnActive) {
		this.receiptBtnActive = receiptBtnActive;
	}

	/**
	 * @return the rsltFileBtnActive
	 */
	public boolean isRsltFileBtnActive() {
		return rsltFileBtnActive;
	}

	/**
	 * @param rsltFileBtnActive the rsltFileBtnActive to set
	 */
	public void setRsltFileBtnActive(boolean rsltFileBtnActive) {
		this.rsltFileBtnActive = rsltFileBtnActive;
	}

	/**
	 * @return the rsltFileViewActive
	 */
	public boolean isRsltFileViewActive() {
		return rsltFileViewActive;
	}

	/**
	 * @param rsltFileViewActive the rsltFileViewActive to set
	 */
	public void setRsltFileViewActive(boolean rsltFileViewActive) {
		this.rsltFileViewActive = rsltFileViewActive;
	}

	/**
	 * @return the deleteBtnActive
	 */
	public boolean isDeleteBtnActive() {
		return deleteBtnActive;
	}

	/**
	 * @param deleteBtnActive the deleteBtnActive to set
	 */
	public void setDeleteBtnActive(boolean deleteBtnActive) {
		this.deleteBtnActive = deleteBtnActive;
	}

	/**
	 * @return the initRejctBtnActive
	 */
	public boolean isInitRejctBtnActive() {
		return initRejctBtnActive;
	}

	/**
	 * @param initRejctBtnActive the initRejctBtnActive to set
	 */
	public void setInitRejctBtnActive(boolean initRejctBtnActive) {
		this.initRejctBtnActive = initRejctBtnActive;
	}

	/**
	 * @return the reReceiptBntActive
	 */
	public boolean isReReceiptBntActive() {
		return reReceiptBntActive;
	}

	/**
	 * @param reReceiptBntActive the reReceiptBntActive to set
	 */
	public void setReReceiptBntActive(boolean reReceiptBntActive) {
		this.reReceiptBntActive = reReceiptBntActive;
	}

	/**
	 * @return the ecmRoll
	 */
	public boolean isEcmRoll() {
		return ecmRoll;
	}

	/**
	 * @param ecmRoll the ecmRoll to set
	 */
	public void setEcmRoll(boolean ecmRoll) {
		this.ecmRoll = ecmRoll;
	}

	/**
	 * @return the drafter
	 */
	public boolean isDrafter() {
		return drafter;
	}

	/**
	 * @param drafter the drafter to set
	 */
	public void setDrafter(boolean drafter) {
		this.drafter = drafter;
	}

	/**
	 * @return the reqScheduleCdBtnActive
	 */
	public boolean isReqScheduleCdBtnActive() {
		return reqScheduleCdBtnActive;
	}

	/**
	 * @param reqScheduleCdBtnActive the reqScheduleCdBtnActive to set
	 */
	public void setReqScheduleCdBtnActive(boolean reqScheduleCdBtnActive) {
		this.reqScheduleCdBtnActive = reqScheduleCdBtnActive;
	}
}
