/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.proposal.model;

/**
 * 개발제안서 개발 Permission VO
 * 
 * @author pjh
 * @version $Id$
 */
public class ProposalPermission {
	
	// 의뢰자 수정 가능여부
	private boolean reqViewActive = false;
	
	// TCS 수정여부
	private boolean tcsViewActive = false;
	
	// 임시저장
	private boolean tempSaveBtnActive = false;
	
	// 저장버튼
	private boolean saveBtnActive = false;
	
	// 삭제
	private boolean deleteBtnActive = false;
	
	// 파일
	private boolean fileBtnActive = false;
	
	// 품질보증부
	private boolean qadAuth = false;
	// 신제품연구팀
	private boolean labAuth = false;
	// 영업팀
	private boolean salesAuth = false;
	// SCM팀
	private boolean scmAuth = false;
	// 마케팅팀
	private boolean marketAuth = false;
	// TCS팀
	private boolean tcsAuth = false;
	
	// ECM 사용자여부
	private boolean ecmRoll = false;

	
	// 저장타입
	private String saveType = "";
	
	public ProposalPermission() {
	}
	
	public ProposalPermission(boolean init) {
		
		this.reqViewActive = init;
		this.tempSaveBtnActive = init;
		this.fileBtnActive = init;
	}
	
	/**
	 * 의뢰자 
	 */
	public void reqViewEnable() {
		
		this.reqViewActive = true;
		this.fileBtnActive = true;
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
	 * @return the tempSaveBtnActive
	 */
	public boolean isTempSaveBtnActive() {
		return tempSaveBtnActive;
	}

	/**
	 * @param tempSaveBtnActive the tempSaveBtnActive to set
	 */
	public void setTempSaveBtnActive(boolean tempSaveBtnActive) {
		this.tempSaveBtnActive = tempSaveBtnActive;
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
	 * @return the fileBtnActive
	 */
	public boolean isFileBtnActive() {
		return fileBtnActive;
	}

	/**
	 * @param fileBtnActive the fileBtnActive to set
	 */
	public void setFileBtnActive(boolean fileBtnActive) {
		this.fileBtnActive = fileBtnActive;
	}

	/**
	 * @return the qadAuth
	 */
	public boolean isQadAuth() {
		return qadAuth;
	}

	/**
	 * @param qadAuth the qadAuth to set
	 */
	public void setQadAuth(boolean qadAuth) {
		this.qadAuth = qadAuth;
	}

	/**
	 * @return the labAuth
	 */
	public boolean isLabAuth() {
		return labAuth;
	}

	/**
	 * @param labAuth the labAuth to set
	 */
	public void setLabAuth(boolean labAuth) {
		this.labAuth = labAuth;
	}

	/**
	 * @return the scmAuth
	 */
	public boolean isScmAuth() {
		return scmAuth;
	}

	/**
	 * @param scmAuth the scmAuth to set
	 */
	public void setScmAuth(boolean scmAuth) {
		this.scmAuth = scmAuth;
	}

	/**
	 * @return the marketAuth
	 */
	public boolean isMarketAuth() {
		return marketAuth;
	}

	/**
	 * @param marketAuth the marketAuth to set
	 */
	public void setMarketAuth(boolean marketAuth) {
		this.marketAuth = marketAuth;
	}

	/**
	 * @return the salesAuth
	 */
	public boolean isSalesAuth() {
		return salesAuth;
	}

	/**
	 * @param salesAuth the salesAuth to set
	 */
	public void setSalesAuth(boolean salesAuth) {
		this.salesAuth = salesAuth;
	}

	/**
	 * @return the tcsAuth
	 */
	public boolean isTcsAuth() {
		return tcsAuth;
	}

	/**
	 * @param tcsAuth the tcsAuth to set
	 */
	public void setTcsAuth(boolean tcsAuth) {
		this.tcsAuth = tcsAuth;
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
	 * @return the saveType
	 */
	public String getSaveType() {
		return saveType;
	}

	/**
	 * @param saveType the saveType to set
	 */
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	
	
}
