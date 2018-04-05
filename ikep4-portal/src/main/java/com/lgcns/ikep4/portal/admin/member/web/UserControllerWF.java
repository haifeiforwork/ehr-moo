/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.member.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 사용자 관리 화면 컨트롤러
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: UserControllerWF.java 19179 2012-06-11 07:47:15Z malboru80 $
 */
@Controller
@RequestMapping(value = "/portal/admin/member/user/workflow")
public class UserControllerWF extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Autowired
	private GroupService groupService;

	private static final String ROOT_GROUP_ID = "A00000";

	/**
	 * 초기 사용자 트리를 그림
	 * 
	 * @param searchCondition 목록 검색 조건
	 * @return ModelAndView
	 */
	@RequestMapping("/getList.do")
	public ModelAndView list(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			UserSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("portal/admin/member/user/workflow/list");
		// 첫 팝업 호출시 ORG 타입으로 호출
		List<Map<String, Object>> list = getOrgGroupAndUser(null);
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("items", list);

		String listJson = "";

		try {
			ObjectMapper mapper = new ObjectMapper();
			listJson = mapper.writeValueAsString(items);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		mav.addObject("deptItems", listJson);
		mav.addObject("searchCondition", searchCondition);

		return mav;
	}

	/**
	 * 우측의 폼 화면에 들어가는 사용자 정보를 가져온다. 첫 로딩시에는 빈 폼을 보여주고 사용자가 리스트에서 항목을 선택하는 경우 해당
	 * 항목의 ID를 이용하여 정보를 가져와서 보여준다.
	 * 
	 * @param userId 상세정보를 요청받은 사용자 ID
	 * @param model Model 객체
	 * @return String 상세정보화면 URI
	 */
	@RequestMapping(value = "/form.do", method = RequestMethod.GET)
	public String getForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String userId, Model model) {

		User user = null;
		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		if (userId != null && !"".equals(userId)) {

			user = userService.read(userId);
			List<Map<String, String>> groupList = userService.selectGroupForUser(userId);
			user.setCheckIdFlag("modify");

			if (user.getGroupId() != null && !"".equals(user.getGroupId())) {
				user.setIsRepresentGroup("1");
			} else {
				user.setIsRepresentGroup("0");
			}

			Group leadingGroup = userService.selectLeadingGroup(userId);

			if (leadingGroup != null) {
				user.setLeadingGroupId(leadingGroup.getGroupId());
			}

			model.addAttribute("groupList", groupList);
			model.addAttribute("leadingGroup", leadingGroup);
		} else {
			user = new User();
			user.setIsRepresentGroup("1");
			user.setUserStatus("C");
			user.setCheckIdFlag("new");
		}

		model.addAttribute("jobClassList", userService.selectJobClassAll(portal.getPortalId()));
		model.addAttribute("timezoneList", userService.selectTimezoneAll(sessionUser.getLocaleCode()));
		model.addAttribute("localeList", userService.selectLocaleCodeAll(sessionUser.getLocaleCode()));
		model.addAttribute("nationList", userService.selectNationAll(sessionUser.getLocaleCode()));
		model.addAttribute("jobRankList", userService.selectJobRankAll(portal.getPortalId()));
		model.addAttribute("jobDutyList", userService.selectJobDutyAll(portal.getPortalId()));
		model.addAttribute("jobPositionList", userService.selectJobPositionAll(portal.getPortalId()));
		model.addAttribute("jobTitleList", userService.selectJobTitleAll(portal.getPortalId()));
		model.addAttribute("user", user);
		model.addAttribute("sessionLocaleCode", sessionUser.getLocaleCode());
		model.addAttribute("userLocaleCode", sessionUser.getLocaleCode());

		return "portal/admin/member/user/workflow/form";
	}

	/**
	 * 등록/수정시에 해당 사용자 ID의 중복을 체크하여 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는
	 * 문자열을 반환한다.
	 * 
	 * @param id 중복을 체크할 사용자 ID
	 * @return String 중복 유무를 알려주는 문자열 Flag
	 */
	@RequestMapping(value = "/checkId.do")
	public @ResponseBody
	String checkId(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String id) {

		boolean result = userService.exists(id);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}

	/**
	 * 사용자을 신규 등록하거나 수정한다. ID가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다. 생성, 수정이 끝난
	 * 후에는 해당 사용자의 ID를 반환하여 form을 불러오는데 사용하는데
	 * 
	 * @param user 신규/수정 등록하고자 하는 사용자 객체
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return id 최종 등록한 사용자의 상세 정보를 가져오기 위한 사용자 ID
	 */
	@RequestMapping(value = "/createUser.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @ValidEx User user,
			BindingResult result, SessionStatus status, HttpServletRequest request, AccessingResult accessResult) {

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		User sessionUser = (User) getRequestAttribute("ikep.user");

		String id = user.getUserId();
		String removePicture = request.getParameter("removePicture");
		String removeProfilePicture = request.getParameter("removeProfilePicture");
		String pictureId = user.getPictureId();
		String profilePictureId = user.getProfilePictureId();
		String preUserPassword = request.getParameter("preUserPassword");
		String preMailPassword = request.getParameter("preMailPassword");
		String teamEnglishName = groupService.read(user.getGroupId()).getGroupEnglishName();
		String isRepresentGroup = StringUtil.nvl(user.getIsRepresentGroup(), "0");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		boolean isCodeExist = userService.exists(id);

		if (removePicture == null) {
			removePicture = "N";
		} else {
			removePicture = "Y";
		}

		if (removeProfilePicture == null) {
			removeProfilePicture = "N";
		} else {
			removeProfilePicture = "Y";
		}

		if (isCodeExist) { // 수정

			user.setIsRepresentGroup(isRepresentGroup);
			user.setTeamEnglishName(teamEnglishName);
			user.setRegisterId(sessionUser.getUserId());
			user.setRegisterName(sessionUser.getUserName());
			user.setUpdaterId(sessionUser.getUserId());
			user.setUpdaterName(sessionUser.getUserName());

			if (!user.getUserPassword().equals(preUserPassword)) {
				user.setUserPassword(EncryptUtil.encryptSha(user.getUserId() + user.getUserPassword()));
			}

			if (!user.getMailPassword().equals(preMailPassword)) {
				user.setMailPassword(EncryptUtil.encryptText(user.getMailPassword()));
			}

			// image 파일 업로드
			if (removePicture.equals("Y") && removeProfilePicture.equals("Y")) {
				user.setPictureId(null);
				user.setProfilePictureId(null);
				userService.update(user);

				fileService.removeFileLink(pictureId, user.getUserId(), "PO");
				fileService.removeFileLink(profilePictureId, user.getUserId(), "PO");
			} else {
				if (removePicture.equals("Y") && removeProfilePicture.equals("N")) {
					user.setPictureId(null);
					userService.update(user);

					fileService.removeFileLink(pictureId, user.getUserId(), "PO");
					fileService.createFileLink(profilePictureId, user.getUserId(), "PO", user);
				} else if (removePicture.equals("N") && removeProfilePicture.equals("Y")) {
					user.setProfilePictureId(null);
					userService.update(user);

					fileService.createFileLink(pictureId, user.getUserId(), "PO", user);
					fileService.removeFileLink(profilePictureId, user.getUserId(), "PO");
				} else {
					userService.update(user);

					fileService.createFileLink(pictureId, user.getUserId(), "PO", user);
					fileService.createFileLink(profilePictureId, user.getUserId(), "PO", user);
				}
			}

		} else { // 생성

			user.setIsRepresentGroup(isRepresentGroup);
			user.setUserPassword(EncryptUtil.encryptSha(user.getUserId() + user.getUserPassword()));
			user.setMailPassword(EncryptUtil.encryptText(user.getMailPassword()));
			user.setTeamEnglishName(teamEnglishName);
			user.setPortalId(portal.getPortalId());
			user.setRegisterId(sessionUser.getUserId());
			user.setRegisterName(sessionUser.getUserName());
			user.setUpdaterId(sessionUser.getUserId());
			user.setUpdaterName(sessionUser.getUserName());

			// image 파일 업로드
			if (removePicture.equals("Y") && removeProfilePicture.equals("Y")) {
				user.setPictureId(null);
				user.setProfilePictureId(null);
				userService.create(user);
			} else {
				if (removePicture.equals("Y") && removeProfilePicture.equals("N")) {
					user.setPictureId(null);
					userService.create(user);

					fileService.createFileLink(profilePictureId, user.getUserId(), "PO", user);
				} else if (removePicture.equals("N") && removeProfilePicture.equals("Y")) {
					user.setProfilePictureId(null);
					userService.create(user);

					fileService.createFileLink(pictureId, user.getUserId(), "PO", user);
				} else {
					userService.create(user);
					fileService.createFileLink(pictureId, user.getUserId(), "PO", user);
					fileService.createFileLink(profilePictureId, user.getUserId(), "PO", user);
				}
			}
		}

		status.setComplete();

		return id;
	}

	/**
	 * 사용자를 삭제한다.
	 * 
	 * @param user 삭제할 사용자 ID
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteUser.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) User user,
			AccessingResult accessResult) {

		String pictureId = user.getPictureId();
		String profilePictureId = user.getProfilePictureId();

		if(!StringUtil.isEmpty(pictureId)) {
			fileService.removeFileLink(pictureId, user.getUserId(), "PO");
		}
		if(!StringUtil.isEmpty(profilePictureId)) {
			fileService.createFileLink(profilePictureId, user.getUserId(), "PO", user);
		}

		userService.delete(user);

		return "redirect:/portal/admin/member/user/workflow/getList.do";
	}

	/**
	 * 해당 그룹에 속한 사용자를 가져온다.
	 * 
	 * @param groupId 조회할 그룹 ID
	 * @param response HttpServletResponse 객체
	 * @return item 그룹에 해당하는 사용자 정보가 담긴 Map
	 */
	@RequestMapping("/requestGroupChildren.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> requestGroupChildren(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam(value = "groupId", required = false) String groupId, HttpServletResponse response) {

		String tempGroupId = groupId;

		if (groupId == null || groupId.equals("")) {
			tempGroupId = ROOT_GROUP_ID;
		}

		List<Map<String, Object>> list = null;
		Map<String, Object> item = new HashMap<String, Object>();

		try {
			list = getOrgGroupAndUser(tempGroupId);

			item.put("items", list);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return item;
	}

	/**
	 * 해당 그룹에 대한 그룹과 사용자 정보를 가져옴
	 * 
	 * @param groupId 조회할 그룹의 ID
	 * @return
	 */
	private List<Map<String, Object>> getOrgGroupAndUser(String groupId) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(sessionuser.getUserId());
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);
		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", group.getGroupName());
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}

		List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, sessionuser.getUserId());
		for (User user : userList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "user");
			map.put("name", user.getUserName());
			map.put("code", "");
			map.put("parent", user.getGroupId());
			map.put("id", user.getUserId());
			map.put("empNo", user.getEmpNo());
			map.put("email", user.getMail());
			map.put("mobile", user.getMobile());
			map.put("jobTitle", user.getJobTitleName());
			map.put("teamName", user.getTeamName());
			list.add(map);
		}

		return list;
	}

	/**
	 * MS Office Excel을 이용하여 사용자를 대량 입력하기 위한 양식을 내려받는 함수
	 * 
	 * @param req HttpServletRequest 객체
	 * @return mav
	 */
	@RequestMapping("/excelForm.do")
	public ModelAndView excelForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			HttpServletRequest req) {

		ModelAndView mav = new ModelAndView("portal/admin/member/user/workflow/excelForm");

		// 더블 서밋방지 Token 셋팅
		String token = HttpUtil.setToken(req);
		mav.addObject("token", token);

		return mav;
	}

	/**
	 * 대량 입력을 위한 Excel 파일을 업로드하는 함수
	 * 
	 * @param file 업로드할 Excel 파일
	 * @param req HttpServletRequest 객체
	 * @return mav
	 */
	@RequestMapping("/excelUpload.do")
	public ModelAndView excelUpload(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("file") CommonsMultipartFile file, HttpServletRequest req) {

		ModelAndView mav = new ModelAndView("portal/admin/member/user/workflow/excelResult");

		try {

			// 더블 서밋방지 Token 체크
			if (HttpUtil.isValidToken(req)) {

				InputStream inp = file.getInputStream();

				User userSession = (User) getRequestAttribute("ikep.user");
				Portal portal = (Portal) getRequestAttribute("ikep.portal");

				String className = "com.lgcns.ikep4.support.user.member.model.User";

				List<Object> list = ExcelUtil.readExcel(className, inp, 1);

				int successCount = 0;
				int failCount = 0;
				List<User> userList = new ArrayList<User>();

				for (Object obj : list) {

					try {

						User user = (User) obj;
						user.setRegisterId(userSession.getUserId());
						user.setRegisterName(userSession.getUserName());
						user.setUpdaterId(userSession.getUserId());
						user.setUpdaterName(userSession.getUserName());
						user.setPortalId(portal.getPortalId());
						user.setUserPassword(EncryptUtil.encryptSha(user.getUserId() + user.getUserPassword()));

						user = setData(user);

						if (userService.exists(user.getUserId())) {
							userService.updateForExcel(user);
						} else {
							userService.createForExcel(user);
						}

						user.setSuccessYn("Y");
						successCount++;

					} catch (Exception e) {

						User user = (User) obj;
						user.setSuccessYn("N");
						user.setErrMsg(e.getMessage());
						userList.add(user);
						failCount++;
					}
				}

				mav.addObject("userList", userList);
				mav.addObject("totalCount", list.size());
				mav.addObject("successCount", successCount);
				mav.addObject("failCount", failCount);

				// Token 초기화
				String token = HttpUtil.setToken(req);
				mav.addObject("token", token);
			}

		} catch (Exception e) {
			return mav;
		}

		return mav;
	}

	/**
	 * 엑셀 입력에 필요한 사용자 정보를 세팅한다.
	 * 
	 * @param user 정보를 세팅할 사용자 객체
	 * @return User 객체
	 */
	private User setData(User user) {

		if (user.getUserStatus().equals("재직")) {
			user.setUserStatus("C");
		} else if (user.getUserStatus().equals("휴직")) {
			user.setUserStatus("H");
		} else {
			user.setUserStatus("T");
		}

		if (user.getLeader().equals("예")) {
			user.setLeader("1");
		} else {
			user.setLeader("0");
		}

		Map<String, String> param = new HashMap<String, String>();
		param.put("paramField", "group_name");
		param.put("paramTable", "ikep4_ev_group");
		param.put("paramCondition", "group_id");
		param.put("paramValue", user.getGroupId());

		user.setTeamName(userService.selectJobCode(param));

		param.clear();
		param.put("paramField", "job_class_code");
		param.put("paramTable", "ikep4_ev_job_class");
		param.put("paramCondition", "job_class_name");
		param.put("paramValue", user.getJobClassName());

		user.setJobClassCode(userService.selectJobCode(param));

		param.clear();
		param.put("paramField", "job_rank_code");
		param.put("paramTable", "ikep4_ev_job_rank");
		param.put("paramCondition", "job_rank_name");
		param.put("paramValue", user.getJobRankName());

		user.setJobRankCode(userService.selectJobCode(param));

		param.clear();
		param.put("paramField", "job_duty_code");
		param.put("paramTable", "ikep4_ev_job_duty");
		param.put("paramCondition", "job_duty_name");
		param.put("paramValue", user.getJobDutyName());

		user.setJobDutyCode(userService.selectJobCode(param));

		param.clear();
		param.put("paramField", "job_position_code");
		param.put("paramTable", "ikep4_ev_job_position");
		param.put("paramCondition", "job_position_name");
		param.put("paramValue", user.getJobPositionName());

		user.setJobPositionCode(userService.selectJobCode(param));

		param.clear();
		param.put("paramField", "job_title_code");
		param.put("paramTable", "ikep4_ev_job_title");
		param.put("paramCondition", "job_title_name");
		param.put("paramValue", user.getJobTitleName());

		user.setJobTitleCode(userService.selectJobCode(param));

		return user;
	}

	/**
	 * 사용자의 패스워드를 초기화하는 화면을 보여줌
	 * 
	 * @param userId 패스워드를 초기화할 사용자의 ID
	 * @param isAllUser 전체 사용자인이 아닌지 체크하는 플래그
	 * @return ModelAndView
	 */
	@RequestMapping("/initPasswordForm.do")
	public ModelAndView initPasswordForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String userId, String isAllUser) {

		ModelAndView mav = new ModelAndView("portal/admin/member/user/workflow/initPasswordForm");

		mav.addObject("userId", userId);
		mav.addObject("isAllUser", isAllUser);

		return mav;
	}

	/**
	 * 사용자의 패스워드를 초기화한다.
	 * 
	 * @param userId 패스워드를 초기화할 사용자의 ID
	 * @param userPassword 새로 설정할 패스워드
	 * @param isAllUser 전체 사용자인이 아닌지 체크하는 플래그
	 * @return 성공을 알리는 문자열 플래그
	 */
	@RequestMapping("/initPassword.do")
	public @ResponseBody
	String initPassword(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String userId, String userPassword, String isAllUser) {

		UserSearchCondition searchCondition = new UserSearchCondition();
		searchCondition.setUserId(userId);

		if (isAllUser != null && isAllUser.equals("Y")) {
			searchCondition.setIsAllUser(isAllUser);
		} else {
			searchCondition.setIsAllUser(null);
		}

		List<User> userList = userService.selectForPassword(searchCondition);

		List<User> upadteList = new ArrayList<User>();
		for (User user : userList) {
			user.setUserPassword(EncryptUtil.encryptSha(user.getUserId() + userPassword));
			upadteList.add(user);
		}

		userService.updateForPassword(upadteList);

		return "success";
	}
}