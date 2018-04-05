package com.lgcns.ikep4.portal.login.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData")
public class MailSettingInfoReturnData {

	private String mailAccountName;
	private String mailAddress;
	private String mailID;
	private String mailPassword;
	private String receiveServerType;
	private String receiveServerAddress;
	private String receiveServerPort;
	private String receiveServerSecurity;
	private String sendServerAddress;
	private String sendServerPort;
	private String sendServerSecurity;
	private String sendServerAuth;
	private String sendOutgoingId;
	private String sendOutgoingPw;
	
	public String getMailAccountName() {
		return mailAccountName;
	}
	public void setMailAccountName(String mailAccountName) {
		this.mailAccountName = mailAccountName;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getMailID() {
		return mailID;
	}
	public void setMailID(String mailID) {
		this.mailID = mailID;
	}
	public String getMailPassword() {
		return mailPassword;
	}
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	public String getReceiveServerType() {
		return receiveServerType;
	}
	public void setReceiveServerType(String receiveServerType) {
		this.receiveServerType = receiveServerType;
	}
	public String getReceiveServerAddress() {
		return receiveServerAddress;
	}
	public void setReceiveServerAddress(String receiveServerAddress) {
		this.receiveServerAddress = receiveServerAddress;
	}
	public String getReceiveServerPort() {
		return receiveServerPort;
	}
	public void setReceiveServerPort(String receiveServerPort) {
		this.receiveServerPort = receiveServerPort;
	}
	public String getReceiveServerSecurity() {
		return receiveServerSecurity;
	}
	public void setReceiveServerSecurity(String receiveServerSecurity) {
		this.receiveServerSecurity = receiveServerSecurity;
	}
	public String getSendServerAddress() {
		return sendServerAddress;
	}
	public void setSendServerAddress(String sendServerAddress) {
		this.sendServerAddress = sendServerAddress;
	}
	public String getSendServerPort() {
		return sendServerPort;
	}
	public void setSendServerPort(String sendServerPort) {
		this.sendServerPort = sendServerPort;
	}
	public String getSendServerSecurity() {
		return sendServerSecurity;
	}
	public void setSendServerSecurity(String sendServerSecurity) {
		this.sendServerSecurity = sendServerSecurity;
	}
	public String getSendServerAuth() {
		return sendServerAuth;
	}
	public void setSendServerAuth(String sendServerAuth) {
		this.sendServerAuth = sendServerAuth;
	}
	public String getSendOutgoingId() {
		return sendOutgoingId;
	}
	public void setSendOutgoingId(String sendOutgoingId) {
		this.sendOutgoingId = sendOutgoingId;
	}
	public String getSendOutgoingPw() {
		return sendOutgoingPw;
	}
	public void setSendOutgoingPw(String sendOutgoingPw) {
		this.sendOutgoingPw = sendOutgoingPw;
	}
	
}