/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.lightpack.todo.dao.TaskDao;
import com.lgcns.ikep4.lightpack.todo.dao.TaskUserDao;
import com.lgcns.ikep4.lightpack.todo.dao.TodoDao;
import com.lgcns.ikep4.lightpack.todo.model.Task;
import com.lgcns.ikep4.lightpack.todo.model.TaskUser;
import com.lgcns.ikep4.lightpack.todo.model.TaskUserPk;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoConstants;
import com.lgcns.ikep4.lightpack.todo.service.TaskService;
import com.lgcns.ikep4.lightpack.todo.service.TodoService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskServiceImpl.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Service 
@Transactional
public class TaskServiceImpl extends GenericServiceImpl<Task, String> implements TaskService {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private TaskDao taskDao;
	@Autowired
	private TaskUserDao taskUserDao;
	@Autowired
	private TodoDao todoDao;	
	
	@Autowired
	private IdgenService idgenService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private FileService fileService;
    @Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private TodoService todoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailSendService mailSendService;

	public String create(Task task) {
		return taskDao.create(task);
	}

	public boolean exists(String taskId) {
		return taskDao.exists(taskId);
	}

	public Task read(String taskId) {
		return taskDao.get(taskId);
	}

	public void delete(String taskId) {
		taskDao.remove(taskId);
	}

	public void update(Task task) {
		taskDao.update(task);
	}
	//작업 등록 조회
	public Task getTask(String taskId) {
		Task tak = taskDao.getTask(taskId);
		List<FileData> fileDataList = fileService.getItemFile(taskId, "N");
		tak.setFileDataList(fileDataList);
		
		return tak;
	}

	//작업등록
	public String createTask(Task task, User user) {
		//timezone 적용
		task.setStartDate(timeZoneSupportService.convertServerTimeZone(task.getStartDate())); 
		task.setDueDate(timeZoneSupportService.convertServerTimeZone(task.getDueDate()));
		
		String etcName = task.getEtcName();  
		String[] tmp = etcName.split(",");
		List<String> workersList = new ArrayList<String>(Arrays.asList(tmp));
		task.setWorkerList(workersList);
		task.setDirectorId(user.getUserId());
		task.setRegisterId(user.getUserId());
		task.setRegisterName(user.getUserName());
		task.setUpdaterId(user.getUserId());
		task.setUpdaterName(user.getUserName());
		
		//IKEP4_TD_TASK 테이블 등록
		String taskId = idgenService.getNextId();
		if(task.getFileLinkList() != null) {
			fileService.saveFileLink(task.getFileLinkList(), taskId, IKepConstant.ITEM_TYPE_CODE_TODO, user);
			task.setTaskAttachCount(task.getFileLinkList().size());
		} else {
			task.setTaskAttachCount(0);
		}
		
		task.setTaskId(taskId);
		task.setTaskStatus("A");
		if (task.getSubworkCode()==null){
			task.setSubworkCode(TodoConstants.TODO_SUBWORK_CODE);
		}
		taskDao.create(task);
		
		//IKEP4_TD_TASK_USER, IKEP4_TD_TODO 테이블 등록
		TaskUser taskUser = new TaskUser();
		taskUser.setTaskId(taskId);
		taskUser.setUserStatus("A");
		taskUser.setRegisterId(task.getRegisterId());
		taskUser.setRegisterName(task.getRegisterName());
		taskUser.setUpdaterId(task.getUpdaterId());
		taskUser.setUpdaterName(task.getUpdaterName()); 
		
		List<String> workerList = task.getWorkerList();
		for(int i=0; i<workerList.size(); i++) {
			taskUser.setWorkerId(workerList.get(i));
			taskUserDao.create(taskUser);
			
			/*
			todoService.todoAssign(TodoConstants.TODO_SYSTEM_CODE, TodoConstants.TODO_SUBWORK_CODE, taskId, workerList.get(i),
					task.getTitle(), task.getDirectorId(), task.getDueDate(), 
					messageSource.getMessage("ui.lightpack.todo.message.taskStatusName.start", null, new Locale(user.getLocaleCode())), 
					TodoConstants.TASK_URL);
			*/
			todoService.todoAssign(TodoConstants.TODO_SYSTEM_CODE, task.getSubworkCode().toString(), taskId, workerList.get(i),
				task.getTitle(), task.getDirectorId(), task.getDueDate(), 
				messageSource.getMessage("ui.lightpack.todo.message.taskStatusName.start", null, new Locale(user.getLocaleCode())), 
				TodoConstants.TASK_URL);
			
			if (!"MYTASK".equals(task.getSubworkCode())){ //SubWork Code == "MYTASK"인 경우 mail 발송 제외
				String title = task.getRegisterName() + "님이 업무를 등록하였습니다.";
				String content = "<html><body>제목:"+task.getTitle()+"<br/><br/>지시자:"+task.getRegisterName()+"<br/><br/>업무기한:"+DateUtil.getFmtDateString(task.getDueDate(), "yyyy.MM.dd HH:mm:ss")+"<br/><br/>업무착수:"+DateUtil.getFmtDateString(task.getStartDate(), "yyyy.MM.dd HH:mm:ss")+"<br/><br/>내용:"+task.getTaskContents()+"</body></html>";
				
				Mail mail = new Mail();
				Map dataMap = new HashMap();
				User userInfo = new User();
				List<HashMap> list = new ArrayList<HashMap>();
	
				userInfo = userService.read(workerList.get(i));
				if (!StringUtil.isEmpty(userInfo.getMail()) && !StringUtil.isEmpty(userInfo.getUserName())) {
					// 수신자
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("email", userInfo.getMail());
					map.put("name", userInfo.getUserName());
					list.add(map);
				}
	
				mail.setToEmailList(list);
	
				// 제목
				mail.setTitle(title);
				// 내용
				mail.setContent(content);
				// 메일형식(txt,html,template)
				mail.setMailType(MailConstant.MAIL_TYPE_HTML);
	
				// 메일보내기(발신자,메일서버정도 등을 공통 셋팅하여 메일을 보냄)
				String returnMsg = mailSendService.sendMail(mail, dataMap, user);
			}
		}
		
		return taskId;
	}
	
