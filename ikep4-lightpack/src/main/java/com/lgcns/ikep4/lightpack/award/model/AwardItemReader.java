/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.model;

import java.util.Date;

/**
 * 게시글 조회자
 *
 * @author ${user}
 * @version $$Id: AwardReference.java 16240 2011-08-18 04:08:15Z giljae $$
 */
public class AwardItemReader extends AwardReferencePK { 

	


	static final long serialVersionUID = 3799859553101751062L;

	private String itemId;
	
	private String awardItemId;
	
	private String readerType;
	
	private String readerId;
	
	private String readerName;

	private String readerTeamName;
	
	private Date readDate;
	
	private String readerMail;
	
	private String readerPassword;
	
	private String contents;
	
	private String wordName;

	
	
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getReaderPassword() {
		return readerPassword;
	}

	public void setReaderPassword(String readerPassword) {
		this.readerPassword = readerPassword;
	}

	public String getReaderMail() {
		return readerMail;
	}

	public void setReaderMail(String readerMail) {
		this.readerMail = readerMail;
	}

	public String getReaderTeamName() {
		return readerTeamName;
	}

	public void setReaderTeamName(String readerTeamName) {
		this.readerTeamName = readerTeamName;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public String getReaderName() {
		return readerName;
	}

	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getAwardItemId() {
		return awardItemId;
	}

	public void setAwardItemId(String awardItemId) {
		this.awardItemId = awardItemId;
	}

	public String getReaderType() {
		return readerType;
	}

	public void setReaderType(String readerType) {
		this.readerType = readerType;
	}

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}


 
}