package com.lgcns.ikep4.lightpack.todo.portlet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoConstants;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;
import com.lgcns.ikep4.lightpack.todo.service.TodoService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;

@Controller
@RequestMapping(value = "/lightpack/todo/portlet/myTodo")
public class MyTodoController extends BaseController {
	@Autowired
	PortalPortletUserConfigService portalPortletUserConfigService;

	@Autowired
	private TodoService todoService;
	
	@Autowired
	private CacheService cacheService;

	/**
	 * 포틀릿 설정 모드
	 * @param portletConfigId
	 * @return
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("/lightpack/todo/portlet/myTodo/configView");
		
		//조회
		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if(listSize == 0) {
			listSize = 5;
		}
		
		//todo 목록 캐시에서 조회
		List<Todo> todoList = (List<Todo>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(todoList == null) {
			User user = (User) getRequestAttribute("ikep.user");
			todoList = todoService.listMyTodo(user.getUserId(), listSize);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, todoList);
		}
		
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("portletId", portletId);
		mav.addObject("todoList", todoList);
		mav.addObject("listSize", listSize);
		//Todo 작업 키
		mav.addObject("todoSystemCode", TodoConstants.TODO_SYSTEM_CODE);
		mav.addObject("todoSubworkCode", TodoConstants.TODO_SUBWORK_CODE);
		
		return mav;
	}
	/**
	 * 포틀릿 일반 모드
	 * @param portletConfigId
	 * @return
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("/lightpack/todo/portlet/myTodo/normalView");
		
		//조회
		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if(listSize == 0) {
			listSize = 5;
		}
		
		//todo 목록 캐시에서 조회
		List<Todo> todoList = (List<Todo>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(todoList == null) {
			User user = (User) getRequestAttribute("ikep.user");
			todoList = todoService.listMyTodo(user.getUserId(), listSize);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, todoList);
		}
		
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("todoList", todoList);
		//Todo 작업 키
		mav.addObject("todoSystemCode", TodoConstants.TODO_SYSTEM_CODE);
		mav.addObject("todoSubworkCode", TodoConstants.TODO_SUBWORK_CODE);
		
		return mav;
	}
	/**
	 * 포트릿 최대화 모드
	 * @param portletConfigId
	 * @param todoSearchCondition
	 * @return
	 */
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId, TodoSearchCondition todoSearchCondition) {
		ModelAndView mav = new ModelAndView("/lightpack/todo/portlet/myTodo/maxView");
		mav.addObject("portletConfigId", portletConfigId);

		//TodoSearchCondition 셋팅
		if(todoSearchCondition == null) {
			todoSearchCondition = new TodoSearchCondition();
		}
		if(todoSearchCondition.getSearchText() == null) {
			todoSearchCondition.setSearchText("");
		}
		User user = (User) getRequestAttribute("ikep.user");
		todoSearchCondition.setDirectorId(user.getUserId());
		todoSearchCondition.setWorkerId(user.getUserId());
		
		//조회
		SearchResult<Todo> searchResult = todoService.listMyTodoSearch(todoSearchCondition);
		mav.addObject("searchResult", searchResult);
		mav.addObject("todoSearchCondition", searchResult.getSearchCondition());
		
		//Todo 작업 키
		mav.addObject("todoSystemCode", TodoConstants.TODO_SYSTEM_CODE);
		mav.addObject("todoSubworkCode", TodoConstants.TODO_SUBWORK_CODE);
		
		return mav;
	}
}