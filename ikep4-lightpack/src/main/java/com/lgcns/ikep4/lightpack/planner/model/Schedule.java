/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 일정 model
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: Schedule.java 18496 2012-05-03 07:45:56Z yruyo $
 */
public class Schedule extends BaseObject {

	private static final long serialVersionUID = 2719096577180009107L;
	
	public static final String ITEM_TYPE_CODE = "SD";
	
	public static final String DATE_TIME_FORMAT = "yyyyMMddHHmm";
	
	public static final String DATE_FORMAT = "yyyyMMdd";

	/**
	 * portal ID
	 */
	private String portalId;
	
	/**
	 * 일정 아이디
	 */
	private String scheduleId;

	/**
	 * 일정 종류 ( 0 : 일정, 1 : 이벤트, 2 : To-Do)
	 */
	private String scheduleType = "0";

	/**
	 * 일정 시작일
	 */
	private Date startDate;

	/**
	 * 일정 종료일
	 */
	private Date endDate;

	/**
	 * 종일 일정 여부 ( 0: 시간일정, 1: 종일일정)
	 */
	private int wholeday = 0;

	/**
	 * 일정 제목
	 */
	private String title;

	/**
	 * 일정 장소
	 */
	private String place;

	/**
	 * 일정 내용
	 */
	private String contents;

	/**
	 * 참석 확인 요청 여부 ( 0 : 미요청, 1 : 요청)
	 */
	private int attendanceRequest = 0;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * 일정 범주(카테고리) ID
	 */
	private String categoryId;
	private String categoryName;
	
	
	private String managerId;
	
	

	/**
	 * 일정 공개 여부 ( 0 : 공개일정, 1 : 비공개일정)
	 */
	private int schedulePublic = 0;
	
	/**
	 * 일정 공개 여부 ( 0 : 공개일정, 1 : 숨김일정)
	 */
	private int schedulePrivate = 0;
	
	
	//전사일정 관련
	
	/*
	 * 전사일정 사업장명
	 */
	private String workAreaName ;	
	public int getSchedulePrivate() {
		return schedulePrivate;
	}

	public void setSchedulePrivate(int schedulePrivate) {
		this.schedulePrivate = schedulePrivate;
	}

	/*
	 * 전사일정 여부 (0:일반일정 1:전사일정)
	 */
	private int everyoneSchedule   = 0; 
	/*
	 * 전사일정 확정여부 (0:확정전 1:확정)
	 */
	private int approveResult     = 0;
	/*
	 * 일정 수정 표시 (0:표시없음 1:수정표시, 2삭제표시)
	 */
	private int updateDisplay	  = 0;
	
	
	
	
	/**
	 * 알람 요청 여부 ( 0 : 미요청, 1 : 요청)
	 */
	private int alarmRequest = 0; 
	
	/**
	 * 첨부파일 수
	 */
	private int attachFileCount = 0;
	
	/**
	 * 일정 반복 여부 ( 0 : 단일일정, 1: 반복일정)
	 */
	private int repeat = 0;
	
	/**
	 * 반복 일정 그룹 ID (반복되는 첫번째 일정 ID)
	 */
	private String repeatGroupId;
	
	/**
	 * 공유할 그룹(Collaboaration) 아이디
	 */
	private String workspaceId;
	private String workspaceName;
	
	private boolean sendmail;
	
	/**
	 * 회의실 내역
	 */
	private List<MeetingRoom> meetingRoomList;
	
	
	/**
	 * 반복일정
	 */
	private List<Recurrences> recurrences;
	
	/**
	 * 모임 참석 요청이 체크된 일정의 참석 대상자중 참여를 수락한 사람에게 알림
	 */
	private List<Alarm> alarmList;
	
	/**
	 * 일정 참여자 및 참조자
	 */
	private List<Participant> participantList;
	
	/**
	 * 위임자 ID
	 * 
	 */
	private String mandatorId = null;
	
	/**
	 * 신탁자 ID
	 * 
	 */
	private String trusteeId;
	
