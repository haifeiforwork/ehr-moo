/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.testreq.model;

/**
 * 시험의뢰서 Permission VO
 * 
 * @author pjh
 * @version $Id$
 */
public class TestRequestPermission {
	
	// 의뢰부서  수정 가능여부
	private boolean reqViewActive = false;
	
	// 주관부서 수정 가능여부
	private boolean rcvViewActive = false;
	
	// 추가사항 수정 가능여부
	private boolean writeDetailViewActive = false;
	
	// 접수버튼
	private boolean receiptBtnActive = false;
	
	// 저장버튼
	private boolean saveBtnActive = false;
	
	// 삭제버튼
	private boolean deleteBtnActive = false;
	
	// 승인버튼 활성화여부
	private boolean approveBtnActive = false;
	
	// 부결버튼 활성화여부
	private boolean rejectBtnActive = false;
	
	// 부결사유등록 활성화여부
	private boolean rejectResonBtnActive = false;
	
	//추가사항 버튼 활성화여부
	private boolean addWriteDetailBtnActive = false;
	
	// 부결여부
	private boolean isRejected = false;
	
	// ecm권한
	private boolean ecmRoll = false;
	
	// 초기화
	private boolean initRejctBtnActive = false;
	
	// 접수자가 있는상태에서 접수하기 버튼
	private boolean reReceiptBntActive = false;
	
	// 상신자
	private boolean drafter = false;
	/**
	 * 생성자
	 */
	public TestRequestPermission() {
	}
	
	public TestRequestPermission( boolean init) {
		
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
	 * 반려이면 반려버튼 비활성화, 반려사유보기버튼 활성화
	 */
	public void setRejectBtnActive(){
		
		this.rejectBtnActive = false;
		this.rejectResonBtnActive = true;
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
	 * 주관부서 활성화
	 */
	public void setRcvModActive() {
		this.saveBtnActive = true;
		this.rcvViewActive = true;
		this.approveBtnActive = true;
		this.rejectBtnActive = true;
	}
	
	/**
	 * 주관부서 활성화 -상신후
	 */
	public void setRcvModActive2() {
		this.saveBtnActive = true;
		this.rcvViewActive = true;
	}
	
	/**
	 * 검토,승인단계 버튼 활성화
	 */
	public void setBtnApprRejectActive(){
		this.approveBtnActive = true;
		this.rejectBtnActive = true;
		
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
	 * @return the rcvViewActive
	 */
	public boolean isRcvViewActive() {
		return rcvViewActive;
	}

	/**
	 * @param rcvViewActive the rcvViewActive to set
	 */
	public void setRcvViewActive(boolean rcvViewActive) {
		this.rcvViewActive = rcvViewActive;
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
	 * @return the addWriteDetailBtnActive
	 */
	public boolean isAddWriteDetailBtnActive() {
		return addWriteDetailBtnActive;
	}

	/**
	 * @param addWriteDetailBtnActive the addWriteDetailBtnActive to set
	 */
	public void setAddWriteDetailBtnActive(boolean addWriteDetailBtnActive) {
		this.addWriteDetailBtnActive = addWriteDetailBtnActive;
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
	 * @return the writeDetailViewActive
	 */
	public boolean isWriteDetailViewActive() {
		return writeDetailViewActive;
	}

	/**
	 * @param writeDetailViewActive the writeDetailViewActive to set
	 */
	public void setWriteDetailViewActive(boolean writeDetailViewActive) {
		this.writeDetailViewActive = writeDetailViewActive;
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
}
