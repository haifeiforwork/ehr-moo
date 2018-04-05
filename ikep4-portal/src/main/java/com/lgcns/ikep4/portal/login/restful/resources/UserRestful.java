package com.lgcns.ikep4.portal.login.restful.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.login.model.UserAccount;
import com.lgcns.ikep4.portal.login.restful.model.DeptInfoBody;
import com.lgcns.ikep4.portal.login.restful.model.DeptInfoHead;
import com.lgcns.ikep4.portal.login.restful.model.DeptInfoReturnData;
import com.lgcns.ikep4.portal.login.restful.model.DeptListBody;
import com.lgcns.ikep4.portal.login.restful.model.DeptListHead;
import com.lgcns.ikep4.portal.login.restful.model.DeptListReturnData;
import com.lgcns.ikep4.portal.login.restful.model.MailSettingInfoReturnData;
import com.lgcns.ikep4.portal.login.restful.model.UserAuthBody;
import com.lgcns.ikep4.portal.login.restful.model.UserAuthHead;
import com.lgcns.ikep4.portal.login.restful.model.UserAuthReturnData;
import com.lgcns.ikep4.portal.login.restful.model.UserInfoBody;
import com.lgcns.ikep4.portal.login.restful.model.UserInfoHead;
import com.lgcns.ikep4.portal.login.restful.model.UserInfoMobileBody;
import com.lgcns.ikep4.portal.login.restful.model.UserInfoMobileHead;
import com.lgcns.ikep4.portal.login.restful.model.UserInfoMobileReturnData;
import com.lgcns.ikep4.portal.login.restful.model.UserInfoParam;
import com.lgcns.ikep4.portal.login.restful.model.UserInfoReturnData;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.support.restful.model.Head;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


@Path("/user")
@Component
public class UserRestful extends BaseController {
	
	@Autowired
	private LoginUserDetailsService loginUserDetailsService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GroupDao groupDao;
	
	/**
	 * 사용자 인증
	 */
	@POST 
	@Path("/retrieveIsSuccessAuth")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserAuthHead retrieveIsSuccessAuth(UserInfoParam userInfoParam) {

		UserAuthHead result = new UserAuthHead();
		try {
			UserAuthReturnData returnData = new UserAuthReturnData();

			// session에서 portalId를 읽어온다.
			/*String portalId = (String) getRequestAttribute("ikep.portalId");

			if (portalId == null) {
				Head head = new Head(1, "portalId is Null");
				result = new UserAuthHead(head, null);
				return result;
			}*/

			// 입력된 password를 encrypt한다.
			String encryptPassword = EncryptUtil.encryptText(userInfoParam.getUserPassword());
			
			//상태가 재직중인 사람내에서만 검색 함  (USER_STATUS='C')
			UserAccount userAccount = loginUserDetailsService.loadUserByUsernameMobile(userInfoParam.getUserId());

			// User가 존재하지 않을 경우
			if (userAccount == null) {
				Head head = new Head(1, "UserAccount Empty");
				returnData.setIsSuccessAuth("1");
				UserAuthBody body = new UserAuthBody(returnData);
				result = new UserAuthHead(head, body);
				return result;
			}

			// authentication check
			if (!encryptPassword.equals(userAccount.getPassword())) {
				Head head = new Head(1, "Password not equals");
				returnData.setIsSuccessAuth("1");
				UserAuthBody body = new UserAuthBody(returnData);
				result = new UserAuthHead(head, body);
				return result;
			}
			
			//인증 성공
			returnData.setIsSuccessAuth("0");
			Head head = new Head(0, Response.Status.OK.toString());
			UserAuthBody body = new UserAuthBody(returnData);
			result = new UserAuthHead(head, body);
		
		} catch (Exception e) {//e.printStackTrace();
			Head head = new Head(2, "Error");
			result = new UserAuthHead(head, null);
		}
				
		return result;
	}
	
