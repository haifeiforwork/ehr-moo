/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.message.util.MessageUtil;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: Message.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Message extends BaseObject {

	/** MessageService 아이템 타입. */
	public static final String ITEM_TYPE = "MessageService";

	private static final long serialVersionUID = -822341272640411863L;

	/**
	 * 메시지ID
	 */
	private String messageId;

	/**
	 * 카테고리ID
	 */
	private String categoryId;

	/**
	 * 카테고리이름
	 */
	private String categoryName;

	/**
	 * 카테고리이름
	 */
	private String categoryEnglishName;

	/**
	 * 컨텐츠
	 */
	@NotEmpty
	private String contents;

	/**
	 * 첨부_수
	 */
	private Integer attachCount;

	/**
	 * 첨부_크기
	 */
	private Integer attachSize;

	/**
	 * 수신자_수
	 */
	private Integer receiverCount;

	/**
	 * 수신자_LIST
	 */
	@NotEmpty
	private String receiverList;

	/**
	 * 수신자_LIST
	 */
	private String receiverEnglishList;

	/**
	 * 여부_긴급
	 */
	private Integer isUrgent;

	/**
	 * 여부_보관
	 */
	private Integer isStored;

	/**
	 * 전송_상태
	 */
	private Integer isComplete;

	/**
	 * 발신자ID
	 */
	private String senderId;

	/**
	 * 발신자_이름
	 */
	private String senderName;

	/**
	 * 발신자_영문_이름
	 */
	private String senderEnglishName;

	/**
	 * 발신_일
	 */
	private Date sendDate;

	/**
	 * 사용자ID
	 */
	private String userId;

	/**
	 * 수신일
	 */
	private Date receiveDate;

	/**
	 * 여부_읽기
	 */
	private Integer isRead;

	/**
	 * 읽은날짜
	 */
	private Date readDate;

	/**
	 * 삭제여부
	 */
	private Integer isDelete;

	/**
	 * 쪽지구분
	 */
	private String messageClass;

	/**
	 * 쪽지구분
	 */
	private String receiverCheck;

	/**
	 * 사용자이름
	 */
	private String userName;

	/**
	 * 사용자이름
	 */
	private String userEnglishName;

	/**
	 * 수신확인 List
	 */
	private List<ReadMessageUser> readMessageUser;

	/**
	 * 수신타입 P : 사용자(Person), T : 부서(Team)
	 */
	private String receiveType;

	/**
	 * 그룹명
	 */
	private String groupName;

	/**
	 * 그룹명
	 */
	private String groupEnglishName;

	/**
	 * FileLink
	 */
	private List<FileLink> fileLinkList;

	/**
	 * FileData
	 */
	private List<FileData> fileDataList;

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

	public Integer getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Integer attachSize) {
		this.attachSize = attachSize;
	}

	public Integer getReceiverCount() {
		return receiverCount;
	}

	public void setReceiverCount(Integer receiverCount) {
		this.receiverCount = receiverCount;
	}

	public String getReceiverList() {
		return receiverList;
	}

	public void setReceiverList(String receiverList) {
		this.receiverList = receiverList;
	}

	public Integer getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(Integer isUrgent) {
		this.isUrgent = isUrgent;
	}

	public Integer getIsStored() {
		return isStored;
	}

	public void setIsStored(Integer isStored) {
		this.isStored = isStored;
	}

	public Integer getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
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

	@JsonSerialize(using = MessageUtil.class)
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getMessageClass() {
		return messageClass;
	}

	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}

	public String getReceiverCheck() {
		return receiverCheck;
	}

	public void setReceiverCheck(String receiverCheck) {
		this.receiverCheck = receiverCheck;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<ReadMessageUser> getReadMessageUser() {
		return readMessageUser;
	}

	public void setReadMessageUser(List<ReadMessageUser> readMessageUser) {
		this.readMessageUser = readMessageUser;
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

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getReceiverEnglishList() {
		return receiverEnglishList;
	}

	public void setReceiverEnglishList(String receiverEnglishList) {
		this.receiverEnglishList = receiverEnglishList;
	}

	public String getSenderEnglishName() {
		return senderEnglishName;
	}

	public void setSenderEnglishName(String senderEnglishName) {
		this.senderEnglishName = senderEnglishName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getGroupEnglishName() {
		return groupEnglishName;
	}

	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}

	public String getCategoryEnglishName() {
		return categoryEnglishName;
	}

	public void setCategoryEnglishName(String categoryEnglishName) {
		this.categoryEnglishName = categoryEnglishName;
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
