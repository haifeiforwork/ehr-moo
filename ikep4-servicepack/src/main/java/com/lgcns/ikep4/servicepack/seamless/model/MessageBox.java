package com.lgcns.ikep4.servicepack.seamless.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.servicepack.seamless.util.SeamlessMessageUtil;


public class MessageBox extends BaseObject {

	private static final long serialVersionUID = -5236075381641516225L;

	/**
	 * 메시지ID
	 */
	private String messageId;

	/**
	 * MAIL ID
	 */
	private String mailUid;

	/**
	 * 메시지타입
	 */
	private String messageType;

	/**
	 * 메시지 보내기 타입
	 */
	private List<String> sendType;

	/**
	 * 제목
	 */
	private String title;

	/**
	 * 내용
	 */
	private String contents;

	/**
	 * 첨부_수
	 */
	private Integer attachCount;

	/**
	 * 수신자_수
	 */
	private Integer receiverCount;

	/**
	 * 발신자_ID
	 */
	private String senderId;

	/**
	 * 발신자_이름
	 */
	private String senderName;

	/**
	 * 발신자_이름 (영문)
	 */
	private String senderEngName;

	/**
	 * 발신_일자
	 */
	private Date sendDate;

	/**
	 * 발신_일자
	 */
	private Date sendDateOrg;

	/**
	 * 삭제_여부
	 */
	private Integer isDelete;

	/**
	 * 소유자_ID
	 */
	private String ownerId;

	/**
	 * 읽기_여부
	 */
	private Integer isRead;

	/**
	 * 발신자_메일주소
	 */
	private String senderMail;

	/**
	 * 내부메세지_여부
	 */
	private Integer isInternalMessage;

	/**
	 * 발신자_팀명
	 */
	private String senderTeamName;

	/**
	 * 발신자_팀명(영문)
	 */
	private String senderTeamEngName;

	/**
	 * 발신자_직급명칭
	 */
	private String senderJobTitleName;

	/**
	 * 발신자_직급명칭(영문)
	 */
	private String senderJobTitleEngName;

	/**
	 * 수신자_타입
	 */
	private String receiverType;

	/**
	 * 수신자_ID
	 */
	private String receiverId;

	/**
	 * 수신자_이름
	 */
	private String receiverName;

	/**
	 * 수신자_이름(영문)
	 */
	private String receiverEngName;

	/**
	 * 수신자_메일
	 */
	private String receiverMail;

	/**
	 * 수신자_팀명
	 */
	private String receiverTeamName;

	/**
	 * 수신자_팀명(영문)
	 */
	private String receiverTeamEngName;

	/**
	 * 수신자_직급명칭
	 */
	private String receiverJobTitleName;

	/**
	 * 수신자_직급명칭(영문)
	 */
	private String receiverJobTitleEngName;

	/**
	 * 오늘 여부
	 */
	private Integer isToday;

	/**
	 * String Date
	 */
	private String stringDate;

	/**
	 * 수신[R],발신[S] 구분
	 */
	private String boxType;

	/**
	 * 사용자정보
	 */
	private User infoUser;

	/**
	 * 수신자List
	 */
	private List<Recipient> recipientList;

	/**
	 * 수신자List(String[] type)
	 */
	private String receiverList;

	/**
	 * 첨부파일 List
	 */
	private List<AttachFile> attachFileList;

	/**
	 * FileLink
	 */
	private List<FileLink> fileLinkList;

	/**
	 * FileData
	 */
	private List<FileData> fileDataList;

	@SuppressWarnings("unused")
	private String jsSendDate;

	@SuppressWarnings("unused")
	private String jsSendTime;

	/**
	 * 사진파일 경로 (큰이미지)
	 */
	private String picturePath;

	/**
	 * 사진파일 경로 (작은이미지)
	 */
	private String profilePicturePath;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Integer getAttachCount() {
		return attachCount;
	}

	public void setAttachCount(Integer attachCount) {
		this.attachCount = attachCount;
	}

	public Integer getReceiverCount() {
		return receiverCount;
	}

