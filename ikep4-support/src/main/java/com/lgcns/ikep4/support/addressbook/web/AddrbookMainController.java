/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.base.Constant;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.model.Category;
import com.lgcns.ikep4.support.addressbook.model.Contact;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.model.Person2group;
import com.lgcns.ikep4.support.addressbook.model.PersonList;
import com.lgcns.ikep4.support.addressbook.search.AddrgroupSearchCondition;
import com.lgcns.ikep4.support.addressbook.search.ContactSearchCondition;
import com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition;
import com.lgcns.ikep4.support.addressbook.service.AddrgroupService;
import com.lgcns.ikep4.support.addressbook.service.CategoryService;
import com.lgcns.ikep4.support.addressbook.service.ContactService;
import com.lgcns.ikep4.support.addressbook.service.PersonService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.excel.ExcelModelConstants;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.excel.ExcelViewModel;
import com.lgcns.ikep4.util.icard.ICardUtil;
import com.lgcns.ikep4.util.icard.model.ICard;
import com.lgcns.ikep4.util.icard.model.ITel;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 주소록 조회 입력 그룹 관리용 Controller
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: AddrbookMainController.java 16100 2011-08-03 05:35:43Z handul32
 *          $
 */
@Controller
@RequestMapping(value = "/support/addressbook")
@SessionAttributes("adressbook")
public class AddrbookMainController extends BaseController {

	/**
	 * 주소록 그룹 컨트롤용 Service
	 */
	@Autowired
	private AddrgroupService addrgroupService;

	/**
	 * 주소록 Person 컨드롤용 Service
	 */
	@Autowired
	private PersonService personService;

	/**
	 * 주소록 Contact History 컨트롤용 Service (현재 사용안함)
	 */
	@Autowired
	private ContactService contactService;

	/**
	 * 해당 모듈 관리 권한 컨트롤용 Service
	 */
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
    private UserDao userDao;

	/**
	 * Logger 작성용
	 */
	Log logger = LogFactory.getLog(AddrbookMainController.class);

	/**
	 * 주소록에서 주소록 목록을 조회 하는메인 페이지
	 * 
	 * @param addrgroupId 주소록 그룹 ID
	 * @param addrgroupName 주소록 그룹 명
	 * @param groupType 'P' 개인그룹 'O' 공용그룹
	 * @return 주소록 조회 메인 페이지 View
	 */
	@RequestMapping(value = "/addressbookHome.do")
	public ModelAndView getView(@RequestParam(value = "addrgroupId", required = false) String addrgroupId,
			@RequestParam(value = "addrgroupName", required = false) String addrgroupName,
			@RequestParam(value = "groupType", required = false) String paramGroupType) {

		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookhome");

		if (StringUtil.isEmpty(addrgroupId)) {
			addrgroupId = "";
		}

		String groupType = paramGroupType;
		if (StringUtil.isEmpty(groupType)) {
			groupType = "P";
		}

		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		mav.addObject("addrgroupId", addrgroupId);
		if (StringUtil.isEmpty(addrgroupName)) {
			if (StringUtil.isEmpty(addrgroupId)) {
				mav.addObject("addrgroupName", messageSource.getMessage(
						"ui.support.addressbook.addrgroup.private.totalList.title", null,
						new Locale(user.getLocaleCode())));
			} else if (addrgroupId.equals("NOGROUP")) {
				// 미분류시
				mav.addObject("addrgroupName", messageSource.getMessage(
						"ui.support.addressbook.addrgroup.private.undiffer.title", null,
						new Locale(user.getLocaleCode())));
			} else {
				Addrgroup addrgroup = new Addrgroup();
				addrgroup.setAddrgroupId(addrgroupId);
				addrgroup = addrgroupService.read(addrgroup);
				mav.addObject("addrgroupName", addrgroup.getAddrgroupName());
			}
		} else {
			mav.addObject("addrgroupName", addrgroupName);
		}
		mav.addObject("groupType", groupType);
		
		PersonSearchCondition paramPersonSearch = new PersonSearchCondition();
		paramPersonSearch.setViewMode("2");
		mav.addObject("personSearch", paramPersonSearch);

		return mav;
	}

	/**
	 * 주소록 그룹에 저장된 주소록의 목록을 조회 하여 카드 형식으로 보여주는 페이지
	 * 
	 * @param addrgroupId 주소록 그룹 ID
	 * @param addrgroupName 주소록 그룹 명
	 * @param groupType 'P' 개인그룹 'O' 공용그룹
	 * @param paramPersonSearch 주소록 검색용 Person 객체
	 * @return 카드형식 주소록 Person 목록 View
	 */
	@RequestMapping(value = "/readAddrbookListByAddrgroup.do")
	public ModelAndView readAddrbookListByAddrgroup(
			@RequestParam(value = "addrgroupId", required = false) String addrgroupId,
			@RequestParam(value = "addrgroupName", required = false) String addrgroupName,
			@RequestParam(value = "groupType", required = false) String paramGroupType,
			PersonSearchCondition paramPersonSearch) {

		ModelAndView mav = null;

		if (StringUtil.isEmpty(paramPersonSearch.getViewMode())) {
			paramPersonSearch.setViewMode("2");
		}
		if (paramPersonSearch.getViewMode().equals("2")) {
			mav = new ModelAndView("/support/addressbook/addressbooklistbody");
		} else {
			mav = new ModelAndView("/support/addressbook/addressbooklistbodyForList");
		}

		User user = (User) getRequestAttribute("ikep.user");
		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		String groupType = paramGroupType;
		if (StringUtil.isEmpty(groupType)) {
			groupType = "P";
		}
		PersonSearchCondition personSearch = paramPersonSearch;
		personSearch.setRegisterId(user.getUserId());
		personSearch.setIndexSearchLocale(user.getLocaleCode());
		if (StringUtil.isEmpty(addrgroupId)) {
			personSearch.setAddrgroupId(null);
		} else {
			personSearch.setAddrgroupId(addrgroupId);
		}
		if (StringUtil.isEmpty(groupType)) {
			personSearch.setGroupType(null);
		} else {
			personSearch.setGroupType(groupType);
		}

		// 검색용 파라미터 구분 재 편성
		personSearch = setSearchKeyWord(personSearch);

		SearchResult<Person> personList = personService.list(personSearch);
		BoardCode boardCode = new BoardCode();
		mav.addObject("personSearch", personList.getSearchCondition());
		mav.addObject("boardCode", boardCode);

		// Integer totalCount = personService.listCount(personSearch);

		mav.addObject("personList", personList);
		if (!(personList.isEmptyRecord())) {
			mav.addObject("personListSize", personList.getEntity().size());
		}

		// mav.addObject("personListSize", personList..size());
		mav.addObject("addrgroupId", addrgroupId);
		if (StringUtil.isEmpty(addrgroupName)) {
			if (StringUtil.isEmpty(addrgroupId)) {
				mav.addObject("addrgroupName", messageSource.getMessage(
						"ui.support.addressbook.addrgroup.private.totalList.title", null,
						new Locale(user.getLocaleCode())));
			} else if (addrgroupId.equals("NOGROUP")) {
				// 미분류시
				mav.addObject("addrgroupName", messageSource.getMessage(
						"ui.support.addressbook.addrgroup.private.undiffer.title", null,
						new Locale(user.getLocaleCode())));
			} else {
				Addrgroup addrgroup = new Addrgroup();
				addrgroup.setAddrgroupId(addrgroupId);
				addrgroup = addrgroupService.read(addrgroup);
				mav.addObject("addrgroupName", addrgroup.getAddrgroupName());
			}
		} else {
			mav.addObject("addrgroupName", addrgroupName);
		}
		mav.addObject("groupType", groupType);

		return mav;
	}

