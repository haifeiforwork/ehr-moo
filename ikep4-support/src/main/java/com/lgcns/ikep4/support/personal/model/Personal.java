package com.lgcns.ikep4.support.personal.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Favorite
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: Personal.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Personal extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -4211116526098936007L;

	/**
	 * 아이템 ID
	 */
	private String itemId;

	/**
	 * 서브 ID
	 */
	private String subId;

	/**
	 * 모듈 한국어
	 */
	private String module;
	
	/**
	 * 모듈 영어
	 */
	private String moduleEn;

	/**
	 * 타입
	 */
	private String type;

	/**
	 * 제목
	 */
	private String title;

	/**
	 * 조회수
	 */
	private int hitCount;

	/**
	 * 추천수
	 */
	private int recommendCount;

	/**
	 * 댓글수
	 */
	private int linereplyCount;

	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록자영문명
	 */
	private String registerEnglishName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * id
	 */
	private String fileId;

	/**
	 * 저장경로
	 */
	private String filePath;

	/**
	 * 저장파일명
	 */
	private String fileName;

	/**
	 * 실제파일명
	 */
	private String fileRealName;

	/**
	 * 파일타입
	 */
	private String fileContentsType;

	/**
	 * 확장자
	 */
	private String fileExtension;

	/**
	 * 크기
	 */
	private int fileSize;

	/**
	 * 타겟 아이템 ID
	 */
	private String targetId;

	/**
	 * 타겟 아이템 URL
	 */
	private String targetUrl;

	/**
	 * 댓글 ID
	 */
	private String linereplyId;

	/**
	 * 댓글 내용
	 */
	private String contents;

	/**
	 * 댓글 등록자 ID
	 */
	private String linereplyRegisterId;

	/**
	 * 댓글 등록자명
	 */
	private String linereplyRegisterName;

	/**
	 * 댓글 등록자 영문명
	 */
	private String linereplyRegisterEnglishName;

	/**
	 * 댓글 등록일
	 */
	private Date linereplyRegistDate;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	public void setModuleEn(String moduleEn) {
		this.moduleEn = moduleEn;
	}

	public String getModuleEn() {
		return moduleEn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	public int getLinereplyCount() {
		return linereplyCount;
	}

	public void setLinereplyCount(int linereplyCount) {
		this.linereplyCount = linereplyCount;
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

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileRealName() {
		return fileRealName;
	}

	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}

	public String getFileContentsType() {
		return fileContentsType;
	}

	public void setFileContentsType(String fileContentsType) {
		this.fileContentsType = fileContentsType;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getLinereplyId() {
		return linereplyId;
	}

	public void setLinereplyId(String linereplyId) {
		this.linereplyId = linereplyId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getLinereplyRegisterId() {
		return linereplyRegisterId;
	}

	public void setLinereplyRegisterId(String linereplyRegisterId) {
		this.linereplyRegisterId = linereplyRegisterId;
	}

	public String getLinereplyRegisterName() {
		return linereplyRegisterName;
	}

	public void setLinereplyRegisterName(String linereplyRegisterName) {
		this.linereplyRegisterName = linereplyRegisterName;
	}

	public Date getLinereplyRegistDate() {
		return linereplyRegistDate;
	}

	public void setLinereplyRegistDate(Date linereplyRegistDate) {
		this.linereplyRegistDate = linereplyRegistDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	public String getLinereplyRegisterEnglishName() {
		return linereplyRegisterEnglishName;
	}

	public void setLinereplyRegisterEnglishName(String linereplyRegisterEnglishName) {
		this.linereplyRegisterEnglishName = linereplyRegisterEnglishName;
	}
}
