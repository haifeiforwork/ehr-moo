package com.lgcns.ikep4.lightpack.planner.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoConstants;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;
import com.lgcns.ikep4.lightpack.todo.service.TodoService;
import com.lgcns.ikep4.lightpack.todo.service.UserSettingService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value = "lightpack/planner/webDiary")
public class WebDiaryController extends BaseController {
	@Autowired
	private TodoService todoService;
    @Autowired
    private TimeZoneSupportService timeZoneSupportService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private WorkspaceService workspaceService;

	private static final String SUCCESS = "success";

	private static final String FAIL = "fail";

	public static final int TODO_DEFALUT_PAGE_PER_RECORD = 18;

	private User getUser() {
		return (User) getRequestAttribute("ikep.user");
	}
	
	private Portal getPortal() {
		return (Portal) getRequestAttribute("ikep.portal");
	}
	
	@RequestMapping("/init")
	public ModelAndView init() {
		
		ModelAndView mav = new ModelAndView("lightpack/planner/webDiary");
		
		User user = getUser();
		String workspaceId = workspaceService.myTeamWorkspace(user.getUserId());
		
		try {

			String nowDay = timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd");
			mav.addObject("nowDay", nowDay);
			
			// Schedule 조회
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = scheduleService.readMyWebDiarySchedule(user.getUserId());
			mav.addObject("searchSchedule", list);
			mav.addObject("workspaceId", workspaceId);
			
			// Todo 조회
			TodoSearchCondition todoSearchCondition = new TodoSearchCondition();
			//UserSetting userSetting = userSettingService.read(user.getUserId());
			todoSearchCondition.setPageViewNum(TODO_DEFALUT_PAGE_PER_RECORD);
			todoSearchCondition.setPagePerRecord(TODO_DEFALUT_PAGE_PER_RECORD);
			todoSearchCondition.setSearchText("");
			todoSearchCondition.setDirectorId(user.getUserId());
			todoSearchCondition.setWorkerId(user.getUserId());

			SearchResult<Todo> todoSearchResult = todoService.listMyTodoSearchDueDate(todoSearchCondition);
			mav.addObject("todoSearchResult", todoSearchResult);
			mav.addObject("searchTodo", todoSearchResult.getEntity());
			mav.addObject("todoSearchCondition", todoSearchResult.getSearchCondition());
			
			//Todo 작업 키
			mav.addObject("todoSystemCode", TodoConstants.TODO_SYSTEM_CODE);
			mav.addObject("todoSubworkCode", TodoConstants.TODO_SUBWORK_CODE);
			
			/* Note 조회 제거 - 2014.09.16
			// Note 조회
			NoteSearchCondition noteSearchCondition = new NoteSearchCondition();
			
			noteSearchCondition.setPagePerRecord(PORTAL_NOTE_DEFALUT_PAGE_PER_RECORD);
			// 정렬 컬럼 정보 없을 경우 등록일로 설정
			noteSearchCondition.setSortColumn("REGIST_DATE");
			// 정렬 유형 정보 없을 경우 최신글로 설정
			noteSearchCondition.setSortType("DESC");

			noteSearchCondition.setSortWord(noteSearchCondition.getSortColumn() + ":" + noteSearchCondition.getSortType());
			noteSearchCondition.setPortalId(user.getPortalId());
			noteSearchCondition.setUserId(user.getUserId());
			noteSearchCondition.setFolderId("A");
			//noteSearchCondition.setFolderId(folderId);
			//noteSearchCondition.setSearchType(searchType);
			//noteSearchCondition.setSearchWord(searchText);

			// 폴더 관리 정보를 가져온다.
			NoteFolder noteFolder = this.noteFolderService.readFolder(noteSearchCondition);
			
			// 노트 목록을 가져온다.
			SearchResult<Note> noteSearchResult = this.noteService.webDiaryListNoteBySearchCondition(noteSearchCondition);

			List<NoteFolder> noteUserFolder = this.noteFolderService.listByFolderRootId(user.getPortalId(), user.getUserId());
			
			mav.addObject("noteFolder", noteFolder);
			mav.addObject("userFolderList", noteUserFolder);
			mav.addObject("noteSearchResult", noteSearchResult);
			mav.addObject("searchNote", noteSearchResult.getEntity());
			mav.addObject("noteSearchCondition", noteSearchResult.getSearchCondition());
			*/
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return mav;
	}

	@RequestMapping("/initList")
	public @ResponseBody Map<String, Object> initList() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user = getUser();
		String workspaceId = workspaceService.myTeamWorkspace(user.getUserId());
		
		try {

			String nowDay = timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd");
			result.put("nowDay", nowDay);
			result.put("sDate", nowDay);
			
			// Schedule 조회
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = scheduleService.readMyWebDiarySchedule(user.getUserId());
			result.put("searchSchedule", list);
			result.put("workspaceId", workspaceId);
			
			// Todo 조회
			TodoSearchCondition todoSearchCondition = new TodoSearchCondition();
			//UserSetting userSetting = userSettingService.read(user.getUserId());
			todoSearchCondition.setPageViewNum(TODO_DEFALUT_PAGE_PER_RECORD);
			todoSearchCondition.setPagePerRecord(TODO_DEFALUT_PAGE_PER_RECORD);
			todoSearchCondition.setSearchText("");
			todoSearchCondition.setDirectorId(user.getUserId());
			todoSearchCondition.setWorkerId(user.getUserId());

			SearchResult<Todo> todoSearchResult = todoService.listMyTodoSearchDueDate(todoSearchCondition);
			result.put("todoSearchResult", todoSearchResult);
			result.put("searchTodo", todoSearchResult.getEntity());
			result.put("todoSearchCondition", todoSearchResult.getSearchCondition());
			
			//Todo 작업 키
			result.put("todoSystemCode", TodoConstants.TODO_SYSTEM_CODE);
			result.put("todoSubworkCode", TodoConstants.TODO_SUBWORK_CODE);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping("/getDateSchedule")
	public @ResponseBody Map<String, Object> getDateSchedule(String sDate, String sGubun) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		User user = getUser();

		String nowDay = "";
		if (StringUtil.isEmpty(sDate)){
			nowDay = timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd");
		} else {
			nowDay = sDate;
		}

		String workspaceId = workspaceService.myTeamWorkspace(user.getUserId());
		
		try {
			// Schedule 조회
			if (sGubun=="" || sGubun.equals("personal")){
				sGubun = "personal";
				list = scheduleService.webDiaryReadUserDateSchedule(user.getUserId(), sDate.replace(".", ""), sDate.replace(".", ""));
			} else {
				sGubun = "workspace";
				list = scheduleService.webDiaryReadWorkspaceSchedule(workspaceId, sDate.replace(".", ""), "");	
			}

			// Todo 조회
			TodoSearchCondition todoSearchCondition = new TodoSearchCondition();
			//UserSetting userSetting = userSettingService.read(user.getUserId());
			todoSearchCondition.setPageViewNum(TODO_DEFALUT_PAGE_PER_RECORD);
			todoSearchCondition.setPagePerRecord(TODO_DEFALUT_PAGE_PER_RECORD);
			todoSearchCondition.setSearchText("");
			todoSearchCondition.setDirectorId(user.getUserId());
			todoSearchCondition.setWorkerId(user.getUserId());
			/*
			// 특정일자만 조회
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			String from = sDate+" 00:00";
			String to = sDate+" 23:59";
			Date fromDay = transFormat.parse(from);
			Date toDay = transFormat.parse(to);
			
			todoSearchCondition.setFromDay(fromDay);
			todoSearchCondition.setToDay(toDay);
			
			SearchResult<Todo> todoSearchResult = todoService.listMyTodoSearchDueDate(todoSearchCondition);
			*/
			
			SearchResult<Todo> todoSearchResult = todoService.listMyTodoSearch(todoSearchCondition);
			result.put("todoSearchResult", todoSearchResult);
			result.put("searchTodo", todoSearchResult.getEntity());
			result.put("todoSearchCondition", todoSearchResult.getSearchCondition());
			
			result.put("success", SUCCESS);

		} catch (ParseException e) {
			result.put("success", FAIL);
			e.printStackTrace();
		}

		//Todo 작업 키
		result.put("todoSystemCode", TodoConstants.TODO_SYSTEM_CODE);
		result.put("todoSubworkCode", TodoConstants.TODO_SUBWORK_CODE);
		
		result.put("nowDay", nowDay);
		result.put("sDate", sDate);
		result.put("sGubun", sGubun);
		result.put("workspaceId", workspaceId);
		result.put("searchSchedule", list);

		return result;

	}

	@RequestMapping("/getDateScheduleList")
	public @ResponseBody Map<String, Object> getDateScheduleList(String sDate, String sGubun) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		User user = getUser();

		String nowDay = "";
		if (StringUtil.isEmpty(sDate)){
			nowDay = timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd");
		} else {
			nowDay = sDate;
		}

		String workspaceId = workspaceService.myTeamWorkspace(user.getUserId());
		
		try {
			// Schedule 조회
			if (sGubun=="" || sGubun.equals("personal")){
				sGubun = "personal";
				list = scheduleService.webDiaryReadUserDateSchedule(user.getUserId(), sDate.replace(".", ""), sDate.replace(".", ""));
			} else {
				sGubun = "workspace";
				list = scheduleService.webDiaryReadWorkspaceSchedule(workspaceId, sDate.replace(".", ""), "");	
			}

			result.put("success", SUCCESS);

		} catch (ParseException e) {
			result.put("success", FAIL);
			e.printStackTrace();
		}

		result.put("nowDay", nowDay);
		result.put("sDate", sDate);
		result.put("sGubun", sGubun);
		result.put("workspaceId", workspaceId);
		result.put("searchSchedule", list);

		return result;
	}

