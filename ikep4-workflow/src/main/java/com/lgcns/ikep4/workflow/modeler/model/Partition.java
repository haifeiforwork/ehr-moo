/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.model;

import java.sql.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * IKEP4_WF_PARTITION 테이블 VO
 *
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: Partition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class Partition extends BaseObject {
	 
	/**
	 * 파티션 아이디
	 */
	private String partitionId;
	
	/**
	 * 파티션 이름
	 */
	private String partitionName;
	
	/**
	 * 파티션 설명
	 */
	private String description;
	
	/**
	 * 파티션 등록 날짜
	 */
	private Date createDate;
	
	/**
	 * 파티션 수정 날짜
	 */
	private Date updateDate;

	/**
	 * @return the partitionId
	 */
	public String getPartitionId() {
		return partitionId;
	}

	/**
	 * @param partitionId the partitionId to set
	 */
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	/**
	 * @return the partitionName
	 */
	public String getPartitionName() {
		return partitionName;
	}

	/**
	 * @param partitionName the partitionName to set
	 */
	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
