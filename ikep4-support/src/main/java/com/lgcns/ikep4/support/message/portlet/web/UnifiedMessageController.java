package com.lgcns.ikep4.support.message.portlet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.model.PortletMessage;
import com.lgcns.ikep4.support.message.search.MessageSearchCondition;
import com.lgcns.ikep4.support.message.service.MessageService;
import com.lgcns.ikep4.support.message.service.PortletMessageService;
import com.lgcns.ikep4.support.message.util.MessageConstance;
import com.lgcns.ikep4.support.message.util.MessageUtil;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;

@Controller
@RequestMapping(value = "/support/message/portlet/unifiedMessage")
public class UnifiedMessageController extends BaseController{

	@Autowired
	private PortletMessageService portletMessageService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("support/message/portlet/unifiedMessage/normalView");
		
		User user = (User) getRequestAttribute("ikep.user");
		PortletMessage portletMessage = this.portletMessageService.read(user.getUserId());
		int listSize = 0;
		if (portletMessage != null) { listSize = portletMessage.getListNum(); }
		if (listSize < 1) { listSize = MessageConstance.portletDefaultListSize; }

		MessageSearchCondition messageSearchCondition = new MessageSearchCondition();
		messageSearchCondition.setUserId(user.getUserId());
		messageSearchCondition.setEndRowIndex(listSize);
		List<Message> messageList = (List<Message>) this.portletMessageService.unReadReceiveMessageList(messageSearchCondition);
		if (messageList != null && messageList.size() > 0) {
			for (int i = 0; i < messageList.size(); i++) {
				messageList.get(i).setContents(messageTitleMaker(MessageUtil.getText(messageList.get(i).getContents(),1),MessageConstance.titleWordNum));
			}
		}
		mav.addObject("listSize", listSize);
		mav.addObject("messageList", messageList);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
	
	@RequestMapping(value = "messageView")  
	public ModelAndView readMessage(@RequestParam("messageId") String messageId) {   
		ModelAndView modelAndView = new ModelAndView(); 
		User user = (User) getRequestAttribute("ikep.user");
		Message message = new Message();
		User senderUser = new User();
		MessageSearchCondition messageSearchCondition = new MessageSearchCondition();
		messageSearchCondition.setMessageId(messageId);
		messageSearchCondition.setUserId(user.getUserId());
		if (this.messageService.readCheck(messageSearchCondition)) {
			message = this.messageService.read(messageId);
			this.messageService.insertIsRead(messageId, user.getUserId());
			senderUser = this.userService.read(message.getSenderId());
			message.setContents(message.getContents().replace("\n", "<br>"));
		}
		modelAndView.setViewName("support/message/portlet/unifiedMessage/messageView");
		modelAndView.addObject("message", message);
		modelAndView.addObject("senderUser", senderUser);
		return modelAndView;
	}
	
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("support/message/portlet/unifiedMessage/configView");
		
		User user = (User) getRequestAttribute("ikep.user");
		PortletMessage portletMessage = this.portletMessageService.read(user.getUserId());
		int listSize = 0;
		if (portletMessage != null) { listSize = portletMessage.getListNum(); }
		if (listSize < 1) { listSize = MessageConstance.portletDefaultListSize; }

		MessageSearchCondition messageSearchCondition = new MessageSearchCondition();
		messageSearchCondition.setUserId(user.getUserId());
		messageSearchCondition.setEndRowIndex(listSize);
		List<Message> messageList = (List<Message>) this.portletMessageService.unReadReceiveMessageList(messageSearchCondition);
		if (messageList != null && messageList.size() > 0) {
			for (int i = 0; i < messageList.size(); i++) {
				messageList.get(i).setContents(messageTitleMaker(MessageUtil.getText(messageList.get(i).getContents(),1),MessageConstance.titleWordNum));
			}
		}
		mav.addObject("listSize", listSize);
		mav.addObject("messageList", messageList);
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("portletId", portletId);
		
		return mav;
	}
	
	@RequestMapping(value="/updateListCount")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateListCount(@RequestParam("count") Integer count) { 
		User user = (User) getRequestAttribute("ikep.user");

		PortletMessage portletMessage = new PortletMessage();
		portletMessage.setListNum(count);
		portletMessage.setRegisterId(user.getUserId());
		portletMessage.setRegisterName(user.getUserName());
		portletMessage.setUpdaterId(user.getUserId());
		portletMessage.setUpdaterName(user.getUserName());
		this.portletMessageService.create(portletMessage);
	}
	
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("support/message/portlet/unifiedMessage/maxView");
		
		User user = (User) getRequestAttribute("ikep.user");
		PortletMessage portletMessage = this.portletMessageService.read(user.getUserId());
		int listSize = 0;
		if (portletMessage != null) { listSize = portletMessage.getListNum(); }
		if (listSize < 1) {	listSize = MessageConstance.portletDefaultListSize; }

		MessageSearchCondition messageSearchCondition = new MessageSearchCondition();
		messageSearchCondition.setUserId(user.getUserId());
		messageSearchCondition.setEndRowIndex(listSize);
		List<Message> messageList = (List<Message>) this.portletMessageService.unReadReceiveMessageList(messageSearchCondition);
		if (messageList != null && messageList.size() > 0) {
			for (int i = 0; i < messageList.size(); i++) {
				messageList.get(i).setContents(messageTitleMaker(MessageUtil.getText(messageList.get(i).getContents(),1),MessageConstance.titleWordNum));
			}
		}
		mav.addObject("listSize", listSize);
		mav.addObject("messageList", messageList);
		mav.addObject("portletConfigId", portletConfigId);
		
		return mav;
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
}