	//작업수정
	public void updateTask(Task task, User user) {
		//timezone 적용
		if (task.getStartDate() != null){
			task.setStartDate(timeZoneSupportService.convertServerTimeZone(task.getStartDate()));
		}
		task.setDueDate(timeZoneSupportService.convertServerTimeZone(task.getDueDate()));
		
		String[] tmp = task.getEtcName().split(",");
		List<String> workersList = new ArrayList<String>(Arrays.asList(tmp));
		task.setWorkerList(workersList);
		task.setTaskStatus("A");
		task.setDirectorId(user.getUserId());
		task.setUpdaterId(user.getUserId());
		task.setUpdaterName(user.getUserName());
		
		Map<String, Object> workersMap = new HashMap<String, Object>();
		workersMap.put("taskId", task.getTaskId());
		workersMap.put("workerId", task.getWorkerList());
		List<TaskUser> deleteTaskUserList = taskUserDao.listDeleteWorker(workersMap);
		
		//IKEP4_TD_TASK_USER, IKEP4_TD_TODO 삭제
		boolean isDeleteWorker = false;
		for(int i=0; i<deleteTaskUserList.size(); i++) {
			isDeleteWorker = true;
			fileService.removeItemFile(deleteTaskUserList.get(i).getTaskId() + "_" + deleteTaskUserList.get(i).getWorkerId());
			taskUserDao.remove((TaskUserPk) deleteTaskUserList.get(i));
			//todoService.todoDelete(TodoConstants.TODO_SYSTEM_CODE, TodoConstants.TODO_SUBWORK_CODE, deleteTaskUserList.get(i).getTaskId(), deleteTaskUserList.get(i).getWorkerId());
			todoService.todoDelete(TodoConstants.TODO_SYSTEM_CODE, task.getSubworkCode().toString(), deleteTaskUserList.get(i).getTaskId(), deleteTaskUserList.get(i).getWorkerId());
		}
		
		//IKEP4_TD_TASK_USER, IKEP4_TD_TODO 테이블 수정/등록
		boolean isNewWorker = false;
		TaskUser taskUser = new TaskUser();
		taskUser.setTaskId(task.getTaskId());
		taskUser.setUserStatus(task.getTaskStatus());
		taskUser.setRegisterId(task.getUpdaterId());
		taskUser.setRegisterName(task.getUpdaterName());
		taskUser.setUpdaterId(task.getUpdaterId());
		taskUser.setUpdaterName(task.getUpdaterName());
		List<String> workerList = task.getWorkerList();
		for(int i=0; i<workerList.size(); i++) {
			taskUser.setWorkerId(workerList.get(i));
			if(taskUserDao.exists((TaskUserPk)taskUser)) {
				Todo todo = new Todo();
				todo.setSystemCode(TodoConstants.TODO_SYSTEM_CODE);
				//todo.setSubworkCode(TodoConstants.TODO_SUBWORK_CODE);
				todo.setSubworkCode(task.getSubworkCode());
				todo.setTaskKey(task.getTaskId());
				todo.setWorkerId(taskUser.getWorkerId());
				todo.setTitle(task.getTitle());
				todo.setDirectorId(user.getUserId());
				todo.setDueDate(task.getDueDate());
				todoDao.update(todo);
			} else {
				isNewWorker = true;
				taskUserDao.create(taskUser);
				/*
				todoService.todoAssign(TodoConstants.TODO_SYSTEM_CODE, TodoConstants.TODO_SUBWORK_CODE, task.getTaskId(), workerList.get(i),
						task.getTitle(), task.getDirectorId(), task.getDueDate(), messageSource.getMessage("ui.lightpack.todo.message.taskStatusName.start", null, new Locale(user.getLocaleCode())), TodoConstants.TASK_URL);
				*/
				todoService.todoAssign(TodoConstants.TODO_SYSTEM_CODE, task.getSubworkCode().toString(), task.getTaskId(), workerList.get(i),
						task.getTitle(), task.getDirectorId(), task.getDueDate(), messageSource.getMessage("ui.lightpack.todo.message.taskStatusName.start", null, new Locale(user.getLocaleCode())), TodoConstants.TASK_URL);
			}
		}
		
		//IKEP4_TD_TASK 테이블 수정
		if(task.getFileLinkList() != null) {
			fileService.saveFileLink(task.getFileLinkList(), task.getTaskId(), IKepConstant.ITEM_TYPE_CODE_TODO, user);
			task.setTaskAttachCount(task.getFileLinkList().size());
		} else {
			task.setTaskAttachCount(0);
		}
		
		if(isDeleteWorker && !isNewWorker) {//작업자 삭제에 의한 진행중 작업자 조회
			taskUser.setUserStatus("B"); 
			int count = taskUserDao.countRestReverseStatus(taskUser);
			if(count == 0) {
				task.setTaskStatus("B");
				taskDao.update(task);
			} else {
				taskDao.update(task);
			}
		} else {
			taskDao.update(task);
		}
	}
	
	//작업삭제
	public void deleteTask(String taskId) {
		
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);

		Task task = taskDao.getTask(taskId);
		
		if (task.getSubworkCode() == null){
			task.setSubworkCode("MYTASK");
		}
		
		if (task.getEtcName() == null){
			task.setEtcName(user.getUserId());
		}

		//todoService.deleteFromTask(TodoConstants.TODO_SYSTEM_CODE, TodoConstants.TODO_SUBWORK_CODE, taskId);
		todoService.deleteFromTask(TodoConstants.TODO_SYSTEM_CODE, task.getSubworkCode(), taskId);
		
		List<TaskUser> taskUserList = taskUserDao.listTaskUserByTaskId(taskId);
		for(int i=0; i<taskUserList.size(); i++) {
			fileService.removeItemFile(taskId + "_" + taskUserList.get(i).getWorkerId());
		}
		taskUserDao.removeByTaskId(taskId);

		fileService.removeItemFile(taskId);
		taskDao.remove(taskId);
	}
}