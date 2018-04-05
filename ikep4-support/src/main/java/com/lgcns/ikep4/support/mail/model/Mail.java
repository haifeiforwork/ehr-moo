package com.lgcns.ikep4.support.mail.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.mail.MailConstant;


/**
 * Mail 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: Mail.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public class Mail extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 53324305347152143L;

	/**
	 * 메일 서버 아이피
	 */
	private String server;

	/**
	 * 메일 서버 포트
	 */
	private int port;

	/**
	 * 메일 SSL 여부
	 */
	private boolean isSsl;
	
	/**
	 * 메일계정 id
	 */
	private String userId;

	/**
	 * 메일계정 password
	 */
	private String userPwd;

	/**
	 * 발신자 이메일
	 */
	private String fromEmail;

	/**
	 * 발신자 명
	 */
	private String fromName;

	/**
	 * 수신/참조/발신자 이메일 리스트
	 */
	@NotEmpty
	private List<String> emailList;

	/**
	 * 수신자 이메일 리스트
	 */
	@SuppressWarnings("rawtypes")
	private List<HashMap> toEmailList;

	/**
	 * 참조자 이메일
	 */
	private String ccEmail;

	/**
	 * 참조자 이메일 리스트
	 */
	@SuppressWarnings("rawtypes")
	private List<HashMap> ccEmailList;

	/**
	 * 비밀참조자 이메일
	 */
	private String bccEmail;

	/**
	 * 참조자 이메일 리스트
	 */
	@SuppressWarnings("rawtypes")
	private List<HashMap> bccEmailList;

	/**
	 * 제목
	 */
	@NotEmpty
	@Size(min = 0, max = 1000)
	private String title;

	/**
	 * 내용
	 */
	@NotEmpty
	private String content;

	/**
	 * 첨부원본 내용
	 */
	private String contentOriginal;

	/**
	 * 메일 타입 (template, html, text)
	 */
	private String mailType = MailConstant.MAIL_TYPE_HTML;

	/**
	 * Template 메일인 경우, Template Html 파일의 경로를 지정함
	 */
	private String mailTemplatePath;

	/**
	 * 메일ID
	 */
	private int mailId;
	
	/**
	 * UID
	 */
	private String messageId;

	/**
	 * 크기
	 */
	private int size;

	/**
	 * 발신일자
	 */
	private Date sendDate;

	/**
	 * content_type
	 */
	private String contentMimeType;

	/**
	 * Folder 명
	 */
	private String folderName;

	/**
	 * 읽기여부
	 */
	private String readYn;

	/**
	 * 첨부파일 리스트
	 */
	private List<MailAttach> attachList;

	/**
	 * 첨부파일 리스트
	 */
	private List<Mail> childMailList;

	/**
	 * 파일링크 리스트(메일 보낼때 사용 : 플레쉬 파일 업로드 컨트롤러 에서 사용함)
	 */
	private List<FileLink> fileLinkList;

	/**
	 * 파일데이터 리스트(메일 보낼때 사용 : 본문 내용 자동 첨부해서 보내기할때, 첨부파일도 가져오기 위해서 사용함)
	 */
	private List<FileData> fileDataList;

	/**
	 * 수신일자
	 */
	private Date receivedDate;

	public String getFromEmail() {
		return fromEmail;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public String getCcEmail() {
		return ccEmail;
	}

	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}

	@SuppressWarnings("rawtypes")
	public List<HashMap> getCcEmailList() {
		return ccEmailList;
	}

	@SuppressWarnings("rawtypes")
	public void setCcEmailList(List<HashMap> ccEmailList) {
		this.ccEmailList = ccEmailList;
	}

	@SuppressWarnings("rawtypes")
	public List<HashMap> getToEmailList() {
		return toEmailList;
	}

	@SuppressWarnings("rawtypes")
	public void setToEmailList(List<HashMap> toEmailList) {
		this.toEmailList = toEmailList;
	}

	public List<MailAttach> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<MailAttach> attachList) {
		this.attachList = attachList;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMailId() {
		return mailId;
	}

	public void setMailId(int mailId) {
		this.mailId = mailId;
	}

	public String getContentMimeType() {
		return contentMimeType;
	}

	public void setContentMimeType(String contentMimeType) {
		this.contentMimeType = contentMimeType;
	}

	public List<Mail> getChildMailList() {
		return childMailList;
	}

	public void setChildMailList(List<Mail> childMailList) {
		this.childMailList = childMailList;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getReadYn() {
		return readYn;
	}

	public void setReadYn(String readYn) {
		this.readYn = readYn;
	}

	public void addAttachList(MailAttach attachVO) {

		if (attachList == null) {
			attachList = new ArrayList<MailAttach>();
		}

		attachList.add(attachVO);
	}

	public void addChildMailList(Mail mailVO) {

		if (childMailList == null) {
			childMailList = new ArrayList<Mail>();
		}

		childMailList.add(mailVO);
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSsl() {
		return isSsl;
	}

	public void setSsl(boolean isSsl) {
		this.isSsl = isSsl;
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

	public String getBccEmail() {
		return bccEmail;
	}

	public void setBccEmail(String bccEmail) {
		this.bccEmail = bccEmail;
	}

	@SuppressWarnings("rawtypes")
	public List<HashMap> getBccEmailList() {
		return bccEmailList;
	}

	@SuppressWarnings("rawtypes")
	public void setBccEmailList(List<HashMap> bccEmailList) {
		this.bccEmailList = bccEmailList;
	}

	public String getMailTemplatePath() {
		return mailTemplatePath;
	}

	public void setMailTemplatePath(String mailTemplatePath) {
		this.mailTemplatePath = mailTemplatePath;
	}

	public List<String> getEmailList() {
		return emailList;
	}

	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
	}

	public String getContentOriginal() {
		return contentOriginal;
	}

	public void setContentOriginal(String contentOriginal) {
		this.contentOriginal = contentOriginal;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
