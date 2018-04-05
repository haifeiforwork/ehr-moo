/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officesupplies.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public class OfficeSupplies extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6166180181636524352L;

	private String startDate;

	private String endDate;
	
	private String registerId;

	private String registerName;

	private String registDate;
	
	private String updaterId;
	
	private String updaterName;
	
	private String updateDate;

	private String requestReason;
	
	private String officesuppliesId;
	
	private String approveDate;
	
	private String approveUserId;
	
	private String approveStatus;
	
	private String rejectReason;
	
	private String managerId;
	
	private String approveUserName;
	
	private String isDelete;
	
	private String fileName;
	
	private String ecmFileYn;
	
	private String reqType;
	
	private String foreverYn;
	
	private String productName;
	private String productNo;
	private String price2;
	private String unit;
	private String status1;
	private String price1;
	private String status2;
	private String manageApprDate;
	private String teamApprDate;
	private String amount1;
	private String teamId;
	private String amount2;
	private String manageReviewDate;
	private String manageReviewerId;
	private String manageReviewerName;
	private String manageApprId;
	private String manageApprName;
	private String teamApprId;
	private String teamApprName;
	private String teamReviewerId;
	private String teamReviewerName;
	private String teamReviewDate;
	private String teamName;
	private String category;
	private String category1;
	private String category2;
	private String remark;
	private String categoryName1;
	private String categoryName2;

	private String boardId;
	private String categoryId;
	private String categoryName;
	private String categorySeqId;
	private String categoryType;
	private String color;
	private String description;
	private String portalId;
	private String year;
	private String month;
	private String status;
	
	private String calMnt;
	private String fullPath;
	
	private String teamLeaderId;
	private String teamManagerId;
	private String teamLeaderName;
	private String teamManagerName;
	private String useYn;
	/**
	 * CategoryId_LIST
	 */
	private String categoryIdList;
	
	/**
	 * CategoryId_LIST
	 */
	private String delIdList;
	
	/**
	 * CategoryId_LIST
	 */
	private String addNameList;
	
	/**
	 * CategoryId_LIST
	 */
	private String updateNameList;
	
	/**
	 * CategoryId_LIST
	 */
	private String updateIdList;
	
	private String alignList;
	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getOfficeSuppliesId() {
		return officesuppliesId;
	}

	public void setOfficeSuppliesId(String officesuppliesId) {
		this.officesuppliesId = officesuppliesId;
	}

	public String getRequestReason() {
		return requestReason;
	}

	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getApproveUserId() {
		return approveUserId;
	}

	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getApproveUserName() {
		return approveUserName;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEcmFileYn() {
		return ecmFileYn;
	}

	public void setEcmFileYn(String ecmFileYn) {
		this.ecmFileYn = ecmFileYn;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getForeverYn() {
		return foreverYn;
	}

	public void setForeverYn(String foreverYn) {
		this.foreverYn = foreverYn;
	}

	public String getOfficesuppliesId() {
		return officesuppliesId;
	}

	public void setOfficesuppliesId(String officesuppliesId) {
		this.officesuppliesId = officesuppliesId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getAmount1() {
		return amount1;
	}

	public void setAmount1(String amount1) {
		this.amount1 = amount1;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPrice1() {
		return price1;
	}

	public void setPrice1(String price1) {
		this.price1 = price1;
	}

	public String getPrice2() {
		return price2;
	}

	public void setPrice2(String price2) {
		this.price2 = price2;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getManageApprDate() {
		return manageApprDate;
	}

	public void setManageApprDate(String manageApprDate) {
		this.manageApprDate = manageApprDate;
	}

	public String getTeamApprDate() {
		return teamApprDate;
	}

	public void setTeamApprDate(String teamApprDate) {
		this.teamApprDate = teamApprDate;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getAmount2() {
		return amount2;
	}

	public void setAmount2(String amount2) {
		this.amount2 = amount2;
	}

	public String getManageReviewDate() {
		return manageReviewDate;
	}

	public void setManageReviewDate(String manageReviewDate) {
		this.manageReviewDate = manageReviewDate;
	}

	public String getManageReviewerId() {
		return manageReviewerId;
	}

	public void setManageReviewerId(String manageReviewerId) {
		this.manageReviewerId = manageReviewerId;
	}

	public String getManageReviewerName() {
		return manageReviewerName;
	}

	public void setManageReviewerName(String manageReviewerName) {
		this.manageReviewerName = manageReviewerName;
	}

	public String getManageApprId() {
		return manageApprId;
	}

	public void setManageApprId(String manageApprId) {
		this.manageApprId = manageApprId;
	}

	public String getManageApprName() {
		return manageApprName;
	}

	public void setManageApprName(String manageApprName) {
		this.manageApprName = manageApprName;
	}

	public String getTeamApprId() {
		return teamApprId;
	}

	public void setTeamApprId(String teamApprId) {
		this.teamApprId = teamApprId;
	}

	public String getTeamApprName() {
		return teamApprName;
	}

	public void setTeamApprName(String teamApprName) {
		this.teamApprName = teamApprName;
	}

	public String getTeamReviewerId() {
		return teamReviewerId;
	}

	public void setTeamReviewerId(String teamReviewerId) {
		this.teamReviewerId = teamReviewerId;
	}

	public String getTeamReviewerName() {
		return teamReviewerName;
	}

	public void setTeamReviewerName(String teamReviewerName) {
		this.teamReviewerName = teamReviewerName;
	}

	public String getTeamReviewDate() {
		return teamReviewDate;
	}

	public void setTeamReviewDate(String teamReviewDate) {
		this.teamReviewDate = teamReviewDate;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategorySeqId() {
		return categorySeqId;
	}

	public void setCategorySeqId(String categorySeqId) {
		this.categorySeqId = categorySeqId;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(String categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public String getDelIdList() {
		return delIdList;
	}

	public void setDelIdList(String delIdList) {
		this.delIdList = delIdList;
	}

	public String getAddNameList() {
		return addNameList;
	}

	public void setAddNameList(String addNameList) {
		this.addNameList = addNameList;
	}

	public String getUpdateNameList() {
		return updateNameList;
	}

	public void setUpdateNameList(String updateNameList) {
		this.updateNameList = updateNameList;
	}

	public String getUpdateIdList() {
		return updateIdList;
	}

	public void setUpdateIdList(String updateIdList) {
		this.updateIdList = updateIdList;
	}

	public String getAlignList() {
		return alignList;
	}

	public void setAlignList(String alignList) {
		this.alignList = alignList;
	}

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCategoryName1() {
		return categoryName1;
	}

	public void setCategoryName1(String categoryName1) {
		this.categoryName1 = categoryName1;
	}

	public String getCategoryName2() {
		return categoryName2;
	}

	public void setCategoryName2(String categoryName2) {
		this.categoryName2 = categoryName2;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCalMnt() {
		return calMnt;
	}

	public void setCalMnt(String calMnt) {
		this.calMnt = calMnt;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(String teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public String getTeamManagerId() {
		return teamManagerId;
	}

	public void setTeamManagerId(String teamManagerId) {
		this.teamManagerId = teamManagerId;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getTeamLeaderName() {
		return teamLeaderName;
	}

	public void setTeamLeaderName(String teamLeaderName) {
		this.teamLeaderName = teamLeaderName;
	}

	public String getTeamManagerName() {
		return teamManagerName;
	}

	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}

}