	/**
	 * 반복일정 update시 사용, repeatId 적용후 대체 要
	 */
	private Date oldStart;
	private Date oldEnd;

	/**
	 * 파일 업로드용
	 *
	 */
	private List<FileLink> fileLinkList;
	private List<FileData> fileDataList;
	
	private String userInfo;
	private String userEnglishInfo;
	
	private String meetingRoomId;
	
	private String cartooletcId;
	private String cartooletcName;
	

	private String sdate;
	private String stime;
	private String edate;
	private String etime;
	private String alarmType;
	private String alarmTime;
	private String listType;
	private String approveStatus;
	
	private Date begda; 
	
	private String pernr;

	private Date endda;
	
	private String atext;
	
	private String awart;
	
	private String whole;
	
	private String requestReason;
	
	private String color;
	
	private int refYn = 0;
	
	private int pacYn = 0;
	
	private List<String> targetGroupList;
	
	private List<String> targetList;
	
	private List<FavoriteTarget> targetGroup;
	
	private List<FavoriteTarget> targetUser;
	
	private String targetGroupId;
	
	private String targetGroupName;
	
	private String targetUserId;
	
	private String favoriteId;
	
	private String itemTypeCode;
	
	private String type;
	
	private String searchCode;
	
	public int getRefYn() {
		return refYn;
	}

	public void setRefYn(int refYn) {
		this.refYn = refYn;
	}

	public int getPacYn() {
		return pacYn;
	}

	public void setPacYn(int pacYn) {
		this.pacYn = pacYn;
	}

	public Schedule() {
		
	}
	
