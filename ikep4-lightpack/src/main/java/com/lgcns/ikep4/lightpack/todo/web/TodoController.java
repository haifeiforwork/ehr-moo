/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.todo.model.MyOrder;
import com.lgcns.ikep4.lightpack.todo.model.Task;
import com.lgcns.ikep4.lightpack.todo.model.TaskUser;
import com.lgcns.ikep4.lightpack.todo.model.TaskUserPk;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoConstants;
import com.lgcns.ikep4.lightpack.todo.model.TodoPk;
import com.lgcns.ikep4.lightpack.todo.model.UserSetting;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;
import com.lgcns.ikep4.lightpack.todo.service.MyOrderService;
import com.lgcns.ikep4.lightpack.todo.service.TaskService;
import com.lgcns.ikep4.lightpack.todo.service.TaskUserService;
import com.lgcns.ikep4.lightpack.todo.service.TodoService;
import com.lgcns.ikep4.lightpack.todo.service.UserSettingService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


/**
 * Controller 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TodoController.java 19510 2012-06-26 09:35:56Z malboru80 $
 */
@Controller
@RequestMapping(value = "/lightpack/todo")
@SessionAttributes("todo")
public class TodoController extends BaseController {
	@Autowired
	private TodoService todoService;
	@Autowired
	private MyOrderService myOrderService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskUserService taskUserService;
	@Autowired
	private UserSettingService userSettingService;
	
	@Autowired
    ACLService aclService;
    @Autowired
    private TimeZoneSupportService timeZoneSupportService;

    @Autowired
	private CacheService cacheService;
    
	private static final String SUCCESS = "success";

	private static final String FAIL = "fail";

	/**
	 * myTodo Count JSON type return
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/myTodoCount")
	public @ResponseBody String myTodoCount(String id) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("count", todoService.countMyTodo(id));
		String returnValue = jsonObj.toString();	// {"count":개수} 형태의 json 타입으로 return 함.
		
		return returnValue;
	}
	
	/**
	 * Todo 메인
	 * @param todoPk
	 * @return
	 */
	@RequestMapping(value = "/todoView")
	public ModelAndView todoView(TodoPk todoPk) {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/lightpack/todo/todoView");
		
		//레프트 메뉴
		mav.addObject("countMyTodo", todoService.countMyTodo(user.getUserId()));
		mav.addObject("countMyTask", todoService.countMyTask(user.getUserId()));
		if(todoPk != null) {
			mav.addObject("systemCode", todoPk.getSystemCode());
			mav.addObject("subworkCode", todoPk.getSubworkCode());
			mav.addObject("taskKey", todoPk.getTaskKey());
			mav.addObject("workerId", todoPk.getWorkerId());
		}
		
		return mav;
	}
	