	/**
	 * 사용자정보 조회
	 */
	@POST 
	@Path("/retrieveUserInfo")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserInfoHead retrieveUserInfo(UserInfoParam userInfoParam) {

		UserInfoHead result = new UserInfoHead();
		
		try {
			
			Head head = new Head();
			UserInfoReturnData userReturnData = new UserInfoReturnData();
			MailSettingInfoReturnData mailReturnData = new MailSettingInfoReturnData();
			
			User user = userDao.get(userInfoParam.getUserId());
			
			if(user != null){
				userReturnData.setPortalId(StringUtil.nvl(user.getPortalId(),""));
				userReturnData.setUserId(StringUtil.nvl(user.getUserId(), ""));
				userReturnData.setUserName(StringUtil.nvl(user.getUserName(), ""));
				userReturnData.setUserEnglishName(StringUtil.nvl(user.getUserEnglishName(), ""));
				userReturnData.setUserStatus(StringUtil.nvl(user.getUserStatus(), ""));
				userReturnData.setUserDeptCode(StringUtil.nvl(user.getGroupId(), ""));
				userReturnData.setUserDeptName(StringUtil.nvl(user.getTeamName(), ""));
				userReturnData.setUserDeptEnglishName(StringUtil.nvl(user.getTeamEnglishName(), ""));
				userReturnData.setUserTitle(StringUtil.nvl(user.getJobTitleName(), ""));
				userReturnData.setMobile(StringUtil.nvl(user.getMobile(), ""));
				userReturnData.setMail(StringUtil.nvl(user.getMail(), ""));
				userReturnData.setOfficePhoneNo(StringUtil.nvl(user.getOfficePhoneNo(), ""));
				userReturnData.setBirthday(StringUtil.nvl(user.getBirthday(), ""));
				userReturnData.setWeddingAnniv(StringUtil.nvl(user.getWeddingAnniv(), ""));
				userReturnData.setLeader(StringUtil.nvl(user.getLeader(), ""));
				userReturnData.setTimezoneId(StringUtil.nvl(user.getTimezoneId(), ""));
				userReturnData.setLocaleCode(StringUtil.nvl(user.getLocaleCode(), ""));
				userReturnData.setNationCode(StringUtil.nvl(user.getNationCode(), ""));
				userReturnData.setCurrentJob(StringUtil.nvl(user.getCurrentJob(), ""));
				userReturnData.setProfileStatus(StringUtil.nvl(user.getProfileStatus(), ""));
				userReturnData.setProfilePictureId(StringUtil.nvl(user.getProfilePictureId(), ""));
				userReturnData.setProfilePicturePath(StringUtil.nvl(user.getProfilePicturePath(), ""));
				userReturnData.setPictureId(StringUtil.nvl(user.getPictureId(), ""));
				userReturnData.setPicturePath(StringUtil.nvl(user.getPicturePath(), ""));
			
//				try {
//					MailSetting setting = mailSettingDao.get(userInfoParam.getUserId());
//					
//					if(setting != null){
//						mailReturnData.setMailAccountName(StringUtil.nvl(user.getUserName(), ""));
//						mailReturnData.setMailAddress(setting.getEmailAddress());
//						mailReturnData.setMailID(setting.getAccountId());
//						String pw = setting.getPassword();
//						mailReturnData.setMailPassword(pw.substring(pw.indexOf("^")+1, pw.length()));
//						if ( setting.getIsImap() == 1 ) {
//							mailReturnData.setReceiveServerType("I");
//						} else {
//							mailReturnData.setReceiveServerType("P");
//						}
//						mailReturnData.setReceiveServerAddress(setting.getIncomingServer());
//						mailReturnData.setReceiveServerPort(setting.getIncomingPort()+"");
//						mailReturnData.setReceiveServerSecurity(setting.getIncomingSsl()+"");
//						mailReturnData.setSendServerAddress(StringUtil.nvl(setting.getOutgoingServer(), ""));
//						mailReturnData.setSendServerPort(StringUtil.nvl(setting.getOutgoingPort()+"", ""));
//						mailReturnData.setSendServerSecurity(StringUtil.nvl(setting.getOutgoingSsl()+"" ,""));
//						mailReturnData.setSendServerAuth(StringUtil.nvl(setting.getOutgoingAuth()+"" ,""));
//						mailReturnData.setSendOutgoingId(StringUtil.nvl(setting.getOutgoingId(), ""));
//						if ( !StringUtil.isEmpty(setting.getOutgoingPw()) ) {
//							pw = setting.getOutgoingPw();
//							mailReturnData.setSendOutgoingPw(pw.substring(pw.indexOf("^")+1, pw.length()));
//						} else {
//							mailReturnData.setSendOutgoingPw("");	
//						}
//					} else {
//						PortalMail mailInfo = portalMailService.get(user.getPortalId());
//						
//						mailReturnData.setMailAccountName(StringUtil.nvl(user.getUserName(), ""));
//						mailReturnData.setMailAddress(StringUtil.nvl(user.getMail(), ""));
//						mailReturnData.setMailID("");
//						mailReturnData.setMailPassword("");
//						mailReturnData.setReceiveServerType("");
//						mailReturnData.setReceiveServerAddress("");
//						mailReturnData.setReceiveServerPort("");
//						mailReturnData.setReceiveServerSecurity("");
//						mailReturnData.setSendServerAddress(StringUtil.nvl(mailInfo.getSmtpServer(), ""));
//						mailReturnData.setSendServerPort(StringUtil.nvl(mailInfo.getSmtpPort()+"", ""));
//						mailReturnData.setSendServerSecurity(StringUtil.nvl(mailInfo.getSmtpSsl()+"" ,""));
//						mailReturnData.setSendServerAuth("1");
//						mailReturnData.setSendOutgoingId(StringUtil.nvl(user.getMail().substring(0, user.getMail().indexOf("@")), ""));
//						mailReturnData.setSendOutgoingPw(StringUtil.nvl(user.getMailPassword(), ""));
//					}
//				} catch (Exception ex) {
//					mailReturnData = null;
//				}
				
				head = new Head(0, Response.Status.OK.toString());
				UserInfoBody body = new UserInfoBody(userReturnData, mailReturnData);
				result = new UserInfoHead(head, body);
			}else{
				head = new Head(1, "UserInfo Empty");
				result = new UserInfoHead(head, null);
			}
		
		} catch (Exception e) {
			Head head = new Head(2, "Error");
			result = new UserInfoHead(head, null);
		}
				
		return result;
	}
	