	public Schedule(String title, String content, int repeat, Date startDate, Date endDate) {
		this.title = title;
		this.contents = content;
		this.repeat = repeat;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public Schedule(String scheduleId, String portalId, String categoryId, String registerId, String registerName, String title, String content, int repeat, Date startDate, Date endDate) {
		this(title, content, repeat, startDate, endDate);
		
		this.scheduleId = scheduleId;
		this.portalId = portalId;
		this.categoryId = categoryId;
		this.registerId = registerId;
		this.registerName = registerName;
		this.registDate = new Date();
		this.updaterId = registerId;
		this.updaterName = registerName;
		this.updateDate = this.registDate;
		
	}
	///////////////////////////////////////////////////////////
	
	public static Date toDate(final String str) {
		if(str.length() < 4) {
			throw new IllegalArgumentException();
		}

		String dtStr = StringUtils.rightPad(str, 14, "0");
		
		Calendar c = Calendar.getInstance();
		c.clear();
		
		int year = Integer.valueOf(dtStr.substring(0, 4)); //new Integer(dtStr.substring(0, 4));
		int month = Integer.valueOf(dtStr.substring(4, 6)) - 1; //new Integer(dtStr.substring(4, 6)) - 1;
		int date = Integer.valueOf(dtStr.substring(6, 8)); //new Integer(dtStr.substring(6, 8));
		int hourOfDay = Integer.valueOf(dtStr.substring(8, 10)); //new Integer(dtStr.substring(8, 10));
		int minute = Integer.valueOf(dtStr.substring(10, 12)); //new Integer(dtStr.substring(10, 12));	
		int second = Integer.valueOf(dtStr.substring(12, 14)); //new Integer(dtStr.substring(12, 14));
		
		c.set(year, month, date, hourOfDay, minute, second);
		
		return c.getTime();
	}
	
	public void assignUser(User user) {
		this.registerId = user.getUserId();
		this.registerName = user.getUserName();
		this.updaterId = this.registerId;
		this.updaterName = this.registerName;
		this.portalId = user.getPortalId();
	}
	
	/**
	 * @return the wholeday
	 */
	public int getWholeday() {
		return wholeday;
	}

	/**
	 * @param wholeday the wholeday to set
	 */
	public void setWholeday(int wholeday) {
		this.wholeday = wholeday;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the attendanceRequest
	 */
	public int getAttendanceRequest() {
		return attendanceRequest;
	}

	/**
	 * @param attendanceRequest the attendanceRequest to set
	 */
	public void setAttendanceRequest(int attendanceRequest) {
		this.attendanceRequest = attendanceRequest;
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

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
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

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the schedulePublic
	 */
	public int getSchedulePublic() {
		return schedulePublic;
	}

	/**
	 * @param schedulePublic the schedulePublic to set
	 */
	public void setSchedulePublic(int schedulePublic) {
		this.schedulePublic = schedulePublic;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the scheduleId
	 */
	public String getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * @return the scheduleType
	 */
	public String getScheduleType() {
		return scheduleType;
	}

	/**
	 * @param scheduleType the scheduleType to set
	 */
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	/**
	 * @return the alarmRequest
	 */
	public int getAlarmRequest() {
		return alarmRequest;
	}

	/**
	 * @param alarmRequest the alarmRequest to set
	 */
	public void setAlarmRequest(int alarmRequest) {
		this.alarmRequest = alarmRequest;
	}

	/**
	 * @return the attachFileCount
	 */
	public int getAttachFileCount() {
		return attachFileCount;
	}

	/**
	 * @param attachFileCount the attachFileCount to set
	 */
	public void setAttachFileCount(int attachFileCount) {
		this.attachFileCount = attachFileCount;
	}

	/**
	 * @return the repeat
	 */
	public int getRepeat() {
		return repeat;
	}

	/**
	 * @param repeat the repeat to set
	 */
	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	/**
	 * @return the repeatGroupId
	 */
	public String getRepeatGroupId() {
		return repeatGroupId;
	}

	/**
	 * @param repeatGroupId the repeatGroupId to set
	 */
	public void setRepeatGroupId(String repeatGroupId) {
		this.repeatGroupId = repeatGroupId;
	}

	/**
	 * @return the recurrences
	 */
	public List<Recurrences> getRecurrences() {
		return recurrences;
	}

	/**
	 * @param recurrences the recurrences to set
	 */
	public void setRecurrences(List<Recurrences> recurrences) {
		this.recurrences = recurrences;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 * 
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the alarmList
	 */
	public List<Alarm> getAlarmList() {
		return alarmList;
	}

	/**
	 * @param alarmList the alarmList to set
	 */
	public void setAlarmList(List<Alarm> alarmList) {
		this.alarmList = alarmList;
	}

	/**
	 * @return the participantList
	 */
	public List<Participant> getParticipantList() {
		return participantList;
	}

	/**
	 * @param participantList the participantList to set
	 */
	public void setParticipantList(List<Participant> participantList) {
		this.participantList = participantList;
	}

	public String getMandatorId() {
		return mandatorId;
	}

	public void setMandatorId(String mandatorId) {
		this.mandatorId = mandatorId;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getWorkspaceName() {
		return workspaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	public Date getOldStart() {
		return oldStart;
	}

	public void setOldStart(Date oldStart) {
		this.oldStart = oldStart;
	}

	public Date getOldEnd() {
		return oldEnd;
	}

	public void setOldEnd(Date oldEnd) {
		this.oldEnd = oldEnd;
	}

	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}

	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	public boolean isSendmail() {
		return sendmail;
	}

	public void setSendmail(boolean sendmail) {
		this.sendmail = sendmail;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getUserEnglishInfo() {
		return userEnglishInfo;
	}

	public void setUserEnglishInfo(String userEnglishInfo) {
		this.userEnglishInfo = userEnglishInfo;
	}

	/**
	 * @return the meetingRoomId
	 */
	public String getMeetingRoomId() {
		return meetingRoomId;
	}

	/**
	 * @param meetingRoomId the meetingRoomId to set
	 */
	public void setMeetingRoomId(String meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

	/**
	 * @return the sdate
	 */
	public String getSdate() {
		return sdate;
	}

	/**
	 * @param sdate the sdate to set
	 */
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	/**
	 * @return the stime
	 */
	public String getStime() {
		return stime;
	}

	/**
	 * @param stime the stime to set
	 */
	public void setStime(String stime) {
		this.stime = stime;
	}

	/**
	 * @return the edate
	 */
	public String getEdate() {
		return edate;
	}

	/**
	 * @param edate the edate to set
	 */
	public void setEdate(String edate) {
		this.edate = edate;
	}

	/**
	 * @return the etime
	 */
	public String getEtime() {
		return etime;
	}

	/**
	 * @param etime the etime to set
	 */
	public void setEtime(String etime) {
		this.etime = etime;
	}

	/**
	 * @return the alarmType
	 */
	public String getAlarmType() {
		return alarmType;
	}

	/**
	 * @param alarmType the alarmType to set
	 */
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	/**
	 * @return the alarmTime
	 */
	public String getAlarmTime() {
		return alarmTime;
	}

	/**
	 * @param alarmTime the alarmTime to set
	 */
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	/**
	 * @return the listType
	 */
	public String getListType() {
		return listType;
	}

	/**
	 * @param listType the listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}

	/**
	 * @return the approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}


	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getWorkAreaName() {
		return workAreaName;
	}

	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}

	public int getEveryoneSchedule() {
		return everyoneSchedule;
	}

	public void setEveryoneSchedule(int everyoneSchedule) {
		this.everyoneSchedule = everyoneSchedule;
	}

	public int getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(int approveResult) {
		this.approveResult = approveResult;
	}

	public int getUpdateDisplay() {
		return updateDisplay;
	}

	public void setUpdateDisplay(int updateDisplay) {
		this.updateDisplay = updateDisplay;
	}

	public String getCartooletcId() {
		return cartooletcId;
	}

	public void setCartooletcId(String cartooletcId) {
		this.cartooletcId = cartooletcId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public List<MeetingRoom> getMeetingRoomList() {
		return meetingRoomList;
	}

	public void setMeetingRoomList(List<MeetingRoom> meetingRoomList) {
		this.meetingRoomList = meetingRoomList;
	}

	public String getTrusteeId() {
		return trusteeId;
	}

	public void setTrusteeId(String trusteeId) {
		this.trusteeId = trusteeId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCartooletcName() {
		return cartooletcName;
	}

	public void setCartooletcName(String cartooletcName) {
		this.cartooletcName = cartooletcName;
	}

	public String getPernr() {
		return pernr;
	}

	public void setPernr(String pernr) {
		this.pernr = pernr;
	}

	public String getAtext() {
		return atext;
	}

	public void setAtext(String atext) {
		this.atext = atext;
	}

	public String getRequestReason() {
		return requestReason;
	}

	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}

	public Date getBegda() {
		return begda;
	}

	public void setBegda(Date begda) {
		this.begda = begda;
	}

	public Date getEndda() {
		return endda;
	}

	public void setEndda(Date endda) {
		this.endda = endda;
	}

	public String getWhole() {
		return whole;
	}

	public void setWhole(String whole) {
		this.whole = whole;
	}

	public String getAwart() {
		return awart;
	}

	public void setAwart(String awart) {
		this.awart = awart;
	}

	public List<String> getTargetGroupList() {
		return targetGroupList;
	}

	public void setTargetGroupList(List<String> targetGroupList) {
		this.targetGroupList = targetGroupList;
	}

	public List<String> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<String> targetList) {
		this.targetList = targetList;
	}

	public List<FavoriteTarget> getTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(List<FavoriteTarget> targetGroup) {
		this.targetGroup = targetGroup;
	}

	public List<FavoriteTarget> getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(List<FavoriteTarget> targetUser) {
		this.targetUser = targetUser;
	}

	public String getTargetGroupId() {
		return targetGroupId;
	}

	public void setTargetGroupId(String targetGroupId) {
		this.targetGroupId = targetGroupId;
	}

	public String getTargetGroupName() {
		return targetGroupName;
	}

	public void setTargetGroupName(String targetGroupName) {
		this.targetGroupName = targetGroupName;
	}

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	
}