	@RequestMapping("/getMyTaskList")
	public @ResponseBody Map<String, Object> getMyTaskList(String sDate, String sGubun) {

		Map<String, Object> result = new HashMap<String, Object>();

		User user = getUser();

		String nowDay = "";
		if (StringUtil.isEmpty(sDate)){
			nowDay = timeZoneSupportService.convertTimeZoneToString("yyyy.MM.dd");
		} else {
			nowDay = sDate;
		}

		String workspaceId = workspaceService.myTeamWorkspace(user.getUserId());
		
		// Todo 조회
		TodoSearchCondition todoSearchCondition = new TodoSearchCondition();
		//UserSetting userSetting = userSettingService.read(user.getUserId());
		todoSearchCondition.setPageViewNum(TODO_DEFALUT_PAGE_PER_RECORD);
		todoSearchCondition.setPagePerRecord(TODO_DEFALUT_PAGE_PER_RECORD);
		todoSearchCondition.setSearchText("");
		todoSearchCondition.setDirectorId(user.getUserId());
		todoSearchCondition.setWorkerId(user.getUserId());
		/*
		// 특정일자만 조회
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		String from = sDate+" 00:00";
		String to = sDate+" 23:59";
		Date fromDay = transFormat.parse(from);
		Date toDay = transFormat.parse(to);
		
		todoSearchCondition.setFromDay(fromDay);
		todoSearchCondition.setToDay(toDay);
		
		SearchResult<Todo> todoSearchResult = todoService.listMyTodoSearchDueDate(todoSearchCondition);
		*/
		
		SearchResult<Todo> todoSearchResult = todoService.listMyTodoSearch(todoSearchCondition);
		List<Todo> list = todoSearchResult.getEntity();
		result.put("todoSearchResult", todoSearchResult);
		//result.put("searchTodo", todoSearchResult.getEntity());
		result.put("searchTodo", list);
		result.put("todoSearchCondition", todoSearchResult.getSearchCondition());
		
		//Todo 작업 키
		result.put("todoSystemCode", TodoConstants.TODO_SYSTEM_CODE);
		result.put("todoSubworkCode", TodoConstants.TODO_SUBWORK_CODE);
		
		result.put("nowDay", nowDay);
		result.put("sDate", sDate);
		result.put("sGubun", sGubun);
		result.put("workspaceId", workspaceId);

		return result;
	}
 
}