	@RequestMapping("/getTodoCount")
	public @ResponseBody String getTodoCount() {
		String result = "";
		try {
			User user = (User) getRequestAttribute("ikep.user");
			int count = todoService.countMyTodo(user.getUserId());
			result = "{\"count\":" + count + "}"; 
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * UserSetting 조회
	 * @return
	 */
	@RequestMapping(value = "/updateUserSettingView")
	public ModelAndView updateUserSettingView() {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/lightpack/todo/updateUserSettingView");

		UserSetting userSetting = userSettingService.read(user.getUserId());
		mav.addObject("userSetting", userSetting);
		
		return mav;
	}

	/**
	 * UserSetting 저장
	 * @param userSetting
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateUserSetting")
	public String updateUserSetting(UserSetting userSetting, BindingResult result, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");

		userSetting.setUserId(user.getUserId());
		if(userSettingService.exists(user.getUserId())) {
			userSettingService.update(userSetting);
		} else {
			userSettingService.create(userSetting);
		}
		
		status.setComplete(); 
		
		return "redirect:/lightpack/todo/listTodoView.do";
	}

	/**
	 * Todo 조회
	 * @param todoSearchCondition
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/listTodoView")
	public ModelAndView listTodoView(TodoSearchCondition todoSearchCondition, @RequestParam(value="searchConditionString", required=false) String searchConditionString) {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/lightpack/todo/listTodoView");

		//TodoSearchCondition 셋팅
		if(todoSearchCondition == null) {
			todoSearchCondition = new TodoSearchCondition();
		}
		if(todoSearchCondition.getPageViewNum() == null) {
			UserSetting userSetting = userSettingService.read(user.getUserId());
			todoSearchCondition.setPageViewNum(userSetting.getPageViewNum());
			todoSearchCondition.setPagePerRecord(userSetting.getPageViewNum());
		}
		if(todoSearchCondition.getSearchText() == null) {
			todoSearchCondition.setSearchText("");
		}
		todoSearchCondition.setDirectorId(user.getUserId());
		todoSearchCondition.setWorkerId(user.getUserId());

		//서치컨디션 유지 
		if(StringUtils.isEmpty(searchConditionString)) {
			searchConditionString = ModelBeanUtil.makeSearchConditionString(todoSearchCondition,
					"pageViewNum",
					"todoStatus",
					"fromDay",
					"toDay",
					"searchType",
					"searchText",
					"directorId",
					"workerId",
					"pageIndex",
					"pagePerRecord"
			);
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, todoSearchCondition);
		}
		
		//Todo 조회
		SearchResult<Todo> searchResult = todoService.listMyTodoSearch(todoSearchCondition);
		mav.addObject("searchResult", searchResult);
		mav.addObject("todoSearchCondition", searchResult.getSearchCondition());
		mav.addObject("searchConditionString", searchConditionString);
		
		String nowDay = timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd");
		mav.addObject("nowDay", nowDay);
		mav.addObject("nowDay_pre_14", DateUtil.getPrevDate(timeZoneSupportService.convertTimeZoneToString("yyyyMMdd"), 14, "yyyy.MM.dd"));
		
		//메뉴 페이지의 Todo 갯수 변경용
		mav.addObject("countMyTodo", todoService.countMyTodo(user.getUserId()));
		
		//Todo 작업 키
		mav.addObject("todoSystemCode", TodoConstants.TODO_SYSTEM_CODE);
		mav.addObject("todoSubworkCode", TodoConstants.TODO_SUBWORK_CODE);
		mav.addObject("todoSubworkCode2", TodoConstants.TODO_SUBWORK_CODE2);
		
		return mav;
	}
	
	/**
	 * 자동완료 처리
	 * @param keys
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/updateTodo")
	public String autoComplete(String keys, @RequestParam(value="searchConditionString", required=false) String searchConditionString) {
		User user = (User) getRequestAttribute("ikep.user");

		String[] tmp = keys.split(",");
		List<TodoPk> todoPkList = new ArrayList<TodoPk>();
		for(int i=0; i<tmp.length; i++) {
			TodoPk todoPk = new TodoPk();
			todoPk.setSystemCode(tmp[i]);
			i++;
			todoPk.setSubworkCode(tmp[i]);
			i++;
			todoPk.setTaskKey(tmp[i]);
			i++;
			todoPk.setWorkerId(tmp[i]);

			todoPkList.add(todoPk);
		}
		todoService.processTodoAutoComplete(todoPkList, user);
		
		if(todoPkList != null && todoPkList.size() > 0) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", todoPkList.get(0).getWorkerId());
		}

		return "redirect:/lightpack/todo/listTodoView.do?searchConditionString=" + searchConditionString;
	}

	/**
	 * 작업할 내용 가져오기
	 * @param todoPk
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/readTodoView")
	public ModelAndView readTodoView(TodoPk todoPk, String taskId, String searchConditionString) throws JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView("/lightpack/todo/readTodoView");
		
		ObjectMapper mapper = new ObjectMapper();

		User user = (User) getRequestAttribute("ikep.user");
		
		if (todoPk.getTaskKey()== null){
			todoPk.setTaskKey(taskId);
		}
		
		if (todoPk.getWorkerId()== null){
			todoPk.setWorkerId(user.getUserId());
		}

		if (todoPk.getSearchConditionString()== null && !StringUtils.isEmpty(searchConditionString)){
			todoPk.setSearchConditionString(searchConditionString);
		}
		
		Task task = taskService.getTask(todoPk.getTaskKey());
		
		mav.addObject("task", task);
		mav.addObject("subworkCode", task.getSubworkCode());
		mav.addObject("taskFiles", mapper.writeValueAsString(task.getFileDataList()));	// 화면의 파일 목록 출력하기 위해 추가
		
		List<TaskUser> taskUserList = taskUserService.listTaskUserByTaskId(todoPk.getTaskKey());
		
		for(int i=0; i<taskUserList.size(); i++) {
			taskUserList.get(i).setUserContents(StringUtil.replaceHtmlString(taskUserList.get(i).getUserContents()));
		}
		
		mav.addObject("taskUserList", taskUserList);
		
		TaskUserPk taskUserPk = new TaskUserPk();
		taskUserPk.setWorkerId(todoPk.getWorkerId());
		taskUserPk.setTaskId(todoPk.getTaskKey());
		TaskUser taskUser = taskUserService.getTaskUser(taskUserPk);
		mav.addObject("taskUser", taskUser);

		String fileDataListJson = mapper.writeValueAsString(taskUser.getFileDataList());
		mav.addObject("fileDataListJson", fileDataListJson);
		
		mav.addObject("searchConditionString", todoPk.getSearchConditionString());
		
		//메뉴 페이지의 Todo 갯수 변경용
		mav.addObject("countMyTodo", todoService.countMyTodo(user.getUserId()));
		
		//페이지 권한 검사:관리자, 지시자, 작업자 만 가능
		//User user = (User) getRequestAttribute("ikep.user");
		if(!aclService.isSystemAdmin("SocialAnalyzer", user.getUserId())) {
			if(!user.getUserId().equals(task.getDirectorId())) {
				boolean isAuth = false;
				for(int i=0; i<taskUserList.size(); i++) {
					if(user.getUserId().equals(taskUserList.get(i).getWorkerId())) {
						isAuth = true;
						break;
					}
				}
				if(!isAuth) {
					throw new IKEP4AuthorizedException();
				}
			}
		}

		return mav;
	}
	
	/**
	 * 할일 저장(임시저장, 완료저장)
	 * @param taskUser
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/saveTodo")
	public String saveTodo(TaskUser taskUser, BindingResult result, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");
		
		Task taskOrg = taskService.getTask(taskUser.getTaskId());
		
		if (StringUtil.isEmpty(taskUser.getUserContents())){
			taskUser.setUserContents(taskOrg.getTaskContents());
		}
		
		taskUserService.updateTaskUser(taskUser, user);
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", taskUser.getWorkerId());
		
		return "redirect:/lightpack/todo/listTodoView.do?searchConditionString=" + taskUser.getSearchConditionString();
	}
	
	/**
	 * 나의 작업 수정 화면으로 이동
	 * @param taskId
	 * @param searchConditionString
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateTodoView")
	public ModelAndView updateTodoView(@RequestParam("taskId") String taskId, @RequestParam(value="searchConditionString", required=false) String searchConditionString) throws JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView("/lightpack/todo/updateTodoView");
		
		Task task = taskService.getTask(taskId);
		mav.addObject("task", task);
		mav.addObject("subworkCode", task.getSubworkCode());
		
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = mapper.writeValueAsString(task.getFileDataList());
		mav.addObject("fileDataListJson", fileDataListJson);
		
		List<TaskUser> taskUserList = taskUserService.listTaskUserByTaskId(taskId);
		mav.addObject("taskUserList", taskUserList);

		mav.addObject("searchConditionString", searchConditionString);
		
		return mav;
	}	

	/**
	 * 나의 업무 수정
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/updateMyTask")
	public String updateMyTask(Task task) {
		User user = (User) getRequestAttribute("ikep.user");
		
		//Task taskTmp = taskService.getTask(task.getTaskId());
		
		if (StringUtil.isEmpty(task.getSubworkCode())){
			task.setSubworkCode("MYTASK");
		}
		if (task.getSubworkCode() == null){
			task.setSubworkCode("MYTASK");
		}

		if (StringUtil.isEmpty(task.getEtcName())){
			task.setEtcName(user.getUserId());
		}
		if (task.getEtcName().length() == 0 ){
			task.setEtcName(user.getUserId());
		}

		if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
			task.setTaskContents(task.getTitle());
		}
		
		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(task.getTaskId());

		taskService.updateTask(task, user);
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}

		return "redirect:/lightpack/todo/readTodoView.do?taskId=" + task.getTaskId() + "&searchConditionString=" + task.getSearchConditionString();
	}

	/**
	 * 나의 업무 삭제
	 * @param taskId
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/deleteMyTask")
	public String deleteMyTask(@RequestParam("taskId") String taskId, @RequestParam(value="searchConditionString", required=false) String searchConditionString) {
		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(taskId);
		
		taskService.deleteTask(taskId);
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}
		
		return "redirect:/lightpack/todo/listTodoView.do?searchConditionString=" + searchConditionString;
	}
	
	/**
	 * 나의 업무 등록  화면 이동하기
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/createTodoView")
	public ModelAndView createTodoView(@RequestParam(value="searchConditionString", required=false) String searchConditionString) { 
		ModelAndView mav = new ModelAndView("/lightpack/todo/createTodoView");
		
		mav.addObject("task", new Task());
		mav.addObject("subworkCode", "MYTASK");
		mav.addObject("nowDay", timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd"));
		mav.addObject("nowDay_pos_1", DateUtil.getNextDate(timeZoneSupportService.convertTimeZoneToString("yyyyMMdd"), 1, "yyyy.MM.dd"));

		mav.addObject("searchConditionString", searchConditionString);
		
		return mav;
	}
	
	/**
	 * 나의 업무 등록
	 * @param task
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createTodo")
	public String createTodo(Task task, BindingResult result, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");

		TodoPk todoPk = new TodoPk();
	
		if (StringUtil.isEmpty(task.getSubworkCode())){
			task.setSubworkCode("MYTASK");
		}
		if (task.getSubworkCode() == null){
			task.setSubworkCode("MYTASK");
		}

		if (StringUtil.isEmpty(task.getEtcName())){
			task.setEtcName(user.getUserId());
		}
		if (task.getEtcName().length() == 0 ){
			task.setEtcName(user.getUserId());
		}

		if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
			task.setTaskContents(task.getTitle());
		}
		
		String taskId = taskService.createTask(task, user);
		
		todoPk.setTaskKey(taskId);
		
		if(!StringUtil.isEmpty(task.getEtcName())) {
			String[] tmp = task.getEtcName().split(",");
			
			for(int i = 0; i < tmp.length; i++) {
				// 포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", tmp[i]);
			}
		}
		
		status.setComplete();
		

		return "redirect:/lightpack/todo/readTodoView.do?taskId=" + taskId;
	}	
	
	/**
	 * Order 조회
	 * @param todoSearchCondition
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/listOrderView")
	public ModelAndView listOrderView(TodoSearchCondition todoSearchCondition, @RequestParam(value="searchConditionString", required=false) String searchConditionString) {
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/lightpack/todo/listOrderView");

		//TodoSearchCondition 셋팅
		if(todoSearchCondition == null) {
			todoSearchCondition = new TodoSearchCondition();
		}
		if(todoSearchCondition.getPageViewNum() == null) {
			UserSetting userSetting = userSettingService.read(user.getUserId());
			todoSearchCondition.setPageViewNum(userSetting.getPageViewNum());
			todoSearchCondition.setPagePerRecord(userSetting.getPageViewNum());
		}
		if(todoSearchCondition.getSearchText() == null) {
			todoSearchCondition.setSearchText("");
		}
		todoSearchCondition.setDirectorId(user.getUserId());
		todoSearchCondition.setWorkerId(user.getUserId());

		//서치컨디션 유지 
		if(StringUtils.isEmpty(searchConditionString)) {
			searchConditionString = ModelBeanUtil.makeSearchConditionString(todoSearchCondition,
					"pageViewNum",
					"todoStatus",
					"fromDay",
					"toDay",
					"searchType",
					"searchText",
					"directorId",
					"workerId",
					"pageIndex",
					"pagePerRecord"
			);
		} else {
			ModelBeanUtil.makeSearchCondition(searchConditionString, todoSearchCondition);
		}
		
		SearchResult<MyOrder> searchResult = myOrderService.listMyOrderSearch(todoSearchCondition);
		mav.addObject("searchResult", searchResult);
		mav.addObject("todoSearchCondition", searchResult.getSearchCondition());

		String nowDay = timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd");
		mav.addObject("nowDay", nowDay);
		mav.addObject("nowDay_pre_14", DateUtil.getPrevDate(timeZoneSupportService.convertTimeZoneToString("yyyyMMdd"), 14, "yyyy.MM.dd"));
		
		mav.addObject("searchConditionString", searchConditionString);

		//메뉴 페이지의 Todo 갯수 변경용
		mav.addObject("countMyTodo", todoService.countMyTodo(user.getUserId()));

		return mav;
	}	

	/**
	 * Order 상세 조회	
	 * @param taskId
	 * @param searchConditionString
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/readOrderView")
	public ModelAndView readOrderView(@RequestParam("taskId") String taskId, @RequestParam(value="searchConditionString", required=false) String searchConditionString) throws JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView("/lightpack/todo/readOrderView");

		ObjectMapper mapper = new ObjectMapper();
		
		Task task = taskService.getTask(taskId);
		mav.addObject("task", task);
		mav.addObject("taskFiles", mapper.writeValueAsString(task.getFileDataList()));	// 화면의 파일 목록 출력하기 위해 추가
		
		List<TaskUser> taskUserList = taskUserService.listTaskUserByTaskId(taskId);
				
		for(int i=0; i<taskUserList.size(); i++) {
			taskUserList.get(i).setUserContents(StringUtil.replaceHtmlString(taskUserList.get(i).getUserContents()));
		}
		
		mav.addObject("taskUserList", taskUserList);
		mav.addObject("searchConditionString", searchConditionString);

		//페이지 권한 검사:관리자, 지시자, 작업자 만 가능
		User user = (User) getRequestAttribute("ikep.user");
		if(!aclService.isSystemAdmin("SocialAnalyzer", user.getUserId())) {
			if(!user.getUserId().equals(task.getDirectorId())) {
				boolean isAuth = false;
				for(int i=0; i<taskUserList.size(); i++) {
					if(user.getUserId().equals(taskUserList.get(i).getWorkerId())) {
						isAuth = true;
						break;
					}
				}
				if(!isAuth) {
					throw new IKEP4AuthorizedException();
				}
			}
		}

		//메뉴 페이지의 Todo 갯수 변경용
		mav.addObject("countMyTodo", todoService.countMyTodo(user.getUserId()));

		return mav;
	}	

	/**
	 * 작업 지시 수정 화면으로 이동
	 * @param taskId
	 * @param searchConditionString
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateTaskView")
	public ModelAndView updateTaskView(@RequestParam("taskId") String taskId, @RequestParam(value="searchConditionString", required=false) String searchConditionString) throws JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView("/lightpack/todo/updateTaskView");
		
		Task task = taskService.getTask(taskId);
		mav.addObject("task", task);
		mav.addObject("subworkCode", task.getSubworkCode());
		
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = mapper.writeValueAsString(task.getFileDataList());
		mav.addObject("fileDataListJson", fileDataListJson);
		
		List<TaskUser> taskUserList = taskUserService.listTaskUserByTaskId(taskId);
		mav.addObject("taskUserList", taskUserList);

		mav.addObject("searchConditionString", searchConditionString);
		
		return mav;
	}	

	/**
	 * 작업 지시 등록  화면 이동하기
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/createTaskView")
	public ModelAndView createTaskView(@RequestParam(value="searchConditionString", required=false) String searchConditionString) { 
		ModelAndView mav = new ModelAndView("/lightpack/todo/createTaskView");
		
		mav.addObject("task", new Task());
		mav.addObject("subworkCode", "TASK");
		mav.addObject("nowDay", timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd"));
		mav.addObject("nowDay_pos_1", DateUtil.getNextDate(timeZoneSupportService.convertTimeZoneToString("yyyyMMdd"), 1, "yyyy.MM.dd"));

		mav.addObject("searchConditionString", searchConditionString);
		
		return mav;
	}
	
	/**
	 * 작업 지시 등록
	 * @param task
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createTask")
	public String createTask(Task task, BindingResult result, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");
	
		String taskId = taskService.createTask(task, user);
		
		if(!StringUtil.isEmpty(task.getEtcName())) {
			String[] tmp = task.getEtcName().split(",");
			
			for(int i = 0; i < tmp.length; i++) {
				// 포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", tmp[i]);
			}
		}
		
		status.setComplete();

		return "redirect:/lightpack/todo/readOrderView.do?taskId=" + taskId;
	}	
	
	/**
	 * 작업 지시 수정
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/updateTask")
	public String updateTask(Task task) {
		User user = (User) getRequestAttribute("ikep.user");
		
		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(task.getTaskId());
		
		if (StringUtil.isEmpty(task.getSubworkCode())){
			task.setSubworkCode("TASK");
		}
		
		if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
			task.setTaskContents(task.getTitle());
		}
		
		taskService.updateTask(task, user);
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}

		return "redirect:/lightpack/todo/readOrderView.do?taskId=" + task.getTaskId() + "&searchConditionString=" + task.getSearchConditionString();
	}

	/**
	 * 작업 지시 삭제
	 * @param taskId
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/deleteTask")
	public String deleteTask(@RequestParam("taskId") String taskId, @RequestParam(value="searchConditionString", required=false) String searchConditionString) {
		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(taskId);
		
		taskService.deleteTask(taskId);
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}
		
		return "redirect:/lightpack/todo/listOrderView.do?searchConditionString=" + searchConditionString;
	}
	
	
	/* 웹 다이어리에서 사용함 */
	/**
	 * 작업 지시, 나의 할일 조회
	 * - 웹다이어리에서 목록 조회시 사용
	 * @param taskId
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/webDiaryReadTodoView")
	public ModelAndView webDiaryReadTodoView(TodoPk todoPk, String taskId, String searchConditionString) throws JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView("/lightpack/planner/webdiary/webDiaryReadTodoView");
		
		ObjectMapper mapper = new ObjectMapper();

		User user = (User) getRequestAttribute("ikep.user");
		
		if (todoPk.getTaskKey()== null){
			todoPk.setTaskKey(taskId);
		}
		
		if (todoPk.getWorkerId()== null){
			todoPk.setWorkerId(user.getUserId());
		}

		if (todoPk.getSearchConditionString()== null && !StringUtils.isEmpty(searchConditionString)){
			todoPk.setSearchConditionString(searchConditionString);
		}
		
		Task task = taskService.getTask(todoPk.getTaskKey());
		
		mav.addObject("task", task);
		mav.addObject("subworkCode", task.getSubworkCode());
		mav.addObject("taskFiles", mapper.writeValueAsString(task.getFileDataList()));	// 화면의 파일 목록 출력하기 위해 추가
		
		List<TaskUser> taskUserList = taskUserService.listTaskUserByTaskId(todoPk.getTaskKey());
		
		for(int i=0; i<taskUserList.size(); i++) {
			taskUserList.get(i).setUserContents(StringUtil.replaceHtmlString(taskUserList.get(i).getUserContents()));
		}
		
		mav.addObject("taskUserList", taskUserList);
		
		TaskUserPk taskUserPk = new TaskUserPk();
		taskUserPk.setWorkerId(todoPk.getWorkerId());
		taskUserPk.setTaskId(todoPk.getTaskKey());
		TaskUser taskUser = taskUserService.getTaskUser(taskUserPk);
		mav.addObject("taskUser", taskUser);

		String fileDataListJson = mapper.writeValueAsString(taskUser.getFileDataList());
		mav.addObject("fileDataListJson", fileDataListJson);
		
		mav.addObject("searchConditionString", todoPk.getSearchConditionString());
		
		//페이지 권한 검사:관리자, 지시자, 작업자 만 가능
		//User user = (User) getRequestAttribute("ikep.user");
		if(!aclService.isSystemAdmin("SocialAnalyzer", user.getUserId())) {
			if(!user.getUserId().equals(task.getDirectorId())) {
				boolean isAuth = false;
				for(int i=0; i<taskUserList.size(); i++) {
					if(user.getUserId().equals(taskUserList.get(i).getWorkerId())) {
						isAuth = true;
						break;
					}
				}
				if(!isAuth) {
					throw new IKEP4AuthorizedException();
				}
			}
		}

		return mav;
	}

	/**
	 * 작업지시 저장(임시저장, 완료저장) 
	 * - 웹다이어리의 작업지시 내역 화면에서 작업 완료시 사용
	 * @param taskUser
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/webDiarySaveTodo")
	public String webDiarySaveTodo(TaskUser taskUser, BindingResult result, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");
		
		Task taskOrg = taskService.getTask(taskUser.getTaskId());
		
		if (StringUtil.isEmpty(taskUser.getUserContents())){
			taskUser.setUserContents(taskOrg.getTaskContents());
		}
		
		taskUserService.updateMyTaskUser(taskUser, user);
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", taskUser.getWorkerId());
		
		//return "redirect:/lightpack/todo/listTodoView.do?searchConditionString=" + taskUser.getSearchConditionString();
		return "redirect:/lightpack/planner/webDiary/init.do";
	}

	/**
	 * 작업 지시 수정
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/webDiaryUpdateTask")
	public String webDiaryUpdateTask(Task task) {
		User user = (User) getRequestAttribute("ikep.user");
		
		Task taskOrg = taskService.getTask(task.getTaskId());
		
		if (StringUtil.isEmpty(task.getSubworkCode())){
			task.setSubworkCode(taskOrg.getSubworkCode());
		}

		if (StringUtil.isEmpty(task.getEtcName())){
			task.setEtcName(taskOrg.getEtcName());
		}
		
		if (task.getEtcName().length() == 0 ){
			task.setEtcName(taskOrg.getEtcName());
		}

		if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
			task.setTaskContents(task.getTitle());
		}
		
		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(task.getTaskId());
		
		taskService.updateTask(task, user);
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}

		//return "redirect:/lightpack/todo/readOrderView.do?taskId=" + task.getTaskId() + "&searchConditionString=" + task.getSearchConditionString();
		return "redirect:/lightpack/planner/webDiary/init.do";
	}
	/**
	 * 나의할일 등록
	 * - 웹다이어리에서 나의할일 등록시 사용
	 * @param task
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/webDiaryCreateMyTask")
	//public String webDiaryCreateMyTask(String title, String subworkCode, BindingResult result, SessionStatus status) {
	public String webDiaryCreateMyTask(String title, String subworkCode, String sDate) {
		try {
			//if (title != ""){
			if (!StringUtil.isEmpty(title)){
				User user = (User) getRequestAttribute("ikep.user");
		
				Task task = new Task();
				/*
				Date s = new Date();
				Date d = new Date(s.getYear(),s.getMonth(),s.getDate(),23,59); //당일 완료 기한 설정(23:59)
				*/
				
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
				String from = sDate+" 00:00";
				String to = sDate+" 23:59"; //당일 완료 기한 설정(23:59)
				Date startDay = transFormat.parse(from);
				Date endDay = transFormat.parse(to);
				
				title = URLDecoder.decode(title, "UTF-8");
			
				task.setTitle(title);
				if (StringUtil.isEmpty(task.getSubworkCode())){
					task.setSubworkCode("MYTASK");
				}
				if (StringUtil.isEmpty(task.getEtcName())){
					task.setEtcName(user.getUserId());
				}
				if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
					task.setTaskContents(task.getTitle());
				}
				//task.setSubworkCode("MYTASK");
				//task.setEtcName(user.getUserId());
				//task.setTaskContents(title);
				task.setStartDate(startDay);
				task.setDueDate(endDay);
			
				String taskId = taskService.createTask(task, user);
				
				if(!StringUtil.isEmpty(task.getEtcName())) {
					String[] tmp = task.getEtcName().split(",");
					
					for(int i = 0; i < tmp.length; i++) {
						// 포틀릿 contents 캐시 Element 삭제
						cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", tmp[i]);
					}
				}
			}
			//status.setComplete();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//return "redirect:/lightpack/todo/readOrderView.do?taskId=" + taskId;
		return "redirect:/lightpack/planner/webDiary/init.do";
	}	
	
	/**
	 * 작업지시 저장(완료저장)
	 * - 웹다이어리 할일 목록에서 완료처리 시 사용(작업지시)
	 * @param taskUser
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/webDiaryCompleteTask")
	public String webDiaryCompleteTask(String taskKey) {
		User user = (User) getRequestAttribute("ikep.user");
		TaskUser taskUser = new TaskUser();
		
		Task taskOrg = taskService.getTask(taskKey);
		
		taskUser.setUserStatus("B");
		taskUser.setWorkerId(user.getUserId());
		taskUser.setTaskId(taskOrg.getTaskId());
		taskUser.setUserContents(taskOrg.getTaskContents());
		
		taskUserService.updateTaskUser(taskUser, user);
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", taskUser.getWorkerId());
		
		//return "redirect:/lightpack/todo/listTodoView.do?searchConditionString=" + taskUser.getSearchConditionString();
		return "redirect:/lightpack/planner/webDiary/init.do";
	}
	
	/**
	 * 나의할일 저장(완료저장)
	 * - 웹다이어리 할일 목록에서 완료처리 시 사용(나의할일)
	 * @param taskUser
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/webDiaryCompleteMyTask")
	public String webDiaryCompleteMyTask( String taskKey) {
		User user = (User) getRequestAttribute("ikep.user");
		TaskUser taskUser = new TaskUser();
		
		Task taskOrg = taskService.getTask(taskKey);
		
		taskUser.setUserStatus("B");
		taskUser.setWorkerId(user.getUserId());
		taskUser.setTaskId(taskOrg.getTaskId());
		taskUser.setUserContents(taskOrg.getTaskContents());
		
		taskUserService.updateMyTaskUser(taskUser, user);
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", taskUser.getWorkerId());
		
		//return "redirect:/lightpack/todo/listTodoView.do?searchConditionString=" + taskUser.getSearchConditionString();
		return "redirect:/lightpack/planner/webDiary/init.do";
	}
	
	/**
	 * 나의할일 수정(연기)
	 * - 웹다이어리 할일 목록에서 업무 기한 연장 시 사용(나의할일)
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/webDiaryUpdateMyTaskDue")
	public String webDiaryUpdateMyTaskDue(String taskKey, String dueDate) {
		try {
			User user = (User) getRequestAttribute("ikep.user");
			
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			String to = dueDate+" 23:59"; //완료 기한 설정(23:59)
			Date due = transFormat.parse(to);

			Task task = taskService.getTask(taskKey);
			
			//Task task = new Task();
			
			task.setTaskStatus("A"); //연기
			task.setDueDate(due);
			task.setSubworkCode("MYTASK");
			if (StringUtil.isEmpty(task.getEtcName())){
				task.setEtcName(user.getUserId());
			}
			//task.setEtcName(user.getUserId());
			
			List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(task.getTaskId());
			
			taskService.updateTask(task, user);
			
			for(int i = 0; i < oldTaskUserList.size(); i++) {
				// 포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		//return "redirect:/lightpack/todo/readOrderView.do?taskId=" + task.getTaskId() + "&searchConditionString=" + task.getSearchConditionString();
		return "redirect:/lightpack/planner/webDiary/init.do";

	}

	/**
	 * 나의할일 수정(Title 변경)
	 * - 웹다이어리 할일 목록에서 타이틀 변경 시 사용(나의할일)
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/webDiaryChangeMyTask")
	public String webDiaryChangeMyTask(String title, String taskId) {

		User user = (User) getRequestAttribute("ikep.user");
		
		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(taskId);

		Task task = taskService.getTask(taskId);

		if (StringUtil.isEmpty(task.getEtcName())){
			task.setEtcName(user.getUserId());
		}

		if (task.getEtcName().length() == 0){
			task.setEtcName(user.getUserId());
		}
		
		if (StringUtil.isEmpty(task.getSubworkCode())){
			task.setSubworkCode("MYTASK");
		}

		if (task.getSubworkCode() == null){
			task.setSubworkCode("MYTASK");
		}

		if (!StringUtil.isEmpty(title)){
			task.setTitle(title);
			if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
				task.setTaskContents(task.getTitle());
			}
			//task.setTaskContents(title);
			
			//List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(task.getTaskId());
			
			taskService.updateTask(task, user);
			
		} else {
			//List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(taskId);
			
			taskService.deleteTask(taskId);
			
		}
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}

		//return "redirect:/lightpack/todo/readOrderView.do?taskId=" + task.getTaskId() + "&searchConditionString=" + task.getSearchConditionString();
		return "redirect:/lightpack/planner/webDiary/init.do";

	}

	/**
	 * 나의 업무 등록  화면 이동하기
	 * - 웹다이어리에서 등록 화면 호출 시 사용(나의할일)
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/webDiaryCreateTodoView")
	public ModelAndView webDiaryCreateTodoView(@RequestParam(value="searchConditionString", required=false) String searchConditionString) { 
		ModelAndView mav = new ModelAndView("/lightpack/planner/webdiary/webDiaryCreateTodoView");
		
		mav.addObject("task", new Task());
		mav.addObject("subworkCode", "MYTASK");
		mav.addObject("nowDay", timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd"));
		mav.addObject("nowDay_pos_1", DateUtil.getNextDate(timeZoneSupportService.convertTimeZoneToString("yyyyMMdd"), 1, "yyyy.MM.dd"));

		mav.addObject("searchConditionString", searchConditionString);
		
		return mav;
	}
	
	/**
	 * 나의 업무 등록
	 * - 웹다이어리의 등록 화면에서 저장 시 사용(나의할일)
	 * @param task
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/webDiaryCreateTodo")
	public String webDiaryCreateTodo(Task task, BindingResult result, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");

		if (StringUtil.isEmpty(task.getSubworkCode())){
			task.setSubworkCode("MYTASK");
		}
		if (StringUtil.isEmpty(task.getEtcName())){
			task.setEtcName(user.getUserId());
		}
		if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
			task.setTaskContents(task.getTitle());
		}
		
		String taskId = taskService.createTask(task, user);
		
		TodoPk todoPk = new TodoPk();
		
		todoPk.setTaskKey(taskId);
		
		if(!StringUtil.isEmpty(task.getEtcName())) {
			String[] tmp = task.getEtcName().split(",");
			
			for(int i = 0; i < tmp.length; i++) {
				// 포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", tmp[i]);
			}
		}
		
		status.setComplete();
		

		//return "redirect:/lightpack/todo/webDiaryReadTodoView.do?taskId=" + taskId;
		return "redirect:/lightpack/planner/webDiary/init.do";
	}	

	/**
	 * 나의 작업 수정 화면으로 이동
	 * - 웹다이어리에서 수정 화면 호출 시 사용(나의할일)
	 * @param taskId
	 * @param searchConditionString
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/webDiaryUpdateTodoView")
	public ModelAndView webDiaryUpdateTodoView(@RequestParam("taskId") String taskId, @RequestParam(value="searchConditionString", required=false) String searchConditionString) throws JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView("/lightpack/planner/webdiary/webDiaryUpdateTodoView");
		
		Task task = taskService.getTask(taskId);
		mav.addObject("task", task);
		mav.addObject("subworkCode", task.getSubworkCode());
		
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = mapper.writeValueAsString(task.getFileDataList());
		mav.addObject("fileDataListJson", fileDataListJson);
		
		List<TaskUser> taskUserList = taskUserService.listTaskUserByTaskId(taskId);
		mav.addObject("taskUserList", taskUserList);

		mav.addObject("searchConditionString", searchConditionString);
		
		return mav;
	}	

	/**
	 * 나의 업무 수정
	 * - 웹다이어리의 수정 화면에서 저장 시 사용(나의할일)
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/webDiaryUpdateMyTask")
	public String webDiaryUpdateMyTask(Task task) {
		User user = (User) getRequestAttribute("ikep.user");
		
		//Task taskTmp = taskService.getTask(task.getTaskId());
		
		Task taskOrg = taskService.getTask(task.getTaskId());
		
		if (StringUtil.isEmpty(task.getSubworkCode())){
			task.setSubworkCode(taskOrg.getSubworkCode());
		}

		if (StringUtil.isEmpty(task.getEtcName())){
			task.setEtcName(taskOrg.getEtcName());
		}
		
		if (task.getEtcName().length() == 0 ){
			task.setEtcName(taskOrg.getEtcName());
		}

		if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
			task.setTaskContents(task.getTitle());
		}
		
		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(task.getTaskId());

		taskService.updateTask(task, user);
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}

		//return "redirect:/lightpack/todo/webDiaryReadTodoView.do?taskId=" + task.getTaskId() + "&searchConditionString=" + task.getSearchConditionString();
		return "redirect:/lightpack/planner/webDiary/init.do";
	}
	
	/**
	 * 나의 업무 삭제
	 * - 웹다이어리의 조회 화면, 목록에서 사용(나의할일)
	 * @param taskId
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/webDiaryDeleteMyTask")
	public String webDiaryDeleteMyTask(@RequestParam("taskId") String taskId, @RequestParam(value="searchConditionString", required=false) String searchConditionString) {
		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(taskId);
		
		taskService.deleteTask(taskId);
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}
		
		//return "redirect:/lightpack/todo/listTodoView.do?searchConditionString=" + searchConditionString;
		return "redirect:/lightpack/planner/webDiary/init.do";
	}
	
	/**
	 * 나의 업무 등록 Ajax
	 * - 웹다이어리의 등록 화면에서 저장 시 사용(나의할일)
	 * @param task
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/webDiaryCreateTodoAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Object> webDiaryCreateTodoAjax(Task task, BindingResult result, SessionStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = (User) getRequestAttribute("ikep.user");

		TodoPk todoPk = new TodoPk();
	
		if (StringUtil.isEmpty(task.getEtcName())){
			task.setEtcName(user.getUserId());
		}
		
		if (StringUtil.isEmpty(task.getSubworkCode())){
			task.setSubworkCode("MYTASK");
		}

		if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
			task.setTaskContents(task.getTitle());
		}
		
		String taskId = taskService.createTask(task, user);
		
		todoPk.setTaskKey(taskId);
		
		if(!StringUtil.isEmpty(task.getEtcName())) {
			String[] tmp = task.getEtcName().split(",");
			
			for(int i = 0; i < tmp.length; i++) {
				// 포틀릿 contents 캐시 Element 삭제
				cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", tmp[i]);
			}
		}
		
		status.setComplete();
		
		resultMap.put("taskId", taskId);
		resultMap.put("searchConditionString", task.getSearchConditionString());

		//return "redirect:/lightpack/todo/webDiaryReadTodoView.do?taskId=" + taskId;
		//return "redirect:/lightpack/planner/webDiary/init.do";
		return resultMap;
	}	

	/**
	 * 나의 업무 수정 Ajax
	 * - 웹다이어리의 수정 화면에서 저장 시 사용(나의할일)
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/webDiaryUpdateMyTaskAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Object> webDiaryUpdateMyTaskAjax(Task task) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user = (User) getRequestAttribute("ikep.user");
		
		//Task taskTmp = taskService.getTask(task.getTaskId());
		
		Task taskOrg = taskService.getTask(task.getTaskId());
		
		if (StringUtil.isEmpty(task.getSubworkCode())){
			task.setSubworkCode(taskOrg.getSubworkCode());
		}

		if (StringUtil.isEmpty(task.getEtcName())){
			task.setEtcName(taskOrg.getEtcName());
		}
		
		if (task.getEtcName().length() == 0 ){
			task.setEtcName(taskOrg.getEtcName());
		}

		if (StringUtil.isEmpty(task.getTaskContents())){ // 작업 내용이 없는 경우 제목으로 작업 내용을 생성
			task.setTaskContents(task.getTitle());
		}
		
		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(task.getTaskId());

		taskService.updateTask(task, user);
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}

		//return "redirect:/lightpack/todo/webDiaryReadTodoView.do?taskId=" + task.getTaskId() + "&searchConditionString=" + task.getSearchConditionString();
		//return "redirect:/lightpack/planner/webDiary/init.do";

		result.put("taskId", task.getTaskId());
		result.put("searchConditionString", task.getSearchConditionString());
		
		return result;
	}

	/**
	 * 나의 업무 삭제 Ajax
	 * - 웹다이어리의 조회 화면, 목록에서 사용(나의할일)
	 * @param taskId
	 * @param searchConditionString
	 * @return
	 */
	@RequestMapping(value = "/webDiaryDeleteMyTaskAjax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String webDiaryDeleteMyTaskAjax(@RequestParam("taskId") String taskId, @RequestParam(value="searchConditionString", required=false) String searchConditionString) {
		Map<String, Object> result = new HashMap<String, Object>();

		List<TaskUser> oldTaskUserList = taskUserService.listTaskUserByTaskId(taskId);
		
		taskService.deleteTask(taskId);
		
		for(int i = 0; i < oldTaskUserList.size(); i++) {
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-todo-portlet", "Cachemode-todo-portlet", "Elementkey-todo-portlet", oldTaskUserList.get(i).getWorkerId());
		}
		
		//return "redirect:/lightpack/todo/listTodoView.do?searchConditionString=" + searchConditionString;
		//return "redirect:/lightpack/planner/webDiary/init.do";
		return SUCCESS;
	}
	

	
}
