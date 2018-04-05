package com.lgcns.ikep4.servicepack.seamless.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.mail.base.MailException;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailReadService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.servicepack.seamless.dao.ReceiveBoxDao;
import com.lgcns.ikep4.servicepack.seamless.dao.SeamlessMessageDao;
import com.lgcns.ikep4.servicepack.seamless.dao.SeamlessmessageUserSetingDao;
import com.lgcns.ikep4.servicepack.seamless.dao.SendBoxDao;
import com.lgcns.ikep4.servicepack.seamless.model.AttachFile;
import com.lgcns.ikep4.servicepack.seamless.model.ContacUser;
import com.lgcns.ikep4.servicepack.seamless.model.MailSynchronize;
import com.lgcns.ikep4.servicepack.seamless.model.MessageBox;
import com.lgcns.ikep4.servicepack.seamless.model.Recipient;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageUserSeting;
import com.lgcns.ikep4.servicepack.seamless.search.SeamlessmessageSearchCondition;
import com.lgcns.ikep4.servicepack.seamless.service.SeamlessMessageAsyncService;
import com.lgcns.ikep4.servicepack.seamless.service.SeamlessMessageService;
import com.lgcns.ikep4.servicepack.seamless.util.SeamlessMessageConstance;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

@Service
@Transactional
public class SeamlessMessageServiceImpl extends GenericServiceImpl<MessageBox,String> implements SeamlessMessageService {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private SeamlessMessageDao seamlessMessageDao;
	
	@Autowired
	private ReceiveBoxDao receiveBoxDao;
	
	@Autowired
	private SendBoxDao sendBoxDao;
	
	@Autowired
	private SeamlessmessageUserSetingDao seamlessmessageUserSetingDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private MailReadService mailReadService;
	
	@Autowired
	private SeamlessMessageAsyncService seamlessMessageAsyncService;
	
	@Autowired
	public SeamlessMessageServiceImpl (SeamlessMessageDao dao){
		super(dao);
		this.seamlessMessageDao = dao;
	}
	
	public MessageBox getReceiveBox (SeamlessmessageSearchCondition seamlessmessageSearchCondition) {
		MessageBox messageBox = receiveBoxDao.get(seamlessmessageSearchCondition);
		if (messageBox != null) {
			messageBox.setAttachFileList(receiveBoxDao.getAttachFileList(seamlessmessageSearchCondition.getMessageId()));
			messageBox.setRecipientList(receiveBoxDao.getRecipientList(seamlessmessageSearchCondition.getMessageId()));
		}
		return messageBox;
	}
	
	public void removeReceiveBox (SeamlessmessageSearchCondition seamlessmessageSearchCondition) {
		receiveBoxDao.removeAttachFile(seamlessmessageSearchCondition.getMessageId());
		receiveBoxDao.removeRecipient(seamlessmessageSearchCondition.getMessageId());
		receiveBoxDao.remove(seamlessmessageSearchCondition);
	}
	
	public void updateIsRead (String messageId, String userId) {
		MessageBox messageBox = new MessageBox();
		messageBox.setMessageId(messageId);
		messageBox.setOwnerId(userId);
		messageBox.setIsRead(1);
		receiveBoxDao.update(messageBox);
	}
	
	public MessageBox getSendBox (SeamlessmessageSearchCondition seamlessmessageSearchCondition) {
		MessageBox messageBox = sendBoxDao.get(seamlessmessageSearchCondition);
		if (messageBox != null) {
			messageBox.setAttachFileList(sendBoxDao.getAttachFileList(seamlessmessageSearchCondition.getMessageId()));
			messageBox.setRecipientList(sendBoxDao.getRecipientList(seamlessmessageSearchCondition.getMessageId()));
		}
		return messageBox;
	}
	
	public void removeSendBox (SeamlessmessageSearchCondition seamlessmessageSearchCondition) {
		sendBoxDao.removeAttachFile(seamlessmessageSearchCondition.getMessageId());
		sendBoxDao.removeRecipient(seamlessmessageSearchCondition.getMessageId());
		sendBoxDao.remove(seamlessmessageSearchCondition);
	}

	public List<MessageBox> receiveList(SeamlessmessageSearchCondition searchCondition) {
		return (List<MessageBox>) this.seamlessMessageDao.receiveList(searchCondition);
	}
	
	public List<MessageBox> sendList(SeamlessmessageSearchCondition searchCondition) {
		return (List<MessageBox>) this.seamlessMessageDao.sendList(searchCondition);
	}
	
