package com.lgcns.ikep4.servicepack.survey.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.servicepack.survey.util.MagicNumUtils;
import com.lgcns.ikep4.servicepack.survey.util.NumberUtils;
import com.lgcns.ikep4.servicepack.survey.validation.groups.Create;
import com.lgcns.ikep4.servicepack.survey.validation.groups.Reject;
import com.lgcns.ikep4.servicepack.survey.validation.groups.Update;

/**
 * 
 * 설문 테이블
 *
 * @author ihko11
 * @version $Id: Survey.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class Survey extends SurveyBaseObject {
	private static final long serialVersionUID = -5059648601385202958L;

	/**
     * 설문 ID
     */
	@NotEmpty(groups={Update.class,Reject.class})
    private String surveyId;

    /**
     * 설문 제목
     */
    @NotEmpty(groups={Create.class, Update.class})
    @Size(min=1,max=MagicNumUtils.VALID_VARCHAR_MAX_SIZE,groups={Create.class, Update.class})
    private String title;

    /**
     *설문 시작 일자
     */
    @NotNull(groups= {Create.class, Update.class})
    @DateTimeFormat(pattern="yyyy.MM.dd")
    private Date startDate;

    /**
     *설문 종료 일자
     */
    @NotNull(groups={Create.class, Update.class})
    @DateTimeFormat(pattern="yyyy.MM.dd")
    private Date endDate;

    /**
     * 익명 지원 여부( 0 : 실명, 1 : 익명)
     */
    @Digits(fraction = 0, integer = 1, groups={Create.class, Update.class}) 
    private Integer anonymous;

    /**
     * 설문 거부 버튼 표시 여부( 0 : 비표시, 1 :  표시)
     */
    @Digits(fraction = 0, integer = 1, groups={Create.class, Update.class}) 
    private Integer rejectButton;

    /**
     *설문 공개 여부( 0 : 공개, 1: 비공개)
     */
    @Digits(fraction = 0, integer = 1, groups={Create.class, Update.class}) 
    private Integer resultOpen;

    /**
     * 설문 결재자 ID
     */
    private String approverId;
    
    private String approverName;
    
    private Date responseDate;
    
    private String sendComment;
    
    

    public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	/**
     *설문상태( 0 : 작성중, 1 : 승인대기, 2 : 반려3 : 진행중, 4 : 종료)
     */
    private Integer surveyStatus;

    /**
     *설문결재 반려사유
     */
    
    @NotEmpty(groups={Reject.class})
    private String oppositionReason;

    /**
     * 포탈 ID
     */
    private String portalId;

    
    /**
     * 설문내용
     */
    private String contents;
    
    /**
     * 설문 내용(머릿말) 타입
	( 0 : 텍스트, 1 : 이미지)
     */
    @Digits(fraction = 0, integer = 1) 
    private Integer contentsType;
    
    /**
     * ( 0 : 전사, 1 : 개별설정)
     */
    @Digits(fraction = 0, integer = 1) 
    private Integer surveyTarget;
    
    
    /**
     *  대상자 목록
     */
    private List<Target> targetList = new ArrayList<Target>();
    private List<Target> targetGroupList = new ArrayList<Target>();
    
    private List<User> targetUsers = new ArrayList<User>();
    private List<Group> targetGroups = new ArrayList<Group>();
    
    private Integer rowIndex;
    
    private FileData fileData;
    
    private Integer responseCnt;
    private Integer sendLogCnt;
    private Integer isValid;
    private Integer sendOption;
    private Integer mailSendYn;
    
    
    
    public Survey(){}

	public Survey(Integer anonymous, Integer rejectButton, Integer resultOpen, Integer surveyStatus,
			Integer contentsType, Integer surveyTarget) {
		super();
		this.anonymous = anonymous;
		this.rejectButton = rejectButton;
		this.resultOpen = resultOpen;
		this.surveyStatus = surveyStatus;
		this.contentsType = contentsType;
		this.surveyTarget = surveyTarget;
	}

	
	public Integer getResponseCnt() {
		return responseCnt;
	}

	public void setResponseCnt(Integer responseCnt) {
		this.responseCnt = responseCnt;
	}

	public Integer getSendLogCnt() {
		return sendLogCnt;
	}

	public void setSendLogCnt(Integer sendLogCnt) {
		this.sendLogCnt = sendLogCnt;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public Integer getRejectButton() {
		return rejectButton;
	}

	public void setRejectButton(Integer rejectButton) {
		this.rejectButton = rejectButton;
	}

	public Integer getResultOpen() {
		return resultOpen;
	}

	public void setResultOpen(Integer resultOpen) {
		this.resultOpen = resultOpen;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public Integer getSurveyStatus() {
		return surveyStatus;
	}

	public void setSurveyStatus(Integer surveyStatus) {
		this.surveyStatus = surveyStatus;
	}

	public String getOppositionReason() {
		return oppositionReason;
	}

	public void setOppositionReason(String oppositionReason) {
		this.oppositionReason = oppositionReason;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Integer getContentsType() {
		return contentsType;
	}

	public void setContentsType(Integer contentsType) {
		this.contentsType = contentsType;
	}

	public Integer getSurveyTarget() {
		return surveyTarget;
	}

	public void setSurveyTarget(Integer surveyTarget) {
		this.surveyTarget = surveyTarget;
	}

	public List<Target> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<Target> targetList) {
		this.targetList = targetList;
	}
	
	public void addTarget(Target target){
		this.targetList.add(target);
	}
	
	public List<Target> getTargetGroupList() {
		return targetGroupList;
	}

	public void setTargetGroupList(List<Target> targetGroupList) {
		this.targetGroupList = targetGroupList;
	}
	
	public void addGroupTarget(Target target){
		this.targetGroupList.add(target);
	}
	
	public List<User> getTargetUsers() {
		return targetUsers;
	}

	public void setTargetUsers(List<User> targetUsers) {
		this.targetUsers = targetUsers;
	}

	public List<Group> getTargetGroups() {
		return targetGroups;
	}

	public void setTargetGroups(List<Group> targetGroups) {
		this.targetGroups = targetGroups;
	}
	
	

	public FileData getFileData() {
		return fileData;
	}

	public void setFileData(FileData fileData) {
		this.fileData = fileData;
	}

	public String getSurveyStatusName(){
		if(  SurveyStatus.WRITING.getCode().equals(this.surveyStatus) ) {
			return SurveyStatus.WRITING.getStatus();
		}	
		else if(  SurveyStatus.ANSERING.getCode().equals(this.surveyStatus) ){ 
			return SurveyStatus.ANSERING.getStatus();
		}	
		else if(  SurveyStatus.END.getCode().equals(this.surveyStatus) ) {
			return SurveyStatus.END.getStatus();
		}	
		else if(  SurveyStatus.REJECT.getCode().equals(this.surveyStatus) ){ 
			return SurveyStatus.REJECT.getStatus();
		}	
		else if(  SurveyStatus.WAIT.getCode().equals(this.surveyStatus) ){ 
			return SurveyStatus.WAIT.getStatus();
		}	
		else if(  SurveyStatus.APPROVE.getCode().equals(this.surveyStatus) ){ 
			return SurveyStatus.APPROVE.getStatus();
		}	
		else{
			return this.surveyStatus+"";
		}
	}

	@JsonIgnore(value=false)
	public double getResponseAvg() {
		try
		{
			return NumberUtils.round2(responseCnt, sendLogCnt);
		}
		catch(Exception e){
			return 0;
		}
	}

	public Integer getSendOption() {
		return sendOption;
	}

	public void setSendOption(Integer sendOption) {
		this.sendOption = sendOption;
	}

	public Integer getMailSendYn() {
		return mailSendYn;
	}

	public void setMailSendYn(Integer mailSendYn) {
		this.mailSendYn = mailSendYn;
	}

	public String getSendComment() {
		return sendComment;
	}

	public void setSendComment(String sendComment) {
		this.sendComment = sendComment;
	}
}