package com.lgcns.ikep4.servicepack.workalignment.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprListService;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoConstants;
import com.lgcns.ikep4.lightpack.todo.service.TodoService;
import com.lgcns.ikep4.servicepack.workalignment.constants.WorkalignmentConstants;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;


@Controller
@RequestMapping(value = "/servicepack/workalignment")
public class WorkAlignmentController extends BaseController {

	@Autowired
	private TodoService todoService;

	@Autowired
	private CalendarService calendarService;

//	@Autowired
//	private WorkplaceListService workplaceListService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private ApprListService apprListService;

	/**
	 * 메인
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/workAlignment")
	public ModelAndView listTodo() throws ParseException {

		ModelAndView mav = new ModelAndView("/servicepack/workalignment/workAlignment");

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		List<Todo> todoList = todoService.listMyTodo(user.getUserId(), WorkalignmentConstants.MY_TODO_PER);
		int todoListCnt = todoList.size();

		List<Todo> orderList = todoService.listMyOrderDueWeek(user.getUserId());
		List<Todo> todayFirst = todoService.listMyTodoDueToday(user.getUserId());
		List<Todo> tomorrowFirst = todoService.listMyTodoDueTomorrow(user.getUserId());
		// List<Schedule> todaySchedule =
		// calendarService.readTodaySchedule(user.getUserId());
		// List<Schedule> tomorrowSchedule =
		// calendarService.readTomorrowSchedule(user.getUserId());

//		WorkplaceSearchCondition workplaceSearchCondition = new WorkplaceSearchCondition();

//		workplaceSearchCondition.setQueryId("todoList");
//		workplaceSearchCondition.setUserId(user.getUserId());

		// SearchResult<WorkItemBean> approvalList =
		// this.workplaceListService.workplaceWorkList(workplaceSearchCondition);
		// int approvalListCnt = 0;

		// if (approvalList.getEntity() != null) {
		// approvalListCnt = approvalList.getEntity().size();
		// }

//		workplaceSearchCondition.setQueryId("myRequestList");
//		workplaceSearchCondition.setUserId(user.getUserId());
		// SearchResult<WorkItemBean> requestList =
		// this.workplaceListService.workplaceWorkList(workplaceSearchCondition);

		// SimpleDateFormat todayTop = new SimpleDateFormat("yyyy.MM.dd (EEE)");
		Date todayTop = timeZoneSupportService.convertTimeZone();// new Date();
		Date tomorrowTop = new Date(todayTop.getTime());// new Date();

		tomorrowTop.setDate(todayTop.getDate() + 1);

		// 결재해야할 문서
		ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();
		apprListSearchCondition.setUserId(user.getUserId());
		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setListType("listApprTodo");
		apprListSearchCondition.setSortColumn("apprReqDate");
		apprListSearchCondition.setSortType("DESC");

		SearchResult<ApprList> myTodoResult = this.apprListService.listBySearchCondition(apprListSearchCondition);
		mav.addObject("myTodoResult", myTodoResult);

		// 기안한 문서
		ApprListSearchCondition apprListSearchCondition2 = new ApprListSearchCondition();
		apprListSearchCondition2.setUserId(user.getUserId());
		apprListSearchCondition2.setPortalId(portalId);
		apprListSearchCondition2.setListType("myRequestList");
		apprListSearchCondition2.setSortColumn("apprReqDate");
		apprListSearchCondition2.setSortType("DESC");

		SearchResult<ApprList> myRequestResult = this.apprListService.listByMyRequest(apprListSearchCondition2);
		mav.addObject("myRequestResult", myRequestResult);

		// mav.addObject("approvalList", approvalList.getEntity());
		// mav.addObject("approvalListCnt", approvalListCnt);
		// mav.addObject("requestList", requestList.getEntity());
		mav.addObject("todoList", todoList);
		mav.addObject("todoListCnt", todoListCnt);
		mav.addObject("todoSystemCode", TodoConstants.TODO_SYSTEM_CODE);
		mav.addObject("todoSubworkCode", TodoConstants.TODO_SUBWORK_CODE);
		mav.addObject("orderList", orderList);
		mav.addObject("todayFirst", todayFirst);
		mav.addObject("tomorrowFirst", tomorrowFirst);
		// mav.addObject("todaySchedule",todaySchedule);
		// mav.addObject("tomorrowSchedule",tomorrowSchedule);
		mav.addObject("todayTop", todayTop);
		mav.addObject("tomorrowTop", tomorrowTop);
		return mav;
	}

	/**
	 * 서브 메인
	 * 
	 * @return
	 */
	@RequestMapping(value = "/subMain.do", method = RequestMethod.GET)
	public String subBody() {

		return "/servicepack/workalignment/subMain";
	}
}