	public List<MessageBox> contactHistoryList(SeamlessmessageSearchCondition searchCondition) {
		return (List<MessageBox>) this.seamlessMessageDao.contactHistoryList(searchCondition);
	}
	
	public Date lastContactDate (SeamlessmessageSearchCondition searchCondition) {
		return (Date) this.seamlessMessageDao.lastContactDate(searchCondition);
	}
	
	public List<ContacUser> contactUserList (SeamlessmessageSearchCondition searchCondition) {
		return (List<ContacUser>) this.seamlessMessageDao.contactUserList(searchCondition);
	}
	
	public String contackImapMail (String folderName, User user, int size) {
		String resultMsg = "OK";
		MailSynchronize mailSynchronize = new MailSynchronize();
		mailSynchronize.setFolderType(folderName);
		mailSynchronize.setMessageType(SeamlessMessageConstance.messageTypeMail);
		mailSynchronize.setOwnerId(user.getUserId());
		Date fromDate = this.seamlessMessageDao.getSyncDate(mailSynchronize);
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.AM_PM, Calendar.AM);
		c.add(Calendar.DATE, 1);	
		
		Date toDate = c.getTime();
		
		mailSynchronize.setRegistDate(toDate);
		try {
			int mailCount = mailReadService.getMailListNum(folderName, user, fromDate, toDate);
			
			this.seamlessMessageAsyncService.updateSyncStart(folderName, user.getUserId());
			if (mailCount <= 10) {
				try {
					List<Mail> mailList = mailReadService.getMailList(folderName, user, fromDate, toDate, size);	
					this.createMail(mailList, user, folderName);
					mailSynchronize.setRegistDate(Calendar.getInstance().getTime());
					if (mailList.size() > 0) { mailSynchronize.setRegistDate(mailList.get(0).getReceivedDate()); }
					mailSynchronize.setResult(1);
				} catch (Exception e){
				} finally {
					this.seamlessmessageUserSetingDao.updateSyncEnd(folderName, user.getUserId());
				}
			} else {
				this.seamlessMessageAsyncService.syncImapMail(folderName, user, fromDate, toDate, size);
				mailSynchronize.setResult(0);
				mailSynchronize.setRegistDate(Calendar.getInstance().getTime());
				mailSynchronize.setResultDescription("async Service call");
				resultMsg = "ASYNC";
			}
		} catch (MailException e) {
			mailSynchronize.setResult(0);
			mailSynchronize.setRegistDate(Calendar.getInstance().getTime());
			mailSynchronize.setResultDescription("MailException");
			resultMsg = "MailError";
		} catch (MessagingException e) {
			mailSynchronize.setResult(0);
			mailSynchronize.setRegistDate(Calendar.getInstance().getTime());
			mailSynchronize.setResultDescription("MessagingException");
			resultMsg = "MessagingError";
		} catch (Exception e) {
			mailSynchronize.setResult(0);
			mailSynchronize.setRegistDate(Calendar.getInstance().getTime());
			mailSynchronize.setResultDescription(e.getMessage());
			resultMsg = e.getMessage();
			throw new IKEP4ApplicationException("", e);
		} finally {
			if(!fromDate.equals(mailSynchronize.getRegistDate())){
				this.seamlessMessageDao.createSynchronize(mailSynchronize);
			}
		}
		return resultMsg;
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

	public SeamlessmessageUserSeting getUserSeting(String registerId) {
		SeamlessmessageUserSeting seamlessmessageUserSeting = this.seamlessmessageUserSetingDao.get(registerId);
		if (seamlessmessageUserSeting == null) {
			SeamlessmessageUserSeting makeUserSeting = new SeamlessmessageUserSeting();
			makeUserSeting.setRegisterId(registerId);
			makeUserSeting.setAutoConnect(0);
			makeUserSeting.setIsSourceDelete(0);
			makeUserSeting.setInboxSyncComplete(0);
			makeUserSeting.setSentSyncComplete(0);
			seamlessmessageUserSeting = makeUserSeting;
		}
		return seamlessmessageUserSeting;
	}

	public void updateUserSeting(SeamlessmessageUserSeting seamlessmessageUserSeting) {
		if (this.seamlessmessageUserSetingDao.exists(seamlessmessageUserSeting.getRegisterId()) ) {
			this.seamlessmessageUserSetingDao.update(seamlessmessageUserSeting);
		} else {
			this.seamlessmessageUserSetingDao.create(seamlessmessageUserSeting);
		}
	}

}
