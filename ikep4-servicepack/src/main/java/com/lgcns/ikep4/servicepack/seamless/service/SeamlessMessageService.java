package com.lgcns.ikep4.servicepack.seamless.service;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.servicepack.seamless.model.ContacUser;
import com.lgcns.ikep4.servicepack.seamless.model.MessageBox;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageUserSeting;
import com.lgcns.ikep4.servicepack.seamless.search.SeamlessmessageSearchCondition;

public interface SeamlessMessageService extends GenericService<MessageBox,String> {

	public MessageBox getReceiveBox (SeamlessmessageSearchCondition seamlessmessageSearchCondition);
	
	public void removeReceiveBox (SeamlessmessageSearchCondition seamlessmessageSearchCondition);
	
	public void updateIsRead (String messageId, String userId);
	
	public MessageBox getSendBox (SeamlessmessageSearchCondition seamlessmessageSearchCondition) ;
	
	public void removeSendBox (SeamlessmessageSearchCondition seamlessmessageSearchCondition) ;
	
	public List<MessageBox> receiveList (SeamlessmessageSearchCondition searchCondition) ;
	
	public List<MessageBox> sendList (SeamlessmessageSearchCondition searchCondition) ;
	
	public List<MessageBox> contactHistoryList(SeamlessmessageSearchCondition searchCondition) ;
	
	public Date lastContactDate (SeamlessmessageSearchCondition searchCondition) ;
	
	public List<ContacUser> contactUserList (SeamlessmessageSearchCondition searchCondition);
	
	public String contackImapMail (String folderName, User user, int size);
	
	public void createMail(List<Mail> mailList, User user, String folderName);
	
	public SeamlessmessageUserSeting getUserSeting(String registerId);
	
	public void updateUserSeting(SeamlessmessageUserSeting seamlessmessageUserSeting);
}
