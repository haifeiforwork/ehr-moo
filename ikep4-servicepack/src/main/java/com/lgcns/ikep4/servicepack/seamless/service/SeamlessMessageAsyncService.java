package com.lgcns.ikep4.servicepack.seamless.service;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.user.member.model.User;

public interface SeamlessMessageAsyncService {

	public void updateSyncStart(String folderName, String registerId);
	
	public void syncImapMail(String folderName, User user, Date fromDate, Date toDate, int size);
	
	public void createMail(List<Mail> mailList, User user, String folderName);
	
	public void changeRead(String folderName, String mailId, User user);
	
}
