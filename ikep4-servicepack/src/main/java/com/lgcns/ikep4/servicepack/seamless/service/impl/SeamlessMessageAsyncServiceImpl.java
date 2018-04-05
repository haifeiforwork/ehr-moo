package com.lgcns.ikep4.servicepack.seamless.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailReadService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.servicepack.seamless.dao.ReceiveBoxDao;
import com.lgcns.ikep4.servicepack.seamless.dao.SeamlessMessageDao;
import com.lgcns.ikep4.servicepack.seamless.dao.SeamlessmessageUserSetingDao;
import com.lgcns.ikep4.servicepack.seamless.dao.SendBoxDao;
import com.lgcns.ikep4.servicepack.seamless.model.AttachFile;
import com.lgcns.ikep4.servicepack.seamless.model.MailSynchronize;
import com.lgcns.ikep4.servicepack.seamless.model.MessageBox;
import com.lgcns.ikep4.servicepack.seamless.model.Recipient;
import com.lgcns.ikep4.servicepack.seamless.service.SeamlessMessageAsyncService;
import com.lgcns.ikep4.servicepack.seamless.util.SeamlessMessageConstance;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

@Service
@Transactional
public class SeamlessMessageAsyncServiceImpl implements SeamlessMessageAsyncService {

	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private SeamlessMessageDao seamlessMessageDao;
	
	@Autowired
	private ReceiveBoxDao receiveBoxDao;
	
	@Autowired
	private SendBoxDao sendBoxDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private SeamlessmessageUserSetingDao seamlessmessageUserSetingDao;
	
	@Autowired
	private MailReadService mailReadService;
	
	@Async
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateSyncStart(String folderName, String registerId) {
		this.seamlessmessageUserSetingDao.updateSyncStart(folderName, registerId);
	}

	@Async
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void syncImapMail(String folderName, User user, Date fromDate, Date toDate, int size) {
		MailSynchronize mailSynchronize = new MailSynchronize();
		mailSynchronize.setFolderType(folderName);
		mailSynchronize.setMessageType(SeamlessMessageConstance.messageTypeMail);
		mailSynchronize.setOwnerId(user.getUserId());
		mailSynchronize.setRegistDate(toDate);
		try {
			List<Mail> mailList = mailReadService.getMailList(folderName, user, fromDate, toDate, size);
			
			this.createMail(mailList, user, folderName);
			
			if (mailList.size() > 0) { mailSynchronize.setRegistDate(mailList.get(0).getReceivedDate()); }
			mailSynchronize.setResult(1);
		} catch (Exception e) {
			mailSynchronize.setResult(0);
			mailSynchronize.setRegistDate(Calendar.getInstance().getTime());
			mailSynchronize.setResultDescription(e.getMessage());
			throw new IKEP4ApplicationException("", e);
		} finally {
			if(!fromDate.equals(mailSynchronize.getRegistDate())){
				this.seamlessMessageDao.createSynchronize(mailSynchronize);
			}
			this.seamlessmessageUserSetingDao.updateSyncEnd(folderName, user.getUserId());
		}
		
	}
	
	@Async
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void changeRead(String folderName, String mailId, User user){
		mailReadService.changeRead(folderName, mailId, user);
	}
	
