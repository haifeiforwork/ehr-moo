package com.lgcns.ikep4.util.mail.model;

import org.apache.commons.mail.EmailAttachment;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Mail 첨부파일 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: MailAttach.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MailAttach extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -3323536731201070816L;

	/**
	 * 파일명
	 */
	private String name;

	/**
	 * URL 파일을 첨부할 경우 설정
	 */
	private String url;

	/**
	 * 로컬 파일을 첨부할 경우 설정
	 */
	private String path;

	/**
	 * 비고
	 */
	private String description;

	/**
	 * EmailAttachment.ATTACHMENT
	 */
	private String disposition = EmailAttachment.ATTACHMENT;

	/**
	 * 파일 크기
	 */
	private int size;

	/**
	 * 파일 타입
	 */
	private String fileType;

	/**
	 * multipart에서 파일의 위치 경로
	 */
	private String multipartPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getMultipartPath() {
		return multipartPath;
	}

	public void setMultipartPath(String multipartPath) {
		this.multipartPath = multipartPath;
	}

}
