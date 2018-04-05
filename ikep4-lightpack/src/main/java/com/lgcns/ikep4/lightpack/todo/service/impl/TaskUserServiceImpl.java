/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.service.impl;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.lightpack.todo.dao.TaskDao;
import com.lgcns.ikep4.lightpack.todo.dao.TaskUserDao;
import com.lgcns.ikep4.lightpack.todo.dao.UserSettingDao;
import com.lgcns.ikep4.lightpack.todo.model.Task;
import com.lgcns.ikep4.lightpack.todo.model.TaskUser;
import com.lgcns.ikep4.lightpack.todo.model.TaskUserPk;
import com.lgcns.ikep4.lightpack.todo.model.TodoConstants;
import com.lgcns.ikep4.lightpack.todo.model.UserSetting;
import com.lgcns.ikep4.lightpack.todo.service.TaskUserService;
import com.lgcns.ikep4.lightpack.todo.service.TodoService;

/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskUserServiceImpl.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Service 
@Transactional
public class TaskUserServiceImpl extends GenericServiceImpl<TaskUser, TaskUserPk> implements TaskUserService {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private TaskUserDao taskUserDao;
	@Autowired
	private TaskDao taskDao;
	@Autowired
	private UserSettingDao userSettingDao;

	@Autowired
	private FileService fileService;
	@Autowired
	private MessageOutsideService messageOutsideService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private SmsService smsService;
	@Autowired
	private UserService userService;

	@Autowired
	private TodoService todoService;

	public TaskUserPk create(TaskUser taskUser) {
		return taskUserDao.create(taskUser);
	}

	public boolean exists(TaskUserPk taskUserPk) {
		return taskUserDao.exists(taskUserPk);
	}

	public TaskUser read(TaskUserPk taskUserPk) {
		return taskUserDao.get(taskUserPk);
	}

	public void delete(TaskUserPk taskUserPk) {
		taskUserDao.remove(taskUserPk);
	}
	//할일 조회
	public TaskUser getTaskUser(TaskUserPk taskUserPk) {
		TaskUser taskUser = taskUserDao.getTaskUser(taskUserPk);
		List<FileData> fileDataList = fileService.getItemFile(taskUserPk.getTaskId() + "_" + taskUserPk.getWorkerId(), "N");
		taskUser.setFileDataList(fileDataList);
		/*
		if (fileDataList.size() > 0){
			taskUser.setFileDataList(fileDataList);
		}
		*/
		return taskUser;
	}

	public void update(TaskUser taskUser) {
		taskUserDao.update(taskUser);
	}
	
	//작업담당자 정보 조회
	public List<TaskUser> listTaskUserByTaskId(String taskId) {
		List<TaskUser> taskUserList = taskUserDao.listTaskUserByTaskId(taskId);
		for(int i=0; i<taskUserList.size(); i++) {
			List<FileData> fileDataList = fileService.getItemFile(taskId + "_" + taskUserList.get(i).getWorkerId(), "N");
			taskUserList.get(i).setFileDataList(fileDataList);
		}
		
		return taskUserList;
	}
	//할일 저장(임시저장, 완료저장)
	public void updateTaskUser(TaskUser taskUser, User user) {
		if(taskUser.getFileLinkList() != null) {
			fileService.saveFileLink(taskUser.getFileLinkList(), taskUser.getTaskId() + "_" + taskUser.getWorkerId(), IKepConstant.ITEM_TYPE_CODE_TODO, user);
			taskUser.setUserAttachCount(taskUser.getFileLinkList().size());
		} else {
			taskUser.setUserAttachCount(0);
		}
		
		TaskUserPk taskUserPk = new TaskUserPk();
		taskUserPk.setWorkerId(taskUser.getWorkerId());
		taskUserPk.setTaskId(taskUser.getTaskId());
		TaskUser oldTaskUser = taskUserDao.getTaskUser(taskUserPk);
		
		taskUser.setUpdaterId(user.getUserId());
		taskUser.setUpdaterName(user.getUserName());
		taskUserDao.update(taskUser);
		
		Task tmpTask = taskDao.getTask(taskUser.getTaskId());
		
		//완료 처리 일 경우
		if(oldTaskUser.getUserStatus().equals("A") && taskUser.getUserStatus().equals("B")) {
			//todoService.todoComplete(TodoConstants.TODO_SYSTEM_CODE, TodoConstants.TODO_SUBWORK_CODE, taskUser.getTaskId(), taskUser.getWorkerId());
			todoService.todoComplete(TodoConstants.TODO_SYSTEM_CODE, tmpTask.getSubworkCode().toString(), taskUser.getTaskId(), taskUser.getWorkerId());
			
			int runningTodo = taskUserDao.countRestReverseStatus(taskUser);
			if(runningTodo == 0) {
				Task task = new Task();
				task.setTaskId(taskUser.getTaskId());
				task.setUpdaterId(taskUser.getUpdaterId());
				task.setUpdaterName(taskUser.getUpdaterName());
				task.setTitle(" ");
				taskDao.updateCompleteStatus(task);
				
				//지시작업 종료 알림
				UserSetting userSetting = userSettingDao.get(oldTaskUser.getRegisterId());
				if(userSetting != null) {
					String notiType = userSetting.getTaskEndNotiType();
					if(notiType.equals("S")) {//sms
						User receiverUser = userService.read(oldTaskUser.getRegisterId());
						task = taskDao.get(taskUser.getTaskId());
						Sms sms = new Sms();
						String[] receiverIds = {receiverUser.getUserId()};
						String[] receiverPhonenos = {receiverUser.getMobile()};
						sms.setReceiverIds(receiverIds);
						sms.setReceiverPhonenos(receiverPhonenos);
						sms.setContents(messageSource.getMessage("ui.lightpack.todo.message.completedText.pre", null, new Locale(user.getLocaleCode())) +
								task.getTitle() + messageSource.getMessage("ui.lightpack.todo.message.completedText.post", null, new Locale(user.getLocaleCode())));
						sms.setRegisterId(user.getUserId());
						sms.setRegisterName(user.getUserName());
						smsService.create(sms);
					} else if(notiType.equals("M")) {//쪽지
						 task = taskDao.get(taskUser.getTaskId());
						 Message message = new Message();
						 String senderId = user.getUserId();
						 String senderName = user.getUserName();
						 String contents = messageSource.getMessage("ui.lightpack.todo.message.completedText.pre", null, new Locale(user.getLocaleCode())) +
							task.getTitle() + messageSource.getMessage("ui.lightpack.todo.message.completedText.post", null, new Locale(user.getLocaleCode()));
						 String receiverList = oldTaskUser.getRegisterId() + "/" + oldTaskUser.getRegisterName();
						 message.setSenderId(senderId);
						 message.setSenderName(senderName);
						 message.setContents(contents);
						 message.setReceiverList(receiverList);
						 messageOutsideService.sendMessageOutside(message, user);
					}
				}
			}
		}
	}
	