	/**
	 * 부서정보 조회
	 */
	@POST 
	@Path("/retrieveDeptInfo")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public DeptInfoHead retrieveDeptInfo(UserInfoParam userInfoParam) {

		DeptInfoHead result = new DeptInfoHead();
		try {
			DeptInfoReturnData returnData = new DeptInfoReturnData();
			
			Group group = groupDao.get(userInfoParam.getGroupId());
			
			if(group != null){
				returnData.setPortalId(StringUtil.nvl(group.getPortalId(),""));
				returnData.setGroupId(StringUtil.nvl(group.getGroupId(),""));
				returnData.setGroupName(StringUtil.nvl(group.getGroupName(),""));
				returnData.setGroupEnglishName(StringUtil.nvl(group.getGroupEnglishName(),""));
				returnData.setFullPath(StringUtil.nvl(group.getFullPath(),""));
				returnData.setViewOption(StringUtil.nvl(group.getViewOption(),""));
				
				Head head = new Head(0, Response.Status.OK.toString());
				DeptInfoBody body = new DeptInfoBody(returnData);
				result = new DeptInfoHead(head, body);
			}else{
				Head head = new Head(1, "GroupInfo Empty");
				result = new DeptInfoHead(head, null);
			}
		
		} catch (Exception e) {
			Head head = new Head(2, "Error");
			result = new DeptInfoHead(head, null);
		}
				
		return result;
	}
	
