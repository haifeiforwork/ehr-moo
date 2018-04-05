package com.lgcns.ikep4.support.note.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="NoteDetail")
public class NoteViewReturnData0 {

	private String folderId;
	private String folderName;
	private String noteId;
	private String noteTitle;
	private String noteContent;
	private String isImportant;
	private String regUserId;
	private String regUserName;
	private String regUserDept;
	private String regUserTitle;
	private String regDate;
		
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public String getIsImportant() {
		return isImportant;
	}
	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getRegUserName() {
		return regUserName;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	public String getRegUserDept() {
		return regUserDept;
	}
	public void setRegUserDept(String regUserDept) {
		this.regUserDept = regUserDept;
	}
	public String getRegUserTitle() {
		return regUserTitle;
	}
	public void setRegUserTitle(String regUserTitle) {
		this.regUserTitle = regUserTitle;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
}