	public void createMail(List<Mail> mailList, User user, String folderName) {
		for (int i = 0; i < mailList.size(); i++) {
			MessageBox messageBox = new MessageBox();
			String messageId = idgenService.getNextId();
			messageBox.setMessageId(messageId);
			messageBox.setMessageType(SeamlessMessageConstance.messageTypeMail);
			messageBox.setTitle(mailList.get(i).getTitle());
			messageBox.setMailUid(mailList.get(i).getMessageId());
			messageBox.setContents(mailList.get(i).getContent());
			
			String[] makeSender = mailList.get(i).getFromEmail().split("@");
			messageBox.setSenderId(makeSender[0]);
			messageBox.setSenderName(mailList.get(i).getFromName());
			messageBox.setSendDate(mailList.get(i).getSendDate());
			messageBox.setSenderMail(mailList.get(i).getFromEmail());
			messageBox.setIsRead(0);
			if(mailList.get(i).getReadYn() != null && mailList.get(i).getReadYn().equals("Y")) {
				messageBox.setIsRead(1);
			} 
			messageBox.setIsInternalMessage(0);
			messageBox.setOwnerId(user.getUserId());
			List<Recipient> recipientList = new ArrayList<Recipient>();
			for (int j = 0; j < mailList.get(i).getToEmailList().size(); j++) {
				Recipient recipientTo = new Recipient();
				recipientTo.setMessageId(messageId);
				String[] receiveList = mailList.get(i).getToEmailList().get(j).get("email").toString().split("@");
				recipientTo.setReceiverId(receiveList[0]);
				if(mailList.get(i).getToEmailList().get(j).get("email").toString().equals(user.getMail())) {
					recipientTo.setReceiverId(user.getUserId());
				}
				recipientTo.setReceiverName(mailList.get(i).getToEmailList().get(j).get("name").toString());
				recipientTo.setReceiverMail(mailList.get(i).getToEmailList().get(j).get("email").toString());
				recipientTo.setReceiverType("O");
				recipientTo.setReceiveType("TO");
				recipientTo.setReceiveDate(mailList.get(i).getReceivedDate());
				recipientList.add(recipientTo);
			}
			if (mailList.get(i).getCcEmailList() != null) {
				for (int j = 0; j < mailList.get(i).getCcEmailList().size(); j++) {
					Recipient recipientCc = new Recipient();
					recipientCc.setMessageId(messageId);
					String[] receiveList = mailList.get(i).getCcEmailList().get(j).get("email").toString().split("@");
					recipientCc.setReceiverId(receiveList[0]);
					if(mailList.get(i).getCcEmailList().get(j).get("email").toString().equals(user.getMail())) {
						recipientCc.setReceiverId(user.getUserId());
					}
					recipientCc.setReceiverName(mailList.get(i).getCcEmailList().get(j).get("name").toString());
					recipientCc.setReceiverMail(mailList.get(i).getCcEmailList().get(j).get("email").toString());
					recipientCc.setReceiverType("O");
					recipientCc.setReceiveType("CC");
					recipientCc.setReceiveDate(mailList.get(i).getReceivedDate());
					recipientList.add(recipientCc);
				}
			}
			if (mailList.get(i).getBccEmailList() != null) {
				for (int j = 0; j < mailList.get(i).getBccEmailList().size(); j++) {
					Recipient recipientBcc = new Recipient();
					recipientBcc.setMessageId(messageId);
					String[] receiveList = mailList.get(i).getBccEmailList().get(j).get("email").toString().split("@");
					recipientBcc.setReceiverId(receiveList[0]);
					if(mailList.get(i).getBccEmailList().get(j).get("email").toString().equals(user.getMail())) {
						recipientBcc.setReceiverId(user.getUserId());
					}
					recipientBcc.setReceiverName(mailList.get(i).getBccEmailList().get(j).get("name").toString());
					recipientBcc.setReceiverMail(mailList.get(i).getBccEmailList().get(j).get("email").toString());
					recipientBcc.setReceiverType("O");
					recipientBcc.setReceiveType("BCC");
					recipientBcc.setReceiveDate(mailList.get(i).getReceivedDate());
					recipientList.add(recipientBcc);
				}
			}
			List<AttachFile> attachFileList = new ArrayList<AttachFile>();
			if (mailList.get(i).getAttachList() != null) {
				messageBox.setAttachCount(mailList.get(i).getAttachList().size());
				for (int j = 0; j < mailList.get(i).getAttachList().size(); j++) {
					AttachFile attachFile = new AttachFile();
					attachFile.setMessageId(messageId);
					attachFile.setAttachId(j+"");
					attachFile.setAttachName(mailList.get(i).getAttachList().get(j).getName());
					attachFile.setAttachSize(mailList.get(i).getAttachList().get(j).getSize());
					attachFile.setMultipartPath(mailList.get(i).getAttachList().get(j).getMultipartPath());
					if (attachFile.getAttachName() != null) { attachFileList.add(attachFile); }
				}
			}
			messageBox.setReceiverCount(recipientList.size());
			if (folderName.equals(SeamlessMessageConstance.imapReceiveBox)){
				receiveBoxDao.create(messageBox);
				for (int k = 0; k < recipientList.size(); k++) {
					receiveBoxDao.createRecipient(recipientList.get(k));
				}
				for (int k = 0; k < attachFileList.size(); k++) {
					receiveBoxDao.createAttachFile(attachFileList.get(k));
				}
			} else if (folderName.equals(SeamlessMessageConstance.imapsendBox)){
				messageBox.setSenderId(user.getUserId());
				messageBox.setSenderName(user.getUserName());
				sendBoxDao.create(messageBox);
				for (int k = 0; k < recipientList.size(); k++) {
					sendBoxDao.createRecipient(recipientList.get(k));
				}
				for (int k = 0; k < attachFileList.size(); k++) {
					sendBoxDao.createAttachFile(attachFileList.get(k));
				}
			}
		}
	}
}
