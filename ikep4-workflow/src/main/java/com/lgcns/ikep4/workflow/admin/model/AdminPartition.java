/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_PARTITION
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminPartition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminPartition extends BaseObject{
	
	private static final long serialVersionUID = 231767328317453351L;
	
    private String partitionId;  //파티션 ID    
    private String partitionName;//파티션 이름  
    private String description;   //파티션 설명  
    private String createDate;   //생성일자 
    private String updateDate;   //수정일자   
	public String getPartitionId() {
		return partitionId;
	}
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}
	public String getPartitionName() {
		return partitionName;
	}
	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
