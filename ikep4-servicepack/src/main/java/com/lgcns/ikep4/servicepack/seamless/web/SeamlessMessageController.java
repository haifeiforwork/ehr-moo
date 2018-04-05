package com.lgcns.ikep4.servicepack.seamless.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.servicepack.seamless.model.ContacUser;
import com.lgcns.ikep4.servicepack.seamless.model.MessageBox;
import com.lgcns.ikep4.servicepack.seamless.model.Recipient;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageAdmin;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageCode;
import com.lgcns.ikep4.servicepack.seamless.model.SeamlessmessageUserSeting;
import com.lgcns.ikep4.servicepack.seamless.search.SeamlessmessageSearchCondition;
import com.lgcns.ikep4.servicepack.seamless.service.SeamlessMessageAsyncService;
import com.lgcns.ikep4.servicepack.seamless.service.SeamlessMessageService;
import com.lgcns.ikep4.servicepack.seamless.service.SeamlessmessageAdminService;
import com.lgcns.ikep4.servicepack.seamless.util.SeamlessMessageConstance;
import com.lgcns.ikep4.servicepack.seamless.util.SeamlessMessageUtil;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.model.MailAttach;
import com.lgcns.ikep4.support.mail.service.MailReadService;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.model.MessageReceive;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.message.service.MessageService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: SeamlessMessageController.java 16925 2011-10-28 06:19:28Z giljae $
 */
@Controller
@RequestMapping(value = "/servicepack/seamless")
public class SeamlessMessageController extends BaseController {
	
	@Autowired
	private SeamlessMessageService seamlessMessageService;
	
	@Autowired
	private SeamlessmessageAdminService seamlessmessageAdminService;
	
	@Autowired
	private SeamlessMessageAsyncService seamlessMessageAsyncService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private MailReadService mailReadService;
	