	public void setReceiverCount(Integer receiverCount) {
		this.receiverCount = receiverCount;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@JsonSerialize(using = SeamlessMessageUtil.class)
	public Date getSendDate() {
		return sendDate;
	}

	public String getJsSendDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		String rnDate = "";

		if (isToday == 1) {
			rnDate = timeFormat.format(sendDate);
		} else {
			rnDate = dateFormat.format(sendDate);
		}
		return rnDate;
	}

	public String getJsSendTime() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		return timeFormat.format(sendDate);
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	public Integer getIsInternalMessage() {
		return isInternalMessage;
	}

	public void setIsInternalMessage(Integer isInternalMessage) {
		this.isInternalMessage = isInternalMessage;
	}

	public User getInfoUser() {
		return infoUser;
	}

	public void setInfoUser(User infoUser) {
		this.infoUser = infoUser;
	}

	public List<Recipient> getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(List<Recipient> recipientList) {
		this.recipientList = recipientList;
	}

	public List<AttachFile> getAttachFileList() {
		return attachFileList;
	}

	public void setAttachFileList(List<AttachFile> attachFileList) {
		this.attachFileList = attachFileList;
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

	public String getSenderTeamName() {
		return senderTeamName;
	}

	public void setSenderTeamName(String senderTeamName) {
		this.senderTeamName = senderTeamName;
	}

	public String getSenderJobTitleName() {
		return senderJobTitleName;
	}

	public void setSenderJobTitleName(String senderJobTitleName) {
		this.senderJobTitleName = senderJobTitleName;
	}

	public Integer getIsToday() {
		return isToday;
	}

	public void setIsToday(Integer isToday) {
		this.isToday = isToday;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMail() {
		return receiverMail;
	}

	public void setReceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}

	public String getReceiverTeamName() {
		return receiverTeamName;
	}

	public void setReceiverTeamName(String receiverTeamName) {
		this.receiverTeamName = receiverTeamName;
	}

	public String getReceiverJobTitleName() {
		return receiverJobTitleName;
	}

	public void setReceiverJobTitleName(String receiverJobTitleName) {
		this.receiverJobTitleName = receiverJobTitleName;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public String getStringDate() {
		return stringDate;
	}

	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}

	public String getReceiverList() {
		return receiverList;
	}

	public void setReceiverList(String receiverList) {
		this.receiverList = receiverList;
	}

	@JsonSerialize(using = SeamlessMessageUtil.class)
	public Date getSendDateOrg() {
		return sendDateOrg;
	}

	public void setSendDateOrg(Date sendDateOrg) {
		this.sendDateOrg = sendDateOrg;
	}

	public String getSenderEngName() {
		return senderEngName;
	}

	public void setSenderEngName(String senderEngName) {
		this.senderEngName = senderEngName;
	}

	public String getSenderTeamEngName() {
		return senderTeamEngName;
	}

	public void setSenderTeamEngName(String senderTeamEngName) {
		this.senderTeamEngName = senderTeamEngName;
	}

	public String getSenderJobTitleEngName() {
		return senderJobTitleEngName;
	}

	public void setSenderJobTitleEngName(String senderJobTitleEngName) {
		this.senderJobTitleEngName = senderJobTitleEngName;
	}

	public String getReceiverEngName() {
		return receiverEngName;
	}

	public void setReceiverEngName(String receiverEngName) {
		this.receiverEngName = receiverEngName;
	}

	public String getReceiverTeamEngName() {
		return receiverTeamEngName;
	}

	public void setReceiverTeamEngName(String receiverTeamEngName) {
		this.receiverTeamEngName = receiverTeamEngName;
	}

	public String getReceiverJobTitleEngName() {
		return receiverJobTitleEngName;
	}

	public void setReceiverJobTitleEngName(String receiverJobTitleEngName) {
		this.receiverJobTitleEngName = receiverJobTitleEngName;
	}

	public String getMailUid() {
		return mailUid;
	}

	public void setMailUid(String mailUid) {
		this.mailUid = mailUid;
	}

	public List<String> getSendType() {
		return sendType;
	}

	public void setSendType(List<String> sendType) {
		this.sendType = sendType;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getProfilePicturePath() {
		return profilePicturePath;
	}

	public void setProfilePicturePath(String profilePicturePath) {
		this.profilePicturePath = profilePicturePath;
	}

}
