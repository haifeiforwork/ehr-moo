/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ApprovalLinePk.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class ApprovalLinePk extends BaseObject {
	private static final long serialVersionUID = 3854591138435753310L;
	
	/**
	 * 결재ID
	 */
	private String  approvalId;      

	/**
	 * 결재순서
	 */
	private Integer approvalLine; 

	/**
	 * @return the approvalId
	 */
	public String getApprovalId() {
		return approvalId;
	}
	/**
	 * @param approvalId the approvalId to set
	 */
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}
	/**
	 * @return the approvalLine
	 */
	public Integer getApprovalLine() {
		return approvalLine;
	}
	/**
	 * @param approvalLine the approvalLine to set
	 */
	public void setApprovalLine(Integer approvalLine) {
		this.approvalLine = approvalLine;
	}
}
