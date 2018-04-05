/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.search;

import java.util.Date;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 소셜블로그 댓글 조회시 사용하기 위한 VO
 *
 * @author 이형운
 * @version $Id: SocialBoardLinereplySearchCondition.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialBoardLinereplySearchCondition extends SearchCondition {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 538298286424301760L;

	/**
	 * 댓글 ID
	 */
	private String linereplyId;

	/**
	 * 댓글이 작성된 게시글 아이템 ID
	 */
    private String itemId;

    /**
     * 댓글 그룹 ID
     */
    private String linereplyGroupId;

    /**
     * 댓글 부모그룹 ID
     */
    private String linereplyParentId;

    /**
     * 댓글 스탭
     */
    private Integer step = 0;

    /**
     * 댓글 인덴트
     */
    private Integer indentation = 0;

    /**
     * 댓글 내용
     */
    private String contents;

    /**
     * 댓글 삭제 여부
     */
    private Integer linereplyDelete = 0;

    /**
     * 댓글 등록자 ID
     */
    private String registerId;

    /**
     * 댓글 등록자 명
     */
    private String registerName;

    /**
     * 댓글 등록일시
     */
    private Date registDate;

    /**
     * 댓글 수정자 ID
     */
    private String updaterId;

    /**
     * 댓글 수정자 명
     */
    private String updaterName;

    /**
     * 댓글 수정일
     */
    private Date updateDate;
    
    /**
     * 등록한 작성자 English Name
     */
    private String registerEnglishName;
    
    /**
     * 등록한 작성자 Team Name
     */
    private String registerTeamName;
    
    /**
     * 등록한 작성자 Team Englisgh Name
     */
    private String registerTeamEngName;
    
    /**
     * 등록한 작성자 JobTitleCode
     */
    private String registerJobTitleCode;
    
    /**
     * 등록한 작성자 JobTitleName
     */
    private String registerJobTitleName;
    
    /**
     * 등록한 작성자 registerJobTitleEnglishName
     */
    private String registerJobTitleEnglishName;
    
    
    /**
     * 로그인한 세션 사용자의 locale 정보
     */
    private String sessUserLocale;

	/**
	 * @return the linereplyId
	 */
	public String getLinereplyId() {
		return linereplyId;
	}

	/**
	 * @param linereplyId the linereplyId to set
	 */
	public void setLinereplyId(String linereplyId) {
		this.linereplyId = linereplyId;
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
	 * @return the linereplyGroupId
	 */
	public String getLinereplyGroupId() {
		return linereplyGroupId;
	}

	/**
	 * @param linereplyGroupId the linereplyGroupId to set
	 */
	public void setLinereplyGroupId(String linereplyGroupId) {
		this.linereplyGroupId = linereplyGroupId;
	}

	/**
	 * @return the linereplyParentId
	 */
	public String getLinereplyParentId() {
		return linereplyParentId;
	}

	/**
	 * @param linereplyParentId the linereplyParentId to set
	 */
	public void setLinereplyParentId(String linereplyParentId) {
		this.linereplyParentId = linereplyParentId;
	}

	/**
	 * @return the step
	 */
	public Integer getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(Integer step) {
		this.step = step;
	}

	/**
	 * @return the indentation
	 */
	public Integer getIndentation() {
		return indentation;
	}

	/**
	 * @param indentation the indentation to set
	 */
	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
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
	 * @return the linereplyDelete
	 */
	public Integer getLinereplyDelete() {
		return linereplyDelete;
	}

	/**
	 * @param linereplyDelete the linereplyDelete to set
	 */
	public void setLinereplyDelete(Integer linereplyDelete) {
		this.linereplyDelete = linereplyDelete;
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
	 * @return the registerEnglishName
	 */
	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	/**
	 * @param registerEnglishName the registerEnglishName to set
	 */
	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	/**
	 * @return the registerTeamName
	 */
	public String getRegisterTeamName() {
		return registerTeamName;
	}

	/**
	 * @param registerTeamName the registerTeamName to set
	 */
	public void setRegisterTeamName(String registerTeamName) {
		this.registerTeamName = registerTeamName;
	}

	
	/**
	 * @return the registerTeamEngName
	 */
	public String getRegisterTeamEngName() {
		return registerTeamEngName;
	}

	/**
	 * @param registerTeamEngName the registerTeamEngName to set
	 */
	public void setRegisterTeamEngName(String registerTeamEngName) {
		this.registerTeamEngName = registerTeamEngName;
	}
	
	/**
	 * @return the registerJobTitleCode
	 */
	public String getRegisterJobTitleCode() {
		return registerJobTitleCode;
	}

	/**
	 * @param registerJobTitleCode the registerJobTitleCode to set
	 */
	public void setRegisterJobTitleCode(String registerJobTitleCode) {
		this.registerJobTitleCode = registerJobTitleCode;
	}

	/**
	 * @return the registerJobTitleName
	 */
	public String getRegisterJobTitleName() {
		return registerJobTitleName;
	}

	/**
	 * @param registerJobTitleName the registerJobTitleName to set
	 */
	public void setRegisterJobTitleName(String registerJobTitleName) {
		this.registerJobTitleName = registerJobTitleName;
	}

	/**
	 * @return the sessUserLocale
	 */
	public String getSessUserLocale() {
		return sessUserLocale;
	}

	/**
	 * @param sessUserLocale the sessUserLocale to set
	 */
	public void setSessUserLocale(String sessUserLocale) {
		this.sessUserLocale = sessUserLocale;
	}

	/**
	 * @return the registerJobTitleEnglishName
	 */
	public String getRegisterJobTitleEnglishName() {
		return registerJobTitleEnglishName;
	}

	/**
	 * @param registerJobTitleEnglishName the registerJobTitleEnglishName to set
	 */
	public void setRegisterJobTitleEnglishName(String registerJobTitleEnglishName) {
		this.registerJobTitleEnglishName = registerJobTitleEnglishName;
	}

}
