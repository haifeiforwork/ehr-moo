package com.lgcns.ikep4.support.popup.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition;
import com.lgcns.ikep4.support.addressbook.service.PersonService;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobClass;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.code.service.JobClassService;
import com.lgcns.ikep4.support.user.code.service.JobDutyService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.support.user.role.service.RoleService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 팝업 검색 처리 Controller
 * 
 * @author 유승목
 * @version $Id: PopupController.java 16273 2011-08-18 07:07:47Z giljae $
 */
@Controller
@RequestMapping(value = "/support/popup")
public class PopupController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	GroupService groupService;

	@Autowired
	RoleService roleService;

	@Autowired
	JobClassService jobClassService;

	@Autowired
	JobDutyService jobDutyService;
	
	@Autowired
	PersonService personService;

	@RequestMapping("/popupTest")
	public String popupTest() {

		return "support/popup/popupTest";
	}

	/**
	 * 사용자 검색 팝업창
	 * 
	 * @param name
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/popupUser")
	public ModelAndView popupUser(String name, String column, String isMulti) {

		ModelAndView mav = new ModelAndView("support/popup/popupUser");

		UserSearchCondition searchCondition = new UserSearchCondition();

		searchCondition.setSearchColumn(column);
		searchCondition.setSearchWord(name);

		mav.addObject("searchCondition", searchCondition);
		mav.addObject("isMulti", isMulti);

		return mav;
	}
	
	@RequestMapping("/subPopupUser")
	public ModelAndView subPopupUser(String name, String column, String isMulti) {

		ModelAndView mav = new ModelAndView("support/popup/subPopupUser");

		UserSearchCondition searchCondition = new UserSearchCondition();

		searchCondition.setSearchColumn(column);
		searchCondition.setSearchWord(name);

		mav.addObject("searchCondition", searchCondition);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 사용자 리스트 검색
	 * 
	 * @param searchCondition
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/getPopupUserList")
	public ModelAndView getPopupUserList(UserSearchCondition searchCondition, String isMulti) {

		ModelAndView mav = new ModelAndView("/support/popup/popupUserList");
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("USER_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);
		
		SearchResult<User> searchResult = userService.list(searchCondition);

		BoardCode boardCode = new BoardCode();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 사용자 정보 검색
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/getUser")
	public @ResponseBody
	User getUser(String name, String column) {

		User user = null;
		UserSearchCondition searchCondition = new UserSearchCondition();
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		searchCondition.setSearchColumn(column);
		searchCondition.setSearchWord(name);

		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("USER_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);
		
		SearchResult<User> searchResult = userService.list(searchCondition);

		if (searchResult.isEmptyRecord()) {
			user = new User();
			user.setUserName("isEmpty");
		} else {
			if ((searchResult.getEntity()).size() == 1) {
				user = (searchResult.getEntity()).get(0);
			} else {
				user = new User();
				user.setUserName("isMany");
			}
		}

		return user;
	}

	/**
	 * 부서 검색 팝업창
	 * 
	 * @param name
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/popupGroup")
	public ModelAndView popupGroup(String name, String isMulti) {

		ModelAndView mav = new ModelAndView("support/popup/popupGroup");

		UserSearchCondition searchCondition = new UserSearchCondition();

		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);

		mav.addObject("searchCondition", searchCondition);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 부서 리스트 검색
	 * 
	 * @param searchCondition
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/getPopupGroupList")
	public ModelAndView getPopupGroupList(UserSearchCondition searchCondition, String isMulti) {

		ModelAndView mav = new ModelAndView("/support/popup/popupGroupList");
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("GROUP_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setGroupTypeId("G0001");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);
		
		SearchResult<Group> searchResult = groupService.list(searchCondition);

		BoardCode boardCode = new BoardCode();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 부서 정보 검색
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/getGroup")
	public @ResponseBody
	Group getGroup(String name) {

		Group group = null;
		UserSearchCondition searchCondition = new UserSearchCondition();
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);

		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("GROUP_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setGroupTypeId("G0001");
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);
		
		SearchResult<Group> searchResult = groupService.list(searchCondition);

		if (searchResult.isEmptyRecord()) {
			group = new Group();
			group.setGroupName("isEmpty");
		} else {
			if ((searchResult.getEntity()).size() == 1) {
				group = (searchResult.getEntity()).get(0);
			} else {
				group = new Group();
				group.setGroupName("isMany");
			}
		}

		return group;
	}

	/**
	 * 그룹 검색 팝업창
	 * 
	 * @param name
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/popupGroupType")
	public ModelAndView popupGroupType(String name, String isMulti) {

		ModelAndView mav = new ModelAndView("support/popup/popupGroupType");

		UserSearchCondition searchCondition = new UserSearchCondition();

		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);

		mav.addObject("searchCondition", searchCondition);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 그룹 리스트 검색
	 * 
	 * @param searchCondition
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/getPopupGroupTypeList")
	public ModelAndView getPopupGroupTypeList(UserSearchCondition searchCondition, String isMulti) {

		ModelAndView mav = new ModelAndView("/support/popup/popupGroupTypeList");
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("GROUP_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);
		
		SearchResult<Group> searchResult = groupService.list(searchCondition);

		BoardCode boardCode = new BoardCode();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 그룹 정보 검색
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/getGroupType")
	public @ResponseBody
	Group getGroupType(String name) {

		Group group = null;
		UserSearchCondition searchCondition = new UserSearchCondition();
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);

		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("GROUP_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);
		
		SearchResult<Group> searchResult = groupService.list(searchCondition);

		if (searchResult.isEmptyRecord()) {
			group = new Group();
			group.setGroupName("isEmpty");
		} else {
			if ((searchResult.getEntity()).size() == 1) {
				group = (searchResult.getEntity()).get(0);
			} else {
				group = new Group();
				group.setGroupName("isMany");
			}
		}

		return group;
	}

	/**
	 * 역할 검색 팝업창
	 * 
	 * @param name
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/popupRole")
	public ModelAndView popupRole(String name, String isMulti) {

		ModelAndView mav = new ModelAndView("support/popup/popupRole");

		UserSearchCondition searchCondition = new UserSearchCondition();

		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);

		mav.addObject("searchCondition", searchCondition);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 역할 리스트 검색
	 * 
	 * @param searchCondition
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/getPopupRoleList")
	public ModelAndView getPopupRoleList(AdminSearchCondition searchCondition, String isMulti) {

		ModelAndView mav = new ModelAndView("/support/popup/popupRoleList");
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("ROLE_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);
		
		SearchResult<Role> searchResult = roleService.list(searchCondition);

		BoardCode boardCode = new BoardCode();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 역할 정보 검색
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/getRole")
	public @ResponseBody
	Role getRole(String name) {

		Role role = null;
		AdminSearchCondition searchCondition = new AdminSearchCondition();
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);

		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("ROLE_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setUserLocaleCode(sessionuser.getLocaleCode());
		searchCondition.setPortalId(portalId);

		SearchResult<Role> searchResult = roleService.list(searchCondition);

		if (searchResult.isEmptyRecord()) {
			role = new Role();
			role.setRoleName("isEmpty");
		} else {
			if ((searchResult.getEntity()).size() == 1) {
				role = (searchResult.getEntity()).get(0);
			} else {
				role = new Role();
				role.setRoleName("isMany");
			}
		}

		return role;
	}

	/**
	 * 고용형태 검색 팝업창
	 * 
	 * @param name
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/popupJobClass")
	public ModelAndView popupJobClass(String name, String isMulti) {

		ModelAndView mav = new ModelAndView("support/popup/popupJobClass");

		AdminSearchCondition searchCondition = new AdminSearchCondition();

		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);

		mav.addObject("searchCondition", searchCondition);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 고용형태 리스트 검색
	 * 
	 * @param searchCondition
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/getPopupJobClassList")
	public ModelAndView getPopupJobClassList(AdminSearchCondition searchCondition, String isMulti) {

		ModelAndView mav = new ModelAndView("/support/popup/popupJobClassList");

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("JOB_CLASS_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setFieldName("jobClassName");
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setPortalId(portalId);

		SearchResult<JobClass> searchResult = jobClassService.list(searchCondition);

		BoardCode boardCode = new BoardCode();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 고용형태 정보 검색
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/getJobClass")
	public @ResponseBody
	JobClass getJobClass(String name) {

		JobClass jobClass = null;
		AdminSearchCondition searchCondition = new AdminSearchCondition();

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);
		
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("JOB_CLASS_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setFieldName("jobClassName");
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setPortalId(portalId);

		SearchResult<JobClass> searchResult = jobClassService.list(searchCondition);

		if (searchResult.isEmptyRecord()) {
			jobClass = new JobClass();
			jobClass.setJobClassName("isEmpty");
		} else {
			if ((searchResult.getEntity()).size() == 1) {
				jobClass = (searchResult.getEntity()).get(0);
			} else {
				jobClass = new JobClass();
				jobClass.setJobClassName("isMany");
			}
		}

		return jobClass;
	}

	/**
	 * 직책 검색 팝업창
	 * 
	 * @param name
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/popupJobDuty")
	public ModelAndView popupJobDuty(String name, String isMulti) {

		ModelAndView mav = new ModelAndView("support/popup/popupJobDuty");

		AdminSearchCondition searchCondition = new AdminSearchCondition();

		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);

		mav.addObject("searchCondition", searchCondition);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 직책 리스트 검색
	 * 
	 * @param searchCondition
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/getPopupJobDutyList")
	public ModelAndView getPopupJobDutyList(AdminSearchCondition searchCondition, String isMulti) {

		ModelAndView mav = new ModelAndView("/support/popup/popupJobDutyList");

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("JOB_DUTY_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setFieldName("jobDutyName");
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setPortalId(portalId);

		SearchResult<JobDuty> searchResult = jobDutyService.list(searchCondition);

		BoardCode boardCode = new BoardCode();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 직책 정보 검색
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/getJobDuty")
	public @ResponseBody
	JobDuty getJobDuty(String name) {

		JobDuty jobDuty = null;
		AdminSearchCondition searchCondition = new AdminSearchCondition();

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		searchCondition.setSearchColumn("title");
		searchCondition.setSearchWord(name);
		
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("JOB_DUTY_NAME");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("ASC");
		}
		searchCondition.setFieldName("jobDutyName");
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setPortalId(portalId);

		SearchResult<JobDuty> searchResult = jobDutyService.list(searchCondition);

		if (searchResult.isEmptyRecord()) {
			jobDuty = new JobDuty();
			jobDuty.setJobDutyName("isEmpty");
		} else {
			if ((searchResult.getEntity()).size() == 1) {
				jobDuty = (searchResult.getEntity()).get(0);
			} else {
				jobDuty = new JobDuty();
				jobDuty.setJobDutyName("isMany");
			}
		}

		return jobDuty;
	}

	/**
	 * 내가 등록한 주소록 사용자 검색 팝업창
	 * 
	 * @param name
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/popupAddrPerson")
	public ModelAndView popupAddrPerson(String isMulti) {

		ModelAndView mav = new ModelAndView("support/popup/popupAddrPerson");

		PersonSearchCondition personSearch = new PersonSearchCondition();
		mav.addObject("PersonSearch", personSearch);
		
		mav.addObject("isMulti", isMulti);

		return mav;
	}

	/**
	 * 내가 등록한 주소록 사용자 리스트 검색
	 * 
	 * @param searchCondition
	 * @param isMulti
	 * @return
	 */
	@RequestMapping("/getAddrPersonList")
	public ModelAndView getAddrPersonList(PersonSearchCondition personSearch, String isMulti) {

		ModelAndView mav = new ModelAndView("/support/popup/popupAddrPersonList");
		User user = (User) getRequestAttribute("ikep.user");
		personSearch.setRegisterId(user.getUserId());
		personSearch.setUserType("P");
		SearchResult<Person> searchResult = personService.listAllPopUp(personSearch);
		BoardCode boardCode = new BoardCode(); 
		mav.addObject("searchResult", searchResult);
		mav.addObject("PersonSearch", searchResult.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		
		mav.addObject("searchPersonName", personSearch.getPersonName());
		mav.addObject("isMulti", isMulti);

		return mav;
	}
	
	@RequestMapping("/popupNotice")
	public ModelAndView popupNotice() {

		ModelAndView mav = new ModelAndView("/support/popup/popupNotice");

		return mav;
	}
}