	/**
	 * 개인 그룹 및 공용 그룹 관리를 위한 메뉴 조회
	 * 
	 * @param groupType 'P' 개인그룹 'O' 공용그룹
	 * @return
	 */
	@RequestMapping(value = "/listAddrgroupList.do")
	public ModelAndView listAddrgroupList(@RequestParam(value = "groupType", required = false) String groupType) {

		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookgrouplist");

		User user = (User) getRequestAttribute("ikep.user");
		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		if (StringUtil.isEmpty(groupType) && groupType.equals("O") && (!isSystemAdmin)) {
			throw new IKEP4AuthorizedException();
		}

		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		// List<Map<String,Object>> listNavi =
		// getNavigationList(addrgroupNavigation);
		mav.addObject("groupType", groupType);
		// mav.addObject("addrgroupName", addrgroupName );
		// mav.addObject("addrgroupNavigation", addrgroupNavigation );
		// mav.addObject("listNavi", listNavi );
		// mav.addObject("listNaviSize", listNavi.size() );

		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(user.getUserId());
		addrgroupSearch.setPortalId(user.getPortalId());
		addrgroupSearch.setGroupType(groupType);
		//addrgroupService.list(addrgroupSearch);

		SearchResult<Addrgroup> addrgroupList = addrgroupService.list(addrgroupSearch);
		BoardCode boardCode = new BoardCode();

		// mav.addObject("searchResult", addrgroupList);
		mav.addObject("searchCondition", addrgroupList.getSearchCondition());
		mav.addObject("boardCode", boardCode);

		mav.addObject("addrgroupList", addrgroupList);
		mav.addObject("groupType", groupType);

		return mav;
	}
	
	@RequestMapping(value = "/editCategory.do")
	public ModelAndView editCategory() {

		Category categoryBoardId = new Category();
		categoryBoardId.setBoardId("100000000001");

		List<Category> categoryList = null;
		categoryList = this.categoryService.listCategory(categoryBoardId);		
		
		ModelAndView model = new ModelAndView("/support/addressbook/editCategory");
		User user = (User) getRequestAttribute("ikep.user");
		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		model.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		
		model.addObject("categoryList", categoryList);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}

	/**
	 * 개인 그룹 및 공용 그룹 을 생성 수정 하기 위한 등록 Form 을 반환 한다.
	 * 
	 * @param addrgroupId 주소록 그룹 ID
	 * @param groupType 'P' 개인그룹 'O' 공용그룹
	 * @return 그룹 을 생성 수정 위한 등록 Form
	 */
	@RequestMapping(value = "/editAddrgroupList.do")
	public ModelAndView editAddrgroupList(@RequestParam(value = "addrgroupId", required = false) String addrgroupId,
			@RequestParam(value = "groupType", required = false) String groupType) {

		String type = "CREATE";
		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookgroupform");

		User user = (User) getRequestAttribute("ikep.user");
		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		if (StringUtil.isEmpty(groupType) && groupType.equals("O") && (!isSystemAdmin)) {
			throw new IKEP4AuthorizedException();
		}

		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		// List<Map<String,Object>> listNavi =
		// getNavigationList(addrgroupNavigation);

		mav.addObject("addrgroupId", addrgroupId);
		mav.addObject("groupType", groupType);
		// mav.addObject("addrgroupName", addrgroupName );
		// mav.addObject("addrgroupNavigation", addrgroupNavigation );
		// mav.addObject("listNavi", listNavi );
		// mav.addObject("listNaviSize", listNavi.size() );

		PersonSearchCondition personSearch = new PersonSearchCondition();
		Addrgroup addrgroup = new Addrgroup();
		SearchResult<Person> personList = new SearchResult<Person>(personSearch);

		if (!(StringUtil.isEmpty(addrgroupId))) {
			addrgroup.setAddrgroupId(addrgroupId);
			addrgroup.setGroupType(groupType);
			addrgroup = addrgroupService.read(addrgroup);

			personSearch.setAddrgroupId(addrgroupId);
			personSearch.setFieldName("jobTitleName");
			personSearch.setUserLocaleCode(user.getLocaleCode());

			personList = personService.listAll(personSearch);
			BoardCode boardCode = new BoardCode();

			// mav.addObject("searchResult", addrgroupList);
			mav.addObject("searchCondition", personList.getSearchCondition());
			mav.addObject("boardCode", boardCode);

			type = "UPDATE";
		} else {
			// addrgroupId = idgenService.getNextId();
			// addrgroup.setAddrgroupId(addrgroupId);
			addrgroup.setGroupType(groupType);
			type = "CREATE";

		}

		Category categoryBoardId = new Category();
		List<Category> categoryList = null;
		categoryBoardId.setBoardId("100000000001");
		categoryList = this.categoryService.listCategory(categoryBoardId);

		mav.addObject("addrgroup", addrgroup);
		mav.addObject("personList", personList);
		mav.addObject("type", type);
		mav.addObject("categoryList", categoryList);

		return mav;
	}

