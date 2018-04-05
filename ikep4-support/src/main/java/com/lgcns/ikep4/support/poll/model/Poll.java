/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.poll.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서혜숙
 * @version $Id: Poll.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Poll extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4384194596792625462L;

	/**
	 * 온라인 투표 ID
	 */
	@NotEmpty
	private String pollId;

	/**
	 * 투표 제목
	 */
	@Size(min=0, max=1024)
	private String question;
	
	/**
	 * 투표 시작일
	 */
	@NotEmpty
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date startDate;	

	/**
	 * 오늘날짜
	 */
	private Date toDate;	

	/**
	 * 투표 종료일
	 */
	@NotEmpty
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date endDate;	
	
	/**
	 * 투표 답변수
	 */
	@NotEmpty	
	private int answerCount;	
	
	/**
	 * 투표진행상태(0:신규,1:진행중,2:완료)
	 */
	@NotEmpty
	private String status;

	/**
	 * 투표 대상자 공개여부(0:공개, 1:개별설정)
	 */
	@NotEmpty
	private String permissionType;

	/**
	 * 투표요청채널(0:없음,1:메일,2:쪽지,3:메일과쪽지)
	 */
	@NotEmpty
	private String requestChannel;		

	/**
	 * 투표결과표시차트타입(0:바 차트,1:파이 차트,2:찬성/반대 차트)
	 */
	@NotEmpty
	private String chartType;	
	
	/**
	 * 포탈 ID (for Multi Portal)
	 */
	@NotEmpty
	private String portalId;
	
	/**
	 * 등록자 발신자ID
	 */
	@NotEmpty
	private String registerId;		

	/**
	 * 등록자 이름
	 */
	@NotEmpty
	private String registerName;
	
	/**
	 * 등록자 영어 이름
	 */	
	private String registerEnglishName;

	/**
	 * 등록자 부서 이름
	 */
	private String teamName;
	
	/**
	 * 등록자 부서 영어이름
	 */
	private String teamEnglishName;

	/**
	 * 등록자 직급명
	 */
	private String jobTitleName;

	/**
	 * 등록자 직급영문명
	 */
	private String jobTitleEnglishName;

	/**
	 * 등록일시
	 */
	@NotEmpty
	private Date registDate;
	
	/**
	 * updater Id
	 */
	@NotEmpty
	private String updaterId;
	
	/**
	 * updater 이름
	 */
	@NotEmpty
	private String updaterName;
	
	/**
	 * update 날짜
	 */
	@NotEmpty
	private Date updateDate;
	
	/**
	 * 질문에 대한 답변 항목 ID
	 */
	@NotEmpty
	private String answerId;
	
	/**
	 * answer title 들 
	 */
	@NotEmpty
	private String[] answerTitles;	
	
	/**
	 * answer title
	 */
	@NotEmpty
	private String answerTitle;		

	/**
	 * target UserId 들 
	 */
	@NotEmpty
	private String[] targetUserIds;	

	/**
	 * targetId
	 */
	@NotEmpty
	private String targetId;
	
	/**
	 * targetType
	 */
	@NotEmpty
	private String targetType;	

	/**
	 * 답변자 ID
	 */
	@NotEmpty
	private String answerUserId;
	
	private String groupId;

	/**
	 * 투표 날짜
	 */
	@NotEmpty
	private Date answerDate;	
	
	/**
	 * 게시판 가져올 끝 수
	 */
	private String endNo;
	
	/**
	 * 게시판 가져올 처음 수
	 */
	private String baseNo;	

	/**
	 * 현재 페이지 번호
	 */
	private int curPage;
	
	/**
	 * 투표 갯수
	 */
	@NotEmpty	
	private int answerTotal;
	
	/**
	 * 총 투표 갯수
	 */
	@NotEmpty	
	private int answerTotalSum;	

	/**
	 * 진행중 완료 여부
	 */
	private String isComplete;		

	/**
	 * 투표 퍼센트
	 */
	@NotEmpty	
	private int answerPercent;

	/**
	 * 투표여부
	 */
	private String isApply;	

	/**
	 * 남은기간
	 */
	private String remainDayCnt;
	
	/**
	 * 참여자수
	 */
	private String resultCnt;	
	
	/**
	 * 참여여부
	 */
	private String isResultExists;		
	
	/**
	 * 투표대상자여부
	 */
	private String isTarget;	
	
	/**
	 * 사용자 권한 배열
	 */
	private String[] addrUserList;
	
	/**
	 * 그룹 권한 배열
	 */
	private String[] addrGroupTypeList;	

	/**
	 * 보기항목
	 */	
	private List<Answer> answerList;

	/**
	 * 대상자 리스트
	 */		
	private List<Target> targetList;
	
	/**
	 * 대상자 리스트-사용자
	 */		
	private List<Target> targetUserList;	

	/**
	 * 대상자 리스트-그룹
	 */		
	private List<Target> targetGroupTypeList;	
	
	/**
	 * 팝업모드
	 */
	private String viewMode;
	
	public List<Target> getTargetUserList() {
		return targetUserList;
	}

	public void setTargetUserList(List<Target> targetUserList) {
		this.targetUserList = targetUserList;
	}

	public List<Target> getTargetGroupTypeList() {
		return targetGroupTypeList;
	}

	public void setTargetGroupTypeList(List<Target> targetGroupTypeList) {
		this.targetGroupTypeList = targetGroupTypeList;
	}
	
	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public List<Target> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<Target> targetList) {
		this.targetList = targetList;
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}

	public String getRemainDayCnt() {
		return remainDayCnt;
	}

	public void setRemainDayCnt(String remainDayCnt) {
		this.remainDayCnt = remainDayCnt;
	}

	/**
	 * @return the registerId
	 */		
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */		
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}	
	
	/**
	 * @return the registerName
	 */		
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */		
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registDate
	 */		
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */		
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}	
	
	public String getEndNo() {
		return endNo;
	}

	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	public String getBaseNo() {
		return baseNo;
	}

	public void setBaseNo(String baseNo) {
		this.baseNo = baseNo;
	}	
		
	
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}	
	
	public String getPollId() {
		return pollId;
	}

	public void setPollId(String pollId) {
		this.pollId = pollId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}	

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public String getRequestChannel() {
		return requestChannel;
	}

	public void setRequestChannel(String requestChannel) {
		this.requestChannel = requestChannel;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String[] getAnswerTitles() {
		return answerTitles;
	}

	public void setAnswerTitles(String[] answerTitles) {
		if ( answerTitles != null ) {
			this.answerTitles = new String[answerTitles.length];
			System.arraycopy(answerTitles, 0, this.answerTitles, 0, answerTitles.length);
		}
	}

	
	public String getAnswerTitle() {
		return answerTitle;
	}

	public void setAnswerTitle(String answerTitle) {
		this.answerTitle = answerTitle;
	}
	
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	
	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}	
	
	public String getAnswerUserId() {
		return answerUserId;
	}

	public void setAnswerUserId(String answerUserId) {
		this.answerUserId = answerUserId;
	}
	
	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}	
		
	public int getAnswerTotal() {
		return answerTotal;
	}

	public void setAnswerTotal(int answerTotal) {
		this.answerTotal = answerTotal;
	}		
	
	public int getAnswerTotalSum() {
		return answerTotalSum;
	}

	public void setAnswerTotalSum(int answerTotalSum) {
		this.answerTotalSum = answerTotalSum;
	}
	
	public int getAnswerPercent() {
		return answerPercent;
	}

	public void setAnswerPercent(int answerPercent) {
		this.answerPercent = answerPercent;
	}	
	
	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}	
		
	public String getIsApply() {
		return isApply;
	}

	public void setIsApply(String isApply) {
		this.isApply = isApply;
	}
		
	public String[] getTargetUserIds() {
		return targetUserIds;
	}

	public void setTargetUserIds(String[] targetUserIds) {
		if ( targetUserIds != null ) {
			this.targetUserIds = new String[targetUserIds.length];
			System.arraycopy(targetUserIds, 0, this.targetUserIds, 0, targetUserIds.length);
		}		
	}		
	
	public String getResultCnt() {
		return resultCnt;
	}

	public void setResultCnt(String resultCnt) {
		this.resultCnt = resultCnt;
	}		

	public String getIsResultExists() {
		return isResultExists;
	}

	public void setIsResultExists(String isResultExists) {
		this.isResultExists = isResultExists;
	}	
	
	public String getIsTarget() {
		return isTarget;
	}

	public void setIsTarget(String isTarget) {
		this.isTarget = isTarget;
	}	
	
	public String[] getAddrUserList() {
		return addrUserList;
	}

	public void setAddrUserList(String[] addrUserList) {
		if ( addrUserList != null ) {
			this.addrUserList = new String[addrUserList.length];
			System.arraycopy(addrUserList, 0, this.addrUserList, 0, addrUserList.length);
		}		
	}

	public String[] getAddrGroupTypeList() {
		return addrGroupTypeList;
	}

	public void setAddrGroupTypeList(String[] addrGroupTypeList) {
		if ( addrGroupTypeList != null ) {
			this.addrGroupTypeList = new String[addrGroupTypeList.length];
			System.arraycopy(addrGroupTypeList, 0, this.addrGroupTypeList, 0, addrGroupTypeList.length);
		}
	}	
	
	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}		

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}	
}
