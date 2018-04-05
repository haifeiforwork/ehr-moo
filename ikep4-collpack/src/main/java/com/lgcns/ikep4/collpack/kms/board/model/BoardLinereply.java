/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 댓글 모델 클래스
 *
 * @author ${user}
 * @version $$Id: BoardLinereply.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public class BoardLinereply extends BaseObject {
	private static final long serialVersionUID = 200453567356205870L;
	
	/** 댓글 ID */
	private String linereplyId;
	
	/** 게시글 ID */
    private String itemId;
    
    /** 댓글 그룹 ID */
    private String linereplyGroupId;
    
    /** 부모 댓글 ID*/
    private String linereplyParentId;
   
    /** 같은 부모 내의 댓글 형제들의 순서  */
    private Integer step = 0;
   
    /** 댓글의 들여쓰기 수  */
    private Integer indentation = 0;
   
    /** 내용 */
    private String contents;
   
    /** 댓글 삭제여부 (작성자가 삭제를 하는 경우 하위 댓글이 있는 경우 "1"로 바꾸고 삭제된 댓글입니다. 표시  */
    private Integer linereplyDelete = 0;
	
    /** 등록자 ID. */
	private String registerId;

	/** 등록자 이름. */
	private String registerName;

	/** 등록일. */
	private Date registDate;

	/** 수정자 Id. */
	private String updaterId;

	/** 게시물 ID. */
	private String updaterName;

	/** 수정자 ID. */
	private Date updateDate;
   
	/** 작성자  */
    private User user;  
    
	private String groupId;
	private String groupName;
	
	private String score;
	
	private String flag;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLinereplyId() {
		return linereplyId;
	}

	public void setLinereplyId(String linereplyId) {
		this.linereplyId = linereplyId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getLinereplyGroupId() {
		return linereplyGroupId;
	}

	public void setLinereplyGroupId(String linereplyGroupId) {
		this.linereplyGroupId = linereplyGroupId;
	}

	public String getLinereplyParentId() {
		return linereplyParentId;
	}

	public void setLinereplyParentId(String linereplyParentId) {
		this.linereplyParentId = linereplyParentId;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Integer getLinereplyDelete() {
		return linereplyDelete;
	}

	public void setLinereplyDelete(Integer linereplyDelete) {
		this.linereplyDelete = linereplyDelete;
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	} 
}