	/**
	 * 주소록 사용자 그룹 저장
	 * 
	 * @param addrgroup 주소록 그룹 객체
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/saveAddrgroupList.do")
	@ResponseStatus(HttpStatus.OK)
	public void saveAddrgroupList(@Valid Addrgroup addrgroup, BindingResult result, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");

		if (addrgroup != null && (addrgroup.getGroupType()).equals("O")) {
			boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
			if (!isSystemAdmin) { // true : 관리권한이 있음, false : 관리 권한이 없음
				throw new IKEP4AuthorizedException();
			}
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		try {

			addrgroupService.saveAddrgroupList(addrgroup, user);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		status.setComplete();
	}

	/**
	 * 주소록 사용자 그룹 삭제
	 * 
	 * @param addrgroupId 주소록 그룹 ID
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/deleteAddrgroupList.do")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAddrgroupList(@RequestParam(value = "addrgroupId", required = false) String addrgroupId,
			SessionStatus status) {

		try {
			addrgroupService.deleteAddrgroupList(addrgroupId);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		status.setComplete();

	}

	/**
	 * 개인 주소록 Person 정보 입력 및 수정 FORM 등록 페이지를 반환 한다.
	 * 
	 * @param addrgroupId 주소록 그룹 ID
	 * @param groupType 'P' 개인그룹 'O' 공용그룹
	 * @param personId 주소록 개인 ID
	 * @return Person 정보 입력 및 수정 FORM View
	 */
	@RequestMapping(value = "/editPerson.do")
	public ModelAndView editPerson(@RequestParam(value = "addrgroupId", required = false) String paramAddrgroupId,
			@RequestParam(value = "groupType", required = false) String groupType,
			@RequestParam(value = "personId", required = false) String personId) {

		String type = "CREATE";
		String dispType = "EDIT";
		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookpersonform");

		User user = (User) getRequestAttribute("ikep.user");

		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		String addrgroupId = paramAddrgroupId;
		// List<Map<String,Object>> listNavi =
		// getNavigationList(addrgroupNavigation);
		mav.addObject("addrgroupId", addrgroupId);
		mav.addObject("groupType", groupType);
		// mav.addObject("addrgroupName", addrgroupName );
		// mav.addObject("addrgroupNavigation", addrgroupNavigation );
		// mav.addObject("listNavi", listNavi );
		// mav.addObject("listNaviSize", listNavi.size() );

		// 그룹 정보 조회
		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(user.getUserId());
		addrgroupSearch.setPortalId(user.getPortalId());
		addrgroupSearch.setGroupType(groupType);
		addrgroupService.list(addrgroupSearch);

		SearchResult<Addrgroup> addrgroupList = addrgroupService.list(addrgroupSearch);
		if (!(addrgroupList.isEmptyRecord())) {
			for (Addrgroup addrgroup : addrgroupList.getEntity()) {
				if (addrgroup.getAddrgroupId().equals(addrgroupId)) {
					mav.addObject("addrgroupName", addrgroup.getAddrgroupName());
				}
			}
		}
		BoardCode boardCode = new BoardCode();

		mav.addObject("searchCondition", addrgroupList.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("addrgroupList", addrgroupList);

		// 사용자 정보 조회
		Person person = new Person();

		if (!(StringUtil.isEmpty(personId))) {
			person.setPersonId(personId);
			if (StringUtil.isEmpty(addrgroupId)) {
				person.setAddrgroupId(null);
			} else {
				person.setAddrgroupId(addrgroupId);
			}

			person = personService.read(person);

			if (!(person.getCompanyUserId() == null || person.getCompanyUserId().equals(""))) {
				dispType = "VIEW";
			}

			type = "UPDATE";
		} else {

			dispType = "EDIT";
			type = "CREATE";

		}

		mav.addObject("person", person);
		mav.addObject("dispType", dispType);
		mav.addObject("type", type);

		return mav;
	}

	/**
	 * 주소록 사용자 Person 정보 저장
	 * 
	 * @param person Person 정보 객체
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/savePerson.do")
	@ResponseStatus(HttpStatus.OK)
	public void savePerson(@Valid Person person, BindingResult result, SessionStatus status) {

		User user = (User) getRequestAttribute("ikep.user");
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else {
			try {
				personService.savePerson(person, user);

			} catch (Exception ex) {
				throw new IKEP4AjaxException("code", ex);
			}
		}

		status.setComplete();
	}

	/**
	 * 주소록 사용자 연락처 삭제 연락처 삭제를 선택하면 Person 의 연락처 데이타와 연락처가 연결되어 있는 모든 그룹 목록에서도 삭제
	 * 된다.
	 * 
	 * @param personId 삭제할 Person ID
	 * @param userType O : 외부 사용자 I 내부 사용자
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/deletePerson.do")
	@ResponseStatus(HttpStatus.OK)
	public void deletePerson(@RequestParam(value = "personId", required = false) String personId,
			@RequestParam(value = "userType", required = false) String userType, SessionStatus status) {

		try {
			personService.removePersonByType(personId, userType);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		status.setComplete();
	}

	/**
	 * 주소록 사용자 목록에서 연락처 삭제 연락처 삭제를 선택하면 Person 의 연락처 데이타와 연락처가 연결되어 있는 모든 그룹
	 * 목록에서도 삭제 된다.
	 * 
	 * @param personId
	 * @param groupType
	 * @param userType
	 * @param status
	 */
	@RequestMapping(value = "/deletePersonList.do")
	@ResponseStatus(HttpStatus.OK)
	public void deletePersonList(@RequestParam(value = "personIdList", required = false) List<String> personIdList,
			SessionStatus status) {

		try {

			for (int i = 0; i < personIdList.size(); i++) {

				List<String> paramList = StringUtil.getTokens(personIdList.get(i), "|");

				personService.removePersonByType(paramList.get(0), paramList.get(1));

			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		status.setComplete();
	}
	
	@RequestMapping(value = "/copyTeamList.do")
	@ResponseStatus(HttpStatus.OK)
	public void copyTeamList(@RequestParam(value = "personIdList", required = false) List<String> personIdList,
			SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");
		Person2group person2group = new Person2group();
		person2group.setRegisterId(user.getUserId());
		person2group.setRegisterName(user.getUserName());
		person2group.setAddrgroupId(user.getGroupId());
		try {

			for (int i = 0; i < personIdList.size(); i++) {

				List<String> paramList = StringUtil.getTokens(personIdList.get(i), "|");
				person2group.setPersonId(paramList.get(0));
				person2group.setUserType(paramList.get(1));

				personService.copyTeamByType(person2group);

			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		status.setComplete();
	}
	
	@RequestMapping(value = "/copyMyList.do")
	@ResponseStatus(HttpStatus.OK)
	public void copyMyList(@RequestParam(value = "personIdList", required = false) List<String> personIdList,
			SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");
		Person person = new Person();
		try {

			for (int i = 0; i < personIdList.size(); i++) {

				List<String> paramList = StringUtil.getTokens(personIdList.get(i), "|");
				person.setPersonId(paramList.get(0));

				personService.copyMyAddr(person, user);

			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		status.setComplete();
	}

	/**
	 * 멤버를 그룹에서만 제외하고 공용 그룹에서 삭제는 그룹 목록에서만 제외 한다.
	 * 
	 * @param addrgroupId 주소록 그룹 ID
	 * @param groupType 'P' 개인그룹 'O' 공용그룹
	 * @param personIdList 제외할 Person 리스트 객체
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/removePersonFromGroup.do")
	@ResponseStatus(HttpStatus.OK)
	public void removePersonFromGroup(@RequestParam(value = "addrgroupId", required = false) String addrgroupId,
			@RequestParam(value = "groupType", required = false) String groupType,
			@RequestParam(value = "personIdList", required = false) List<String> personIdList, SessionStatus status) {

		try {

			personService.removePersonFromGroup(personIdList, addrgroupId);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		status.setComplete();
	}

	/**
	 * Social Connection 에서 등록된 리스트를 보여 주기 위한 페이지 (현재 사용안함)
	 * 
	 * @return Social Connection View 페이지
	 */
	@RequestMapping(value = "/listSocialConnection.do")
	public ModelAndView listSocialConnection() {

		ModelAndView mav = new ModelAndView("/support/addressbook/addressbooksocialconnect");

		User user = (User) getRequestAttribute("ikep.user");

		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		mav.addObject("userId", user.getUserId());
		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(user.getUserId());
		addrgroupSearch.setPortalId(user.getPortalId());
		addrgroupService.list(addrgroupSearch);

		SearchResult<Addrgroup> addrgroupList = addrgroupService.list(addrgroupSearch);
		BoardCode boardCode = new BoardCode();
		// mav.addObject("searchResult", addrgroupList);
		mav.addObject("searchCondition", addrgroupList.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("addrgroupList", addrgroupList);

		return mav;
	}

	/**
	 * Contact History page 조회 (현재 사용안함)
	 * 
	 * @return Contact History View 페이지
	 */
	@RequestMapping(value = "/listContactHistory.do")
	public ModelAndView listContactHistory() {

		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookcontacthistory");

		User user = (User) getRequestAttribute("ikep.user");

		mav.addObject("userId", user.getUserId());
		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		return mav;
	}

	/**
	 * Contact History 를 List 를 조회 (현재 사용안함)
	 * 
	 * @param registerId 조회하는 대상 ID
	 * @param searchContactId 조회 할 대상 ID
	 * @param searchType 조회 조건 타입
	 * @return Contact History 리스트 View
	 */
	@RequestMapping(value = "/readContactHistory.do")
	public ModelAndView readContactHistory(@RequestParam(value = "registerId", required = false) String registerId,
			@RequestParam(value = "searchContactId", required = false) String searchContactId,
			@RequestParam(value = "searchType", required = false, defaultValue = "post") String searchType) {

		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookconthistlist");
		User user = (User) getRequestAttribute("ikep.user");
		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		Integer fetchSize = Constant.DEFAULT_CONN_HIST_FETCH_SIZE;

		ContactSearchCondition contactSearch = new ContactSearchCondition();
		contactSearch.setRegisterId(registerId);
		contactSearch.setSearchContactId(searchContactId);
		contactSearch.setSearchType(searchType);
		contactSearch.setFetchSize(fetchSize);
		SearchResult<Contact> contactList = contactService.list(contactSearch);

		// BoardCode boardCode = new BoardCode();
		// mav.addObject("searchResult", addrgroupList);
		mav.addObject("searchCondition", contactList.getSearchCondition());
		// mav.addObject("boardCode", boardCode);
		mav.addObject("contactList", contactList);
		mav.addObject("searchContactId", searchContactId);

		return mav;
	}

	/**
	 * 주소록 Import 하는 페이지 조회용
	 * 
	 * @param step1 Import 파일 타입 vcard : ICARD outlook : 아웃륵 CSV 파일, excelfile :
	 *            엑셀 파일
	 * @param file 실제 import 할 파일
	 * @return
	 */
	@RequestMapping(value = "/listAddrbookImport.do")
	public ModelAndView listAddrbookImport(
			@RequestParam(value = "step1", required = false, defaultValue = "excelfile") String step1,
			@RequestParam(value = "file", required = false) CommonsMultipartFile file,
			@RequestParam(value = "pgroupType", required = false) String groupType,
			@RequestParam(value = "gType", required = false) String gType) {

		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookimport");
		User user = (User) getRequestAttribute("ikep.user");

		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(user.getUserId());
		addrgroupSearch.setPortalId(user.getPortalId());
		addrgroupSearch.setGroupType("P");
		addrgroupService.list(addrgroupSearch);

		SearchResult<Addrgroup> addrgroupList = addrgroupService.list(addrgroupSearch);
		addrgroupSearch.setGroupType("G");
		SearchResult<Addrgroup> paddrgroupList = addrgroupService.list(addrgroupSearch);
		addrgroupSearch.setGroupType("P");
		BoardCode boardCode = new BoardCode();
		// mav.addObject("searchResult", addrgroupList);
		mav.addObject("searchCondition", addrgroupList.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("addrgroupList", addrgroupList);
		mav.addObject("paddrgroupList", paddrgroupList);
		mav.addObject("groupType", groupType);
		mav.addObject("gType", gType);

		mav.addObject("step1", step1);
		List<Person> list = new ArrayList<Person>();
		InputStream uploadFile = null;

		if (file != null) {
			try {
				uploadFile = file.getInputStream();
				if (step1.equals("vcard")) {
					list = readICard(uploadFile);
				} else if (step1.equals("outlook")) {
					list = readOutlookCSV(uploadFile);
					// 현재 미사용
					// }else if ( step1.equals("expoutlook") ){
					// list = readExpOutlookCSV(uploadFile);
				} else if (step1.equals("excelfile")) {
					list = readExcelFile(uploadFile);
				}

				if (uploadFile != null) {
					try {
						uploadFile.close();
					} catch (IOException e1) {
					}
				}
				mav.addObject("status", "success");
				mav.addObject(
						"message",
						messageSource.getMessage("support.fileupload.message.upload.success", null,
								new Locale(user.getLocaleCode())));

			} catch (Exception e) {

				if (uploadFile != null) {
					try {
						uploadFile.close();
					} catch (IOException e1) {
					}
				}
				list = new ArrayList<Person>();
				mav.addObject("status", "error");
				mav.addObject(
						"message",
						messageSource.getMessage("support.fileupload.message.upload.fail", null,
								new Locale(user.getLocaleCode())));

			}
		}
		String[] personNames = new String[list.size()];
		PersonList personList = new PersonList();
		if("G".equals(groupType)){
			
			for(int ti = 0; ti < list.size(); ti++){
				personNames[ti] = list.get(ti).getPersonName();
			}
			List<Person> tmpPersonList = new ArrayList<Person>();
			
			tmpPersonList = (List<Person>) addrgroupService.personList(personNames);
			personList.setPersonList(tmpPersonList);
			personList.setPersonListSize(tmpPersonList.size());
		}else{
			personList.setPersonList(list);
			personList.setPersonListSize(list.size());
		}
		boolean ecmrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ECM");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			ecmrole = true;
		}
		mav.addObject("ecmrole", ecmrole);
		mav.addObject("personList", personList);

		return mav;
	}
	
	@RequestMapping(value = "/listAddrbookImportEcm.do")
	public ModelAndView listAddrbookImportEcm(String ecmFileUrl2,
			@RequestParam(value = "step1", required = false, defaultValue = "excelfile") String step1,
			@RequestParam(value = "file", required = false) CommonsMultipartFile file,
			@RequestParam(value = "pgroupType", required = false) String groupType,
			@RequestParam(value = "gType", required = false) String gType) {

		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookimport");
		User user = (User) getRequestAttribute("ikep.user");

		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(user.getUserId());
		addrgroupSearch.setPortalId(user.getPortalId());
		addrgroupSearch.setGroupType("P");
		addrgroupService.list(addrgroupSearch);

		SearchResult<Addrgroup> addrgroupList = addrgroupService.list(addrgroupSearch);
		addrgroupSearch.setGroupType("G");
		SearchResult<Addrgroup> paddrgroupList = addrgroupService.list(addrgroupSearch);
		addrgroupSearch.setGroupType("P");
		BoardCode boardCode = new BoardCode();
		// mav.addObject("searchResult", addrgroupList);
		mav.addObject("searchCondition", addrgroupList.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("addrgroupList", addrgroupList);
		mav.addObject("paddrgroupList", paddrgroupList);
		mav.addObject("groupType", groupType);
		mav.addObject("gType", gType);

		mav.addObject("step1", step1);
		try {
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		
		URL url = new URL(ecmFileUrl2);
		File srcFile2 = new File(tempUploadRoot+"/planerExcel.xls");
		FileUtils.copyURLToFile(url, srcFile2);
		
		FileInputStream uploadFile = new FileInputStream(srcFile2);
		
		List<Person> list = new ArrayList<Person>();

		if (uploadFile != null) {
			try {
				list = readExcelEcmFile(uploadFile);

				if (uploadFile != null) {
					try {
						uploadFile.close();
					} catch (IOException e1) {
					}
				}
				mav.addObject("status", "success");
				mav.addObject(
						"message",
						messageSource.getMessage("support.fileupload.message.upload.success", null,
								new Locale(user.getLocaleCode())));

			} catch (Exception e) {

				if (uploadFile != null) {
					try {
						uploadFile.close();
					} catch (IOException e1) {
					}
				}
				list = new ArrayList<Person>();
				mav.addObject("status", "error");
				mav.addObject(
						"message",
						messageSource.getMessage("support.fileupload.message.upload.fail", null,
								new Locale(user.getLocaleCode())));

			}
		}
		String[] personNames = new String[list.size()];
		PersonList personList = new PersonList();
		if("G".equals(groupType)){
			
			for(int ti = 0; ti < list.size(); ti++){
				personNames[ti] = list.get(ti).getPersonName();
			}
			List<Person> tmpPersonList = new ArrayList<Person>();
			
			tmpPersonList = (List<Person>) addrgroupService.personList(personNames);
			personList.setPersonList(tmpPersonList);
			personList.setPersonListSize(tmpPersonList.size());
		}else{
			personList.setPersonList(list);
			personList.setPersonListSize(list.size());
		}

		boolean ecmrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ECM");
		int mntRole = userDao.getUserRoleCheck(map);
		if(mntRole > 0){
			ecmrole = true;
		}
		mav.addObject("ecmrole", ecmrole);
		mav.addObject("personList", personList);
		
		} catch (Exception e) {
			return mav;
		}

		return mav;
	}
	
	public String getFilePath(String path) {

		String date = getToday("yyyy-MM-dd");
		String yyyy = date.substring(0, 4);
		String mm = date.substring(5, 7);

		return path + yyyy + "/" + mm + "/" + date;
	}
	
	public String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/**
	 * Import 파일을 통해 불러온 주소록 사용자 일괄 저장
	 * 
	 * @param personList 저장할 Person 객체 List
	 * @param result Validation 결과 값
	 * @param status SessionStatus 값
	 */
	@RequestMapping(value = "/saveAddressbookFile.do")
	@ResponseStatus(HttpStatus.OK)
	public void saveAddressbookFile(PersonList personList, BindingResult result, SessionStatus status, String pgroupTp) {

		User user = (User) getRequestAttribute("ikep.user");

		try {
			personService.saveAddrressbookFile(personList, user, pgroupTp);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		status.setComplete();
	}

	/**
	 * 엑셀 예제 파일 다운로드용 설명 팝업
	 * 
	 * @return 다운로드 설명 팝업 View
	 */
	@RequestMapping(value = "/downAddrbookImportTemplatePopup.do")
	public ModelAndView downAddrbookImportTemplatePopup(
			@RequestParam(value = "groupType", required = false) String groupType
	// @RequestParam(value = "addrgroupName", required = false) String
	// addrgroupName
	// , @RequestParam(value = "addrgroupNavigation", required = false) String
	// addrgroupNavigation
	) {

		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookimporttemplatepopup");

		User user = (User) getRequestAttribute("ikep.user");

		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));
		mav.addObject("groupType", groupType);
		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		return mav;
	}

	/**
	 * 주소록 데이타를 Export 할수 있는 조회 페이지
	 * 
	 * @return Export 조회 View 페이지
	 */
	@RequestMapping(value = "/listAddrbookExport.do")
	public ModelAndView listAddrbookExport(@RequestParam(value = "gType", required = false) String gType) {

		ModelAndView mav = new ModelAndView("/support/addressbook/addressbookexport");

		User user = (User) getRequestAttribute("ikep.user");

		mav.addObject("userName", user.getUserName());
		mav.addObject("userEnglishName", user.getUserEnglishName());

		boolean isSystemAdmin = aclService.isSystemAdmin("Addressbook", user.getUserId());
		mav.addObject("publicManageFlag", String.valueOf(isSystemAdmin));

		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setRegisterId(user.getUserId());
		addrgroupSearch.setPortalId(user.getPortalId());
		addrgroupSearch.setGroupType("P");
		addrgroupService.list(addrgroupSearch);

		SearchResult<Addrgroup> addrgroupList = addrgroupService.list(addrgroupSearch);
		addrgroupSearch.setGroupType("G");
		SearchResult<Addrgroup> paddrgroupList = addrgroupService.list(addrgroupSearch);
		addrgroupSearch.setGroupType("P");
		BoardCode boardCode = new BoardCode();
		// mav.addObject("searchResult", addrgroupList);
		mav.addObject("searchCondition", addrgroupList.getSearchCondition());
		mav.addObject("boardCode", boardCode);
		mav.addObject("addrgroupList", addrgroupList);
		mav.addObject("paddrgroupList", paddrgroupList);
		mav.addObject("gType", gType);

		return mav;
	}

	/**
	 * 다운 로드전 해당 아이템이 다운로드 될 주소록 데이타가 있는지 체크 하는 메서드
	 * 
	 * @param step1 Export 파일 타입 vcard : ICARD outlook : 아웃륵 CSV 파일, excelfile :
	 *            엑셀 파일
	 * @param step2 Export 할 주소록 그룹
	 * @return 다운로드 가능 건수
	 */
	@RequestMapping(value = "/chkDownloadAddressbookFile.do")
	public @ResponseBody
	String checkownloadAddressbookFile(@RequestParam(value = "step1", required = false) String step1,
			@RequestParam(value = "step2", required = false) String step2,
			@RequestParam(value = "gType", required = false) String gType) {
		Integer downloadRowCnt = 0;
		
		User user = (User) getRequestAttribute("ikep.user");

		PersonSearchCondition personSearch = new PersonSearchCondition();
		if (!(StringUtil.isEmpty(step2))) {
			personSearch.setAddrgroupId(step2);
		}
		personSearch.setGroupType(gType);
		personSearch.setRegisterId(user.getUserId());
		personSearch.setFieldName("jobTitleName");
		personSearch.setUserLocaleCode(user.getLocaleCode());

		SearchResult<Person> searchResult = personService.listAll(personSearch);
		List<Person> personList = searchResult.getEntity();

		if (personList != null && !(personList.isEmpty())) {
			downloadRowCnt = searchResult.getRecordCount();
		}

		return String.valueOf(downloadRowCnt);
	}
	
	public void downloadAddressbookFile(@RequestParam(value = "step1", required = false) String step1,
			@RequestParam(value = "step2", required = false) String step2, HttpServletResponse response) {
		User user = (User) getRequestAttribute("ikep.user");

		PersonSearchCondition personSearch = new PersonSearchCondition();
		if (!(StringUtil.isEmpty(step2))) {
			personSearch.setAddrgroupId(step2);
		}
		personSearch.setGroupType("P");
		personSearch.setRegisterId(user.getUserId());
		personSearch.setFieldName("jobTitleName");
		personSearch.setUserLocaleCode(user.getLocaleCode());

		SearchResult<Person> searchResult = personService.listAll(personSearch);
		List<Person> personList = searchResult.getEntity();

		if (!(StringUtil.isEmpty(step1)) && step1.equals("vcard")) {

			if (personList.size() > 0) {
				downloadICard(personList, response);
			}

		} else if (!(StringUtil.isEmpty(step1)) && step1.equals("outlook")) {

			if (personList.size() > 0) {
				downloadOutlook(personList, response);
			}

		} else {

			if (personList.size() > 0) {
				downloadExcel(personList, response);
			}

		}

	}

	/**
	 * 실제 주소록 데이타 Export Download 해주는 메서드
	 * 
	 * @param step1 Export 파일 타입 vcard : ICARD outlook : 아웃륵 CSV 파일, excelfile :
	 *            엑셀 파일
	 * @param step2 Export 할 주소록 그룹
	 * @param response 파일 다운로드 OutputStream
	 */
	@RequestMapping(value = "/downloadAddressbookCSVFile.do")
	public void downloadAddressbookCSVFile(@RequestParam(value = "step1", required = false) String step1,
			@RequestParam(value = "step2", required = false) String step2, HttpServletResponse response) {
		User user = (User) getRequestAttribute("ikep.user");

		PersonSearchCondition personSearch = new PersonSearchCondition();
		if (!(StringUtil.isEmpty(step2))) {
			personSearch.setAddrgroupId(step2);
		}
		personSearch.setGroupType("P");
		personSearch.setRegisterId(user.getUserId());
		personSearch.setFieldName("jobTitleName");
		personSearch.setUserLocaleCode(user.getLocaleCode());

		SearchResult<Person> searchResult = personService.listAll(personSearch);
		List<Person> personList = searchResult.getEntity();

		if (!(StringUtil.isEmpty(step1)) && step1.equals("vcard")) {

			if (personList.size() > 0) {
				downloadICard(personList, response);
			}

		} else if (!(StringUtil.isEmpty(step1)) && step1.equals("outlook")) {

			if (personList.size() > 0) {
				downloadOutlook(personList, response);
			}

		} else {

			if (personList.size() > 0) {
				downloadExcel(personList, response);
			}

		}

	}
	
	@RequestMapping(value = "/downloadAddressbookExcelFile.do")
	public ModelAndView downloadAddressbookExcelFile(@RequestParam(value = "step1", required = false) String step1,
			@RequestParam(value = "gType", required = false) String gType,
			@RequestParam(value = "step2", required = false) String step2, HttpServletResponse response) {
		
		ExcelViewModel excel = new ExcelViewModel();  
		
		User user = (User) getRequestAttribute("ikep.user");

		PersonSearchCondition personSearch = new PersonSearchCondition();
		if (!(StringUtil.isEmpty(step2))) {
			personSearch.setAddrgroupId(step2);
		}
		personSearch.setGroupType(gType);
		personSearch.setRegisterId(user.getUserId());
		personSearch.setFieldName("jobTitleName");
		personSearch.setUserLocaleCode(user.getLocaleCode());

		SearchResult<Person> searchResult = personService.listAll(personSearch);
		List<Person> personList = searchResult.getEntity();
		
		try {				
			
			if( personList.size() > 0 ) {
				
				
				String fileName = personList.get(0).getRegisterName() + "_" + DateUtil.getTodayDateTime("yyyyMMddHHmmss") + "_" + ".xls";
				
			
				excel.setFileName(fileName);   
			    excel.setSheetName("Sheet");   
			    
			    excel.setTitle(personList.get(0).getRegisterName() + "_" + getMessage("ui.support.addressbook.addrgroup.private." , "addressbook"));  
			    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
			    
			    excel.addColumn("personName", "personName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
			    excel.addColumn("empNo", "empNo", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("mailAddress", "mailAddress", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("companyName", "companyName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("teamName", "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("jobRankName", "jobRankName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("mobilePhoneno", "mobilePhoneno", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("officePhoneno", "officePhoneno", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
				excel.addColumn("personMemo", "personMemo", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
							
				excel.setDataList(personList); 
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}

	/**
	 * 구분자를 가진 String을 받아서 List형태로 리턴함
	 * 
	 * @param str 읽어 들인 CSV 파일의 readLine 값
	 * @param delim 구분자 (,)
	 * @return 구분자로 구분된 String 의 Map 데이타
	 */
	public static List<Map<String, Object>> getAddrbookCSV(String str, String delim) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String[] splitStr = str.split("[,]");
		// StringTokenizer st = new StringTokenizer(str, delim);
		for (int i = 0; i < splitStr.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String nextValue = splitStr[i];
			if (StringUtil.isEmpty(nextValue)) {
				nextValue = " ";
			}
			map.put("addrItem", nextValue);
			list.add(map);
		}
		return list;
	}

	/**
	 * 주소록을 ICard 형태로 다운로드 받기 위해 호출해주는 메서드
	 * 
	 * @param personList 주소록 Person 리스트
	 * @param response 파일 다운로드 OutputStream
	 */
	public void downloadICard(List<Person> personList, HttpServletResponse response) {

		String fileName = personList.get(0).getRegisterName() + "_" + DateUtil.getTodayDateTime("yyyyMMddHHmmss") + "_"
				+ ".vcf";

		List<ICard> icardList = new ArrayList<ICard>();

		for (Person person : personList) {

			ICard icard = new ICard();
			icard.setGivenName(person.getPersonName());
			icard.setFullName(person.getPersonName());
			icard.setOrg(person.getCompanyName());
			icard.setDept(person.getTeamName());
			icard.setTitle(person.getJobRankName());
			icard.setEmail(person.getMailAddress());

			ITel mobileITel = new ITel();
			mobileITel.setType("CELL");
			mobileITel.setNumber(person.getMobilePhoneno());
			icard.addITelList(mobileITel);

			ITel officeITel = new ITel();
			officeITel.setType("WORK");
			officeITel.setNumber(person.getOfficePhoneno());
			icard.addITelList(officeITel);

			icardList.add(icard);

		}

		// 파일 저장
		ICardUtil.saveICard(icardList, fileName, response);
	}

	/**
	 * 읽어온 ICard 파일에서 Person 리스트를 추출하여 반환 한다.
	 * 
	 * @param uploadFile ICard file의 InputStream
	 * @return 추출된 Person 리스트
	 */
	public List<Person> readICard(InputStream uploadFile) {

		List<Person> list = new ArrayList<Person>();

		List<ICard> icardList = ICardUtil.readICard(uploadFile);

		if (icardList.size() > 0) {
			for (ICard icard : icardList) {
				Person person = new Person();
				// person.setPersonId(personId);
				person.setPersonName(icard.getFullName());
				person.setCompanyName(icard.getOrg());
				person.setTeamName(icard.getDept());
				person.setJobRankName(icard.getTitle());
				person.setMailAddress(icard.getEmail());

				List<ITel> itelList = icard.getItelList();
				if (itelList != null) {
					for (ITel iTel : itelList) {
						if (iTel.getType().equals("CELL")) {
							person.setMobilePhoneno(iTel.getNumber());
						} else if (iTel.getType().equals("WORK")) {
							person.setOfficePhoneno(iTel.getNumber());
						}
					}
				}

				list.add(person);
			}
		}

		return list;
	}

	/**
	 * 주소록을 Outlook용 CSV 파일 형태로 다운로드 받기 위해 호출해주는 메서드
	 * 
	 * @param personList 주소록 Person 리스트
	 * @param response 파일 다운로드 OutputStream
	 */
	public void downloadOutlook(List<Person> personList, HttpServletResponse response) {

		ServletOutputStream sout = null;
		String fileName = personList.get(0).getRegisterName() + "_" + DateUtil.getTodayDateTime("yyyyMMddHHmmss") + "_"
				+ ".csv";

		try {
			String[] fields = { "호칭(영문)", "이름", "중간 이름", "성", "호칭(한글)", "회사", "부서", "직함", "근무지 주소 번지", "근무지 번지 2",
					"근무지 번지 3", "근무지 구/군/시", "근무지 시/도", "근무지 우편 번호", "근무지 국가/지역", "집 번지", "집 번지 2", "집 번지 3",
					"집 주소 구/군/시", "집 주소 시/도", "집 주소 우편 번호", "집 주소 국가/지역", "기타 번지", "기타 번지 2", "기타 번지 3", "기타 구/군/시",
					"기타 시/도", "기타 우편 번호", "기타 국가/지역", "비서 전화 번호", "근무지 팩스", "근무처 전화", "근무처 전화 2", "다시 걸 전화", "카폰",
					"회사 대표 전화", "집 팩스", "집 전화 번호", "집 전화 2", "ISDN", "휴대폰", "기타 팩스", "기타 전화", "호출기", "기본 전화", "무선 전화",
					"TTY/TDD 전화", "텔렉스", "거리", "결혼 기념일", "계정", "관리자 이름", "국가", "근무처 주소 사서함", "기타 주소 사서함", "디렉터리 서버",
					"머리글자", "메모", "배우자", "범주 항목", "비서 이름", "비용 정보", "사무실 위치", "사용자 1", "사용자 2", "사용자 3", "사용자 4", "생일",
					"성별", "숨김", "언어", "우선 순위", "우편물 종류", "웹 페이지,인터넷 약속 있음/약속 없음,자녀,전자 메일 주소,전자 메일 유형,전자 메일 표시 이름",
					"전자 메일 2 주소", "전자 메일 2 유형", "전자 메일 2 표시 이름", "전자 메일 3 주소", "전자 메일 3 유형", "전자 메일 3 표시 이름",
					"주민 등록 번호", "중심어", "직업", "집 주소 사서함", "추천인", "취미", "ID 번호" };

			// Excel 파일 저장
			sout = response.getOutputStream();

			fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
			// response.setContentType("text/plain;charset=UTF-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");

			int iMaxLeg = fields.length;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < iMaxLeg; i++) {
				sb.append(fields[i]);

				if (i != (fields.length - 1)) {
					sb.append(",");
				} else {
					sb.append("\r\n");
				}
			}
			sout.write(sb.toString().getBytes("EUC-KR"));

			for (Person person : personList) {
				StringBuffer sbline = new StringBuffer();
				for (int i = 0; i < iMaxLeg; i++) {
					switch (i) {
					case Constant.OUTLOOK_FIRST_NAME_INDEX: // 이름
						sbline.append(person.getPersonName()).append(",");
						break;
					case Constant.OUTLOOK_LAST_NAME_INDEX: // 성
						sbline.append(",");
						break;
					case Constant.OUTLOOK_COMPANY_INDEX: // 회사
						sbline.append(person.getCompanyName()).append(",");
						break;
					case Constant.OUTLOOK_TEAM_NAME_INDEX: // 부서
						sbline.append(person.getTeamName()).append(",");
						break;
					case Constant.OUTLOOK_JOB_RANK_NAME_INDEX: // 직함
						sbline.append(person.getJobRankName()).append(",");
						break;
					case Constant.OUTLOOK_OFFICE_PHONE_INDEX: // 사무실전화
						sbline.append(person.getOfficePhoneno()).append(",");
						break;
					case Constant.OUTLOOK_MOBILE_PHONE_INDEX: // 휴대전화
						sbline.append(person.getMobilePhoneno()).append(",");
						break;
					case Constant.OUTLOOK_MEMO_INDEX: // 메모
						sbline.append(
								StringUtil.replace(StringUtil.replace(person.getPersonMemo(), "\n", ""), "\r", ""))
								.append(",");
						break;
					case Constant.OUTLOOK_MAIL_ADDRESS_INDEX: // 이메일
						sbline.append(person.getMailAddress()).append(",");
						break;
					default:
						sbline.append(",");
						break;
					}
				}
				sbline.append("\r\n");
				sout.write(sbline.toString().getBytes("EUC-KR"));
			}
		} catch (Exception e) {
			logger.error("Scheduled " + e.toString() + " Misfired!!");
		} finally {
			if (sout != null) {
				try {
					sout.flush();
				} catch (IOException e1) {
				}
			}
			if (sout != null) {
				try {
					sout.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 읽어온 MS Outlook 파일에서 Person 리스트를 추출하여 반환 한다.
	 * 
	 * @param uploadFile ICard file의 InputStream
	 * @return 추출된 Person 리스트
	 * @throws IOException File IO Exception
	 */
	public List<Person> readOutlookCSV(InputStream uploadFile) throws IOException {

		List<Person> list = new ArrayList<Person>();

		BufferedReader in = new BufferedReader(new InputStreamReader(uploadFile, "EUC-KR"));

		List<Map<String, Object>> messageFile = null;
		String lineStr = null;

		int idx = 0;
		while ((lineStr = in.readLine()) != null) {
			if (idx > 0) {

				messageFile = getAddrbookCSV(StringUtil.replace(lineStr, "\"", ""), ",");
				Person person = new Person();
				for (int i = 0; i < messageFile.size(); i++) {
					switch (i) {
					case Constant.OUTLOOK_FIRST_NAME_INDEX: // 이름
						person.setPersonName((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.OUTLOOK_LAST_NAME_INDEX: // 성
						person.setPersonName((String) messageFile.get(i).get("addrItem") + person.getPersonName());
						break;
					case Constant.OUTLOOK_COMPANY_INDEX: // 회사
						person.setCompanyName((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.OUTLOOK_TEAM_NAME_INDEX: // 부서
						person.setTeamName((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.OUTLOOK_JOB_RANK_NAME_INDEX: // 직함
						person.setJobRankName((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.OUTLOOK_OFFICE_PHONE_INDEX: // 사무실전화
						person.setOfficePhoneno((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.OUTLOOK_MOBILE_PHONE_INDEX: // 휴대전화
						person.setMobilePhoneno((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.OUTLOOK_MEMO_INDEX: // 메모
						person.setPersonMemo((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.OUTLOOK_MAIL_ADDRESS_INDEX: // 이메일
						person.setMailAddress((String) messageFile.get(i).get("addrItem"));
						break;
					}
				}
				list.add(person);
			}
			idx++;
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e1) {
			}
		}

		return list;
	}

	/**
	 * 읽어온 Outlook Express 파일에서 Person 리스트를 추출하여 반환 한다.
	 * 
	 * @param uploadFile ICard file의 InputStream
	 * @return 추출된 Person 리스트
	 * @throws IOException File IO Exception
	 */
	public List<Person> readExpOutlookCSV(InputStream uploadFile) throws IOException {

		List<Person> list = new ArrayList<Person>();

		BufferedReader in = new BufferedReader(new InputStreamReader(uploadFile, "EUC-KR"));

		List<Map<String, Object>> messageFile = null;
		String lineStr = null;

		int idx = 0;
		while ((lineStr = in.readLine()) != null) {
			if (idx > 0) {
				messageFile = getAddrbookCSV(lineStr, ",");
				Person person = new Person();
				for (int i = 0; i < messageFile.size(); i++) {
					switch (i) {
					case Constant.EXP_OUTLOOK_FULL_NAME_INDEX: // 전체 이름
						person.setPersonName((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.EXP_OUTLOOK_MAIL_ADDRESS_INDEX: // 이메일
						person.setMailAddress((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.EXP_OUTLOOK_MOBILE_PHONE_INDEX: // 휴대전화
						person.setMobilePhoneno((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.EXP_OUTLOOK_OFFICE_PHONE_INDEX: // 사무실전화
						person.setOfficePhoneno((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.EXP_OUTLOOK_COMPANY_INDEX: // 회사
						person.setCompanyName((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.EXP_OUTLOOK_JOB_RANK_NAME_INDEX: // 직함
						person.setJobRankName((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.EXP_OUTLOOK_TEAM_NAME_INDEX: // 부서
						person.setTeamName((String) messageFile.get(i).get("addrItem"));
						break;
					case Constant.EXP_OUTLOOK_MEMO_INDEX: // 메모
						person.setPersonMemo((String) messageFile.get(i).get("addrItem"));
						break;

					}
				}

				list.add(person);
			}
			idx++;
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e1) {
			}
		}

		return list;
	}

	/**
	 * 주소록을 Excel 파일 형태로 다운로드 받기 위해 호출해주는 메서드
	 * 
	 * @param personList 주소록 Person 리스트
	 * @param response 파일 다운로드 OutputStream
	 */
     public void downloadExcel(List<Person> personList, HttpServletResponse response) {

		String fileName = personList.get(0).getRegisterName() + "_" + DateUtil.getTodayDateTime("yyyyMMddHHmmss") + "_"
				+ ".xlsx";

		List<Object> testList = new ArrayList<Object>();
		for (Person person : personList) {
			testList.add(person);
		}

		LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
		titleMap.put("personName", "personName");
		titleMap.put("mailAddress", "mailAddress");
		titleMap.put("companyName", "companyName");
		titleMap.put("teamName", "teamName");
		titleMap.put("jobRankName", "jobRankName");
		titleMap.put("mobilePhoneno", "mobilePhoneno");
		titleMap.put("officePhoneno", "officePhoneno");
		titleMap.put("personMemo", "personMemo");

		// 파일 저장
		ExcelUtil.saveExcel(titleMap, testList, fileName, response);

	}

	/**
	 * 읽어온 Outlook Express 파일에서 Person 리스트를 추출하여 반환 한다.
	 * 
	 * @param uploadFile ICard file의 InputStream
	 * @return 추출된 Person 리스트
	 * @throws IOException File IO Exception
	 */
	public List<Person> readExcelFile(InputStream uploadFile) throws IOException {

		List<Person> list = new ArrayList<Person>();

		String className = "com.lgcns.ikep4.support.addressbook.model.Person";
		List<Object> dataList = ExcelUtil.readExcel(className, uploadFile, 1);
		
		//가져온 데이터에서 Null인 경우를 제외하기  위한 검증값
		Person pTest = new Person();

		for (Object object : dataList) {	
			
			if(!pTest.equals((Person) object))
			{
				list.add((Person) object);
			}
		}

		return list;
	}
	
	public List<Person> readExcelEcmFile(FileInputStream uploadFile) throws IOException {

		List<Person> list = new ArrayList<Person>();

		String className = "com.lgcns.ikep4.support.addressbook.model.Person";
		List<Object> dataList = ExcelUtil.readEcmExcel(className, uploadFile, 1);
		
		//가져온 데이터에서 Null인 경우를 제외하기  위한 검증값
		Person pTest = new Person();

		for (Object object : dataList) {	
			
			if(!pTest.equals((Person) object))
			{
				list.add((Person) object);
			}
		}

		return list;
	}

	/**
	 * 검색용 파리미터를 받아서 Search KeyWord 별로 분류 하여 리턴한다.
	 * 
	 * @param personSearch Person 조회용 객체
	 * @return 검색 파라미터 구분된 Person 조회용 객체
	 */
	public PersonSearchCondition setSearchKeyWord(PersonSearchCondition personSearch) {

		if (!(StringUtil.isEmpty(personSearch.getSearchKeyword()))) {
			if ((personSearch.getSearchColumn()).equals("PNAME")) {
				personSearch.setPersonName(personSearch.getSearchKeyword());
			} else if ((personSearch.getSearchColumn()).equals("CNAME")) {
				personSearch.setCompanyName(personSearch.getSearchKeyword());
			} else if ((personSearch.getSearchColumn()).equals("HPNUM")) {
				personSearch.setMobilePhoneno(personSearch.getSearchKeyword());
			} else if ((personSearch.getSearchColumn()).equals("OPNUM")) {
				personSearch.setOfficePhoneno(personSearch.getSearchKeyword());
			} else if ((personSearch.getSearchColumn()).equals("MEMOFD")) {
				personSearch.setPersonMemo(personSearch.getSearchKeyword());
			} else if ((personSearch.getSearchColumn()).equals("MAILADR")) {
				personSearch.setMailAddress(personSearch.getSearchKeyword());
			}
		}

		return personSearch;
	}
	
	private String getMessage(String prefix, String key){
		String msg =  messageSource.getMessage(prefix+ key, null, Locale.getDefault());		
		return msg;
	}
	
	@RequestMapping(value = "/messengerAddrgroupList.do")
	public ModelAndView readAddrgroupListByGroupType() {
		
		ModelAndView mav = new ModelAndView("/support/addressbook/messengerAddrgroupList");

		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		addrgroupSearch.setPortalId("P000001");
		addrgroupSearch.setGroupType("O");
		
		SearchResult<Addrgroup> addrgroupList = addrgroupService.list(addrgroupSearch);

		mav.addObject("addrgroupList", addrgroupList);
		
		return mav;
	}

}