	/**
	 * 부서검색
	 */
	@POST 
	@Path("/retrieveDeptList")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public DeptListHead retrieveDeptList(UserInfoParam userInfoParam) {

		DeptListHead result = new DeptListHead();
		try {
			List<DeptListReturnData> returnData = new ArrayList<DeptListReturnData>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("groupName", userInfoParam.getGroupName());
			
			List<Group> groupList = groupDao.selectGroupSearch(map);
			
			if(groupList != null){
				for(Group group : groupList){
					
					DeptListReturnData tempData = new DeptListReturnData();
					
					tempData.setPortalId(StringUtil.nvl(userInfoParam.getPortalId(),""));
					tempData.setGroupId(StringUtil.nvl(group.getGroupId(),""));
					tempData.setGroupName(StringUtil.nvl(group.getGroupName(),""));
					tempData.setGroupEnglishName(StringUtil.nvl(group.getGroupEnglishName(),""));
					tempData.setFullPath(StringUtil.nvl(group.getFullPath(),""));
					tempData.setViewOption(StringUtil.nvl(group.getViewOption(),""));
					
					returnData.add(tempData);
				}
				Head head = new Head(0, Response.Status.OK.toString());
				DeptListBody body = new DeptListBody(returnData);
				result = new DeptListHead(head, body);
			}else{
				Head head = new Head(1, "GroupInfo Empty");
				result = new DeptListHead(head, null);
			}
		
		} catch (Exception e) {
			Head head = new Head(2, "Error");
			result = new DeptListHead(head, null);
		}
				
		return result;
	}
	
	/**
	 * 사용자정보 조회
	 */
	@POST 
	@Path("/retrieveUserInfoByMobile")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserInfoMobileHead retrieveUserInfoByMobile(UserInfoParam userInfoParam) {

		UserInfoMobileHead result = new UserInfoMobileHead();
		try {
			UserInfoMobileReturnData returnData = new UserInfoMobileReturnData();
			
			String mobile = userInfoParam.getMobile();
			mobile = mobile.replaceAll("-", "");
			
			User user = userDao.getMobileUser(mobile);
			
			if(user != null){
				returnData.setPortalId(StringUtil.nvl(user.getPortalId(),""));
				returnData.setUserId(StringUtil.nvl(user.getUserId(), ""));
				returnData.setUserName(StringUtil.nvl(user.getUserName(), ""));
				returnData.setUserEnglishName(StringUtil.nvl(user.getUserEnglishName(), ""));
				returnData.setUserStatus(StringUtil.nvl(user.getUserStatus(), ""));
				returnData.setUserDeptCode(StringUtil.nvl(user.getGroupId(), ""));
				returnData.setUserDeptName(StringUtil.nvl(user.getTeamName(), ""));
				returnData.setUserDeptEnglishName(StringUtil.nvl(user.getTeamEnglishName(), ""));
				returnData.setUserTitle(StringUtil.nvl(user.getJobTitleName(), ""));
				returnData.setMobile(StringUtil.nvl(user.getMobile(), ""));
				returnData.setMail(StringUtil.nvl(user.getMail(), ""));
				returnData.setOfficePhoneNo(StringUtil.nvl(user.getOfficePhoneNo(), ""));
				returnData.setLocaleCode(StringUtil.nvl(user.getLocaleCode(), ""));
				returnData.setNationCode(StringUtil.nvl(user.getNationCode(), ""));
			
				Head head = new Head(0, Response.Status.OK.toString());
				UserInfoMobileBody body = new UserInfoMobileBody(returnData);
				result = new UserInfoMobileHead(head, body);
			}else{
				Head head = new Head(1, "UserInfo Empty");
				result = new UserInfoMobileHead(head, null);
			}
		
		} catch (Exception e) {
			Head head = new Head(2, "Error");
			result = new UserInfoMobileHead(head, null);
		}
				
		return result;
	}
}
