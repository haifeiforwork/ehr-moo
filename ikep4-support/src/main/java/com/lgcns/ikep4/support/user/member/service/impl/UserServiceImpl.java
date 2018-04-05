/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.member.service.impl;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.model.UserSearchCondition;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.jco.Connection;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.UserDetail;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: UserServiceImpl.java 19178 2012-06-11 07:46:13Z malboru80 $
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private MessageSource messageSource;
	
private Socket sc;
	
	private InputStream is = null;
	private OutputStream os = null;
	private Properties prop = null;		
	private String serverIp = null;
	private String serverPort = null;
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(User user) {

		userDao.create(user);

		String representGroupId = user.getGroupId();
		String isRepresentGroup = user.getIsRepresentGroup();

		List<Group> groupList = user.getGroupList();

		for (int i = 0; i < groupList.size(); i++) {
			user.setGroupId(groupList.get(i).getGroupId());
			userDao.addUserToGroup(user);
		}

		user.setGroupId(representGroupId);
		user.setIsRepresentGroup(isRepresentGroup);

		userDao.updateRepresentGroup(user);

		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#createForExcel
	 * (java.lang.Object)
	 */
	public String createForExcel(User user) {

		userDao.create(user);
		userDao.addUserToGroup(user);
		
		user.setIsRepresentGroup("1");

		userDao.updateRepresentGroup(user);

		return null;
	}
	
	public int requestCertification(User user){
		
		Random random = new Random();
		String ran = Integer.toString(random.nextInt(9000)+1000); 
		user.setTempCode(ran);
		int userCheck = userDao.getUserInfo(user);
		userDao.requestCertification(user);
		
		if(userCheck > 0){
			Sms sms = new Sms();
			sms.setRegisterId(user.getUserId());
			sms.setRegisterName(user.getUserName());
			
			sms.setReceiverPhonenos(user.getMobile().split("[-]"));
			sms.setReceiverIds(user.getUserId().split("[-]"));
			
			if(!StringUtil.isEmpty(user.getMobile())){
				sms.setSenderPhoneno(user.getMobile());
			}
			else{
				sms.setSenderPhoneno("****");
			}
			sms.setContents("[네오넷] 본인확인을 위해 인증번호 ["+ran+"]를 입력해주세요.");
			smsService.create(sms); 
		}
		return userCheck;
	}
	
	public int requestCertificationCheck(User user){
		
		int userCheck = userDao.getUserInfoCheck(user);
		return userCheck;
	}
	                                              

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String id) {

		return userDao.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#list(com.lgcns
	 * .ikep4.support.user.member.model.UserSearchCondition)
	 */
	public SearchResult<User> list(UserSearchCondition searchCondition) {
		
		if (StringUtil.isEmpty(searchCondition.getGroupTypeId())) {
			searchCondition.setGroupTypeId("G0001");
		}

		if (!StringUtil.isEmpty(searchCondition.getGroupId())){
			searchCondition.setChildGroupIds(userDao.selectAllChildGroupId(searchCondition.getGroupId()));
		}
		Integer count = userDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<User> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<User>(searchCondition);

		} else {

			List<User> list = userDao.selectAll(searchCondition);

			List<User> returnList = new ArrayList<User>();
			for (User user : list) {
				returnList.add(user);
			}

			searchResult = new SearchResult<User>(returnList, searchCondition);
		}

		return searchResult;

	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#list(com.lgcns
	 * .ikep4.support.user.member.model.UserSearchCondition)
	 */
	public SearchResult<User> treelist(UserSearchCondition searchCondition, User sessionuser) {
		
		if (StringUtil.isEmpty(searchCondition.getGroupTypeId())) {
			searchCondition.setGroupTypeId("G0001");
		}
		List<User> specialLists = userDao.getList(sessionuser.getUserId());
		String specialGroup1 = "";
		String specialGroup2 = "";
		if(specialLists!=null && specialLists.size()>0){
			for (User user : specialLists) {
				specialGroup1  = userDao.selectAllChildGroupId(user.getGroupId());
				if(specialGroup2 == ""){
					specialGroup2 = specialGroup1;
				}else{
					specialGroup2 = specialGroup2 +","+ specialGroup1;
				}
			}
		}
		if (!StringUtil.isEmpty(searchCondition.getGroupId())){
			specialGroup1 = userDao.selectAllChildGroupId(searchCondition.getGroupId());
			if(specialGroup2 == ""){
				specialGroup2 = specialGroup1;
			}else{
				specialGroup2 = specialGroup2 +","+ specialGroup1;
			}
			searchCondition.setChildGroupIds(specialGroup2);
		}
		Integer count = userDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<User> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<User>(searchCondition);

		} else {

			List<User> list = userDao.selectAll(searchCondition);

			List<User> returnList = new ArrayList<User>();
			for (User user : list) {
				if (!"ko".equals(searchCondition.getUserLocaleCode())) {
					user.setUserName(user.getUserEnglishName());
					user.setTeamName(user.getTeamEnglishName());
					user.setJobTitleName(user.getJobTitleEnglishName());
				}
				returnList.add(user);
			}

			searchResult = new SearchResult<User>(returnList, searchCondition);
		}

		return searchResult;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#get(java
	 * .io.Serializable)
	 */
	public User read(String id) {

		return userDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectGroupForUser
	 * (java.lang.String)
	 */
	public List<Map<String, String>> selectGroupForUser(String userId) {

		return userDao.selectGroupForUser(userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(User user) {

		String representGroupId = user.getGroupId();
		String isRepresentGroup = user.getIsRepresentGroup();
		List<Map<String, String>> preGroupList = selectGroupForUser(user.getUserId());

		List<Group> groupList = user.getGroupList();
		
		boolean isLeader = false;

		// 기존에 설정된 그룹정보 중에서 삭제 대상 소속 그룹 찾아 지우기
		for (int i = 0; i < preGroupList.size(); i++) {
			User oldUser = new User();
			String groupId = preGroupList.get(i).get("groupId");
			String userId = preGroupList.get(i).get("userId");
			
			boolean isDelete = true;
			for(Group group : groupList) {
				if(groupId.equals(group.getGroupId())) {
					groupList.remove(group);
					isDelete = false;
					break;
				}
			}
			
			if(isDelete == true) {
				if(user.getUserId().equals(preGroupList.get(i).get("leaderId"))) {
					userDao.updateLeaderInfo(groupId);	// 해당 조직의 리더 정보 삭제
				}
				oldUser.setUserId(userId);
				oldUser.setGroupId(groupId);
				userDao.removeUserFromCertainGroup(oldUser);
			} else {	// 삭제되진 않는 소속 그룹중에서 내가 리더로 설정된 그룹이 있는지 확인
				if(user.getUserId().equals(preGroupList.get(i).get("leaderId"))) {
					isLeader = true;
				}
			}
		}
		
		for (int i = 0; i < groupList.size(); i++) {
			user.setGroupId(groupList.get(i).getGroupId());
			userDao.addUserToGroup(user);
		}
		
		user.setLeader(isLeader ? "1" : "0");
		
		// 사용자 정보 업데이트, 무림 사용자와 타 사용자 정보를 분리하여 업데이트 한다
		if(user.getEmpNo() != null && user.getEmpNo().length() > 0)
		{
			userDao.updateMoorimUser(user);
		}
		else
		{
			userDao.update(user);
		}
		
		//groupDao.updateLeaderInfo(user);
		
		// 대표 그룹 정보 업데이트
		user.setGroupId(representGroupId);
		user.setIsRepresentGroup(isRepresentGroup);

		userDao.updateRepresentGroup(user);

		/*
		 * 리더 정보 업데이트 - 특정 그룹의 리더로 지정은 불가(리더였던 그룹의 리더ID를 삭제) 이전 사용자 정보에서 리더 플래그를
		 * 가져와서 비교한 후 다른 경우(0->1이 되는 경우는 없음) 리더인 그룹의 ID를 이용하여 해당 그룹의 리더 정보를 제거함
		 */
//		if (!preIsLeader.equals(postIsLeader)) {
//			userDao.updateLeaderInfo(user.getLeadingGroupId());
//		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#updateForExcel
	 * (java.lang.Object)
	 */
	public void updateForExcel(User user) {
		
		String preIsLeader = userDao.get(user.getUserId()).getLeader();
		String postIsLeader = user.getLeader();
		List<Map<String, String>> preGroupList = selectGroupForUser(user.getUserId());

		// 사용자 정보 업데이트
		userDao.update(user);

		// 그룹 정보 업데이트(bulk) - 기존 User_Group 정보 삭제 후 생성
		for (int i = 0; i < preGroupList.size(); i++) {
			User oldUser = new User();
			oldUser.setUserId(preGroupList.get(i).get("userId"));
			oldUser.setGroupId(preGroupList.get(i).get("groupId"));
			userDao.removeUserFromCertainGroup(oldUser);
		}
		
		userDao.addUserToGroup(user);

		// 대표 그룹 정보 업데이트
		user.setIsRepresentGroup("1");

		userDao.updateRepresentGroup(user);

		/*
		 * 리더 정보 업데이트 - 특정 그룹의 리더로 지정은 불가(리더였던 그룹의 리더ID를 삭제) 이전 사용자 정보에서 리더 플래그를
		 * 가져와서 비교한 후 다른 경우(0->1이 되는 경우는 없음) 리더인 그룹의 ID를 이용하여 해당 그룹의 리더 정보를 제거함
		 */
		if (!preIsLeader.equals(postIsLeader)) {
			userDao.updateLeaderInfo(user.getLeadingGroupId());
		}
	}
	
	public void authCheck(User user){
		Connection connect = null;
		HttpServletRequest request = null;
    	List<UserDetail> userDetailList = new ArrayList<UserDetail>();
		try {
			
			connect = new Connection(request);	

			JCoFunction function = connect.getSAPFunction("Z_HR_EP_PERSON_DATA_SEND2");
			function.getImportParameterList().setValue("I_PERNR", user.getEmpNo());
			function.getImportParameterList().setValue("I_DATE", "");
			connect.executeSAP(function);
			String tmpRole = "";
			if (function.getTableParameterList() != null) {
				
	        	JCoTable tb = function.getTableParameterList().getTable("ITAB");        	
	        	        	
	        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
	        	{
	        		tmpRole = tb.getString("AD_ROLE");
	        	}
	        	//log.debug(userDetailData);
			}
			//log.debug("");			
			Map<String, String> map = new HashMap<String, String>();
			String[] tmpRoles = tmpRole.split(",");
			
			for(int i=1;i<16;i++){
				System.out.println(Integer.toString(i));
				if(i<10){
					map.put("roleName", "A0"+Integer.toString(i));
				}else{
					map.put("roleName", "A"+Integer.toString(i));
				}
				map.put("userId", user.getUserId());
				userDao.deleteRoleUser(map);
				map.clear();
			}
			
			for(int j=0;j<tmpRoles.length;j++){
				map.put("roleName", tmpRoles[j]);
				map.put("userId", user.getUserId());
				map.put("userName", user.getUserId());
				userDao.insertRoleUser(map);
				map.clear();
			}
				

			} catch (JCoException e) {

				log.debug("e.getMessage():"+e.getMessage());		
				log.debug("e.getGroup():"+e.getGroup());
				log.debug("e.getKey():"+e.getKey());
				e.printStackTrace();
			}finally{

			}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#remove
	 * (java.io.Serializable)
	 */
	public void delete(User user) {

		String preIsLeader = userDao.get(user.getUserId()).getLeader();
		String userId = user.getUserId();

		// user_group에서 user 매핑정보 삭제
		userDao.deleteUserFromGroup(userId);

		// user가 리더인 경우 해당 그룹의 리더 정보 삭제
		if (preIsLeader.equals("1")) {
			userDao.updateLeaderInfo(user.getLeadingGroupId());
		}

		// user_role에서 user 매핑정보 삭제
		userDao.deleteUserFromRole(userId);

		// user_sys_permission에서 user 매핑정보 삭제
		userDao.deleteUserFromSysPermission(userId);

		// user_con_permission에서 user 매핑정보 삭제
		userDao.deleteUserFromConPermission(userId);

		// user_absence에서 user 매핑정보 삭제
		userDao.deleteUserFromAbsence(userId);

		userDao.remove(userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectNationAll
	 * (java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	public List selectNationAll(String localeCode) {

		return userDao.selectNationAll(localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectTimezoneAll
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	public List selectTimezoneAll(String localeCode) {

		return userDao.selectTimezoneAll(localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectLocaleCodeAll
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	public List selectLocaleCodeAll(String localeCode) {

		return userDao.selectLocaleCodeAll(localeCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectJobClassAll
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobClassAll(String portalId) {

		return userDao.selectJobClassAll(portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectJobRankAll
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobRankAll(String portalId) {

		return userDao.selectJobRankAll(portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectWorkPlaceCodeAll
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	public List selectWorkPlaceCodeAll(String portalId) {

		return userDao.selectWorkPlaceCodeAll(portalId);
	}
	
		/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectJobPositionAll
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobPositionAll(String portalId) {

		return userDao.selectJobPositionAll(portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectJobTitleAll
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobTitleAll(String portalId) {

		return userDao.selectJobTitleAll(portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectJobDutyAll
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	public List selectJobDutyAll(String portalId) {

		return userDao.selectJobDutyAll(portalId);
	}


   /*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectAllForTree
	 * (java .lang.String,java.lang.String\)
	 */
	public List<User> selectAllForTree(String localeCode, String groupId, String userId) {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchFlag", "false");
		param.put("groupId", groupId);
		param.put("userId", userId);
		param.put("fieldName", "jobTitleName");
		param.put("userLocaleCode", localeCode);
		param.put("portalId", portalId);

		return userDao.selectAllForTree(param);
	}
	
	public List<User> selectAgentUserList(String roleName) {
		
		return userDao.selectAgentUserList(roleName);
	}
	
	public SearchResult<User> selectUserPwUpdateList(UserSearchCondition searchCondition){
		Integer count = userDao.countUserPwUpdateList(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<User> searchResult = null;
		
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<User>(searchCondition);
		}else{
			List<User> userList = userDao.selectUserPwUpdateList(searchCondition);
			searchResult = new SearchResult<User>(userList, searchCondition);
		}
		
		return searchResult;
	}
	
	public List getWorkPlaceNameList() {
		return sort(userDao.getWorkPlaceNameList());
	}
	
	private List<String> sort(List workPlaceNameList){
		String workPlaceHead = messageSource.getMessage("message.collpack.kms.admin.permission.user.workPlaceName.head", null, Locale.getDefault());
		int head_quarter = workPlaceNameList.indexOf(workPlaceHead);
		
		if(head_quarter > -1){
			int pos = workPlaceNameList.size();
			
			workPlaceNameList.add(pos-1, workPlaceNameList.get(0));
			if(head_quarter > 1){
				workPlaceNameList.add(pos, workPlaceNameList.get(1));
				workPlaceNameList.set(1, workPlaceHead);
				workPlaceNameList.remove(head_quarter);
			}
			workPlaceNameList.set(0, "ALL");
		}
		return workPlaceNameList;
	}

    /*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectAllForTree
	 * (java .lang.String,java.lang.String\)
	 */
	public List<User> selectJobTitleUserForTree(String localeCode, String jobTitleCode, String userId) {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchFlag", "false");
		param.put("jobTitleCode", jobTitleCode);
		param.put("userId", userId);
		param.put("fieldName", "jobTitleName");
		param.put("userLocaleCode", localeCode);
		param.put("portalId", portalId);

		return userDao.selectJobTitleUserForTree(param);
	}
	

	
	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectCompanyCodeAll
	 * ()
	 */
	@SuppressWarnings("rawtypes")
	public List selectCompanyCodeAll(String portalId) {

		return userDao.selectCompanyCodeAll(portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectAllForTree
	 * (java .lang.String,java.lang.String\)
	 */
	public List<User> selectJobDutyUserForTree(String localeCode, String jobDutyCode, String userId) {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchFlag", "false");
		param.put("jobDutyCode", jobDutyCode);
		param.put("userId", userId);
		param.put("fieldName", "jobDutyName");
		param.put("userLocaleCode", localeCode);
		param.put("portalId", portalId);

		return userDao.selectJobDutyUserForTree(param);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectSearchForTree
	 * (java .lang.String,java.lang.String\)
	 */
	public List<User> selectSearchForTree(String localeCode, String keyword, String registerId) {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchFlag", "true");
		param.put("fieldName", "jobTitleName");
		param.put("userLocaleCode", localeCode);
		param.put("userName", keyword);
		param.put("portalId", portalId);
		param.put("register", registerId);
		
		// if (localeCode.equals("ko")) {
		// param.put("userName", keyword);
		// } else {
		// param.put("userEnglishName", keyword);
		// }

		return userDao.selectSearchForTree(param);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#updateProfile
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateMyPsInfo(User profile) {
		userDao.updateMyPsInfo(profile);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#updateProfile
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfile(User profile) {
		userDao.updateProfile(profile);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#updateProfileStaus
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfileStaus(User profile) {
		userDao.updateProfileStaus(profile);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#updatePictureId
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updatePictureId(User profile) {
		userDao.updatePictureId(profile);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.member.service.UserService#
	 * updateProfilePictureId(com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateProfilePictureId(User profile) {
		userDao.updateProfilePictureId(profile);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#updateTwitterInfo
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateTwitterInfo(User profile) {
		userDao.updateTwitterInfo(profile);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#updateFacebookInfo
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateFacebookInfo(User profile) {
		userDao.updateFacebookInfo(profile);

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#updateExportField
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateExportField(User profile) {
		userDao.updateExportField(profile);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#updateCurrentJob
	 * (com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateCurrentJob(User profile) {
		userDao.updateCurrentJob(profile);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectJobCode
	 * (java.util.Map)
	 */
	public String selectJobCode(Map<String, String> param) {

		return userDao.selectJobCode(param);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectForPassword
	 * (com.lgcns.ikep4.support.user.member.model.UserSearchCondition)
	 */
	public List<User> selectForPassword(UserSearchCondition searchCondition) {
		return userDao.selectForPassword(searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#updateForPassword
	 * (java.util.List)
	 */
	public void updateForPassword(List<User> userList) {

		for (User user : userList) {
			userDao.updateForPassword(user);
		}

	}
	
	public void updateMsgForPassword(List<User> userList) {

		for (User user : userList) {
			updateMsgPassword(user);
		}

	}
	
	private String getUrlEncoding(String str) {
		String encStr = "";
		
		try {
			encStr = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encStr;
	}
	
	private void updateMsgPassword(User user) {
		BufferedWriter bw = null;
		String userId = user.getUserId();
		String password = user.getUserPassword();
		if (connect()) {
			try {
				bw = new BufferedWriter(new OutputStreamWriter(os, "EUC-KR"));
				
				StringBuffer sb = new StringBuffer();
				sb.append("ORG\tModUserPassword\t");
				sb.append(userId).append("\t");
				sb.append(getUrlEncoding(password)).append("\f");
				
				bw.write(sb.toString());
				bw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {try {bw.close(); bw = null;} catch(Exception ee) {}}
				if (sc != null) {try {sc.close(); sc = null;} catch(Exception ee) {}}
			}
		}
	}
	
	private boolean connect() {
		boolean isConnected = false;
		prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
		serverIp = prop.getProperty("messenger.server.ip");
		serverPort = prop.getProperty("messenger.server.port");
		
		SocketAddress addr = new InetSocketAddress(serverIp, Integer.parseInt(serverPort));
		
		StringBuffer msg = new StringBuffer();
		
		try {
			sc = new Socket();
			sc.connect(addr, 3000);
			
			is = sc.getInputStream();
			os = sc.getOutputStream();
			
			//msg.append("Server [").append(ip).append("][").append(port).append("] Connected...");
			//System.out.println(msg.toString());
			
			isConnected = true;
		} catch(Exception e) {
			msg.append("[ERROR] connect: [").append(e.getMessage()).append("][").append(serverIp).append("][").append(serverPort).append("]");
			System.out.println(msg.toString());
		}
		
		return isConnected;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#selectLeadingGroup
	 * (java.lang.String)
	 */
	public Group selectLeadingGroup(String userId) {

		return userDao.selectLeadingGroup(userId);
	}
	
	public List<Group> selectLeadingGroupAll(String userId) {

		return userDao.selectLeadingGroupAll(userId);
	}
	
	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * Sap에서 수신된 사용자 정보를 DB의 temp 테이블에 저장
	 */
	public void updateSapUser(List<UserDetail> userDetailList){
		
		String tmp = "tmp";
		userDao.deleteTmpUser(tmp);
		
		for (UserDetail userDetail : userDetailList) {
			
			try{
			userDao.updateSapUser(userDetail);
			}
			catch(Exception e)
			{
				continue;
			}
	    }
		
		//userDao.updateEntryDate();
	}
	
	public void updateSapNewUser(List<UserDetail> userDetailList){
		
		String tmp = "tmp";
		userDao.deleteTmpNewUser(tmp);
		
		for (UserDetail userDetail : userDetailList) {
			
			try{
			userDao.updateSapNewUser(userDetail);
			}
			catch(Exception e)
			{
				continue;
			}
	    }
		
		//userDao.updateEntryDate();
	}
	
	public String updateEpUserTabeFromTmpUserTable()
	{
		return userDao.updateEpUserTableFromTmpUserTable();
	
	}
	
	
	public String updateUserMenuAcl()
	{
		return userDao.updateUserMenuAcl();
	}
	
	public void updatePublicAddressbook()
	{
		userDao.updatePublicAddressbook();
	}
	
	public String readJobCondition(String jobName){
		return userDao.readJobCondition(jobName);
	}
	
	/**
	 * 팀 유저 리스트
	 * @param groupId
	 * @return
	 */
	public List<User> listTeamUser(String groupId) {
		return userDao.listTeamUser(groupId);
	}
	
	public List<User> listTeamLeader(String groupId) {
		return userDao.listTeamLeader(groupId);
	}
	
	public void loginLogInput(String userId){
		userDao.loginLogInput(userId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#list(com.lgcns
	 * .ikep4.support.user.member.model.UserSearchCondition)
	 */
	public SearchResult<User> groupListMobile(UserSearchCondition searchCondition) {
		
		if (StringUtil.isEmpty(searchCondition.getGroupTypeId())) {
			searchCondition.setGroupTypeId("G0001");
		}

		Integer count = userDao.countBySelectGroupList(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<User> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<User>(searchCondition);

		} else {

			List<User> list = userDao.selectGroupList(searchCondition);

			List<User> returnList = new ArrayList<User>();
			for (User user : list) {
				returnList.add(user);
			}

			searchResult = new SearchResult<User>(returnList, searchCondition);
		}

		return searchResult;

	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#groupListForTreeMobile(com.lgcns
	 * .ikep4.support.user.member.model.UserSearchCondition)
	 */
	public SearchResult<User> groupListForTreeMobile(UserSearchCondition searchCondition) {

		if (StringUtil.isEmpty(searchCondition.getGroupTypeId())) {
			searchCondition.setGroupTypeId("G0001");
		}

		Integer count = userDao.countBySelectGroupListForTreeMobile(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<User> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<User>(searchCondition);

		} else {
			List<User> list = userDao.selectGroupListForTreeMobile(searchCondition);

			List<User> returnList = new ArrayList<User>();
			for (User user : list) {
				returnList.add(user);
			}

			searchResult = new SearchResult<User>(returnList, searchCondition);
		}

		return searchResult;

	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#listAll(com.lgcns
	 * .ikep4.support.user.member.model.UserSearchCondition)
	 */
	public SearchResult<User> listAll(UserSearchCondition searchCondition) {
		
		if (StringUtil.isEmpty(searchCondition.getGroupTypeId())) {
			searchCondition.setGroupTypeId("G0001");
		}
		Integer count = userDao.countBySearchConditionAll(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<User> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<User>(searchCondition);

		} else {

			List<User> list = userDao.selectAllUser(searchCondition);

			List<User> returnList = new ArrayList<User>();
			for (User user : list) {
				returnList.add(user);
			}

			searchResult = new SearchResult<User>(returnList, searchCondition);
		}

		return searchResult;

	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.member.service.UserService#list(com.lgcns
	 * .ikep4.support.user.member.model.UserSearchCondition)
	 */
	public SearchResult<User> listForMobile(UserSearchCondition searchCondition) {
		
		if (StringUtil.isEmpty(searchCondition.getGroupTypeId())) {
			searchCondition.setGroupTypeId("G0001");
		}

		if (!StringUtil.isEmpty(searchCondition.getGroupId())){
			searchCondition.setChildGroupIds(userDao.selectAllChildGroupId(searchCondition.getGroupId()));
		}
		Integer count = userDao.countBySearchConditionForMobile(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<User> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<User>(searchCondition);

		} else {
			
			String portalId = (String) getRequestAttribute("ikep.portalId");

			List<User> list = userDao.selectAllForMobile(searchCondition);

			List<User> returnList = new ArrayList<User>();
			for (User user : list) {
				if(user.getJobDutyCode() != null)
				{
					user.setJobTitleName(user.getJobDutyName());
				}
				returnList.add(user);
			}

			searchResult = new SearchResult<User>(returnList, searchCondition);
		}

		return searchResult;

	}

	public void executeMappingDB() {
		userDao.executeMappingDB();
	}
}
