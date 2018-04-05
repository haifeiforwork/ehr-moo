/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.model.MessageAdmin;
import com.lgcns.ikep4.support.message.model.MessageAlarm;
import com.lgcns.ikep4.support.message.model.MessageCategory;
import com.lgcns.ikep4.support.message.model.MessageChart;
import com.lgcns.ikep4.support.message.model.MessageCode;
import com.lgcns.ikep4.support.message.model.MessageMonitor;
import com.lgcns.ikep4.support.message.model.MessageReceive;
import com.lgcns.ikep4.support.message.model.MessageSpecialUser;
import com.lgcns.ikep4.support.message.search.MessageMonitorSearchCondition;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;
import com.lgcns.ikep4.support.message.service.MessageAdminService;
import com.lgcns.ikep4.support.message.service.MessageCategoryService;
import com.lgcns.ikep4.support.message.service.MessageService;
import com.lgcns.ikep4.support.message.service.MessageSpecialUserService;
import com.lgcns.ikep4.support.message.util.MessageConstance;
import com.lgcns.ikep4.support.message.util.MessageUtil;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.messenger.AtMessengerCommunicator;


/**
 * TODO Javadoc주석작성
 * 
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageController.java 17335 2012-02-22 05:52:20Z yruyo $
 */
@Controller
@RequestMapping(value = "/support/message")
@SessionAttributes({ "message", "searchCondition" })
public class MessageController extends BaseController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private MessageAdminService messageAdminService;

	@Autowired
	private MessageSpecialUserService messageSpecialUserService;

	@Autowired
	private MessageCategoryService messageCategoryService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private UserService userService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	private static final Set<String> internalAnnotationAttributes = new HashSet<String>(3);
	
	private Properties prop = null;		
	private String serverIp = null;
	private String serverPort = null;

	/**
	 * TODO 쪽지메인화면 (받은메세지함) Controller
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/messageListView")
	public ModelAndView listMessageView(MessageSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();

		List<MessageCategory> categoryList = this.messageCategoryService.getCategory();

		User user = (User) getRequestAttribute("ikep.user");

		searchCondition.setUserId(user.getUserId());

		SearchResult<Message> searchResult = this.messageService.getReceiveMessageList(searchCondition);

		if (!searchResult.isEmptyRecord()) {
			for (int i = 0; i < searchResult.getEntity().size(); i++) {

				if (searchResult.getEntity().get(i).getContents() == null) {
					searchResult.getEntity().get(i).setContents("");
				} else {
					searchResult
							.getEntity()
							.get(i)
							.setContents(
									messageTitleMaker(
											MessageUtil.getText(searchResult.getEntity().get(i).getContents(), 1),
											MessageConstance.titleWordNum));
				}
			}
		}

		MessageCode messageCode = new MessageCode();
		MessageAdmin userMessageAdmin = new MessageAdmin();
		userMessageAdmin = this.messageAdminService.getUserAdmin(user.getUserId());
		int newMessage = this.messageService.countNewMessage(searchCondition.getUserId());

		String adminYn = checkSystemAdmin(user);
		if (searchCondition.getLayoutType() == null) {
			searchCondition.setLayoutType("LR");
		}
		modelAndView.setViewName("support/message/messageListView");
		modelAndView.addObject("categoryList", categoryList);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("messageCode", messageCode);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("newMessage", newMessage);
		modelAndView.addObject("userMessageAdmin", userMessageAdmin);
		modelAndView.addObject("viewCode", "R");
		modelAndView.addObject("adminYn", adminYn);
		return modelAndView;
	}

	/**
	 * TODO 보낸쪽지함 Controller
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/messageSendListView")
	public ModelAndView messageSendListView(MessageSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();
		List<MessageCategory> categoryList = this.messageCategoryService.getCategory();

		User user = (User) getRequestAttribute("ikep.user");

		searchCondition.setUserId(user.getUserId());

		SearchResult<Message> searchResult = this.messageService.getSendMessageList(searchCondition);

		String unconfirmedMessage = messageSource.getMessage("message.support.message.detail.unconfirmedMessage", null,
				new Locale(user.getLocaleCode()));
		String checkedMessage = messageSource.getMessage("message.support.message.detail.checkedMessage", null,
				new Locale(user.getLocaleCode()));
		String checkingMessage = messageSource.getMessage("message.support.message.detail.checkingMessage", null,
				new Locale(user.getLocaleCode()));

		if (!searchResult.isEmptyRecord()) {
			for (int i = 0; i < searchResult.getEntity().size(); i++) {
				String orgReceiverList = searchResult.getEntity().get(i).getReceiverList();
				searchResult.getEntity().get(i).setReceiverList(receiverMaker(orgReceiverList, 1, "ko"));
				searchResult.getEntity().get(i).setReceiverEnglishList(receiverMaker(orgReceiverList, 1, "en"));
				if (searchResult.getEntity().get(i).getContents() == null) {
					searchResult.getEntity().get(i).setContents("");
				} else {
					searchResult
							.getEntity()
							.get(i)
							.setContents(
									messageTitleMaker(
											MessageUtil.getText(searchResult.getEntity().get(i).getContents(), 1),
											MessageConstance.titleWordNum));
				}
				if (searchResult.getEntity().get(i).getReceiverCheck().substring(0, 1).equals("N")) {
					searchResult
							.getEntity()
							.get(i)
							.setReceiverCheck(
									unconfirmedMessage
											+ searchResult.getEntity().get(i).getReceiverCheck().substring(1));
				} else if (searchResult.getEntity().get(i).getReceiverCheck().substring(0, 1).equals("C")) {
					searchResult
							.getEntity()
							.get(i)
							.setReceiverCheck(
									checkedMessage + searchResult.getEntity().get(i).getReceiverCheck().substring(1));
				} else if (searchResult.getEntity().get(i).getReceiverCheck().substring(0, 1).equals("I")) {
					searchResult
							.getEntity()
							.get(i)
							.setReceiverCheck(
									checkingMessage + searchResult.getEntity().get(i).getReceiverCheck().substring(1));
				}
			}
		}
		MessageSearchCondition searchConditionNew = new MessageSearchCondition();
		searchConditionNew = (MessageSearchCondition) searchResult.getSearchCondition();

		if (searchCondition.getResultCode() != null) {
			searchConditionNew.setResultCode(searchCondition.getResultCode());
		}

		MessageCode messageCode = new MessageCode();
		MessageAdmin userMessageAdmin = new MessageAdmin();
		userMessageAdmin = this.messageAdminService.getUserAdmin(user.getUserId());
		int newMessage = this.messageService.countNewMessage(searchCondition.getUserId());

		String adminYn = checkSystemAdmin(user);
		if (searchCondition.getLayoutType() == null) {
			searchCondition.setLayoutType("LR");
		}
		modelAndView.setViewName("support/message/messageSendListView");
		modelAndView.addObject("categoryList", categoryList);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("messageCode", messageCode);
		modelAndView.addObject("searchCondition", searchConditionNew);
		modelAndView.addObject("newMessage", newMessage);
		modelAndView.addObject("userMessageAdmin", userMessageAdmin);
		modelAndView.addObject("viewCode", "S");
		modelAndView.addObject("adminYn", adminYn);
		return modelAndView;
	}

	/**
	 * TODO 개인보관함 Controller
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/messageStoreListView")
	public ModelAndView messageStoreListView(MessageSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();

		List<MessageCategory> categoryList = this.messageCategoryService.getCategory();

		User user = (User) getRequestAttribute("ikep.user");

		searchCondition.setUserId(user.getUserId());

		SearchResult<Message> searchResult = this.messageService.getStoreMessageList(searchCondition);

		if (!searchResult.isEmptyRecord()) {
			for (int i = 0; i < searchResult.getEntity().size(); i++) {
				if (searchResult.getEntity().get(i).getMessageClass().equals("S")) {
					String orgReceiverList = searchResult.getEntity().get(i).getReceiverList();
					searchResult.getEntity().get(i).setReceiverList(receiverMaker(orgReceiverList, 1, "ko"));
					searchResult.getEntity().get(i).setReceiverEnglishList(receiverMaker(orgReceiverList, 1, "en"));
				} else {
					searchResult.getEntity().get(i).setReceiverList(searchResult.getEntity().get(i).getSenderName());
					searchResult.getEntity().get(i)
							.setReceiverEnglishList(searchResult.getEntity().get(i).getSenderEnglishName());
				}
				if (searchResult.getEntity().get(i).getContents() == null) {
					searchResult.getEntity().get(i).setContents("");
				} else {
					searchResult
							.getEntity()
							.get(i)
							.setContents(
									messageTitleMaker(
											MessageUtil.getText(searchResult.getEntity().get(i).getContents(), 1),
											MessageConstance.titleWordNum));
				}
			}
		}

		MessageCode messageCode = new MessageCode();
		MessageAdmin userMessageAdmin = new MessageAdmin();
		userMessageAdmin = this.messageAdminService.getUserAdmin(user.getUserId());
		int newMessage = this.messageService.countNewMessage(searchCondition.getUserId());

		String adminYn = checkSystemAdmin(user);
		if (searchCondition.getLayoutType() == null) {
			searchCondition.setLayoutType("LR");
		}
		modelAndView.setViewName("support/message/messageStoreListView");
		modelAndView.addObject("categoryList", categoryList);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("messageCode", messageCode);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("newMessage", newMessage);
		modelAndView.addObject("userMessageAdmin", userMessageAdmin);
		modelAndView.addObject("viewCode", "K");
		modelAndView.addObject("adminYn", adminYn);
		return modelAndView;
	}

	/**
	 * TODO 그룹 쪽지화면 (그룹 받은메세지함) Controller
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/groupMessageListView")
	public ModelAndView groupMessageListView(@RequestParam("groupId") String groupId,
			MessageSearchCondition searchCondition, BindingResult result, SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();

		List<MessageCategory> categoryList = this.messageCategoryService.getCategory();

		User user = (User) getRequestAttribute("ikep.user");
		if (groupId == null || groupId.equals("")) {
			searchCondition.setGroupId(user.getGroupId());
		} else {
			searchCondition.setGroupId(groupId);
		}

		SearchResult<Message> searchResult = this.messageService.getGroupMessageList(searchCondition);

		if (!searchResult.isEmptyRecord()) {
			for (int i = 0; i < searchResult.getEntity().size(); i++) {
				if (searchResult.getEntity().get(i).getContents() == null) {
					searchResult.getEntity().get(i).setContents("");
				} else {
					searchResult
							.getEntity()
							.get(i)
							.setContents(
									messageTitleMaker(
											MessageUtil.getText(searchResult.getEntity().get(i).getContents(), 1),
											MessageConstance.titleWordNum));
				}
			}
		}

		MessageCode messageCode = new MessageCode();

		if (searchCondition.getLayoutType() == null) {
			searchCondition.setLayoutType("LR");
		}
		modelAndView.setViewName("support/message/groupMessageListView");
		modelAndView.addObject("categoryList", categoryList);
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("messageCode", messageCode);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("groupId", groupId);
		return modelAndView;
	}

	/**
	 * TODO 선택한 쪽지 읽기 AJAX Controller
	 * 
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "readMessage")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Message readMessage(@RequestParam("messageId") String messageId,
			@RequestParam(value = "isRead", required = false, defaultValue = "1") String isRead) {
		Message message = new Message();
		User user = (User) getRequestAttribute("ikep.user");
		MessageSearchCondition messageSearchCondition = new MessageSearchCondition();
		messageSearchCondition.setMessageId(messageId);
		messageSearchCondition.setUserId(user.getUserId());
		if (this.messageService.readCheck(messageSearchCondition)) {
			message = this.messageService.read(messageId);
			if (isRead.equals("0")) {
				this.messageService.insertIsRead(messageId, user.getUserId());
			}
			String orgReceiverList = message.getReceiverList();
			message.setReceiverList(receiverMaker(orgReceiverList, MessageConstance.viewUserNum, "ko"));
			message.setReceiverEnglishList(receiverMaker(orgReceiverList, MessageConstance.viewUserNum, "en"));
			message.setContents(message.getContents().replace("\n", "<br>"));
			message.setSendDate(timeZoneSupportService.convertTimeZone(message.getSendDate()));
		}
		return message;
	}

	/**
	 * TODO 쪽지보내기 팝업 열기 Controller
	 * 
	 * @param returnMsg
	 * @param senderUserId
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/messageNew")
	public ModelAndView messageNew(Model model,
			@RequestParam(value = "returnMsg", required = false, defaultValue = "NEW") String returnMsg,
			@RequestParam(value = "senderUserId", required = false, defaultValue = "NO") String senderUserId,
			@RequestParam(value = "contents", required = false, defaultValue = "NO") String contents,
			@RequestParam(value = "type", required = false, defaultValue = "NO") String type,
			@RequestParam(value = "reSendMessageId", required = false, defaultValue = "NO") String reSendMessageId)
			throws JsonGenerationException, JsonMappingException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		Message message = new Message();
		if (getModelAttribute("message") != null) {
			message = (Message) getModelAttribute("message");
		}
		List<MessageCategory> categoryList = this.messageCategoryService.getCategory();

		User user = (User) getRequestAttribute("ikep.user");

		MessageAdmin userMessageAdmin = new MessageAdmin();
		userMessageAdmin = this.messageAdminService.getUserAdmin(user.getUserId());

		User replyUser = new User();
		if (!senderUserId.equals("NO")) {
			replyUser = userService.read(senderUserId);
		}

		if (!contents.equals("NO")) {
			message.setContents(contents);
		}

		Message reSendMessage = new Message();
		if (!reSendMessageId.equals("NO")) {
			reSendMessage = this.messageService.read(reSendMessageId);
			if (type.equals("resend")) {
				message.setContents(reSendMessage.getContents());
				// 첨부파일 가져오기
				ObjectMapper mapper = new ObjectMapper();
				String fileDataListJson = mapper.writeValueAsString(reSendMessage.getFileDataList());
				message.setFileDataList(reSendMessage.getFileDataList());
				modelAndView.addObject("fileDataListJson", fileDataListJson);
			} else if (type.equals("reply")) {
				String replyContents = "\n-------------------------  Original Message ----------------------\n"
						+ reSendMessage.getContents();
				message.setContents(replyContents);
				replyUser = userService.read(reSendMessage.getSenderId());
			}
		}
		
		if (!user.getLocaleCode().equals("ko")) {
			replyUser.setUserName(replyUser.getUserEnglishName());
			replyUser.setJobTitleName(replyUser.getJobTitleEnglishName());
			replyUser.setTeamName(replyUser.getTeamEnglishName());
		}

		modelAndView.setViewName("support/message/messageNew");
		modelAndView.addObject("message", message);
		modelAndView.addObject("categoryList", categoryList);
		modelAndView.addObject("returnMsg", returnMsg);
		modelAndView.addObject("replyUser", replyUser);
		modelAndView.addObject("setContents", contents);
		modelAndView.addObject("userMessageAdmin", userMessageAdmin);
		return modelAndView;
	}

	/**
	 * TODO 쪽지보내기 Controller
	 * 
	 * @param message
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/messageSend")
	public String messageSend(Model model, @Valid Message message, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute(PREFIX_BINDING_RESULT + "message", result);
			return "forward:/support/message/messageNew.do";
		}
		User user = (User) getRequestAttribute("ikep.user");
		message.setAttachSize(0);
		message.setIsStored(0);
		message.setIsComplete(0);
		if (message.getIsUrgent() == null) {
			message.setIsUrgent(0);
		}
		message.setSenderId(user.getUserId());
		message.setSenderName(user.getUserName());

		String receiverList = message.getReceiverList().replaceAll(" ", "");
		receiverList = receiverList.replace("[", "");
		receiverList = receiverList.replace("]", "");
		String[] arrayReceive;
		arrayReceive = receiverList.split(",");
		ArrayList<String> receiverString = new ArrayList<String>();
		for (int i = 0; i < arrayReceive.length; i++) {
			String[] arrayReceiveUserList;
			arrayReceiveUserList = arrayReceive[i].split("/");
			if (arrayReceiveUserList.length > 1) {
				receiverString.add(arrayReceiveUserList[0] + "/" + arrayReceiveUserList[1]);
			} else {
				receiverString.add(arrayReceiveUserList[0]);
			}
		}
		receiverList = receiverString.toString().replace("[", "").replace("]", "");
		message.setReceiverList(receiverList);
		message.setReceiverCount(arrayReceive.length);
		String messageId = this.messageService.sendMessage(message, user);

		List<Message> list = new ArrayList<Message>();
		for (int i = 0; i < arrayReceive.length; i++) {
			String[] arrayReceiveUser;
			arrayReceiveUser = arrayReceive[i].split("/");
			Message receiveMessage = new Message();
			receiveMessage.setSenderId(user.getUserId());
			receiveMessage.setSenderName(user.getUserName());
			receiveMessage.setMessageId(messageId);
			receiveMessage.setUserId(arrayReceiveUser[0]);
			if (arrayReceiveUser.length > 1) {
				receiveMessage.setUserName(arrayReceiveUser[1]);
			}
			if (arrayReceiveUser.length > 2) {
				if (arrayReceiveUser[2].equals("user")) {
					receiveMessage.setReceiveType("P");
				} else if (arrayReceiveUser[2].equals("group")) {
					receiveMessage.setReceiveType("T");
				}
			} else {
				receiveMessage.setReceiveType("P");
			}

			list.add(receiveMessage);
		}
		this.messageService.insertReceiveMessage(list);

		return "redirect:/support/message/messageNew.do?returnMsg=SAVE";
	}
	
	@RequestMapping(value = "/messageSendNew")
	public String messageSendNew(Model model, @Valid Message message, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute(PREFIX_BINDING_RESULT + "message", result);
			return "forward:/support/message/messageNew.do";
		}
		User user = (User) getRequestAttribute("ikep.user");
		message.setSenderId(user.getUserId());
		message.setSenderName(user.getUserName());
		
		prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
		serverIp = prop.getProperty("messenger.server.ip");
		serverPort = prop.getProperty("messenger.server.port");
		AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
		
		String prefix	= "message.collpack.kms.common.messenger.";
		String title 	= user.getUserName()+"님으로부터 쪽지가 도착했습니다.";
		String contents = message.getContents();

		

		String receiverList = message.getReceiverList().replaceAll(" ", "");
		receiverList = receiverList.replace("[", "");
		receiverList = receiverList.replace("]", "");
		String[] arrayReceive;
		arrayReceive = receiverList.split(",");
		for (int i = 0; i < arrayReceive.length; i++) {
			String[] arrayReceiveUserList;
			arrayReceiveUserList = arrayReceive[i].split("/");
			//메시지 보내기
			atmc.addMessage(arrayReceiveUserList[0], user.getUserName(), contents.toString(), "", title,"smspop");
			atmc.send();
		}

		return "redirect:/support/message/messageNew.do?returnMsg=SAVE";
	}

	/**
	 * TODO 수신자 만들기 (이름 외 00인 ) 형태
	 * 
	 * @param receiverList 수신자리스트
	 * @param userNum 화면에 보여질 수신자 이름수
	 * @return receiverView 화면에 userNum만큼의 (이름 외 00인 ) 으로 표현된다.
	 */
	public String receiverMaker(String receiverList, int userNum, String langType) {
		String receiverView = "";
		String[] arrayReceive;
		arrayReceive = receiverList.split(",");
		User user = (User) getRequestAttribute("ikep.user");
		String overPer = messageSource.getMessage("message.support.message.detail.overPer", null,
				new Locale(user.getLocaleCode()));
		String person = messageSource.getMessage("message.support.message.detail.person", null,
				new Locale(user.getLocaleCode()));
		String[] arrayReceiveUser;
		if (userNum < arrayReceive.length) {
			for (int i = 0; i < userNum; i++) {
				arrayReceiveUser = arrayReceive[i].split("/");
				String userName = "";
				User guestUser = userService.read(arrayReceiveUser[0].trim());
				if (guestUser == null) {
					userName = arrayReceiveUser[0];
					if (arrayReceiveUser.length > 1) {
						userName = arrayReceiveUser[1];
					}
				} else {
					if (langType.equals("en")) {
						userName = guestUser.getUserEnglishName();
					} else {
						userName = guestUser.getUserName();
					}
				}
				if (i == 0) {
					receiverView = userName;
				} else {
					receiverView = receiverView + ", " + userName;
				}
			}
			receiverView = receiverView + " " + overPer + " " + (arrayReceive.length - userNum) + person;
		} else {
			for (int i = 0; i < arrayReceive.length; i++) {
				arrayReceiveUser = arrayReceive[i].split("/");
				String userName = "";
				User guestUser = userService.read(arrayReceiveUser[0].trim());
				if (guestUser == null) {
					userName = arrayReceiveUser[0];
					if (arrayReceiveUser.length > 1) {
						userName = arrayReceiveUser[1];
					}
				} else {
					if (langType.equals("en")) {
						userName = guestUser.getUserEnglishName();
					} else {
						userName = guestUser.getUserName();
					}
				}
				if (i == 0) {
					receiverView = userName;
				} else {
					receiverView = receiverView + ", " + userName;
				}
			}
		}

		return receiverView;
	}

	/**
	 * TODO 리스트 쪽지내용 Title 만들기
	 * 
	 * @param messageContents 쪽지내용
	 * @param wordNum 리스트에 보여질 글자수
	 * @return messageTitle 첫번째 행을 보여주거나 wordNum 만큼의 글을 보여준다.
	 */
	public String messageTitleMaker(String messageContents, int wordNum) {

		String messageTitle = "";
		int lineWord = messageContents.indexOf("\r");
		if (lineWord > 1 && lineWord < wordNum) {
			messageTitle = messageContents.substring(0, lineWord);
		} else if (messageContents.length() > wordNum) {
			messageTitle = messageContents.substring(0, wordNum);
		} else {
			messageTitle = messageContents;
		}
		return messageTitle;
	}

	/**
	 * TODO 수신함 LIST 삭제 Controller
	 * 
	 * @param searchCondition
	 * @param checkboxMessageItem
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteReceiveList")
	public ModelAndView deleteReceiveList(MessageSearchCondition searchCondition,
			@RequestParam("checkboxMessageItem") String[] checkboxMessageItem, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		for (int i = 0; i < checkboxMessageItem.length; i++) {
			Message message = new Message();
			message.setMessageId(checkboxMessageItem[i]);
			message.setUserId(user.getUserId());
			messageList.add(message);
		}
		this.messageService.deleteReceiveMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageListView.do");
	}

	/**
	 * TODO 수신 쪽지 LIST 보관함으로 이동 Controller
	 * 
	 * @param searchCondition
	 * @param checkboxMessageItem
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/keepReceiveList")
	public ModelAndView keepReceiveList(MessageSearchCondition searchCondition,
			@RequestParam("checkboxMessageItem") String[] checkboxMessageItem, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		for (int i = 0; i < checkboxMessageItem.length; i++) {
			Message message = new Message();
			message.setMessageId(checkboxMessageItem[i]);
			message.setUserId(user.getUserId());
			messageList.add(message);
		}
		this.messageService.keepReceiveMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageListView.do");
	}

	/**
	 * TODO 읽은 쪽지 삭제 Controller
	 * 
	 * @param searchCondition
	 * @param readMessageId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteReceiveReadMessage")
	public ModelAndView deleteReceiveReadMessage(MessageSearchCondition searchCondition,
			@RequestParam("readMessageId") String readMessageId, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		Message message = new Message();
		message.setMessageId(readMessageId);
		message.setUserId(user.getUserId());
		messageList.add(message);
		this.messageService.deleteReceiveMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageListView.do");
	}

	/**
	 * TODO 읽은 쪽지 보관함 이동 Controller
	 * 
	 * @param searchCondition
	 * @param readMessageId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/keepReceiveReadMessage")
	public ModelAndView keepReceiveReadMessage(MessageSearchCondition searchCondition,
			@RequestParam("readMessageId") String readMessageId, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		Message message = new Message();
		message.setMessageId(readMessageId);
		message.setUserId(user.getUserId());
		messageList.add(message);
		this.messageService.keepReceiveMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageListView.do");
	}

	/**
	 * TODO 보낸 쪽지LIST 삭제 Controller
	 * 
	 * @param searchCondition
	 * @param checkboxMessageItem
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteSendList")
	public ModelAndView deleteSendList(MessageSearchCondition searchCondition,
			@RequestParam("checkboxMessageItem") String[] checkboxMessageItem, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		for (int i = 0; i < checkboxMessageItem.length; i++) {
			Message message = new Message();
			message.setMessageId(checkboxMessageItem[i]);
			message.setUserId(user.getUserId());
			messageList.add(message);
		}
		this.messageService.deleteSendMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageSendListView.do");
	}

	/**
	 * TODO 보낸쪽지LIST 보관함 이동 Controller
	 * 
	 * @param searchCondition
	 * @param checkboxMessageItem
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/keepSendList")
	public ModelAndView keepSendList(MessageSearchCondition searchCondition,
			@RequestParam("checkboxMessageItem") String[] checkboxMessageItem, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		for (int i = 0; i < checkboxMessageItem.length; i++) {
			Message message = new Message();
			message.setMessageId(checkboxMessageItem[i]);
			message.setUserId(user.getUserId());
			messageList.add(message);
		}
		this.messageService.keepSendMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageStoreListView.do");
	}

	/**
	 * TODO 보낸쪽지 단건 삭제 Controller
	 * 
	 * @param searchCondition
	 * @param readMessageId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteSendReadMessage")
	public ModelAndView deleteSendReadMessage(MessageSearchCondition searchCondition,
			@RequestParam("readMessageId") String readMessageId, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		Message message = new Message();
		message.setMessageId(readMessageId);
		message.setUserId(user.getUserId());
		messageList.add(message);
		this.messageService.deleteSendMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageSendListView.do");
	}

	/**
	 * TODO 보낸 쪽지 단건 보관함 이동 Controller
	 * 
	 * @param searchCondition
	 * @param readMessageId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/keepSendReadMessage")
	public ModelAndView keepSendReadMessage(MessageSearchCondition searchCondition,
			@RequestParam("readMessageId") String readMessageId, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		Message message = new Message();
		message.setMessageId(readMessageId);
		message.setUserId(user.getUserId());
		messageList.add(message);
		this.messageService.keepSendMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageSendListView.do");
	}

	/**
	 * TODO 보관함 쪽지LIST 삭제 Controller
	 * 
	 * @param searchCondition
	 * @param checkboxMessageItem
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteStoreList")
	public ModelAndView deleteStoreList(MessageSearchCondition searchCondition,
			@RequestParam("checkboxMessageItem") String[] checkboxMessageItem, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		for (int i = 0; i < checkboxMessageItem.length; i++) {
			Message message = new Message();
			message.setMessageId(checkboxMessageItem[i]);
			message.setUserId(user.getUserId());
			messageList.add(message);
		}
		this.messageService.deleteStoreMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageStoreListView.do");
	}

	/**
	 * TODO 보관함 쪽지 단건 삭제 Controller
	 * 
	 * @param searchCondition
	 * @param readMessageId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteStoreReadMessage")
	public ModelAndView deleteStoreReadMessage(MessageSearchCondition searchCondition,
			@RequestParam("readMessageId") String readMessageId, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		List<Message> messageList = new ArrayList<Message>();
		Message message = new Message();
		message.setMessageId(readMessageId);
		message.setUserId(user.getUserId());
		messageList.add(message);
		this.messageService.deleteStoreMessage(messageList);

		return new ModelAndView("redirect:/support/message/messageStoreListView.do");
	}

	/**
	 * TODO 보낸쪽지 회수 Controller
	 * 
	 * @param searchCondition
	 * @param readMessageId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/withdrawMessage")
	public ModelAndView withdrawMessage(MessageSearchCondition searchCondition,
			@RequestParam("readMessageId") String readMessageId, SessionStatus status) {

		String resultCode = this.messageService.withdrawMessage(readMessageId);

		return new ModelAndView("redirect:/support/message/messageSendListView.do").addObject("resultCode", resultCode);
	}

	/**
	 * TODO 관리자 설정정보변경 Controller
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/messageAdminSeting")
	public ModelAndView messageAdminSeting(MessageSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();

		User user = (User) getRequestAttribute("ikep.user");

		MessageAdmin messageAdmin = new MessageAdmin();
		messageAdmin = this.messageAdminService.read(user.getPortalId());

		MessageAdmin setmessageAdmin = new MessageAdmin();
		MessageCode matchCode = new MessageCode();
		if (messageAdmin == null) {
			setmessageAdmin.setPortalId(user.getPortalId());
			setmessageAdmin.setMaxMonthFilesize(matchCode.getMaxMonthFilesize().get(0).getKey());
			setmessageAdmin.setMaxStoredFilesize(matchCode.getMaxStoredFilesize().get(0).getKey());
			setmessageAdmin.setMaxAttachFilesize(matchCode.getMaxAttachFilesize().get(0).getKey());
			setmessageAdmin.setMaxReceiverCount(matchCode.getMaxReceiverCount().get(0).getKey());
			setmessageAdmin.setKeepDays(matchCode.getKeepDays().get(0).getKey());
			messageAdmin = setmessageAdmin;
		} else {
			String checkYn = "N";
			for (int i = 0; i < matchCode.getMaxMonthFilesize().size(); i++) {
				if (messageAdmin.getMaxMonthFilesize().equals(matchCode.getMaxMonthFilesize().get(i).getKey())) {
					checkYn = "Y";
					setmessageAdmin.setMaxMonthFilesize(messageAdmin.getMaxMonthFilesize());
					break;
				}
			}
			if (checkYn.equals("N")) {
				setmessageAdmin.setMaxMonthFilesize(0);
			}

			checkYn = "N";
			for (int i = 0; i < matchCode.getMaxStoredFilesize().size(); i++) {
				if (messageAdmin.getMaxStoredFilesize().equals(matchCode.getMaxStoredFilesize().get(i).getKey())) {
					checkYn = "Y";
					setmessageAdmin.setMaxStoredFilesize(messageAdmin.getMaxStoredFilesize());
					break;
				}
			}
			if (checkYn.equals("N")) {
				setmessageAdmin.setMaxStoredFilesize(0);
			}

			checkYn = "N";
			for (int i = 0; i < matchCode.getMaxAttachFilesize().size(); i++) {
				if (messageAdmin.getMaxAttachFilesize().equals(matchCode.getMaxAttachFilesize().get(i).getKey())) {
					checkYn = "Y";
					setmessageAdmin.setMaxAttachFilesize(messageAdmin.getMaxAttachFilesize());
					break;
				}
			}
			if (checkYn.equals("N")) {
				setmessageAdmin.setMaxAttachFilesize(0);
			}

			checkYn = "N";
			for (int i = 0; i < matchCode.getMaxReceiverCount().size(); i++) {
				if (messageAdmin.getMaxReceiverCount().equals(matchCode.getMaxReceiverCount().get(i).getKey())) {
					checkYn = "Y";
					setmessageAdmin.setMaxReceiverCount(messageAdmin.getMaxReceiverCount());
					break;
				}
			}
			if (checkYn.equals("N")) {
				setmessageAdmin.setMaxReceiverCount(0);
			}

			checkYn = "N";
			for (int i = 0; i < matchCode.getKeepDays().size(); i++) {
				if (messageAdmin.getKeepDays().equals(matchCode.getKeepDays().get(i).getKey())) {
					checkYn = "Y";
					setmessageAdmin.setKeepDays(messageAdmin.getKeepDays());
					break;
				}
			}
			if (checkYn.equals("N")) {
				setmessageAdmin.setKeepDays(0);
			}
		}

		int newMessage = this.messageService.countNewMessage(user.getUserId());

		String adminYn = checkSystemAdmin(user);

		MessageAdmin userMessageAdmin = new MessageAdmin();
		userMessageAdmin = this.messageAdminService.getUserAdmin(user.getUserId());

		List<MessageChart> messageChart = null;

		if (searchCondition.getSearchChart() != null && searchCondition.getSearchChart().equals("weekChart")) {
			messageChart = this.messageAdminService.messageWeekChartList();
		} else {
			messageChart = this.messageAdminService.messageDayChartList();
		}

		modelAndView.setViewName("support/message/messageAdminSeting");
		modelAndView.addObject("messageCode", new MessageCode());
		modelAndView.addObject("messageAdmin", messageAdmin);
		modelAndView.addObject("userMessageAdmin", userMessageAdmin);
		modelAndView.addObject("setmessageAdmin", setmessageAdmin);
		modelAndView.addObject("messageChart", messageChart);
		modelAndView.addObject("searchCondition", searchCondition);
		modelAndView.addObject("newMessage", newMessage);
		modelAndView.addObject("viewCode", "T");
		modelAndView.addObject("adminYn", adminYn);
		return modelAndView;
	}

	/**
	 * TODO 메시지 Setting 값 입력 AJAX Controller
	 * 
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "messageSettingUpdate")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Integer messageSettingUpdate(@RequestParam("columnId") String columnId,
			@RequestParam("columnValue") Integer columnValue) {
		try {
			MessageAdmin messageAdmin = new MessageAdmin();
			User user = (User) getRequestAttribute("ikep.user");
			messageAdmin.setPortalId(user.getPortalId());
			log.debug("----------------------------------[" + columnId + "]---------------------------");
			if (columnId.equals("maxMonthFilesize")) {
				messageAdmin.setMaxMonthFilesize(columnValue);
			}
			if (columnId.equals("maxStoredFilesize")) {
				messageAdmin.setMaxStoredFilesize(columnValue);
			}
			if (columnId.equals("maxAttachFilesize")) {
				messageAdmin.setMaxAttachFilesize(columnValue);
			}
			if (columnId.equals("maxReceiverCount")) {
				messageAdmin.setMaxReceiverCount(columnValue);
			}
			if (columnId.equals("keepDays")) {
				messageAdmin.setKeepDays(columnValue);
			}
			if (columnId.equals("diskSize")) {
				messageAdmin.setDiskSize(columnValue);
			}
			if (columnId.equals("managerAlarmRatio")) {
				messageAdmin.setManagerAlarmRatio(columnValue);
			}
			this.messageAdminService.create(messageAdmin);
		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);

		}
		return columnValue;
	}

	/**
	 * TODO 사용자별 발송용량 정보 Controller
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/messageUserVolumnInfo")
	public ModelAndView messageUserVolumnInfo(MessageMonitorSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();

		User user = (User) getRequestAttribute("ikep.user");

		MessageAdmin messageAdmin = new MessageAdmin();
		messageAdmin = this.messageAdminService.read(user.getPortalId());

		int newMessage = this.messageService.countNewMessage(user.getUserId());

		String adminYn = checkSystemAdmin(user);

		MessageAdmin userMessageAdmin = new MessageAdmin();
		userMessageAdmin = this.messageAdminService.getUserAdmin(user.getUserId());

		searchCondition.setPortalId(user.getPortalId());
		SearchResult<MessageMonitor> searchResult = this.messageAdminService.getUserVolumnInfoList(searchCondition);

		modelAndView.setViewName("support/message/messageUserVolumnInfo");
		modelAndView.addObject("messageAdmin", messageAdmin);
		modelAndView.addObject("newMessage", newMessage);
		modelAndView.addObject("messageCode", new MessageCode());
		modelAndView.addObject("userMessageAdmin", userMessageAdmin);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("viewCode", "V");
		modelAndView.addObject("adminYn", adminYn);

		return modelAndView;
	}

	/**
	 * TODO 개인별 월별 최대 발송 용량 예외적용 Controller
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/messageSpecialUser")
	public ModelAndView messageSpecialUser(MessageMonitorSearchCondition searchCondition, BindingResult result,
			SessionStatus status) {
		ModelAndView modelAndView = new ModelAndView();

		User user = (User) getRequestAttribute("ikep.user");

		MessageAdmin messageAdmin = new MessageAdmin();
		messageAdmin = this.messageAdminService.read(user.getPortalId());

		int newMessage = this.messageService.countNewMessage(user.getUserId());

		String adminYn = checkSystemAdmin(user);

		MessageAdmin userMessageAdmin = new MessageAdmin();
		userMessageAdmin = this.messageAdminService.getUserAdmin(user.getUserId());

		SearchResult<MessageSpecialUser> searchResult = this.messageSpecialUserService
				.getSpecialUserList(searchCondition);

		modelAndView.setViewName("support/message/messageSpecialUser");
		modelAndView.addObject("messageAdmin", messageAdmin);
		modelAndView.addObject("userMessageAdmin", userMessageAdmin);
		modelAndView.addObject("messageCode", new MessageCode());
		modelAndView.addObject("newMessage", newMessage);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("searchResult", searchResult);
		modelAndView.addObject("viewCode", "E");
		modelAndView.addObject("adminYn", adminYn);
		return modelAndView;
	}

	/**
	 * TODO 사용자별 예외 월별발송용량 입력 AJAX Controller
	 * 
	 * @param userId, maxMonthFilesize
	 * @return
	 */
	@RequestMapping(value = "messageSpecialUserUpdate")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void messageSpecialUserUpdate(@RequestParam("userId") String userId,
			@RequestParam("maxMonthFilesize") Integer maxMonthFilesize) {
		try {
			MessageSpecialUser messageSpecialUser = new MessageSpecialUser();
			messageSpecialUser.setUserId(userId);
			messageSpecialUser.setMaxMonthFilesize(maxMonthFilesize);
			this.messageSpecialUserService.create(messageSpecialUser);
		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
	}

	/**
	 * TODO 사용자별 예외 월별발송용량LIST 삭제 Controller
	 * 
	 * @param searchCondition
	 * @param checkboxMessageItem
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteSpecialUserList")
	public ModelAndView deleteSpecialUserList(MessageSearchCondition searchCondition,
			@RequestParam("checkboxMessageItem") String[] checkboxMessageItem, SessionStatus status) {

		for (int i = 0; i < checkboxMessageItem.length; i++) {
			this.messageSpecialUserService.delete(checkboxMessageItem[i]);
		}

		return new ModelAndView("redirect:/support/message/messageSpecialUser.do");
	}

	/**
	 * TODO 수신확인 List AJAX Controller
	 * 
	 * @param userId, maxMonthFilesize
	 * @return
	 */
	@RequestMapping(value = "messageReceiveList")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<MessageReceive> messageReceiveList(@RequestParam("messageId") String messageId,
			@RequestParam("rowNum") Integer rowNum) {
		List<MessageReceive> messageReceiveList = new ArrayList<MessageReceive>();
		try {
			messageReceiveList = this.messageService.messageReceiveList(messageId, rowNum);
		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return messageReceiveList;
	}

	/**
	 * TODO 메인화면 신규메세지 count AJAX Controller
	 * 
	 * @param
	 * @return returnValue
	 */
	@RequestMapping(value = "/getMessageCount")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String getMessageCount() {
		User user = (User) getRequestAttribute("ikep.user");
		int count = this.messageService.countNewMessage(user.getUserId()); // 얻고자
																			// 하는
																			// 개수를
																			// 나타냄.

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", count);

		String returnValue = jsonObj.toString(); // {"count":개수} 형태의 json 타입으로
													// return 함.

		return returnValue;
	}

	/**
	 * @valid 사용시 groups 사용가능하다록 설정하는부분
	 * @param result
	 * @param o
	 * @param classes
	 * @return
	 */
	protected boolean isValid(Errors errors, Object o, Class<?>... classes) {
		Class<?>[] clazz = null;

		if (classes == null || classes.length == 0 || classes[0] == null) {
			clazz = new Class<?>[] { Default.class };
		} else {
			clazz = classes;
		}

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(o, clazz);

		for (ConstraintViolation<Object> violation : violations) {
			String field = violation.getPropertyPath().toString();
			FieldError fieldError = errors.getFieldError(field);
			ConstraintDescriptor<?> constraintDescriptor = violation.getConstraintDescriptor();
			String constraintName = constraintDescriptor.getAnnotation().annotationType().getSimpleName();
			if (fieldError == null || !fieldError.isBindingFailure()) {
				try {
					errors.rejectValue(
							field,
							constraintName,
							getArgumentsForConstraint(errors.getObjectName(), field,
									violation.getConstraintDescriptor()), violation.getMessage());
				} catch (NotReadablePropertyException ex) {
					throw new IllegalStateException("JSR-303 validated property '" + field
							+ "' does not have a corresponding accessor for Spring data binding - "
							+ "check your DataBinder's configuration (bean property versus direct field access)", ex);
				}
			}
		}

		return violations.size() == 0 && !errors.hasErrors();
	}

	/**
	 * valid에서 argment받을수 있도록 수정 message_xxx.propeties Size.survey.title = {0}을
	 * 최소{1}에서 최대{2} 글자수 사이만 입력해주세요 사용가능
	 * 
	 * @param objectName
	 * @param field
	 * @param descriptor
	 * @return
	 */
	protected Object[] getArgumentsForConstraint(String objectName, String field, ConstraintDescriptor<?> descriptor) {
		List<Object> arguments = new LinkedList<Object>();
		String[] codes = new String[] {
				MessageConstance.MESSAGE_PREFIX + Errors.NESTED_PATH_SEPARATOR + objectName
						+ Errors.NESTED_PATH_SEPARATOR + field, field };
		// System.out.println(MESSAGE_PREFIX + Errors.NESTED_PATH_SEPARATOR +
		// objectName + Errors.NESTED_PATH_SEPARATOR + field);
		arguments.add(new DefaultMessageSourceResolvable(codes, field));
		// Using a TreeMap for alphabetical ordering of attribute names
		// Map<String, Object> attributesToExpose = new TreeMap<String,
		// Object>();
		for (Map.Entry<String, Object> entry : descriptor.getAttributes().entrySet()) {
			String attributeName = entry.getKey();
			Object attributeValue = entry.getValue();
			if (!internalAnnotationAttributes.contains(attributeName)) {
				// System.out.println(attributeName + ":" + attributeValue
				// +"------------------------------------------");
				// attributesToExpose.put(attributeName, attributeValue);
				arguments.add(attributeValue);
			}
		}

		return arguments.toArray(new Object[arguments.size()]);
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
		if (isSystemAdmin) {
			adminYn = "Y";
		}
		return adminYn;
	}

	/**
	 * TODO 메인화면 신규메세지 알림 AJAX Controller
	 * 
	 * @param
	 * @return returnValue
	 */
	@RequestMapping(value = "/newMessageArrived")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String newMessageArrived() {
		User user = (User) getRequestAttribute("ikep.user");
		MessageAlarm messageAlarm = new MessageAlarm();
		Integer count = 0;
		String messageId = null;
		String title = null;
		String senderName = null;
		String senderEnglishName = null;
		String senderJobTitle = null;
		String senderJobTitleEnglish = null;
		String receiverDate = null;
		messageAlarm = this.messageService.getNewMessageArrived(user.getUserId());

		if (messageAlarm.getMessageId() != null && !messageAlarm.getMessageId().equals("")) {
			Message message = this.messageService.read(messageAlarm.getMessageId());
			message.setSendDate(timeZoneSupportService.convertTimeZone(message.getSendDate()));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			User senderUser = userService.read(message.getSenderId());
			messageAlarm.setUserId(user.getUserId());
			this.messageService.updateMessageAlarm(messageAlarm);
			count = messageAlarm.getNewCount();
			messageId = message.getMessageId();
			if (message.getContents() == null) {
				title = "";
			} else {
				title = messageTitleMaker(MessageUtil.getText(message.getContents(), 1), MessageConstance.titleWordNum);
			}
			senderName = message.getSenderName();
			senderEnglishName = message.getSenderEnglishName();
			senderJobTitle = senderUser.getJobTitleName();
			senderJobTitleEnglish = senderUser.getJobTitleEnglishName();
			receiverDate = dateFormat.format(message.getSendDate());
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", count);
		jsonObj.put("messageId", messageId);
		jsonObj.put("title", title);
		jsonObj.put("senderName", senderName);
		jsonObj.put("senderEnglishName", senderEnglishName);
		jsonObj.put("senderJobTitle", senderJobTitle);
		jsonObj.put("senderJobTitleEnglish", senderJobTitleEnglish);
		jsonObj.put("receiverDate", receiverDate);

		String returnValue = jsonObj.toString(); // {"count":개수} 형태의 json 타입으로
													// return 함.

		return returnValue;
	}
}
