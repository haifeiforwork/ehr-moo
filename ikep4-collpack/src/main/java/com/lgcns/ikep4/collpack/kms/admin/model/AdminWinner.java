package com.lgcns.ikep4.collpack.kms.admin.model;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.lgcns.ikep4.support.base.constant.CommonConstant;

public class AdminWinner {

	private String itemSeq;	
	private String rowNum;
	
	@NotNull
	private String winnerId;
	private String isMonth;
	private String isMonthValue;
	private String winYear;
	private String winGb;	
	private String regCnt;
	private String imageFileId;
	
	private String reason;
	
	private String winnerName;
	
	@NotNull
	@NumberFormat(style=Style.NUMBER)
	private String sort;
	
	
	@NotNull
	@NumberFormat(style=Style.NUMBER)
	private String mark;
	
	@NotNull
	@NumberFormat(style=Style.NUMBER)
	private String conversionMark;
	private String registDate;
	private String userName;
	private String jobTitleName;
	private String teamName;
	private String workPlaceName;
	private String itemSeqs;
	
	public String[] getItemSeqs() {
		if(itemSeqs != null)
			return itemSeqs.split(CommonConstant.COMMA_SEPARATER);
		else 
			return null;
	}
	public void setItemSeqs(String itemSeqs) {
		this.itemSeqs = itemSeqs;
	}
	
	public String getItemSeq() {
		return itemSeq;
	}
	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	
	public String[] getWinYears(){
		
		if(winYear !=null && winYear.contains(CommonConstant.COMMA_SEPARATER)){
			return winYear.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}
	
	public String getWinYear() {
		return winYear;
	}
	public void setWinYear(String winYear) {
		this.winYear = winYear;
	}
	
	public String[] getWinGbs(){
		
		if(winGb !=null && winGb.contains(CommonConstant.COMMA_SEPARATER)){
			return winGb.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}
	
	public String[] getReasons(){
		
		if(reason !=null && reason.contains(CommonConstant.COMMA_SEPARATER)){
			return reason.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}
	public String getWinGb() {
		return winGb;
	}
	public void setWinGb(String winGb) {
		this.winGb = winGb;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJobTitleName() {
		return jobTitleName;
	}
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getWorkPlaceName() {
		return workPlaceName;
	}
	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}	
	
	
	public String[] getWinnerIds(){
		
		if(winnerId !=null && winnerId.contains(CommonConstant.COMMA_SEPARATER)){
			return winnerId.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}
	public String getWinnerId() {
		return winnerId;
	}
	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}
	
	public String[] getIsMonths(){
		
		if(isMonth !=null && isMonth.contains(CommonConstant.COMMA_SEPARATER)){
			return isMonth.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}
	public String getIsMonth() {
		return isMonth;
	}
	public void setIsMonth(String isMonth) {
		this.isMonth = isMonth;
	}
	
	public String[] getSorts(){
		
		if(sort !=null && sort.contains(CommonConstant.COMMA_SEPARATER)){
			return sort.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String[] getMarks(){
		
		if(mark !=null && mark.contains(CommonConstant.COMMA_SEPARATER)){
			return mark.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
	public String[] getConversionMarks(){
		
		if(conversionMark !=null && conversionMark.contains(CommonConstant.COMMA_SEPARATER)){
			return conversionMark.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}
	public String getConversionMark() {
		return conversionMark;
	}
	public void setConversionMark(String conversionMark) {
		this.conversionMark = conversionMark;
	}
	
	
	public String getIsMonthValue() {
		return isMonthValue;
	}
	public void setIsMonthValue(String isMonthValue) {
		this.isMonthValue = isMonthValue;
	}
	
	public String[] getRegCnts(){
		
		if(regCnt !=null && regCnt.contains(CommonConstant.COMMA_SEPARATER)){
			return regCnt.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}
	
	public String getRegCnt() {
		return regCnt;
	}
	public void setRegCnt(String regCnt) {
		this.regCnt = regCnt;
	}
	public String getImageFileId() {
		return imageFileId;
	}
	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}
	public String getWinnerName() {
		return winnerName;
	}
	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
