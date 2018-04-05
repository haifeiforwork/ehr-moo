package com.lgcns.ikep4.portal.ehr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.portal.moorimmss.service.MssTeamTreeSpecialService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value="/portal/ehr")
public class MoorimEHRController extends BaseController {

	/** The acl service. */
	@Autowired
	private ACLService aclService;

	@Autowired
    private UserDao userDao;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MssTeamTreeSpecialService mssTeamTreeSpecialService;
	//Log
	protected final Log log = LogFactory.getLog(this.getClass());

	public boolean isSystemAdmin(User user) {

		return this.aclService.isSystemAdmin("MoorimEss", user.getUserId());
	}

	@RequestMapping(value = "/ehrPersonalMng.do")
	public ModelAndView ehrPesonalMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrPersonalMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrPersonalMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));

		return mav;
	}
	
	@RequestMapping(value = "/ehrBusinessSupport.do")
	public ModelAndView ehrBusinessSupport(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrBusinessSupport");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrBusinessSupport");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));

		return mav;
	}


	
	@RequestMapping(value = "/ehrEvaluationMng.do")
	public ModelAndView ehrEvaluationMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrEvaluationMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "evaluationMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}

	
	@RequestMapping(value = "/ehrManPowerMng.do")
	public ModelAndView ehrManPowerMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrManPowerMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrManPowerMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}
	
	@RequestMapping(value = "/ehrResultMng.do")
	public ModelAndView ehrResultMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrResultMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		if(!StringUtil.isEmpty(user.getEmpNo())){
			userService.authCheck(user);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrResultMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}
	
	@RequestMapping(value = "/ehrTeamMng.do")
	public ModelAndView ehrTeamMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrTeamMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		
		String searchRootGroupId="";
		List<Map<String, Object>> list = null;
		if(isSystemAdmin){
			searchRootGroupId="";//전체인원
			
			list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
		}else{
			
			
			List<MssTeamTreeSpecial> specialList = mssTeamTreeSpecialService.getList(user.getUserId());
			if(specialList!=null && specialList.size()>0){
				list = getOrgGroupAndUser3(specialList, "", "USER");//팀뷰어 예외자
			}else{
			
				searchRootGroupId=user.getGroupId();
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ getJobDutyCode:"+user.getJobDutyCode()+"@@");
				if("22".equals(user.getJobDutyCode())){//반장이면
					list = getOrgGroupAndUser2(searchRootGroupId, "", "USER");//소속 인원만.
				}else{
					list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
				}
			}
		}

		
		try {
		ObjectMapper objectMapper = new ObjectMapper();

		mav.addObject("deptItems", objectMapper.writeValueAsString(list));

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrTeamMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}
	
	@RequestMapping(value = "/ehrHrMng.do")
	public ModelAndView ehrHrMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrHrMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		
		String searchRootGroupId="";
		List<Map<String, Object>> list = null;
		if(isSystemAdmin){
			searchRootGroupId="";//전체인원
			
			list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
		}else{
			
			
			List<MssTeamTreeSpecial> specialList = mssTeamTreeSpecialService.getList(user.getUserId());
			if(specialList!=null && specialList.size()>0){
				list = getOrgGroupAndUser3(specialList, "", "USER");//팀뷰어 예외자
			}else{
			
				searchRootGroupId=user.getGroupId();
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ getJobDutyCode:"+user.getJobDutyCode()+"@@");
				if("22".equals(user.getJobDutyCode())){//반장이면
					list = getOrgGroupAndUser2(searchRootGroupId, "", "USER");//소속 인원만.
				}else{
					list = getOrgGroupAndUser(searchRootGroupId, "", "USER");//내 부서 인원만.
				}
			}
		}

		
		try {
		ObjectMapper objectMapper = new ObjectMapper();

		mav.addObject("deptItems", objectMapper.writeValueAsString(list));

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrHrMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}


	
	@RequestMapping(value = "/ehrDiligenceMng.do")
	public ModelAndView ehrDiligenceMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrDiligenceMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrDiligenceMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}
	
	@RequestMapping(value = "/ehrPayMng.do")
	public ModelAndView ehrPayMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrPayMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrPayMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}
	
	@RequestMapping(value = "/ehrPersonalDivsionMng.do")
	public ModelAndView ehrPersonalDivsionMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrPersonalDivsionMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrPersonalDivsionMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}

	
	@RequestMapping(value = "/ehrOrganogramMng.do")
	public ModelAndView ehrOrganogramMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrOrganogramMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrOrganogramMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}

	
	@RequestMapping(value = "/ehrPayDiligenceMng.do")
	public ModelAndView ehrPayDiligenceMng(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView( "/portal/ehr/ehrPayDiligenceMng");

		User user = (User) getRequestAttribute("ikep.user");
		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Map<String, String> map = new HashMap<String, String>();
		for(int i=1;i<16;i++){
			map.put("userId", user.getUserId());
			if(i<10){
				map.put("roleName", "A0"+Integer.toString(i));
			}else{
				map.put("roleName", "A"+Integer.toString(i));
			}
			int tmpRole = userDao.getUserRoleCheck(map);
			if(tmpRole > 0){
				mav.addObject(map.get("roleName"), true);
			}else{
				mav.addObject(map.get("roleName"), false);
			}
			map.clear();
		}
		
		map.put("userId", user.getUserId());
		map.put("roleName", "H0");
		int tmpRole = userDao.getUserRoleCheck(map);
		if(tmpRole > 0){
			mav.addObject(map.get("roleName"), true);
		}else{
			mav.addObject(map.get("roleName"), false);
		}
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("Bigmenu", "ehrPayDiligenceMng");
		//mav.addObject("Bigmenu", request.getParameter("menuId"));
		return mav;
	}

	private List<Map<String, Object>> getOrgGroupAndUser(String groupId, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";
		if (StringUtil.isEmpty(userId)) {
			searchUserId = sessionuser.getUserId();
		} else {
			searchUserId = userId;
		}

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(searchUserId);

		// 부서
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);
		
		if(!"".equals(groupId)){
			Group sessionUserGroup = groupService.read(groupId);
			
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("type", "group");
			smap.put("name", sessionUserGroup.getGroupName());
			smap.put("code", sessionUserGroup.getGroupId());
			smap.put("groupTypeId", sessionUserGroup.getGroupTypeId());
			smap.put("parent", sessionUserGroup.getParentGroupId());
			smap.put("hasChild", sessionUserGroup.getChildGroupCount());
			list.add(smap);
		}
		
		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (sessionuser.getLocaleCode().equals("ko")) {
				map.put("name", group.getGroupName());
			} else {
				map.put("name", group.getGroupEnglishName());
			}
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}

		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, searchUserId);
			for (User user : userList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				String strJobTitle = user.getJobTitleName();
				if(!StringUtil.isEmpty(user.getJobDutyName()))
				{
					strJobTitle = user.getJobDutyName();
				}
				
				map.put("name", user.getUserName() + " " + strJobTitle);
				map.put("jobTitle", strJobTitle);
				map.put("teamName", user.getTeamName());
				map.put("code", "");
				map.put("parent", user.getGroupId());
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("leader", user.getLeader());
				list.add(map);
			}
		}

		return list;
	}
	
	private List<Map<String, Object>> getOrgGroupAndUser2(String groupId, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";
		if (StringUtil.isEmpty(userId)) {
			searchUserId = sessionuser.getUserId();
		} else {
			searchUserId = userId;
		}

		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(searchUserId);

		// 부서
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);
		
		if(!"".equals(groupId)){
			Group sessionUserGroup = groupService.read(groupId);
			
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("type", "group");
			smap.put("name", sessionUserGroup.getGroupName());
			smap.put("code", sessionUserGroup.getGroupId());
			smap.put("groupTypeId", sessionUserGroup.getGroupTypeId());
			smap.put("parent", sessionUserGroup.getParentGroupId());
			smap.put("hasChild", sessionUserGroup.getChildGroupCount());
			list.add(smap);
		}
		
		for (Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			if (sessionuser.getLocaleCode().equals("ko")) {
				map.put("name", group.getGroupName());
			} else {
				map.put("name", group.getGroupEnglishName());
			}
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ sessionuser.getScheduleManager():"+sessionuser.getScheduleManager()+"@@");
		// 사용자추가
		if (!(selectType.equals("GROUP"))) {

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId, searchUserId);
			for (User user : userList) {
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@ user.getScheduleManager():"+user.getScheduleManager()+"@@");
				
				if((sessionuser.getScheduleManager()).equals(user.getScheduleManager())){//동일한 스케줄 인원이면 더한다. 즉 같은 반장 소속이면.
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", "user");

	
					String strJobTitle = user.getJobTitleName();
					if(!StringUtil.isEmpty(user.getJobDutyName()))
					{
						strJobTitle = user.getJobDutyName();
					}
					
					map.put("name", user.getUserName() + " " + strJobTitle);
					map.put("jobTitle", strJobTitle);
					map.put("teamName", user.getTeamName());

					map.put("code", "");
					map.put("parent", user.getGroupId());
					map.put("id", user.getUserId());
					map.put("empNo", user.getEmpNo());
					map.put("email", user.getMail());
					map.put("mobile", user.getMobile());
					map.put("leader", user.getLeader());
					list.add(map);
				}
			}
		}

		return list;
	}
	private List<Map<String, Object>> getOrgGroupAndUser3(List<MssTeamTreeSpecial> specialGroupList, String userId, String selectType) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String searchUserId = "";


		
		for (MssTeamTreeSpecial specialGroup : specialGroupList) {
			Group searchgroup = new Group();
			searchgroup.setGroupId(specialGroup.getGroupId());
			searchgroup.setRegisterId(specialGroup.getUserId());
	

			Group specialRootGroup = groupService.read(specialGroup.getGroupId());
			
			Map<String, Object> smap = new HashMap<String, Object>();
			
			
			// 부서
			if(specialGroup.getChildGroupCnt() < 1){
				
			
				List<Group> groupList = groupService.selectOrgGroup(searchgroup);
				
				if(groupList.size() == 0){
					groupList = groupService.selectOrgGroupSp(searchgroup);
				}
				
				for (Group group : groupList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", "group");
					if (sessionuser.getLocaleCode().equals("ko")) {
						map.put("name", group.getGroupName());
					} else {
						map.put("name", group.getGroupEnglishName());
					}
					map.put("code", group.getGroupId());
					map.put("groupTypeId", group.getGroupTypeId());
					map.put("parent", group.getParentGroupId());
					map.put("hasChild", group.getChildGroupCount());
					list.add(map);
				}
			}else{
				smap.put("type", "group");
				smap.put("name", specialRootGroup.getGroupName());
				smap.put("code", specialRootGroup.getGroupId());
				smap.put("groupTypeId", specialRootGroup.getGroupTypeId());
				smap.put("parent", specialRootGroup.getParentGroupId());
				smap.put("hasChild", specialRootGroup.getChildGroupCount());
				list.add(smap);
				
			}
		}

		// 사용자추가
		/*
		if (!(selectType.equals("GROUP")) && specialGroupList.size()==1) {
			
			if (StringUtil.isEmpty(userId)) {
				searchUserId = sessionuser.getUserId();
			} else {
				searchUserId = userId;
			}
			
			
			Group searchgroup = new Group();
			searchgroup.setGroupId(sessionuser.getGroupId());
			searchgroup.setRegisterId(userId);
				// 부서

			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), searchgroup.getGroupId(), searchUserId);
			for (User user : userList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				String strJobTitle = user.getJobTitleName();
				if(!StringUtil.isEmpty(user.getJobDutyName()))
				{
					strJobTitle = user.getJobDutyName();
				}
				
				map.put("name", user.getUserName() + " " + strJobTitle);
				map.put("jobTitle", strJobTitle);
				map.put("teamName", user.getTeamName());
				map.put("code", "");
				map.put("parent", user.getGroupId());
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("leader", user.getLeader());
				list.add(map);
			}
			
		
		}
		 */	
		return list;

	}
}
