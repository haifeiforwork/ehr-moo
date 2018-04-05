package com.lgcns.ikep4.approval.work.model;

import com.lgcns.ikep4.approval.admin.model.ApprDoc;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormItem.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ApprDocAuth extends BaseObject {

	static final long serialVersionUID = -6331628894387215219L;
	
	public ApprDocAuth(ApprDoc apprDoc){
		this.listType 		= apprDoc.getListType();
		this.userId 		= apprDoc.getUserId();
		this.apprDocType	= apprDoc.getApprDocType();
		this.apprDocStatus 	= apprDoc.getApprDocStatus();
		this.registerId 	= apprDoc.getRegisterId();
		this.parentApprId	= apprDoc.getParentApprId();

		// 기안자 여부
		this.register = this.userId.equals(this.registerId); 
	}
	
	/**
	 * 읽기권한
	 */
	private boolean hasReadAuth = false;
	
	/**
	 * 문서 목록
	 */
	private String listType;
	
	/**
	 * 사용자
	 */
	private String userId;
		
	/**
	 * 결재문서 유형 (0:내부결재,1:협조공문-수신처가 있는 결재문서)
	 */
	private int apprDocType;
	
	/**
	 * 문서상태 (0:임시저장, 1:진행중, 2:완료, 3:반려, 4:회수, 5:오류,6:접수대기 ..)
	 */
	private int apprDocStatus;
	
	/**
	 * 문서 원본인지 복사본(부서합의,수신처)인지 확인
	 */
	private String parentApprId;
	
	/**
	 * 기안자 아이디
	 */
	private String registerId;
	
	/**
	 * 리턴 URL
	 */
	private String returnUrl;
	
	
	/**
	 * 사용자 정보
	 */
	
	// 기안자 여부
	private boolean register;
	
	// 결재자 여부
	private boolean approver;
	
	// 합의자 여부
	private boolean agreer;
	
	// 수신자 여부
	private boolean receiver;
	
	// 참조자 여부
	private boolean referencer;
	
	// 열람권자 여부
	private boolean reader;
	
	// 관리자 여부
	private boolean auther;
	
	// 전체문서보기 사용자여부
	private boolean viwer;
	
	// 검토자 여부
	private boolean examUser;
	
	// 공람대상자 여부
	private boolean displayUser;
	
	/**
	 * 결재 라인 정보
	 */
	
	// 결재타입(결재자ㅣ: 0,합의자 : 1)
	//private int apprType;
	
	/**
	 * 현재 문서가 부서합의/수신처 문서인지(1:부서합의,3:수신처)
	 */
	private int parentApprType;
	/**
	 * 현재 사용자가 결재했는지 확인( 0:대기,1:진행,2:승인,3:반려,4:합의..)
	 */
	private int apprStatus;
	
	/**
	 * 결재선 변경 권한 여부 (권한없음:0, 권한있음:1)
	 */
	private int lineModifyAuth;
	
	/**
	 * 결재문서 변경 권한 여부 (권한없음:0, 권한있음:1)
	 */
	private int docModifyAuth;
	
	/**
	 * 참조자,열람권자 변경 권한 여부 (권한없음:0, 권한있음:1)
	 */
	private int readModifyAuth;
	
	/**
	 * 결재 회수 가능여부
	 */
	private boolean canInitApprDocStatus;

	/**
	 * 결재 취소 가능여부
	 */
	private boolean  canCancelApprDocStatus;
	
	
	/**
	 * 기타 정보
	 */
	
	// 공람지정 버튼
	private boolean useDisplayButton;
	
	// 검토요청권한 여부
	private boolean examIsRequest;
	
	// 검토한 문서여부
	private boolean examStatus;
	
	// 공람문서 여부
	private boolean useDisplayDoc;

	// 위임자 정보
	private	String entrustUserId;
	
	/**
	 * 수임자여부확인
	 */
	private	boolean hasSignUser;
	
	/**
	 * 참조의견등록여부 확인
	 */
	private boolean hasReference;
	
	public boolean isHasReadAuth() {
		return hasReadAuth;
	}


	public void setHasReadAuth(boolean hasReadAuth) {
		this.hasReadAuth = hasReadAuth;
	}


	public String getListType() {
		return listType;
	}


	public void setListType(String listType) {
		this.listType = listType;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public int getApprDocType() {
		return apprDocType;
	}


	public void setApprDocType(int apprDocType) {
		this.apprDocType = apprDocType;
	}


	public int getApprDocStatus() {
		return apprDocStatus;
	}


	public void setApprDocStatus(int apprDocStatus) {
		this.apprDocStatus = apprDocStatus;
	}


	public String getRegisterId() {
		return registerId;
	}


	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}


	public String getReturnUrl() {
		return returnUrl;
	}


	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}


	public boolean isRegister() {
		return register;
	}


	public void setRegister(boolean register) {
		this.register = register;
	}


	public boolean isReferencer() {
		return referencer;
	}


	public void setReferencer(boolean referencer) {
		this.referencer = referencer;
	}


	public boolean isReader() {
		return reader;
	}


	public void setReader(boolean reader) {
		this.reader = reader;
	}


	public boolean isAuther() {
		return auther;
	}


	public void setAuther(boolean auther) {
		this.auther = auther;
	}


	public int getLineModifyAuth() {
		return lineModifyAuth;
	}


	public void setLineModifyAuth(int lineModifyAuth) {
		this.lineModifyAuth = lineModifyAuth;
	}


	public int getDocModifyAuth() {
		return docModifyAuth;
	}


	public void setDocModifyAuth(int docModifyAuth) {
		this.docModifyAuth = docModifyAuth;
	}


	public int getReadModifyAuth() {
		return readModifyAuth;
	}


	public void setReadModifyAuth(int readModifyAuth) {
		this.readModifyAuth = readModifyAuth;
	}


	public boolean isCanInitApprDocStatus() {
		return canInitApprDocStatus;
	}


	public void setCanInitApprDocStatus(boolean canInitApprDocStatus) {
		this.canInitApprDocStatus = canInitApprDocStatus;
	}


	public boolean isCanCancelApprDocStatus() {
		return canCancelApprDocStatus;
	}


	public void setCanCancelApprDocStatus(boolean canCancelApprDocStatus) {
		this.canCancelApprDocStatus = canCancelApprDocStatus;
	}


	public boolean isUseDisplayButton() {
		return useDisplayButton;
	}


	public void setUseDisplayButton(boolean useDisplayButton) {
		this.useDisplayButton = useDisplayButton;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getParentApprId() {
		return parentApprId;
	}


	public void setParentApprId(String parentApprId) {
		this.parentApprId = parentApprId;
	}


	public boolean isExamUser() {
		return examUser;
	}


	public void setExamUser(boolean examUser) {
		this.examUser = examUser;
	}


	public boolean isDisplayUser() {
		return displayUser;
	}


	public void setDisplayUser(boolean displayUser) {
		this.displayUser = displayUser;
	}


	public boolean isExamIsRequest() {
		return examIsRequest;
	}


	public void setExamIsRequest(boolean examIsRequest) {
		this.examIsRequest = examIsRequest;
	}


	public boolean isUseDisplayDoc() {
		return useDisplayDoc;
	}


	public void setUseDisplayDoc(boolean useDisplayDoc) {
		this.useDisplayDoc = useDisplayDoc;
	}


	public boolean isExamStatus() {
		return examStatus;
	}


	public void setExamStatus(boolean examStatus) {
		this.examStatus = examStatus;
	}


	public int getApprStatus() {
		return apprStatus;
	}


	public void setApprStatus(int apprStatus) {
		this.apprStatus = apprStatus;
	}


	public int getParentApprType() {
		return parentApprType;
	}


	public void setParentApprType(int parentApprType) {
		this.parentApprType = parentApprType;
	}


	public String getEntrustUserId() {
		return entrustUserId;
	}


	public void setEntrustUserId(String entrustUserId) {
		this.entrustUserId = entrustUserId;
	}


	public boolean isApprover() {
		return approver;
	}


	public void setApprover(boolean approver) {
		this.approver = approver;
	}


	public boolean isAgreer() {
		return agreer;
	}


	public void setAgreer(boolean agreer) {
		this.agreer = agreer;
	}


	public boolean isReceiver() {
		return receiver;
	}

	public void setReceiver(boolean receiver) {
		this.receiver = receiver;
	}

	public boolean isViwer() {
		return viwer;
	}

	public void setViwer(boolean viwer) {
		this.viwer = viwer;
	}


	public boolean isHasSignUser() {
		return hasSignUser;
	}


	public void setHasSignUser(boolean hasSignUser) {
		this.hasSignUser = hasSignUser;
	}


	/**
	 * @return the hasReference
	 */
	public boolean isHasReference() {
		return hasReference;
	}


	/**
	 * @param hasReference the hasReference to set
	 */
	public void setHasReference(boolean hasReference) {
		this.hasReference = hasReference;
	}
	
}