	//할일 저장(임시저장, 완료저장)
	public void updateMyTaskUser(TaskUser taskUser, User user) {
		/*
		if(taskUser.getFileLinkList() != null) {
			fileService.saveFileLink(taskUser.getFileLinkList(), taskUser.getTaskId() + "_" + taskUser.getWorkerId(), IKepConstant.ITEM_TYPE_CODE_TODO, user);
			taskUser.setUserAttachCount(taskUser.getFileLinkList().size());
		} else {
			taskUser.setUserAttachCount(0);
		}
		*/
		TaskUserPk taskUserPk = new TaskUserPk();
		taskUserPk.setWorkerId(taskUser.getWorkerId());
		taskUserPk.setTaskId(taskUser.getTaskId());
		TaskUser oldTaskUser = taskUserDao.getTaskUser(taskUserPk);
		
		taskUser.setUpdaterId(user.getUserId());
		taskUser.setUpdaterName(user.getUserName());
		taskUserDao.update(taskUser);
		
		Task tmpTask = taskDao.getTask(taskUser.getTaskId());

		//완료 처리 일 경우
		if(oldTaskUser.getUserStatus().equals("A") && taskUser.getUserStatus().equals("B")) {
			//todoService.todoComplete(TodoConstants.TODO_SYSTEM_CODE, "MYTASK", taskUser.getTaskId(), taskUser.getWorkerId());
			todoService.todoComplete(TodoConstants.TODO_SYSTEM_CODE, tmpTask.getSubworkCode().toString(), taskUser.getTaskId(), taskUser.getWorkerId());
			
			int runningTodo = taskUserDao.countRestReverseStatus(taskUser);
			if(runningTodo == 0) {
				Task task = new Task();
				task.setTaskId(taskUser.getTaskId());
				task.setUpdaterId(taskUser.getUpdaterId());
				task.setUpdaterName(taskUser.getUpdaterName());
				task.setTitle(" ");
				taskDao.updateCompleteStatus(task);
				/*
				//지시작업 종료 알림
				UserSetting userSetting = userSettingDao.get(oldTaskUser.getRegisterId());
				if(userSetting != null) {
					String notiType = userSetting.getTaskEndNotiType();
					if(notiType.equals("S")) {//sms
						User receiverUser = userService.read(oldTaskUser.getRegisterId());
						task = taskDao.get(taskUser.getTaskId());
						Sms sms = new Sms();
						String[] receiverIds = {receiverUser.getUserId()};
						String[] receiverPhonenos = {receiverUser.getMobile()};
						sms.setReceiverIds(receiverIds);
						sms.setReceiverPhonenos(receiverPhonenos);
						sms.setContents(messageSource.getMessage("ui.lightpack.todo.message.completedText.pre", null, new Locale(user.getLocaleCode())) +
								task.getTitle() + messageSource.getMessage("ui.lightpack.todo.message.completedText.post", null, new Locale(user.getLocaleCode())));
						sms.setRegisterId(user.getUserId());
						sms.setRegisterName(user.getUserName());
						smsService.create(sms);
					} else if(notiType.equals("M")) {//쪽지
						 task = taskDao.get(taskUser.getTaskId());
						 Message message = new Message();
						 String senderId = user.getUserId();
						 String senderName = user.getUserName();
						 String contents = messageSource.getMessage("ui.lightpack.todo.message.completedText.pre", null, new Locale(user.getLocaleCode())) +
							task.getTitle() + messageSource.getMessage("ui.lightpack.todo.message.completedText.post", null, new Locale(user.getLocaleCode()));
						 String receiverList = oldTaskUser.getRegisterId() + "/" + oldTaskUser.getRegisterName();
						 message.setSenderId(senderId);
						 message.setSenderName(senderName);
						 message.setContents(contents);
						 message.setReceiverList(receiverList);
						 messageOutsideService.sendMessageOutside(message, user);
					}
				}
				*/
			}
		}
	}
}