	@Autowired
	private MessageOutsideService messageOutsideService;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
	/**TODO 통합메세지 > 통합 수신함 Controller
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/receiveListView")
	public ModelAndView receiveListView(SeamlessmessageSearchCondition searchCondition, BindingResult result, SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();  
		
		User user = (User) getRequestAttribute("ikep.user");
		SeamlessmessageAdmin seamlessmessageAdmin = this.seamlessmessageAdminService.read(user.getPortalId());
		int size = 0;
		if (seamlessmessageAdmin.getMaxImapCount() != null) { size = seamlessmessageAdmin.getMaxImapCount(); }
		SeamlessmessageUserSeting seamlessmessageUserSeting = this.seamlessMessageService.getUserSeting(user.getUserId());
		//IMAP 가져오기
		if(seamlessmessageUserSeting.getAutoConnect() == 1 && seamlessmessageUserSeting.getInboxSyncComplete() == 0) {
			try {
				this.seamlessMessageService.contackImapMail(SeamlessMessageConstance.imapReceiveBox,user, size);
			} catch (Exception e) {
			}
		}
		if (searchCondition.getUserId() == null) { searchCondition.setUserId(user.getUserId()); }
		List<MessageBox> receiveList = this.seamlessMessageService.receiveList(searchCondition);
		for (int i = 0; i < receiveList.size(); i++) {
			String removeHtml = SeamlessMessageUtil.getText(receiveList.get(i).getContents(),1);
			if (receiveList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeMail)) {
				receiveList.get(i).setContents(messageTitleMaker("["+receiveList.get(i).getTitle() +"] " +removeHtml,SeamlessMessageConstance.titleWordNum));
			} else {
				receiveList.get(i).setContents(messageTitleMaker(removeHtml,SeamlessMessageConstance.titleWordNum));
			}
		}
		String adminYn = checkSystemAdmin(user);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		String lastDate = "";
		if (receiveList.size() > 0) { lastDate = dateFormat.format(receiveList.get(receiveList.size()-1).getSendDate()); }
		
		modelAndView.setViewName("servicepack/seamless/receiveListView");
		modelAndView.addObject("receiveList", receiveList);
		modelAndView.addObject("receiveListSize", receiveList.size());
		modelAndView.addObject("mcontacUserList", contactHistoryMenu(user));
		modelAndView.addObject("lastDate", lastDate);
		modelAndView.addObject("ikepUser", user);
		modelAndView.addObject("seamlessmessageUserSeting", seamlessmessageUserSeting);
		modelAndView.addObject("searchCondition", searchCondition);
		modelAndView.addObject("viewCode", "R");
		modelAndView.addObject("messageType", searchCondition.getMessageType());
		modelAndView.addObject("adminYn", adminYn);
		return modelAndView;
	}
	
	/**TODO 수신함 LIST 삭제 Controller
	 * @param searchCondition
	 * @param checkboxMessageItem
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteReceiveBoxList")
	public ModelAndView deleteReceiveBoxList(SeamlessmessageSearchCondition searchCondition, @RequestParam("checkboxMessageItem") String[] checkboxMessageItem, SessionStatus status) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		List<Message> messageList = new ArrayList<Message>();
		SeamlessmessageUserSeting seamlessmessageUserSeting = this.seamlessMessageService.getUserSeting(user.getUserId());
		for (int i = 0; i < checkboxMessageItem.length; i++) {
			String[] arrayReceiveUser = checkboxMessageItem[i].split("/");
			if (arrayReceiveUser[0].equals(SeamlessMessageConstance.messageTypeMessage)) {
				Message message = new Message();
				message.setMessageId(arrayReceiveUser[1]);
				message.setUserId(user.getUserId());
				messageList.add(message);
			} else if (arrayReceiveUser[0].equals(SeamlessMessageConstance.messageTypeMail)) {
				SeamlessmessageSearchCondition removeSearchCondition = new SeamlessmessageSearchCondition();
				removeSearchCondition.setMessageId(arrayReceiveUser[1]);
				removeSearchCondition.setUserId(user.getUserId());
				if (seamlessmessageUserSeting != null && seamlessmessageUserSeting.getIsSourceDelete() != null && seamlessmessageUserSeting.getIsSourceDelete() == 1) {
					MessageBox messageBox = this.seamlessMessageService.getReceiveBox(removeSearchCondition);
					if (messageBox.getMailUid() != null && !messageBox.getMailUid().equals("")) {
						try {
							this.mailReadService.deleteMail("uid", SeamlessMessageConstance.imapReceiveBox, messageBox.getMailUid(), user);
						} catch (Exception e) {
						}
					}
				}
				this.seamlessMessageService.removeReceiveBox(removeSearchCondition);
			}
		}
		if (messageList.size() > 0) { this.messageService.deleteReceiveMessage(messageList); }
		
		return new ModelAndView("redirect:/servicepack/seamless/receiveListView.do");
	}
	
	/**TODO receiveList 추가 보기 AJAX Controller
	 * @param searchColumn, searchWord, lastDate
	 * @return receiveList
	 */
	@RequestMapping(value = "receiveList")  
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<MessageBox> receiveList(@RequestParam(value="searchColumn", required=false, defaultValue="") String searchColumn
			, @RequestParam(value="searchWord", required=false, defaultValue="") String searchWord
			, @RequestParam(value="messageType", required=false, defaultValue="") String messageType
			, @RequestParam(value="lastDate", required=false, defaultValue="") String lastDate ) {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setUserId(user.getUserId());
		searchCondition.setSearchColumn(searchColumn);
		searchCondition.setSearchWord(searchWord);
		searchCondition.setMessageType(messageType);
		searchCondition.setSendDate(lastDate);
		
		List<MessageBox> receiveList = this.seamlessMessageService.receiveList(searchCondition);
		for (int i = 0; i < receiveList.size(); i++) {
			String removeHtml = SeamlessMessageUtil.getText(receiveList.get(i).getContents(),1);
			if (receiveList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeMail)) {
				receiveList.get(i).setContents(messageTitleMaker("["+receiveList.get(i).getTitle() +"] " +removeHtml,SeamlessMessageConstance.titleWordNum));
			} else {
				receiveList.get(i).setContents(messageTitleMaker(removeHtml,SeamlessMessageConstance.titleWordNum));
			}
			receiveList.get(i).setSendDateOrg(receiveList.get(i).getSendDate());
			receiveList.get(i).setSendDate(timeZoneSupportService.convertTimeZone(receiveList.get(i).getSendDate()));
		}
		return receiveList;
	}
	
	/**TODO 통합메세지 > 통합 발신함 Controller
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/sendListView")
	public ModelAndView sendListView(SeamlessmessageSearchCondition searchCondition, BindingResult result, SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();  
		
		User user = (User) getRequestAttribute("ikep.user");
		SeamlessmessageAdmin seamlessmessageAdmin = this.seamlessmessageAdminService.read(user.getPortalId());
		int size = 0;
		if (seamlessmessageAdmin.getMaxImapCount() != null) { size = seamlessmessageAdmin.getMaxImapCount(); }
		SeamlessmessageUserSeting seamlessmessageUserSeting = this.seamlessMessageService.getUserSeting(user.getUserId());
		//IMAP 가져오기
		if(seamlessmessageUserSeting.getAutoConnect() == 1 && seamlessmessageUserSeting.getSentSyncComplete() == 0) {
			try {
				this.seamlessMessageService.contackImapMail(SeamlessMessageConstance.imapsendBox,user, size);
			} catch (Exception e) {
			}
		}
		if (searchCondition.getUserId() == null) { searchCondition.setUserId(user.getUserId()); }
		List<MessageBox> sendList = this.seamlessMessageService.sendList(searchCondition);
		for (int i = 0; i < sendList.size(); i++) {
			String removeHtml = "";
			if (sendList.get(i).getContents() != null) { removeHtml = SeamlessMessageUtil.getText(sendList.get(i).getContents(),1); }
			if (sendList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeMail)) {
				sendList.get(i).setContents(messageTitleMaker("["+sendList.get(i).getTitle() +"] " +removeHtml,SeamlessMessageConstance.titleWordNum));
			} else if (sendList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeMessage)) {
				sendList.get(i).setContents(messageTitleMaker(removeHtml,SeamlessMessageConstance.titleWordNum));
			} else if (sendList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeSMS)) {
				sendList.get(i).setContents(sendList.get(i).getTitle());
			}
		}
		String adminYn = checkSystemAdmin(user);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		String lastDate = "";
		if (sendList.size() > 0) { lastDate = dateFormat.format(sendList.get(sendList.size()-1).getSendDate()); }
		
		modelAndView.setViewName("servicepack/seamless/sendListView");
		modelAndView.addObject("sendList", sendList);
		modelAndView.addObject("sendListSize", sendList.size());
		modelAndView.addObject("mcontacUserList", contactHistoryMenu(user));
		modelAndView.addObject("lastDate", lastDate);
		modelAndView.addObject("ikepUser", user);
		modelAndView.addObject("seamlessmessageUserSeting", seamlessmessageUserSeting);
		modelAndView.addObject("searchCondition", searchCondition);
		modelAndView.addObject("messageType", searchCondition.getMessageType());
		modelAndView.addObject("viewCode", "S");
		modelAndView.addObject("adminYn", adminYn);
		return modelAndView;
	}
	
	/**TODO 발신함 LIST 삭제 Controller
	 * @param searchCondition
	 * @param checkboxMessageItem
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteSendBoxList")
	public ModelAndView deleteSendBoxList(SeamlessmessageSearchCondition searchCondition, @RequestParam("checkboxMessageItem") String[] checkboxMessageItem, SessionStatus status) {
		
		User user = (User) getRequestAttribute("ikep.user");
		SeamlessmessageUserSeting seamlessmessageUserSeting = this.seamlessMessageService.getUserSeting(user.getUserId());
		List<Message> messageList = new ArrayList<Message>();
		for (int i = 0; i < checkboxMessageItem.length; i++) {
			String[] arrayReceiveUser = checkboxMessageItem[i].split("/");
			if (arrayReceiveUser[0].equals(SeamlessMessageConstance.messageTypeMessage)) {
				Message message = new Message();
				message.setMessageId(arrayReceiveUser[1]);
				message.setUserId(user.getUserId());
				messageList.add(message);
			} else if (arrayReceiveUser[0].equals(SeamlessMessageConstance.messageTypeMail)) {
				SeamlessmessageSearchCondition removeSearchCondition = new SeamlessmessageSearchCondition();
				removeSearchCondition.setMessageId(arrayReceiveUser[1]);
				removeSearchCondition.setUserId(user.getUserId());
				if (seamlessmessageUserSeting != null && seamlessmessageUserSeting.getIsSourceDelete() != null && seamlessmessageUserSeting.getIsSourceDelete() == 1) {
					MessageBox messageBox = this.seamlessMessageService.getSendBox(removeSearchCondition);
					if (messageBox.getMailUid() != null && !messageBox.getMailUid().equals("")) {
						try{
							this.mailReadService.deleteMail("uid", SeamlessMessageConstance.imapsendBox, messageBox.getMailUid(), user);
						} catch (Exception e) {
						}
					}
				}
				this.seamlessMessageService.removeSendBox(removeSearchCondition);
			} else if (arrayReceiveUser[0].equals(SeamlessMessageConstance.messageTypeSMS)) {
				this.smsService.delete(arrayReceiveUser[1]);
			}
		}
		this.messageService.deleteSendMessage(messageList);
		
		return new ModelAndView("redirect:/servicepack/seamless/sendListView.do");
	}
	
	/**TODO sendList 추가 보기 AJAX Controller
	 * @param searchColumn, searchWord, lastDate
	 * @return sendList
	 */
	@RequestMapping(value = "sendList")  
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<MessageBox> sendList(@RequestParam(value="searchColumn", required=false, defaultValue="") String searchColumn
			, @RequestParam(value="searchWord", required=false, defaultValue="") String searchWord
			, @RequestParam(value="messageType", required=false, defaultValue="") String messageType
			, @RequestParam(value="lastDate", required=false, defaultValue="") String lastDate ) {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setUserId(user.getUserId());
		searchCondition.setSearchColumn(searchColumn);
		searchCondition.setSearchWord(searchWord);
		searchCondition.setMessageType(messageType);
		searchCondition.setSendDate(lastDate);
		
		List<MessageBox> sendList = this.seamlessMessageService.sendList(searchCondition);
		for (int i = 0; i < sendList.size(); i++) {
			String removeHtml = "";
			if (sendList.get(i).getContents() != null) { removeHtml = SeamlessMessageUtil.getText(sendList.get(i).getContents(),1); }
			if (sendList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeMail)) {
				sendList.get(i).setContents(messageTitleMaker("["+sendList.get(i).getTitle() +"] " +removeHtml,SeamlessMessageConstance.titleWordNum));
			} else if (sendList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeMessage)) {
				sendList.get(i).setContents(messageTitleMaker(removeHtml,SeamlessMessageConstance.titleWordNum));
			} else if (sendList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeSMS)) {
				sendList.get(i).setContents(sendList.get(i).getTitle());
			}
			sendList.get(i).setSendDateOrg(sendList.get(i).getSendDate());
			sendList.get(i).setSendDate(timeZoneSupportService.convertTimeZone(sendList.get(i).getSendDate()));
		}
		return sendList;
	}
	
	/**TODO messageView 읽은 메세지 보기
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "messageView")  
	public ModelAndView messageView(@RequestParam("messageType") String messageType
			, @RequestParam("messageId") String messageId
			, @RequestParam("boxType") String boxType) {
		ModelAndView modelAndView = new ModelAndView(); 
		MessageBox messageBox = new MessageBox();
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setMessageId(messageId);
		searchCondition.setUserId(user.getUserId());
		User senderUser = new User();
		
		if (messageType.equals("Message")) {
			Message message = this.messageService.read(messageId);
			senderUser = userService.read(message.getSenderId());
			messageBox.setMessageId(messageId);
			messageBox.setSenderId(message.getSenderId());
			messageBox.setSenderName(message.getSenderName());
			messageBox.setSenderEngName(senderUser.getUserEnglishName());
			messageBox.setSenderMail(senderUser.getMail());
			messageBox.setSenderJobTitleName(senderUser.getJobTitleName());
			messageBox.setSenderJobTitleEngName(senderUser.getJobTitleEnglishName());
			messageBox.setSenderTeamName(senderUser.getTeamName());
			messageBox.setSenderTeamEngName(senderUser.getTeamEnglishName());
			messageBox.setSendDate(message.getSendDate());
			messageBox.setReceiverCount(message.getReceiverCount());
			messageBox.setMessageType(messageType);
			messageBox.setAttachCount(message.getAttachCount());
			messageBox.setPicturePath(senderUser.getPicturePath());
			messageBox.setProfilePicturePath(senderUser.getProfilePicturePath());
			
			String[] arrayReceive = message.getReceiverList().split(",");
			String[] arrayReceiveUser = arrayReceive[0].split("/");
			User receiveUser = userService.read(arrayReceiveUser[0]);
			if(receiveUser != null){
				messageBox.setReceiverName(receiveUser.getUserName());
				messageBox.setReceiverEngName(receiveUser.getUserEnglishName());
			} else if(arrayReceiveUser.length >= 2) {
				messageBox.setReceiverName(arrayReceiveUser[1]);
				messageBox.setReceiverEngName(arrayReceiveUser[1]);
			}
			if (boxType.equals("R")){
				//messageBox.setReceiverName(user.getUserName());
				this.messageService.insertIsRead(messageId, user.getUserId()); 
			} 
			
			messageBox.setFileDataList(message.getFileDataList());
			messageBox.setContents(message.getContents().replace("\n", "<br>"));
			
			List<Recipient> recipientList = new ArrayList<Recipient>();
			
			for(String receive : arrayReceive){
				String[] receives = receive.split("/");
				
				User userInfo = userService.read(receives[0].trim());
				
				if(userInfo != null){
					Recipient recipient = new Recipient();
					recipient.setReceiverName(userInfo.getUserName());
					recipient.setReceiveType("Message");
					recipient.setReceiverMail(userInfo.getMail());
					
					recipientList.add(recipient);
				}
			}
			
			messageBox.setRecipientList(recipientList);
			
		} else if (messageType.equals("Mail")) {
			if (boxType.equals("R")){
				messageBox = this.seamlessMessageService.getReceiveBox(searchCondition);
				messageBox.setReceiverName(user.getUserName());
				this.seamlessMessageService.updateIsRead(messageId, user.getUserId()); 
				if (messageBox.getMailUid() != null && !messageBox.getMailUid().equals("")) {
					this.seamlessMessageAsyncService.changeRead(SeamlessMessageConstance.imapReceiveBox, messageBox.getMailUid(), user);
				}
				
				senderUser = userService.read(messageBox.getSenderId());
				messageBox.setSenderName(senderUser.getUserName());
				messageBox.setSenderEngName(senderUser.getUserEnglishName());
				messageBox.setSenderJobTitleName(senderUser.getJobTitleName());
				messageBox.setSenderJobTitleEngName(senderUser.getJobTitleEnglishName());
				messageBox.setSenderTeamName(senderUser.getTeamName());
				messageBox.setSenderTeamEngName(senderUser.getTeamEnglishName());
				messageBox.setPicturePath(senderUser.getPicturePath());
				messageBox.setProfilePicturePath(senderUser.getProfilePicturePath());
				
				messageBox.setReceiverName(user.getUserName());
				messageBox.setReceiverEngName(user.getUserEnglishName());
			} else {
				messageBox = this.seamlessMessageService.getSendBox(searchCondition);
				messageBox.setReceiverName(messageBox.getRecipientList().get(0).getReceiverName());
				messageBox.setReceiverMail(messageBox.getRecipientList().get(0).getReceiverMail());
				
				senderUser = userService.read(messageBox.getRecipientList().get(0).getReceiverId());
				messageBox.setReceiverName(senderUser.getUserName());
				messageBox.setReceiverEngName(senderUser.getUserEnglishName());
				messageBox.setReceiverJobTitleName(senderUser.getJobTitleName());
				messageBox.setReceiverJobTitleEngName(senderUser.getJobTitleEnglishName());
				messageBox.setReceiverTeamName(senderUser.getTeamName());
				messageBox.setReceiverTeamEngName(senderUser.getTeamEnglishName());
				messageBox.setPicturePath(senderUser.getPicturePath());
				messageBox.setProfilePicturePath(senderUser.getProfilePicturePath());
				
			}
		} else if (messageType.equals("SMS")) {
			Sms sms = this.smsService.read(messageId);
			senderUser = userService.read(sms.getRegisterId());
			messageBox.setMessageId(messageId);
			messageBox.setSenderId(sms.getRegisterId());
			messageBox.setSenderName(senderUser.getUserName());
			messageBox.setSenderEngName(senderUser.getUserEnglishName());
			messageBox.setSenderMail(senderUser.getMail());
			messageBox.setSenderJobTitleName(senderUser.getJobTitleName());
			messageBox.setSenderJobTitleEngName(senderUser.getJobTitleEnglishName());
			messageBox.setSenderTeamName(senderUser.getTeamName());
			messageBox.setSenderTeamEngName(senderUser.getTeamEnglishName());
			messageBox.setSendDate(sms.getRegistDate());
			messageBox.setReceiverName(sms.getReceiverName());
			messageBox.setReceiverEngName(sms.getReceiverEnglishName());
			messageBox.setContents(sms.getContents());
			messageBox.setMessageType(messageType);
			messageBox.setPicturePath(senderUser.getPicturePath());
			messageBox.setProfilePicturePath(senderUser.getProfilePicturePath());
		}
		
		
		modelAndView.setViewName("servicepack/seamless/messageView");
		modelAndView.addObject("messageBox", messageBox);
		modelAndView.addObject("boxType", boxType);
		return modelAndView;
	}
	
	/**
	 * 메일 첨부파일 다운로드
	 * 
	 * @param folderName
	 * @param mailId
	 * @param multipartPath
	 * @param model
	 * @param response
	 * @return @
	 */
	@RequestMapping(value = "downloadMailAttach")
	public String downloadMailAttach(@RequestParam(value = "boxType", required = false) String boxType,
			@RequestParam(value = "messageId", required = false) String messageId,
			@RequestParam(value = "multipartPath", required = false) String multipartPath, ModelMap model,
			HttpServletResponse response) {
		try {
			User user = (User) getRequestAttribute("ikep.user");
			String folderName = "";
			String mailUid = "";
			MessageBox messageBox = new MessageBox();
			SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
			searchCondition.setMessageId(messageId);
			searchCondition.setUserId(user.getUserId());
			
			if (boxType.equals("R")) {
				folderName = SeamlessMessageConstance.imapReceiveBox; 
				messageBox = this.seamlessMessageService.getReceiveBox(searchCondition);
			} else if (boxType.equals("S")) {
				folderName = SeamlessMessageConstance.imapsendBox; 
				messageBox = this.seamlessMessageService.getSendBox(searchCondition);
			}
			if (messageBox.getMailUid() != null) { mailUid = messageBox.getMailUid(); }
			if (!boxType.equals("") && !mailUid.equals("")) {
					this.mailReadService.downloadMailAttach("uid", folderName, mailUid, response, multipartPath, user);
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return "servicepack/seamless/messageView";
	}
	
	/**TODO 읽은 쪽지 삭제 Controller
	 * @param messageId
	 * @param messageType
	 * @param boxType
	 * @return
	 */
	@RequestMapping(value = "deleteMessage")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void deleteMessage(@RequestParam("messageId") String messageId
			, @RequestParam("messageType") String messageType
			, @RequestParam("boxType") String boxType) {
		User user = (User) getRequestAttribute("ikep.user");
		if (messageType.equals(SeamlessMessageConstance.messageTypeMessage)) {
			List<Message> messageList = new ArrayList<Message>();
			Message message = new Message();
			message.setMessageId(messageId);
			message.setUserId(user.getUserId());
			messageList.add(message);
			if (boxType.equals("R")) { this.messageService.deleteReceiveMessage(messageList);}
			if (boxType.equals("S")) { this.messageService.deleteSendMessage(messageList);}
		} else if (messageType.equals(SeamlessMessageConstance.messageTypeMail)) {
			SeamlessmessageSearchCondition removeSearchCondition = new SeamlessmessageSearchCondition();
			removeSearchCondition.setMessageId(messageId);
			removeSearchCondition.setUserId(user.getUserId());
			String folderName = "";
			MessageBox messageBox = new MessageBox();
			SeamlessmessageUserSeting seamlessmessageUserSeting = this.seamlessMessageService.getUserSeting(user.getUserId());
			if (seamlessmessageUserSeting != null && seamlessmessageUserSeting.getIsSourceDelete() != null && seamlessmessageUserSeting.getIsSourceDelete() == 1) {
				if (boxType.equals("R")) {
					folderName = SeamlessMessageConstance.imapReceiveBox; 
					messageBox = this.seamlessMessageService.getReceiveBox(removeSearchCondition);
				} else if (boxType.equals("S")) {
					folderName = SeamlessMessageConstance.imapsendBox; 
					messageBox = this.seamlessMessageService.getSendBox(removeSearchCondition);
				}
				if (messageBox.getMailUid() != null && !messageBox.getMailUid().equals("")) {
					this.mailReadService.deleteMail("uid",folderName, messageBox.getMailUid(), user);
				}
			}
			if (boxType.equals("R")) { this.seamlessMessageService.removeReceiveBox(removeSearchCondition);}
			if (boxType.equals("S")) { this.seamlessMessageService.removeSendBox(removeSearchCondition);}
		} else if (messageType.equals(SeamlessMessageConstance.messageTypeSMS)) {
			this.smsService.delete(messageId);
		}
		
	}
	
	/**TODO 최종접속일자 보기
	 * @param searchCondition
	 * @return lastContactDate
	 */
	@RequestMapping(value = "lastContactDate")  
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String lastContactDate(@RequestParam("guestId") String guestId) {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setUserId(user.getUserId());
		searchCondition.setGuestId(guestId);
		Date getDate = this.seamlessMessageService.lastContactDate(searchCondition);
		if (getDate != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			return dateFormat.format(timeZoneSupportService.convertTimeZone(getDate));
		} else {
			return "";
		}
	}
	
	/**TODO historyListView contact History 보기
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "historyListView")  
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<MessageBox> historyListView(@RequestParam("messageId") String messageId
			, @RequestParam("guestId") String guestId, @RequestParam(value = "messageType", required = false) String messageType) {
		List<MessageBox> messageBoxList = new ArrayList<MessageBox>();
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setMessageId(messageId);
		searchCondition.setUserId(user.getUserId());
		searchCondition.setGuestId(guestId);
		searchCondition.setMessageType(messageType);
		searchCondition.setRowNum(1);
		List<MessageBox> messageThis = this.seamlessMessageService.contactHistoryList(searchCondition);
		if (messageThis.size() > 0) {
			searchCondition.setRowNum(2);
			searchCondition.setMessageId(null);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			searchCondition.setSendDate(dateFormat.format(messageThis.get(0).getSendDate()));
			searchCondition.setSearchColumn("before");
			List<MessageBox> messageBefore = this.seamlessMessageService.contactHistoryList(searchCondition);
			searchCondition.setSearchColumn("after");
			List<MessageBox> messageAfter = this.seamlessMessageService.contactHistoryList(searchCondition);
			
			if(messageAfter.size() > 0) { messageBoxList.addAll(messageAfter); }
			messageBoxList.add(messageThis.get(0));
			if(messageBefore.size() > 0) { messageBoxList.addAll(messageBefore); }
			
			for (int i = 0; i < messageBoxList.size(); i++) {
				messageBoxList.get(i).setSenderId(guestId);
				String removeHtml = "";
				if (messageBoxList.get(i).getContents() != null) { removeHtml = SeamlessMessageUtil.getText(messageBoxList.get(i).getContents(),1); }
				if (messageBoxList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeMail)) {
					messageBoxList.get(i).setContents(messageTitleMaker(messageBoxList.get(i).getTitle() +" " +removeHtml,SeamlessMessageConstance.historyWordNum));
				} else if (messageBoxList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeMessage)) {
					messageBoxList.get(i).setContents(messageTitleMaker(removeHtml,SeamlessMessageConstance.historyWordNum));
				} else if (messageBoxList.get(i).getMessageType().equals(SeamlessMessageConstance.messageTypeSMS)) {
					messageBoxList.get(i).setContents(messageBoxList.get(i).getTitle());
				}
				messageBoxList.get(i).setSendDateOrg(messageBoxList.get(i).getSendDate());
				messageBoxList.get(i).setSendDate(timeZoneSupportService.convertTimeZone(messageBoxList.get(i).getSendDate()));
			}
		}
		return messageBoxList;
	}
	
	/**TODO moreHistoryList 추가 보기 AJAX Controller
	 * @param searchColumn, searchWord, lastDate
	 * @return sendList
	 */
	@RequestMapping(value = "moreHistoryList")  
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<MessageBox> moreHistoryList(@RequestParam(value="guestId", required=false, defaultValue="") String guestId
			, @RequestParam(value="searchColumn", required=false, defaultValue="") String searchColumn
			, @RequestParam(value="lastDate", required=false, defaultValue="") String lastDate
			, @RequestParam(value = "messageType", required = false) String messageType) {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setUserId(user.getUserId());
		searchCondition.setGuestId(guestId);
		searchCondition.setMessageType(messageType);
		if (searchColumn.equals("")) {
			searchCondition.setSearchColumn("before");
		} else {
			searchCondition.setSearchColumn(searchColumn);
		}
		if (lastDate.equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.AM_PM, Calendar.AM);
			c.add(Calendar.DATE, 1);	
			Date toDate = c.getTime();
			searchCondition.setSendDate(dateFormat.format(toDate));
		} else {
			searchCondition.setSendDate(lastDate);
		}
		searchCondition.setRowNum(SeamlessMessageConstance.historyViewCount);
		
		List<MessageBox> messageBoxList = this.seamlessMessageService.contactHistoryList(searchCondition);
		for (int i = 0; i < messageBoxList.size(); i++) {
			messageBoxList.get(i).setSenderId(guestId);
			String removeHtml = "";
			if (messageBoxList.get(i).getContents() != null) { removeHtml = SeamlessMessageUtil.getText(messageBoxList.get(i).getContents(),1); }
			if (messageBoxList.get(i).getMessageType().equals("Mail")) {
				messageBoxList.get(i).setContents(messageTitleMaker(messageBoxList.get(i).getTitle() +" " +removeHtml,SeamlessMessageConstance.historyWordNum));
			} else if (messageBoxList.get(i).getMessageType().equals("Message")) {
				messageBoxList.get(i).setContents(messageTitleMaker(removeHtml,SeamlessMessageConstance.historyWordNum));
			} else if (messageBoxList.get(i).getMessageType().equals("SMS")) {
				messageBoxList.get(i).setContents(messageBoxList.get(i).getTitle());
			}
			messageBoxList.get(i).setSendDateOrg(messageBoxList.get(i).getSendDate());
			messageBoxList.get(i).setSendDate(timeZoneSupportService.convertTimeZone(messageBoxList.get(i).getSendDate()));
		}
		return messageBoxList;
	}
	
	/**TODO 리스트 쪽지내용 Title 만들기 
	 * @param messageContents 쪽지내용
	 * @param wordNum         리스트에 보여질 글자수
	 * @return messageTitle   첫번째 행을 보여주거나 wordNum 만큼의 글을 보여준다.
	 */
	public String messageTitleMaker(String messageContents, int wordNum) {
			
		String messageTitle = "";
		int lineWord = messageContents.indexOf("\r");
		if (lineWord > 1 && lineWord < wordNum) {
			messageTitle = messageContents.substring(0, lineWord);
		} else if (messageContents.length() > wordNum){
			messageTitle = messageContents.substring(0, wordNum);
		} else {
			messageTitle = messageContents;
		}
		return messageTitle;
	}
	
	/**TODO 관리자 설정정보변경 Controller
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/adminView")
	public ModelAndView adminView(SeamlessmessageSearchCondition searchCondition, BindingResult result, SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();  
		
		User user = (User) getRequestAttribute("ikep.user");
		
		SeamlessmessageAdmin seamlessmessageAdmin = new SeamlessmessageAdmin();
		seamlessmessageAdmin = this.seamlessmessageAdminService.read(user.getPortalId());
		
		SeamlessmessageAdmin setmessageAdmin = new SeamlessmessageAdmin();
		SeamlessmessageCode matchCode = new SeamlessmessageCode();
		if(seamlessmessageAdmin == null) {			
			setmessageAdmin.setPortalId(user.getPortalId());
			seamlessmessageAdmin = setmessageAdmin;
		} else {
			String checkYn = "N";
			if (seamlessmessageAdmin.getMaxAttachFilesize() != null) {
				for (int i = 0; i < matchCode.getMaxAttachFilesize().size(); i++) {
					if (seamlessmessageAdmin.getMaxAttachFilesize().equals(matchCode.getMaxAttachFilesize().get(i).getKey())) {
						checkYn = "Y";
						setmessageAdmin.setMaxAttachFilesize(seamlessmessageAdmin.getMaxAttachFilesize());
						break;
					}
				}
				if (checkYn.equals("N")) { setmessageAdmin.setMaxAttachFilesize(0); }
			}
			checkYn = "N";
			if (seamlessmessageAdmin.getMaxReceiverCount() != null) {
				for (int i = 0; i < matchCode.getMaxReceiverCount().size(); i++) {
					if (seamlessmessageAdmin.getMaxReceiverCount().equals( matchCode.getMaxReceiverCount().get(i).getKey())) {
						checkYn = "Y";
						setmessageAdmin.setMaxReceiverCount(seamlessmessageAdmin.getMaxReceiverCount());
						break;
					}
				}
				if (checkYn.equals("N")) { setmessageAdmin.setMaxReceiverCount(0); }
			}
			checkYn = "N";
			if (seamlessmessageAdmin.getKeepDays() != null) {
				for (int i = 0; i < matchCode.getKeepDays().size(); i++) {
					if (seamlessmessageAdmin.getKeepDays().equals(matchCode.getKeepDays().get(i).getKey())) {
						checkYn = "Y";
						setmessageAdmin.setKeepDays(seamlessmessageAdmin.getKeepDays());
						break;
					}
				}
				if (checkYn.equals("N")) { setmessageAdmin.setKeepDays(0); }
			}
			checkYn = "N";
			if (seamlessmessageAdmin.getMaxImapCount() != null) {
				for (int i = 0; i < matchCode.getMaxImapCount().size(); i++) {
					if (seamlessmessageAdmin.getMaxImapCount().equals(matchCode.getMaxImapCount().get(i).getKey())) {
						checkYn = "Y";
						setmessageAdmin.setMaxImapCount(seamlessmessageAdmin.getMaxImapCount());
						break;
					}
				}
				if (checkYn.equals("N")) { setmessageAdmin.setMaxImapCount(0); }
			}
		}
		
		String adminYn = checkSystemAdmin(user);
		modelAndView.setViewName("servicepack/seamless/adminView");
		modelAndView.addObject("messageCode", new SeamlessmessageCode());
		modelAndView.addObject("messageAdmin", seamlessmessageAdmin);
		modelAndView.addObject("mcontacUserList", contactHistoryMenu(user));
		modelAndView.addObject("setmessageAdmin", setmessageAdmin);
		modelAndView.addObject("viewCode", "A");
		modelAndView.addObject("adminYn", adminYn);
		return modelAndView;
	}
	
	/**TODO 메시지 Setting 값 입력 AJAX Controller
	 * @param columnId, columnValue
	 * @return
	 */
	@RequestMapping(value = "adminSettingUpdate")  
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Integer adminSettingUpdate(@RequestParam("columnId") String columnId, @RequestParam("columnValue") Integer columnValue) {   
		try {
			SeamlessmessageAdmin seamlessmessageAdmin = new SeamlessmessageAdmin();
			User user = (User) getRequestAttribute("ikep.user");
			seamlessmessageAdmin.setPortalId(user.getPortalId());
			seamlessmessageAdmin.setColumnName(columnId);
			if (columnId.equals("maxAttachFilesize")){
				seamlessmessageAdmin.setMaxAttachFilesize(columnValue);
			} 
			if (columnId.equals("maxReceiverCount")){
				seamlessmessageAdmin.setMaxReceiverCount(columnValue);
			} 
			if (columnId.equals("keepDays")){
				seamlessmessageAdmin.setKeepDays(columnValue);
			} 
			if (columnId.equals("maxImapCount")){
				seamlessmessageAdmin.setMaxImapCount(columnValue);
			} 
			this.seamlessmessageAdminService.create(seamlessmessageAdmin);
		} catch(Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return columnValue;
	}
	
	/**TODO 통합메세지 > Contac History Controller
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/contactHistoryView")
	public ModelAndView contactHistoryView(@RequestParam(value = "setUser", required = false) String setUser
						, SeamlessmessageSearchCondition searchCondition, BindingResult result, SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();  
		
		User user = (User) getRequestAttribute("ikep.user");
		
		searchCondition.setUserId(user.getUserId());
		searchCondition.setUserIdR(user.getUserId());
		searchCondition.setUserIdS(user.getUserId());
		searchCondition.setStartRnum(0);
		searchCondition.setEndRnum(SeamlessMessageConstance.contactViewCount);

		if (searchCondition.getBoxType() != null && searchCondition.getBoxType().equals("R")) { searchCondition.setUserIdS(null);}
		if (searchCondition.getBoxType() != null && searchCondition.getBoxType().equals("S")) { searchCondition.setUserIdR(null);}

		SeamlessmessageUserSeting seamlessmessageUserSeting = this.seamlessMessageService.getUserSeting(user.getUserId());
		List<ContacUser> contacUserList = this.seamlessMessageService.contactUserList(searchCondition);
		String adminYn = checkSystemAdmin(user);
		modelAndView.setViewName("servicepack/seamless/contactHistoryView");
		modelAndView.addObject("contacUserList", contacUserList);
		modelAndView.addObject("mcontacUserList", contactHistoryMenu(user));
		modelAndView.addObject("searchCondition", searchCondition);
		modelAndView.addObject("seamlessmessageUserSeting", seamlessmessageUserSeting);
		modelAndView.addObject("viewCode", "C");
		modelAndView.addObject("adminYn", adminYn);
		modelAndView.addObject("setUser", setUser);
		return modelAndView;
	}
	
	/**TODO contactHistoryList 추가 보기 AJAX Controller
	 * @param searchColumn, searchWord, lastDate
	 * @return receiveList
	 */
	@RequestMapping(value = "contactList")  
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<ContacUser> contactList(@RequestParam(value="boxType", required=false, defaultValue="") String boxType
			, @RequestParam(value="endRnum", required=false, defaultValue="") Integer endRnum ) {
		SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setBoxType(boxType);
		searchCondition.setUserId(user.getUserId());
		searchCondition.setUserIdR(user.getUserId());
		searchCondition.setUserIdS(user.getUserId());
		searchCondition.setStartRnum(endRnum);
		searchCondition.setEndRnum(endRnum+SeamlessMessageConstance.contactViewCount);

		if (searchCondition.getBoxType() != null && searchCondition.getBoxType().equals("R")) { searchCondition.setUserIdS(null);}
		if (searchCondition.getBoxType() != null && searchCondition.getBoxType().equals("S")) { searchCondition.setUserIdR(null);}
		
		return (List<ContacUser>) this.seamlessMessageService.contactUserList(searchCondition);
	}
	
	/**TODO 통합메세지 > Contac History menu User
	 * @param user
	 * @return List<ContacUser>
	 */
	public List<ContacUser> contactHistoryMenu(User user) {
		SeamlessmessageSearchCondition msearchCondition = new SeamlessmessageSearchCondition();
		msearchCondition.setUserId(user.getUserId());
		msearchCondition.setUserIdR(user.getUserId());
		msearchCondition.setUserIdS(user.getUserId());
		msearchCondition.setStartRnum(0);
		msearchCondition.setEndRnum(SeamlessMessageConstance.contactViewCount);
		return this.seamlessMessageService.contactUserList(msearchCondition);
	}
	
	/**TODO 쪽지보내기 팝업 열기 Controller
	 * @param returnMsg
	 * @param senderUserId
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/messageSendView")
	public ModelAndView messageSendView(Model model, @RequestParam(value="returnMsg", required=false, defaultValue="NEW") String returnMsg
			, @RequestParam(value="senderUserId", required=false, defaultValue="NO") String senderUserId
			, @RequestParam(value="contents", required=false, defaultValue="NO") String contents
			, @RequestParam(value="type", required=false, defaultValue="NO") String type
			, @RequestParam(value="messageType", required=false, defaultValue="NO") String messageType
			, @RequestParam(value="boxType", required=false, defaultValue="NO") String boxType
			, @RequestParam(value="reSendMessageId", required=false, defaultValue="NO") String reSendMessageId) throws JsonGenerationException, JsonMappingException, IOException {
		ModelAndView modelAndView = new ModelAndView();  
		MessageBox messageBox = new MessageBox();
		if (getModelAttribute("messageBox") != null) {
			messageBox = (MessageBox)getModelAttribute("messageBox");
		}
		User user = (User) getRequestAttribute("ikep.user");
		
		SeamlessmessageAdmin userMessageAdmin = new SeamlessmessageAdmin();
		userMessageAdmin = this.seamlessmessageAdminService.read(user.getPortalId());
		
		User replyUser = new User();
		List<User> replyList = new ArrayList<User>();
		if (!senderUserId.equals("NO")) { replyUser = userService.read(senderUserId); }
		if (!contents.equals("NO")) { messageBox.setContents(contents); }
		
		if (!reSendMessageId.equals("NO")) {
			if (messageType.equals(SeamlessMessageConstance.messageTypeMessage)) {
				Message reSendMessage = this.messageService.read(reSendMessageId);
				if (type.equals("resend") || (type.equals("reply") && boxType.equals("S")) ) { 
					messageBox.setContents(reSendMessage.getContents());
					//첨부파일 가져오기
					ObjectMapper mapper = new ObjectMapper();
					String fileDataListJson = mapper.writeValueAsString(reSendMessage.getFileDataList());
					messageBox.setFileDataList(reSendMessage.getFileDataList());
					modelAndView.addObject("fileDataListJson", fileDataListJson);
					if (type.equals("reply") && boxType.equals("S")){
						List<MessageReceive> messageReceiveList = this.messageService.messageReceiveList(reSendMessageId,reSendMessage.getReceiverCount());
						for (int i = 0; i < messageReceiveList.size(); i++) {
							replyUser = userService.read(messageReceiveList.get(i).getUserId());
							if (replyUser != null) {
								replyList.add(replyUser);
							}
						}
						replyUser = null;
					}
				} else if(type.equals("reply") && boxType.equals("R")) { 
					String replyContents = "\n-------------------------  Original Message ----------------------\n" +reSendMessage.getContents();
					messageBox.setContents(replyContents);
					replyUser = userService.read(reSendMessage.getSenderId());
				}
			} else if (messageType.equals(SeamlessMessageConstance.messageTypeMail)) {
				SeamlessmessageSearchCondition searchCondition = new SeamlessmessageSearchCondition();
				searchCondition.setMessageId(reSendMessageId);
				searchCondition.setUserId(user.getUserId());
				MessageBox reSendMessageBox = new MessageBox();
				String folderName = "";
				String mailUid = "";
				if (boxType.equals("R")){
					folderName = SeamlessMessageConstance.imapReceiveBox; 
					reSendMessageBox = this.seamlessMessageService.getReceiveBox(searchCondition);
				} else if (boxType.equals("S")){
					folderName = SeamlessMessageConstance.imapsendBox; 
					reSendMessageBox = this.seamlessMessageService.getSendBox(searchCondition);
				}
				if (type.equals("resend") || (type.equals("reply") && boxType.equals("S"))) { 
					messageBox.setContents(reSendMessageBox.getContents());
					//첨부파일 가져오기
					List<FileData> uploadList = new ArrayList<FileData>();
					if (reSendMessageBox.getMailUid() != null) {
						mailUid = reSendMessageBox.getMailUid();
						String strFileName = "";
						for (int i = 0; i < reSendMessageBox.getAttachFileList().size(); i++) {
							strFileName = reSendMessageBox.getAttachFileList().get(i).getAttachName();
							FileData fileData = this.mailReadService.copyMailAttach(folderName, strFileName, mailUid, reSendMessageBox.getAttachFileList().get(i).getMultipartPath(), user);
							uploadList.add(fileData);
						}
					}
					if (uploadList != null) {
						ObjectMapper mapper = new ObjectMapper();
						String fileDataListJson = mapper.writeValueAsString(uploadList);
						messageBox.setFileDataList(uploadList);
						modelAndView.addObject("fileDataListJson", fileDataListJson);
					}
					if (type.equals("reply") && boxType.equals("S")){
						for (int i = 0; i < reSendMessageBox.getRecipientList().size(); i++) {
							replyUser = userService.read(reSendMessageBox.getRecipientList().get(i).getReceiverId());
							if (replyUser != null) {
								replyList.add(replyUser);
							} else {
								User replyUserMail = new User();
								replyUserMail.setMail(reSendMessageBox.getRecipientList().get(i).getReceiverMail());
								replyList.add(replyUserMail);
							}
						}
						replyUser = null;
					}
				} else if(type.equals("reply") && boxType.equals("R")) { 
					String replyContents = "\n-------------------------  Original Message ----------------------\n" +reSendMessageBox.getContents();
					messageBox.setContents(replyContents);
					replyUser = userService.read(reSendMessageBox.getSenderId());
				}
			} else if (messageType.equals(SeamlessMessageConstance.messageTypeSMS)) {
				Sms sms = this.smsService.read(reSendMessageId);
				messageBox.setContents(sms.getContents());
				if (type.equals("reply") && boxType.equals("R")){
					replyUser = userService.read(sms.getRegisterId());
				} else if (type.equals("reply") && boxType.equals("S")){
					replyUser = userService.read(sms.getReceiverId());
				}
			}
		}
		
		modelAndView.setViewName("servicepack/seamless/messageSendView");
		modelAndView.addObject("messageBox", messageBox);
		modelAndView.addObject("messageCode", new SeamlessmessageCode());
		modelAndView.addObject("returnMsg",returnMsg);
		modelAndView.addObject("replyUser",replyUser);
		modelAndView.addObject("replyList",replyList);
		modelAndView.addObject("setContents",contents);
		modelAndView.addObject("userMessageAdmin",userMessageAdmin);
		return modelAndView;
	}
	
	/**TODO 쪽지보내기 Controller
	 * @param message
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/seamlessMessageSend")
	public String seamlessMessageSend(Model model, MessageBox messageBox, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute(PREFIX_BINDING_RESULT + "messageBox", result);
			return "forward:/servicepack/seamless/messageSendView.do";
		}
		User user = (User) getRequestAttribute("ikep.user");
		Message message = new Message();
		Sms sms = new Sms();
		Mail mail = new Mail();
		
		String[] arrayReceive;
		arrayReceive = messageBox.getReceiverList().replaceAll(" ", "").split(",");
		String receiverIds = "";
		List<String> receiverList = new ArrayList<String>();
		String receiverPhonenos = "";
		
		@SuppressWarnings("rawtypes")
		List<HashMap> toEmailList = new ArrayList<HashMap>();
		
		User ruser = new User();
		
		Boolean isMail = false;
		Boolean isMessage = false;
		Boolean isSMS = false;
		for (int i = 0; i < messageBox.getSendType().size(); i++) {
			if (messageBox.getSendType().get(i).equals(SeamlessMessageConstance.messageTypeMail)) { isMail = true; }
			if (messageBox.getSendType().get(i).equals(SeamlessMessageConstance.messageTypeMessage)) { isMessage = true; }
			if (messageBox.getSendType().get(i).equals(SeamlessMessageConstance.messageTypeSMS)) { isSMS = true; }
		}
		int j = 0;
		for (int i = 0; i < arrayReceive.length; i++) {
			String[] arrayReceiveUserList = arrayReceive[i].split("/");
			HashMap<String, String> map = new HashMap<String, String>();
			if (arrayReceiveUserList[0].equals("Mail")) {
				map.put("email", arrayReceiveUserList[1]);
				map.put("name", arrayReceiveUserList[1]);
				toEmailList.add(map);
			} else {
				ruser = this.userService.read(arrayReceiveUserList[0]);
				if (ruser != null) {
					receiverList.add(arrayReceiveUserList[0]+ "/" + arrayReceiveUserList[1]);
					if (ruser.getMobile() != null && !ruser.getMobile().equals("")) {
						if (j==0) {
							receiverIds = arrayReceiveUserList[0];
							receiverPhonenos = ruser.getMobile();
						} else {
							receiverIds = receiverIds + "," + arrayReceiveUserList[0];
							receiverPhonenos = receiverPhonenos + "," + ruser.getMobile();
						}
						j++;
					}
					if (ruser.getMail() != null && !ruser.getMail().equals("")) {
						map.put("email", ruser.getMail());
						map.put("name", ruser.getUserName());
						toEmailList.add(map);
					}
				}
			}
		}
		
		if (isMessage) {
			message.setSenderId(user.getUserId());
			message.setSenderName(user.getUserName());
			message.setContents(messageBox.getContents());
			message.setReceiverList(receiverList.toString());
			message.setFileLinkList(messageBox.getFileLinkList());
			//message.setFileDataList(messageBox.getFileDataList());
			this.messageOutsideService.sendMessageOutside(message, user);
		}
		if (isSMS) {
			sms.setRegisterId(user.getUserId());
			sms.setRegisterName(user.getUserName());
			String smsContents = SeamlessMessageUtil.getText(messageBox.getContents(),1);
			sms.setContents(getShortString(smsContents));
			sms.setReceiverIds(receiverIds.split(","));          
			sms.setReceiverPhonenos(receiverPhonenos.split(","));
			this.smsService.create(sms);
		}
		if (isMail) {
			mail.setToEmailList(toEmailList);
			mail.setTitle(messageTitleMaker(messageBox.getContents(),SeamlessMessageConstance.titleWordNum));
			mail.setContent(messageBox.getContents());
			mail.setMailType(MailConstant.MAIL_TYPE_TXT);
			mail.setFileLinkList(messageBox.getFileLinkList());
			this.sendMail(mail, user);
			try {
				int size = 0;
				SeamlessmessageAdmin seamlessmessageAdmin = this.seamlessmessageAdminService.read(user.getPortalId());
				if (seamlessmessageAdmin.getMaxImapCount() != null) { size = seamlessmessageAdmin.getMaxImapCount(); }
				this.seamlessMessageService.contackImapMail(SeamlessMessageConstance.imapsendBox,user, size);
			} catch (Exception e) {
			}
		}
		status.setComplete();
		return "redirect:/servicepack/seamless/messageSendView.do?returnMsg=SAVE";
	}
	
	/** 문자 Byte로 자르기 로직
	 * @param orig
	 * @return
	 */
	public String getShortString(String orig){
		byte[] byteString = orig.getBytes();
		int length =  SeamlessMessageConstance.smsCutNum;
		if (byteString.length <= length) {
			return orig;
		} else {
			int minusByteCount = 0;
			for (int i = 0; i < length; i++) {
				minusByteCount += (byteString[i] < 0) ? 1 : 0;
			}
			if (minusByteCount % 2 != 0) {
				length--;
			}
			return new String(byteString, 0, length);
		}
	}

	/**
	 * 메일 보내는 로직
	 * @param mail
	 * @param user
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public void sendMail(Mail mail, User user){
		// 파일 첨부가 있을 경우(플레쉬 업로드 컨트롤러에서 파일 첨부가 있을경우)
		Properties fileprop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		String uploadRoot = fileprop.getProperty("ikep4.support.fileupload.upload_root");
		List<MailAttach> mailAttachList = new ArrayList<MailAttach>();	
		if (mail.getFileLinkList() != null) {
			for (int i = 0; i < mail.getFileLinkList().size(); i++) {
				if (!mail.getFileLinkList().get(i).getFlag().equals("del")) {
					FileData fileData = fileService.read(mail.getFileLinkList().get(i).getFileId());
					if (checkImageFile(fileData.getFileRealName())) {
						uploadRoot = fileprop.getProperty("ikep4.support.fileupload.upload_root_image");
					} 
					MailAttach mailAttach = new MailAttach();
					mailAttach.setName(fileData.getFileRealName());
					mailAttach.setPath(uploadRoot + fileData.getFilePath() + File.separatorChar
							+ fileData.getFileName());
					mailAttachList.add(mailAttach);
				}
			}
		}
		mail.setAttachList(mailAttachList);
		this.mailSendService.sendMail(mail, new HashMap(), user);
	}
	private boolean checkImageFile(String fileName) {

		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		String keywordList = prop.getProperty("ikep4.support.fileupload.image_file");

		Pattern p = Pattern.compile(keywordList, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(fileName);

		return m.find();
	}
	
	/**TODO 사용자별 IMAP MAIL SETING AJAX Controller
	 * @param userId, isSourceDelete
	 * @return
	 */
	@RequestMapping(value = "updateUserSeting")  
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateUserSeting(@RequestParam("userId") String userId
			, @RequestParam("isSourceDelete") Integer isSourceDelete
			, @RequestParam("autoConnect") Integer autoConnect) {   
		User user = (User) getRequestAttribute("ikep.user");
		SeamlessmessageUserSeting seamlessmessageUserSeting = new SeamlessmessageUserSeting();
		seamlessmessageUserSeting.setRegisterId(userId);
		seamlessmessageUserSeting.setIsSourceDelete(isSourceDelete);
		seamlessmessageUserSeting.setAutoConnect(autoConnect);
		seamlessmessageUserSeting.setUpdaterId(user.getUserId());
		this.seamlessMessageService.updateUserSeting(seamlessmessageUserSeting);
	}
	
	/**TODO IMAP MAIL 수동 연결해서 가져오기
	 * @param folderName
	 * @return resultMsg
	 */
	@RequestMapping(value = "connectImapMail")  
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String connectImapMail(@RequestParam("folderName") String folderName) {   
		User user = (User) getRequestAttribute("ikep.user");
		SeamlessmessageAdmin seamlessmessageAdmin = this.seamlessmessageAdminService.read(user.getPortalId());
		int size = 0;
		if (seamlessmessageAdmin.getMaxImapCount() != null) { size = seamlessmessageAdmin.getMaxImapCount(); }
		String resultMsg = "OK";
		SeamlessmessageUserSeting seamlessmessageUserSeting = this.seamlessMessageService.getUserSeting(user.getUserId());
		if (folderName.equals(SeamlessMessageConstance.imapReceiveBox)) {
			//if (seamlessmessageUserSeting.getInboxSyncComplete() == 1 ) {
			//	resultMsg = "SYNC";
			//} else {
				resultMsg = this.seamlessMessageService.contackImapMail(folderName,user, size);
			//}
		} else if (folderName.equals(SeamlessMessageConstance.imapsendBox)) {
			//if (seamlessmessageUserSeting.getSentSyncComplete() == 1 ) {
			//	resultMsg = "SYNC";
			//} else {
				resultMsg = this.seamlessMessageService.contackImapMail(folderName,user, size);
			//}
		}
		return resultMsg;
	}
	
	/**TODO 통합메세지 메뉴에서 동기화 중일때 상태 체크 AJAX Controller
	 * @param 
	 * @return returnValue
	 */
	@RequestMapping(value="/mailSyncState")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String mailSyncState() {
		User user = (User) getRequestAttribute("ikep.user");
		SeamlessmessageUserSeting seamlessmessageUserSeting = this.seamlessMessageService.getUserSeting(user.getUserId());
		Integer inboxSyncComplete = 0;
		Integer sentSyncComplete = 0;

		inboxSyncComplete = seamlessmessageUserSeting.getInboxSyncComplete();
		sentSyncComplete = seamlessmessageUserSeting.getSentSyncComplete();
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("inboxSyncComplete", inboxSyncComplete);
		jsonObj.put("sentSyncComplete", sentSyncComplete);
		
		String returnValue = jsonObj.toString();
		
		return returnValue;
	}
	
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 *
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public String checkSystemAdmin(User user) {
		String sysName = "Messaging";
		boolean isSystemAdmin = this.aclService.isSystemAdmin(sysName, user.getUserId());
		String adminYn = "N";
        if (isSystemAdmin) { adminYn = "Y";}
		return adminYn;
	}

}
