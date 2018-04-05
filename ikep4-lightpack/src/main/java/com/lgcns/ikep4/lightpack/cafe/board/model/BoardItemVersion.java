/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 김종철
 * @version $Id: BoardItemVersion.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class BoardItemVersion extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 7842700329244222863L;

	/**
	 * 버전 아이디
	 */
	private String versionId;

	// 버전 정보
	private Double version;

	// 게시물 아이디
	private String itemId;

	// 게시물 타입
	private String itemType;

	// 공지여부
	private Integer itemDisplay = 0;

	// 게시물 제목
	private String title;

	// 게시물 내용
	private String contents;

	// 게시물 첨부 갯수
	private Integer attachFileCount;

	// 게시 시작일
	private Date startDate;

	// 게시 종료일
	private Date endDate;

	// 등록자 아이디
	private String registerId;

	// 등록자 명
	private String registerName;

	// 등록일
	private Date registDate;

	// 게시물 비교 버전
	private BoardItemVersion compareBoardItem;

	// 파일 링크 정보
	private List<FileLink> fileLinkList;

	// 파일 데이터
	private List<FileData> fileDataList;

	// 버전 아이디
	private List<String> versionIds;

	private String fileDataListJson;

	/**
	 * @return the versionId
	 */
	public String getVersionId() {
		return versionId;
	}

	/**
	 * @param versionId the versionId to set
	 */
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	/**
	 * @return the version
	 */
	public Double getVersion() {
		return version;
	}

	/**
	 * @param d the version to set
	 */
	public void setVersion(Double version) {
		this.version = version;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the itemDEisplay
	 */
	public Integer getItemDisplay() {
		return itemDisplay;
	}

	/**
	 * @param itemDEisplay the itemDEisplay to set
	 */
	public void setItemDisplay(Integer itemDisplay) {
		this.itemDisplay = itemDisplay;
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
	 * @return the attachFileCount
	 */
	public Integer getAttachFileCount() {
		return attachFileCount;
	}

	/**
	 * @param attachFileCount the attachFileCount to set
	 */
	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
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
	 * @return the fileDataList
	 */
	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	/**
	 * @param fileDataList the fileDataList to set
	 */
	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	/**
	 * @return the compareBoardItem
	 */
	public BoardItemVersion getCompareBoardItem() {
		return compareBoardItem;
	}

	/**
	 * @param compareBoardItem the compareBoardItem to set
	 */
	public void setCompareBoardItem(BoardItemVersion compareBoardItem) {
		this.compareBoardItem = compareBoardItem;
	}

	/**
	 * @return the fileLinkList
	 */
	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}

	/**
	 * @param fileLinkList the fileLinkList to set
	 */
	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	/**
	 * @return the versionIds
	 */
	public List<String> getVersionIds() {
		return versionIds;
	}

	/**
	 * @param versionIds the versionIds to set
	 */
	public void setVersionIds(List<String> versionIds) {
		this.versionIds = versionIds;
	}

	/**
	 * @return the fileDataListJson
	 */
	public String getFileDataListJson() {
		return fileDataListJson;
	}

	/**
	 * @param fileDataListJson the fileDataListJson to set
	 */
	public void setFileDataListJson(String fileDataListJson) {
		this.fileDataListJson = fileDataListJson;
	}

}
