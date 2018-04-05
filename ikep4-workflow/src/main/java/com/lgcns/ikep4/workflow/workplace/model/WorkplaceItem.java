/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 이재경
 * @version $Id: WorkplaceItem.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class WorkplaceItem extends BaseObject {

	static final long serialVersionUID = -427033559893562577L;

	private String itemId;

    private String boardId;

    private String itemParentId;

    private String itemGroupId;

    private Integer step;

    private Integer indentation;

    private String itemSeqId;

    private String itemType;

    private Integer itemDisplay;

    private String title;

    private Integer hitCount;

    private Integer recommendCount;

    private Integer replyCount;

    private Integer linereplyCount;

    private Integer attachFileCount;

    private Date startDate;

    private Date endDate;

    private Integer itemDelete;

    private String registerId;

    private String registerName;

    private Date registDate;

    private String updaterId;

    private String updaterName;

    private Date updateDate; 
 
    private String contents;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getItemParentId() {
		return itemParentId;
	}

	public void setItemParentId(String itemParentId) {
		this.itemParentId = itemParentId;
	}

	public String getItemGroupId() {
		return itemGroupId;
	}

	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getIndentation() {
		return indentation;
	}

	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
	}

	public String getItemSeqId() {
		return itemSeqId;
	}

	public void setItemSeqId(String itemSeqId) {
		this.itemSeqId = itemSeqId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Integer getItemDisplay() {
		return itemDisplay;
	}

	public void setItemDisplay(Integer itemDisplay) {
		this.itemDisplay = itemDisplay;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public Integer getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(Integer recommendCount) {
		this.recommendCount = recommendCount;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public Integer getLinereplyCount() {
		return linereplyCount;
	}

	public void setLinereplyCount(Integer linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	public Integer getAttachFileCount() {
		return attachFileCount;
	}

	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
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

	public Integer getItemDelete() {
		return itemDelete;
	}

	public void setItemDelete(Integer itemDelete) {
		this.itemDelete = itemDelete;
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

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}	
}
